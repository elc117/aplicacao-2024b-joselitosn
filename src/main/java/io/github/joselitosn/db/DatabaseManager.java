package io.github.joselitosn.db;

import java.sql.Connection;
import java.sql.SQLException;

import io.github.joselitosn.db.providers.DatabaseProvider;

public final class DatabaseManager {
    private static DatabaseManager instance = null;
    private final DatabaseProvider provider;
    private Connection connection;

    private DatabaseManager(DatabaseProvider provider) {
        this.provider = provider;
        try {
            this.connection = provider.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("DatabaseManager not initialized");
        }
        return instance;
    }

    public static synchronized DatabaseManager getInstance(DatabaseProvider provider) {
        if (instance == null) {
            instance = new DatabaseManager(provider);
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = provider.getConnection();
            }
            return provider.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() throws SQLException {
        provider.closeConnection(connection);
    }
}
