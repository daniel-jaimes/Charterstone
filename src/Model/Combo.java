package Model;

public class Combo {
    private Character resource;
    private int quantity;

    public Combo(Character resource, int quantity) {
        this.resource = resource;
        this.quantity = quantity;
    }
    //GETTERS
    public Character getResource() {
        return resource;
    }
    public int getQuantity() {
        return quantity;
    }
}
