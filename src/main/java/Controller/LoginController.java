package Controller;

import Model.Monster;
import Model.Spell;
import Model.Trap;
import Model.User;
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
            user=newUser;
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
            createCard();
            return "user logged in successfully!";
        }
    }

    public static void createCard() {
        createMonster();
        createSpell();
        createTrap();
    }

    public static void createMonster() {
        List<String[]> list = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader("C:\\Users\\ae\\IdeaProjects\\Monster.csv"))) {
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
        Monster monster1 = new Monster(list.get(24)[0], list.get(24)[7], list.get(24)[4], Integer.parseInt(list.get(24)[8])
                , "Monster", Integer.parseInt(list.get(24)[6]), Integer.parseInt(list.get(24)[5])
                , list.get(24)[3], list.get(24)[2], Integer.parseInt(list.get(24)[1]));
        Monster monster2 = new Monster(list.get(26)[0], list.get(26)[7], list.get(26)[4], Integer.parseInt(list.get(26)[8])
                , "Monster", Integer.parseInt(list.get(26)[6]), Integer.parseInt(list.get(26)[5])
                , list.get(26)[3], list.get(26)[2], Integer.parseInt(list.get(26)[1]));
        Monster monster3 = new Monster(list.get(22)[0], list.get(22)[7], list.get(22)[4], Integer.parseInt(list.get(22)[8])
                , "Monster", Integer.parseInt(list.get(22)[6]), Integer.parseInt(list.get(22)[5])
                , list.get(22)[3], list.get(22)[2], Integer.parseInt(list.get(22)[1]));
        Monster monster4 = new Monster(list.get(8)[0], list.get(8)[7], list.get(8)[4], Integer.parseInt(list.get(8)[8])
                , "Monster", Integer.parseInt(list.get(8)[6]), Integer.parseInt(list.get(8)[5])
                , list.get(8)[3], list.get(8)[2], Integer.parseInt(list.get(8)[1]));
        Monster monster5 = new Monster(list.get(21)[0], list.get(21)[7], list.get(21)[4], Integer.parseInt(list.get(21)[8])
                , "Monster", Integer.parseInt(list.get(21)[6]), Integer.parseInt(list.get(21)[5])
                , list.get(21)[3], list.get(21)[2], Integer.parseInt(list.get(21)[1]));
        Monster monster6 = new Monster(list.get(1)[0], list.get(1)[7], list.get(1)[4], Integer.parseInt(list.get(1)[8])
                , "Monster", Integer.parseInt(list.get(1)[6]), Integer.parseInt(list.get(1)[5])
                , list.get(1)[3], list.get(1)[2], Integer.parseInt(list.get(1)[1]));
        Monster monster7 = new Monster(list.get(11)[0], list.get(11)[7], list.get(11)[4], Integer.parseInt(list.get(11)[8])
                , "Monster", Integer.parseInt(list.get(11)[6]), Integer.parseInt(list.get(11)[5])
                , list.get(11)[3], list.get(11)[2], Integer.parseInt(list.get(11)[1]));
        Monster monster8 = new Monster(list.get(12)[0], list.get(12)[7], list.get(12)[4], Integer.parseInt(list.get(12)[8])
                , "Monster", Integer.parseInt(list.get(12)[6]), Integer.parseInt(list.get(12)[5])
                , list.get(12)[3], list.get(12)[2], Integer.parseInt(list.get(12)[1]));
        Monster monster9 = new Monster(list.get(15)[0], list.get(15)[7], list.get(15)[4], Integer.parseInt(list.get(15)[8])
                , "Monster", Integer.parseInt(list.get(15)[6]), Integer.parseInt(list.get(15)[5])
                , list.get(15)[3], list.get(15)[2], Integer.parseInt(list.get(15)[1]));
        Monster monster10 = new Monster(list.get(25)[0], list.get(25)[7], list.get(25)[4], Integer.parseInt(list.get(25)[8])
                , ",Monster", Integer.parseInt(list.get(25)[6]), Integer.parseInt(list.get(25)[5])
                , list.get(25)[3], list.get(25)[2], Integer.parseInt(list.get(25)[1]));
        Monster monster11 = new Monster(list.get(13)[0], list.get(13)[7], list.get(13)[4], Integer.parseInt(list.get(13)[8])
                , "Monster", Integer.parseInt(list.get(13)[6]), Integer.parseInt(list.get(13)[5])
                , list.get(13)[3], list.get(13)[2], Integer.parseInt(list.get(13)[1]));
        Monster monster12 = new Monster(list.get(35)[0], list.get(35)[7], list.get(35)[4], Integer.parseInt(list.get(35)[8])
                , "Monster", Integer.parseInt(list.get(35)[6]), Integer.parseInt(list.get(35)[5])
                , list.get(35)[3], list.get(35)[2], Integer.parseInt(list.get(35)[1]));
        Monster monster13 = new Monster(list.get(5)[0], list.get(5)[7], list.get(5)[4], Integer.parseInt(list.get(5)[8])
                , "Monster", Integer.parseInt(list.get(5)[6]), Integer.parseInt(list.get(5)[5])
                , list.get(5)[3], list.get(5)[2], Integer.parseInt(list.get(5)[1]));
        Monster monster14 = new Monster(list.get(18)[0], list.get(18)[7], list.get(18)[4], Integer.parseInt(list.get(18)[8])
                , "Monster", Integer.parseInt(list.get(18)[6]), Integer.parseInt(list.get(18)[5])
                , list.get(18)[3], list.get(18)[2], Integer.parseInt(list.get(18)[1]));
        Monster monster15 = new Monster(list.get(30)[0], list.get(30)[7], list.get(30)[4], Integer.parseInt(list.get(30)[8])
                , "Monster", Integer.parseInt(list.get(30)[6]), Integer.parseInt(list.get(30)[5])
                , list.get(30)[3], list.get(30)[2], Integer.parseInt(list.get(30)[1]));
        Monster monster16 = new Monster(list.get(29)[0], list.get(29)[7], list.get(29)[4], Integer.parseInt(list.get(29)[8])
                , "Monster", Integer.parseInt(list.get(29)[6]), Integer.parseInt(list.get(29)[5])
                , list.get(29)[3], list.get(29)[2], Integer.parseInt(list.get(29)[1]));
        Monster monster17 = new Monster(list.get(9)[0], list.get(9)[7], list.get(9)[4], Integer.parseInt(list.get(9)[8])
                , "Monster", Integer.parseInt(list.get(9)[6]), Integer.parseInt(list.get(9)[5])
                , list.get(9)[3], list.get(9)[2], Integer.parseInt(list.get(9)[1]));
        Monster monster18 = new Monster(list.get(41)[0], list.get(41)[7], list.get(41)[4], Integer.parseInt(list.get(41)[8])
                , "Monster", Integer.parseInt(list.get(41)[6]), Integer.parseInt(list.get(41)[5])
                , list.get(41)[3], list.get(41)[2], Integer.parseInt(list.get(41)[1]));
        Monster monster19 = new Monster(list.get(7)[0], list.get(7)[7], list.get(7)[4], Integer.parseInt(list.get(7)[8])
                , "Monster", Integer.parseInt(list.get(7)[6]), Integer.parseInt(list.get(7)[5])
                , list.get(7)[3], list.get(7)[2], Integer.parseInt(list.get(7)[1]));
    }

    public static void createSpell() {
        List<String[]> list = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader("C:\\Users\\ae\\IdeaProjects\\SpellTrap.csv"))) {
            list = reader.readAll();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }
        // cardType : icon(property)
        Spell spell = new Spell(list.get(13)[0],list.get(13)[3],list.get(13)[2],Integer.parseInt(list.get(13)[5])
        ,list.get(13)[1],list.get(13)[4]);
        Spell spell1 = new Spell("Terraforming", "Add 1 Field Spell from your Deck to your hand."
                , "Normal", 2500, "spell", "Limited");
        Spell spell2 = new Spell("Pot of Greed", "Draw 2 cards."
                , "Normal", 2500, "spell", "Limited");
        Spell spell3 = new Spell("Raigeki", "Destroy all monsters your opponent controls."
                , "Normal", 2500, "spell", "Limited");
        Spell spell4 = new Spell("Raigeki", "Destroy all monsters your opponent controls."
                , "Normal", 2500, "spell", "Limited");
        Spell spell5 = new Spell("Change of Heart", "Target 1 monster your opponent controls;" +
                " take control of it until the End Phase."
                , "Normal", 2500, "spell", "Limited");
        Spell spell6 = new Spell("Dark Hole", "Destroy all monsters on the field."
                , "Normal", 2500, "spell", "Unlimited");
        Spell spell7 = new Spell("Swords of Revealing Light", "After this card's activation," +
                " it remains on the field, but destroy it during the End Phase of your opponent's 3rd turn." +
                " When this card is activated: If your opponent controls a face-down monster, flip all monsters they control face-up." +
                " While this card is face-up on the field, your opponent's monsters cannot declare an attack."
                , "Normal", 2500, "spell", "Unlimited");
        Spell spell8 = new Spell("Harpie's Feather Duster", "Destroy all Spells and Traps your opponent controls."
                , "Normal", 2500, "spell", "Limited");
        Spell spell9 = new Spell("Twin Twisters", "Discard 1 card, then target up to 2 Spells/Traps on the field;" +
                " destroy them."
                , "Quick-play", 3500, "spell", "Unlimited");
        Spell spell10 = new Spell("Forest", "All Insect, Beast, Plant, and Beast-Warrior " +
                "monsters on the field gain 200 ATK/DEF."
                , "Field", 4300, "spell", "Unlimited");
        Spell spell11 = new Spell("United We Stand", "The equipped monster gains 800 ATK/DEF for each" +
                " face-up monster you control."
                , "Equip", 4300, "spell", "Unlimited");
    }

    public static void createTrap() {
        // cardType : icon(property)
        Trap trap = new Trap("Trap Hole", "When your opponent Normal or Flip Summons 1 monster with 1000" +
                " or more ATK: Target that monster; destroy that target."
                , "Normal", 2000, "trap", "Unlimited");
        Trap trap1 = new Trap("Mirror Force", "When an opponent's monster declares an attack:" +
                " Destroy all your opponent's Attack Position monsters."
                , "Normal", 2000, "trap", "Unlimited");
        Trap trap2 = new Trap("Magic Cylinder", "When an opponent's monster declares an attack:" +
                " Target the attacking monster; negate the attack, and if you do," +
                " inflict damage to your opponent equal to its ATK."
                , "Normal", 2000, "trap", "Unlimited");
        Trap trap3 = new Trap("Mind Crush", "Declare 1 card name; if that card is in your opponent's hand," +
                " they must discard all copies of it, otherwise you discard 1 random card."
                , "Normal", 2000, "trap", "Unlimited");
        Trap trap4 = new Trap("Torrential Tribute", "When a monster(s) is Summoned: Destroy all monsters on the field."
                , "Normal", 2000, "trap", "Unlimited");
        Trap trap5 = new Trap("Time Seal", "Skip the Draw Phase of your opponent's next turn."
                , "Normal", 2000, "trap", "Limited");
        Trap trap6 = new Trap("Magic Jammer", "When a Spell Card is activated: Discard 1 card;" +
                " negate the activation, and if you do, destroy it."
                , "Counter", 3000, "trap", "Unlimited");
        Trap trap7 = new Trap("Call of The Haunted", "Activate this card by targeting 1 monster in your GY;" +
                " Special Summon that target in Attack Position. When this card leaves the field, destroy that monster." +
                " When that monster is destroyed, destroy this card."
                , "Continuous", 3500, "trap", "Unlimited");

    }

}