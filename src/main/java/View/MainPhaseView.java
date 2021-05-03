package View;

import Controller.MainPhaseController;
import Model.Monster;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainPhaseView extends DuelView implements Set, Summon {
    private static MainPhaseView mainPhaseView = new MainPhaseView();
    private String phaseName;
    Scanner scanner1;

    private MainPhaseView() {

    }

    public static MainPhaseView getInstance() {
        return mainPhaseView;
    }

    public void run(Scanner scanner, String nameOfPhase, boolean startOfPhase) {
        scanner1 = scanner;
        phaseName = nameOfPhase;
        if (startOfPhase) {
            System.out.println(phaseName);
        }

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
            showCard(getCommandMatcher(command, "^card show (.+)$"));
            showSelectedCard(getCommandMatcher(command, "card show --selected"));
            showGraveyard(getCommandMatcher(command, "show graveyard"));
            summon(getCommandMatcher(command, "^summon$"));
            flipSummon(getCommandMatcher(command, "^flip-summon$"));
            specialSummon(getCommandMatcher(command, "^special-summon$"));
            if (command.equals("enterPhase")) {
                enterPhase(scanner);
                break;
            } else if (command.equals("set"))
                set();
        }
    }

    @Override
    public void summon(Matcher matcher) {
        if (matcher.find()) {
            MainPhaseController mainPhaseController = MainPhaseController.getInstance();
            System.out.println(mainPhaseController.summon());
        }
    }

    public Integer getCardAddressForTribute() {
        return scanner1.nextInt();
    }

    public String getStateOfCardForSummon() {
        return scanner1.nextLine();
    }

    @Override
    public void flipSummon(Matcher matcher) {
        if (matcher.find()) {
            MainPhaseController mainPhaseController = MainPhaseController.getInstance();
            System.out.println(mainPhaseController.flipSummon());
        }
    }

    @Override
    public void specialSummon(Matcher matcher) {
        if (matcher.find()) {
            MainPhaseController mainPhaseController = MainPhaseController.getInstance();
            System.out.println(mainPhaseController.specialSummon());
        }
    }

    public String summonMonsterHasTwoMethods() {
        System.out.println("Do you want to summon this card with tribute?");
        return scanner1.nextLine();
    }

    public String normalSummonCardThatCanSummonAnotherCard(){
        System.out.println("Do you want to summon another monster in defence?");
        return scanner1.nextLine();
    }
    @Override
    public void ritualSummon() {

    }

    @Override
    public void set() {
        MainPhaseController mainPhaseController = MainPhaseController.getInstance();
        System.out.println(mainPhaseController.set());
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

    public void enterPhase(Scanner scanner) {
        String newPhase = scanner.nextLine();
        if (phaseName.equals("MainPhase1")) {
            if (newPhase.equals("MainPhase2")) {
                run(scanner, "MainPhase2", true);
            } else if (newPhase.equals("BattlePhase")) {
                BattlePhaseView battlePhaseView = BattlePhaseView.getInstance();
                battlePhaseView.run(scanner, true);
            } else if (newPhase.equals("EndPhase")) {
                System.out.println("EndPhase");
                duelModel.turn = 1 - duelModel.turn;
                DrawPhaseView drawPhaseView = DrawPhaseView.getInstance();
                drawPhaseView.newCard(scanner, duelModel.getUsernames().get(duelModel.turn), false);
                duelModel.deleteMonsterSetOrSummonInThisTurn();
            } else {
                System.out.println("please enter another or correct phase");
                run(scanner, phaseName, false);
            }
        } else if (phaseName.equals("MainPhase2")) {
            if (newPhase.equals("EndPhase")) {
                System.out.println("EndPhase");
                duelModel.turn = 1 - duelModel.turn;
                DrawPhaseView drawPhaseView = DrawPhaseView.getInstance();
                drawPhaseView.newCard(scanner, duelModel.getUsernames().get(duelModel.turn), false);
                duelModel.deleteMonsterSetOrSummonInThisTurn();
            } else {
                System.out.println("please enter another or correct phase");
                run(scanner, phaseName, false);
            }
        }
    }

}