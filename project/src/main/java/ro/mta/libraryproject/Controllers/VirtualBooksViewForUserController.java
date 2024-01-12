package ro.mta.libraryproject.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import ro.mta.libraryproject.FinalApp.MainApp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *   
 */
public class VirtualBooksViewForUserController implements Initializable  {

    public class TmpVirtualBook {
        SimpleStringProperty ID;
        SimpleStringProperty ISBN;
        SimpleStringProperty title;
        SimpleStringProperty authors;
        SimpleStringProperty genre;
        SimpleStringProperty pages;


        public TmpVirtualBook(String ID, String ISBN, String title, String authors, String genre, String pages) {
            this.ID = new SimpleStringProperty(ID);
            this.ISBN = new SimpleStringProperty(ISBN);
            this.title = new SimpleStringProperty(title);
            this.authors = new SimpleStringProperty(authors);
            this.genre = new SimpleStringProperty(genre);
            this.pages = new SimpleStringProperty(pages);
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

        public String getPages() {
            return pages.get();
        }

        public SimpleStringProperty pagesProperty() {
            return pages;
        }

        public void setPages(String pages) {
            this.pages.set(pages);
        }
    }

    @FXML
    TableView<VirtualBooksViewForUserController.TmpVirtualBook> table;
    @FXML
    TableColumn<VirtualBooksViewForUserController.TmpVirtualBook, String> col_ID, col_ISBN, col_title, col_authors, col_genre, col_pages;
    @FXML
    private TableView<VirtualBooksViewForUserController.TmpVirtualBook> selectedRow;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            MainApp.sendMessageToServer("View virtual books for user\n");
            MainApp.getMessageFromServer();
            String buffer = MainApp.getMessageFromServer();

            final ObservableList<VirtualBooksViewForUserController.TmpVirtualBook> data = FXCollections.observableArrayList();

            String[] buffer_array = buffer.split("~");

            for(String virtualBook : buffer_array) {
                String[] virtualBookData = virtualBook.split("%");
                VirtualBooksViewForUserController.TmpVirtualBook tmpBook = new VirtualBooksViewForUserController.TmpVirtualBook(virtualBookData[0], virtualBookData[1], virtualBookData[2], virtualBookData[3], virtualBookData[4], virtualBookData[5]);
                data.add(tmpBook);
            }

            col_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            col_ISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
            col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
            col_authors.setCellValueFactory(new PropertyValueFactory<>("authors"));
            col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
            col_pages.setCellValueFactory(new PropertyValueFactory<>("pages"));

            table.setItems(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pressedBackBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Dashboard/UserView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressedBuyBtn(MouseEvent mouseEvent) {
        // TO DO

        try {
            MainApp.sendMessageToServer("Buy virtual book\n");
            MainApp.sendMessageToServer(table.getSelectionModel().getSelectedItem().getID() + "\n");
            MainApp.sendMessageToServer(table.getSelectionModel().getSelectedItem().getISBN() + "\n");
            MainApp.sendMessageToServer(table.getSelectionModel().getSelectedItem().getTitle() + "\n");
            MainApp.sendMessageToServer(table.getSelectionModel().getSelectedItem().getAuthors() + "\n");
            MainApp.sendMessageToServer(table.getSelectionModel().getSelectedItem().getGenre() + "\n");
            MainApp.sendMessageToServer(table.getSelectionModel().getSelectedItem().getPages() + "\n");

            MainApp.getMessageFromServer();
            String buffer = MainApp.getMessageFromServer();

            if(buffer.equals("Added!")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Successfully request!");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Bad request!");
                alert.showAndWait();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
