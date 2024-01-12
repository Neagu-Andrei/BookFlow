package ro.mta.libraryproject.Persons;

import junit.framework.TestCase;
import org.junit.Test;
import java.time.LocalDate;

public class GuestUserTest extends TestCase {

    @Test
    public void testViewBooks() {
        LocalDate date = LocalDate.now();
        GuestUser guestUser = new GuestUser("user", "dorel", "dorel", date, "aici", "07", "mail", "dorel", "asld");
        assertEquals(java.util.Optional.ofNullable(guestUser.viewBooks()), java.util.Optional.ofNullable(true));
    }
}