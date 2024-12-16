package io.github.joselitosn.db.providers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Fornecedor de conexão com o banco de dados.
 * Esta classe fornece métodos para obter e fechar conexões com o banco de dados.
 */
public class DatabaseProvider {
    protected final String url;
    protected final Properties properties;  // Use Properties for flexibility

    protected DatabaseProvider(Builder builder) {
        this.url = builder.url;
        this.properties = builder.properties;
    }

    /**
     * Construtor interno para criar instâncias de DatabaseProvider.
     */
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

    /**
     * Obtém uma conexão com o banco de dados.
     * @return Uma conexão com o banco de dados.
     */
    public Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(url, properties); // Use properties here
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            throw e;
        }

    }

    /**
     * Fecha a conexão com o banco de dados.
     * @param connection A conexão a ser fechada.
     * @throws SQLException Se ocorrer um erro ao fechar a conexão.
     */
    public void closeConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
