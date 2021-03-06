public class ShipPositionConstraints {
    public static final int TOP_LEFT_GAMEFIELD_X_LOW_CONSTRAINT = Constants.GAMEFIELD_SIZE + Constants.FIELD_PIXEL_SIZE;
    public static final int TOP_LEFT_GAMEFIELD_X_HIGH_CONSTRAINT = Constants.GAMEFIELD_SIZE + Constants.GAMEFIELD_SIZE;
    public static final int TOP_LEFT_GAMEFIELD_Y_LOW_CONSTRAINT = Constants.FIELD_PIXEL_SIZE + Constants.FIELD_PIXEL_SIZE;
    public static final int TOP_LEFT_GAMEFIELD_Y_HIGH_CONSTRAINT = Constants.GAMEFIELD_SIZE + Constants.FIELD_PIXEL_SIZE;
    public static final int TOP_LEFT_CONSTRAINTS[] = {
            TOP_LEFT_GAMEFIELD_X_LOW_CONSTRAINT,
            TOP_LEFT_GAMEFIELD_X_HIGH_CONSTRAINT,
            TOP_LEFT_GAMEFIELD_Y_LOW_CONSTRAINT,
            TOP_LEFT_GAMEFIELD_Y_HIGH_CONSTRAINT
    };

    public static final int TOP_RIGHT_GAMEFIELD_X_LOW_CONSTRAINT = Constants.GAMEFIELD_SIZE + Constants.FIELD_PIXEL_SIZE
            + 10 * Constants.FIELD_PIXEL_SIZE + 2 * Constants.FIELD_PIXEL_SIZE;
    public static final int TOP_RIGHT_GAMEFIELD_X_HIGH_CONSTRAINT = Constants.GAMEFIELD_SIZE + Constants.GAMEFIELD_SIZE +
            Constants.GAMEFIELD_SIZE + Constants.FIELD_PIXEL_SIZE;
    public static final int TOP_RIGHT_GAMEFIELD_Y_LOW_CONSTRAINT = Constants.FIELD_PIXEL_SIZE + Constants.FIELD_PIXEL_SIZE;
    public static final int TOP_RIGHT_GAMEFIELD_Y_HIGH_CONSTRAINT = Constants.GAMEFIELD_SIZE + Constants.FIELD_PIXEL_SIZE;
    public static final int TOP_RIGHT_CONSTRAINTS[] = {
            TOP_RIGHT_GAMEFIELD_X_LOW_CONSTRAINT,
            TOP_RIGHT_GAMEFIELD_X_HIGH_CONSTRAINT,
            TOP_RIGHT_GAMEFIELD_Y_LOW_CONSTRAINT,
            TOP_RIGHT_GAMEFIELD_Y_HIGH_CONSTRAINT
    };

    public static final int BOTTOM_LEFT_GAMEFIELD_X_LOW_CONSTRAINT = Constants.GAMEFIELD_SIZE + Constants.FIELD_PIXEL_SIZE;
    public static final int BOTTOM_LEFT_GAMEFIELD_X_HIGH_CONSTRAINT = 2 * Constants.GAMEFIELD_SIZE;
    public static final int BOTTOM_LEFT_GAMEFIELD_Y_LOW_CONSTRAINT = 3 * Constants.FIELD_PIXEL_SIZE + Constants.GAMEFIELD_SIZE;
    public static final int BOTTOM_LEFT_GAMEFIELD_Y_HIGH_CONSTRAINT = Constants.FIELD_PIXEL_SIZE + 920;
    public static final int BOTTOM_LEFT_CONSTRAINTS[] = {
            BOTTOM_LEFT_GAMEFIELD_X_LOW_CONSTRAINT,
            BOTTOM_LEFT_GAMEFIELD_X_HIGH_CONSTRAINT,
            BOTTOM_LEFT_GAMEFIELD_Y_LOW_CONSTRAINT,
            BOTTOM_LEFT_GAMEFIELD_Y_HIGH_CONSTRAINT
    };

    public static final int BOTTOM_RIGHT_GAMEFIELD_X_LOW_CONSTRAINT = (2 * Constants.GAMEFIELD_SIZE) + (2 * Constants.FIELD_PIXEL_SIZE);
    public static final int BOTTOM_RIGHT_GAMEFIELD_X_HIGH_CONSTRAINT = (3 * Constants.GAMEFIELD_SIZE) + Constants.FIELD_PIXEL_SIZE;
    public static final int BOTTOM_RIGHT_GAMEFIELD_Y_LOW_CONSTRAINT = Constants.GAMEFIELD_SIZE + (3 * Constants.FIELD_PIXEL_SIZE);
    public static final int BOTTOM_RIGHT_GAMEFIELD_Y_HIGH_CONSTRAINT = 920 + Constants.FIELD_PIXEL_SIZE;
    public static final int BOTTOM_RIGHT_CONSTRAINTS[] = {
            BOTTOM_RIGHT_GAMEFIELD_X_LOW_CONSTRAINT,
            BOTTOM_RIGHT_GAMEFIELD_X_HIGH_CONSTRAINT,
            BOTTOM_RIGHT_GAMEFIELD_Y_LOW_CONSTRAINT,
            BOTTOM_RIGHT_GAMEFIELD_Y_HIGH_CONSTRAINT
    };
}
