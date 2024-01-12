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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *   
 */
public class VirtualBooksViewForLibrarianController implements Initializable {

    /**
     *   
     */
    public class TmpVirtualBook {
        SimpleStringProperty ID;
        SimpleStringProperty ISBN;
        SimpleStringProperty title;
        SimpleStringProperty authors;
        SimpleStringProperty genre;
        SimpleStringProperty pages;
        SimpleStringProperty key;

        public TmpVirtualBook(String ID, String ISBN, String title, String authors, String genre, String pages, String key) {
            this.ID = new SimpleStringProperty(ID);
            this.ISBN = new SimpleStringProperty(ISBN);
            this.title = new SimpleStringProperty(title);
            this.authors = new SimpleStringProperty(authors);
            this.genre = new SimpleStringProperty(genre);
            this.pages = new SimpleStringProperty(pages);
            this.key = new SimpleStringProperty(key);
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

        public String getKey() {
            return key.get();
        }

        public SimpleStringProperty keyProperty() {
            return key;
        }

        public void setKey(String section) {
            this.key.set(section);
        }
    }

    @FXML
    TextField newId, newIsbn, newTitle, newAuthor, newGenre, newNumberOfPages;
    @FXML
    TableView<VirtualBooksViewForLibrarianController.TmpVirtualBook> table;
    @FXML
    TableColumn<VirtualBooksViewForLibrarianController.TmpVirtualBook, String> col_ID, col_ISBN, col_title, col_authors, col_genre, col_pages, col_key;

    /**
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            MainApp.sendMessageToServer("View virtual books\n");
            MainApp.getMessageFromServer();
            String buffer = MainApp.getMessageFromServer();

            final ObservableList<VirtualBooksViewForLibrarianController.TmpVirtualBook> data = FXCollections.observableArrayList();

            String[] buffer_array = buffer.split("~");

            for(String virtualBook : buffer_array) {
                String[] virtualBookData = virtualBook.split("%");
                VirtualBooksViewForLibrarianController.TmpVirtualBook tmpBook = new VirtualBooksViewForLibrarianController.TmpVirtualBook(virtualBookData[0], virtualBookData[1], virtualBookData[2], virtualBookData[3], virtualBookData[4], virtualBookData[5], virtualBookData[6]);
                data.add(tmpBook);
            }

            col_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            col_ISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
            col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
            col_authors.setCellValueFactory(new PropertyValueFactory<>("authors"));
            col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
            col_pages.setCellValueFactory(new PropertyValueFactory<>("pages"));
            col_key.setCellValueFactory(new PropertyValueFactory<>("key"));

            table.setItems(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param mouseEvent
     */
    public void pressedDeleteBtn(MouseEvent mouseEvent) {
        // TO DO
        if((newIsbn.getText().isEmpty() == true)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Complete isbn field if you want to delete a virtual book!!");
            alert.showAndWait();
        }
        else {
            try {
                MainApp.sendMessageToServer("Delete virtual book\n");

                MainApp.sendMessageToServer(newIsbn.getText() + "\n");

                MainApp.getMessageFromServer();
                String buffer = MainApp.getMessageFromServer();

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

    /**
     * @param mouseEvent
     */
    public void pressedAddBtn(MouseEvent mouseEvent) {
        // TO DO
        if((newIsbn.getText().isEmpty() == true) || (newTitle.getText().isEmpty() == true) || (newAuthor.getText().isEmpty() == true) || (newGenre.getText().isEmpty() == true)
            || (newNumberOfPages.getText().isEmpty() == true)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Complete all fields!");
            alert.showAndWait();
        }
        else {
            try {
                MainApp.sendMessageToServer("Add virtual book\n");

                MainApp.sendMessageToServer(newIsbn.getText() + "\n");
                MainApp.sendMessageToServer(newTitle.getText() + "\n");
                MainApp.sendMessageToServer(newAuthor.getText() + "\n");
                MainApp.sendMessageToServer(newGenre.getText() + "\n");
                MainApp.sendMessageToServer(newNumberOfPages.getText() + "\n");

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

    /**
     * @param mouseEvent
     */
    public void setOnMouseClicked(MouseEvent mouseEvent) {
        newId.setText(table.getSelectionModel().getSelectedItem().getID());
        newIsbn.setText(table.getSelectionModel().getSelectedItem().getISBN());
        newTitle.setText(table.getSelectionModel().getSelectedItem().getTitle());
        newAuthor.setText(table.getSelectionModel().getSelectedItem().getAuthors());
        newGenre.setText(table.getSelectionModel().getSelectedItem().getGenre());
        newNumberOfPages.setText(table.getSelectionModel().getSelectedItem().getPages());
    }

    /**
     * @param mouseEvent
     */
    public void pressedEditBtn(MouseEvent mouseEvent) {
        if((newId.getText().isEmpty() == true) || (newIsbn.getText().isEmpty() == true) || (newTitle.getText().isEmpty() == true) || (newAuthor.getText().isEmpty() == true) || (newGenre.getText().isEmpty() == true)
                || (newNumberOfPages.getText().isEmpty() == true)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Complete all fields!");
            alert.showAndWait();
        }
        else {
            try {
                MainApp.sendMessageToServer("Edit virtual book\n");

                MainApp.sendMessageToServer(newId.getText() + "\n");
                MainApp.sendMessageToServer(newIsbn.getText() + "\n");
                MainApp.sendMessageToServer(newTitle.getText() + "\n");
                MainApp.sendMessageToServer(newAuthor.getText() + "\n");
                MainApp.sendMessageToServer(newGenre.getText() + "\n");
                MainApp.sendMessageToServer(newNumberOfPages.getText() + "\n");

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

    /**
     * @param mouseEvent
     */
    public void pressedBackBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Dashboard/LibrarianView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
