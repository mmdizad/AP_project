package View;

import Controller.LoginController;
import Model.User;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartDuelView extends MainMenu {
    @Override
    public void run(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            Pattern pattern = Pattern.compile("duel --new --second-player (\\S+) --rounds (\\d+)");
            Matcher matcher = pattern.matcher(input);
            Pattern pattern1 = Pattern.compile("duel --new --rounds (\\d+) --second-player (\\S+)");
            Matcher matcher1 = pattern1.matcher(input);
            Pattern pattern2 = Pattern.compile("duel --new --ai --rounds (\\d+)");
            Matcher matcher2 = pattern2.matcher(input);

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
                    else if (LoginController.user.getActiveDeck().getCardsMain().size() < 40){
                        System.out.println(LoginController.user.getUsername() + "'s deck is invalid");
                    }else if (secondUser.getActiveDeck().getCardsMain().size() < 40){
                        System.out.println(secondUser.getUsername() + "'s deck is invalid");
                    } else if (round == 3 || round == 1) {
                        if (round == 1) {
                            DuelView duelView = DuelView.getInstance();
                            duelView.selectFirstPlayer(secondPlayerUserName, scanner, duelView, false);
                            printWinnerAndGiveScoreOneRound(duelView,LoginController.user,secondUser);
                        }else {
                            for (int i = 0;i < 3;i++){
                                DuelView duelView = DuelView.getInstance();
                                duelView.selectFirstPlayer(secondPlayerUserName, scanner, duelView, false);
                            }
                        }
                    } else System.out.println("number of rounds is not supported");
                    //valid deck check nashode
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
                    else if (LoginController.user.getActiveDeck().getCardsMain().size() < 40){
                        System.out.println(LoginController.user.getUsername() + "'s deck is invalid");
                    }else if (secondUser.getActiveDeck().getCardsMain().size() < 40){
                        System.out.println(secondUser.getUsername() + "'s deck is invalid");
                    } else if (round == 3 || round == 1) {
                        if (round == 1) {
                            DuelView duelView = DuelView.getInstance();
                            duelView.selectFirstPlayer(secondPlayerUserName, scanner, duelView, false);
                            printWinnerAndGiveScoreOneRound(duelView,LoginController.user,secondUser);
                        }else {
                            for (int i = 0;i < 3;i++){
                                DuelView duelView = DuelView.getInstance();
                                duelView.selectFirstPlayer(secondPlayerUserName, scanner, duelView, false);
                            }
                        }
                    } else System.out.println("number of rounds is not supported");
                }
            }else if (matcher2.find()) {
                int round = Integer.parseInt(matcher2.group(1));
                if (LoginController.user.getActiveDeck() == null) {
                    System.out.println(LoginController.user.getUsername() + " has no active deck");
                } else if (LoginController.user.getActiveDeck().getCardsMain().size() < 40){
                    System.out.println(LoginController.user.getUsername() + "'s deck is invalid");
                } else if (round == 3 || round == 1) {
                    User ai = new User("ai" , "ai" , "ai");
                    ai.addDeck(LoginController.user.getActiveDeck());
                    ai.setActiveDeck(LoginController.user.getActiveDeck());
                    DuelView duelView = DuelView.getInstance();
                    duelView.selectFirstPlayer(ai.getUsername(), scanner, duelView, true);
                } else System.out.println("number of rounds is not supported");
            }else if (input.equals("menu exit")) break;
            else if (input.equals("menu show-current")) System.out.println("StartDuel");
            else System.out.println("invalid command!");
        }
    }

    public void printWinnerAndGiveScoreOneRound(DuelView duelView,User firstUser,User secondUser){
        if (duelView.duelModel.getLifePoint(0) <= 0){
            secondUser.setScore(1000);
            secondUser.increaseCoins(1000 + duelView.duelModel.getLifePoint(1));
            firstUser.increaseCoins(100);
        }else if (duelView.duelModel.getLifePoint(1) <= 0){
            firstUser.setScore(1000);
            firstUser.increaseCoins(1000 + duelView.duelModel.getLifePoint(1));
            secondUser.increaseCoins(100);
        }else if (duelView.duelModel.turn == 0){
            secondUser.setScore(1000);
            secondUser.increaseCoins(1000 + duelView.duelModel.getLifePoint(1));
            firstUser.increaseCoins(100);
        }else {
            firstUser.setScore(1000);
            firstUser.increaseCoins(1000 + duelView.duelModel.getLifePoint(1));
            secondUser.increaseCoins(100);
        }
    }
}