package userinterface.views.checkinbox;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import userinterface.AudioPlayer;
import userinterface.WindowManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is the controller for the check-inbox view
 * It displays information about the user having to check their email
 * @author Håkon
 */
public class CheckInboxController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    /**
     * This method switches to the 'Login'-view
     * @param e is the event when the 'Login'-button is clicked
     */
    public void loginButtonPressed(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.switchScene(e, WindowManager.LOGIN);
    }
}
