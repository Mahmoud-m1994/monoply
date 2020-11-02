package gamelogic;

/**
 * The Street_class is used to create street
 * Street has name, and contains array of properties
 * @author Espen, Mathias
 */
public class Street {
    private final String name;
    private Property[] properties;

    public Street(String name, Property[] properties){
        this.name       = name;
        this.properties = properties;
    }

    public String getName() {
        return this.name;
    }

    /**This method checks if a given player can upgrade
     * (build houses, hotels etc) on the current street
     *
     * @param player the player that can upgrade
     * @return boolean based on the result of this method and property owner
     */
    public boolean canUpgrade(Player player){

        //Checks if player is null
        if(player == null)
            return false;

        //Loops through all properties, and checks that the player owns all the properties
        for(Property property : properties) {

            if (property.getOwner() == null) return false;

            if (!property.getOwner().equals(player)) {
                return false;
            }
        }

        return true;
    }
}