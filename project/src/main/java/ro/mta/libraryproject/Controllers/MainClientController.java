package ro.mta.libraryproject.Controllers;

import javafx.event.ActionEvent;
import ro.mta.libraryproject.FinalApp.MainApp;

public class MainClientController {
    public void pressedLoginBtn(ActionEvent actionEvent) {
        try {
            MainApp.setScene("src/main/resources/Functionalities/Common/LoginView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressedSignUpBtn(ActionEvent actionEvent) {
        try {
            MainApp.setScene("src/main/resources/Functionalities/Common/SignupView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressedViewBooksBtn(ActionEvent actionEvent) {
        try {
            MainApp.setScene("src/main/resources/Functionalities/Guest/BooksViewForGuest.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressedExitBtn(ActionEvent actionEvent) {
        javafx.application.Platform.exit();
    }
}


