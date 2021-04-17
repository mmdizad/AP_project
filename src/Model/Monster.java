package Model;

public class Monster extends Card{
    private int defensePower;
    private int attackPower;
    private int level;

    public Monster(String name,String description,String type,int price,String category,int defensePower,int attackPower,int level){
        super(name,description,type,price,category);
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
