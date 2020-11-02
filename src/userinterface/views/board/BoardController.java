package userinterface.views.board;


import database.DatabaseListener;
import database.Database;

import game.Game;
import gamelogic.*;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import userinterface.AudioPlayer;
import userinterface.ControllerMediator;
import userinterface.WindowManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.*;

/**
 * This class is the controller for the main board view, which is also the main view for the game itself.
 * It handles all the UI-interaction concerning the board.
 * @author Håkon, Espen, Mathias, Marcus, Mahmoud
 */
public class BoardController<stage> implements Initializable {
    private final int FIELD_COUNT = 28;

    //Chat variables
    public TextField textField = new TextField();
    public TextArea chatField  = new TextArea();

    //Main board variables
    public BorderPane borderPane = new BorderPane();
    public GridPane topFields    = new GridPane();
    public GridPane bottomFields = new GridPane();
    public GridPane leftFields   = new GridPane();
    public GridPane rightFields  = new GridPane();

    //Button variables
    public Button throwDice      = new Button();
    public Button viewProperties = new Button();
    public Button endTurn        = new Button();

    //Field variables
    private TilePane[] fields;
    private TilePane[] propertyFields;
    public TilePane field0  = new TilePane();
    public TilePane field1  = new TilePane();
    public TilePane field2  = new TilePane();
    public TilePane field3  = new TilePane();
    public TilePane field4  = new TilePane();
    public TilePane field5  = new TilePane();
    public TilePane field6  = new TilePane();
    public TilePane field7  = new TilePane();
    public TilePane field8  = new TilePane();
    public TilePane field9  = new TilePane();
    public TilePane field10 = new TilePane();
    public TilePane field11 = new TilePane();
    public TilePane field12 = new TilePane();
    public TilePane field13 = new TilePane();
    public TilePane field14 = new TilePane();
    public TilePane field15 = new TilePane();
    public TilePane field16 = new TilePane();
    public TilePane field17 = new TilePane();
    public TilePane field18 = new TilePane();
    public TilePane field19 = new TilePane();
    public TilePane field20 = new TilePane();
    public TilePane field21 = new TilePane();
    public TilePane field22 = new TilePane();
    public TilePane field23 = new TilePane();
    public TilePane field24 = new TilePane();
    public TilePane field25 = new TilePane();
    public TilePane field26 = new TilePane();
    public TilePane field27 = new TilePane();

    //Image variables
    private ImageView[] images;
    public ImageView redCar    = new ImageView();
    public ImageView blueCar   = new ImageView();
    public ImageView greenCar  = new ImageView();
    public ImageView yellowCar = new ImageView();

    //Dice variables
    public Label leftDiceLabel  = new Label();
    public Label rightDiceLabel = new Label();
    private boolean diceThrown  = false;

    //Player variables
    private static Player player;
    private ArrayList<Player> opponents = new ArrayList<>();

    public static int debtCounter = 0;

    //Turn variable
    private int turnCount = 0;

    private Game game = new Game();

    private Property property;

    private static Property[] properties;
    private Card[] wildCards;
    //Hardkodet wildcard felt
    private int[] wildCardField = {4, 9, 12, 17, 24, 26};
    private Quiz[] quizzes;
    private String[] scenarios;

    public static int gameID = 0;

    public Label infoLabel = new Label();

    int lastID;
    int firstID;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    static ScheduledFuture<?> terminator;

