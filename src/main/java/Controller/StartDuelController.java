package Controller;
import Model.User;

public class StartDuelController {
    private static StartDuelController startDuelController;

    private StartDuelController(){

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
                        return "Duel Started Successfully";
                    } else
                        return "number of rounds is not supported";
                }
            }
        }
        return "";
    }
}
