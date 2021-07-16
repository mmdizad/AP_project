import Controller.DeckController;
import Controller.DuelController;
import Controller.LoginAndSignUpController;
import Controller.StartDuelController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        LoginAndSignUpController.createFolders();
        LoginAndSignUpController.createCard();
        runApp();
    }

    public static void runApp() throws IOException {
        // TODO ServerController.loadData();

        ServerSocket serverSocket = new ServerSocket(7777);
        while (true) {
            Socket socket = serverSocket.accept();
            startNewThread(serverSocket, socket);
        }
    }

    private static void startNewThread(ServerSocket serverSocket, Socket socket) {
        new Thread(() -> {
            try {
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                getInputAndProcess(dataInputStream, dataOutputStream);
                socket.close();
                serverSocket.close();
                dataInputStream.close();
            } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void getInputAndProcess(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        while (true) {
            String input = dataInputStream.readUTF();
            String response = process(input);
            if (input.equals(""))
                break;
            else {
                dataOutputStream.writeUTF(response);
                dataOutputStream.flush();
            }
        }
    }

    private static String process(String input) {
        if (input.startsWith("create user")) {
            LoginAndSignUpController loginAndSignUpController = LoginAndSignUpController.getInstance();
            return loginAndSignUpController.createUser(input);
        } else if (input.startsWith("login user")) {
            LoginAndSignUpController loginAndSignUpController = LoginAndSignUpController.getInstance();
            return loginAndSignUpController.login(input);
        } else if (input.startsWith("new Duel")) {
            return StartDuelController.getInstance().startTheGame(input);
        } else if (input.startsWith("Select First Player")) {
            DuelController duelController = new DuelController();
            return String.valueOf(duelController.selectFirstPlayer(input));
        } else if (input.startsWith("deck create")) {
            return DeckController.getInstance().deckCreate(input);
        } else if (input.startsWith("deck delete")) {
            return DeckController.getInstance().deckDelete(input);
        } else if (input.startsWith("deck setActive")) {
            return DeckController.getInstance().deckSetActive(input);
        } else if (input.startsWith("add card")) {
            return DeckController.getInstance().addCard(input);
        } else if (input.startsWith("delete card")) {
            return DeckController.getInstance().deleteCard(input);
        } else if (input.startsWith("deck show")) {
            return DeckController.getInstance().deckShow(input);
        } else if (input.startsWith("show all owned cards")) {
            return DeckController.getInstance().showAllOwnedCards(input);
        } else if (input.startsWith("show all deck")) {
            return DeckController.getInstance().showAllDeck(input);
        } else if (input.startsWith("show one card")) {
            return DeckController.getInstance().showOneCard(input);
        } else if (input.startsWith("New Card To Hand")) {
            return DuelController.newCardToHand(input);
        }
        return "";
    }

    public Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
