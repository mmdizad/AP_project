package View;

import Controller.DuelController;
import Controller.LoginController;
import Model.DuelModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuelView {
    protected DuelController duelController;
    protected DuelModel duelModel;
    protected Scanner scanner1;

    public void selectFirstPlayer(String secondPlayerUsername, Scanner scanner, DuelView duelView) {
        scanner1 = scanner;
        ArrayList<Integer> someRandomNumbers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            someRandomNumbers.add(i + 1);
        }
        Collections.shuffle(someRandomNumbers);
        int starterGame = someRandomNumbers.get(0);
        if (starterGame % 2 == 0) {
            duelModel = new DuelModel(LoginController.user.getUsername(), secondPlayerUsername);
            DrawPhaseView drawPhaseView = DrawPhaseView.getInstance();
            drawPhaseView.newCard(scanner, LoginController.user.getUsername(), true);
            System.out.println("EndPhase");
            duelModel.turn = 1 - duelModel.turn;
            drawPhaseView.newCard(scanner, secondPlayerUsername, true);
            System.out.println("EndPhase");
            duelModel.turn = 1 - duelModel.turn;
            StandByPhaseView standByPhaseView = StandByPhaseView.getInstance();
            standByPhaseView.run(scanner);
        } else {
            duelModel = new DuelModel(secondPlayerUsername, LoginController.user.getUsername());
            DrawPhaseView drawPhaseView = DrawPhaseView.getInstance();
            drawPhaseView.newCard(scanner, secondPlayerUsername, true);
            System.out.println("EndPhase");
            duelModel.turn = 1 - duelModel.turn;
            drawPhaseView.newCard(scanner, LoginController.user.getUsername(), true);
            System.out.println("EndPhase");
            duelModel.turn = 1 - duelModel.turn;
            StandByPhaseView standByPhaseView = StandByPhaseView.getInstance();
            standByPhaseView.run(scanner);
        }
        duelController = new DuelController();
        duelController.setDuelModel(duelModel, duelView,duelController);
    }

    protected void deselect(Matcher matcher) {
        if (matcher.find()) {
            System.out.println(duelController.deselect());
        }
    }

    protected void activateEffect() {

    }

    public void opponentActiveEffect(boolean hasAnySpellOrTrap) {
        if (hasAnySpellOrTrap) {
            duelModel.turn = 1 - duelModel.turn;
            System.out.println("now it will be " + duelModel.getUsernames().get(duelModel.turn) + " turn");
            System.out.println("do you want to activate your trap and spell?");
            String response = scanner1.nextLine();
            if (!response.equals("NO") && !response.equals("YES")) {
                System.out.println("you must enter NO or YES");
            }
            if (response.equals("YES")) {
                System.out.println(duelController.opponentActiveSpellOrTrap());
                duelModel.turn = 1 - duelModel.turn;
            }
            if (response.equals("NO")) {
                duelModel.turn = 1 - duelModel.turn;
                System.out.println("now it will be " + duelModel.getUsernames().get(duelModel.turn) + " turn");
            }
        }
    }

    public Matcher scanCommandForActiveSpell() {
        String command = scanner1.nextLine();
        return getCommandMatcher(command, "^select --spell (\\d+)$");
    }

    public String scanKindOfGraveyardForActiveEffect() {
        System.out.println("please specify the graveyard(My/Opponent)");
        return scanner1.nextLine();
    }


    public Integer scanNumberOfCardForActiveEffect() {
        System.out.println("please specify the number of card you want");
        return scanner1.nextInt();
    }

    protected void showGraveyard(Matcher matcher) {
        if (matcher.find()) {
            ArrayList<String> output = duelController.showGraveYard();
            for (String s : output) {
                System.out.println(s);
            }
        }
    }

    protected void showCard(Matcher matcher) {
        if (matcher.find()) {
            ArrayList<String> output = duelController.checkCard(matcher);
            for (String s : output) {
                System.out.println(s);
            }
        }
    }

    protected void showSelectedCard(Matcher matcher) {
        if (matcher.find()) {
            ArrayList<String> output = duelController.checkSelectedCard(matcher);
            for (String s : output) {
                System.out.println(s);
            }
        }
    }

    public void surrender() {
    }

    protected void select(Matcher matcher) {

    }

    protected void selectMonster(Matcher matcher) {
        if (matcher.find()) {
            System.out.println(duelController.selectMonster(matcher));
        }
    }

    protected void selectOpponentMonster(Matcher matcher) {
        if (matcher.find()) {
            System.out.println(duelController.selectOpponentMonster(matcher));
        }
    }

    protected void selectSpellOrTrap(Matcher matcher) {
        if (matcher.find()) {
            System.out.println(duelController.selectSpellOrTrap(matcher));
        }
    }

    protected void selectOpponentSpell(Matcher matcher) {
        if (matcher.find()) {
            System.out.println(duelController.selectOpponentSpellOrTrap(matcher));
        }
    }

    protected void selectField(Matcher matcher) {
        if (matcher.find()) {
            System.out.println(duelController.selectFieldZone());
        }
    }

    protected void selectOpponentField(Matcher matcher) {
        if (matcher.find()) {
            System.out.println(duelController.selectOpponentFieldZone());
        }
    }

    protected void selectHand(Matcher matcher) {
        if (matcher.find()) {
            System.out.println(duelController.selectHand(matcher));
        }
    }

    protected Matcher getCommandMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        return matcher;
    }


}