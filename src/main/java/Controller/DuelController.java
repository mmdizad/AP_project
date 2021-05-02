package Controller;

import Model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class DuelController {
    protected DuelModel duelModel;

    public void setDuelModel(DuelModel duelModel) {
        this.duelModel = duelModel;
    }

    public void selectFirstPlayer() {

    }

    public String deselect() {
        if (duelModel.getSelectedCards().get(duelModel.turn).get(0) == null) {
            return "no card is selected yet";
        } else {
            duelModel.deSelectedCard();
            return "card deselected";
        }
    }

    public String activateEffect(Matcher matcher) {
        return null;
    }


    public ArrayList<String> showGraveYard() {
        ArrayList<Card> graveyardCards = duelModel.getGraveyard(0);
        ArrayList<String> output = new ArrayList<>();
        for (int i = 0; i < graveyardCards.size(); i++) {
            output.add(i + 1 + ". " + graveyardCards.get(i).getName() + ": " + graveyardCards.get(i).getDescription());
        }
        if (graveyardCards.size() == 0) {
            output.add("graveyard empty");
        }
        return output;
    }

    public ArrayList<String> checkCard(Matcher matcher) {
        ArrayList<String> output = new ArrayList<>();
        if (Card.getCardByName(matcher.group(1)) == null){
            output.add("card with name " + matcher.group(1) + " does not exist");
        }else {
            Card card = Card.getCardByName(matcher.group(1));
            if (card.getCategory().equals("Monster")){
                output = showMonster(matcher.group(1));
            }else if (card.getCategory().equals("Spell")){
                output = showSpell(matcher.group(1));
            }else {
                output = showTrap(matcher.group(1));
            }
        }
        return output;
    }

    public ArrayList<String> checkSelectedCard(Matcher matcher) {
        ArrayList<String> output = new ArrayList<>();
        ArrayList<ArrayList<Card>> selectedCards = duelModel.getSelectedCards();
        ArrayList<HashMap<Card,String>> detailsOfSelectedCards = duelModel.getDetailOfSelectedCard();
        if (selectedCards.get(duelModel.turn).get(0) == null){
            output.add("no card is selected yet");
        }else {
            Card card = selectedCards.get(duelModel.turn).get(0);
            if (detailsOfSelectedCards.get(duelModel.turn).get(card).equals("opponentH")){
                output.add("card is not visible");
            }else {
                if (card.getCategory().equals("Monster")){
                    output = showMonster(card.getName());
                }else if (card.getCategory().equals("Spell")){
                    output = showSpell(card.getName());
                }else {
                    output = showTrap(card.getName());
                }
            }
        }
        return output;
    }

    private ArrayList<String> showMonster(String cardName) {
        Monster monster = Monster.getMonsterByName(cardName);
        ArrayList<String> output = new ArrayList<>();
        output.add("Name: " + monster.getName());
        output.add("Level: " + monster.getLevel());
        output.add("Type: " + monster.getMonsterType());
        output.add("ATK: " + monster.getAttackPower());
        output.add("DEF: " + monster.getDefensePower());
        output.add("Description: " + monster.getDescription());
        return output;
    }

    private ArrayList<String> showSpell(String cardName) {
        Spell spell = Spell.getSpellByName(cardName);
        ArrayList<String> output = new ArrayList<>();
        output.add("Name: " + spell.getName());
        output.add("Spell");
        output.add("Type: " + spell.getCardType());
        output.add("Description: " + spell.getDescription());
        return output;
    }

    private ArrayList<String> showTrap(String cardName) {
        Trap trap = Trap.getTrapByName(cardName);
        ArrayList<String> output = new ArrayList<>();
        output.add("Name: " + trap.getName());
        output.add("Trap");
        output.add("Type: " + trap.getCardType());
        output.add("Description: " + trap.getDescription());
        return output;
    }

    public String surrender() {
        return null;
    }

    public String selectMonster(Matcher matcher) {
        if (duelModel.getMonster(duelModel.turn, Integer.parseInt(matcher.group(1))) == null) {
            return "no card found in the given position";
        } else {
            String condition = "my";
            condition = condition + duelModel.getMonsterCondition(duelModel.turn,Integer.parseInt(matcher.group(1))).charAt(1);
            duelModel.setSelectedCard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                    Integer.parseInt(matcher.group(1))),condition);
            return "card selected";
        }
    }


    public String selectOpponentMonster(Matcher matcher) {
        if (duelModel.getMonster(duelModel.turn - 1, Integer.parseInt(matcher.group(1))) == null) {
            return "no card found in the given position";
        } else {
            String condition = "opponent";
            condition = condition + duelModel.getMonsterCondition(duelModel.turn - 1,Integer.parseInt(matcher.group(1))).charAt(1);
            duelModel.setSelectedCard(duelModel.turn, duelModel.getMonster(duelModel.turn - 1,
                    Integer.parseInt(matcher.group(1))),condition);
            return "card selected";
        }
    }


    public String selectSpellOrTrap(Matcher matcher) {
        if (duelModel.getSpellAndTrap(duelModel.turn, Integer.parseInt(matcher.group(1))) == null) {
            return "no card found in the given position";
        } else {
            String condition = "my";
            condition = condition + duelModel.getMonsterCondition(duelModel.turn,Integer.parseInt(matcher.group(1))).charAt(0);
            duelModel.setSelectedCard(duelModel.turn, duelModel.getSpellAndTrap(duelModel.turn,
                    Integer.parseInt(matcher.group(1))),condition);
            return "card selected";
        }
    }

    public String selectOpponentSpellOrTrap(Matcher matcher) {
        if (duelModel.getSpellAndTrap(duelModel.turn - 1, Integer.parseInt(matcher.group(1))) == null) {
            return "no card found in the given position";
        } else {
            String condition = "opponent";
            condition = condition + duelModel.getMonsterCondition(duelModel.turn - 1,Integer.parseInt(matcher.group(1))).charAt(0);
            duelModel.setSelectedCard(duelModel.turn, duelModel.getSpellAndTrap(duelModel.turn - 1,
                    Integer.parseInt(matcher.group(1))),condition);
            return "card selected";
        }
    }

    public String selectFieldZone() {
        if (duelModel.getFieldZoneCard(duelModel.turn) == null) {
            return "no card found in the given position";
        } else {
            duelModel.setSelectedCard(duelModel.turn, duelModel.getFieldZoneCard(duelModel.turn),"myO");
            return "card selected";
        }
    }


    public String selectOpponentFieldZone() {
        if (duelModel.getFieldZoneCard(duelModel.turn - 1) == null) {
            return "no card found in the given position";
        } else {
            duelModel.setSelectedCard(duelModel.turn, duelModel.getFieldZoneCard(duelModel.turn - 1),"opponentO");
            return "card selected";
        }
    }


    public String selectHand(Matcher matcher) {
        if (duelModel.getHandCards().get(duelModel.turn).size() < Integer.parseInt(matcher.group(1))) {
            return "invalid selection";
        } else {
            duelModel.setSelectedCard(duelModel.turn, duelModel.getHandCards().get(duelModel.turn).
                    get(Integer.parseInt(matcher.group(1)) - 1),"myO");
            return "card selected";
        }
    }

}