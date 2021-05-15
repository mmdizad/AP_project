package View;

import Controller.DuelController;
import Controller.LoginController;
import Controller.NewCardToHandController;
import Model.DuelModel;
import Model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuelView {
    protected static DuelView duelView;
    protected DuelController duelController;
    protected DuelModel duelModel;
    protected Scanner scanner1;
    protected boolean isCommandInvalid = true;
    protected boolean isAi;

    protected DuelView() {

    }

    public static DuelView getInstance() {
        if (duelView == null)
            duelView = new DuelView();
        return duelView;
    }

    public void selectFirstPlayer(String secondPlayerUsername, Scanner scanner, DuelView duelView, boolean isAi) {
        scanner1 = scanner;
        this.isAi = isAi;
        ArrayList<Integer> someRandomNumbers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            someRandomNumbers.add(i + 1);
        }
        Collections.shuffle(someRandomNumbers);
        int starterGame = someRandomNumbers.get(0);
        if (starterGame % 2 == 0) {
            duelModel = new DuelModel(LoginController.user.getUsername(), secondPlayerUsername);
            duelController = DuelController.getInstance();
            NewCardToHandController newCardToHandController = NewCardToHandController.getInstance();
            duelController.setDuelModel(duelModel, duelView, duelController, isAi);
            DrawPhaseView drawPhaseView = DrawPhaseView.getInstance();
            drawPhaseView.newCard(scanner, LoginController.user.getUsername(), true);
            System.out.println("EndPhase");
            duelModel.turn = 1 - duelModel.turn;
            drawPhaseView.newCard(scanner, secondPlayerUsername, true);
            System.out.println("EndPhase");
            duelModel.turn = 1 - duelModel.turn;
            StandByPhaseView standByPhaseView = StandByPhaseView.getInstance();
            showBoard();
            standByPhaseView.run(scanner);
        } else {
            duelModel = new DuelModel(secondPlayerUsername, LoginController.user.getUsername());
            duelController = DuelController.getInstance();
            duelController.setDuelModel(duelModel, duelView, duelController, isAi);
            DrawPhaseView drawPhaseView = DrawPhaseView.getInstance();
            drawPhaseView.newCard(scanner, secondPlayerUsername, true);
            System.out.println("EndPhase");
            duelModel.turn = 1 - duelModel.turn;
            drawPhaseView.newCard(scanner, LoginController.user.getUsername(), true);
            System.out.println("EndPhase");
            duelModel.turn = 1 - duelModel.turn;
            StandByPhaseView standByPhaseView = StandByPhaseView.getInstance();
            showBoard();
            standByPhaseView.run(scanner);
        }


    }

    protected void deselect(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            System.out.println(duelController.deselect());
        }
    }

    public String scanAddressForTributeForRitualSummon() {
        System.out.println("please enter two address from deck for tribute for ritual summon" +
                "separate it with space (ex: 3 4)");
        return scanner1.nextLine();
    }


    public String getCardNameForTrapMindCrush() {
        System.out.println("enter card name:");
        return scanner1.nextLine();
    }

    public void opponentActiveEffect(boolean hasAnySpellOrTrap) {
        if (hasAnySpellOrTrap) {
            duelModel.turn = 1 - duelModel.turn;
            if (duelModel.getCreatorUsername(duelModel.turn).equals("ai")) {
                duelController.aiOpponentActiveSpellOrTrap();
                duelModel.turn = 1 - duelModel.turn;
            } else {
                System.out.println("now it will be " + duelModel.getUsernames().get(duelModel.turn) + " turn");
                System.out.println("do you want to activate your trap or spell?");
                String response = scanner1.nextLine();
                while (!response.equals("NO") && !response.equals("YES")) {
                    System.out.println("you must enter NO or YES");
                    response = scanner1.nextLine();
                }
                if (response.equals("YES")) {
                    // check ...
                    String result = duelController.opponentActiveSpellOrTrap();
                    System.out.println(result);
                    duelModel.turn = 1 - duelModel.turn;
                }
                if (response.equals("NO")) {
                    duelModel.turn = 1 - duelModel.turn;
                    System.out.println("now it will be " + duelModel.getUsernames().get(duelModel.turn) + " turn");
                }
            }
        }
    }

    public Matcher scanCommandForActiveSpell() {
        String command = scanner1.nextLine();
        return getCommandMatcher(command, "^select --spell (\\d+)$");
    }

    public String scanKindOfGraveyardForActiveEffect() {
        System.out.println("please specify the graveyard(My/Opponent)");
        return scanner1.nextLine();
    }


    public Integer scanNumberOfCardForActiveEffect() {
        System.out.println("please specify the number of card you want");
        return scanner1.nextInt();
    }

    public Integer scanNumberOfCardForDeleteFromHand() {
        System.out.println("please specify the place of card you want for delete from your hand");
        return scanner1.nextInt();
    }

    public Integer scanNumberOfCardThatWouldBeDelete() {
        System.out.println("please enter number of the  cards you want to destroyed (0 or 1 or 2)");
        return scanner1.nextInt();
    }

    public String scanPlaceOfCardWantToDestroyed() {
        System.out.println("please specify the filed and place of cards you want to destroyed" +
                "(separate it with space ex: my/opponent 1)");
        return scanner1.nextLine();
    }

    public Integer scanForChoseMonsterForEquip(ArrayList<Integer> placeOfCard) {
        System.out.print("chose which Monster Want to equip ");
        for (Integer integer : placeOfCard) {
            System.out.print(integer + " ");
        }
        int place = scanner1.nextInt();
        return place;
    }

    public void showBoard() {
        ArrayList<String> board = duelModel.getBoard();
        for (String s : board) {
            System.out.println(s);
        }

    }

    public void showGraveyard(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            ArrayList<String> output = duelController.showGraveYard(duelModel.turn);
            for (String s : output) {
                System.out.println(s);
            }
        }
    }

    protected void showCard(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            ArrayList<String> output = duelController.checkCard(matcher);
            for (String s : output) {
                System.out.println(s);
            }
        }
    }

    protected void showSelectedCard(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            ArrayList<String> output = duelController.checkSelectedCard(matcher);
            for (String s : output) {
                System.out.println(s);
            }
        }
    }

    public void surrender() {
    }

    protected void select(Matcher matcher) {

    }

    public void selectMonster(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            System.out.println(duelController.selectMonster(matcher));
        }
    }

    public void selectOpponentMonster(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            System.out.println(duelController.selectOpponentMonster(matcher));
        }
    }

    public void selectField(Matcher matcher) {
        if (matcher.find()) {
            int place = Integer.parseInt(matcher.group(1));
            isCommandInvalid = false;
            System.out.println(duelController.selectFieldZone(place));
        }
    }

    public void selectOpponentField(Matcher matcher) {
        if (matcher.find()) {
            int place = Integer.parseInt(matcher.group(1));
            isCommandInvalid = false;
            System.out.println(duelController.selectOpponentFieldZone(place));
        }
    }

    public void selectSpellOrTrap(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            System.out.println(duelController.selectSpellOrTrap(matcher));
        }
    }

    public void selectOpponentSpell(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            System.out.println(duelController.selectOpponentSpellOrTrap(matcher));
        }
    }


    public void selectHand(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            System.out.println(duelController.selectHand(matcher));
        }
    }

    public Matcher getCommandMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        return matcher;
    }

    public void showGraveyardForSomeClasses(int turn) {
        ArrayList<String> output = duelController.showGraveYard(turn);
        for (String s : output) {
            System.out.println(s);
        }
    }

}