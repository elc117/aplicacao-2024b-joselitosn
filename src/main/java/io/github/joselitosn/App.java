package io.github.joselitosn;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import io.github.joselitosn.db.DatabaseManager;
import io.github.joselitosn.db.providers.DatabaseProvider;
import io.github.joselitosn.db.providers.MySQLProvider;
import io.github.joselitosn.db.providers.SQLiteProvider;

public class App {
    public static void main(String[] args) {
        String dbProvider;
        String dbUrl;
        String dbDatabase;
        String dbUsername;
        String dbPassword;

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
        if (dbProvider.equals("SQLite")) {
            provider = new SQLiteProvider(dbUrl);
        } else if (dbProvider.equals("MySQL")) {
            provider = new MySQLProvider(dbUrl, dbDatabase, dbUsername, dbPassword);
        } else {
            throw new RuntimeException("Provedor de banco de dados inválido: " + dbProvider);
        }

        // instância o singleton com o provider selecionado
        DatabaseManager dbManager = DatabaseManager.getInstance(provider);
        Connection connection;
        try {
            connection = dbManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