    /**
     * This method is initializes important elements of the board view
     * when entering a game.
     *
     * @see Initializable#initialize(URL, ResourceBundle)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControllerMediator.getInstance().registerBoardController(this);

        //Gets player, opponents, gameID and properties from the PreLoader
        gameID = ControllerMediator.getInstance().getGameID();
        player = ControllerMediator.getInstance().getPlayer();
        opponents = ControllerMediator.getInstance().getOpponents();
        property = ControllerMediator.getInstance().getProperty();

        Database.setAutoIncrementValue();

        //Sets listeners
        Database.setListeners(gameID, player);
        Database.setListeners(gameID, player);
        Database.setChatListener(gameID);
            //ControllerMediator.getInstance().updateChat(gameID);


        properties = game.getProperties();

        wildCards = game.getWildcards();
        quizzes   = game.getQuizzes();
        scenarios = game.getScenarios();

        updateMoneyLabel();

        //Sets HeightListener to force the main board square and scale it according to the window
        borderPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight) {
                double fieldSize = ((double) newHeight) / 5;

                borderPane.setPrefWidth((double) newHeight);

                topFields.setPrefHeight(fieldSize);
                bottomFields.setPrefHeight(fieldSize);
                leftFields.setPrefWidth(fieldSize);
                rightFields.setPrefWidth(fieldSize);
            }
        });

        //Fields array
        fields = new TilePane[]{field0, field1, field2, field3, field4, field5, field6, field7, field8,
                field9, field10, field11, field12, field13, field14, field15, field16, field17, field18,
                field19, field20, field21, field22, field23, field24, field25, field26, field27};

        //Properties array
        propertyFields = new TilePane[]{field1, field2, field3, field5, field6, field8, field10,
                field11, field13, field15, field16, field18, field19,
                field20, field22, field23, field25, field27};

        setLabels();
        //Images array
        images = new ImageView[]{redCar, blueCar, greenCar, yellowCar};
        setOpponentsVisible();
        updateMoneyLabel();

        //Waits for game to start
        awaitStart(true);


    }

    /**
     * This method sets all the pieces on the board visible
     */
    public void setOpponentsVisible(){
        for(int i = 0; i < this.opponents.size(); i++)
            images[i].setVisible(true);
    }

    /**
     * This method updates all the pieces on the board. If a player has leaved,
     * it is alerted and the piece is removed from the view.
     *
     * @param opponents A list of the local player's opponents
     */
    public void updatePlayers(ArrayList<Player> opponents) {
        //Checks if there are any new players
        if (this.opponents.size() == opponents.size())
            return;
        //Checks if all opponents have left
        if (opponents.size() == 0) {
            this.opponents = opponents;

            WindowManager.displayAlertBox("Congratulations!","You Win! \nAll other players have been declared bankrupt!");

            return;
        }
        //Checks who left
        if (this.opponents.size() > opponents.size()) {
            for (int i = 0; i < this.opponents.size(); i++) {
                boolean found = false;
                for (int j = 0; j < opponents.size(); j++) {
                    if (this.opponents.get(i).equals(opponents.get(j))) {
                        found = true;
                        break;
                    }
                }

                if (!found) {

                    WindowManager.displayAlertBox("A player left the game!", "Player: " + this.opponents.get(i).getUsername() + " has left the game, kys.");

                    images[i].setVisible(false);
                    this.opponents = opponents;
                    return;
                }
            }
        }

        //Sets pieces visible for each player
        this.opponents = opponents;
        setOpponentsVisible();
    }

    /**
     * This method moves a piece on the board.
     *
     * @param currentPos The piece's current position before moving
     * @param newPos The piece's new position after moving
     * @param img The piece's visual image on the board
     */
    public void movePiece(int currentPos, int newPos, ImageView img) {
        fields[currentPos].getChildren().remove(img);
        fields[newPos].getChildren().add(img);

        //Rotates image
        if (newPos < 7)
            img.setRotate(0);
        else if (newPos < 14)
            img.setRotate(90);
        else if (newPos < 21)
            img.setRotate(180);
        else
            img.setRotate(270);
    }

