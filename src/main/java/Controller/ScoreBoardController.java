package Controller;

import Model.User;

import java.util.ArrayList;
import java.util.Comparator;

public class ScoreBoardController extends LoginController {

    private static ScoreBoardController scoreBoardController = new ScoreBoardController();

    private ScoreBoardController() {

    }

    public static ScoreBoardController getInstance() {
        return scoreBoardController;
    }

    public ArrayList<String> scoreBoard() {
        ArrayList<User> users = User.getAllUsers();
        Comparator<User> compareScoreboard = Comparator
                .comparing(User::getScore, Comparator.reverseOrder());
        users.sort(compareScoreboard);
        ArrayList<String> output = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            int j = i + 1;
            output.add(j + "-" + users.get(i).getUsername() + ": " + users.get(i).getScore());
        }
        return output;
    }

}