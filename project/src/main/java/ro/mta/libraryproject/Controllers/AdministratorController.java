package ro.mta.libraryproject.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import ro.mta.libraryproject.FinalApp.MainApp;

/**
 *   
 */
public class AdministratorController {
    public void pressedBackBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Dashboard/AdministratorView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressedViewBooksBtn(MouseEvent actionEvent) {
        try {
            MainApp.setScene("src/main/resources/Functionalities/Librarian/BooksViewForLibrarian.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressedAddLibrarianBtn(MouseEvent actionEvent) {
        try {
            MainApp.setScene("src/main/resources/Functionalities/Admin/AddLibrarian.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressedViewUsersBtn(MouseEvent actionEvent) {
        try {
            MainApp.setScene("src/main/resources/Functionalities/UsersView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressedViewArchivesBtn(MouseEvent actionEvent) {
        try {
            MainApp.setScene("src/main/resources/Functionalities/ArchivesViewPrivate.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressedProfileBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Functionalities/Common/ProfileView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressedLogoutBtn(MouseEvent actionEvent) {
        try {
            MainApp.sendMessageToServer("Logout\n");
            MainApp.setScene("src/main/resources/Dashboard/MainClientView.fxml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
