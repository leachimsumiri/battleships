package main;

import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class App extends Application {
    private final static Logger LOGGER = Logger.getLogger(App.class.getName());

    private static final Player player1 = new Player();
    private static final Player player2 = new Player();

    private static int gameRound = 1;
    private boolean shipsComplete = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GameGUI gameGUI = new GameGUI(primaryStage);

        addEventlistener(gameGUI);
    }

    private void addEventlistener(GameGUI gameGUI){
        addResetButtonEventListener(gameGUI);
        addNewGameButtonEventListener(gameGUI);
        addContinueButtonEventListener(gameGUI);
        addFieldClickEventListener(gameGUI);
        addLeftSaveButtonEventListener(gameGUI);
        addRightSaveButtonEventListener(gameGUI);
        addShowPlayer1ShipsButtonEventListener(gameGUI);
        addShowPlayer2ShipsButtonEventListener(gameGUI);
    }

    private void saveShips(GameGUI gameGUI,ShipImage[] shipImages, Player player, int p1x, int p1y, int p2x, int p2y) {
        for (ShipImage shipImage : shipImages) {
            if (!shipImage.isLocked()) {
                Field field = Field.caclulateFieldFromImagePixels(shipImage.getField(), p1x, p1y, p2x, p2y);

                if (field != null) {
                    if (player.playerGameField.addShipToFleet(field, shipImage.getLength(), shipImage.getDirection(), shipImage.getDiffVectorX(), shipImage.getDiffVectorY())) {
                        if(player.playerGameField.isFleetComplete())
                            shipImage.lock();
                    } else {
                        // System.out.println("schiff nicht angelegt+ zurückseten");
                        //TODO check ob reset geht
                        shipImage.changePosition(new Field());
                        shipImage.rotateTo(Direction.RIGHT);
                    }
                } else {
                    //  System.out.println("null+zurücksetzen");
                    shipImage.changePosition(new Field());
                    shipImage.rotateTo(Direction.RIGHT);

                }
            } else {
                //   System.out.println("schiff deaktiviert");
            }
        }

        if (player.playerGameField.isFleetComplete()) {
            gameRound++;
            if (player == player1) {
                gameGUI.changeMask();
                gameGUI.SAVE_SHIPS_LEFT_BUTTON.setVisible(false);
            } else {
                gameGUI.SAVE_SHIPS_RIGHT_BUTTON.setVisible(false);
                gameGUI.changeMask();
                gameGUI.SHOW_PLAYER_1_SHIPS_BUTTON.setVisible(true);
                gameGUI.SHOW_PLAYER_2_SHIPS_BUTTON.setVisible(true);
                gameGUI.INDICATE_1.setVisible(true);
            }
            if (player1.playerGameField.isFleetComplete() && player2.playerGameField.isFleetComplete()) {
                gameGUI.activateMask();
            }
        }
    }

    private void executeAttack(GameGUI gameGUI, Field shipField) {
        Field field;
        if (!(player1.playerGameField.gameOver() || player2.playerGameField.gameOver())) {
            if (shipsComplete) {
                System.out.println("Schiffe fertig");
                if (gameRound % 2 == 1) {
                    field = Field.caclulateFieldFromImagePixels(shipField, 440 + 40, 40 + 40, 440 + 440, 440 + 40);

                    if (field != null) {
                        if (player1.wasPreviouslyAttackedOnField(field)) {
                            if (player2.playerGameField.attack(field)) {
                                gameGUI.drawAttack(field, shipField, player2);
                                player1.saveAttack(field);
                                gameGUI.activateMask();
                                AudioData.bombPlayer.stop();
                                AudioData.bombPlayer.play();

                            } else {
                                gameGUI.drawMiss(shipField);
                                player1.saveAttack(field);
                                gameGUI.activateMask();
                                gameGUI.INDICATE_1.setVisible(false);
                                gameGUI.INDICATE_2.setVisible(true);
                                AudioData.missPlayer.stop();
                                AudioData.missPlayer.play();
                            }
                        }
                    }

                    if (player2.playerGameField.gameOver()) {
                        System.out.println("Spieler 1 hat gewonnen");
                        gameGUI.deactivateMask();
                        gameGUI.SHOW_PLAYER_1_SHIPS_BUTTON.setVisible(false);
                        gameGUI.SHOW_PLAYER_2_SHIPS_BUTTON.setVisible(false);
                        gameGUI.RESET_BUTTON.setVisible(false);
                        gameGUI.getRootPane().getChildren().add(gameGUI.PLAYER_1_WON_IMAGE_VIEW);
                        gameGUI.PLAYER_1_WON_IMAGE_VIEW.setX(50);
                        gameGUI.PLAYER_1_WON_IMAGE_VIEW.setY(520);
                        AudioData.winnerPlayer.stop();
                        AudioData.winnerPlayer.play();
                        gameGUI.getRootPane().getChildren().add(gameGUI.CONTINUE_BUTTON);
                        gameGUI.CONTINUE_BUTTON.setLayoutX(160);
                        gameGUI.CONTINUE_BUTTON.setLayoutY(850);
                        gameGUI.CONTINUE_BUTTON.setVisible(true);
                    }

                } else {
                    field = Field.caclulateFieldFromImagePixels(shipField, 440 + 40 + 10 * 40 + 2 * 40, 40 + 40, 440 + 440 + 440 + 40, 440 + 40);
                    if (field != null) {
                        if (player2.wasPreviouslyAttackedOnField(field)) {
                            if (player1.playerGameField.attack(field)) {
                                gameGUI.drawAttack(field, shipField, player1);
                                player2.saveAttack(field);
                                gameGUI.activateMask();
                                AudioData.bombPlayer.stop();
                                AudioData.bombPlayer.play();
                            } else {
                                gameGUI.drawMiss(shipField);
                                player2.saveAttack(field);
                                gameGUI.activateMask();
                                gameGUI.INDICATE_1.setVisible(true);
                                gameGUI.INDICATE_2.setVisible(false);
                                AudioData.missPlayer.stop();
                                AudioData.missPlayer.play();
                            }
                        }
                    }

                    if (player1.playerGameField.gameOver()) {
                        System.out.println("Spieler 2 hat gewonnen");
                        gameGUI.deactivateMask();
                        gameGUI.SHOW_PLAYER_1_SHIPS_BUTTON.setVisible(false);
                        gameGUI.SHOW_PLAYER_2_SHIPS_BUTTON.setVisible(false);
                        gameGUI.RESET_BUTTON.setVisible(false);
                        gameGUI.getRootPane().getChildren().add(gameGUI.PLAYER_2_WON_IMAGE_VIEW);
                        gameGUI.PLAYER_2_WON_IMAGE_VIEW.setX(1450);
                        gameGUI.PLAYER_2_WON_IMAGE_VIEW.setY(520);
                        AudioData.winnerPlayer.stop();
                        AudioData.winnerPlayer.play();
                        gameGUI.getRootPane().getChildren().add(gameGUI.CONTINUE_BUTTON);
                        gameGUI.CONTINUE_BUTTON.setLayoutX(1520);
                        gameGUI.CONTINUE_BUTTON.setLayoutY(850);
                        gameGUI.CONTINUE_BUTTON.setVisible(true);
                    }
                }
            }
        }
    }

    private boolean checkIfAllFleetsAreComplete() {
        return player1.playerGameField.isFleetComplete() && player2.playerGameField.isFleetComplete();
    }

    //TODO ist das wirklich reset?
    public void resetGame(GameGUI gameGUI) {
        for (int i = 0; i < Constants.TOTAL_SHIP_COUNT; i++) {
            gameGUI.getPlayer1ShipImages()[i].rotateTo(Direction.RIGHT);
            gameGUI.getPlayer2ShipImages()[i].rotateTo(Direction.RIGHT);
            gameGUI.getPlayer2ShipImages()[i].reset();
            gameGUI.getPlayer1ShipImages()[i].reset();
        }

        player1.playerGameField.removeAll();
        player2.playerGameField.removeAll();
        player1.resetAttackedFields();
        player2.resetAttackedFields();

        gameRound = 1;
        shipsComplete = false;

        gameGUI.SAVE_SHIPS_RIGHT_BUTTON.setVisible(true);
        gameGUI.SAVE_SHIPS_LEFT_BUTTON.setVisible(true);

        gameGUI.reset();

        gameGUI.RESET_BUTTON.setVisible(true);
        gameGUI.START_MENU_IMAGE_VIEW.setVisible(false);
    }

    private void addResetButtonEventListener(GameGUI gameGUI){
        gameGUI.RESET_BUTTON.setOnAction(event -> {
            this.resetGame(gameGUI);
            gameGUI.updateStage();
        });
    }

    private void addNewGameButtonEventListener(GameGUI gameGUI){
        gameGUI.NEW_GAME_BUTTON.setOnAction(event -> {
            this.resetGame(gameGUI);
            gameGUI.updateStage();
        });
    }

    private void addContinueButtonEventListener(GameGUI gameGUI){
        gameGUI.CONTINUE_BUTTON.setOnAction(event -> {
            this.resetGame(gameGUI);

            gameGUI.RESET_BUTTON.setVisible(false);
            gameGUI.getRootPane().getChildren().add(gameGUI.NEW_GAME_BUTTON);
            gameGUI.getRootPane().getChildren().add(gameGUI.EXIT_BUTTON);
            gameGUI.START_MENU_IMAGE_VIEW.setVisible(true);
            gameGUI.NEW_GAME_BUTTON.setVisible(true);
            gameGUI.EXIT_BUTTON.setVisible(true);

            gameGUI.updateStage();
        });
    }

    private void addFieldClickEventListener(GameGUI gameGUI){
        gameGUI.getRootPane().addEventHandler(MouseEvent.ANY, event -> {
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                double pressedX = event.getSceneX();
                double pressedY = event.getSceneY();

                Field pressedField = new Field((int) Math.round(pressedX), (int) Math.round(pressedY));
                executeAttack(gameGUI, pressedField);
            }
        });
    }

    private void addLeftSaveButtonEventListener(GameGUI gameGUI){
        gameGUI.SAVE_SHIPS_LEFT_BUTTON.setOnAction(event -> {
            saveShips(gameGUI, gameGUI.getPlayer2ShipImages(), player1, 440 + 40, 40 + 440 + 40 + 40, 440 + 440, 40 + 920);
            this.shipsComplete = checkIfAllFleetsAreComplete();
        });
    }

    private void addRightSaveButtonEventListener(GameGUI gameGUI){
        gameGUI.SAVE_SHIPS_RIGHT_BUTTON.setOnAction(event -> {
            saveShips(gameGUI, gameGUI.getPlayer1ShipImages(), player2, 2 * 440 + 40 + 40, 40 + 440 + 40 + 40, 440 + 440 + 40 + 440, 920 + 40);
            this.shipsComplete = checkIfAllFleetsAreComplete();
        });
    }

    private void addShowPlayer1ShipsButtonEventListener(GameGUI gameGUI){
        gameGUI.SHOW_PLAYER_1_SHIPS_BUTTON.setOnAction(event -> gameGUI.changeMask());
    }

    private void addShowPlayer2ShipsButtonEventListener(GameGUI gameGUI){
        gameGUI.SHOW_PLAYER_2_SHIPS_BUTTON.setOnAction(event -> gameGUI.changeMask());
    }

    public static Player getPlayer1() {
        return player1;
    }

    public static Player getPlayer2() {
        return player2;
    }

    public static void setGameRound(int gameRound) {
        App.gameRound = gameRound;
    }

    public static int getGameRound() {
        return gameRound;
    }
}