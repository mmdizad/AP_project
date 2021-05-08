package Controller;

import Model.*;
import View.DuelView;
import View.MainPhaseView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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
    //این تابع حین بازی صدا زده میشه تا کارت های ورودی شامل میدان شوند
    public void activeField(){
        if(duelModel.getField().get(duelModel.turn).get(0)!=null){
          Spell spell=(Spell) duelModel.getField().get(duelModel.turn).get(0);
            if(spell.getName().equals("Yami"))
                effectOfYami();
            else if(spell.getName().equals("Forest"))
                effectOfForest();
            else if(spell.getName().equals("Closed Forest"))
                effectOfClosedForest();
            else if(spell.getName().equals("UMIIRUKA"))
                effectOfUmiiruka();
        }else if(duelModel.getField().get(1-duelModel.turn).get(0)!=null){
            Spell spell=(Spell) duelModel.getField().get(1-duelModel.turn).get(0);
            if(spell.getName().equals("Yami"))
                effectOfYami();
            else if(spell.getName().equals("Forest"))
                effectOfForest();
            else if(spell.getName().equals("Closed Forest"))
                effectOfClosedForest();
            else if(spell.getName().equals("UMIIRUKA"))
                effectOfUmiiruka();

        }

    }

    public String deselect() {
        if (duelModel.getSelectedCards().get(duelModel.turn).get(0) == null) {
            return "no card is selected yet";
        } else {
            duelModel.deSelectedCard();
            return "card deselected";
        }
    }

    public String activateEffect(int placeOfSpell) {
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
        } else if (spell.getName().equals("Spell Absorption")) {
            return effectOfSpellAbsorption(placeOfSpell);
        } else if (spell.getName().equals("Messenger of peace")) {
            return effectOfMessengerOfPeace(placeOfSpell);
        } else if (spell.getName().equals("Twin Twisters")) {
            return effectOfTwinTwisters(placeOfSpell);
        } else if (spell.getName().equals("Mystical space typhoon")) {
            return effectOfMysticalSpaceTyphoon(placeOfSpell);
        } else if (spell.getName().equals("Ring of Defense")) {
            return effectOfRingOfDefense(placeOfSpell);
        } else if (spell.getName().equals("Advanced Ritual Art")) {
            return effectOfAdvancedRitualArt(placeOfSpell);
        }else

        return "";
    }

    public void isOpponentHasAnySpellOrTrapForActivate() {
        boolean hasAnySpellOrTrap = false;
        for (int i = 1; i <= 5; i++) {
            if (duelModel.getSpellAndTrap(1 - duelModel.turn, i) != null) {
                if (duelModel.getSpellAndTrap(1 - duelModel.turn, i).getCardType()
                        .equals("Quick-play")) {
                    hasAnySpellOrTrap = true;
                    break;
                }
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
                if (duelModel.getSelectedCards().get(duelModel.turn).get(0).getCategory().equals("Trap")) {
                    return opponentActiveTrap();
                } else {
                    if (!duelModel.getSelectedCards().get(duelModel.turn).get(0).getCardType()
                            .equals("Quick-play")) {
                        return "you cant active this spell int this turn";
                    }
                    String[] detailsOfSelectedCard = duelModel.getDetailOfSelectedCard().get(duelModel.turn).
                            get(duelModel.getSelectedCards().get(duelModel.turn).get(0)).split("/");
                    String stateOfSelectedCard = detailsOfSelectedCard[1];
                    if (stateOfSelectedCard.equals("O")) {
                        return "Card already Activated";
                    } else {
                        return opponentActiveSpell(Integer.parseInt(matcher.group(1)));
                    }
                }
            }
        }
        return "it’s not your turn to play this kind of moves";
    }

    public String opponentActiveSpell(int placeOfSpell) {
        Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
        Spell spell = (Spell) card;
        if (spell.getName().equals("Twin Twisters")) {
            return effectOfTwinTwisters(placeOfSpell);
        } else if (spell.getName().equals("Mystical space typhoon")) {
            return effectOfMysticalSpaceTyphoon(placeOfSpell);
        } else if (spell.getName().equals("Ring of Defense")) {
            return effectOfRingOfDefense(placeOfSpell);
        }
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
                    return specialEffectOfMonsterReborn(placeOfSpell, response);
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
                    return specialEffectOfMonsterReborn(placeOfSpell, response);
                }
            }
        }
    }

    public String specialEffectOfMonsterReborn(int placeOfSpell, String response) {
        if (placeOfSpell != -1) {
            if (response.equals("activated and summoned successfully")) {
                duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                duelModel.effectOfSpellAbsorptionCards();
                duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                duelModel.addCardToGraveyard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                        .get(0));
                return "spell activated";
            }
        } else {
            if (response.equals("activated and summoned successfully")) {
                Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                String response1 = activeSpellFromHand(false);
                if (response1.equals("spell activated")) {
                    duelModel.addCardToGraveyard(duelModel.turn, card);
                    duelModel.effectOfSpellAbsorptionCards();
                }
                return response1;
            }
        }
        return response;
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
            if (placeOfSpell != -1) {
                duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                duelModel.effectOfSpellAbsorptionCards();
                duelModel.addCardFromDeckToHandInMiddleOfGame(duelModel.turn, card);
                duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                duelModel.addCardToGraveyard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                        .get(0));
                return "spell activated";
            } else {
                Card card1 = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                String response = activeSpellFromHand(false);
                if (response.equals("spell activated")) {
                    duelModel.addCardToGraveyard(duelModel.turn, card1);
                    duelModel.effectOfSpellAbsorptionCards();
                    duelModel.addCardFromDeckToHandInMiddleOfGame(duelModel.turn, card);
                }
                return response;
            }
        }

    }

    public String effectOfPotOfGreed(int placeOfSpell) {
        ArrayList<Card> cardsInDeckOfPlayer = duelModel.getPlayersCards().get(duelModel.turn);
        if (cardsInDeckOfPlayer.size() < 2) {
            return "you dont have enough card for add to your hand";
        } else {
            if (placeOfSpell != -1) {
                duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                specialEffectOfPotOfGreed();
                duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                duelModel.addCardToGraveyard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                        .get(0));
                return "spell activated";
            } else {
                Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                String response = activeSpellFromHand(false);
                if (response.equals("spell activated")) {
                    duelModel.addCardToGraveyard(duelModel.turn, card);
                    specialEffectOfPotOfGreed();
                }
                return response;
            }
        }
    }

    public void specialEffectOfPotOfGreed() {
        duelModel.effectOfSpellAbsorptionCards();
        duelModel.addCardFromDeckToHandInMiddleOfGame(duelModel.turn
                , duelModel.getPlayersCards().get(duelModel.turn).get(duelModel.
                        getPlayersCards().get(duelModel.turn).size() - 1));
        duelModel.addCardFromDeckToHandInMiddleOfGame(duelModel.turn
                , duelModel.getPlayersCards().get(duelModel.turn).get(duelModel.
                        getPlayersCards().get(duelModel.turn).size() - 1));
    }

    public String effectOfRaigeki(int placeOfSpell) {
        boolean opponentHasAnyMonster = false;
        ArrayList<Card> monstersInFieldOfPlayer = duelModel.getMonstersInField().get(1 - duelModel.turn);
        int i = 0;
        for (Card card : monstersInFieldOfPlayer) {
            if (card != null) {
                opponentHasAnyMonster = true;
            }
            i++;
        }
        if (opponentHasAnyMonster) {
            if (placeOfSpell != -1) {
                duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                deleteAllMonsters(1 - duelModel.turn);
                duelModel.effectOfSpellAbsorptionCards();
                duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                duelModel.addCardToGraveyard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                        .get(0));
                return "spell activated";
            } else {
                Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                String response = activeSpellFromHand(false);
                if (response.equals("spell activated")) {
                    duelModel.addCardToGraveyard(duelModel.turn, card);
                    duelModel.effectOfSpellAbsorptionCards();
                    deleteAllMonsters(1 - duelModel.turn);
                }
                return response;
            }
        } else {
            return "your opponent dont have any monster";
        }
    }

    public void deleteAllMonsters(int turn) {
        ArrayList<Card> monstersInFieldOfPlayer = duelModel.getMonstersInField().get(turn);
        int i = 0;
        for (Card card : monstersInFieldOfPlayer) {
            if (card != null) {
                duelModel.deleteMonster(1 - duelModel.turn, i);
                duelModel.addCardToGraveyard(1 - duelModel.turn, card);
            }
            i++;
        }
    }

    public String effectOfChangeOfHeart(int placeOfSpell) {
        if (placeOfSpell == -1 && isSpellZoneFull(duelModel.turn)) {
            return "spell card zone is full";
        }
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
                return "monster card zone is full";
            }
            if (placeOfSpell != -1) {
                duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                duelModel.effectOfSpellAbsorptionCards();
                duelModel.addBorrowCard(borrowCard, detailsOfBorrowCard + "/" + placeOfFieldThatBorrowCardSet);
                duelModel.deleteMonster(1 - duelModel.turn, numberOfMonsterCard - 1);
                duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                duelModel.addCardToGraveyard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                        .get(0));
                return "spell activated";
            } else {
                Card card1 = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                String response = activeSpellFromHand(false);
                duelModel.addCardToGraveyard(duelModel.turn, card1);
                duelModel.effectOfSpellAbsorptionCards();
                duelModel.addBorrowCard(borrowCard, detailsOfBorrowCard +
                        "/" + placeOfFieldThatBorrowCardSet);
                duelModel.deleteMonster(1 - duelModel.turn, numberOfMonsterCard - 1);
                return response;
            }
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
        if (placeOfSpell == -1 && isSpellZoneFull(duelModel.turn)) {
            return "spell card zone is full";
        }
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
                } else if (card.getName().equals("Messenger of peace")) {
                    duelModel.deleteMessengerOfPeaceCards(1 - duelModel.turn);
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
            if (placeOfSpell != -1) {
                duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                duelModel.effectOfSpellAbsorptionCards();
                duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                duelModel.addCardToGraveyard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                        .get(0));
                return "spell activated";
            } else {
                Card card1 = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                String response = activeSpellFromHand(false);
                duelModel.addCardToGraveyard(duelModel.turn, card1);
                duelModel.effectOfSpellAbsorptionCards();
                return response;
            }
        }
    }

    public String effectOfSwordsOfRevealingLight(int placeOfSpell) {
        // not complete it needs a change in battlePhase
        if (placeOfSpell == -1 && isSpellZoneFull(duelModel.turn)) {
            return "spell card zone is full";
        }
        if (placeOfSpell != -1) {
            duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
            duelModel.effectOfSpellAbsorptionCards();
            duelModel.setSwordsCard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn).get(0));
            changeStateOfMonsterWithSwordsCard(1 - duelModel.turn);
            return "spell activated";
        } else {
            Card card1 = duelModel.getSelectedCards().get(duelModel.turn).get(0);
            String response = activeSpellFromHand(true);
            duelModel.effectOfSpellAbsorptionCards();
            duelModel.setSwordsCard(duelModel.turn, card1);
            changeStateOfMonsterWithSwordsCard(1 - duelModel.turn);
            return response;
        }
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
        if (placeOfSpell == -1 && isSpellZoneFull(duelModel.turn)) {
            return "spell card zone is full";
        }
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
            if (placeOfSpell != -1) {
                duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                duelModel.effectOfSpellAbsorptionCards();
                duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                duelModel.addCardToGraveyard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                        .get(0));
                return "spell activated";
            } else {
                Card card1 = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                String response = activeSpellFromHand(false);
                duelModel.addCardToGraveyard(duelModel.turn, card1);
                duelModel.effectOfSpellAbsorptionCards();
                return response;
            }
        } else {
            return "you and your opponent dont have any monsters";
        }
    }

    public String effectOfSupplySquad(int placeOfSpell) {
        if (placeOfSpell == -1 && isSpellZoneFull(duelModel.turn)) {
            return "spell card zone is full";
        }
        if (placeOfSpell != -1) {
            duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
            duelModel.effectOfSpellAbsorptionCards();
            duelModel.setSupplySquad(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                    .get(0));
            return "spell activated";
        } else {
            Card card1 = duelModel.getSelectedCards().get(duelModel.turn).get(0);
            String response = activeSpellFromHand(true);
            duelModel.effectOfSpellAbsorptionCards();
            duelModel.setSupplySquad(duelModel.turn, card1);
            return response;
        }
    }

    public String effectOfSpellAbsorption(int placeOfSpell) {
        if (placeOfSpell == -1 && isSpellZoneFull(duelModel.turn)) {
            return "spell card zone is full";
        }
        if (placeOfSpell != -1) {
            duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
            duelModel.effectOfSpellAbsorptionCards();
            duelModel.setSpellAbsorptionCards(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                    .get(0));
            return "spell activated";
        } else {
            Card card1 = duelModel.getSelectedCards().get(duelModel.turn).get(0);
            String response = activeSpellFromHand(true);
            duelModel.effectOfSpellAbsorptionCards();
            duelModel.setSpellAbsorptionCards(duelModel.turn, card1);
            return response;
        }
    }

    public String effectOfMessengerOfPeace(int placeOfSpell) {
        // not complete it needs a change in battlePhase
        if (placeOfSpell == -1 && isSpellZoneFull(duelModel.turn)) {
            return "spell card zone is full";
        }
        if (placeOfSpell != -1) {
            duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
            duelModel.effectOfSpellAbsorptionCards();
            duelModel.setMessengerOfPeace(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                    .get(0));
            return "spell activated";
        } else {
            Card card1 = duelModel.getSelectedCards().get(duelModel.turn).get(0);
            String response = activeSpellFromHand(true);
            duelModel.effectOfSpellAbsorptionCards();
            duelModel.setMessengerOfPeace(duelModel.turn, card1);
            return response;
        }
    }

    public String effectOfTwinTwisters(int placeOfSpell) {
        if (placeOfSpell == -1 && isSpellZoneFull(duelModel.turn)) {
            return "spell card zone is full";
        }
        if (hasSpellSetInThisTurn()) {
            return "preparations of this spell are not done yet";
        }
        int placeOfCardInHand = duelView.scanNumberOfCardForDeleteFromHand();
        if (placeOfCardInHand > duelModel.getHandCards().get(duelModel.turn).size()) {
            return "you cant delete card with this address from your hand";
        } else {
            Card card = duelModel.getHandCards().get(duelModel.turn)
                    .get(placeOfCardInHand - 1);
            duelModel.deleteCardFromHand(card);
            duelModel.addCardToGraveyard(duelModel.turn, card);
            int numberOfCardsPlayerWantToDestroyed = duelView.scanNumberOfCardThatWouldBeDelete();
            if (numberOfCardsPlayerWantToDestroyed == 0) {
                duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                duelModel.effectOfSpellAbsorptionCards();
                duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                duelModel.addCardToGraveyard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                        .get(0));
                return "card activated";
            } else if (numberOfCardsPlayerWantToDestroyed == 1) {
                String response = duelView.scanPlaceOfCardWantToDestroyed();
                String[] splitResponse = response.split(" ");
                int placeOfSpellOrTrapCard = Integer.parseInt(splitResponse[1]);
                if (splitResponse[0].equals("my")) {
                    return deleteASpellCardInActiveSpell(duelModel.turn, placeOfSpell,
                            placeOfSpellOrTrapCard);
                } else if (splitResponse[0].equals("opponent")) {
                    return deleteASpellCardInActiveSpell(1 - duelModel.turn, placeOfSpell,
                            placeOfSpellOrTrapCard);
                } else {
                    return "you must enter my/opponent";
                }
            } else if (numberOfCardsPlayerWantToDestroyed == 2) {
                String response1 = duelView.scanPlaceOfCardWantToDestroyed();
                String response2 = duelView.scanPlaceOfCardWantToDestroyed();
                String[] splitResponse1 = response1.split(" ");
                String[] splitResponse2 = response2.split(" ");
                int placeOfSpellOrTrapCard1 = Integer.parseInt(splitResponse1[1]);
                int placeOfSpellOrTrapCard2 = Integer.parseInt(splitResponse2[1]);
                if (splitResponse1[0].equals("my") && splitResponse2[0].equals("my")) {
                    deleteASpellCardInActiveSpell(duelModel.turn, placeOfSpell,
                            placeOfSpellOrTrapCard1);
                    deleteASpellCardInActiveSpell(duelModel.turn, placeOfSpell,
                            placeOfSpellOrTrapCard2);
                } else if (splitResponse1[0].equals("opponent") && splitResponse2[0].equals("opponent")) {
                    deleteASpellCardInActiveSpell(1 - duelModel.turn, placeOfSpell,
                            placeOfSpellOrTrapCard1);
                    deleteASpellCardInActiveSpell(1 - duelModel.turn, placeOfSpell,
                            placeOfSpellOrTrapCard2);
                } else if (splitResponse1[0].equals("my") && splitResponse2[0].equals("opponent")) {
                    deleteASpellCardInActiveSpell(duelModel.turn, placeOfSpell,
                            placeOfSpellOrTrapCard1);
                    deleteASpellCardInActiveSpell(1 - duelModel.turn, placeOfSpell,
                            placeOfSpellOrTrapCard2);
                } else if (splitResponse1[0].equals("opponent") && splitResponse2[0].equals("my")) {
                    deleteASpellCardInActiveSpell(1 - duelModel.turn, placeOfSpell,
                            placeOfSpellOrTrapCard1);
                    deleteASpellCardInActiveSpell(duelModel.turn, placeOfSpell,
                            placeOfSpellOrTrapCard2);
                } else {
                    return "you must enter my/opponent";
                }
            } else {
                return "you cant destroy spell in this number";
            }
        }
        return "";
    }

    public String effectOfMysticalSpaceTyphoon(int placeOfSpell) {
        if (placeOfSpell == -1 && isSpellZoneFull(duelModel.turn)) {
            return "spell card zone is full";
        }
        if (hasSpellSetInThisTurn()) {
            return "preparations of this spell are not done yet";
        }
        String response = duelView.scanPlaceOfCardWantToDestroyed();
        String[] splitResponse = response.split(" ");
        int placeOfSpellOrTrapCard = Integer.parseInt(splitResponse[1]);
        if (splitResponse[0].equals("my")) {
            return deleteASpellCardInActiveSpell(duelModel.turn, placeOfSpell,
                    placeOfSpellOrTrapCard);
        } else if (splitResponse[0].equals("opponent")) {
            return deleteASpellCardInActiveSpell(1 - duelModel.turn, placeOfSpell,
                    placeOfSpellOrTrapCard);
        } else {
            return "you must enter my/opponent";
        }

    }

    public String effectOfRingOfDefense(int placeOfSpell) {
        // not complete it needs a change in trap
        if (placeOfSpell == -1 && isSpellZoneFull(duelModel.turn)) {
            return "spell card zone is full";
        }
        if (hasSpellSetInThisTurn()) {
            return "preparations of this spell are not done yet";
        }
        return "";
    }


    public boolean hasSpellSetInThisTurn() {
        ArrayList<Card> spellsAndTrapsSetInThisTurn = duelModel.getSpellsAndTarpsSetInThisTurn()
                .get(duelModel.turn);
        for (Card card : spellsAndTrapsSetInThisTurn) {
            if (card.getCategory().equals("Spell")) {
                if (card == duelModel.getSelectedCards().get(duelModel.turn).get(0)) {
                    return true;
                }
            }
        }
        return false;
    }


    public String deleteASpellCardInActiveSpell(int turn, int placeOfSpell, int placeOfSpellOrTrapCard) {
        if (placeOfSpellOrTrapCard > 5) {
            return "you must enter correct address";
        } else {
            Card card1 = duelModel.getSpellsAndTrapsInFiled().get(turn)
                    .get(placeOfSpellOrTrapCard - 1);
            if (card1 == null) {
                return "you cant destroyed card with this address";
            } else {
                if (card1.getName().equals("Swords of Revealing Light")) {
                    duelModel.deleteSwordsCard(turn, card1);
                } else if (card1.getName().equals("Supply Squad")) {
                    duelModel.deleteSupplySquadCard(turn, card1);
                } else if (card1.getName().equals("Spell Absorption")) {
                    duelModel.deleteSpellAbsorptionCards(turn, card1);
                } else if (card1.getName().equals("Messenger of peace")) {
                    duelModel.deleteMessengerOfPeaceCards(turn);
                } else {
                    duelModel.deleteSpellAndTrap(turn, placeOfSpellOrTrapCard - 1);
                    duelModel.addCardToGraveyard(turn, card1);
                }
                if (placeOfSpell != -1) {
                    duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                    duelModel.effectOfSpellAbsorptionCards();
                    duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                    duelModel.addCardToGraveyard(duelModel.turn, duelModel.getSelectedCards().get(duelModel.turn)
                            .get(0));
                    return "card activated";
                } else {
                    Card spellCard = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                    String response = activeSpellFromHand(true);
                    duelModel.addCardToGraveyard(duelModel.turn, spellCard);
                    duelModel.effectOfSpellAbsorptionCards();
                    return response;
                }
            }
        }
    }

    public boolean isSpellZoneFull(int turn) {
        boolean spellZoneFull = true;
        ArrayList<Card> spellsAndTraps = duelModel.getSpellsAndTrapsInFiled().get(turn);
        for (Card card : spellsAndTraps) {
            if (card == null) {
                spellZoneFull = false;
                break;
            }
        }
        return spellZoneFull;
    }

    public String activeSpellFromHand(boolean continuousSpell) {
        if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(0) == null) {
            duelModel.addSpellAndTrapFromHandToGame("O/1", 0);
            if (!continuousSpell)
                duelModel.deleteSpellAndTrap(duelModel.turn, 0);
        } else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(1) == null) {
            duelModel.addSpellAndTrapFromHandToGame("O/2", 1);
            if (!continuousSpell)
                duelModel.deleteSpellAndTrap(duelModel.turn, 1);
        } else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(2) == null) {
            duelModel.addSpellAndTrapFromHandToGame("O/3", 2);
            if (!continuousSpell)
                duelModel.deleteSpellAndTrap(duelModel.turn, 2);
        } else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(3) == null) {
            duelModel.addSpellAndTrapFromHandToGame("O/4", 3);
            if (!continuousSpell)
                duelModel.deleteSpellAndTrap(duelModel.turn, 3);
        } else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(4) == null) {
            duelModel.addSpellAndTrapFromHandToGame("O/5", 4);
            if (!continuousSpell)
                duelModel.deleteSpellAndTrap(duelModel.turn, 4);
        }
        return "spell activated";

    }

    public String effectOfAdvancedRitualArt(int placeOfSpell) {
        boolean hasAnyRitualMonster = false;
        if (placeOfSpell == -1 && isSpellZoneFull(duelModel.turn)) {
            return "spell card zone is full";
        }
        ArrayList<Card> handCards = duelModel.getHandCards().get(duelModel.turn);
        ArrayList<Card> cardsInDeck = duelModel.getPlayersCards().get(duelModel.turn);
        Monster monster = null;
        for (Card card : handCards) {
            if (card.getCategory().equals("Monster")) {
                monster = (Monster) card;
                if (monster.getCardType().equals("Ritual")) {
                    hasAnyRitualMonster = true;
                    break;
                }
            }
        }
        if (!hasAnyRitualMonster) {
            return "there is no way you could ritual summon a monster";
        } else {
            String[] addressOfCardForTribute = duelView.scanAddressForTributeForRitualSummon().split(" ");
            if (addressOfCardForTribute.length < 2) {
                return "you must enter two address for tribute from your deck";
            } else {
                int addressOfCard1 = Integer.parseInt(addressOfCardForTribute[0]);
                int addressOfCard2 = Integer.parseInt(addressOfCardForTribute[1]);
                if (addressOfCard1 > cardsInDeck.size() || addressOfCard2 > cardsInDeck.size()) {
                    return "you must enter correct address";
                } else {
                    Card card1 = cardsInDeck.get(addressOfCard1 - 1);
                    Card card2 = cardsInDeck.get(addressOfCard2 - 1);
                    if (!card1.getCategory().equals("Monster") || !card2.getCategory().equals("Monster")) {
                        return "you must tribute monster";
                    } else {
                        Monster monster1 = (Monster) card1;
                        Monster monster2 = (Monster) card2;
                        if (monster1.getLevel() + monster2.getLevel() != monster.getLevel()) {
                            return "selected monsters levels don’t match with ritual monster";
                        } else {
                            MainPhaseView mainPhaseView = MainPhaseView.getInstance();
                            String stateOfCardForSummon = mainPhaseView.getStateOfCardForSummon();
                            if (!stateOfCardForSummon.equals("Attack")
                                    && !stateOfCardForSummon.equals("Defence")) {
                                return "you must Highlight the state of card for summon correctly";
                            }
                            String response = ritualSummonMonsterOnField(stateOfCardForSummon, monster);
                            if (response.equals("summoned successfully")) {
                                if (placeOfSpell != -1) {
                                    duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                                } else {
                                    Card spellCard = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                                    activeSpellFromHand(false);
                                    duelModel.addCardToGraveyard(duelModel.turn, spellCard);
                                }
                                duelModel.deleteCardFromDeck(duelModel.turn, addressOfCard1 - 1, card1);
                                duelModel.deleteCardFromDeck(duelModel.turn, addressOfCard2 - 1, card2);
                                duelModel.effectOfSpellAbsorptionCards();
                            }
                            return response;
                        }
                    }
                }
            }
        }
    }

    public String ritualSummonMonsterOnField(String state, Card card) {
        String stateOfCard = "OO";
        if (state.equals("Attack")) {
            stateOfCard = "OO";
        } else if (state.equals("Defence")) {
            stateOfCard = "DO";
        }
        if (duelModel.getMonstersInField().get(duelModel.turn).get(0) == null) {
            duelModel.ritualSummon(stateOfCard + "/1", 0, card);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(1) == null) {
            duelModel.ritualSummon(stateOfCard + "/2", 1, card);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(2) == null) {
            duelModel.ritualSummon(stateOfCard + "/3", 0, card);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(3) == null) {
            duelModel.ritualSummon(stateOfCard + "/4", 0, card);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(4) == null) {
            duelModel.ritualSummon(stateOfCard + "/5", 0, card);
        } else {
            return "monster card zone is full";
        }
        return "summoned successfully";
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


            } else {
                monster.setDefensePower(monster.getAttackPower() + monster.getDefensePower());

            }
            return "spell activated";
        } else return "you don't have any Warrior monster to equip ";
    }

    public String effectOfUmiiruka() {
        for (int i = 0; i < 5; i++) {
            Monster monster = (Monster) duelModel.getMonstersInField().get(duelModel.turn).get(i);
            Monster monster1 = (Monster) duelModel.getMonstersInField().get(duelModel.turn).get(i);
            if (monster != null)
                if (monster.getCardType().equals("Aqua"))
                    if (duelModel.getSpellZoneActivate().get(duelModel.turn).get(i)) {
                        duelModel.getSpellZoneActivate().get(duelModel.turn).add(i, true);
                        monster.setDefensePower(monster.getDefensePower() - 400);
                        monster.setAttackPower(monster.getAttackPower() + 500);
                    }
            if (monster1 != null)
                if (monster1.getCardType().equals("Aqua"))
                    if (!duelModel.getSpellZoneActivate().get(1 - duelModel.turn).get(i)) {
                        duelModel.getSpellZoneActivate().get(1 - duelModel.turn).add(i, true);
                        monster1.setDefensePower(monster1.getDefensePower() - 400);
                        monster1.setAttackPower(monster1.getAttackPower() + 500);
                    }
        }
        deselect();
        return "spellZone activated";
    }

    public String effectOfClosedForest() {
        for (int i = 0; i < 5; i++) {
            Monster monster = (Monster) duelModel.getMonstersInField().get(duelModel.turn).get(i);
            if (monster != null)
                if (monster.getMonsterType().equals("Beast-Type")) {
                    if (!duelModel.getSpellZoneActivate().get(duelModel.turn).get(i))
                        duelModel.getSpellZoneActivate().get(duelModel.turn).add(i, true);
                    monster.setAttackPower(monster.getAttackPower() + duelModel.getGraveyard(duelModel.turn).size() * 100);
                }
        }
        return "spellZone activated";
    }

    public String effectOfForest() {

        for (int i = 0; i < 5; i++) {
            Monster monster = (Monster) duelModel.getMonstersInField().get(duelModel.turn).get(i);
            Monster monster1 = (Monster) duelModel.getMonstersInField().get(1 - duelModel.turn).get(i);
            if (monster != null)
                if (monster.getMonsterType().equals("Beast-Warrior") || monster.getMonsterType().equals("Beast") || monster.getMonsterType().equals("Insect"))
                    if (!duelModel.getSpellZoneActivate().get(duelModel.turn).get(i)) {
                        duelModel.getSpellZoneActivate().get(duelModel.turn).add(i, true);
                        Monster monster2 = Monster.getMonsterByName(monster.getName());
                        monster.setAttackPower(monster2.getAttackPower() + 200);
                        monster.setDefensePower(monster2.getDefensePower() + 200);
                    }
            if (monster1 != null)
                if (monster1.getMonsterType().equals("Beast-Warrior") || monster.getMonsterType().equals("Beast") || monster.getMonsterType().equals("Insect"))
                    if (!duelModel.getSpellZoneActivate().get(1 - duelModel.turn).get(i)) {
                        duelModel.getSpellZoneActivate().get(1 - duelModel.turn).add(i, true);
                        monster1.setAttackPower(Monster.getMonsterByName(monster1.getName()).getAttackPower() + 200);
                        monster1.setDefensePower(Monster.getMonsterByName(monster1.getName()).getDefensePower() + 200);
                    }
        }
        return "spellZone activated";
    }

    public String effectOfYami() {
        for (int i = 0; i < 5; i++) {
            Monster monster = (Monster) duelModel.getMonstersInField().get(duelModel.turn).get(i);
            Monster monster1 = (Monster) duelModel.getMonstersInField().get(1 - duelModel.turn).get(i);
            if (monster != null) {
                if (monster.getMonsterType().equals("Fiend") || monster.getMonsterType().equals("Spellcaster")) {
                    if (!duelModel.getSpellZoneActivate().get(duelModel.turn).get(i)) {
                        duelModel.getSpellZoneActivate().get(duelModel.turn).add(i, true);
                        Monster monster2 = Monster.getMonsterByName(monster.getName());
                        monster.setAttackPower(monster2.getAttackPower() + 200);
                        monster.setDefensePower(monster2.getDefensePower() + 200);
                    }
                } else if (monster.getMonsterType().equals("Fairy")) {
                    if (!duelModel.getSpellZoneActivate().get(duelModel.turn).get(i)) {
                        duelModel.getSpellZoneActivate().get(duelModel.turn).add(i, true);
                        Monster monster2 = Monster.getMonsterByName(monster.getName());
                        monster.setAttackPower(monster2.getAttackPower() - 200);
                        monster.setDefensePower(monster2.getDefensePower() - 200);
                    }

                }
            }
            if (monster1 != null) {
                if (monster1.getMonsterType().equals("Fiend") || monster1.getMonsterType().equals("Spellcaster")) {
                    if (!duelModel.getSpellZoneActivate().get(1 - duelModel.turn).get(i)) {
                        duelModel.getSpellZoneActivate().get(1 - duelModel.turn).add(i, true);
                        monster1.setAttackPower(Monster.getMonsterByName(monster1.getName()).getAttackPower() + 200);
                        monster1.setDefensePower(Monster.getMonsterByName(monster1.getName()).getDefensePower() + 200);

                    }
                } else if (monster1.getMonsterType().equals("Fairy")) {
                    if (!duelModel.getSpellZoneActivate().get(1 - duelModel.turn).get(i)) {
                        duelModel.getSpellZoneActivate().get(1 - duelModel.turn).add(i, true);
                        Monster monster2 = Monster.getMonsterByName(monster.getName());
                        monster1.setAttackPower(monster2.getAttackPower() - 200);
                        monster1.setDefensePower(monster2.getDefensePower() - 200);
                    }
                }
            }
        }
        return "spellZoneActivate";
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
        if (duelModel.getSelectedCards().get(duelModel.turn).get(0).getName().equals("Magic Cylinder")) {
            activeNormalTraps();
        } else if (duelModel.getSelectedCards().get(duelModel.turn).get(0).getName().equals("Mirror Force")) {
            activeNormalTraps();
        } else if (duelModel.getSelectedCards().get(duelModel.turn).get(0).getName().equals("Mind Crush")) {
            activeTrapMindCrush();
        } else if (duelModel.getSelectedCards().get(duelModel.turn).get(0).getName().equals("Trap Hole")) {
            activeNormalTraps();
        } else if (duelModel.getSelectedCards().get(duelModel.turn).get(0).getName().equals("Torrential Tribute")) {
            activeNormalTraps();
        } else if (duelModel.getSelectedCards().get(duelModel.turn).get(0).getName().equals("Time Seal")) {
            activeNormalTraps();
        } else if (duelModel.getSelectedCards().get(duelModel.turn).get(0).getName().equals("Negate Attack")) {
            activeNormalTraps();
        } else if (duelModel.getSelectedCards().get(duelModel.turn).get(0).getName().equals("Call of the Haunted")) {
            activeTrapCallOfTheHaunted();
        } else if (duelModel.getSelectedCards().get(duelModel.turn).get(0).getName().equals("Magic Jammer")) {
            activeNormalTraps();
        } else if (duelModel.getSelectedCards().get(duelModel.turn).get(0).getName().equals("Solemn Warning")) {
            activeNormalTraps();
        }
        return "trap activated";
    }

    public void activeNormalTraps() {
        Card trap = duelModel.getSelectedCards().get(duelModel.turn).get(0);
        int place = Integer.parseInt(duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(trap).split("/")[2]);
        String condition = duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(trap);
        duelModel.changeSpellAndTrapCondition(duelModel.turn, place, condition.replaceAll("H", "O"));
    }

    public void activeTrapMindCrush() {
        Card trap = duelModel.getSelectedCards().get(duelModel.turn).get(0);
        int place = Integer.parseInt(duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(trap).split("/")[2]);
        duelModel.deleteSpellAndTrap(duelModel.turn, place - 1);
        duelModel.addCardToGraveyard(duelModel.turn, trap);
//        String condition = duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(trap);
//        duelModel.changeSpellAndTrapCondition(duelModel.turn, place,condition.replaceAll("H","O"));
        String cardName = duelView.getCardNameForTrapMindCrush();
        ArrayList<ArrayList<Card>> cardsInHand = duelModel.getHandCards();
        boolean isCardExistInOpponentHand = false;
        for (Card card : cardsInHand.get(duelModel.turn - 1)) {
            if (card.getName().equals(cardName)) {
                duelModel.deleteCardFromOpponentHand(card);
                isCardExistInOpponentHand = true;
            }
        }
        if (!isCardExistInOpponentHand) {
            ArrayList<Integer> number = new ArrayList<>();
            for (int i = 0; i < duelModel.getHandCards().get(duelModel.turn).size(); i++) {
                number.add(i + 1);
            }
            Collections.shuffle(number);
            Card card = duelModel.getHandCards().get(duelModel.turn).get(number.get(0));
            duelModel.deleteCardFromHand(card);
            duelModel.addCardToGraveyard(duelModel.turn, card);
        }
    }

    public void activeTrapCallOfTheHaunted() {
        Card trap = duelModel.getSelectedCards().get(duelModel.turn).get(0);
        int place = Integer.parseInt(duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(trap).split("/")[2]);
        duelModel.deleteSpellAndTrap(duelModel.turn, place - 1);
        duelModel.addCardToGraveyard(duelModel.turn, trap);
        ArrayList<Card> graveyard = duelModel.getGraveyard(duelModel.turn);
        for (int i = 0; i < graveyard.size() - 1; i++) {
            Card card = graveyard.get(i);
            if (card.getCategory().equals("Monster")) {
                ArrayList<Card> monsters = duelModel.getMonstersInField().get(duelModel.turn);
                for (int j = 0; j < 5; j++) {
                    if (monsters.get(j) == null) {
                        duelModel.addCertainMonsterFromGraveyardToGame(duelModel.turn, "OO/" + j + 1, j, card);
                        duelModel.deleteCardFromGraveyard(duelModel.turn, i);
                        return;
                    }
                }
            }
        }
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
            duelModel.setSelectedCard(duelModel.turn, duelModel.getFieldZoneCard(duelModel.turn), "My/Filed/"+duelModel.getFieldCondition().get(duelModel.turn).get(0));
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
            if (numberOfTurnExist >= 5) {
                duelModel.deleteSwordsCard(duelModel.turn, swordCard);
            } else {
                entry.setValue(numberOfTurnExist + 1);
            }
        }
        for (Map.Entry<Card, Integer> entry : duelModel.getSwordsCard().get(1 - duelModel.turn).entrySet()) {
            Card swordCard = entry.getKey();
            int numberOfTurnExist = entry.getValue();
            if (numberOfTurnExist >= 5) {
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