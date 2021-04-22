package Controller;

import Model.Card;

import java.util.ArrayList;
import java.util.Comparator;
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
                user.increaseCoins(card.getPrice() * -1);
                user.addCard(card);
                //check shavad
                return "card bought!";
            } else return "not enough money";
        }

    }

    public ArrayList<String> getAllCard() {
        ArrayList<String> output = new ArrayList<>();
        ArrayList<Card> cards = Card.getAllCardsCard();
        cards.sort(Comparator.comparing(Card::getName));

        for (Card card : cards) {
            output.add(card.getName() + ": " + card.getDescription());
        }
        return output;
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