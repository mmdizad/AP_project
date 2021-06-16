package View;

import Controller.LoginAndSignUpController;
import Controller.ShopController;
import Model.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopView extends MainMenu implements Initializable {
    private static ShopView shopView = new ShopView();
    public static Stage stage;
    public TableView cardTable;
    public TextField cardNameTXT;
    public Slider moneyToIncrease;
    public Label userMoney;
    public Label result;

    private ObservableList<Card> cardList;

    public ShopView() {

    }

    public static ShopView getInstance() {
        return shopView;
    }

    public void start(Stage stage) throws IOException {
        this.stage = stage;
        URL url = new File("src/main/java/FXMLFiles/ShopMenu.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(Objects.requireNonNull(url));
        stage.setTitle("Shop");

        stage.setScene(new Scene(root, 1920, 1000));
        stage.show();

    }

    public String run(String input) {

        Pattern pattern = Pattern.compile("^increase --money (\\d+)$");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return increaseMoney(matcher);
        }
        //trim matcher later!
        Pattern patternBuy = Pattern.compile("shop buy (.+)");
        Matcher matcherBuy = patternBuy.matcher(input);
        if (matcherBuy.find())
            return buyCard(matcherBuy);
        LoginAndSignUpController.saveChangesToFile();
        return "invalid command";
    }

    public String increaseMoney(Matcher matcher) {
        ShopController shopController = ShopController.getInstance();
        return shopController.increaseMoney(matcher);
    }

    public String buyCard(Matcher matcher) {
        ShopController shopController = ShopController.getInstance();
        return shopController.buyCard(matcher);
    }

    public void showCard() {
        ShopController shopController = ShopController.getInstance();
        ArrayList<String> cards = shopController.getAllCard();
        for (String card : cards) {
            System.out.println(card);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cardList = FXCollections.observableArrayList();
        TableColumn number = new TableColumn("price");
        TableColumn<Card, ImageView> picture = new TableColumn("picture");
        TableColumn name = new TableColumn("name");
        TableColumn description = new TableColumn("description");
        number.setCellValueFactory(new PropertyValueFactory<>("price"));
        picture.setCellValueFactory(new PropertyValueFactory<>("imageView"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        cardList.addAll(Card.getAllCardsCard());
        cardTable.getColumns().addAll(number, picture, name, description);
        cardTable.setItems(cardList);
        userMoney.setText( String.valueOf(LoginAndSignUpController.user.getCoins()));


    }

    public void buyCard(MouseEvent mouseEvent) {

        result.setText(run("shop buy " + cardNameTXT.getText()));
        cardNameTXT.clear();
        System.out.println(result);
        userMoney.setText( String.valueOf(LoginAndSignUpController.user.getCoins()));
    }

    public void increaseMoney(MouseEvent mouseEvent) {

        int money= (int) moneyToIncrease.getValue();
        result.setText(run("increase --money " + money)+money);
        moneyToIncrease.adjustValue(1000);
        userMoney.setText( String.valueOf(LoginAndSignUpController.user.getCoins()));

    }

    public void back(MouseEvent mouseEvent) {
        try {
            URL url = new File("src/main/java/FXMLFiles/MainMenu.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            ShopView.stage.setTitle("LoginPage");
            ShopView.stage.setScene(new Scene(root, 1920, 1000));
            ShopView.stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillText(MouseEvent mouseEvent) {
        TablePosition pos = (TablePosition) cardTable.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        Card card = (Card) cardTable.getItems().get(row);
        cardNameTXT.setText(card.getName());
    }
}
