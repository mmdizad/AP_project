package Controller;

import Model.Card;
import View.DuelView;
import java.util.ArrayList;

public class TransitionOfCardToHand {
    private static final TransitionOfCardToHand transitionOfCardToHand = new TransitionOfCardToHand();

    public static TransitionOfCardToHand getInstance() {
        return transitionOfCardToHand;
    }

    private TransitionOfCardToHand() {

    }

    public void transition(ArrayList<Card> cards) {
        for (Card card: cards) {
            String cardName = card.getName();
            Card card1 = Card.getCardByName(cardName);
            DuelView.pane.getChildren().add(card1.getImageView());
        }
    }
}
