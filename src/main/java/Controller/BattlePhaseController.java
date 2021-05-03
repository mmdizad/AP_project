package Controller;

import Model.Card;
import Model.Monster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class BattlePhaseController extends DuelController {

    public ArrayList<Integer> attackedCards = new ArrayList<>();

    private static BattlePhaseController battlePhaseController = new BattlePhaseController();

    private BattlePhaseController() {

    }

    public static BattlePhaseController getInstance() {
        return battlePhaseController;
    }

    public String attack(Matcher matcher) {
        int placeNumber = Integer.parseInt(matcher.group(1));
        ArrayList<ArrayList<Card>> selectedCards = duelModel.getSelectedCards();
        ArrayList<ArrayList<Card>> monstersInField = duelModel.getMonstersInField();
        if (selectedCards.get(duelModel.turn).get(0) == null) {
            return "no card is selected yet";
        } else {
            Card ourCard = selectedCards.get(duelModel.turn).get(0);
            ArrayList<HashMap<Card, String>> detailOfSelectedCards = duelModel.getDetailOfSelectedCard();
            String[] condition = detailOfSelectedCards.get(duelModel.turn).get(ourCard).split("/");
            if (!ourCard.getCategory().equals("Monster") || !condition[0].equals("My") || !condition[1].equals("OO")) {
                return "you can’t attack with this card";
            } else {
                if (attackedCards.contains(Integer.parseInt(condition[2]))) {
                    return "this card already attacked";
                } else {
                    if (monstersInField.get(duelModel.turn - 1).get(placeNumber - 1) == null) {
                        return "there is no card to attack here";
                    } else {
                        attackedCards.add(Integer.parseInt(condition[2]));
                        if (duelModel.getMonsterCondition(duelModel.turn - 1, placeNumber).startsWith("OO")) {
                            Card opponentCard = monstersInField.get(duelModel.turn - 1).get(placeNumber - 1);
                            int ourCardAttack = Monster.getMonsterByName(ourCard.getName()).getAttackPower();
                            int opponentCardAttack = Monster.getMonsterByName(opponentCard.getName()).getAttackPower();
                            if (ourCardAttack > opponentCardAttack) {
                                duelModel.addCardToGraveyard(duelModel.turn - 1, opponentCard);
                                duelModel.deleteMonster(duelModel.turn - 1, placeNumber - 1);
                                duelModel.decreaseLifePoint(ourCardAttack - opponentCardAttack, duelModel.turn - 1);
                                int difference = ourCardAttack - opponentCardAttack;
                                duelModel.deSelectedCard();
                                return "your opponent’s monster is destroyed and your opponent receives " + difference + "  battle damage";
                            } else if (ourCardAttack == opponentCardAttack) {
                                duelModel.addCardToGraveyard(duelModel.turn - 1, opponentCard);
                                duelModel.deleteMonster(duelModel.turn - 1, placeNumber - 1);
                                duelModel.addCardToGraveyard(duelModel.turn, ourCard);
                                duelModel.deleteMonster(duelModel.turn, Integer.parseInt(condition[2]));
                                duelModel.deSelectedCard();
                                return "both you and your opponent monster cards are destroyed and no one receives damage";
                            } else {
                                duelModel.addCardToGraveyard(duelModel.turn, ourCard);
                                duelModel.deleteMonster(duelModel.turn, Integer.parseInt(condition[2]));
                                duelModel.decreaseLifePoint(opponentCardAttack - ourCardAttack, duelModel.turn);
                                int difference = opponentCardAttack - ourCardAttack;
                                duelModel.deSelectedCard();
                                return "Your monster card is destroyed and you received " + difference + " battle damage";
                            }
                        } else {
                            Card opponentCard = monstersInField.get(duelModel.turn - 1).get(placeNumber - 1);
                            int ourCardAttack = Monster.getMonsterByName(ourCard.getName()).getAttackPower();
                            int opponentCardDefense = Monster.getMonsterByName(opponentCard.getName()).getDefensePower();
                            if (ourCardAttack > opponentCardDefense) {
                                duelModel.addCardToGraveyard(duelModel.turn - 1, opponentCard);
                                duelModel.deleteMonster(duelModel.turn - 1, placeNumber - 1);
                                duelModel.deSelectedCard();
                                if (duelModel.getMonsterCondition(duelModel.turn - 1, placeNumber).startsWith("DO")) {
                                    return "the defense position monster is destroyed";
                                } else {
                                    return "opponent’s monster card was " + opponentCard.getName() +
                                            " and the defense position monster is destroyed";
                                }
                            } else if (ourCardAttack == opponentCardDefense) {
                                duelModel.deSelectedCard();
                                if (duelModel.getMonsterCondition(duelModel.turn - 1, placeNumber).startsWith("DO")) {
                                    return "no card is destroyed";
                                } else {
                                    return "opponent’s monster card was " + opponentCard.getName() + " and no card is destroyed";
                                }
                            } else {
                                duelModel.decreaseLifePoint(opponentCardDefense - ourCardAttack, duelModel.turn);
                                duelModel.deSelectedCard();
                                if (duelModel.getMonsterCondition(duelModel.turn - 1, placeNumber).startsWith("DO")) {
                                    return "no card is destroyed and you received <damage> battle damage";
                                } else {
                                    return "opponent’s monster card was " + opponentCard.getName() +
                                            " and no card is destroyed and you received <damage> battle damage";
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public String directAttack(Matcher matcher) {
        ArrayList<ArrayList<Card>> selectedCards = duelModel.getSelectedCards();
        ArrayList<ArrayList<Card>> monstersInField = duelModel.getMonstersInField();
        if (selectedCards.get(duelModel.turn).get(0) == null) {
            return "no card is selected yet";
        } else {
            Card ourCard = selectedCards.get(duelModel.turn).get(0);
            ArrayList<HashMap<Card, String>> detailOfSelectedCards = duelModel.getDetailOfSelectedCard();
            String[] condition = detailOfSelectedCards.get(duelModel.turn).get(ourCard).split("/");
            if (!ourCard.getCategory().equals("Monster") || !condition[0].equals("My") || !condition[1].equals("OO")) {
                return "you can’t attack with this card";
            } else {
                if (attackedCards.contains(Integer.parseInt(condition[2]))) {
                    return "this card already attacked";
                } else {
                    boolean canAttack = true;
                    for (int i = 0; i < 5; i++) {
                        if (monstersInField.get(duelModel.turn - 1).get(i) != null) {
                            canAttack = false;
                        }
                    }
                    if (!canAttack) {
                        return "you can’t attack the opponent directly";
                    } else {
                        int ourCardAttack = Monster.getMonsterByName(ourCard.getName()).getAttackPower();
                        attackedCards.add(Integer.parseInt(condition[2]));
                        duelModel.decreaseLifePoint(ourCardAttack, duelModel.turn - 1);
                        duelModel.deSelectedCard();
                        return "you opponent receives " + ourCardAttack + " battale damage";
                    }
                }
            }
        }
    }
}