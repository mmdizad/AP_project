package Controller;

import Model.Card;
import Model.DuelModel;
import java.util.ArrayList;
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
        return null;
    }

    public ArrayList<String> checkSelectedCard(Matcher matcher) {
        return null;
    }

    private ArrayList<String> showMonster(String cardName) {
        return null;
    }

    private ArrayList<String> showSpell(String cardName) {
        return null;
    }

    private ArrayList<String> showTrap(String cardName) {
        return null;
    }

    public String surrender() {
        return null;
    }

    public String selectMonster(Matcher matcher) {
        if (duelModel.getMonster(duelModel.turn, Integer.parseInt(matcher.group(1))) == null) {
            return "no card found in the given position";
        } else {
            duelModel.setSelectedCard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                    Integer.parseInt(matcher.group(1))));
            return "card selected";
        }
    }


    public String selectOpponentMonster(Matcher matcher) {
        if (duelModel.getMonster(duelModel.turn - 1, Integer.parseInt(matcher.group(1))) == null) {
            return "no card found in the given position";
        } else {
            duelModel.setSelectedCard(duelModel.turn, duelModel.getMonster(duelModel.turn - 1,
                    Integer.parseInt(matcher.group(1))));
            return "card selected";
        }
    }


    public String selectSpellOrTrap(Matcher matcher) {
        if (duelModel.getSpellAndTrap(duelModel.turn, Integer.parseInt(matcher.group(1))) == null) {
            return "no card found in the given position";
        } else {
            duelModel.setSelectedCard(duelModel.turn, duelModel.getSpellAndTrap(duelModel.turn,
                    Integer.parseInt(matcher.group(1))));
            return "card selected";
        }
    }

    public String selectOpponentSpellOrTrap(Matcher matcher) {
        if (duelModel.getSpellAndTrap(duelModel.turn - 1, Integer.parseInt(matcher.group(1))) == null) {
            return "no card found in the given position";
        } else {
            duelModel.setSelectedCard(duelModel.turn, duelModel.getSpellAndTrap(duelModel.turn - 1,
                    Integer.parseInt(matcher.group(1))));
            return "card selected";
        }
    }

    public String selectFieldZone() {
        if (duelModel.getFieldZoneCard(duelModel.turn) == null) {
            return "no card found in the given position";
        } else {
            duelModel.setSelectedCard(duelModel.turn, duelModel.getFieldZoneCard(duelModel.turn));
            return "card selected";
        }
    }


    public String selectOpponentFieldZone() {
        if (duelModel.getFieldZoneCard(duelModel.turn - 1) == null) {
            return "no card found in the given position";
        } else {
            duelModel.setSelectedCard(duelModel.turn, duelModel.getFieldZoneCard(duelModel.turn - 1));
            return "card selected";
        }
    }


    public String selectHand(Matcher matcher) {
        if (duelModel.getHandCards().get(duelModel.turn).size() < Integer.parseInt(matcher.group(1))) {
            return "invalid selection";
        } else {
            duelModel.setSelectedCard(duelModel.turn, duelModel.getHandCards().get(duelModel.turn).
                    get(Integer.parseInt(matcher.group(1)) - 1));
            return "card selected";
        }
    }

}