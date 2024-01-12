package ro.mta.libraryproject.Controllers;

import javafx.scene.input.MouseEvent;
import ro.mta.libraryproject.FinalApp.MainApp;

/**
 *   
 */
public class UserViewController {
    public void pressedLogoutBtn(MouseEvent mouseEvent) {
        try {
            MainApp.sendMessageToServer("Logout\n");
            MainApp.setScene("src/main/resources/Dashboard/MainClientView.fxml");
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

    public void pressedAddArchiveBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Functionalities/User/AddArchive.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressedViewArchivesBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Functionalities/ArchivesView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressedViewBooksBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Functionalities/User/BooksViewForUser.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressedViewBorrowedBooksBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Functionalities/User/ViewMyBooks.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressedViewVirtualBooksBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Functionalities/User/VirtualBooksViewForUser.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressedViewMyVirtualBooksBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Functionalities/User/ViewMyVirtualBooks.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
