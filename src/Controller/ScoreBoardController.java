package Controller;

import java.util.ArrayList;

public class ScoreBoardController extends LoginController {

    private static ScoreBoardController scoreBoardController= new ScoreBoardController();

    private ScoreBoardController(){

    }

    public static ScoreBoardController getInstance(){
        return scoreBoardController;
    }

    public ArrayList<String> scoreBoard(){

    }

}