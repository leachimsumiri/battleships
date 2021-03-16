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

import java.util.logging.Level;
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
        LOGGER.log(Level.INFO, "GUI Initialization done");
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

    public final void resetShipImages(){
        for (int i = 0; i < Constants.TOTAL_SHIP_COUNT; i++) {
            this.getPlayer1ShipImages()[i].rotateTo(Direction.RIGHT);
            this.getPlayer2ShipImages()[i].rotateTo(Direction.RIGHT);
            this.getPlayer2ShipImages()[i].reset();
            this.getPlayer1ShipImages()[i].reset();
        }
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

        ImageView newMissImageView = new ImageView(Constants.MISS_IMAGE_PATH);
        newMissImageView.setX(field.getX());
        newMissImageView.setY(field.getY());

        this.rootPane.getChildren().add(newMissImageView);
        App.setGameRound(App.getGameRound()+1);
    }

    public void drawAttack(Field field, Field shipField, Player player) {
        int diffx = (int) shipField.getX() % 40;
        shipField.setX(shipField.getX() - diffx);

        int diffy = (int) shipField.getY() % 40;
        shipField.setY(shipField.getY() - diffy);

        ImageView newHitImageView = new ImageView(Constants.HIT_IMAGE_PATH);
        newHitImageView.setX(shipField.getX());
        newHitImageView.setY(shipField.getY());
        this.rootPane.getChildren().addAll(newHitImageView);

        Ship ship = player.playerGameField.isDestroyed(field);

        Image image;
        if (ship != null) {
            switch (ship.getLength()) {
                case Constants.FIELDLENGTH_OF_SMALL_SHIPS:
                    image = new Image(Constants.SMALL_SHIP_DESTROYED_IMAGE_PATH);
                    break;
                case Constants.FIELDLENGTH_OF_MEDIUM_SHIPS:
                    image =  new Image(Constants.MEDIUM_SHIP_DESTROYED_IMAGE_PATH);
                    break;
                case Constants.FIELDLENGTH_OF_LARGE_SHIPS:
                    image =  new Image(Constants.LARGE_SHIP_DESTROYED_IMAGE_PATH);
                    break;
                case Constants.FIELDLENGTH_OF_XLARGE_SHIPS:
                    image = new Image(Constants.XLARGE_SHIP_DESTROYED_IMAGE_PATH);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + ship.getLength());
            }

            Field enemyField = getAttackedEnemyField(ship, player);
            ShipImage destroyedEnemeyShipImage = new ShipImage(enemyField, ship.getLength(), image);
            this.rootPane.getChildren().add(destroyedEnemeyShipImage.getImageView());
            destroyedEnemeyShipImage.rotateTo(ship.getDirection());
            destroyedEnemeyShipImage.lock();
        }
    }

    private Field getAttackedEnemyField(Ship ship, Player player){
        int xInPixel = ship.getStartField().getX() * Constants.FIELD_PIXEL_SIZE;
        int yInPixel = ship.getStartField().getY() * Constants.FIELD_PIXEL_SIZE;

        int enemyXInPixel, enemyYInPixel;

        if (player == App.getPlayer1()) {
            enemyXInPixel = xInPixel + 2 * Constants.GAMEFIELD_SIZE + Constants.FIELD_PIXEL_SIZE + Constants.FIELD_PIXEL_SIZE;
            enemyYInPixel = yInPixel +  2 * Constants.FIELD_PIXEL_SIZE;
        } else if (player == App.getPlayer2()){
            enemyXInPixel = xInPixel + (Constants.GAMEFIELD_SIZE + Constants.FIELD_PIXEL_SIZE);
            enemyYInPixel = yInPixel + (2 * Constants.FIELD_PIXEL_SIZE);
        } else {
            throw new IllegalStateException("Unexpected player: " + player);
        }

        return new Field(enemyXInPixel - ship.getDivX(), enemyYInPixel - ship.getDivY());
    }

    private void setIslandImageViewProperties(){
        this.LEFT_ISLAND_IMAGE_VIEW.setX(Constants.GAMEFIELD_SIZE-1);
        this.LEFT_ISLAND_IMAGE_VIEW.setY(Constants.FIELD_PIXEL_SIZE-1 + Constants.GAMEFIELD_SIZE + Constants.FIELD_PIXEL_SIZE);
        this.RIGHT_ISLAND_IMAGE_VIEW.setX(Constants.GAMEFIELD_SIZE-1 + Constants.GAMEFIELD_SIZE + Constants.FIELD_PIXEL_SIZE);
        this.RIGHT_ISLAND_IMAGE_VIEW.setY(Constants.FIELD_PIXEL_SIZE-1 + 440 + Constants.FIELD_PIXEL_SIZE);
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
        this.SAVE_SHIPS_LEFT_BUTTON.setLayoutX(Constants.LEFT_INVENTORY_X_POS);
        this.SAVE_SHIPS_LEFT_BUTTON.setLayoutY(500);
        this.SAVE_SHIPS_LEFT_BUTTON.setPrefSize(120, 10);
    }

    private void setRightSaveButtonPropertier(){
        this.SAVE_SHIPS_RIGHT_BUTTON.setLayoutX(Constants.RIGHT_INVENTORY_X_POS);
        this.SAVE_SHIPS_RIGHT_BUTTON.setLayoutY(500);
        this.SAVE_SHIPS_RIGHT_BUTTON.setPrefSize(120, 10);
    }

    private void setShowPlayer1ShipsButtonProperties(){
        this.SHOW_PLAYER_1_SHIPS_BUTTON.setLayoutX(Constants.RIGHT_INVENTORY_X_POS);
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

    public void proceedAfterSaveShipsPlayer1(){
        this.changeMask();
        this.SAVE_SHIPS_LEFT_BUTTON.setVisible(false);
    }

    public void proceedAfterSaveShipsPlayer2(){
        this.SAVE_SHIPS_RIGHT_BUTTON.setVisible(false);
        this.changeMask();
        this.SHOW_PLAYER_1_SHIPS_BUTTON.setVisible(true);
        this.SHOW_PLAYER_2_SHIPS_BUTTON.setVisible(true);
        this.INDICATE_1.setVisible(true);
    }

    public void playWinningAnimations(int playerNumber){
        ImageView winnerImageView = playerNumber == 1 ? this.PLAYER_1_WON_IMAGE_VIEW : this.PLAYER_2_WON_IMAGE_VIEW;
        this.deactivateMask();

        this.SHOW_PLAYER_1_SHIPS_BUTTON.setVisible(false);
        this.SHOW_PLAYER_2_SHIPS_BUTTON.setVisible(false);
        this.RESET_BUTTON.setVisible(false);

        this.getRootPane().getChildren().add(winnerImageView);
        winnerImageView.setX(playerNumber == 1 ? 50 : 1450);
        winnerImageView.setY(520);

        AudioData.winnerPlayer.stop();
        AudioData.winnerPlayer.play();

        this.getRootPane().getChildren().add(this.CONTINUE_BUTTON);
        this.CONTINUE_BUTTON.setLayoutX(playerNumber == 1 ? 160 : 1520);
        this.CONTINUE_BUTTON.setLayoutY(850);
        this.CONTINUE_BUTTON.setVisible(true);
    }

    public void drawAttackMove(Player attackedPlayer, Player attackingPlayer, Field field, Field shipField){
        this.drawAttack(field, shipField, attackedPlayer);
        attackingPlayer.saveAttack(field);
        this.activateMask();
        AudioData.bombPlayer.stop();
        AudioData.bombPlayer.play();
    }

    public void drawMissMove(Player attackingPlayer, Field field, Field shipField){
        this.drawMiss(shipField);
        attackingPlayer.saveAttack(field);
        this.activateMask();
        this.INDICATE_1.setVisible(false);
        this.INDICATE_2.setVisible(true);
        AudioData.missPlayer.stop();
        AudioData.missPlayer.play();
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
