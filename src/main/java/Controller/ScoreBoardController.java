package Controller;

import Model.User;

import java.util.ArrayList;
import java.util.Comparator;

public class ScoreBoardController {

    private static ScoreBoardController scoreBoardController;

    public static ScoreBoardController getInstance() {
        if (scoreBoardController == null) {
            scoreBoardController = new ScoreBoardController();
        }
        return scoreBoardController;
    }

    private ScoreBoardController () {

    }

    public String getScoreBoard() {
        ArrayList<User> users = User.getAllUsers();
        Comparator<User> compareScoreboard = Comparator
                .comparing(User::getScore, Comparator.reverseOrder())
                .thenComparing(User::getUsername);
        users.sort(compareScoreboard);
        String output = "";
        for (int i = 0; i < users.size(); i++) {
            int j = i + 1;
            output += (j + "-" + users.get(i).getUsername() + ": " + users.get(i).getScore()) + "\n";
        }
        return output;
    }

}
