package Controller;

import Model.*;
import View.DuelView;
import View.MainPhaseView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;

public class DuelController {
    protected DuelModel duelModel;
    protected DuelView duelView;
    protected DuelController duelController;

    public void setDuelModel(DuelModel duelModel, DuelView duelView, DuelController duelController) {
        this.duelModel = duelModel;
        this.duelView = duelView;
        this.duelController = duelController;
    }

    public void selectFirstPlayer() {

    }

    public String deselect() {
        if (duelModel.getSelectedCards().get(duelModel.turn).get(0) == null) {
            return "no card is selected yet";
        } else {
            duelModel.deSelectedCard();
            return "card deselected";
        }
    }

    public String activateEffect(Matcher matcher) {
        return null;
    }

    public void isOpponentHasAnySpellOrTrapForActivate() {
        boolean hasAnySpellOrTrap = false;
        for (int i = 1; i <= 5; i++) {
            if (duelModel.getSpellAndTrap(1 - duelModel.turn, i) != null) {
                hasAnySpellOrTrap = true;
                break;
            }
        }
        duelView.opponentActiveEffect(hasAnySpellOrTrap);
    }

    public String opponentActiveSpellOrTrap() {
        Matcher matcher = duelView.scanCommandForActiveSpell();
        if (matcher.find()) {
            String response = selectSpellOrTrap(matcher);
            System.out.println(response);
            if (response.equals("card selected")) {
                String[] detailsOfSelectedCard = duelModel.getDetailOfSelectedCard().get(duelModel.turn).
                        get(duelModel.getSelectedCards().get(duelModel.turn).get(0)).split("/");
                String stateOfSelectedCard = detailsOfSelectedCard[1];
                if (stateOfSelectedCard.equals("O")) {
                    return "Card already Activated";
                } else {
                    return opponentActiveSpell(Integer.parseInt(matcher.group(1)));
                }
            } else {
                return opponentActiveTrap();
            }
        }
        return "it’s not your turn to play this kind of moves";
    }

    public String opponentActiveSpell(int placeOfSpell) {
        Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
        Spell spell = (Spell) card;
        if (spell.getName().equals("Monster Reborn"))
            return effectOfMonsterReborn(placeOfSpell);
        else if (spell.getName().equals("Terraforming"))
            return effectOfTerraforming(placeOfSpell);
        else if (spell.getName().equals("Pot of Greed"))
            return effectOfPotOfGreed(placeOfSpell);
        else if (spell.getName().equals("Raigeki"))
            return effectOfRaigeki(placeOfSpell);
        else if (spell.getName().equals("Change of Heart"))
            return effectOfChangeOfHeart(placeOfSpell);
        else if (spell.getName().equals("Harpie’s Feather Duster"))
            return effectOfHarpiesFeatherDuster(placeOfSpell);
        else if (spell.getName().equals("Swords of Revealing Light")) {
            return effectOfSwordsOfRevealingLight(placeOfSpell);
        } else if (spell.getName().equals("Dark Hole")) {
            return effectOfDarkHole(placeOfSpell);
        } else if (spell.getName().equals("Supply Squad")) {
            return effectOfSupplySquad(placeOfSpell);
        } else if (spell.getName().equals("Spell Absorption"))
            return effectOfSpellAbsorption(placeOfSpell);
        return "";
    }

