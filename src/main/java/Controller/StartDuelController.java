package Controller;

import View.DuelView;
import View.StartDuelView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartDuelController extends LoginAndSignUpController implements Initializable {
    @FXML
    public Button singleDuelBTN;

    @FXML
    public Button matchDuelBTN;

    @FXML
    public Button aiDuelBTN;

    @FXML
    public Button backButton;

    @FXML
    public TextField opponentUsername;

    @FXML
    public Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        singleDuelBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                StartDuelView startDuelView = new StartDuelView();
                String response = startDuelView.startTheGame(opponentUsername.getText(), 1);
                if (opponentUsername.getText().equals("")) {
                    label.setText("you must fill opponentUsername TextField");
                } else if (!response.equals("")){
                    label.setText(response);
                } else {
                    Stage stage =  (Stage) singleDuelBTN.getScene().getWindow();
                    DuelView.getInstance().start(stage);
                }
            }
        });

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage chooseDuelStage = (Stage) backButton.getScene().getWindow();
                chooseDuelStage.close();

                URL url = null;
                try {
                    url = new File("src/main/java/FXMLFiles/MainMenu.fxml").toURI().toURL();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Parent root = null;
                try {
                    assert url != null;
                    root = FXMLLoader.load(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                chooseDuelStage.setTitle("MainMenuPage");
                assert root != null;
                chooseDuelStage.setScene(new Scene(root, 1920, 1080));
                chooseDuelStage.show();
            }
        });

        matchDuelBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                StartDuelView startDuelView = new StartDuelView();
                String response = startDuelView.startTheGame(opponentUsername.getText(), 3);
                if (opponentUsername.getText().equals("")) {
                    label.setText("you must fill opponentUsername TextField");
                } else if (!response.equals("")){
                    label.setText(response);
                } else {
                   Stage stage =  (Stage) matchDuelBTN.getScene().getWindow();
                   stage.close();
                }
            }
        });

    }
}