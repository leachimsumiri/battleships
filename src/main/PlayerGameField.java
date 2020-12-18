package main;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerGameField {
    private final static Logger LOGGER = Logger.getLogger(PlayerGameField.class.getName());

    private ArrayList<Ship> fleet = new ArrayList<>();

    private boolean isFieldFree(Field field) {
        for (Ship ship : this.fleet) {
            for (ShipPart shipPart : ship.getShipParts()) {
                if (shipPart.getField().equals(field)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isAreaFree(Field field, int length, Direction direction) {
        for (int i = 0; i < length; i++) {
            if (!isFieldValid(field)) {
                LOGGER.log(Level.INFO, "Field invalid");
                return false;
            }

            if (!this.isFieldFree(field)) {
                LOGGER.log(Level.INFO, "Field not free");
                return false;
            }

            adjustCoordinatesAccordingToShipDirection(direction, field);
        }
        return true;
    }

    private void adjustCoordinatesAccordingToShipDirection(Direction shipDirection, Field field) {
        switch (shipDirection) {
            case UP:
                field.decrementY();
                break;
            case RIGHT:
                field.incrementX();
                break;
            case LEFT:
                field.decrementX();
                break;
            case DOWN:
                field.incrementY();
                break;
        }
    }

    private boolean coordinateIsValid(int coordinate){
        return coordinate >= Constants.FIELD_MIN_VALUE && coordinate <= Constants.FIELD_MAX_VALUE;
    }

    private boolean isFieldValid(Field field){
        return coordinateIsValid(field.getX()) && coordinateIsValid(field.getY());
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
        return this.currentShipsWithLength(Constants.FIELDLENGTH_OF_SMALL_SHIPS) == Constants.AMOUNT_OF_SMALL_SHIPS;
    }

    private boolean allMediumShipsPlaced(){
        return this.currentShipsWithLength(Constants.FIELDLENGTH_OF_MEDIUM_SHIPS) == Constants.AMOUNT_OF_MEDIUM_SHIPS;
    }

    private boolean allLargeShipsPlaced(){
        return this.currentShipsWithLength(Constants.FIELDLENGTH_OF_LARGE_SHIPS) == Constants.AMOUNT_OF_LARGE_SHIPS;
    }

    private boolean allXLargeShipsPlaced(){
        return this.currentShipsWithLength(Constants.FIELDLENGTH_OF_XLARGE_SHIPS) == Constants.AMOUNT_OF_XLARGE_SHIPS;
    }

    private boolean allShipsPlaced(){
        return allSmallShipsPlaced() && allMediumShipsPlaced() && allLargeShipsPlaced() && allXLargeShipsPlaced();
    }

    public boolean addShipToFleet(Field field, int length, Direction direction, int diffvectorx, int diffvectory) {
        if (isAreaFree(field, length, direction)) {
            this.fleet.add(new Ship(field, length, direction, diffvectorx, diffvectory));
            return true;
        } else {
            LOGGER.log(Level.INFO, "Ship couldn't be added because Area was not free or Field was invalid");
            return false;
        }
    }

    /*Es überprüft für jedes Schiff der Flotte (ArrayList mit Schiffen) ob die x,y Koordinaten zutreffen. Wenn ja,
    dann werden die Koordinaten weitergegeben und die attack Methode in der Klasse Ship überprüft das gleiche für
    jeden ShipPart.*/
    public boolean attack(Field field) {
        for (Ship ship : this.fleet) {
            if (ship.attack(field)) {
                return true;
            }
        }
        return false;

    }

    /*Checkt für jeden ShipPart jedes Schiffes im fleet ArrayList, ob es destroyed ist. Wenn x und y auf ein ganzes
    Schiff zutreffen und checkIfDestroyed (Ship-Klasse) true liefert, returned es das zerstörte Schiff, ansonsten null.*/
    public Ship isDestroyed(Field field) {
        for (Ship ship : this.fleet) {
            for (ShipPart shipPart : ship.getShipParts()) {
                if (shipPart.getField().equals(field) && ship.checkIfDestroyed()) {
                    return ship;
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
    public void removeAll() {
        this.fleet = new ArrayList<Ship>(0);
    }
}
