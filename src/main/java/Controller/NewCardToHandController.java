package Controller;

import Model.*;
import View.DrawPhaseView;
import View.DuelView;

import java.util.ArrayList;
import java.util.Comparator;


public class NewCardToHandController extends DuelController {

    private static NewCardToHandController newCardToHandController = null;
    static DuelView duelView = duelController.duelView;
    static DuelModel duelModel = duelController.duelModel;

    private NewCardToHandController() {

    }

    public static NewCardToHandController getInstance() {
        if (newCardToHandController == null)
            newCardToHandController = new NewCardToHandController();
        return newCardToHandController;
    }

    public ArrayList<Card> newCardToHand(String playerUsername) {
        TransitionOfCardToHand transitionOfCardToHand = TransitionOfCardToHand.getInstance();
        User user = User.getUserByUsername(playerUsername);
        assert user != null;
        Deck deck = user.getActiveDeck();
        ArrayList<Card> cardsInDeck = deck.getCardsMain();
        if (cardsInDeck.size() >= 1) {
            for (int i = 1; i < 6; i++) {
                duelController.deselect();
                Card card = duelController.duelModel.getSpellAndTrap(1 - duelController.duelModel.turn, i);
                if (card != null) {
                    if (card.getName().equals("Time Seal") && duelController.duelModel.getSpellAndTrapCondition(1 - duelModel.turn, i).charAt(0) == 'O') {
                        duelController.duelModel.deleteSpellAndTrap(1 - duelController.duelModel.turn, i - 1);
                        duelController.duelModel.addCardToGraveyard(1 - duelController.duelModel.turn, card);
                        return null;
                    }
                }
            }
            ArrayList<Card> cards = duelController.duelModel.addCardToHand();
            transitionOfCardToHand.transition(cards);
            return cards;
        } else {
            return null;
            // جایگزین دارد
        }
    }

