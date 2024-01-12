package ro.mta.libraryproject.Library;

import org.junit.Test;
import ro.mta.libraryproject.Main.ManagerDB;

import java.sql.ResultSet;


public class DeleteBookTest {
    @Test
    public void testDeleteBook() {
        ManagerDB managerDB = new ManagerDB();
        managerDB.deleteBook("test","test");

        boolean result = managerDB.getBook("test","test");
        if(result == false)
        {
            System.out.println("Test completed!");
        }
        else
        {
            System.out.println("Test did not complet!");
        }
    }
}