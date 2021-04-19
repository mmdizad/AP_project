package Model;

public class Monster extends Card {
    private int defensePower;
    private int attackPower;
    private int level;
    private String monsterType;
    private String attribute;

    public Monster(String name, String description, String cardType, int price, String category, int defensePower, int attackPower
            , String monsterType, String attribute, int level) {
        super(name, description, cardType, price, category);
        setDefensePower(defensePower);
        setAttackPower(attackPower);
        setMonsterType(monsterType);
        setAttribute(attribute);
        setLevel(level);
    }

    public int getDefensePower() {
        return defensePower;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getLevel() {
        return level;
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
        this.defensePower = defensePower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
