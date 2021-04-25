package Model;

import java.util.ArrayList;

public class DuelModel {
    private static ArrayList<ArrayList<Card>> playersCards;
    private static ArrayList<ArrayList<Card>> monstersInField;
    private static ArrayList<ArrayList<Card>> spellsAndTraps;
    private static ArrayList<ArrayList<String>> monsterCondition;
    private static ArrayList<ArrayList<String>> spellAndTrapCondition;
    private static ArrayList<ArrayList<Card>> graveyard;
    private static ArrayList<ArrayList<Card>> handCards;
    private static ArrayList<Card> field;
    private int lifePointUser = 8000;
    private int lifePointOpponent = 8000;
    private ArrayList<String> usernames;
    private static int turn = 0;

    static {
        playersCards = new ArrayList<>();
        monstersInField = new ArrayList<>();
        spellsAndTraps = new ArrayList<>();
        monsterCondition = new ArrayList<>();
        spellAndTrapCondition = new ArrayList<>();
        graveyard = new ArrayList<>();
        handCards = new ArrayList<>();
        field = new ArrayList<>();
    }

    public DuelModel(String playerUsername, String opponentUsername) {
        usernames = new ArrayList<>();
        usernames.add(playerUsername);
        usernames.add(opponentUsername);
        User user = User.getUserByUsername(playerUsername);
        Deck activeDeck = user.getActiveDeck();
        ArrayList<Card> cardsInPlayerActiveDeck = activeDeck.getCardsMain();
        for (Card card : cardsInPlayerActiveDeck) {
            playersCards.get(0).add(card);
        }
        User opponentUser = User.getUserByUsername(opponentUsername);
        Deck activeOpponentDeck = opponentUser.getActiveDeck();
        ArrayList<Card> cardsInOpponentActiveDeck = activeOpponentDeck.getCardsMain();
        for (Card card : cardsInOpponentActiveDeck) {
            playersCards.get(1).add(card);
        }
    }

    public void decreaseLifePoint(int lifePoint, int turn) {

    }

    public void increaseLifePoint(int lifePoint, int turn) {

    }

    public void addCardToHand() {
        if (handCards.size()<=turn) {
            ArrayList<Card> cardsInPlayerHand = handCards.get(turn);
            User user = User.getUserByUsername(usernames.get(turn));
            Deck deck = user.getActiveDeck();
            ArrayList<Card> cardsInDeck = deck.getCardsMain();

        }else {

        }
    }

    public void addMonsterFromHandToGame(int turn, String condition, Card card) {

    }

    public void addSpellAndTrapFromHandToGame(int turn, String condition, Card card) {

    }

    public void changeAttackAndDefense(int place) {

    }

    public void deleteMonster(int turn, int place) {

    }

    public void deleteSpellAndTrap(int turn, int place) {

    }

    public void activateSpell(int turn, int place) {

    }

    public void changeSetToFaceUp(int place) {

    }

    public String getGraveyard() {
        return null;
        //جایگزین شود
    }

    public Card getMonster(int turn, int place) {
        return null;
        //جایگزین شود
    }

    public Card getSpellAndTrap(int turn, int place) {
        return null;
        //جایگزین شود
    }

    public String getBoard() {
        return null;
        //جایگزین شود
    }

    public void changeUser() {

    }
}
