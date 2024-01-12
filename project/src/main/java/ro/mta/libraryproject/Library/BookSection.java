package ro.mta.libraryproject.Library;

import org.javatuples.Pair;
import ro.mta.libraryproject.Main.ManagerDB;

import java.lang.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ouatu Laura
 * @author Valentin-Ciprian Popescu
 */
public class BookSection {
    /**
     * Members description
     */
    int maxNumberOfShelfPerSection;
    List<Shelf> shelfs = new ArrayList<Shelf>();
    int id;

    /**
     * ro.mta.libraryproject.Library.BookSection class constructor
     */
    public BookSection(int _id) {
        this.id = _id;

        ManagerDB managerDB = new ManagerDB();

        Pair<ResultSet, ResultSetMetaData> resultPair = managerDB.fetchShelfs();

        try {
            while (resultPair.getValue0().next()) {
                int bookSectionID = Integer.parseInt(resultPair.getValue0().getString(3));
                if(bookSectionID == _id) {
                    int shelfID = Integer.parseInt(resultPair.getValue0().getString(1));
                    Shelf shelf = new Shelf(shelfID);
                    this.shelfs.add(shelf);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *viewBook desplay book details
     */
    void viewBook()
    {
        //TO DO:
        System.out.print("View book in library");
    }

    /**
     *addBookToSection adding a book in library
     */
    void addBookToSection()
    {
        // TO DO: adding books in library
        System.out.print("Add book in library");
    }

    public int getId() {
        return this.id;
    }

    /**
     * @author Alexandru Alexandru
     * @return buffer with books in a book section
     */
    public String viewBookSection() {
        String buffer = "";
        int id = getId();
        buffer = buffer + "Book section: " + String.valueOf(id) + "#";
        for (int i = 0; i < shelfs.size(); i++) {
            buffer += shelfs.get(i).viewBooks();
        }

        return buffer;
    }

}
