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

public class DeckController extends LoginController {

    private static DeckController deckController = new DeckController();

    private DeckController() {

    }

    public static DeckController getInstance() {
        return deckController;
    }

    public String deckCreate(Matcher matcher) {
        try {
            dataOutputStream.writeUTF("deck create/" + matcher.group(1) + "/" + token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String deckDelete(Matcher matcher) {
        try {
            dataOutputStream.writeUTF("deck delete/" + matcher.group(1) + "/" + token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String deckSetActivate(Matcher matcher) {
        try {
            dataOutputStream.writeUTF("deck setActive/" + matcher.group(1) + "/" + token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String addCard(Matcher matcher) {
        try {
            if (matcher.groupCount() == 2) {

                dataOutputStream.writeUTF("add card" + "/" + matcher.group(1) + "/" + matcher.group(2) + "/" + token);

            } else {
                dataOutputStream.writeUTF("add card" + "/" + matcher.group(1) + "/" + matcher.group(2) + "/" +  token + "/" + matcher.group(3));
            }
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        }catch(IOException e){
            e.printStackTrace();
        }
        return "";
    }

    public String deleteCard(Matcher matcher) {
        try {
            if (matcher.groupCount() == 2) {

                dataOutputStream.writeUTF("delete card" + "/" + matcher.group(1) + "/" + matcher.group(2) + "/" +  token);

            } else {
                dataOutputStream.writeUTF("delete card" + "/" + matcher.group(1) + "/" + matcher.group(2) + "/" +  token + "/" + matcher.group(3));
            }
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        }catch(IOException e){
            e.printStackTrace();
        }
        return "";
    }

    public String deckShow(Matcher matcher) {
        try {
            if (matcher.groupCount() == 1) {

                dataOutputStream.writeUTF("deck show" + "/" + matcher.group(1) + "/" +  token);

            } else {
                dataOutputStream.writeUTF("deck show" + "/" + matcher.group(1) + "/" +  token + "/" + matcher.group(2));
            }
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        }catch(IOException e){
            e.printStackTrace();
        }
        return "";
    }

    public String checkCard(Matcher matcher) {
        try {
            dataOutputStream.writeUTF("show one card/" + matcher.group(1) + "/" + token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String showAllDeck() {
        try {
            dataOutputStream.writeUTF("show all deck/" + token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String showAllOwnedCards() {
        try {
            dataOutputStream.writeUTF("show all owned cards/" + token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
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