package ro.mta.libraryproject.Library;

import org.junit.Test;
import ro.mta.libraryproject.Main.ManagerDB;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;


public class BorrowBookTest {
    @Test
    public void testBorrowBook() {
        ManagerDB managerDB = new ManagerDB();
     // managerDB.borrowBook(5,4);

        boolean result = managerDB.getBorrowBook(4,5);
        if(result==true)
        {
            System.out.println("Test completed!");
        }
        else
        {
            System.out.println("Test did not complet!");
        }
    }
}