package userinterface.views.settings;

import database.Database;
import database.Password;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Paint;
import userinterface.AudioPlayer;
import userinterface.ControllerMediator;
import userinterface.WindowManager;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is the controller for the settings view
 * It handles all the UI-interaction concerning the settings
 * @author Mathias, Mahmoud, Håkon
 */
public class SettingsController implements Initializable {

    public Label infoLabel = new Label();

    public TextField nicknameField = new TextField();
    public PasswordField passwordField = new PasswordField();
    public PasswordField conPasswordField = new PasswordField();
    public PasswordField oldPasswordField = new PasswordField();

    public Button changePasswordButton = new Button();
    public Button changeNicknameButton = new Button();
    public Button backButton = new Button();

    private String username = ControllerMediator.getInstance().getPlayer().getUsername();

    public void initialize(URL url, ResourceBundle resourceBundle){}

    /**
     * This method changes the password
     */
    public void changePasswordButtonClicked(){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);

        // Gets the password
        String pass = getPassword();
        if(pass == null) return;

        changePasswordButton.setDisable(true);

        //Sets mouse cursor to 'NONE'
        Scene scene = changePasswordButton.getScene();
        scene.setCursor(Cursor.NONE);

        Task task = new Task(){

            @Override
            protected Integer call(){
                System.out.println("CHANGIN PASSWORD");
                return Database.changePassword(username, pass);
            }
        };

        task.setOnSucceeded(event ->{

            scene.setCursor(Cursor.DEFAULT);
            changePasswordButton.setDisable(false);

            int result = (int) task.getValue();
            switch (result){
                case Database.OK: infoLabel.setText("Password updated"); break;
                case Database.DATABASE_ERROR: printError("Database error, please try again later"); break;
            }

        });

        new Thread(task).start();

    }

    /**
     * This method changes the nickname of a user
     */
    public void changeNicknameButtonClicked(){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);

        String nickname = nicknameField.getText();

        changeNicknameButton.setDisable(true);
        Scene scene = changeNicknameButton.getScene();
        scene.setCursor(Cursor.NONE);

        Task task = new Task(){
            @Override
            protected Integer call(){
                    return Database.changeNickname(username, nickname);
            }

        };

        task.setOnSucceeded(event ->{

                scene.setCursor(Cursor.DEFAULT);
                changeNicknameButton.setDisable(false);

                int result = (int) task.getValue();
                switch (result){
                    case Database.OK: infoLabel.setText("Nickname updated"); break;
                    case Database.DATABASE_ERROR: printError("Database error, please try again later"); break;
                }
        });

        new Thread(task).start();

    }

    /**
     * This method switches to the 'Options'-dialog
     * @param e the event when 'Back'-button is clicked
     */
    public void backButtonClicked(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.switchScene(e, WindowManager.OPTIONS);
    }

    /**
     * This method checks if the new password inputs are valid or not
     * @return a string with the password if valid, null if invalid.
     */
    private String getPassword(){
        String oldPass         = oldPasswordField.getText();
        String password        = passwordField.getText();
        String confirmPassword = conPasswordField.getText();

        //Passwords are not equal
        if(!password.equals(confirmPassword)){
            printError("Passwords are not equal");
            return null;
        }

        //Password is invalid
        if(!Password.validate(password)) {
            printError("Invalid password");
            return null;
        }

        if(Database.checkPassword(username, oldPass) == 5) {
            printError("Old password don't mach");
            return null;
        }

        return password;
    }

    /**
     * This method prints an error message
     * @param msg the message to print
     */
    private void printError(String msg){
        infoLabel.setTextFill(Paint.valueOf("#d0000e"));
        infoLabel.setText(msg);
    }
}