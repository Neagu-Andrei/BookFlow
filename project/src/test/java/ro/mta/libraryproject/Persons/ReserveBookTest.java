package ro.mta.libraryproject.Persons;
import org.junit.Test;
import ro.mta.libraryproject.Library.Archive;
import ro.mta.libraryproject.Library.Book;
import static junit.framework.TestCase.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;


public class ReserveBookTest {
    @Test
    public void testReserveBook() {
        ArrayList<String> authors = new ArrayList<>();
        authors.add("Rodica");

        RegisteredUser registeredUser = new RegisteredUser(" ", "1234");

        LocalDate date = LocalDate.of(2021, 1, 8);
        Book book = new Book("12423", "test", " FFG", authors, "test", 399, 1, 12, 300);



        // Assert reservation date is not before current date
        assertEquals(registeredUser.reserveBook(book, date), false );

        // Assert correct printed string
        date = LocalDate.of(2021, 12, 30);
        assertEquals(registeredUser.reserveBook(book, date), true);

        // Assert book is available
        assertEquals(registeredUser.reserveBook(book, date), false);


    }
}

