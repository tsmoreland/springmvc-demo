package ca.tsmoreland.datafundementals.infrastructure.repository;

import ca.tsmoreland.datafundementals.model.Book;
import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BookDao extends AbstractDao implements Dao<Book> {

    @Override
    public Book create(Book book) {
        final String sql = "INSERT INTO BOOKS (title) VALUE (?)";

        return execute(sql,
            statement -> {
                statement.setString(1, book.getTitle());
                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        book.setId(generatedKeys.getLong(1));
                    }

                }
                return book;
            },
            (c, s) -> c.prepareStatement(s, Statement.RETURN_GENERATED_KEYS),
            book);
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
    public Book update(Book book) {
        final String sql = "UPDATE BOOKS SET title = ? WHERE id = ?";

        return execute(sql,
            statement -> {
                statement.setString(1, book.getTitle());
                statement.setLong(2, book.getId());
                statement.executeUpdate();
                // not entirely happy with the returns here, well here ok but on error or exception we shouldn't be returning the same thing, any Optional<Book> would
                // at least tell us if the update worked (where empty means it failed.

                return book;
            },
            book);
    }

    @Override
    public int[] update(List<Book> entities) {
        final String sql = "UPDATE BOOKS SET title = ?, rating = ? WHERE ID = ?";
        return execute(sql, statement -> {
            for (Book book : entities) {
                statement.setString(1, book.getTitle());
                statement.setInt(2, book.getRating());
                statement.setLong(3, book.getId());
                statement.addBatch();
            }

            return statement.executeBatch();
        }, new int[0]);
    }

    @Override
    public int delete(Book book) {
        final String sql = "DELETE FROM BOOKS WHERE id = ?";
        return execute(sql, statement -> {
            statement.setLong(1, book.getId());
            return statement.executeUpdate();
        }, 0);
    }

    private Book readFromResults(ResultSet results) throws SQLException {
        var book = new Book();
        book.setId(results.getLong("id"));
        book.setTitle(results.getString("title"));
        return book;
    }

    private <TResult> TResult execute(final String sql, PreparedStatementConsumer<TResult> consumer, final TResult defaultValue) {
       return execute(sql, consumer, Connection::prepareStatement, defaultValue);
    }
    private <TResult> TResult execute(final String sql, PreparedStatementConsumer<TResult> consumer, PreparedStatementProducer producer, final TResult defaultValue) {
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

    @FunctionalInterface
    interface PreparedStatementProducer {
        PreparedStatement produce(Connection connection, final String sql) throws SQLException;
    }

}
