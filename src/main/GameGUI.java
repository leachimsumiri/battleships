package main;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class GameGUI {
    private final static Logger LOGGER = Logger.getLogger(GameGUI.class.getName());

    public final Button SAVE_SHIPS_LEFT_BUTTON = new Button(Constants.SAVE_SHIPS_TEXT);
    public final Button SAVE_SHIPS_RIGHT_BUTTON = new Button(Constants.SAVE_SHIPS_TEXT);
    public final Button NEW_GAME_BUTTON = new Button(Constants.NEW_GAME_TEXT);
    public final Button EXIT_BUTTON = new Button(Constants.END_GAME_TEXT);
    public final Button RESET_BUTTON = new Button(Constants.RESTART_GAME_TEXT);
    public final Button SHOW_PLAYER_1_SHIPS_BUTTON = new Button(Constants.SHOW_OWN_SHIPS_TEXT);
    public final Button SHOW_PLAYER_2_SHIPS_BUTTON = new Button(Constants.SHOW_OWN_SHIPS_TEXT);
    public final Button CONTINUE_BUTTON = new Button(Constants.CONTINUE_TEXT);

    public final ImageView START_MENU_IMAGE_VIEW = new ImageView(Constants.START_MENU_IMAGE_PATH);
    public final ImageView PLAYER_1_WON_IMAGE_VIEW = new ImageView(Constants.PLAYER1_WON_IMAGE_PATH);
    public final ImageView PLAYER_2_WON_IMAGE_VIEW = new ImageView(Constants.PLAYER2_WON_IMAGE_PATH);
    public final ImageView LEFT_ISLAND_IMAGE_VIEW = new ImageView(Constants.LEFT_ISLAND_IMAGE_PATH);
    public final ImageView RIGHT_ISLAND_IMAGE_VIEW = new ImageView(Constants.RIGHT_ISLAND_IMAGE_PATH);
    public final ImageView MISS_IMAGE_VIEW = new ImageView(Constants.MISS_IMAGE_PATH);
    public final ImageView HIT_IMAGE_VIEW = new ImageView(Constants.HIT_IMAGE_PATH);

    public final Image SMALL_SHIP_DESTROYED_IMAGE = new Image(Constants.SMALL_SHIP_DESTROYED_IMAGE_PATH);
    public final Image MEDIUM_SHIP_DESTROYED_IMAGE = new Image(Constants.MEDIUM_SHIP_DESTROYED_IMAGE_PATH);
    public final Image LARGE_SHIP_DESTROYED_IMAGE = new Image(Constants.LARGE_SHIP_DESTROYED_IMAGE_PATH);
    public final Image XLARGE_SHIP_DESTROYED_IMAGE = new Image(Constants.XLARGE_SHIP_DESTROYED_IMAGE_PATH);

    public final Rectangle INDICATE_1 = new Rectangle(439, 481, 442, 7);
    public final Rectangle INDICATE_2 = new Rectangle(919, 481, 442, 7);

    private final Font font = new Font(30);

    private Pane rootPane = new Pane();
    private final Stage stage;

    private final ShipImage[] player1ShipImages;
    private final ShipImage[] player2ShipImages;

    public GameGUI(Stage stage){
        this.player1ShipImages = ShipImage.createInitialShipImages(Constants.PLAYER_1_INITIAL_FIELDS);
        this.player2ShipImages = ShipImage.createInitialShipImages(Constants.PLAYER_2_INITIAL_FIELDS);
        this.stage = stage;
        init();
    }

    public final void init(){
        this.setIslandImageViewProperties();
        this.reset();

        this.setResetButtonProperties();
        this.setNewGameButtonProperties();
        this.initExitButton();

        this.rootPane.getChildren().addAll(this.RESET_BUTTON, this.NEW_GAME_BUTTON, this.EXIT_BUTTON);

        this.updateStage();
    }

    public final void reset(){
        this.rootPane = new Pane();
        this.rootPane.setBackground(new Background(Constants.BACKGROUND_IMAGE));
        drawGUI();
    }

    private void drawGUI() {
        AudioData.musicPlayer.setCycleCount(500);
        AudioData.musicPlayer.play();

        for (int i = 0; i < Constants.TOTAL_SHIP_COUNT; i++) {
            this.rootPane.getChildren().add(this.player2ShipImages[i].getImageView());
            this.rootPane.getChildren().add(this.player1ShipImages[i].getImageView());
        }

        this.setLeftSaveButtonPropertier();
        this.setRightSaveButtonPropertier();

        this.START_MENU_IMAGE_VIEW.setVisible(true);

        this.setShowPlayer1ShipsButtonProperties();
        this.setShowPlayer2ShipsButtonProperties();

        this.setIndicatesColors();

        this.rootPane.getChildren().addAll(this.SHOW_PLAYER_1_SHIPS_BUTTON, this.SHOW_PLAYER_2_SHIPS_BUTTON, this.SAVE_SHIPS_LEFT_BUTTON, this.SAVE_SHIPS_RIGHT_BUTTON,
                this.LEFT_ISLAND_IMAGE_VIEW, this.RIGHT_ISLAND_IMAGE_VIEW, this.START_MENU_IMAGE_VIEW, this.INDICATE_1,
                this.INDICATE_2);

        this.hideElementsForStartmenu();

        changeMask();
    }

    public void drawMiss(Field field) {
        int diffx = field.getX() % Constants.FIELD_PIXEL_SIZE;
        field.setX(field.getX() - diffx);

        int diffy = field.getY() % Constants.FIELD_PIXEL_SIZE;
        field.setY(field.getY() - diffy);

        this.MISS_IMAGE_VIEW.setX(field.getX());
        this.MISS_IMAGE_VIEW.setY(field.getY());

        this.rootPane.getChildren().add(this.MISS_IMAGE_VIEW);
        App.setGameRound(App.getGameRound()+1);
    }

    public void drawAttack(Field field, Field shipField, Player player) {
        ShipImage shipImage;

        int diffx = (int) shipField.getX() % 40;
        shipField.setX(shipField.getX() - diffx);

        int diffy = (int) shipField.getY() % 40;
        shipField.setY(shipField.getY() - diffy);

        this.HIT_IMAGE_VIEW.setX(shipField.getX());
        this.HIT_IMAGE_VIEW.setY(shipField.getY());
        this.rootPane.getChildren().addAll(this.HIT_IMAGE_VIEW);

        Ship ship = player.playerGameField.isDestroyed(field);

        Image image;
        if (ship != null) {
            switch (ship.getLength()) {
                case Constants.FIELDLENGTH_OF_SMALL_SHIPS:
                    image = this.SMALL_SHIP_DESTROYED_IMAGE;
                    break;
                case Constants.FIELDLENGTH_OF_MEDIUM_SHIPS:
                    image = this.MEDIUM_SHIP_DESTROYED_IMAGE;
                    break;
                case Constants.FIELDLENGTH_OF_LARGE_SHIPS:
                    image = this.LARGE_SHIP_DESTROYED_IMAGE;
                    break;
                case Constants.FIELDLENGTH_OF_XLARGE_SHIPS:
                    image = this.XLARGE_SHIP_DESTROYED_IMAGE;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + ship.getLength());
            }

            //TODO calc function
            int x, y;
            //*40 um auf unsere Spielfeldkoordinaten zu kommen
            x = ship.getStartField().getX() * Constants.FIELD_PIXEL_SIZE;
            y = ship.getStartField().getY() * Constants.FIELD_PIXEL_SIZE;
            //Wird immer in das gegenüberliegende Feld gesetzt, deshalb stehen hier die Koordinaten vom Spieler 2
            if (player == App.getPlayer1()) {
                x += 2 * 440 + Constants.FIELD_PIXEL_SIZE + Constants.FIELD_PIXEL_SIZE;
                y += 2 * Constants.FIELD_PIXEL_SIZE;
            } else if (player == App.getPlayer2()){
                x += (440 + Constants.FIELD_PIXEL_SIZE);
                y += (2 * Constants.FIELD_PIXEL_SIZE);
            } else {
                throw new IllegalStateException("Unexpected player: " + player);
            }

            Field newField = new Field(x - ship.getDivx(), y - ship.getDivy());
            shipImage = new ShipImage(newField, ship.getLength(), image);
            this.rootPane.getChildren().add(shipImage.getImageView());
            shipImage.rotateTo(ship.getDirection());
            shipImage.lock();
        }
    }

    private void setIslandImageViewProperties(){
        this.LEFT_ISLAND_IMAGE_VIEW.setX(439);
        this.LEFT_ISLAND_IMAGE_VIEW.setY(39 + 440 + Constants.FIELD_PIXEL_SIZE);
        this.RIGHT_ISLAND_IMAGE_VIEW.setX(439 + 440 + Constants.FIELD_PIXEL_SIZE);
        this.RIGHT_ISLAND_IMAGE_VIEW.setY(39 + 440 + Constants.FIELD_PIXEL_SIZE);
    }

    private Scene createNewScene(Pane battleshipContainer){
        return new Scene(battleshipContainer, Constants.FIXED_GAMEWINDOW_WIDTH, Constants.FIXED_GAMEWINDOW_HEIGHT);
    }

    public void updateStage(){
        Scene scene = this.createNewScene(this.rootPane);
        this.stage.setScene(scene);
        this.stage.show();
    }

    public void activateMask() {
        this.LEFT_ISLAND_IMAGE_VIEW.setVisible(true);
        this.RIGHT_ISLAND_IMAGE_VIEW.setVisible(true);
    }

    public void deactivateMask() {
        this.LEFT_ISLAND_IMAGE_VIEW.setVisible(false);
        this.RIGHT_ISLAND_IMAGE_VIEW.setVisible(false);
    }

    public void changeMask() {
        if (App.getGameRound() % 2 == 1) {
            this.LEFT_ISLAND_IMAGE_VIEW.setVisible(false);
            this.RIGHT_ISLAND_IMAGE_VIEW.setVisible(true);
        } else if (App.getGameRound() % 2 == 0) {
            this.LEFT_ISLAND_IMAGE_VIEW.setVisible(true);
            this.RIGHT_ISLAND_IMAGE_VIEW.setVisible(false);
        }
    }


    private void setResetButtonProperties(){
        this.RESET_BUTTON.setLayoutX(440);
        this.RESET_BUTTON.setLayoutY(10);
        this.RESET_BUTTON.setPrefHeight(10);
    }

    private void setNewGameButtonProperties(){
        this.NEW_GAME_BUTTON.setLayoutX(700);
        this.NEW_GAME_BUTTON.setLayoutY(300);
        this.NEW_GAME_BUTTON.setMinSize(400, 150);
        this.NEW_GAME_BUTTON.setFont(this.font);
    }

    private void initExitButton(){
        this.setExitButtonProperties();
        this.addExitButtonEventListener();
    }

    private void setExitButtonProperties(){
        this.EXIT_BUTTON.setLayoutX(700);
        this.EXIT_BUTTON.setLayoutY(500);
        this.EXIT_BUTTON.setMinSize(400, 150);
        this.EXIT_BUTTON.setFont(this.font);
    }

    private void addExitButtonEventListener(){
        this.EXIT_BUTTON.setOnAction(event -> System.exit(0));
    }

    private void setLeftSaveButtonPropertier(){
        this.SAVE_SHIPS_LEFT_BUTTON.setLayoutX(1800 - 1520 - 3 * 40);
        this.SAVE_SHIPS_LEFT_BUTTON.setLayoutY(500);
        this.SAVE_SHIPS_LEFT_BUTTON.setPrefSize(120, 10);
    }

    private void setRightSaveButtonPropertier(){
        this.SAVE_SHIPS_RIGHT_BUTTON.setLayoutX(1520);
        this.SAVE_SHIPS_RIGHT_BUTTON.setLayoutY(500);
        this.SAVE_SHIPS_RIGHT_BUTTON.setPrefSize(120, 10);
    }

    private void setShowPlayer1ShipsButtonProperties(){
        this.SHOW_PLAYER_1_SHIPS_BUTTON.setLayoutX(1520);
        this.SHOW_PLAYER_1_SHIPS_BUTTON.setLayoutY(550);
        this.SHOW_PLAYER_1_SHIPS_BUTTON.setPrefSize(120, 10);
    }

    private void setShowPlayer2ShipsButtonProperties(){
        this.SHOW_PLAYER_2_SHIPS_BUTTON.setLayoutX(160);
        this.SHOW_PLAYER_2_SHIPS_BUTTON.setLayoutY(550);
        this.SHOW_PLAYER_2_SHIPS_BUTTON.setPrefSize(120, 10);
    }

    private void setIndicatesColors(){
        this.INDICATE_1.setFill(Color.RED);
        this.INDICATE_2.setFill(Color.RED);
    }

    private void hideElementsForStartmenu(){
        this.RESET_BUTTON.setVisible(false);
        this.LEFT_ISLAND_IMAGE_VIEW.setVisible(false);
        this.RIGHT_ISLAND_IMAGE_VIEW.setVisible(false);
        this.SHOW_PLAYER_1_SHIPS_BUTTON.setVisible(false);
        this.SHOW_PLAYER_2_SHIPS_BUTTON.setVisible(false);
        this.INDICATE_1.setVisible(false);
        this.INDICATE_2.setVisible(false);
    }

    public Pane getRootPane() {
        return rootPane;
    }

    public ShipImage[] getPlayer1ShipImages() {
        return player1ShipImages;
    }

    public ShipImage[] getPlayer2ShipImages() {
        return player2ShipImages;
    }
}
