package View;

import java.util.Scanner;

public class StandByPhaseView extends DuelView {
    private static StandByPhaseView standByPhaseView = new StandByPhaseView();

    private StandByPhaseView() {

    }

    public void run(Scanner scanner) {
        System.out.println("StandByPhase");
        MainPhaseView mainPhaseView = MainPhaseView.getInstance();
        mainPhaseView.run(scanner, "MainPhase1", true);
    }

    public static StandByPhaseView getInstance() {
        return standByPhaseView;
    }
}
