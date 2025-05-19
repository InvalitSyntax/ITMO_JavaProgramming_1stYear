package org.example.collectionClasses.app;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import org.apache.commons.codec.digest.DigestUtils;
import org.example.collectionClasses.model.*;

public class DBManager implements AutoCloseable {
    private Connection connection;
    private boolean originalAutoCommit;

    public DBManager() {
        try {
            this.connect();
            this.originalAutoCommit = connection.getAutoCommit();
        } catch (SQLException e) {
            handleDatabaseError(e, "Не удалось подключиться к базе данных");
            throw new RuntimeException("Database connection failed", e);
        }
    }

    // ========== Authorization Methods ==========
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

    // ========== Collection Methods ==========
    public SpaceMarineCollectionManager loadCollection() {
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

    // ========== Helper Methods ==========
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
    
    public void clearCollection() {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM space_marines");
        } catch (SQLException e) {
            handleDatabaseError(e, "Ошибка при добавлении элемента");
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

    // ========== Connection Management ==========
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

    // ========== Utility Methods ==========
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

    private void handleDatabaseError(AppController app, SQLException e, String message) {
        e.printStackTrace();
        app.getIoManager().writeMessage(message + ": " + e.getMessage(), false);
    }

    private void handleDatabaseError(SQLException e, String message) {
        e.printStackTrace();
        System.err.println(message + ": " + e.getMessage());
    }
}