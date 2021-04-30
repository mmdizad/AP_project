package View;

import java.util.Scanner;

public class MainPhase1View extends DuelView implements Set, Summon {
    private static MainPhase1View mainPhase1View = new MainPhase1View();

    private MainPhase1View() {

    }

    public static MainPhase1View getInstance() {
        return mainPhase1View;
    }

    public void run(Scanner scanner) {
        while (true) {
            String command = scanner.nextLine();
            selectMonster(getCommandMatcher(command, "^select --monster (\\d+)$"));
            selectOpponentMonster(getCommandMatcher(command, "^select --monster (\\d+) --opponent$"));
            selectOpponentMonster(getCommandMatcher(command, "^select --opponent --monster (\\d+)$"));
            selectSpellOrTrap(getCommandMatcher(command, "^select --spell (\\d+)$"));
            selectOpponentSpell(getCommandMatcher(command, "^select --spell (\\d+) --opponent$"));
            selectOpponentSpell(getCommandMatcher(command, "^select --opponent --spell (\\d+)$"));
            selectField(getCommandMatcher(command, "^select --field"));
            selectOpponentField(getCommandMatcher(command, "^select --opponent --filed"));
            selectOpponentField(getCommandMatcher(command, "^select --field --opponent"));
            if (command.equals("enterPhase")) {
                String newPhase = enterPhase("MainPhase1", scanner);
                if (newPhase.equals("MainPhase2")) {
                    MainPhase2View mainPhase2View = MainPhase2View.getInstance();
                    mainPhase2View.run(scanner);
                    break;
                } else if (newPhase.equals("BattlePhase")) {
                    BattlePhaseView battlePhaseView = BattlePhaseView.getInstance();
                    battlePhaseView.run(scanner);
                    break;
                }
            }
        }
    }

    @Override
    public void summon() {

    }

    @Override
    public void flipSummon() {

    }

    @Override
    public void specialSummon() {

    }

    @Override
    public void ritualSummon() {

    }

    @Override
    public void set() {

    }

    @Override
    public void setTrap() {

    }

    @Override
    public void setSpell() {

    }

    @Override
    public void setMonster() {

    }

    @Override
    public void setPosition() {

    }
}