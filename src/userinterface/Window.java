package userinterface;

import database.Database;
import database.LocalStorage;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class is used to initialize and set up the main window for the game
 * @author Håkon
 */
public class Window extends Application{
    private static final int WIDTH  = 900;
    private static final int HEIGHT = 600;

    @Override
    public void start(Stage primaryStage){
        Parent root;

        primaryStage.setOnCloseRequest(event -> logout());

        /*
        Loads username from file
        LocalStorage returns a username (user is remembered) -> go to game
        LocalStorage returns null (user is not remembered)   -> go to login
         */
        String username = LocalStorage.getUserData();
        if(username != null) {
            ControllerMediator.getInstance().setUsername(username);
            root = WindowManager.getParent(WindowManager.PRELOADER);
        }
        else
            root = WindowManager.getParent(WindowManager.LOGIN);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        WindowManager.setTheme(scene);

        primaryStage.setTitle("Monopoly");
        primaryStage.setScene(scene);
        primaryStage.show();

        AudioPlayer.play(AudioPlayer.BACKGROUND_MUSIC, true);
    }

    /**
     * Logs the user out when closing the window
     */
    private static void logout(){
        String username = ControllerMediator.getInstance().getUsername();
        if(username == null)
            return;

        try {
            final int gameID = ControllerMediator.getInstance().getGameID();
            Database.logOut(gameID, ControllerMediator.getInstance().getUsername());
        } catch (NullPointerException e) {
            System.out.println("Could not log ut user");
            e.printStackTrace();
        }
    }
}