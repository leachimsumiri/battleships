package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.lang.reflect.Method;

public class Main extends Application {
    private Player player1 = new Player();
    private Player player2 = new Player();

    private double pressedX, pressedY;
    private int gameRound = 1;
    private boolean shipsComplete = false;

    ShipImage[] player1ShipImages = ShipImage.createInitialShipImages(Constants.PLAYER_1_INITIAL_FIELDS);
    ShipImage[] player2ShipImages = ShipImage.createInitialShipImages(Constants.PLAYER_2_INITIAL_FIELDS);

    private Pane battleshipContainer = new Pane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws NoSuchMethodException {
        GameGUI.setIslandImageViewProperties();
        battleshipContainer.setBackground(new Background(Constants.BACKGROUND_IMAGE));

        drawGUI();

        GameGUI.initResetButton(battleshipContainer, primaryStage);
        battleshipContainer.getChildren().add(GameGUI.RESET_BUTTON);


        battleshipContainer.getChildren().add(GameGUI.NEW_GAME_BUTTON);

        GameGUI.EXIT_BUTTON.setLayoutX(700);
        GameGUI.EXIT_BUTTON.setLayoutY(500);
        GameGUI.EXIT_BUTTON.setMinSize(400, 150);
        //GameGUI.EXIT_BUTTON.setFont(font);
        GameGUI.EXIT_BUTTON.setOnAction(event -> System.exit(0));

        battleshipContainer.getChildren().add(GameGUI.EXIT_BUTTON);
        GameGUI.CONTINUE_BUTTON.setOnAction(event -> {
            resetGame();
            GameGUI.RESET_BUTTON.setVisible(false);
            battleshipContainer.getChildren().add(GameGUI.NEW_GAME_BUTTON);
            battleshipContainer.getChildren().add(GameGUI.EXIT_BUTTON);
            GameGUI.START_MENU_IMAGE_VIEW.setVisible(true);
            GameGUI.NEW_GAME_BUTTON.setVisible(true);
            GameGUI.EXIT_BUTTON.setVisible(true);
            Scene scene = new Scene(battleshipContainer, 1800, 1000);
            primaryStage.setScene(scene);
            primaryStage.show();
        });

        Scene scene = new Scene(battleshipContainer, 1800, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawGUI() {
        AudioData.musicPlayer.setCycleCount(500);
        AudioData.musicPlayer.play();

        for (int i = 0; i < Constants.TOTAL_SHIP_COUNT; i++) {
            battleshipContainer.getChildren().add(player2ShipImages[i].getImageView());
            battleshipContainer.getChildren().add(player1ShipImages[i].getImageView());
        }

        battleshipContainer.addEventHandler(MouseEvent.ANY, event -> {
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                pressedX = event.getSceneX();
                pressedY = event.getSceneY();

                Field pressedField = new Field((int) Math.round(pressedX), (int) Math.round(pressedY));
                executeAttack(pressedField);
            }
        });

        GameGUI.SAVE_SHIPS_LEFT_BUTTON.setLayoutX(1800 - 1520 - 3 * 40);
        GameGUI.SAVE_SHIPS_LEFT_BUTTON.setLayoutY(500);
        GameGUI.SAVE_SHIPS_LEFT_BUTTON.setPrefSize(120, 10);
        GameGUI.SAVE_SHIPS_LEFT_BUTTON.setOnAction(event -> {
            saveShips(player2ShipImages, player1, 440 + 40, 40 + 440 + 40 + 40, 440 + 440, 40 + 920);
            this.shipsComplete = checkIfAllFleetsAreComplete();
        });

        GameGUI.SAVE_SHIPS_RIGHT_BUTTON.setLayoutX(1520);
        GameGUI.SAVE_SHIPS_RIGHT_BUTTON.setLayoutY(500);
        GameGUI.SAVE_SHIPS_RIGHT_BUTTON.setPrefSize(120, 10);
        GameGUI.SAVE_SHIPS_RIGHT_BUTTON.setOnAction(event -> {
            saveShips(player1ShipImages, player2, 2 * 440 + 40 + 40, 40 + 440 + 40 + 40, 440 + 440 + 40 + 440, 920 + 40);
            this.shipsComplete = checkIfAllFleetsAreComplete();
        });

        GameGUI.START_MENU_IMAGE_VIEW.setVisible(true);
        GameGUI.SHOW_PLAYER_1_SHIPS_BUTTON.setLayoutX(1520);
        GameGUI.SHOW_PLAYER_1_SHIPS_BUTTON.setLayoutY(550);
        GameGUI.SHOW_PLAYER_1_SHIPS_BUTTON.setPrefSize(120, 10);
        GameGUI.SHOW_PLAYER_1_SHIPS_BUTTON.setOnAction(event -> changeMask());

        GameGUI.SHOW_PLAYER_2_SHIPS_BUTTON.setLayoutX(160);
        GameGUI.SHOW_PLAYER_2_SHIPS_BUTTON.setLayoutY(550);
        GameGUI.SHOW_PLAYER_2_SHIPS_BUTTON.setPrefSize(120, 10);
        GameGUI.SHOW_PLAYER_2_SHIPS_BUTTON.setOnAction(event -> changeMask());

        GameGUI.INDICATE_1.setFill(Color.RED);
        GameGUI.INDICATE_2.setFill(Color.RED);

        battleshipContainer.getChildren().add(GameGUI.SHOW_PLAYER_1_SHIPS_BUTTON);
        battleshipContainer.getChildren().add(GameGUI.SHOW_PLAYER_2_SHIPS_BUTTON);
        battleshipContainer.getChildren().addAll(GameGUI.SAVE_SHIPS_LEFT_BUTTON, GameGUI.SAVE_SHIPS_RIGHT_BUTTON,
                GameGUI.LEFT_ISLAND_IMAGE_VIEW, GameGUI.RIGHT_ISLAND_IMAGE_VIEW, GameGUI.START_MENU_IMAGE_VIEW, GameGUI.INDICATE_1,
                GameGUI.INDICATE_2);

        GameGUI.RESET_BUTTON.setVisible(false);
        GameGUI.LEFT_ISLAND_IMAGE_VIEW.setVisible(false);
        GameGUI.RIGHT_ISLAND_IMAGE_VIEW.setVisible(false);
        GameGUI.SHOW_PLAYER_1_SHIPS_BUTTON.setVisible(false);
        GameGUI.SHOW_PLAYER_2_SHIPS_BUTTON.setVisible(false);
        GameGUI.INDICATE_1.setVisible(false);
        GameGUI.INDICATE_2.setVisible(false);
        changeMask();
    }

    private void activateMask() {
        GameGUI.LEFT_ISLAND_IMAGE_VIEW.setVisible(true);
        GameGUI.RIGHT_ISLAND_IMAGE_VIEW.setVisible(true);
    }

    private void deactivateMask() {
        GameGUI.LEFT_ISLAND_IMAGE_VIEW.setVisible(false);
        GameGUI.RIGHT_ISLAND_IMAGE_VIEW.setVisible(false);
    }

    private void changeMask() {
        if (gameRound % 2 == 1) {
            GameGUI.LEFT_ISLAND_IMAGE_VIEW.setVisible(false);
            GameGUI.RIGHT_ISLAND_IMAGE_VIEW.setVisible(true);
        } else if (gameRound % 2 == 0) {
            GameGUI.LEFT_ISLAND_IMAGE_VIEW.setVisible(true);
            GameGUI.RIGHT_ISLAND_IMAGE_VIEW.setVisible(false);
        }
    }

    //berechnet spielfeld Koordination anhand von imageship größen (40px pro feld) <- unbedingt ändern..
    private Field caclulateFieldFromImagePixels(Field shipField, int p1x, int p1y, int p2x, int p2y) {
        Field field = new Field();

        //Checkt ob die Koordinaten vom Schiff im richtigen Feld liegen
        if (shipField.getX() >= p1x && shipField.getX() <= p2x && shipField.getY() >= p1y && shipField.getY() <= p2y) {
            int vectorx, vectory;
            //berechnet Relation zum Spielfeld
            //TODO gscheit berechnen
            vectorx = shipField.getX() - p1x;
            vectory = shipField.getY() - p1y;

            //40px = Feldbreite & Höhe
            field.setX(vectorx / 40);
            field.setY(vectory / 40);
            return field;
        }
        return null;
    }


    private void saveShips(ShipImage[] shipImages, Player player, int p1x, int p1y, int p2x, int p2y) {
        for (ShipImage shipImage : shipImages) {
            if (!shipImage.isLocked()) {
                Field field = caclulateFieldFromImagePixels(shipImage.getField(), p1x, p1y, p2x, p2y);

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
                changeMask();
                GameGUI.SAVE_SHIPS_LEFT_BUTTON.setVisible(false);

            } else {
                GameGUI.SAVE_SHIPS_RIGHT_BUTTON.setVisible(false);
                changeMask();
                GameGUI.SHOW_PLAYER_1_SHIPS_BUTTON.setVisible(true);
                GameGUI.SHOW_PLAYER_2_SHIPS_BUTTON.setVisible(true);
                GameGUI.INDICATE_1.setVisible(true);
            }
            if (player1.playerGameField.isFleetComplete() && player2.playerGameField.isFleetComplete()) {
                activateMask();
            }
        }
    }

    private void executeAttack(Field shipField) {
        Field field;
        if (!(player1.playerGameField.gameOver() || player2.playerGameField.gameOver())) {
            if (shipsComplete) {
                System.out.println("Schiffe fertig");
                if (gameRound % 2 == 1) {
                    field = caclulateFieldFromImagePixels(shipField, 440 + 40, 40 + 40, 440 + 440, 440 + 40);

                    if (field != null) {
                        if (player1.wasPreviouslyAttackedOnField(field)) {
                            if (player2.playerGameField.attack(field)) {
                                drawAttack(field, shipField, player2);
                                player1.saveAttack(field);
                                activateMask();
                                AudioData.bombPlayer.stop();
                                AudioData.bombPlayer.play();

                            } else {
                                drawMiss(shipField);
                                player1.saveAttack(field);
                                activateMask();
                                GameGUI.INDICATE_1.setVisible(false);
                                GameGUI.INDICATE_2.setVisible(true);
                                AudioData.missPlayer.stop();
                                AudioData.missPlayer.play();
                            }
                        }
                    }

                    if (player2.playerGameField.gameOver()) {
                        System.out.println("Spieler 1 hat gewonnen");
                        deactivateMask();
                        GameGUI.SHOW_PLAYER_1_SHIPS_BUTTON.setVisible(false);
                        GameGUI.SHOW_PLAYER_2_SHIPS_BUTTON.setVisible(false);
                        GameGUI.RESET_BUTTON.setVisible(false);
                        battleshipContainer.getChildren().add(GameGUI.PLAYER_1_WON_IMAGE_VIEW);
                        GameGUI.PLAYER_1_WON_IMAGE_VIEW.setX(50);
                        GameGUI.PLAYER_1_WON_IMAGE_VIEW.setY(520);
                        AudioData.winnerPlayer.stop();
                        AudioData.winnerPlayer.play();
                        battleshipContainer.getChildren().add(GameGUI.CONTINUE_BUTTON);
                        GameGUI.CONTINUE_BUTTON.setLayoutX(160);
                        GameGUI.CONTINUE_BUTTON.setLayoutY(850);
                        GameGUI.CONTINUE_BUTTON.setVisible(true);
                    }

                } else {
                    field = caclulateFieldFromImagePixels(shipField, 440 + 40 + 10 * 40 + 2 * 40, 40 + 40, 440 + 440 + 440 + 40, 440 + 40);
                    if (field != null) {
                        if (player2.wasPreviouslyAttackedOnField(field)) {
                            if (player1.playerGameField.attack(field)) {
                                drawAttack(field, shipField, player1);
                                player2.saveAttack(field);
                                activateMask();
                                AudioData.bombPlayer.stop();
                                AudioData.bombPlayer.play();
                            } else {
                                drawMiss(shipField);
                                player2.saveAttack(field);
                                activateMask();
                                GameGUI.INDICATE_1.setVisible(true);
                                GameGUI.INDICATE_2.setVisible(false);
                                AudioData.missPlayer.stop();
                                AudioData.missPlayer.play();
                            }
                        }
                    }

                    if (player1.playerGameField.gameOver()) {
                        System.out.println("Spieler 2 hat gewonnen");
                        deactivateMask();
                        GameGUI.SHOW_PLAYER_1_SHIPS_BUTTON.setVisible(false);
                        GameGUI.SHOW_PLAYER_2_SHIPS_BUTTON.setVisible(false);
                        GameGUI.RESET_BUTTON.setVisible(false);
                        battleshipContainer.getChildren().add(GameGUI.PLAYER_2_WON_IMAGE_VIEW);
                        GameGUI.PLAYER_2_WON_IMAGE_VIEW.setX(1450);
                        GameGUI.PLAYER_2_WON_IMAGE_VIEW.setY(520);
                        AudioData.winnerPlayer.stop();
                        AudioData.winnerPlayer.play();
                        battleshipContainer.getChildren().add(GameGUI.CONTINUE_BUTTON);
                        GameGUI.CONTINUE_BUTTON.setLayoutX(1520);
                        GameGUI.CONTINUE_BUTTON.setLayoutY(850);
                        GameGUI.CONTINUE_BUTTON.setVisible(true);
                    }
                }
            }
        }
    }

    /*Wasserzeichen, gerundet auf die richtige Stelle setzen*/
    private void drawMiss(Field field) {
        int diffx = field.getX() % 40;
        field.setX(field.getX() - diffx);

        int diffy = field.getY() % 40;
        field.setY(field.getY() - diffy);

        ImageView miss = new ImageView("file:res/Waterhitmarker.png");
        miss.setX(field.getX());
        miss.setY(field.getY());

        battleshipContainer.getChildren().add(miss);
        gameRound++;
    }

    /*Feuerzeichen, gerundet auf die richtige Stelle. Wenn Schiff zerstört, richtiges destroyed Schiff setzen*/
    private void drawAttack(Field field, Field shipField, Player player) {
        ShipImage shipImage;

        int diffx = (int) shipField.getX() % 40;
        shipField.setX(shipField.getX() - diffx);

        int diffy = (int) shipField.getY() % 40;
        shipField.setY(shipField.getY() - diffy);

        ImageView hit = new ImageView("file:res/Hit.png");
        hit.setX(shipField.getX());
        hit.setY(shipField.getY());
        battleshipContainer.getChildren().addAll(hit);

        Image image = new Image("file:res/1x2_Ship_Destroyed.png");
        /*Objekt ship wird entweder null oder ein Schiff zugewiesen (Siehe Klasse Ship, Methode isDestroyed). Wenn
        das Schiff zerstört ist, wird im switch case gefragt welche Länge und dementsprechen setzen wir das Schiff*/
        Ship ship = player.playerGameField.isDestroyed(field);

        if (ship != null) {
            System.out.println("zerstört");
            switch (ship.getLength()) {
                case 0:
                    break;
                case 2:
                    image = new Image("file:res/1x2_Ship_Destroyed.png");
                    break;
                case 3:
                    image = new Image("file:res/1x3_Ship_Destroyed.png");
                    break;
                case 4:
                    image = new Image("file:res/1x4_Ship_Destroyed.png");
                    break;
                case 5:
                    image = new Image("file:res/1x5_Ship_Destroyed.png");
                    break;
            }

            //TODO calc function
            int x, y;
            //*40 um auf unsere Spielfeldkoordinaten zu kommen
            x = ship.getStartField().getX() * 40;
            y = ship.getStartField().getY() * 40;
            //Wird immer in das gegenüberliegende Feld gesetzt, deshalb stehen hier die Koordinaten vom Spieler 2
            if (player == player1) {
                x += 2 * 440 + 40 + 40;
                y += 2 * 40;

            } else {
                x += (440 + 40);
                y += (2 * 40);
            }

            /*Schiff kreiert und zum Battleshipcontainer dazugehaut und lock==true, um es nicht bewegbar zu machen*/
            Field newField = new Field(x - ship.getDivx(), y - ship.getDivy());
            shipImage = new ShipImage(newField, ship.getLength(), image);
            battleshipContainer.getChildren().add(shipImage.getImageView());
            shipImage.rotateTo(ship.getDirection());
            shipImage.lock();
        }
    }

    private boolean checkIfAllFleetsAreComplete() {
        return player1.playerGameField.isFleetComplete() && player2.playerGameField.isFleetComplete();
    }

    public void resetGame() {
        for (int i = 0; i < Constants.TOTAL_SHIP_COUNT; i++) {
            player1ShipImages[i].rotateTo(Direction.RIGHT);
            player2ShipImages[i].rotateTo(Direction.RIGHT);
            player2ShipImages[i].reset();
            player1ShipImages[i].reset();
        }

        player1.playerGameField.removeAll();
        player2.playerGameField.removeAll();
        player1.resetAttackedFields();
        player2.resetAttackedFields();
        gameRound = 1;
        shipsComplete = false;
        GameGUI.SAVE_SHIPS_RIGHT_BUTTON.setVisible(true);
        GameGUI.SAVE_SHIPS_LEFT_BUTTON.setVisible(true);
        battleshipContainer = new Pane();
        BackgroundImage background = new BackgroundImage(new Image("file:res/BattleshipsBackground.png", 1800, 1000,
                true, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        battleshipContainer.setBackground(new Background(background));
        drawGUI();
        battleshipContainer.getChildren().add(GameGUI.RESET_BUTTON);
        GameGUI.RESET_BUTTON.setVisible(true);
        GameGUI.START_MENU_IMAGE_VIEW.setVisible(false);
    }
}