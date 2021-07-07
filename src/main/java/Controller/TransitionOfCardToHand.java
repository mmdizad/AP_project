package Controller;

import Model.Card;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class TransitionOfCardToHand {
    private static final TransitionOfCardToHand transitionOfCardToHand = new TransitionOfCardToHand();

    public static TransitionOfCardToHand getInstance() {
        return transitionOfCardToHand;
    }

    private TransitionOfCardToHand() {

    }

    public void transition(ArrayList<Card> cards) {
        for (Card card : cards) {
            ImageView imageView;
            if (card.getCategory().equals("Monster")) {
                URL url = null;
                try {
                    url = new File("src/main/resource/Monsters/" + card.getName() + ".jpg").toURI().toURL();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                imageView = new ImageView(new Image(Objects.requireNonNull(url).toString()));
                imageView.setFitWidth(100);
                imageView.setFitHeight(120);

            } else {
                URL url = null;
                try {
                    url = new File("src/main/resource/SpellTrap/" + card.getName() + ".jpg").toURI().toURL();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                imageView = new ImageView();
                 imageView.setImage(new Image(Objects.requireNonNull(url).toString()));
                imageView.setFitWidth(100);
                imageView.setFitHeight(120);
            }
            imageView.setTranslateX(-600);
            imageView.setTranslateY(-15);
            //DuelView.hBoxS.getChildren().add(imageView);
        }
    }
}
