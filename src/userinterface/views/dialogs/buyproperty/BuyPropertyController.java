package userinterface.views.dialogs.buyproperty;

import game.FieldInitializer;
import gamelogic.Property;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import userinterface.AudioPlayer;
import userinterface.ControllerMediator;
import userinterface.WindowManager;
import userinterface.views.board.BoardController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is the controller for the 'Buy Property'-view
 * It handles all UI-interaction when buying a property
 * @author Marcus
 */
public class BuyPropertyController implements Initializable {

    Property property;

    int Player_pos = ControllerMediator.getInstance().getPlayer().getPosition();
    Property [] properties = FieldInitializer.getProperties();

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
     * This method initializes the controller and all the labels for the property
     * @see Initializable
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControllerMediator.getInstance().registerBuyPropertyController(this);

        priceLabels = new Label[]{ priceLabel, rentLabel, mortgageLabel, firstHouseLabel, secondHouseLabel,
                thirdHouseLabel, fourthHouseLabel, hotelLabel, housePriceLabel, hotelPriceLabel };

        infoLabels = new Label[]{ titleLabel, usernameLabel };
    }

    /**
     * This method presents an alert and updates the labels if buying was successful
     * @param e the event when 'Buy'-button is clicked
     */
    public void buyClicked(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        Player_pos = ControllerMediator.getInstance().getPlayer().getPosition();
        int balance = ControllerMediator.getInstance().getPlayer().getBalance();


        for(int i = 0; i < properties.length; i++) {
            if(Player_pos == properties[i].getPosition()) {
                property = properties[i];
                break;
            }
        }
        if(balance >= property.getPrice()){
            BoardController.buyProperty(property);
            ControllerMediator.getInstance().updateMoneyLabel();

            WindowManager.displayAlertBox("Property Bought", "Hey, You just bought " + property.getName());
            ControllerMediator.getInstance().updateMoneyLabel();

        } else {

            WindowManager.displayAlertBox("Not Enough Money", "Hey, You're too poor to buy " + property.getName());

        }
    }

    /**
     * This method closes the 'Buy Property'-dialog
     * @param e the event when 'Close'-button is clicked
     */
    public void closeClicked(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.closeWindow(e);
    }

    /**
     * This method sets all the price labels for a property
     * @param prices an array of different prices for the property
     */
    public void setPriceLabelsBPC(int[] prices){
        for(int i = 0; i < this.priceLabels.length; i++)
            this.priceLabels[i].setText(String.valueOf(prices[i]));
    }

    /**
     * This method sets all the info labels for a property
     * @param info an array of info labels
     */
    public void setInfoLabelsBPC(String[] info){
        for(int i = 0; i < this.infoLabels.length; i++)
            this.infoLabels[i].setText(info[i]);
    }

    /**
     * This method sets the color of a property
     * @param color the color of the property
     */
    public void setColorBPC(String color){
        this.header.getStyleClass().clear();
        this.header.getStyleClass().add(color);
    }
}