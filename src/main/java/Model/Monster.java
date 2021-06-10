package Model;

import java.util.HashMap;

public class Monster extends Card {
    public static HashMap<String, Monster> monsters = new HashMap<>();
    private int defensePowerMonster;
    private int attackPowerMonster;
    private int levelMonster;
    private String monsterType;
    private String attribute;
    private boolean hasSpecialSummonMonster;

    public Monster(String name, String description, String cardType, int price, String category, int defensePower, int attackPower
            , String monsterType, String attribute, int level, boolean hasSpecialSummon) {
        super(name, description, cardType, price, category);
        setDefensePower(defensePower);
        setAttackPower(attackPower);
        setMonsterType(monsterType);
        setAttribute(attribute);
        setLevel(level);
        setHasSpecialSummon(hasSpecialSummon);
        monsters.put(name, this);
    }

    public int getDefensePower() {
        return defensePowerMonster;
    }

    public int getAttackPower() {
        return attackPowerMonster;
    }

    public int getLevel() {
        return levelMonster;
    }

    public void setMonsterType(String monsterType) {
        this.monsterType = monsterType;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getMonsterType() {
        return monsterType;
    }

    public void setDefensePower(int defensePower) {
        this.defensePowerMonster = defensePower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPowerMonster = attackPower;
    }

    public void setLevel(int level) {
        this.levelMonster = level;
    }

    public void setHasSpecialSummon(boolean hasSpecialSummon) {
        this.hasSpecialSummonMonster = hasSpecialSummon;
    }

    public boolean isHasSpecialSummon() {
        return hasSpecialSummonMonster;
    }

    public static Monster getMonsterByName(String name) {
        return monsters.get(name);
    }

}
