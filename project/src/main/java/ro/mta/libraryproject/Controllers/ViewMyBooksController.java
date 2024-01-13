package ro.mta.libraryproject.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import ro.mta.libraryproject.FinalApp.MainApp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

  /* @author Andi*
 *   
 */
public class ViewMyBooksController implements Initializable {
    public void pressedBackBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Dashboard/UserView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class TmpBookUser{
        SimpleStringProperty title;
        SimpleStringProperty author;
        SimpleStringProperty lendingDate;
        SimpleStringProperty returnDate;

        public TmpBookUser(String title,String author, String lending_date, String ret_date) {
            this.title = new SimpleStringProperty(title);
            this.author = new SimpleStringProperty(author);
            this.lendingDate = new SimpleStringProperty(lending_date);
            this.returnDate = new SimpleStringProperty(ret_date);
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

        public String getAuthor() {
            return author.get();
        }

        public SimpleStringProperty authorProperty() {
            return author;
        }

        public void setAuthor(String author) {
            this.author.set(author);
        }

        public String getLending() {
            return lendingDate.get();
        }

        public SimpleStringProperty lendingProperty() {
            return lendingDate;
        }

        public void setLending(String lending) {
            this.lendingDate.set(lending);
        }

        public String getReturn() {
            return returnDate.get();
        }

        public SimpleStringProperty returnProperty() {
            return returnDate;
        }

        public void setReturn(String ret) {
            this.returnDate.set(ret);
        }
    }

    @FXML
    TableView<TmpBookUser> table;
    @FXML
    TableColumn<TmpBookUser, String> col_title, col_author, col_lending, col_return;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            MainApp.sendMessageToServer("View borrowed books\n");
            MainApp.getMessageFromServer();

            String x = MainApp.getMessageFromServer();
            String y = MainApp.getMessageFromServer();
            String buffer = MainApp.getMessageFromServer();

            final ObservableList<TmpBookUser> data = FXCollections.observableArrayList();

            String[] buffer_array = buffer.split("~");

            for(String user : buffer_array) {
                String[] book_data = user.split("%");
                TmpBookUser tmpBook = new TmpBookUser(book_data[1], book_data[2], book_data[4], book_data[5]);

                data.add(tmpBook);
            }

            col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
            col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
            col_lending.setCellValueFactory(new PropertyValueFactory<>("lending"));
            col_return.setCellValueFactory(new PropertyValueFactory<>("return"));

            table.setItems(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        @FXML private TextField titleField;
        @FXML private TextField authorField;

        public void pressedReturnBtn(MouseEvent mouseEvent) {
            // TO DO ****************** handle event
            try {
                if (titleField.getText().isEmpty()) {
                    titleField.requestFocus();
                } else if (authorField.getText().isEmpty()) {
                    authorField.requestFocus();
                } else {
                    MainApp.sendMessageToServer("Return book\n");
                    MainApp.sendMessageToServer(authorField.getText() + "\n");
                    MainApp.sendMessageToServer(titleField.getText() + "\n");
                    String IDBook = MainApp.getMessageFromServer();
                    System.out.println(IDBook);
                }
            } catch (Exception e) {
            e.printStackTrace();
        }
        }
}
