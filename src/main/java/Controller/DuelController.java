package Controller;

import Model.*;

import java.util.*;

public class DuelController {
    public static ArrayList<DuelModel> duelModels;
    private static DuelController duelController;

    private DuelController() {

    }

    public static DuelController getInstance() {
        if (duelController == null)
            duelController = new DuelController();

        return duelController;
    }

    static {
        duelModels = new ArrayList<>();
    }

    public int selectFirstPlayer(String input) {
        String[] partsOfInput = input.split("/");
        String secondPlayerUsername = partsOfInput[1];
        String token = partsOfInput[2];
        if (LoginAndSignUpController.loggedInUsers.containsKey(token)) {
            ArrayList<Integer> someRandomNumbers = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                someRandomNumbers.add(i + 1);
            }
            Collections.shuffle(someRandomNumbers);
            int starterGame = someRandomNumbers.get(0);
            User user = LoginAndSignUpController.loggedInUsers.get(token);
            DuelModel duelModel;
            if (starterGame % 2 == 0) {
                duelModel = new DuelModel(user.getUsername(), secondPlayerUsername);
            } else {
                duelModel = new DuelModel(secondPlayerUsername, user.getUsername());
            }
            duelModels.add(duelModel);
            return starterGame;
        }
        return -1;
    }

    public String newCardToHand(String input) {
        String playerUsername = input.split("/")[1];
        String tokenOfPlayer = input.split("/")[2];

        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            User user = User.getUserByUsername(playerUsername);
            assert user != null;
            Deck deck = user.getActiveDeck();
            ArrayList<Card> cardsInDeck = deck.getCardsMain();

            if (cardsInDeck.size() >= 1) {
                for (DuelModel duelModel : duelModels) {
                    if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername))
                        return duelModel.addCardToHand();
                }
            }
        }
        return "";
    }

    public String selectMonster(String input) {
        String tokenOfPlayer = input.split("/")[2];
        int placeOfMonster = Integer.parseInt(input.split("/")[1]);
        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    if (duelModel.getMonster(duelModel.turn, placeOfMonster) == null) {
                        return "no card found in the given position";
                    } else {
                        String condition = "My/";
                        condition = condition + duelModel.getMonsterCondition(duelModel.turn, placeOfMonster);
                        duelModel.setSelectedCard(duelModel.turn, duelModel.getMonster(duelModel.turn, placeOfMonster), condition);
                        return "card selected";
                    }
                }
            }
        }
        return "";
    }

    public String selectOpponentMonster(String input) {
        String tokenOfPlayer = input.split("/")[2];
        int placeOfMonster = Integer.parseInt(input.split("/")[1]);
        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    if (duelModel.getMonster(1 - duelModel.turn, placeOfMonster) == null) {
                        return "no card found in the given position";
                    } else {
                        String condition = "Opponent/";
                        condition = condition + duelModel.getMonsterCondition(1 - duelModel.turn, placeOfMonster);
                        duelModel.setSelectedCard(duelModel.turn, duelModel.getMonster(1 - duelModel.turn, placeOfMonster),
                                condition);
                        return "card selected";
                    }
                }
            }
        }
        return "";
    }

    public String selectSpellOrTrap(String input) {
        String tokenOfPlayer = input.split("/")[2];
        int placeOfSpellOrTrap = Integer.parseInt(input.split("/")[1]);
        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    if (duelModel.getSpellAndTrap(duelModel.turn, placeOfSpellOrTrap) == null) {
                        return "no card found in the given position";
                    } else {
                        String condition = "My/";
                        condition = condition + duelModel.getMonsterCondition(duelModel.turn, placeOfSpellOrTrap);
                        duelModel.setSelectedCard(duelModel.turn, duelModel.getSpellAndTrap(duelModel.turn,
                                placeOfSpellOrTrap), condition);
                        return "card selected";
                    }
                }
            }
        }
        return "";
    }

    public String selectOpponentSpellOrTrap(String input) {
        String tokenOfPlayer = input.split("/")[2];
        int placeOfSpellOrTrap = Integer.parseInt(input.split("/")[1]);
        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    if (duelModel.getSpellAndTrap(1 - duelModel.turn, placeOfSpellOrTrap) == null) {
                        return "no card found in the given position";
                    } else {
                        String condition = "Opponent/";
                        condition = condition + duelModel.getMonsterCondition(1 - duelModel.turn, placeOfSpellOrTrap);
                        duelModel.setSelectedCard(duelModel.turn, duelModel.getSpellAndTrap(1 - duelModel.turn,
                                placeOfSpellOrTrap), condition);
                        return "card selected";
                    }
                }
            }
        }
        return "";
    }

    public String selectFieldZone(String input) {
        String tokenOfPlayer = input.split("/")[2];
        int place = Integer.parseInt(input.split("/")[1]);
        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    if (duelModel.getField().get(place) == null) {
                        return "no card found in the given position";
                    } else {
                        duelModel.setSelectedCard(duelModel.turn, duelModel.getFieldZoneCard(duelModel.turn), "My/Filed/" + place);
                        return "card selected";
                    }
                }
            }
        }
        return "";
    }

    public String selectOpponentFieldZone(String input) {
        String tokenOfPlayer = input.split("/")[2];
        int place = Integer.parseInt(input.split("/")[1]);
        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    if (duelModel.getFieldZoneCard(1 - duelModel.turn) == null) {
                        return "no card found in the given position";
                    } else {
                        duelModel.setSelectedCard(duelModel.turn, duelModel.getFieldZoneCard(1 - duelModel.turn),
                                "Opponent/Filed/" + place);
                        return "card selected";
                    }
                }
            }
        }
        return "";
    }

    public String selectHand(String input) {
        String tokenOfPlayer = input.split("/")[2];
        int placeOfCard = Integer.parseInt(input.split("/")[1]);
        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    if (duelModel.getHandCards().get(duelModel.turn).size() < placeOfCard) {
                        return "invalid selection";
                    } else {
                        duelModel.setSelectedCard(duelModel.turn, duelModel.getHandCards().get(duelModel.turn).
                                get(placeOfCard - 1), "Hand");
                        return "card selected";
                    }
                }
            }
        }
        return "";
    }

    public String deselect(String input) {
        String tokenOfPlayer = input.split("/")[1];
        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    if (duelModel.getSelectedCards().get(duelModel.turn).get(0) == null) {
                        return "no card is selected yet";
                    } else {
                        duelModel.deSelectedCard();
                        return "card deselected";
                    }
                }
            }
        }
        return "";
    }


    public String summon(String input) {
        String response = "";
        String tokenOfPlayer = input.split("/")[1];
        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    if (duelModel.getSelectedCards().get(duelModel.turn).get(0) == null) {
                        return "no card is selected yet";
                    } else if (!duelModel.getSelectedCards().get(duelModel.turn).get(0).getCategory().equals("Monster")) {
                        return "you can’t summon this card";
                    } else {
                        Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                        String detailsOfSelectedCard = duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(card);
                        if (!detailsOfSelectedCard.equals("Hand")) {
                            return "you can’t summon this card";
                        } else if (card.getCardType().equals("Ritual") || card.isHasSpecialSummon()) {
                            return "you can’t summon this card";
                        } else if (duelModel.getMonsterSetOrSummonInThisTurn() != null) {
                            return "you already summoned/set on this turn";
                        } else if (card.getLevel() <= 4 && !card.getName().equals("Terratiger, the Empowered Warrior")) {
                            response = normalSummonMonsterOnField(card, "Attack", duelModel);
                            return response;
                        } else if (card.getName().equals("Terratiger, the Empowered Warrior")) {
                            response = normalSummonMonsterOnField(card, "Attack", duelModel) + "/" + "summon Terratiger, the Empowered Warrior";
                            return response;
                        } else if (card.getLevel() == 5 || card.getLevel() == 6) {
                            if (getNumberOfMonstersInPlayerField(duelModel) >= 1) {
                                return "Normal Summon Card With Level 5 or 6";
                            }
                            return "there are not enough cards for tribute";
                        } else if (card.getName().equals("Beast King Barbaros")) {
                            return "Summon Beast King Barbaros";
                        } else if (card.getLevel() >= 7) {
                            return "Summon Monster With Level 7 Or More";
                        }
                    }
                }
            }
        }
        return "";
    }

    public String normalSummonCardThatCanSummonAnotherCard(String input) {
        String response = input.split("/")[1];
        String tokenOfPlayer = input.split("/")[2];
        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    if (!response.equals("NO") && !response.equals("YES")) {
                        return "Please enter correct response";
                    } else if (response.equals("YES")) {
                        if (duelModel.getSelectedCards().get(duelModel.turn).get(0) == null) {
                            return "no card is selected yet";
                        } else if (!duelModel.getSelectedCards().get(duelModel.turn).get(0).getCategory().equals("Monster")) {
                            return "you can’t summon this card";
                        } else {
                            Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                            String detailsOfSelectedCard = duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(card);
                            if (!detailsOfSelectedCard.equals("Hand")) {
                                return "you can’t summon this card";
                            } else if (card.getCardType().equals("Ritual") || card.isHasSpecialSummon()) {
                                return "you can’t summon this card";
                            } else if (card.getLevel() > 4) {
                                return "you can’t summon this card";
                            } else {
                                return normalSummonMonsterOnField(card, "Defence", duelModel);
                            }
                        }
                    }
                }
            }
        }
        return "";
    }

    public String normalSummonMonsterWithLevel5or6(String input) {
        int address = Integer.parseInt(input.split("/")[1]);
        String stateOfCard = input.split("/")[2];
        String tokenOfPlayer = input.split("/")[3];
        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    if (address > 5) {
                        return "there no monsters one this address";
                    } else if (duelModel.getMonstersInField().get(duelModel.turn).get(address - 1) == null) {
                        return "there no monsters one this address";
                    } else {
                        if (!stateOfCard.equals("Defence") && !stateOfCard.equals("Attack")) {
                            return "please enter the appropriate state (Defence or Attack)";
                        } else {
                            Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                            duelModel.deleteMonster(duelModel.turn, address - 1);
                            duelModel.addCardToGraveyard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                                    address));
                            return normalSummonMonsterOnField(card, stateOfCard, duelModel);
                        }
                    }
                }
            }
        }
        return "";
    }


    public String summonMonsterHasTwoMethods(String input) {
        String response = input.split("/")[1];
        int address1 = Integer.parseInt(input.split("/")[2]);
        int address2 = Integer.parseInt(input.split("/")[3]);
        int address3 = Integer.parseInt(input.split("/")[4]);
        String stateOfCard = input.split("/")[5];
        String tokenOfPlayer = input.split("/")[6];

        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    Card monster = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                    if (!response.equals("NO") && !response.equals("YES")) {
                        return "Please enter correct response";
                    } else if (response.equals("NO")) {
                        monster.setAttackPower(1900);
                        duelModel.monsterFlipSummonOrNormalSummonForTrapHole = monster;
                        return normalSummonMonsterOnField(monster, "Attack", duelModel);
                    } else {
                        if (address1 > 5 || address2 > 5 || address3 > 5 || address1 == address2 || address2 == address3
                                || address1 == address3) {
                            return "there is no monster on one of these addresses";
                        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(address1 - 1) == null
                                || duelModel.getMonstersInField().get(duelModel.turn).get(address2 - 1) == null ||
                                duelModel.getMonstersInField().get(duelModel.turn).get(address3 - 1) == null) {
                            return "there is no monster on one of these addresses";
                        } else {
                            if (!stateOfCard.equals("Defence") && !stateOfCard.equals("Attack")) {
                                return "please enter the appropriate state (Defence or Attack)";
                            } else {
                                duelModel.deleteMonster(duelModel.turn, address1 - 1);
                                duelModel.addCardToGraveyard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                                        address1));
                                duelModel.deleteMonster(duelModel.turn, address2 - 1);
                                duelModel.addCardToGraveyard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                                        address2));
                                duelModel.deleteMonster(duelModel.turn, address3 - 1);
                                duelModel.addCardToGraveyard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                                        address3));
                                for (int i = 0; i < 5; i++) {
                                    duelModel.deleteMonster(1 - duelModel.turn, i);
                                    duelModel.deleteSpellAndTrap(1 - duelModel.turn, i);
                                }
                                return normalSummonMonsterOnField(monster, stateOfCard, duelModel);
                            }
                        }
                    }
                }
            }
        }
        return "";
    }

    public String summonCardWithLevel7orMore(String input) {
        int address = Integer.parseInt(input.split("/")[1]);
        int address1 = Integer.parseInt(input.split("/")[2]);
        String stateOfCard = input.split("/")[3];
        String tokenOfPlayer = input.split("/")[4];

        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                    if (getNumberOfMonstersInPlayerField(duelModel) < 2) {
                        return "there are not enough cards for tribute";
                    }
                    if (address > 5 || address1 > 5 || address1 == address) {
                        return "there is no monster on one of these addresses";
                    } else if (duelModel.getMonstersInField().get(duelModel.turn).get(address - 1) == null
                            || duelModel.getMonstersInField().get(duelModel.turn).get(address1 - 1) == null) {
                        return "there is no monster on one of these addresses";
                    } else {
                        if (!stateOfCard.equals("Defence") && !stateOfCard.equals("Attack")) {
                            return "please enter the appropriate state (Defence or Attack)";
                        } else {
                            duelModel.deleteMonster(duelModel.turn, address - 1);
                            duelModel.deleteMonster(duelModel.turn, address1 - 1);
                            duelModel.addCardToGraveyard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                                    address));
                            duelModel.addCardToGraveyard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                                    address1));
                            return normalSummonMonsterOnField(card, stateOfCard, duelModel);
                        }
                    }
                }
            }
        }
        return "";
    }


    public Integer getNumberOfMonstersInPlayerField(DuelModel duelModel) {
        ArrayList<Card> monstersInField = duelModel.getMonstersInField().get(duelModel.turn);
        int numberOfMonstersInPlayerFiled = 0;
        for (Card card : monstersInField) {
            if (card != null) {
                numberOfMonstersInPlayerFiled++;
            }
        }
        return numberOfMonstersInPlayerFiled;
    }


    public String normalSummonMonsterOnField(Card monster, String state, DuelModel duelModel) {
        String stateOfCard = "OO";
        if (state.equals("Attack")) {
            stateOfCard = "OO";
        } else if (state.equals("Defence")) {
            stateOfCard = "DO";
        }
        if (duelModel.getMonstersInField().get(duelModel.turn).get(0) == null) {
            duelModel.addMonsterFromHandToGame(stateOfCard + "/1", 0);
            duelModel.setMonsterSetOrSummonInThisTurn(monster, 1);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(1) == null) {
            duelModel.addMonsterFromHandToGame(stateOfCard + "/2", 1);
            duelModel.setMonsterSetOrSummonInThisTurn(monster, 2);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(2) == null) {
            duelModel.addMonsterFromHandToGame(stateOfCard + "/3", 2);
            duelModel.setMonsterSetOrSummonInThisTurn(monster, 3);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(3) == null) {
            duelModel.addMonsterFromHandToGame(stateOfCard + "/4", 3);
            duelModel.setMonsterSetOrSummonInThisTurn(monster, 4);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(4) == null) {
            duelModel.addMonsterFromHandToGame(stateOfCard + "/5", 4);
            duelModel.setMonsterSetOrSummonInThisTurn(monster, 5);
        } else {
            return "monster card zone is full";
        }
        if (monster.getName().equals("Command knight")) {
            effectOfCommandKnight(duelModel);
        }

        if (monster.getLevel() <= 4) {
            if (monster.getAttackPower() >= 1000) {
                duelModel.monsterFlipSummonOrNormalSummonForTrapHole = monster;
            }
        }
        duelModel.monsterSummonForEffectOfSomeTraps = monster;
        return "summoned successfully";
    }

    public String specialSummon(String input) {
        String tokenOfPlayer = input.split("/")[1];

        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    if (duelModel.getSelectedCards().get(duelModel.turn).get(0) == null) {
                        return "there is no way you could special summon a monster";
                    } else if (!duelModel.getSelectedCards().get(duelModel.turn).get(0).getCategory().equals("Monster")) {
                        return "there is no way you could special summon a monster";
                    } else {
                        Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                        String detailsOfSelectedCard = duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(card);
                        if (!detailsOfSelectedCard.equals("Hand")) {
                            return "there is no way you could special summon a monster";
                        } else if (!card.isHasSpecialSummon()) {
                            return "there is no way you could special summon a monster";
                        } else if (card.getName().equals("The Tricky")) {
                            return "Special Summon The Tricky";
                        } else if (card.getName().equals("Gate Guardian")) {
                            return "Special Summon Gate Guardian";
                        }
                    }
                }
            }
        }
        return "";
    }

    public String specialSummonTheTricky(String input) {
        int address = Integer.parseInt(input.split("/")[1]);
        String stateOfCard = input.split("/")[2];
        String tokenOfPlayer = input.split("/")[3];
        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    if (address > duelModel.getHandCards().get(duelModel.turn).size()) {
                        return "there is no way you could special summon a monster";
                    } else {
                        if (!stateOfCard.equals("Defence") && !stateOfCard.equals("Attack")) {
                            return "please enter the appropriate state (Defence or Attack)";
                        } else {
                            duelModel.deleteCardFromHandWithIndex(address - 1);
                            duelModel.addCardToGraveyard(duelModel.turn, duelModel.getHandCards().
                                    get(duelModel.turn).get(address - 1));
                            return specialSummonMonsterOnField(stateOfCard, duelModel);
                        }
                    }
                }
            }
        }
        return "";
    }

    public String specialSummonGateGuardian(String input) {
        int address = Integer.parseInt(input.split("/")[1]);
        int address1 = Integer.parseInt(input.split("/")[2]);
        int address2 = Integer.parseInt(input.split("/")[3]);
        String stateOfCard = input.split("/")[4];
        String tokenOfPlayer = input.split("/")[5];

        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    if (address > 5 || address1 > 5 || address2 > 5 || address == address1 || address == address2
                            || address1 == address2) {
                        return "there is no way you could special summon a monster";
                    } else if (duelModel.getMonstersInField().get(duelModel.turn).get(address - 1) == null
                            || duelModel.getMonstersInField().get(duelModel.turn).get(address1 - 1) == null ||
                            duelModel.getMonstersInField().get(duelModel.turn).get(address2 - 1) == null) {
                        return "there is no way you could special summon a monster";
                    } else {
                        if (!stateOfCard.equals("Defence") && !stateOfCard.equals("Attack")) {
                            return "please enter the appropriate state (Defence or Attack)";
                        } else {
                            duelModel.deleteMonster(duelModel.turn, address - 1);
                            duelModel.addCardToGraveyard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                                    address));
                            duelModel.deleteMonster(duelModel.turn, address1 - 1);
                            duelModel.addCardToGraveyard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                                    address1));
                            duelModel.deleteMonster(duelModel.turn, address2 - 1);
                            duelModel.addCardToGraveyard(duelModel.turn, duelModel.getMonster(duelModel.turn,
                                    address2));
                            return specialSummonMonsterOnField(stateOfCard, duelModel);
                        }
                    }
                }
            }
        }
        return "";
    }

    public String specialSummonMonsterOnField(String state, DuelModel duelModel) {
        String stateOfCard = "OO";
        if (state.equals("Attack")) {
            stateOfCard = "OO";
        } else if (state.equals("Defence")) {
            stateOfCard = "DO";
        }
        Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
        if (duelModel.getMonstersInField().get(duelModel.turn).get(0) == null) {
            duelModel.addMonsterFromHandToGame(stateOfCard + "/1", 0);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(1) == null) {
            duelModel.addMonsterFromHandToGame(stateOfCard + "/2", 1);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(2) == null) {
            duelModel.addMonsterFromHandToGame(stateOfCard + "/3", 2);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(3) == null) {
            duelModel.addMonsterFromHandToGame(stateOfCard + "/4", 3);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(4) == null) {
            duelModel.addMonsterFromHandToGame(stateOfCard + "/5", 4);
        } else {
            return "monster card zone is full";
        }
        duelModel.monsterSummonForEffectOfSomeTraps = card;
        return "summoned successfully";
    }

    public void effectOfCommandKnight(DuelModel duelModel) {
        for (Card card : duelModel.getMonstersInField().get(duelModel.turn)) {
            if (card != null) {
                card.setAttackPower(card.getAttackPower() + 400);
            }
        }
        for (Card card : duelModel.getMonstersInField().get(1 - duelModel.turn)) {
            if (card != null) {
                card.setAttackPower(card.getAttackPower() + 400);
            }
        }
    }

    public String flipSummon(String input) {
        String tokenOfPlayer = input.split("/")[1];
        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    if (duelModel.getSelectedCards().get(duelModel.turn).get(0) == null) {
                        return "no card is selected yet";
                    } else {
                        Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
                        if (!card.getCategory().equals("Monster")) {
                            return "you can’t flipSummon this card";
                        }
                        String detailsOfSelectedCard = duelModel.getDetailOfSelectedCard().get(duelModel.turn).get(card);
                        String[] details = detailsOfSelectedCard.split("/");
                        if (details[0].equals("Hand") || details[0].equals("Opponent")) {
                            return "you can’t change this card position";
                        } else if (!details[1].equals("DO") && !details[1].equals("DH") && !details[1].equals("OO")) {
                            return "you can’t change this card position";
                        } else if (!details[1].equals("DH")) {
                            return "you can’t flip summon this card";
                        } else {
                            int placeOfSummonCard = Integer.parseInt(details[2]);
                            if (duelModel.getMonsterSetOrSummonInThisTurn() == null) {
                                duelModel.flipSummon(placeOfSummonCard - 1);
                                return "flip summoned successfully";
                            } else {
                                String detailsOfCardSummonedOrSetInThisTurn = duelModel.getMonsterCondition(duelModel.turn,
                                        duelModel.thePlaceOfMonsterSetOrSummonInThisTurn - 1);
                                String[] details1 = detailsOfCardSummonedOrSetInThisTurn.split("/");
                                int placeOfCardSummonedOrSetInThisTurn = Integer.parseInt(details1[1]);
                                if (placeOfCardSummonedOrSetInThisTurn == placeOfSummonCard) {
                                    return "you can’t flip summon this card";
                                } else {
                                    duelModel.flipSummon(placeOfSummonCard - 1);
                                    if (card.getName().equals("Command knight")) {
                                        effectOfCommandKnight(duelModel);
                                    }
                                    if (card.getLevel() <= 4) {
                                        if (card.getAttackPower() >= 1000) {
                                            duelModel.monsterFlipSummonOrNormalSummonForTrapHole = card;
                                        }
                                    }
                                    duelModel.monsterSummonForEffectOfSomeTraps = card;
                                    if (card.getName().equals("Man-Eater Bug")) {
                                        return "flipSummon Man-Eater Bug";
                                    }
                                    return "flip summoned successfully";
                                }
                            }
                        }
                    }
                }
            }
        }
        return "";
    }

    public String flipSummonManEaterBug(String input) {
        int placeOfMonster = Integer.parseInt(input.split("/")[1]);
        String tokenOfPlayer = input.split("/")[2];
        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    if (placeOfMonster > 5) {
                        return "you must enter correct address";
                    } else {
                        Card card = duelModel.getMonster(1 - duelModel.turn, placeOfMonster);
                        if (card == null) {
                            return "this address has not monster";
                        } else {
                            duelModel.deleteMonster(1 - duelModel.turn, placeOfMonster - 1);
                            duelModel.addCardToGraveyard(1 - duelModel.turn, card);
                            return "monster with this address in opponent board destroyed";
                        }
                    }
                }
            }
        }
        return "";
    }

    public String set(String command) {
        String tokenOfPlayer = command.split("/")[1];
        String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
        for (DuelModel duelModel : duelModels) {
            if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                ArrayList<ArrayList<Card>> selectedCards = duelModel.getSelectedCards();
                if (selectedCards.get(duelModel.turn) == null) {
                    return "no card is selected yet";
                } else {
                    if (!(duelModel.getHandCards().get(duelModel.turn)).contains((selectedCards.get(duelModel.turn)).get(0))) {
                        return "you can’t set this card";
                    } else if ((selectedCards.get(duelModel.turn).get(0)).getCategory().equals("Monster")) {
                        if (selectedCards.get(duelModel.turn).get(0).getLevel() > 4)
                            return "this card cannot set normally";
                        if (duelModel.monsterSetOrSummonInThisTurn != null)
                            return "you already summoned/set on this turn";
                        if (selectedCards.get(duelModel.turn).get(0).getLevel() > 5)
                            return "this card can not set";
                        else
                            return this.setMonster(duelModel);

                    } else
                        return setTrapOrSpell(duelModel);
                }
            }
        }
        return "player not found";
    }

    public String setTrapOrSpell(DuelModel duelModel) {
        Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
        if (card.getCardType().equals("Field")) {
            duelModel.setField(card);
        }
        if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(0) == null)
            duelModel.addSpellAndTrapFromHandToGame("H/1", 0);
        else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(1) == null)
            duelModel.addSpellAndTrapFromHandToGame("H/2", 1);
        else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(2) == null)
            duelModel.addSpellAndTrapFromHandToGame("H/3", 2);
        else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(3) == null)
            duelModel.addSpellAndTrapFromHandToGame("H/4", 3);
        else if (duelModel.getSpellsAndTrapsInFiled().get(duelModel.turn).get(4) == null)
            duelModel.addSpellAndTrapFromHandToGame("H/5", 4);
        else return "spell card zone is full";
        duelModel.setSpellsAndTrapsSetInThisTurn(duelModel.turn, card);
        return "set successfully";

    }

    public String setMonster(DuelModel duelModel) {
        Card card = duelModel.getSelectedCards().get(duelModel.turn).get(0);
        if (duelModel.getMonstersInField().get(duelModel.turn).get(0) == null) {
            duelModel.addMonsterFromHandToGame("DH/1", 0);
            duelModel.setMonsterSetOrSummonInThisTurn(card, 1);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(1) == null) {
            duelModel.addMonsterFromHandToGame("DH/2", 1);
            duelModel.setMonsterSetOrSummonInThisTurn(card, 2);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(2) == null) {
            duelModel.addMonsterFromHandToGame("DH/3", 2);
            duelModel.setMonsterSetOrSummonInThisTurn(card, 3);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(3) == null) {
            duelModel.addMonsterFromHandToGame("DH/4", 3);
            duelModel.setMonsterSetOrSummonInThisTurn(card, 4);
        } else if (duelModel.getMonstersInField().get(duelModel.turn).get(4) == null) {
            duelModel.addMonsterFromHandToGame("DH/5", 4);
            duelModel.setMonsterSetOrSummonInThisTurn(card, 5);
        } else {
            return "monster card zone is full";
        }
        for (Card spellCard : duelModel.getSpellsAndTrapsInFiled().get(1 - duelModel.turn)) {
            if (spellCard != null) {
                if (spellCard.getName().equals("Swords of Revealing Light")) {
                    // duelController.changeStateOfMonsterWithSwordsCard(duelModel.turn);
                    break;
                }
            }
        }
        return "set successfully";
    }

    public void endPhaseMethod(String input) {
        String tokenOfPlayer = input.split("/")[1];
        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    if (duelModel.getBorrowCards().size() > 0) {
                        ActiveEffectController.getInstance().refundsTheBorrowCards(duelModel);
                    }
                    duelController.hasSwordCard(duelModel);
                    duelController.hasSupplySquadCard(duelModel);
                    duelController.hasSomeCardsInsteadOfScanner(duelModel);
                    duelModel.deleteMonstersDestroyedInThisTurn();
                    duelModel.deleteSpellAndTrapsSetInThisTurn();
                    duelModel.deleteCardsInsteadOfScanners();
                    duelModel.deleteMonsterSetOrSummonInThisTurn();
                    duelModel.turn = 1 - duelModel.turn;
                }

            }
        }
    }

    public void hasSwordCard(DuelModel duelModel) {
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

    public void hasSupplySquadCard(DuelModel duelModel) {
        ArrayList<Card> monsterDestroyedInThisTurn1 = duelModel.getMonsterDestroyedInThisTurn().get(duelModel.turn);
        ArrayList<Card> monsterDestroyedInThisTurn2 = duelModel.getMonsterDestroyedInThisTurn().get(1 - duelModel.turn);
        ArrayList<Card> supplyCards1 = duelModel.getSupplySquadCards().get(duelModel.turn);
        ArrayList<Card> supplyCards2 = duelModel.getSupplySquadCards().get(1 - duelModel.turn);
        if (monsterDestroyedInThisTurn1.size() > 0) {
            if (supplyCards1.size() > 0) {
                for (int i = 0; i < supplyCards1.size(); i++) {
                    duelModel.addCardFromDeckToHandInMiddleOfGame(duelModel.turn
                            , duelModel.getPlayersCards().get(duelModel.turn).get(duelModel.
                                    getPlayersCards().get(duelModel.turn).size() - 1));
                }
            }
        } else if (monsterDestroyedInThisTurn2.size() > 0) {
            if (supplyCards2.size() > 0) {
                for (int j = 0; j < supplyCards2.size(); j++) {
                    duelModel.addCardFromDeckToHandInMiddleOfGame(duelModel.turn
                            , duelModel.getPlayersCards().get(duelModel.turn).get(duelModel.
                                    getPlayersCards().get(duelModel.turn).size() - 1));
                }
            }
        }
    }

    public void hasSomeCardsInsteadOfScanner(DuelModel duelModel) {
        HashMap<Card, Integer> cardsInsteadOfScanner = duelModel.getCardsInsteadOfScanners();
        if (cardsInsteadOfScanner.size() > 0) {
            for (Map.Entry<Card, Integer> entry : cardsInsteadOfScanner.entrySet()) {
                Card monster = new Monster("Scanner", "Once per turn, you can select 1 of your opponent's monsters" +
                        " that is removed from play. Until the End Phase, this card's name is treated" +
                        " as the selected monster's name, and this card has the same Attribute, Level, ATK," +
                        " and DEF as the selected monster. If this card is removed from the field while this effect" +
                        " is applied, remove it from play.", "Effect", 8000, "Monster",
                        0, 0, "Machine", "LIGHT", 1, false);
                int placeOfScanner = entry.getValue();
                duelModel.getMonstersInField().get(duelModel.turn).set(placeOfScanner - 1, monster);
            }
        }
    }

    public Integer hasSpellEffectInStandByPhase(String input) {
        String tokenOfPlayer = input.split("/")[1];
        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    return duelModel.getMessengerOfPeace().get(duelModel.turn).size();
                }
            }
        }
        return null;
    }

    public String effectOfSpellInStandByPhase(String input) {
        String tokenOfPlayer = input.split("/")[1];
        int response = Integer.parseInt(input.split("/")[2]);

        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : duelModels) {
                if (duelModel.getUsernames().get(duelModel.turn).equals(playerUsername)) {
                    if (response != 1 && response != 2) {
                        return "you must entered 1 or 2";
                    } else if (response == 1) {
                        duelModel.deleteMessengerOfPeaceCards(duelModel.turn, duelModel.getMessengerOfPeace().get(duelModel.turn)
                                .get(0));
                        return "your messenger of peace card destroyed";
                    } else {
                        duelModel.decreaseLifePoint(100, duelModel.turn);
                        return "your lp decreases 100 unit";
                    }
                }
            }
        }
        return "";
    }

    public String showSelectedCard(String command) {
        if (!LoginAndSignUpController.loggedInUsers.containsKey(command.split("/")[1])) {
            return "wrong token!";
        }
        User user = LoginAndSignUpController.loggedInUsers.get(command.split("/")[1]);
        for (DuelModel duelModel : DuelController.duelModels) {
            if (duelModel.getUsernames().get(duelModel.turn).equals(user.getUsername())) {
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
                String response = "";
                for (String s : output) {
                    response = response + s;
                    response = response + "\n";
                }
                return response;
            }
        }
        return "its not your turn!";
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

    public String showGraveYard(String command) {
        if (!LoginAndSignUpController.loggedInUsers.containsKey(command.split("/")[2])) {
            return "wrong token!";
        }
        User user = LoginAndSignUpController.loggedInUsers.get(command.split("/")[2]);
        for (DuelModel duelModel : DuelController.duelModels) {
            if (duelModel.getUsernames().get(duelModel.turn).equals(user.getUsername())) {
                int turn = Integer.parseInt(command.split("/")[1]);
                ArrayList<Card> graveyardCards = duelModel.getGraveyard(turn);
                ArrayList<String> output = new ArrayList<>();
                for (int i = 0; i < graveyardCards.size(); i++) {
                    output.add(i + 1 + ". " + graveyardCards.get(i).getName() + ": " + graveyardCards.get(i).getDescription());
                }
                if (graveyardCards.size() == 0) {
                    output.add("graveyard empty");
                }
                String response = "";
                for (String s : output) {
                    response = response + s;
                    response = response + "\n";
                }
                return response;
            }
        }
        return "its not your turn";
    }

}