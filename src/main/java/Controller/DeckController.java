package Controller;

import Model.*;
import com.google.gson.Gson;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeckController extends LoginAndSignUpController {

    @FXML
    Button showAllDeckBtn;

    @FXML
    TableView<Deck> showAllDeckTable;

    @FXML
    Text activeDeckText;

    @FXML
    TableView<Deck> deckDeleteTable;

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


    private static DeckController deckController = new DeckController();

    private DeckController() {

    }

    public static DeckController getInstance() {
        return deckController;
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
        for (File file : files){
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
        showAllDeckTable.getColumns().clear();
        TableColumn<Deck,String> nameColumn = new TableColumn<>("DECK NAME");
        TableColumn<Deck,Integer> numberOfCardsColumn = new TableColumn<>("NUMBER OF CARDS");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberOfCardsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfCards"));

        showAllDeckTable.getColumns().addAll(nameColumn,numberOfCardsColumn);

        ArrayList<Deck> decks = getAllDecks();

        for (Deck deck : decks) {
            showAllDeckTable.getItems().add(deck);
        }

        String activeDeck = user.getActiveDeck().getName() + ": " + user.getActiveDeck().getNumberOfCards();

        activeDeckText.setText(activeDeck);

        Parent parent = FXMLLoader.load(getClass().getResource("FXMLFiles/ShowAllDeck.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void deleteDeckBtnEvent(ActionEvent actionEvent) throws IOException {
        deckDeleteTable.getColumns().clear();
        TableColumn<Deck,String> nameColumn = new TableColumn<>("DECK NAME");
        TableColumn<Deck,Integer> numberOfCardsColumn = new TableColumn<>("NUMBER OF CARDS");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberOfCardsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfCards"));

        deckDeleteTable.getColumns().addAll(nameColumn,numberOfCardsColumn);

        ArrayList<Deck> decks = getAllDecks();

        for (Deck deck : decks) {
            deckDeleteTable.getItems().add(deck);
        }

        Parent parent = FXMLLoader.load(getClass().getResource("FXMLFiles/DeleteDeck.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void deckDeleteBtnEvent(ActionEvent actionEvent) {
        if (deckDeleteTable.getSelectionModel().getSelectedItem() != null) {
            Deck deck = deckDeleteTable.getSelectionModel().getSelectedItem();
            if (Deck.getDeckByName(deck.getName()) == null) {
                deckDeleteText.setText("YOU HAVE DELETED THIS DECK BEFORE");
            }else {
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
                deckDeleteText.setText("YOU HAVE DELETED THIS DECK BEFORE");
            }else {
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

    public void deckCreateBtnEvent(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("FXMLFiles/DeckCreate.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void deckCreateSubmitBtnEvent(ActionEvent event) {
        String deckName = deckCreateTextField.getText();
        if (Deck.getDeckByName(deckName) != null) {
            deckCreateText.setText("DECK WITH THIS NAME EXISTS");
        }else {
            Pattern pattern = Pattern.compile("^deck create (.+)$");
            Matcher matcher = pattern.matcher("deck create " + deckName);
            if (matcher.find()) {
                deckCreate(matcher);
            }
            deckCreateText.setFill(Color.GREEN);
            deckCreateText.setText("DECK CREATED SUCCESSFULLY");
        }
    }

    public String deckDelete(Matcher matcher) {
        ArrayList<Deck> decks = user.getDecks();
        if (Deck.getDeckByName(matcher.group(1)) == null) {
            return "deck with name " + matcher.group(1) + " does not exist";
        } else {
            Deck deck = Deck.getDeckByName(matcher.group(1));
            if (!userContainsDeck(deck,decks)) {
                return "deck with name " + matcher.group(1) + " does not exist";
            } else {
                user.deleteDeck(deck);
                Deck.deleteDeck(deck);
                File file = new File(System.getProperty("user.home") + "/Desktop\\AP FILES\\Decks\\" + matcher.group(1) + "deck.txt");
                file.delete();
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
            if (!userContainsDeck(deck,decks)) {
                return "deck with name " + matcher.group(1) + " does not exist";
            } else {
                user.setActiveDeck(deck);
                saveChangesToFile(deck);
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
                if (!userContainsDeck(deck,decks)) {
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
                if (!userContainsDeck(deck,decks)) {
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
        for (Card card1 : sideCards){
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
            if (!userContainsDeck(deck,decks)) {
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

    public String changeMainAndSideCards(String command,User inGameUser){
        ArrayList<Card> mainCards = inGameUser.getActiveDeck().getCardsMain();
        ArrayList<Card> sideCards = inGameUser.getActiveDeck().getCardsSide();
        Pattern pattern = Pattern.compile("^change --(.+) with --(.+)$");
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()){
            boolean cardExistInMain = false;
            boolean cardExistInSide = false;
            for (Card card : mainCards){
                if (card.getName().equals(matcher.group(1))){
                 cardExistInMain = true;
                }
            }
            for (Card card : sideCards){
                if (card.getName().equals(matcher.group(2))){
                    cardExistInSide = true;
                }
            }
            if (!cardExistInMain){
                return "Card with name " + matcher.group(1) + " doesnt exist in main cards";
            }
            if (!cardExistInSide){
                return "Card with name " + matcher.group(2) + " doesnt exist in side cards";
            }
            for (Card card : mainCards){
                if (card.getName().equals(matcher.group(1))){
                    sideCards.add(card);
                    mainCards.remove(card);
                    break;
                }
            }
            for (Card card : sideCards){
                if (card.getName().equals(matcher.group(2))){
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

    public boolean userContainsDeck(Deck deck,ArrayList<Deck> decks) {
        for (Deck deck1 : decks){
            if (deck1.getName().equals(deck.getName())){
                return true;
            }
        }
        return false;
    }
}