package org.example.collectionClasses.app;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.codec.digest.DigestUtils;

public class DBManager implements AutoCloseable {
    private Connection connection;

    public DBManager() {
        try {
            this.connect();
            this.connection.setAutoCommit(true);
        } catch (SQLException e) {
            handleDatabaseError(e, "Не удалось подключиться к базе данных");
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
                    if (rs.next()) {
                        String storedHash = rs.getString("password_hash");
                        if (storedHash.equals(hashedPassword)) {
                            return true;
                        }
                        return false;
                    }
                    return false;
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

    @Override
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
    
    private void connect() throws SQLException {
        this.connection = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/studs",
            "s467433",
            "gtsNAsET0QoUHh3Q");
        this.connection.setAutoCommit(true);
    }

    private boolean validateCredentials(AppController app, String login, String password) {
        if (login == null || login.isEmpty() || password == null || password.isEmpty()) {
            app.getIoManager().writeMessage("Логин и пароль не могут быть пустыми", false);
            return false;
        }
        return true;
    }

    private boolean validateCredentials(String login, String password) {
        if (login == null || login.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        return true;
    }

    private void ensureConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect();
        }
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

    private void createUser(String login, String hashedPassword) throws SQLException {
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
        System.out.println(message + ": " + e.getMessage());
        app.getIoManager().writeMessage(message + ": " + e.getMessage(), false);
    }

    private void handleDatabaseError(SQLException e, String message) {
        //e.printStackTrace();
        System.out.println(message + ": " + e.getMessage());
    }
}