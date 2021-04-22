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
    public static HashMap<String,Deck> decks;

    public Deck(String name,String creatorName){
        this.name = name;
        this.creatorName = creatorName;
        decks.put(name,this);
    }

    public void addCardToMain(Card card){
        cardsMain.add(card);
    }

    public void addCardToSide(Card card){
        cardsSide.add(card);
    }

    public void deleteCardFromMain(Card card){
        cardsMain.remove(card);
    }

    public void deleteCardFromSide(Card card){
        cardsSide.remove(card);
    }

    public static void deleteDeck(Deck deck){
        decks.remove(deck);
    }

    public static Deck getDeckByName(String name){
        return decks.get(name);
    }

    public ArrayList<Card> getCardsMain() {
        return cardsMain;
    }

    public ArrayList<Card> getCardsSide() {
        return cardsSide;
    }

    public void sortCards(){

    }

    public String getName() {
        return name;
    }

    public boolean isDeckValid(){
        //جایگزین شود
        return true;
    }
}