package ro.mta.libraryproject.Persons;
import org.junit.Test;
import ro.mta.libraryproject.Library.Book;

import java.time.LocalDate;
import java.util.ArrayList;


public class BorrowBookTest {
    @Test
    public void testBorrowBook() {
        LocalDate date = LocalDate.of(2021, 1, 8);
        ArrayList<String> authors = new ArrayList<>();
        authors.add("Rodica");
        RegisteredUser registeredUser = new RegisteredUser(" ", "1234");
        Book book = new Book("12423", "test", " FFG", authors, "test", 399, 1, 12, 300);
        registeredUser.reserveBook(book, date);

        System.out.println(registeredUser.currentBorrowedBooks);

    }
}