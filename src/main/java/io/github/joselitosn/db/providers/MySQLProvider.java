package io.github.joselitosn.db.providers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MySQLProvider implements DatabaseProvider {
    private final String DB_URL;
    private final String DB_USERNAME;
    private final String DB_PASSWORD;

    private Connection connection;

    public MySQLProvider(String url, String database,String username, String password) {
        this.DB_URL = "jdbc:mysql://" + url + "/" + database;
        this.DB_USERNAME = username;
        this.DB_PASSWORD = password;
    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados MySQL: " + e.getMessage());
            throw e;
        }
        return connection;
    }

    @Override
    public void closeConnection() throws SQLException { connection.close(); }
}
