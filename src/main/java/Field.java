public class Field {
    private int x;
    private int y;

    public Field(){
        this(0,0);
    }

    public Field(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean equals(Field field){
        return this.x == field.x && this.y == field.y;
    }

    public void incrementX(){
        this.x++;
    }

    public void incrementY(){
        this.y++;
    }

    public void decrementX(){
        this.x--;
    }

    public void decrementY(){
        this.y--;
    }

    public static Field caclulateFieldFromImagePixels(Field shipField, int[] constraints) {
        Field field = new Field();

        if (fieldFulfillsConstraints(shipField, constraints)) {
            int vectorx = shipField.getX() - constraints[0];
            int vectory = shipField.getY() - constraints[2];

            field.setX(vectorx / Constants.FIELD_PIXEL_SIZE);
            field.setY(vectory / Constants.FIELD_PIXEL_SIZE);
            return field;
        }
        return null;
    }

    private static boolean fieldFulfillsConstraints(Field field, int[] constraints){
        return field.getX() >= constraints[0] && field.getX() <= constraints[1] && field.getY() >= constraints[2]
                && field.getY() <= constraints[3];
    }
}
