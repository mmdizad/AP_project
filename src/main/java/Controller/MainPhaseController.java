package Controller;

import Model.Card;
import Model.Monster;
import View.MainPhaseView;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class MainPhaseController extends DuelController {
    private static MainPhaseController mainPhaseController = new MainPhaseController();

    private MainPhaseController() {

    }

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
            } else if (this.duelModel.monsterSetOrSummonInThisTurn==null) {
                return "you already summoned/set on this turn";
            } else if ((selectedCards.get(this.duelModel.turn).get(0)).getCardType().equals("Monster")) {
                return this.setMonster();
            } else
                return setTrapOrSpell();

        }
    }

    public String setTrapOrSpell() {
        if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(0) == null)
            duelModel.addSpellAndTrapFromHandToGame("set", 0);
        else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(1) == null)
            duelModel.addSpellAndTrapFromHandToGame("set", 1);
        else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(2) == null)
            duelModel.addSpellAndTrapFromHandToGame("set", 2);
        else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(3) == null)
            duelModel.addSpellAndTrapFromHandToGame("set", 3);
        else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(4) == null)
            duelModel.addSpellAndTrapFromHandToGame("set", 4);
        else return "monster card zone is full";
        return "set successfully";
    }

    public String setMonster() {
        if (duelModel.getMonstersInField().get(duelModel.turn).get(0) == null)
            duelModel.addMonsterFromHandToGame("OO", 0);
        else if (duelModel.getMonstersInField().get(duelModel.turn).get(1) == null)
            duelModel.addMonsterFromHandToGame("OO", 1);
        else if (duelModel.getMonstersInField().get(duelModel.turn).get(2) == null)
            duelModel.addMonsterFromHandToGame("OO", 2);
        else if (duelModel.getMonstersInField().get(duelModel.turn).get(3) == null)
            duelModel.addMonsterFromHandToGame("OO", 3);
        else if (duelModel.getMonstersInField().get(duelModel.turn).get(4) == null)
            duelModel.addMonsterFromHandToGame("OO", 4);
        else {
            return "monster card zone is full";
        }
        return "summoned successfully";
    }

    public String setPosition(Matcher matcher) {
        return null;
    }

    public String summon() {
        MainPhaseView mainPhaseView = MainPhaseView.getInstance();
        boolean existMonsterOnField = false;
        if (duelModel.getSelectedCards().get(duelModel.turn).get(0) == null) {
            return "no card is selected yet";
        } else if (!duelModel.getSelectedCards().get(duelModel.turn).get(0).getCategory().equals("Monster")) {
            return "you can’t summon this card";
        } else {
            Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
            ArrayList<Card> cardsInHandsOfPlayer = duelModel.getHandCards().get(duelModel.turn);
            String detailsOfSelectedCard = duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(card);
            if (!detailsOfSelectedCard.equals("myHand")){
                return "you can’t summon this card";
            }
           else if (card.getCardType().equals("Ritual")) {
                return "you can’t summon this card";
            } else if (duelModel.getMonsterSetOrSummonInThisTurn()!= null) {
                return "you already summoned/set on this turn";
            } else {
                Monster monster = (Monster) card;
                if (monster.getLevel() <= 4) {
                    return setMonsterOnField(monster);
                } else if (monster.getLevel() == 5 || monster.getLevel() == 6) {
                    for (Card cardInHandOfPlayer : cardsInHandsOfPlayer) {
                        if (cardInHandOfPlayer.getCategory().equals("monster")) {
                            existMonsterOnField = true;
                            break;
                        }
                    }
                    if (!existMonsterOnField) {
                        return "there are not enough cards for tribute";
                    } else {
                        int address = mainPhaseView.getMonsterAddressForTribute();
                        if (address > 5) {
                            return "there no monsters one this address";
                        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(address - 1) == null) {
                            return "there no monsters one this address";
                        } else {
                            duelModel.deleteMonster(duelModel.turn, address - 1);
                            return setMonsterOnField(monster);
                        }
                    }
                } else if (monster.getLevel() >= 7) {
                    int numberOfPlayerMonsterOnField = 0;
                    for (Card cardInHandOfPlayer : cardsInHandsOfPlayer) {
                        if (cardInHandOfPlayer.getCategory().equals("monster")) {
                            numberOfPlayerMonsterOnField++;
                        }
                    }
                    if (numberOfPlayerMonsterOnField < 2) {
                        return "there are not enough cards for tribute";
                    }
                    int address = mainPhaseView.getMonsterAddressForTribute();
                    int address1 = mainPhaseView.getMonsterAddressForTribute();
                    if (address > 5 || address1 > 5) {
                        return "there is no monster on one of these addresses";
                    } else if (duelModel.getMonstersInField().get(duelModel.turn).get(address - 1) == null
                            || duelModel.getMonstersInField().get(duelModel.turn).get(address1 - 1) == null) {
                        return "there is no monster on one of these addresses";
                    } else {
                        duelModel.deleteMonster(duelModel.turn, address - 1);
                        duelModel.deleteMonster(duelModel.turn, address1 - 1);
                        return setMonsterOnField(monster);
                    }
                }
            }
        }
        return null;
    }

    public String setMonsterOnField(Monster monster) {
        if (duelModel.getMonstersInField().get(duelModel.turn).get(0) == null)
            duelModel.addMonsterFromHandToGame("OO", 0);
        else if (duelModel.getMonstersInField().get(duelModel.turn).get(1) == null)
            duelModel.addMonsterFromHandToGame("OO", 1);
        else if (duelModel.getMonstersInField().get(duelModel.turn).get(2) == null)
            duelModel.addMonsterFromHandToGame("OO", 2);
        else if (duelModel.getMonstersInField().get(duelModel.turn).get(3) == null)
            duelModel.addMonsterFromHandToGame("OO", 3);
        else if (duelModel.getMonstersInField().get(duelModel.turn).get(4) == null)
            duelModel.addMonsterFromHandToGame("OO", 4);
        else {
            return "monster card zone is full";
        }
        duelModel.setMonsterSetOrSummonInThisTurn(monster);
        return "summoned successfully";
    }

    public String flipSummon() {
        if (duelModel.getSelectedCards().get(duelModel.turn).get(0) == null) {
            return "no card is selected yet";
        } {
            boolean existSelectedCardInPlayerMonsterZone = false;
            ArrayList<Card> monstersInPlayerMonsterZone = duelModel.getMonstersInField().get(duelModel.turn);
            for (Card card : monstersInPlayerMonsterZone){
                if (card == duelModel.getSelectedCards().get(duelModel.turn).get(0)){
                    existSelectedCardInPlayerMonsterZone = true;
                    break;
                }
            }
            if (!existSelectedCardInPlayerMonsterZone){
                return "you can’t change this card position";
            }else{
                Monster monster = (Monster) duelModel.getSelectedCards().get(duelModel.turn).get(0);
                if (monster == duelModel.getMonsterSetOrSummonInThisTurn()){
                    return "you can’t flip summon this card";
                }
            }
        }
        return null;
    }

    public String specialSummon(Matcher matcher) {
        return null;
    }


    public String ritualSummon(Matcher matcher) {
        return null;
    }
}