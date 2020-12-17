package sample;

import java.util.ArrayList;

public class Ship {
    private ArrayList<ShipPart> shipparts = new ArrayList<>();
    private int length;
    private int x;
    private int y;
    private Direction direction;
    private int divx, divy;

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
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

    private void generateShip(int x, int y, int length, Direction directions) {
        for (int i = 0; i < length; i++) {
            shipparts.add(new ShipPart(x, y));
            switch (directions) {
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
    }

    public Ship(int x, int y, int length, Direction directions, int diffvectorx, int diffvectory) {
        this.x = x;
        this.y = y;
        this.direction = directions;
        this.length = length;
        this.divx = diffvectorx;
        this.divy = diffvectory;

        generateShip(x, y, length, directions);

        System.out.println("ich generiere schiff an X= " + this.x + " Y =" + this.y + " richtung" + this.direction + " länge =" + this.length);
    }

    public boolean attack(int x, int y) {
        for (ShipPart shippart : this.shipparts) {
            if (shippart.getX() == x && shippart.getY() == y) {
                shippart.destroy();
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
