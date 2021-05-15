package View;

import Controller.DeckController;
import Controller.LoginController;
import Model.User;

import java.util.ArrayList;
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
                startTheGame(scanner, matcher, 1, 2);
            } else if (matcher1.find()) {
                startTheGame(scanner, matcher1, 2, 1);
            } else if (matcher2.find()) {
                int round = Integer.parseInt(matcher2.group(1));
                if (LoginController.user.getActiveDeck() == null) {
                    System.out.println(LoginController.user.getUsername() + " has no active deck");
                } else if (LoginController.user.getActiveDeck().getCardsMain().size() < 40) {
                    System.out.println(LoginController.user.getUsername() + "'s deck is invalid");
                } else if (round == 3 || round == 1) {
                    User ai = new User("ai", "ai", "ai");
                    ai.addDeck(LoginController.user.getActiveDeck());
                    ai.setActiveDeck(LoginController.user.getActiveDeck());
                    startTheGameWithAi(round,ai,scanner);
                } else System.out.println("number of rounds is not supported");
            } else if (input.equals("menu exit")) break;
            else if (input.equals("menu show-current")) System.out.println("StartDuel");
            else System.out.println("invalid command");
        }
    }

    private void startTheGameWithAi(int round,User secondUser,Scanner scanner) {
        if (round == 1) {
            DuelView duelView = DuelView.getInstance();
            duelView.selectFirstPlayer(secondUser.getUsername(), scanner, duelView, false);
            printWinnerAndGiveScoreOneRound(duelView, LoginController.user, secondUser);
        } else {
            int userWins = 0;
            int secondPlayerWins = 0;
            ArrayList<Integer> maxLPs = new ArrayList<>();
            maxLPs.add(0);
            maxLPs.add(0);
            for (int i = 0; i < 3; i++) {
                DuelView duelView = DuelView.getInstance();
                duelView.selectFirstPlayer(secondUser.getUsername(), scanner, duelView, false);
                int winner = printWinnerThreeRound(duelView, LoginController.user, secondUser);
                if (winner == 0) userWins++;
                else secondPlayerWins++;
                if (duelView.duelModel.getLifePoint(0) > maxLPs.get(0)) {
                    maxLPs.set(0, duelView.duelModel.getLifePoint(0));
                }
                if (duelView.duelModel.getLifePoint(1) > maxLPs.get(1)) {
                    maxLPs.set(1, duelView.duelModel.getLifePoint(1));
                }
                if (userWins == 2) {
                    finishThreeRound(duelView, LoginController.user, secondUser, maxLPs.get(0));
                    return;
                }
                if (secondPlayerWins == 2) {
                    finishThreeRound(duelView, secondUser, LoginController.user, maxLPs.get(1));
                    return;
                }
                changeCardsBetweenRounds(LoginController.user, secondUser, scanner);
            }
        }
    }

    private void startTheGame(Scanner scanner, Matcher matcher, int i2, int i3) {
        String secondPlayerUserName = matcher.group(i2);
        int round = Integer.parseInt(matcher.group(i3));

        if (!User.isUserWithThisUsernameExists(secondPlayerUserName))
            System.out.println("there is no player with this username");
        else {
            User secondUser = User.getUserByUsername(secondPlayerUserName);
            if (LoginController.user.getActiveDeck() == null)
                System.out.println(LoginController.user.getUsername() + " has no active deck");
            else if (secondUser.getActiveDeck() == null)
                System.out.println(secondUser.getUsername() + " has no active deck");
            else if (LoginController.user.getActiveDeck().getCardsMain().size() < 40) {
                System.out.println(LoginController.user.getUsername() + "'s deck is invalid");
            } else if (secondUser.getActiveDeck().getCardsMain().size() < 40) {
                System.out.println(secondUser.getUsername() + "'s deck is invalid");
            } else if (round == 3 || round == 1) {
                if (round == 1) {
                    DuelView duelView = DuelView.getInstance();
                    duelView.selectFirstPlayer(secondPlayerUserName, scanner, duelView, false);
                    printWinnerAndGiveScoreOneRound(duelView, LoginController.user, secondUser);
                } else {
                    int userWins = 0;
                    int secondPlayerWins = 0;
                    ArrayList<Integer> maxLPs = new ArrayList<>();
                    maxLPs.add(0);
                    maxLPs.add(0);
                    for (int i = 0; i < 3; i++) {
                        DuelView duelView = DuelView.getInstance();
                        duelView.selectFirstPlayer(secondPlayerUserName, scanner, duelView, false);
                        int winner = printWinnerThreeRound(duelView, LoginController.user, secondUser);
                        if (winner == 0) userWins++;
                        else secondPlayerWins++;
                        if (duelView.duelModel.getLifePoint(0) > maxLPs.get(0)) {
                            maxLPs.set(0, duelView.duelModel.getLifePoint(0));
                        }
                        if (duelView.duelModel.getLifePoint(1) > maxLPs.get(1)) {
                            maxLPs.set(1, duelView.duelModel.getLifePoint(1));
                        }
                        if (userWins == 2) {
                            finishThreeRound(duelView, LoginController.user, secondUser, maxLPs.get(0));
                            return;
                        }
                        if (secondPlayerWins == 2) {
                            finishThreeRound(duelView, secondUser, LoginController.user, maxLPs.get(1));
                            return;
                        }
                        changeCardsBetweenRounds(LoginController.user, secondUser, scanner);
                    }
                }
            } else System.out.println("number of rounds is not supported");
        }
    }

    public void finishThreeRound(DuelView duelView, User winner, User looser, int maxLP) {
        winner.setScore(3000);
        winner.increaseCoins(3000 + 3 * maxLP);
        looser.increaseCoins(300);
        System.out.println(winner.getUsername() + " won the game and the score is: 3000 - 0");
    }

    public int printWinnerThreeRound(DuelView duelView, User firstUser, User secondUser) {
        if (duelView.duelModel.getLifePoint(0) <= 0) {
            System.out.println(secondUser.getUsername() + " won this round");
            return 1;
        } else if (duelView.duelModel.getLifePoint(1) <= 0) {
            System.out.println(firstUser.getUsername() + " won this round");
            return 0;
        } else if (duelView.duelModel.turn == 0) {
            System.out.println(secondUser.getUsername() + " won this round");
            return 1;
        } else {
            System.out.println(firstUser.getUsername() + " won this round");
            return 0;
        }
    }

    public void printWinnerAndGiveScoreOneRound(DuelView duelView, User firstUser, User secondUser) {
        if (duelView.duelModel.getLifePoint(0) <= 0) {
            secondUser.setScore(1000);
            secondUser.increaseCoins(1000 + duelView.duelModel.getLifePoint(1));
            firstUser.increaseCoins(100);
            System.out.println(secondUser.getUsername() + " won the game and the score is: 1000 - 0");
        } else if (duelView.duelModel.getLifePoint(1) <= 0) {
            firstUser.setScore(1000);
            firstUser.increaseCoins(1000 + duelView.duelModel.getLifePoint(1));
            secondUser.increaseCoins(100);
            System.out.println(firstUser.getUsername() + " won the game and the score is: 1000 - 0");
        } else if (duelView.duelModel.turn == 0) {
            secondUser.setScore(1000);
            secondUser.increaseCoins(1000 + duelView.duelModel.getLifePoint(1));
            firstUser.increaseCoins(100);
            System.out.println(secondUser.getUsername() + " won the game and the score is: 1000 - 0");
        } else {
            firstUser.setScore(1000);
            firstUser.increaseCoins(1000 + duelView.duelModel.getLifePoint(1));
            secondUser.increaseCoins(100);
            System.out.println(firstUser.getUsername() + " won the game and the score is: 1000 - 0");
        }
    }

    public void changeCardsBetweenRounds(User first, User second, Scanner scanner) {
        if (!first.getUsername().equals("ai")) {
            System.out.println(first.getUsername() + "'s turn to change cards");
            System.out.println("enter    change --(mainCardName) with --(sideCardName)   or finish to continue");
            String command = scanner.nextLine();
            while (!command.equals("finish")) {
                DeckController deckController = DeckController.getInstance();
                System.out.println(deckController.changeMainAndSideCards(command, first));
            }
        }
        if (!second.getUsername().equals("ai")) {
            System.out.println(second.getUsername() + "'s turn to change cards");
            System.out.println("enter    change --(mainCardName) with --(sideCardName)   or finish to continue");
            String command = scanner.nextLine();
            while (!command.equals("finish")) {
                DeckController deckController = DeckController.getInstance();
                System.out.println(deckController.changeMainAndSideCards(command, second));
            }
        }
    }
}