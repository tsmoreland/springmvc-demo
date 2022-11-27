package ca.tsmoreland.datafundementals.infrastructure.repository.templates;

import ca.tsmoreland.datafundementals.infrastructure.repository.ConnectionProvider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public abstract class JdbcQueryForSingleTemplate<TEntity> {
    private final ConnectionProvider connectionProvider;

    public JdbcQueryForSingleTemplate(final ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public Optional<TEntity> queryForSingle(final String sql) {
        try (Connection connection = connectionProvider.getConnection();
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(sql);) {

            if (results.next()) {
                return Optional.of(mapItem(results));
            }
            return Optional.empty();

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public abstract TEntity mapItem(ResultSet results) throws SQLException;
}
