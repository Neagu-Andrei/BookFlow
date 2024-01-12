package ro.mta.libraryproject.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import ro.mta.libraryproject.FinalApp.MainApp;

import java.io.IOException;
import java.time.LocalDate;
import java.util.function.UnaryOperator;

/**
 *   
 */
public class ProfileViewController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private DatePicker dateField;

    private void refreshInfo() {
        MainApp.sendMessageToServer("Get my information\n");
        try {
            String b = MainApp.getMessageFromServer();
            firstnameField.setText(b.split("\t")[0]);
            lastnameField.setText(b.split("\t")[1]);
            addressField.setText(b.split("\t")[3]);
            phoneField.setText(b.split("\t")[4]);
            emailField.setText(b.split("\t")[5]);
            usernameField.setText(b.split("\t")[6]);
            dateField.setValue(LocalDate.parse(b.split("\t")[2]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        UnaryOperator<TextFormatter.Change> numberValidationFormatter = change -> {
            if (change.getText().matches("\\d+")) {
                return change; //if change is a number
            } else {
                change.setText(""); //else make no change
                return change;
            }

        };
        phoneField.setTextFormatter(new TextFormatter<String>(numberValidationFormatter));
        dateField.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) > 0);
            }
        });
        refreshInfo();
    }

    public void pressedBackBtn(MouseEvent mouseEvent) {
        if (MainApp.getRole().equals("registeredUser")) {
            try {
                MainApp.setScene("src/main/resources/Dashboard/UserView.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
        // TO DO handle event bassed on user type
        // Admin -> return to his dashboard
        // Librarian -> return to his dashboard
        // User -> return to his dashboard
    }

    public void pressedChangeBtn(MouseEvent mouseEvent) {
        errorLabel.setText("");
        if(firstnameField.getText().isEmpty()){
            firstnameField.requestFocus();
        }else if(lastnameField.getText().isEmpty()){
            lastnameField.requestFocus();
        }else if(phoneField.getText().isEmpty()){
            phoneField.requestFocus();
        }else if(emailField.getText().isEmpty()){
            emailField.requestFocus();
        }else if(!emailField.getText().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")){
            errorLabel.setText("Invalid email");
            emailField.requestFocus();
        }else if(addressField.getText().isEmpty()){
            addressField.requestFocus();
        }else if(dateField.getValue()==null) {
            dateField.requestFocus();
        }else if(usernameField.getText().isEmpty()){
            usernameField.requestFocus();
        }else {
            String msg = firstnameField.getText() + "\t";
            msg += lastnameField.getText() + "\t";
            msg += dateField.getValue().toString() + "\t";
            msg += addressField.getText() + "\t";
            msg += phoneField.getText() + "\t";

            msg += emailField.getText() + "\t";
            msg += usernameField.getText() + "\t";
            msg += passwordField.getText() + "\n";
            //System.out.println(msg);
            MainApp.sendMessageToServer("Update personal information\n");
            MainApp.sendMessageToServer(msg);
            try {
                errorLabel.setText(MainApp.getMessageFromServer());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // TO DO handle event for change user information profile
    }

    public void pressedRestoreBtn(MouseEvent mouseEvent) {
        refreshInfo();
    }

    // complete all fields with data
}
