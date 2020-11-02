package userinterface.views.dialogs.wildcard;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import userinterface.AudioPlayer;
import userinterface.ControllerMediator;
import userinterface.WindowManager;
import gamelogic.Card;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is the controller of the wildcard view
 * @author Espen
 */
public class wildCardController implements Initializable {

    public Label descriptonLabel = new Label();
    public Label transactionLabel    = new Label();

    /**
     * This method registers the wildcardcontroller in the ControllerMediator
     * @see Initializable
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControllerMediator.getInstance().registerwildCardController(this);
    }

    /**
     * This method closes the window
     * @param e Event when close-button is clicked
     */
    public void closePressed(ActionEvent e) {
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.closeWindow(e);
    }

    /**
     * This method sets the right description to the card displayed
     * @param card The card is the card object to display the label to
     */
    public void setDescriptonLabel(Card card){
        this.descriptonLabel.setText(card.getDescription());
    }

    /**
     * This method sets the right price to the card displayed
     * @param card The card is the card object to display the label to
     */
    public void setTransactionLabel(Card card){
        this.transactionLabel.setText(""+card.getTransaction());
    }

}
