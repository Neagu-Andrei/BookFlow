package ro.mta.libraryproject.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import ro.mta.libraryproject.FinalApp.MainApp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

  /* @author Andi*
 *  Valentin-Ciprian Popescu
 */
public class BooksViewForGuestController implements Initializable {
    public void pressedBackBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Dashboard/MainClientView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class TmpBook {
        SimpleStringProperty ID;
        SimpleStringProperty ISBN;
        SimpleStringProperty title;
        SimpleStringProperty authors;
        SimpleStringProperty genre;
        SimpleStringProperty status;
        SimpleStringProperty pages;
        SimpleStringProperty books;
        SimpleStringProperty shelf;
        SimpleStringProperty section;


        public TmpBook(String ID, String ISBN, String title, String authors, String genre, String status, String pages, String books, String shelf, String section) {
            this.ID = new SimpleStringProperty(ID);
            this.ISBN = new SimpleStringProperty(ISBN);
            this.title = new SimpleStringProperty(title);
            this.authors = new SimpleStringProperty(authors);
            this.genre = new SimpleStringProperty(genre);
            this.status = new SimpleStringProperty(status);
            this.pages = new SimpleStringProperty(pages);
            this.books = new SimpleStringProperty(books);
            this.shelf = new SimpleStringProperty(shelf);
            this.section = new SimpleStringProperty(section);
        }

        public String getID() {
            return ID.get();
        }

        public SimpleStringProperty IDProperty() {
            return ID;
        }

        public void setID(String ID) {
            this.ID.set(ID);
        }

        public String getISBN() {
            return ISBN.get();
        }

        public SimpleStringProperty ISBNProperty() {
            return ISBN;
        }

        public void setISBN(String ISBN) {
            this.ISBN.set(ISBN);
        }

        public String getTitle() {
            return title.get();
        }

        public SimpleStringProperty titleProperty() {
            return title;
        }

        public void setTitle(String title) {
            this.title.set(title);
        }

        public String getAuthors() {
            return authors.get();
        }

        public SimpleStringProperty authorsProperty() {
            return authors;
        }

        public void setAuthors(String authors) {
            this.authors.set(authors);
        }

        public String getGenre() {
            return genre.get();
        }

        public SimpleStringProperty genreProperty() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre.set(genre);
        }

        public String getStatus() {
            return status.get();
        }

        public SimpleStringProperty statusProperty() {
            return status;
        }

        public void setStatus(String status) {
            this.status.set(status);
        }

        public String getPages() {
            return pages.get();
        }

        public SimpleStringProperty pagesProperty() {
            return pages;
        }

        public void setPages(String pages) {
            this.pages.set(pages);
        }

        public String getBooks() {
            return books.get();
        }

        public SimpleStringProperty booksProperty() {
            return books;
        }

        public void setBooks(String books) {
            this.books.set(books);
        }

        public String getShelf() {
            return shelf.get();
        }

        public SimpleStringProperty shelfProperty() {
            return shelf;
        }

        public void setShelf(String shelf) {
            this.shelf.set(shelf);
        }

        public String getSection() {
            return section.get();
        }

        public SimpleStringProperty sectionProperty() {
            return section;
        }

        public void setSection(String section) {
            this.section.set(section);
        }
    }

    @FXML
    TableView<TmpBook> table;
    @FXML
    TableColumn<TmpBook, String> col_ID, col_ISBN, col_title, col_authors, col_genre, col_status, col_pages, col_books, col_shelf, col_section;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            MainApp.sendMessageToServer("View books\n");
            String buffer = MainApp.getMessageFromServer();

            buffer = buffer.replace("&", "\n").replace("#", "\n").replace("$", "\n").replace("%", "\n");
            String[] dataArray = buffer.split("\n");

            String section = "", shelf = "", isbn = "", title = "", authors = "", status = "", genre = "", pages = "", id = "", books = "";

            final ObservableList<BooksViewForGuestController.TmpBook> obsData = FXCollections.observableArrayList();

            for(String data : dataArray) {
                if(data.contains("Book section:")) {
                    section = data.split(":")[1]; continue;
                }
                if(data.contains("Shelf:")) {
                    shelf = data.split(":")[1];
                    continue;
                }
                if(data.contains("ISBN:")) {
                    isbn = data.split(":")[1];
                    continue;
                }
                if(data.contains("Title:")) {
                    title = data.split(":")[1];
                    continue;
                }
                if(data.contains("Authors:")) {
                    authors = data.split(":")[1];
                    continue;
                }
                if(data.contains("Status:")) {
                    status = data.split(":")[1];
                    continue;
                }
                if(data.contains("Genre:")) {
                    genre = data.split(":")[1];
                    continue;
                }
                if(data.contains("NumberOfPages:")) {
                    pages = data.split(":")[1];
                    continue;
                }
                if(data.contains("BookID:")) {
                    id = data.split(":")[1];
                    continue;
                }
                if(data.contains("NumberOfBooks:")) {
                    books = data.split(":")[1];

                    TmpBook tmpBook = new TmpBook(id, isbn, title, authors, genre, status, pages, books, shelf, section);
                    obsData.add(tmpBook);

                    continue;
                }
            }

            col_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            col_ISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
            col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
            col_authors.setCellValueFactory(new PropertyValueFactory<>("authors"));
            col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
            col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
            col_pages.setCellValueFactory(new PropertyValueFactory<>("pages"));
            col_books.setCellValueFactory(new PropertyValueFactory<>("books"));
            col_shelf.setCellValueFactory(new PropertyValueFactory<>("shelf"));
            col_section.setCellValueFactory(new PropertyValueFactory<>("section"));

            table.setItems(obsData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
