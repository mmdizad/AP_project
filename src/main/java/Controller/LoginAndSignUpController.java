package Controller;

import Model.User;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class LoginAndSignUpController {
    private static LoginAndSignUpController loginAndSignUpController;
    private static HashMap<String, User> loggedInUsers;

    private LoginAndSignUpController() {

    }

    public static LoginAndSignUpController getInstance() {
        if (loginAndSignUpController == null)
            loginAndSignUpController = new LoginAndSignUpController();

        return loginAndSignUpController;
    }

    public String createUser(String input) {
        String[] partsOfInput = input.split("/");
        String username = partsOfInput[1];
        String nickname = partsOfInput[2];
        String password = partsOfInput[3];

        try {
            File openingUser = new File(System.getProperty("user.home") + "/Desktop\\AP FILES\\Users\\" + username + "user.txt");
            if (!User.isUserWithThisNicknameExists(nickname) && openingUser.createNewFile()) {
                Gson gson = new Gson();
                User user = new User(username, nickname, password);
                String userInfo = gson.toJson(user);
                FileWriter myWriter = new FileWriter(System.getProperty("user.home") + "/Desktop\\AP FILES\\Users\\" + username + "user.txt");
                myWriter.write(userInfo);
                myWriter.close();
                return "user created successfully!";
            } else {
                if (!User.isUserWithThisNicknameExists(nickname)) {
                    return "user with username " + username + " already exists";
                }
                return "user with nickname " + nickname + " already exists";
            }
        } catch (
                IOException e) {
            return "An error occurred.";
        }
    }

    public String login(String input) {
        String[] partsOfInput = input.split("/");
        String username = partsOfInput[1];
        String password = partsOfInput[2];
        try {
            File openingUser = new File(System.getProperty("user.home") + "/Desktop\\AP FILES\\Users\\" + username + "user.txt");
            if (!openingUser.exists()) {
                return "Username and password didn't match!";
            } else {
                Gson gson = new Gson();
                StringBuilder getDetail = new StringBuilder();
                Scanner myReader = new Scanner(openingUser);
                while (myReader.hasNextLine()) {
                    getDetail.append(myReader.nextLine());
                }
                String userInfo = getDetail.toString();
                User user1 = gson.fromJson(userInfo, User.class);
                myReader.close();
                if (!user1.getPassword().equals(password)) {
                    return "Username and password didn't match!";
                } else {
                    String token = UUID.randomUUID().toString();
                    User user = User.getUserByUsername(username);
                    loggedInUsers.put(token, user);
                    return token;
                }
            }
        } catch (IOException e) {
            return "An error occurred.";
        }
    }
}
