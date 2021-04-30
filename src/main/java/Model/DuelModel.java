package Model;

import java.util.ArrayList;

public class DuelModel {
    private static ArrayList<ArrayList<Card>> selectedCards;
    private static ArrayList<ArrayList<Card>> playersCards;
    private static ArrayList<ArrayList<Card>> monstersInField;
    private static ArrayList<ArrayList<Card>> spellsAndTraps;
    private static ArrayList<ArrayList<String>> monsterCondition;
    private static ArrayList<ArrayList<String>> spellAndTrapCondition;
    private static ArrayList<ArrayList<Card>> graveyard;
    private static ArrayList<ArrayList<Card>> handCards;
    private static ArrayList<ArrayList<Card>> field;
    private int lifePointUser = 8000;
    private int lifePointOpponent = 8000;
    private ArrayList<String> usernames;
    public int turn = 0;

    static {
        selectedCards = new ArrayList<>();
        ArrayList<Card> selectCard1 = new ArrayList<>();
        ArrayList<Card> selectCard2 = new ArrayList<>();
        selectedCards.add(selectCard1);
        selectedCards.add(selectCard2);
        playersCards = new ArrayList<>();
        ArrayList<Card> playerCard1 = new ArrayList<>();
        ArrayList<Card> playerCard2 = new ArrayList<>();
        playersCards.add(playerCard1);
        playersCards.add(playerCard2);
        monstersInField = new ArrayList<>();
        ArrayList<Card> monstersInField1 = new ArrayList<>();
        ArrayList<Card> monstersInField2 = new ArrayList<>();
        monstersInField.add(monstersInField1);
        monstersInField.add(monstersInField2);
        spellsAndTraps = new ArrayList<>();
        ArrayList<Card> spellsAndTraps1 = new ArrayList<>();
        ArrayList<Card> spellsAndTraps2 = new ArrayList<>();
        spellsAndTraps.add(spellsAndTraps1);
        spellsAndTraps.add(spellsAndTraps2);
        monsterCondition = new ArrayList<>();
        ArrayList<String> monsterCondition1  = new ArrayList<>();
        ArrayList<String> monsterCondition2 = new ArrayList<>();
        monsterCondition.add(monsterCondition1);
        monsterCondition.add(monsterCondition2);
        spellAndTrapCondition = new ArrayList<>();
        ArrayList<String> spellAndTrapCondition1  = new ArrayList<>();
        ArrayList<String> spellAndTrapCondition2 = new ArrayList<>();
        spellAndTrapCondition.add(spellAndTrapCondition1);
        spellAndTrapCondition.add(spellAndTrapCondition2);
        graveyard = new ArrayList<>();
        ArrayList<Card> graveyard1 = new ArrayList<>();
        ArrayList<Card> graveyard2 = new ArrayList<>();
        graveyard.add(graveyard1);
        graveyard.add(graveyard2);
        handCards = new ArrayList<>();
        field = new ArrayList<>();
        ArrayList<Card> field1 = new ArrayList<>();
        ArrayList<Card> field2 = new ArrayList<>();
        field.add(field1);
        field.add(field2);
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

    public ArrayList<Card> addCardToHand() {
        if (handCards.size() <= turn) {
            ArrayList<Card> handCardsPlayer = new ArrayList<>();
            handCards.add(handCardsPlayer);
            ArrayList<Card> firstCardsInPlayerHand = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                firstCardsInPlayerHand.add(playersCards.get(turn).get(playersCards.get(turn).size() - 1));
                playersCards.get(turn).remove(playersCards.get(turn).get(playersCards.get(turn).size() - 1));
            }
            handCards.get(turn).addAll(firstCardsInPlayerHand);
            return firstCardsInPlayerHand;
        } else {
            ArrayList<Card> cardAddedInPlayerHand = new ArrayList<>();
            handCards.get(turn).add(playersCards.get(turn).get(playersCards.get(turn).size() - 1));
            cardAddedInPlayerHand.add(playersCards.get(turn).get(playersCards.get(turn).size() - 1));
            playersCards.get(turn).remove(playersCards.get(turn).get(playersCards.get(turn).size() - 1));
            return cardAddedInPlayerHand;
        }
    }

    public void addMonsterFromHandToGame(int turn, String condition, Card card) {

    }

    public void addSpellAndTrapFromHandToGame(int turn, String condition, Card card) {

    }

    public void changeAttackAndDefense(int place) {

    }

    public Card getFieldZoneCard(int turn){
        return field.get(turn).get(0);
    }

    public void deleteMonster(int turn, int place) {

    }

    public void deleteSpellAndTrap(int turn, int place) {

    }

    public void activateSpell(int turn, int place) {

    }

    public void changeSetToFaceUp(int place) {

    }

    public ArrayList<Card> getGraveyard(int turn) {
        return graveyard.get(turn);
    }

    public Card getMonster(int turn, int place) {
        return monstersInField.get(turn).get(place-1);
    }

    public Card getSpellAndTrap(int turn, int place) {
        return spellsAndTraps.get(turn).get(place - 1);
    }

    public void setSelectedCard(int turn,Card card){
        if (selectedCards.get(turn).get(0) != null) {
            selectedCards.get(turn).remove(0);
        }
        selectedCards.get(turn).add(card);
    }

    public static ArrayList<ArrayList<Card>> getHandCards() {
        return handCards;
    }

    public static ArrayList<ArrayList<Card>> getPlayersCards() {
        return playersCards;
    }

    public String getBoard() {
        return null;
        //جایگزین شود
    }

    public void changeUser() {

    }
}
