package View;

import Controller.LoginController;
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
        URL url = new File("src/main/java/FXMLFiles/SignUp.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.setTitle("SignUpPage");
        stage.setScene(new Scene(root, 1920, 1000));
        stage.show();
    }

    public void createUser(String username, String nickname, String password) {
        LoginController loginController = new LoginController();
        System.out.println(loginController.createUser(username, nickname, password));
    }

    public void login(String username, String password, Scanner scanner) throws IOException {
        LoginController loginController = new LoginController();
        String response = loginController.login(username, password);
        System.out.println(response);
        if (response.equals("user logged in successfully!")) {
            MainMenu mainMenu = new MainMenu();
            mainMenu.run(scanner);
        }
    }


}