package main;

public class ShipPart {
    private final Field field;
    private boolean damage;

    public ShipPart(Field field) {
        this.field = field;
        this.damage = false;
    }

    public Field getField() {
        return field;
    }

    public boolean isDamaged() {
        return damage;
    }

    public void destroy() {
        this.damage = true;
    }
}