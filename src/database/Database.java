package database;

import gamelogic.Player;
import gamelogic.Property;
import userinterface.ControllerMediator;

import java.sql.*;
import java.util.ArrayList;

/**
 * The Database-class is the class that does all the
 * direct communication between the java-application and the database.
 * @author Håkon, Espen, Mathias, Marcus, Mahmoud
 */
public class Database {
    public final static int OK = 0;
    public final static int DATABASE_ERROR  = -1;
    public final static int EMAIL_TAKEN     = -2;
    public final static int USERNAME_TAKEN  = -3;
    public final static int INVALID_EMAIL   = -4;
    public final static int INVALID_GAME_ID = -5;
    public final static int INCORRECT_PASSWORD_OR_USERNAME = -6;

    private static ConnectionPool connectionPool = new ConnectionPool();

    private final static int MAX_PLAYERS = 4;

    public static void setListeners(int gameID, Player player){
        DatabaseListener.getActivePlayer(gameID);
        DatabaseListener.getOpponents(gameID, player);
    }

    public static void setChatListener(int gameId){
        DatabaseListener.getChat(gameId);
    }

    /**
     * This method attempts to log in the user.
     *
     * @param username The username is the local players input when loging in.
     * @param password The password is the local players input when loging in.
     * @return The method returns an integer based on the result of the method.
     */
    public static int checkPassword(String username, String password){
        String sql = "SELECT password FROM user WHERE username = ?";

        Connection connection = null;
        PreparedStatement getPassword = null;

        try {
            connection = connectionPool.getConnection();
            getPassword = connection.prepareStatement(sql);

            getPassword.setString(1, username);
            ResultSet res = getPassword.executeQuery();

            //Checks if there is a user with given username
            if(!res.next())
                return INCORRECT_PASSWORD_OR_USERNAME;

            String hashedPassword = res.getString(1);
            boolean passwordCorrect = Password.check(password, hashedPassword);

            //Checks if password is correct
            if(passwordCorrect)
                return OK;
            else
                return INCORRECT_PASSWORD_OR_USERNAME;

        } catch (SQLException e){
            System.out.println("Could not log in user!");
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(getPassword);
            DatabaseManager.closeConnection(connection);
        }

        return DATABASE_ERROR;
    }

    /**
     * This method attempts to register a new user.
     *
     * @param email The email is the local players input when trying to register.
     * @param username The username is the local players input when trying to register.
     * @param password The password is the local players input when trying to register.
     * @return The method returns an integer based on the result of the method.
     */
    //Attempts to register a user
    public static int registerUser(String email, String username, String password){
        String sql = "INSERT INTO user VALUES (?, ?, ?)";

        //Checks if email is available
        if(!emailAvailable(email))
            return EMAIL_TAKEN;

        //Checks if username is available
        if(!usernameAvailable(username))
            return USERNAME_TAKEN;

        Connection connection = null;
        PreparedStatement registerUser = null;

        try {
            connection = connectionPool.getConnection();

            registerUser = connection.prepareStatement(sql);

            registerUser.setString(1, email);
            registerUser.setString(2, username);
            registerUser.setString(3, Password.getSaltedHash(password));
            registerUser.executeUpdate();

            return OK;
        } catch (SQLException e){
            System.out.println("Could not register user!");
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("Could not get hashed password!");
            e.printStackTrace();
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(registerUser);
            DatabaseManager.closeConnection(connection);
        }

        return DATABASE_ERROR;
    }

    /**
     * This method check if an email address already is registered in the database
     *
     * @param email The email address that is being checked
     * @return Returns false if the email already in use, and true if it's available.
     */

    //Checks if a given email is available (not registered in the database)
    private static boolean emailAvailable(String email){
        String sql = "SELECT * FROM user WHERE email = ?";

        Connection connection = null;
        PreparedStatement getEmail = null;

        try {
            connection = connectionPool.getConnection();
            getEmail = connection.prepareStatement(sql);
            getEmail.setString(1, email);

            ResultSet res = getEmail.executeQuery();

            //Checks if email is in the database
            if(res.next())
                return false;

        } catch (SQLException e){
            System.out.println("Could not execute sql query");
            e.printStackTrace();
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(getEmail);
            DatabaseManager.closeConnection(connection);
        }

        return true;
    }

    /**
     * This method checks if an username is available, or already is registered in the database.
     *
     * @param username The username that is being checked.
     * @return Returns false if the username already is in use, and true if its available.
     */
    private static boolean usernameAvailable(String username){
        String sql = "SELECT * FROM user WHERE username = ?";

        Connection connection = null;
        PreparedStatement getUser = null;

        try {
            connection = connectionPool.getConnection();
            getUser = connection.prepareStatement(sql);
            getUser.setString(1, username);

            ResultSet res = getUser.executeQuery();

            //Checks if username is in the database
            if(res.next())
                return false;

        } catch (SQLException e){
            System.out.println("Could not execute sql query");
            e.printStackTrace();
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(getUser);
            DatabaseManager.closeConnection(connection);
        }

        return true;
    }

