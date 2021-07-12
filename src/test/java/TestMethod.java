import Controller.LoginController;
import Model.Card;
import Model.User;
import View.LoginView;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class TestMethod {
    @Test
    public void TestForCreateUserController() {
        new User("mohammad", "mmd", "123456");
        LoginController loginController = new LoginController();
        Assert.assertEquals("user with username mohammad already exists", loginController.createUser("mohammad"
                , "mmd", "123456"));
        Assert.assertEquals("user with nickname mmd already exists", loginController.createUser("amir",
                "mmd", "123456"));
        Assert.assertEquals("user created successfully!", loginController.createUser("amir",
                "mohammad", "324247"));
    }

    @Test
    public void TestForLoginController() {
        LoginController loginController = new LoginController();
        Assert.assertEquals("Username and password didn't match!", loginController.login("mmd",
                "123456"));
        new User("mohammad", "mmd", "3241");
        Assert.assertEquals("Username and password didn't match!", loginController.login("mohammad",
                "2222"));
        Assert.assertEquals("user logged in successfully!", loginController.login("mohammad", "3241"));
    }

    @Test
    public void TestForCreateCardLoginController() {
        //LoginController.createCard();
        Assert.assertNotNull(Card.getCardByName("Trap Hole"));
    }

    @Test
    public void TestForLoginView() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        InputStream SysInBackUp = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("menu showCurrent\nmenu show-current\nmenu enter Menu\nmenu exit"
                .getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
        new LoginView().run();
        Assert.assertEquals("invalid command" + System.lineSeparator() + "Login Menu"
                + System.lineSeparator() + "menu navigation is not possible" + System.lineSeparator(), outContent.toString());
        System.setIn(SysInBackUp);
    }

    @Test
    public void TestForCreateUserParameter() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        InputStream SysInBackUp = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("user create -y mmd -p 123 -n mmd\nmenu exit"
                .getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
        new LoginView().run();
        Assert.assertEquals("invalid command" + System.lineSeparator(), outContent.toString());
        System.setIn(SysInBackUp);
    }

    @Test
    public void TestForCreateUserLoginView() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        InputStream SysInBackUp = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream(("user create -u mmd -p 123 -n mmd" +
                "\nuser create -u mmd -n mmd -p 123\n" + "user create -p 123 -u ali -n ali\n"
                + "user create -n mmd -p 123 -u amir\n"
                + "user create -p 123456 -n reza -u reza\n"
                + "user create -n mmd -u ali -p 1380\n"
                + "user create -n mmd -u mmd -u 123\n" + "menu exit")
                .getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
        new LoginView().run();
        Assert.assertEquals("user created successfully!" + System.lineSeparator()
                + "user with username mmd already exists" + System.lineSeparator()
                + "user created successfully!" + System.lineSeparator()
                + "user with nickname mmd already exists" + System.lineSeparator()
                + "user created successfully!" + System.lineSeparator()
                + "user with username ali already exists" + System.lineSeparator()
                + "invalid command" + System.lineSeparator(), outContent.toString());
        System.setIn(SysInBackUp);
    }

    @Test
    public void TestForLoginInLoginView() {
        new User("mmd", "mmd", "123");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        InputStream SysInBackUp = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream(("user login -u mmd -p 123 -n mmd" +
                "\nuser login -u mmd -p 1234\n" + "user login -u mmd -p 123\n" + "menu exit" + "\nmenu exit")
                .getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
        new LoginView().run();
        Assert.assertEquals("invalid command" + System.lineSeparator() +
                "Username and password didn't match!" + System.lineSeparator() +
                "user logged in successfully!" + System.lineSeparator(), outContent.toString());
        System.setIn(SysInBackUp);
    }

    @Test
    public void TestForLoginErrorsInLoginView() {
        new User("mmd", "mmd", "123");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        InputStream SysInBackUp = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream(("user login -u mmd -u mmd\n" +
                "user login -p 123 -m as\n"
                + "user login -p mmd -p ali" +
                "\nuser login -p 123 -u mmd" + "\nmenu exit" + "\nmenu exit")
                .getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
        new LoginView().run();
        Assert.assertEquals("invalid command" + System.lineSeparator() +
                "invalid command" + System.lineSeparator() +
                "invalid command" + System.lineSeparator() +
                "user logged in successfully!" + System.lineSeparator(), outContent.toString());
        System.setIn(SysInBackUp);
    }
}
