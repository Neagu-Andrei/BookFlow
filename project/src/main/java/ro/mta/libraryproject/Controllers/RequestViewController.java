package ro.mta.libraryproject.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import ro.mta.libraryproject.FinalApp.MainApp;

import java.io.IOException;

/**
 *   
 */
public class RequestViewController {
    @FXML
    private TableView<TMPReq> reqBookTable;
    @FXML
    private TableColumn<TMPReq, String> usernameCol;
    @FXML
    private TableColumn<TMPReq, String> titleCol;
    @FXML
    private TableColumn<TMPReq, String> authorCol;
    @FXML
    private TableColumn<TMPReq, String> dateCol;
    @FXML
    private TableView<TMPReq> reqArchiveTable;
    @FXML
    private TableColumn<TMPReq, String> usernameCol1;
    @FXML
    private TableColumn<TMPReq, String> titleCol1;
    @FXML
    private TableColumn<TMPReq, String> authorCol1;
    @FXML
    private TableColumn<TMPReq, String> dateCol1;
    @FXML
    private TabPane tabPane;
    @FXML
    private Label errorLabel;

    public void pressedBackBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Dashboard/LibrarianView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void initialize() {
        try {
            String msg;
            ObservableList<TMPReq> bookList = FXCollections.observableArrayList();
            usernameCol.setCellValueFactory((new PropertyValueFactory<>("username")));
            titleCol.setCellValueFactory((new PropertyValueFactory<>("title")));
            authorCol.setCellValueFactory((new PropertyValueFactory<>("author")));
            dateCol.setCellValueFactory((new PropertyValueFactory<>("date")));

            MainApp.sendMessageToServer("View unapproved requests\n");

            System.out.println(MainApp.getMessageFromServer());
            msg = MainApp.getMessageFromServer();
            if (!msg.isEmpty()) {
                for (String row : msg.split("~")) {
                    String[] item = row.split("\t");
                    //System.out.println(row);
                    bookList.add(new TMPReq(item[0], item[1], item[2], item[3], item[4]));
                }
            }
            reqBookTable.setItems(bookList);

            ObservableList<TMPReq> archiveList = FXCollections.observableArrayList();
            usernameCol1.setCellValueFactory((new PropertyValueFactory<>("username")));
            titleCol1.setCellValueFactory((new PropertyValueFactory<>("title")));
            authorCol1.setCellValueFactory((new PropertyValueFactory<>("author")));
            dateCol1.setCellValueFactory((new PropertyValueFactory<>("date")));

            System.out.println(MainApp.getMessageFromServer());
            msg = MainApp.getMessageFromServer();
            if (!msg.isEmpty()) {
                for (String row : msg.split("~")) {
                    String[] item = row.split("\t");
                    //System.out.println(row);
                    archiveList.add(new TMPReq(item[0], item[1], item[2], item[3], item[4]));
                }
            }

            reqArchiveTable.setItems(archiveList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleBtnPressed(String opt){
        if (tabPane.getSelectionModel().getSelectedIndex() == 0) {
            if (reqBookTable.getSelectionModel().getSelectedItem() != null) {
                if(!errorLabel.getText().isEmpty())
                    errorLabel.setText("");
                TMPReq req = reqBookTable.getSelectionModel().getSelectedItem();
                MainApp.sendMessageToServer("Handle requests\n");
                MainApp.sendMessageToServer(tabPane.getSelectionModel().getSelectedItem().getText()+'\n');
                MainApp.sendMessageToServer(req.ID+"\n");
                MainApp.sendMessageToServer(opt +"\n");
                try {
                    if(MainApp.getMessageFromServer().contains("successfully")){
                        reqBookTable.getItems().remove(req);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                errorLabel.setText("Invalid selection");
            }
        }else{
            if (reqArchiveTable.getSelectionModel().getSelectedItem() != null) {
                if(!errorLabel.getText().isEmpty())
                    errorLabel.setText("");
                TMPReq req = reqArchiveTable.getSelectionModel().getSelectedItem();
                MainApp.sendMessageToServer("Handle requests\n");
                MainApp.sendMessageToServer(tabPane.getSelectionModel().getSelectedItem().getText()+'\n');
                MainApp.sendMessageToServer(req.ID+"\n");
                try {
                    if(MainApp.getMessageFromServer().contains("successfully")){
                        reqBookTable.getItems().remove(req);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                errorLabel.setText("Invalid selection");
            }
        }
    }

        // TO DO ************ handle event

    public void pressedAcceptBtn(MouseEvent mouseEvent) {
        handleBtnPressed("A");
    }

    public void pressedDeclineBtn(MouseEvent mouseEvent) {
        handleBtnPressed("D");
        // TO DO ************ handle event
    }

    public class TMPReq {
        String ID;
        String username;
        String title;
        String author;
        String date;

        public TMPReq(String id, String username, String title, String author, String date) {
            this.ID = id;
            this.author = author;
            this.date = date;
            this.title = title;
            this.username = username;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
