package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class DuelModel {
    private ArrayList<ArrayList<Card>> selectedCards;
    private ArrayList<HashMap<Card, String>> detailOfSelectedCard;
    private ArrayList<ArrayList<Card>> playersCards;
    private ArrayList<ArrayList<Card>> monstersInField;
    private ArrayList<ArrayList<Card>> spellsAndTrapsInFiled;
    private ArrayList<ArrayList<String>> monsterCondition;
    private ArrayList<ArrayList<String>> spellAndTrapCondition;
    private ArrayList<ArrayList<Card>> graveyard;
    private ArrayList<ArrayList<Card>> handCards;
    private ArrayList<ArrayList<Card>> field;
    private int lifePointUser = 8000;
    private int lifePointOpponent = 8000;
    private ArrayList<String> usernames;
    public int turn = 0;
    public Card monsterSetOrSummonInThisTurn;
    public int thePlaceOfMonsterSetOrSummonInThisTurn;
    public boolean[] setposition = new boolean[5];

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
        for (int i = 0; i < 5; i++) {
            monstersInField1.add(null);
            monstersInField2.add(null);
        }
        monstersInField.add(monstersInField1);
        monstersInField.add(monstersInField2);
        spellsAndTrapsInFiled = new ArrayList<>();
        ArrayList<Card> spellsAndTraps1 = new ArrayList<>();
        ArrayList<Card> spellsAndTraps2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            spellsAndTraps1.add(null);
            spellsAndTraps2.add(null);
        }
        spellsAndTrapsInFiled.add(spellsAndTraps1);
        spellsAndTrapsInFiled.add(spellsAndTraps2);
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
        detailOfSelectedCard = new ArrayList<>();
        HashMap<Card, String> detailOfSelectedCard1 = new HashMap<>();
        HashMap<Card, String> detailOfSelectedCard2 = new HashMap<>();
        detailOfSelectedCard.add(detailOfSelectedCard1);
        detailOfSelectedCard.add(detailOfSelectedCard2);
    }

    public void decreaseLifePoint(int lifePoint, int turn) {
        if (turn == 1) {
            lifePointOpponent -= lifePoint;
        } else {
            lifePointUser -= lifePoint;
        }
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

    public void addMonsterFromHandToGame(String condition, int index) {
        monstersInField.get(turn).add(index, selectedCards.get(turn).get(0));
        monsterCondition.get(turn).add(index, condition);
        deSelectedCard();
        deleteCardFromHand(getMonster(turn, index + 1));
    }

    public void addSpellAndTrapFromHandToGame(String condition, int index) {
        spellsAndTrapsInFiled.get(turn).add(index, selectedCards.get(turn).get(0));
        spellAndTrapCondition.get(turn).add(index, condition);
        deSelectedCard();
        deleteCardFromHand(getSpellAndTrap(turn, index + 1));
    }

    public void changeAttackAndDefense(int place) {
        if (monsterCondition.get(turn).get(place) == "OO")
            monsterCondition.get(turn).set(place,"DO");
        else monsterCondition.get(turn).set(place, "OO");
    }

    public Card getFieldZoneCard(int turn) {
        return field.get(turn).get(0);
    }

    public void deleteMonster(int turn, int place) {
        monstersInField.get(turn).add(place, null);
    }

    public void deleteSpellAndTrap(int turn, int place) {
        spellsAndTrapsInFiled.get(turn).add(place, null);
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

    public String getMonsterCondition(int turn, int place) {
        return monsterCondition.get(turn).get(place - 1);
    }

    public Card getSpellAndTrap(int turn, int place) {
        return spellsAndTrapsInFiled.get(turn).get(place - 1);
    }

    public String getSpellAndTrapCondition(int turn, int place) {
        return spellAndTrapCondition.get(turn).get(place - 1);
    }

    public void addCardToGraveyard(int turn, Card card) {
        graveyard.get(turn).add(card);
    }

    public void setSelectedCard(int turn, Card card, String condition) {
        if (selectedCards.get(turn).get(0) != null) {
            deSelectedCard();
        }
        selectedCards.get(turn).add(card);
        detailOfSelectedCard.get(turn).put(card, condition);
    }


    public void deSelectedCard() {
        selectedCards.get(turn).remove(0);
        detailOfSelectedCard.get(turn).clear();
    }

    public ArrayList<ArrayList<Card>> getHandCards() {
        return handCards;
    }

    public ArrayList<ArrayList<Card>> getPlayersCards() {
        return playersCards;
    }

    public ArrayList<ArrayList<Card>> getSelectedCards() {
        return selectedCards;
    }

    public ArrayList<HashMap<Card, String>> getDetailOfSelectedCard() {
        return detailOfSelectedCard;
    }

    public ArrayList<String> getBoard() {
        ArrayList<String> board = new ArrayList<>();

        String handCardOpponent = "    ";
        String handCardUser = "    ";
        for (int i = 0; i < handCards.get(1 - turn).size(); i++) {
            handCardOpponent = handCardOpponent + "c    ";
        }
        for (int i = 0; i < handCards.get(turn).size(); i++) {
            handCardUser = handCardUser + "c    ";
        }


        ArrayList<String[]> spellConditionOpponent = new ArrayList<>();
        ArrayList<String[]> spellConditionUser = new ArrayList<>();
        ArrayList<String[]> conditionMonsterOpponent = new ArrayList<>();
        ArrayList<String[]> conditionMonsterUser = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            conditionMonsterOpponent.add(monsterCondition.get(1 - turn).get(i).split("/"));
            conditionMonsterUser.add(monsterCondition.get(turn).get(i).split("/"));
            spellConditionOpponent.add(spellAndTrapCondition.get(1 - turn).get(i).split("/"));
            spellConditionUser.add(spellAndTrapCondition.get(turn).get(i).split("/"));
        }

        String spellFieldofOpponet = "    " + spellConditionOpponent.get(4)[0] + "    " + spellConditionOpponent.get(2)[0] + "    " + spellConditionOpponent.get(1)[0] + "    " + spellConditionOpponent.get(3)[0] + "    " + spellConditionOpponent.get(5)[0];
        String spellFieldUser = "    " + spellConditionUser.get(5)[0] + "    " + spellConditionUser.get(3)[0] + "    " + spellConditionUser.get(1)[0] + "    " + spellConditionUser.get(2)[0] + "    " + spellConditionUser.get(4)[0];
        String monsterFieldUser = "    " + conditionMonsterUser.get(5)[0] + "    " + conditionMonsterUser.get(3)[0] + "    " + conditionMonsterUser.get(1)[0] + "    " + conditionMonsterUser.get(2)[0] + "    " + conditionMonsterUser.get(4)[0];
        String monsterFieldOpponent = "    " + conditionMonsterOpponent.get(4)[0] + "    " + conditionMonsterOpponent.get(2)[0] + "    " + conditionMonsterOpponent.get(1)[0] + "    " + conditionMonsterOpponent.get(3)[0] + "    " + conditionMonsterOpponent.get(5)[0];
        board.add(usernames.get(turn - 1) + ":" + lifePointOpponent);
        board.add(handCardOpponent);
        board.add(String.valueOf(playersCards.get(1 - turn).size()));
        board.add(spellFieldofOpponet);
        board.add(monsterFieldOpponent);
        if (field.get(1 - turn) == null)
            board.add(graveyard.get(1 - turn).size() + "                        " + "E");
        else board.add(graveyard.get(1 - turn).size() + "                        " + "O");
        board.add("----------------------------------------");
        if (field.get(turn) == null)
            board.add("E" + "                        " + graveyard.get(turn).size());
        else board.add("O" + "                        " + graveyard.get(1 - turn).size());
        board.add(monsterFieldUser);
        board.add(spellFieldUser);
        board.add(handCardUser);
        board.add(usernames.get(turn) + ":" + lifePointUser);
        return board;

    }

    public ArrayList<String> getUsernames() {
        return usernames;
    }

    public ArrayList<ArrayList<Card>> getMonstersInField() {
        return monstersInField;
    }

    public ArrayList<ArrayList<Card>> getSpellsAndTrapsInFiled() {
        return spellsAndTrapsInFiled;
    }

    public void setMonsterSetOrSummonInThisTurn(Card monsterSetOrSummonInThisTurn, int place) {
        this.monsterSetOrSummonInThisTurn = monsterSetOrSummonInThisTurn;
        this.thePlaceOfMonsterSetOrSummonInThisTurn = place;
    }

    public Card getMonsterSetOrSummonInThisTurn() {
        return monsterSetOrSummonInThisTurn;
    }

    public void deleteMonsterSetOrSummonInThisTurn() {
        monsterSetOrSummonInThisTurn = null;
    }

    public void changeUser() {
    }

    public void flipSummon(int place) {
        monsterCondition.get(turn).add(place, "OO/" + place + 1);
        deSelectedCard();
    }

    public void deleteCardFromHand(Card card) {
        handCards.get(turn).remove(card);
    }

    public void deleteCardFromHandWithIndex(int index) {
        handCards.get(turn).remove(index);
    }


    public void addMonsterFromGraveyardToGame(String condition, int index) {
        monstersInField.get(turn).add(index, selectedCards.get(turn).get(0));
        monsterCondition.get(turn).add(index, condition);
    }

    public void deleteCardFromGraveyard(int turn, int indexOfCard) {
        graveyard.get(turn).remove(indexOfCard);
    }

    public void addCardFromDeckToHandInMiddleOfGame(int turn, Card card) {
        handCards.get(turn).add(card);
        playersCards.get(turn).remove(card);
    }

}
