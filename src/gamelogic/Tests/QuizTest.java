/**
 * Test of class Quiz
 */
package gamelogic.Tests;

import gamelogic.Quiz;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuizTest {

    @BeforeAll
    public static void setUpClass(){}

    @AfterAll
    public static void tearDownClass() {}

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown() {}

    private String question = "Who is the best team?";
    private String[] alternatives = {"Team12,Team!!,Team??"};
    private String correctAnswer = "Team12";
    private int transaction = 10;
    private String[] outcomes = {"Stay at home","Go to NTNU"};

    Quiz quiz = new Quiz(question,alternatives,correctAnswer,transaction,outcomes);

    /*
     * Test of getQuestion
     */
    @Test
    public void testGetQuestion() {
        System.out.println("Quiz: testGetQuestion");
        String exp = "Who is the best team?";
        String end = quiz.getQuestion();
        assertEquals(exp,end);
    }

    /*
     * Test of getCorrectAnswer
     */
    @Test
    public void testGetCorrectAnswer() {
        System.out.println("Quiz: testGetCorrectAnswer");
        String exp = "Team12";
        String end = quiz.getCorrectAnswer();
        assertEquals(exp,end);
    }

    /*
     * Test of getTransaction
     */
    @Test
    public void testGetTransaction() {
        System.out.println("Quiz: testGetTransaction");
        int exp = 10;
        int end = quiz.getTransaction();
        assertEquals(exp,end);
    }
}
