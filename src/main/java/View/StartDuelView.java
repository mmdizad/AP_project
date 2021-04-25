package View;

import Controller.DuelController;
import Controller.LoginController;
import Model.DuelModel;
import Model.User;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartDuelView extends MainMenu {
    @Override
    public void run(Scanner scanner) {
        while(true) {
            String input = scanner.nextLine();
            Pattern pattern = Pattern.compile("duel -new -second-player (\\S+) -rounds (\\d+)");
            Matcher matcher = pattern.matcher(input);
            Pattern pattern1 = Pattern.compile("duel -new -rounds (\\d+) -second-player (\\S+)");
            Matcher matcher1 = pattern1.matcher(input);

            if (matcher.find()) {
                String secondPlayerUserName = matcher.group(1);
                int round = Integer.parseInt(matcher.group(2));

                if (!User.isUserWithThisUsernameExists(secondPlayerUserName))
                    System.out.println("there is no player with this username");
                else {
                    User secondUser = User.getUserByUsername(secondPlayerUserName);
                    if (LoginController.user.getActiveDeck() == null)
                        System.out.println(LoginController.user.getUsername() + " has no active deck");
                    else if (secondUser.getActiveDeck() == null)
                        System.out.println(secondUser.getUsername() + " has no active deck");
                    else if (round == 3 || round == 1) {
                        DuelView duelView = new DuelView();
                        duelView.selectFirstPlayer(secondPlayerUserName);
                    } else System.out.println("number of rounds is not supported");
                }

            } else if (matcher1.find()) {
                String secondPlayerUserName = matcher1.group(2);
                int round = Integer.parseInt(matcher1.group(1));

                if (!User.isUserWithThisUsernameExists(secondPlayerUserName))
                    System.out.println("there is no player with this username");
                else {
                    User secondUser = User.getUserByUsername(secondPlayerUserName);
                    if (LoginController.user.getActiveDeck() == null)
                        System.out.println(LoginController.user.getUsername() + " has no active deck");
                    else if (secondUser.getActiveDeck() == null)
                        System.out.println(secondUser.getUsername() + " has no active deck");
                    else if (round == 3 || round == 1) {
                        DuelView duelView = new DuelView();
                        duelView.selectFirstPlayer(secondPlayerUserName);
                    } else System.out.println("number of rounds is not supported");
                }
            }else if (input.equals("menu exit"))break;
            else if (input.equals("menu show-current")) System.out.println("StartDuel");
                else System.out.println("invalid command!");


        }
    }
}