package View;

import Controller.LoginAndSignUpController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class LoginAndSignUpView extends Menu {

    public void SignUpShow() throws IOException {
        LoginAndSignUpController.createFolders();
        URL url = new File("src/main/java/FXMLFiles/SignUp.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.setTitle("SignUpPage");
        stage.setScene(new Scene(root, 1920, 1000));
        stage.show();
    }

    public void loginShow() throws IOException {
        LoginAndSignUpController.createFolders();
        URL url = new File("src/main/java/FXMLFiles/Login.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.setTitle("LoginPage");
        stage.setScene(new Scene(root, 1920, 1000));
        stage.show();
    }

}