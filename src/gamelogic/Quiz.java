package gamelogic;

/**
 * The Quiz-class is used to create quizzes. A Quiz has a question,
 * an array of alternatives, a correct answer, a transaction and an array of outcomes.
 * @author Marcus
 */
public class Quiz {

    private String question;
    private String[] alternatives;
    private String correctAnswer;
    private int transaction;
    private String[] outcomes;

    public Quiz(String question, String[] alternatives, String correctAnswer, int transaction, String[] outcomes){
        this.question = question;
        this.alternatives = alternatives;
        this.correctAnswer = correctAnswer;
        this.transaction = transaction;
        this.outcomes = outcomes;

    }

    public String getQuestion() {
        return question;
    }

    public int getTransaction() { return transaction; }

    public String getCorrectAnswer() { return correctAnswer; }

    public String[] getAlternatives() {
        return alternatives;
    }

    private boolean isCorrect(String answer) {
        if(answer.equals(correctAnswer))
            return true;
        else
            return false;
    }

    /**
     * This method is used to validate if a players answer is correct or not,
     * and then make a transaction based on this.
     *
     * @param player This is the player giving an answer to the quiz
     * @param answer The players answer
     * @return This will return an outcome depending on the players answer
     */
    public String validateAnswer(Player player, String answer) {
        if(isCorrect(answer) == true) {
            player.transaction(transaction);
            return outcomes[0];
        }
        else {
            player.transaction(-transaction);
            return outcomes[1];
        }
    }

}
