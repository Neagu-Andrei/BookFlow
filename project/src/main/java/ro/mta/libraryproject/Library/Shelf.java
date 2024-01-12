package ro.mta.libraryproject.Library;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.javatuples.Pair;
import ro.mta.libraryproject.Main.ManagerDB;
import java.lang.*;

/**
 *  Ouatu Laura
 *  Valentin-Ciprian Popescu
 */
public class Shelf {
    /**
     * Members description
     */
    int maxNumberOfBooksPerSection;
    List<Book> books = new ArrayList<Book>();
    int id;

    /**
     *ro.mta.libraryproject.Library.Shelf class constructor
     */
    public Shelf(int _id) {
        this.id = _id;

        ManagerDB managerDB = new ManagerDB();

        Pair<ResultSet, ResultSetMetaData> resultPair = managerDB.fetchBooks();

        try {
            while(resultPair.getValue0().next()) {
                int shelfID = Integer.parseInt(resultPair.getValue0().getString(7));
                if(shelfID == _id) {
                    String isbn = resultPair.getValue0().getString(2);
                    String title = resultPair.getValue0().getString(3);
                    String status = resultPair.getValue0().getString(6);
                    List<String> authors = new ArrayList<String>();
                    authors.add(resultPair.getValue0().getString(4));
                    String genre = resultPair.getValue0().getString(5);
                    int numberOfPages = Integer.parseInt(resultPair.getValue0().getString(9));
                    int numberOfBooks = Integer.parseInt(resultPair.getValue0().getString(8));
                    int id_book = Integer.parseInt(resultPair.getValue0().getString(1));

                    Book book = new Book(isbn, title, status, authors, genre, numberOfBooks, numberOfPages, shelfID, id_book);

                    this.books.add(book);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public int getId() {
        return this.id;
    }

    /**
     *   
     * @return books from a shelf
     */
    public String viewBooks() {
        String buffer = "";
        int id = getId();
        buffer = buffer + "Shelf: " + String.valueOf(id) + "$";
        for (int i = 0; i < books.size(); i++) {
            buffer += books.get(i).viewDetails();
        }
        return buffer;
    }
}
