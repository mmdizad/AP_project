package Controller;

import Model.User;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;

public class ProfileController extends LoginController {

    private static ProfileController profileController = new ProfileController();

    public static ProfileController getInstance() {
        return profileController;
    }

    public String changeNickName(Matcher matcher) {
        String nickName = matcher.group(1);
        if (User.isUserWithThisNicknameExists(nickName)) return "user with nickname " + nickName + "already exists";
        else {
            ProfileController.user.setNickname(nickName);
            return "nickname changed successfully!";
        }

    }

    public String changePassword(String currentPassword, String newPassword) {

        if (!user.getPassword().equals(currentPassword)) return "current password is invalid";
        else if (currentPassword.equals(newPassword)) return "please enter a new password";
        else {
            user.setPassword(newPassword);
            return "password changed successfully!";
        }
    }
}