package Controller;

import Model.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LoginController {
    public static User user;

    public String createUser(String username, String nickname, String password) {
        if (User.isUserWithThisUsernameExists(username)) {
            return "user with username " + username + " already exists";
        } else if (User.isUserWithThisNicknameExists(nickname)) {
            return "user with nickname " + nickname + " already exists";
        } else {
            User newUser = new User(username, nickname, password);
            return "user created successfully!";
        }
    }

    public String login(String username, String password) {
        if (!User.isUserWithThisUsernameExists(username)) {
            return "Username and password didn't match!";
        } else if (!User.getUserByUsername(username).getPassword().equals(password)) {
            return "Username and password didn't match!";
        } else {
            user = User.getUserByUsername(username);
            return "user logged in successfully!";
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
                , list.get(28)[3], list.get(28)[2], Integer.parseInt(list.get(28)[1]));
        theFirstMonsterCards.add(monster);
        Monster monster1 = new Monster(list.get(24)[0], list.get(24)[7], list.get(24)[4], Integer.parseInt(list.get(24)[8])
                , "Monster", Integer.parseInt(list.get(24)[6]), Integer.parseInt(list.get(24)[5])
                , list.get(24)[3], list.get(24)[2], Integer.parseInt(list.get(24)[1]));
        theFirstMonsterCards.add(monster1);
        Monster monster2 = new Monster(list.get(26)[0], list.get(26)[7], list.get(26)[4], Integer.parseInt(list.get(26)[8])
                , "Monster", Integer.parseInt(list.get(26)[6]), Integer.parseInt(list.get(26)[5])
                , list.get(26)[3], list.get(26)[2], Integer.parseInt(list.get(26)[1]));
        theFirstMonsterCards.add(monster2);
        Monster monster3 = new Monster(list.get(22)[0], list.get(22)[7], list.get(22)[4], Integer.parseInt(list.get(22)[8])
                , "Monster", Integer.parseInt(list.get(22)[6]), Integer.parseInt(list.get(22)[5])
                , list.get(22)[3], list.get(22)[2], Integer.parseInt(list.get(22)[1]));
        theFirstMonsterCards.add(monster3);
        Monster monster4 = new Monster(list.get(8)[0], list.get(8)[7], list.get(8)[4], Integer.parseInt(list.get(8)[8])
                , "Monster", Integer.parseInt(list.get(8)[6]), Integer.parseInt(list.get(8)[5])
                , list.get(8)[3], list.get(8)[2], Integer.parseInt(list.get(8)[1]));
        theFirstMonsterCards.add(monster4);
        Monster monster5 = new Monster(list.get(21)[0], list.get(21)[7], list.get(21)[4], Integer.parseInt(list.get(21)[8])
                , "Monster", Integer.parseInt(list.get(21)[6]), Integer.parseInt(list.get(21)[5])
                , list.get(21)[3], list.get(21)[2], Integer.parseInt(list.get(21)[1]));
        theFirstMonsterCards.add(monster5);
        Monster monster6 = new Monster(list.get(1)[0], list.get(1)[7], list.get(1)[4], Integer.parseInt(list.get(1)[8])
                , "Monster", Integer.parseInt(list.get(1)[6]), Integer.parseInt(list.get(1)[5])
                , list.get(1)[3], list.get(1)[2], Integer.parseInt(list.get(1)[1]));
        theFirstMonsterCards.add(monster6);
        Monster monster7 = new Monster(list.get(11)[0], list.get(11)[7], list.get(11)[4], Integer.parseInt(list.get(11)[8])
                , "Monster", Integer.parseInt(list.get(11)[6]), Integer.parseInt(list.get(11)[5])
                , list.get(11)[3], list.get(11)[2], Integer.parseInt(list.get(11)[1]));
        theFirstMonsterCards.add(monster7);
        Monster monster8 = new Monster(list.get(12)[0], list.get(12)[7], list.get(12)[4], Integer.parseInt(list.get(12)[8])
                , "Monster", Integer.parseInt(list.get(12)[6]), Integer.parseInt(list.get(12)[5])
                , list.get(12)[3], list.get(12)[2], Integer.parseInt(list.get(12)[1]));
        theFirstMonsterCards.add(monster8);
        Monster monster9 = new Monster(list.get(15)[0], list.get(15)[7], list.get(15)[4], Integer.parseInt(list.get(15)[8])
                , "Monster", Integer.parseInt(list.get(15)[6]), Integer.parseInt(list.get(15)[5])
                , list.get(15)[3], list.get(15)[2], Integer.parseInt(list.get(15)[1]));
        theFirstMonsterCards.add(monster9);
        Monster monster10 = new Monster(list.get(25)[0], list.get(25)[7], list.get(25)[4], Integer.parseInt(list.get(25)[8])
                , ",Monster", Integer.parseInt(list.get(25)[6]), Integer.parseInt(list.get(25)[5])
                , list.get(25)[3], list.get(25)[2], Integer.parseInt(list.get(25)[1]));
        theFirstMonsterCards.add(monster10);
        Monster monster11 = new Monster(list.get(13)[0], list.get(13)[7], list.get(13)[4], Integer.parseInt(list.get(13)[8])
                , "Monster", Integer.parseInt(list.get(13)[6]), Integer.parseInt(list.get(13)[5])
                , list.get(13)[3], list.get(13)[2], Integer.parseInt(list.get(13)[1]));
        theFirstMonsterCards.add(monster11);
        Monster monster12 = new Monster(list.get(35)[0], list.get(35)[7], list.get(35)[4], Integer.parseInt(list.get(35)[8])
                , "Monster", Integer.parseInt(list.get(35)[6]), Integer.parseInt(list.get(35)[5])
                , list.get(35)[3], list.get(35)[2], Integer.parseInt(list.get(35)[1]));
        theFirstMonsterCards.add(monster12);
        Monster monster13 = new Monster(list.get(5)[0], list.get(5)[7], list.get(5)[4], Integer.parseInt(list.get(5)[8])
                , "Monster", Integer.parseInt(list.get(5)[6]), Integer.parseInt(list.get(5)[5])
                , list.get(5)[3], list.get(5)[2], Integer.parseInt(list.get(5)[1]));
        theFirstMonsterCards.add(monster13);
        Monster monster14 = new Monster(list.get(18)[0], list.get(18)[7], list.get(18)[4], Integer.parseInt(list.get(18)[8])
                , "Monster", Integer.parseInt(list.get(18)[6]), Integer.parseInt(list.get(18)[5])
                , list.get(18)[3], list.get(18)[2], Integer.parseInt(list.get(18)[1]));
        theFirstMonsterCards.add(monster14);
        Monster monster15 = new Monster(list.get(30)[0], list.get(30)[7], list.get(30)[4], Integer.parseInt(list.get(30)[8])
                , "Monster", Integer.parseInt(list.get(30)[6]), Integer.parseInt(list.get(30)[5])
                , list.get(30)[3], list.get(30)[2], Integer.parseInt(list.get(30)[1]));
        theFirstMonsterCards.add(monster15);
        Monster monster16 = new Monster(list.get(29)[0], list.get(29)[7], list.get(29)[4], Integer.parseInt(list.get(29)[8])
                , "Monster", Integer.parseInt(list.get(29)[6]), Integer.parseInt(list.get(29)[5])
                , list.get(29)[3], list.get(29)[2], Integer.parseInt(list.get(29)[1]));
        theFirstMonsterCards.add(monster16);
        Monster monster17 = new Monster(list.get(9)[0], list.get(9)[7], list.get(9)[4], Integer.parseInt(list.get(9)[8])
                , "Monster", Integer.parseInt(list.get(9)[6]), Integer.parseInt(list.get(9)[5])
                , list.get(9)[3], list.get(9)[2], Integer.parseInt(list.get(9)[1]));
        theFirstMonsterCards.add(monster17);
        Monster monster18 = new Monster(list.get(41)[0], list.get(41)[7], list.get(41)[4], Integer.parseInt(list.get(41)[8])
                , "Monster", Integer.parseInt(list.get(41)[6]), Integer.parseInt(list.get(41)[5])
                , list.get(41)[3], list.get(41)[2], Integer.parseInt(list.get(41)[1]));
        theFirstMonsterCards.add(monster18);
        Monster monster19 = new Monster(list.get(7)[0], list.get(7)[7], list.get(7)[4], Integer.parseInt(list.get(7)[8])
                , "Monster", Integer.parseInt(list.get(7)[6]), Integer.parseInt(list.get(7)[5])
                , list.get(7)[3], list.get(7)[2], Integer.parseInt(list.get(7)[1]));
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
        Monster monster20 = new Monster(list.get(2)[0], list.get(2)[7], list.get(2)[4], Integer.parseInt(list.get(2)[8])
                , "Monster", Integer.parseInt(list.get(2)[6]), Integer.parseInt(list.get(2)[5])
                , list.get(2)[3], list.get(2)[2], Integer.parseInt(list.get(2)[1]));
        Monster monster21 = new Monster(list.get(3)[0], list.get(3)[7], list.get(3)[4], Integer.parseInt(list.get(3)[8])
                , "Monster", Integer.parseInt(list.get(3)[6]), Integer.parseInt(list.get(3)[5])
                , list.get(3)[3], list.get(3)[2], Integer.parseInt(list.get(3)[1]));
        Monster monster22 = new Monster(list.get(4)[0], list.get(4)[7], list.get(4)[4], Integer.parseInt(list.get(4)[8])
                , "Monster", Integer.parseInt(list.get(4)[6]), Integer.parseInt(list.get(4)[5])
                , list.get(4)[3], list.get(4)[2], Integer.parseInt(list.get(4)[1]));
        Monster monster23 = new Monster(list.get(6)[0], list.get(6)[7], list.get(6)[4], Integer.parseInt(list.get(6)[8])
                , "Monster", Integer.parseInt(list.get(6)[6]), Integer.parseInt(list.get(6)[5])
                , list.get(6)[3], list.get(6)[2], Integer.parseInt(list.get(6)[1]));
        Monster monster24 = new Monster(list.get(10)[0], list.get(10)[7], list.get(10)[4], Integer.parseInt(list.get(10)[8])
                , "Monster", Integer.parseInt(list.get(10)[6]), Integer.parseInt(list.get(10)[5])
                , list.get(10)[3], list.get(10)[2], Integer.parseInt(list.get(10)[1]));
        Monster monster25 = new Monster(list.get(14)[0], list.get(14)[7], list.get(14)[4], Integer.parseInt(list.get(14)[8])
                , "Monster", Integer.parseInt(list.get(14)[6]), Integer.parseInt(list.get(14)[5])
                , list.get(14)[3], list.get(14)[2], Integer.parseInt(list.get(14)[1]));
        Monster monster26 = new Monster(list.get(16)[0], list.get(16)[7], list.get(16)[4], Integer.parseInt(list.get(16)[8])
                , "Monster", Integer.parseInt(list.get(16)[6]), Integer.parseInt(list.get(16)[5])
                , list.get(16)[3], list.get(16)[2], Integer.parseInt(list.get(16)[1]));
        Monster monster27 = new Monster(list.get(17)[0], list.get(17)[7], list.get(17)[4], Integer.parseInt(list.get(17)[8])
                , "Monster", Integer.parseInt(list.get(17)[6]), Integer.parseInt(list.get(17)[5])
                , list.get(17)[3], list.get(17)[2], Integer.parseInt(list.get(17)[1]));
        Monster monster28 = new Monster(list.get(19)[0], list.get(19)[7], list.get(19)[4], Integer.parseInt(list.get(19)[8])
                , "Monster", Integer.parseInt(list.get(19)[6]), Integer.parseInt(list.get(19)[5])
                , list.get(19)[3], list.get(19)[2], Integer.parseInt(list.get(19)[1]));
        Monster monster29 = new Monster(list.get(20)[0], list.get(20)[7], list.get(20)[4], Integer.parseInt(list.get(20)[8])
                , "Monster", Integer.parseInt(list.get(20)[6]), Integer.parseInt(list.get(20)[5])
                , list.get(20)[3], list.get(20)[2], Integer.parseInt(list.get(20)[1]));
        Monster monster30 = new Monster(list.get(23)[0], list.get(23)[7], list.get(23)[4], Integer.parseInt(list.get(23)[8])
                , ",Monster", Integer.parseInt(list.get(23)[6]), Integer.parseInt(list.get(23)[5])
                , list.get(23)[3], list.get(23)[2], Integer.parseInt(list.get(23)[1]));
        Monster monster31 = new Monster(list.get(27)[0], list.get(27)[7], list.get(27)[4], Integer.parseInt(list.get(27)[8])
                , "Monster", Integer.parseInt(list.get(27)[6]), Integer.parseInt(list.get(27)[5])
                , list.get(27)[3], list.get(27)[2], Integer.parseInt(list.get(27)[1]));
        Monster monster32 = new Monster(list.get(31)[0], list.get(31)[7], list.get(31)[4], Integer.parseInt(list.get(31)[8])
                , "Monster", Integer.parseInt(list.get(31)[6]), Integer.parseInt(list.get(31)[5])
                , list.get(31)[3], list.get(31)[2], Integer.parseInt(list.get(31)[1]));
        Monster monster33 = new Monster(list.get(32)[0], list.get(32)[7], list.get(32)[4], Integer.parseInt(list.get(32)[8])
                , "Monster", Integer.parseInt(list.get(32)[6]), Integer.parseInt(list.get(32)[5])
                , list.get(32)[3], list.get(32)[2], Integer.parseInt(list.get(32)[1]));
        Monster monster34 = new Monster(list.get(33)[0], list.get(33)[7], list.get(33)[4], Integer.parseInt(list.get(33)[8])
                , "Monster", Integer.parseInt(list.get(33)[6]), Integer.parseInt(list.get(33)[5])
                , list.get(33)[3], list.get(33)[2], Integer.parseInt(list.get(33)[1]));
        Monster monster35 = new Monster(list.get(34)[0], list.get(34)[7], list.get(34)[4], Integer.parseInt(list.get(34)[8])
                , "Monster", Integer.parseInt(list.get(34)[6]), Integer.parseInt(list.get(34)[5])
                , list.get(34)[3], list.get(34)[2], Integer.parseInt(list.get(34)[1]));
        Monster monster36 = new Monster(list.get(36)[0], list.get(36)[7], list.get(36)[4], Integer.parseInt(list.get(36)[8])
                , "Monster", Integer.parseInt(list.get(36)[6]), Integer.parseInt(list.get(36)[5])
                , list.get(36)[3], list.get(36)[2], Integer.parseInt(list.get(36)[1]));
        Monster monster37 = new Monster(list.get(37)[0], list.get(37)[7], list.get(37)[4], Integer.parseInt(list.get(37)[8])
                , "Monster", Integer.parseInt(list.get(37)[6]), Integer.parseInt(list.get(37)[5])
                , list.get(37)[3], list.get(37)[2], Integer.parseInt(list.get(37)[1]));
        Monster monster38 = new Monster(list.get(38)[0], list.get(38)[7], list.get(38)[4], Integer.parseInt(list.get(38)[8])
                , "Monster", Integer.parseInt(list.get(38)[6]), Integer.parseInt(list.get(38)[5])
                , list.get(38)[3], list.get(38)[2], Integer.parseInt(list.get(38)[1]));
        Monster monster39 = new Monster(list.get(39)[0], list.get(39)[7], list.get(39)[4], Integer.parseInt(list.get(39)[8])
                , "Monster", Integer.parseInt(list.get(39)[6]), Integer.parseInt(list.get(39)[5])
                , list.get(39)[3], list.get(39)[2], Integer.parseInt(list.get(39)[1]));
        Monster monster40 = new Monster(list.get(40)[0], list.get(40)[7], list.get(40)[4], Integer.parseInt(list.get(40)[8])
                , "Monster", Integer.parseInt(list.get(40)[6]), Integer.parseInt(list.get(40)[5])
                , list.get(40)[3], list.get(40)[2], Integer.parseInt(list.get(40)[1]));
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
        Spell spell12 = new Spell(list.get(21)[0], list.get(21)[3], list.get(21)[2], Integer.parseInt(list.get(21)[5])
                , list.get(21)[1], list.get(21)[4]);
        Spell spell13 = new Spell(list.get(22)[0], list.get(22)[3], list.get(22)[2], Integer.parseInt(list.get(22)[5])
                , list.get(22)[1], list.get(22)[4]);
        Spell spell14 = new Spell(list.get(23)[0], list.get(23)[3], list.get(23)[2], Integer.parseInt(list.get(23)[5])
                , list.get(23)[1], list.get(23)[4]);
        Spell spell15 = new Spell(list.get(26)[0], list.get(26)[3], list.get(26)[2], Integer.parseInt(list.get(26)[5])
                , list.get(26)[1], list.get(26)[4]);
        Spell spell16 = new Spell(list.get(27)[0], list.get(27)[3], list.get(27)[2], Integer.parseInt(list.get(27)[5])
                , list.get(27)[1], list.get(27)[4]);
        Spell spell17 = new Spell(list.get(29)[0], list.get(29)[3], list.get(29)[2], Integer.parseInt(list.get(29)[5])
                , list.get(29)[1], list.get(29)[4]);
        Spell spell18 = new Spell(list.get(30)[0], list.get(30)[3], list.get(30)[2], Integer.parseInt(list.get(30)[5])
                , list.get(30)[1], list.get(30)[4]);
        Spell spell19 = new Spell(list.get(31)[0], list.get(31)[3], list.get(31)[2], Integer.parseInt(list.get(31)[5])
                , list.get(31)[1], list.get(31)[4]);
        Spell spell20 = new Spell(list.get(32)[0], list.get(32)[3], list.get(32)[2], Integer.parseInt(list.get(32)[5])
                , list.get(32)[1], list.get(32)[4]);
        Spell spell21 = new Spell(list.get(34)[0], list.get(34)[3], list.get(34)[2], Integer.parseInt(list.get(34)[5])
                , list.get(34)[1], list.get(34)[4]);
        Spell spell22 = new Spell(list.get(35)[0], list.get(35)[3], list.get(35)[2], Integer.parseInt(list.get(35)[5])
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

        Trap trap8 = new Trap(list.get(7)[0], list.get(7)[3], list.get(7)[2], Integer.parseInt(list.get(7)[5])
                , list.get(7)[1], list.get(7)[4]);
        Trap trap9 = new Trap(list.get(8)[0], list.get(8)[3], list.get(8)[2], Integer.parseInt(list.get(8)[5])
                , list.get(8)[1], list.get(8)[4]);
        Trap trap10 = new Trap(list.get(11)[0], list.get(11)[3], list.get(11)[2], Integer.parseInt(list.get(11)[5])
                , list.get(11)[1], list.get(11)[4]);
        Trap trap11 = new Trap(list.get(12)[0], list.get(12)[3], list.get(12)[2], Integer.parseInt(list.get(12)[5])
                , list.get(12)[1], list.get(12)[4]);
    }

}