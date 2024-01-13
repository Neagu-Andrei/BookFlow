package ro.mta.libraryproject.Interfaces;

import java.time.LocalDate;

  /* @author Andi*
 *    
 *
 * Interface class for ro.mta.libraryproject.AbstractClasses.Person
 */

public interface IPerson {

    //int getID();
    String getRole();
    String getFirstName();
    String getLastName();
    LocalDate getBirthDate();
    String getAddress();
    String getPhoneNumber();
    String getEmail();
    String getFullDetails();
    void setRole(String role);
    void setFirstName(String firstName);
    void setLastName(String lastName);
    void setBirthDate(LocalDate birthDate);
    void setAddress(String address);
    void setPhoneNumber(String phoneNumber);
    void setEmail(String email);
}