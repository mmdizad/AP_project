package Controller;

public class StandByPhaseController extends DuelController {

    public Integer hasSpellEffectInThisPhase() {
        return duelModel.getMessengerOfPeace().get(duelModel.turn).size();
    }

    public String effectOfSpellInThisPhase(int response) {
        if (response != 1 && response != 2) {
            return "you must entered 1 or 2";
        } else if (response == 1) {
            duelModel.deleteMessengerOfPeaceCards(duelModel.turn, duelModel.getMessengerOfPeace().get(duelModel.turn)
                    .get(0));
            return "your messenger of peace cards destroyed";
        } else {
            duelModel.decreaseLifePoint(100 * duelModel.getMessengerOfPeace().get(duelModel.turn).size(),
                    duelModel.turn);
            return "your lp decreases 100 unit";
        }
    }
}