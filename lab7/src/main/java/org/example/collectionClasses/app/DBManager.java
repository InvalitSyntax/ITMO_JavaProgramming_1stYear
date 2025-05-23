package org.example.collectionClasses.app;

import java.net.ConnectException;
import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import org.apache.commons.codec.digest.DigestUtils;
import org.example.collectionClasses.model.*;
import org.postgresql.util.PSQLException;

public class DBManager implements AutoCloseable {
    private Connection connection;
    private boolean originalAutoCommit;

    public DBManager() {
        try {
            this.connect();
            this.originalAutoCommit = connection.getAutoCommit();
        } catch (SQLException e) {
            handleDatabaseError(e, "Не удалось подключиться к базе данных");
            //throw new RuntimeException("Database connection failed", e);
        }
    }

    public synchronized boolean updateElement(int id, SpaceMarine updatedMarine) {
        try {
            ensureConnection();
            originalAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            if (!marineExists(id)) {
                return false;
            }

            int coordId = getMarineCoordinateId(id);
            updateCoordinates(coordId, updatedMarine.getCoordinates());

            Integer chapterId = getMarineChapterId(id);
            if (updatedMarine.getChapter() != null) {
                if (chapterId != null) {
                    updateChapter(chapterId, updatedMarine.getChapter());
                } else {
                    chapterId = saveChapter(updatedMarine.getChapter());
                }
            } else if (chapterId != null) {
                deleteChapter(chapterId);
                chapterId = null;
            }

            boolean result = updateMarine(id, updatedMarine, coordId, chapterId);

            if (result) {
                connection.commit();
            } else {
                connection.rollback();
            }

            return result;
        } catch (SQLException e) {
            try {
                if (connection != null && !connection.getAutoCommit()) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                System.err.println("Ошибка отката транзакции: " + ex.getMessage());
            }
            handleDatabaseError(e, "Ошибка при обновлении элемента");
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(originalAutoCommit);
                }
            } catch (SQLException e) {
                System.err.println("Ошибка восстановления autoCommit: " + e.getMessage());
            }
        }
    }


    private boolean marineExists(int id) throws SQLException {
        String sql = "SELECT 1 FROM space_marines WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private int getMarineCoordinateId(int marineId) throws SQLException {
        String sql = "SELECT coord_id FROM space_marines WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, marineId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("coord_id");
                }
            }
        }
        throw new SQLException("Координаты не найдены для десантника с ID: " + marineId);
    }

    private Integer getMarineChapterId(int marineId) throws SQLException {
        String sql = "SELECT chapter_id FROM space_marines WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, marineId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getObject("chapter_id", Integer.class) : null;
            }
        }
    }

    private void updateCoordinates(int coordId, Coordinates coord) throws SQLException {
        String sql = "UPDATE coordinates SET x = ?, y = ? WHERE coord_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, coord.getX());
            if (coord.getY() != null) {
                stmt.setFloat(2, coord.getY());
            } else {
                stmt.setNull(2, Types.FLOAT);
            }
            stmt.setInt(3, coordId);
            stmt.executeUpdate();
        }
    }

    private void updateChapter(int chapterId, Chapter chapter) throws SQLException {
        String sql = "UPDATE chapters SET name = ?, world = ? WHERE chapter_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, chapter.getName());
            stmt.setString(2, chapter.getWorld());
            stmt.setInt(3, chapterId);
            stmt.executeUpdate();
        }
    }

    private void deleteChapter(int chapterId) throws SQLException {
        String sql = "DELETE FROM chapters WHERE chapter_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, chapterId);
            stmt.executeUpdate();
        }
    }

    private boolean updateMarine(int id, SpaceMarine marine, int coordId, Integer chapterId) throws SQLException {
        String sql = "UPDATE space_marines SET " +
                    "name = ?, " +
                    "creation_date = ?, " +
                    "health = ?, " +
                    "loyal = ?, " +
                    "weapon_type = ?, " +
                    "melee_weapon = ?, " +
                    "chapter_id = ?, " +
                    "coord_id = ? " +
                    "WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, marine.getName());
            stmt.setTimestamp(2, Timestamp.from(marine.getCreationDate().toInstant()));
            
            if (marine.getHealth() != null) {
                stmt.setFloat(3, marine.getHealth());
            } else {
                stmt.setNull(3, Types.FLOAT);
            }
            
            stmt.setBoolean(4, marine.getLoyal());
            
            if (marine.getWeaponType() != null) {
                stmt.setString(5, marine.getWeaponType().name());
            } else {
                stmt.setNull(5, Types.VARCHAR);
            }
            
            if (marine.getMeleeWeapon() != null) {
                stmt.setString(6, marine.getMeleeWeapon().name());
            } else {
                stmt.setNull(6, Types.VARCHAR);
            }
            
            if (chapterId != null) {
                stmt.setInt(7, chapterId);
            } else {
                stmt.setNull(7, Types.INTEGER);
            }
            
            stmt.setInt(8, coordId);
            stmt.setInt(9, id);
            
            return stmt.executeUpdate() > 0;
        }
    }

    public synchronized boolean removeElementById(int id) {
        boolean originalAutoCommit = true;
        try {
            ensureConnection();
            originalAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            // 1. Получаем зависимости
            Integer coordId = getMarineCoordinateId(id);
            Integer chapterId = getMarineChapterId(id);

            // 2. Удаляем самого десантника
            String deleteMarineSql = "DELETE FROM space_marines WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(deleteMarineSql)) {
                stmt.setInt(1, id);
                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    connection.rollback();
                    return false;
                }
            }

            // 3. Удаляем координаты
            if (coordId != null) {
                String deleteCoordSql = "DELETE FROM coordinates WHERE coord_id = ?";
                try (PreparedStatement stmt = connection.prepareStatement(deleteCoordSql)) {
                    stmt.setInt(1, coordId);
                    stmt.executeUpdate();
                }
            }

            // 4. Удаляем главу (если есть и больше не используется)
            if (chapterId != null && !isChapterUsedElsewhere(chapterId, id)) {
                String deleteChapterSql = "DELETE FROM chapters WHERE chapter_id = ?";
                try (PreparedStatement stmt = connection.prepareStatement(deleteChapterSql)) {
                    stmt.setInt(1, chapterId);
                    stmt.executeUpdate();
                }
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                if (connection != null && !connection.getAutoCommit()) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                System.err.println("Ошибка при откате транзакции: " + ex.getMessage());
            }
            handleDatabaseError(e, "Ошибка при удалении элемента");
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(originalAutoCommit);
                }
            } catch (SQLException e) {
                System.err.println("Ошибка восстановления autoCommit: " + e.getMessage());
            }
        }
    }
    private boolean isChapterUsedElsewhere(int chapterId, int excludeMarineId) throws SQLException {
        String sql = "SELECT 1 FROM space_marines WHERE chapter_id = ? AND id != ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, chapterId);
            stmt.setInt(2, excludeMarineId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
    public boolean authorize(String login, String password) {
        if (!validateCredentials(login, password)) {
            return false;
        }

        String hashedPassword = DigestUtils.md5Hex(password);

        try {
            ensureConnection();
            String sql = "SELECT password_hash FROM users WHERE login = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, login);
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next() && rs.getString("password_hash").equals(hashedPassword);
                }
            }
        } catch (SQLException e) {
            handleDatabaseError(e, "Ошибка при авторизации");
            return false;
        }
    }

    public boolean authorize(AppController app, String login, String password) {
        if (!validateCredentials(app, login, password)) {
            return false;
        }

        String hashedPassword = DigestUtils.md5Hex(password);

        try {
            ensureConnection();
            String sql = "SELECT password_hash FROM users WHERE login = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, login);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String storedHash = rs.getString("password_hash");
                        if (storedHash.equals(hashedPassword)) {
                            app.getIoManager().writeMessage("Авторизация успешна", false);
                            return true;
                        }
                        app.getIoManager().writeMessage("Неверный пароль", false);
                        return false;
                    }
                    app.getIoManager().writeMessage("Пользователь не найден", false);
                    return false;
                }
            }
        } catch (SQLException e) {
            handleDatabaseError(app, e, "Ошибка при авторизации");
            return false;
        }
    }

    public boolean register(AppController app, String login, String password) {
        if (!validateCredentials(app, login, password)) {
            return false;
        }

        String hashedPassword = DigestUtils.md5Hex(password);

        try {
            ensureConnection();
            
            if (userExists(login)) {
                app.getIoManager().writeMessage("Пользователь уже существует", false);
                return false;
            }

            createUser(login, hashedPassword);
            app.getIoManager().writeMessage("Регистрация успешна", false);
            return true;
            
        } catch (SQLException e) {
            handleDatabaseError(app, e, "Ошибка при регистрации");
            return false;
        }
    }

    public synchronized SpaceMarineCollectionManager loadCollection() {
        Deque<SpaceMarine> collection = new ConcurrentLinkedDeque<>();
        SpaceMarineCollectionManager collectionManager = new SpaceMarineCollectionManager();

        try {
            ensureConnection();
            
            String sql = "SELECT sm.*, c.x, c.y, ch.name as chapter_name, ch.world " +
                         "FROM space_marines sm " +
                         "JOIN coordinates c ON sm.coord_id = c.coord_id " +
                         "LEFT JOIN chapters ch ON sm.chapter_id = ch.chapter_id";
            
            try (PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    SpaceMarine marine = mapRowToSpaceMarine(rs);
                    collection.add(marine);
                }
            }
            
            updateCollectionMetadata(!collection.isEmpty(), collectionManager);
            collectionManager.setMarines(collection);
            
        } catch (SQLException e) {
            handleDatabaseError(e, "Ошибка загрузки коллекции");
        }
        
        return collectionManager;
    }

    public synchronized boolean addElement(SpaceMarine marine) {
        try {
            ensureConnection();
            originalAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            
            boolean result = addElementInternal(marine);
            
            if (result) {
                connection.commit();
            } else {
                connection.rollback();
            }
            
            return result;
            
        } catch (SQLException e) {
            try {
                if (connection != null && !connection.getAutoCommit()) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                System.err.println("Ошибка отката транзакции: " + ex.getMessage());
            }
            handleDatabaseError(e, "Ошибка при добавлении элемента");
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(originalAutoCommit);
                }
            } catch (SQLException e) {
                System.err.println("Ошибка восстановления autoCommit: " + e.getMessage());
            }
        }
    }

    private SpaceMarine mapRowToSpaceMarine(ResultSet rs) throws SQLException {
        SpaceMarine marine = new SpaceMarine();
        marine.setId(rs.getInt("id"));
        marine.setName(rs.getString("name"));
        marine.setCreationDate(rs.getTimestamp("creation_date")
            .toInstant().atZone(ZoneId.systemDefault()));
        marine.setHealth(rs.getObject("health", Float.class));
        marine.setLoyal(rs.getBoolean("loyal"));
        marine.setUserLogin(rs.getString("login"));
        
        Coordinates coord = new Coordinates();
        coord.setX(rs.getDouble("x"));
        coord.setY(rs.getObject("y", Float.class));
        marine.setCoordinates(coord);
        
        String weaponType = rs.getString("weapon_type");
        if (weaponType != null) marine.setWeaponType(Weapon.valueOf(weaponType));
        
        String meleeWeapon = rs.getString("melee_weapon");
        if (meleeWeapon != null) marine.setMeleeWeapon(MeleeWeapon.valueOf(meleeWeapon));
        
        String chapterName = rs.getString("chapter_name");
        if (chapterName != null) {
            Chapter chapter = new Chapter();
            chapter.setName(chapterName);
            chapter.setWorld(rs.getString("world"));
            marine.setChapter(chapter);
        }
        
        return marine;
    }
    
    private void updateCollectionMetadata(boolean hasElements, 
                                       SpaceMarineCollectionManager manager) throws SQLException {
        String sql = "UPDATE collection_metadata SET creation_date = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            if (hasElements) {
                stmt.setTimestamp(1, Timestamp.from(manager.getCreationDate().toInstant()));
            } else {
                stmt.setNull(1, Types.TIMESTAMP);
            }
            stmt.executeUpdate();
        }
    }
    
    public synchronized boolean clearCollection(String login) {
        boolean originalAutoCommit = true;
        try {
            ensureConnection();
            originalAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            // 1. Получаем все ID десантников пользователя
            List<Integer> marineIds = new ArrayList<>();
            String selectSql = "SELECT id, coord_id, chapter_id FROM space_marines WHERE login = ?";
            try (PreparedStatement stmt = connection.prepareStatement(selectSql)) {
                stmt.setString(1, login);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        marineIds.add(rs.getInt("id"));
                    }
                }
            }

            // 2. Удаляем каждого десантника с зависимостями
            for (int id : marineIds) {
                if (!removeElementById(id)) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                if (connection != null && !connection.getAutoCommit()) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                System.err.println("Ошибка при откате транзакции: " + ex.getMessage());
            }
            handleDatabaseError(e, "Ошибка при очистке коллекции");
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(originalAutoCommit);
                }
            } catch (SQLException e) {
                System.err.println("Ошибка восстановления autoCommit: " + e.getMessage());
            }
        }
    }
    
    private boolean addElementInternal(SpaceMarine marine) throws SQLException {
        int coordId = saveCoordinates(marine.getCoordinates());
        Integer chapterId = marine.getChapter() != null ? saveChapter(marine.getChapter()) : null;
        
        String sql = "INSERT INTO space_marines " +
                     "(login, name, coord_id, creation_date, health, loyal, " +
                     "weapon_type, melee_weapon, chapter_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, 
             Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, marine.getUserLogin());
            stmt.setString(2, marine.getName());
            stmt.setInt(3, coordId);
            stmt.setTimestamp(4, Timestamp.from(marine.getCreationDate().toInstant()));
            
            if (marine.getHealth() != null) {
                stmt.setFloat(5, marine.getHealth());
            } else {
                stmt.setNull(5, Types.FLOAT);
            }
            
            stmt.setBoolean(6, marine.getLoyal());
            
            if (marine.getWeaponType() != null) {
                stmt.setString(7, marine.getWeaponType().name());
            } else {
                stmt.setNull(7, Types.VARCHAR);
            }
            
            if (marine.getMeleeWeapon() != null) {
                stmt.setString(8, marine.getMeleeWeapon().name());
            } else {
                stmt.setNull(8, Types.VARCHAR);
            }
            
            if (chapterId != null) {
                stmt.setInt(9, chapterId);
            } else {
                stmt.setNull(9, Types.INTEGER);
            }
            
            if (stmt.executeUpdate() == 0) {
                return false;
            }
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    marine.setId(rs.getInt(1));
                }
            }
        }
        return true;
    }

    private int saveCoordinates(Coordinates coord) throws SQLException {
        String sql = "INSERT INTO coordinates (x, y) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, 
             Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setDouble(1, coord.getX());
            
            if (coord.getY() != null) {
                stmt.setFloat(2, coord.getY());
            } else {
                stmt.setNull(2, Types.FLOAT);
            }
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Не удалось сохранить координаты");
    }

    private int saveChapter(Chapter chapter) throws SQLException {
        String sql = "INSERT INTO chapters (name, world) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, 
             Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, chapter.getName());
            stmt.setString(2, chapter.getWorld());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Не удалось сохранить главу");
    }

    private void connect() throws SQLException {
        this.connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/studs",
        "s467433",
        "gtsNAsET0QoUHh3Q");
    }

    private void ensureConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect();
        }
    }

    @Override
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    private boolean validateCredentials(AppController app, String login, String password) {
        if (login == null || login.isEmpty() || password == null || password.isEmpty()) {
            app.getIoManager().writeMessage("Логин и пароль не могут быть пустыми", false);
            return false;
        }
        return true;
    }

    private boolean validateCredentials(String login, String password) {
        return !(login == null || login.isEmpty() || password == null || password.isEmpty());
    }

    private boolean userExists(String login) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE login = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private synchronized void createUser(String login, String hashedPassword) throws SQLException {
        String sql = "INSERT INTO users (login, password_hash) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, hashedPassword);
            if (stmt.executeUpdate() == 0) {
                throw new SQLException("Не удалось создать пользователя");
            }
        }
    }

    private void handleDatabaseError(AppController app, Exception e, String message) {
        //e.printStackTrace();
        app.getIoManager().writeMessage(message + ": " + e.getMessage(), false);
    }

    private void handleDatabaseError(Exception e, String message) {
        //e.printStackTrace();
        System.err.println(message + ": " + e.getMessage());
    }
}