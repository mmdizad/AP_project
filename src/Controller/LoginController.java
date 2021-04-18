package Controller;

import Model.User;

public class LoginController {
    public User user;

    public String createUser(String username, String nickname, String password) {
        if (User.isUserWithThisUsernameExists(username)) {
            return "user with username " + username + " already exists";
        } else if (User.isUserWithThisNicknameExists(nickname)) {
            return "user with nickname " + nickname + " already exists";
        } else {
            User newUser = new User(username, nickname, password);
            return "user created successfully!";
        }
    }

    public String login(String username, String password) {
        if (!User.isUserWithThisUsernameExists(username)) {
            return "Username and password didn't match!";
        } else if (!User.getUserByUsername(username).getPassword().equals(password)) {
            return "Username and password didn't match!";
        } else {
            user = User.getUserByUsername(username);
            return "user logged in successfully!";
        }
    }

    public void createCard() {

    }

    public void createMonster() {

    }

    public void createSpell() {

    }

    public void createTrap() {

    }

}