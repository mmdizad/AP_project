package View;

import Controller.LoginAndSignUpController;
import Controller.ProfileController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileView extends MainMenu {
    private static ProfileView profileView = new ProfileView();
    public Label resultChangeName;
    public Button changeNameBTN;
    public TextField nameField;
    public TextField oldPasswordTXT;
    public PasswordField newPassword;
    public PasswordField newPassword2;
    public Label resultChangePassword;


    public ProfileView() {

    }

    public static ProfileView getInstance() {
        return profileView;
    }

    public void run(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            Pattern patternChangeNickName = Pattern.compile("profile change -n (\\S+)");
            Matcher matcherChangeNickName = patternChangeNickName.matcher(input);
            Pattern patternChangePassword = Pattern.compile("profile change -password (\\S+) (\\S+) (\\S+) (\\S+)");
            Matcher matcherChangePassword = patternChangePassword.matcher(input);

            if (matcherChangeNickName.find()) {
                ProfileController profileController = ProfileController.getInstance();
                System.out.println(profileController.changeNickName(matcherChangeNickName));

            } else if (matcherChangePassword.find()) {
                changePassword(matcherChangePassword);

            } else if (input.equals("menu exit")) break;
            else if (input.equals("menu show-current")) System.out.println("ProfileMenu");
            else System.out.println("invalid command!");
            LoginAndSignUpController.saveChangesToFile();
        }
    }

    public void changeNickName(String nickName) {
        //کارایی خاصی نداشت:)
    }

    public void changePassword(Matcher matcher) {
        ProfileController profileController = ProfileController.getInstance();
        if (matcher.group(1).equals("-current") && matcher.group(3).equals("-new")) {
            String currentPassword = matcher.group(2);
            String newPassword = matcher.group(4);
            System.out.println(profileController.changePassword(currentPassword, newPassword));
        } else if (matcher.group(1).equals("-new") && matcher.group(3).equals("-current")) {
            String currentPassword = matcher.group(4);
            String newPassword = matcher.group(2);
            System.out.println(profileController.changePassword(currentPassword, newPassword));
        } else System.out.println("invalid command!");
    }



    public void goChangeName(MouseEvent mouseEvent) {
        URL url = null;
        try {
            url = new File("src/main/java/FXMLFiles/ProfChangeName.fxml").toURI().toURL();
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
        Stage mainMenuStage = MainMenu.stage;
        mainMenuStage.setTitle("MainMenu");
        assert root != null;
        mainMenuStage.setScene(new Scene(root, 1320, 700));
        mainMenuStage.show();

    }

    public void ChangePasswordBTN(MouseEvent mouseEvent) {
        if(oldPasswordTXT.getText().equals(LoginAndSignUpController.user.getPassword())){
           if(newPassword.getText().equals(newPassword2.getText())){
               LoginAndSignUpController.user.setPassword(newPassword.getText());
               newPassword.clear();
               newPassword2.clear();
               oldPasswordTXT.clear();
               resultChangePassword.setText("Password change successfully");
               resultChangePassword.setTextFill(Color.GREEN);
           }

        }else {
            resultChangePassword.setText("Password isn't true or passwords  not similar");
            resultChangePassword.setTextFill(Color.RED);
        }

    }

    public void changeNameBTN(MouseEvent mouseEvent) {
        if(nameField.getText()!=null){
            LoginAndSignUpController.user.setUsername(nameField.getText());
            resultChangeName.setText("UserName Changed!");
            resultChangeName.setTextFill(Color.GREEN);
        }
    }

    public void backToProfMenu(MouseEvent mouseEvent) {
        URL url = null;
        try {
            url = new File("src/main/java/FXMLFiles/profileMenu.fxml").toURI().toURL();
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
        Stage mainMenuStage = MainMenu.stage;
        mainMenuStage.setTitle("MainMenu");
        assert root != null;
        mainMenuStage.setScene(new Scene(root, 1920, 1000));
        mainMenuStage.show();
    }


    public void backtoMainMenuBTN(MouseEvent mouseEvent) {
        URL url = null;
        try {
            url = new File("src/main/java/FXMLFiles/MainMenu.fxml").toURI().toURL();
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
        Stage mainMenuStage = new Stage();
        stage = mainMenuStage;
        mainMenuStage.setTitle("MainMenu");
        assert root != null;
        mainMenuStage.setScene(new Scene(root, 1920, 1000));
        mainMenuStage.show();
    }
    public void showMenu(){
        URL url = null;
        try {
            url = new File("src/main/java/FXMLFiles/profileMenu.fxml").toURI().toURL();
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
        Stage mainMenuStage = MainMenu.stage;
        mainMenuStage.setTitle("MainMenu");
        assert root != null;
        mainMenuStage.setScene(new Scene(root, 1920, 1000));
        mainMenuStage.show();
    }

    public void changePassMenu(MouseEvent mouseEvent) {
        URL url = null;
        try {
            url = new File("src/main/java/FXMLFiles/ProfChangePass.fxml").toURI().toURL();
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
        Stage mainMenuStage = MainMenu.stage;
        mainMenuStage.setTitle("MainMenu");
        assert root != null;
        mainMenuStage.setScene(new Scene(root, 1300, 700));
        mainMenuStage.show();
    }

    public void backToMainMenu(MouseEvent mouseEvent) {
        URL url = null;
        try {
            url = new File("src/main/java/FXMLFiles/profileMenu.fxml").toURI().toURL();
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
        Stage mainMenuStage = MainMenu.stage;
        mainMenuStage.setTitle("MainMenu");
        assert root != null;
        mainMenuStage.setScene(new Scene(root, 1920, 1000));
        mainMenuStage.show();
    }
}
