package Controller;

import Model.Spell;
import Model.Trap;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CreateSpellAndTrapController implements Initializable {

    public ImageView cardImage;
    public ChoiceBox<String> icons;
    public CheckBox killMonsterEffect;
    public CheckBox killSpellEffect;
    public CheckBox increaseLPEffect;
    public CheckBox decreaseLPEffect;
    public Button calculate;
    public ToggleButton trapButton;
    public ToggleButton unlimitedButton;
    public CheckBox[] effects;
    public Label priceLBL;
    public Button backBTN;
    public Button createBTN;
    public TextField nameTextField;
    public Label errorLBL;
    public TextArea cardDescription;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setIcons();

        cardImage.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                handleDropImage(event);
            }
        });

        cardImage.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                handleDragOverImage(event);
            }
        });

        calculate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                calculate();
            }
        });

        backBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) backBTN.getScene().getWindow();
                URL url = null;
                try {
                    url = new File("src/main/java/FXMLFiles/CreateCard.fxml").toURI().toURL();
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
                stage.setTitle("CreateCard");
                assert root != null;
                stage.setScene(new Scene(root, 1920, 1150));
                stage.show();
            }
        });

        createBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    createCard();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void calculate() {
        effects = new CheckBox[]{killMonsterEffect, killSpellEffect, increaseLPEffect, decreaseLPEffect};
        calculatePriceOfSpellAndTrapCard(effects);
    }

    public String calculatePriceOfSpellAndTrapCard(CheckBox[] effects) {
        int price = 0;
        if (trapButton.isSelected()) {
            price += 800;
        }
        if (unlimitedButton.isSelected()) {
            price += 500;
        }
        for (CheckBox effect : effects) {
            if (effect.isSelected())
                price += 1000;
        }

        priceLBL.setText(String.valueOf(price));
        return String.valueOf(price);
    }

    public void createCard() throws IOException {
        calculate();
        effects = new CheckBox[]{killMonsterEffect, killSpellEffect, increaseLPEffect, decreaseLPEffect};
        String error = errorCheck(effects);

        if (!error.equals("")) {
            errorLBL.setText(error);
            errorLBL.setTextFill(Color.RED);
        } else {
            String status = "Limited";
            calculate();
            File outputFile = new File("src/main/resource/SpellTrap/" + nameTextField.getText() + ".JPG");
            BufferedImage bImage = SwingFXUtils.fromFXImage(cardImage.getImage(), null);
            ImageIO.write(bImage, "JPG", outputFile);

            if (unlimitedButton.isSelected())
                status = "Unlimited";

            if (trapButton.isSelected()) {
                new Trap(nameTextField.getText(), cardDescription.getText(), icons.getValue(),
                        Integer.parseInt(calculatePriceOfSpellAndTrapCard(effects)), "Trap", status);
            } else {
                new Spell(nameTextField.getText(), cardDescription.getText(), icons.getValue(),
                        Integer.parseInt(calculatePriceOfSpellAndTrapCard(effects)), "Spell", status);
            }

            LoginAndSignUpController.user.setCoins(LoginAndSignUpController.user.getCoins()
                    - Integer.parseInt(calculatePriceOfSpellAndTrapCard(effects)));

            errorLBL.setText("card created successfully!");
        }
    }

    public String errorCheck(CheckBox[] effects) {
        if (nameTextField.getText().equals(""))
            return "You should choose a name for your card";
        if (cardImage.getImage() == null)
            return "You should choose an image for your card";
        if (Integer.parseInt(calculatePriceOfSpellAndTrapCard(effects)) > LoginAndSignUpController.user.getCoins())
            return "You don't have enough money for create card!";
        return "";
    }

    public void handleDropImage(DragEvent dragEvent) {
        List<File> files = dragEvent.getDragboard().getFiles();
        try {
            Image image = new Image(new FileInputStream(files.get(files.size() - 1)));
            cardImage.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void handleDragOverImage(DragEvent dragEvent) {
        if (dragEvent.getDragboard().hasFiles())
            dragEvent.acceptTransferModes(TransferMode.ANY);
    }

    public void setIcons() {
        String[] icons1 = {"Equip", "Quick-Play", "Ritual", "Continuous", "Counter", "Normal"};
        icons.getItems().addAll(icons1);
        icons.setValue("Equip");
    }

}
