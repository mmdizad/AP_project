package View;

import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class WelcomeView extends VBox {
    private CheckBox loginCheckBox;
    private CheckBox signupCheckBox;

    public WelcomeView() throws FileNotFoundException {
        setLoginCheckBox(loginCheckBox = new CheckBox());
        loginCheckBox.setTranslateX(800);
        loginCheckBox.setTranslateY(295);

        setSignupCheckBox(signupCheckBox = new CheckBox());
        signupCheckBox.setTranslateX(800);
        signupCheckBox.setTranslateY(404);

        this.getChildren().addAll(loginCheckBox, signupCheckBox);
        FileInputStream input = new FileInputStream("C:\\Users\\yugioh-welcome.JPG");
        Image image = new Image(input);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        this.setBackground(background);
    }

    public void setLoginCheckBox(CheckBox loginCheckBox) {
        this.loginCheckBox = loginCheckBox;
    }

    public void setSignupCheckBox(CheckBox signupCheckBox) {
        this.signupCheckBox = signupCheckBox;
    }

    public CheckBox getLoginCheckBox() {
        return loginCheckBox;
    }

    public CheckBox getSignupCheckBox() {
        return signupCheckBox;
    }

}

