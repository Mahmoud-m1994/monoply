package userinterface.views.dialogs.myproperties;

import database.Database;
import game.FieldInitializer;
import gamelogic.Property;
import gamelogic.Street;
import gamelogic.Upgrade;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import userinterface.AudioPlayer;
import userinterface.ControllerMediator;
import userinterface.WindowManager;
import userinterface.views.board.BoardController;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * This class is the controller for the MyProperties dialog
 * @author Marcus
 */
public class MyPropertiesController implements Initializable {
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

    public Button rightButton = new Button();
    public Button leftButton = new Button();

    public Upgrade upgrade;
    public Property property;

    private int gameid = 0;
    private String username;

    /**
     * This method initializes the controller and all the labels for the view
     * @see Initializable
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControllerMediator.getInstance().registerMyPropertiesController(this);

        priceLabels = new Label[]{ priceLabel, rentLabel, mortgageLabel, firstHouseLabel, secondHouseLabel,
                thirdHouseLabel, fourthHouseLabel, hotelLabel, housePriceLabel, hotelPriceLabel };

        infoLabels = new Label[]{ titleLabel, usernameLabel };

        property  = ControllerMediator.getInstance().getProperty();
        gameid = ControllerMediator.getInstance().getGameID();
        username = ControllerMediator.getInstance().getUsername();
        upgrade = ControllerMediator.getInstance().getUpgrade();
    }

    /**
     * This method handles the event when right-button is clicked
     * @param e the event when 'Right'-button is clicked
     */
    public void rightButtonClicked(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        BoardController.next(e, true);
    }

    /**
     * This method handle the event when left-button is clicked
     * @param e the event when the 'Left'-button is clicked
     */
    public void leftButtonClicked(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        BoardController.next(e, false);
    }

    /**
     * This method handle the event when back-button is clicked
     * @param e the event when the 'Back'-button is clicked
     */
    public void closeWindowClicked(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.myPropertyNr = 0;

        WindowManager.closeWindow(e);
    }

    /**
     * This method handle the event when buyUpgrade-button is clicked.
     * It checks if you owns all the properties within a street, or if the property already
     * has max upgrades.
     *
     * @param e Event when buyUpgrade-button is clicked.
     */
    public void buyUpgradeClicked(ActionEvent e){
        Property[] props = BoardController.prop();
        Property property = props[WindowManager.myPropertyNr];
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);

        Street check = null;
        for(int i = 0; i < FieldInitializer.getStreets().length; i++) {
            if(property.getColor().equals(FieldInitializer.getStreets()[i].getName())) {
                check = FieldInitializer.getStreets()[i];
                break;
            }
        }

        if (!check.canUpgrade(property.getOwner())) {
            WindowManager.displayAlertBox("Cant upgrade!","You dont own all the propeties in this street!");
            return;
        }

        int housePrice = property.getHousePrice();
        int hotellPrice = property.getHotelPrice();
        int lvl = property.getHouseCount() + property.getHotelCount() + 1;

        if(property.getHouseCount()+property.getHotelCount() == 5){
            WindowManager.displayAlertBox("Can not Upgrade!", "Property has max upgrades!");
            return;
        }

        if(ControllerMediator.getInstance().getPlayer().getBalance() > hotellPrice && property.getHouseCount() == 4) {
            property.addHotel();
            Database.updateUpgradeDB(gameid, property, hotellPrice, lvl);
            WindowManager.displayAlertBox("Upgrade", "You bought a hotel!");
            return;
        }

        if(ControllerMediator.getInstance().getPlayer().getBalance() > housePrice && property.getHouseCount() <= 4) {
            property.addHouse();
            Database.updateUpgradeDB(gameid, property, housePrice, lvl);
            WindowManager.displayAlertBox("Upgrade", "You bought a house!");
            return;
        }
    }

    /**
     * This method handle the event when sell-button is clicked.
     * It checks if you have any upgrades to sell,
     * and if not it sells the property itself.
     *
     * @param e Event when buyUpgrade-button is clicked.
     */
    public void sellClicked(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        Property[] props = BoardController.prop();
        Property property = props[WindowManager.myPropertyNr];

        int housePrice = (int) (property.getHousePrice() * -0.75);
        int hotellPrice = (int) (property.getHotelPrice() * -0.75);
        int lvl = property.getHouseCount() + property.getHotelCount() - 1;

        if(property.getHouseCount()+property.getHotelCount() == 0){
            if(Database.checkOwnership(gameid, property.getPosition()) != null) {
                Database.sellProperty(property, gameid, username);

                WindowManager.displayAlertBox("Sold property!", "You sold " + property.getName() + "!");
            } else {
                WindowManager.displayAlertBox("Can't sell property", "You don't own this property!");
            }
            ControllerMediator.getInstance().updateMoneyLabel();
            return;
        }

        if(property.getHotelCount() == 1) {
            property.sellHotel();
            Database.updateUpgradeDB(gameid, property, hotellPrice, lvl);
            WindowManager.displayAlertBox("Sold!", "You sold a hotel!");
            return;
        }

        if(property.getHouseCount() <= 4 && property.getHouseCount() > 0) {
            property.sellHouse();
            Database.updateUpgradeDB(gameid, property, housePrice, lvl);
            WindowManager.displayAlertBox("Sold!", "You sold a house!");
            return;
        }

    }

    /**
     * This method sets the prices for the property card displayed to the user.
     *
     * @param prices The prices is an array of ints displayed on the card.
     */
    public void setPriceLabelsMPC(int[] prices){
        for(int i = 0; i < this.priceLabels.length; i++)
            this.priceLabels[i].setText(String.valueOf(prices[i]));
    }

    /**
     * This method sets the info labels for the property card displayed to the user.
     *
     * @param info The info is an array of strings displayed on the card.
     */
    public void setInfoLabelsMPC(String[] info){
        for(int i = 0; i < this.infoLabels.length; i++)
            this.infoLabels[i].setText(info[i]);
    }

    /**
     * This method sets the color to the property card displayed to the user.
     *
     * @param color The color is the color of the street the property belongs to.
     */
    public void setColor(String color){
        this.header.getStyleClass().clear();
        this.header.getStyleClass().add(color);
    }
}