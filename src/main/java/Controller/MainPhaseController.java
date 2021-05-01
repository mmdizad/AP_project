package Controller;

import Model.Card;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class MainPhaseController extends DuelController {
    private static MainPhaseController mainPhaseController = new MainPhaseController();

    public static MainPhaseController getInstance() {
        return mainPhaseController;
    }

    public String set() {
        ArrayList<ArrayList<Card>> selectedCards = this.duelModel.getSelectedCards();
        if (selectedCards.get(this.duelModel.turn) == null) {
            return "no card is selected yet";
        } else {
            if (!(duelModel.getHandCards().get(this.duelModel.turn)).contains((selectedCards.get(this.duelModel.turn)).get(0))) {
                return "you can’t set this card";
            } else if (this.duelModel.moneterCardSetOrSummonThisTurn) {
                return "you already summoned/set on this turn";
            } else if ((selectedCards.get(this.duelModel.turn).get(0)).getCardType().equals("Monster")) {
                return this.setMonster();
            } else
                return setTrapOrSpell();

        }
    }

    public String setTrapOrSpell(/*Matcher matcher*/) {
        return null;
    }


    public String setMonster(/*Matcher matcher*/) {
        if (duelModel.getSelectedCards().get(duelModel.turn).get(0) == null)
            duelModel.addMonsterFromHandToGame("set", 0);
        else if (duelModel.getSelectedCards().get(duelModel.turn).get(1) == null)
            duelModel.addMonsterFromHandToGame("set", 1);
        else if (duelModel.getSelectedCards().get(duelModel.turn).get(2) == null)
            duelModel.addMonsterFromHandToGame("set", 2);
        else if (duelModel.getSelectedCards().get(duelModel.turn).get(3) == null)
            duelModel.addMonsterFromHandToGame("set", 3);
        else if (duelModel.getSelectedCards().get(duelModel.turn).get(4) == null)
            duelModel.addMonsterFromHandToGame("set", 4);
else return "monster card zone is full";
return "set successfully";
    }

    public String setPosition(Matcher matcher) {
        return null;
    }

    public String summon(Matcher matcher) {
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