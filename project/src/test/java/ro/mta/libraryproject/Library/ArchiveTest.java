package ro.mta.libraryproject.Library;

import org.junit.Test;

import java.time.LocalDate;

/**
 * @author Alexandru Alexandru
 */

public class ArchiveTest {
    @Test
    public void testGet() {
        LocalDate date = LocalDate.of(2021, 1, 8);
        Archive archive = new Archive("Alexandru Alexandru",date,true);
        System.out.println(archive.getExpirationDate());
        System.out.println(archive.getReadAccess());
        System.out.println(archive.getUser());
    }
}