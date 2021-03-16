import java.util.ArrayList;

public class Ship {
    private ArrayList<ShipPart> shipParts = new ArrayList<>();
    private final int length;
    private final Field startField;
    private final Direction direction;
    private final int divX;
    private final int divY;

    public Field getStartField() {
        return startField;
    }

    public int getDivX()
    {
        return divX;
    }

    public int getDivY()
    {
        return divY;
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
        return shipParts;
    }

    private void generateShip(Field startField, int length, Direction direction) {
        for (int i = 0; i < length; i++) {
            this.shipParts.add(new ShipPart(startField));
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
        this.divX = diffvectorx;
        this.divY = diffvectory;

        generateShip(startField, length, direction);
    }

    public boolean attack(Field field) {
        for (ShipPart shipPart : this.shipParts) {
            if (shipPart.getField().equals(field)) {
                shipPart.destroy();
                return true;
            }
        }
        return false;
    }

    public boolean checkIfDestroyed() {
        for (ShipPart shipPart : this.shipParts) {
            if (!shipPart.isDamaged()) {
                return false;
            }
        }
        return true;
    }
}
