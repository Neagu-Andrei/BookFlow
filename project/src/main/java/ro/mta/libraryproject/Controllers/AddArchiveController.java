package ro.mta.libraryproject.Controllers;

        import javafx.fxml.FXML;
        import javafx.scene.control.DatePicker;
        import javafx.scene.control.Label;
        import javafx.scene.control.TextField;
        import javafx.scene.input.KeyCode;
        import javafx.scene.input.KeyEvent;
        import javafx.scene.input.MouseEvent;
        import ro.mta.libraryproject.FinalApp.MainApp;

        import java.io.IOException;
        import java.time.format.DateTimeFormatter;

  /* @author Andi*
 *   
 */
public class AddArchiveController {
    public void pressedBackBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Dashboard/UserView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML private TextField readAccessField;
    @FXML private DatePicker expirationDataField;
    @FXML private Label errorLabel;

    public void pressedEnterKey(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            // TO DO ************** handle event
            if (readAccessField.getText().isEmpty()) {
                readAccessField.requestFocus();
            } else if (expirationDataField.getValue() == null) {
                expirationDataField.requestFocus();
            } else {
                MainApp.sendMessageToServer("Add archive\n");
                MainApp.sendMessageToServer(readAccessField.getText() + "\n");
                MainApp.sendMessageToServer(expirationDataField.getValue().format(DateTimeFormatter.ofPattern("d/MM/yyyy")) + "\n");
                String status = MainApp.getMessageFromServer();
                String role = MainApp.getMessageFromServer();
                if (status.equals("Successfully add archive") && !role.isEmpty()) {
                    try {
                        MainApp.setScene("src/main/resources/Dashboard/UserView.fxml");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    errorLabel.setText(status);
                    readAccessField.setText("");
                   //expirationDataField.setValue("");
                }
            }
        }
    }
}