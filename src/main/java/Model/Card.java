package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Card {
    protected static HashMap<String, Card> cards;
    public static ArrayList<Card> allCards;
    private static final ArrayList<Card> firstCards;

    static {
        cards = new HashMap<>();
        allCards = new ArrayList<>();
        firstCards = new ArrayList<>();
    }

    protected String name;
    protected String description;
    protected int price;
    protected String category;
    protected String cardType;
    protected String cardID;

    public Card(String name, String description, String cardType, int price, String category) {
        setName(name);
        setDescription(description);
        setCardType(cardType);
        setPrice(price);
        setCategory(category);
        cards.put(name, this);
        allCards.add(this);
    }

    public static void addFirstCards(ArrayList<Card> addFirstCards) {
        firstCards.addAll(addFirstCards);
        allCards.addAll(addFirstCards);
        for (Card firstCard : addFirstCards) {
            cards.put(firstCard.getName(), firstCard);
        }
    }

    public static ArrayList<Card> getFirstCards() {
        return firstCards;
    }

    public static Card getCardByName(String cardName) {
        return cards.get(cardName);
    }

    public static ArrayList<Card> getAllCardsCard() {
        return allCards;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCardType() {
        return this.cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCardID() {
        return this.cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }
}