package ro.mta.libraryproject.Library;

import ro.mta.libraryproject.Interfaces.IBook;

import java.util.List;

/**
 *    
 *
 * Implementation of class ro.mta.libraryproject.Library.Book
 *
 */

public class Book implements IBook {

    String ISBN;
    String title;
    String status;
    List<String> authors;
    String genre;
    int numberOfBooks;
    int numberOfPages;
    int shelfId;
    int bookId;

    public Book() {

        System.out.print("Create a new instance of ro.mta.libraryproject.Library.Book class.\n");
    }
    public Book(String ISBN,
         String title,
         String status,
         List<String> authors,
         String genre,
         int numberOfBooks,
         int numberOfPages,
         int shelfId,
         int bookId)
    {
        /*
         *
         * TO DO: add Google ro.mta.libraryproject.Library.Book API integration in order to retrieve official book information
         * and also additional details that could help categorize and also facilitate the search
         *
         */
        this.ISBN = ISBN;
        this.title = title;
        this.status = status;
        this.authors = authors;
        this.genre = genre;
        this.numberOfBooks = numberOfBooks;
        this.numberOfPages = numberOfPages;
        this.shelfId = shelfId;
        this.bookId= bookId;
    }
    public String getISBN() {return ISBN;}
    public String getTitle() {return title;}
    public String getStatus() {return status;}
    public String getAuthors() {
        String buffer = "";
        for (int i = 0; i < authors.size(); i++) {
            buffer += authors.get(i);
        }
        return buffer;
    }
    public String getGenre() { return genre;}
    public int getNumberOfPages() {return numberOfPages;}

    public int getNumberOfBooks() {
        return numberOfBooks;
    }

    public int getShelfId() {return shelfId;}
    public int getBookId() {return bookId;}
    public String viewDetails() {
        return "ISBN: "+ this.getISBN() + " %"  + "Title: "+ this.getTitle() + " %" + "Authors: " + this.getAuthors()
                + " %" + "Status: " + this.getStatus() + " %" + "Genre: " + this.getGenre() +  " %"
        + "NumberOfPages: " + this.getNumberOfPages() + " %"  + "BookID: " + this.getBookId()+
                " %" + "NumberOfBooks: " + this.getNumberOfBooks() + "&";
    }

    public void setISBN(String ISBN) { this.ISBN = ISBN;}
    public void setTitle(String title){this.title = title;}
    public void setStatus(String status){this.status = status;}
    public void setAuthors(List<String>authors)
    {
        this.authors.clear();
        this.authors.addAll(authors);
    }
    public void setGenre(String genre) { this.genre=genre;}
    public void setNumberOfPages(int numberOfPages) {this.numberOfPages=numberOfPages;}
    public void setShelfId(int shelfId) { this.shelfId = shelfId;}
    public void setBookId(int bookId) {this.bookId = bookId;}


}