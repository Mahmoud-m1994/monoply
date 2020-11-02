package userinterface.views.themeeditor;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import userinterface.AudioPlayer;
import userinterface.CustomTheme;
import userinterface.WindowManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This view lets the user make a custom theme
 * Note: Because stylesheets are cached in JavaFX, the
 * application needs to be restarted before the custom
 * theme can be applied
 * @author Håkon
 */
public class ThemeEditorController implements Initializable {

    public ColorPicker backgroundColorPicker = new ColorPicker();
    public ColorPicker textColorPicker       = new ColorPicker();
    public ColorPicker buttonColorPicker     = new ColorPicker();
    public ColorPicker borderColorPicker     = new ColorPicker();

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    /**
     * This method writes the new custom theme to a .css file
     * @param e the event when 'Save Theme'-button is clicked
     */
    public void saveThemePressed(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);

        String backgroundColor = "#" + backgroundColorPicker.getValue().toString().substring(2);
        String textColor       = "#" + textColorPicker.getValue().toString().substring(2);
        String buttonColor     = "#" + buttonColorPicker.getValue().toString().substring(2);
        String borderColor     = "#" + borderColorPicker.getValue().toString().substring(2);

        CustomTheme theme = new CustomTheme(backgroundColor, textColor, buttonColor, borderColor);
        theme.saveTheme();
    }

    /**
     * This method switches back to the theme settings view
     * @param e the event when 'Back'-button is clicked
     */
    public void backButtonPressed(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.switchScene(e, WindowManager.THEME);
    }
}