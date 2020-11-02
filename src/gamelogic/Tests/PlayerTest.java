/**
 * Test of class Player
 */
package gamelogic.Tests;

import gamelogic.Player;
import gamelogic.Property;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest{
    @BeforeAll
    public static void setUpClass(){}

    @AfterAll
    public static void tearDownClass() {}

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown() {}

    /*
     * Test of getUsername method
     */
    @Test
    public void testGetUsername(){
        System.out.println("Player: testGetUsername");
        Player player = new Player("Mahmoud","MIB31");
        String exp    = "Mahmoud";
        String end    = player.getUsername();
        assertEquals(exp,end);
    }

    /*
     * Test of getNickname
     */
    @Test
    public void testGetNickname(){
        System.out.println("Player: testGetNickname: ");
        Player player = new Player("Mahmoud","MIB31");
        String exp    = "MIB31";
        String end    = player.getNickname();
        assertEquals(exp,end);
    }
    /*
     * Test of setNickname method
     */
    @Test
    public void testSetNickname(){
        System.out.println("Player: testSetNickname");
        Player player= new Player("Mahmoud","MIB31");
        player.setNickname("MIB");
        String exp = "MIB";
        String end = player.getNickname();
        assertEquals(exp,end);
    }

    /*
     * Test of get/set position methods
     * @returns integer based on position to player
     */
    @Test
    public void testPosition(){
        System.out.println("Player: testPosition");
        Player player= new Player("Mahmoud",0);
        player.setPosition(10);
        int exp = 10;
        int end = player.getPosition();
        assertEquals(exp,end);
    }
    /*
     * Test of transaction method
     * @returns integer of balance after transaction method
     */
    @Test
    public void testTransaction(){
        System.out.println("Player: testTransaction");
        Player player= new Player("Mahmoud","MIB31");
        player.transaction(100);
        int exp = 5100;
        int end = player.getBalance();
        assertEquals(exp,end);
    }
    /*
     * Test of toString method
     * @returns string based on input from another methods
     */
    @Test
    public void testToString(){
        System.out.println("Player: testToString");
        Player player= new Player("Mahmoud","MIB31");
        player.setPosition(5);
        String exp = "The nickname of the player: Mahmoud is MIB31 and his position is 5";
        String end = player.toString();
        assertEquals(exp,end);
    }

    /*
     * Test of checkOwnership method
     * @returns true
     */
    @Test
    public void testCheckOwnership(){
        System.out.println("Player: testCheckOwnership");
        Player player= new Player("Mahmoud","MIB31");
        Property property= new Property(2,"House",50,"Red");
        property.setOwner(player);
        boolean exp = true;
        boolean end = player.checkOwnership(property);
        assertEquals(exp,end);
    }
}