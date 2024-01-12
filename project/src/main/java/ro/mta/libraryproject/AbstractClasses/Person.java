package ro.mta.libraryproject.AbstractClasses;

import ro.mta.libraryproject.Interfaces.IPerson;

import java.time.LocalDate;

/**
 *    
 *
 *
 *
 * Class implementing the person part from witch all the users-roles classes are derived
 */
public class Person implements IPerson {

    //int ID;
    String role;        //admin, librarian, registeredUser, guestUser
    String firstName;
    String lastName;
    LocalDate birthDate;
    String address;
    String phoneNumber;
    String email;
    String username;
    String password;

    public Person() {

        System.out.print("Create a new instance of ro.mta.libraryproject.AbstractClasses.Person class.\n");
    }

    public Person(String role,
           String firstName,
           String lastName,
           LocalDate birthDate,
           String address,
           String phoneNumber,
           String email,
           String username,
           String password
    ){
        //this.ID=id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.birthDate=birthDate;
        this.address=address;
        this.phoneNumber=phoneNumber;
        this.email=email;
        this.username=username;
        this.password=password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

//    public int getID() {
//        return ID;
//    }

    public String getRole() {
        return role;
    }

    public String getRoleAsString(){
        //TO DO ROLE LOGIC
        return "To do role logic\n";
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getFullDetails() {
        /*
        This method should be overridden in every child class if there are additional information to be returned
         */
        return firstName+";"+lastName+";"+birthDate+";"+address+";"+phoneNumber+";"+email+";";
    }

    public void setRole(String role) {
        this.role=role;
    }

    public void setFirstName(String firstName) {
        this.firstName=firstName;
    }

    public void setLastName(String lastName) {
        this.lastName=lastName;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate=birthDate;
    }

    public void setAddress(String address) {
        this.address=address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber=phoneNumber;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public void setUsername(String username) {this.username=username; }

    public void setPassword(String password) {this.password=password; }
}
