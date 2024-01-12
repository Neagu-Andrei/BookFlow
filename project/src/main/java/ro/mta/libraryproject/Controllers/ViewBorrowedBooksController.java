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
import java.util.ResourceBundle;

/**
 * @author Alexandru Alexandru
 */
public class ViewBorrowedBooksController implements Initializable{
    public void pressedBackBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Dashboard/LibrarianView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class TmpBookLibrarian{
        SimpleStringProperty ID;
        SimpleStringProperty username;
        SimpleStringProperty ISBN;
        SimpleStringProperty lending_date;
        SimpleStringProperty return_date;

        public TmpBookLibrarian(String ID, String username, String ISBN, String lending_date, String return_date) {
            this.ID = new SimpleStringProperty(ID);
            this.username = new SimpleStringProperty(username);
            this.ISBN = new SimpleStringProperty(ISBN);
            this.lending_date = new SimpleStringProperty(lending_date);
            this.return_date = new SimpleStringProperty(return_date);
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

        public String getUsername() {
            return username.get();
        }

        public SimpleStringProperty usernameProperty() {
            return username;
        }

        public void setUsername(String username) {
            this.username.set(username);
        }

        public String getLending() {
            return lending_date.get();
        }

        public SimpleStringProperty lendingProperty() {
            return lending_date;
        }

        public void setLending(String lending) {
            this.lending_date.set(lending);
        }

        public String getReturn() {
            return return_date.get();
        }

        public SimpleStringProperty returnProperty() {
            return return_date;
        }

        public void setReturn(String ret) {
            this.return_date.set(ret);
        }
    }

    @FXML
    TableView<TmpBookLibrarian> table;
    @FXML
    TableColumn<TmpBookLibrarian, String> col_ID, col_username, col_ISBN, col_lending, col_return;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            MainApp.sendMessageToServer("View borrowed books\n");
            MainApp.getMessageFromServer();
            String buffer = MainApp.getMessageFromServer();

            final ObservableList<TmpBookLibrarian> data = FXCollections.observableArrayList();

            String[] buffer_array = buffer.split("~");

            for(String user : buffer_array) {
                String[] book_data = user.split("%");
                TmpBookLibrarian tmpBook = new TmpBookLibrarian(book_data[0], book_data[1], book_data[2], book_data[3], book_data[4]);
                data.add(tmpBook);
            }

            col_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            col_ISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
            col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
            col_lending.setCellValueFactory(new PropertyValueFactory<>("lending"));
            col_return.setCellValueFactory(new PropertyValueFactory<>("return"));

            table.setItems(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private TextField isbnField;
    @FXML
    private TextField searchField;

    public void pressedSearchBtn(MouseEvent mouseEvent) {
        try {

        if(isbnField.getText().isEmpty()){
            isbnField.requestFocus();
        }else{
            MainApp.sendMessageToServer("Search borrow book by ISBN\n");
            MainApp.sendMessageToServer(isbnField.getText()+"\n");

            String buffer = MainApp.getMessageFromServer();

            while(buffer.contains(" ")) {
                buffer = MainApp.getMessageFromServer();
            }
            if(buffer.contains("%")) {
                System.out.println(buffer);

                final ObservableList<TmpBookLibrarian> data = FXCollections.observableArrayList();

                String[] buffer_array = buffer.split("~");

                for (String user : buffer_array) {
                    String book_data = user.replace("%","           ");
                    searchField.setText(book_data);
                }
            }
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