    public String effectOfMonsterReborn(int placeOfSpell) {
        MainPhaseView mainPhaseView = MainPhaseView.getInstance();
        String kindOfGraveyard = duelView.scanKindOfGraveyardForActiveEffect();
        int numberOfCard = duelView.scanNumberOfCardForActiveEffect();
        if (!kindOfGraveyard.equals("My") && !kindOfGraveyard.equals("Opponent")) {
            return "you must enter correct state of card for summon";
        } else if (kindOfGraveyard.equals("My")) {
            if (numberOfCard > duelModel.getGraveyard(duelModel.turn).size()) {
                return "card with this number not available";
            } else if (!duelModel.getGraveyard(duelModel.turn).get(numberOfCard - 1).getCategory()
                    .equals("Monster")) {
                return "you cant summon this card";
            } else {
                String state = mainPhaseView.getStateOfCardForSummon();
                if (!state.equals("Defence") && !state.equals("Attack")) {
                    return "please enter the appropriate state (Defence or Attack)";
                } else {
                    String response = specialSummonMonsterOnFieldFromGraveyard(duelModel.turn
                            , state, numberOfCard - 1);
                    if (response.equals("activated and summoned successfully")) {
                        duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                        duelModel.effectOfSpellAbsorptionCards();
                        duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                        duelModel.addCardToGraveyard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                                .get(0));
                    }
                    return response;
                }
            }
        } else {
            if (numberOfCard > duelModel.getGraveyard(1 - duelModel.turn).size()) {
                return "card with this number not available";
            } else if (!duelModel.getGraveyard(1 - duelModel.turn).get(numberOfCard - 1).getCategory()
                    .equals("Monster")) {
                return "you cant summon this card";
            } else {
                String state = mainPhaseView.getStateOfCardForSummon();
                if (!state.equals("Defence") && !state.equals("Attack")) {
                    return "please enter the appropriate state (Defence or Attack)";
                } else {
                    String response = specialSummonMonsterOnFieldFromGraveyard(1 - duelModel.turn
                            , state, numberOfCard - 1);
                    if (response.equals("activated and summoned successfully")) {
                        duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                        duelModel.effectOfSpellAbsorptionCards();
                        duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                        duelModel.addCardToGraveyard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                                .get(0));
                    }
                    return response;
                }
            }
        }
    }

    public String effectOfTerraforming(int placeOfSpell) {
        Card card = null;
        ArrayList<Card> cardsInDeckOfPlayer = duelModel.getPlayersCards().get(duelModel.turn);
        for (Card card1 : cardsInDeckOfPlayer) {
            if (card1.getCategory().equals("Spell")) {
                if (card1.getCardType().equals("Field")) {
                    card = card1;
                    break;
                }
            }
        }
        if (card == null) {
            return "you dont have any FieldCard for add to your hand";
        } else {
            duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
            duelModel.effectOfSpellAbsorptionCards();
            duelModel.addCardFromDeckToHandInMiddleOfGame(duelModel.turn, card);
            duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
            duelModel.addCardToGraveyard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                    .get(0));
            return "spell activated";
        }

    }

    public String effectOfPotOfGreed(int placeOfSpell) {
        ArrayList<Card> cardsInDeckOfPlayer = duelModel.getPlayersCards().get(duelModel.turn);
        if (cardsInDeckOfPlayer.size() < 2) {
            return "you dont have enough card for add to your hand";
        } else {
            duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
            duelModel.effectOfSpellAbsorptionCards();
            duelModel.addCardFromDeckToHandInMiddleOfGame(duelModel.turn
                    , duelModel.getPlayersCards().get(duelModel.turn).get(duelModel.
                            getPlayersCards().get(duelModel.turn).size() - 1));
            duelModel.addCardFromDeckToHandInMiddleOfGame(duelModel.turn
                    , duelModel.getPlayersCards().get(duelModel.turn).get(duelModel.
                            getPlayersCards().get(duelModel.turn).size() - 1));
            duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
            duelModel.addCardToGraveyard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                    .get(0));
        }
        return "spell activated";
    }

    public String effectOfRaigeki(int placeOfSpell) {
        boolean opponentHasAnyMonster = false;
        ArrayList<Card> monstersInFieldOfPlayer = duelModel.getMonstersInField().get(1 - duelModel.turn);
        int i = 0;
        for (Card card : monstersInFieldOfPlayer) {
            if (card != null) {
                opponentHasAnyMonster = true;
                duelModel.deleteMonster(1 - duelModel.turn, i);
                duelModel.addCardToGraveyard(1 - duelModel.turn, card);
            }
            i++;
        }
        if (opponentHasAnyMonster) {
            duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
            duelModel.effectOfSpellAbsorptionCards();
            duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
            duelModel.addCardToGraveyard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                    .get(0));
            return "spell activated";
        } else {
            return "your opponent dont have any monster";
        }
    }

    public String effectOfChangeOfHeart(int placeOfSpell) {
        int numberOfMonsterCard = duelView.scanNumberOfCardForActiveEffect();
        if (numberOfMonsterCard > 5) {
            return "you cant get card with this address";
        } else if (duelModel.getMonstersInField().get(1 - duelModel.turn).get(numberOfMonsterCard - 1) == null) {
            return "you cant get card with this address";
        } else {
            Card borrowCard = duelModel.getMonstersInField().get(1 - duelModel.turn).get(numberOfMonsterCard - 1);
            String detailsOfBorrowCard = duelModel.getMonsterCondition(1 - duelModel.turn, numberOfMonsterCard);
            String[] details = detailsOfBorrowCard.split("/");
            String stateOfBorrowCard = details[0];
            int placeOfFieldThatBorrowCardSet;
            if (duelModel.getMonstersInField().get(duelModel.turn).get(0) == null) {
                duelModel.getMonstersInField().get(duelModel.turn).add(0, borrowCard);
                duelModel.addMonsterCondition(duelModel.turn, 0, stateOfBorrowCard + "/1");
                placeOfFieldThatBorrowCardSet = 1;
            } else if (duelModel.getMonstersInField().get(duelModel.turn).get(1) == null) {
                duelModel.getMonstersInField().get(duelModel.turn).add(1, borrowCard);
                duelModel.addMonsterCondition(duelModel.turn, 1, stateOfBorrowCard + "/2");
                placeOfFieldThatBorrowCardSet = 2;
            } else if (duelModel.getMonstersInField().get(duelModel.turn).get(2) == null) {
                duelModel.getMonstersInField().get(duelModel.turn).add(2, borrowCard);
                duelModel.addMonsterCondition(duelModel.turn, 2, stateOfBorrowCard + "/3");
                placeOfFieldThatBorrowCardSet = 3;
            } else if (duelModel.getMonstersInField().get(duelModel.turn).get(3) == null) {
                duelModel.getMonstersInField().get(duelModel.turn).add(3, borrowCard);
                duelModel.addMonsterCondition(duelModel.turn, 3, stateOfBorrowCard + "/4");
                placeOfFieldThatBorrowCardSet = 4;
            } else if (duelModel.getMonstersInField().get(duelModel.turn).get(4) == null) {
                duelModel.getMonstersInField().get(duelModel.turn).add(4, borrowCard);
                duelModel.addMonsterCondition(duelModel.turn, 4, stateOfBorrowCard + "/5");
                placeOfFieldThatBorrowCardSet = 5;
            } else {
                duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                duelModel.addCardToGraveyard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                        .get(0));
                return "monster card zone is full";
            }
            duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
            duelModel.effectOfSpellAbsorptionCards();
            duelModel.addBorrowCard(borrowCard, detailsOfBorrowCard + "/" + placeOfFieldThatBorrowCardSet);
            duelModel.deleteMonster(1 - duelModel.turn, numberOfMonsterCard - 1);
            duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
            duelModel.addCardToGraveyard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                    .get(0));
            return "spell activated";
        }
    }

    public void refundsTheBorrowCards() {
        ArrayList<Card> borrowCards = duelModel.getBorrowCards();
        ArrayList<String> conditionsOfBorrowCards = duelModel.getConditionOfBorrowCards();
        int i = 0;
        for (Card borrowCard : borrowCards) {
            String[] detailsOfBorrowCard = conditionsOfBorrowCards.get(i).split("/");
            String stateOfBorrowCard = detailsOfBorrowCard[0];
            int placeOfBorrowCard = Integer.parseInt(detailsOfBorrowCard[1]);
            int placeOfFieldThatBorrowCardSet = Integer.parseInt(detailsOfBorrowCard[2]);
            duelModel.deleteMonster(1 - duelModel.turn, placeOfFieldThatBorrowCardSet - 1);
            duelModel.getMonstersInField().get(duelModel.turn).add(placeOfBorrowCard - 1, borrowCard);
            duelModel.addMonsterCondition(duelModel.turn, placeOfBorrowCard - 1, stateOfBorrowCard
                    + "/" + placeOfBorrowCard);
            i++;
        }
        duelModel.deleteBorrowCard();
    }

    public String effectOfHarpiesFeatherDuster(int placeOfSpell) {
        ArrayList<Card> spellAndTraps = duelModel.getSpellsAndTrapsInFiled().get(1 - duelModel.turn);
        boolean hasSpellOrTrapCard = false;
        int i = 0;
        for (Card card : spellAndTraps) {
            if (card != null) {
                hasSpellOrTrapCard = true;
                if (card.getName().equals("Swords of Revealing Light")) {
                    duelModel.deleteSwordsCard(1 - duelModel.turn, card);
                } else if (card.getName().equals("Supply Squad")) {
                    duelModel.deleteSupplySquadCard(1 - duelModel.turn, card);
                } else if (card.getName().equals("Spell Absorption")) {
                    duelModel.deleteSpellAbsorptionCards(1 - duelModel.turn, card);
                } else {
                    duelModel.deleteSpellAndTrap(1 - duelModel.turn, i);
                    duelModel.addCardToGraveyard(1 - duelModel.turn, card);
                }
            }
            i++;
        }
        if (!hasSpellOrTrapCard) {
            return "dont have any spell or trap";
        } else {
            duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
            duelModel.effectOfSpellAbsorptionCards();
            duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
            duelModel.addCardToGraveyard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                    .get(0));
            return "spell activated";
        }
    }

    public String effectOfSwordsOfRevealingLight(int placeOfSpell) {
        // not complete it needs a change in battlePhase
        duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
        duelModel.effectOfSpellAbsorptionCards();
        duelModel.setSwordsCard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn).get(0));
        changeStateOfMonsterWithSwordsCard(1 - duelModel.turn);
        return "spell activated";
    }

    public void changeStateOfMonsterWithSwordsCard(int turn) {
        ArrayList<Card> monstersInField = duelModel.getMonstersInField().get(turn);
        int i = 1;
        for (Card card : monstersInField) {
            if (card != null) {
                String[] detailsOfMonsterCard = duelModel.getMonsterCondition(turn, i)
                        .split("/");
                if (detailsOfMonsterCard[0].equals("DH")) {
                    duelModel.addMonsterCondition(turn, i - 1, "DO" + i);
                }
            }
            i++;
        }
    }

    public String effectOfDarkHole(int placeOfSpell) {
        boolean opponentHasAnyMonster = false;
        boolean youHaveAnyMonster = false;
        ArrayList<Card> monstersInField1 = duelModel.getMonstersInField().get(1 - duelModel.turn);
        int i = 0;
        for (Card card : monstersInField1) {
            if (card != null) {
                opponentHasAnyMonster = true;
                duelModel.deleteMonster(1 - duelModel.turn, i);
                duelModel.addCardToGraveyard(1 - duelModel.turn, card);
            }
            i++;
        }
        ArrayList<Card> monstersInField2 = duelModel.getMonstersInField().get(duelModel.turn);
        i = 0;
        for (Card card : monstersInField2) {
            if (card != null) {
                youHaveAnyMonster = true;
                duelModel.deleteMonster(duelModel.turn, i);
                duelModel.addCardToGraveyard(duelModel.turn, card);
            }
            i++;
        }
        if (opponentHasAnyMonster || youHaveAnyMonster) {
            duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
            duelModel.effectOfSpellAbsorptionCards();
            duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
            duelModel.addCardToGraveyard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                    .get(0));
            return "spell activated";
        } else {
            return "you and your opponent dont have any monsters";
        }
    }

    public String effectOfSupplySquad(int placeOfSpell) {
        duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
        duelModel.effectOfSpellAbsorptionCards();
        duelModel.setSupplySquad(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                .get(0));
        return "spell activated";
    }

    public String effectOfSpellAbsorption(int placeOfSpell) {
        duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
        duelModel.effectOfSpellAbsorptionCards();
        duelModel.setSpellAbsorptionCards(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                .get(0));
        return "spell activated";
    }

    public String effectOfMagnumShield(int placeOfSpellInField) {
        boolean isWarriorExist = false;
        ArrayList<Integer> placeOfWarriorCard = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Card card = duelModel.getMonstersInField().get(duelModel.turn).get(i);
            if (card != null)
                //check it
                if (card.getCategory().equals("Warrior")) {
                    if (duelModel.getMonsterCondition(duelModel.turn, i).equals("DO") || duelModel.getMonsterCondition(duelModel.turn, i).equals("OO"))
                        isWarriorExist = true;
                    placeOfWarriorCard.add(i);
                }
        }
        if (isWarriorExist) {
            int place = duelView.scanForChoseMonsterForEquip(placeOfWarriorCard);
            //فرض میکنیم که عدد درست وارد شده
            Monster monster = (Monster) duelModel.getMonstersInField().get(duelModel.turn).get(place);
            if (duelModel.getMonsterCondition(duelModel.turn, place).startsWith("D")) {
                monster.setAttackPower(monster.getAttackPower() + monster.getDefensePower());

                duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).set(placeOfSpellInField, duelModel.getSelectedCards().get(duelModel.turn).get(0));
                //پر شود


                return "spell activated";
            } else {
                monster.setDefensePower(monster.getAttackPower() + monster.getDefensePower());

                return "spell activated";

            }
        } else return "you don't have any Warrior monster to equip ";
    }

    public String effectOfUmiiruka() {
        duelModel.setField(duelModel.getSelectedCards().get(duelModel.turn).get(0));
        duelModel.getHandCards().get(duelModel.turn).remove(duelModel.getSelectedCards().get(duelModel.turn).get(0));
        for (int i = 0; i < 5; i++) {
            Monster monster = (Monster) duelModel.getMonstersInField().get(duelModel.turn).get(i);
            Monster monster1 = (Monster) duelModel.getMonstersInField().get(duelModel.turn).get(i);
            if (monster != null) {
                monster.setDefensePower(monster.getDefensePower() - 400);
                monster.setAttackPower(monster.getAttackPower() + 500);
            }
            if (monster1 != null) {
                monster1.setDefensePower(monster1.getDefensePower() - 400);
                monster1.setAttackPower(monster1.getAttackPower() + 500);
            }
        }
        return "spell activated";
    }

    public String effectOfClosedForest() {
        duelModel.getHandCards().get(duelModel.turn).remove(duelModel.getSelectedCards().get(duelModel.turn).get(0));
        duelModel.setField(duelModel.getSelectedCards().get(duelModel.turn).get(0));
        for (int i = 0; i < 5; i++) {
            Monster monster = (Monster) duelModel.getMonstersInField().get(duelModel.turn).get(i);
            if (monster != null)
                monster.setAttackPower(monster.getAttackPower() + duelModel.getGraveyard(duelModel.turn).size() * 100);
        }
        deselect();
        return "spell activated";
    }

    public String specialSummonMonsterOnFieldFromGraveyard(int turn, String state, int indexOfCardOfGraveyard) {
        String stateOfCard = "OO";
        if (state.equals("Attack")) {
            stateOfCard = "OO";
        } else if (state.equals("Defence")) {
            stateOfCard = "DO";
        }
        if (duelModel.getMonstersInField().get(duelModel.turn).get(0) == null) {
            duelModel.addMonsterFromGraveyardToGame(stateOfCard + "/1", 0);
            duelModel.deleteCardFromGraveyard(turn, indexOfCardOfGraveyard);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(1) == null) {
            duelModel.addMonsterFromGraveyardToGame(stateOfCard + "/2", 1);
            duelModel.deleteCardFromGraveyard(turn, indexOfCardOfGraveyard);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(2) == null) {
            duelModel.addMonsterFromGraveyardToGame(stateOfCard + "/3", 2);
            duelModel.deleteCardFromGraveyard(turn, indexOfCardOfGraveyard);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(3) == null) {
            duelModel.addMonsterFromGraveyardToGame(stateOfCard + "/4", 3);
            duelModel.deleteCardFromGraveyard(turn, indexOfCardOfGraveyard);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(4) == null) {
            duelModel.addMonsterFromGraveyardToGame(stateOfCard + "/5", 4);
            duelModel.deleteCardFromGraveyard(turn, indexOfCardOfGraveyard);
        } else {
            return "monster card zone is full";
        }
        return "activated and summoned successfully";
    }

    public String opponentActiveTrap() {
        return null;
    }


    public ArrayList<String> showGraveYard() {
        ArrayList<Card> graveyardCards = duelModel.getGraveyard(0);
        ArrayList<String> output = new ArrayList<>();
        for (int i = 0; i < graveyardCards.size(); i++) {
            output.add(i + 1 + ". " + graveyardCards.get(i).getName() + ": " + graveyardCards.get(i).getDescription());
        }
        if (graveyardCards.size() == 0) {
            output.add("graveyard empty");
        }
        return output;
    }

    public ArrayList<String> checkCard(Matcher matcher) {
        ArrayList<String> output = new ArrayList<>();
        if (Card.getCardByName(matcher.group(1)) == null) {
            output.add("card with name " + matcher.group(1) + " does not exist");
        } else {
            Card card = Card.getCardByName(matcher.group(1));
            if (card.getCategory().equals("Monster")) {
                output = showMonster(matcher.group(1));
            } else if (card.getCategory().equals("Spell")) {
                output = showSpell(matcher.group(1));
            } else {
                output = showTrap(matcher.group(1));
            }
        }
        return output;
    }

    public ArrayList<String> checkSelectedCard(Matcher matcher) {
        ArrayList<String> output = new ArrayList<>();
        ArrayList<ArrayList<Card>> selectedCards = duelModel.getSelectedCards();
        ArrayList<HashMap<Card, String>> detailsOfSelectedCards = duelModel.getDetailOfSelectedCard();
        if (selectedCards.get(duelModel.turn).get(0) == null) {
            output.add("no card is selected yet");
        } else {
            Card card = selectedCards.get(duelModel.turn).get(0);
            if (detailsOfSelectedCards.get(duelModel.turn).get(card).equals("Opponent/H")) {
                output.add("card is not visible");
            } else {
                if (card.getCategory().equals("Monster")) {
                    output = showMonster(card.getName());
                } else if (card.getCategory().equals("Spell")) {
                    output = showSpell(card.getName());
                } else {
                    output = showTrap(card.getName());
                }
            }
        }
        return output;
    }

    private ArrayList<String> showMonster(String cardName) {
        Monster monster = Monster.getMonsterByName(cardName);
        ArrayList<String> output = new ArrayList<>();
        output.add("Name: " + monster.getName());
        output.add("Level: " + monster.getLevel());
        output.add("Type: " + monster.getMonsterType());
        output.add("ATK: " + monster.getAttackPower());
        output.add("DEF: " + monster.getDefensePower());
        output.add("Description: " + monster.getDescription());
        return output;
    }

    private ArrayList<String> showSpell(String cardName) {
        Spell spell = Spell.getSpellByName(cardName);
        ArrayList<String> output = new ArrayList<>();
        output.add("Name: " + spell.getName());
        output.add("Spell");
        output.add("Type: " + spell.getCardType());
        output.add("Description: " + spell.getDescription());
        return output;
    }

    private ArrayList<String> showTrap(String cardName) {
        Trap trap = Trap.getTrapByName(cardName);
        ArrayList<String> output = new ArrayList<>();
        output.add("Name: " + trap.getName());
        output.add("Trap");
        output.add("Type: " + trap.getCardType());
        output.add("Description: " + trap.getDescription());
        return output;
    }

    public String surrender() {
        return null;
    }

    public String selectMonster(Matcher matcher) {
        if (duelModel.getMonster(duelModel.turn, Integer.parseInt(matcher.group(1))) == null) {
            return "no card found in the given position";
        } else {
            String condition = "My/";
            condition = condition + duelModel.getMonsterCondition(duelModel.turn, Integer.parseInt(matcher.group(1)));
            duelModel.setSelectedCard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                    Integer.parseInt(matcher.group(1))), condition);
            return "card selected";
        }
    }


    public String selectOpponentMonster(Matcher matcher) {
        if (duelModel.getMonster(duelModel.turn - 1, Integer.parseInt(matcher.group(1))) == null) {
            return "no card found in the given position";
        } else {
            String condition = "Opponent/";
            condition = condition + duelModel.getMonsterCondition(duelModel.turn - 1, Integer.parseInt(matcher.group(1)));
            duelModel.setSelectedCard(duelModel.turn, duelModel.getMonster(duelModel.turn - 1,
                    Integer.parseInt(matcher.group(1))), condition);
            return "card selected";
        }
    }


    public String selectSpellOrTrap(Matcher matcher) {
        if (duelModel.getSpellAndTrap(duelModel.turn, Integer.parseInt(matcher.group(1))) == null) {
            return "no card found in the given position";
        } else {
            String condition = "My/";
            condition = condition + duelModel.getMonsterCondition(duelModel.turn, Integer.parseInt(matcher.group(1)));
            duelModel.setSelectedCard(duelModel.turn, duelModel.getSpellAndTrap(duelModel.turn,
                    Integer.parseInt(matcher.group(1))), condition);
            return "card selected";
        }
    }

    public String selectOpponentSpellOrTrap(Matcher matcher) {
        if (duelModel.getSpellAndTrap(duelModel.turn - 1, Integer.parseInt(matcher.group(1))) == null) {
            return "no card found in the given position";
        } else {
            String condition = "Opponent/";
            condition = condition + duelModel.getMonsterCondition(duelModel.turn - 1, Integer.parseInt(matcher.group(1)));
            duelModel.setSelectedCard(duelModel.turn, duelModel.getSpellAndTrap(duelModel.turn - 1,
                    Integer.parseInt(matcher.group(1))), condition);
            return "card selected";
        }
    }

    public String selectFieldZone() {
        if (duelModel.getFieldZoneCard(duelModel.turn) == null) {
            return "no card found in the given position";
        } else {
            duelModel.setSelectedCard(duelModel.turn, duelModel.getFieldZoneCard(duelModel.turn), "My/Filed");
            return "card selected";
        }
    }


    public String selectOpponentFieldZone() {
        if (duelModel.getFieldZoneCard(duelModel.turn - 1) == null) {
            return "no card found in the given position";
        } else {
            duelModel.setSelectedCard(duelModel.turn, duelModel.getFieldZoneCard(duelModel.turn - 1), "Opponent/Field");
            return "card selected";
        }
    }


    public String selectHand(Matcher matcher) {
        if (duelModel.getHandCards().get(duelModel.turn).size() < Integer.parseInt(matcher.group(1))) {
            return "invalid selection";
        } else {
            duelModel.setSelectedCard(duelModel.turn, duelModel.getHandCards().get(duelModel.turn).
                    get(Integer.parseInt(matcher.group(1)) - 1), "Hand");
            return "card selected";
        }
    }

    public void hasSwordCard() {
        for (Map.Entry<Card, Integer> entry : duelModel.getSwordsCard().get(duelModel.turn).entrySet()) {
            Card swordCard = entry.getKey();
            int numberOfTurnExist = entry.getValue();
            if (numberOfTurnExist >= 2) {
                duelModel.deleteSwordsCard(duelModel.turn, swordCard);
            } else {
                entry.setValue(numberOfTurnExist + 1);
            }
        }
        for (Map.Entry<Card, Integer> entry : duelModel.getSwordsCard().get(1 - duelModel.turn).entrySet()) {
            Card swordCard = entry.getKey();
            int numberOfTurnExist = entry.getValue();
            if (numberOfTurnExist >= 2) {
                duelModel.deleteSwordsCard(1 - duelModel.turn, swordCard);
            } else {
                entry.setValue(numberOfTurnExist + 1);
            }
        }
    }

    public void hasSupplySquadCard() {
        ArrayList<Card> monsterDestroyedInThisTurn1 = duelModel.getMonsterDestroyedInThisTurn()
                .get(duelModel.turn);
        ArrayList<Card> monsterDestroyedInThisTurn2 = duelModel.getMonsterDestroyedInThisTurn()
                .get(1 - duelModel.turn);
        ArrayList<Card> supplyCards1 = duelModel.getSupplySquadCards().get(duelModel.turn);
        ArrayList<Card> supplyCards2 = duelModel.getSupplySquadCards().get(1 - duelModel.turn);
        if (monsterDestroyedInThisTurn1.size() > 0) {
            if (supplyCards1.size() > 0) {
                duelModel.addCardFromDeckToHandInMiddleOfGame(duelModel.turn
                        , duelModel.getPlayersCards().get(duelModel.turn).get(duelModel.
                                getPlayersCards().get(duelModel.turn).size() - 1));
            }
        } else if (monsterDestroyedInThisTurn2.size() > 0) {
            if (supplyCards2.size() > 0) {
                duelModel.addCardFromDeckToHandInMiddleOfGame(duelModel.turn
                        , duelModel.getPlayersCards().get(duelModel.turn).get(duelModel.
                                getPlayersCards().get(duelModel.turn).size() - 1));
            }
        }
    }


}