package View;

import Controller.ProfileController;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileView extends MainMenu {
    private static ProfileView profileView = new ProfileView();
    Scanner scanner = new Scanner(System.in);

    private ProfileView() {

    }

    public static ProfileView getInstance() {
        return profileView;
    }

    public void run() {
        String input = scanner.nextLine();
        Pattern patternchangeNickName = Pattern.compile("profile change -n (\\S+)");
        Matcher matcherchangeNickName = patternchangeNickName.matcher(input);
        Pattern patternchangePassword = Pattern.compile("profile change -password (\\S+) (\\S+) (\\S+) (\\S+)");
        Matcher matcherchangePassword = patternchangePassword.matcher(input);

        if (matcherchangeNickName.find()) {
            ProfileController profileController = new ProfileController();
            System.out.println(profileController.changeNickName(matcherchangeNickName));

        } else if (matcherchangePassword.find()) {
            changePassword(matcherchangePassword);

        } else System.out.println("invalid command!");

    }

    public void changeNickName(String nickName) {
        //کارایی خاصی نداشت:)


    }

    public void changePassword(Matcher matcher) {
        ProfileController profileController = new ProfileController();
        if (matcher.group(1).equals("-current") && matcher.group(3).equals("-new")) {
            String currentPassword = matcher.group(2);
            String newPassword = matcher.group(4);
            System.out.println(profileController.changePassword(currentPassword, newPassword));
        } else if (matcher.group(1).equals("-new") && matcher.group(3).equals("-current")) {
            String currentPassword = matcher.group(4);
            String newPassword = matcher.group(2);
            System.out.println(profileController.changePassword(currentPassword, newPassword));
        } else System.out.println("invalid command!");
    }

}
