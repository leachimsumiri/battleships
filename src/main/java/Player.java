import java.util.ArrayList;

public class Player {
    PlayerGameField playerGameField = new PlayerGameField();

    private ArrayList<Field> attackedFields = new ArrayList<>();

    public void saveAttack(Field field) {
        this.attackedFields.add(field);
    }

    public boolean hasPreviouslyAttackedSameField(Field field) {
        for (Field attackedField : this.attackedFields) {
            if (attackedField.equals(field)) {
                return true;
            }
        }
        return false;
    }

    public void resetAttackedFields() {
        this.attackedFields = new ArrayList<>();
    }
}
