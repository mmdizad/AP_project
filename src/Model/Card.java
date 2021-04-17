package Model;

import java.util.HashMap;

public class Card {
    private String name;
    private String description;
    private int price;
    private String category;
    private String type;
    private String cardID;
    private HashMap<String,Card> cards;


    public Card(String name,String description,String type,int price,String category){

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

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
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

    public static Card getCardByName(String cardName){
        return null;
        //جایگزین شود
    }
}
