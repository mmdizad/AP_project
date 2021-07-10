package Controller;

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

public class CreateCardController implements Initializable {

    public Button createMonsterBTN;
    public Button createSpellAndTrapBTN;
    public Button back;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) back.getScene().getWindow();
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
                stage.setTitle("MainMenu");
                assert root != null;
                stage.setScene(new Scene(root, 1920, 1150));
                stage.show();
            }
        });

        createMonsterBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) createMonsterBTN.getScene().getWindow();
                URL url = null;
                try {
                    url = new File("src/main/java/FXMLFiles/CreateMonsterFxml.fxml").toURI().toURL();
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
                stage.setTitle("CreateMonster");
                assert root != null;
                stage.setScene(new Scene(root, 1920, 1080));
                stage.show();
            }
        });

        createSpellAndTrapBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) createSpellAndTrapBTN.getScene().getWindow();
                URL url = null;
                try {
                    url = new File("src/main/java/FXMLFiles/CreateSpellAndTrap.fxml").toURI().toURL();
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
                stage.setTitle("CreateSpellAndTrap");
                assert root != null;
                stage.setScene(new Scene(root, 1920, 1080));
                stage.show();
            }
        });
    }
}
