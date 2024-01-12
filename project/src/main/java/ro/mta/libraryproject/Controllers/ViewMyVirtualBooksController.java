package ro.mta.libraryproject.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Path;
import ro.mta.libraryproject.FinalApp.MainApp;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * @author Alexandru Alexandru
 */
public class ViewMyVirtualBooksController implements Initializable {
    public TextArea text;

    public void pressedBackBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Dashboard/UserView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class TmpVirtualBook {
        SimpleStringProperty ID;
        SimpleStringProperty ISBN;
        SimpleStringProperty title;
        SimpleStringProperty authors;
        SimpleStringProperty genre;
        SimpleStringProperty pages;
        SimpleStringProperty accessKey;


        public TmpVirtualBook(String ID, String ISBN, String title, String authors, String genre, String pages, String accessKey) {
            this.ID = new SimpleStringProperty(ID);
            this.ISBN = new SimpleStringProperty(ISBN);
            this.title = new SimpleStringProperty(title);
            this.authors = new SimpleStringProperty(authors);
            this.genre = new SimpleStringProperty(genre);
            this.pages = new SimpleStringProperty(pages);
            this.accessKey = new SimpleStringProperty(accessKey);
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

        public String getAccessKey() {
            return accessKey.get();
        }

        public SimpleStringProperty accessKeyProperty() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey.set(accessKey);
        }
    }

    @FXML
    TableView<TmpVirtualBook> table;
    @FXML
    TableColumn<TmpVirtualBook, String> col_ID, col_ISBN, col_title, col_authors, col_genre, col_pages, col_accessKey;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            MainApp.sendMessageToServer("View my virtual books\n");
            MainApp.getMessageFromServer();
            String buffer = MainApp.getMessageFromServer();

            final ObservableList<TmpVirtualBook> data = FXCollections.observableArrayList();

            String[] buffer_array = buffer.split("~");

            for(String virtualBook : buffer_array) {
                String[] virtualBookData = virtualBook.split("%");
                TmpVirtualBook tmpBook = new TmpVirtualBook(virtualBookData[0], virtualBookData[1], virtualBookData[2], virtualBookData[3], virtualBookData[4], virtualBookData[5], virtualBookData[6]);
                data.add(tmpBook);
            }

            col_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            col_ISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
            col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
            col_authors.setCellValueFactory(new PropertyValueFactory<>("authors"));
            col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
            col_pages.setCellValueFactory(new PropertyValueFactory<>("pages"));
            col_accessKey.setCellValueFactory(new PropertyValueFactory<>("accessKey"));

            table.setItems(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pressedViewBtn(MouseEvent mouseEvent) {
        // TO DO handle event

        String isbn = table.getSelectionModel().getSelectedItem().getISBN();
        String myKey = table.getSelectionModel().getSelectedItem().getAccessKey();

        if((isbn.isEmpty() == true) || (myKey.isEmpty() == true)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Select virtual book!");
            alert.showAndWait();
        }
        else {
            try {
                MainApp.sendMessageToServer("Select view virtual book\n");
                MainApp.sendMessageToServer(isbn + "\n");
                MainApp.sendMessageToServer(myKey + "\n");
                MainApp.getMessageFromServer();
                String buffer = MainApp.getMessageFromServer();

                if(buffer.equals("yes") == true) {
                    String content = new String(Files.readAllBytes(Paths.get("src/main/java/ro/mta/libraryproject/Files/text")));
                    text.setText(content);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
