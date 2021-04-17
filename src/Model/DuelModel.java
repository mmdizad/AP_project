package Model;

import java.util.ArrayList;

public class DuelModel {
    private int lifePointUser = 8000;
    private int lifePointOpponent = 8000;
    private String playerUser;
    private String opponentUser;
    private int turn = 0;
    private ArrayList<ArrayList<Card>> playersCards;
    private ArrayList<ArrayList<Card>> monstersInField;
    private ArrayList<ArrayList<Card>> SpellsAndTraps;
    private ArrayList<ArrayList<String>> monsterCondition;
    private ArrayList<ArrayList<String>> spellAndTrapCondition;
    private ArrayList<ArrayList<Card>> graveyard;
    private ArrayList<ArrayList<Card>> handCards;
    private ArrayList<Card> field;

    public DuelModel(String playerUser,String opponentUser){

    }

    public void decreaseLifePoint(int lifePoint,int turn){

    }

    public void increaseLifePoint(int lifePoint,int turn){

    }

    public void addCardToHand(int turn){

    }

    public void addMonsterFromHandToGame(int turn,String condition,Card card){

    }

    public void addSpellAndTrapFromHandToGame(int turn,String condition,Card card){

    }

    public void changeAttackAndDefense(int place){

    }

    public void deleteMonster(int turn,int place){

    }

    public void deleteSpellAndTrap(int turn,int place){

    }

    public void activateSpell(int turn,int place){

    }

    public void changeSetToFaceUp(int place){

    }

    public String getGraveyard(){
        return null;
        //جایگزین شود
    }

    public Card getMonster(int turn,int place){
        return null;
        //جایگزین شود
    }

    public Card getSpellAndTrap(int turn,int place){
        return null;
        //جایگزین شود
    }

    public String getBoard(){
        return null;
        //جایگزین شود
    }

    public void changeUser(){

    }
}
