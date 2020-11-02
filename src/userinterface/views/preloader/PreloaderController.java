package userinterface.views.preloader;

import database.Database;
import gamelogic.Building;
import gamelogic.Player;
import gamelogic.Property;
import gamelogic.Upgrade;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import userinterface.ControllerMediator;
import userinterface.WindowManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class is a preloader that initializes the board
 * by communicating with the database and setting some variables
 * @author Håkon, Espen, Mathias
 */
public class PreloaderController implements Initializable {
    public VBox root = new VBox();

    private Player player;
    private int gameID = 0;
    private ArrayList<Player> opponents = new ArrayList<>();

    //@author Espen, Mathias
    private Property property;
    private Upgrade upgrade;
    private Building building;
    private int level;

    /**
     * This method initializes the main values that are used in the game
     * @author Håkon
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControllerMediator.getInstance().registerPreloaderController(this);
        ControllerMediator.getInstance().setInGame(true);

        String username = ControllerMediator.getInstance().getUsername();
        player = new Player(username, username);

        //Connects to game
        Task task = new Task() {
            @Override
            protected Integer call() {
                try {
                    gameID    = Database.connectToGame(username);
                    opponents = Database.getOpponents(gameID, player);
                } catch (Exception e){
                    e.printStackTrace();
                }

                return 0;
            }
        };

        new Thread(task).start();

        //Handles the result from the preloader
        task.setOnSucceeded(event -> {
            Scene scene = root.getScene();
            WindowManager.switchScene(scene, WindowManager.BOARD);

        });

        //@author Espen, Mathias
        int lvl  = ControllerMediator.getInstance().getLevel();
        property = ControllerMediator.getInstance().getProperty();
        upgrade  = new Upgrade(property, building, lvl);
    }

    public Player getPlayer(){
        return this.player;
    }

    public int getGameID(){ return this.gameID; }

    public ArrayList<Player> getOpponents(){ return this.opponents; }

    //@author Espen, Mathias
    public Property getProperty() { return  this.property; }
    public void buyUpgrade() { }
    public Upgrade getUpgrade() { return this.upgrade; }
    public int getLevel() { return  this.level; }
}