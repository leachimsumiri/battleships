package main;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class GameGUI {
    public static final Button saveShipsLeftButton = new Button(Constants.SAVE_SHIPS_TEXT);
    public static final Button saveShipsRightButton = new Button(Constants.SAVE_SHIPS_TEXT);
    public static final Button newGameButton = new Button(Constants.NEW_GAME_TEXT);
    public static final Button exitButton = new Button(Constants.END_GAME_TEXT);
    public static final Button resetButton = new Button(Constants.RESTART_GAME_TEXT);
    public static final Button showPlayer1ShipsButton = new Button(Constants.SHOW_OWN_SHIPS_TEXT);
    public static final Button showPlayer2ShipsButton = new Button(Constants.SHOW_OWN_SHIPS_TEXT);
    public static final Button continueButton = new Button(Constants.CONTINUE_TEXT);

    public static final ImageView startMenuImageView = new ImageView(Constants.START_MENU_IMAGE_PATH);
    public static final ImageView player1WonImageView = new ImageView(Constants.PLAYER1_WON_IMAGE_PATH);
    public static final ImageView player2WonImageView = new ImageView(Constants.PLAYER2_WON_IMAGE_PATH);
    public static final ImageView leftIslandImageView = new ImageView(Constants.LEFT_ISLAND_IMAGE_PATH);
    public static final ImageView rightIslandImageView = new ImageView(Constants.RIGHT_ISLAND_IMAGE_PATH);

    public void create(){

    }
}
