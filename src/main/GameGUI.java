package main;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class GameGUI {
    public static final Button SAVE_SHIPS_LEFT_BUTTON = new Button(Constants.SAVE_SHIPS_TEXT);
    public static final Button SAVE_SHIPS_RIGHT_BUTTON = new Button(Constants.SAVE_SHIPS_TEXT);
    public static final Button NEW_GAME_BUTTON = new Button(Constants.NEW_GAME_TEXT);
    public static final Button EXIT_BUTTON = new Button(Constants.END_GAME_TEXT);
    public static final Button RESET_BUTTON = new Button(Constants.RESTART_GAME_TEXT);
    public static final Button SHOW_PLAYER_1_SHIPS_BUTTON = new Button(Constants.SHOW_OWN_SHIPS_TEXT);
    public static final Button SHOW_PLAYER_2_SHIPS_BUTTON = new Button(Constants.SHOW_OWN_SHIPS_TEXT);
    public static final Button CONTINUE_BUTTON = new Button(Constants.CONTINUE_TEXT);

    public static final ImageView START_MENU_IMAGE_VIEW = new ImageView(Constants.START_MENU_IMAGE_PATH);
    public static final ImageView PLAYER_1_WON_IMAGE_VIEW = new ImageView(Constants.PLAYER1_WON_IMAGE_PATH);
    public static final ImageView PLAYER_2_WON_IMAGE_VIEW = new ImageView(Constants.PLAYER2_WON_IMAGE_PATH);
    public static final ImageView LEFT_ISLAND_IMAGE_VIEW = new ImageView(Constants.LEFT_ISLAND_IMAGE_PATH);
    public static final ImageView RIGHT_ISLAND_IMAGE_VIEW = new ImageView(Constants.RIGHT_ISLAND_IMAGE_PATH);

    public static final Rectangle INDICATE_1 = new Rectangle(439, 481, 442, 7);
    public static final Rectangle INDICATE_2 = new Rectangle(919, 481, 442, 7);

    public void create(){

    }
}
