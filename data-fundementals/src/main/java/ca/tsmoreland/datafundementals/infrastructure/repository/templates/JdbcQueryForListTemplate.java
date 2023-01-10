package ca.tsmoreland.datafundementals.infrastructure.repository.templates;

import ca.tsmoreland.datafundementals.infrastructure.repository.ConnectionProvider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class JdbcQueryForListTemplate<TEntity> {
    private final ConnectionProvider connectionProvider;

    public JdbcQueryForListTemplate(final ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public List<TEntity> queryForList(final String sql) {
        try (Connection connection = connectionProvider.getConnection();
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(sql);) {

            List<TEntity> entities = new ArrayList<>();
            while (results.next()) {
                entities.add(mapItem(results));
            }
            return entities;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public abstract TEntity mapItem(ResultSet results) throws SQLException;


}