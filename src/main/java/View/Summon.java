package View;

import java.util.regex.Matcher;

public interface Summon {
    public void summon(Matcher matcher);

    public void flipSummon(Matcher matcher);

    public void specialSummon(Matcher matcher);

    public void ritualSummon();

}

