package View;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuelView {

    public void run(Scanner scanner) {

    }

    public void selectFirstPlayer() {

    }

    protected void deselect(Matcher matcher) {

    }

    public void nextPhase() {
    }

    protected void activateEffect() {
    }

    protected void showGraveyard() {

    }

    protected void showCard() {

    }

    protected void showSelectedCard() {

    }

    public void surrender() {
    }

    protected void select(Matcher matcher) {
    }

    protected void selectMonster(Matcher matcher) {
    }

    protected void selectOpponentMonster(Matcher matcher) {
    }

    protected void selectSpellOrTrap(Matcher matcher) {
    }

    protected void selectOpponentSpell(Matcher matcher) {
    }

    protected void selectField(Matcher matcher) {
    }

    protected void selectOpponentField(Matcher matcher) {
    }

    protected void selectHand(Matcher matcher) {
    }

    protected Matcher getCommandMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        return matcher;
    }

}