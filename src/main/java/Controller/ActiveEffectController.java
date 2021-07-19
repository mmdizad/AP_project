package Controller;

import Model.Card;
import Model.DuelModel;

import java.util.ArrayList;
import java.util.Map;

public class ActiveEffectController {
    private static ActiveEffectController activeEffectController;

    private ActiveEffectController() {

    }

    public static ActiveEffectController getInstance() {
        if (activeEffectController == null) {
            activeEffectController = new ActiveEffectController();
        }
        return activeEffectController;
    }

    public String activateSpellEffectMainController(String input) {
        String tokenOfPlayer = input.split("/")[1];
        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : DuelController.duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    if (duelModel.getSelectedCards().get(duelModel.turn).get(0) == null) {
                        return "no card is selected yet";
                    } else if (!duelModel.getSelectedCards().get(duelModel.turn).get(0).getCategory().equals("Spell")) {
                        return "activate effect is only for spell cards.";
                    } else {
                        Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                        String[] detailOfSelectedCard = duelModel.getDetailOfSelectedCard().get(duelModel.turn)
                                .get(duelModel.getSelectedCards().get(duelModel.turn).get(0)).split("/");
                        if (detailOfSelectedCard[0].equals("Hand")) {
                            if (isSpellZoneFull(duelModel.turn, duelModel)) {
                                return "spell card zone is full";
                            } else
                                return -1 + "/" + card.getName();
                        } else if (detailOfSelectedCard[0].equals("opponent"))
                            return "you cant active your opponent field card";
                        else if (detailOfSelectedCard[0].equals("My") && detailOfSelectedCard[1].equals("O")) {
                            return "you have already activated this card";
                        } else if (detailOfSelectedCard[0].equals("My") && detailOfSelectedCard[1].equals("H")) {
                            return Integer.parseInt(detailOfSelectedCard[2]) + "/" + card.getName() + "/" + card.getCardType();
                        }
                        return "you cant active this card";
                    }
                }
            }
        }
        return "";
    }

    public String effectOfMonsterReborn(String input) {
        int placeOfSpell = Integer.parseInt(input.split("/")[1]);
        String kindOfGraveyard = input.split("/")[2];
        int numberOfCard = Integer.parseInt(input.split("/")[3]);
        String state = input.split("/")[4];
        String tokenOfPlayer = input.split("/")[5];

        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : DuelController.duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    if (!kindOfGraveyard.equals("My") && !kindOfGraveyard.equals("Opponent")) {
                        return "you must enter correct state of card for summon";
                    } else if (kindOfGraveyard.equals("My")) {
                        if (numberOfCard > duelModel.getGraveyard(duelModel.turn).size()) {
                            return "card with this number not available";
                        } else if (!duelModel.getGraveyard(duelModel.turn).get(numberOfCard - 1).getCategory().equals("Monster")) {
                            return "you cant summon this card";
                        } else {
                            if (!state.equals("Defence") && !state.equals("Attack")) {
                                return "please enter the appropriate state (Defence or Attack)";
                            } else {
                                if (isMonsterZoneFull(duelModel.turn, duelModel)) {
                                    return "monster zone is full";
                                }
                                return specialEffectOfMonsterReborn(placeOfSpell, state, numberOfCard, duelModel);
                            }
                        }
                    } else {
                        if (numberOfCard > duelModel.getGraveyard(1 - duelModel.turn).size()) {
                            return "card with this number not available";
                        } else if (!duelModel.getGraveyard(1 - duelModel.turn).get(numberOfCard - 1).getCategory()
                                .equals("Monster")) {
                            return "you cant summon this card";
                        } else {
                            if (!state.equals("Defence") && !state.equals("Attack")) {
                                return "please enter the appropriate state (Defence or Attack)";
                            } else {
                                if (isMonsterZoneFull(duelModel.turn, duelModel)) {
                                    return "monster zone is full";
                                }
                                return specialEffectOfMonsterReborn(placeOfSpell, state, numberOfCard, duelModel);
                            }
                        }
                    }
                }
            }
        }
        return "";
    }

    public String specialEffectOfMonsterReborn(int placeOfSpell, String state, int numberOfCard, DuelModel duelModel) {
        if (placeOfSpell != -1) {
            Card card;
            card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
            duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
            duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
            duelModel.monsterSummonForEffectOfSomeTraps = null;
            if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                specialSummonMonsterOnFieldFromGraveyard(1 - duelModel.turn
                        , state, numberOfCard - 1, duelModel);
                duelModel.effectOfSpellAbsorptionCards();
                duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                duelModel.addCardToGraveyard(duelModel.turn, card);
                duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                return "spell activated";
            }
        } else {
            Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
            String response1 = activeSpellFromHand(duelModel);
            duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
            duelModel.monsterSummonForEffectOfSomeTraps = null;
            if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                specialSummonMonsterOnFieldFromGraveyard(1 - duelModel.turn
                        , state, numberOfCard - 1, duelModel);
                duelModel.effectOfSpellAbsorptionCards();
                int indexOfSpell = duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).indexOf(card);
                duelModel.deleteSpellAndTrap(duelModel.turn, indexOfSpell);
                duelModel.addCardToGraveyard(duelModel.turn, card);
                duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                return response1;
            }
        }
        return "";
    }

    public String effectOfTerraforming(String input) {
        int placeOfSpell = Integer.parseInt(input.split("/")[1]);
        String tokenOfPlayer = input.split("/")[2];

        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : DuelController.duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    Card card = null;
                    ArrayList<Card> cardsInDeckOfPlayer = duelModel.getPlayersCards().get(duelModel.turn);
                    for (Card card1 : cardsInDeckOfPlayer) {
                        if (card1 != null) {
                            if (card1.getCategory().equals("Spell")) {
                                if (card1.getCardType().equals("Field")) {
                                    card = card1;
                                    break;
                                }
                            }
                        }
                    }
                    if (card == null) {
                        return "you dont have any FieldCard for add to your hand";
                    } else {
                        Card card1 = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                        if (placeOfSpell != -1) {
                            duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                            duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card1, false);
                            if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card1)) {
                                duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card1);
                                duelModel.addCardFromDeckToHandInMiddleOfGame(duelModel.turn, card);
                                duelModel.effectOfSpellAbsorptionCards();
                                duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                                duelModel.addCardToGraveyard(duelModel.turn, card1);
                                return "spell activated";
                            }
                        } else {
                            String response = activeSpellFromHand(duelModel);
                            duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card1, false);
                            if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card1)) {
                                duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card1);
                                duelModel.addCardFromDeckToHandInMiddleOfGame(duelModel.turn, card);
                                duelModel.effectOfSpellAbsorptionCards();
                                int indexOfSpell = duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).indexOf(card1);
                                duelModel.deleteSpellAndTrap(duelModel.turn, indexOfSpell);
                                duelModel.addCardToGraveyard(duelModel.turn, card1);
                                return response;
                            }
                        }
                        duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card1);
                    }
                }
            }
        }
        return "";
    }


    public String effectOfPotOfGreed(String input) {
        int placeOfSpell = Integer.parseInt(input.split("/")[1]);
        String tokenOfPlayer = input.split("/")[2];

        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : DuelController.duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    ArrayList<Card> cardsInDeckOfPlayer = duelModel.getPlayersCards().get(duelModel.turn);
                    if (cardsInDeckOfPlayer.size() < 2) {
                        return "you dont have enough card for add to your hand";
                    } else {
                        Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                        if (placeOfSpell != -1) {
                            duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                            duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
                            if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                                duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                                specialEffectOfPotOfGreed(duelModel);
                                duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                                duelModel.addCardToGraveyard(duelModel.turn, card);
                                return "spell activated";
                            }
                        } else {
                            String response = activeSpellFromHand(duelModel);
                            duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
                            if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                                duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                                specialEffectOfPotOfGreed(duelModel);
                                int indexOfSpell = duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).indexOf(card);
                                duelModel.deleteSpellAndTrap(duelModel.turn, indexOfSpell);
                                duelModel.addCardToGraveyard(duelModel.turn, card);
                                return response;
                            }
                        }
                        duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                    }
                }
            }
        }
        return "";
    }


    public void specialEffectOfPotOfGreed(DuelModel duelModel) {
        duelModel.addCardFromDeckToHandInMiddleOfGame(duelModel.turn
                , duelModel.getPlayersCards().get(duelModel.turn).get(duelModel.
                        getPlayersCards().get(duelModel.turn).size() - 1));
        duelModel.addCardFromDeckToHandInMiddleOfGame(duelModel.turn
                , duelModel.getPlayersCards().get(duelModel.turn).get(duelModel.
                        getPlayersCards().get(duelModel.turn).size() - 1));
        duelModel.effectOfSpellAbsorptionCards();
    }

    public String effectOfRaigeki(String input) {
        int placeOfSpell = Integer.parseInt(input.split("/")[1]);
        String tokenOfPlayer = input.split("/")[2];

        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : DuelController.duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    boolean opponentHasAnyMonster = false;
                    ArrayList<Card> monstersInFieldOfPlayer = duelModel.getMonstersInField().get(1 - duelModel.turn);

                    for (Card card : monstersInFieldOfPlayer) {
                        if (card != null) {
                            opponentHasAnyMonster = true;
                        }
                    }
                    if (opponentHasAnyMonster) {
                        Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                        if (placeOfSpell != -1) {
                            duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                            duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
                            if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                                duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                                deleteAllMonsters(1 - duelModel.turn, duelModel);
                                duelModel.effectOfSpellAbsorptionCards();
                                duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                                duelModel.addCardToGraveyard(duelModel.turn, card);
                                return "spell activated";
                            }
                        } else {
                            String response = activeSpellFromHand(duelModel);
                            duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
                            if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                                duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                                deleteAllMonsters(1 - duelModel.turn, duelModel);
                                duelModel.effectOfSpellAbsorptionCards();
                                int indexOfSpell = duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).indexOf(card);
                                duelModel.deleteSpellAndTrap(duelModel.turn, indexOfSpell);
                                duelModel.addCardToGraveyard(duelModel.turn, card);
                                return response;
                            }
                        }
                        duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                    } else {
                        return "your opponent dont have any monster";
                    }
                }
            }
        }
        return "";
    }

    public String effectOfChangeOfHeart(String input) {
        int placeOfSpell = Integer.parseInt(input.split("/")[1]);
        int numberOfMonsterCard = Integer.parseInt(input.split("/")[2]);
        String tokenOfPlayer = input.split("/")[3];

        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : DuelController.duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    if (numberOfMonsterCard > 5) {
                        return "you cant get card with this address";
                    } else if (duelModel.getMonstersInField().get(1 - duelModel.turn).get(numberOfMonsterCard - 1) == null) {
                        return "you cant get card with this address";
                    } else {
                        if (isMonsterZoneFull(duelModel.turn, duelModel)) {
                            return "monster zone full";
                        } else {
                            Card borrowCard = duelModel.getMonstersInField().get(1 - duelModel.turn).get(numberOfMonsterCard - 1);
                            String detailsOfBorrowCard = duelModel.getMonsterCondition(1 - duelModel.turn, numberOfMonsterCard);
                            Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                            if (placeOfSpell != -1) {
                                duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                                duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
                                if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                                    duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                                    duelModel.deleteMonster(1 - duelModel.turn, numberOfMonsterCard - 1);
                                    specialEffectOfChangeOfHeart(borrowCard, detailsOfBorrowCard, duelModel);
                                    duelModel.effectOfSpellAbsorptionCards();
                                    duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                                    duelModel.addCardToGraveyard(duelModel.turn, card);
                                    return "spell activated";
                                }
                            } else {
                                String response = activeSpellFromHand(duelModel);
                                duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
                                if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                                    duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                                    duelModel.deleteMonster(1 - duelModel.turn, numberOfMonsterCard - 1);
                                    specialEffectOfChangeOfHeart(borrowCard, detailsOfBorrowCard, duelModel);
                                    duelModel.effectOfSpellAbsorptionCards();
                                    int indexOfSpell = duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).indexOf(card);
                                    duelModel.deleteSpellAndTrap(duelModel.turn, indexOfSpell);
                                    duelModel.addCardToGraveyard(duelModel.turn, card);
                                    return response;
                                }
                            }
                            duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                        }
                    }
                }
            }
        }
        return "";
    }


    public void specialEffectOfChangeOfHeart(Card borrowCard, String detailsOfBorrowCard, DuelModel duelModel) {
        String[] details = detailsOfBorrowCard.split("/");
        String stateOfBorrowCard = details[0];
        if (duelModel.getMonstersInField().get(duelModel.turn).get(0) == null) {
            duelModel.getMonstersInField().get(duelModel.turn).set(0, borrowCard);
            duelModel.addMonsterCondition(duelModel.turn, 0, stateOfBorrowCard + "/1");
            duelModel.addBorrowCard(borrowCard, detailsOfBorrowCard + "/" + 1);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(1) == null) {
            duelModel.getMonstersInField().get(duelModel.turn).set(1, borrowCard);
            duelModel.addMonsterCondition(duelModel.turn, 1, stateOfBorrowCard + "/2");
            duelModel.addBorrowCard(borrowCard, detailsOfBorrowCard + "/" + 2);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(2) == null) {
            duelModel.getMonstersInField().get(duelModel.turn).set(2, borrowCard);
            duelModel.addMonsterCondition(duelModel.turn, 2, stateOfBorrowCard + "/3");
            duelModel.addBorrowCard(borrowCard, detailsOfBorrowCard + "/" + 3);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(3) == null) {
            duelModel.getMonstersInField().get(duelModel.turn).set(3, borrowCard);
            duelModel.addMonsterCondition(duelModel.turn, 3, stateOfBorrowCard + "/4");
            duelModel.addBorrowCard(borrowCard, detailsOfBorrowCard + "/" + 4);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(4) == null) {
            duelModel.getMonstersInField().get(duelModel.turn).set(4, borrowCard);
            duelModel.addMonsterCondition(duelModel.turn, 4, stateOfBorrowCard + "/5");
            duelModel.addBorrowCard(borrowCard, detailsOfBorrowCard + "/" + 5);
        }
    }

    public void refundsTheBorrowCards(DuelModel duelModel) {
        ArrayList<Card> borrowCards = duelModel.getBorrowCards();
        ArrayList<String> conditionsOfBorrowCards = duelModel.getConditionOfBorrowCards();
        int i = 0;
        for (Card borrowCard : borrowCards) {
            String[] detailsOfBorrowCard = conditionsOfBorrowCards.get(i).split("/");
            String stateOfBorrowCard = detailsOfBorrowCard[0];
            int placeOfBorrowCard = Integer.parseInt(detailsOfBorrowCard[1]);
            int placeOfFieldThatBorrowCardSet = Integer.parseInt(detailsOfBorrowCard[2]);
            duelModel.deleteMonster(1 - duelModel.turn, placeOfFieldThatBorrowCardSet - 1);
            duelModel.getMonstersInField().get(duelModel.turn).set(placeOfBorrowCard - 1, borrowCard);
            duelModel.addMonsterCondition(duelModel.turn, placeOfBorrowCard - 1, stateOfBorrowCard
                    + "/" + placeOfBorrowCard);
            i++;
        }
        duelModel.deleteBorrowCard();
    }


    public String effectOfHarpiesFeatherDuster(String input) {
        int placeOfSpell = Integer.parseInt(input.split("/")[1]);
        String tokenOfPlayer = input.split("/")[2];

        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : DuelController.duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    ArrayList<Card> spellAndTraps = duelModel.getSpellsAndTrapsInFiled().get(1 - duelModel.turn);
                    boolean hasSpellOrTrapCard = false;
                    for (Card card : spellAndTraps) {
                        if (card != null) {
                            hasSpellOrTrapCard = true;
                            break;
                        }
                    }
                    if (!hasSpellOrTrapCard) {
                        return "your opponent dont have any spell or trap";
                    } else {
                        Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                        if (placeOfSpell != -1) {
                            duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                            duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
                            if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                                duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                                specialEffectOfHarpiesFeatherDuster(duelModel);
                                duelModel.effectOfSpellAbsorptionCards();
                                duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                                duelModel.addCardToGraveyard(duelModel.turn, card);
                                return "spell activated";
                            }
                        } else {
                            String response = activeSpellFromHand(duelModel);
                            duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
                            if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                                duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                                specialEffectOfHarpiesFeatherDuster(duelModel);
                                duelModel.effectOfSpellAbsorptionCards();
                                int indexOfSpell = duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).indexOf(card);
                                duelModel.deleteSpellAndTrap(duelModel.turn, indexOfSpell);
                                duelModel.addCardToGraveyard(duelModel.turn, card);
                                return response;
                            }
                        }
                        duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                    }
                }
            }
        }
        return "";
    }

    public void specialEffectOfHarpiesFeatherDuster(DuelModel duelModel) {
        ArrayList<Card> spellAndTraps = duelModel.getSpellsAndTrapsInFiled().get(1 - duelModel.turn);
        int i = 0;
        for (Card card : spellAndTraps) {
            if (card != null) {
                if (card.getName().equals("Swords of Revealing Light")) {
                    duelModel.deleteSwordsCard(1 - duelModel.turn, card);
                } else if (card.getName().equals("Supply Squad")) {
                    duelModel.deleteSupplySquadCard(1 - duelModel.turn, card);
                } else if (card.getName().equals("Spell Absorption")) {
                    duelModel.deleteSpellAbsorptionCards(1 - duelModel.turn, card);
                } else if (card.getName().equals("Messenger of peace")) {
                    duelModel.deleteMessengerOfPeaceCards(1 - duelModel.turn, card);
                } else {
                    duelModel.deleteSpellAndTrap(1 - duelModel.turn, i);
                    duelModel.addCardToGraveyard(1 - duelModel.turn, card);
                }
                for (Map.Entry<Card, Boolean> entry : duelModel.getSpellOrTrapActivated().get(1 - duelModel.turn).entrySet()) {
                    Card spellCardActivated = entry.getKey();
                    if (spellCardActivated == card) {
                        duelModel.getSpellOrTrapActivated().get(1 - duelModel.turn).put(card, true);
                    }
                }
            }
            i++;
        }
    }

    public String effectOfSwordsOfRevealingLight(String input) {
        int placeOfSpell = Integer.parseInt(input.split("/")[1]);
        String tokenOfPlayer = input.split("/")[2];

        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : DuelController.duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                    if (placeOfSpell != -1) {
                        duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                        duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
                        if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                            duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                            duelModel.setSwordsCard(duelModel.turn, card);
                            changeStateOfMonsterWithSwordsCard(1 - duelModel.turn, duelModel);
                            duelModel.effectOfSpellAbsorptionCards();
                            return "spell activated";
                        }
                    } else {
                        String response = activeSpellFromHand(duelModel);
                        duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
                        if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                            duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                            duelModel.setSwordsCard(duelModel.turn, card);
                            changeStateOfMonsterWithSwordsCard(1 - duelModel.turn, duelModel);
                            duelModel.effectOfSpellAbsorptionCards();
                            return response;
                        }
                    }
                    duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                }
            }
        }
        return "";
    }


    public void changeStateOfMonsterWithSwordsCard(int turn, DuelModel duelModel) {
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

    public String effectOfDarkHole(String input) {
        int placeOfSpell = Integer.parseInt(input.split("/")[1]);
        String tokenOfPlayer = input.split("/")[2];

        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : DuelController.duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    boolean opponentHasAnyMonster = false;
                    boolean youHaveAnyMonster = false;
                    ArrayList<Card> monstersInField1 = duelModel.getMonstersInField().get(1 - duelModel.turn);
                    for (Card card : monstersInField1) {
                        if (card != null) {
                            opponentHasAnyMonster = true;
                            break;
                        }
                    }
                    ArrayList<Card> monstersInField2 = duelModel.getMonstersInField().get(duelModel.turn);
                    for (Card card : monstersInField2) {
                        if (card != null) {
                            youHaveAnyMonster = true;
                            break;
                        }
                    }
                    if (opponentHasAnyMonster || youHaveAnyMonster) {
                        Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                        if (placeOfSpell != -1) {
                            duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                            duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
                            if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                                duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                                deleteAllMonsters(duelModel.turn, duelModel);
                                deleteAllMonsters(1 - duelModel.turn, duelModel);
                                duelModel.effectOfSpellAbsorptionCards();
                                duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                                duelModel.addCardToGraveyard(duelModel.turn, card);
                                return "spell activated";
                            }
                        } else {
                            String response = activeSpellFromHand(duelModel);
                            duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
                            if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                                duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                                deleteAllMonsters(duelModel.turn, duelModel);
                                deleteAllMonsters(1 - duelModel.turn, duelModel);
                                duelModel.effectOfSpellAbsorptionCards();
                                int indexOfSpell = duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).indexOf(card);
                                duelModel.deleteSpellAndTrap(duelModel.turn, indexOfSpell);
                                duelModel.addCardToGraveyard(duelModel.turn, card);
                                return response;
                            }
                        }
                        duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                    } else {
                        return "you and your opponent dont have any monsters";
                    }
                }
            }
        }
        return "";
    }

    public String effectOfSupplySquad(String input) {
        int placeOfSpell = Integer.parseInt(input.split("/")[1]);
        String tokenOfPlayer = input.split("/")[2];

        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : DuelController.duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                    if (placeOfSpell != -1) {
                        duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                        duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
                        duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
                        if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                            duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                            duelModel.setSupplySquad(duelModel.turn, card);
                            duelModel.effectOfSpellAbsorptionCards();
                            return "spell activated";
                        }
                    } else {
                        String response = activeSpellFromHand(duelModel);
                        duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
                        if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                            duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                            duelModel.setSupplySquad(duelModel.turn, card);
                            duelModel.effectOfSpellAbsorptionCards();
                            return response;
                        }
                    }
                    duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                }
            }
        }
        return "";
    }

    public String effectOfSpellAbsorption(String input) {
        int placeOfSpell = Integer.parseInt(input.split("/")[1]);
        String tokenOfPlayer = input.split("/")[2];

        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : DuelController.duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                    if (placeOfSpell != -1) {
                        duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                        duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
                        if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                            duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                            duelModel.effectOfSpellAbsorptionCards();
                            duelModel.setSpellAbsorptionCards(duelModel.turn, card);
                            return "spell activated";
                        }
                    } else {
                        String response = activeSpellFromHand(duelModel);
                        duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
                        if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                            duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                            duelModel.effectOfSpellAbsorptionCards();
                            duelModel.setSpellAbsorptionCards(duelModel.turn, card);
                            return response;
                        }
                    }
                    duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                }
            }
        }
        return "";
    }

    public String effectOfMessengerOfPeace(String input) {
        int placeOfSpell = Integer.parseInt(input.split("/")[1]);
        String tokenOfPlayer = input.split("/")[2];

        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : DuelController.duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                    if (placeOfSpell != -1) {
                        duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                        duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
                        if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                            duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                            duelModel.setMessengerOfPeace(duelModel.turn, card);
                            duelModel.effectOfSpellAbsorptionCards();
                            return "spell activated";
                        }
                    } else {
                        String response = activeSpellFromHand(duelModel);
                        duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
                        if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                            duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                            duelModel.setMessengerOfPeace(duelModel.turn, card);
                            duelModel.effectOfSpellAbsorptionCards();
                            return response;
                        }
                    }
                    duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                }
            }
        }
        return "";
    }

    public String effectOfMysticalSpaceTyphoon(String input) {
        int placeOfSpell = Integer.parseInt(input.split("/")[1]);
        String response = input.split("/")[2];
        String tokenOfPlayer = input.split("/")[3];

        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : DuelController.duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    if (hasSpellSetInThisTurn(duelModel)) {
                        return "preparations of this spell are not done yet";
                    }
                    Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                    if (placeOfSpell != -1) {
                        duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                    } else {
                        activeSpellFromHand(duelModel);
                    }
                    duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
                    if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                        duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                        String[] splitResponse = response.split(" ");
                        int placeOfSpellOrTrapCard = Integer.parseInt(splitResponse[1]);
                        if (splitResponse[0].equals("my")) {
                            return deleteASpellCardInActiveSpell(duelModel.turn, placeOfSpell, placeOfSpellOrTrapCard, duelModel);
                        } else if (splitResponse[0].equals("opponent")) {
                            return deleteASpellCardInActiveSpell(1 - duelModel.turn, placeOfSpell, placeOfSpellOrTrapCard, duelModel);
                        } else {
                            return "you must enter my/opponent";
                        }
                    }
                    duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                }
            }
        }
        return "";
    }

    public String effectOfRingOfDefense(String input) {
        int placeOfSpell = Integer.parseInt(input.split("/")[1]);
        String tokenOfPlayer = input.split("/")[2];

        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : DuelController.duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    if (hasSpellSetInThisTurn(duelModel)) {
                        return "preparations of this spell are not done yet";
                    } else {
                        Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                        if (placeOfSpell != -1) {
                            duelModel.changePositionOfSpellOrTrapCard(duelModel.turn, placeOfSpell);
                        } else {
                            activeSpellFromHand(duelModel);
                        }
                        duelModel.getSpellOrTrapActivated().get(duelModel.turn).put(card, false);
                        if (!duelModel.getSpellOrTrapActivated().get(duelModel.turn).get(card)) {
                            duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                            duelModel.effectOfSpellAbsorptionCards();
                            return "spell activated";
                        }
                        duelModel.getSpellOrTrapActivated().get(duelModel.turn).remove(card);
                    }
                }
            }
        }
        return "";
    }


    public String deleteASpellCardInActiveSpell(int turn, int placeOfSpell, int placeOfSpellOrTrapCard, DuelModel duelModel) {
        if (placeOfSpellOrTrapCard > 5) {
            return "you must enter correct address";
        } else {
            Card card1 = duelModel.getSpellsAndTrapsInFiled().get(turn).get(placeOfSpellOrTrapCard - 1);
            if (card1 == null) {
                return "you cant destroyed card with this address";
            } else {
                Card spellCard = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                if (placeOfSpell != -1) {
                    deleteASpell(turn, placeOfSpellOrTrapCard, card1, duelModel);
                    duelModel.effectOfSpellAbsorptionCards();
                    duelModel.deleteSpellAndTrap(duelModel.turn, placeOfSpell - 1);
                    duelModel.addCardToGraveyard(duelModel.turn, spellCard);
                    return "spell activated";
                } else {
                    deleteASpell(turn, placeOfSpellOrTrapCard, card1, duelModel);
                    duelModel.effectOfSpellAbsorptionCards();
                    int indexOfSpell = duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).indexOf(spellCard);
                    duelModel.deleteSpellAndTrap(duelModel.turn, indexOfSpell);
                    duelModel.addCardToGraveyard(duelModel.turn, spellCard);
                    return "spell activated";
                }
            }
        }
    }

    public void deleteASpell(int turn, int placeOfSpellOrTrapCard, Card card, DuelModel duelModel) {
        if (card.getName().equals("Swords of Revealing Light")) {
            duelModel.deleteSwordsCard(turn, card);
        } else if (card.getName().equals("Supply Squad")) {
            duelModel.deleteSupplySquadCard(turn, card);
        } else if (card.getName().equals("Spell Absorption")) {
            duelModel.deleteSpellAbsorptionCards(turn, card);
        } else if (card.getName().equals("Messenger of peace")) {
            duelModel.deleteMessengerOfPeaceCards(turn, card);
        } else {
            duelModel.deleteSpellAndTrap(turn, placeOfSpellOrTrapCard - 1);
            duelModel.addCardToGraveyard(turn, card);
        }
        for (Map.Entry<Card, Boolean> entry : duelModel.getSpellOrTrapActivated().get(turn).entrySet()) {
            Card spellCardActivated = entry.getKey();
            if (spellCardActivated == card) {
                duelModel.getSpellOrTrapActivated().get(turn).put(card, true);
            }
        }
    }

    public boolean hasSpellSetInThisTurn(DuelModel duelModel) {
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

    public void deleteAllMonsters(int turn, DuelModel duelModel) {
        ArrayList<Card> monstersInFieldOfPlayer = duelModel.getMonstersInField().get(turn);
        int i = 0;
        for (Card card : monstersInFieldOfPlayer) {
            if (card != null) {
                duelModel.deleteMonster(turn, i);
                duelModel.addCardToGraveyard(turn, card);
            }
            i++;
        }
    }

    public boolean isSpellZoneFull(int turn, DuelModel duelModel) {
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

    public boolean isMonsterZoneFull(int turn, DuelModel duelModel) {
        boolean isMonsterZoneFull = true;
        ArrayList<Card> monsterInFiledPlayer = duelModel.getMonstersInField().get(turn);
        for (Card card : monsterInFiledPlayer) {
            if (card == null) {
                isMonsterZoneFull = false;
                break;
            }
        }
        return isMonsterZoneFull;
    }

    public void specialSummonMonsterOnFieldFromGraveyard(int turn, String state, int indexOfCardOfGraveyard, DuelModel duelModel) {
        String stateOfCard = "OO";
        if (state.equals("Attack")) {
            stateOfCard = "OO";
        } else if (state.equals("Defence")) {
            stateOfCard = "DO";
        }
        Card card = duelModel.getGraveyard(duelModel.turn).get(indexOfCardOfGraveyard);
        if (duelModel.getMonstersInField().get(duelModel.turn).get(0) == null) {
            duelModel.addMonsterFromGraveyardToGame(stateOfCard + "/1", card, 0);
            duelModel.deleteCardFromGraveyard(turn, indexOfCardOfGraveyard);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(1) == null) {
            duelModel.addMonsterFromGraveyardToGame(stateOfCard + "/2", card, 1);
            duelModel.deleteCardFromGraveyard(turn, indexOfCardOfGraveyard);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(2) == null) {
            duelModel.addMonsterFromGraveyardToGame(stateOfCard + "/3", card, 2);
            duelModel.deleteCardFromGraveyard(turn, indexOfCardOfGraveyard);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(3) == null) {
            duelModel.addMonsterFromGraveyardToGame(stateOfCard + "/4", card, 3);
            duelModel.deleteCardFromGraveyard(turn, indexOfCardOfGraveyard);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(4) == null) {
            duelModel.addMonsterFromGraveyardToGame(stateOfCard + "/5", card, 4);
            duelModel.deleteCardFromGraveyard(turn, indexOfCardOfGraveyard);
        }
        if (card.getName().equals("Command knight")) {
            DuelController.getInstance().effectOfCommandKnight(duelModel);
        }
        activeFieldInGame(duelModel);
        duelModel.monsterSummonForEffectOfSomeTraps = card;
    }


    public String activeSpellFromHand(DuelModel duelModel) {
        if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(0) == null) {
            duelModel.addSpellAndTrapFromHandToGame("O/1", 0);
        } else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(1) == null) {
            duelModel.addSpellAndTrapFromHandToGame("O/2", 1);
        } else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(2) == null) {
            duelModel.addSpellAndTrapFromHandToGame("O/3", 2);
        } else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(3) == null) {
            duelModel.addSpellAndTrapFromHandToGame("O/4", 3);
        } else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(4) == null) {
            duelModel.addSpellAndTrapFromHandToGame("O/5", 4);
        }
        return "spell activated";
    }

    public void activeFieldInGame(DuelModel duelModel) {
        // TODO By Erfan
    }
}

