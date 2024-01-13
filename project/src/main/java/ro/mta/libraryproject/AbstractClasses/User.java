package ro.mta.libraryproject.AbstractClasses;

import java.time.LocalDate;

  /* @author Andi*
 * Class implementing the user part.
 *   
 */
public class User extends Person {

      /* @author Andi*
     * Member decription
     */

      /* @author Andi*
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
