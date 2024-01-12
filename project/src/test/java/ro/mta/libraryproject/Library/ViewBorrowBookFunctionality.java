package ro.mta.libraryproject.Library;


import org.junit.Assert;
import org.junit.Test;

import ro.mta.libraryproject.Main.ManagerDB;
import ro.mta.libraryproject.Persons.Librarian;

import java.time.LocalDate;

public class ViewBorrowBookFunctionality {

    @Test
    public void testViewBorrowedBooks() {

        ManagerDB managerDB=new ManagerDB();
        LocalDate date = LocalDate.of(2020, 12, 12);
        Archive archive = new Archive("marcel marcel", date, true);
        Librarian librarian=new Librarian("jhgf","df",date,"sa","as","sa","ds"," 8bd574fdb05c2dc5017188a2f4c32d5b81963e0a33eccba92404e968c665006d");

       Assert.assertNotNull(managerDB.viewBorrowedBooks());

       managerDB.viewBorrowedBooks();



    }
}
