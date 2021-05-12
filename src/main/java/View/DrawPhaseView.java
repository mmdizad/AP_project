package View;

import Controller.NewCardToHandController;
import Model.Card;

import java.util.ArrayList;
import java.util.Scanner;

public class DrawPhaseView extends DuelView {
    private static DrawPhaseView drawPhaseView = new DrawPhaseView();

    private DrawPhaseView() {
        super();

    }

    public static DrawPhaseView getInstance() {
        return drawPhaseView;
    }

    public void newCard(Scanner scanner, String playerUsername, boolean startOfGame) {
        System.out.println("DrawPhase");
        NewCardToHandController newCardToHandController = NewCardToHandController.getInstance();
        ArrayList<Card> cardsAddedToPlayerHand = newCardToHandController.newCardToHand(playerUsername);
        if (cardsAddedToPlayerHand != null) {
            for (Card card : cardsAddedToPlayerHand) {
                System.out.println("new card added to the hand :" + card.getName());
            }
        } else {
            System.out.println("no card added to hand");
        }
        if (!startOfGame) {
            DuelView.getInstance().showBoard();
            System.out.println(newCardToHandController.hasHeraldOfCreation());
            System.out.println(newCardToHandController.hasScannerMonster());
            StandByPhaseView standByPhaseView = StandByPhaseView.getInstance();
            standByPhaseView.run(scanner);
        }
    }

    public int scanNumberCardTributeForHeraldOfCreation() {
        System.out.println("please enter address of card from your hand for destroy it " +
                "for get herald of creation card");
        return scanner1.nextInt();
    }

    public int scanPlaceOfCardForAddToHandFromGraveyard() {
        System.out.println("please enter address of card that you want add to hand from graveyard" +
                "(level it must be 7 or more)");
        return scanner1.nextInt();
    }

    public String scanResponseForHeraldOfCreation() {
        System.out.println("do you want to use effect of Herald of Creation? (enter yse or no)");
        return scanner1.nextLine();
    }

    public String scanResponseForScanner() {
        System.out.println("do you want to change it with monster in opponent graveyard int this turn?");
        return scanner1.nextLine();
    }

    public int scanPlaceOfCardForInsteadOfScanner() {
        System.out.println("please enter address of monster from opponent graveyard, this" +
                "card is InsteadOf your scanner in this turn");
        return scanner1.nextInt();
    }


}
