package View;

import Controller.NewCardToHandController;
import Model.Card;

import java.util.ArrayList;
import java.util.Scanner;

public class DrawPhaseView extends DuelView {
    private static DrawPhaseView drawPhaseView = new DrawPhaseView();

    public void run(Scanner scanner) {
        while (true) {
            String command = scanner.nextLine();
            if (command.equals("enterPhase")) {
                String newPhase = enterPhase("DrawPhase", scanner);
                if (newPhase.equals("MainPhase1")) {
                    MainPhase1View mainPhase1View = MainPhase1View.getInstance();
                    mainPhase1View.run(scanner);
                    break;
                } else if (newPhase.equals("MainPhase2")) {
                    MainPhase2View mainPhase2View = MainPhase2View.getInstance();
                    mainPhase2View.run(scanner);
                    break;
                } else if (newPhase.equals("BattlePhase")) {
                    BattlePhaseView battlePhaseView = BattlePhaseView.getInstance();
                    battlePhaseView.run(scanner);
                    break;
                }
            } else {
                System.out.println("invalid command");
            }
        }
    }

    private DrawPhaseView() {

    }

    public static DrawPhaseView getInstance() {
        return drawPhaseView;
    }

    public void newCard(String playerUsername) {
        System.out.println("DrawPhase");
        NewCardToHandController newCardToHandController = NewCardToHandController.getInstance();
        ArrayList<Card> cardsAddedToPlayerHand = newCardToHandController.newCardToHand(playerUsername, duelModel);
        if (cardsAddedToPlayerHand != null) {
            for (Card card : cardsAddedToPlayerHand) {
                System.out.println("new card added to the hand :" + card.getName());
            }
        }
    }

}
