package userinterface.views.audio;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import userinterface.AudioPlayer;
import userinterface.WindowManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is the controller for the audio
 * Allows the user to toggle the background music, or increase/decrease the sound
 * @author Håkon
 */
public class AudioController implements Initializable {
    public Button toggleMusicButton = new Button();

    /**
     * This method checks if the music is enabled or disabled
     * If music is enabled show 'Disable background music' on button (default value)
     * If music is disabled, show 'Enable background music'
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        if(!AudioPlayer.musicEnabled())
            toggleMusicButton.setText("Enable background music");
    }

    /**
     * This method increases the volume for the background music
     * @param e the event when the 'Increase volume'-button is pressed
     */
    public void increaseVolumePressed(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        AudioPlayer.setVolume(6.0f);
    }

    /**
     * This method decreases the volume for the background music
     * @param e the event when the 'Decrease volume'-button is pressed
     */
    public void decreaseVolumePressed(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        AudioPlayer.setVolume(-10.0f);
    }

    /**
     * This method toggles the background music on or off
     * @param e the event when the 'Enable/Disable background music'-button is clicked
     */
    public void toggleMusicPressed(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);

        if(AudioPlayer.musicEnabled()){
            AudioPlayer.stopMusic();
            toggleMusicButton.setText("Enable background music");
        } else {
            AudioPlayer.startMusic();
            toggleMusicButton.setText("Disable background music");
        }
    }

    /**
     * This method switches to the 'Options'-view
     * @param e the event when the 'Back'-button is clicked
     */
    public void backButtonPressed(ActionEvent e) {
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.switchScene(e, WindowManager.OPTIONS);
    }
}