    public String hasHeraldOfCreation() {
        DrawPhaseView drawPhaseView = DrawPhaseView.getInstance();
        DuelModel duelModel = duelController.duelModel;
        ArrayList<Card> monstersInFiled = duelModel.getMonstersInField().get(duelModel.turn);
        for (Card card : monstersInFiled) {
            if (card != null) {
                if (card.getName().equals("Herald of Creation")) {
                    String response;
                    if (!isAi) {
                        response = drawPhaseView.scanResponseForHeraldOfCreation();
                    } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                        response = drawPhaseView.scanResponseForHeraldOfCreation();
                    } else {
                        if (hasAiMonsterInGraveyardForHeraldOfCreation() && duelModel.getHandCards()
                                .get(duelModel.turn).size() >= 1) {
                            response = "yes";
                        } else {
                            response = "no";
                        }
                    }
                    if (response.equals("yes"))
                        return effectOfHeraldOfCreation();
                }
            }
        }
        return "";
    }

    public String hasScannerMonster() {
        DrawPhaseView drawPhaseView = DrawPhaseView.getInstance();
        DuelModel duelModel = duelController.duelModel;
        ArrayList<Card> monstersInFiled = duelModel.getMonstersInField().get(duelModel.turn);
        int i = 1;
        for (Card card : monstersInFiled) {
            if (card != null) {
                if (card.getName().equals("Scanner")) {
                    String response;
                    if (!isAi) {
                        response = drawPhaseView.scanResponseForScanner();
                    } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                        response = drawPhaseView.scanResponseForScanner();
                    } else {
                        if (hasOpponentMonsterInGraveyard()) {
                            response = "yes";
                        } else {
                            response = "no";
                        }
                    }
                    if (response.equals("yes"))
                        return effectOfScanner(i);
                }
            }
            i++;
        }
        return "";
    }

    public String effectOfHeraldOfCreation() {
        int addressOfHandCard;
        DrawPhaseView drawPhaseView = DrawPhaseView.getInstance();
        if (!isAi) {
            addressOfHandCard = drawPhaseView.scanNumberCardTributeForHeraldOfCreation();
            duelView.showGraveyardForSomeClasses(duelModel.turn);
        } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
            addressOfHandCard = drawPhaseView.scanNumberCardTributeForHeraldOfCreation();
            duelView.showGraveyardForSomeClasses(duelModel.turn);
        } else {
            addressOfHandCard = 1;
        }
        if (addressOfHandCard > duelModel.getHandCards().get(duelModel.turn).size()) {
            return "this address in hand is not correct";
        } else {
            int addressOfCardInGraveyard;
            if (!isAi) {
                addressOfCardInGraveyard = drawPhaseView.scanPlaceOfCardForAddToHandFromGraveyard();
            } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
                addressOfCardInGraveyard = drawPhaseView.scanPlaceOfCardForAddToHandFromGraveyard();
            } else {
                Card card = getBestMonsterFromGraveyard(duelModel.turn);
                addressOfCardInGraveyard = duelModel.getGraveyard(duelModel.turn).indexOf(card) + 1;
            }
            if (addressOfCardInGraveyard > duelModel.getGraveyard(duelModel.turn).size()) {
                return "this address in graveyard is not correct";
            } else {
                Card card = duelModel.getGraveyard(duelModel.turn).get(addressOfCardInGraveyard - 1);
                if (!card.getCategory().equals("Monster")) {
                    return "you must select monster to add to your hand";
                } else {
                    if (card.getLevel() < 7) {
                        return "you must select monster with level 7 or more";
                    } else {
                        duelModel.deleteCardFromHand(duelModel.getHandCards().get(duelModel.turn)
                                .get(addressOfHandCard - 1));
                        duelModel.deleteCardFromGraveyard(duelModel.turn, addressOfCardInGraveyard - 1);
                        duelModel.getHandCards().get(duelModel.turn).add(card);
                        return "card added to your hand";
                    }
                }
            }
        }
    }

    public String effectOfScanner(int placeOfScanner) {
        int addressOfCardInGraveyard;
        DrawPhaseView drawPhaseView = DrawPhaseView.getInstance();
        if (!isAi) {
            duelView.showGraveyardForSomeClasses(1 - duelModel.turn);
            addressOfCardInGraveyard = drawPhaseView.scanPlaceOfCardForInsteadOfScanner();
        } else if (!duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
            duelView.showGraveyardForSomeClasses(1 - duelModel.turn);
            addressOfCardInGraveyard = drawPhaseView.scanPlaceOfCardForInsteadOfScanner();
        } else {
            addressOfCardInGraveyard = duelModel.getGraveyard(1 - duelModel.turn)
                    .indexOf(getBestMonsterFromGraveyard(1 - duelModel.turn)) + 1;
        }
        if (addressOfCardInGraveyard > duelModel.getGraveyard(1 - duelModel.turn).size()) {
            return "this address in graveyard is not correct";
        } else {
            Card card = duelModel.getGraveyard(1 - duelModel.turn).get(addressOfCardInGraveyard - 1);
            if (!card.getCategory().equals("Monster")) {
                return "you must select monster to insteadOf Scanner";
            } else {
                duelModel.getMonstersInField().get(duelModel.turn).set(placeOfScanner - 1, card);
                duelModel.setCardsInsteadOfScanners(card, placeOfScanner);
                return "a monster card insteadOf Scanner";
            }
        }
    }

    public boolean hasOpponentMonsterInGraveyard() {
        boolean hasOpponentMonsterInGraveyardForAi = false;
        ArrayList<Card> graveyardCards = duelModel.getGraveyard(1 - duelModel.turn);
        for (Card card : graveyardCards) {
            if (card.getCategory().equals("Monster")) {
                hasOpponentMonsterInGraveyardForAi = true;
                break;
            }
        }
        return hasOpponentMonsterInGraveyardForAi;
    }

    public Card getBestMonsterFromGraveyard(int turn) {
        ArrayList<Card> monstersInGraveyard = new ArrayList<>();
        for (Card card : duelModel.getGraveyard(turn)) {
            if (card.getCategory().equals("Monster")) {
                monstersInGraveyard.add(card);
            }
        }
        Comparator<Card> compareForAiScanner = Comparator
                .comparing(Card::getLevel, Comparator.reverseOrder())
                .thenComparing(Card::getAttackPower, Comparator.reverseOrder())
                .thenComparing(Card::getDefensePower, Comparator.reverseOrder());
        monstersInGraveyard.sort(compareForAiScanner);
        return monstersInGraveyard.get(0);
    }

    public boolean hasAiMonsterInGraveyardForHeraldOfCreation() {
        boolean hasMonsterInGraveyardForHeraldOfCreation = false;
        ArrayList<Card> graveyardCards = duelModel.getGraveyard(duelModel.turn);
        for (Card card : graveyardCards) {
            if (card.getCategory().equals("Monster")) {
                if (card.getLevel() >= 7) {
                    hasMonsterInGraveyardForHeraldOfCreation = true;
                    break;
                }
            }
        }
        return hasMonsterInGraveyardForHeraldOfCreation;
    }
}