    /**
     * This method sets the position for a specific player in the database.
     *
     * @param username Takes the username for the player.
     * @param position Takes the new position for the player.
     * @return The method returns an integer based on the result of the method.
     */
    //Sets position of player
    public static int setPosition(String username, int position){
        String sql = "UPDATE player_game SET position = ? WHERE username = ?";

        Connection connection = null;
        PreparedStatement setPosition = null;

        try {
            connection = connectionPool.getConnection();
            setPosition = connection.prepareStatement(sql);

            setPosition.setInt(1, position);
            setPosition.setString(2, username);
            setPosition.executeUpdate();

        } catch (SQLException e){
            System.out.println("Could not execute sql query");
            e.printStackTrace();

            return DATABASE_ERROR;
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(setPosition);
            DatabaseManager.closeConnection(connection);
        }

        return OK;
    }

    /**
     * This method sets the AutoIncrement value for the chat back to 0 in the database.
     *
     * @return The method returns an integer based on the result of the method.
     */
    public static int setAutoIncrementValue(){
        String sql = "ALTER TABLE Message AUTO_INCREMENT = 0";
        Connection connection = null;
        PreparedStatement setValue = null;

        try {
            connection = connectionPool.getConnection();
            setValue = connection.prepareStatement(sql);
            setValue.executeUpdate();

        } catch (SQLException e){
            System.out.println("Could not execute sql query mm");
            e.printStackTrace();
            return DATABASE_ERROR;

        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(setValue);
            DatabaseManager.closeConnection(connection);
        }
        return OK;
    }

    /**
     * This method writes a new chat message to the database.
     *
     * @param gameId The gameId is the id of the game the message is connected to.
     * @param username The username is the username of the player that write the message.
     * @param msg The msg is a string with the message that is written.
     * @return The method returns an integer based on the result of the method.
     */
    //Sets message in chat table
    public static int setMessage(int gameId,String username,String msg){
        String sql = "INSERT INTO Message(game_id,username,text) VALUES (?,?,?)";
        Connection connection = null;
        PreparedStatement setMessage = null;

        try {
            connection = connectionPool.getConnection();
            setMessage = connection.prepareStatement(sql);
            //chat_id is 0 because it's auto increment in mySQL
            setMessage.setInt(1, gameId);
            setMessage.setString(2, username);
            setMessage.setString(3,msg);
            setMessage.executeUpdate();

        } catch (SQLException e){
            System.out.println("Could not execute sql query mm");
            e.printStackTrace();
            return DATABASE_ERROR;

        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(setMessage);
            DatabaseManager.closeConnection(connection);
        }

        return OK;
    }

    /**
     * This method gets the last/greatest messageId for a specific game from the database.
     *
     * @param gameId The gameId is the id of the game the messageId is connected to.
     * @return The method returns the last/greatest messageId.
     */
    public static int getMessageId(int gameId){
        int id = 0;
        String sql = "SELECT MAX(message_id) x FROM Message WHERE game_id=?";

        Connection connection = null;
        PreparedStatement getMessageId = null;

        try {
            connection = connectionPool.getConnection();
            getMessageId = connection.prepareStatement(sql);
            getMessageId.setInt(1, gameId);

            ResultSet res = getMessageId.executeQuery();

            if (res.next())
                id = res.getInt("x") +1;

        } catch (SQLException e) {
            System.out.println("Could not execute sql query DB 249");
            e.printStackTrace();
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(getMessageId);
            DatabaseManager.closeConnection(connection);
        }

        return id;
    }

    /**
     * This method gets the last message written to the database for a specific game.
     *
     * @param gameId The gameId is the id of the game the messageId is connected to.
     * @return The method returns a string with the last message, or null if there are no messages.
     */
    public static String getLastMessage(int gameId){
        String message = null;
        String sql = "SELECT * FROM Message WHERE message_id=?";

        Connection connection = null;
        PreparedStatement getLastMessage = null;

        try {
            connection = connectionPool.getConnection();
            getLastMessage = connection.prepareStatement(sql);
            getLastMessage.setInt(1, getMessageId(gameId) - 1);

            ResultSet res = getLastMessage.executeQuery();

            if (res.next())
                message = res.getString(3)+": "+res.getString(4);

        } catch (SQLException e) {
            System.out.println("Could not execute sql query her DB 287");
            e.printStackTrace();

        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(getLastMessage);
            DatabaseManager.closeConnection(connection);

        }

        return message;
    }

