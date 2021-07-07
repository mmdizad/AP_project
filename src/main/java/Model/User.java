package Model;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

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
    private String profileURL;
    private ArrayList<Deck> decks = new ArrayList<>();
    private Deck activeDeck;
    private ArrayList<Card> cards;


    public User(String username, String nickname, String password) {
        setUsername(username);
        setNickname(nickname);
        setPassword(password);
        users.put(username, this);
        allUsers.add(this);
        coins = 10000000;
        this.cards = new ArrayList<>();
        this.addFirstCards(Card.getFirstCards());
        Random random = new Random();
        int num =Math.abs( random.nextInt())% 18;
         num=num+7000;
        profileURL = "src/main/resource/Icons/" +num+ ".dds.png";

    }

    public static User getUserByUsername(String username) {
        try {
            File openingUser = new File(System.getProperty("user.home") + "/Desktop\\AP FILES\\Users\\" + username + "user.txt");
            if (!openingUser.exists()) {
                return null;
            } else {
                Gson gson = new Gson();
                StringBuilder getDetail = new StringBuilder();
                Scanner myReader = new Scanner(openingUser);
                while (myReader.hasNextLine()) {
                    getDetail.append(myReader.nextLine());
                }
                String userInfo = getDetail.toString();
                User user1 = gson.fromJson(userInfo, User.class);
                myReader.close();
                return user1;
            }
        } catch (IOException e) {
            return null;
        }
    }

    public static boolean isUserWithThisUsernameExists(String username) {
        ArrayList<User> users = new ArrayList<>();
        File folder = new File(System.getProperty("user.home") + "/Desktop\\AP FILES\\Users\\");
        File[] files = folder.listFiles();
        for (File file : files) {
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
            User user1 = gson.fromJson(userInfo, User.class);
            myReader.close();
            users.add(user1);
            if (user1.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUserWithThisNicknameExists(String nickname) {
        File folder = new File(System.getProperty("user.home") + "/Desktop\\AP FILES\\Users\\");
        File[] files = folder.listFiles();
        for (File file : files) {
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
            User user1 = gson.fromJson(userInfo, User.class);
            myReader.close();
            if (user1.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        File folder = new File(System.getProperty("user.home") + "/Desktop\\AP FILES\\Users\\");
        File[] files = folder.listFiles();
        for (File file : files) {
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
            User user1 = gson.fromJson(userInfo, User.class);
            myReader.close();
            users.add(user1);
        }
        return users;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
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
