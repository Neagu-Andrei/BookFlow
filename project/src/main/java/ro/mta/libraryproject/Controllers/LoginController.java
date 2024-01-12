package ro.mta.libraryproject.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import ro.mta.libraryproject.FinalApp.MainApp;
import ro.mta.libraryproject.Main.Main;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class LoginController {
    public void pressedBackBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Dashboard/MainClientView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    public void pressedEnterKey(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER)  {
            if(usernameField.getText().isEmpty()){
                usernameField.requestFocus();
            }
            else if(passwordField.getText().isEmpty()){
                passwordField.requestFocus();
            }else{
            MainApp.sendMessageToServer("Login\n");
            MainApp.sendMessageToServer(usernameField.getText()+"\n");
            MainApp.sendMessageToServer(passwordField.getText()+"\n");
            String status=MainApp.getMessageFromServer();
            String role=MainApp.getMessageFromServer();
            if(status.equals("Successfully logged in")&&!role.isEmpty()){
                MainApp.setRole(role);
                if(role.equals("registeredUser")) {
                    try {
                        MainApp.setScene("src/main/resources/Dashboard/UserView.fxml");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(role.equals("librarian")) {
                    try {
                        MainApp.setScene("src/main/resources/Dashboard/LibrarianView.fxml");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(role.equals("admin")) {
                    try {
                        MainApp.setScene("src/main/resources/Dashboard/AdministratorView.fxml");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }else{
                errorLabel.setText(status);
                usernameField.setText("");
                passwordField.setText("");
            }
        }}
    }

}
