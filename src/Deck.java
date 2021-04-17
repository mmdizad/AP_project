package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Deck {
    private String name;
    private String creatorName;
    private ArrayList<Card> cardsMain;
    private ArrayList<Card> cardsSide;
    private ArrayList<Card> allMonsters;
    private ArrayList<Card> allSpellsAndTraps;
    public HashMap<String,Deck> decks;

    public Deck(String name,String creatorName){

    }

    public void addCardToMain(Card card){

    }

    public void addCardToSide(Card card){


    }

    public void deleteCardFromMain(Card card){

    }

    public void deleteCardFromSide(Card card){


    }

    public static Deck getDeckByName(String name){
        return null;
        //جایگزین شود
    }

    public ArrayList<Card> getCardsMain() {
        return cardsMain;
    }

    public ArrayList<Card> getCardsSide() {
        return cardsSide;
    }

    public void sortCards(){

    }

    public boolean isDeckValid(){
        //جایگزین شود
        return true;
    }
}
