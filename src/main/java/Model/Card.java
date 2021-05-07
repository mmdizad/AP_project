package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Card {
    private static final ArrayList<Card> firstCards;
    public static ArrayList<Card> allCards;
    protected static HashMap<String, Card> cards;

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
    private int defensePower;
    private int attackPower;
    private int level;

    public Card(String name, String description, String cardType, int price, String category) {
        setName(name);
        setDescription(description);
        setCardType(cardType);
        setPrice(price);
        setCategory(category);
        if (getCardByName(name) == null) {
            cards.put(name, this);
            allCards.add(this);
        }
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public void setDefensePower(int defensePower) {
        this.defensePower = defensePower;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getDefensePower() {
        return defensePower;
    }

    public static void addFirstCards(ArrayList<Card> addFirstCards) {
        firstCards.addAll(addFirstCards);
        allCards.addAll(addFirstCards);
        for (Card firstCard : addFirstCards) {
            cards.put(firstCard.getName(), firstCard);
        }
    }

    public static ArrayList<Card> getNewFirstCard() {
        ArrayList<Card> newFirstCards = new ArrayList<>();
        for (int i = 1; i < firstCards.size(); i++) {
            Card card = new Card(firstCards.get(i).name, firstCards.get(i).description, firstCards.get(i).cardType, firstCards.get(i).price, firstCards.get(i).category);
            newFirstCards.add(card);
        }
        return newFirstCards;
    }
//روی این تابع یه فور بزن و یک کپی ازش بساز بفرس مثل پایین


    public static ArrayList<Card> getFirstCards() {
        return firstCards;
    }


    //این تابع رو با تابع گت کار بای نیم که توی دک زدی جایگزین کن رضا

    public static Card getNewCardBYName(String cardName) {
        Card card = cards.get(cardName);
        Card newCard = new Card(card.name, card.description, card.cardType, card.price, card.category);
        return newCard;
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