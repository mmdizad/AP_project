package Controller;

import Model.Card;
import Model.User;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImportAndExportController {

    @FXML
    public TableView<Card> importTable;

    @FXML
    public TableView<Card> exportTable;

    @FXML
    public Text importAndExportTxt;

    public static ImportAndExportController importAndExportController = new ImportAndExportController();

    public ImportAndExportController(){

    }

    public static ImportAndExportController getInstance(){
        return importAndExportController;
    }

    public void showScene(Stage stage) throws IOException {
        URL url = new File("src/main/java/FXMLFiles/ImportAndExport.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(Objects.requireNonNull(url));
        stage.setTitle("import and export");

        Scene scene = new Scene(root,1920,1000);
        stage.setScene(scene);
        stage.show();

        importTable = (TableView<Card>) scene.lookup("#importTable1") ;
        exportTable = (TableView<Card>) scene.lookup("#exportTable1") ;
        ObservableList<Card> cardListExport;
        cardListExport = FXCollections.observableArrayList();

        TableColumn name = new TableColumn("name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        cardListExport.addAll(LoginAndSignUpController.user.getCards());
        exportTable.getColumns().addAll( name);
        exportTable.setItems(cardListExport);

        ObservableList<Card> cardListImport;
        cardListImport = FXCollections.observableArrayList();

        TableColumn name1 = new TableColumn("name");
        name1.setCellValueFactory(new PropertyValueFactory<>("name"));
        //cardListImport.addAll();
        importTable.getColumns().addAll(name1);
        importTable.setItems(cardListImport);
    }

    public void importAndExportBackBtnEvent(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/java/FXMLFiles/MainMenu.fxml").toURI().toURL();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(url));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();

    }

    public void exportBtnEvent(ActionEvent actionEvent) {
        if (exportTable.getSelectionModel().getSelectedItem() == null) {
            importAndExportTxt.setText("SELECT A CARD FIRST");
            importAndExportTxt.setFill(Color.RED);
        }else {
            Card card = exportTable.getSelectionModel().getSelectedItem();
            Pattern pattern = Pattern.compile("^export (.+)$");
            Matcher matcher = pattern.matcher("export " + card.getName());
            if (matcher.find()) {
                importAndExportTxt.setText(exportController(matcher));
                importAndExportTxt.setFill(Color.GREEN);
            }
        }
    }

    public void importBtnEvent(ActionEvent actionEvent) {
        if (importTable.getSelectionModel().getSelectedItem() == null) {
            importAndExportTxt.setText("SELECT A CARD FIRST");
            importAndExportTxt.setFill(Color.RED);
        } else {
            Card card = importTable.getSelectionModel().getSelectedItem();
            Pattern pattern = Pattern.compile("^import (.+)$");
            Matcher matcher = pattern.matcher("import " + card.getName());
            if (matcher.find()) {
                importAndExportTxt.setText(importController(matcher));
                importAndExportTxt.setFill(Color.GREEN);
            }
        }
    }

    public String exportController(Matcher matcher){
        String cardName = matcher.group(1);
        if (Card.getCardByName(cardName) == null){
            return "card with name " + cardName + " doesn't exist";
        }else {
            Gson gson = new Gson();
            Card card = Card.getCardByName(cardName);
            String cardInfo = gson.toJson(card);
            return "card imported";
        }
    }

    public String importController(Matcher matcher){
        String input = "";
        Gson gson = new Gson();
        Card card = gson.fromJson(input, Card.class);
        Card card1 = new Card(card.getName(),card.getDescription(),card.getCardType(),card.getPrice(), card.getCategory());
        return "card exported";
    }

}
