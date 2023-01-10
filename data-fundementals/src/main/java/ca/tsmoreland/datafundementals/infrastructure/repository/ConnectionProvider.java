package ca.tsmoreland.datafundementals.infrastructure.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionProvider {
    Connection getConnection() throws SQLException;
}
