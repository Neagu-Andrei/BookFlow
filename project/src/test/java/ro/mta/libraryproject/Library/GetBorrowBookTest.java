package ro.mta.libraryproject.Library;

import org.junit.Test;
import ro.mta.libraryproject.Main.ManagerDB;
import org.junit.Assert;
import java.sql.ResultSet;


public class GetBorrowBookTest {
    @Test
    public void testGetBorrowedBook() {
        
        ManagerDB managerDB=new ManagerDB();

        //Everything should be okay
        Assert.assertEquals(managerDB.getBorrowedBook(3),true);

        Assert.assertEquals(managerDB.getBorrowedBook(4),true);
        
        //Should fail because of non-existing user
        Assert.assertEquals(managerDB.getBorrowedBook(5),false);

        System.out.println("All tests passed!");
    }
}