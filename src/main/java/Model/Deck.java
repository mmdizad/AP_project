package Model;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Deck {
    private String name;
    private String creatorName;
    private ArrayList<Card> cardsMain = new ArrayList<>();
    private ArrayList<Card> cardsSide = new ArrayList<>();
    private ArrayList<Card> allMonsters = new ArrayList<>();
    private ArrayList<Card> allSpellsAndTraps = new ArrayList<>();
    private int numberOfCards = 0;
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
            card1.setImageView(card.getImageView());
            System.out.println(card.getImageView());
            cardsMain.add(card1);
        }else {
            Card card1 = new Card(card.getName(),card.getDescription(),card.getCardType(),card.getPrice(),card.getCategory());
            card1.setImageView(card.getImageView());
            System.out.println(card.getImageView());
            cardsMain.add(card1);
        }
        numberOfCards = cardsMain.size();
    }

    public void addCardToSide(Card card) {
        if (card.getCategory().equals("Monster")){
            Card card1 = new Card(card.getName(),card.getDescription(),card.getCardType(),card.getPrice(),card.getCategory());
            card1.setAttackPower(Monster.getMonsterByName(card.getName()).getAttackPower());
            card1.setDefensePower(Monster.getMonsterByName(card.getName()).getDefensePower());
            card1.setLevel(Monster.getMonsterByName(card.getName()).getLevel());
            card1.setImageView(card.getImageView());
            cardsSide.add(card1);
        }else {
            Card card1 = new Card(card.getName(),card.getDescription(),card.getCardType(),card.getPrice(),card.getCategory());
            card1.setImageView(card.getImageView());
            cardsSide.add(card1);
        }
    }

    public void deleteCardFromMain(Card card) {
        for (Card card1 : cardsMain){
            if (card1.getName().equals(card.getName())){
                cardsMain.remove(card1);
                numberOfCards = cardsMain.size();
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
        decks.remove(deck.getName());
    }

    public static Deck getDeckByName(String name) {
        File file = new File(System.getProperty("user.home") + "/Desktop\\AP FILES\\Decks\\" + name + "deck.txt");
        if (!file.exists()){
            return null;
        }else {
            Gson gson = new Gson();
            StringBuilder getDetail = new StringBuilder();
            Scanner myReader = null;
            try {
                myReader = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (myReader.hasNextLine()) {
                getDetail.append(myReader.nextLine());
            }
            String userInfo = getDetail.toString();
            Deck deck = gson.fromJson(userInfo,Deck.class);
            myReader.close();
            return deck;
        }
    }

    public ArrayList<Card> getCardsMain() {
        return cardsMain;
    }

    public ArrayList<Card> getCardsSide() {
        return cardsSide;
    }

    public void sortCards() {

    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getName() {
        return name;
    }

    public boolean isDeckValid() {
        //جایگزین شود
        return true;
    }
}