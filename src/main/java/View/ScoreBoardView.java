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
        System.out.println(scoreBoardController.scoreBoard());
        while (true) {
            System.out.println("enter Refresh to load data again or Back to exit: ");
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            if (command.equals("Refresh")) {
                System.out.println(scoreBoardController.scoreBoard());
            } else if (command.equals("Back")) {
                return;
            }
        }
    }


}