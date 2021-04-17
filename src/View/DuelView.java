package View;

import java.util.regex.Matcher;

public class DuelView extends StartDuelView{
    public void selectFirstPlayer(){}

    @Override
    public void run() {
        super.run();
    }
    protected void deselect(Matcher matcher){

    }
    public void nextPhase(){}
    protected void activateEffect(){}
    protected void showGraveyard(){

    }
    protected void showCard(){

    }
    protected void showSelectedCard(){

    }
    public void surrender(){}
    protected void select(Matcher matcher){
    }
    protected  void selectMonster(Matcher matcher){}
    protected void selectOpponentMonster(Matcher matcher){}
    protected void selectSpell(Matcher matcher){}
    protected void selectOpponentSpell(Matcher matcher){}
    protected void selectField(Matcher matcher){}
    protected void selectOpponentField(Matcher matcher){}
    protected void selectHand(Matcher matcher){}
    protected Matcher getCommandMatcher(String command,String regex){
        return null;
    }

}