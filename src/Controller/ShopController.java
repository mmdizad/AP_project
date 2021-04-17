package Controller;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class ShopController extends LoginController {

    private static ShopController shopController = new ShopController();

    private ShopController() {

    }

    public static ShopController getInstance() {
        return shopController;
    }

    public String buyCard(Matcher matcher){
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

}