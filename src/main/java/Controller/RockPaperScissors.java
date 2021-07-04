package Controller;

import View.DuelView;
import View.StartDuelView;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class RockPaperScissors implements Initializable {

    public static int turnOfGame;
    public static int starterTheGame;
    private int numberOfImageSelected1;

    @FXML
    public ImageView imageView1;

    @FXML
    public ImageView imageView2;

    @FXML
    public ImageView imageView3;

    @FXML
    public ImageView imageView4;

    @FXML
    public ImageView imageView5;

    @FXML
    public ImageView imageView6;

    @FXML
    public AnchorPane anchorPane;

    @FXML
    public Label label;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        translateTransitionImageView(imageView1, 1);
        translateTransitionImageView(imageView2, 1);
        translateTransitionImageView(imageView3, 1);
        translateTransitionImageView(imageView4, 1);
        translateTransitionImageView(imageView5, 1);
        translateTransitionImageView(imageView6, 1);
    }

    public void translateTransitionImageView(ImageView imageView, int turn) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setFromX(0f);
        translateTransition.setByX(1200);
        translateTransition.setCycleCount(1);
        translateTransition.setNode(imageView);
        translateTransition.setDuration(new Duration(2000));
        translateTransition.setAutoReverse(true);
        translateTransition.play();
        imageView1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (turn == 1) {
                    numberOfImageSelected1 = 1;
                    chooseImage();
                } else {
                    URL url = null;
                    try {
                        url = new File("src/main/java/View/jya_pa_4.jpg").toURI().toURL();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    assert url != null;
                    imageView1.setImage(new Image(url.toString()));
                    if (numberOfImageSelected1 == 1)
                        turnOfGame = 0;
                    else if (numberOfImageSelected1 == 2)
                        turnOfGame = 0;
                    else if (numberOfImageSelected1 == 3)
                        turnOfGame = 1;
                    translateTransition.pause();
                    starterTheGame(turnOfGame);
                }
            }
        });

        imageView2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (turn == 1) {
                    numberOfImageSelected1 = 2;
                    chooseImage();
                } else {
                    URL url = null;
                    try {
                        url = new File("src/main/java/View/jya_gu_4.jpg").toURI().toURL();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    assert url != null;
                    imageView2.setImage(new Image(url.toString()));
                    if (numberOfImageSelected1 == 1)
                        turnOfGame = 0;
                    else if (numberOfImageSelected1 == 2)
                        turnOfGame = 0;
                    else if (numberOfImageSelected1 == 3)
                        turnOfGame = 1;
                    translateTransition.pause();
                    starterTheGame(turnOfGame);
                }
            }
        });


        imageView3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (turn == 1) {
                    numberOfImageSelected1 = 3;
                    chooseImage();
                } else {
                    URL url = null;
                    try {
                        url = new File("src/main/java/View/jya_cyo_4.jpg").toURI().toURL();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    assert url != null;
                    imageView3.setImage(new Image(url.toString()));
                    if (numberOfImageSelected1 == 1)
                        turnOfGame = 1;
                    else if (numberOfImageSelected1 == 2)
                        turnOfGame = 0;
                    else if (numberOfImageSelected1 == 3)
                        turnOfGame = 0;
                    translateTransition.pause();
                    starterTheGame(turnOfGame);
                }
            }
        });
    }

    public void chooseImage() {
        label.setText("The Turn Changed!");
        translateTransitionImageView(imageView1, 2);
        translateTransitionImageView(imageView2, 2);
        translateTransitionImageView(imageView3, 2);
        translateTransitionImageView(imageView4, 2);
        translateTransitionImageView(imageView5, 2);
        translateTransitionImageView(imageView6, 2);
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(label);
        fadeTransition.setCycleCount(1);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        fadeTransition.setDuration(new Duration(5000));
        fadeTransition.play();
    }

    public void starterTheGame(int turn) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choose who start game");
        if (turn == 0) {
            alert.setContentText(LoginAndSignUpController.user.getUsername() + " must choose starter Duel" + "\n" +
                    "Do you want to start the Duel?");
        } else {
            alert.setContentText(StartDuelView.secondPlayerUserName1 + " must choose start Duel" + "\n" +
                    "Do you want to start the Duel?");
        }

        ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(okButton, noButton);
        alert.showAndWait().ifPresent(type -> {
            if (type == okButton) {
                starterTheGame = turnOfGame;
                StartDuelView.startTheGame();
            } else if (type == noButton) {
                starterTheGame = 1 - turnOfGame;
                StartDuelView.startTheGame();
            }
        });

    }

}
