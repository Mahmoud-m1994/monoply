package gamelogic;

import userinterface.ControllerMediator;

/**
 * The Upgrade_class is used to create Upgrade..
 * Upgrade has property,building and level
 * @author Espen, Mathias
 */
public class Upgrade {
    private final double SELL_FACTOR = 0.75;
    private final int MAX = 6;

    private int level = 0;
    private int price;
    private double faktor;

    private Property property;
    private Building building;

    /**
     *
     * @param property is the property that will upgrade
     * @param building is the building that will upgrade
     * @param level is the start level of the upgrade
     */
    public Upgrade(Property property, Building building, int level){
        this.property = property;
        this.building = building;
        this.level = level;
    }

    public void setLevel(int level) {
        if(level > MAX)
            return;

        this.level = level;
    }

    public int getLevel(){ return this.level; }


    public int getPrice() {
        return building.getPrice();
    }

}