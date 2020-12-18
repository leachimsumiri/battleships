package main;

import java.util.ArrayList;

public class Ship {
    private ArrayList<ShipPart> shipparts = new ArrayList<>();
    private int length;
    private Field startField;
    private Direction direction;
    private int divx, divy;

    public Field getStartField() {
        return startField;
    }

    public int getDivx()
    {
        return divx;
    }

    public int getDivy()
    {
        return divy;
    }

    public int getLength()
    {
        return length;
    }

    public Direction getDirection()
    {
        return direction;
    }

    public ArrayList<ShipPart> getShipParts()
    {
        return shipparts;
    }

    private void generateShip(Field startField, int length, Direction direction) {
        for (int i = 0; i < length; i++) {
            shipparts.add(new ShipPart(startField));
            switch (direction) {
                case UP:
                    startField.decrementY();
                    break;
                case RIGHT:
                    startField.incrementX();
                    break;
                case LEFT:
                    startField.decrementX();
                    break;
                case DOWN:
                    startField.incrementY();
                    break;
            }
        }
    }

    public Ship(Field startField, int length, Direction direction, int diffvectorx, int diffvectory) {
        this.startField = startField;
        this.direction = direction;
        this.length = length;
        this.divx = diffvectorx;
        this.divy = diffvectory;

        generateShip(startField, length, direction);
    }

    public boolean attack(Field field) {
        for (ShipPart shipPart : this.shipparts) {
            if (shipPart.getField().equals(field)) {
                shipPart.destroy();
                return true;
            }
        }
        return false;
    }

    public boolean checkIfDestroyed() {
        for (ShipPart shippart : this.shipparts) {
            if (!shippart.isDamaged()) {
                return false;
            }
        }
        return true;
    }
}
