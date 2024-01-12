package ro.mta.libraryproject.FinalApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.URL;

public class MainApp extends Application {

    private static Stage stage;
    private static Socket s;
    private static DataOutputStream dos;
    private static BufferedReader br;
    private static String role;

    public static void main(String[] args) {

        try {
            s = new Socket("localhost", 15808);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            dos = new DataOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        launch(args);
    }

    public static void sendMessageToServer(String message){
        try {
            dos.writeBytes(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getMessageFromServer() throws IOException {
        return br.readLine();
    }

    public static String getRole(){
        return role;
    }

    public static void setRole(String role) {
        MainApp.role = role;
    }

    public static void setScene(String fxml) throws Exception {
        URL url = new File(fxml).toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(root);
            stage.setScene(scene);
            stage.sizeToScene();
        } else {
            stage.getScene().setRoot(root);
            stage.sizeToScene();
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        try {
            setScene("src/main/resources/Dashboard/MainClientView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }

        stage.setTitle("Library");
        stage.show();
    }
}
