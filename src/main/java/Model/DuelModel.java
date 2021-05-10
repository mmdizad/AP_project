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
    private ArrayList<Card> borrowCards;
    private ArrayList<String> conditionOfBorrowCards;
    private ArrayList<HashMap<Card, Integer>> swordsCard;
    private ArrayList<ArrayList<Card>> supplySquadCards;
    private ArrayList<ArrayList<Card>> monsterDestroyedInThisTurn;
    private ArrayList<ArrayList<Card>> spellAbsorptionCards;
    private ArrayList<ArrayList<Card>> messengerOfPeace;
    private ArrayList<ArrayList<Card>> spellsAndTarpsSetInThisTurn;
    private ArrayList<ArrayList<Boolean>> spellZoneActivate;
    private ArrayList<ArrayList<Card>> activatedMonsterEffects;
    private HashMap<Card, Integer> cardsInsteadOfScanners;

    public DuelModel(String playerUsername, String opponentUsername) {
        lifePoints = new ArrayList<>();
        int lifePointUser = 8000;
        int lifePointOpponent = 8000;
        lifePoints.add(lifePointUser);
        lifePoints.add(lifePointOpponent);
        usernames = new ArrayList<>();
        usernames.add(playerUsername);
        usernames.add(opponentUsername);
        User user = User.getUserByUsername(playerUsername);
        Deck activeDeck = user.getActiveDeck();
        ArrayList<Card> cardsInPlayerActiveDeck = activeDeck.getCardsMain();
        playersCards = new ArrayList<>();
        ArrayList<Card> playerCard1 = new ArrayList<>();
        ArrayList<Card> playerCard2 = new ArrayList<>();
        playersCards.add(playerCard1);
        playersCards.add(playerCard2);
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
        field1.add(null);
        field1.add(null);
        ArrayList<Card> field2 = new ArrayList<>();
        field2.add(null);
        field2.add(null);
        field.add(field1);
        field.add(field2);
        detailOfSelectedCard = new ArrayList<>();
        HashMap<Card, String> detailOfSelectedCard1 = new HashMap<>();
        HashMap<Card, String> detailOfSelectedCard2 = new HashMap<>();
        detailOfSelectedCard.add(detailOfSelectedCard1);
        detailOfSelectedCard.add(detailOfSelectedCard2);
        borrowCards = new ArrayList<>();
        conditionOfBorrowCards = new ArrayList<>();
        swordsCard = new ArrayList<>();
        HashMap<Card, Integer> swordsCard1 = new HashMap<>();
        HashMap<Card, Integer> swordsCard2 = new HashMap<>();
        swordsCard.add(swordsCard1);
        swordsCard.add(swordsCard2);
        monsterDestroyedInThisTurn = new ArrayList<>();
        ArrayList<Card> monsterDestroyedInThisTurn1 = new ArrayList<>();
        ArrayList<Card> monsterDestroyedInThisTurn2 = new ArrayList<>();
        monsterDestroyedInThisTurn.add(monsterDestroyedInThisTurn1);
        monsterDestroyedInThisTurn.add(monsterDestroyedInThisTurn2);
        supplySquadCards = new ArrayList<>();
        ArrayList<Card> supplySquadCards1 = new ArrayList<>();
        ArrayList<Card> supplySquadCards2 = new ArrayList<>();
        supplySquadCards.add(supplySquadCards1);
        supplySquadCards.add(supplySquadCards2);
        spellAbsorptionCards = new ArrayList<>();
        ArrayList<Card> spellAbsorptionCards1 = new ArrayList<>();
        ArrayList<Card> spellAbsorptionCards2 = new ArrayList<>();
        spellAbsorptionCards.add(spellAbsorptionCards1);
        spellAbsorptionCards.add(spellAbsorptionCards2);
        messengerOfPeace = new ArrayList<>();
        ArrayList<Card> messengerOfPeace1 = new ArrayList<>();
        ArrayList<Card> messengerOfPeace2 = new ArrayList<>();
        messengerOfPeace.add(messengerOfPeace1);
        messengerOfPeace.add(messengerOfPeace2);
        spellsAndTarpsSetInThisTurn = new ArrayList<>();
        ArrayList<Card> spellsAndTarpsSetInThisTurn1 = new ArrayList<>();
        ArrayList<Card> spellsAndTarpsSetInThisTurn2 = new ArrayList<>();
        spellsAndTarpsSetInThisTurn.add(spellsAndTarpsSetInThisTurn1);
        spellsAndTarpsSetInThisTurn.add(spellsAndTarpsSetInThisTurn2);
        ArrayList<Card> activatedMonsterEffects1 = new ArrayList<>();
        ArrayList<Card> activatedMonsterEffects2 = new ArrayList<>();
        activatedMonsterEffects.add(activatedMonsterEffects1);
        activatedMonsterEffects.add(activatedMonsterEffects2);
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

    public void addBorrowCard(Card card, String condition) {
        borrowCards.add(card);
        conditionOfBorrowCards.add(condition);
    }

    public ArrayList<Card> getBorrowCards() {
        return borrowCards;
    }

    public ArrayList<String> getConditionOfBorrowCards() {
        return conditionOfBorrowCards;
    }

    public void deleteBorrowCard() {
        borrowCards.clear();
        conditionOfBorrowCards.clear();
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
        spellsAndTrapsInFiled.get(turn).set(index, selectedCards.get(turn).get(0));
        spellAndTrapCondition.get(turn).set(index, condition);
        deSelectedCard();
        deleteCardFromHand(getSpellAndTrap(turn, index + 1));
    }

    public void addMonsterCondition(int turn, int index, String condition) {
        monsterCondition.get(turn).set(index, condition);
    }

    public void changeAttackAndDefense(int place) {
        if (monsterCondition.get(turn).get(place).equals("OO"))
            monsterCondition.get(turn).set(place, "DO");
        else monsterCondition.get(turn).set(place, "OO");
    }

    public ArrayList<ArrayList<Card>> getField() {
        return field;
    }

    public Card getFieldZoneCard(int turn) {
        return field.get(turn).get(0);
    }

    public ArrayList<ArrayList<Boolean>> getSpellZoneActivate() {
        return spellZoneActivate;
    }

    public void deleteMonster(int turn, int place) {
        monstersInField.get(turn).set(place, null);
        monsterCondition.get(turn).set(place, "");
    }

    public void deleteSpellAndTrap(int turn, int place) {
        spellsAndTrapsInFiled.get(turn).set(place, null);
        spellAndTrapCondition.get(turn).set(place, "");
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


    public void changePositionOfSpellOrTrapCard(int turn, int place) {
        String[] condition = spellAndTrapCondition.get(turn).get(place - 1).split("/");
        if (condition[0].equals("H")) {
            spellAndTrapCondition.get(turn).add(place - 1, "O/" + place);
        }
    }
    public  void activeField(Card card){
        deletExitedField();
        field.get(turn).set(0,card);
    }

    public void deletExitedField() {
        field.get(turn).remove(0);
        field.get(1 - turn).remove(0);
        field.get(turn).add(selectedCards.get(turn).get(0));
        for (int i = 0; i < 5; i++) {
            Monster monster = (Monster) monstersInField.get(turn).get(i);
            Monster monster1 = (Monster) monstersInField.get(1 - turn).get(i);
            monster.setDefensePower(Card.getCardByName(monster.getName()).getDefensePower());
            monster.setAttackPower(Card.getCardByName(monster.getName()).getAttackPower());
            monster1.setAttackPower(Card.getCardByName(monster1.getName()).getAttackPower());
            monster1.setDefensePower(Card.getCardByName(monster1.getName()).getDefensePower());
            spellZoneActivate.get(turn).set(i,false);
            spellZoneActivate.get(1-turn).set(i,false);
        }

    }

    public void addCardToGraveyard(int turn, Card card) {
        if (card.getCategory().equals("Monster")) {
            //اینارو برای میدان زدم وقتی که کارت حذف میشه باید
            Monster monster = (Monster) card;
            monster.setAttackPower(Card.getCardByName(card.name).getAttackPower());
            monster.setDefensePower(Card.getCardByName(card.name).getDefensePower());
            monsterDestroyedInThisTurn.get(turn).add(card);
        }
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
        selectedCards.get(turn).set(0,null);
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
        board.add(usernames.get(turn - 1) + ":" + lifePoints.get(1 - turn));
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
        board.add(usernames.get(turn) + ":" + lifePoints.get(turn));
        return board;

    }

    public void setField(Card card) {
       field.get(turn).set(1,card);
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

    public void flipSummon(int place) {
        monsterCondition.get(turn).add(place, "OO/" + place + 1);
        deSelectedCard();
    }

    public void changeSpellAndTrapCondition(int turn, int place, String condition) {
        spellAndTrapCondition.get(turn).set(place - 1, condition);
    }

    public void deleteCardFromHand(Card card) {
        handCards.get(turn).remove(card);
    }

    public void deleteCardFromOpponentHand(Card card) {
        handCards.get(1 - turn).remove(card);
    }

    public void deleteCardFromHandWithIndex(int index) {
        handCards.get(turn).remove(index);
    }


    public void addMonsterFromGraveyardToGame(String condition, int index) {
        monstersInField.get(turn).add(index, selectedCards.get(turn).get(0));
        monsterCondition.get(turn).add(index, condition);
    }

    public void addCertainMonsterFromGraveyardToGame(int turn, String condition, int index, Card card) {
        monstersInField.get(turn).add(index, card);
        monsterCondition.get(turn).add(index, condition);
    }

    public void addCertainSpellOrTrapFromGraveyardToGame(String condition, int index, Card card) {
        monstersInField.get(turn).add(index, card);
        monsterCondition.get(turn).add(index, condition);
    }

    public void deleteCardFromGraveyard(int turn, int indexOfCard) {
        graveyard.get(turn).remove(indexOfCard);
    }

    public void addCardFromDeckToHandInMiddleOfGame(int turn, Card card) {
        handCards.get(turn).add(card);
        playersCards.get(turn).remove(card);
    }

    public void setSwordsCard(int turn, Card swordCard) {
        swordsCard.get(turn).put(swordCard, 0);
    }

    public void deleteSwordsCard(int turn, Card swordCard) {
        swordsCard.get(turn).remove(swordCard);
        int index = spellsAndTrapsInFiled.get(turn).indexOf(swordCard);
        deleteSpellAndTrap(turn, index);
        addCardToGraveyard(turn, swordCard);
    }

    public ArrayList<HashMap<Card, Integer>> getSwordsCard() {
        return swordsCard;
    }

    public ArrayList<ArrayList<Card>> getMonsterDestroyedInThisTurn() {
        return monsterDestroyedInThisTurn;
    }

    public void deleteMonstersDestroyedInThisTurn() {
        monsterDestroyedInThisTurn.clear();
    }

    public void setSupplySquad(int turn, Card card) {
        supplySquadCards.get(turn).add(card);
    }

    public ArrayList<ArrayList<Card>> getSupplySquadCards() {
        return supplySquadCards;
    }

    public void deleteSupplySquadCard(int turn, Card card) {
        supplySquadCards.get(turn).remove(card);
        int index = spellsAndTrapsInFiled.get(turn).indexOf(card);
        deleteSpellAndTrap(turn, index);
        addCardToGraveyard(turn, card);
    }

    public void setSpellAbsorptionCards(int turn, Card card) {
        spellAbsorptionCards.get(turn).add(card);
    }

    public void deleteSpellAbsorptionCards(int turn, Card card) {
        spellAbsorptionCards.get(turn).remove(card);
        int index = spellsAndTrapsInFiled.get(turn).indexOf(card);
        deleteSpellAndTrap(turn, index);
        addCardToGraveyard(turn, card);
    }

    public void effectOfSpellAbsorptionCards() {
        if (spellAbsorptionCards.get(turn).size() > 0) {
            increaseLifePoint(500 * spellAbsorptionCards.get(turn).size(), turn);
        } else if (spellAbsorptionCards.get(1 - turn).size() > 0) {
            increaseLifePoint(500 * spellAbsorptionCards.get(1 - turn).size(), 1 - turn);
        }
    }

    public void setMessengerOfPeace(int turn, Card card) {
        messengerOfPeace.get(turn).add(card);
    }

    public void deleteMessengerOfPeaceCards(int turn) {
        // maybe has a bug
        for (Card card : messengerOfPeace.get(turn)) {
            int index = spellsAndTrapsInFiled.get(turn).indexOf(card);
            deleteSpellAndTrap(turn, index);
            addCardToGraveyard(turn, card);
        }
        messengerOfPeace.get(turn).clear();
    }

    public ArrayList<ArrayList<Card>> getMessengerOfPeace() {
        return messengerOfPeace;
    }

    public void setSpellsAndTrapsSetInThisTurn(int turn, Card spellOrTrapCard) {
        spellsAndTarpsSetInThisTurn.get(turn).add(spellOrTrapCard);
    }

    public ArrayList<ArrayList<Card>> getSpellsAndTarpsSetInThisTurn() {
        return spellsAndTarpsSetInThisTurn;
    }

    public void deleteSpellAndTrapsSetInThisTurn() {
        spellsAndTarpsSetInThisTurn.clear();
    }

    public void addActivatedMonsterEffect(Card card, int turn) {
        activatedMonsterEffects.get(turn).add(card);
    }

    public ArrayList<Card> getActivatedMonsterEffect(int turn) {
        return activatedMonsterEffects.get(turn);
    }

    public void ritualSummon(String condition, int index, Card card) {
        monstersInField.get(turn).add(index, card);
        monsterCondition.get(turn).add(index, condition);
        deleteCardFromHand(getMonster(turn, index + 1));
    }

    public void deleteCardFromDeck(int turn, int index, Card card) {
        playersCards.get(turn).remove(index);
        addCardToGraveyard(turn, card);
    }

    public void setCardsInsteadOfScanners(Card card, int placeOfCard) {
        cardsInsteadOfScanners.put(card, placeOfCard);
    }

    public HashMap<Card, Integer> getCardsInsteadOfScanners() {
        return cardsInsteadOfScanners;
    }

    public void deleteCardsInsteadOfScanners() {
        cardsInsteadOfScanners.clear();
    }
}
