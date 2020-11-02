package userinterface.views.theme;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import userinterface.AudioPlayer;
import userinterface.WindowManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is the controller of the theme view
 * It handles all the UI-interaction concerning the themes
 * @author Håkon
 */
public class ThemeController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){ }

    /**
     * This method sets the theme to 'Standard'
     * @param e the event when 'Standard'-button is clicked
     */
    public void standardThemePressed(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.setTheme(e, WindowManager.STANDARD_THEME);
    }

    /**
     * This method sets the theme to 'Dracula'
     * @param e the event when 'Dracula'-button is clicked
     */
    public void draculaThemePressed(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.setTheme(e, WindowManager.DRACULA_THEME);
    }

    /**
     * This method sets the theme to 'Pinky'
     * @param e the event when 'Pinky'-button is clicked
     */
    public void pinkyThemePressed(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.setTheme(e, WindowManager.PINKY_THEME);
    }

    /**
     * This method sets the theme to 'Custom'
     * @param e the event when 'Custom'-button is clicked
     */
    public void customThemePressed(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.setTheme(e, WindowManager.CUSTOM_THEME);
    }

    /**
     * This method switches to the 'Theme Editor'-view
     * @param e the event when 'Edit custom theme'-button is clicked
     */
    public void editCustomThemePressed(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.switchScene(e, WindowManager.THEME_EDITOR);
    }

    /**
     * This method switches to the 'Options'-dialog
     * @param e the event when 'Back'-button is clicked
     */
    public void backButtonPressed(ActionEvent e) {
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.switchScene(e, WindowManager.OPTIONS);
    }
}