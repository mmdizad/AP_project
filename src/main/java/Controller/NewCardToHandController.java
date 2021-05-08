package Controller;

import Model.*;
import View.DrawPhaseView;

import java.util.ArrayList;
import java.util.concurrent.Callable;


public class NewCardToHandController extends DuelController {

    private static NewCardToHandController newCardToHandController = new NewCardToHandController();

    private NewCardToHandController() {

    }

    public static NewCardToHandController getInstance() {
        return newCardToHandController;
    }

    public ArrayList<Card> newCardToHand(String playerUsername, DuelModel duelModel) {
        User user = User.getUserByUsername(playerUsername);
        Deck deck = user.getActiveDeck();
        ArrayList<Card> cardsInDeck = deck.getCardsMain();
        if (cardsInDeck.size() >= 1) {
            //ما اینجا duelModel نول هست چک کنید
            for (int i = 1; i < 6; i++) {
                Card card = duelModel.getSpellAndTrap(1 - duelModel.turn, i);
                if (card != null) {
                    if (card.getName().equals("Time Seal") && duelModel.getSpellAndTrapCondition(1 - duelModel.turn, i).charAt(0) == 'O') {
                        duelModel.deleteSpellAndTrap(1 - duelModel.turn, i - 1);
                        duelModel.addCardToGraveyard(1 - duelModel.turn, card);
                        return null;
                    }
                }
            }
            return duelModel.addCardToHand();
        } else {
            return null;
            // جایگزین دارد
        }
    }

    public String hasHeraldOfCreation() {
        DrawPhaseView drawPhaseView = DrawPhaseView.getInstance();
        ArrayList<Card> monstersInFiled = duelModel.getMonstersInField().get(duelModel.turn);
        for (Card card : monstersInFiled) {
            if (card.getName().equals("Herald of Creation")) {
                String response = drawPhaseView.scanResponse();
                if (response.equals("yes"))
                return effectOfHeraldOfCreation();
            }
        }
        return "";
    }

    public String effectOfHeraldOfCreation() {
        DrawPhaseView drawPhaseView = DrawPhaseView.getInstance();
        int addressOfHandCard = drawPhaseView.scanNumberCardTributeForHeraldOfCreation();
        duelView.showGraveyardForSomeClasses();
        if (addressOfHandCard > duelModel.getHandCards().get(duelModel.turn).size()) {
            return "this address in hand is not correct";
        } else {
            int addressOfCardInGraveyard = drawPhaseView.scanPlaceOfCardForAddToHandFromGraveyard();
            if (addressOfCardInGraveyard > duelModel.getGraveyard(duelModel.turn).size()) {
                return "this address in graveyard is not correct";
            } else {
                Card card = duelModel.getGraveyard(duelModel.turn).get(addressOfCardInGraveyard - 1);
                if (!card.getCategory().equals("Monster")) {
                    return "you must select monster to add to your hand";
                } else {
                    Monster monster = (Monster) card;
                    if (monster.getLevel() < 7) {
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


}
