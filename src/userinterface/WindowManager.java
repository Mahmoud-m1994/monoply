package userinterface;

import gamelogic.Card;
import gamelogic.Player;
import gamelogic.Property;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * This class is used to handle all scene and stage changes
 * @author Håkon, Marcus, Espen
 */
public class WindowManager {
    public static final String STANDARD_THEME = WindowManager.class.getResource("/userinterface/style/themes/standard.css").toExternalForm();
    public static final String DRACULA_THEME  = WindowManager.class.getResource("/userinterface/style/themes/dracula.css").toExternalForm();
    public static final String PINKY_THEME    = WindowManager.class.getResource("/userinterface/style/themes/pinky.css").toExternalForm();
    public static final String CUSTOM_THEME   = WindowManager.class.getResource("/userinterface/style/themes/custom.css").toExternalForm();

    private static String currentTheme = STANDARD_THEME;

    public static final String LOGIN           = "/userinterface/views/login/login.fxml";
    public static final String BOARD           = "/userinterface/views/board/board.fxml";
    public static final String OPTIONS         = "/userinterface/views/options/options.fxml";
    public static final String REGISTER        = "/userinterface/views/register/register.fxml";
    public static final String PRELOADER       = "/userinterface/views/preloader/preloader.fxml";
    public static final String REGISTERED      = "/userinterface/views/registered/registered.fxml";
    public static final String CHECK_INBOX     = "/userinterface/views/checkinbox/checkinbox.fxml";
    public static final String FORGOT_PASSWORD = "/userinterface/views/forgotpassword/forgotpassword.fxml";
    public static final String SETTINGS        = "/userinterface/views/settings/settings.fxml";
    public static final String FEEDBACK        = "/userinterface/views/feedback/feedback.fxml";
    public static final String AUDIO           = "/userinterface/views/audio/audio.fxml";
    public static final String THEME           = "/userinterface/views/theme/theme.fxml";
    public static final String THEME_EDITOR    = "/userinterface/views/themeeditor/themeeditor.fxml";

    private static final String PROPERTY_DIALOG        = "/userinterface/views/dialogs/property/property.fxml";
    private static final String MY_PROPERTIES_DIALOG   = "/userinterface/views/dialogs/myproperties/myproperties.fxml";
    private static final String BUY_PROPERTY_DIALOG    = "/userinterface/views/dialogs/buyproperty/buyproperty.fxml";
    private static final String QUIZ                   = "/userinterface/views/dialogs/quiz/Quiz.fxml";
    private static final String CARD_DIALOG            = "/userinterface/views/dialogs/wildcard/wildcard.fxml";
    private static final String INFO_DIALOG            = "/userinterface/views/dialogs/infodialog/infodialog.fxml";

    private static double xOffset = 0;
    private static double yOffset = 0;

    public static int myPropertyNr = 0;

