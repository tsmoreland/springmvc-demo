package ca.tsmoreland.datafundementals;

import ca.tsmoreland.datafundementals.infrastructure.repository.BookDao;
import ca.tsmoreland.datafundementals.infrastructure.repository.Dao;
import ca.tsmoreland.datafundementals.model.Book;

import java.util.List;
import java.util.Optional;

public class App {
    public static void main( String[] args ) {

        Dao<Book> bookDao = new BookDao();
        List<Book> books = bookDao.findAll();

        for (Book book : books) {
            System.out.println(book);
        }

        Optional<Book> maybeBook = bookDao.findById(1);
        maybeBook.ifPresent(System.out::println);

        var templateForCreated = new Book();
        templateForCreated.setTitle("Batman City of Bane Part 1");

        var created = bookDao.create(templateForCreated);
        System.out.println(created);

    }
}
