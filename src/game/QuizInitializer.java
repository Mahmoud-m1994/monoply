package game;

import gamelogic.Quiz;

/**
 * This class is used to initialize a list of quizzes
 * @author Marucs
 * @see Quiz
 */
public class QuizInitializer {

    private static Quiz[] quizzes = initializeQuizzes();

    public static Quiz[] getQuizzes() {
        return quizzes;
    }

    /**
     * This method is used to initialize an array of Quizzes
     * @return a quiz array
     */
    private static Quiz[] initializeQuizzes() {

        //Alternatives
        String[][] alternatives = new String[][] {
                new String[]{"1: Keep calm and walk home", "2: Take a walk and try again"},
                new String[]{"1: 22?", "2: Is this a trick question?"},
                new String[]{"1: Accept", "2: Decline"},
                new String[]{"1: Mallorca", "2: Stay home"},
                new String[]{"1: Yes", "2: No"}
        };

        //Outcomes
        String[][] outcomes = new String[][] {
                new String[]{"Well.. At least you saved some cash. 200(money) you had set aside goes straight into" +
                        " your account.", "What the he... The bouncer still won't let you in, and you're stuck in a loop" +
                        " outside samfundet. Pay 200(money) for a taxi and get outta here."},
                new String[]{"Good job, but don't get cocky. Everyone should know this. Receive 10(money) for every " +
                        "visit to the drunk cell.", "Jeeeeesus... Don't you ever watch TV? Pay a fine of 10(money) for " +
                        "every visit to the drunk cell"},
                new String[]{"Wow.. You may have really missed out on something there.. Still, receive 2000(money) for" +
                        " all the \"tørrfesk\" you will never buy.", "Wow! That felt great didn't it?! Too bad you became" +
                        " addicted.. Pay 2000(money) for a 3 year supply of \"tørrfesk\"."},
                new String[]{"You stay at home and it's just as depressing as you feared. On " +
                        " the bright side, you finally won something on the lottery. Receive 3000(money)", "You " +
                        "travel to Mallorca with the boys/girls, but enjoying shots with Charter Svein isn't free.. " +
                        "Pay 3000(money)."},
                new String[]{"Turns out James' first name is Lebron, so of course he makes the dunk with ease." +
                        " Receive 500(money) from your other friends, who obviously doesn't watch the NBA.", "Turns out " +
                        "James' first name is Lebron, so of course he makes the dunk with ease. Pay 500(money)."}
        };

        Quiz[] quizzes = {
                new Quiz("Damn!! The bouncer won't let you into samfundet. What do you do?", alternatives[0]
                        , alternatives[0][0], 200, outcomes[0]),
                new Quiz("How many times has that one norwegian dude been to the drunk cell???!!!",
                        alternatives[1], alternatives[1][0], 220, outcomes[1]),
                new Quiz("You're at a kick ass party in Mosjøen, and this guy called Marcus offers you " +
                        "some \"tørrfæsk\"! Do you accept it?", alternatives[2], alternatives[2][1],2000,
                         outcomes[2]),
                new Quiz("Finally! You've been give some time of work. Do you wish to take a trip to Mallorca " +
                        "with the boys/girls, or do you choose a quiet week at home?", alternatives[3],
                        alternatives[3][1], 3000, outcomes[3]),
                new Quiz("You're out playing some basketball with your friends. You guys are making a bet. " +
                        "Do you think James can make a windmill dunk from the free throw line?", alternatives[4],
                        alternatives[4][0], 500, outcomes[4])
        };

        return quizzes;
    }
}