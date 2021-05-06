package View;

import Controller.StandByPhaseController;

import java.util.Scanner;

public class StandByPhaseView extends DuelView {
    private static StandByPhaseView standByPhaseView = new StandByPhaseView();

    private StandByPhaseView() {

    }

    public void run(Scanner scanner) {
        System.out.println("StandByPhase");
        StandByPhaseController standByPhaseController = new StandByPhaseController();
        boolean response = standByPhaseController.hasSpellEffectInThisPhase();
        if (response) {
            System.out.println("you want to destroy Messenger of peace spell or decrease your lp(100 unit)");
            System.out.println("enter 1 (destroy card) or 2(decrease lp)");
            int i = scanner.nextInt();
            System.out.println(standByPhaseController.effectOfSpellInThisPhase(i));
        }

        MainPhaseView mainPhaseView = MainPhaseView.getInstance();
        mainPhaseView.run(scanner, "MainPhase1", true);
    }

    public static StandByPhaseView getInstance() {
        return standByPhaseView;
    }
}
