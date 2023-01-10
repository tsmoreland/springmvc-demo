package ca.tsmoreland.datafundementals;

import ca.tsmoreland.datafundementals.infrastructure.repository.BookDao;
import ca.tsmoreland.datafundementals.infrastructure.repository.Dao;
import ca.tsmoreland.datafundementals.infrastructure.repository.MySqlConnectionProvider;
import ca.tsmoreland.datafundementals.model.Book;

import java.util.List;
import java.util.Optional;

public class App {
    public static void main( String[] args ) {

        Dao<Book> bookDao = new BookDao(new MySqlConnectionProvider());
        List<Book> seedData = List.of(
            new Book("Batman: I am Gotham"),
            new Book("Batman: I am Suicide"),
            new Book("Batman: I am Bane"),
            new Book("Batman: The war of Jokes and Riddles")
        );

        for (Book book : seedData) {
            bookDao.create(book);
        }

        List<Book> books = bookDao.findAll();

        for (Book book : books) {
            System.out.println(book);
        }

        Optional<Book> maybeBook = bookDao.findById(1);
        if (maybeBook.isPresent()) {
            System.out.println(maybeBook.get());
            maybeBook.get().setTitle("Batman City of Bane Part 2");
            var updatedBook = bookDao.update(maybeBook.get());
            System.out.println(updatedBook);
        }

        var templateForCreated = new Book();
        templateForCreated.setTitle("Batman City of Bane Part 1");

        var created = bookDao.create(templateForCreated);
        System.out.println(created);

        List<Book> updatedBooks = books.stream()
            .peek(b -> b.setRating(5))
            .toList();
        bookDao.update(updatedBooks);

        int deleted = bookDao.delete(created);
        System.out.printf("Deleted %d records%n", deleted);

        for (Book book : seedData) {
            bookDao.delete(book);
        }
    }
}
