package gamelogic;

/**
 * The Card_class is used to create a card
 * Card has description,transaction value,position
 * @author Espen, Mathias
 */
public class Card {
    private String description;
    private int transaction;
    private int position;

    public Card(String description, int transaction, int position){
        this.description = description;
        this.transaction = transaction;
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public int getTransaction() {
        return transaction;
    }

    public int getPosition() {
        return position;
    }

    public void makeTransaction (Player player){
        if(player == null || this.transaction == 0)
            return;

        player.transaction(this.transaction);
    }
    /*
    public void movePiece (Piece p) {
        p.setPosition(this.position);
    }
    */
}
