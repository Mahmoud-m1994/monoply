package database;

import java.io.*;

/**
 * This class is used for storing user data locally (.txt file)
 * @author Håkon
 */
public class LocalStorage {
    private static final String FILENAME = "user.txt";

    /**
     * This method writes user data to the 'user.txt' file
     * @param username the username to be stored
     */
    public static void storeUserData(String username){
        try (FileWriter fileWriter = new FileWriter(FILENAME);
             BufferedWriter writer = new BufferedWriter(fileWriter)){

            writer.write(username);

        } catch (IOException e){
            System.out.println("Could not store user data");
            e.printStackTrace();
        }
    }

    /**
     * This method reads user data from the 'user.txt' file
     * @return username if on file, null if file is empty
     */
    public static String getUserData(){
        String username = null;

        try (FileReader fileReader = new FileReader(FILENAME);
             BufferedReader reader = new BufferedReader(fileReader)){

            username = reader.readLine();

        } catch (IOException e){
            System.out.println("Could not read user data");
            e.printStackTrace();
        }

        return username;
    }

    /**
     * This method clears the 'user.txt' file
     */
    public static void clearUserData(){
        try {
            new FileWriter(FILENAME).close();
        } catch (IOException e){
            System.out.println("Could not clear user data");
            e.printStackTrace();
        }
    }
}