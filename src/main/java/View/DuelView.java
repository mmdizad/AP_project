package View;

import Controller.*;
import Model.DuelModel;

import java.io.IOException;
import java.util.ArrayList;
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
        String response = "";
        try {
            LoginController.dataOutputStream.writeUTF("Select First Player/" + secondPlayerUsername + "/"
                    + LoginController.token);
            LoginController.dataOutputStream.flush();
            response = LoginController.dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        scanner1 = scanner;
        this.isAi = isAi;
        int starterGame = Integer.parseInt(response);
        if (starterGame != -1) {
            if (starterGame % 2 == 0) {
                duelModel = new DuelModel(LoginController.user.getUsername(), secondPlayerUsername);
                duelController = DuelController.getInstance();
                duelController.setDuelModel(duelModel, duelView, duelController, isAi);
                DrawPhaseView drawPhaseView = DrawPhaseView.getInstance();
                drawPhaseView.newCard(scanner, LoginController.user.getUsername(), true);
                System.out.println("EndPhase");
//                duelModel.turn = 1 - duelModel.turn;
//                drawPhaseView.newCard(scanner, secondPlayerUsername, true);
//                System.out.println("EndPhase");
//                duelModel.turn = 1 - duelModel.turn;
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
    }

    protected void deselect(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            System.out.println(DuelController.getInstance().deselect());
        }
    }

    public String getCardNameForTrapMindCrush() {
        System.out.println("enter card name:");
        return scanner1.nextLine();
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
        ArrayList<String> board = duelView.duelModel.getBoard();
        for (String s : board) {
            System.out.println(s);
        }
    }

    public void showGraveyard(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            System.out.println(DuelController.getInstance().showGraveYard(duelView.duelModel.turn));
        }
    }

    protected void showCard(Matcher matcher) {
        if (matcher.find()) {
            if (!matcher.group(1).equals("--selected")) {
                isCommandInvalid = false;
                System.out.println(DuelController.getInstance().checkCard(matcher));
            }
        }
    }

    protected void showSelectedCard(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            System.out.println(DuelController.getInstance().checkSelectedCard(matcher));
        }
    }

    public void selectMonster(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            System.out.println(DuelController.getInstance().selectMonster(matcher));
        }
    }

    public void selectOpponentMonster(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            System.out.println(DuelController.getInstance().selectOpponentMonster(matcher));
        }
    }

    public void selectField(Matcher matcher) {
        if (matcher.find()) {
            int place = Integer.parseInt(matcher.group(1)) - 1;
            isCommandInvalid = false;
            System.out.println(DuelController.getInstance().selectFieldZone(place));
        }
    }

    public void selectOpponentField(Matcher matcher) {
        if (matcher.find()) {
            int place = Integer.parseInt(matcher.group(1)) - 1;
            isCommandInvalid = false;
            System.out.println(DuelController.getInstance().selectOpponentFieldZone(place));
        }
    }

    public void selectSpellOrTrap(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            System.out.println(DuelController.getInstance().selectSpellOrTrap(matcher));
        }
    }

    public void selectOpponentSpell(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            System.out.println(DuelController.getInstance().selectOpponentSpellOrTrap(matcher));
        }
    }


    public void selectHand(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            System.out.println(DuelController.getInstance().selectHand(matcher));
        }
    }

    public Matcher getCommandMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        return matcher;
    }

    public void showGraveyardForSomeClasses(int turn) {
        System.out.println(DuelController.getInstance().showGraveYard(turn));

    }

}