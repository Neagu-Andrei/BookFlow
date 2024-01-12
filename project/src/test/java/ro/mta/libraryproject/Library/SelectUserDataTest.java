package ro.mta.libraryproject.Library;


import org.junit.Assert;
import org.junit.Test;
import ro.mta.libraryproject.Main.ManagerDB;
import ro.mta.libraryproject.Persons.RegisteredUser;

import java.time.LocalDate;

/**
 *  Merlus Mihai
 */

public class SelectUserDataTest {
    @Test
    public void testSelectUserData() {


        ManagerDB managerDB=new ManagerDB();
        LocalDate date = LocalDate.of(2020, 12, 12);
        RegisteredUser user=new RegisteredUser("jhgf","df",date,"sa","as","sa","ds","8bd574fdb05c2dc5017188a2f4c32d5b81963e0a33eccba92404e968c665006d");
        // Everything should be okay here

        Assert.assertNotNull(managerDB.selectUserData(1));

        date = LocalDate.of(1999, 9, 30);
        user=new RegisteredUser("not_existing","not_existing",date,"not_existing","not_existing","not_existing","not_existing","not_existing");
        // Should fail because of non-existent user
        Assert.assertEquals(managerDB.selectUserData(-1),null);

        System.out.println("All tests passed!");

    }
}