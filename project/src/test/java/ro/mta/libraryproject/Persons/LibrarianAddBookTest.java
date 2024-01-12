package ro.mta.libraryproject.Persons;
import org.junit.Test;
import ro.mta.libraryproject.Library.Book;

import static junit.framework.TestCase.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;

public class LibrarianAddBookTest{

    @Test
    public void testAddBook() {
        /*
    }
        Librarian(String firstname, String lastname, LocalDate birthday,
                String address, String phone, String email,
                String username, String password)

         */

        ArrayList<String> authors = new ArrayList<>();
        authors.add("Bacovia");

        LocalDate date = LocalDate.of(2021, 1, 8);
        Book book = new Book("12423", "test", " FFG", authors, "test", 399, 1, 12, 300);
        Librarian librarian = new Librarian("Rodica", "Cretu", date, "Strada 9", "049234234994", "dmajsd@gmail.com", "rodica", "cretu");

        // Only case at the moment
        assertEquals(librarian.addBook(book), true );
    }
    }