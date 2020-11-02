package gamelogic;

/**
 * This class is used to create a building
 * @author Håkon
 */
public class Building {
    private int price;

    public Building(int price){
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
