/**
 * Test of class Property
 */
package gamelogic.Tests;

import gamelogic.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertyTest {
    Player player = new Player("Mahmoud","MIB31");
    Property property= new Property(2,"House",50,"Red");
    Building building = new Building(50);
    Upgrade upgrade = new Upgrade(property,building,3);


    @BeforeAll
    public static void setUpClass(){}

    @AfterAll
    public static void tearDownClass() {}

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown() {}

    /*
     * Test of getName method
     */
    @Test
    public void testGetNameName(){
        System.out.println("Property: testGetNameName");
        String exp = "House";
        String end = property.getName();
        assertEquals(exp, end);
    }
    /*
     * Test of getPrice
     */
    @Test
    public void getPrice(){
        System.out.println("Property: getPrice");
        int exp = 50;
        int end = property.getPrice();
        assertEquals(exp, end);
    }

    /*
     * Test of(get/set Owner,setPosition methods)
     */
    @Test
    public void testGetOwner(){
        System.out.println("Property: getOwner");
        player.setPosition(2); // the position of the property
        property.setOwner(player);
        String exp = "The nickname of the player: Mahmoud is MIB31 and his position is 2";
        String end = String.valueOf(property.getOwner());
        assertEquals(exp, end);
    }
    /*
     * Test of getColor
     */
    @Test
    public void testGetColor(){
        System.out.println("Property: testGetColor");
        String exp = "Red";
        String end = property.getColor();
        assertEquals(exp, end);
    }
    /*
     * Test of getHotelPrice
     */
    @Test
    public void testGetHotelPrice(){
        System.out.println("Property: testGetHotelPrice");
        int exp = 75;
        int end = property.getHotelPrice();
        assertEquals(exp, end);
    }
    /*
     * Test of getHousePrice
     */
    @Test
    public void testGetHousePrice(){
        Property property1= new Property(3,"House",25,"Red");
        System.out.println("Property: testGetHousePrice");
        int exp = 18;
        int end = property1.getHousePrice();
        assertEquals(exp, end);
    }
    /*
     * Test of getMortgage
     */
    @Test
    public void testGetMortgage(){
        System.out.println("Property: testGetMortgage");
        int exp = 37;
        int end = property.getMortgage();
        assertEquals(exp, end);
    }
    /*
     * Test of getRent
     */
    @Test
    public void testGetRent(){
        System.out.println("Property: testGetRent");
        int exp = 15;
        int end = property.getRent();
        assertEquals(exp, end);
    }
    /*
     * Test of getPosition
     */
    @Test
    public void testGetPosition(){
        System.out.println("Property: testGetPosition");
        int exp = 2;
        int end = property.getPosition();
        assertEquals(exp, end);
    }
    /*
     * Test of getCurrentRent
     *
     */
    @Test
    public void testGetCurrentRent(){
        System.out.println("Property: testGetCurrentRent");
        Property property3= new Property(2,"House",1,"Red");
        Building house1 = new House(0);
        House house2 = new House(20);
        Hotel hotel  = new Hotel(50);
        ArrayList<Building> buildings = new ArrayList<>();
        buildings.add(house1);
        boolean exp = true;
        boolean end = property3.addHouse();
        assertEquals(exp, end);
    }

    /*
     * Test1 of buyProperty
     * Exp is false because Balance < price
     */
    @Test
    public void testBuyProperty1(){
        System.out.println("Property: testBuyProperty1");
        player.setBalance(40);
        boolean exp = false;
        boolean end = property.GLbuyProperty(player);;
        assertEquals(exp, end);
    }

    /*
     * Test2 of buyProperty
     * Returns balance = 200 - 50 = 150
     */
    @Test
    public void testBuyProperty2(){
        System.out.println("Property: testBuyProperty2");
        player.setBalance(200);
        property.GLbuyProperty(player);
        int exp = 150;
        int end = player.getBalance();
        assertEquals(exp, end);
    }
    /*
     * Test of payRent
     * Returns false because player owns this property
     */
    @Test
    public void testPayRent(){
        System.out.println("Property: payRent");
        property.setOwner(player);
        boolean exp = false;
        boolean end = property.payRent(player);
        assertEquals(exp, end);
    }
}
