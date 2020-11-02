
import javafx.application.Application;
import userinterface.Window;

/**
 * This class is used to run the application/game, and displays the main window.
 * @author Håkon
 */
public class Runner {
    public static void main(String[] args){
        //Launches the application
        Application.launch(Window.class, args);
    }
}