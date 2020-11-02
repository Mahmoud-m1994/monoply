package gamelogic.Tests;
/*
 * Test of class Upgrade
 */

import gamelogic.Building;
import gamelogic.House;
import gamelogic.Property;
import gamelogic.Upgrade;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UpgradeTest {
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

    Property property = new Property(3,"Samfundet",200,"Red");
    Building building = new House(100);
    Upgrade  upgrade  = new Upgrade(property,building,0);

    /*
     * Test of get/set level methods
     */
    @Test
    public void testSetLevel(){
        System.out.println("Upgrade: testSetLevel");
        upgrade.setLevel(1);
        int exp = 1;
        int end = upgrade.getLevel();
        assertEquals(exp,end);

    }
    /*
     * Test of getPrice method
     */
    @Test
    public void testGetPrice(){
        System.out.println("Upgrade: testGetPrice");
        int exp = 100;
        int end = upgrade.getPrice();
        assertEquals(exp,end);
    }
}
