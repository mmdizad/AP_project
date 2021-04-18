package View;

import java.util.regex.Matcher;

public class ProfileView extends MainMenu {
    private static ProfileView profileView = new ProfileView();

    private ProfileView(){

    }
    public static ProfileView getInstance(){
        return profileView;
    }

    public void run(){

    }
    public void changeNickName(){}
    public void changePassword(Matcher matcher){}

}
