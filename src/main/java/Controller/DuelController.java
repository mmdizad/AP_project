package Controller;

import Model.Card;
import Model.Deck;
import Model.DuelModel;
import Model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DuelController {
    public static HashMap<DuelModel, Boolean> duelModels;
    private static DuelController duelController;

    private DuelController() {

    }

    public static DuelController getInstance() {
        if (duelController == null)
            duelController = new DuelController();

        return duelController;
    }

    static {
        duelModels = new HashMap<>();
    }

    public int selectFirstPlayer(String input) {
        String[] partsOfInput = input.split("/");
        String secondPlayerUsername = partsOfInput[1];
        boolean isAi = Boolean.parseBoolean(partsOfInput[2]);
        String token = partsOfInput[3];
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
            duelModels.put(duelModel, isAi);
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
                for (Map.Entry<DuelModel, Boolean> entry : duelModels.entrySet()) {
                    DuelModel duelModel = entry.getKey();
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
            for (Map.Entry<DuelModel, Boolean> entry : duelModels.entrySet()) {
                DuelModel duelModel = entry.getKey();
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
            for (Map.Entry<DuelModel, Boolean> entry : duelModels.entrySet()) {
                DuelModel duelModel = entry.getKey();
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
            for (Map.Entry<DuelModel, Boolean> entry : duelModels.entrySet()) {
                DuelModel duelModel = entry.getKey();
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
            for (Map.Entry<DuelModel, Boolean> entry : duelModels.entrySet()) {
                DuelModel duelModel = entry.getKey();
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
            for (Map.Entry<DuelModel, Boolean> entry : duelModels.entrySet()) {
                DuelModel duelModel = entry.getKey();
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
            for (Map.Entry<DuelModel, Boolean> entry : duelModels.entrySet()) {
                DuelModel duelModel = entry.getKey();
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
            for (Map.Entry<DuelModel, Boolean> entry : duelModels.entrySet()) {
                DuelModel duelModel = entry.getKey();
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

}