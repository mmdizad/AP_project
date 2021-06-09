package View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainMenu {

    public void showMenu(){
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
        Stage mainMenuStage = new Stage();
        mainMenuStage.setTitle("MainMenu");
        assert root != null;
        mainMenuStage.setScene(new Scene(root, 1920, 1000));
        mainMenuStage.show();
    }
}