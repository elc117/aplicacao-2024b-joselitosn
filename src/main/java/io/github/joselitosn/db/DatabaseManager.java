package io.github.joselitosn.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import io.github.joselitosn.db.providers.DatabaseProvider;
import io.github.joselitosn.db.providers.MySQLProvider;
import io.github.joselitosn.db.providers.SQLiteProvider;
import io.github.joselitosn.events.EventListener;
import io.github.joselitosn.events.EventManager;

import javax.xml.crypto.Data;

public final class DatabaseManager {
    private static DatabaseManager instance = null;
    private final DatabaseProvider provider;
    private Connection connection;
    private EventManager events = new EventManager("connect", "disconnect");

    private DatabaseManager(DatabaseProvider provider) {
        this.provider = provider;
        try {
            this.connection = provider.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerSubscriber(EventListener listener) {
        events.subscribe("connect", listener);
        events.subscribe("disconnect", listener);
    }

    public static DatabaseManager getInstance() {
        if (instance != null)
            return instance;

        // read database file location DB_URL from config file
        Properties properties = new Properties();
        try {
            properties.load(Files.newInputStream(Paths.get("db.properties")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String dbProvider = properties.getProperty("DB_PROVIDER");
        String dbUrl = properties.getProperty("DB_URL");
        String dbDatabase = properties.getProperty("DB_DATABASE");
        String dbUsername = properties.getProperty("DB_USERNAME");
        String dbPassword = properties.getProperty("DB_PASSWORD");

        // select provider based on config file
        DatabaseProvider provider;
        switch (dbProvider) {
            case "SQLite":
                provider = new SQLiteProvider.Builder().
                        url(dbUrl).
                        build();
                break;
            case "MySQL":
                provider = new MySQLProvider.Builder().
                        url(dbUrl).
                        database(dbDatabase).
                        username(dbUsername).
                        password(dbPassword).
                        build();
                break;
            default:
                throw new RuntimeException("Provedor de banco de dados inválido: " + dbProvider);
        }

        return getInstance(provider);
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
            events.notify("connect", "Connected to database:" + provider);
            return provider.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() throws SQLException {
        provider.closeConnection(connection);
        events.notify("disconnect", "Disconnected from database:" + provider);
    }
}
