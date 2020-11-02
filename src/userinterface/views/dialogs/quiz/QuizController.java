package userinterface.views.dialogs.quiz;

import game.QuizInitializer;
import gamelogic.Player;
import gamelogic.Quiz;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import userinterface.ControllerMediator;
import userinterface.WindowManager;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;


/**
 *
 * @author Marcus
 */
public class QuizController implements Initializable {

    private Quiz[] quizzes = QuizInitializer.getQuizzes();
    private Quiz quiz;
    private Player player  = ControllerMediator.getInstance().getPlayer();

    public VBox quizArea           = new VBox();
    public TextFlow question       = new TextFlow();
    public Label firstAlternative  = new Label();
    public Label secondAlternative = new Label();

    public Button alt1 = new Button();
    public Button alt2 = new Button();

    @Override
    // TODO: Improve the quiz-view
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Puts a quiz and its alternatives into view
        quiz = quizzes[new Random().nextInt(quizzes.length)];
        Text t = new Text(quiz.getQuestion());
        question.getChildren().add(t);
        firstAlternative.setText(quiz.getAlternatives()[0]);
        secondAlternative.setText(quiz.getAlternatives()[1]);
    }

    public void alt1Pressed(ActionEvent actionEvent) {
        alertOutcome(actionEvent, 0);
        ControllerMediator.getInstance().updateMoneyLabel();
    }

    public void alt2Pressed(ActionEvent actionEvent) {
        alertOutcome(actionEvent, 1);
        ControllerMediator.getInstance().updateMoneyLabel();
    }

    // closes the quiz-view and shows alert
    private void alertOutcome(ActionEvent event, int answer) {
        WindowManager.closeWindow(event);

        String outcome = quiz.validateAnswer(player, quiz.getAlternatives()[answer]);
        quizArea.getChildren().add(new Text(outcome));

        WindowManager.displayAlertBox("What happened?", outcome);
    }
}