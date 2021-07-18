package View;

import Controller.StandByPhaseController;
import Model.DuelModel;

import java.util.Scanner;

public class StandByPhaseView extends DuelView {
    private static final StandByPhaseView standByPhaseView = new StandByPhaseView();
    DuelModel duelModel = duelView.duelModel;

    private StandByPhaseView() {

    }

    public void run(Scanner scanner) {
        System.out.println("StandByPhase");
        StandByPhaseController standByPhaseController = new StandByPhaseController();
        int response = standByPhaseController.hasSpellEffectInThisPhase();
        if (response > 0) {
            int i;
            for (int j = 0; j < response; j++) {
                System.out.println("you want to destroy Messenger of peace spell (in order of time being on the field)" +
                        " or decrease your lp(100 unit)");
                System.out.println("enter 1 (destroy cards) or 2(decrease lp)");
                i = scanner.nextInt();
                String result = standByPhaseController.effectOfSpellInThisPhase(i);
                System.out.println(result);
            }
        }
        MainPhaseView mainPhaseView = MainPhaseView.getInstance();
        mainPhaseView.run(scanner, "MainPhase1", true);
    }

    public static StandByPhaseView getInstance() {
        return standByPhaseView;
    }
}
