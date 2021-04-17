package Controller;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class DeckController extends LoginController {

    private static DeckController deckController = new DeckController();

    private  DeckController() {

    }

    public static DeckController getInstance(){
        return deckController;
    }

    public String deckCreate(Matcher matcher){
        return null;
    }

    public String deckDelete(Matcher matcher){
        return null;
    }

    public String deckSetActivate(Matcher matcher){
        return null;
    }

    public String addCard(Matcher matcher){
        return null;
    }

    public String addCardToMain(Matcher matcher){
        return null;
    }

    public String addCardToSide(Matcher matcher){
        return null;
    }

    public String deleteCard(Matcher matcher){
        return null;
    }

    public String deleteCardFromMain(Matcher matcher){
        return null;
    }

    public String deleteCardFromSide(Matcher matcher){
        return null;
    }

    public ArrayList<String> deckShow(Matcher matcher){
        return null;
    }

    public ArrayList<String> checkCard(Matcher matcher){
        return null;
    }

    private ArrayList<String> showMonster(String cardName){
        return null;
    }

    private ArrayList<String> showSpell(String cardName){
        return null;
    }

    private ArrayList<String> showTrap(String cardName){
        return null;
    }

    public ArrayList<String> showAllDeck(){
        return null;
    }

    public ArrayList<String> showAllOwnedCards(){
        return null;
    }

}