/**
 * Test of class Street
 */
package gamelogic.Tests;

import gamelogic.Card;
import gamelogic.Player;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


class CardTest {
    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
    Card card = new Card("Quiz",2,10);
    Player player = new Player("Mahmoud","MIB31");

    /*
     * Test of getDescription
     */
    @Test
    public void testGetDescription(){
        System.out.println("Card: getDescription");
        String exp = "Quiz";
        String end = card.getDescription();
        assertEquals(exp,end);
    }
    /*
     * Test of getPosition
     */
    @Test
    public void testGetPosition(){
        System.out.println("Card: testGetPosition");
        int exp = 10;
        int end = card.getPosition();
        assertEquals(exp,end);
    }
    /*
     * Test of getTransaction
     */
    @Test
    public void testGetTransaction(){
        System.out.println("Card: testGetTransaction");
        int exp = 2;
        int end = card.getTransaction();
        assertEquals(exp,end);
    }
    /*
     * Test of makeTransaction
     * Returns 5002 based on makeTransaction which adds 2 to players balance
     */
    @Test
    public void testMakeTransaction(){
        System.out.println("Card: testMakeTransaction");
        int exp = 5002;
        int end = player.transaction(card.getTransaction());
        assertEquals(exp,end);
    }
}
