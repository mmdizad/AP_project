package Controller;

import View.LoginAndSignUpView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    @FXML
    public Rectangle rectangle;

    @FXML
    public Button loginButton;

    @FXML
    public Button signUpButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) signUpButton.getScene().getWindow();
                stage.close();
                try {
                    LoginAndSignUpView loginAndSignUpView = new LoginAndSignUpView();
                    loginAndSignUpView.SignUpShow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();
                try {
                    LoginAndSignUpView loginAndSignUpView = new LoginAndSignUpView();
                    loginAndSignUpView.loginShow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
