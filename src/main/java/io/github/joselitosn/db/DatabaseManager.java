package io.github.joselitosn.db;

import java.sql.Connection;
import java.sql.SQLException;

import io.github.joselitosn.db.providers.DatabaseProvider;

public final class DatabaseManager {
    /**
     * Instância singleton do DatabaseManager.
     */
    private static DatabaseManager instance = null;

    /**
     * Provedor de banco de dados.
     */
    private final DatabaseProvider provider;

    /**
     * Construtor privado para garantir o padrão singleton.
     * @param provider O provedor de banco de dados a ser utilizado.
     */
    private DatabaseManager(DatabaseProvider provider) {
        this.provider = provider;
    }

    /**
     * Obtém a instância singleton do DatabaseManager.
     *
     * @return A instância singleton do DatabaseManager.
     * @throws IllegalStateException Se o DatabaseManager não foi inicializado.
     */
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("DatabaseManager not initialized");
        }
        return instance;
    }
    /**
     * Obtém a instância singleton do DatabaseManager, inicializando-a se necessário.
     *
     * @param provider O provedor de banco de dados a ser utilizado.
     * @return A instância singleton do DatabaseManager.
     */
    public static synchronized DatabaseManager getInstance(DatabaseProvider provider) {
        if (instance == null) {
            instance = new DatabaseManager(provider);
        }
        return instance;
    }
    /**
     * Obtém uma conexão com o banco de dados.
     *
     * @return Uma conexão com o banco de dados.
     * @throws SQLException Se ocorrer um erro ao obter a conexão.
     */
    public Connection getConnection() throws SQLException {
        return provider.getConnection();
    }
    /**
     * Fecha a conexão com o banco de dados.
     *
     * Este método deve ser chamado quando a conexão não for mais necessária.
     * Falha em chamar este método pode resultar em vazamento de recursos.
     *
     * @throws SQLException Se ocorrer um erro ao fechar a conexão.
     */
    public void closeConnection() throws SQLException {
        provider.closeConnection();
    }
}
