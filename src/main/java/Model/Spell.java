package Model;

import java.util.HashMap;

public class Spell extends Card {
    private String status;
    public static HashMap<String, Spell> spells = new HashMap<>();

    public Spell(String name, String description, String cardType, int price, String category, String status) {
        super(name, description, cardType, price, category);
        setStatus(status);
        spells.put(name, this);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static Spell getSpellByName(String name) {
        return spells.get(name);
    }

}