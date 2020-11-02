package userinterface.views.feedback;

import database.Email;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import userinterface.AudioPlayer;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import userinterface.WindowManager;

/**
 * This class is the controller of the feedback view.
 * It handles all the UI-interaction concerning the settings.
 * @author Mathias
 */
public class FeedbackController implements Initializable {

    public Label infoLabel     = new Label();
    public TextField textField = new TextField();
    public TextArea textArea   = new TextArea();
    public Button sendButton   = new Button();

    private final String email = "monoplyteam12@gmail.com";

    public void initialize(URL url, ResourceBundle resourceBundle){}

    /**
     * This method handle the event when the send-button is clicked
     * It creates an email and sends it to the developers chosen mailbox
     * @param e the event when the 'Send'-button is clicked
     */
    public void sendButtonPressed(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);

        String title   = textField.getText();
        String message = textArea.getText();

        sendButton.setDisable(true);

        Scene scene = sendButton.getScene();
        scene.setCursor(Cursor.NONE);

        Task task = new Task(){

            @Override
            protected Boolean call(){
                return Email.send(email, title, message);
            }
        };
        task.setOnSucceeded(event -> {

            scene.setCursor(Cursor.DEFAULT);
            sendButton.setDisable(false);

            boolean emailSent = (boolean) task.getValue();
            if (emailSent){
                infoLabel.setText("Feedback sent");
                textField.clear();
                textArea.clear();
            }
            else
                printError("Database error, please try again later");
        });

        new Thread(task).start();
    }

    /**
     * This method switches to the 'Options'-view
     * @param e the event when the 'Back'-button is clicked
     */
    public void backButtonPressed(ActionEvent e){
        AudioPlayer.play(AudioPlayer.BUTTON_CLICK);
        WindowManager.switchScene(e, WindowManager.OPTIONS);
    }

    /**
     * This method prints an error message
     * @param msg he message to print
     */
    private void printError(String msg){
        infoLabel.setTextFill(Paint.valueOf("#d0000e"));
        infoLabel.setText(msg);
    }
}