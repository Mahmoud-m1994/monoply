/**
 * Test of class Building
 */
package gamelogic.Tests;

import gamelogic.Building;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuildingTest {
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

    Building building = new Building(50);

    /*
     * Test of get/set price method
     */
    @Test
    public void testPrice(){
        System.out.println("Building: testPrice");
        building.setPrice(10);
        int exp = 10;
        int end = building.getPrice();
        assertEquals(exp, end);
    }
}
