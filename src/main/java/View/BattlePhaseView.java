package View;

import Controller.BattlePhaseController;

import java.util.Scanner;
import java.util.regex.Matcher;

public class BattlePhaseView extends DuelView {
    private static BattlePhaseView battlePhaseView = new BattlePhaseView();

    private BattlePhaseView() {

    }

    public static BattlePhaseView getInstance() {
        return battlePhaseView;
    }

    public void run(Scanner scanner, boolean startOfPhase) {
        if (startOfPhase) {
            System.out.println("BattlePhase");
        }
        while (true) {
            String command = scanner.nextLine();
            attack(getCommandMatcher(command,"^attack ([1-5]{1})$"));
            directAttack(getCommandMatcher(command,"^attack direct$"));
            if (command.equals("enterMenu")) {
                enterPhase(scanner);
                break;
            }
        }
    }

    public void attack(Matcher matcher) {
        if (matcher.find()){
            System.out.println(BattlePhaseController.getInstance().attack(matcher));
        }
    }

    public void directAttack(Matcher matcher) {
        if (matcher.find()){
            System.out.println(BattlePhaseController.getInstance().directAttack(matcher));
        }
    }

    public void enterPhase(Scanner scanner) {
        String newPhase = scanner.nextLine();
        if (newPhase.equals("MainPhase2")) {
            BattlePhaseController.getInstance().attackedCards.clear();
            MainPhaseView mainPhaseView = MainPhaseView.getInstance();
            mainPhaseView.run(scanner, "MainPhase2", true);
        } else if (newPhase.equals("EndPhase")) {
            System.out.println("EndPhase");
            BattlePhaseController.getInstance().attackedCards.clear();
            duelModel.turn = 1 - duelModel.turn;
            DrawPhaseView drawPhaseView = DrawPhaseView.getInstance();
            drawPhaseView.newCard(scanner, duelModel.getUsernames().get(duelModel.turn), false);
            duelModel.deleteMonsterSetOrSummonInThisTurn();
        } else {
            System.out.println("please enter another or correct phase");
            run(scanner, false);
        }
    }
}