package ca.tsmoreland.datafundementals;

import ca.tsmoreland.datafundementals.infrastructure.repository.BookDao;
import ca.tsmoreland.datafundementals.infrastructure.repository.Dao;
import ca.tsmoreland.datafundementals.model.Book;

import java.util.List;

public class App {
    public static void main( String[] args ) {

        Dao<Book> bookDao = new BookDao();
        List<Book> books = bookDao.findAll();

        for (Book book : books) {
            System.out.println(book);
        }

    }
}
