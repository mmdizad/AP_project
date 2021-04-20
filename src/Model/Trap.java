package Model;

import java.util.HashMap;

public class Trap extends Card {
    private String status;
    public static HashMap<String,Trap> traps = new HashMap<>();

    public Trap(String name, String description, String cardType, int price, String category, String status) {
        super(name, description, cardType, price, category);
        setStatus(status);
        traps.put(name,this);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static Trap getTrapByName(String name){
        return traps.get(name);
    }

}