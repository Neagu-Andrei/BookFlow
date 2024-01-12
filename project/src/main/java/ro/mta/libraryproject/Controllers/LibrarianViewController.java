package ro.mta.libraryproject.Controllers;

import javafx.scene.input.MouseEvent;
import ro.mta.libraryproject.FinalApp.MainApp;

/**
 * @author Alexandru Alexandru
 */
public class LibrarianViewController {
    public void pressedLogoutBtn(MouseEvent mouseEvent) {
        try {
            MainApp.sendMessageToServer("Logout\n");
            MainApp.setScene("src/main/resources/Dashboard/MainClientView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressedViewBooksBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Functionalities/Librarian/BooksViewForLibrarian.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressedViewRequestsBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Functionalities/Librarian/RequestsView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressedViewArchivesBtn(MouseEvent mouseEvent) {
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

    public void pressedViewUsersBtn(MouseEvent mouseEvent) {
        try {

            MainApp.setScene("src/main/resources/Functionalities/UsersView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressedViewVirtualBooksBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Functionalities/Librarian/VirtualBooksViewForLibrarian.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressedViewBorrowedBooksBtn(MouseEvent mouseEvent) {
        try {
            MainApp.setScene("src/main/resources/Functionalities/Librarian/ViewBorrowedBooks.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
