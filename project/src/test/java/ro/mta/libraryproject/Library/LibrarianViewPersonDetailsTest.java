package ro.mta.libraryproject.Library;
import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;
import ro.mta.libraryproject.Main.ManagerDB;
import ro.mta.libraryproject.Persons.RegisteredUser;

import java.time.LocalDate;


/**
 *  Merlus Mihai
 */
public class LibrarianViewPersonDetailsTest extends TestCase {
    @Test
    public void testViewPersonsDetailsTest(){

        ManagerDB managerDB=new ManagerDB();
        LocalDate date = LocalDate.of(2020, 12, 12);

        RegisteredUser user=new RegisteredUser("jhgf","df",date,"sa","as","sa","ds","8bd574fdb05c2dc5017188a2f4c32d5b81963e0a33eccba92404e968c665006d");

        if(managerDB.librarianViewPersonDetails()==null)
        {
            System.out.println("Test failed");
        }
        else {
            System.out.println("Test succeded");
        }

    }

}