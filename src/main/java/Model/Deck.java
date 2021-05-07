package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Deck {
    private String name;
    private String creatorName;
    private ArrayList<Card> cardsMain = new ArrayList<>();
    private ArrayList<Card> cardsSide = new ArrayList<>();
    private ArrayList<Card> allMonsters = new ArrayList<>();
    private ArrayList<Card> allSpellsAndTraps = new ArrayList<>();
    public static HashMap<String, Deck> decks;

    static {
        decks = new HashMap<>();
    }

    public Deck(String name, String creatorName) {
        this.name = name;
        this.creatorName = creatorName;
        decks.put(name, this);
        cardsMain = new ArrayList<>();
        cardsSide = new ArrayList<>();
    }

    public void addCardToMain(Card card) {
        if (card.getCategory().equals("Monster")){
            Card card1 = new Card(card.getName(),card.getDescription(),card.getCardType(),card.getPrice(),card.getCategory());
            card1.setAttackPower(Monster.getMonsterByName(card.getName()).getAttackPower());
            card1.setDefensePower(Monster.getMonsterByName(card.getName()).getDefensePower());
            card1.setLevel(Monster.getMonsterByName(card.getName()).getLevel());
            cardsMain.add(card1);
        }else {
            Card card1 = new Card(card.getName(),card.getDescription(),card.getCardType(),card.getPrice(),card.getCategory());
            cardsMain.add(card1);
        }
    }

    public void addCardToSide(Card card) {
        if (card.getCategory().equals("Monster")){
            Card card1 = new Card(card.getName(),card.getDescription(),card.getCardType(),card.getPrice(),card.getCategory());
            card1.setAttackPower(Monster.getMonsterByName(card.getName()).getAttackPower());
            card1.setDefensePower(Monster.getMonsterByName(card.getName()).getDefensePower());
            card1.setLevel(Monster.getMonsterByName(card.getName()).getLevel());
            cardsSide.add(card1);
        }else {
            Card card1 = new Card(card.getName(),card.getDescription(),card.getCardType(),card.getPrice(),card.getCategory());
            cardsSide.add(card1);
        }
    }

    public void deleteCardFromMain(Card card) {
        for (Card card1 : cardsMain){
            if (card1.getName().equals(card.getName())){
                cardsMain.remove(card1);
                return;
            }
        }
    }

    public void deleteCardFromSide(Card card) {
        for (Card card1 : cardsSide){
            if (card1.getName().equals(card.getName())){
                cardsSide.remove(card1);
                return;
            }
        }
    }

    public static void deleteDeck(Deck deck) {
        decks.remove(deck);
    }

    public static Deck getDeckByName(String name) {
        return decks.get(name);
    }

    public ArrayList<Card> getCardsMain() {
        return cardsMain;
    }

    public ArrayList<Card> getCardsSide() {
        return cardsSide;
    }

    public void sortCards() {

    }

    public String getName() {
        return name;
    }

    public boolean isDeckValid() {
        //جایگزین شود
        return true;
    }
}