package View;

import Controller.LoginAndSignUpController;
import Controller.ShopAndSignUpController;

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

            Pattern pattern = Pattern.compile("^increase --money (\\d+)$");
            Matcher matcher = pattern.matcher(input);
            if (matcher.find()){
                increaseMoney(matcher);
            }
            //trim matcher later!
            Pattern patternBuy = Pattern.compile("shop buy (.+)");
            Matcher matcherBuy = patternBuy.matcher(input);
            if (matcherBuy.find())
                buyCard(matcherBuy);
            else if (input.equals("shop show --all"))
                showCard();
            else if (input.equals("menu exit")) break;
            else if (input.equals("menu show-current")) System.out.println("ShopMenu");
            else System.out.println("invalid command!");
            LoginAndSignUpController.saveChangesToFile();
        }
    }

    public void increaseMoney(Matcher matcher) {
        ShopAndSignUpController shopController = ShopAndSignUpController.getInstance();
        System.out.println(shopController.increaseMoney(matcher));
    }

    public void buyCard(Matcher matcher) {
        ShopAndSignUpController shopController = ShopAndSignUpController.getInstance();
        System.out.println(shopController.buyCard(matcher));
    }

    public void showCard() {
        ShopAndSignUpController shopController = ShopAndSignUpController.getInstance();
        ArrayList<String> cards = shopController.getAllCard();
        for (String card : cards) {
            System.out.println(card);
        }
    }

}
