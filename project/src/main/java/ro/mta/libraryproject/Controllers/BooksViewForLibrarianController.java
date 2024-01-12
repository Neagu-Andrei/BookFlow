package ro.mta.libraryproject.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import ro.mta.libraryproject.FinalApp.MainApp;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * @author Alexandru Alexandru
 */
public class BooksViewForLibrarianController implements Initializable {

    @FXML
    private TextField ISBN;
    @FXML
    private PasswordField Title;
    @FXML
    private TextField Status;
    @FXML
    private TextField Author;
    @FXML
    private TextField Genre;
    @FXML
    private TextField NumberOfBooks;
    @FXML
    private TextField NumberOfPages;
    @FXML
    private TextField ShelfID;


    public void pressedBackBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Dashboard/LibrarianView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public class TmpBookLib {
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


        public TmpBookLib(String ID, String ISBN, String title, String authors, String genre, String status, String pages, String books, String shelf, String section) {
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
    TextField  newIsbn, newTitle, newAuthor, newGenre, newNumberOfPages, newStatus, newNumberOfBooks, newShelfID, newSection;
    @FXML
    TableView<TmpBookLib> table;
    @FXML
    TableColumn<TmpBookLib, String> col_ID, col_ISBN, col_title, col_authors, col_genre, col_status, col_pages, col_books, col_shelf, col_section;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            MainApp.sendMessageToServer("View books librarian\n");
            String buffer = MainApp.getMessageFromServer();
            //System.out.println(buffer);

            buffer = buffer.replace("&", "\n").replace("#", "\n").replace("$", "\n").replace("%", "\n");
            //System.out.println(buffer);
            String[] dataArray = buffer.split("\n");

            String section = "", shelf = "", isbn = "", title = "", authors = "", status = "", genre = "", pages = "", id = "", books = "";

            final ObservableList<TmpBookLib> obsData = FXCollections.observableArrayList();

            for (String data : dataArray) {
                if (data.contains("Book section:")) {
                    section = data.split(":")[1];
                    continue;
                }
                if (data.contains("Shelf:")) {
                    shelf = data.split(":")[1];
                    continue;
                }
                if (data.contains("ISBN:")) {
                    isbn = data.split(":")[1];
                    continue;
                }
                if (data.contains("Title:")) {
                    title = data.split(":")[1];
                    continue;
                }
                if (data.contains("Authors:")) {
                    authors = data.split(":")[1];
                    continue;
                }
                if (data.contains("Status:")) {
                    status = data.split(":")[1];
                    continue;
                }
                if (data.contains("Genre:")) {
                    genre = data.split(":")[1];
                    continue;
                }
                if (data.contains("NumberOfPages:")) {
                    pages = data.split(":")[1];
                    continue;
                }
                if (data.contains("BookID:")) {
                    id = data.split(":")[1];
                    continue;
                }
                if (data.contains("NumberOfBooks:")) {
                    books = data.split(":")[1];

                    TmpBookLib tmpBook = new TmpBookLib(id, isbn, title, authors, genre, status, pages, books, shelf, section);
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

    public void pressedEditBtn(MouseEvent mouseEvent) {
        if((newIsbn.getText().isEmpty() == true) || (newTitle.getText().isEmpty() == true) || (newAuthor.getText().isEmpty() == true) || (newGenre.getText().isEmpty() == true)
                || (newNumberOfPages.getText().isEmpty() == true) ||(newStatus.getText().isEmpty() == true)|| (newNumberOfPages.getText().isEmpty() == true) || (newNumberOfBooks.getText().isEmpty() == true) || (newShelfID.getText().isEmpty() == true)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Complete all fields!");
            alert.showAndWait();
        }
        else {
            try {
                MainApp.sendMessageToServer("Add\n");

                MainApp.sendMessageToServer(newIsbn.getText() + "\n");
                MainApp.sendMessageToServer(newTitle.getText() + "\n");
                MainApp.sendMessageToServer(newStatus.getText() + "\n");
                MainApp.sendMessageToServer(newAuthor.getText() + "\n");
                MainApp.sendMessageToServer(newGenre.getText() + "\n");
                MainApp.sendMessageToServer(newNumberOfBooks.getText() + "\n");
                MainApp.sendMessageToServer(newNumberOfPages.getText() + "\n");
                MainApp.sendMessageToServer(newShelfID.getText() + "\n");

                MainApp.getMessageFromServer();
                String buffer = MainApp.getMessageFromServer();
                System.out.println(buffer);

                if(buffer.equals("Edited!")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully added!");
                    alert.showAndWait();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Not added!");
                    alert.showAndWait();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void pressedDeleteBtn(MouseEvent mouseEvent) {
        if((newTitle.getText().isEmpty() == true)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Complete title and author fields if you want to delete a book!!");
            alert.showAndWait();
        }
        else if((newAuthor.getText().isEmpty() == true))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Complete title and author fields if you want to delete a book!!");
            alert.showAndWait();
        }
        else {
            try {
                MainApp.sendMessageToServer("Delete\n");

                MainApp.sendMessageToServer(newIsbn.getText() + "\n");

                MainApp.getMessageFromServer();
                String buffer = MainApp.getMessageFromServer();

                System.out.println(buffer);

                System.out.println(buffer);

                if(buffer.equals("Deleted!")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully deleted!");
                    alert.showAndWait();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Not deleted!");
                    alert.showAndWait();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void pressedAddBtn(MouseEvent mouseEvent) {
        if((newIsbn.getText().isEmpty() == true) || (newTitle.getText().isEmpty() == true) || (newAuthor.getText().isEmpty() == true) || (newGenre.getText().isEmpty() == true)
                || (newNumberOfPages.getText().isEmpty() == true) ||(newStatus.getText().isEmpty() == true)|| (newNumberOfPages.getText().isEmpty() == true) || (newNumberOfBooks.getText().isEmpty() == true) || (newShelfID.getText().isEmpty() == true)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Complete all fields!");
            alert.showAndWait();
        }
        else {
            try {
                MainApp.sendMessageToServer("Add\n");

                MainApp.sendMessageToServer(newIsbn.getText() + "\n");
                MainApp.sendMessageToServer(newTitle.getText() + "\n");
                MainApp.sendMessageToServer(newStatus.getText() + "\n");
                MainApp.sendMessageToServer(newAuthor.getText() + "\n");
                MainApp.sendMessageToServer(newGenre.getText() + "\n");
                MainApp.sendMessageToServer(newNumberOfBooks.getText() + "\n");
                MainApp.sendMessageToServer(newNumberOfPages.getText() + "\n");
                MainApp.sendMessageToServer(newShelfID.getText() + "\n");

                MainApp.getMessageFromServer();
                String buffer = MainApp.getMessageFromServer();

                if(buffer.equals("Added!")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully added!");
                    alert.showAndWait();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Not added!");
                    alert.showAndWait();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
