package Controller;

import Model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class ScoreBoardController extends LoginController {

    private static ScoreBoardController scoreBoardController = new ScoreBoardController();

    private ScoreBoardController() {

    }

    public static ScoreBoardController getInstance() {
        return scoreBoardController;
    }

    public String scoreBoard() {
        try {
            dataOutputStream.writeUTF("scoreBoard");
            dataOutputStream.flush();
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}