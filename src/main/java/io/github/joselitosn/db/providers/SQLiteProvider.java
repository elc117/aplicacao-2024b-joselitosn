package io.github.joselitosn.db.providers;

public class SQLiteProvider extends DatabaseProvider {

    private SQLiteProvider(Builder builder) {
        super(builder);
    }

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
}
