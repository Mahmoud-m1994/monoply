package userinterface.views.forgotpassword;

import database.Database;
import database.Email;
import database.Password;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import userinterface.AudioPlayer;
import userinterface.WindowManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is the controller for the forgotPassword view
 * @author Håkon
 */
public class ForgotPasswordController implements Initializable {
    public TextField emailField = new TextField();

    public Label infoLabel = new Label();

    public Button sendEmailButton = new Button();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    /**
     * This method switches to the 'Login'-view
     * @param e the event when the 'Login'-hyperlink is pressed
     */
    public void backToLoginClicked(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.switchScene(e, WindowManager.LOGIN);
    }

    /**
     * This method switches to the 'Register'-view
     * @param e The event when the 'Register'-hyperlink is pressed
     */
    public void createAccountClicked(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.switchScene(e, WindowManager.REGISTER);
    }

    /**
     * This method attempts to send an email to the user with a new password
     * It only sends the email if the user is registered
     * @param e the event when 'Send Email'-button is pressed
     */
    public void sendEmailButtonClicked(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);

        //Gets the email
        String email = getEmail();
        if(email == null)
            return;

        //Temporarily disables the send email button
        sendEmailButton.setDisable(true);

        //Sets mouse cursor to 'NONE'
        Scene scene = sendEmailButton.getScene();
        scene.setCursor(Cursor.NONE);

        //Attempts to register the user
        Task task = new Task () {
            @Override
            protected Integer call(){
                return Database.updatePassword(email, Password.generatePassword(10));
            }
        };

        task.setOnSucceeded(event -> {

            //Displays cursor and let the user press the register button again
            scene.setCursor(Cursor.DEFAULT);
            sendEmailButton.setDisable(false);

            //Gets the result and either a) redirects the user or b) Displays an error message
            int result = (int) task.getValue();
            switch(result){
                case Database.OK:             WindowManager.switchScene(e, WindowManager.CHECK_INBOX);    break;
                case Database.INVALID_EMAIL:  printError("No user is registered with this email");  break;
                case Database.DATABASE_ERROR: printError("Database error, please try again later"); break;
            }
        });

        new Thread(task).start();
    }

    /**
     * This method checks if the email entered is valid
     * @return the email if valid, null if invalid
     */
    private String getEmail(){
        String email = emailField.getText().trim();

        //Checks if email is valid
        if(email.length() < 6 || !Email.isValidEmail(email)){
            printError("Invalid email address");
            return null;
        }

        return email;
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