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
            isCommandInvalid = true;
            String command = scanner.nextLine();
            attack(getCommandMatcher(command, "^attack ([1-5]{1})$"), scanner);
            directAttack(getCommandMatcher(command, "^attack direct$"));
            selectMonster(getCommandMatcher(command, "^select --monster (\\d+)$"));
            selectOpponentMonster(getCommandMatcher(command, "^select --monster (\\d+) --opponent$"));
            selectOpponentMonster(getCommandMatcher(command, "^select --opponent --monster (\\d+)$"));
            selectSpellOrTrap(getCommandMatcher(command, "^select --spell (\\d+)$"));
            selectOpponentSpell(getCommandMatcher(command, "^select --spell (\\d+) --opponent$"));
            selectOpponentSpell(getCommandMatcher(command, "^select --opponent --spell (\\d+)$"));
            selectField(getCommandMatcher(command, "^select --field"));
            selectOpponentField(getCommandMatcher(command, "^select --opponent --field"));
            selectOpponentField(getCommandMatcher(command, "^select --field --opponent"));
            deselect(getCommandMatcher(command, "^select -d$"));
            selectHand(getCommandMatcher(command, "^select --hand (\\d+)$"));
            showCard(getCommandMatcher(command, "^card show (.+)$"));
            showSelectedCard(getCommandMatcher(command, "card show --selected"));
            showGraveyard(getCommandMatcher(command, "show graveyard"));
            activateEffectBattlePhaseView(getCommandMatcher(command, "^activate effect$"));
            if (command.equals("enterMenu")) {
                enterPhase(scanner);
                break;
            }
            if (isCommandInvalid) {
                System.out.println("invalid command");
            }
            isCommandInvalid = true;
        }
    }

    public void attack(Matcher matcher, Scanner scanner) {
        if (matcher.find()) {
            isCommandInvalid = false;
            String response = BattlePhaseController.getInstance().attack(matcher);
            if (response.startsWith("opponent’s monster card was") || response.startsWith("no card is destroyed")||
            response.startsWith("the defense position monster is destroyed") || response.startsWith("Your monster card is destroyed and you received")
            || response.startsWith("both you and your opponent monster") || response.startsWith("your opponent’s monster is destroyed")){
                duelController.isOpponentHasAnySpellOrTrapForActivate();
            }
            System.out.println(response);
            if (response.equals("opponent had Negate Attack trap and canceled your attack and ended battle phase,enter the phase you want to go:")) {
                enterPhase(scanner);
            }
        }

    }

    public String getCyberseCard() {
        System.out.println("opponent attacked you but you had Texchanger,now enter a Cyberse typed card to special summon: ");
        return scanner1.nextLine();
    }

    public void directAttack(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            String response = BattlePhaseController.getInstance().directAttack(matcher);
            if (response.startsWith("you opponent receives")){
                duelController.isOpponentHasAnySpellOrTrapForActivate();
            }
            System.out.println(response);
        }
    }

    public void enterPhase(Scanner scanner) {
        String newPhase = scanner.nextLine();
        if (newPhase.equals("MainPhase2")) {
            BattlePhaseController.getInstance().attackedCards.clear();
            MainPhaseView mainPhaseView = MainPhaseView.getInstance();
            mainPhaseView.run(scanner, "MainPhase2", true);
        } else if (newPhase.equals("EndPhase")) {
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
            BattlePhaseController.getInstance().attackedCards.clear();
            duelModel.turn = 1 - duelModel.turn;
            DrawPhaseView drawPhaseView = DrawPhaseView.getInstance();
            drawPhaseView.newCard(scanner, duelModel.getUsernames().get(duelModel.turn), false);
        } else {
            System.out.println("please enter another or correct phase");
            run(scanner, false);
        }
    }

    public void activateEffectBattlePhaseView(Matcher matcher) {
        if (matcher.find()) {
            BattlePhaseController battlePhaseController = BattlePhaseController.getInstance();
            String result = battlePhaseController.activateEffectBattlePhaseController();
            System.out.println(result);
        }
    }
}