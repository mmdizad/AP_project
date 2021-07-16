package Controller;

import Model.Card;
import Model.Deck;
import Model.DuelModel;
import Model.User;

import java.util.ArrayList;
import java.util.Collections;

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

    public static String newCardToHand(String input) {
        String playerUsername = input.split("/")[1];
        String tokenOfPlayer = input.split("/")[2];

        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            User user = User.getUserByUsername(playerUsername);
            assert user != null;
            Deck deck = user.getActiveDeck();
            ArrayList<Card> cardsInDeck = deck.getCardsMain();

            if (cardsInDeck.size() >= 1) {
                for (DuelModel duelModel : duelModels) {
                    if (duelModel.getUsernames().get(0).equals(playerUsername) ||
                            duelModel.getUsernames().get(1).equals(playerUsername))
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
                if (duelModel.getUsernames().get(0).equals(playerUsername) ||
                        duelModel.getUsernames().get(1).equals(playerUsername)) {
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
                if (duelModel.getUsernames().get(0).equals(playerUsername) ||
                        duelModel.getUsernames().get(1).equals(playerUsername)) {
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
                if (duelModel.getUsernames().get(0).equals(playerUsername) ||
                        duelModel.getUsernames().get(1).equals(playerUsername)) {
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
                if (duelModel.getUsernames().get(0).equals(playerUsername) ||
                        duelModel.getUsernames().get(1).equals(playerUsername)) {
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
                if (duelModel.getUsernames().get(0).equals(playerUsername) ||
                        duelModel.getUsernames().get(1).equals(playerUsername)) {
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
                if (duelModel.getUsernames().get(0).equals(playerUsername) ||
                        duelModel.getUsernames().get(1).equals(playerUsername)) {
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
                if (duelModel.getUsernames().get(0).equals(playerUsername) ||
                        duelModel.getUsernames().get(1).equals(playerUsername)) {
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


    public String summon(String input) {
        String tokenOfPlayer = input.split("/")[2];
        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)) {
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            for (DuelModel duelModel : duelModels) {
                if (duelModel.getUsernames().get(0).equals(playerUsername) ||
                        duelModel.getUsernames().get(1).equals(playerUsername)) {
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
                        } else if (card.getLevel() <= 4) {
                            return "NormalSummon";
                        }
                    }
                }
            }
        }
        return "";
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
        if (monster.getLevel() <= 4) {
            if (monster.getAttackPower() >= 1000) {
                duelModel.monsterFlipSummonOrNormalSummonForTrapHole = monster;
            }
        }
        duelModel.monsterSummonForEffectOfSomeTraps = monster;
        return "summoned successfully";
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
}