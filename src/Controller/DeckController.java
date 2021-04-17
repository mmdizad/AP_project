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

    }

    public String deckDelete(Matcher matcher){

    }

    public String deckSetActivate(Matcher matcher){

    }

    public String addCard(Matcher matcher){

    }

    public String addCardToMain(Matcher matcher){

    }

    public String addCardToSide(Matcher matcher){

    }

    public String deleteCard(Matcher matcher){

    }

    public String deleteCardFromMain(Matcher matcher){

    }

    public String deleteCardFromSide(Matcher matcher){

    }

    public ArrayList<String> deckShow(Matcher matcher){

    }

    public ArrayList<String> checkCard(Matcher matcher){

    }

    private ArrayList<String> showMonster(String cardName){

    }

    private ArrayList<String> showSpell(String cardName){

    }

    private ArrayList<String> showTrap(String cardName){

    }

    public ArrayList<String> showAllDeck(){

    }

    public ArrayList<String> showAllOwnedCards(){

    }

}