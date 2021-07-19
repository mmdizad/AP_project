package View;

import Controller.DuelController;
import Controller.LoginController;
import Controller.MainPhaseController;
import Model.DuelModel;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;


public class MainPhaseView extends DuelView implements Set, Summon {
    private static final MainPhaseView mainPhaseView = new MainPhaseView();
    DuelController duelController = duelView.duelController;
    DuelModel duelModel = duelView.duelModel;
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
            isCommandInvalid = true;
            selectMonster(getCommandMatcher(command, "^select --monster (\\d+)$"));
            selectOpponentMonster(getCommandMatcher(command, "^select --monster (\\d+) --opponent$"));
            selectOpponentMonster(getCommandMatcher(command, "^select --opponent --monster (\\d+)$"));
            selectSpellOrTrap(getCommandMatcher(command, "^select --spell (\\d+)$"));
            selectOpponentSpell(getCommandMatcher(command, "^select --spell (\\d+) --opponent$"));
            selectOpponentSpell(getCommandMatcher(command, "^select --opponent --spell (\\d+)$"));
            selectField(getCommandMatcher(command, "^select --field (\\d+)$"));
            selectOpponentField(getCommandMatcher(command, "^select --opponent --field (\\d+)$"));
            selectOpponentField(getCommandMatcher(command, "^select --field (\\d+) --opponent$"));
            deselect(getCommandMatcher(command, "^select -d$"));
            selectHand(getCommandMatcher(command, "^select --hand (\\d+)$"));
            showCard(getCommandMatcher(command, "^card show (.+)$"));
            showSelectedCard(getCommandMatcher(command, "card show --selected"));
            showGraveyard(getCommandMatcher(command, "show graveyard"));
            summon(getCommandMatcher(command, "^summon$"));
            flipSummon(getCommandMatcher(command, "^flip-summon$"));
            specialSummon(getCommandMatcher(command, "^special-summon$"));
            increaseLP(getCommandMatcher(command, "^increase --LP (\\d+)$"));
            activateEffectMainView(getCommandMatcher(command, "^activate effect$"));

            if (command.equals("enterPhase")) {
                isCommandInvalid = false;
                enterPhase(scanner);
                break;
            } else if (command.equals("set")) {
                isCommandInvalid = false;
                set();
            } else if (command.equals("surrender")) {
                break;
            } else if (isCommandInvalid) {
                System.out.println("invalid command");
            }

            isCommandInvalid = true;
            if (anyOneWon()) {
                return;
            }
        }
    }

    public boolean anyOneWon() {
        MainPhaseController mainPhaseController = MainPhaseController.getInstance();
        return mainPhaseController.anyoneWon();
    }

    public void increaseLP(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            DuelController duelController = DuelController.getInstance();
            duelController.increaseLP(matcher);
            System.out.println("lifePoint increase");
        }
    }

    @Override
    public void summon(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            MainPhaseController mainPhaseController = MainPhaseController.getInstance();
            String result = mainPhaseController.summon();
            if (result.equals("summoned successfully")) {
                System.out.println(result);
                duelController.activeFieldInGame();
                duelController.isOpponentHasAnySpellOrTrapForActivate();
                duelModel.monsterFlipSummonOrNormalSummonForTrapHole = null;
                duelModel.monsterSummonForEffectOfSomeTraps = null;

            } else {
                System.out.println(result);
            }
        }
    }

    public void activateEffectMainView(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            MainPhaseController mainPhaseController = MainPhaseController.getInstance();
            String result = mainPhaseController.activateSpellEffectMainController();
            System.out.println(result);
        }
    }

    public Integer getCardAddressForTribute() {
        return scanner1.nextInt();
    }

    public String getStateOfCardForSummon() {
        System.out.println("please enter the state of card you want for summon (Attack or Defence)");
        return scanner1.nextLine();
    }

    @Override
    public void flipSummon(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            MainPhaseController mainPhaseController = MainPhaseController.getInstance();
            String response = mainPhaseController.flipSummon();
            if (response.equals("flipSummon Man-Eater Bug")) {
                System.out.println("flip summoned successfully");
                duelController.activeFieldInGame();
                System.out.println(mainPhaseController.flipSummonManEaterBug());
                duelController.isOpponentHasAnySpellOrTrapForActivate();
                duelModel.monsterFlipSummonOrNormalSummonForTrapHole = null;
            } else {
                if (response.equals("flip summoned successfully")) {
                    System.out.println(response);
                    duelController.activeFieldInGame();
                    duelController.isOpponentHasAnySpellOrTrapForActivate();
                    duelModel.monsterFlipSummonOrNormalSummonForTrapHole = null;
                    duelModel.monsterSummonForEffectOfSomeTraps = null;
                } else {
                    System.out.println(response);
                }
            }
        }
    }

    public int scanPlaceOfMonsterForDestroyInManEaterFlipSummon() {
        System.out.println("please enter the place of monster that you want destroyed" +
                "(in opponent board)");
        return scanner1.nextInt();
    }

    @Override
    public void specialSummon(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            MainPhaseController mainPhaseController = MainPhaseController.getInstance();
            String result = mainPhaseController.specialSummon();
            if (result.equals("summon successfully")) {
                System.out.println(result);
                duelController.activeFieldInGame();
                duelController.isOpponentHasAnySpellOrTrapForActivate();
                duelModel.monsterSummonForEffectOfSomeTraps = null;
            } else {
                System.out.println(result);
            }
        }
    }

    public String summonMonsterHasTwoMethods() {
        System.out.println("Do you want to summon this card with tribute?");
        return scanner1.nextLine();
    }

    public String normalSummonCardThatCanSummonAnotherCard() {
        System.out.println("Do you want to summon another monster in defence?");
        return scanner1.nextLine();
    }

    @Override
    public void set() {
        MainPhaseController mainPhaseController = MainPhaseController.getInstance();
        String result = mainPhaseController.set();
        if (result.equals("set successfully")) {
            System.out.println(result);
            duelView.duelController.isOpponentHasAnySpellOrTrapForActivate();
        } else {
            System.out.println(result);
        }
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
            } else {
                endPhaseMethod(scanner, newPhase);
            }
        } else if (phaseName.equals("MainPhase2")) {
            endPhaseMethod(scanner, newPhase);
        }
    }

    public void endPhaseMethod(Scanner scanner, String newPhase) {
        if (newPhase.equals("EndPhase")) {
            try {
                LoginController.dataOutputStream.writeUTF("EndPhase" + "/" + LoginController.token);
                LoginController.dataOutputStream.flush();
                LoginController.dataInputStream.readUTF();
            } catch (IOException x) {
                x.printStackTrace();
            }
            System.out.println("EndPhase");
            duelModel.turn = 1 - duelModel.turn;
            DrawPhaseView drawPhaseView = DrawPhaseView.getInstance();
            drawPhaseView.newCard(scanner, duelModel.getUsernames().get(duelModel.turn), false);
        } else {
            System.out.println("please enter another or correct phase");
            run(scanner, phaseName, false);
        }
    }
}