    /**
     * This method gets an username given an email address from the database.
     *
     * @param email The email address that you want the username for.
     * @return The method returns a string with the email, or null if the email address isn't registered.
     */
    private static String getUsername(String email){
        String sql = "SELECT username FROM user WHERE email = ?";
        String username = null;

        Connection connection = null;
        PreparedStatement getUsername = null;

        try {
            connection = connectionPool.getConnection();
            getUsername = connection.prepareStatement(sql);

            getUsername.setString(1, email);
            ResultSet res = getUsername.executeQuery();

            //Checks if username is in the database
            if(res.next())
                username = res.getString(1);

        } catch (SQLException e){
            System.out.println("Could not execute sql query");
            e.printStackTrace();
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(getUsername);
            DatabaseManager.closeConnection(connection);
        }

        return username;
    }

    /**
     * This method changes the nickname for a given username in the database.
     *
     * @param username The username for the player.
     * @param nickname The nickname that shall be changed to.
     * @return The method returns an integer based on the result of the method.
     */
    public static int changeNickname(String username, String nickname){
        String sql = "UPDATE player SET nickname = ? WHERE username = ?";

        Connection connection = null;
        PreparedStatement changeNickname = null;

        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);

            changeNickname = connection.prepareStatement(sql);
            changeNickname.setString(2, username);
            changeNickname.setString(1, nickname);
            changeNickname.executeUpdate();

