package Controller;

import View.LoginAndSignUpView;
import View.ShopView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    public Button duelButton;

    @FXML
    public Button profileButton;

    @FXML
    public Button scoreBoardButton;

    @FXML
    public Button shopButton;

    @FXML
    public Button deckButton;

    @FXML
    public Button loginButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();
                LoginAndSignUpView loginAndSignUpView = new LoginAndSignUpView();
                try {
                    loginAndSignUpView.loginShow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        shopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) loginButton.getScene().getWindow();
                ShopView shopView = ShopView.getInstance();
                try {
                    shopView.start(stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
