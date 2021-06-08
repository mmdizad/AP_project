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

public class DeckAndSignUpController extends LoginAndSignUpController {

    private static DeckAndSignUpController deckController = new DeckAndSignUpController();

    private DeckAndSignUpController() {

    }

    public static DeckAndSignUpController getInstance() {
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

    public boolean userContainsDeck(Deck deck,ArrayList<Deck> decks) {
        for (Deck deck1 : decks){
            if (deck1.getName().equals(deck.getName())){
                return true;
            }
        }
        return false;
    }
}