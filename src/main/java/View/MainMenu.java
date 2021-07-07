package View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu {
    public static Stage stage;
    public void showMenu(){
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

    private boolean invalidCommand;

    public void run(Scanner scanner) {
        String input;

        while (true) {
            invalidCommand = true;
            input = scanner.nextLine();
            showMenu(getCommandMatcher(input, "^menu show-current$"));
            enterMenu(getCommandMatcher(input, "^menu enter (\\S+)$"), scanner);


            if (input.equals("user logout") || input.equals("menu exit")) {
                break;
            }
            if (invalidCommand) {
                System.out.println("invalid command");
            }
        }
    }

    protected void showMenu(Matcher matcher) {
        if (matcher.find()) {
            invalidCommand = false;
            System.out.println("Main Menu");
        }
    }

    public void enterMenu(Matcher matcher, Scanner scanner) {
        if (matcher.find()) {
            invalidCommand = false;
            String menuName = matcher.group(1);
            if (menuName.equals("Login")) {
//                LoginView loginView = new LoginView();
//                loginView.run();
            } else if (menuName.equals("Duel")) {
                StartDuelView startDuelView = new StartDuelView();
                startDuelView.run(scanner);
            } else if (menuName.equals("Deck")) {
                DeckView deckView = DeckView.getInstance();
                deckView.run(scanner);
            } else if (menuName.equals("Scoreboard")) {
                ScoreBoardView scoreBoardView = ScoreBoardView.getInstance();
                scoreBoardView.run(scanner);
            } else if (menuName.equals("Profile")) {
                ProfileView profileView = ProfileView.getInstance();
                profileView.run(scanner);
            } else if (menuName.equals("Shop")) {
                ShopView shopView = ShopView.getInstance();
                shopView.run(scanner);
            } else if (menuName.equals("ImportAndExport")) {
                ImportAndExport importAndExport = ImportAndExport.getInstance();
                importAndExport.run(scanner);
            } else {
                System.out.println("invalid command");
            }
        }
    }

    protected Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

}