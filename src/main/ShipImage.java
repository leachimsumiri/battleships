package main;

import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.image.ImageView;

public class ShipImage {
    private final Field field;
    private final Field startField;
    private final int length;
    private int rotate = 1;
    private int diffVectorX, diffVectorY;
    private double startX, startY, moveX, moveY, setX, setY, newX, newY;

    private final ImageView imageView;
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
        this.diffVectorX = 0;
        this.diffVectorY = 0;
        this.imageView = new ImageView(this.image);
        this.imageView.setX(this.field.getX());
        this.imageView.setY(this.field.getY());

        this.imageView.addEventHandler(MouseEvent.ANY, event -> {
            if (!this.locked) {
                if (event.getEventType() == MouseEvent.MOUSE_PRESSED && event.getButton().equals(MouseButton.PRIMARY)) {
                    setMouseStartCoordinates(event);
                    trackMouseMovement(event);
                }

                if (event.getEventType() == MouseEvent.MOUSE_DRAGGED && event.getButton().equals(MouseButton.PRIMARY)) {
                    setFieldAtDragging(event);
                }

                if (event.getEventType() == MouseEvent.MOUSE_CLICKED && event.getButton().equals(MouseButton.SECONDARY)) {
                    rotateImageAndImageShip();
                }
            }
        });
    }

    private void setMouseStartCoordinates(MouseEvent event){
        startX = event.getSceneX();
        startY = event.getSceneY();
    }

    private void trackMouseMovement(MouseEvent event){
        moveX = ((ImageView) (event.getSource())).getTranslateX();
        moveY = ((ImageView) (event.getSource())).getTranslateY();
    }

    private void setFieldAtDragging(MouseEvent event){
        setX = event.getSceneX() - startX;
        setY = event.getSceneY() - startY;

        newX = moveX + setX;
        newY = moveY + setY;

        int diffX = (int) newX % Constants.FIELD_PIXEL_SIZE;
        newX = newX - diffX;

        int diffY = (int) newY % Constants.FIELD_PIXEL_SIZE;
        newY = newY - diffY;

        ((ImageView) (event.getSource())).setTranslateX(newX);
        ((ImageView) (event.getSource())).setTranslateY(newY);

        this.field.setX(this.startField.getX() + getDiffVectorX() + (int) newX);
        this.field.setY(this.startField.getY() + getDiffVectorY() + (int) newY);
    }

    public void lock() {
        this.locked = true;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void changePosition(Field field) {
        this.imageView.setTranslateX(field.getX());
        this.imageView.setTranslateY(field.getY());
        this.field.setX(field.getX() + this.startField.getX() + diffVectorX);
        this.field.setY(field.getY() + this.startField.getY() + diffVectorY);
    }

    public void reset() {
        this.locked = false;
        this.changePosition(new Field());
    }

    private void rotateImageAndImageShip() {
        double value = this.imageView.getRotate();
        this.imageView.setRotate(value - 90);

        if (this.getLength() % 2 != 1) {
            if (this.rotate % 2 == 1) {
                this.imageView.setX(this.imageView.getX() + 20);
                this.imageView.setY(this.imageView.getY() - 20);
            } else {
                this.imageView.setX(this.imageView.getX() - 20);
                this.imageView.setY(this.imageView.getY() + 20);
            }
        }

        this.rotate++;

        switch (this.direction) {
            case UP:
                this.direction = Direction.LEFT;
                if (this.getLength() % 2 == 1) {
                    this.field.setX(this.startField.getX() + 40 * (this.getLength() / 2));
                    this.field.setY(this.startField.getY() - 40 * (this.getLength() / 2));

                    this.setDiffVectorX(this.getDiffVectorX() + 40 * (this.getLength() / 2));
                    this.setDiffVectorY(this.getDiffVectorY() - 40 * (this.getLength() / 2));
                } else {
                    if (this.getLength() != 2) {
                        this.field.setX(this.startField.getX() + 40);
                        this.field.setY(this.startField.getY() - 40);

                        this.setDiffVectorX(this.getDiffVectorX() + 40);
                        this.setDiffVectorY(this.getDiffVectorY() - 40);
                    }
                }
                break;
            case DOWN:
                this.direction = Direction.RIGHT;
                if (this.getLength() % 2 == 1) {
                    this.field.setX(this.startField.getX() - 40 * (this.getLength() / 2));
                    this.field.setY(this.startField.getY() + 40 * (this.getLength() / 2));

                    this.setDiffVectorX(this.getDiffVectorX() - 40 * (this.getLength() / 2));
                    this.setDiffVectorY(this.getDiffVectorY() + 40 * (this.getLength() / 2));
                } else {
                    if (this.getLength() == 2) {
                        this.field.setX(this.startField.getX() - 40);
                        this.field.setY(this.startField.getY() + 40);

                        this.setDiffVectorX(this.getDiffVectorX() - 40);
                        this.setDiffVectorY(this.getDiffVectorY() + 40);

                    } else {
                        this.field.setX(this.startField.getX() - 2 * 40);
                        this.field.setY(this.startField.getY() + 2 * 40);

                        this.setDiffVectorX(this.getDiffVectorX() - 40 * 2);
                        this.setDiffVectorY(this.getDiffVectorY() + 40 * 2);
                    }
                }
                break;
            case LEFT:
                this.direction = Direction.DOWN;
                if (this.getLength() % 2 == 1) {
                    this.field.setX(this.startField.getX() - 40 * (this.getLength() / 2));
                    this.field.setY(this.startField.getY() - 40 * (this.getLength() / 2));

                    this.setDiffVectorX(this.getDiffVectorX() - 40 * (this.getLength() / 2));
                    this.setDiffVectorY(this.getDiffVectorY() - 40 * (this.getLength() / 2));
                } else {
                    if (this.getLength() == 2) {
                        this.field.setY(this.startField.getY() - 40);

                        this.setDiffVectorY(this.getDiffVectorY() - 40);
                    } else {
                        this.field.setX(this.startField.getX() - 40);
                        this.field.setY(this.startField.getY() - 2 * 40);

                        this.setDiffVectorX(this.getDiffVectorX() - 40);
                        this.setDiffVectorY(this.getDiffVectorY() - 40 * 2);
                    }
                }
                break;
            case RIGHT:
                this.direction = Direction.UP;
                if (this.getLength() % 2 == 1) {
                    this.field.setX(this.startField.getX() + 40 * (this.getLength() / 2));
                    this.field.setY(this.startField.getY() + 40 * (this.getLength() / 2));

                    this.setDiffVectorX(this.getDiffVectorX() + 40 * (this.getLength() / 2));
                    this.setDiffVectorY(this.getDiffVectorY() + 40 * (this.getLength() / 2));
                } else {
                    if (this.getLength() == 2) {
                        this.field.setX(this.startField.getX() + 40);

                        this.setDiffVectorX(this.getDiffVectorX() + 40);
                    } else {
                        this.field.setX(this.startField.getX() + 2 * 40);
                        this.field.setY(this.startField.getY() + 40);

                        this.setDiffVectorX(this.getDiffVectorX() + 2 * 40);
                        this.setDiffVectorY(this.getDiffVectorY() + 40);
                    }
                }
                break;
        }
    }

    public void rotateTo(Direction directionRotate) {
        while (this.direction != directionRotate) {
            this.rotateImageAndImageShip();
        }
    }

    public static ShipImage[] createInitialShipImages(InitialFields initialFields){
        return new ShipImage[]{
                new ShipImage(initialFields.getSmallShipField(), Constants.FIELDLENGTH_OF_SMALL_SHIPS, Constants.battleshipImages[0]),
                new ShipImage(initialFields.getSmallShipField(), Constants.FIELDLENGTH_OF_SMALL_SHIPS, Constants.battleshipImages[0]),
                new ShipImage(initialFields.getSmallShipField(), Constants.FIELDLENGTH_OF_SMALL_SHIPS, Constants.battleshipImages[0]),
                new ShipImage(initialFields.getSmallShipField(), Constants.FIELDLENGTH_OF_SMALL_SHIPS, Constants.battleshipImages[0]),
                new ShipImage(initialFields.getMediumShipField(), Constants.FIELDLENGTH_OF_MEDIUM_SHIPS, Constants.battleshipImages[1]),
                new ShipImage(initialFields.getMediumShipField(), Constants.FIELDLENGTH_OF_MEDIUM_SHIPS, Constants.battleshipImages[1]),
                new ShipImage(initialFields.getMediumShipField(), Constants.FIELDLENGTH_OF_MEDIUM_SHIPS, Constants.battleshipImages[1]),
                new ShipImage(initialFields.getLargeShipField(), Constants.FIELDLENGTH_OF_LARGE_SHIPS, Constants.battleshipImages[2]),
                new ShipImage(initialFields.getLargeShipField(), Constants.FIELDLENGTH_OF_LARGE_SHIPS, Constants.battleshipImages[2]),
                new ShipImage(initialFields.getXlargeShipField(), Constants.FIELDLENGTH_OF_XLARGE_SHIPS, Constants.battleshipImages[3])
        };
    }
}
