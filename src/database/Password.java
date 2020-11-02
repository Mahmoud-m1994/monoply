package database;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used for managing a password
 * @author Espen, Mahmoud, Håkon
 */
public class Password {
    private static final int iterations    = 20 * 1000;
    private static final int saltLen       = 32;
    private static final int desiredKeyLen = 256;

    /**
     * This method generates a random password
     * @param length the length of password
     * @return a random password
     */
    public static String generatePassword(int length){

        //Uses upper and lowercase letters + numbers to generate the password
        String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerChars = upperChars.toLowerCase();
        String numbers    = "0123456789";

        String input = upperChars + lowerChars + numbers;

        //Generates a random password as a char array
        Random random = new Random();
        char[] passwordArray = new char[length];
        for(int i = 0; i < length; i++)
            passwordArray[i]= input.charAt(random.nextInt(input.length()));

        //Converts to char array to String
        return String.valueOf(passwordArray);
    }

    /**
     * Used for validating a password (has to match a pattern)
     * @param password the password to validate
     * @return true if valid, false if invalid
     */
    public static boolean validate(String password){
        //Regex used for validating password
        Pattern pattern = Pattern.compile("(?=^.{6,}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s)[0-9a-zA-Z!@#$%^&*()]*$");
        Matcher matcher = pattern.matcher(password);

        //Password does not match the proper format
        if(!matcher.find())
            return false;

        return true;
    }

    /**
     * This method generates a salted PBKDF2 hash of given a plaintext password
     * @param password the password to obtain the salt from
     * @return a salted PBKDF2 hash
     * @throws Exception
     */
    public static String getSaltedHash(String password) throws Exception {
        byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);

        //Stores the salt with the password
        return Base64.getEncoder().encodeToString(salt) + "$" + hash(password, salt);
    }

    /**
     * This method checks whether given plaintext password corresponds to a stored salted hash of the password
     * @param password the password to check
     * @param stored the password stored (hashed)
     * @return true if a match, false if not a match
     * @throws Exception
     */
    public static boolean check(String password, String stored) throws Exception{
        String[] saltAndHash = stored.split("\\$");

        if (saltAndHash.length != 2)
            throw new IllegalStateException("The stored password must have the form 'salt$hash'");

        String hashOfInput = hash(password, Base64.getDecoder().decode(saltAndHash[0]));

        return hashOfInput.equals(saltAndHash[1]);
    }

    /**
     * This method hashes the password
     * @param password the password to hash
     * @param salt the salt used
     * @return a hashed password
     * @throws Exception
     */
    private static String hash(String password, byte[] salt) throws Exception {
        if (password == null || password.length() == 0)
            throw new IllegalArgumentException("Empty password!");

        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        SecretKey key = f.generateSecret(new PBEKeySpec(
                password.toCharArray(), salt, iterations, desiredKeyLen));

        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
}