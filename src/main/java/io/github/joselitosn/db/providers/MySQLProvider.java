package io.github.joselitosn.db.providers;

public class MySQLProvider extends DatabaseProvider {

    private MySQLProvider(Builder builder) {
        super(builder);
    }

    public static class Builder extends DatabaseProvider.Builder {

        @Override
        public MySQLProvider build() {
            if (this.url == null || this.url.isEmpty()) {
                throw new IllegalArgumentException("URL cannot be null or empty.");
            }
            if (this.properties.getProperty("database") == null || this.properties.getProperty("database").isEmpty()){
                throw new IllegalArgumentException("Database cannot be null or empty.");
            }
            this.url("jdbc:mysql://" + this.url + "/" + this.properties.getProperty("database"));

            return new MySQLProvider(this);
        }
    }

    @Override
    public String toString() {
        return "MySQL: " + this.url + " database: " + this.properties.getProperty("database");
    }
}
