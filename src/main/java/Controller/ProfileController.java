package Controller;

import Model.User;

import java.util.regex.Matcher;

public class ProfileController  {
    private static final ProfileController profileController = new ProfileController();

    public static ProfileController getInstance() {
        return profileController;
    }

    public String changeNickName(String command) {
         String nickName = command.split("/")[1];
        if (User.isUserWithThisNicknameExists(nickName)) return "user with nickname " + nickName + "already exists";
        else {
            LoginAndSignUpController.loggedInUsers.get(command.split("/")[2]).setNickname(nickName);
            return "nickname changed successfully!";
        }

    }

    public String changePassword(String command) {
             String currentPassword=command.split("/")[1];
        String newPassword=command.split("/")[2];
        User user = LoginAndSignUpController.loggedInUsers.get(command.split("/")[3]);
        if (!user.getPassword().equals(currentPassword)) return "current password is invalid";
        else if (currentPassword.equals(newPassword)) return "please enter a new password";
        else {
            user.setPassword(newPassword);
            return "password changed successfully!";
        }
    }
}