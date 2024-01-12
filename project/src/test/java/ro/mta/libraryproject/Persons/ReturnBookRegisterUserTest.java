package ro.mta.libraryproject.Persons;
import ro.mta.libraryproject.Library.Book;
import org.junit.Test;
import java.util.ArrayList;

public class ReturnBookRegisterUserTest {
    @Test
    public void testReturnBookRegister()
    {
        ArrayList<String> authors = new ArrayList<>();
        authors.add("Rodica");
        ArrayList<String> title = new ArrayList<>();
        authors.add("Luceafar");
        RegisteredUser registeredUser = new RegisteredUser(" ", "1234");
        Book book=new Book("12423", "test", " FFG", authors, "test", 399, 1, 12, 300);
        registeredUser.returnBook("Luceafar","Rodica");

        System.out.println(registeredUser.currentBorrowedBooks);

    }

}