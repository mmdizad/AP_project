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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopView extends MainMenu implements Initializable {
    private static ShopView shopView = new ShopView();

    public TableView cardTable;
    private ObservableList<Card> cardList;
    public ShopView() {

    }

   public void start( Stage stage) throws IOException {
       URL url = new File("src/main/java/FXMLFiles/ShopMenu.fxml").toURI().toURL();
       Parent root = FXMLLoader.load(Objects.requireNonNull(url));
       stage.setTitle("Shop");

       stage.setScene(new Scene(root, 1920, 1000));
       stage.show();

   }
    public static ShopView getInstance() {
        return shopView;
    }

    public void run(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();

            Pattern pattern = Pattern.compile("^increase --money (\\d+)$");
            Matcher matcher = pattern.matcher(input);
            if (matcher.find()){
                increaseMoney(matcher);
            }
            //trim matcher later!
            Pattern patternBuy = Pattern.compile("shop buy (.+)");
            Matcher matcherBuy = patternBuy.matcher(input);
            if (matcherBuy.find())
                buyCard(matcherBuy);
            else if (input.equals("shop show --all"))
                showCard();
            else if (input.equals("menu exit")) break;
            else if (input.equals("menu show-current")) System.out.println("ShopMenu");
            else System.out.println("invalid command!");
            LoginAndSignUpController.saveChangesToFile();
        }
    }

    public void increaseMoney(Matcher matcher) {
        ShopController shopController = ShopController.getInstance();
        System.out.println(shopController.increaseMoney(matcher));
    }

    public void buyCard(Matcher matcher) {
        ShopController shopController = ShopController.getInstance();
        System.out.println(shopController.buyCard(matcher));
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
        cardList= FXCollections.observableArrayList();
        TableColumn number = new TableColumn("num") ;
        TableColumn picture = new TableColumn("picture") ;
        TableColumn name = new TableColumn("name") ;
        TableColumn description = new TableColumn("description") ;
        number.setCellValueFactory(new PropertyValueFactory<>("level"));
        picture.setCellValueFactory(new PropertyValueFactory<>("imageView"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        cardList.addAll(Card.getAllCardsCard());
        cardTable.getColumns().addAll(number,picture,name,description);
        cardTable.setItems(cardList);

    }
}
