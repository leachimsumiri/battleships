package sample;

import java.util.ArrayList;

public class Field {
    private static final int AMOUNT_OF_SMALL_SHIPS = 4;
    private static final int AMOUNT_OF_MEDIUM_SHIPS = 3;
    private static final int AMOUNT_OF_LARGE_SHIPS = 2;
    private static final int AMOUNT_OF_XLARGE_SHIPS = 1;

    private ArrayList<Ship> fleet = new ArrayList<>();

    /*Überprüft für alle Schiffe und deren ShipParts(zweite For Schleife), ob sie auf den jeweils übergebenen x,y
    Koordinaten liegen.*/
    private boolean isFree(int x, int y) {
        for (Ship warship : this.fleet) {
            for (ShipPart part : warship.getShipParts()) {
                if (part.getX() == x && part.getY() == y) {
                    return false;
                }
            }
        }
        return true;
    }

    /*Überprüft, ob man setzen darf.*/
    private boolean isAreaFree(int x, int y, int length, Direction dir) {
        for (int i = 0; i < length; i++) {
            /*Hier, nimmt es die Koordinaten und prüft ob es innerhalb vom Spielfeld liegt. Wenn nicht, returned er false und
            isAreaFree liefert in der setShip Methode false zurück (was dann passiert, steht in der setShip Methode)*/
            if (x < 0 || x > 9 || y < 0 || y > 9) {
                //  System.out.println("anlegen x= "+x +" y= "+y);
                return false;
            }

            /*Überprüft, ob möglich zu setzen mit der isFree Methode. Wenn nicht, ebenfalls false.*/
            if (!this.isFree(x, y)) {
                return false;
            }

            /*Wenn beide if-Bedienungen true zurück liefern, erhöhen wir entweder die x oder y Koordinate
            abhängig von der Richtung. Wenn das Schiff nach oben zeigt, müssen wir y-- machen, um den nächsten 40
            Pixelblock (== 1 ShipPart) zu überprüfen, ob da ein Schiff gesetzt werden darf. Das machen wir alles so
            lang, wie die Länge von dem Schiff, das wir setzen wollen. (For-Schleife)*/
            switch (dir) {
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
        return true;
    }

    private int shipCount(int length) {
        int count = 0;
        for (Ship warship : this.fleet) {
            if (warship.getLength() == length) {
                count++;
            }
        }
        return count;
    }

    public boolean isFleetComplete() {
        return allShipsPlaced();
    }

    private boolean allSmallShipsPlaced(){
        return this.shipCount(2) == AMOUNT_OF_SMALL_SHIPS;
    }

    private boolean allMediumShipsPlaced(){
        return this.shipCount(3) == AMOUNT_OF_MEDIUM_SHIPS;
    }

    private boolean allLargeShipsPlaced(){
        return this.shipCount(4) == AMOUNT_OF_LARGE_SHIPS;
    }

    private boolean allXLargeShipsPlaced(){
        return this.shipCount(5) == AMOUNT_OF_XLARGE_SHIPS;
    }

    private boolean allShipsPlaced(){
        return allSmallShipsPlaced() && allMediumShipsPlaced() && allLargeShipsPlaced() && allXLargeShipsPlaced();
    }

    public boolean setShip(int x, int y, int length, Direction dire, int diffvectorx, int diffvectory) {
    /*Zuerst überprüfen wir, ob die Anzahl der Schiffe, mit der Länge die wir gerade setzen wollen, nicht eh schon
    erfüllt ist. Wenn schon, return false und brich ab.*/
        switch (length) {
            case 2:
                if (this.shipCount(length) >= 4) {
                    return false;
                }
                break;
            case 3:
                if (this.shipCount(length) >= 3) {
                    return false;
                }
                break;
            case 4:
                if (this.shipCount(length) >= 2) {
                    return false;
                }
                break;
            case 5:
                if (this.shipCount(length) >= 1) {
                    return false;
                }
                break;
            default:
                return false;
        }

        /*Switch hat nirgends false zurück geliefert, wir landen hier. wir überprüfen mit der isAreaFree Methode, ob
        wir am gewünschten Ort setzen dürfen. Wie?(steht oben beschrieben). Falls true, adden wir ein Objekt der
        Klasse Ship (also ein Schiff) zu unserer ArrayList fleet mittels dem Konstruktor der Klasse Ship. Wieso
        diffvectorx und y? Das steht in der main bei der Methode saveShips dabei.*/
        if (isAreaFree(x, y, length, dire)) {
            this.fleet.add(new Ship(x, y, length, dire, diffvectorx, diffvectory));
            return true;
        } else {
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
