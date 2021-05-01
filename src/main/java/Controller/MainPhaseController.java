package Controller;
import Model.Card;
import Model.Monster;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class MainPhaseController extends DuelController {
    private static MainPhaseController mainPhaseController = new MainPhaseController();

    private MainPhaseController() {

    }

    public static MainPhaseController getInstance() {
        return mainPhaseController;
    }

    public String set(Matcher matcher) {
        return null;
    }

    public String setTrap(Matcher matcher) {
        return null;
    }

    public String setSpell(Matcher matcher) {
        return null;
    }

    public String setMonster(Matcher matcher) {
        return null;
    }

    public String setPosition(Matcher matcher) {
        return null;
    }

    public String summon() {
        boolean existSelectedCardInHandOfPlayer = false;
        if (duelModel.getSelectedCards().get(duelModel.turn).get(0) == null) {
            return "no card is selected yet";
        } else if (!duelModel.getSelectedCards().get(duelModel.turn).get(0).getCategory().equals("Monster")) {
            return "you can’t summon this card";
        } else {
            Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
            ArrayList<Card> cardsInHandsOfPlayer = duelModel.getHandCards().get(duelModel.turn);
            for (Card cardInHandOfPlayer : cardsInHandsOfPlayer) {
                if (card == cardInHandOfPlayer) {
                    existSelectedCardInHandOfPlayer = true;
                    break;
                }
            }
            if (!existSelectedCardInHandOfPlayer) {
                return "you can’t summon this card";
            } else if (card.getCardType().equals("Ritual")) {
                return "you can’t summon this card";
            } else if (duelModel.getMonstersInField().get(duelModel.turn).size() >= 5) {
                return "monster card zone is full";
            }else if (duelModel.monsterSetOrSummonInThisTurn!=null){
                return "you already summoned/set on this turn";
            }
        }
        return null;
    }

    public String flipSummon(Matcher matcher) {
        return null;
    }

    public String specialSummon(Matcher matcher) {
        return null;
    }


    public String ritualSummon(Matcher matcher) {
        return null;
    }

}