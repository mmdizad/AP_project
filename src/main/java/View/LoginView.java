package View;

import Controller.LoginController;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginView extends Menu {
    private boolean invalidCommand;

    public void run() {
        //LoginController.createFolders();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            invalidCommand = true;
            String input = scanner.nextLine();
            isCreateUserParametersValid(getCommandMatcher(input, "^user create (\\S+) (\\S+) " +
                    "(\\S+) (\\S+) (\\S+) (\\S+)$"));
            isLoginParametersValid(getCommandMatcher(input, "^user login (\\S+) (\\S+) (\\S+)" +
                    " (\\S+)$"), scanner);
            showMenu(getCommandMatcher(input, "^menu show-current$"));
            enterMenu(getCommandMatcher(input, "^menu enter \\S+$"));
            if (input.equals("menu exit")) {
                break;
            }
            if (invalidCommand) {
                System.out.println("invalid command");
            }
        }
    }

    public void enterMenu(Matcher matcher) {
        if (matcher.find()) {
            invalidCommand = false;
            System.out.println("menu navigation is not possible");
        }
    }

    public void createUser(String username, String nickname, String password) {
        LoginController loginController = new LoginController();
        System.out.println(loginController.createUser(username, nickname, password));
    }

    public void login(String username, String password, Scanner scanner) {
        LoginController loginController = new LoginController();
        String response = loginController.login(username, password);
        if (!response.equals("Username and password didn't match!") && !response.equals("An error occurred.")
                && !response.equals("")) {
            System.out.println("login successfully");
            MainMenu mainMenu = new MainMenu();
            mainMenu.run(scanner);
        } else {
            System.out.println(response);
        }
    }

    public Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    public void showMenu(Matcher matcher) {
        if (matcher.find()) {
            invalidCommand = false;
            System.out.println("Login Menu");
        }
    }

    public void isCreateUserParametersValid(Matcher matcher) {
        if (matcher.find()) {
            invalidCommand = false;
            int validParameter = 1;
            ArrayList<String> parameters = new ArrayList<>();
            parameters.add(matcher.group(1));
            parameters.add(matcher.group(3));
            parameters.add(matcher.group(5));
            for (String parameter : parameters) {
                if (!parameter.equals("-p") && !parameter.equals("-u")
                        && !parameter.equals("-n")) {
                    System.out.println("invalid command");
                    validParameter = 0;
                    break;
                }
            }
            if (validParameter == 1) {
                isCreateUserParametersUnique(matcher, parameters);
            }
        }
    }

    public void isCreateUserParametersUnique(Matcher matcher, ArrayList<String> parameters) {
        String parameter1 = parameters.get(0);
        String parameter2 = parameters.get(1);
        String parameter3 = parameters.get(2);
        if (parameter1.equals("-p") && parameter2.equals("-u")
                && parameter3.equals("-n")) {
            createUser(matcher.group(4), matcher.group(6), matcher.group(2));
        } else if (parameter1.equals("-p") && parameter2.equals("-n")
                && parameter3.equals("-u")) {
            createUser(matcher.group(6), matcher.group(4), matcher.group(2));
        } else if (parameter1.equals("-u") && parameter2.equals("-p")
                && parameter3.equals("-n")) {
            createUser(matcher.group(2), matcher.group(6), matcher.group(4));
        } else if (parameter1.equals("-u") && parameter2.equals("-n")
                && parameter3.equals("-p")) {
            createUser(matcher.group(2), matcher.group(4), matcher.group(6));
        } else if (parameter1.equals("-n") && parameter2.equals("-p")
                && parameter3.equals("-u")) {
            createUser(matcher.group(6), matcher.group(2), matcher.group(4));
        } else if (parameter1.equals("-n") && parameter2.equals("-u")
                && parameter3.equals("-p")) {
            createUser(matcher.group(4), matcher.group(2), matcher.group(6));
        } else {
            System.out.println("invalid command");
        }
    }

    public void isLoginParametersValid(Matcher matcher, Scanner scanner) {
        if (matcher.find()) {
            invalidCommand = false;
            int validParameter = 1;
            ArrayList<String> parameters = new ArrayList<>();
            parameters.add(matcher.group(1));
            parameters.add(matcher.group(3));
            for (String parameter : parameters) {
                if (!parameter.equals("-p") && !parameter.equals("-u")) {
                    System.out.println("invalid command");
                    validParameter = 0;
                    break;
                }
            }
            if (validParameter == 1) {
                isLoginParametersUnique(matcher, parameters, scanner);
            }
        }
    }

    public void isLoginParametersUnique(Matcher matcher, ArrayList<String> parameters, Scanner scanner) {
        String parameter1 = parameters.get(0);
        String parameter2 = parameters.get(1);
        if (parameter1.equals("-p") && parameter2.equals("-u")) {
            login(matcher.group(4), matcher.group(2), scanner);
        } else if (parameter1.equals("-u") && parameter2.equals("-p")) {
            login(matcher.group(2), matcher.group(4), scanner);
        } else {
            System.out.println("invalid command");
        }
    }
}