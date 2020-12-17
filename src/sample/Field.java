package sample;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Field {
    private final static Logger LOGGER = Logger.getLogger(Field.class.getName());

    private static final int AMOUNT_OF_SMALL_SHIPS = 4;
    private static final int AMOUNT_OF_MEDIUM_SHIPS = 3;
    private static final int AMOUNT_OF_LARGE_SHIPS = 2;
    private static final int AMOUNT_OF_XLARGE_SHIPS = 1;

    private static final int LENGTH_OF_SMALL_SHIPS = 2;
    private static final int LENGTH_OF_MEDIUM_SHIPS = 3;
    private static final int LENGTH_OF_LARGE_SHIPS = 4;
    private static final int LENGTH_OF_XLARGE_SHIPS = 5;

    private ArrayList<Ship> fleet = new ArrayList<>();

    private boolean isFieldFree(int x, int y) {
        for (Ship ship : this.fleet) {
            for (ShipPart shipPart : ship.getShipParts()) {
                if (shipPart.getX() == x && shipPart.getY() == y) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isAreaFree(int x, int y, int length, Direction direction) {
        for (int i = 0; i < length; i++) {
            if (pointIsValid(x, y)) {
                LOGGER.log(Level.INFO, "Point invalid");
                return false;
            }

            if (!this.isFieldFree(x, y)) {
                LOGGER.log(Level.INFO, "Field not free");
                return false;
            }

            adjustCoordinatesAccordingToShipDirection(direction, x, y);
        }
        return true;
    }

    private void adjustCoordinatesAccordingToShipDirection(Direction shipDirection, int x, int y) {
        switch (shipDirection) {
            case UP:
                y--;
                break;
            case RIGHT:
                x++;
                break;
            case LEFT:
                x--;
                break;
            case DOWN:
                y++;
                break;
        }
    }

    private boolean coordinateIsValid(int coordinate){
        return coordinate >= 0 && coordinate <= 9;
    }

    private boolean pointIsValid(int x, int y){
        return coordinateIsValid(x) && coordinateIsValid(y);
    }

    private int currentShipsWithLength(int length) {
        int count = 0;
        for (Ship ship : this.fleet) {
            if (ship.getLength() == length) {
                count++;
            }
        }
        return count;
    }

    public boolean isFleetComplete() {
        return allShipsPlaced();
    }

    private boolean allSmallShipsPlaced(){
        return this.currentShipsWithLength(2) == AMOUNT_OF_SMALL_SHIPS;
    }

    private boolean allMediumShipsPlaced(){
        return this.currentShipsWithLength(3) == AMOUNT_OF_MEDIUM_SHIPS;
    }

    private boolean allLargeShipsPlaced(){
        return this.currentShipsWithLength(4) == AMOUNT_OF_LARGE_SHIPS;
    }

    private boolean allXLargeShipsPlaced(){
        return this.currentShipsWithLength(5) == AMOUNT_OF_XLARGE_SHIPS;
    }

    private boolean allShipsPlaced(){
        return allSmallShipsPlaced() && allMediumShipsPlaced() && allLargeShipsPlaced() && allXLargeShipsPlaced();
    }

    public boolean addShipToFleet(int x, int y, int length, Direction dire, int diffvectorx, int diffvectory) {
        if (isAreaFree(x, y, length, dire)) {
            this.fleet.add(new Ship(x, y, length, dire, diffvectorx, diffvectory));
            return true;
        } else {
            LOGGER.log(Level.INFO, "Ship couldn't be added because Area was not free");
            return false;
        }
    }

    /*Es überprüft für jedes Schiff der Flotte (ArrayList mit Schiffen) ob die x,y Koordinaten zutreffen. Wenn ja,
    dann werden die Koordinaten weitergegeben und die attack Methode in der Klasse Ship überprüft das gleiche für
    jeden ShipPart.*/
    public boolean attack(int x, int y) {
        for (Ship warship : this.fleet) {
            if (warship.attack(x, y)) {
                return true;
            }
        }
        return false;

    }
/*Checkt für jeden ShipPart jedes Schiffes im fleet ArrayList, ob es destroyed ist. Wenn x und y auf ein ganzes
Schiff zutreffen und checkIfDestroyed (Ship-Klasse) true liefert, returned es das zerstörte Schiff, ansonsten null.*/
    public Ship isDestroyed(int x, int y) {
        for (Ship warship : this.fleet) {
            for (ShipPart part : warship.getShipParts()) {
                if (part.getX() == x && part.getY() == y && warship.checkIfDestroyed()) {
                    return warship;
                }
            }
        }
        return null;
    }

    /*Es geht jedes Schiff durch und schaut ob es zerstört ist.*/
    public boolean gameOver() {
        for (Ship warship : this.fleet) {
            if (!warship.checkIfDestroyed()) {
                return false;
            }
        }
        return true;
    }

    /*Verwendung: reset Methode in der Main. Wenn reset aufgerufen wird, wird removeAll aktiviert, bedeutet, dass wir
    eine neue ArrayList fleet erstellen (die alte wird gelöscht quasi).*/
    public void removeAll()
    {
        this.fleet = new ArrayList<Ship>(0);
    }
}
