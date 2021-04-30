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

    public void selectFirstPlayer(String secondPlayerUsername, Scanner scanner) {
        ArrayList<Integer> someRandomNumbers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            someRandomNumbers.add(i + 1);
        }
        Collections.shuffle(someRandomNumbers);
        int starterGame = someRandomNumbers.get(0);
        if (starterGame % 2 == 0) {
            duelModel = new DuelModel(LoginController.user.getUsername(), secondPlayerUsername);
            DrawPhaseView drawPhaseView = DrawPhaseView.getInstance();
            drawPhaseView.newCard(LoginController.user.getUsername());
            System.out.println("EndPhase");
            duelModel.turn = 1 - duelModel.turn;
            drawPhaseView.newCard(secondPlayerUsername);
            System.out.println("EndPhase");
            duelModel.turn = 1 - duelModel.turn;
            drawPhaseView.run(scanner);
        } else {
            duelModel = new DuelModel(secondPlayerUsername, LoginController.user.getUsername());
            DrawPhaseView drawPhaseView = DrawPhaseView.getInstance();
            drawPhaseView.newCard(secondPlayerUsername);
            System.out.println("EndPhase");
            duelModel.turn = 1 - duelModel.turn;
            drawPhaseView.newCard(LoginController.user.getUsername());
            System.out.println("EndPhase");
            duelModel.turn = 1 - duelModel.turn;
            drawPhaseView.run(scanner);
        }
        duelController = new DuelController();
        duelController.setDuelModel(duelModel);
    }

    protected void deselect(Matcher matcher) {
        if (matcher.find()) {
            System.out.println(duelController.deselect());
        }
    }

    public String enterPhase(String currentPhase, Scanner scanner) {
        String newPhase = scanner.nextLine();
        if (newPhase.equals(currentPhase)) {
            System.out.println("please enter another phase.");
        } else {
            if (newPhase.equals("MainPhase1")) {
                return newPhase;
            } else if (newPhase.equals("MainPhase2")) {
                return newPhase;
            } else if (newPhase.equals("BattlePhase")) {
                return newPhase;
            } else {
                System.out.println("please enter correct phase.");
            }
        }
        return null;
    }

    protected void activateEffect() {
    }

    protected void showGraveyard() {
        ArrayList<String> output = duelController.showGraveYard();
        for (String s : output) {
            System.out.println(s);
        }
    }

    protected void showCard() {

    }

    protected void showSelectedCard() {

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