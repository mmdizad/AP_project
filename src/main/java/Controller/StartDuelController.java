package Controller;

import Model.Card;
import Model.User;

import java.util.HashMap;
import java.util.Map;

public class StartDuelController {
    private static StartDuelController startDuelController;
    private static HashMap<String, String> startDuel;

    static {
        startDuel = new HashMap<>();
    }

    private StartDuelController() {

    }

    public static StartDuelController getInstance() {
        if (startDuelController == null)
            startDuelController = new StartDuelController();

        return startDuelController;
    }

    public String startTheGame(String input) {
        String[] partsOfInput = input.split("/");
        String secondPlayerUserName = partsOfInput[1];
        int round = Integer.parseInt(partsOfInput[2]);
        String token = partsOfInput[3];
        if (LoginAndSignUpController.loggedInUsers.containsKey(token)) {
            if (!User.isUserWithThisUsernameExists(secondPlayerUserName))
                return "there is no player with this username";
            else {
                User user1 = LoginAndSignUpController.loggedInUsers.get(token);
                User user2 = User.getUserByUsername(secondPlayerUserName);
                assert user1 != null;
                if (user1.getActiveDeck() == null)
                    return user1.getUsername() + " has no active deck";
                else {
                    assert user2 != null;
                    if (user2.getActiveDeck() == null)
                        return user2.getUsername() + " has no active deck";
                    else if (user1.getActiveDeck().getCardsMain().size() < 40) {
                        return user1.getUsername() + "'s deck is invalid";
                    } else if (user2.getActiveDeck().getCardsMain().size() < 40) {
                        return user2.getUsername() + "'s deck is invalid";
                    } else if (round == 3 || round == 1) {
                        if (startDuel.containsKey(user1.getUsername())) {
                            if (startDuel.get(user1.getUsername()).split("/")[0].equals(user2.getUsername())) {
                                int roundOfGame = Integer.parseInt(startDuel.get(user1.getUsername()).split("/")[1]);
                                if (round == roundOfGame) {
                                    return "Your Request Already Registered";
                                }
                            }
                        }
                        for (Map.Entry<String, String> entry : startDuel.entrySet()) {
                            String playerUsername = entry.getKey();
                            String opponentUsername = entry.getValue().split("/")[0];
                            int roundOfGame = Integer.parseInt(entry.getValue().split("/")[1]);
                            if (opponentUsername.equals(user1.getUsername())) {
                                if (user2.getUsername().equals(playerUsername)) {
                                    if (roundOfGame == round) {
                                        return "Duel Started Successfully";
                                    }
                                }
                            }
                        }
                        startDuel.put(user1.getUsername(), user2.getUsername() + "/" + round);
                        return "Your Request Registered";
                    } else
                        return "number of rounds is not supported";
                }
            }
        }
        return "";
    }

    public String cancelTheGame(String input){
        String[] partsOfInput = input.split("/");
        String secondPlayerUserName = partsOfInput[1];
        String tokenOfPlayer = partsOfInput[2];
        if (LoginAndSignUpController.loggedInUsers.containsKey(tokenOfPlayer)){
            String playerUsername = LoginAndSignUpController.loggedInUsers.get(tokenOfPlayer).getUsername();
            if (startDuel.containsKey(playerUsername)){
                if (startDuel.get(playerUsername).equals(secondPlayerUserName)){
                    startDuel.remove(playerUsername);
                    return "Duel Canceled Successfully";
                }
            }
        }
        return "";
    }
}
