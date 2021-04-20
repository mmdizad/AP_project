package Model;

import java.util.HashMap;

public class Card {
    protected static HashMap<String, Card> cards;
    protected String name;
    protected String description;
    protected int price;
    protected String category;
    protected String cardType;
    protected String cardID;

    static {
        cards = new HashMap<>();

    }

    public Card(String name, String description, String cardType, int price, String category) {
        setName(name);
        setDescription(description);
        setCardType(cardType);
        setPrice(price);
        setCategory(category);
        cards.put(name, this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getDescription() {
        return description;
    }

    public String getCardType() {
        return cardType;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getCardID() {
        return cardID;
    }

    public static Card getCardByName(String cardName) {
        return null;
        //جایگزین شود
    }
}