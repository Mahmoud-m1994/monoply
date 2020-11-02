package userinterface.views.options;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import userinterface.AudioPlayer;
import userinterface.ControllerMediator;
import userinterface.WindowManager;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is the controller of the options view
 * It handles all the UI-interaction concerning the settings
 * @author Mathias
 */
public class OptionsController implements Initializable {

    public Button settingsButton = new Button();

    private String username = ControllerMediator.getInstance().getUsername();

    /**
     * This method is initializes important elements of the option view
     * If the user isn't logged in the settings-button isn't available
     * @see Initializable#initialize(URL, ResourceBundle)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(username == null){
            settingsButton.setVisible(false);
        }
    }

    /**
     * This method switches to the 'Settings'-view
     * @param e the event when the 'Settings'-button is clicked
     */
    public void settingsButtonPressed(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.switchScene(e, WindowManager.SETTINGS);
    }

    /**
     * This method switches to the 'Feedback'-view
     * @param e the event when the 'Feedback'-button is clicked
     */
    public void feedbackButtonPressed(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.switchScene(e, WindowManager.FEEDBACK);
    }

    /**
     * This method switches to the 'Audio'-view
     * @param e the event when the 'Audio'-button is clicked
     */
    public void audioButtonPressed(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.switchScene(e, WindowManager.AUDIO);
    }

    /**
     * This method switches to the 'Theme'-view
     * @param e the event when the 'Theme'-button is clicked
     */
    public void themeButtonPressed(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.switchScene(e, WindowManager.THEME);
    }

    /**
     * This method switches to the 'Board'-view if the user is logged in
     * or to the 'Login'-view when the user is not logged in
     * @param e the event when the 'Back'-button is clicked
     */
    public void backButtonPressed(ActionEvent e) {
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);

        if(username != null)
            WindowManager.switchScene(e, WindowManager.BOARD);
        else
            WindowManager.switchScene(e, WindowManager.LOGIN);
    }
}