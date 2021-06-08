package View;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu {
    private boolean invalidCommand;

    public void run(Scanner scanner) throws IOException {
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

    public void enterMenu(Matcher matcher, Scanner scanner) throws IOException {
        if (matcher.find()) {
            invalidCommand = false;
            String menuName = matcher.group(1);
            if (menuName.equals("Login")) {
                LoginAndSignUpView loginAndSignUpView = new LoginAndSignUpView();
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