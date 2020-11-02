package gamelogic;

/**
 * The Player_class is used to do everything has to do with player
 * such username,position,balance,nickname...
 * @author Espen, Mathias
 */

public class Player{
    private String username;
    private String nickname;

    private int position = 0;
    private int balance  = 5000;

    private boolean bankrupt = false;

    public Player(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }

    public Player(String username, int position){
        this.username = username;
        this.position = position;
    }

    public int getBalance() { return balance; }

    public void setBalance(int balance) { this.balance = balance; }

    public String getUsername() { return username; }

    public String getNickname() { return nickname; }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public boolean isBankrupt() { return bankrupt; }

    public void setBankrupt(boolean bankrupt) { this.bankrupt = bankrupt; }

    /**
     * This method adds to or pulls from players' balance
     *
     * @param sum is the amount
     * @return integer of the balance based on the result of the method
     */
    public int transaction(int sum){
        this.balance += sum;
        return this.balance;
    }

    /**
     * This method checks if the player owns the property sent as a parameter
     *
     * @param prop
     * @return  boolean based on the result of the method
     */
    public boolean checkOwnership(Property prop){
        if(prop.getOwner().equals(this))
            return true;

        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;

        if(!(obj instanceof Player))
            return false;

        Player player = (Player) obj;

        if(this.getUsername().equals(player.username))
            return true;

        return false;
    }

    public String toString(){
        String result = "The nickname of the player: "+getUsername()+ " is "+ getNickname()+ " and his position is "+ getPosition();
        return result;
    }
}