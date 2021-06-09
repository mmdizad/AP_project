package Controller;

import Model.*;
import com.google.gson.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LoginAndSignUpController implements Initializable {
    public static User user;

    @FXML
    public Button SubmitButton;

    @FXML
    public Button BackButton;

    @FXML
    public TextField UsernameTextField;

    @FXML
    public TextField NicknameTextField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public Label errorLabel;

    @FXML
    public TextField usernameLogin;

    @FXML
    public TextField passwordLogin;

    @FXML
    public Button submitLogin;

    @FXML
    public Button backLogin;

    @FXML
    public Label labelLogin;


    public String createUser(String username, String nickname, String password) {
        try {
            File openingUser = new File(System.getProperty("user.home") + "/Desktop\\AP FILES\\Users\\" + username + "user.txt");
            if (!User.isUserWithThisNicknameExists(nickname) && openingUser.createNewFile()) {
                Gson gson = new Gson();
                User user = new User(username, nickname, password);
                String userInfo = gson.toJson(user);
                FileWriter myWriter = new FileWriter(System.getProperty("user.home") + "/Desktop\\AP FILES\\Users\\" + username + "user.txt");
                myWriter.write(userInfo);
                myWriter.close();
                return "user created successfully!";
            } else {
                if (!User.isUserWithThisNicknameExists(nickname)) {
                    return "user with username " + username + " already exists";
                }
                return "user with nickname " + nickname + " already exists";
            }
        } catch (IOException e) {
            return "An error occurred.";
        }
    }

    public String login(String username, String password) {
        try {
            File openingUser = new File(System.getProperty("user.home") + "/Desktop\\AP FILES\\Users\\" + username + "user.txt");
            if (!openingUser.exists()) {
                return "Username and password didn't match!";
            } else {
                Gson gson = new Gson();
                StringBuilder getDetail = new StringBuilder();
                Scanner myReader = new Scanner(openingUser);
                while (myReader.hasNextLine()) {
                    getDetail.append(myReader.nextLine());
                }
                String userInfo = getDetail.toString();
                User user1 = gson.fromJson(userInfo, User.class);
                myReader.close();
                if (!user1.getPassword().equals(password)) {
                    return "Username and password didn't match!";
                } else {
                    user = user1;
                    return "user logged in successfully!";
                }
            }
        } catch (IOException e) {
            return "An error occurred.";
        }
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

    public static void createFolders() {
        File apFiles = new File(System.getProperty("user.home") + "/Desktop\\AP FILES");
        if (!apFiles.exists()) {
            apFiles.mkdir();
        }
        File users = new File(System.getProperty("user.home") + "/Desktop\\AP FILES\\Users");
        if (!users.exists()) {
            users.mkdir();
        }
        File decks = new File(System.getProperty("user.home") + "/Desktop\\AP FILES\\Decks");
        if (!decks.exists()) {
            decks.mkdir();
        }
    }

    public static void createCard() {
        createFirstMonster();
        createFirstSpell();
        createFirstTrap();
        createOtherMonsters();
        createOtherSpells();
        createOtherTraps();
    }

    public static void createFirstMonster() {
        ArrayList<Card> theFirstMonsterCards = new ArrayList<>();
        List<String[]> list = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader("C:\\Users\\Monster.csv"))) {
            list = reader.readAll();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }

        Monster monster = new Monster(list.get(28)[0], list.get(28)[7], list.get(28)[4], Integer.parseInt(list.get(28)[8])
                , "Monster", Integer.parseInt(list.get(28)[6]), Integer.parseInt(list.get(28)[5])
                , list.get(28)[3], list.get(28)[2], Integer.parseInt(list.get(28)[1]), false);
        theFirstMonsterCards.add(monster);
        Monster monster1 = new Monster(list.get(24)[0], list.get(24)[7], list.get(24)[4], Integer.parseInt(list.get(24)[8])
                , "Monster", Integer.parseInt(list.get(24)[6]), Integer.parseInt(list.get(24)[5])
                , list.get(24)[3], list.get(24)[2], Integer.parseInt(list.get(24)[1]), false);
        theFirstMonsterCards.add(monster1);
        Monster monster2 = new Monster(list.get(26)[0], list.get(26)[7], list.get(26)[4], Integer.parseInt(list.get(26)[8])
                , "Monster", Integer.parseInt(list.get(26)[6]), Integer.parseInt(list.get(26)[5])
                , list.get(26)[3], list.get(26)[2], Integer.parseInt(list.get(26)[1]), false);
        theFirstMonsterCards.add(monster2);
        Monster monster3 = new Monster(list.get(22)[0], list.get(22)[7], list.get(22)[4], Integer.parseInt(list.get(22)[8])
                , "Monster", Integer.parseInt(list.get(22)[6]), Integer.parseInt(list.get(22)[5])
                , list.get(22)[3], list.get(22)[2], Integer.parseInt(list.get(22)[1]), false);
        theFirstMonsterCards.add(monster3);
        Monster monster4 = new Monster(list.get(8)[0], list.get(8)[7], list.get(8)[4], Integer.parseInt(list.get(8)[8])
                , "Monster", Integer.parseInt(list.get(8)[6]), Integer.parseInt(list.get(8)[5])
                , list.get(8)[3], list.get(8)[2], Integer.parseInt(list.get(8)[1]), false);
        theFirstMonsterCards.add(monster4);
        Monster monster5 = new Monster(list.get(21)[0], list.get(21)[7], list.get(21)[4], Integer.parseInt(list.get(21)[8])
                , "Monster", Integer.parseInt(list.get(21)[6]), Integer.parseInt(list.get(21)[5])
                , list.get(21)[3], list.get(21)[2], Integer.parseInt(list.get(21)[1]), false);
        theFirstMonsterCards.add(monster5);
        Monster monster6 = new Monster(list.get(1)[0], list.get(1)[7], list.get(1)[4], Integer.parseInt(list.get(1)[8])
                , "Monster", Integer.parseInt(list.get(1)[6]), Integer.parseInt(list.get(1)[5])
                , list.get(1)[3], list.get(1)[2], Integer.parseInt(list.get(1)[1]), false);
        theFirstMonsterCards.add(monster6);
        Monster monster7 = new Monster(list.get(11)[0], list.get(11)[7], list.get(11)[4], Integer.parseInt(list.get(11)[8])
                , "Monster", Integer.parseInt(list.get(11)[6]), Integer.parseInt(list.get(11)[5])
                , list.get(11)[3], list.get(11)[2], Integer.parseInt(list.get(11)[1]), false);
        theFirstMonsterCards.add(monster7);
        Monster monster8 = new Monster(list.get(12)[0], list.get(12)[7], list.get(12)[4], Integer.parseInt(list.get(12)[8])
                , "Monster", Integer.parseInt(list.get(12)[6]), Integer.parseInt(list.get(12)[5])
                , list.get(12)[3], list.get(12)[2], Integer.parseInt(list.get(12)[1]), false);
        theFirstMonsterCards.add(monster8);
        Monster monster9 = new Monster(list.get(15)[0], list.get(15)[7], list.get(15)[4], Integer.parseInt(list.get(15)[8])
                , "Monster", Integer.parseInt(list.get(15)[6]), Integer.parseInt(list.get(15)[5])
                , list.get(15)[3], list.get(15)[2], Integer.parseInt(list.get(15)[1]), false);
        theFirstMonsterCards.add(monster9);
        Monster monster10 = new Monster(list.get(25)[0], list.get(25)[7], list.get(25)[4], Integer.parseInt(list.get(25)[8])
                , ",Monster", Integer.parseInt(list.get(25)[6]), Integer.parseInt(list.get(25)[5])
                , list.get(25)[3], list.get(25)[2], Integer.parseInt(list.get(25)[1]), false);
        theFirstMonsterCards.add(monster10);
        Monster monster11 = new Monster(list.get(13)[0], list.get(13)[7], list.get(13)[4], Integer.parseInt(list.get(13)[8])
                , "Monster", Integer.parseInt(list.get(13)[6]), Integer.parseInt(list.get(13)[5])
                , list.get(13)[3], list.get(13)[2], Integer.parseInt(list.get(13)[1]), false);
        theFirstMonsterCards.add(monster11);
        Monster monster12 = new Monster(list.get(35)[0], list.get(35)[7], list.get(35)[4], Integer.parseInt(list.get(35)[8])
                , "Monster", Integer.parseInt(list.get(35)[6]), Integer.parseInt(list.get(35)[5])
                , list.get(35)[3], list.get(35)[2], Integer.parseInt(list.get(35)[1]), false);
        theFirstMonsterCards.add(monster12);
        Monster monster13 = new Monster(list.get(5)[0], list.get(5)[7], list.get(5)[4], Integer.parseInt(list.get(5)[8])
                , "Monster", Integer.parseInt(list.get(5)[6]), Integer.parseInt(list.get(5)[5])
                , list.get(5)[3], list.get(5)[2], Integer.parseInt(list.get(5)[1]), false);
        theFirstMonsterCards.add(monster13);
        Monster monster14 = new Monster(list.get(18)[0], list.get(18)[7], list.get(18)[4], Integer.parseInt(list.get(18)[8])
                , "Monster", Integer.parseInt(list.get(18)[6]), Integer.parseInt(list.get(18)[5])
                , list.get(18)[3], list.get(18)[2], Integer.parseInt(list.get(18)[1]), false);
        theFirstMonsterCards.add(monster14);
        Monster monster15 = new Monster(list.get(30)[0], list.get(30)[7], list.get(30)[4], Integer.parseInt(list.get(30)[8])
                , "Monster", Integer.parseInt(list.get(30)[6]), Integer.parseInt(list.get(30)[5])
                , list.get(30)[3], list.get(30)[2], Integer.parseInt(list.get(30)[1]), false);
        theFirstMonsterCards.add(monster15);
        Monster monster16 = new Monster(list.get(29)[0], list.get(29)[7], list.get(29)[4], Integer.parseInt(list.get(29)[8])
                , "Monster", Integer.parseInt(list.get(29)[6]), Integer.parseInt(list.get(29)[5])
                , list.get(29)[3], list.get(29)[2], Integer.parseInt(list.get(29)[1]), false);
        theFirstMonsterCards.add(monster16);
        Monster monster17 = new Monster(list.get(9)[0], list.get(9)[7], list.get(9)[4], Integer.parseInt(list.get(9)[8])
                , "Monster", Integer.parseInt(list.get(9)[6]), Integer.parseInt(list.get(9)[5])
                , list.get(9)[3], list.get(9)[2], Integer.parseInt(list.get(9)[1]), false);
        theFirstMonsterCards.add(monster17);
        Monster monster18 = new Monster(list.get(41)[0], list.get(41)[7], list.get(41)[4], Integer.parseInt(list.get(41)[8])
                , "Monster", Integer.parseInt(list.get(41)[6]), Integer.parseInt(list.get(41)[5])
                , list.get(41)[3], list.get(41)[2], Integer.parseInt(list.get(41)[1]), false);
        theFirstMonsterCards.add(monster18);
        Monster monster19 = new Monster(list.get(7)[0], list.get(7)[7], list.get(7)[4], Integer.parseInt(list.get(7)[8])
                , "Monster", Integer.parseInt(list.get(7)[6]), Integer.parseInt(list.get(7)[5])
                , list.get(7)[3], list.get(7)[2], Integer.parseInt(list.get(7)[1]), false);
        theFirstMonsterCards.add(monster19);
        Card.addFirstCards(theFirstMonsterCards);
    }

    public static void createFirstSpell() {
        ArrayList<Card> theFirstSpellCards = new ArrayList<>();
        List<String[]> list = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader("C:\\Users\\SpellTrap.csv"))) {
            list = reader.readAll();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }
        // cardType : icon(property)
        Spell spell = new Spell(list.get(13)[0], list.get(13)[3], list.get(13)[2], Integer.parseInt(list.get(13)[5])
                , list.get(13)[1], list.get(13)[4]);
        theFirstSpellCards.add(spell);
        Spell spell1 = new Spell(list.get(14)[0], list.get(14)[3], list.get(14)[2], Integer.parseInt(list.get(14)[5])
                , list.get(14)[1], list.get(14)[4]);
        theFirstSpellCards.add(spell1);
        Spell spell2 = new Spell(list.get(15)[0], list.get(15)[3], list.get(15)[2], Integer.parseInt(list.get(15)[5])
                , list.get(15)[1], list.get(15)[4]);
        theFirstSpellCards.add(spell2);
        Spell spell3 = new Spell(list.get(16)[0], list.get(16)[3], list.get(16)[2], Integer.parseInt(list.get(16)[5])
                , list.get(16)[1], list.get(16)[4]);
        theFirstSpellCards.add(spell3);
        Spell spell4 = new Spell(list.get(17)[0], list.get(17)[3], list.get(17)[2], Integer.parseInt(list.get(17)[5])
                , list.get(17)[1], list.get(17)[4]);
        theFirstSpellCards.add(spell4);
        Spell spell5 = new Spell(list.get(20)[0], list.get(20)[3], list.get(20)[2], Integer.parseInt(list.get(20)[5])
                , list.get(20)[1], list.get(20)[4]);
        theFirstSpellCards.add(spell5);
        Spell spell6 = new Spell(list.get(18)[0], list.get(18)[3], list.get(18)[2], Integer.parseInt(list.get(18)[5])
                , list.get(18)[1], list.get(18)[4]);
        theFirstSpellCards.add(spell6);
        Spell spell7 = new Spell(list.get(19)[0], list.get(19)[3], list.get(19)[2], Integer.parseInt(list.get(19)[5])
                , list.get(19)[1], list.get(19)[4]);
        theFirstSpellCards.add(spell7);
        Spell spell8 = new Spell(list.get(24)[0], list.get(24)[3], list.get(24)[2], Integer.parseInt(list.get(24)[5])
                , list.get(24)[1], list.get(24)[4]);
        theFirstSpellCards.add(spell8);
        Spell spell9 = new Spell(list.get(28)[0], list.get(28)[3], list.get(28)[2], Integer.parseInt(list.get(28)[5])
                , list.get(28)[1], list.get(28)[4]);
        theFirstSpellCards.add(spell9);
        Spell spell10 = new Spell(list.get(33)[0], list.get(33)[3], list.get(33)[2], Integer.parseInt(list.get(33)[5])
                , list.get(33)[1], list.get(33)[4]);
        theFirstSpellCards.add(spell10);
        Spell spell11 = new Spell(list.get(25)[0], list.get(25)[3], list.get(25)[2], Integer.parseInt(list.get(25)[5])
                , list.get(25)[1], list.get(25)[4]);
        theFirstSpellCards.add(spell11);
        Card.addFirstCards(theFirstSpellCards);
    }

    public static void createFirstTrap() {
        ArrayList<Card> theFirstTrapCards = new ArrayList<>();
        List<String[]> list = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader("C:\\Users\\SpellTrap.csv"))) {
            list = reader.readAll();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }
        // cardType : icon(property)
        Trap trap = new Trap(list.get(1)[0], list.get(1)[3], list.get(1)[2], Integer.parseInt(list.get(1)[5])
                , list.get(1)[1], list.get(1)[4]);
        theFirstTrapCards.add(trap);
        Trap trap1 = new Trap(list.get(2)[0], list.get(2)[3], list.get(2)[2], Integer.parseInt(list.get(2)[5])
                , list.get(2)[1], list.get(2)[4]);
        theFirstTrapCards.add(trap1);
        Trap trap2 = new Trap(list.get(3)[0], list.get(3)[3], list.get(3)[2], Integer.parseInt(list.get(3)[5])
                , list.get(3)[1], list.get(3)[4]);
        theFirstTrapCards.add(trap2);
        Trap trap3 = new Trap(list.get(4)[0], list.get(4)[3], list.get(4)[2], Integer.parseInt(list.get(4)[5])
                , list.get(4)[1], list.get(4)[4]);
        theFirstTrapCards.add(trap3);
        Trap trap4 = new Trap(list.get(5)[0], list.get(5)[3], list.get(5)[2], Integer.parseInt(list.get(5)[5])
                , list.get(5)[1], list.get(5)[4]);
        theFirstTrapCards.add(trap4);
        Trap trap5 = new Trap(list.get(6)[0], list.get(6)[3], list.get(6)[2], Integer.parseInt(list.get(6)[5])
                , list.get(6)[1], list.get(6)[4]);
        theFirstTrapCards.add(trap5);
        Trap trap6 = new Trap(list.get(9)[0], list.get(9)[3], list.get(9)[2], Integer.parseInt(list.get(9)[5])
                , list.get(9)[1], list.get(9)[4]);
        theFirstTrapCards.add(trap6);
        Trap trap7 = new Trap(list.get(10)[0], list.get(10)[3], list.get(10)[2], Integer.parseInt(list.get(10)[5])
                , list.get(10)[1], list.get(10)[4]);
        theFirstTrapCards.add(trap7);
        Card.addFirstCards(theFirstTrapCards);
    }

    public static void createOtherMonsters() {
        List<String[]> list = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader("C:\\Users\\Monster.csv"))) {
            list = reader.readAll();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }
        new Monster(list.get(2)[0], list.get(2)[7], list.get(2)[4], Integer.parseInt(list.get(2)[8])
                , "Monster", Integer.parseInt(list.get(2)[6]), Integer.parseInt(list.get(2)[5])
                , list.get(2)[3], list.get(2)[2], Integer.parseInt(list.get(2)[1]), false);
        new Monster(list.get(3)[0], list.get(3)[7], list.get(3)[4], Integer.parseInt(list.get(3)[8])
                , "Monster", Integer.parseInt(list.get(3)[6]), Integer.parseInt(list.get(3)[5])
                , list.get(3)[3], list.get(3)[2], Integer.parseInt(list.get(3)[1]), false);
        new Monster(list.get(4)[0], list.get(4)[7], list.get(4)[4], Integer.parseInt(list.get(4)[8])
                , "Monster", Integer.parseInt(list.get(4)[6]), Integer.parseInt(list.get(4)[5])
                , list.get(4)[3], list.get(4)[2], Integer.parseInt(list.get(4)[1]), false);
        new Monster(list.get(6)[0], list.get(6)[7], list.get(6)[4], Integer.parseInt(list.get(6)[8])
                , "Monster", Integer.parseInt(list.get(6)[6]), Integer.parseInt(list.get(6)[5])
                , list.get(6)[3], list.get(6)[2], Integer.parseInt(list.get(6)[1]), false);
        new Monster(list.get(10)[0], list.get(10)[7], list.get(10)[4], Integer.parseInt(list.get(10)[8])
                , "Monster", Integer.parseInt(list.get(10)[6]), Integer.parseInt(list.get(10)[5])
                , list.get(10)[3], list.get(10)[2], Integer.parseInt(list.get(10)[1]), false);
        new Monster(list.get(14)[0], list.get(14)[7], list.get(14)[4], Integer.parseInt(list.get(14)[8])
                , "Monster", Integer.parseInt(list.get(14)[6]), Integer.parseInt(list.get(14)[5])
                , list.get(14)[3], list.get(14)[2], Integer.parseInt(list.get(14)[1]), false);
        new Monster(list.get(16)[0], list.get(16)[7], list.get(16)[4], Integer.parseInt(list.get(16)[8])
                , "Monster", Integer.parseInt(list.get(16)[6]), Integer.parseInt(list.get(16)[5])
                , list.get(16)[3], list.get(16)[2], Integer.parseInt(list.get(16)[1]), false);
        new Monster(list.get(17)[0], list.get(17)[7], list.get(17)[4], Integer.parseInt(list.get(17)[8])
                , "Monster", Integer.parseInt(list.get(17)[6]), Integer.parseInt(list.get(17)[5])
                , list.get(17)[3], list.get(17)[2], Integer.parseInt(list.get(17)[1]), false);
        new Monster(list.get(19)[0], list.get(19)[7], list.get(19)[4], Integer.parseInt(list.get(19)[8])
                , "Monster", Integer.parseInt(list.get(19)[6]), Integer.parseInt(list.get(19)[5])
                , list.get(19)[3], list.get(19)[2], Integer.parseInt(list.get(19)[1]), false);
        new Monster(list.get(20)[0], list.get(20)[7], list.get(20)[4], Integer.parseInt(list.get(20)[8])
                , "Monster", Integer.parseInt(list.get(20)[6]), Integer.parseInt(list.get(20)[5])
                , list.get(20)[3], list.get(20)[2], Integer.parseInt(list.get(20)[1]), false);
        new Monster(list.get(23)[0], list.get(23)[7], list.get(23)[4], Integer.parseInt(list.get(23)[8])
                , ",Monster", Integer.parseInt(list.get(23)[6]), Integer.parseInt(list.get(23)[5])
                , list.get(23)[3], list.get(23)[2], Integer.parseInt(list.get(23)[1]), true);
        new Monster(list.get(27)[0], list.get(27)[7], list.get(27)[4], Integer.parseInt(list.get(27)[8])
                , "Monster", Integer.parseInt(list.get(27)[6]), Integer.parseInt(list.get(27)[5])
                , list.get(27)[3], list.get(27)[2], Integer.parseInt(list.get(27)[1]), false);
        new Monster(list.get(31)[0], list.get(31)[7], list.get(31)[4], Integer.parseInt(list.get(31)[8])
                , "Monster", Integer.parseInt(list.get(31)[6]), Integer.parseInt(list.get(31)[5])
                , list.get(31)[3], list.get(31)[2], Integer.parseInt(list.get(31)[1]), false);
        new Monster(list.get(32)[0], list.get(32)[7], list.get(32)[4], Integer.parseInt(list.get(32)[8])
                , "Monster", Integer.parseInt(list.get(32)[6]), Integer.parseInt(list.get(32)[5])
                , list.get(32)[3], list.get(32)[2], Integer.parseInt(list.get(32)[1]), false);
        new Monster(list.get(33)[0], list.get(33)[7], list.get(33)[4], Integer.parseInt(list.get(33)[8])
                , "Monster", Integer.parseInt(list.get(33)[6]), Integer.parseInt(list.get(33)[5])
                , list.get(33)[3], list.get(33)[2], Integer.parseInt(list.get(33)[1]), false);
        new Monster(list.get(34)[0], list.get(34)[7], list.get(34)[4], Integer.parseInt(list.get(34)[8])
                , "Monster", Integer.parseInt(list.get(34)[6]), Integer.parseInt(list.get(34)[5])
                , list.get(34)[3], list.get(34)[2], Integer.parseInt(list.get(34)[1]), false);
        new Monster(list.get(36)[0], list.get(36)[7], list.get(36)[4], Integer.parseInt(list.get(36)[8])
                , "Monster", Integer.parseInt(list.get(36)[6]), Integer.parseInt(list.get(36)[5])
                , list.get(36)[3], list.get(36)[2], Integer.parseInt(list.get(36)[1]), false);
        new Monster(list.get(37)[0], list.get(37)[7], list.get(37)[4], Integer.parseInt(list.get(37)[8])
                , "Monster", Integer.parseInt(list.get(37)[6]), Integer.parseInt(list.get(37)[5])
                , list.get(37)[3], list.get(37)[2], Integer.parseInt(list.get(37)[1]), false);
        new Monster(list.get(38)[0], list.get(38)[7], list.get(38)[4], Integer.parseInt(list.get(38)[8])
                , "Monster", Integer.parseInt(list.get(38)[6]), Integer.parseInt(list.get(38)[5])
                , list.get(38)[3], list.get(38)[2], Integer.parseInt(list.get(38)[1]), false);
        new Monster(list.get(39)[0], list.get(39)[7], list.get(39)[4], Integer.parseInt(list.get(39)[8])
                , "Monster", Integer.parseInt(list.get(39)[6]), Integer.parseInt(list.get(39)[5])
                , list.get(39)[3], list.get(39)[2], Integer.parseInt(list.get(39)[1]), true);
        new Monster(list.get(40)[0], list.get(40)[7], list.get(40)[4], Integer.parseInt(list.get(40)[8])
                , "Monster", Integer.parseInt(list.get(40)[6]), Integer.parseInt(list.get(40)[5])
                , list.get(40)[3], list.get(40)[2], Integer.parseInt(list.get(40)[1]), false);
    }

    public static void createOtherSpells() {
        List<String[]> list = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader("C:\\Users\\SpellTrap.csv"))) {
            list = reader.readAll();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }
        new Spell(list.get(21)[0], list.get(21)[3], list.get(21)[2], Integer.parseInt(list.get(21)[5])
                , list.get(21)[1], list.get(21)[4]);
        new Spell(list.get(22)[0], list.get(22)[3], list.get(22)[2], Integer.parseInt(list.get(22)[5])
                , list.get(22)[1], list.get(22)[4]);
        new Spell(list.get(23)[0], list.get(23)[3], list.get(23)[2], Integer.parseInt(list.get(23)[5])
                , list.get(23)[1], list.get(23)[4]);
        new Spell(list.get(26)[0], list.get(26)[3], list.get(26)[2], Integer.parseInt(list.get(26)[5])
                , list.get(26)[1], list.get(26)[4]);
        new Spell(list.get(27)[0], list.get(27)[3], list.get(27)[2], Integer.parseInt(list.get(27)[5])
                , list.get(27)[1], list.get(27)[4]);
        new Spell(list.get(29)[0], list.get(29)[3], list.get(29)[2], Integer.parseInt(list.get(29)[5])
                , list.get(29)[1], list.get(29)[4]);
        new Spell(list.get(30)[0], list.get(30)[3], list.get(30)[2], Integer.parseInt(list.get(30)[5])
                , list.get(30)[1], list.get(30)[4]);
        new Spell(list.get(31)[0], list.get(31)[3], list.get(31)[2], Integer.parseInt(list.get(31)[5])
                , list.get(31)[1], list.get(31)[4]);
        new Spell(list.get(32)[0], list.get(32)[3], list.get(32)[2], Integer.parseInt(list.get(32)[5])
                , list.get(32)[1], list.get(32)[4]);
        new Spell(list.get(34)[0], list.get(34)[3], list.get(34)[2], Integer.parseInt(list.get(34)[5])
                , list.get(34)[1], list.get(34)[4]);
        new Spell(list.get(35)[0], list.get(35)[3], list.get(35)[2], Integer.parseInt(list.get(35)[5])
                , list.get(35)[1], list.get(35)[4]);
    }

    public static void createOtherTraps() {
        List<String[]> list = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader("C:\\Users\\SpellTrap.csv"))) {
            list = reader.readAll();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }

        new Trap(list.get(7)[0], list.get(7)[3], list.get(7)[2], Integer.parseInt(list.get(7)[5])
                , list.get(7)[1], list.get(7)[4]);
        new Trap(list.get(8)[0], list.get(8)[3], list.get(8)[2], Integer.parseInt(list.get(8)[5])
                , list.get(8)[1], list.get(8)[4]);
        new Trap(list.get(11)[0], list.get(11)[3], list.get(11)[2], Integer.parseInt(list.get(11)[5])
                , list.get(11)[1], list.get(11)[4]);
        new Trap(list.get(12)[0], list.get(12)[3], list.get(12)[2], Integer.parseInt(list.get(12)[5])
                , list.get(12)[1], list.get(12)[4]);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (SubmitButton != null) {
            SubmitButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (UsernameTextField.getText().equals("") || NicknameTextField.getText().equals("")
                            || passwordField.getText().equals("")) {
                        errorLabel.setText("You must fill all of box");
                        errorLabel.setTextFill(Color.RED);
                    } else {
                        String response = createUser(UsernameTextField.getText(), NicknameTextField.getText(),
                                passwordField.getText());
                        errorLabel.setText(response);
                        if (!response.equals("user created successfully!")) {
                            errorLabel.setTextFill(Color.RED);
                        } else {
                            errorLabel.setTextFill(Color.GREEN);
                        }
                    }
                }
            });

            BackButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Stage stage = (Stage) BackButton.getScene().getWindow();
                    backToWelcomePage(stage);
                }
            });
        } else {
            submitLogin.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (usernameLogin.getText().equals("") || passwordLogin.getText().equals("")) {
                        errorLabel.setText("You must fill all of box");
                        errorLabel.setTextFill(Color.RED);
                    } else {
                        String response = login(usernameLogin.getText(), passwordLogin.getText());
                        labelLogin.setText(response);
                        if (response.equals("user logged in successfully!")) {
                            labelLogin.setTextFill(Color.GREEN);
                            Stage stage = (Stage) backLogin.getScene().getWindow();
                            stage.close();
                            URL url = null;
                            try {
                                url = new File("src/main/java/FXMLFiles/MainMenu.fxml").toURI().toURL();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            Parent root = null;
                            try {
                                assert url != null;
                                root = FXMLLoader.load(url);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Stage mainMenuStage = new Stage();
                            mainMenuStage.setTitle("MainMenu");
                            assert root != null;
                            mainMenuStage.setScene(new Scene(root, 1920, 1000));
                            mainMenuStage.show();
                        } else {
                            labelLogin.setTextFill(Color.RED);
                        }
                    }
                }
            });

            backLogin.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Stage stage = (Stage) backLogin.getScene().getWindow();
                    backToWelcomePage(stage);
                }

            });
        }
    }

    public void backToWelcomePage(Stage stage) {
        stage.close();
        LoginAndSignUpController.createFolders();
        URL url = null;
        try {
            url = new File("src/main/java/FXMLFiles/Welcome.fxml").toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Parent root = null;
        try {
            assert url != null;
            root = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage welcomeStage = new Stage();
        welcomeStage.setTitle("WelcomePage");
        assert root != null;
        welcomeStage.setScene(new Scene(root, 1920, 1000));
        welcomeStage.show();
    }
}

