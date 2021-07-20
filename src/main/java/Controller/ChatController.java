package Controller;

import java.io.IOException;

public class ChatController extends LoginController{

    private static ChatController chatController;

    public static ChatController getInstance() {
        if (chatController == null) {
            chatController = new ChatController();
        }
        return chatController;
    }

    private ChatController () {

    }


    public String sendMessages(String message) {
        try {
            dataOutputStream.writeUTF("send message/" + message + "/" + token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String loadMessages() {
        try {
            dataOutputStream.writeUTF("load message/" + token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String deleteMessages(String message) {
        try {
            dataOutputStream.writeUTF("delete message/" + message + "/" + token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String pinMessages(String message) {
        try {
            dataOutputStream.writeUTF("pin message/" + message + "/" + token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String editMessages(String message, String newMessage) {
        try {
            dataOutputStream.writeUTF("edit message/" + message + "/" + newMessage + "/" + token);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
