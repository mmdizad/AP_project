package Controller;

import Model.*;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeckController {

    private static DeckController deckController;

    public static DeckController getInstance() {
        if (deckController == null) {
            deckController = new DeckController();
        }
        return deckController;
    }

    private DeckController() {

    }

    public String deckCreate(String command) {
        if (!LoginAndSignUpController.loggedInUsers.containsKey(command.split("/")[2])) {
            return "wrong token!";
        }
        String deckName = command.split("/")[1];
        User user = LoginAndSignUpController.loggedInUsers.get(command.split("/")[2]);
        if (Deck.getDeckByName(deckName) != null) {
            return "deck with name " + deckName + " already exists";
        } else {
            Deck deck = new Deck(deckName, user.getUsername());
            user.addDeck(deck);
            File file = new File(System.getProperty("user.home") + "/Desktop\\AP FILES\\Decks\\" + deckName + "deck.txt");
            try {
                if (file.createNewFile()) {
                    Gson gson = new Gson();
                    String deckInfo = gson.toJson(deck);
                    FileWriter fileWriter = new FileWriter(System.getProperty("user.home") + "/Desktop\\AP FILES\\Decks\\" + deckName + "deck.txt");
                    fileWriter.write(deckInfo);
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            saveChangesToFile(user);
            return "deck created successfully!";
        }
    }

    public String deckDelete(String command) {
        if (!LoginAndSignUpController.loggedInUsers.containsKey(command.split("/")[2])) {
            return "wrong token!";
        }
        String deckName = command.split("/")[1];
        User user = LoginAndSignUpController.loggedInUsers.get(command.split("/")[2]);
        ArrayList<Deck> decks = user.getDecks();
        if (Deck.getDeckByName(deckName) == null) {
            return "deck with name " + deckName + " does not exist";
        } else {
            Deck deck = Deck.getDeckByName(deckName);
            if (!userContainsDeck(deck,decks)) {
                return "deck with name " + deckName + " does not exist";
            } else {
                user.deleteDeck(deck);
                Deck.deleteDeck(deck);
                File file = new File(System.getProperty("user.home") + "/Desktop\\AP FILES\\Decks\\" + deckName + "deck.txt");
                file.delete();
                saveChangesToFile(user);
                return "deck deleted successfully";
            }
        }
    }

    public String deckSetActive(String command) {
        if (!LoginAndSignUpController.loggedInUsers.containsKey(command.split("/")[2])) {
            return "wrong token!";
        }
        String deckName = command.split("/")[1];
        User user = LoginAndSignUpController.loggedInUsers.get(command.split("/")[2]);
        ArrayList<Deck> decks = user.getDecks();
        if (Deck.getDeckByName(deckName) == null) {
            return "deck with name " + deckName + " does not exist";
        } else {
            Deck deck = Deck.getDeckByName(deckName);
            if (!userContainsDeck(deck,decks)) {
                return "deck with name " + deckName + " does not exist";
            } else {
                user.setActiveDeck(deck);
                saveChangesToFile(deck);
                saveChangesToFile(user);
                return "deck activated successfully";
            }
        }
    }

    public String addCard(String command) {
        if (!LoginAndSignUpController.loggedInUsers.containsKey(command.split("/")[3])) {
            return "wrong token!";
        }
        String cardName = command.split("/")[1];
        String deckName = command.split("/")[2];
        User user = LoginAndSignUpController.loggedInUsers.get(command.split("/")[3]);
        if (Card.getCardByName(cardName) == null || user.getCardByName(cardName) == null) {
            return "card with name " + cardName + " does not exist";
        } else {
            ArrayList<Deck> decks = user.getDecks();
            if (Deck.getDeckByName(deckName) == null) {
                return "deck with name " + deckName + " does not exist";
            } else {
                Deck deck = Deck.getDeckByName(deckName);
                if (!userContainsDeck(deck,decks)) {
                    return "deck with name " + deckName + " does not exist";
                } else {
                    ArrayList<Card> mainCards = deck.getCardsMain();
                    ArrayList<Card> sideCards = deck.getCardsSide();
                    ArrayList<Card> userCards = user.getCards();
                    int numberOfCardsInDeck = 0;
                    int numberOfCardsInPlayerCards = 0;
                    for (Card card : sideCards) {
                        if (card.getName().equals(cardName)) numberOfCardsInDeck++;
                    }
                    for (Card card : mainCards) {
                        if (card.getName().equals(cardName)) numberOfCardsInDeck++;
                    }
                    for (Card card : userCards) {
                        if (card.getName().equals(cardName)) numberOfCardsInPlayerCards++;
                    }
                    if (numberOfCardsInDeck >= numberOfCardsInPlayerCards) {
                        return "card with name " + cardName + " does not exist";
                    } else {
                        if (command.split("/").length == 4) {
                            return addCardToMain(cardName, deckName);
                        } else {
                            return addCardToSide(cardName, deckName);
                        }
                    }
                }
            }
        }
    }

    public String addCardToMain(String cardName, String deckName) {
        Deck deck = Deck.getDeckByName(deckName);
        if (deck.getCardsMain().size() >= 60) {
            return "main deck is full";
        } else {
            ArrayList<Card> mainCards = deck.getCardsMain();
            ArrayList<Card> sideCards = deck.getCardsSide();
            int numberOfCardsInDeck = 0;
            for (int i = 0; i < mainCards.size(); i++) {
                if (mainCards.get(i).getName().equals(cardName)) {
                    numberOfCardsInDeck++;
                }
            }
            for (int i = 0; i < sideCards.size(); i++) {
                if (sideCards.get(i).getName().equals(cardName)) {
                    numberOfCardsInDeck++;
                }
            }
            if (numberOfCardsInDeck >= 3) {
                return "there are already three cards with name " + cardName + " in deck " + deckName;
            } else {
                Card card = Card.getCardByName(cardName);
                deck.addCardToMain(card);
                saveChangesToFile(deck);
                return "card added to deck successfully";
            }
        }
    }

    public String addCardToSide(String cardName, String deckName) {
        Deck deck = Deck.getDeckByName(deckName);
        if (deck.getCardsSide().size() >= 15) {
            return "main deck is full";
        } else {
            ArrayList<Card> mainCards = deck.getCardsMain();
            ArrayList<Card> sideCards = deck.getCardsSide();
            int numberOfCardsInDeck = 0;
            for (int i = 0; i < mainCards.size(); i++) {
                if (mainCards.get(i).getName().equals(cardName)) {
                    numberOfCardsInDeck++;
                }
            }
            for (int i = 0; i < sideCards.size(); i++) {
                if (sideCards.get(i).getName().equals(cardName)) {
                    numberOfCardsInDeck++;
                }
            }
            if (numberOfCardsInDeck >= 3) {
                return "there are already three cards with name " + cardName + " in deck " + deckName;
            } else {
                Card card = Card.getCardByName(cardName);
                deck.addCardToSide(card);
                saveChangesToFile(deck);
                return "card added to deck successfully";
            }
        }
    }

    public String deleteCard(String command) {
        if (!LoginAndSignUpController.loggedInUsers.containsKey(command.split("/")[3])) {
            return "wrong token!";
        }
        String cardName = command.split("/")[1];
        String deckName = command.split("/")[2];
        User user = LoginAndSignUpController.loggedInUsers.get(command.split("/")[3]);
        if (Card.getCardByName(cardName) == null) {
            return "card with name " + cardName + " does not exist";
        } else {
            ArrayList<Deck> decks = user.getDecks();
            if (Deck.getDeckByName(deckName) == null) {
                return "deck with name " + deckName + " does not exist";
            } else {
                Deck deck = Deck.getDeckByName(deckName);
                if (!userContainsDeck(deck,decks)) {
                    return "deck with name " + deckName + " does not exist";
                } else {
                    if (command.split("/").length == 4) {
                        return deleteCardFromMain(cardName, deckName);
                    } else {
                        return deleteCardFromSide(cardName, deckName);
                    }
                }
            }
        }
    }

    public String deleteCardFromMain(String cardName, String deckName) {
        Deck deck = Deck.getDeckByName(deckName);
        ArrayList<Card> mainCards = deck.getCardsMain();
        Card card = Card.getCardByName(cardName);
        for (Card card1 : mainCards) {
            if (card1.getName().equals(card.getName())) {
                deck.deleteCardFromMain(card);
                saveChangesToFile(deck);
                return "card removed form deck successfully";
            }
        }
        return "card with name " + cardName + " does not exist in main deck";
    }

    public String deleteCardFromSide(String cardName, String deckName) {
        Deck deck = Deck.getDeckByName(deckName);
        ArrayList<Card> sideCards = deck.getCardsSide();
        Card card = Card.getCardByName(cardName);
        for (Card card1 : sideCards){
            if (card1.getName().equals(card.getName())) {
                deck.deleteCardFromSide(card);
                saveChangesToFile(deck);
                return "card removed form deck successfully";
            }
        }
        return "card with name " + cardName + " does not exist in side deck";
    }

    public String deckShow (String command) {
        if (!LoginAndSignUpController.loggedInUsers.containsKey(command.split("/")[2])) {
            return "wrong token!";
        }
        String deckName = command.split("/")[1];
        User user = LoginAndSignUpController.loggedInUsers.get(command.split("/")[2]);
        ArrayList<String> output = new ArrayList<>();
        ArrayList<Deck> decks = user.getDecks();
        if (Deck.getDeckByName(deckName) == null) {
            output.add("deck with name " + deckName + " does not exist");
            return output.get(0);
        } else {
            Deck deck = Deck.getDeckByName(deckName);
            if (!userContainsDeck(deck,decks)) {
                output.add("deck with name " + deckName + " does not exist");
                return output.get(0);
            } else {
                output.add("Deck: " + deckName);
                if (command.split("/").length == 3) {
                    showDeckMain(output, deck);
                } else {
                    showDeckSide(output, deck);
                }
                String response = "";
                for (String s : output) {
                    response = response + s;
                    response = response + "\n";
                }
                return response;
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

    public String showAllOwnedCards(String command) {
        if (!LoginAndSignUpController.loggedInUsers.containsKey(command.split("/")[1])) {
            return "wrong token!";
        }
        User user = LoginAndSignUpController.loggedInUsers.get(command.split("/")[1]);
        String output = "";
        ArrayList<Card> cards = user.getCards();
        Comparator<Card> compareForShow = Comparator
                .comparing(Card::getName);
        cards.sort(compareForShow);
        for (int i = 0; i < cards.size(); i++) {
            output = output + cards.get(i).getName() + ": " + cards.get(i).getDescription();
            output = output + "\n";
        }
        return output;
    }

    public String showAllDeck(String command) {
        if (!LoginAndSignUpController.loggedInUsers.containsKey(command.split("/")[1])) {
            return "wrong token!";
        }
        User user = LoginAndSignUpController.loggedInUsers.get(command.split("/")[1]);
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
            Deck deck = Deck.getDeckByName(decks.get(i).getName());
            output.add(deck.getName() + ":  main deck " + deck.getCardsMain().size()
                    + ", side deck" + deck.getCardsSide().size() + ", " + validation);
        }
        String response = "";
        for (String s : output) {
            response = response + s;
            response = response + "\n";
        }
        return response;
    }

    public String showOneCard(String command) {
        if (!LoginAndSignUpController.loggedInUsers.containsKey(command.split("/")[2])) {
            return "wrong token!";
        }
        String cardName = command.split("/")[1];
        ArrayList<String> output = new ArrayList<>();
        if (Card.getCardByName(cardName) == null) {
            output.add("card with name " + cardName + " does not exist");
        } else {
            Card card = Card.getCardByName(cardName);
            if (card.getCategory().equals("Monster")) {
                output = showMonster(cardName);
            } else if (card.getCategory().equals("Spell")) {
                output = showSpell(cardName);
            } else {
                output = showTrap(cardName);
            }
        }
        String response = "";
        for (String s : output) {
            response = response + s;
            response = response + "\n";
        }
        return response;
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
            saveChangesToFile(inGameUser.getActiveDeck());
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

    public void saveChangesToFile(User user) {
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
