package Controller;

import java.io.IOException;
import java.util.regex.Matcher;

public class ProfileController extends LoginController {
    private static final ProfileController profileController = new ProfileController();

    public static ProfileController getInstance() {
        return profileController;
    }

    public String changeNickName(Matcher matcher) {
        String nickName = matcher.group(1);
        try {
            dataOutputStream.writeUTF("change nickname/"+nickName+"/"+token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "nnn";
    }

    public String changePassword(String currentPassword, String newPassword) {
        try {
            dataOutputStream.writeUTF("change Password/"+currentPassword+"/"+newPassword+"/"+token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "bug in change pass";
    }
}