    /**
     * This method is used to move the local player's piece on the board.
     * It also needs to check if the player's new position is a corner field.
     *
     * @param currentPos The piece's current position before moving
     * @param newPos The piece's new position after moving
     * @param img The piece's visual image on the board
     */
    private void moveMyPiece(int currentPos, int newPos, ImageView img) {
        movePiece(currentPos, newPos, img);

        //Checks if the new position is "samfundet"
        if(newPos == 7) {
            WindowManager.displayAlertBox("Samfundet", scenarios[new Random().nextInt(scenarios.length)]);
            game.skipTurn(player);
        }

        //Checks if the new position is "Tore på sporet"
        else if(newPos == 14) {
            WindowManager.displayAlertBox("Oh no! Where did your piece go?!" , "Skip a turn and see if you can find it next round");
            hidePiece(player, currentPos);
        }

        //Checks if the new position is "Quiz-field"
        else if(newPos == 21) {
            WindowManager.showQuizDialog();
        }

        //Checks if player is passing start
        if(currentPos > newPos && newPos >= 0) {
            System.out.println("Passed start");

            if(turnCount > 5) {
                WindowManager.displayAlertBox("Passed start",
                        "You passed start, receive 500(money) from the bank");
                player.transaction(500);
            } else {
                WindowManager.displayAlertBox("Passed start",
                        "You passed start waaay to quickly and got pulled over by Nattpatruljen, " +
                                "receive a speeding ticket of 250(money)");
                player.transaction(-250);
            }

            updateMoneyLabel();
            turnCount = 0;
        }
    }

    /**
     * This method handles the event when "throw dice" button is pressed.
     * When called it will move player/piece if allowed, and take appropriate
     * action. Position changes is updated to the database.
     *
     * @param e The event when "throw dice"-button is pressed
     */
    public void diceButtonPressed(ActionEvent e) {
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        String activeUser = Database.getActivePlayer(gameID).getUsername();
        Database.getUpgrades(properties, gameID);

        //Returns if current player is not this.player
        if(!activeUser.equals(player.getUsername())){
            WindowManager.displayAlertBox("Kys, it's not your turn!", "Wait for " + activeUser + " to finish his turn.");

            return;
        }

        //Returns if dice has already been thrown
        if(diceThrown) {
            WindowManager.displayAlertBox("You can only throw the dice once per round!", "Move on with your turn");
            return;
        }
        diceThrown = true;

        // Sets piece visible
        images[3].setVisible(true);

        //Checks debtCounter /Lose Condtion
        if (loseCondition()) {
            WindowManager.displayAlertBox("Bankrupt!", "You have been in debt too long!");
            game.logOut(gameID, player);
            WindowManager.switchScene(e, WindowManager.LOGIN);
        }

        //Gets dice values
        int firstRoll = Dice.rollDice();
        int secondRoll = Dice.rollDice();

        //Sets dice text
        leftDiceLabel.setText(String.valueOf(firstRoll));
        rightDiceLabel.setText(String.valueOf(secondRoll));

        //Gets player position
        int currentPos = player.getPosition();
        int tmpPos = currentPos + firstRoll + secondRoll;
        final int newPos = (tmpPos < FIELD_COUNT) ? tmpPos : tmpPos - FIELD_COUNT;

        //Moves piece
        moveMyPiece(currentPos, newPos, images[3]);

        //Sets new player position
        player.setPosition(newPos);

        //Writes new pos to database
        Task updateDatabase = new Task() {
            @Override
            protected Object call() {
                return Database.setPosition(player.getUsername(), newPos);
            }
        };

        new Thread(updateDatabase).start();

        payRent(e);
    }

    /**
     * This method is used to hide a player's piece from the board for a special event.
     * It will also move the piece to a random location.
     *
     * @param player The local player on the current system
     * @param currentPos The player's current position on the board
     */
    private void hidePiece(Player player, int currentPos) {
        int newPos = new Random().nextInt(28);
        game.skipTurn(player);
        images[3].setVisible(false);
        movePiece(currentPos, newPos, images[3]);
    }

    /**
     * This method handles the event when "End turn"-button is pressed.
     * It will reset the diceThrown variable to false and end the local player's turn.
     * It will also add to the turn counter.
     *
     * @param e
     */
    public void endTurnPressed(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        //TODO: Delete method when thread is added
        game.endTurn(gameID, player);
        updatePlayers(Database.getOpponents(gameID, player));
        diceThrown = false;
        turnCount++;
    }

    /**
     * This method handles the event when the "Rules"-button is pressed.
     * It will call the method showRules from WindowManager.
     *
     * @param e The event when "Rules"-button is pressed
     * @see WindowManager#showRules(Event)
     */
    public void rulesButtonPressed(ActionEvent e) {
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.showRules(e);
    }

