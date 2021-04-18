package Controller;

import Model.User;

public class LoginController {
    public User user;

    public String createUser(String username, String nickname, String password) {
        if (User.isUserWithThisUsernameExists(username)){
            return "user with username " +username+ " already exists";
        }else if (User.isUserWithThisNicknameExists(nickname)){
            return "user with nickname "+nickname+" already exists";
        }else {
            User newUser = new User(username,nickname,password);
            return "user created successfully!";
        }
    }

    public String login(String command) {
        return null;
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