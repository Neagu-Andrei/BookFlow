package ro.mta.libraryproject.AbstractClasses;

import java.time.LocalDate;

/**
 * Class implementing the user part.
 * @author Alexandru Alexandru
 */
public class User extends Person {

    /**
     * Member decription
     */

    /**
     * ro.mta.libraryproject.AbstractClasses.User class constructor
     */
    public User(String role, String firstname, String lastname, LocalDate birthday,
                String address, String phone, String email,
                String username, String password)  {
        //TO DO
        super(role,firstname,lastname,birthday,address,phone,email,username,password);
        //System.out.print("Create new user!\n");
    }

    public User(String username, String password) {
        //TO DO
        //super(-1, -1, "", "", null, "", "", "", username, password);
        System.out.print("Create new user!\n");
    }

    public void addArchive() {

    }
}
