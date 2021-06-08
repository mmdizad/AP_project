import Controller.LoginAndSignUpController;
import Model.Card;
import Model.User;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TestMethod {
    @Test
    public void TestForCreateUserController() {
        new User("mohammad", "mmd", "123456");
        LoginAndSignUpController loginAndSignUpController = new LoginAndSignUpController();
        Assert.assertEquals("user with username mohammad already exists", loginAndSignUpController.createUser("mohammad"
                , "mmd", "123456"));
        Assert.assertEquals("user with nickname mmd already exists", loginAndSignUpController.createUser("amir",
                "mmd", "123456"));
        Assert.assertEquals("user created successfully!", loginAndSignUpController.createUser("amir",
                "mohammad", "324247"));
    }

    @Test
    public void TestForLoginController() {
        LoginAndSignUpController loginAndSignUpController = new LoginAndSignUpController();
        Assert.assertEquals("Username and password didn't match!", loginAndSignUpController.login("mmd",
                "123456"));
        new User("mohammad", "mmd", "3241");
        Assert.assertEquals("Username and password didn't match!", loginAndSignUpController.login("mohammad",
                "2222"));
        Assert.assertEquals("user logged in successfully!", loginAndSignUpController.login("mohammad", "3241"));
    }

    @Test
    public void TestForCreateCardLoginController() {
        LoginAndSignUpController.createCard();
        Assert.assertNotNull(Card.getCardByName("Trap Hole"));
    }

    @Test
    public void TestForLoginView() throws IOException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        InputStream SysInBackUp = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("menu showCurrent\nmenu show-current\nmenu enter Menu\nmenu exit"
                .getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
//        new LoginView().run();
        Assert.assertEquals("invalid command" + System.lineSeparator() + "Login Menu"
                + System.lineSeparator() + "menu navigation is not possible" + System.lineSeparator(), outContent.toString());
        System.setIn(SysInBackUp);
    }

    @Test
    public void TestForCreateUserParameter() throws IOException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        InputStream SysInBackUp = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("user create -y mmd -p 123 -n mmd\nmenu exit"
                .getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
//        new LoginView().run();
        Assert.assertEquals("invalid command" + System.lineSeparator(), outContent.toString());
        System.setIn(SysInBackUp);
    }

    @Test
    public void TestForCreateUserLoginView() throws IOException {
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
//        new LoginView().run();
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
    public void TestForLoginInLoginView() throws IOException {
        new User("mmd", "mmd", "123");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        InputStream SysInBackUp = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream(("user login -u mmd -p 123 -n mmd" +
                "\nuser login -u mmd -p 1234\n" + "user login -u mmd -p 123\n" + "menu exit" + "\nmenu exit")
                .getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
//        new LoginView().run();
        Assert.assertEquals("invalid command" + System.lineSeparator() +
                "Username and password didn't match!" + System.lineSeparator() +
                "user logged in successfully!" + System.lineSeparator(), outContent.toString());
        System.setIn(SysInBackUp);
    }

    @Test
    public void TestForLoginErrorsInLoginView() throws IOException {
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
//        new LoginView().run();
        Assert.assertEquals("invalid command" + System.lineSeparator() +
                "invalid command" + System.lineSeparator() +
                "invalid command" + System.lineSeparator() +
                "user logged in successfully!" + System.lineSeparator(), outContent.toString());
        System.setIn(SysInBackUp);
    }
}
