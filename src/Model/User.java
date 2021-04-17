package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String username;
    private String password;
    private int coins;
    private int score;
    private int wins;
    private int losses;
    private ArrayList<Deck> decks;
    private Deck activeDeck;
    private ArrayList<Card> cards;
    public static HashMap<String,User> users;


    public User(String username,String password){


    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static User getUserByUsername(String username){
    return users.get(username);
    }

    public void increaseScore(){
        score++;
    }

    public void increaseWins(){
        wins++;
    }

    public void increaseLosses(){
        losses++;
    }

    public void addDeck(Deck addingDeck){

    }

    public ArrayList<Deck> getDecks(){
        return decks;
    }

    public void deleteDeck(Deck deletingDeck){

    }

    public void setActiveDeck(Deck activatingDeck){

    }

    public Deck getActiveDeck(){
        return activeDeck;
    }

    public Card getCardByName(String cardName){
        //اینو گذاشتم که ارور نداشته باشه.جایگزین شه
        return null;
    }

    public Deck getDeckByName(String deckName){
        //اینو گذاشتم که ارور نداشته باشه.جایگزین شه
        return null;
    }

    public int getCoins() {
        return coins;
    }

    public void increaseCoins(int coins){

    }

    public void decreaseCoins(int coins){

    }
}
