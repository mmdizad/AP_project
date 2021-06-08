package View;

import Controller.ScoreBoardController;

import java.util.ArrayList;
import java.util.Scanner;

public class ScoreBoardView extends MainMenu {
    private static ScoreBoardView scoreBoardView = new ScoreBoardView();

    private ScoreBoardView() {

    }

    public static ScoreBoardView getInstance() {
        return scoreBoardView;
    }

    public void run(Scanner scanner) {
        while (true) {
            String command = scanner.nextLine();
            if (getCommandMatcher(command, "^menu enter (\\S+)$").find()) {
                System.out.println("menu navigation is not possible");
            } else if (command.equals("menu show-current")) {
                System.out.println("ScoreboardMenu");
            } else if (command.equals("scoreboard show")) {
                showScoreboard();
            }
        }
    }

    public void showScoreboard() {
        ScoreBoardController scoreBoardController = ScoreBoardController.getInstance();
        ArrayList<String> output = scoreBoardController.scoreBoard();
        for (int i = 0; i < output.size(); i++) {
            System.out.println(output.get(i));
        }
    }


}