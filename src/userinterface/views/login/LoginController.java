package userinterface.views.login;

import database.Database;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import userinterface.AudioPlayer;
import database.LocalStorage;
import userinterface.ControllerMediator;
import userinterface.WindowManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is the controller for the login view
 * @author Håkon
 */
public class LoginController implements Initializable {
    public Label infoLabel = new Label();

    public TextField usernameField = new TextField();
    public PasswordField passwordField = new PasswordField();

    public Button loginButton = new Button();

    public CheckBox rememberMeBox = new CheckBox();

    /**
     * This method registers the login-controller in the controllerMediator
     * @see Initializable#initialize(URL, ResourceBundle)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControllerMediator.getInstance().registerLoginController(this);
    }

    /**
     * This method switches to the 'Options'-view
     * @param e the event when the 'Options'-button is clicked
     */
    public void optionsButtonClicked(ActionEvent e) {
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.switchScene(e, WindowManager.OPTIONS);
    }

    /**
     * This method attempts to login the user
     * @param e the event when 'Login'-button is clicked.
     */
    public void loginButtonClicked(ActionEvent e) {
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);

        //Gets the username and password
        String username = usernameField.getText().trim().toLowerCase();
        String password = passwordField.getText();

        //Makes sure username and password are of the right length
        if (username.length() < 3 || password.length() < 6) {
            printError("Incorrect username or password");
            return;
        }

        //Temporarily disables the login button
        loginButton.setDisable(true);

        //Sets cursor to 'NONE'
        Scene scene = loginButton.getScene();
        scene.setCursor(Cursor.NONE);

        //Attempts to log in the user
        Task task = new Task() {
            @Override
            protected Integer call() {
                int result = 0;
                try {
                    result = Database.checkPassword(username, password);
                } catch (Exception e){
                    e.printStackTrace();
                }
                return result;
            }
        };

        //Handles the result of the login
        task.setOnSucceeded(event -> {

            //Display cursor and let the user press the button again
            scene.setCursor(Cursor.DEFAULT);
            loginButton.setDisable(false);

            //Gets the result and either a) logs the user in or b) displays error message
            int result = (int) task.getValue();
            switch (result) {
                case Database.OK:

                    //Stores the user if the 'Remember me' box is checked
                    if (rememberMeBox.isSelected())
                        LocalStorage.storeUserData(username);

                    ControllerMediator.getInstance().setUsername(username);
                    WindowManager.switchScene(e, WindowManager.PRELOADER);
                    break;
                case Database.DATABASE_ERROR:
                    printError("Database error, please try again later");
                    break;
                case Database.INCORRECT_PASSWORD_OR_USERNAME:
                    printError("Incorrect username or password");
                    break;
            }
        });

        new Thread(task).start();
    }

    /**
     * This method prints error message
     * @param msg the message to print
     */
    private void printError(String msg) {
        infoLabel.setTextFill(Paint.valueOf("#d0000e"));
        infoLabel.setText(msg);
    }

    /**
     * This method handles remember me functionality
     * If checked the user data should be written to file
     * during login
     * If not checked the user data should be completely cleared
     * @param e the event when the 'Remember me'-checkbox is pressed
     */
    public void rememberMeClicked(ActionEvent e) {
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);

        boolean checked = rememberMeBox.isSelected();

        if (!checked)
            LocalStorage.clearUserData();
    }

    /**
     * This method switches to the 'Create Account' view
     * @param e the event when the 'Create Account'-hyperlink is clicked.
     */
    public void createAccountClicked(ActionEvent e) {
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.switchScene(e, WindowManager.REGISTER);
    }

    /**
     * This method switches to the 'Forgot Password'-view
     * @param e the event when the 'Forgot Password'-hyperlink is clicked
     */
    public void forgotPasswordClicked(ActionEvent e) {
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.switchScene(e, WindowManager.FORGOT_PASSWORD);
    }
}