package ro.mta.libraryproject.Persons;

import ro.mta.libraryproject.AbstractClasses.Person;
import ro.mta.libraryproject.AbstractClasses.User;
import ro.mta.libraryproject.Library.Book;
import ro.mta.libraryproject.Main.ManagerDB;

import java.time.LocalDate;

/**
 * Class implementing the librarian part for books and users administration.
 * @author Alexandru Alexandru
 */
public class Librarian extends Person {
    /**
     * Member decription
     */
    //List<ro.mta.libraryproject.Persons.RegisteredUser> registeredUsers;

    /**
     * ro.mta.libraryproject.Persons.Librarian class constructor
     */
    public Librarian(String firstname, String lastname, LocalDate birthday,
                     String address, String phone, String email,
                     String username, String password) {
        //TO DO
        super("librarian",firstname,lastname,birthday,address,phone,email,username,password);
        //System.out.print("Create new librarian!\n");
        //registeredUsers = new ArrayList<ro.mta.libraryproject.Persons.RegisteredUser>();
    }

    public void addUser(User newUser) {
        //TO DO
        System.out.print("Add user!\n");
    }

    public boolean addBook(Book book) {
        ManagerDB managerDB = new ManagerDB();
        return managerDB.addBook(book);
    }

    public void deleteBook() {
        //TO DO
        System.out.print("Delete book!\n");
    }

    public void editBookInfo() {
        //TO DO
        System.out.print("Edit book info!\n");
    }

    public void addArchive() {
        //TO DO
        System.out.print("Add archive!\n");
    }

    public void editUserInfo(User user) {
        //TO DO
        System.out.print("Edit user info!\n");
    }
}
