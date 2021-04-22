package View;

import Controller.ProfileController;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileView extends MainMenu {
    private static ProfileView profileView = new ProfileView();

    private ProfileView() {

    }

    public static ProfileView getInstance() {
        return profileView;
    }

    public void run(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            Pattern patternChangeNickName = Pattern.compile("profile change -n (\\S+)");
            Matcher matcherChangeNickName = patternChangeNickName.matcher(input);
            Pattern patternChangePassword = Pattern.compile("profile change -password (\\S+) (\\S+) (\\S+) (\\S+)");
            Matcher matcherChangePassword = patternChangePassword.matcher(input);

            if (matcherChangeNickName.find()) {
                ProfileController profileController = ProfileController.getInstance();
                System.out.println(profileController.changeNickName(matcherChangeNickName));

            } else if (matcherChangePassword.find()) {
                changePassword(matcherChangePassword);

            } else if (input.equals("menu exit")) break;
            else if(input.equals("menu show-current")) System.out.println("ProfileMenu");
            else System.out.println("invalid command!");
        }
    }

    public void changeNickName(String nickName) {
        //کارایی خاصی نداشت:)
    }

    public void changePassword(Matcher matcher) {
        ProfileController profileController =ProfileController.getInstance();
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
