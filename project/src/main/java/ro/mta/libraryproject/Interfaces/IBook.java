package ro.mta.libraryproject.Interfaces;

import java.util.List;

/**
 * @author Dragos
 * @author laura
 *
 * Interface class for ro.mta.libraryproject.Library.Book
 *
 */

public interface IBook {

    String viewDetails();
    String getISBN();
    String getStatus();
    String getAuthors();
    String getGenre();
    int getNumberOfPages();
    int getShelfId();
    int getBookId();

    void setISBN(String ISBN);
    void setTitle(String title);
    void setStatus(String status);
    void setAuthors(List<String>authors);
    void setGenre(String genre);
    void setNumberOfPages(int numberOfPages);
    void setShelfId(int shelfId);
    void setBookId(int bookId);

}
