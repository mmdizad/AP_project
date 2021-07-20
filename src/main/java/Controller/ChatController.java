package Controller;

import Model.User;

import java.util.ArrayList;
import java.util.Locale;

public class ChatController {

    private static ChatController chatController;
    private static ArrayList<String> messages = new ArrayList<>();
    private static String pinnedMessage = "";

    public static ChatController getInstance() {
        if (chatController == null) {
            chatController = new ChatController();
        }
        return chatController;
    }

    public ChatController () {

    }

    public String sendMessage(String command) {
        if (!LoginAndSignUpController.loggedInUsers.containsKey(command.split("/")[2])) {
            return "wrong token!";
        }
        String message = command.split("/")[1];
        User user = LoginAndSignUpController.loggedInUsers.get(command.split("/")[2]);
        messages.add(message + "/" + user.getUsername());
        return "message sent!";
    }

    public String loadMessages(String command) {
        if (!LoginAndSignUpController.loggedInUsers.containsKey(command.split("/")[1])) {
            return "wrong token!";
        }
        String output = "pinned message: \n";
        output += pinnedMessage + "\n";
        output += "messages: \n";
        for (String message : messages) {
            output += message.split("/")[1] + ": " + message.split("/")[0] + "\n";
        }
        return output;
    }

    public String deleteMessage(String command) {
        if (!LoginAndSignUpController.loggedInUsers.containsKey(command.split("/")[2])) {
            return "wrong token!";
        }
        String message = command.split("/")[1];
        User user = LoginAndSignUpController.loggedInUsers.get(command.split("/")[2]);
        if (messages.contains(message + "/" + user.getUsername())) {
            messages.remove(message + "/" + user.getUsername());
            return "message deleted successfully";
        }else {
            return "you dont have any message with this text";
        }
    }

    public String pinMessage(String command) {
        if (!LoginAndSignUpController.loggedInUsers.containsKey(command.split("/")[2])) {
            return "wrong token!";
        }
        String message = command.split("/")[1];
        User user = LoginAndSignUpController.loggedInUsers.get(command.split("/")[2]);
        if (messages.contains(message + "/" + user.getUsername())) {
            pinnedMessage = user.getUsername() + ": " + message;
            return "message pinned successfully";
        }else {
            return "you dont have any message with this text";
        }
    }

    public String editMessage(String command) {
        if (!LoginAndSignUpController.loggedInUsers.containsKey(command.split("/")[3])) {
            return "wrong token!";
        }
        String message = command.split("/")[1];
        String newMessage = command.split("/")[2];
        User user = LoginAndSignUpController.loggedInUsers.get(command.split("/")[3]);
        if (messages.contains(message + "/" + user.getUsername())) {
            messages.remove(message + "/" + user.getUsername());
            messages.add(newMessage + "/" + user.getUsername());
            return "message edited successfully";
        }else {
            return "you dont have any message with this text";
        }
    }
}
