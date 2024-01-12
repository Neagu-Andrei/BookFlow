package ro.mta.libraryproject.Library;

import org.junit.Test;
import ro.mta.libraryproject.Main.ManagerDB;
import ro.mta.libraryproject.Persons.RegisteredUser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditUserTest{
    @Test
    public void testEditUser() {
        ManagerDB managerDB = new ManagerDB();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = "23/05/1999";
        RegisteredUser registeredUser = new RegisteredUser("test11", "test", LocalDate.parse(date, formatter), "test", "test", "test", "test", "test");
        managerDB.editUser(registeredUser, "47");
        RegisteredUser verify = managerDB.selectUserData(47);

        if(registeredUser.getFirstName().equals(verify.getFirstName())&&
                registeredUser.getLastName().equals(verify.getLastName())&&
                registeredUser.getBirthDate().equals(verify.getBirthDate())&&
                registeredUser.getAddress().equals(verify.getAddress())&&
                registeredUser.getPhoneNumber().equals(verify.getPhoneNumber())&&
                registeredUser.getEmail().equals(verify.getEmail())&&
                registeredUser.getUsername().equals(verify.getUsername())&&
                registeredUser.getPassword().equals(verify.getPassword()))
        {
            System.out.println("Test completed!");
        }
        else
        {
            System.out.println("Test did not complet!");
        }
    }
}