package gamelogic;

/**
 * The Bank_class is used to do transactions between players
 * @author Espen, Mathias
 */
public class Bank {
    private final double SELLING_FACTOR = 0.75;
    private final double BUYING_FACTOR  = 1.25;

    /**
     * This method makes a transaction between a list of players and a player
     * @param players
     * @param playerFrom
     * @param sum
     * @returns boolean based on the result of the method
     */
    public boolean transaction(Player[] players, Player playerFrom, int sum){

        for(int i = 0; i < players.length; i++){
            if (players[i].equals(playerFrom))
                return false;

            transaction(players[i], playerFrom, sum);
        }
        return true;
    }

    /**
     * This method makes a transaction between two players
     * @param playerTo
     * @param playerFrom
     * @param sum
     * @return boolean based on the result of the method
     */
    public static boolean transaction(Player playerTo, Player playerFrom, int sum){
        int newBalance = playerFrom.transaction(-sum);

        //Checks if player goes bankrupt
        if(newBalance < 0){
            playerFrom.setBankrupt(true);
            playerTo.transaction(sum + newBalance);
            return false;
        }

        playerTo.transaction(sum);
        return true;
    }
}