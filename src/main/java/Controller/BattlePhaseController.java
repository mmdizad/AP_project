package Controller;

import Model.Card;
import Model.DuelModel;
import Model.Monster;
import View.BattlePhaseView;
import View.DuelView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BattlePhaseController extends DuelController {

    public ArrayList<Integer> attackedCards = new ArrayList<>();
    private final DuelModel duelModel = duelController.duelModel;

    private static BattlePhaseController battlePhaseController = new BattlePhaseController();

    private BattlePhaseController() {

    }

    public static BattlePhaseController getInstance() {
        return battlePhaseController;
    }

    public String attack(Matcher matcher) {
        try {
            LoginController.dataOutputStream.writeUTF("attack/" + matcher.group(1) + "/" + LoginController.token);
            LoginController.dataOutputStream.flush();
            return LoginController.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void addCyberseCard(String cardName) {
        Card card;
        ArrayList<Card> graveyard = duelModel.getGraveyard(1 - duelModel.turn);
        ArrayList<Card> hand = duelModel.getHandCards().get(1 - duelModel.turn);
        ArrayList<Card> deck = duelModel.getPlayersCards().get(1 - duelModel.turn);
        ArrayList<Card> monsters = duelModel.getMonstersInField().get(1 - duelModel.turn);
        for (Card card1 : graveyard) {
            if (card1.getName().equals(cardName)) {
                graveyard.remove(card1);
                if (duelModel.getMonstersInField().get(1 - duelModel.turn).get(0) == null) {
                    monsters.set(0, card1);
                    duelModel.addMonsterCondition(1 - duelModel.turn, 0, "OO/1");
                } else if (duelModel.getMonstersInField().get(1 - duelModel.turn).get(1) == null) {
                    monsters.set(1, card1);
                    duelModel.addMonsterCondition(1 - duelModel.turn, 1, "OO/2");
                } else if (duelModel.getMonstersInField().get(1 - duelModel.turn).get(2) == null) {
                    monsters.set(2, card1);
                    duelModel.addMonsterCondition(1 - duelModel.turn, 2, "OO/3");
                } else if (duelModel.getMonstersInField().get(1 - duelModel.turn).get(3) == null) {
                    monsters.set(3, card1);
                    duelModel.addMonsterCondition(1 - duelModel.turn, 3, "OO/4");
                } else if (duelModel.getMonstersInField().get(1 - duelModel.turn).get(4) == null) {
                    monsters.set(4, card1);
                    duelModel.addMonsterCondition(1 - duelModel.turn, 4, "OO/5");
                }
                return;
            }
        }
        for (Card card1 : deck) {
            if (card1.getName().equals(cardName)) {
                deck.remove(card1);
                if (duelModel.getMonstersInField().get(1 - duelModel.turn).get(0) == null) {
                    monsters.set(0, card1);
                    duelModel.addMonsterCondition(1 - duelModel.turn, 0, "OO/1");
                } else if (duelModel.getMonstersInField().get(1 - duelModel.turn).get(1) == null) {
                    monsters.set(1, card1);
                    duelModel.addMonsterCondition(1 - duelModel.turn, 1, "OO/2");
                } else if (duelModel.getMonstersInField().get(1 - duelModel.turn).get(2) == null) {
                    monsters.set(2, card1);
                    duelModel.addMonsterCondition(1 - duelModel.turn, 2, "OO/3");
                } else if (duelModel.getMonstersInField().get(1 - duelModel.turn).get(3) == null) {
                    monsters.set(3, card1);
                    duelModel.addMonsterCondition(1 - duelModel.turn, 3, "OO/4");
                } else if (duelModel.getMonstersInField().get(1 - duelModel.turn).get(4) == null) {
                    monsters.set(4, card1);
                    duelModel.addMonsterCondition(1 - duelModel.turn, 4, "OO/5");
                }
                return;
            }
        }
        for (Card card1 : hand) {
            if (card1.getName().equals(cardName)) {
                hand.remove(card1);
                if (duelModel.getMonstersInField().get(1 - duelModel.turn).get(0) == null) {
                    monsters.set(0, card1);
                    duelModel.addMonsterCondition(1 - duelModel.turn, 0, "OO/1");
                } else if (duelModel.getMonstersInField().get(1 - duelModel.turn).get(1) == null) {
                    monsters.set(1, card1);
                    duelModel.addMonsterCondition(1 - duelModel.turn, 1, "OO/2");
                } else if (duelModel.getMonstersInField().get(1 - duelModel.turn).get(2) == null) {
                    monsters.set(2, card1);
                    duelModel.addMonsterCondition(1 - duelModel.turn, 2, "OO/3");
                } else if (duelModel.getMonstersInField().get(1 - duelModel.turn).get(3) == null) {
                    monsters.set(3, card1);
                    duelModel.addMonsterCondition(1 - duelModel.turn, 3, "OO/4");
                } else if (duelModel.getMonstersInField().get(1 - duelModel.turn).get(4) == null) {
                    monsters.set(4, card1);
                    duelModel.addMonsterCondition(1 - duelModel.turn, 4, "OO/5");
                }
                return;
            }
        }
    }

    public String directAttack(Matcher matcher) {
        try {
            LoginController.dataOutputStream.writeUTF("direct attack/" + LoginController.token);
            LoginController.dataOutputStream.flush();
            return LoginController.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
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
                return "you cant activate this spell in this phase";
            } else if (detailOfSelectedCard[0].equals("My") && detailOfSelectedCard[1].equals("O")) {
                return "you have already activated this card";
            } else if (detailOfSelectedCard[0].equals("My") && detailOfSelectedCard[1].equals("H")) {
              //  return duelController.activateEffect(Integer.parseInt(detailOfSelectedCard[2]));
            }
            return "you cant active this card";
        }
    }

    public void setCommandsForAi() {
        MainPhaseController mainPhaseController = MainPhaseController.getInstance();
        int attackPower = 0;
        int place = 0;
        ArrayList<Card> ourMonsters = duelModel.getMonstersInField().get(duelModel.turn);
        for (int i = 0; i < 5; i++) {
            if (ourMonsters.get(i) != null) {
                if (ourMonsters.get(i).getAttackPower() > attackPower) {
                    attackPower = ourMonsters.get(i).getAttackPower();
                    place = i + 1;
                }
            }
        }
        if (place != 0) {
            String input = "select --monster " + place;
            Pattern pattern = Pattern.compile("^select --monster (\\d+)$");
            Matcher matcher = pattern.matcher(input);
            if (matcher.find()) {
                DuelController.getInstance().selectMonster(matcher);
            }
            ArrayList<Card> opponentMonsters = duelModel.getMonstersInField().get(1 - duelModel.turn);
            int opponentAttackPower = 100000;
            int opponentPlace = -1;
            for (int i = 0; i < 5; i++) {
                if (opponentMonsters.get(i) != null) {
                    if (opponentMonsters.get(i).getAttackPower() < opponentAttackPower) {
                        opponentPlace = i + 1;
                        opponentAttackPower = opponentMonsters.get(i).getAttackPower();
                    }
                }
            }
            if (opponentPlace == -1) {
                input = "attack direct";
                Pattern pattern1 = Pattern.compile("^attack direct$");
                Matcher matcher1 = pattern1.matcher(input);
                if (matcher1.find()) {
                    directAttack(matcher1);
                }
            } else {
                input = "attack " + opponentPlace;
                Pattern pattern1 = Pattern.compile("^attack (\\d+)$");
                Matcher matcher1 = pattern1.matcher(input);
                if (matcher1.find()) {
                    attack(matcher1);
                }
            }
        }
    }

    public String getCyberseCard() {
        ArrayList<Card> cards = duelModel.getPlayersCards().get(1 - duelModel.turn);
        for (Card card : cards) {
            if (card.getCategory().equals("Monster")) {
                if (card.getCardType().equals("Cyberse")) {
                    return card.getName();
                }
            }
        }
        return null;
    }
}