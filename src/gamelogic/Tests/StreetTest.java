package gamelogic.Tests;
/*
 * Test of class Street
 */
import gamelogic.Player;
import gamelogic.Property;
import gamelogic.Street;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


class StreetTest {
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

    Property property1= new Property(1,"Kalvskinnet",100,"Red");
    Property property2= new Property(2,"Gløs",50,"Red");
    Property property3= new Property(3,"Samfundet",200,"Red");
    Property[] properties = {property1,property2,property3};
    Street street = new Street("Sverres Gate",properties);
    Player player = new Player("Mahmoud","MIB31");

    /*
     * Test of getName
     */
    @Test
    public void testGetNameName(){
        System.out.println("Street: testGetNameName");
        String exp = "Sverres Gate";
        String end = street.getName();
        assertEquals(exp, end);
    }

    /*
     * Test of canUpgrade
     *
     */
}
