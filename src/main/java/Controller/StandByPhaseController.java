package Controller;

import Model.DuelModel;

public class StandByPhaseController extends DuelController {
    private final DuelModel duelModel = duelController.duelModel;

    public Integer hasSpellEffectInThisPhase() {
        return duelModel.getMessengerOfPeace().get(duelModel.turn).size();
    }

    public String effectOfSpellInThisPhase(int response) {
        if (response != 1 && response != 2) {
            return "you must entered 1 or 2";
        } else if (response == 1) {
            duelModel.deleteMessengerOfPeaceCards(duelModel.turn, duelModel.getMessengerOfPeace().get(duelModel.turn)
                    .get(0));
            return "your messenger of peace card destroyed";
        } else {
            duelModel.decreaseLifePoint(100, duelModel.turn);
            return "your lp decreases 100 unit";
        }
    }
}