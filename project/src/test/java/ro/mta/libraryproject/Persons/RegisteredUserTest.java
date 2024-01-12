package ro.mta.libraryproject.Persons;

import junit.framework.TestCase;

import java.time.LocalDate;

public class RegisteredUserTest extends TestCase {

    public void testGetRequests() {
        RegisteredUser registeredUser;
        registeredUser = new RegisteredUser("  ", "Paul", LocalDate.now(), "Acasa, Nr. 10", "0722765678", "paul@  .com", "paul.  ", "1234");
        System.out.println(registeredUser.getRequests("books"));
        System.out.println(registeredUser.getRequests("archive"));

    }
}