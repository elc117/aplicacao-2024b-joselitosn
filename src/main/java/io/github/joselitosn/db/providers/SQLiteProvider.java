package io.github.joselitosn.db.providers;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class SQLiteProvider implements DatabaseProvider {
    private Connection connection;
    private final String DB_URL;

    public SQLiteProvider(String dbPath) {
        this.DB_URL = "jdbc:sqlite:" + dbPath;
    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL);
            }
            return connection;
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void closeConnection() throws SQLException {
        if (this.connection != null && !this.connection.isClosed()) {
            this.connection.close();
            this.connection = null;
        }
    }
}
