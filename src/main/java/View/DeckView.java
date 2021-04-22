package View;

import Controller.*;

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
        String command = scanner.nextLine();
        if (getCommandMatcher(command,"^deck create (\\S+)$").find()){
            deckCreate(getCommandMatcher(command,"^deck create (\\S+)$"));
        }else if (getCommandMatcher(command,"^deck delete (\\S+)$").find()){
            deckDelete(getCommandMatcher(command,"^deck delete (\\S+)$"));
        }else if (getCommandMatcher(command,"^deck set-activate (\\S+)$").find()){
            deckSetActive(getCommandMatcher(command,"^deck set-activate (\\S+)$"));
        }else if (getCommandMatcher(command,"^deck add-card --card (\\S+) --deck (\\S+)$").find()){
            addCard(getCommandMatcher(command,"^deck add-card --card (\\S+) --deck (\\S+)$"));
        }else if (getCommandMatcher(command,"^deck add-card --card (\\S+) --deck (\\S+) --(side)$").find()){
            addCard(getCommandMatcher(command,"^deck add-card --card (\\S+) --deck (\\S+) --(side)$"));
        }else if (getCommandMatcher(command,"^deck rm-card --card (\\S+) --deck (\\S+)$").find()){
            deleteCard(getCommandMatcher(command,"^deck rm-card --card (\\S+) --deck (\\S+)$"));
        }else if (getCommandMatcher(command,"^deck rm-card --card (\\S+) --deck (\\S+) --(side)$").find()){
            deleteCard(getCommandMatcher(command,"^deck rm-card --card (\\S+) --deck (\\S+) --(side)$"));
        }else if (getCommandMatcher(command,"^deck show --all$").find()){
            showAllDeck();
        }else if (getCommandMatcher(command,"^deck show --deck-name (\\S+)$").find()){
            deckShow(getCommandMatcher(command,"^deck show --deck-name (\\S+)$"));
        }else if (getCommandMatcher(command,"^deck rm-card --card (\\S+) --deck (\\S+) --(side)$").find()){
            deckShow(getCommandMatcher(command,"^deck rm-card --card (\\S+) --deck (\\S+) --(side)$"));
        }else if (getCommandMatcher(command,"^deck show --cards$").find()){
            showCard(getCommandMatcher(command,"^deck show --cards$"));
        }
    }
    public void deckCreate(Matcher matcher){
        DeckController deckController = DeckController.getInstance();
        System.out.println(deckController.deckCreate(matcher));
    }
    public void deckDelete(Matcher matcher){
        DeckController deckController = DeckController.getInstance();
        System.out.println(deckController.deckDelete(matcher));
    }
    public void deckSetActive(Matcher matcher){
        DeckController deckController = DeckController.getInstance();
        System.out.println(deckController.deckSetActivate(matcher));
    }
    public void addCard(Matcher matcher){
        DeckController deckController = DeckController.getInstance();
        System.out.println(deckController.addCard(matcher));
    }

    public void deleteCard(Matcher matcher){
        DeckController deckController = DeckController.getInstance();
        System.out.println(deckController.deleteCard(matcher));
    }

    public void deckShow(Matcher matcher){
        DeckController deckController = DeckController.getInstance();
        ArrayList<String> output = deckController.deckShow(matcher);
        for (int i = 0;i < output.size();i++){
            System.out.println(output.get(i));
        }
    }

    public void showCard(Matcher matcher){
        DeckController deckController = DeckController.getInstance();
        ArrayList<String> output = deckController.showAllOwnedCards();
        for (int i = 0;i < output.size();i++){
            System.out.println(output.get(i));
        }
    }

    public void showAllDeck(){
        DeckController deckController = DeckController.getInstance();
        ArrayList<String> output = deckController.showAllDeck();
        for (int i = 0;i < output.size();i++){
            System.out.println(output.get(i));
        }

    }
}