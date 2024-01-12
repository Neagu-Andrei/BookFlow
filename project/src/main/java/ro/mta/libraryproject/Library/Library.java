package ro.mta.libraryproject.Library;

import org.javatuples.Pair;
import ro.mta.libraryproject.Interfaces.IPerson;
import ro.mta.libraryproject.Main.ManagerDB;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rodica
 * @author Valentin-Ciprian Popescu
 */
public class Library {
    private String name;
    private String address;

    List<BookSection> bookSections;
    List<IPerson> persons;
    List<Archive> archivedDocuments;

    public Library() {
        //TO DO
        bookSections = new ArrayList<BookSection>();
        persons = new ArrayList<IPerson>();
        archivedDocuments = new ArrayList<Archive>();

        ManagerDB managerDB = new ManagerDB();

        Pair<ResultSet, ResultSetMetaData> resultPair = managerDB.fetchBookSections();
        try {
            while (resultPair.getValue0().next()) {
                int bookSectionID = Integer.parseInt(resultPair.getValue0().getString(1));
                BookSection bookSection = new BookSection(bookSectionID);
                this.bookSections.add(bookSection);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addSection(BookSection newSection){
        //TO DO
        System.out.print("Add section!\n");
    }

    public void addPerson(IPerson newPerson){
        //TO DO
        System.out.print("Add person!\n");
    }

    public void addArchive(Archive newArchive){
        //TO DO
        System.out.print("Add archive!\n");
    }

    /**
     * @author Alexandru Alexandru
     * @return buffer with what library contains
     */
    public String viewLibraryBooks() {
        String buffer = "";

        for (int i = 0; i < bookSections.size(); i++) {
            buffer += bookSections.get(i).viewBookSection();
        }
        return buffer;
    }
}
