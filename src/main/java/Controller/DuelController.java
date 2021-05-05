package Controller;

import Model.*;
import View.DuelView;
import View.MainPhaseView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class DuelController {
    protected DuelModel duelModel;
    protected DuelView duelView;

    public void setDuelModel(DuelModel duelModel, DuelView duelView) {
        this.duelModel = duelModel;
        this.duelView = duelView;
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
                return opponentActiveSpell(Integer.parseInt(matcher.group(1)));
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
        return "";
    }

    public String effectOfMonsterReborn(int placeOfSpell) {
        MainPhaseController mainPhaseController = MainPhaseController.getInstance();
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
                    return mainPhaseController.specialSummonMonsterOnFieldFromGraveyard(duelModel.turn
                            , state, numberOfCard - 1);
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
                    duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                    duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                    duelModel.addCardToGraveyard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                            .get(0));
                    return mainPhaseController.specialSummonMonsterOnFieldFromGraveyard(1 - duelModel.turn
                            , state, numberOfCard - 1);
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
            duelModel.addCardFromDeckToHandInMiddleOfGame(duelModel.turn, card);
            duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
            duelModel.addCardToGraveyard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                    .get(0));
            return "Filed card added to your hand successfully";
        }

    }

    public String effectOfPotOfGreed(int placeOfSpell) {
        ArrayList<Card> cardsInDeckOfPlayer = duelModel.getPlayersCards().get(duelModel.turn);
        if (cardsInDeckOfPlayer.size() < 2) {
            return "you dont have enough card for add to your hand";
        } else {
            duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
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
        return "two card from deck added to your hand";
    }

    public String effectOfRaigeki(int placeOfSpell) {
        boolean opponentHasAnyMonster = false;
        ArrayList<Card> monstersInFieldOfPlayer = duelModel.getMonstersInField().get(duelModel.turn);
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
            return "all of monsters that your opponent control destroyed";
        } else {
            duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
            duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
            duelModel.addCardToGraveyard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                    .get(0));
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
            duelModel.addBorrowCard(borrowCard, detailsOfBorrowCard + "/" + placeOfFieldThatBorrowCardSet);
            duelModel.deleteMonster(1 - duelModel.turn, numberOfMonsterCard - 1);
            duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
            duelModel.addCardToGraveyard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                    .get(0));
            return "monster card added to your field successfully";
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
        return "";
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
        return "summoned successfully";
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

}