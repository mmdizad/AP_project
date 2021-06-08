package Controller;

import Model.Card;


import java.util.regex.Matcher;

public class ImportAndExportAndSignUpController extends LoginAndSignUpController {

    public static ImportAndExportAndSignUpController importAndExportController = new ImportAndExportAndSignUpController();

    private ImportAndExportAndSignUpController(){

    }

    public static ImportAndExportAndSignUpController getInstance(){
        return importAndExportController;
    }

    public String importController(Matcher matcher){
        String cardName = matcher.group(1);
        if (Card.getCardByName(cardName) == null){
            return "card with name " + cardName + " doesn't exist";
        }else {
//            Gson gson = new Gson();
//            Card card = Card.getCardByName(cardName);
//            String cardInfo = gson.toJson(card);
            return "card imported";
        }
    }

    public String exportController(Matcher matcher){
        String input = ""; //????
//        Gson gson = new Gson();
//        Card card = gson.fromJson(input, Card.class);
//        Card card1 = new Card(card.getName(),card.getDescription(),card.getCardType(),card.getPrice(), card.getCategory());
        return "card exported";
    }

}
