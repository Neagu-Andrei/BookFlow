package ro.mta.libraryproject.Main;

import java.sql.ResultSet;
import java.util.Scanner;

  /* @author Andi*
 *  Ouatu Laura
 */
public class MenuHandler {
      /* @author Andi*
     * Members description
     */
    public boolean is_running;
    public String[] menu_items;

    public MenuHandler() {
        is_running = true;
        menu_items = new String[]{"Login", "Sign up", "Exit"};
    }

      /* @author Andi*
     * run show the menu
     */
    public void run() {
        while (is_running) {
            menu_show();
            menu_user_input();
        }
    }

    public void menu_show() {
        int i = 1;
        for (String item : menu_items) {
            System.out.print(i + ")" + item + "\n");
            i++;
        }
    }


    public void admin_login() {
        System.out.print("Admin logged in successfully!\n");
        is_running = false;
    }

    public void librarian_login() {
        System.out.print("ro.mta.libraryproject.Persons.Librarian logged in successfully!\n");
        is_running = false;
    }

    public void register_user_login() {
        System.out.print("Register ro.mta.libraryproject.AbstractClasses.User logged in successfully!\n");
        is_running = false;
    }

    public void guest_user_login() {
        System.out.print("Guest ro.mta.libraryproject.AbstractClasses.User logged in successfully!\n");
        is_running = false;
    }

    public void menu_login_input(String username, String pass) throws Exception {
        //took creditentials from database and ckecked login

         /* @author Andi* ro.mta.libraryproject.Main.ManagerDB managerDB = new ro.mta.libraryproject.Main.ManagerDB();
        ResultSet resultSet = managerDB.executeSelect(
                "SELECT username, person_password " +
                        "FROM person" +
                        " WHERE username = '" + username + "' AND " +
                        "person_password = '" + pass + "'");

        if (!resultSet.next())
            System.out.print("incorrect credentials!\n");
        is_running = false;
        */
    }

    public void menu_signup_input() {
        //insert creditentials in database
        System.out.print("ro.mta.libraryproject.AbstractClasses.User registration completely successful!\n");
        is_running = false;
    }

    public void login_menu() {
        Scanner user_input = new Scanner(System.in);

        System.out.print("Username:\n");
        String username = user_input.next();
        System.out.print("Password:\n");
        String pass = user_input.next();
       // menu_login_input(username, pass);
    }

    public void menu_user_input() {
        Scanner user_input = new Scanner(System.in);
        String next = user_input.next();
        //if user entered 1
        if (next.equals("1")) {
            login_menu();
        }

        if (next.equals("2")) {
            signup_menu();
        }

        if (next.equals("3")) {
            is_running = false;
        }

    }

    public void signup_menu() {
        Scanner user_input = new Scanner(System.in);

        System.out.print("First name:\n");
        String first = user_input.next();
        System.out.print("Last name:\n");
        String last = user_input.next();
        System.out.print("Birthday:\n");
        String birthday = user_input.next();
        System.out.print("Adress:\n");
        String adress = user_input.next();
        System.out.print("Phone number:\n");
        String phone = user_input.next();
        System.out.print("Email:\n");
        String email = user_input.next();
        System.out.print("Username:\n");
        String username = user_input.next();
        System.out.print("Password:\n");
        String password = user_input.next();
        menu_signup_input();
    }


}
