/**
 * Test of class Bank
 */
package gamelogic.Tests;

import gamelogic.Bank;

import gamelogic.Player;
import gamelogic.Property;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


class BankTest {
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

    Player player1 = new Player("Mahmoud", "MIB31");
    Player player2 = new Player("Håkon","Haaka");
    Player player3 = new Player("Espen","espkal");
    Player[] players = {player1,player2};
    Property property= new Property(2,"House",50,"Red");
    Bank bank = new Bank();

    /*
     * Test of transaction from one to one
     */
    @Test
    public void testTransaction1(){
        System.out.println("Bank: testTransaction");
        bank.transaction(player2,player1,property.getPrice());
        int exp = 5050;
        int end = player2.getBalance();
        assertEquals(exp,end);
    }
    /*
     * Test of transaction from two players to one
     */
    @Test
    public void testTransaction2(){
        System.out.println("Bank: testTransaction2");
        bank.transaction(players,player3,property.getPrice());
        int exp = 4900;
        int end = player3.getBalance();
        assertEquals(exp,end);
    }
}