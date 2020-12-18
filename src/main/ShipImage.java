package main;

import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.image.ImageView;

public class ShipImage {
    private Field field, startField;
    private int length;
    private int rotate = 1;
    private int diffVectorX, diffVectorY;
    private double startX, startY, moveX, moveY, setX, setY, newX, newY;

    private ImageView imageView;
    private Image image;
    private Direction direction;

    private boolean locked = false;

    public Field getField() {
        return field;
    }

    private void setDiffVectorX(int diffVectorX) {
        this.diffVectorX = diffVectorX;
    }

    private void setDiffVectorY(int diffVectorY) {
        this.diffVectorY = diffVectorY;
    }

    public int getDiffVectorX() {
        return diffVectorX;
    }

    public int getDiffVectorY() {
        return diffVectorY;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getLength() {
        return length;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public ShipImage(Field newField, int length, Image image) {
        this.field = newField;
        this.startField = new Field(this.field.getX(), this.field.getY());
        this.length = length;
        this.image = image;
        this.direction = Direction.RIGHT;

        this.imageView = new ImageView(image);
        imageView.setX(this.field.getX());
        imageView.setY(this.field.getY());
        this.setDiffVectorX(0);
        this.setDiffVectorY(0);

        imageView.addEventHandler(MouseEvent.ANY, event -> {
            if (!locked) {
                if (event.getEventType() == MouseEvent.MOUSE_PRESSED && event.getButton().equals(MouseButton.PRIMARY)) {
                    //Koordinaten vom Mouseclick
                    startX = event.getSceneX();
                    startY = event.getSceneY();

                    moveX = ((ImageView) (event.getSource())).getTranslateX();
                    moveY = ((ImageView) (event.getSource())).getTranslateY();
                }

                if (event.getEventType() == MouseEvent.MOUSE_DRAGGED && event.getButton().equals(MouseButton.PRIMARY)) {
                    //Differenz der Koordinaten von der Maus, wo wir losgelassen haben und angefangen haben
                    setX = event.getSceneX() - startX;
                    setY = event.getSceneY() - startY;

                    //Die Differenz zwischen Bild Anfang und wie weit die maus gedragged wurde
                    newX = moveX + setX;
                    newY = moveY + setY;

                    /*wir runden es, damit es durch 40 teilbar ist (weil alle Felder durch 40 teilbar sind)*/
                    int diffX = (int) newX % 40;
                    newX = newX - diffX;

                    int diffY = (int) newY % 40;
                    newY = newY - diffY;

                    ((ImageView) (event.getSource())).setTranslateX(newX);
                    ((ImageView) (event.getSource())).setTranslateY(newY);

                    /*Alle Faktoren werden berücksichtigt, damit die neuen Koordinaten stimmen, muss auch die
                    errechnete differenz vom rotieren mit einbezogen werden.*/
                    field.setX(startField.getX() + getDiffVectorX() + (int) newX);
                    field.setY(startField.getY() + getDiffVectorY() + (int) newY);
                }

                if (event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getButton().equals(MouseButton.SECONDARY)) {
                    rotate();
                }
            }
        });
    }

    /*Gelocked wird, wenn saveShips in der main ein Schiff gespeichert wird oder wenn ein zerstörtes Schiff
    gezeichnet wird. Dient dafür, dass man es nicht mehr draggen kann.*/
    public void lock() {
        this.locked = true;
    }

    public boolean isLocked() {
        return this.locked;
    }

    /*Wir übergeben zwar x und y = 0 wenn wir die Methode aufrufen, bedeuetet aber nur, dass es zur
    Ursprungskoordinate zurückspringt (wird von dort alles relativ gerechnet). Ermöglicht durch this.x=xx...*/
    //Position muss von den ursprugort angegeben werden und nicht von 0/0
    public void changePosition(Field field) {
        this.imageView.setTranslateX(field.getX());
        this.imageView.setTranslateY(field.getY());
        this.field.setX(field.getX() + this.startField.getX() + diffVectorX);
        this.field.setY(field.getY() + this.startField.getY() + diffVectorY);
    }

    /*Nach dem reseten, soll das Schiff wieder zum Ursprungsort zurück*/
    public void reset() {
        this.locked = false;
        this.changePosition(new Field());
    }

    //rotiert das Bild und das im code angelegte Schiff
    private void rotate() {
        /*Die rotate Methode rotiert immer um die Mitte eines Objektes. Das ist ein Problem bei Geraden
         Schiffen weil sie nach dem Rotieren zwischen zwei Feldern liegen würden. Hier verhindern wir
         das, durch Differezenaufsummierung, je nachdem wie oft gedreht wurde.*/
        if (getLength() % 2 == 1) {
            double value = imageView.getRotate();
            imageView.setRotate(value - 90);
        } else {
            /*rotate: Je nachdem welcher Wert rotate hat, muss man addieren oder subtrahieren (kommt
             drauf an wie oft man geklickt hat),*/
            if (rotate % 2 == 1) {
                double value = imageView.getRotate();
                imageView.setRotate(value - 90);
                imageView.setX(imageView.getX() + 20);
                imageView.setY(imageView.getY() - 20);
            } else {
                double value = imageView.getRotate();
                imageView.setRotate(value - 90);
                imageView.setX(imageView.getX() - 20);
                imageView.setY(imageView.getY() + 20);
            }
        }
        rotate++;

         /*Switch ist dafür da, um die Bilder die wir drehen und die ImageShips ("Digital angelegte
          Schiffe" die wir erstellen, nach dem Rotieren abzugleichen. Weil nur weil wir das Bild drehen,
          heißt es ja nicht, dass sich unsere ImageShips mitdrehen. Sind ja zwei verschiedene
          Entitäten. Immer Abhängig von welcher Richtung man dreht, ändern wir manuell dann die
          Direction mit den dementsprechenden Rechungen auch um.*/
        switch (direction) {
            case UP:
                direction = Direction.LEFT;
                if (getLength() % 2 == 1) {
                    field.setX(startField.getX() + 40 * (getLength() / 2));
                    field.setY(startField.getY() - 40 * (getLength() / 2));

                    setDiffVectorX(getDiffVectorX() + 40 * (getLength() / 2));
                    setDiffVectorY(getDiffVectorY() - 40 * (getLength() / 2));
                } else {
                    if (getLength() == 2) {
                    } else {
                        field.setX(startField.getX() + 40);
                        field.setY(startField.getY() - 40);

                        setDiffVectorX(getDiffVectorX() + 40);
                        setDiffVectorY(getDiffVectorY() - 40);
                    }
                }
                break;
            case DOWN:
                direction = Direction.RIGHT;
                if (getLength() % 2 == 1) {
                    field.setX(startField.getX() - 40 * (getLength() / 2));
                    field.setY(startField.getY() + 40 * (getLength() / 2));

                    setDiffVectorX(getDiffVectorX() - 40 * (getLength() / 2));
                    setDiffVectorY(getDiffVectorY() + 40 * (getLength() / 2));
                } else {
                    if (getLength() == 2) {
                        field.setX(startField.getX() - 40);
                        field.setY(startField.getY() + 40);

                        setDiffVectorX(getDiffVectorX() - 40);
                        setDiffVectorY(getDiffVectorY() + 40);

                    } else {
                        field.setX(startField.getX() - 2 * 40);
                        field.setY(startField.getY() + 2 * 40);

                        setDiffVectorX(getDiffVectorX() - 40 * 2);
                        setDiffVectorY(getDiffVectorY() + 40 * 2);
                    }
                }
                break;
            case LEFT:
                direction = Direction.DOWN;
                if (getLength() % 2 == 1) {
                    field.setX(startField.getX() - 40 * (getLength() / 2));
                    field.setY(startField.getY() - 40 * (getLength() / 2));

                    setDiffVectorX(getDiffVectorX() - 40 * (getLength() / 2));
                    setDiffVectorY(getDiffVectorY() - 40 * (getLength() / 2));
                } else {
                    if (getLength() == 2) {
                        field.setY(startField.getY() - 40);

                        setDiffVectorY(getDiffVectorY() - 40);
                    } else {
                        field.setX(startField.getX() - 40);
                        field.setY(startField.getY() - 2 * 40);

                        setDiffVectorX(getDiffVectorX() - 40);
                        setDiffVectorY(getDiffVectorY() - 40 * 2);
                    }
                }
                break;
            case RIGHT:
                direction = Direction.UP;
                if (getLength() % 2 == 1) {
                    field.setX(startField.getX() + 40 * (getLength() / 2));
                    field.setY(startField.getY() + 40 * (getLength() / 2));

                    setDiffVectorX(getDiffVectorX() + 40 * (getLength() / 2));
                    setDiffVectorY(getDiffVectorY() + 40 * (getLength() / 2));
                } else {
                    if (getLength() == 2) {
                        field.setX(startField.getX() + 40);

                        setDiffVectorX(getDiffVectorX() + 40);
                    } else {
                        field.setX(startField.getX() + 2 * 40);
                        field.setY(startField.getY() + 40);

                        setDiffVectorX(getDiffVectorX() + 2 * 40);
                        setDiffVectorY(getDiffVectorY() + 40);
                    }
                }
                break;
        }
    }


    /*Verwenden wir beim reset button in der Main Methode, um auf RIGHT zu rotieren z.B. Es dreht solange, bis es der
     übergebenen Direction entspricht.*/
    public void rotateTo(Direction directionRotate) {
        while (this.direction != directionRotate) {
            this.rotate();
        }
    }
}
