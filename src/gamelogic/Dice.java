package gamelogic;

import java.util.Random;

/**
 * The Dice_class used to return a random value
 * every time Throw Dice button clicked in the game
 * @author Espen, Mathias
 */

public class Dice {
    public static int rollDice() {
        Random random = new Random();

        return random.nextInt(3) + 1;
    }
}
