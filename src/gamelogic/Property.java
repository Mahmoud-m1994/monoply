package gamelogic;

import java.util.ArrayList;

/**
 * The Property class is used to manage properties
 * @author Håkon, Espen, Mathias
 */
public class Property {
    private final double MORTGAGE_FACTOR = 0.75;
    private final double RENT_FACTOR     = 0.3;
    private final double HOUSE_FACTOR    = 0.75;
    private final double HOTEL_FACTOR    = 1.5;

    private ArrayList<Building> buildings = new ArrayList<>();

    private House house;
    private Hotel hotel;

    private final String name;
    private final String color;

    private Player owner;

    private final int rent;
    private final int price;
    private final int mortgage;
    private final int position;

    public Property(int position, String name, int price, String color) {
        this.position = position;
        this.name     = name;
        this.price    = price;
        this.color    = color;

        int housePrice = (int) (price * HOUSE_FACTOR);
        int hotelPrice = (int) (price * HOTEL_FACTOR);

        this.house = new House(housePrice);
        this.hotel = new Hotel(hotelPrice);

        this.rent     = (int) (price * RENT_FACTOR);
        this.mortgage = (int) (price * MORTGAGE_FACTOR);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Player getOwner() {
        return owner;
    }

    public String getColor() {
        return color;
    }

    public int getHotelPrice(){
        return this.hotel.getPrice();
    }

    public int getHousePrice(){
        return this.house.getPrice();
    }

    public int getMortgage(){
        return this.mortgage;
    }

    public int getRent() {
        getCurrentRent();
        return this.rent;
    }

    public int getPosition() {
        return position;
    }

    public int getCurrentRent(){
        int houseRent = this.rent * getHouseCount();
        int hotelRent = (5 * this.rent) * getHotelCount();

        return (this.rent + houseRent + hotelRent);
    }

    /**
     * This method attempts to add house to buildings array
     * @return boolean based on the result of the method
     */
    public boolean addHouse(){
        if(getHouseCount() >= 4)
            return false;

        buildings.add(house);
        return true;
    }

    /**
     * This method attempts to sell house from buildings array
     * @return boolean based on the result of the method
     */
    public boolean sellHouse(){
        if(getHouseCount() < 1)
            return false;

        buildings.remove(house);
        return true;
    }

    /**
     * This method attempts to add hotel to buildings array
     * @return boolean based on the result of the method
     */
    public boolean addHotel(){
        if(getHotelCount() >= 1)
            return false;

        buildings.add(hotel);
        return true;
    }
    /**
     * This method attempts to sell hotel to buildings array
     * @return boolean based on the result of the method
     */
    public boolean sellHotel(){
        if(getHotelCount() < 1)
            return false;

        buildings.remove(hotel);
        return true;
    }
    /**
     * This method counts houses in buildings array
     * The method used as a condition in addHouse method
     * @return Integer based on the result of the method
     */
    public int getHouseCount() {
        int count = 0;

        for (Building building : buildings)
            if (building instanceof House)
                count++;

        return count;
    }

    /**
     * This method counts hotels in buildings array
     * The method used as a condition in addHotel method
     * @return Integer based on the result of the method
     */
    public int getHotelCount(){
        int count = 0;

        for (Building building : buildings)
            if (building instanceof Hotel)
                count++;

        return count;
    }

    /**
     * The method used to by propery
     * @param player is the player that buying the property
     * @return boolean based on the result of the method and Bank.transaction
     */
    public boolean GLbuyProperty(Player player) {

        //Checks that property is not owned by someone, and that the player has enough money
        if(this.owner != null || player.getBalance() <= this.price)
            return false;

        player.transaction(-this.price);
        setOwner(player);

        return true;
    }

    /**
     * The method used in Databse, BoardController classed to set owner of the property
     * @param owner is the player that has bought property
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setUpgrades (int i) {
        int counter = 0;
        while(counter < i && counter < 4) {
            addHouse();
            counter++;
        }
        if (i == 4) addHotel();
    }

    /**
     * The method used when player falls in another players property field
     * @param player is the player that pay rent to another player
     * @return boolean based on Bank.transaction ,the result of the method.
     */
    public boolean payRent(Player player) {

        //Checks if property is owned by someone, and that you are not the owner
        if (this.owner == null || this.owner == player)
            return false;

        Bank.transaction(this.owner, player, this.rent);
        return true;

    }

}