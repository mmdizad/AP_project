package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    public static HashMap<String, User> users;
    public static ArrayList<User> allUsers;

    static {
        users = new HashMap<>();
        allUsers = new ArrayList<>();
    }

    private String username;
    private String password;
    private String nickname;
    private int coins;
    private int score;
    private int wins;
    private int losses;
    private ArrayList<Deck> decks = new ArrayList<>();
    private Deck activeDeck;
    private ArrayList<Card> cards;

    public User(String username, String nickname, String password) {
        setUsername(username);
        setNickname(nickname);
        setPassword(password);
        users.put(username, this);
        allUsers.add(this);
        coins = 100000;
        this.cards = new ArrayList<>();
        this.addFirstCards(Card.getFirstCards());
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

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void addFirstCards(ArrayList<Card> firstCards) {
        this.cards.addAll(firstCards);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void deleteCard(Card card) {
        cards.remove(card);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void increaseScore() {
        score++;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score += score;
    }

    public void increaseWins() {
        wins++;
    }

    public void increaseLosses() {
        losses++;
    }

    public void addDeck(Deck addingDeck) {
        decks.add(addingDeck);
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public void deleteDeck(Deck deletingDeck) {
        decks.remove(deletingDeck);
    }

    public Deck getActiveDeck() {
        return activeDeck;
    }

    public void setActiveDeck(Deck activatingDeck) {
        activeDeck = activatingDeck;
    }

    public Card getCardByName(String cardName) {
        for (Card card : cards) {
            if (card.getName().equals(cardName)) {
                return card;
            }
        }
        return null;
    }

    public Deck getDeckByName(String deckName) {
        for (Deck deck : decks) {
            if (deck.getName().equals(deckName)) {
                return deck;
            }
        }
        return null;
    }

    public int getCoins() {
        return coins;
    }

    public void increaseCoins(int coins) {
        this.coins += coins;
    }

    public void decreaseCoins(int coins) {
        this.coins -= coins;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
