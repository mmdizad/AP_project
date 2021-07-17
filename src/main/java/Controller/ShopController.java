package Controller;

import Model.Card;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class ShopController extends LoginController {

    private static ShopController shopController = new ShopController();

    private ShopController() {

    }

    public static ShopController getInstance() {
        return shopController;
    }

    public String buyCard(Matcher matcher) {
        String cardName = matcher.group(1);
        try {
            dataOutputStream.writeUTF("shop buy card/" + matcher.group(1) + "/" + token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Card> cards = Card.getAllCardsCard();
        boolean cardNameExist = false;
        for (Card value : cards) {
            if (cardName.equals(value.getName())) {
                cardNameExist = true;
                break;
            }
        }

        if (!cardNameExist) return "there is no card with this name";
        else {
            Card card = Card.getCardByName(cardName);
            if (user.getCoins() >= card.getPrice()) {
                user.decreaseCoins(card.getPrice());
                user.addCard(card);
                //check shavad
                return "card bought!";
            } else return "not enough money";
        }

    }

    public String getAllCard() {
        try {
            dataOutputStream.writeUTF("shop show --all");
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "assd";
    }

    public String increaseMoney(Matcher matcher) {
        try {
            dataOutputStream.writeUTF("shop increase money/" + matcher.group(1) + "/" + token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return " ";
    }

    public ArrayList<String> checkCard(Matcher matcher) {
        return null;
    }

    private ArrayList<String> showMonster(String cardName) {
        return null;
    }

    private ArrayList<String> showSpell(String cardName) {
        return null;
    }

    private ArrayList<String> showTrap(String cardName) {
        return null;
    }

}