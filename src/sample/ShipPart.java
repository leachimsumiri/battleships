package sample;

public class ShipPart {
    /*Jeder Teil vom Schiff (in unserem Fall ist jeder Teil genau 40pixel lang) hat die Eigenschaften von der Klasse
    ShipPart*/

    private int x;
    private int y;
    private boolean damage;

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public ShipPart(int x, int y) {
        this.x = x;
        this.y = y;
        this.damage = false;

        System.out.println(" schiffteil an X= " + this.x + " Y =" + this.y + " schaden= " + this.damage);
    }

    public boolean isDamaged()
    {
        return damage;
    }

    public void destroy()
    {
        this.damage = true;
    }
}