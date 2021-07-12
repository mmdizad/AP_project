import Controller.LoginController;
import View.LoginView;

public class Main {
    public static void main(String[] args) {
        LoginController.initializeNetwork();
        LoginView loginView = new LoginView();
        loginView.run();
    }
}
