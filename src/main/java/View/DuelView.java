package View;

import Controller.DuelController;
import Controller.LoginAndSignUpController;
import Controller.NewCardToHandController;
import Controller.RockPaperScissors;
import Model.Deck;
import Model.DuelModel;
import Model.User;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuelView implements Initializable {
    public static DuelView duelView;
    public GridPane fieldsGridPane;
    public HBox upHBox;
    public HBox downHBox;
    public ImageView userBin;
    public ImageView opponentField;
    public ImageView userField;
    public ImageView opponentDeck;
    public ImageView opponentBin;
    protected DuelController duelController;
    protected DuelModel duelModel;
    protected Scanner scanner1;
    protected boolean isCommandInvalid = true;
    protected boolean isAi;
    public static String secondPlayerUsername1;

    public DuelView() {

    }

    public static DuelView getInstance() {
        if (duelView == null)
            duelView = new DuelView();
        return duelView;
    }
    public void start(Stage stage){
         try {
        URL url = new File("src/main/java/FXMLFiles/DuelField.fxml").toURI().toURL();
        Parent root ;
            root = FXMLLoader.load(Objects.requireNonNull(url));
            stage.setTitle("duel");
            stage.setScene(new Scene(root, 1349, 764));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showRockPaperScissors(){
        URL url = null;
        try {
            url = new File("src/main/java/FXMLFiles/RockPaperScissors.fxml").toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Parent root = null;
        try {
            assert url != null;
            root = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();

        stage.setTitle("RockPaperScissorsPage");
        assert root != null;
        stage.setScene(new Scene(root, 1360, 765));
        stage.show();
    }

    public void selectFirstPlayer(String secondPlayerUsername, Scanner scanner, DuelView duelView, boolean isAi) {
        secondPlayerUsername1 = secondPlayerUsername;
        scanner1 = scanner;
        this.isAi = isAi;

        if (RockPaperScissors.starterTheGame == 0) {
            duelModel = new DuelModel(LoginAndSignUpController.user.getUsername(), secondPlayerUsername);
            duelController = DuelController.getInstance();
            NewCardToHandController newCardToHandController = NewCardToHandController.getInstance();
            duelController.setDuelModel(duelModel, duelView, duelController, isAi);
            DrawPhaseView drawPhaseView = DrawPhaseView.getInstance();
            drawPhaseView.newCard(scanner, LoginAndSignUpController.user.getUsername(), true);
            System.out.println("EndPhase");
            duelModel.turn = 1 - duelModel.turn;
            drawPhaseView.newCard(scanner, secondPlayerUsername, true);
            System.out.println("EndPhase");
            duelModel.turn = 1 - duelModel.turn;
            StandByPhaseView standByPhaseView = StandByPhaseView.getInstance();
            showBoard();
            standByPhaseView.run(scanner);
        } else {
            duelModel = new DuelModel(secondPlayerUsername, LoginAndSignUpController.user.getUsername());
            duelController = DuelController.getInstance();
            duelController.setDuelModel(duelModel, duelView, duelController, isAi);
            DrawPhaseView drawPhaseView = DrawPhaseView.getInstance();
            drawPhaseView.newCard(scanner, secondPlayerUsername, true);
            System.out.println("EndPhase");
            duelModel.turn = 1 - duelModel.turn;
            drawPhaseView.newCard(scanner, LoginAndSignUpController.user.getUsername(), true);
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
            System.out.println(DuelController.getInstance().deselect());
        }
    }

    public String scanAddressForTributeForRitualSummon() {
        System.out.println("please enter two address from deck for tribute for ritual summon" +
                "separate it with space (ex: 3 4)");
        return scanner1.nextLine();
    }

    public Integer scanAddressOfCardForRitualSummon() {
        System.out.println("please enter address of monster in your hand for ritual summon");
        return scanner1.nextInt();
    }


    public String getCardNameForTrapMindCrush() {
        System.out.println("enter card name:");
        return scanner1.nextLine();
    }

    public void opponentActiveEffect(boolean hasAnySpellOrTrap) {
        if (hasAnySpellOrTrap) {
            duelModel.turn = 1 - duelModel.turn;
            if (duelModel.getCreatorUsername(duelModel.turn).equals("ai")) {
                DuelController.getInstance().aiOpponentActiveSpellOrTrap();
                duelModel.turn = 1 - duelModel.turn;
            } else {
                System.out.println("now it will be " + duelModel.getUsernames().get(duelModel.turn) + " turn");
                System.out.println("do you want to activate your trap or spell?");
                System.out.println("enter YES or NO");
                String response = scanner1.nextLine();
                while (!response.equals("NO") && !response.equals("YES")) {
                    System.out.println("you must enter NO or YES");
                    response = scanner1.nextLine();
                }
                if (response.equals("YES")) {
                    // check ...
                    String result = DuelController.getInstance().opponentActiveSpellOrTrap();
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
//        ArrayList<String> board = duelView.duelModel.getBoard();
//        for (String s : board) {
//            System.out.println(s);
//        }
    }

    public void showGraveyard(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            ArrayList<String> output = DuelController.getInstance().showGraveYard(duelView.duelModel.turn);
            for (String s : output) {
                System.out.println(s);
            }
        }
    }

    protected void showCard(Matcher matcher) {
        if (matcher.find()) {
            if (!matcher.group(1).equals("--selected")) {
                isCommandInvalid = false;
                ArrayList<String> output = DuelController.getInstance().checkCard(matcher);
                for (String s : output) {
                    System.out.println(s);
                }
            }
        }
    }

    protected void showSelectedCard(Matcher matcher) {
        if (matcher.find()) {
            isCommandInvalid = false;
            ArrayList<String> output = DuelController.getInstance().checkSelectedCard(matcher);
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
            int place = Integer.parseInt(matcher.group(1));
            isCommandInvalid = false;
            System.out.println(DuelController.getInstance().selectFieldZone(place));
        }
    }

    public void selectOpponentField(Matcher matcher) {
        if (matcher.find()) {
            int place = Integer.parseInt(matcher.group(1));
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
        ArrayList<String> output = DuelController.getInstance().showGraveYard(turn);
        for (String s : output) {
            System.out.println(s);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                URL url = null;
                try {
                    url = new File("src/main/resource/Monsters/Unknown.jpg").toURI().toURL();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
              Image image = new Image(Objects.requireNonNull(url).toString());
                ImageView imageView=new ImageView(image);
                imageView.setFitHeight(120);
                imageView.setFitWidth(100);
                fieldsGridPane.add(imageView,j,i);
                fieldsGridPane.setHgap(90);
                fieldsGridPane.setVgap(15);
            }
        }
        for (int i = 0; i < 5; i++) {
            URL url = null;
            try {
                url = new File("src/main/resource/Monsters/Unknown.jpg").toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Image image = new Image(Objects.requireNonNull(url).toString());
            ImageView imageView=new ImageView(image);
            imageView.setFitHeight(100);
            imageView.setFitWidth(80);
            ImageView imageView1=new ImageView(image);
            imageView1.setFitHeight(100);
            imageView1.setFitWidth(80);
            upHBox.setSpacing(20);
            downHBox.setSpacing(20);
            upHBox.getChildren().add(imageView);
            downHBox.getChildren().add(imageView1);
        }
        downHBox.setAlignment(Pos.CENTER_RIGHT);
      // duelModel.getBoard();
    }

}