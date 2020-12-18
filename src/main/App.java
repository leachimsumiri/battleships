package main;

import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class App extends Application {
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

        addStartMenuEventListeners(gameGUI);
    }

    private void addGameEventListeners(GameGUI gameGUI){
        addResetButtonEventListener(gameGUI);
        addContinueButtonEventListener(gameGUI);
        addFieldClickEventListener(gameGUI);
        addLeftSaveButtonEventListener(gameGUI);
        addRightSaveButtonEventListener(gameGUI);
        addShowPlayer1ShipsButtonEventListener(gameGUI);
        addShowPlayer2ShipsButtonEventListener(gameGUI);
    }

    private void addStartMenuEventListeners(GameGUI gameGUI){
        addNewGameButtonEventListener(gameGUI);
    }

    private void lockOrResetShipImages(Field field, Player player, ShipImage shipImage){
        if (field != null) {
            if (player.playerGameField.addShipToFleet(field, shipImage.getLength(), shipImage.getDirection(), shipImage.getDiffVectorX(), shipImage.getDiffVectorY())) {
                if(player.playerGameField.isFleetComplete())
                    shipImage.lock();
            } else {
                shipImage.reset();
            }
        } else {
            shipImage.reset();
        }
    }

    private void saveShips(GameGUI gameGUI, ShipImage[] shipImages, Player player, int[] constraints) {
        for (ShipImage shipImage : shipImages) {
            if (!shipImage.isLocked()) {
                Field field = Field.caclulateFieldFromImagePixels(shipImage.getField(), constraints);
                this.lockOrResetShipImages(field, player, shipImage);
            }
        }

        if (player.playerGameField.isFleetComplete()) {
            gameRound++;
            if (player == player1) {
                proceedAfterSaveShipsPlayer1(gameGUI);
            } else {
                proceedAfterSaveShipsPlayer2(gameGUI);
            }

            if (this.shipsComplete) {
                gameGUI.activateMask();
            }
        }
    }

    private void proceedAfterSaveShipsPlayer1(GameGUI gameGUI){
        gameGUI.proceedAfterSaveShipsPlayer1();
    }

    private void proceedAfterSaveShipsPlayer2(GameGUI gameGUI){
        gameGUI.proceedAfterSaveShipsPlayer2();
    }

    private void executeAttack(GameGUI gameGUI, Field shipField) {
        Field field;
        if (noGameOvers() && this.shipsComplete) {
            if (player1Turn()) {
                field = Field.caclulateFieldFromImagePixels(shipField, ShipPositionConstraints.TOP_LEFT_CONSTRAINTS);
                executePlayMove(gameGUI, player2, player1, shipField, field);
            } else {
                field = Field.caclulateFieldFromImagePixels(shipField, ShipPositionConstraints.TOP_RIGHT_CONSTRAINTS);
                executePlayMove(gameGUI, player1, player2, shipField, field);
            }
        }
    }

    private boolean noGameOvers(){
        return !(player1.playerGameField.gameOver() || player2.playerGameField.gameOver());
    }

    private boolean player1Turn(){
        return gameRound % 2 == 1;
    }

    private void executePlayMove(GameGUI gameGUI, Player attackedPlayer, Player attackingPlayer, Field shipField, Field field){
        if (field != null) {
            if (!attackingPlayer.hasPreviouslyAttackedSameField(field)) {
                if (attackedPlayer.playerGameField.attack(field)) {
                    gameGUI.drawAttackMove(attackedPlayer, attackingPlayer, field, shipField);
                } else {
                    gameGUI.drawMissMove(attackingPlayer, field, shipField);
                }
            }
        }

        if (attackedPlayer.playerGameField.gameOver()) {
            int winner = attackingPlayer == player1 ? 1 : 2;
            gameGUI.playWinningAnimations(winner);
        }
    }

    private boolean checkIfAllFleetsAreComplete() {
        return player1.playerGameField.isFleetComplete() && player2.playerGameField.isFleetComplete();
    }

    public void resetGame(GameGUI gameGUI) {
        gameGUI.resetShipImages();

        player1.playerGameField.removeAll();
        player2.playerGameField.removeAll();
        player1.resetAttackedFields();
        player2.resetAttackedFields();

        gameRound = 1;
        this.shipsComplete = false;

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
            addGameEventListeners(gameGUI);
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
            saveShips(gameGUI, gameGUI.getPlayer2ShipImages(), player1, ShipPositionConstraints.BOTTOM_LEFT_CONSTRAINTS);
            this.shipsComplete = checkIfAllFleetsAreComplete();
        });
    }

    private void addRightSaveButtonEventListener(GameGUI gameGUI){
        gameGUI.SAVE_SHIPS_RIGHT_BUTTON.setOnAction(event -> {
            saveShips(gameGUI, gameGUI.getPlayer1ShipImages(), player2, ShipPositionConstraints.BOTTOM_RIGHT_CONSTRAINTS);
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