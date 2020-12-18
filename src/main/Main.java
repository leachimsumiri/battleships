package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Main extends Application {
    private Player player1 = new Player();
    private Player player2 = new Player();

    private double pressedX, pressedY;
    private int gameRound = 1;
    private boolean shipsComplete = false;

    private final Rectangle indicate1 = new Rectangle(439, 481, 442, 7);
    private final Rectangle indicate2 = new Rectangle(919, 481, 442, 7);

    private Media bomb = new Media(new File("res/bomb.wav").toURI().toString());
    private MediaPlayer bombplay = new MediaPlayer(bomb);
    private Media miss = new Media(new File("res/miss.wav").toURI().toString());
    private MediaPlayer missplay = new MediaPlayer(miss);
    private Media music = new Media(new File("res/music.wav").toURI().toString());
    private MediaPlayer musicplay = new MediaPlayer(music);
    private Media winner = new Media(new File("res/winner.wav").toURI().toString());
    private MediaPlayer winnerplay = new MediaPlayer(winner);

    private Image bships[] = {
            new Image("file:res/1x2_Schiff_Horizontal_1_Fertig.png"),
            new Image("file:res/1x3_Schiff_Horizontal_1_Fertig.png"),
            new Image("file:res/1x4_Schiff_Horizontal_1_Fertig.png"),
            new Image("file:res/1x5_Schiff_Horizontal_1_Fertig.png")
    };

    ShipImage player1ShipImages[] = {
            new ShipImage(Constants.player1SmallShipImageInitialField, 2, bships[0]),
            new ShipImage(Constants.player1SmallShipImageInitialField, 2, bships[0]),
            new ShipImage(Constants.player1SmallShipImageInitialField, 2, bships[0]),
            new ShipImage(Constants.player1SmallShipImageInitialField, 2, bships[0]),
            new ShipImage(Constants.player1MediumShipImageInitialField, 3, bships[1]),
            new ShipImage(Constants.player1MediumShipImageInitialField, 3, bships[1]),
            new ShipImage(Constants.player1MediumShipImageInitialField, 3, bships[1]),
            new ShipImage(Constants.player1LargeShipImageInitialField, 4, bships[2]),
            new ShipImage(Constants.player1LargeShipImageInitialField, 4, bships[2]),
            new ShipImage(Constants.player1XLargeShipImageInitialField, 5, bships[3])
    };

    ShipImage player2ShipImages[] = {
            new ShipImage(Constants.player2SmallShipImageInitialField, 2, bships[0]),
            new ShipImage(Constants.player2SmallShipImageInitialField, 2, bships[0]),
            new ShipImage(Constants.player2SmallShipImageInitialField, 2, bships[0]),
            new ShipImage(Constants.player2SmallShipImageInitialField, 2, bships[0]),
            new ShipImage(Constants.player2MediumShipImageInitialField, 3, bships[1]),
            new ShipImage(Constants.player2MediumShipImageInitialField, 3, bships[1]),
            new ShipImage(Constants.player2MediumShipImageInitialField, 3, bships[1]),
            new ShipImage(Constants.player2LargeShipImageInitialField, 4, bships[2]),
            new ShipImage(Constants.player2LargeShipImageInitialField, 4, bships[2]),
            new ShipImage(Constants.player2XLargeShipImageInitialField, 5, bships[3])
    };


    private Pane battleShipContainer = new Pane();

    private void drawGUI() {
        musicplay.setCycleCount(500);
        musicplay.play();

        for (int i = 0; i < Constants.TOTAL_SHIP_COUNT; i++) {
            battleShipContainer.getChildren().add(player2ShipImages[i].getImageView());
            battleShipContainer.getChildren().add(player1ShipImages[i].getImageView());
        }

        battleShipContainer.addEventHandler(MouseEvent.ANY, event -> {
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                pressedX = event.getSceneX();
                pressedY = event.getSceneY();

                Field pressedField = new Field((int) Math.round(pressedX), (int) Math.round(pressedY));
                executeAttack(pressedField);
            }
        });

        GameGUI.saveShipsLeftButton.setLayoutX(1800 - 1520 - 3 * 40);
        GameGUI.saveShipsLeftButton.setLayoutY(500);
        GameGUI.saveShipsLeftButton.setPrefSize(120, 10);
        GameGUI.saveShipsLeftButton.setOnAction(event -> {
            saveShips(player2ShipImages, player1, 440 + 40, 40 + 440 + 40 + 40, 440 + 440, 40 + 920);
            this.shipsComplete = checkIfAllFleetsAreComplete();
        });

        GameGUI.saveShipsRightButton.setLayoutX(1520);
        GameGUI.saveShipsRightButton.setLayoutY(500);
        GameGUI.saveShipsRightButton.setPrefSize(120, 10);
        GameGUI.saveShipsRightButton.setOnAction(event -> {
            saveShips(player1ShipImages, player2, 2 * 440 + 40 + 40, 40 + 440 + 40 + 40, 440 + 440 + 40 + 440, 920 + 40);
            this.shipsComplete = checkIfAllFleetsAreComplete();
        });

        GameGUI.startMenuImageView.setVisible(true);
        GameGUI.showPlayer1ShipsButton.setLayoutX(1520);
        GameGUI.showPlayer1ShipsButton.setLayoutY(550);
        GameGUI.showPlayer1ShipsButton.setPrefSize(120, 10);
        GameGUI.showPlayer1ShipsButton.setOnAction(event -> changeMask());

        GameGUI.showPlayer2ShipsButton.setLayoutX(160);
        GameGUI.showPlayer2ShipsButton.setLayoutY(550);
        GameGUI.showPlayer2ShipsButton.setPrefSize(120, 10);
        GameGUI.showPlayer2ShipsButton.setOnAction(event -> changeMask());

        indicate1.setFill(Color.RED);
        indicate2.setFill(Color.RED);

        battleShipContainer.getChildren().add(GameGUI.showPlayer1ShipsButton);
        battleShipContainer.getChildren().add(GameGUI.showPlayer2ShipsButton);
        battleShipContainer.getChildren().addAll(GameGUI.saveShipsLeftButton, GameGUI.saveShipsRightButton,
                GameGUI.leftIslandImageView, GameGUI.rightIslandImageView, GameGUI.startMenuImageView, indicate1,
                indicate2);

        GameGUI.resetButton.setVisible(false);
        GameGUI.leftIslandImageView.setVisible(false);
        GameGUI.rightIslandImageView.setVisible(false);
        GameGUI.showPlayer1ShipsButton.setVisible(false);
        GameGUI.showPlayer2ShipsButton.setVisible(false);
        indicate1.setVisible(false);
        indicate2.setVisible(false);
        changeMask();
    }

    private void activateMask() {
        GameGUI.leftIslandImageView.setVisible(true);
        GameGUI.rightIslandImageView.setVisible(true);
    }

    private void deactivateMask() {
        GameGUI.leftIslandImageView.setVisible(false);
        GameGUI.rightIslandImageView.setVisible(false);
    }

    private void changeMask() {
        if (gameRound % 2 == 1) {
            GameGUI.leftIslandImageView.setVisible(false);
            GameGUI.rightIslandImageView.setVisible(true);
        } else if (gameRound % 2 == 0) {
            GameGUI.leftIslandImageView.setVisible(true);
            GameGUI.rightIslandImageView.setVisible(false);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        BackgroundImage background = new BackgroundImage(new Image("file:res/BattleshipsBackground.png", 1800, 1000,
                true, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        //  ImageView Verdeckung = new ImageView("file:res/Spielfeldverdeckung.png");
        GameGUI.leftIslandImageView.setX(439);
        GameGUI.leftIslandImageView.setY(39 + 440 + 40);
        GameGUI.rightIslandImageView.setX(439 + 440 + 40);
        GameGUI.rightIslandImageView.setY(39 + 440 + 40);


        battleShipContainer.setBackground(new Background(background));
        drawGUI();

        GameGUI.resetButton.setLayoutX(440);
        GameGUI.resetButton.setLayoutY(10);
        GameGUI.resetButton.setPrefHeight(10);

        GameGUI.resetButton.setOnAction(event -> {
            reset();
            Scene scenel = new Scene(battleShipContainer, 1800, 1000);
            primaryStage.setScene(scenel);
            primaryStage.show();
        });

        battleShipContainer.getChildren().add(GameGUI.resetButton);
        GameGUI.newGameButton.setLayoutX(700);
        GameGUI.newGameButton.setLayoutY(300);
        GameGUI.newGameButton.setMinSize(400, 150);
        Font font = new Font(30);
        GameGUI.newGameButton.setFont(font);
        GameGUI.newGameButton.setOnAction(event -> {
            reset();
            Scene scene = new Scene(battleShipContainer, 1800, 1000);
            primaryStage.setScene(scene);
            primaryStage.show();
        });

        battleShipContainer.getChildren().add(GameGUI.newGameButton);

        GameGUI.exitButton.setLayoutX(700);
        GameGUI.exitButton.setLayoutY(500);
        GameGUI.exitButton.setMinSize(400, 150);
        GameGUI.exitButton.setFont(font);
        GameGUI.exitButton.setOnAction(event -> System.exit(0));

        battleShipContainer.getChildren().add(GameGUI.exitButton);
        GameGUI.continueButton.setOnAction(event -> {
            reset();
            GameGUI.resetButton.setVisible(false);
            battleShipContainer.getChildren().add(GameGUI.newGameButton);
            battleShipContainer.getChildren().add(GameGUI.exitButton);
            GameGUI.startMenuImageView.setVisible(true);
            GameGUI.newGameButton.setVisible(true);
            GameGUI.exitButton.setVisible(true);
            Scene scene = new Scene(battleShipContainer, 1800, 1000);
            primaryStage.setScene(scene);
            primaryStage.show();
        });

        Scene scene = new Scene(battleShipContainer, 1800, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
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
                GameGUI.saveShipsLeftButton.setVisible(false);

            } else {
                GameGUI.saveShipsRightButton.setVisible(false);
                changeMask();
                GameGUI.showPlayer1ShipsButton.setVisible(true);
                GameGUI.showPlayer2ShipsButton.setVisible(true);
                indicate1.setVisible(true);
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
                                bombplay.stop();
                                bombplay.play();

                            } else {
                                drawMiss(shipField);
                                player1.saveAttack(field);
                                activateMask();
                                indicate1.setVisible(false);
                                indicate2.setVisible(true);
                                missplay.stop();
                                missplay.play();
                            }
                        }
                    }

                    if (player2.playerGameField.gameOver()) {
                        System.out.println("Spieler 1 hat gewonnen");
                        deactivateMask();
                        GameGUI.showPlayer1ShipsButton.setVisible(false);
                        GameGUI.showPlayer2ShipsButton.setVisible(false);
                        GameGUI.resetButton.setVisible(false);
                        battleShipContainer.getChildren().add(GameGUI.player1WonImageView);
                        GameGUI.player1WonImageView.setX(50);
                        GameGUI.player1WonImageView.setY(520);
                        winnerplay.stop();
                        winnerplay.play();
                        battleShipContainer.getChildren().add(GameGUI.continueButton);
                        GameGUI.continueButton.setLayoutX(160);
                        GameGUI.continueButton.setLayoutY(850);
                        GameGUI.continueButton.setVisible(true);
                    }

                } else {
                    field = caclulateFieldFromImagePixels(shipField, 440 + 40 + 10 * 40 + 2 * 40, 40 + 40, 440 + 440 + 440 + 40, 440 + 40);
                    if (field != null) {
                        if (player2.wasPreviouslyAttackedOnField(field)) {
                            if (player1.playerGameField.attack(field)) {
                                drawAttack(field, shipField, player1);
                                player2.saveAttack(field);
                                activateMask();
                                bombplay.stop();
                                bombplay.play();
                            } else {
                                drawMiss(shipField);
                                player2.saveAttack(field);
                                activateMask();
                                indicate1.setVisible(true);
                                indicate2.setVisible(false);
                                missplay.stop();
                                missplay.play();
                            }
                        }
                    }

                    if (player1.playerGameField.gameOver()) {
                        System.out.println("Spieler 2 hat gewonnen");
                        deactivateMask();
                        GameGUI.showPlayer1ShipsButton.setVisible(false);
                        GameGUI.showPlayer2ShipsButton.setVisible(false);
                        GameGUI.resetButton.setVisible(false);
                        battleShipContainer.getChildren().add(GameGUI.player2WonImageView);
                        GameGUI.player2WonImageView.setX(1450);
                        GameGUI.player2WonImageView.setY(520);
                        winnerplay.stop();
                        winnerplay.play();
                        battleShipContainer.getChildren().add(GameGUI.continueButton);
                        GameGUI.continueButton.setLayoutX(1520);
                        GameGUI.continueButton.setLayoutY(850);
                        GameGUI.continueButton.setVisible(true);
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

        battleShipContainer.getChildren().add(miss);
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
        battleShipContainer.getChildren().addAll(hit);

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
            battleShipContainer.getChildren().add(shipImage.getImageView());
            shipImage.rotateTo(ship.getDirection());
            shipImage.lock();
        }
    }

    private boolean checkIfAllFleetsAreComplete() {
        return player1.playerGameField.isFleetComplete() && player2.playerGameField.isFleetComplete();
    }

    private void reset() {
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
        GameGUI.saveShipsRightButton.setVisible(true);
        GameGUI.saveShipsLeftButton.setVisible(true);
        battleShipContainer = new Pane();
        BackgroundImage background = new BackgroundImage(new Image("file:res/BattleshipsBackground.png", 1800, 1000,
                true, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        battleShipContainer.setBackground(new Background(background));
        drawGUI();
        battleShipContainer.getChildren().add(GameGUI.resetButton);
        GameGUI.resetButton.setVisible(true);
        GameGUI.startMenuImageView.setVisible(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}