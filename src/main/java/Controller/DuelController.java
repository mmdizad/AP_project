package Controller;

import Model.Card;
import Model.Deck;
import Model.DuelModel;
import Model.User;

import java.util.ArrayList;
import java.util.Collections;

public class DuelController {
    public static ArrayList<DuelModel> duelModels;

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

}
