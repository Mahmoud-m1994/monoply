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
 * @author Håkon, Marcus, Espen, Mathias
 * This class uses Mediator Pattern to communicate between controllers
 */
public class ControllerMediator implements IMediateControllers {
    private LoginController loginController;
    private PreloaderController preloaderController;
    private BoardController boardController;
    private PropertyController propertyController;
    private wildCardController wildCardController;
    private BuyPropertyController buyPropertyController;
    private MyPropertiesController myPropertiesController;
    private InfoDialogController infoDialogController;

    private String username = null;
    private boolean inGame  = false;

    @Override
    public void registerMyPropertiesController(MyPropertiesController controller) {
        this.myPropertiesController = controller;
    }

    @Override
    public void registerLoginController(LoginController controller) {
        this.loginController = controller;
    }

    @Override
    public void registerPreloaderController(PreloaderController controller) {
        this.preloaderController = controller;
    }

    @Override
    public void registerBoardController(BoardController controller) {
        this.boardController = controller;
    }

    @Override
    public void registerPropertyController(PropertyController controller) {
        this.propertyController = controller;
    }

    @Override
    public void registerBuyPropertyController(BuyPropertyController controller) {
        this.buyPropertyController = controller;
    }

    @Override
    public void registerwildCardController(wildCardController controller) {
        this.wildCardController = controller;
    }

    @Override
    public void registerInfoDialogController(InfoDialogController controller) {
        this.infoDialogController = controller;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void setUsername(String username){
        this.username = username;
    }

    @Override
    public Player getPlayer() {
        return preloaderController.getPlayer();
    }

    @Override
    public Property getProperty(){
        return preloaderController.getProperty();
    }

    @Override
    public  Upgrade getUpgrade() {
        return  preloaderController.getUpgrade();
    }

    @Override
    public int getLevel() {
        return preloaderController.getLevel();
    }

    @Override
    public void setPositon(Player player){
        boardController.setPosition(player);
    }

    @Override
    public int getGameID(){
        return preloaderController.getGameID();
    }

    @Override
    public ArrayList<Player> getOpponents(){
        return preloaderController.getOpponents();
    }

    @Override
    public void updatePlayers(ArrayList<Player> opponents){
        boardController.updatePlayers(opponents);
    }

    @Override
    public void setPriceLabels(int[] prices){
        propertyController.setPriceLabels(prices);
    }

    @Override
    public void setInfoLabels(String[] info){
        propertyController.setInfoLabels(info);
    }

    @Override
    public void setColor(String color){
        propertyController.setColor(color);
    }

    @Override
    public void setPriceLabelsBPC(int[] prices){
        buyPropertyController.setPriceLabelsBPC(prices);
    }

    @Override
    public void setInfoLabelsBPC(String[] info){
        buyPropertyController.setInfoLabelsBPC(info);
    }

    @Override
    public void setColorBPC(String color){
        buyPropertyController.setColorBPC(color);
    }


    @Override
    public void setColorMPC (String color) {
        myPropertiesController.setColor(color);
    }

    @Override
    public void setPriceLabelsMPC (int[] prices) {
        myPropertiesController.setPriceLabelsMPC(prices);
    }

    @Override
    public void setInfoLabelsMPC(String[] info) {
        myPropertiesController.setInfoLabelsMPC(info);
    }

    @Override
    public void setDescriptonLabel(Card card) {
        wildCardController.setDescriptonLabel(card);
    }

    @Override
    public void  setTransactionLabel(Card card) {
        wildCardController.setTransactionLabel(card);
    }

    @Override
    public void updateMoneyLabel() {
        boardController.updateMoneyLabel();
    }

    @Override
    public void awaitStart(boolean waiting) {
        boardController.awaitStart(false);
    }

    @Override
    public void setAlert(String header, String content) {
        infoDialogController.setAlert(header, content);
    }

    @Override
    public void setInGame(boolean bool){
        this.inGame = bool;
    }

    @Override
    public boolean getInGame(){
        return this.inGame;
    }

    @Override
    public void updateChat(int gameID){ boardController.updateChat(gameID);
    }

    //Singleton pattern
    private ControllerMediator(){ }

    public static ControllerMediator getInstance(){
        return ControllerMediatorHolder.INSTANCE;
    }

    private static class ControllerMediatorHolder{
        private static final ControllerMediator INSTANCE = new ControllerMediator();
    }
}