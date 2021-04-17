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

    }

    public ArrayList<String> checkCard(Matcher matcher){

    }

    private ArrayList<String> showMonster(String cardName){

    }

    private ArrayList<String> showSpell(String cardName){

    }

    private ArrayList<String> showTrap(String cardName){

    }

}