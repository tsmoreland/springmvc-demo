package ca.tsmoreland.datafundementals.infrastructure.repository;

import ca.tsmoreland.datafundementals.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BookDao extends AbstractDao implements Dao<Book> {

    @Override
    public void add(Book book) {

    }

    @Override
    public Optional<Book> findById(long id) {
        final String sql = "SELECT id, title FROM BOOKS WHERE ID = ?";
        return execute(sql, statement -> {
            statement.setLong(1, id);

            try (ResultSet results  = statement.executeQuery()) {
                if (results.next()) {
                    return Optional.of(readFromResults(results));
                }
            }
            return Optional.empty();
        }, Optional.empty());
    }

    @Override
    public List<Book> findAll() {
        final String sql = "SELECT * FROM BOOKS";

        try (Connection conn = getConnection();
             Statement statement = conn.createStatement();
             ResultSet results  = statement.executeQuery(sql)) {

            final List<Book> books = new ArrayList<>();;
            while (results.next()) {
                books.add(readFromResults(results));
            }
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public void update(Book book, String[] params) {

    }

    @Override
    public void delete(Book book) {

    }

    private Book readFromResults(ResultSet results) throws SQLException {
        var book = new Book();
        book.setId(results.getLong("id"));
        book.setTitle(results.getString("title"));
        return book;
    }

    private <TResult> TResult execute(String sql, PreparedStatementConsumer<TResult> consumer, TResult defaultValue) {
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            return consumer.consume(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return defaultValue;
    }

    @FunctionalInterface
    interface PreparedStatementConsumer<TResult> {
        TResult consume(PreparedStatement statement) throws SQLException;
    }
}
