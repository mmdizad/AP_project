package Controller;

import Model.*;
import com.google.gson.*;

import java.io.*;
import java.net.Socket;

public class LoginController {
    public static User user;
    public static String token = "";

    public static Socket socket;
    public static DataInputStream dataInputStream;
    public static DataOutputStream dataOutputStream;

    public static void initializeNetwork() {
        try {
            socket = new Socket("localhost", 7777);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException x) {
            x.printStackTrace();
        }
    }

    public String createUser(String username, String nickname, String password) {
        try {
            dataOutputStream.writeUTF("create user/" + username + "/" + nickname + "/" + password);
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }

    public String login(String username, String password) {
        try {
            dataOutputStream.writeUTF("login user/" + username + "/" + password);
            dataOutputStream.flush();
            String response = dataInputStream.readUTF();
            if (!response.equals("Username and password didn't match!") && !response.equals("An error occurred.")) {
                user = User.getUserByUsername(username);
                token = response;
            }
            return response;
        } catch (IOException x) {
            x.printStackTrace();
        }
        return "";
    }

    public static void saveChangesToFile() {
        File myObj = new File(System.getProperty("user.home") + "/Desktop\\AP FILES\\Users\\" + user.getUsername() + "user.txt");
        myObj.delete();
        try {
            Gson gson = new Gson();
            String userInfo = gson.toJson(user);
            FileWriter myWriter = new FileWriter(System.getProperty("user.home") + "/Desktop\\AP FILES\\Users\\" + user.getUsername() + "user.txt");
            myWriter.write(userInfo);
            myWriter.close();
        } catch (IOException ignored) {

        }
    }

    public static void saveChangesToFileByUser(User user) {
        if (!user.getUsername().equals("ai")) {
            File myObj = new File(System.getProperty("user.home") + "/Desktop\\AP FILES\\Users\\" + user.getUsername() + "user.txt");
            myObj.delete();
            try {
                Gson gson = new Gson();
                String userInfo = gson.toJson(user);
                FileWriter myWriter = new FileWriter(System.getProperty("user.home") + "/Desktop\\AP FILES\\Users\\" + user.getUsername() + "user.txt");
                myWriter.write(userInfo);
                myWriter.close();
            } catch (IOException ignored) {

            }
        }
    }
}