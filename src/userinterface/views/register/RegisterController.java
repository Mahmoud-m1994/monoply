package userinterface.views.register;

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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import userinterface.AudioPlayer;
import userinterface.WindowManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class registers a user
 * @author Håkon
 */
public class RegisterController implements Initializable {
    public Label infoLabel = new Label();

    public TextField emailField    = new TextField();
    public TextField usernameField = new TextField();

    public PasswordField passwordField        = new PasswordField();
    public PasswordField confirmPasswordField = new PasswordField();

    public Button registerButton = new Button();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    /**
     * This method registers the user with the values they have given
     * @param e the event when the 'Register'-button is clicked
     */
    public void registerButtonClicked(ActionEvent e) {
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);

        //Gets the email
        String email = getEmail();
        if(email == null)
            return;

        //Gets the username
        String username = getUsername();
        if(username == null)
            return;

        //Gets the password
        String password = getPassword();
        if(password == null)
            return;

        //Temporarily disables the register button
        registerButton.setDisable(true);

        //Sets mouse cursor to 'NONE'
        Scene scene = registerButton.getScene();
        scene.setCursor(Cursor.NONE);

        //Attempts to register the user
        Task task = new Task () {
            @Override
            protected Integer call(){
                System.out.println("REGISTERING");
                return Database.registerUser(email, username, password);
            }
        };

        task.setOnSucceeded(event -> {

            //Displays cursor and let the user press the register button again
            scene.setCursor(Cursor.DEFAULT);
            registerButton.setDisable(false);

            //Gets the result and either a) registers the user or b) displays error message
            int result = (int) task.getValue();
            switch(result){
                case Database.OK:             WindowManager.switchScene(e, WindowManager.REGISTERED);     break;
                case Database.EMAIL_TAKEN:    printError("Email is already taken");                 break;
                case Database.USERNAME_TAKEN: printError("Username is already taken");              break;
                case Database.DATABASE_ERROR: printError("Database error, please try again later"); break;
            }
        });

        new Thread(task).start();
    }

    /**
     * This method checks if the email entered is valid
     * @return email if valid input, null if invalid input
     */
    private String getEmail(){
        String email = emailField.getText().trim().toLowerCase();

        //Checks if email is valid
        if(email.length() < 6 || !Email.isValidEmail(email)){
            printError("Invalid email address");
            return null;
        }

        return email;
    }

    /**
     * This method checks if the username entered is valid
     * @return username if valid, null if invalid input
     */
    private String getUsername(){
        String username = usernameField.getText().trim().toLowerCase();

        //Checks that username is long enough
        if(username.length() < 3){
            printError("Invalid username");
            return null;
        }

        return username;
    }

    /**
     * This method checks if the passwords entered are valid
     * @return password if valid, null if invalid input
     */
    private String getPassword(){
        String password        = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

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

        return password;
    }

    /**
     * This method prints an error message
     * @param msg the message to be printed
     */
    private void printError(String msg){
        infoLabel.setTextFill(Paint.valueOf("#d0000e"));
        infoLabel.setText(msg);
    }

    /**
     * This method switches to the 'Login'-view
     * @param e the event when the 'Login'-button is clicked
     */
    public void loginClicked(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.switchScene(e, WindowManager.LOGIN);
    }
}