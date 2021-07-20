package View;

import Controller.ChatController;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ChatView extends LoginView {

    private static ChatView chatView;

    public static ChatView getInstance() {
        if (chatView == null) {
            chatView = new ChatView();
        }
        return chatView;
    }

    private ChatView () {

    }

    public void run (Scanner scanner) {
        while (true) {
            boolean isCommandValid = false;
            String command = scanner.nextLine();
            Matcher matcher = getCommandMatcher(command, "^send message (.+)$");
            if (matcher.find()) {
                sendMessage(matcher.group(1));
                isCommandValid = true;
            }
            matcher = getCommandMatcher(command, "^load messages$");
            if (matcher.find()) {
                loadMessages();
                isCommandValid = true;
            }
            matcher = getCommandMatcher(command, "^pin message (.+)$");
            if (matcher.find()) {
                pinMessage(matcher.group(1));
                isCommandValid = true;
            }
            matcher = getCommandMatcher(command, "^delete message (.+)$");
            if (matcher.find()) {
                deleteMessage(matcher.group(1));
                isCommandValid = true;
            }
            matcher = getCommandMatcher(command, "^edit message (.+)$");
            if (matcher.find()) {
                editMessage(matcher.group(1), scanner);
                isCommandValid = true;
            }
            if (command.equals("menu show-current")) {
                System.out.println("Chat menu");
                isCommandValid = true;
            }
            if (command.equals("menu exit")) {
                return;
            }
            matcher = getCommandMatcher(command, "^menu enter (\\S+)$");
            if (matcher.find()) {
                System.out.println("menu navigation is not possible");
            }
            if (!isCommandValid) {
                System.out.println("Invalid command");
            }
        }
    }

    public void sendMessage(String message) {
        System.out.println(ChatController.getInstance().sendMessages(message));
    }

    public void loadMessages() {
        System.out.println(ChatController.getInstance().loadMessages());
    }

    public void pinMessage(String message) {
        System.out.println(ChatController.getInstance().pinMessages(message));
    }

    public void deleteMessage(String message) {
        System.out.println(ChatController.getInstance().deleteMessages(message));
    }

    public void editMessage(String message, Scanner scanner) {
        System.out.println("enter edited message: ");
        String newMessage = scanner.nextLine();
        System.out.println(ChatController.getInstance().editMessages(message, newMessage));
    }
}
