package Controller;

import java.util.regex.Matcher;

public class ProfileController extends LoginController {

    private static ProfileController profileController = new ProfileController();

    private ProfileController(){

    }

    public static ProfileController getInstance(){
        return profileController;
    }

    public String changeNickName(Matcher matcher){
        return null;
    }

    public String changePassword(Matcher matcher){
        return null;
    }

}