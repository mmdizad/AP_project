package View;

import Controller.ShopController;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopView extends MainMenu {
    private static ShopView shopView = new ShopView();

    private ShopView() {

    }

    public static ShopView getInstance() {
        return shopView;
    }

    public void run(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();

            //trim matcher later!
            Pattern patternBuy = Pattern.compile("shop buy (.+)");
            Matcher matcherBuy = patternBuy.matcher(input);
            if (matcherBuy.find())
                buyCard(matcherBuy);
            else if (input.equals("shop show -all"))
                showCard();
            else if (input.equals("menu exit")) break;
            else if (input.equals("menu show-current")) System.out.println("ShopMenu");
            else System.out.println("invalid command!");
        }
    }

    public void buyCard(Matcher matcher) {
        ShopController shopController = ShopController.getInstance();
        System.out.println(shopController.buyCard(matcher));
    }

    public void showCard() {
        ShopController shopController = ShopController.getInstance();
        ArrayList<String> cards = shopController.getAllCard();
        for (String card : cards) {
            System.out.println(card);
        }
    }

}
