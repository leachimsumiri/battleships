package main;

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

    public static Field caclulateFieldFromImagePixels(Field shipField, int p1x, int p1y, int p2x, int p2y) {
        Field field = new Field();

        //Checkt ob die Koordinaten vom Schiff im richtigen Feld liegen
        if (shipField.getX() >= p1x && shipField.getX() <= p2x && shipField.getY() >= p1y && shipField.getY() <= p2y) {
            int vectorx, vectory;
            //berechnet Relation zum Spielfeld
            //TODO gscheit berechnen
            vectorx = shipField.getX() - p1x;
            vectory = shipField.getY() - p1y;

            //40px = Feldbreite & Höhe
            field.setX(vectorx / 40);
            field.setY(vectory / 40);
            return field;
        }
        return null;
    }
}
