package ro.mta.libraryproject.Library;

import java.time.LocalDate;

  /* @author Andi*
 *    
 *
 * Implementation of ro.mta.libraryproject.Library.ClassicalBook inheritance relation object
 * Every instance stores
 *
 */
public class ClassicalBook extends Book{

    LocalDate borrowedDate;
    int maxDaysBeforeReturn;

    public ClassicalBook() {
        //TO DO
        System.out.print("Created a new instance of ro.mta.libraryproject.Library.ClassicalBook class.\n");
    }

}