    /**
     * This method is used to get the parent node of a scene
     * @param sceneName string variable pointing to the scene's fxml-file
     * @return parent of the scene
     */
    public static Parent getParent(String sceneName){
        Parent root = null;

        try {
            root = FXMLLoader.load(WindowManager.class.getResource(sceneName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return root;
    }

    /**
     * This method is used to switch to a new scene
     * @param e the event triggering the scene switch
     * @param sceneName string variable pointing to the new scene's fxml-file
     */
    public static void switchScene(ActionEvent e, final String sceneName){
        Parent root = getParent(sceneName);
        Scene scene = ((Node) e.getSource()).getScene();
        scene.setRoot(root);
    }

    /**
     * This method is used to switch to a new Scene
     * @param scene the scene to switch to
     * @param sceneName string variable pointing to the new scene's fxml-file
     */
    public static void switchScene(Scene scene, final String sceneName){
        Parent root = getParent(sceneName);
        scene.setRoot(root);
    }

    /**
     * This method sets a new theme for the current scene
     * @param e the event triggering the theme switch
     * @param newTheme the new theme
     */
    public static void setTheme(ActionEvent e, final String newTheme){
        Scene scene = ((Node) e.getSource()).getScene();

        scene.getStylesheets().remove(currentTheme);
        scene.getStylesheets().add(newTheme);

        currentTheme = newTheme;
    }

    /**
     * This method sets the current theme for the current scene
     * @param scene the scene to set the theme to
     */
    public static void setTheme(Scene scene){
        scene.getStylesheets().add(currentTheme);
    }

    /**
     * This method is used to close a window
     * @param e the event triggering window to close
     */
    public static void closeWindow(ActionEvent e){
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * This method is used to show a dialog
     * @param e the event triggering the dialog
     * @param sceneName string variable pointing to dialog's fxml-file
     * @param title title of the dialog
     * @param width width of the dialog
     * @param height height of the dialog
     * @param modality defines the dialogs modality
     */
    private static void showDialog(Event e, final String sceneName, String title, int width, int height, Modality modality){
        Parent root = getParent(sceneName);

        Stage owner = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Stage stage = new Stage();

        Scene scene = new Scene(root);
        setTheme(scene);

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });

        stage.initOwner(owner);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setTitle(title);
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setScene(scene);
        stage.initModality(modality);
        stage.show();
    }

    /**
     * This method is used for showing the 'Quiz'-dialog
     */
    public static void showQuizDialog() {

        Parent root = getParent(QUIZ);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        setTheme(scene);

        stage.setTitle("Quiz");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * This method is used to show the 'Property'-dialog
     * @param e the event of clicking on a property
     * @param property the property to show in the dialog
     */
    public static void showPropertyDialog(Event e, Property property){
        showDialog(e,
                WindowManager.PROPERTY_DIALOG,
                "Property",
                270, 350,
                Modality.NONE);

        int[] prices = {
                property.getPrice(),
                property.getRent(),
                property.getMortgage(),
                property.getRent() * 2,
                property.getRent() * 3,
                property.getRent() * 4,
                property.getRent() * 5,
                property.getRent() * 8,
                property.getHousePrice(),
                property.getHotelPrice()
        };

        String username = (property.getOwner() != null) ? property.getOwner().getUsername() : "Nobody";

        String[] info = {
                property.getName(),
                username
        };

        ControllerMediator.getInstance().setColor(property.getColor());
        ControllerMediator.getInstance().setPriceLabels(prices);
        ControllerMediator.getInstance().setInfoLabels(info);
    }

    /**
     * This method is used to show 'Buy property'-dialog
     * @param e the event of landing on a property
     * @param property the property to buy
     */
    public static void showBuyPropertyDialog(Event e, Property property){
        showDialog(e,
                WindowManager.BUY_PROPERTY_DIALOG,
                "Buy property",
                320, 450,
                Modality.NONE);

        int[] prices = {
                property.getPrice(),
                property.getRent(),
                property.getMortgage(),
                property.getRent() * 2,
                property.getRent() * 3,
                property.getRent() * 4,
                property.getRent() * 5,
                property.getRent() * 8,
                property.getHousePrice(),
                property.getHotelPrice()
        };

        String username = (property.getOwner() != null) ? property.getOwner().getUsername() : "Nobody";

        String[] info = {
                property.getName(),
                username
        };

        ControllerMediator.getInstance().setColorBPC(property.getColor());
        ControllerMediator.getInstance().setPriceLabelsBPC(prices);
        ControllerMediator.getInstance().setInfoLabelsBPC(info);
    }

    /**
     * This method is used to alert when there is a turn change
     * @param nextPlayer the next player
     */
    public static void alertTurnChange(Player nextPlayer) {
        String nextUsername = nextPlayer.getUsername();
        WindowManager.displayAlertBox("Turn change",
                "Its now " + nextUsername + "'s turn");
    }

    /**
     * This method is used to alert when a new player joins a game
     * @param newPlayer the player joining the game
     */
    public static void alertPlayerJoin(Player newPlayer) {
        WindowManager.displayAlertBox("Player joined",
                newPlayer.getUsername() + " just joined the game");
    }


    /**
     * This method is used to show 'My properties'-dialog
     * @param e the event of clicking the 'View my properties'-button
     * @param props properties to be displayed
     */
    public static void showMyPropertiesDialog(ActionEvent e, Property[] props) {
        showDialog(e,
                WindowManager.MY_PROPERTIES_DIALOG,
                "Buy property",
                350, 500,
                Modality.NONE);

        Property property = props[myPropertyNr];

        int[] prices = {
                property.getPrice(),
                property.getRent(),
                property.getMortgage(),
                property.getRent() * 2,
                property.getRent() * 3,
                property.getRent() * 4,
                property.getRent() * 5,
                property.getRent() * 8,
                property.getHousePrice(),
                property.getHotelPrice()
        };

        String username = (property.getOwner() != null) ? property.getOwner().getUsername() : "Nobody";

        String[] info = {
                property.getName(),
                username
        };

        ControllerMediator.getInstance().setColorMPC(property.getColor());
        ControllerMediator.getInstance().setPriceLabelsMPC(prices);
        ControllerMediator.getInstance().setInfoLabelsMPC(info);
    }

    /**
     * This method is used to show a 'Wildcard'-dialog
     * @param e the event of landing on a wildcard field
     * @param card the wildcard to be displayed
     */
    public static void showCardDialog(Event e, Card card) {
        showDialog(e,
                WindowManager.CARD_DIALOG,
                "Buy property",
                270, 350,
                Modality.NONE);

        ControllerMediator.getInstance().setDescriptonLabel(card);
        ControllerMediator.getInstance().setTransactionLabel(card);
    }

    /**
     * This method is used to display the systems own standard alert box
     * @param header the title for the alert
     * @param content information about the alert
     */
    public static void displayAlertBox(String header, String content) {
        if(!ControllerMediator.getInstance().getInGame())
            return;

        Stage stage = new Stage();
        Parent root = getParent(INFO_DIALOG);
        Scene scene = new Scene(root);
        setTheme(scene);

        ControllerMediator.getInstance().setAlert(header, content);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    /**
     * This method is used to show the 'Rules'-dialog
     * @param e the event when the 'Rules'-button is clicked
     */
    public static void showRules(Event e){
        VBox root   =  new VBox();
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        final WebView browser = new WebView();
        final WebEngine eng = browser.getEngine();
        eng.load("https://gitlab.stud.idi.ntnu.no/mahmouim/monopoly/wikis/System-Documentation/User-manual");

        root.getChildren().addAll(browser);
        stage.setScene(scene);
        stage.setTitle("USER MANUAL");
        stage.show();
    }
 }