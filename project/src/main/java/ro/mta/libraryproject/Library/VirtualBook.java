package ro.mta.libraryproject.Library;

import java.time.LocalDate;

/**
 *    
 *
 * Implementation of ro.mta.libraryproject.Library.VirtualBook inheritance relation object
 * Every instance stores
 *
 */
public class VirtualBook extends Book{

    LocalDate expirationDate;

    public VirtualBook() {
        //TO DO
        System.out.print("Created a new instance of ro.mta.libraryproject.Library.VirtualBook class.\n");
    }

}
