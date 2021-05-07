package Controller;

import Model.Card;
import Model.Deck;
import Model.DuelModel;
import Model.User;

import java.util.ArrayList;


public class NewCardToHandController extends DuelController {

    private static NewCardToHandController newCardToHandController = new NewCardToHandController();

    private NewCardToHandController() {

    }

    public static NewCardToHandController getInstance() {
        return newCardToHandController;
    }

    public ArrayList<Card> newCardToHand(String playerUsername, DuelModel duelModel) {
        User user = User.getUserByUsername(playerUsername);
        Deck deck = user.getActiveDeck();
        ArrayList<Card> cardsInDeck = deck.getCardsMain();
        if (cardsInDeck.size() >= 1) {
            //ما اینجا duelmodelنول هست چک کنید
            for (int i = 1;i < 6;i++){
                Card card = duelModel.getSpellAndTrap(1 - duelModel.turn, i);
                if (card != null) {
                    if (card.getName().equals("Time Seal") && duelModel.getSpellAndTrapCondition(1 - duelModel.turn, i).charAt(0) == 'O'){
                        duelModel.deleteSpellAndTrap(1 - duelModel.turn, i - 1);
                        duelModel.addCardToGraveyard(1 - duelModel.turn, card);
                        return duelModel.getHandCards().get(duelModel.turn);
                    }
                }
            }
            return duelModel.addCardToHand();
        } else {
            return null;
            // جایگزین دارد
        }
    }
}