    /**
     * This method handles the event when the "Options"-button is pressed.
     * It switches the view to the options scene.
     *
     * @param e The event when "Option"-button is pressed
     */
    public void optionsButtonPressed(ActionEvent e) {
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.switchScene(e, WindowManager.OPTIONS);
    }

    /**
     * This method handles the event when the "Log Out"-button is pressed.
     * It will remove the player from the game and take the player back
     * to the login screen.
     *
     * @param e The event when "Log Out"-button is pressed.
     * @see Database#logOut(int, String)
     */
    public void logOutButtonPressed(ActionEvent e) {
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        //Database.logOut(gameID, player.getUsername()); This can probably be removed, test later
        game.logOut(gameID, player);

        WindowManager.switchScene(e, WindowManager.LOGIN);
    }

    /**
     * This method handles the event when the "Start Game"-button is pressed.
     *
     * @param e
     * @see Database#setStarted(int, boolean)
     */
    public void startGamePressed(ActionEvent e) {
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);

        int opponentesNr = opponents.size();
        if (opponentesNr >= 1){
            game.start(gameID);
        } else if(opponentesNr < 1) {
            WindowManager.displayAlertBox("You're alone", "You have no opponents yet, wait for someone to join.");
        }

    }

    /**
     * This method is used to set the active opponent's position.
     *
     * @param player The player is the current active opponent
     */
    public void setPosition(Player player) {
        if (player == null) {
            System.out.println("");
            return;
        }

        System.out.println(player.getUsername() + " " + player.getPosition());

        //Moves piece of active opponent to the position in the database
        for (int i = 0; i < opponents.size(); i++)
            if (opponents.get(i).equals(player)) {
                int currentPos = opponents.get(i).getPosition();
                int newPos = player.getPosition();

                if (currentPos != newPos) {
                    movePiece(currentPos, newPos, images[i]);
                    opponents.get(i).setPosition(newPos);
                }
            }
    }

    /**
     * This method handles the event when a certain property is clicked on.
     * It will present a new view with information about the property.
     *
     * @param e A mouse event when a property is clicked
     */
    public void propertyPressed(MouseEvent e) {
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);

        Property property = null;

        TilePane field = (TilePane) ((VBox) e.getSource()).getChildren().get(0);

        for (int i = 0; i < propertyFields.length; i++)
            if (propertyFields[i].equals(field)) {
                property = properties[i];
                break;
            }
        String owner;
        if (property != null) {
            owner = Database.checkOwnership(gameID, property.getPosition());

            for (int i = 0; i < Database.getOpponents(gameID, this.player).size(); i++) {
                if (Database.getOpponents(gameID, this.player).get(i).getUsername().equals(owner)) {
                    property.setOwner(Database.getOpponents(gameID, this.player).get(i));
                }
            }
            WindowManager.showPropertyDialog(e, property);
        }
    }

    /**
     * This method will present a random wildcard and execute a wildcard event.
     *
     * @param e This is the event when the player throws the dice
     */
    public void drawWildcard(ActionEvent e) {

        Card card;
        Random r = new Random();
        card = wildCards[r.nextInt(wildCards.length)];
        if (card.getPosition() != 0) {
            //Sets new player position
            player.setPosition(card.getPosition());
            Database.setPosition(player.getUsername(), card.getPosition());
            //Moves piece
            movePiece(player.getPosition(), card.getPosition(), images[3]);
            payRent(e);
        }

        int payment = card.getTransaction() * -1;
        Database.updateBalance(player.getUsername(), payment);

        updateMoneyLabel();
        WindowManager.showCardDialog(e, card);
    }

    public static int propLength;

    /**
     * This method creates a list of the local player's properties.
     *
     * @return Returns a list of the local player's properties
     */
    public static Property[] prop() {
        Property[] myPropertiesTemp = new Property[properties.length];
        propLength = 0;
        for (int i = 0; i < properties.length; i++) {
            if (properties[i].getOwner() != null) {
                if (player.getUsername().equals(properties[i].getOwner().getUsername())) {
                    myPropertiesTemp[propLength] = properties[i];
                    propLength++;
                }
            }
        }

        Property[] myProperties = new Property[propLength];

        for (int i = 0; i < myProperties.length; i++) {
            myProperties[i] = myPropertiesTemp[i];
            propLength = myProperties.length - 1;
        }
        propLength = myProperties.length - 1;
        return myProperties;
    }


    /**
     * This method handles the event when the "View my properties"-button is pressed.
     * It will present a new view where the user can navigate through the properties owned.
     *
     * @param e The event when "View my properties"-button is pressed.
     */
    public void myPropertiesButtonPressed(ActionEvent e) {
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);

        if (prop().length == 0) {
            WindowManager.displayAlertBox("No properties", "You dont have any properties to manage atm");
            return;
        }

        WindowManager.showMyPropertiesDialog(e, prop());
    }

    public static void next(ActionEvent e, boolean right) {
        if (right && WindowManager.myPropertyNr == propLength) {
            WindowManager.myPropertyNr = 0;
            WindowManager.closeWindow(e);
            WindowManager.showMyPropertiesDialog(e, prop());
        } else if (right && WindowManager.myPropertyNr != propLength) {
            WindowManager.myPropertyNr++;
            WindowManager.closeWindow(e);
            WindowManager.showMyPropertiesDialog(e, prop());
        } else if (right && WindowManager.myPropertyNr != propLength) {
            WindowManager.myPropertyNr++;
            WindowManager.closeWindow(e);
            WindowManager.showMyPropertiesDialog(e, prop());
        } else if (!right && WindowManager.myPropertyNr == 0) {
            WindowManager.myPropertyNr = propLength;
            WindowManager.closeWindow(e);
            WindowManager.showMyPropertiesDialog(e, prop());
        } else if (!right && WindowManager.myPropertyNr != 0) {
            WindowManager.myPropertyNr--;
            WindowManager.closeWindow(e);
            WindowManager.showMyPropertiesDialog(e, prop());
        }
    }

    /**
     * This method is used to update the money-label of a property when it is bought.
     *
     * @param property A property that has been bought
     */
    public void updateLabel(Property property) {
        int position = property.getPosition();

        VBox parent = (VBox) this.propertyFields[position].getParent();
        Label label = (Label) parent.getChildren().get(1);

        label.setTextFill(Paint.valueOf("#d0000e"));
        label.setText("-$" + property.getCurrentRent());
    }

    /**
     * This method is used to initialize the money-labels at the beginning of a game.
     */
    private void setLabels() {
        for (int i = 0; i < this.propertyFields.length; i++) {
            VBox parent = (VBox) this.propertyFields[i].getParent();
            Label label = (Label) parent.getChildren().get(1);

            label.setTextFill(Paint.valueOf("#017604"));
            label.setText("$" + properties[i].getPrice());
        }
    }

    /**
     * This method is used to buy a certain property.
     *
     * @param property The property that is to be bought
     * @return Returns true if property was bought, and if not false
     * @see Database#buyPropertyDb(int, Property, int, String)
     */
    public static boolean buyProperty(Property property) {
        int upgrade = 0;
        int position = property.getPosition();
        String username = ControllerMediator.getInstance().getUsername();
        if (Database.checkOwnership(gameID, position) == null) {
            Database.buyPropertyDb(gameID, property, upgrade, username);
            return true;
        }
        return false;
    }

    /**
     * This method is used to make a visiting player pay rent if landing on
     * another player's property.
     *
     * @param e The event when a player throws dice
     */
    public void payRent(ActionEvent e) {
        property = null;
        for (int i = 0; i < properties.length; i++) {
            if (player.getPosition() == properties[i].getPosition()) {
                property = properties[i];
                break;
            }
        }

        for (int i = 0; i < wildCardField.length; i++) {
            if (player.getPosition() == wildCardField[i]) {
                drawWildcard(e);
                updateMoneyLabel();
                return;
            }
        }


        if(property.getOwner() != null && player.equals(property.getOwner())) {
            WindowManager.displayAlertBox("Your property!", "You already own this property!");
            return;
        }

        if (property == null && property.getPrice() > player.getBalance()){
            WindowManager.displayAlertBox("To poor", "You don't have enough money to buy this property");
            return;
        }

        String owner = Database.checkOwnership(gameID, player.getPosition());

        for (int i = 0; i < Database.getOpponents(gameID, this.player).size(); i++) {
            if (Database.getOpponents(gameID, this.player).get(i).getUsername().equals(owner)) {
                property.setOwner(Database.getOpponents(gameID, this.player).get(i));
            }
        }

        if (!player.getUsername().equals(owner) && owner != null) {

            Database.payRent(property);
            updateMoneyLabel();
            WindowManager.displayAlertBox("Pay rent", "Hey! You have to pay: " + property.getCurrentRent() + " to " + owner);

        }
        if (owner == null) {
            //WindowManager.switchScene(e, WindowManager.BUY_PROPERTY_DIALOG);
            WindowManager.showBuyPropertyDialog(e, property);
            updateMoneyLabel();
        }

    }

    /**
     * This method is used to update the local player's money-label(label that shows balance).
     */
    public void updateMoneyLabel() {
        int balance = ControllerMediator.getInstance().getPlayer().getBalance();
        infoLabel.setText("Money: " + balance);
    }

    /**
     * This method is called when initializing a the game, and restricts
     * critical functionality until game starts.
     *
     * @param waiting When true the game is awaiting start, when false the game has started
     */
    public void awaitStart(boolean waiting) {

        //Disables buttons
        throwDice.setDisable(waiting);
        viewProperties.setDisable(waiting);
        endTurn.setDisable(waiting);

        //Views alert
        if(waiting == true) {
            WindowManager.displayAlertBox("Waiting...", "Waiting for players to join... Press \"Start Game\" to start the game");
        } else if(waiting == false){
            WindowManager.displayAlertBox("Beginning...", "The game has started! Close window when you are ready");
        }
    }

    /**
     * This method checks how many turns the local player has been in debt.
     *
     * @return Returns true if in debt for 3 turns, otherwise false
     */
    public static boolean loseCondition() {

        if (player.getBalance() > 0) {
            debtCounter = 0;
            return false;
        }

        if(debtCounter == 3) return true;

        if(debtCounter == 2) {
            WindowManager.displayAlertBox("You are in debt!", "This is your last turn to lose your debt!");
            debtCounter++;
            return false;
        }
        if (player.getBalance() < 0) {
            WindowManager.displayAlertBox("In debt!", "You are in debt! \nGet rid of your debt in "+(3-debtCounter)+" turns or youre bankrupt!");
            debtCounter++;
            return false;
        }
        return false;
    }

    /**
     * This method is used to check and alert if another player needs to pay the local player rent
     *
     * @param opponent The player landing on a property
     */
    public static void checkRent(Player opponent) {
        Property prop = null;

        for (int i = 0; i < prop().length; i++) {
            if(opponent.getPosition() == prop()[i].getPosition()) {
                prop = prop()[i];
            }
        }
        if(prop == null) return;

        if(opponent == ControllerMediator.getInstance().getPlayer()) return;

        if(prop.getOwner().equals(player)) {
            WindowManager.displayAlertBox("Rent!", "Hey! You receive " + prop.getCurrentRent() + " in rent from " + opponent.getUsername());
        }

    }

    /**
     * This method handles when the "Send"-button is pressed.
     * It will send a entered chat-message from the local player to the other players
     *
     * @param e The event when "Send"-button is pressed
     * @see Database#setMessage(int, String, String)
     */
    public void sendButtonPressed(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        // Write message to database
        Database.setMessage(gameID,player.getUsername(),textField.getText());
        textField.clear();
    }


    public static int last;

    /**
     * This method is used to update the chat field in view.
     *
     * @param gameID The gameID identifies the game of interest in database
     */
    public void updateChat(int gameID) {


        String[] lines = chatField.getText().split("\n");
        last = lines.length;

        if(Database.getLastMessage(gameID) == null) return;

        if(Database.getLastMessage(gameID).equals(lines[last-1]) && last!= 0) {
            last = DatabaseListener.msgId;
        }

        if(DatabaseListener.msgId > last){
            if(Database.getLastMessage(gameID) == null) return;

            chatField.appendText(Database.getLastMessage(gameID)+"\n");
            }
    }
}