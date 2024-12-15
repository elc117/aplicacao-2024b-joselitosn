package io.github.joselitosn.db.providers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseProvider {
    protected final String url;
    protected final Properties properties;  // Use Properties for flexibility

    protected DatabaseProvider(Builder builder) {
        this.url = builder.url;
        this.properties = builder.properties;
    }

    public static class Builder {
        protected String url;
        protected Properties properties = new Properties(); // Initialize in builder

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder database(String database) {
            this.properties.setProperty("database", database);
            return this;
        }

        public Builder username(String username) {
            this.properties.setProperty("user", username);  // Use standard "user" property
            return this;
        }

        public Builder password(String password) {
            this.properties.setProperty("password", password);
            return this;

        }

        public DatabaseProvider build() {
            if (this.url == null || this.url.isEmpty()) {
                throw new IllegalArgumentException("URL cannot be null or empty.");
            }
            return new DatabaseProvider(this);
        }
    }

    public Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(url, properties); // Use properties here
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            throw e;
        }

    }

    public void closeConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
