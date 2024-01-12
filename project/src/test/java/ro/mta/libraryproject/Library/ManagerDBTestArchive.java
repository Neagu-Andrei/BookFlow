package ro.mta.libraryproject.Library;

import org.junit.Test;
import org.junit.Assert;
import ro.mta.libraryproject.Main.ManagerDB;

import java.time.LocalDate;


public class ManagerDBTestArchive {
    @Test
    public void testGet() {
        ManagerDB managerDB=new ManagerDB();
        LocalDate date = LocalDate.of(2021, 12, 15);
        Archive archive = new Archive("marcel marcel", date, true);

        // Everything should be okay here
        Assert.assertEquals(managerDB.addArchive(archive), true);


        archive = new Archive("not_existing", date, true);
        // Should fail because of non-existent user
        Assert.assertEquals(managerDB.addArchive(archive), false);

        date = LocalDate.of(2021, 12, 10);
        archive = new Archive("marcel marcel", date, true );
        // Should fail because date is in the past
        Assert.assertEquals(managerDB.addArchive(archive), false);

        System.out.println("All tests passed!");
    }
}