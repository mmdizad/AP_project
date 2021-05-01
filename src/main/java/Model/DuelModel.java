package Model;

import java.util.ArrayList;

public class DuelModel {
    private ArrayList<ArrayList<Card>> selectedCards;
    private ArrayList<ArrayList<Card>> playersCards;
    private ArrayList<ArrayList<Card>> monstersInField;
    private ArrayList<ArrayList<Card>> spellsAndTraps;
    private ArrayList<ArrayList<String>> monsterCondition;
    private ArrayList<ArrayList<String>> spellAndTrapCondition;
    private ArrayList<ArrayList<Card>> graveyard;
    private ArrayList<ArrayList<Card>> handCards;
    private ArrayList<ArrayList<Card>> field;
    private int lifePointUser = 8000;
    private int lifePointOpponent = 8000;
    private ArrayList<String> usernames;
    public int turn = 0;
    //ما تو هر مرحله فقط میتونیم یک کارت ست یا سامان کنیم اینو برای اون زدم فقط یادتون باشه  اند فاز زدید اینو بکنید فالز
    public boolean moneterCardSetOrSummonThisTurn =false;

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
        ArrayList<String> monsterCondition1 = new ArrayList<>();
        ArrayList<String> monsterCondition2 = new ArrayList<>();
        monsterCondition.add(monsterCondition1);
        monsterCondition.add(monsterCondition2);
        spellAndTrapCondition = new ArrayList<>();
        ArrayList<String> spellAndTrapCondition1 = new ArrayList<>();
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

    public void decreaseLifePoint(int lifePoint, int turn) {

    }

    public void increaseLifePoint(int lifePoint, int turn) {

    }

    public ArrayList<ArrayList<Card>> getMonstersInField() {
        return monstersInField;
    }

    public ArrayList<ArrayList<Card>> getSpellsAndTraps() {
        return spellsAndTraps;
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
//این تابع تغییر دادم اینجا برای اینکه ما نیاز به ترن نداشتیم و همینجا داریمش و اینکه کارت هم از سلکت کارت ها میگیریم ولی برای اینکه ببینیم کجا قرارش بدیم یه عدد به عنوان هیندکس فرستادم
    public void addMonsterFromHandToGame( String condition,int index) {
         monstersInField.get(turn).add(index,selectedCards.get(turn).get(0));
         monsterCondition.get(turn).add(index,condition);
         selectedCards.get(turn).remove(0);
    }

    public void addSpellAndTrapFromHandToGame( String condition,int index) {
 spellsAndTraps.get(turn).add(index,selectedCards.get(turn).get(0));
 spellAndTrapCondition.get(turn).add(index,condition);
 selectedCards.get(turn).remove(0);
    }

    public void changeAttackAndDefense(int place) {

    }

    public Card getFieldZoneCard(int turn) {
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
        return monstersInField.get(turn).get(place - 1);
    }

    public Card getSpellAndTrap(int turn, int place) {
        return spellsAndTraps.get(turn).get(place - 1);
    }

    public void setSelectedCard(int turn, Card card) {
        if (selectedCards.get(turn).get(0) != null) {
            deSelectedCard();
        }
        selectedCards.get(turn).add(card);
    }

    public void deSelectedCard() {
        selectedCards.get(turn).remove(0);
    }

    public  ArrayList<ArrayList<Card>> getHandCards() {
        return handCards;
    }

    public ArrayList<ArrayList<Card>> getPlayersCards() {
        return playersCards;
    }

    public ArrayList<ArrayList<Card>> getSelectedCards() {
        return selectedCards;
    }

    public String getBoard() {
        return null;
        //جایگزین شود
    }

    public ArrayList<String> getUsernames() {
        return usernames;
    }

    public void changeUser() {
    }
}
