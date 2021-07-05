package Controller;

import Model.Card;
import View.DuelView;
import javafx.scene.image.ImageView;

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
            ImageView imageView =card1.getImageView();
            imageView.setX(200);
            imageView.setY(300);
            imageView.prefWidth(100);
            imageView.prefHeight(120);

            DuelView.hBoxS.getChildren().add(card1.getImageView());
        }
    }
}
