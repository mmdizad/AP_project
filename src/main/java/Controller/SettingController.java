package Controller;

import View.DuelView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
    public Button backToGame;
    public Button exit;
    public Button pauseMusic;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backToGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) backToGame.getScene().getWindow();
                stage.close();
            }
        });

        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) exit.getScene().getWindow();
                DuelView.stage.close();
                stage.close();
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
                Stage stage1 = new Stage();
                stage1.setTitle("MainMenu");
                assert root != null;
                stage1.setScene(new Scene(root, 1920, 1070));
                stage1.show();
            }
        });

        pauseMusic.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
    }
}
