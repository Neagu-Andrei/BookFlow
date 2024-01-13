package ro.mta.libraryproject.Controllers;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import ro.mta.libraryproject.FinalApp.MainApp;
import javafx.fxml.FXML;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

  /* @author Andi*
 *   
 */
public class AddLibrarianController {
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

    public void pressedBackBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Dashboard/AdministratorView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressedEnterKey(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            errorLabel.setText("");
            if (firstnameField.getText().isEmpty()) {
                firstnameField.requestFocus();
            } else if (lastnameField.getText().isEmpty()) {
                lastnameField.requestFocus();
            } else if (phoneField.getText().isEmpty()) {
                phoneField.requestFocus();
            } else if (emailField.getText().isEmpty()) {
                emailField.requestFocus();
            } else if (!emailField.getText().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
                errorLabel.setText("Invalid email");
                emailField.requestFocus();
            } else if (addressField.getText().isEmpty()) {
                addressField.requestFocus();
            } else if (dateField.getValue() == null) {
                dateField.requestFocus();
            } else if (passwordField.getText().isEmpty()) {
                passwordField.requestFocus();
            } else if (usernameField.getText().isEmpty()) {
                usernameField.requestFocus();
            } else {
                MainApp.sendMessageToServer("Sign up\n");
                MainApp.sendMessageToServer(firstnameField.getText() + "\n");
                MainApp.sendMessageToServer(lastnameField.getText() + "\n");
                MainApp.sendMessageToServer(dateField.getValue().format(DateTimeFormatter.ofPattern("d/MM/yyyy")) + "\n");
                MainApp.sendMessageToServer(addressField.getText() + "\n");
                MainApp.sendMessageToServer(phoneField.getText() + "\n");
                MainApp.sendMessageToServer(emailField.getText() + "\n");
                MainApp.sendMessageToServer(usernameField.getText() + "\n");
                MainApp.sendMessageToServer(passwordField.getText() + "\n");
                String status = MainApp.getMessageFromServer();
                if(status.equals("New librarian was added!")){
                    try {
                        MainApp.setScene("src/main/resources/Dashboard/AdministratorView.fxml");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    errorLabel.setText(status);
                    usernameField.setText("");
                    passwordField.setText("");
                }
            }
        }
    }
}
