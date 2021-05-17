package View;

import Controller.DuelController;
import Controller.MainPhaseController;
import Model.DuelModel;

import java.util.Scanner;
import java.util.regex.Matcher;


public class MainPhaseView extends DuelView implements Set, Summon {
    private static final MainPhaseView mainPhaseView = new MainPhaseView();
    private final DuelController duelController = duelView.duelController;
    private final DuelModel duelModel = duelView.duelModel;
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
        if (isAi && duelModel.getUsernames().get(duelModel.turn).equals("ai")) {
            aiMainPhaseView();
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
        }
    }

    public void aiMainPhaseView() {
        MainPhaseController mainPhaseController = MainPhaseController.getInstance();
        mainPhaseController.aiMainPhaseController();
    }

    @Override
    public void summon(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            MainPhaseController mainPhaseController = MainPhaseController.getInstance();
            String result = mainPhaseController.summon();
            if (result.equals("summoned successfully")) {
                System.out.println(result);
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
                System.out.println(mainPhaseController.flipSummonManEaterBug());
                duelController.isOpponentHasAnySpellOrTrapForActivate();
                duelModel.monsterFlipSummonOrNormalSummonForTrapHole = null;
            } else {
                if (response.equals("flip summoned successfully")) {
                    System.out.println(response);
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
            showBoard();
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
                if (duelModel.getBorrowCards().size() > 0) {
                    duelController.refundsTheBorrowCards();
                }
                duelController.hasSwordCard();
                duelController.hasSupplySquadCard();
                duelController.hasSomeCardsInsteadOfScanner();
                duelModel.deleteMonstersDestroyedInThisTurn();
                duelModel.deleteSpellAndTrapsSetInThisTurn();
                duelModel.deleteCardsInsteadOfScanners();
                duelModel.deleteMonsterSetOrSummonInThisTurn();
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


}