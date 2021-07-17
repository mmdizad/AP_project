package Controller;

import Model.Card;
import Model.DuelModel;
import Model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class BattlePhaseController {

    public ArrayList<Integer> attackedCards = new ArrayList<>();
    private static BattlePhaseController battlePhaseController;

    public static BattlePhaseController getInstance() {
        if (battlePhaseController == null) {
            battlePhaseController = new BattlePhaseController();
        }
        return battlePhaseController;
    }

    private BattlePhaseController() {

    }

    public String attack(String command) {
        if (!LoginAndSignUpController.loggedInUsers.containsKey(command.split("/")[2])) {
            return "wrong token!";
        }
        String cardPlace = command.split("/")[1];
        User user = LoginAndSignUpController.loggedInUsers.get(command.split("/")[2]);
        for (DuelModel duelModel : DuelController.duelModels) {
            if (duelModel.getUsernames().get(0).equals(user.getUsername()) ||
                    duelModel.getUsernames().get(1).equals(user.getUsername())) {
                int placeNumber = Integer.parseInt(cardPlace);
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
                                int ourCardFirstAttackPower = ourCard.getAttackPower();
                                attackedCards.add(Integer.parseInt(condition[2]));
                                Card opponentCard = monstersInField.get(1 - duelModel.turn).get(placeNumber - 1);
                                for (int i = 1; i < 6; i++) {
                                    Card card = duelModel.getSpellAndTrap(1 - duelModel.turn, i);
                                    String spellCondition = duelModel.getSpellAndTrapCondition(1 - duelModel.turn, i);
                                    if (card != null) {
                                        if (card.getName().equals("Magic Cylinder") && spellCondition.charAt(0) == 'O') {
                                            boolean ringOfDefenseExist = false;
                                            ArrayList<Card> ourSpells = duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn);
                                            for (int j = 0; j < 5; j++) {
                                                if (ourSpells.get(j) != null) {
                                                    if (ourSpells.get(j).getName().equals("Ring of Defense") && duelModel.getSpellAndTrapCondition(duelModel.turn,
                                                            j + 1).split("/")[0].charAt(0) == 'O') {
                                                        ringOfDefenseExist = true;
                                                    }
                                                }
                                            }
                                            if (!ringOfDefenseExist) {
                                                return trapMagicCylinder(ourCard, card, i, duelModel);
                                            }
                                        } else if (card.getName().equals("Mirror Force") && spellCondition.charAt(0) == 'O') {
                                            return trapMirrorFace(card, i, duelModel);
                                        } else if (card.getName().equals("Negate Attack") && spellCondition.charAt(0) == 'O') {
                                            return trapNegateAttack(card, i, duelModel);
                                        } else if (card.getName().equals("Swords of Revealing Light") && spellCondition.charAt(0) == 'O') {
                                            return ("opponent has active Swords of Revealing Light spell so you can't attack");
                                        } else if (card.getName().equals("Messenger of peace") && spellCondition.charAt(0) == 'O'
                                                && ourCard.getAttackPower() >= 1500) {
                                            return ("opponent has active Messenger of peace spell so you can't attack because your selected" +
                                                    " card's attack power is more than 1500");
                                        }
                                    }
                                }
                                if (opponentCard.getName().equals("Suijin") && !duelModel.getActivatedMonsterEffect(1 - duelModel.turn).contains(opponentCard)) {
                                    ourCard.setAttackPower(0);
                                    duelModel.addActivatedMonsterEffect(opponentCard, 1 - duelModel.turn);
                                }
//                                if (opponentCard.getName().equals("Texchanger") && !duelModel.getActivatedMonsterEffect(1 - duelModel.turn).contains(opponentCard)) {
//                                    ourCard.setAttackPower(0);
//                                    duelModel.addActivatedMonsterEffect(opponentCard, 1 - duelModel.turn);
//                                    BattlePhaseView battlePhaseView = BattlePhaseView.getInstance();
//                                    String cardName = battlePhaseView.getCyberseCard();
//                                    if (Card.getCardByName(cardName).getCardType().equals("Cyberse")) {
//                                        addCyberseCard(cardName);
//                                    }
//                                    return "opponent had Texchanger Monster and canceled the attack!";
//                                }
                                if (opponentCard.getName().equals("Command knight")) {
                                    for (Card card : duelModel.getMonstersInField().get(1 - duelModel.turn)) {
                                        if (card != null && !card.getName().equals("Command knight")) {
                                            return "opponent monster was Command knight and due to having another monsters in field, you cant attack";
                                        }
                                    }
                                }
                                if (duelModel.getMonsterCondition(1 - duelModel.turn, placeNumber).startsWith("OO")) {
                                    int ourCardAttack = 0;
                                    if (ourCard.getName().equals("The Calculator")) {
                                        for (int i = 0; i < 5; i++) {
                                            if (duelModel.getMonstersInField().get(duelModel.turn).get(i) != null
                                                    && duelModel.getMonsterCondition(duelModel.turn, i + 1).charAt(1) == 'O') {
                                                ourCardAttack += duelModel.getMonstersInField().get(duelModel.turn).get(i).getLevel() * 300;
                                            }
                                        }
                                    } else {
                                        ourCardAttack = ourCard.getAttackPower();
                                    }
                                    int opponentCardAttack = 0;
                                    if (opponentCard.getName().equals("The Calculator")) {
                                        for (int i = 0; i < 5; i++) {
                                            if (duelModel.getMonstersInField().get(1 - duelModel.turn).get(i) != null
                                                    && duelModel.getMonsterCondition(1 - duelModel.turn, i + 1).charAt(1) == 'O') {
                                                opponentCardAttack += duelModel.getMonstersInField().get(1 - duelModel.turn).get(i).getLevel() * 300;
                                            }
                                        }
                                    } else {
                                        opponentCardAttack = opponentCard.getAttackPower();
                                    }
                                    if (ourCardAttack > opponentCardAttack) {
                                        if (!opponentCard.getName().equals("Marshmallon")) {
                                            duelModel.addCardToGraveyard(1 - duelModel.turn, opponentCard);
                                            duelModel.deleteMonster(1 - duelModel.turn, placeNumber - 1);
                                        }
                                        int difference = ourCardAttack - opponentCardAttack;
                                        if (opponentCard.getName().equals("Yomi Ship")) {
                                            duelModel.addCardToGraveyard(duelModel.turn, ourCard);
                                            duelModel.deleteMonster(duelModel.turn, Integer.parseInt(condition[2]));
                                        }
                                        if (opponentCard.getName().equals("Exploder Dragon")) {
                                            duelModel.addCardToGraveyard(duelModel.turn, ourCard);
                                            duelModel.deleteMonster(duelModel.turn, Integer.parseInt(condition[2]));
                                            difference = 0;
                                        }
                                        duelModel.decreaseLifePoint(difference, 1 - duelModel.turn);
                                        duelModel.deSelectedCard();
                                        return "your opponent’s monster is destroyed and your opponent receives " + difference + "  battle damage";
                                    } else if (ourCardAttack == opponentCardAttack) {
                                        if (!opponentCard.getName().equals("Marshmallon")) {
                                            duelModel.addCardToGraveyard(1 - duelModel.turn, opponentCard);
                                            duelModel.deleteMonster(1 - duelModel.turn, placeNumber - 1);
                                        }
                                        if (!ourCard.getName().equals("Marshmallon")) {
                                            duelModel.addCardToGraveyard(duelModel.turn, ourCard);
                                            duelModel.deleteMonster(duelModel.turn, Integer.parseInt(condition[2]));
                                        }
                                        duelModel.deSelectedCard();
                                        return "both you and your opponent monster cards are destroyed and no one receives damage";
                                    } else {
                                        if (!ourCard.getName().equals("Marshmallon")) {
                                            duelModel.addCardToGraveyard(duelModel.turn, ourCard);
                                            duelModel.deleteMonster(duelModel.turn, Integer.parseInt(condition[2]));
                                        }
                                        duelModel.decreaseLifePoint(opponentCardAttack - ourCardAttack, duelModel.turn);
                                        int difference = opponentCardAttack - ourCardAttack;
                                        duelModel.deSelectedCard();
                                        if (opponentCard.getName().equals("Suijin")) {
                                            ourCard.setAttackPower(ourCardFirstAttackPower);
                                        }
                                        return "Your monster card is destroyed and you received " + difference + " battle damage";
                                    }
                                } else {
                                    int ourCardAttack = 0;
                                    if (ourCard.getName().equals("The Calculator")) {
                                        for (int i = 0; i < 5; i++) {
                                            if (duelModel.getMonstersInField().get(duelModel.turn).get(i) != null
                                                    && duelModel.getMonsterCondition(duelModel.turn, i + 1).charAt(1) == 'O') {
                                                ourCardAttack += duelModel.getMonstersInField().get(duelModel.turn).get(i).getLevel() * 300;
                                            }
                                        }
                                    } else {
                                        ourCardAttack = ourCard.getAttackPower();
                                    }
                                    int opponentCardDefense = opponentCard.getDefensePower();
                                    if (ourCardAttack > opponentCardDefense) {
                                        if (!opponentCard.getName().equals("Marshmallon")) {
                                            duelModel.addCardToGraveyard(1 - duelModel.turn, opponentCard);
                                            duelModel.deleteMonster(1 - duelModel.turn, placeNumber - 1);
                                        }
                                        if (opponentCard.getName().equals("Yomi Ship")) {
                                            duelModel.addCardToGraveyard(duelModel.turn, ourCard);
                                            duelModel.deleteMonster(duelModel.turn, Integer.parseInt(condition[2]));
                                        }
                                        if (opponentCard.getName().equals("Exploder Dragon")) {
                                            duelModel.addCardToGraveyard(duelModel.turn, ourCard);
                                            duelModel.deleteMonster(duelModel.turn, Integer.parseInt(condition[2]));
                                        }
                                        duelModel.deSelectedCard();
                                        if (duelModel.getMonsterCondition(1 - duelModel.turn, placeNumber).startsWith("DO")) {
                                            return "the defense position monster is destroyed";
                                        } else {
                                            duelModel.decreaseLifePoint(1000, duelModel.turn);
                                            return "opponent’s monster card was " + opponentCard.getName() +
                                                    " and the defense position monster is destroyed";
                                        }
                                    } else if (ourCardAttack == opponentCardDefense) {
                                        duelModel.deSelectedCard();
                                        if (duelModel.getMonsterCondition(1 - duelModel.turn, placeNumber).startsWith("DO")) {
                                            return "no card is destroyed";
                                        } else {
                                            duelModel.decreaseLifePoint(1000, duelModel.turn);
                                            return "opponent’s monster card was " + opponentCard.getName() + " and no card is destroyed";
                                        }
                                    } else {
                                        duelModel.decreaseLifePoint(opponentCardDefense - ourCardAttack, duelModel.turn);
                                        duelModel.deSelectedCard();
                                        int difference = opponentCardDefense - ourCardAttack;
                                        if (opponentCard.getName().equals("Suijin")) {
                                            ourCard.setAttackPower(ourCardFirstAttackPower);
                                        }
                                        if (duelModel.getMonsterCondition(1 - duelModel.turn, placeNumber).startsWith("DO")) {
                                            return "no card is destroyed and you received " + difference + " battle damage";
                                        } else {
                                            duelModel.decreaseLifePoint(1000, duelModel.turn);
                                            return "opponent’s monster card was " + opponentCard.getName() +
                                                    " and no card is destroyed and you received " + difference + 1000 + " battle damage";
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return "";
    }

    public String directAttack(String command) {
        if (!LoginAndSignUpController.loggedInUsers.containsKey(command.split("/")[1])) {
            return "wrong token!";
        }
        User user = LoginAndSignUpController.loggedInUsers.get(command.split("/")[1]);
        for (DuelModel duelModel : DuelController.duelModels) {
            if (duelModel.getUsernames().get(0).equals(user.getUsername()) ||
                    duelModel.getUsernames().get(1).equals(user.getUsername())) {
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
        }
        return "";
    }


    public String trapMagicCylinder(Card ourCard, Card trap, int place, DuelModel duelModel) {
        duelModel.decreaseLifePoint(ourCard.getAttackPower(), duelModel.turn);
        duelModel.addCardToGraveyard(1 - duelModel.turn, trap);
        duelModel.deleteSpellAndTrap(1 - duelModel.turn, place - 1);
        return "Opponent had Magic Cylinder Trap and canceled your attack and you took " + ourCard.getAttackPower() + " damage";
    }

    public String trapMirrorFace(Card trap, int place, DuelModel duelModel) {
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

    public String trapNegateAttack(Card trap, int place, DuelModel duelModel) {
        duelModel.addCardToGraveyard(1 - duelModel.turn, trap);
        duelModel.deleteSpellAndTrap(1 - duelModel.turn, place - 1);
        return "opponent had Negate Attack trap and canceled your attack and ended battle phase,enter the phase you want to go:";
    }
}
