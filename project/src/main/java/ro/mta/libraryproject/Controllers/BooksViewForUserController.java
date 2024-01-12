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

/**
 * @author Alexandru Alexandru
 */
public class BooksViewForUserController implements Initializable {
    public void pressedBackBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Dashboard/UserView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class TmpBook {
        SimpleStringProperty isbn;
        SimpleStringProperty title;
        SimpleStringProperty status;
        SimpleStringProperty author;
        SimpleStringProperty genre;
        SimpleStringProperty numberOfBooks;
        SimpleStringProperty numberOfPages;
        SimpleStringProperty shelfID;
        SimpleStringProperty bookID;

        public TmpBook(String isbn,String title, String status, String author, String genre, String numberOfBooks, String numberOfPages, String shelfID, String bookID) {
            this.isbn = new SimpleStringProperty(isbn);
            this.title = new SimpleStringProperty(title);
            this.status = new SimpleStringProperty(status);
            this.author =new SimpleStringProperty(author);
            this.genre =new SimpleStringProperty(genre);
            this.numberOfBooks =new SimpleStringProperty(numberOfBooks);
            this.numberOfPages =new SimpleStringProperty(numberOfPages);
            this.shelfID =new SimpleStringProperty(shelfID);
            this.bookID =new SimpleStringProperty(bookID);
        }

        public String getIsbn() {
            return isbn.get();
        }

        public SimpleStringProperty isbnProperty() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn.set(isbn);
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

        public String getStatus() {
            return status.get();
        }

        public SimpleStringProperty statusProperty() {
            return status;
        }

        public void setStatus(String status) {
            this.status.set(status);
        }

        public String getAuthor() {
            return author.get();
        }

        public SimpleStringProperty authorProperty() {
            return author;
        }

        public void setAuthor(String author) {
            this.author.set(author);
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

        public String getNumberOfBooks() {
            return numberOfBooks.get();
        }

        public SimpleStringProperty numberOfBooksProperty() {
            return numberOfBooks;
        }

        public void setNumberOfBooks(String numberOfBooks) {
            this.numberOfBooks.set(numberOfBooks);
        }

        public String getNumberOfPages() {
            return numberOfPages.get();
        }

        public SimpleStringProperty numberOfPagesProperty() {
            return numberOfPages;
        }

        public void setNumberOfPages(String numberOfPages) {
            this.numberOfPages.set(numberOfPages);
        }

        public String getShelfID() {
            return shelfID.get();
        }

        public SimpleStringProperty shelfIDProperty() {
            return shelfID;
        }

        public void setShelfID(String shelfID) {
            this.shelfID.set(shelfID);
        }

        public String getBookID() {
            return bookID.get();
        }

        public SimpleStringProperty bookIDProperty() {
            return bookID;
        }

        public void setBookID(String bookID) {
            this.bookID.set(bookID);
        }
    }


    @FXML
    TableView<BooksViewForUserController.TmpBook> table;
    @FXML
    TableColumn<BooksViewForUserController.TmpBook, SimpleStringProperty> isbn, title, status, author, genre, numberOfBooks, numberOfPages, shelfID, bookID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            MainApp.sendMessageToServer("View all books\n");
            MainApp.getMessageFromServer();
            String buffer = MainApp.getMessageFromServer();

            final ObservableList<BooksViewForUserController.TmpBook> data = FXCollections.observableArrayList();

            String[] buffer_array = buffer.split("~");

            for(String user : buffer_array) {
                String[] user_data = user.split("%");
                TmpBook tmpBook = new TmpBook(user_data[0], user_data[1], user_data[2], user_data[3], user_data[4], user_data[5], user_data[6], user_data[8], user_data[9]);
                data.add(tmpBook);
            }

            isbn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
            title.setCellValueFactory(new PropertyValueFactory<>("title"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            author.setCellValueFactory(new PropertyValueFactory<>("author"));
            genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
            numberOfBooks.setCellValueFactory(new PropertyValueFactory<>("numberOfBooks"));
            numberOfPages.setCellValueFactory(new PropertyValueFactory<>("numberOfPages"));
            shelfID.setCellValueFactory(new PropertyValueFactory<>("shelfID"));
            bookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));

            table.setItems(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void pressedBorrowBtn(MouseEvent mouseEvent) {
        // TO DO ****************** handle event
    }

    public void pressedReserveBtn(MouseEvent mouseEvent) {
        // TO DO ****************** handle event
    }
    public void setOnMouseClicked(MouseEvent mouseEvent) {
        isbn.setText(table.getSelectionModel().getSelectedItem().getIsbn());
        title.setText(table.getSelectionModel().getSelectedItem().getTitle());
        status.setText(table.getSelectionModel().getSelectedItem().getStatus());
        author.setText(table.getSelectionModel().getSelectedItem().getAuthor());
        genre.setText(table.getSelectionModel().getSelectedItem().getGenre());
        numberOfBooks.setText(table.getSelectionModel().getSelectedItem().getNumberOfBooks());
        numberOfPages.setText(table.getSelectionModel().getSelectedItem().getNumberOfPages());
        shelfID.setText(table.getSelectionModel().getSelectedItem().getShelfID());
        bookID.setText(table.getSelectionModel().getSelectedItem().getBookID());
    }
}
