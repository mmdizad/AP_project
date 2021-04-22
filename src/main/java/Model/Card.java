package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Card {
    protected static HashMap<String, Card> cards;
    public static ArrayList<Card> shopCard;
    private static ArrayList<Card> firstCards;
    static {
        cards = new HashMap<>();
        shopCard = new ArrayList<>();
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
        shopCard.add(this);
    }
    public static void addFirstCards(ArrayList<Card> addFirstCards){
        firstCards.addAll(addFirstCards);
    }

    public static ArrayList<Card> getFirstCards() {
        return firstCards;
    }

    public static Card getCardByName(String cardName) {
        return cards.get(cardName);
    }

    public static ArrayList<Card> getShopCard() {
        return shopCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }
}