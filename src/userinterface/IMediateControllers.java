package userinterface;

import gamelogic.Card;
import gamelogic.Player;
import gamelogic.Property;
import gamelogic.Upgrade;
import userinterface.views.board.BoardController;
import userinterface.views.dialogs.buyproperty.BuyPropertyController;
import userinterface.views.dialogs.infodialog.InfoDialogController;
import userinterface.views.dialogs.myproperties.MyPropertiesController;
import userinterface.views.dialogs.property.PropertyController;
import userinterface.views.login.LoginController;
import userinterface.views.preloader.PreloaderController;
import userinterface.views.dialogs.wildcard.wildCardController;

import java.util.ArrayList;

/**
 * This class is an interface allowing communication between different controllers
 * @author Håkon, Marcus, Espen, Mathias
 */
public interface IMediateControllers {
    void registerLoginController(LoginController controller);
    void registerPreloaderController(PreloaderController controller);
    void registerBoardController(BoardController controller);
    void registerPropertyController(PropertyController controller);
    void registerwildCardController(wildCardController controller);
    void registerBuyPropertyController(BuyPropertyController controller);
    void registerMyPropertiesController(MyPropertiesController controller);
    void registerInfoDialogController(InfoDialogController controller);

    Player getPlayer();
    Property getProperty();
    Upgrade getUpgrade();

    String getUsername();
    ArrayList<Player> getOpponents();
    void setUsername(String username);

    int getGameID();

    void setPositon(Player player);
    void updatePlayers(ArrayList<Player> opponents);
    void awaitStart(boolean waiting);

    // Different methods for the different controllers
    void setPriceLabels(int[] prices);
    void setInfoLabels(String[] info);
    void setColor(String color);

    void setPriceLabelsBPC(int[] prices);
    void setInfoLabelsBPC(String[] info);
    void setColorBPC(String color);

    void setColorMPC (String color);
    void setPriceLabelsMPC (int[] prices);
    void setInfoLabelsMPC(String[] info);

    int getLevel();

    void setDescriptonLabel(Card card);
    void setTransactionLabel(Card card);

    void updateChat(int gameID);

    void updateMoneyLabel();

    void setAlert(String header, String content);

    void setInGame(boolean bool);
    boolean getInGame();
    }
