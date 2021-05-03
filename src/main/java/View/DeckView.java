package View;

import Controller.*;
import Model.Deck;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class DeckView extends MainMenu {
    private static DeckView deckView = new DeckView();

    private DeckView() {

    }

    public static DeckView getInstance() {
        return deckView;
    }

    @Override
    public void run(Scanner scanner) {
        while (true) {
            boolean isCommandValid = false;
            String command = scanner.nextLine();
            Matcher matcher = getCommandMatcher(command, "^deck create (\\S+)$");
            if (matcher.find()) {
                deckCreate(matcher);
                isCommandValid = true;
            }
            matcher = getCommandMatcher(command, "^deck delete (\\S+)$");
            if (matcher.find()) {
                deckDelete(matcher);
                isCommandValid = true;
            }
            matcher = getCommandMatcher(command, "^deck set-activate (\\S+)$");
            if (matcher.find()) {
                deckSetActive(matcher);
                isCommandValid = true;
            }
            matcher = getCommandMatcher(command, "^deck add-card --card ([^-]+) --deck (\\S+)$");
            if (matcher.find()) {
                addCard(matcher);
                isCommandValid = true;
            }
            matcher = getCommandMatcher(command, "^deck add-card --card ([^-]+) --deck (\\S+) --(side)$");
            if (matcher.find()) {
                addCard(matcher);
                isCommandValid = true;
            }
            matcher = getCommandMatcher(command, "^deck rm-card --card ([^-]+) --deck (\\S+)$");
            if (matcher.find()) {
                deleteCard(matcher);
                isCommandValid = true;
            }
            matcher = getCommandMatcher(command, "^deck rm-card --card ([^-]+) --deck (\\S+) --(side)$");
            if (matcher.find()) {
                deleteCard(matcher);
                isCommandValid = true;
            }
            matcher = getCommandMatcher(command, "^deck show --all$");
            if (matcher.find()) {
                showAllDeck();
                isCommandValid = true;
            }
            matcher = getCommandMatcher(command, "^deck show --deck-name (\\S+)$");
            if (matcher.find()) {
                deckShow(matcher);
                isCommandValid = true;
            }
            matcher = getCommandMatcher(command, "^deck show --deck-name (\\S+) --(side)$");
            if (matcher.find()) {
                deckShow(matcher);
                isCommandValid = true;
            }
            matcher = getCommandMatcher(command, "^deck show --cards$");
            if (matcher.find()) {
                showCard(matcher);
                isCommandValid = true;
            }
            matcher = getCommandMatcher(command, "^card show (.+)$");
            if (matcher.find()) {
                showOneCard(matcher);
                isCommandValid = true;
            }
            if (command.equals("menu show-current")) {
                System.out.println("Deck menu");
                isCommandValid = true;
            }
            if (command.equals("menu exit")) {
                return;
            }
            matcher = getCommandMatcher(command, "^menu enter (\\S+)$");
            if (matcher.find()) {
                System.out.println("menu navigation is not possible");
            }
            if (!isCommandValid) {
                System.out.println("Invalid command");
            }
        }
    }

    public void deckCreate(Matcher matcher) {
        DeckController deckController = DeckController.getInstance();
        System.out.println(deckController.deckCreate(matcher));
    }

    public void deckDelete(Matcher matcher) {
        DeckController deckController = DeckController.getInstance();
        System.out.println(deckController.deckDelete(matcher));
    }

    public void deckSetActive(Matcher matcher) {
        DeckController deckController = DeckController.getInstance();
        System.out.println(deckController.deckSetActivate(matcher));
    }

    public void addCard(Matcher matcher) {
        DeckController deckController = DeckController.getInstance();
        System.out.println(deckController.addCard(matcher));
    }

    public void deleteCard(Matcher matcher) {
        DeckController deckController = DeckController.getInstance();
        System.out.println(deckController.deleteCard(matcher));
    }

    public void deckShow(Matcher matcher) {
        DeckController deckController = DeckController.getInstance();
        ArrayList<String> output = deckController.deckShow(matcher);
        for (int i = 0; i < output.size(); i++) {
            System.out.println(output.get(i));
        }
    }

    public void showCard(Matcher matcher) {
        DeckController deckController = DeckController.getInstance();
        ArrayList<String> output = deckController.showAllOwnedCards();
        for (int i = 0; i < output.size(); i++) {
            System.out.println(output.get(i));
        }
    }

    public void showOneCard(Matcher matcher) {
        DeckController deckController = DeckController.getInstance();
        ArrayList<String> output = deckController.checkCard(matcher);
        for (int i = 0; i < output.size(); i++) {
            System.out.println(output.get(i));
        }
    }

    public void showAllDeck() {
        DeckController deckController = DeckController.getInstance();
        ArrayList<String> output = deckController.showAllDeck();
        for (int i = 0; i < output.size(); i++) {
            System.out.println(output.get(i));
        }

    }
}