package userinterface.views.dialogs.property;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import userinterface.AudioPlayer;
import userinterface.ControllerMediator;
import userinterface.WindowManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is the controller for the property dialog
 * @author Håkon
 */
public class PropertyController implements Initializable {
    public VBox header = new VBox();

    private Label[] priceLabels;
    public Label priceLabel       = new Label();
    public Label rentLabel        = new Label();
    public Label mortgageLabel    = new Label();
    public Label firstHouseLabel  = new Label();
    public Label secondHouseLabel = new Label();
    public Label thirdHouseLabel  = new Label();
    public Label fourthHouseLabel = new Label();
    public Label hotelLabel       = new Label();
    public Label housePriceLabel  = new Label();
    public Label hotelPriceLabel  = new Label();

    private Label[] infoLabels;
    public Label titleLabel    = new Label();
    public Label usernameLabel = new Label();

    /**
     * Initializes the price and info labels (text to be displayed)
     * Registers the property controller in the ControllerMediator
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControllerMediator.getInstance().registerPropertyController(this);

        priceLabels = new Label[]{ priceLabel, rentLabel, mortgageLabel, firstHouseLabel, secondHouseLabel,
                               thirdHouseLabel, fourthHouseLabel, hotelLabel, housePriceLabel, hotelPriceLabel };

        infoLabels = new Label[]{ titleLabel, usernameLabel };
    }

    /**
     * This method closes the window
     * @param e vent when close-button is clicked
     */
    public void closePressed(ActionEvent e) {
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.closeWindow(e);
    }

    /**
     * This method sets the prices for the property card
     * @param prices an array of prices to be displayed
     */
    public void setPriceLabels(int[] prices){
        for(int i = 0; i < this.priceLabels.length; i++)
            this.priceLabels[i].setText(String.valueOf(prices[i]));
    }

    /**
     * This method sets the info labels for the property card
     * @param info an array of strings displayed
     */
    public void setInfoLabels(String[] info){
        for(int i = 0; i < this.infoLabels.length; i++)
            this.infoLabels[i].setText(info[i]);
    }

    /**
     * This method sets the color to the property card
     * @param color the color of the street that the property belongs to
     */
    public void setColor(String color){
        this.header.getStyleClass().clear();
        this.header.getStyleClass().add(color);
    }
}