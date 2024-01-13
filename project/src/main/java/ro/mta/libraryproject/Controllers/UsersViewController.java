package ro.mta.libraryproject.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import ro.mta.libraryproject.FinalApp.MainApp;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


  /* @author Andi*
 *  Valentin-Ciprian Popescu
 */

public class UsersViewController implements Initializable {
    public void pressedBackBtn(MouseEvent mouseEvent)  {
        if (MainApp.getRole().equals("librarian")) {
            try {
                MainApp.setScene("src/main/resources/Dashboard/LibrarianView.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (MainApp.getRole().equals("admin")) {
            try {
                MainApp.setScene("src/main/resources/Dashboard/AdministratorView.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class TmpPerson {
        SimpleStringProperty ID;
        SimpleStringProperty firstname;
        SimpleStringProperty lastname;
        SimpleStringProperty birthdate;
        SimpleStringProperty address;
        SimpleStringProperty phone;
        SimpleStringProperty email;
        SimpleStringProperty username;

        public TmpPerson(String ID, String firstname,
                String lastname,
                String birthdate,
                String address,
                String phone,
                String email,
                String username) {
            this.ID = new SimpleStringProperty(ID);
            this.firstname = new SimpleStringProperty(firstname);
            this.lastname = new SimpleStringProperty(lastname);
            this.birthdate = new SimpleStringProperty(birthdate);
            this.address = new SimpleStringProperty(address);
            this.phone = new SimpleStringProperty(phone);
            this.email = new SimpleStringProperty(email);
            this.username = new SimpleStringProperty(username);
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

        public String getFirstname() {
            return firstname.get();
        }

        public SimpleStringProperty firstnameProperty() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname.set(firstname);
        }

        public String getLastname() {
            return lastname.get();
        }

        public SimpleStringProperty lastnameProperty() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname.set(lastname);
        }

        public String getBirthdate() {
            return birthdate.get();
        }

        public SimpleStringProperty birthdateProperty() {
            return birthdate;
        }

        public void setBirthdate(String birthdate) {
            this.birthdate.set(birthdate);
        }

        public String getAddress() {
            return address.get();
        }

        public SimpleStringProperty addressProperty() {
            return address;
        }

        public void setAddress(String address) {
            this.address.set(address);
        }

        public String getPhone() {
            return phone.get();
        }

        public SimpleStringProperty phoneProperty() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone.set(phone);
        }

        public String getEmail() {
            return email.get();
        }

        public SimpleStringProperty emailProperty() {
            return email;
        }

        public void setEmail(String email) {
            this.email.set(email);
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
    }

    @FXML
    TableView<TmpPerson> table;
    @FXML
    TableColumn<TmpPerson, SimpleStringProperty> ID, firstname, lastname, birthdate, address, phone, email, username;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            MainApp.sendMessageToServer("View all users\n");
            MainApp.getMessageFromServer();
            String buffer = MainApp.getMessageFromServer();

            final ObservableList<TmpPerson> data = FXCollections.observableArrayList();

            String[] buffer_array = buffer.split("~");

            for(String user : buffer_array) {
                String[] user_data = user.split("%");
                TmpPerson tmpPerson = new TmpPerson(user_data[0], user_data[1], user_data[2], user_data[3], user_data[4], user_data[5], user_data[6], user_data[8]);
                data.add(tmpPerson);
            }

            ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
            lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
            birthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
            address.setCellValueFactory(new PropertyValueFactory<>("address"));
            phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            email.setCellValueFactory(new PropertyValueFactory<>("email"));
            username.setCellValueFactory(new PropertyValueFactory<>("username"));

            table.setItems(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void seeAllUsers(MouseEvent mouseEvent) throws IOException {
        MainApp.sendMessageToServer("View all users\n");
        MainApp.getMessageFromServer();
        String buffer = MainApp.getMessageFromServer();

        final ObservableList<TmpPerson> data = FXCollections.observableArrayList();

        String[] buffer_array = buffer.split("~");

        for(String user : buffer_array) {
            String[] user_data = user.split("%");
            TmpPerson tmpPerson = new TmpPerson(user_data[0], user_data[1], user_data[2], user_data[3], user_data[4], user_data[5], user_data[6], user_data[8]);
            data.add(tmpPerson);
        }

        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        birthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));

        table.setItems(data);
    }

    @FXML
    TextField pt_firstname, pt_lastname, pt_email, pt_phone, pt_address, pt_username;
    @FXML
    DatePicker pt_birthdate;
    public void search(MouseEvent mouseEvent) throws IOException {
        MainApp.sendMessageToServer("View all users\n");
        MainApp.getMessageFromServer();
        String buffer = MainApp.getMessageFromServer();

        final ObservableList<TmpPerson> data = FXCollections.observableArrayList();

        String[] buffer_array = buffer.split("~");

        for(String user : buffer_array) {
            String[] user_data = user.split("%");

            if(!pt_firstname.getText().isEmpty())
                if(!user_data[1].contains(pt_firstname.getText()))
                    continue;

            if(!pt_lastname.getText().isEmpty())
                if(!user_data[2].contains(pt_lastname.getText()))
                    continue;

            if(!pt_email.getText().isEmpty())
                if(!user_data[6].contains(pt_email.getText()))
                    continue;

            if(!pt_phone.getText().isEmpty())
                if(!user_data[5].contains(pt_phone.getText()))
                    continue;

            if(!pt_address.getText().isEmpty())
                if(!user_data[4].contains(pt_address.getText()))
                    continue;

            if(!pt_username.getText().isEmpty())
                if(!user_data[8].contains(pt_username.getText()))
                    continue;
            if(pt_birthdate.getValue() != null)
                if(!user_data[3].contains(pt_birthdate.getValue().toString()))
                    continue;

            TmpPerson tmpPerson = new TmpPerson(user_data[0], user_data[1], user_data[2], user_data[3], user_data[4], user_data[5], user_data[6], user_data[8]);
            data.add(tmpPerson);
        }

        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        birthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));

        table.setItems(data);
    }
}
