package game;

import database.Database;
import database.LocalStorage;
import gamelogic.*;

/**
 * This class is used for communication between game logic, database and UI
 * @author Håkon, Marcus, Espen
 */
public class Game {
    private Street[] streets      = FieldInitializer.getStreets();
    private Property[] properties = FieldInitializer.getProperties();
    private Card[] wildcards      = FieldInitializer.getWildcards();
    private Quiz[] quizzes        = QuizInitializer.getQuizzes();
    private String[] scenarios    = FieldInitializer.getScenarios();

    public Street[] getStreets(){
        return this.streets;
    }

    public Property[] getProperties(){
        return this.properties;
    }

    public Card[] getWildcards(){
        return this.wildcards;
    }

    public Quiz[] getQuizzes() {
        return this.quizzes;
    }

    public String[] getScenarios() {return this.scenarios;}

    /**
     * This method is used to log out and remove a user from a certain game
     *
     * @param gameid Identifies the game of interest in database
     * @param player The player that is logging out
     * @see Database#logOut(int, String)
     */
    public static void logOut(int gameid, Player player){
        Database.logOut(gameid, player.getUsername());
        LocalStorage.clearUserData();
    }

    /**
     * This method is used to end a player's turn
     *
     * @param gameid Identifies the game of interest in database
     * @param player The player ending turn
     * @see Database#endTurn(int, String)
     */
    public static void endTurn(int gameid, Player player){
        Database.endTurn(gameid, player.getUsername());
    }

    /**
     * This method is used to start a certain game
     *
     * @param gameid Identifies the game of interest in database
     */
    public static void start(int gameid){
        Database.setStarted(gameid, true);
    }

    /**
     * This method is used to force a player to skip a turn
     *
     * @param player The player skipping a turn
     * @see Database#setLock(String, int)
     */
    public static void skipTurn(Player player) {
        Database.setLock(player.getUsername(), 1);
    }

}
