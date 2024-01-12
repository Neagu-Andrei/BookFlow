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
 *   
 */
public  class ArchivesViewController implements Initializable{
    public void pressedBackBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Dashboard/MainClientView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class TmpArchive  {
        SimpleStringProperty id;
        SimpleStringProperty readAccess;
        SimpleStringProperty userId;
        SimpleStringProperty date;


        public TmpArchive(String id, String readAccess, String userId, String date) {

            this.id = new SimpleStringProperty(id);
            this.readAccess = new SimpleStringProperty(readAccess);
            this.userId = new SimpleStringProperty(userId);
            this.date = new SimpleStringProperty(date);
        }



        public String getID() {
            return id.get();
        }

        public SimpleStringProperty idProperty() {
            return id;
        }

        public void setID(String id) {
            this.id.set(id);
        }

        public String getAccess() {
            return readAccess.get();
        }

        public SimpleStringProperty accessProperty() {
            return readAccess;
        }

        public void setAccess(String readAccess) {
            this.readAccess.set(readAccess);
        }


        public String getUserID() {
            return userId.get();
        }

        public SimpleStringProperty userIDProperty() {
            return userId;
        }

        public void setUserID(String userID) {
            this.userId.set(userID);
        }
        public String getDate() {
            return date.get();
        }

        public SimpleStringProperty dateProperty() {
            return date;
        }

        public void setDate(String date) {
            this.date.set(date);
        }


    }


    @FXML
    TableView<ArchivesViewController.TmpArchive> table;
    @FXML
    TableColumn<ArchivesViewController.TmpArchive, String> col_id, col_read,col_user, col_date;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            MainApp.sendMessageToServer("View archives\n");
            String buffer = MainApp.getMessageFromServer();

            final ObservableList<TmpArchive> obsData = FXCollections.observableArrayList();

            String[] book_data = buffer.split("%");
            TmpArchive tmpArchive = new TmpArchive(book_data[0], book_data[1], book_data[2], book_data[3]);
            obsData.add(tmpArchive);


            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_read.setCellValueFactory(new PropertyValueFactory<>("readAccess"));
            col_user.setCellValueFactory(new PropertyValueFactory<>("userId"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            table.setItems(obsData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
