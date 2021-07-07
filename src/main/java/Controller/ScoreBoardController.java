package Controller;

import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class ScoreBoardController {

    @FXML
    TableView<User> scoreBoardTable;

    private static ScoreBoardController scoreBoardController = new ScoreBoardController();

    public ScoreBoardController() {

    }

    public static ScoreBoardController getInstance() {
        return scoreBoardController;
    }

    public void scoreBoardBackBtnEvent(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/java/FXMLFiles/MainMenu.fxml").toURI().toURL();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(url));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void showScoreBoard(Stage stage) throws IOException {
        URL url = new File("src/main/java/FXMLFiles/ScoreBoard.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(Objects.requireNonNull(url));
        stage.setTitle("ScoreBoard");
        Scene scene = new Scene(root, 1920, 1000);

        stage.setScene(scene);
        stage.show();

        scoreBoardTable = (TableView<User>) scene.lookup("#scoreBoardTable1");
        scoreBoardTable.getColumns().clear();

        TableColumn<User, String> nameColumn = new TableColumn<>("USERNAME");
        TableColumn<User, Integer> scoreColumn = new TableColumn<>("SCORE");


        nameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        scoreBoardTable.getColumns().addAll(nameColumn, scoreColumn);

        ArrayList<User> users = User.getAllUsers();

        Comparator<User> comparator = Comparator.comparing(User::getScore).reversed();

        users.sort(comparator);

        scoreBoardTable.getItems().addAll(users);
    }

    public ArrayList<String> scoreBoard() {
        ArrayList<User> users = User.getAllUsers();
        Comparator<User> compareScoreboard = Comparator
                .comparing(User::getScore, Comparator.reverseOrder());
        users.sort(compareScoreboard);
        ArrayList<String> output = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            int j = i + 1;
            output.add(j + "-" + users.get(i).getUsername() + ": " + users.get(i).getScore());
        }
        return output;
    }

}