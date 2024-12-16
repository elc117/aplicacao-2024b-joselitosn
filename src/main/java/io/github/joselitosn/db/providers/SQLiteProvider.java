package io.github.joselitosn.db.providers;

/**
 * Provider de banco de dados SQLite.
 */
public class SQLiteProvider extends DatabaseProvider {

    /**
     * Construtor privado para a classe SQLiteProvider.
     * @param builder O builder usado para criar a instância.
     */
    private SQLiteProvider(Builder builder) {
        super(builder);
    }

    /**
     * Builder para a classe SQLiteProvider.
     */
    public static class Builder extends DatabaseProvider.Builder {

        @Override
        public SQLiteProvider build() {
            if (this.url == null || this.url.isEmpty()) {
                throw new IllegalArgumentException("URL cannot be null or empty.");
            }
            this.url("jdbc:sqlite:" + this.url);
            return new SQLiteProvider(this);
        }
    }

    /**
     * Retorna uma representação em string do provider.
     */
    @Override
    public String toString() {
        return "SQLite: " + this.url;
    }
}
