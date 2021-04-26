package Controller;

import Model.Card;
import Model.DuelModel;
import Model.User;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class DuelController {
     protected DuelModel duelModel;

    public void setDuelModel(DuelModel duelModel) {
         this.duelModel = duelModel;
    }

    public void selectFirstPlayer(){

    }

    public String deselect(){
        return null;
    }

    public String nextPhase(){
        return null;
    }

    public String activateEffect(Matcher matcher){
        return null;
    }


    public ArrayList<String> showGraveYard(){
        ArrayList<Card> graveyardCards = duelModel.getGraveyard(0);
        ArrayList<String> output = new ArrayList<>();
        for (int i = 0;i < graveyardCards.size();i++){

            output.add(i+1 + ". " + graveyardCards.get(i).getName() + ": " + graveyardCards.get(i).getDescription());
        }
        if (graveyardCards.size() == 0){
            output.add("graveyard empty");
        }
        return output;
    }

    public ArrayList<String> checkCard(Matcher matcher){
        return null;
    }

    public ArrayList<String> checkSelectedCard(Matcher matcher){
        return null;
    }

    private ArrayList<String> showMonster(String cardName){
        return null;
    }

    private ArrayList<String> showSpell(String cardName){
        return null;
    }

    private ArrayList<String> showTrap(String cardName){
        return null;
    }

    public String surrender(){
        return null;
    }

    public ArrayList<String> selectMonster(Matcher matcher){
        return null;
    }


    public String selectOpponentMonster(Matcher matcher){
        if (duelModel.getMonster(1,Integer.parseInt(matcher.group(1))) == null){
            return "no card found in the given position";
        }else {
         duelModel.setSelectedCard(0,duelModel.getMonster(1,Integer.parseInt(matcher.group(1))));
         return "card selected";
        }
    }


    public ArrayList<String> selectSpellOrTrap(Matcher matcher){
        return null;
    }


    public String selectOpponentSpellOrTrap(Matcher matcher){
        if (duelModel.getSpellAndTrap(1,Integer.parseInt(matcher.group(1))) == null){
            return "no card found in the given position";
        }else {
            duelModel.setSelectedCard(0,duelModel.getSpellAndTrap(1,Integer.parseInt(matcher.group(1))));
            return "card selected";
        }
    }

    public ArrayList<String> selectFieldZone(Matcher matcher){
        return null;
    }


    public String selectOpponentFieldZone(Matcher matcher){
        if (duelModel.getFieldZoneCard(1) == null){
            return "no card found in the given position";
        }else {
            duelModel.setSelectedCard(0,duelModel.getFieldZoneCard(1));
            return "card selected";
        }
    }


    public ArrayList<String> selectHand(Matcher matcher){
        return null;
    }

}