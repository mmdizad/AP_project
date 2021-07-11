package Controller;

import Model.*;
import View.DuelView;
import View.MainMenu;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sun.tools.jar.Main;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeckController {

    private User user = LoginAndSignUpController.user;

    public static Deck deck;

    @FXML
    public Button deleteDeckBtn;

    @FXML
    public Button showAllDeckBtn;

    @FXML
    TableView<Deck> showAllDeckTable;

    @FXML
    Text activeDeckText;

    @FXML
    public TableView<Deck> deckDeleteTable;

    @FXML
    Button deckDeleteBtn;

    @FXML
    Button deckSetActiveBtn;

    @FXML
    Text deckDeleteText;

    @FXML
    Button deckCreateBtn;

    @FXML
    Button deckCreateSubmitBtn;

    @FXML
    Text deckCreateText;

    @FXML
    TextField deckCreateTextField;

    @FXML
    Button showDeckBtn;

    @FXML
    TableView<Card> showDeckTableMain;

    @FXML
    TableView<Card> showDeckTableSide;

    @FXML
    Button showDeckInMenuBtn;

    @FXML
    TextField showDeckTextField;

    @FXML
    Text showDeckText;

    @FXML
    Button addCardBtn;

    @FXML
    Button removeCardBtn;

    @FXML
    TableView<Card> addCardTable;

    @FXML
    Button addCardToMainBtn;

    @FXML
    Button addCardToSideBtn;

    @FXML
    Text addCardText;

    @FXML
    TableView<Card> removeCardFromMainTable;

    @FXML
    TableView<Card> removeCardFromSideTable;

    @FXML
    Button removeFromMainBtn;

    @FXML
    Button removeFromSideBtn;

    @FXML
    Text removeCardText;

    @FXML
    Button deckMenuBackBtn;

    @FXML
    public Text endGameTxt;


    private static DeckController deckController = new DeckController();

    public DeckController() {

    }

    public static DeckController getInstance() {
        return deckController;
    }

    public void showScene(Stage stage) throws IOException {
        URL url = new File("src/main/java/FXMLFiles/DeckMenu.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(Objects.requireNonNull(url));
        stage.setTitle("Deck");

        stage.setScene(new Scene(root, 1920, 1000));
        stage.show();

    }

    public void showSceneEndGame(MouseEvent event, String winner) throws IOException {

        URL url = new File("src/main/java/FXMLFiles/EndGame.fxml").toURI().toURL();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(url));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        endGameTxt = (Text) scene.lookup("#endGameTxt1");
        endGameTxt.setText(winner);
    }

    public void endGameBtnEvent(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/java/FXMLFiles/MainMenu.fxml").toURI().toURL();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(url));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public String deckCreate(Matcher matcher) {
        if (Deck.getDeckByName(matcher.group(1)) != null) {
            return "deck with name " + matcher.group(1) + " already exists";
        } else {
            Deck deck = new Deck(matcher.group(1), user.getUsername());
            user.addDeck(deck);
            File file = new File(System.getProperty("user.home") + "/Desktop\\AP FILES\\Decks\\" + matcher.group(1) + "deck.txt");
            try {
                if (file.createNewFile()) {
                    Gson gson = new Gson();
                    String deckInfo = gson.toJson(deck);
                    FileWriter fileWriter = new FileWriter(System.getProperty("user.home") + "/Desktop\\AP FILES\\Decks\\" + matcher.group(1) + "deck.txt");
                    fileWriter.write(deckInfo);
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "deck created successfully!";
        }
    }

    public ArrayList<Deck> getAllDecks() {
        ArrayList<Deck> decks = new ArrayList<>();
        File folder = new File(System.getProperty("user.home") + "/Desktop\\AP FILES\\Decks\\");
        File[] files = folder.listFiles();
        for (File file : files) {
            Gson gson = new Gson();
            StringBuilder getDetail = new StringBuilder();
            Scanner myReader = null;
            try {
                myReader = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (myReader.hasNextLine()) {
                getDetail.append(myReader.nextLine());
            }
            String userInfo = getDetail.toString();
            Deck deck = gson.fromJson(userInfo, Deck.class);
            myReader.close();
            if (deck.getCreatorName().equals(user.getUsername())) {
                decks.add(deck);
            }
        }
        return decks;
    }

    public void showAllDeckBtnEvent(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/FXMLFiles/ShowAllDeck.fxml").toURI().toURL();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(url));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        showAllDeckTable = (TableView<Deck>) scene.lookup("#showAllDeckTable1");
        showAllDeckTable.getColumns().clear();
        TableColumn<Deck, String> nameColumn = new TableColumn<>("DECK NAME");
        TableColumn<Deck, Integer> numberOfCardsColumn = new TableColumn<>("NUMBER OF CARDS");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberOfCardsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfCards"));

        showAllDeckTable.getColumns().addAll(nameColumn, numberOfCardsColumn);

        ArrayList<Deck> decks = getAllDecks();

        for (Deck deck : decks) {
            showAllDeckTable.getItems().add(deck);
        }

        activeDeckText = (Text) scene.lookup("#activeDeckText1") ;

        if (user.getActiveDeck() != null) {
            String activeDeck = user.getActiveDeck().getName() + ": " + Deck.getDeckByName(user.getActiveDeck().getName()).getNumberOfCards();

            activeDeckText.setText(activeDeck);
        }


    }

    public void deleteDeckBtnEvent(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/java/FXMLFiles/DeleteDeck.fxml").toURI().toURL();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(url));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        deckDeleteTable = (TableView<Deck>) scene.lookup("#deckDeleteTable1");
        deckDeleteTable.getColumns().clear();
        TableColumn<Deck, String> nameColumn = new TableColumn<>("DECK NAME");
        TableColumn<Deck, Integer> numberOfCardsColumn = new TableColumn<>("NUMBER OF CARDS");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberOfCardsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfCards"));

        deckDeleteTable.getColumns().addAll(nameColumn, numberOfCardsColumn);

        ArrayList<Deck> decks = getAllDecks();

        for (Deck deck : decks) {
            deckDeleteTable.getItems().add(deck);
        }


    }

    public void deckDeleteBtnEvent(ActionEvent actionEvent) {
        if (deckDeleteTable.getSelectionModel().getSelectedItem() != null) {
            Deck deck = deckDeleteTable.getSelectionModel().getSelectedItem();
            if (Deck.getDeckByName(deck.getName()) == null) {
                deckCreateText.setFill(Color.RED);
                deckDeleteText.setText("YOU HAVE DELETED THIS DECK BEFORE");
            } else {
                Pattern pattern = Pattern.compile("^deck delete (.+)$");
                Matcher matcher = pattern.matcher("deck delete " + deck.getName());
                if (matcher.find()) {
                    deckDelete(matcher);
                }
                deckDeleteText.setText("DECK DELETED SUCCESSFULLY");
                deckDeleteText.setFill(Color.GREEN);
            }
        }
    }

    public void deckSetActiveBtnEvent(ActionEvent event) {
        if (deckDeleteTable.getSelectionModel().getSelectedItem() != null) {
            Deck deck = deckDeleteTable.getSelectionModel().getSelectedItem();
            if (Deck.getDeckByName(deck.getName()) == null) {
                deckCreateText.setFill(Color.RED);
                deckDeleteText.setText("YOU HAVE DELETED THIS DECK BEFORE");
            } else {
                Pattern pattern = Pattern.compile("^deck set active (.+)$");
                Matcher matcher = pattern.matcher("deck set active " + deck.getName());
                if (matcher.find()) {
                    deckSetActivate(matcher);
                }
                deckDeleteText.setText("DECK ACTIVATED SUCCESSFULLY");
                deckDeleteText.setFill(Color.GREEN);
            }
        }
    }

    public void deckCreateBtnEvent(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/java/FXMLFiles/DeckCreate.fxml").toURI().toURL();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(url));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void deckCreateSubmitBtnEvent(ActionEvent event) {
        String deckName = deckCreateTextField.getText();
        if (Deck.getDeckByName(deckName) != null) {
            deckCreateText.setFill(Color.RED);
            deckCreateText.setText("DECK WITH THIS NAME EXISTS");
        } else {
            Pattern pattern = Pattern.compile("^deck create (.+)$");
            Matcher matcher = pattern.matcher("deck create " + deckName);
            if (matcher.find()) {
                deckCreate(matcher);
            }
            deckCreateText.setFill(Color.GREEN);
            deckCreateText.setText("DECK CREATED SUCCESSFULLY");
        }
    }

    public void showDeckBtnEvent(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/java/FXMLFiles/ShowDeck.fxml").toURI().toURL();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(url));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void showDeckInMenuBtnEvent(ActionEvent actionEvent) throws IOException {
        String deckName = showDeckTextField.getText();
        if (Deck.getDeckByName(deckName) == null || !userContainsDeck(Deck.getDeckByName(deckName), user.getDecks())) {
            deckCreateText.setFill(Color.RED);
            showDeckText.setText("YOU DONT HAVE DECK WITH THIS NAME");
        } else {
            URL url = new File("src/main/java/FXMLFiles/ShowDeck.fxml").toURI().toURL();
            Parent parent = FXMLLoader.load(Objects.requireNonNull(url));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
            showDeckTableSide = (TableView<Card>) scene.lookup("#showDeckTableSide1");
            showDeckTableMain = (TableView<Card>) scene.lookup("#showDeckTableMain1");
            showDeckTableSide.getColumns().clear();
            showDeckTableMain.getColumns().clear();
            Deck deck = Deck.getDeckByName(deckName);
            ArrayList<Card> mainCards = deck.getCardsMain();
            ArrayList<Card> sideCards = deck.getCardsSide();

            TableColumn<Card, ImageView> main = new TableColumn<>("MAIN CARDS");
            TableColumn<Card, ImageView> side = new TableColumn<>("SIDE CARDS");

            main.setCellValueFactory(new PropertyValueFactory<>("imageView"));
            side.setCellValueFactory(new PropertyValueFactory<>("imageView"));

            showDeckTableMain.getColumns().add(main);
            showDeckTableSide.getColumns().add(side);

            showDeckTableMain.getItems().addAll(mainCards);
            showDeckTableSide.getItems().addAll(sideCards);
        }
    }

    public void addCardBtnEvent(ActionEvent actionEvent) throws IOException {
        if (deckDeleteTable.getSelectionModel().getSelectedItem() != null) {
            URL url = new File("src/main/java/FXMLFiles/AddCardToDeck.fxml").toURI().toURL();
            Parent parent = FXMLLoader.load(Objects.requireNonNull(url));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
            addCardTable = (TableView<Card>) scene.lookup("#addCardTable1") ;
            ObservableList<Card> cardList;
            cardList = FXCollections.observableArrayList();

            TableColumn<Card, ImageView> picture = new TableColumn("picture");
            TableColumn name = new TableColumn("name");
            TableColumn description = new TableColumn("description");
            picture.setCellValueFactory(new PropertyValueFactory<>("imageView"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            description.setCellValueFactory(new PropertyValueFactory<>("description"));
            cardList.addAll(user.getCards());
            addCardTable.getColumns().addAll(picture, name, description);
            addCardTable.setItems(cardList);
            deck = deckDeleteTable.getSelectionModel().getSelectedItem();

        }
    }

    public void addCardToMainBtnEvent(ActionEvent actionEvent) {
        Card card = addCardTable.getSelectionModel().getSelectedItem();
        if (card == null) {
            addCardText.setText("SELECT A CARD FIRST");
        } else {
            Pattern pattern = Pattern.compile("^add card (.+) to deck (.+)$");
            Matcher matcher = pattern.matcher("add card " + card.getName() + " to deck " + deck.getName());
            if (matcher.find()) {
                addCardText.setText(addCard(matcher));
            }
        }
    }

    public void addCardToSideBtnEvent(ActionEvent actionEvent) {
        Card card = addCardTable.getSelectionModel().getSelectedItem();
        if (card == null) {
            addCardText.setText("SELECT A CARD FIRST");
        } else {
            Pattern pattern = Pattern.compile("^add card (.+) to deck (.+) (side)$");
            Matcher matcher = pattern.matcher("add card " + card.getName() + " to deck " + deck.getName() + " side");
            if (matcher.find()) {
                addCardText.setText(addCard(matcher));
            }
        }
    }

    public void removeCardBtnEvent(ActionEvent actionEvent) throws IOException {
        if (deckDeleteTable.getSelectionModel().getSelectedItem() != null) {
            URL url = new File("src/main/java/FXMLFiles/RemoveCardFromDeck.fxml").toURI().toURL();
            Parent parent = FXMLLoader.load(Objects.requireNonNull(url));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
            deck = deckDeleteTable.getSelectionModel().getSelectedItem();
            removeCardFromMainTable = (TableView<Card>) scene.lookup("#removeCardFromMainTable1") ;
            removeCardFromSideTable = (TableView<Card>) scene.lookup("#removeCardFromSideTable1") ;
            ObservableList<Card> cardListMain;
            cardListMain = FXCollections.observableArrayList();

            TableColumn name = new TableColumn("name");
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            cardListMain.addAll(deck.getCardsMain());
            removeCardFromMainTable.getColumns().addAll( name);
            removeCardFromMainTable.setItems(cardListMain);

            ObservableList<Card> cardListSide;
            cardListSide = FXCollections.observableArrayList();

            TableColumn name1 = new TableColumn("name");
            name1.setCellValueFactory(new PropertyValueFactory<>("name"));
            cardListSide.addAll(deck.getCardsSide());
            removeCardFromSideTable.getColumns().addAll(name1);
            removeCardFromSideTable.setItems(cardListSide);

        }
    }

    public void removeFromMainBtnEvent(ActionEvent event) {
        if (removeCardFromMainTable.getSelectionModel().getSelectedItem() == null) {
            removeCardText.setText("SELECT A CARD FIRST");
        } else {
            Card card = removeCardFromMainTable.getSelectionModel().getSelectedItem();
            Pattern pattern = Pattern.compile("^remove card (.+) from deck (.+)$");
            Matcher matcher = pattern.matcher("remove card " + card.getName() + " from deck " + deck.getName());
            if (matcher.find()) {
                String response = deleteCard(matcher);
                removeCardText.setText(response);
                if (response.equals("card removed form deck successfully")) {
                    removeCardText.setFill(Color.GREEN);
                }else {
                    removeCardText.setFill(Color.RED);
                }
            }
        }
    }

    public void removeFromSideBtnEvent(ActionEvent actionEvent) {
        if (removeCardFromSideTable.getSelectionModel().getSelectedItem() == null) {
            removeCardText.setText("SELECT A CARD FIRST");
        } else {
            Card card = removeCardFromSideTable.getSelectionModel().getSelectedItem();
            Pattern pattern = Pattern.compile("^remove card (.+) from deck (.+) (side)$");
            Matcher matcher = pattern.matcher("remove card " + card.getName() + " from deck " + deck.getName() + " side");
            if (matcher.find()) {
                String response = deleteCard(matcher);
                removeCardText.setText(response);
                if (response.equals("card removed form deck successfully")) {
                    removeCardText.setFill(Color.GREEN);
                }else {
                    removeCardText.setFill(Color.RED);
                }
            }
        }
    }

    public void deckMenuBackBtnEvent(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/java/FXMLFiles/MainMenu.fxml").toURI().toURL();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(url));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void deckCreateBackBtnEvent(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/java/FXMLFiles/DeckMenu.fxml").toURI().toURL();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(url));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void deleteDeckBackBtnEvent(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/java/FXMLFiles/DeckMenu.fxml").toURI().toURL();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(url));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void addCardToDeckBackBtnEvent(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/java/FXMLFiles/DeleteDeck.fxml").toURI().toURL();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(url));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public String deckDelete(Matcher matcher) {
        ArrayList<Deck> decks = user.getDecks();
        if (Deck.getDeckByName(matcher.group(1)) == null) {
            return "deck with name " + matcher.group(1) + " does not exist";
        } else {
            Deck deck = Deck.getDeckByName(matcher.group(1));
            if (!userContainsDeck(deck, decks)) {
                return "deck with name " + matcher.group(1) + " does not exist";
            } else {
                user.deleteDeck(deck);
                Deck.deleteDeck(deck);
                File file = new File(System.getProperty("user.home") + "/Desktop\\AP FILES\\Decks\\" + matcher.group(1) + "deck.txt");
                file.delete();
                File file1 = new File(System.getProperty("user.home") + "/Desktop\\AP FILES\\Users\\" + user.getUsername() + "user.txt");
                file1.delete();
                try {
                    if (file1.createNewFile()) {
                        Gson gson = new Gson();
                        String deckInfo = gson.toJson(user);
                        FileWriter fileWriter = new FileWriter(System.getProperty("user.home") + "/Desktop\\AP FILES\\Users\\" + user.getUsername() + "user.txt");
                        fileWriter.write(deckInfo);
                        fileWriter.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "deck deleted successfully";
            }
        }
    }

    public String deckSetActivate(Matcher matcher) {
        ArrayList<Deck> decks = user.getDecks();
        if (Deck.getDeckByName(matcher.group(1)) == null) {
            return "deck with name " + matcher.group(1) + " does not exist";
        } else {
            Deck deck = Deck.getDeckByName(matcher.group(1));
            if (!userContainsDeck(deck, decks)) {
                return "deck with name " + matcher.group(1) + " does not exist";
            } else {
                user.setActiveDeck(deck);
                saveChangesToFile(deck);
                File file = new File(System.getProperty("user.home") + "/Desktop\\AP FILES\\Users\\" + user.getUsername() + "user.txt");
                file.delete();
                try {
                    if (file.createNewFile()) {
                        Gson gson = new Gson();
                        String deckInfo = gson.toJson(user);
                        FileWriter fileWriter = new FileWriter(System.getProperty("user.home") + "/Desktop\\AP FILES\\Users\\" + user.getUsername() + "user.txt");
                        fileWriter.write(deckInfo);
                        fileWriter.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "deck activated successfully";
            }
        }
    }

    public String addCard(Matcher matcher) {
        if (Card.getCardByName(matcher.group(1)) == null || user.getCardByName(matcher.group(1)) == null) {
            return "card with name " + matcher.group(1) + " does not exist";
        } else {
            ArrayList<Deck> decks = user.getDecks();
            if (Deck.getDeckByName(matcher.group(2)) == null) {
                return "deck with name " + matcher.group(2) + " does not exist";
            } else {
                Deck deck = Deck.getDeckByName(matcher.group(2));
                if (!userContainsDeck(deck, decks)) {
                    return "deck with name " + matcher.group(2) + " does not exist";
                } else {
                    ArrayList<Card> mainCards = deck.getCardsMain();
                    ArrayList<Card> sideCards = deck.getCardsSide();
                    ArrayList<Card> userCards = user.getCards();
                    int numberOfCardsInDeck = 0;
                    int numberOfCardsInPlayerCards = 0;
                    for (Card card : sideCards) {
                        if (card.getName().equals(matcher.group(1))) numberOfCardsInDeck++;
                    }
                    for (Card card : mainCards) {
                        if (card.getName().equals(matcher.group(1))) numberOfCardsInDeck++;
                    }
                    for (Card card : userCards) {
                        if (card.getName().equals(matcher.group(1))) numberOfCardsInPlayerCards++;
                    }
                    if (numberOfCardsInDeck >= numberOfCardsInPlayerCards) {
                        return "card with name " + matcher.group(1) + " does not exist";
                    } else {
                        if (matcher.groupCount() == 2) {
                            return addCardToMain(matcher);
                        } else {
                            return addCardToSide(matcher);
                        }
                    }
                }
            }
        }
    }

    public String addCardToMain(Matcher matcher) {
        Deck deck = Deck.getDeckByName(matcher.group(2));
        if (deck.getCardsMain().size() >= 60) {
            return "main deck is full";
        } else {
            ArrayList<Card> mainCards = deck.getCardsMain();
            ArrayList<Card> sideCards = deck.getCardsSide();
            int numberOfCardsInDeck = 0;
            for (int i = 0; i < mainCards.size(); i++) {
                if (mainCards.get(i).getName().equals(matcher.group(1))) {
                    numberOfCardsInDeck++;
                }
            }
            for (int i = 0; i < sideCards.size(); i++) {
                if (sideCards.get(i).getName().equals(matcher.group(1))) {
                    numberOfCardsInDeck++;
                }
            }
            if (numberOfCardsInDeck >= 3) {
                return "there are already three cards with name " + matcher.group(1) + " in deck " + matcher.group(2);
            } else {
                Card card = Card.getCardByName(matcher.group(1));
                deck.addCardToMain(card);
                saveChangesToFile(deck);
                return "card added to deck successfully";
            }
        }
    }

    public String addCardToSide(Matcher matcher) {
        Deck deck = Deck.getDeckByName(matcher.group(2));
        if (deck.getCardsSide().size() >= 15) {
            return "main deck is full";
        } else {
            ArrayList<Card> mainCards = deck.getCardsMain();
            ArrayList<Card> sideCards = deck.getCardsSide();
            int numberOfCardsInDeck = 0;
            for (int i = 0; i < mainCards.size(); i++) {
                if (mainCards.get(i).getName().equals(matcher.group(1))) {
                    numberOfCardsInDeck++;
                }
            }
            for (int i = 0; i < sideCards.size(); i++) {
                if (sideCards.get(i).getName().equals(matcher.group(1))) {
                    numberOfCardsInDeck++;
                }
            }
            if (numberOfCardsInDeck >= 3) {
                return "there are already three cards with name " + matcher.group(1) + " in deck " + matcher.group(2);
            } else {
                Card card = Card.getCardByName(matcher.group(1));
                deck.addCardToSide(card);
                saveChangesToFile(deck);
                return "card added to deck successfully";
            }
        }
    }

    public String deleteCard(Matcher matcher) {
        if (Card.getCardByName(matcher.group(1)) == null) {
            return "card with name " + matcher.group(1) + " does not exist";
        } else {
            ArrayList<Deck> decks = user.getDecks();
            if (Deck.getDeckByName(matcher.group(2)) == null) {
                return "deck with name " + matcher.group(2) + " does not exist";
            } else {
                Deck deck = Deck.getDeckByName(matcher.group(2));
                if (!userContainsDeck(deck, decks)) {
                    return "deck with name " + matcher.group(2) + " does not exist";
                } else {
                    if (matcher.groupCount() == 2) {
                        return deleteCardFromMain(matcher);
                    } else {
                        return deleteCardFromSide(matcher);
                    }
                }
            }
        }
    }

    public String deleteCardFromMain(Matcher matcher) {
        Deck deck = Deck.getDeckByName(matcher.group(2));
        ArrayList<Card> mainCards = deck.getCardsMain();
        Card card = Card.getCardByName(matcher.group(1));
        for (Card card1 : mainCards) {
            if (card1.getName().equals(card.getName())) {
                deck.deleteCardFromMain(card);
                saveChangesToFile(deck);
                return "card removed form deck successfully";
            }
        }
        return "card with name " + matcher.group(1) + " does not exist in main deck";
    }

    public String deleteCardFromSide(Matcher matcher) {
        Deck deck = Deck.getDeckByName(matcher.group(2));
        ArrayList<Card> sideCards = deck.getCardsSide();
        Card card = Card.getCardByName(matcher.group(1));
        for (Card card1 : sideCards) {
            if (card1.getName().equals(card.getName())) {
                deck.deleteCardFromSide(card);
                saveChangesToFile(deck);
                return "card removed form deck successfully";
            }
        }
        return "card with name " + matcher.group(1) + " does not exist in side deck";
    }

    public ArrayList<String> deckShow(Matcher matcher) {
        ArrayList<String> output = new ArrayList<>();
        ArrayList<Deck> decks = user.getDecks();
        if (Deck.getDeckByName(matcher.group(1)) == null) {
            output.add("deck with name " + matcher.group(1) + " does not exist");
            return output;
        } else {
            Deck deck = Deck.getDeckByName(matcher.group(1));
            if (!userContainsDeck(deck, decks)) {
                output.add("deck with name " + matcher.group(1) + " does not exist");
                return output;
            } else {
                output.add("Deck: " + matcher.group(1));
                if (matcher.groupCount() == 1) {
                    return showDeckMain(output, deck);
                } else {
                    return showDeckSide(output, deck);
                }
            }
        }
    }

    private ArrayList<String> showDeckMain(ArrayList<String> output, Deck deck) {
        output.add("Main deck:");
        ArrayList<Card> monsters = new ArrayList<>();
        ArrayList<Card> spellsAndTraps = new ArrayList<>();
        ArrayList<Card> mainCards = deck.getCardsMain();
        for (int i = 0; i < mainCards.size(); i++) {
            if (mainCards.get(i).getCategory().equals("Monster")) {
                monsters.add(mainCards.get(i));
            } else {
                spellsAndTraps.add(mainCards.get(i));
            }
        }
        Comparator<Card> compareForShow = Comparator
                .comparing(Card::getName);
        monsters.sort(compareForShow);
        spellsAndTraps.sort(compareForShow);
        output.add("Monsters:");
        for (int i = 0; i < monsters.size(); i++) {
            output.add(monsters.get(i).getName() + ": " + monsters.get(i).getDescription());
        }
        output.add("Spell and Traps:");
        for (int i = 0; i < spellsAndTraps.size(); i++) {
            output.add(spellsAndTraps.get(i).getName() + ": " + spellsAndTraps.get(i).getDescription());
        }
        return output;
    }

    private ArrayList<String> showDeckSide(ArrayList<String> output, Deck deck) {
        output.add("Side deck:");
        ArrayList<Card> monsters = new ArrayList<>();
        ArrayList<Card> spellsAndTraps = new ArrayList<>();
        ArrayList<Card> sideCards = deck.getCardsSide();
        for (int i = 0; i < sideCards.size(); i++) {
            if (sideCards.get(i).getCategory().equals("Monster")) {
                monsters.add(sideCards.get(i));
            } else {
                spellsAndTraps.add(sideCards.get(i));
            }
        }
        Comparator<Card> compareForShow = Comparator
                .comparing(Card::getName);
        monsters.sort(compareForShow);
        spellsAndTraps.sort(compareForShow);
        output.add("Monsters:");
        for (int i = 0; i < monsters.size(); i++) {
            output.add(monsters.get(i).getName() + ": " + monsters.get(i).getDescription());
        }
        output.add("Spell and Traps:");
        for (int i = 0; i < spellsAndTraps.size(); i++) {
            output.add(spellsAndTraps.get(i).getName() + ": " + spellsAndTraps.get(i).getDescription());
        }
        return output;
    }


    public ArrayList<String> checkCard(Matcher matcher) {
        ArrayList<String> output = new ArrayList<>();
        if (Card.getCardByName(matcher.group(1)) == null) {
            output.add("card with name " + matcher.group(1) + " does not exist");
        } else {
            Card card = Card.getCardByName(matcher.group(1));
            if (card.getCategory().equals("Monster")) {
                output = showMonster(matcher.group(1));
            } else if (card.getCategory().equals("Spell")) {
                output = showSpell(matcher.group(1));
            } else {
                output = showTrap(matcher.group(1));
            }
        }
        return output;
    }

    private ArrayList<String> showMonster(String cardName) {
        Monster monster = Monster.getMonsterByName(cardName);
        ArrayList<String> output = new ArrayList<>();
        output.add("Name: " + monster.getName());
        output.add("Level: " + monster.getLevel());
        output.add("Type: " + monster.getMonsterType());
        output.add("ATK: " + monster.getAttackPower());
        output.add("DEF: " + monster.getDefensePower());
        output.add("Description: " + monster.getDescription());
        return output;
    }

    private ArrayList<String> showSpell(String cardName) {
        Spell spell = Spell.getSpellByName(cardName);
        ArrayList<String> output = new ArrayList<>();
        output.add("Name: " + spell.getName());
        output.add("Spell");
        output.add("Type: " + spell.getCardType());
        output.add("Description: " + spell.getDescription());
        return output;
    }

    private ArrayList<String> showTrap(String cardName) {
        Trap trap = Trap.getTrapByName(cardName);
        ArrayList<String> output = new ArrayList<>();
        output.add("Name: " + trap.getName());
        output.add("Trap");
        output.add("Type: " + trap.getCardType());
        output.add("Description: " + trap.getDescription());
        return output;
    }

    public ArrayList<String> showAllDeck() {
        ArrayList<Deck> decks = user.getDecks();
        ArrayList<String> output = new ArrayList<>();
        output.add("Decks:");
        output.add("Active deck:");
        if (user.getActiveDeck() != null) {
            Deck activeDeck = user.getActiveDeck();
            String validation = "";
            if (activeDeck.getCardsMain().size() < 40) {
                validation = "invalid";
            } else {
                validation = "valid";
            }
            output.add(activeDeck.getName() + ":  main deck " + activeDeck.getCardsMain().size()
                    + ", side deck" + activeDeck.getCardsSide().size() + validation);
        }
        Comparator<Deck> compareForShow = Comparator
                .comparing(Deck::getName);
        decks.sort(compareForShow);
        output.add("Other decks:");
        for (int i = 0; i < decks.size(); i++) {
            String validation = "";
            if (decks.get(i).getCardsMain().size() < 40) {
                validation = "invalid";
            } else {
                validation = "valid";
            }
            output.add(decks.get(i).getName() + ":  main deck " + decks.get(i).getCardsMain().size()
                    + ", side deck" + decks.get(i).getCardsSide().size() + ", " + validation);
        }
        return output;
    }

    public ArrayList<String> showAllOwnedCards() {
        ArrayList<String> output = new ArrayList<>();
        ArrayList<Card> cards = user.getCards();
        Comparator<Card> compareForShow = Comparator
                .comparing(Card::getName);
        cards.sort(compareForShow);
        for (int i = 0; i < cards.size(); i++) {
            output.add(cards.get(i).getName() + ": " + cards.get(i).getDescription());
        }
        return output;
    }

    public String changeMainAndSideCards(String command, User inGameUser) {
        ArrayList<Card> mainCards = inGameUser.getActiveDeck().getCardsMain();
        ArrayList<Card> sideCards = inGameUser.getActiveDeck().getCardsSide();
        Pattern pattern = Pattern.compile("^change --(.+) with --(.+)$");
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            boolean cardExistInMain = false;
            boolean cardExistInSide = false;
            for (Card card : mainCards) {
                if (card.getName().equals(matcher.group(1))) {
                    cardExistInMain = true;
                }
            }
            for (Card card : sideCards) {
                if (card.getName().equals(matcher.group(2))) {
                    cardExistInSide = true;
                }
            }
            if (!cardExistInMain) {
                return "Card with name " + matcher.group(1) + " doesnt exist in main cards";
            }
            if (!cardExistInSide) {
                return "Card with name " + matcher.group(2) + " doesnt exist in side cards";
            }
            for (Card card : mainCards) {
                if (card.getName().equals(matcher.group(1))) {
                    sideCards.add(card);
                    mainCards.remove(card);
                    break;
                }
            }
            for (Card card : sideCards) {
                if (card.getName().equals(matcher.group(2))) {
                    mainCards.add(card);
                    sideCards.remove(card);
                    break;
                }
            }
            return "cards changed successfully";
        }
        return "invalid command";
    }

    public void saveChangesToFile(Deck deck) {
        File file = new File(System.getProperty("user.home") + "/Desktop\\AP FILES\\Decks\\" + deck.getName() + "deck.txt");
        file.delete();
        try {
            if (file.createNewFile()) {
                Gson gson = new Gson();
                String deckInfo = gson.toJson(deck);
                FileWriter fileWriter = new FileWriter(System.getProperty("user.home") + "/Desktop\\AP FILES\\Decks\\" + deck.getName() + "deck.txt");
                fileWriter.write(deckInfo);
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean userContainsDeck(Deck deck, ArrayList<Deck> decks) {
        Deck deck1 = Deck.getDeckByName(deck.getName());
        if (deck1 == null) {
            return false;
        }else if (deck1.getCreatorName().equals(user.getUsername())) {
            return true;
        }else {
            return false;
        }
    }
}