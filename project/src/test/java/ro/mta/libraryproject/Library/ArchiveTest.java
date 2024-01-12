package ro.mta.libraryproject.Library;

import org.junit.Test;

import java.time.LocalDate;

/**
 *   
 */

public class ArchiveTest {
    @Test
    public void testGet() {
        LocalDate date = LocalDate.of(2021, 1, 8);
        Archive archive = new Archive(" ",date,true);
        System.out.println(archive.getExpirationDate());
        System.out.println(archive.getReadAccess());
        System.out.println(archive.getUser());
    }
}