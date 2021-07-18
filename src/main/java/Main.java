import Controller.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
//        LoginAndSignUpController.createFolders();
//        LoginAndSignUpController.createCard();
        Scanner scanner = new Scanner(System.in);
        new Thread(()-> {
            while (true){
                String command = scanner.nextLine();
                if(command.startsWith("increase card")){
                    increaseCard(getCommandMatcher(command,"increase card (.+) (\\d+)"));
                } else if(command.startsWith("ban")){
                    banCard(getCommandMatcher(command, "ban card (\\w+)"));
                }else if(command.startsWith("remove ban card")){
                    removeBanCard(getCommandMatcher(command,"remove ban card (\\w+)"));
                }else System.out.println("invalid command");

            }
        }).start();
        LoginAndSignUpController.createFolders();
        LoginAndSignUpController.createCard();
        ShopController.initializeCard();
        runApp();

    }
   private static void increaseCard(Matcher matcher){
        if(matcher.find()){
            String cardName = matcher.group(1);
            int numberOfCard = Integer.parseInt(matcher.group(2));
            System.out.println(ShopController.getInstance().increaseNumberOfCard(cardName,numberOfCard));
        }else System.out.println("gg");
 }
    private static void banCard(Matcher matcher){
        if(matcher.find()){
            String cardName =matcher.group(1);
            if(ShopController.getInstance().banCard(cardName)==1)
            System.out.println("card "+cardName +"banned successfully");
            else System.out.println("card doesn't exist");
        }
    }
    private static void removeBanCard(Matcher matcher){
        if(matcher.find()){
            String cardName =matcher.group(1);
            if(ShopController.getInstance().removeBanCard(cardName)==1)
            ShopController.getInstance().removeBanCard(cardName);
            System.out.println("card "+cardName +" removed ban successfully");
        }else System.out.println("invalid command");
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
            DuelController duelController = DuelController.getInstance();
            return String.valueOf(duelController.selectFirstPlayer(input));
        } else if (input.startsWith("deck create")) {
            return DeckController.getInstance().deckCreate(input);
        } else if (input.startsWith("deck delete")) {
            return DeckController.getInstance().deckDelete(input);
        } else if (input.startsWith("deck setActive")) {
            return DeckController.getInstance().deckSetActive(input);
        } else if (input.startsWith("attack")) {
            return BattlePhaseController.getInstance().attack(input);
        } else if (input.startsWith("direct attack")) {
            return BattlePhaseController.getInstance().directAttack(input);
        } else if (input.startsWith("scoreBoard")) {
            return ScoreBoardController.getInstance().getScoreBoard(input);
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
            return DuelController.getInstance().newCardToHand(input);
        } else if (input.startsWith("Select Monster")) {
            return DuelController.getInstance().selectMonster(input);
        } else if (input.startsWith("Select Opponent Monster")) {
            return DuelController.getInstance().selectOpponentMonster(input);
        } else if (input.startsWith("Select Spell Or Trap")) {
            return DuelController.getInstance().selectSpellOrTrap(input);
        } else if (input.startsWith("Select Opponent Spell Or Trap")) {
            return DuelController.getInstance().selectOpponentSpellOrTrap(input);
        } else if (input.startsWith("Select FieldZone")) {
            return DuelController.getInstance().selectFieldZone(input);
        } else if (input.startsWith("Select Opponent FieldZone")) {
            return DuelController.getInstance().selectOpponentFieldZone(input);
        } else if (input.startsWith("Select Hand")) {
            return DuelController.getInstance().selectHand(input);
        } else if (input.startsWith("shop buy card")) {
            return ShopController.getInstance().buyCard(input);
        } else if (input.startsWith("shop increase money")) {
            return ShopController.getInstance().increaseMoney(input);
        } else if (input.startsWith("Summon Terratiger, the Empowered Warrior")) {
            return DuelController.getInstance().normalSummonCardThatCanSummonAnotherCard(input);
        } else if (input.startsWith("summon")) {
            return DuelController.getInstance().summon(input);
        } else if (input.startsWith("Summon Monster With Level 5 Or 6")) {
            return DuelController.getInstance().normalSummonMonsterWithLevel5or6(input);
        } else if (input.startsWith("Summon Beast King Barbaros")) {
            return DuelController.getInstance().summonMonsterHasTwoMethods(input);
        } else if (input.startsWith("Summon Monster With Level 7 Or More")) {
            return DuelController.getInstance().summonCardWithLevel7orMore(input);
        } else if (input.startsWith("special Summon")) {
            return DuelController.getInstance().specialSummon(input);
        } else if (input.startsWith("Special Summon The Tricky")) {
            return DuelController.getInstance().specialSummonTheTricky(input);
        } else if (input.startsWith("Special Summon Gate Guardian")) {
            return DuelController.getInstance().specialSummonGateGuardian(input);
        } else if (input.startsWith("flip Summon")) {
            return DuelController.getInstance().flipSummon(input);
        } else if (input.startsWith("Flip Summon ManEaterBug")) {
            return DuelController.getInstance().flipSummonManEaterBug(input);
        } else if (input.startsWith("shop show --all")) {
            return ShopController.getInstance().getAllCard();
        } else if (input.startsWith("change nickname")) {
            return ProfileController.getInstance().changeNickName(input);
        } else if (input.startsWith("change password")) {
            return ProfileController.getInstance().changePassword(input);
        }else if(input.startsWith("set")){
            DuelController.getInstance().set(input);
        }
        return "";
    }

    public static Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
