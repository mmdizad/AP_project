package Controller;

import Model.Card;
import Model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;

public class ShopController  {
     public static HashMap<String,Integer> cards = new HashMap<>();
     public ArrayList<String> forbiddenCards;

    private static ShopController shopController = new ShopController();


    private ShopController() {

    }

    public static ShopController getInstance() {
        return shopController;
    }

    public String buyCard(String command) {
        String cardName = command.split("/")[1];
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
            if (!LoginAndSignUpController.loggedInUsers.containsKey(command.split("/")[1])) {
                return "wrong token!";
            }else {
                User user = LoginAndSignUpController.loggedInUsers.get(command.split("/")[2]);
                Card card = Card.getCardByName(cardName);
                if (user.getCoins() >= card.getPrice()){
                    user.decreaseCoins(card.getPrice());
                    user.addCard(card);
                    //check shavad
                    return "card bought!";
                } else return "not enough money";
            }
        }


    }
    public static void initializeCard(){
        ArrayList<Card> allCards = Card.getAllCardsCard();
        for (Card card : allCards) {
            cards.put(card.getName(),5);
        }
    }

    public String getAllCard() {
       StringBuilder output = new StringBuilder();
       ArrayList cards = new ArrayList(this.cards.keySet());

        for (int i = 0; i < this.cards.size(); i++) {
            output.append(cards.get(i)).append(" : ").append(this.cards.get(cards.get(i)));
        }
        return output.toString();
    }

    public String increaseMoney(String command) {
        LoginAndSignUpController.loggedInUsers.get(command.split("/")[2]).increaseCoins(Integer.parseInt(command.split("/")[1]));
        return "coin increased";
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