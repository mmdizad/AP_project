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
                    if (monstersInField.get(1 - duelModel.turn).get(placeNumber - 1) == null) {
                        return "there is no card to attack here";
                    } else {
                        attackedCards.add(Integer.parseInt(condition[2]));
                        for (int i = 1; i < 6; i++) {
                            Card card = duelModel.getSpellAndTrap(1 - duelModel.turn, i);
                            String spellCondition = duelModel.getSpellAndTrapCondition(1 - duelModel.turn, i);
                            if (card != null) {
                                if (card.getName().equals("Magic Cylinder") && spellCondition.charAt(0) == 'O') {
                                    return trapMagicCylinder(ourCard, card, i);
                                } else if (card.getName().equals("Mirror Force") && spellCondition.charAt(0) == 'O') {
                                    return trapMirrorFace(card, i);
                                } else if (card.getName().equals("Negate Attack") && spellCondition.charAt(0) == 'O') {
                                    return trapNegateAttack(card, i);
                                }
                            }
                        }
                        if (duelModel.getMonsterCondition(1 - duelModel.turn, placeNumber).startsWith("OO")) {
                            Card opponentCard = monstersInField.get(1 - duelModel.turn).get(placeNumber - 1);
                            int ourCardAttack = ourCard.getAttackPower();
                            int opponentCardAttack = opponentCard.getAttackPower();
                            if (ourCardAttack > opponentCardAttack) {
                                duelModel.addCardToGraveyard(1 - duelModel.turn, opponentCard);
                                duelModel.deleteMonster(1 - duelModel.turn, placeNumber - 1);
                                duelModel.decreaseLifePoint(ourCardAttack - opponentCardAttack, 1 - duelModel.turn);
                                int difference = ourCardAttack - opponentCardAttack;
                                duelModel.deSelectedCard();
                                return "your opponent’s monster is destroyed and your opponent receives " + difference + "  battle damage";
                            } else if (ourCardAttack == opponentCardAttack) {
                                duelModel.addCardToGraveyard(1 - duelModel.turn, opponentCard);
                                duelModel.deleteMonster(1 - duelModel.turn, placeNumber - 1);
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
                            Card opponentCard = monstersInField.get(1 - duelModel.turn).get(placeNumber - 1);
                            int ourCardAttack = ourCard.getAttackPower();
                            int opponentCardDefense = opponentCard.getDefensePower();
                            if (ourCardAttack > opponentCardDefense) {
                                duelModel.addCardToGraveyard(1 - duelModel.turn, opponentCard);
                                duelModel.deleteMonster(1 - duelModel.turn, placeNumber - 1);
                                duelModel.deSelectedCard();
                                if (duelModel.getMonsterCondition(1 - duelModel.turn, placeNumber).startsWith("DO")) {
                                    return "the defense position monster is destroyed";
                                } else {
                                    return "opponent’s monster card was " + opponentCard.getName() +
                                            " and the defense position monster is destroyed";
                                }
                            } else if (ourCardAttack == opponentCardDefense) {
                                duelModel.deSelectedCard();
                                if (duelModel.getMonsterCondition(1 - duelModel.turn, placeNumber).startsWith("DO")) {
                                    return "no card is destroyed";
                                } else {
                                    return "opponent’s monster card was " + opponentCard.getName() + " and no card is destroyed";
                                }
                            } else {
                                duelModel.decreaseLifePoint(opponentCardDefense - ourCardAttack, duelModel.turn);
                                duelModel.deSelectedCard();
                                int difference = opponentCardDefense - ourCardAttack;
                                if (duelModel.getMonsterCondition(1 - duelModel.turn, placeNumber).startsWith("DO")) {
                                    return "no card is destroyed and you received " + difference + " battle damage";
                                } else {
                                    return "opponent’s monster card was " + opponentCard.getName() +
                                            " and no card is destroyed and you received " + difference + " battle damage";
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
                        if (monstersInField.get(1 - duelModel.turn).get(i) != null) {
                            canAttack = false;
                        }
                    }
                    if (!canAttack) {
                        return "you can’t attack the opponent directly";
                    } else {
                        int ourCardAttack = ourCard.getAttackPower();
                        attackedCards.add(Integer.parseInt(condition[2]));
                        duelModel.decreaseLifePoint(ourCardAttack, 1 - duelModel.turn);
                        duelModel.deSelectedCard();
                        return "you opponent receives " + ourCardAttack + " battale damage";
                    }
                }
            }
        }
    }

    public String trapMagicCylinder(Card ourCard, Card trap, int place) {
        duelModel.decreaseLifePoint(ourCard.getAttackPower(), duelModel.turn);
        duelModel.addCardToGraveyard(1 - duelModel.turn, trap);
        duelModel.deleteSpellAndTrap(1 - duelModel.turn, place - 1);
        return "Opponent had Magic Cylinder Trap and canceled your attack and you took " + ourCard.getAttackPower() + " damage";
    }

    public String trapMirrorFace(Card trap, int place) {
        for (int i = 1; i < 6; i++) {
            String condition = duelModel.getMonsterCondition(duelModel.turn, i);
            if (condition != null) {
                if (condition.split("/")[0].equals("OO")) {
                    duelModel.deleteMonster(duelModel.turn, i - 1);
                }
            }
        }
        duelModel.addCardToGraveyard(1 - duelModel.turn, trap);
        duelModel.deleteSpellAndTrap(1 - duelModel.turn, place - 1);
        return "Opponent had Mirror Force trap and removed all of your attacking monsters";
    }

    public String trapNegateAttack(Card trap, int place) {
        duelModel.addCardToGraveyard(1 - duelModel.turn, trap);
        duelModel.deleteSpellAndTrap(1 - duelModel.turn, place - 1);
        return "opponent had Negate Attack trap and canceled your attack and ended battle phase,enter the phase you want to go:";
    }

    public String activateEffectBattlePhaseController() {
        if (duelModel.getSelectedCards().get(duelModel.turn).get(0) == null) {
            return "no card is selected yet";
        } else if (!duelModel.getSelectedCards().get(duelModel.turn).get(0).getCategory().equals("Spell")) {
            return "activate effect is only for spell cards.";
        } else {
            String[] detailOfSelectedCard = duelModel.getDetailOfSelectedCard().get(duelModel.turn)
                    .get(duelModel.getSelectedCards().get(duelModel.turn).get(0)).split("/");
            if (detailOfSelectedCard[0].equals("Hand")) {
                return "you cant active this card";
            }
            if (detailOfSelectedCard[0].equals("My") && detailOfSelectedCard[1].equals("Field")) {

            } else if (detailOfSelectedCard[0].equals("My") && detailOfSelectedCard[1].equals("O")) {
                return "you have already activated this card";
            } else if (detailOfSelectedCard[0].equals("My") && detailOfSelectedCard[1].equals("H")) {
                return duelController.activateEffect(Integer.parseInt(detailOfSelectedCard[2]));
            }
            return "you cant active this card";
        }

    }
}