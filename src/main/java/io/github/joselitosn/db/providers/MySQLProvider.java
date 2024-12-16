package io.github.joselitosn.db.providers;

/**
 * Fornecedor de banco de dados MySQL.
 */
public class MySQLProvider extends DatabaseProvider {

    /**
     * Construtor privado para o MySQLProvider.
     * @param builder O construtor de builder.
     */
    private MySQLProvider(Builder builder) {
        super(builder);
    }

    /**
     * Builder para o MySQLProvider.
     */
    public static class Builder extends DatabaseProvider.Builder {

        /**
         * Constroi o MySQLProvider.
         * @return O MySQLProvider construido.
         * @throws IllegalArgumentException Se a URL ou o nome do banco de dados for nulo ou vazio.
         */
        @Override
        public MySQLProvider build() {
            if (this.url == null || this.url.isEmpty()) {
                throw new IllegalArgumentException("URL cannot be null or empty.");
            }
            String database = this.properties.getProperty("database");
            if (database == null || database.isEmpty()){
                throw new IllegalArgumentException("O nome do banco de dados não pode ser nulo ou vazio.");
            }
            this.url("jdbc:mysql://" + this.url + "/" + this.properties.getProperty("database"));

            return new MySQLProvider(this);
        }
    }

    /**
     * Retorna uma representação em string do MySQLProvider.
     * @return Uma string representando o MySQLProvider.
     */
    @Override
    public String toString() {
        return "MySQL: " + this.url + " database: " + this.properties.getProperty("database");
    }
}
