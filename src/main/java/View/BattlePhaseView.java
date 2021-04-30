package View;

import java.util.Scanner;
import java.util.regex.Matcher;

public class BattlePhaseView extends DuelView {
    private static BattlePhaseView battlePhaseView = new BattlePhaseView();

    private BattlePhaseView(){

    }

    public static BattlePhaseView getInstance() {
        return battlePhaseView;
    }

    public void run(Scanner scanner){

    }

    public void attack(Matcher matcher) {
    }

    public void directAttack(Matcher matcher) {
    }
}