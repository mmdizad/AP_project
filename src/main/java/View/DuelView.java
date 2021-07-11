package View;

import Controller.DuelController;
import Controller.LoginAndSignUpController;
import Controller.NewCardToHandController;
import Controller.RockPaperScissors;
import Model.DuelModel;
import Model.User;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuelView implements Initializable {

    public static DuelView duelView;
    public static String secondPlayerUsername1;
    public static Stage stage = new Stage();
    public static AnchorPane pane = new AnchorPane();
    public static HBox hBoxS = new HBox();
    public static HBox downHBoxS = new HBox();
    public static String currentPhase = "mainPhase1";
    public static ImageView userBinS;
    public static ImageView opponentFieldS;
    public static ImageView userFieldS;
    public static ImageView opponentDeckS;
    public static ImageView opponentBinS;
    public static Label userLifPointLBL;
    public static Label opponentLifPointLBL;
    public static Label specificationsOfCard;
    public static ImageView showCardImage;
    public static ImageView userProfile;
    public static ImageView opponentProfile;
    public HBox upHBox;
    public HBox downHBox;
    public ImageView userBin;
    public ImageView opponentField;
    public ImageView userField;
    public ImageView opponentDeck;
    public ImageView opponentBin;
    public AnchorPane DuelFieldPane;
    public Label lifePointOfUser;
    public Label lifePointOfOpponent;
    public Label cardSpecifications;
    public ImageView showCard;
    public ImageView profileOfUser;
    public ImageView profileOfOpponent;
    public HBox hboxOpponenetSpell;
    public HBox hboxOpponentMonster;
    public HBox hboxMonster;
    public HBox hboxSpell;
    public Label errorLBL;
    public Label opponentUsername;
    public Label userUsername;
    public static Label userUsernameLBL;
    public static Label opponentUsernameLBL;
    public static HBox hboxOpponenetSpellS;
    public static HBox hboxOpponentMonsterS;
    public static HBox hboxMonsterS;
    public static HBox hboxSpellS;
    public static Label informationLBL;
    public static AnchorPane duelFieldPaneS;
    protected DuelController duelController;
    protected DuelModel duelModel;
    protected Scanner scanner1;
    protected boolean isCommandInvalid = true;
    protected boolean isAi;
    @FXML
    Text changePhaseTxt;

    public DuelView() {

    }

    public static DuelView getInstance() {
        if (duelView == null)
            duelView = new DuelView();
        return duelView;
    }

    public void start() {
        try {
            URL url = new File("src/main/java/FXMLFiles/DuelField.fxml").toURI().toURL();
            Parent root;
            root = FXMLLoader.load(Objects.requireNonNull(url));
            stage.setTitle("duel");
            stage.setScene(new Scene(root, 1349, 764));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void battlePhaseBtnEvent(ActionEvent actionEvent) {
        if (currentPhase.equals("mainPhase1")) {
            currentPhase = "battlePhase";
            changePhaseTxt.setText("BATTLE PHASE");
            FadeTransition fadeTransition = new FadeTransition();
            fadeTransition.setDuration(Duration.seconds(2));
            fadeTransition.setNode(changePhaseTxt);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.play();
        }
    }

    public void mainPhase2BtnEvent(ActionEvent actionEvent) {
        if (currentPhase.equals("battlePhase") || currentPhase.equals("mainPhase1")) {
            currentPhase.equals("mainPhase2");
            changePhaseTxt.setText("MAIN PHASE 2");
            FadeTransition fadeTransition = new FadeTransition();
            fadeTransition.setDuration(Duration.seconds(2));
            fadeTransition.setNode(changePhaseTxt);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.play();
        }
    }

    public void endPhaseBtnEvent(ActionEvent actionEvent) {
        currentPhase.equals("mainPhase1");
        changePhaseTxt.setText("MAIN PHASE 1");
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.seconds(2));
        fadeTransition.setNode(changePhaseTxt);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        MainPhaseView.getInstance().enterPhase("EndPhase");
    }

    public void showRockPaperScissors() {
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

        stage.setTitle("RockPaperScissorsPage");
        assert root != null;
        stage.setScene(new Scene(root, 1360, 765));
        stage.show();

        Media media = new Media(Paths.get("src/main/resource/Music/yugioh.mp3").toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
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
            start();
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
            start();
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
        duelModel.getBoard();
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
        opponentFieldS = opponentField;
        opponentBinS = opponentBin;
        userFieldS = userField;
        userBinS = userBin;
        pane = DuelFieldPane;
        hBoxS = upHBox;
        downHBoxS = downHBox;
        userLifPointLBL = lifePointOfUser;
        opponentLifPointLBL = lifePointOfOpponent;
        showCardImage = showCard;
        specificationsOfCard = cardSpecifications;
        userProfile = profileOfUser;
        opponentProfile = profileOfOpponent;
        hboxOpponenetSpellS = hboxOpponenetSpell;
        hboxOpponentMonsterS = hboxOpponentMonster;
        hboxMonsterS = hboxMonster;
        hboxSpellS = hboxSpell;
        informationLBL = errorLBL;
        userUsernameLBL = userUsername;
        opponentUsernameLBL = opponentUsername;
        for (int j = 0; j < 5; j++) {
            hboxMonsterS.setSpacing(95);
            hboxMonsterS.getChildren().add(getImage());
            hboxOpponentMonsterS.setSpacing(95);
            hboxOpponentMonsterS.getChildren().add(getImage());
            hboxSpellS.setSpacing(95);
            hboxSpellS.getChildren().add(getImage());
            hboxOpponenetSpellS.setSpacing(95);
            hboxOpponenetSpellS.getChildren().add(getImage());
        }
        for (int i = 0; i < 8; i++) {
            URL url = null;
            try {
                url = new File("src/main/resource/Icons/100401Parts1.dds.png").toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Image image = new Image(Objects.requireNonNull(url).toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(110);
            imageView.setFitWidth(90);
            ImageView imageView1 = new ImageView(image);
            imageView1.setFitHeight(110);
            imageView1.setFitWidth(90);
            upHBox.setSpacing(20);
            downHBox.setSpacing(20);
            upHBox.getChildren().add(imageView);
            downHBox.getChildren().add(imageView1);
        }
        downHBox.setAlignment(Pos.CENTER_RIGHT);
        //duelModel.getBoard();
    }


    public ImageView getImage() {
        URL url = null;
        try {
            url = new File("src/main/resource/Icons/100201Parts1.dds.png").toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Image image = new Image(Objects.requireNonNull(url).toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(110);
        imageView.setFitWidth(90);
        return imageView;
    }

    public void setCard(MouseEvent mouseEvent) {
        selectHand(getCommandMatcher("select --hand 1", "^select --hand (\\d+)$"));
        MainPhaseView.getInstance().set();
    }

    public void cheat(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setTitle("Cheat");
       alert.setContentText("do you want to increase 2000 life for 2000 coins?");

        ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(okButton, noButton);
        alert.showAndWait().ifPresent(type -> {
            if (type == okButton) {
                duelModel=DuelView.getInstance().duelModel;
                User user =User.getUserByUsername(duelModel.getUsernames().get(duelModel.turn));
               if(user.getCoins()>2000){
                   user.decreaseCoins(2000);
                   duelModel.increaseLifePoint(2000,duelModel.turn);
                   changePhaseTxt.setText("Life increase successfully");
                   FadeTransition fadeTransition = new FadeTransition();
                   fadeTransition.setDuration(Duration.seconds(2));
                   fadeTransition.setNode(changePhaseTxt);
                   fadeTransition.setFromValue(1);
                   fadeTransition.setToValue(0);
                   fadeTransition.play();
                   duelModel.getBoard();
               }else{
                   changePhaseTxt.setText("you dont have enough money!");
                   FadeTransition fadeTransition = new FadeTransition();
                   fadeTransition.setDuration(Duration.seconds(2));
                   fadeTransition.setNode(changePhaseTxt);
                   fadeTransition.setFromValue(1);
                   fadeTransition.setToValue(0);
                   fadeTransition.play();
               }
            } else if (type == noButton) {

            }
        });
    }
}