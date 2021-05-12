package Controller;

public class StandByPhaseController extends DuelController {

    public boolean hasSpellEffectInThisPhase() {
        if (duelController.duelModel.getMessengerOfPeace().get(duelController.duelModel.turn).size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String effectOfSpellInThisPhase(int response) {
        if (response != 1 && response != 2) {
            return "you must entered 1 or 2";
        } else if (response == 1) {
            duelModel.deleteMessengerOfPeaceCards(duelModel.turn);
            return "your messenger of peace cards destroyed";
        } else {
            duelModel.decreaseLifePoint(100 * duelModel.getMessengerOfPeace().get(duelModel.turn).size(),
                    duelModel.turn);
            System.out.println("your lp decreases 100 unit");
        }
        return "";
    }
}