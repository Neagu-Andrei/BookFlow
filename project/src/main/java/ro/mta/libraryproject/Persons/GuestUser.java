package ro.mta.libraryproject.Persons;
import org.javatuples.Pair;
import ro.mta.libraryproject.AbstractClasses.User;
import ro.mta.libraryproject.Library.Book;
import ro.mta.libraryproject.Main.ManagerDB;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *   
 */
public class GuestUser extends User {

    /**
     * Member decription
     */
    int timeForUsingLibrary;
    ArrayList<Book> availableBooks;

    /**
     * ro.mta.libraryproject.AbstractClasses.User class constructor
     */
    public GuestUser(String role, String firstname, String lastname, LocalDate birthday,
                     String address, String phone, String email,
                     String username, String password)  {
        //TO DO
        super(role,firstname,lastname,birthday,address,phone,email,username,password);

        //TO DO
        System.out.print("Create new guest user!\n");

        this.availableBooks = new ArrayList<Book>();
    }

    public Boolean viewBooks() {
        ManagerDB managerDB = new ManagerDB();
        Pair<ResultSet, ResultSetMetaData> resultPair = managerDB.fetchBooks();

        try {
            while(resultPair.getValue0().next()) {
                int shelfID = Integer.parseInt(resultPair.getValue0().getString(7));
                String isbn = resultPair.getValue0().getString(2);
                String title = resultPair.getValue0().getString(3);
                String status = resultPair.getValue0().getString(6);
                List<String> authors = new ArrayList<String>();
                authors.add(resultPair.getValue0().getString(4));
                String genre = resultPair.getValue0().getString(5);
                int numberOfPages = Integer.parseInt(resultPair.getValue0().getString(9));
                int numberOfBooks = Integer.parseInt(resultPair.getValue0().getString(8));
                int id_book = Integer.parseInt(resultPair.getValue0().getString(1));

                Book book = new Book(isbn, title, status, authors, genre, numberOfBooks, numberOfPages, shelfID, id_book);

                this.availableBooks.add(book);
            }

            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
