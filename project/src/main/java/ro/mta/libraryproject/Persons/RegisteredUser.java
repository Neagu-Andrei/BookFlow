package ro.mta.libraryproject.Persons;

import ro.mta.libraryproject.AbstractClasses.User;
import ro.mta.libraryproject.Library.Book;
import ro.mta.libraryproject.Main.ManagerDB;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

  /* @author Andi*
 *   
 */
public class RegisteredUser extends User {

      /* @author Andi*
     * Member decription
     */
    //LocalDate registerDate;
    Map<Book, LocalDate> currentBorrowedBooks;

      /* @author Andi*
     * ro.mta.libraryproject.AbstractClasses.User class constructor
     */
    public RegisteredUser(String firstname, String lastname, LocalDate birthday,
                          String address, String phone, String email,
                          String username, String password) {
        //TO DO
        super("registeredUser", firstname, lastname, birthday, address, phone, email, username, password);
        //System.out.print("Create new registered user!\n");
    }

    public RegisteredUser(String username, String password) {
        //TO DO
        super(username, password);
        System.out.print("Create new registered user!\n");
    }

    public void borrowBook(Book book) {

        this.currentBorrowedBooks.put(book, LocalDate.now());
    }

    public boolean reserveBook(Book book, LocalDate rDate) {

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        Date currentDate = (Date) c.getTime();
        Date reservationDate = Date.valueOf(rDate);

        if (reservationDate.before(currentDate))
            return false;

        else {
            this.currentBorrowedBooks.put(book, rDate);
            return true;
        }
    }

    public void returnBook(String title, String author) {
        for (Book searchBook : currentBorrowedBooks.keySet()) {
            if (searchBook.getTitle().equals(title)) {
                currentBorrowedBooks.remove(searchBook);
            }
        }
    }

    public void viewBorrowedBooks() {
        //TO DO
        System.out.print("View borrowed books!\n");
    }

    //
//    public LocalDate getRegisterDate() {
//
//        return registerDate;
//    }
    public LinkedList<String> getRequests(String option) {
        ManagerDB db = new ManagerDB();
        LinkedList<String> list = new LinkedList<>();
        String s = db.viewUserRequests(this.getUsername(), option);
        if (!s.isEmpty()) {
            for(String row:s.split("~")) list.push(row);
            return list;
        }
        return null;
    }
}
