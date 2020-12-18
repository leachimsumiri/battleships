package main;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.script.ScriptEngine;
import java.io.File;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

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

    public static void setIslandImageViewProperties(){
        LEFT_ISLAND_IMAGE_VIEW.setX(439);
        LEFT_ISLAND_IMAGE_VIEW.setY(39 + 440 + 40);
        RIGHT_ISLAND_IMAGE_VIEW.setX(439 + 440 + 40);
        RIGHT_ISLAND_IMAGE_VIEW.setY(39 + 440 + 40);
    }

    public static void initResetButton(Pane battleshipContainer, Stage stage){
        setResetButtonProperties();
        addResetButtonEventListener(battleshipContainer, stage);
    }

    private static void setResetButtonProperties(){
        RESET_BUTTON.setLayoutX(440);
        RESET_BUTTON.setLayoutY(10);
        RESET_BUTTON.setPrefHeight(10);
    }

    private static void addResetButtonEventListener(Pane battleshipContainer, Stage stage){
        RESET_BUTTON.setOnAction(event -> {
            //TODO evtl. sinnvoller aufteilen
            Main main = new Main();
            main.resetGame();

            GameGUI.updateStage(battleshipContainer, stage);
        });
    }

    private static void setNewGameButtonProperties(){
        GameGUI.NEW_GAME_BUTTON.setLayoutX(700);
        GameGUI.NEW_GAME_BUTTON.setLayoutY(300);
        GameGUI.NEW_GAME_BUTTON.setMinSize(400, 150);
        Font font = new Font(30);
        GameGUI.NEW_GAME_BUTTON.setFont(font);
    }

    private static void addNewGameButtonEventListener(Pane battleshipContainer, Stage stage){
        GameGUI.NEW_GAME_BUTTON.setOnAction(event -> {
            Main main = new Main();
            main.resetGame();

            GameGUI.updateStage(battleshipContainer, stage);
        });
    }

    public static Scene createNewScene(Pane battleshipContainer){
        return new Scene(battleshipContainer, Constants.FIXED_GAMEWINDOW_WIDTH, Constants.FIXED_GAMEWINDOW_HEIGHT);
    }

    public static void updateStage(Pane battleshipContainer, Stage stage){
        Scene scene = GameGUI.createNewScene(battleshipContainer);
        stage.setScene(scene);
        stage.show();
    }
}
