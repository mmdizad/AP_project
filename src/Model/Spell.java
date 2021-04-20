package Model;

public class Spell extends Card {
    private String status;

    public Spell(String name, String description, String cardType, int price, String category, String status) {
        super(name, description, cardType, price, category);
        setStatus(status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}