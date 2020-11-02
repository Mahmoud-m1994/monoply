package database;

import gamelogic.Player;
import javafx.application.Platform;
import userinterface.ControllerMediator;
import userinterface.views.board.BoardController;
import userinterface.WindowManager;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * The DatabaseListener-class is used to keep track when certain values of interest
 * changes in the database
 * @author Håkon, Marcus, Espen, Mathias, Mahmoud
 */
public class DatabaseListener {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static ScheduledFuture<?> terminator;
    private static Player previousPlayer = null;
    private static int playerPosCheck = 0;
    private static ArrayList<Player> opponents = null;
    public static int msgId = 0;

    /**
     * This method checks which player is the active player once every second.
     * It will alert players when there is a turn change or if a player lands
     * on another player's property.
     *
     * @param gameID The gameID identifies the game of interest in the database
     */
    public static void getActivePlayer(int gameID) {
        previousPlayer = Database.getActivePlayer(gameID);

        if(previousPlayer != null)
            playerPosCheck = previousPlayer.getPosition();

        final Runnable print = () -> {
            Player player = Database.getActivePlayer(gameID);

            //Alerts turn change
            if(!player.getUsername().equals(previousPlayer.getUsername()) && Database.checkIfStarted(gameID) == 1) {
                Platform.runLater(()-> WindowManager.alertTurnChange(player));
                previousPlayer = player;
            }
            //Check if opponent lands on your property
            if(!player.getUsername().equals(previousPlayer.getUsername())) {
                //Set starting position for new player
                playerPosCheck = Database.getActivePlayer(gameID).getPosition();
            }
            //Checks if dice has been thrown
            if(playerPosCheck != Database.getActivePlayer(gameID).getPosition()) {
                if(Database.getActivePlayer(gameID).equals(ControllerMediator.getInstance().getPlayer())) return;
                Platform.runLater(()-> BoardController.checkRent(player));
                playerPosCheck = Database.getActivePlayer(gameID).getPosition();
            }

            //Has to be run later so javaFX thread can update the GUI
            Platform.runLater(() -> ControllerMediator.getInstance().setPositon(player));
        };

        scheduler.scheduleAtFixedRate(print, 1, 1, TimeUnit.SECONDS);
    }

    /**
     * This method will check for new joining players every second, and update them into the game.
     * It will alert when a player joins, and stop when the game has started.
     *
     * @param gameID The gameID identifies the game of interest in the database
     * @param player The player is the local player on the current system
     */
    public static void getOpponents(int gameID, Player player){

        opponents = Database.getOpponents(gameID, player);

        final Runnable print = () -> {

            // Before game is started, check for new players
            if(Database.checkIfStarted(gameID) == 0) {
                ArrayList<Player> newOpponents = Database.getOpponents(gameID, player);

                //Alerts when a new player joins
                if(opponents.size() < newOpponents.size()) {
                    Player newPlayer = newOpponents.get(opponents.size());
                    opponents = newOpponents;
                    Platform.runLater(()-> WindowManager.alertPlayerJoin(newPlayer));
                }

                //Has to be run later so javaFX thread can update the GUI
                Platform.runLater(() -> ControllerMediator.getInstance().updatePlayers(newOpponents));
            }
            // When game is started, cancels wait and thread is terminated.
            else {
                Platform.runLater(() -> ControllerMediator.getInstance().awaitStart(false));
                terminator.cancel(false);
            }
        };

        scheduler.scheduleAtFixedRate(print, 1, 1, TimeUnit.SECONDS);

    }

    /**
     * This method checks for new chat messages every second and updates them to the UI.
     *
     * @param gameID The gameID identifies the game of interest in the database
     */
    public static void getChat(int gameID){
        final Runnable print = () -> {
            {
                msgId = Database.getMessageId(gameID);
                int chatfieldIndex = BoardController.last;

                if (msgId > chatfieldIndex && msgId != 0) {
                    ControllerMediator.getInstance().updateChat(gameID);
                    msgId = Database.getMessageId(gameID);
                }

                Platform.runLater(() -> ControllerMediator.getInstance().updateChat(gameID));
            }};

        scheduler.scheduleAtFixedRate(print, 1, 1, TimeUnit.SECONDS);
    }
}