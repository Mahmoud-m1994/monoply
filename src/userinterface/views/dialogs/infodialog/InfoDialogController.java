package userinterface.views.dialogs.infodialog;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import userinterface.AudioPlayer;
import userinterface.ControllerMediator;
import userinterface.WindowManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is the controller for the Alertbox dialog
 * @author Marcus
 */
public class InfoDialogController implements Initializable {

    public Label header     = new Label();
    public TextFlow content = new TextFlow();

    Button okButton = new Button();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControllerMediator.getInstance().registerInfoDialogController(this);
    }

    /**
     * This method closes the alert
     * @param e the event when the 'OK'-button is clicked
     */
    public void okButtonClicked(ActionEvent e) {
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.closeWindow(e);
    }

    /**
     * This method is used to set the wanted alert-message to the alert box
     * @param h the title of the alert box
     * @param info the message for the alert box
     */
    public void setAlert(String h, String info){
        //Adds header
        header.setText(h);

        // Adds info message
        Text infoText = new Text(info);
        content.getChildren().add(infoText);
    }
}