            connection.commit();
        }
        catch (SQLException e){
            DatabaseManager.rollBack(connection);

            System.out.println("Could not execute sql query");
            e.printStackTrace();

            return DATABASE_ERROR;
        } catch (Exception e){
            System.out.println("Could not update Nickname");
            e.printStackTrace();

            return DATABASE_ERROR;
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(changeNickname);
            DatabaseManager.closeConnection(connection);
        }

        return OK;
    }

    /**
     * The method changes the password for a specific user in the database.
     *
     * @param username The username for player.
     * @param password The new password to be set.
     * @return The method returns an integer based on the result of the method.
     */
    public static int changePassword(String username, String password) {
        String sql = "UPDATE user SET password = ? WHERE username = ?";

        Connection connection = null;
        PreparedStatement changePassword = null;

        try{
            String hashedPassword = Password.getSaltedHash(password);

            connection = connectionPool.getConnection();

            changePassword = connection.prepareStatement(sql);
            changePassword.setString(1, hashedPassword);
            changePassword.setString(2, username);
            changePassword.executeUpdate();

        }
        catch (SQLException e){

            System.out.println("Could not execute sql query");
            e.printStackTrace();

            return DATABASE_ERROR;

        } catch (Exception e){
            System.out.println("Could not hash the password");
            e.printStackTrace();

            return DATABASE_ERROR;
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(changePassword);
            DatabaseManager.closeConnection(connection);
        }

        return OK;
    }

    /**
     * The method changes the password to a randomly generated password in the database
     * if the email address is registered, and send the password to the mail address given.
     *
     * @param email The mail address that is registered to the user.
     * @param password The new password to the user.
     * @return he method returns an integer based on the result of the method.
     *
     */
    //Updates password and sends mail to the user
    public static int updatePassword(String email, String password){
        String sql = "UPDATE user SET password = ? WHERE email = ?";

        //Gets the username, return null if no user with that email exists
        String username = getUsername(email);
        if(username == null)
            return INVALID_EMAIL;

        String msg = "Dear " + username + ", "
                + "\nYour new password is " + password + "."
                + "\nHave a nice day!"
                + "\n\n- Team 12";

        Connection connection = null;
        PreparedStatement updatePassword = null;

        try {
            String hashedPassword = Password.getSaltedHash(password);

            connection = connectionPool.getConnection();

            //Updates password in the database
            updatePassword = connection.prepareStatement(sql);
            updatePassword.setString(1, hashedPassword);
            updatePassword.setString(2, email);
            updatePassword.executeUpdate();

            //Sends email, if it fails to send email, rollback
            if(!Email.send(email, "Your new password", msg)){
                DatabaseManager.rollBack(connection);
                return DATABASE_ERROR;
            }

        } catch (SQLException e){

            System.out.println("Could not execute sql query");
            e.printStackTrace();

            return DATABASE_ERROR;
        } catch (Exception e){
            System.out.println("Could not hash the password");
            e.printStackTrace();

            return DATABASE_ERROR;
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(updatePassword);
            DatabaseManager.closeConnection(connection);
        }

        return OK;
    }

    /**
     * The method gets a list of opponents within the same game as a the local player from the database.
     *
     * @param gameID The gameID of the local game.
     * @param player The local player.
     * @return The method returns an ArrayList of opponent.
     */

    public static ArrayList<Player> getOpponents(int gameID, Player player){
        String sql = "SELECT username FROM player_game WHERE game_id = ? AND username <> ?";

        ArrayList<Player> opponents = new ArrayList<>();

        Connection connection = null;
        PreparedStatement getPlayers = null;

        try{
            connection = connectionPool.getConnection();
            getPlayers = connection.prepareStatement(sql);

            getPlayers.setInt(1, gameID);
            getPlayers.setString(2, player.getUsername());
            ResultSet res = getPlayers.executeQuery();

             while(res.next()) {
                 String opponentUsername = res.getString(1);
                 opponents.add(new Player(opponentUsername, opponentUsername));
             }
        } catch (SQLException e){
            System.out.println("Could not execute SQL-query");
            e.printStackTrace();
            return null;
        }  finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(getPlayers);
            DatabaseManager.closeConnection(connection);
        }

        return opponents;
    }

    /**
     * This method updates a lock variable in the database.
     *
     * @param username The username of the player where the lock is updated.
     * @param lock The value of the lock, 0 if false/not locked and 1 if true/locked.
     * @return The method returns an integer based on the result of the method.
     */
    public static int setLock(String username, int lock) {
        String sql = "UPDATE player_game SET locked = ? WHERE username = ?";

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement setLock = connection.prepareStatement(sql)){
            setLock.setInt(1, lock);
            setLock.setString(2, username);
            setLock.executeUpdate();
        } catch (SQLException e){
            System.out.println("Could not execute sql query");
            e.printStackTrace();

            return DATABASE_ERROR;
        }

        return OK;
    }

    /**
     * The method gets the next player in a game to take a turn form the database.
     *
     * @param gameID The gameId is the id for current game.
     * @param username The username is the username of the current player.
     * @return The method returns the username of the next player.
     */
    private static String getNextPlayer(int gameID, String username){
        String[] sql = {"SELECT username FROM player_game WHERE game_id = ?",
                        "SELECT locked FROM player_game WHERE game_id = ? AND username = ?"};

        String nextPlayer = null;
        Boolean locked    = null;

        ArrayList<String> usernames = new ArrayList<>();

        Connection connection = null;
        ResultSet res = null;
        PreparedStatement getUsernames = null;
        PreparedStatement getLocked    = null;

        try {
            connection = connectionPool.getConnection();
            getUsernames = connection.prepareStatement(sql[0]);
            getUsernames.setInt(1, gameID);

            res = getUsernames.executeQuery();

            res.first();
            if(!res.next()) {
            return null; }

            res.beforeFirst();
            while(res.next())
                usernames.add(res.getString(1));

            int index = 0;
            for(int i = 0; i < usernames.size(); i++)
                if(usernames.get(i).equals(username)){
                    if(i == usernames.size() - 1)
                        nextPlayer = usernames.get(0);
                    else {
                        index = i + 1;
                        nextPlayer = usernames.get(index);
                    }

                    break;
                }

            // Checks if next player is locked
            getLocked = connection.prepareStatement(sql[1]);
            getLocked.setInt(1, gameID);
            getLocked.setString(2, nextPlayer);
            res = getLocked.executeQuery();

            if(!res.next())
                System.out.println("Database is empty");

            // if next player is locked, move on to next player
            if(res.getInt(1) == 1) {
                System.out.println("Hoppet over spiller: " + nextPlayer);
                setLock(nextPlayer, 0);
                nextPlayer = getNextPlayer(gameID, nextPlayer);
            }
        } catch (SQLException e){
            System.out.println("Could not execute SQL-query");
            e.printStackTrace();
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(getUsernames);
            DatabaseManager.closeConnection(connection);
        }

        return nextPlayer;
    }

    /**
     * This method ends the turn for the active player by setting the next
     * player to take a turn as active player to the database.
     *
     * @param gameID The gameId is the id of the current game.
     * @param username The username is the username of the player that ends the turn.
     * @return The method returns an integer based on the result of the method.
     */
    public static int endTurn(int gameID, String username){
        String sql = "UPDATE game SET current_player = ? WHERE game_id = ?";

        Connection connection = null;
        PreparedStatement updatePosition      = null;
        PreparedStatement updateCurrentPlayer = null;

        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);

            String currentPlayer = getNextPlayer(gameID, username);

            updateCurrentPlayer = connection.prepareStatement(sql);
            updateCurrentPlayer.setString(1, currentPlayer);
            updateCurrentPlayer.setInt(2, gameID);
            updateCurrentPlayer.executeUpdate();

            connection.commit();
        } catch (SQLException e){
            DatabaseManager.rollBack(connection);

            System.out.println("Could not execute sql query");
            e.printStackTrace();

            return DATABASE_ERROR;
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(updatePosition);
            DatabaseManager.closeStatement(updateCurrentPlayer);
            DatabaseManager.closeConnection(connection);
        }

        return OK;
    }

    /**
     * This method updates the started variable for a given game in the database.
     *
     * @param gameID The gameId of the game to update.
     * @param state The state of the variable, true if started and false if not started.
     * @return The method returns an integer based on the result of the method.
     */
    public static int setStarted(int gameID, boolean state){
        String sql = "UPDATE game SET started = ? WHERE game_id = ?";

        Connection connection = null;
        PreparedStatement setStarted = null;

        try{
            connection = connectionPool.getConnection();
            setStarted = connection.prepareStatement(sql);
            setStarted.setBoolean(1, state);
            setStarted.setInt(2, gameID);
            setStarted.executeUpdate();
        } catch (SQLException e){
            System.out.println("Could not execute sql query");
            e.printStackTrace();

            return DATABASE_ERROR;
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(setStarted);
            DatabaseManager.closeConnection(connection);
        }
        return OK;
    }

    /**
     * This method check if the player is in the database, if not then put it in. Then connects a player to a game,
     * by finding a game with free space that is not started, or creating a new game if there are no games
     * that could be joined. It then sets the player in the game in the database,
     * and if needed creates the game in database.
     *
     * @param username The username is the username for the local player.
     * @return The method returns an integer based on the result of the method.
     */
    public static int connectToGame(String username){
        String[] sql = {
          "SELECT * FROM player WHERE username = ?",
          "INSERT INTO player VALUES (?, NULL)",
          "SELECT MAX(game_id) FROM game",
          "SELECT COUNT(*) FROM player_game WHERE game_id = ?",
          "INSERT INTO game VALUES (DEFAULT, NULL, false)",
          "INSERT INTO player_game VALUES (?, ?, 0, false, 5000)",
          "UPDATE game SET current_player = ? WHERE game_id = ?",
          "SELECT game_id FROM game WHERE started = 0"
        };

        Connection connection = null;
        PreparedStatement getPlayer           = null;
        PreparedStatement insertPlayer        = null;
        PreparedStatement getGameID           = null;
        PreparedStatement getPlayerCount      = null;
        PreparedStatement insertGame          = null;
        PreparedStatement insertPlayerGame    = null;
        PreparedStatement updateCurrentPlayer = null;

        ResultSet res = null;
        int gameID = 0;

        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);

            //Gets a player
            getPlayer = connection.prepareStatement(sql[0]);
            getPlayer.setString(1, username);
            res = getPlayer.executeQuery();

            //Checks if player is already in the database, if not -> put it in
            if(!res.next()){
                insertPlayer = connection.prepareStatement(sql[1]);
                insertPlayer.setString(1, username);
                insertPlayer.executeUpdate();
            }

            //Creates a new game if none exists
            getGameID = connection.prepareStatement(sql[7]);
            res = getGameID.executeQuery();

            if(!res.next()) {
                insertGame = connection.prepareStatement(sql[4]);
                insertGame.executeUpdate();
                getGameID = connection.prepareStatement(sql[7]);
                res = getGameID.executeQuery();
                res.next();
                gameID = res.getInt(1);

                //Inserts values into player_game
                insertPlayerGame = connection.prepareStatement(sql[5]);
                insertPlayerGame.setString(1, username);
                insertPlayerGame.setInt(2, gameID);
                insertPlayerGame.executeUpdate();

                //Sets current player
                updateCurrentPlayer = connection.prepareStatement(sql[6]);
                updateCurrentPlayer.setString(1, username);
                updateCurrentPlayer.setInt(2, gameID);
                updateCurrentPlayer.executeUpdate();

                connection.commit();
            }

            //Connects to existing game
            else {
                //Gets the gameID
                getGameID = connection.prepareStatement(sql[2]);
                res = getGameID.executeQuery();
                res.next();
                gameID = res.getInt(1);

                //Gets the amount of player in the game
                getPlayerCount = connection.prepareStatement(sql[3]);
                getPlayerCount.setInt(1, gameID);
                res = getPlayerCount.executeQuery();
                res.next();
                int playerCount = res.getInt(1);

                //Checks if game is full -> creates a new one
                if(playerCount >= MAX_PLAYERS || gameID == 0){
                    insertGame = connection.prepareStatement(sql[4]);
                    insertGame.executeUpdate();
                    gameID++;
                }

                //Inserts values into player_game
                insertPlayerGame = connection.prepareStatement(sql[5]);
                insertPlayerGame.setString(1, username);
                insertPlayerGame.setInt(2, gameID);
                insertPlayerGame.executeUpdate();

                connection.commit();
            }
        } catch (SQLException e){
            DatabaseManager.rollBack(connection);

            System.out.println("Could not execute sql query");
            e.printStackTrace();

            return DATABASE_ERROR;
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(getPlayer);
            DatabaseManager.closeStatement(insertPlayer);
            DatabaseManager.closeStatement(getPlayerCount);
            DatabaseManager.closeStatement(getGameID);
            DatabaseManager.closeStatement(insertGame);
            DatabaseManager.closeStatement(insertPlayerGame);
            DatabaseManager.closeStatement(updateCurrentPlayer);
            DatabaseManager.closeConnection(connection);
        }

        return gameID;
    }

    /**
     * This method gets the active player form a given game from the database.
     *
     * @param gameID The gameId is the id of the game where you want the active player from.
     * @return The method returns the active from the current game.
     */

    public static Player getActivePlayer(int gameID){
        String sql = "SELECT username, position "
                   + "FROM player_game JOIN game ON game.game_id = player_game.game_id "
                   + "WHERE player_game.username = game.current_player "
                   + "AND game.game_id = ?";

        Player player = null;

        Connection connection = null;
        PreparedStatement getActivePlayer = null;

        try {
            connection = connectionPool.getConnection();
            getActivePlayer = connection.prepareStatement(sql);
            getActivePlayer.setInt(1, gameID);
            ResultSet res = getActivePlayer.executeQuery();

            if(!res.next()) {
                return null;
            }

            res.beforeFirst();
            res.next();

            String username = res.getString(1);
            int position    = res.getInt(2);

            player = new Player(username, position);
        } catch (SQLException e){
            System.out.println("Could not execute sql query");
            e.printStackTrace();
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(getActivePlayer);
            DatabaseManager.closeConnection(connection);
        }

        return player;
    }

    /**
     * This method removes the player and objects connected to the player form the database.
     *
     * @param gameID The gameId is the id of the game where the player logout from.
     * @param username The username is the username of the player to logout.
     * @return The method returns an integer based on the result of the method.
     */

    public static int logOut(int gameID, String username){
        String[] sql = {
                "DELETE FROM player_game WHERE username = ?",
                "DELETE FROM game WHERE game_id = ?",
                "UPDATE game SET current_player = ? WHERE game_id = ?",
                "DELETE FROM property WHERE owner = ? AND game_id = ?"
        };

        Connection connection = null;

        PreparedStatement removeFromPlayerGame = null;
        PreparedStatement removeFromGame = null;
        PreparedStatement updateCurrentPlayer  = null;
        PreparedStatement removeProperty = null;

        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);

            Statement stmt = connection.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");

            removeProperty = connection.prepareStatement(sql[3]);
            removeProperty.setString(1, username);
            removeProperty.setInt(2, gameID);
            removeProperty.executeUpdate();

            removeFromPlayerGame = connection.prepareStatement(sql[0]);
            removeFromPlayerGame.setString(1, username);
            removeFromPlayerGame.executeUpdate();

            stmt.execute("SET FOREIGN_KEY_CHECKS=1");
            stmt.close();

            String currentPlayer = getNextPlayer(gameID, username);

            if(currentPlayer == null) {
                deleteMessage(gameID);
                removeFromGame = connection.prepareStatement(sql[1]);
                removeFromGame.setInt(1, gameID);
                removeFromGame.executeUpdate();

            } else {

                updateCurrentPlayer = connection.prepareStatement(sql[2]);
                updateCurrentPlayer.setString(1, currentPlayer);
                updateCurrentPlayer.setInt(2, gameID);
                updateCurrentPlayer.executeUpdate();
            }

            connection.commit();

            ControllerMediator.getInstance().setInGame(false);
            ControllerMediator.getInstance().setUsername(null);
        } catch (SQLException e){
            DatabaseManager.rollBack(connection);

            System.out.println("Could not execute sql query");
            e.printStackTrace();

            return DATABASE_ERROR;
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(removeProperty);
            DatabaseManager.closeStatement(removeFromPlayerGame);
            DatabaseManager.closeStatement(removeFromGame);
            DatabaseManager.closeStatement(updateCurrentPlayer);
            DatabaseManager.closeConnection(connection);
        }

        return OK;
    }

    /**
     * This method gets the started variable from the database.
     *
     * @param gameID The gameId of the game to check.
     * @return The method returns 0 if the game is not started and 1 if it is started,
     * and a negative int if a database error occurred.
     */

    public static int checkIfStarted(int gameID) {
        String sql = "SELECT started FROM game WHERE game_id = ?";
        int started;

        Connection connection = null;
        PreparedStatement checkStart = null;

        try{
            connection = connectionPool.getConnection();
            checkStart = connection.prepareStatement(sql);
            checkStart.setInt(1, gameID);
            ResultSet res = checkStart.executeQuery();

            if(!res.next())
                return INVALID_GAME_ID;

            started = res.getInt(1);

        } catch (SQLException e){
            System.out.println("Could not execute sql query");
            e.printStackTrace();

            return DATABASE_ERROR;
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(checkStart);
            DatabaseManager.closeConnection(connection);
        }
        return started;
    }

    /**
     * This method update the money in the database for the active player that have to pay,
     * and the player that owns the property the rent is payed for.
     *
     * @param property The property is the property the rent is payed for.
     * @return The method returns an integer based on the result of the method.
     */
    public static int payRent(Property property){
        String sql[] = {
                "UPDATE player_game SET money = ? WHERE username = ?",
                "SELECT money FROM player_game WHERE username = ?",
        };

        Connection connection = null;
        PreparedStatement addMoney = null;
        PreparedStatement payMoney = null;
        PreparedStatement getBalance = null;

        int money = property.getCurrentRent();
        int loseMoney = (money * -1);
        String playerTo = property.getOwner().getUsername();
        String playerFrom = ControllerMediator.getInstance().getUsername();

        int balanceFrom = ControllerMediator.getInstance().getPlayer().getBalance() + loseMoney;
        ControllerMediator.getInstance().getPlayer().transaction(loseMoney);

        int balanceTo;

        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);

            getBalance = connection.prepareStatement(sql[1]);
            getBalance.setString(1, playerTo);
            ResultSet res = getBalance.executeQuery();
            res.first();
            balanceTo = res.getInt(1);

            balanceTo += money;

            addMoney = connection.prepareStatement(sql[0]);
            addMoney.setInt(1, balanceTo);
            addMoney.setString(2, playerTo);
            addMoney.executeUpdate();

            payMoney = connection.prepareStatement(sql[0]);
            payMoney.setInt(1, balanceFrom);
            payMoney.setString(2, playerFrom);
            payMoney.executeUpdate();

            connection.commit();

        } catch (SQLException e){
            System.out.println("Could not execute sql query");
            e.printStackTrace();
            DatabaseManager.rollBack(connection);

            return DATABASE_ERROR;
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(getBalance);
            DatabaseManager.closeStatement(addMoney);
            DatabaseManager.closeStatement(payMoney);
            DatabaseManager.closeConnection(connection);
        }

        return OK;
    }

    /**
     * This method updates the database when a player buy a property
     * by updating the property object and the players balance.
     *
     * @param gameId The gameId is the id of the current game.
     * @param property The property is the property the player buy.
     * @param upgrade The upgrade sets the upgrade level for the property.
     * @param username The username is the username of the player that buy the property.
     * @return The method returns an integer based on the result of the method.
     */
    public static int buyPropertyDb(int gameId, Property property, int upgrade, String username){
        String[] sql ={
                "INSERT INTO property VALUES (?, ?, ?, ?)",
                "UPDATE player_game SET money = ? WHERE username = ?"
        };

        int position = property.getPosition();
        int balance = ControllerMediator.getInstance().getPlayer().getBalance() - property.getPrice();
        ControllerMediator.getInstance().getPlayer().transaction(-property.getPrice());

        Connection connection = null;
        PreparedStatement byProp = null;
        PreparedStatement doTransaction = null;

        try{
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);

            byProp = connection.prepareStatement(sql[0]);
            byProp.setInt(1, position);
            byProp.setInt(2, gameId);
            byProp.setString(3, username);
            byProp.setInt(4, upgrade);
            byProp.executeUpdate();

            doTransaction = connection.prepareStatement(sql[1]);
            doTransaction.setInt(1, balance);
            doTransaction.setString(2,username);
            doTransaction.executeUpdate();

            property.setOwner(ControllerMediator.getInstance().getPlayer());

            connection.commit();

        } catch (SQLException e){
            System.out.println("Could not execute sql query");
            e.printStackTrace();
            DatabaseManager.rollBack(connection);

            return DATABASE_ERROR;
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(byProp);
            DatabaseManager.closeStatement(doTransaction);
            DatabaseManager.closeConnection(connection);
        }

        return OK;
    }

    /**
     * This method check the database and gets the owner form a property if it is owned.
     *
     * @param gameId The gameId is the id of the current game.
     * @param position The position is the position of the property checked.
     * @return The method returns a string with the username of the owner if any, or null if no owner.
     */
    public static String checkOwnership(int gameId, int position){
        String sql = "SELECT owner FROM property WHERE game_id = ? AND position = ?";
        String owner;

        Connection connection = null;
        PreparedStatement getOwned = null;

        try {
            connection = connectionPool.getConnection();
            getOwned = connection.prepareStatement(sql);
            getOwned.setInt(1, gameId);
            getOwned.setInt(2, position);
            ResultSet res = getOwned.executeQuery();

            if(!res.next())
                return null;

            owner = res.getString(1);

        } catch (SQLException e) {
            System.out.println("Could not execute sql query");
            e.printStackTrace();

            return null;
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(getOwned);
            DatabaseManager.closeConnection(connection);
        }

        return owner;
    }

    /**
     * The method update the upgrade level for a property and the balance
     * of the player that buys it the in the database.
     *
     * @param gameId The gameId is the id of the current game.
     * @param property The property is the property where the upgrade is updated.
     * @param price The price is the price of the upgrade.
     * @param lvl The lvl is the new level of the upgrade.
     * @return The method returns an integer based on the result of the method.
     */
    public static int updateUpgradeDB(int gameId, Property property, int price, int lvl){
        String sql = "UPDATE property SET upgrade_lvl = ? WHERE game_id = ? AND position = ?";
        int position = property.getPosition();
        String owner = property.getOwner().getUsername();

        Connection connection = null;
        PreparedStatement setUpgrade = null;

        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            updateBalance(owner, price);

            setUpgrade = connection.prepareStatement(sql);
            setUpgrade.setInt(1, lvl);
            setUpgrade.setInt(2, gameId);
            setUpgrade.setInt(3, position);
            setUpgrade.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Could not execute sql query");
            e.printStackTrace();
            DatabaseManager.rollBack(connection);
            return DATABASE_ERROR;

        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(setUpgrade);
            DatabaseManager.closeConnection(connection);
        }

        return OK;
    }

    /**
     * The method gets the upgrade level for all the properties that is bought from the database
     * and update it local for the current player.
     *
     * @param properties The properties is an array of all the bought properties in the current game.
     * @param gameID The gameId is the id of the current game.
     * @return The method returns an integer based on the result of the method.
     */
    public static int getUpgrades (Property[] properties, int gameID) {
        String sql = "SELECT upgrade_lvl, position FROM property WHERE game_id = ?";

        Connection connection = null;
        PreparedStatement getUpgrades = null;

        try {
            connection = connectionPool.getConnection();

            getUpgrades = connection.prepareStatement(sql);
            getUpgrades.setInt(1, gameID);

            ResultSet res = getUpgrades.executeQuery();

            while(res.next()) {
                for (int i = 0; i < properties.length; i++) {
                    if (properties[i].getPosition() == res.getInt(2)) {
                        properties[i].setUpgrades(res.getInt(1));
                    }
                }
            }

            res.close();
        } catch (SQLException e) {
            System.out.println("Could not execute sql query");
            e.printStackTrace();
            return DATABASE_ERROR;
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(getUpgrades);
            DatabaseManager.closeConnection(connection);
        }

        return OK;
    }

    /**
     * The method updates the property and the players balance in the database when a player sells a property.
     *
     * @param property The property that the current player sells.
     * @param gameID The gameId is the id of the current game.
     * @param username The username is the username of the current player.
     * @return The method returns an integer based on the result of the method.
     */
    public static int sellProperty(Property property, int gameID, String username){
        String sql = "DELETE FROM property WHERE game_id = ? AND position = ?";
        int position = property.getPosition();
        int price = (int) (property.getPrice() * -0.75);

        Connection connection = null;
        PreparedStatement sellProperty = null;

        try {
            connection = connectionPool.getConnection();

            connection.setAutoCommit(false);
            updateBalance(username, price);
            sellProperty = connection.prepareStatement(sql);
            sellProperty.setInt(1, gameID);
            sellProperty.setInt(2, position);
            sellProperty.executeUpdate();

            property.setOwner(null);

            connection.commit();

        } catch (SQLException e) {
            DatabaseManager.rollBack(connection);
            System.out.println("Could not execute sql query");
            e.printStackTrace();
            return DATABASE_ERROR;
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(sellProperty);
            DatabaseManager.closeConnection(connection);
        }

        return OK;
    }

    /**
     * The method deleted all the messages connected to a player form the database.
     *
     * @param gameId The gameId is the id of the current game.
     //*@param username The username is the username of the current player.
     * @return The method returns an integer based on the result of the method.
     */
    public static int deleteMessage(int gameId){
        String sql = "DELETE FROM Message WHERE game_id = ?";

        Connection connection = null;
        PreparedStatement deleteMessage = null;

        try {
            connection = connectionPool.getConnection();

            Statement stmt = connection.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");

            deleteMessage = connection.prepareStatement(sql);
            deleteMessage.setInt(1, gameId);
            deleteMessage.executeUpdate();

            stmt.execute("SET FOREIGN_KEY_CHECKS=1");
            stmt.close();
        } catch (SQLException e){
            System.out.println("Could not execute sql query (delete message");
            e.printStackTrace();

            return DATABASE_ERROR;
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(deleteMessage);
            DatabaseManager.closeConnection(connection);
        }

        return OK;
    }

    /**
     * The method update the balance in the database for the current player.
     *
     * @param username The username is the username of the current player.
     * @param money The money is the amount to be changed. Positive if adding and negative if withdraw money.
     * @return The method returns an integer based on the result of the method.
     */
    public static int updateBalance(String username, int money){
        String sql = "UPDATE player_game SET money = ? WHERE username = ?";

        int balance = ControllerMediator.getInstance().getPlayer().getBalance() - money;
        ControllerMediator.getInstance().getPlayer().transaction(-money);
        Connection connection = null;
        PreparedStatement stat = null;

        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);

            stat = connection.prepareStatement(sql);
            stat.setInt(1, balance);
            stat.setString(2, username);
            stat.executeUpdate();

            connection.commit();

        } catch (SQLException e){
            System.out.println("Could not execute sql query");
            e.printStackTrace();
            DatabaseManager.rollBack(connection);

            return DATABASE_ERROR;
        } finally {
            DatabaseManager.setAutoCommit(connection);
            DatabaseManager.closeStatement(stat);
            DatabaseManager.closeConnection(connection);
        }

        return OK;
    }
}