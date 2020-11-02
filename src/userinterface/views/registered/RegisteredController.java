package userinterface.views.registered;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import userinterface.AudioPlayer;
import userinterface.WindowManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class shows a window that says the user successfully got registered
 * @author Håkon
 */
public class RegisteredController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    /**
     * This method switches to the 'Login'-view
     * @param e the event when the 'Login'-button is clicked
     */
    public void loginButtonPressed(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.switchScene(e, WindowManager.LOGIN);
    }
}
