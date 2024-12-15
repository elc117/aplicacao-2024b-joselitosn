package io.github.joselitosn.db.providers;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseProvider {
    Connection connection = null;
    Connection getConnection() throws SQLException;
    void closeConnection() throws SQLException;
}
