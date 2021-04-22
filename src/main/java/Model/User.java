package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    public static HashMap<String, User> users;
    public static ArrayList<User> allUsers;
    private String username;
    private String password;
    private String nickname;
    private int coins;
    private int score;
    private int wins;
    private int losses;
    private ArrayList<Deck> decks;
    private Deck activeDeck;
    private ArrayList<Card> cards;

    static {
        users = new HashMap<>();
        allUsers = new ArrayList<>();
    }

    public User(String username, String nickname, String password) {
        setUsername(username);
        setNickname(nickname);
        setPassword(password);
        users.put(username,this);
        allUsers.add(this);
        this.cards = new ArrayList<>();
        this.addFirstCards(Card.getFirstCards());
    }

    public void addCard(Card card){
        this.cards.add(card);
    }

    public void addFirstCards(ArrayList<Card> firstCards){
        this.cards.addAll(firstCards);
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

    public void deleteCard(Card card){
        cards.remove(card);
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

    public static User getUserByUsername(String username) {
        return users.get(username);
    }

    public static boolean isUserWithThisUsernameExists(String username) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUserWithThisNicknameExists(String nickname) {
        for (User user : allUsers) {
            if (user.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    public void increaseScore() {
        score++;
    }

    public void increaseWins() {
        wins++;
    }

    public void increaseLosses() {
        losses++;
    }

    public void addDeck(Deck addingDeck) {

    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public void deleteDeck(Deck deletingDeck) {

    }

    public void setActiveDeck(Deck activatingDeck) {

    }

    public Deck getActiveDeck() {
        return activeDeck;
    }

    public Card getCardByName(String cardName) {
        //اینو گذاشتم که ارور نداشته باشه.جایگزین شه
        return null;
    }

    public Deck getDeckByName(String deckName) {
        //اینو گذاشتم که ارور نداشته باشه.جایگزین شه
        return null;
    }

    public int getCoins() {
        return coins;
    }

    public void increaseCoins(int coins) {

    }

    public void decreaseCoins(int coins) {

    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public String getNickname() {
        return nickname;
    }
}
