package View;

import Controller.NewCardToHandController;

public class DrawPhaseView extends DuelView {
    private static DrawPhaseView drawPhaseView = new DrawPhaseView();

    private DrawPhaseView() {

    }

    public static DrawPhaseView getInstance() {
        return drawPhaseView;
    }

    public void newCard(String playerUsername) {
        NewCardToHandController newCardToHandController = NewCardToHandController.getInstance();
        newCardToHandController.newCardToHand(playerUsername,duelModel);
    }

}
