package View;

import Controller.NewCardToHandController;
import Model.Card;

import java.util.ArrayList;

public class DrawPhaseView extends DuelView {
    private static DrawPhaseView drawPhaseView = new DrawPhaseView();

    private DrawPhaseView() {

    }

    public static DrawPhaseView getInstance() {
        return drawPhaseView;
    }

    public void newCard(String playerUsername) {
        System.out.println("DrawPhase");
        NewCardToHandController newCardToHandController = NewCardToHandController.getInstance();
        ArrayList<Card> cardsAddedToPlayerHand = newCardToHandController.newCardToHand(playerUsername,duelModel);
       if (cardsAddedToPlayerHand != null){
           for (Card card : cardsAddedToPlayerHand ){
               System.out.println("new card added to the hand :" + card.getName());
           }
       }
    }

}
