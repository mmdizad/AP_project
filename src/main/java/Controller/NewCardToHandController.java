package Controller;

import Model.Card;
import Model.Deck;
import Model.DuelModel;
import Model.User;
import View.DrawPhaseView;

import java.util.ArrayList;


public class NewCardToHandController extends DuelController {

    private static NewCardToHandController newCardToHandController = new NewCardToHandController();

    private NewCardToHandController() {

    }

    public static NewCardToHandController getInstance() {
        return newCardToHandController;
    }

    public void newCardToHand(String playerUsername, DuelModel duelModel) {
        User user = User.getUserByUsername(playerUsername);
        Deck deck = user.getActiveDeck();
        ArrayList<Card> cardsInDeck = deck.getCardsMain();
        if (cardsInDeck.size() >= 1) {
            duelModel.addCardToHand();
        }
    }
}
