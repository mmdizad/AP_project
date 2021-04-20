package Model;

public class Trap extends Card {
    private String status;

    public Trap(String name, String description, String cardType, int price, String category, String status) {
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