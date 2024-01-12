package ro.mta.libraryproject.Persons;

import ro.mta.libraryproject.AbstractClasses.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Class implementing the administrator part for library administration.
 *   
 */
public class Administrator extends Person {
    /**
     * Member decription
     */
    List<Librarian> registeredLibrarians;

    /**
     * ro.mta.libraryproject.Persons.Administrator class constructor
     */
    public Administrator() {
        //TO DO
        System.out.print("Create new administrator!\n");
        registeredLibrarians = new ArrayList<Librarian>();
    }

    public void addLibrarian(Librarian newLibrarian) {
        //TO DO
        registeredLibrarians.add(newLibrarian);
        System.out.print("Add librarian!\n");
    }

    public void editLibrarianInfo(Librarian librarian) {
        //TO DO
        System.out.print("Edit librarian info!\n");
    }

    public void addSection() {
        //TO DO
        System.out.print("Add new section!\n");
    }

    public void addShelfToSection() {
        //TO DO
        System.out.print("Add new shelf to section!\n");
    }
}
