package main;

import java.util.ArrayList;

public class Player {
    PlayerGameField playerGameField = new PlayerGameField();

    private ArrayList<Field> attackedFields = new ArrayList<>();

    public void saveAttack(Field field) {
        this.attackedFields.add(field);
    }

    boolean wasPreviouslyAttackedOnField(Field field) {
        for (Field attackedField : this.attackedFields) {
            if (attackedField.getX() == field.getX() && attackedField.getY() == field.getY()) {
                return false;
            }
        }
        return true;
    }

    public void resetAttackedFields() {
        this.attackedFields = new ArrayList<>();
    }
}
