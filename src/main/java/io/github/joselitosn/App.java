package io.github.joselitosn;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.github.joselitosn.db.DatabaseManager;
import io.github.joselitosn.db.providers.DatabaseProvider;
import io.github.joselitosn.db.providers.MySQLProvider;
import io.github.joselitosn.db.providers.SQLiteProvider;
import io.github.joselitosn.log.LogEventListener;
import io.github.joselitosn.log.LogManager;
import io.github.joselitosn.notifications.Notification;
import io.github.joselitosn.notifications.NotificationHandler;
import io.github.joselitosn.notifications.NtfyNotification;
import io.github.joselitosn.notifications.SMTPNotification;

public class App {
    public static void main(String[] args) {
        String dbProvider;
        String dbUrl;
        String dbDatabase;
        String dbUsername;
        String dbPassword;

        Logger logger = Logger.getLogger("justsched");
        LogManager logManager = LogManager.getInstance(logger);

        try {
            java.util.logging.LogManager.getLogManager().readConfiguration(Files.newInputStream(Paths.get("logger.properties")));
        } catch (SecurityException | IOException e1) {
            e1.printStackTrace();
        }

        logger.setLevel(Level.INFO);
        try {
            logger.addHandler(new FileHandler("justsched.log", true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // read database file location DB_URL from config file
        Properties properties = new Properties();
        try {
            properties.load(Files.newInputStream(Paths.get("db.properties")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dbProvider = properties.getProperty("DB_PROVIDER");
        dbUrl = properties.getProperty("DB_URL");
        dbDatabase = properties.getProperty("DB_DATABASE");
        dbUsername = properties.getProperty("DB_USERNAME");
        dbPassword = properties.getProperty("DB_PASSWORD");


        // select provider based on config file
        DatabaseProvider provider;
        switch (dbProvider) {
            case "SQLite":
                provider = new SQLiteProvider.Builder()
                        .url(dbUrl)
                        .build();
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

        // instância o singleton com o provider selecionado
        DatabaseManager dbManager = DatabaseManager.getInstance(provider);
        dbManager.registerSubscriber(new LogEventListener(logger));
        Connection connection = dbManager.getConnection();

        Notification notification = new Notification("Test", "Primeiro Teste");

        NotificationHandler chain = new SMTPNotification("joselitostrike@gmail.com");
        chain.setNextHandler(new NtfyNotification("https://ntfy.sh/", "notifications"));

        chain.handle(notification);

        // create table
        try {
            createTable(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // close connection
        try {
            dbManager.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // create table
    public static void createTable(Connection connection) throws SQLException {
        // create table if not exists using prepared statement in sqlite
        String sql = "CREATE TABLE IF NOT EXISTS mytable (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
    }
}
