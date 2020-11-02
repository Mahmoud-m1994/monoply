package game;

import gamelogic.Property;
import gamelogic.Street;
import gamelogic.Card;

/**
 * This class is used to initialize the different fields on the board
 * @author Håkon, Espen
 */
public class FieldInitializer {
    private static Property[] properties = initializeProperties();
    private static Street[] streets      = initializeStreets();
    private static Card [] cards         = initializecards();
    private static String[] scenarios    = initializeScenarios();

    public static Property[] getProperties(){
        return properties;
    }

    public static Street[] getStreets(){
        return streets;
    }

    public static Card[] getWildcards() { return cards; }

    public static String[] getScenarios() {
        return scenarios;
    }

    /**
     * This method initializes all the properties
     * @return property array of all properties
     * @see Property
     */
    private static Property[] initializeProperties(){
        Property[] properties = {
                new Property(1,  "Fyresdal",    350,  "red"),
                new Property(2,  "Pollen",      450,  "red"),
                new Property(3,  "Tyholt",      350,  "blue"),
                new Property(5,  "Bodø",        500,  "blue"),
                new Property(6,  "Sandnes",     550,  "blue"),
                new Property(8,  "Mallorca",    600,  "green"),
                new Property(10, "Porsgrunn", 350,  "green"),
                new Property(11, "Russland",  500,  "green"),
                new Property(13, "Kalvskinnet", 400,  "purple"),
                new Property(15, "Lademoen",  300, "purple"),
                new Property(16, "Sameland",  350, "purple"),
                new Property(18, "Åre", 600, "yellow"),
                new Property(19, "Dyreparken", 700, "yellow"),
                new Property(20, "Måløy",     650, "yellow"),
                new Property(22, "Mosjøen",   800, "orange"),
                new Property(23, "Farsund",   750, "orange"),
                new Property(25, "Nordstrand",850, "darkblue"),
                new Property(27, "Ålesund",   900, "darkblue")
        };

        return properties;
    }

    /**
     * This method initializes all the streets
     * @return street array of all streets
     * @see Street
     */
    public static Street[] initializeStreets(){
        Street redStreet = new Street("red",
                new Property[] {
                    properties[0],
                    properties[1]
                });

        Street blueStreet = new Street("blue",
                new Property[] {
                    properties[2],
                    properties[3],
                    properties[4]
                });

        Street greenStreet = new Street("green",
                new Property[] {
                        properties[5],
                        properties[6],
                        properties[7]
                });

        Street purpleStreet = new Street("purple",
                new Property[] {
                        properties[8],
                        properties[9],
                        properties[10]
                });

        Street yellowStreet = new Street("yellow",
                new Property[] {
                        properties[11],
                        properties[12],
                        properties[13]
                });

        Street orangeStreet = new Street("orange",
                new Property[] {
                        properties[14],
                        properties[15],
                });

        Street darkBlueStreet = new Street("darkblue",
                new Property[] {
                        properties[16],
                        properties[17]
                });

        Street[] streets = { redStreet , blueStreet, greenStreet, purpleStreet, yellowStreet, orangeStreet, darkBlueStreet };

        return streets;
    }

    /**
     * This method initializes all the wildcards in the game
     * @return Returns a Card array of all wildcards
     * @see Card
     */
    public static Card[] initializecards(){
        //TODO Position må settes litt ettersom bretter blir mer konkret.
        Card [] cards = {
                new Card("Oh no!\nYour new toga is to small\nand you cant use it!\n500 (money) down the drain! ", -500, 0),
                new Card("Move directly to Arendal. \nYou take a trip to \"Pollen\" \nand relieve yourself in it\nbut get caught by the police!\nYou get a fixed fine on\n4000 (money).", -4000, 2),
                new Card("\"Mosjøen tørrfesk forening\"\ninvites you to a \nfree All you can eat buffet! \nMove directly to Mosjøen.", 0, 22),
                new Card("\"Porsgrunds Porselænsfabrik AS\"\n has record sales!\nYour 1000 (money) Porsgrunds porcelain collection\nis now worth double!", 1000, 0),
                new Card("\"Sushi x Kobe\" \npreforms at Samfundet tonight! \nPay for a ticket\nand move directly to Samfundet!", -50, 7),
                new Card("The worlds first \n\"Toga fashion week\" \nis to be hosted in Ålesund!\nAnd they want you, \ntoga extraordinaire, to host!\nMove directly to Ålesund\nand collect your paycheck!", 750, 27),
                new Card("Its time for a vacation!\nPay for a plane ticket\nand move directly\nto the most beautiful beaches\nin South Norway, Farsund", -100, 23),
                new Card("You receive a call from\n\"Tore på sporet!\"! He tells you that you\nhave a rich uncle in Belarus! \nSadly, he dies. \nBut not before writing \nyour name\nin his will! \nYou inherit 1000 money!", 1000, 0),
                new Card("Its that time of the year!\nMove directly to Åre\nand drink away 600 (money)!", -600, 18),
                new Card("After a night out you\nwake up in Dyreparken! \nIts like a dream. \nbut your wallet is gone.", -200, 19),
                new Card("You got hungry during\n Datatekknikk and decided to visit\n Geiris Superskalar Burgerbar.\n Pay the chef 200 for\n insane quick service", -200, 0),
                new Card("You are at Kiwi\nand, by accident, \nbuys a planeticket to mexico!\n But you miss your plane!", -300, 0)
        };

        return cards;
    }

    /**
     * This method is used to initialize the different String scenarios for the corner field "Samfundet"
     * @return a string array of all scenarios
     */
    private static String[] initializeScenarios() {
        String[] scenarios = {
                "You went a little overboard on the dance floor at Bodegaen and wake up the next morning with the worst " +
                        "hangover.. Skip a turn to recover from last nights festivities."
        };
        return scenarios;
    }
}