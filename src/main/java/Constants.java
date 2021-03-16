import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class Constants {
    public static final String NEW_GAME_TEXT = "Neues Spiel";
    public static final String END_GAME_TEXT = "Spiel beenden";
    public static final String RESTART_GAME_TEXT = "Neustart";
    public static final String SAVE_SHIPS_TEXT = "Schiffe speichern";
    public static final String SHOW_OWN_SHIPS_TEXT = "Zeige meine Schiffe";
    public static final String CONTINUE_TEXT = "Weiter";

    public static final int TOTAL_SHIP_COUNT = 10;

    public static final String START_MENU_IMAGE_PATH = "file:res/start.png";
    public static final String PLAYER1_WON_IMAGE_PATH = "file:res/spieler1_gewonnen.png";
    public static final String PLAYER2_WON_IMAGE_PATH = "file:res/spieler2_gewonnen.png";
    public static final String LEFT_ISLAND_IMAGE_PATH = "file:res/Insel_Unten_1.png";
    public static final String RIGHT_ISLAND_IMAGE_PATH = "file:res/Insel_Unten_2.png";
    public static final String MISS_IMAGE_PATH = "file:res/Waterhitmarker.png";
    public static final String HIT_IMAGE_PATH = "file:res/Hit.png";
    public static final String SMALL_SHIP_DESTROYED_IMAGE_PATH = "file:res/1x2_Ship_Destroyed.png";
    public static final String MEDIUM_SHIP_DESTROYED_IMAGE_PATH = "file:res/1x3_Ship_Destroyed.png";
    public static final String LARGE_SHIP_DESTROYED_IMAGE_PATH = "file:res/1x4_Ship_Destroyed.png";
    public static final String XLARGE_SHIP_DESTROYED_IMAGE_PATH = "file:res/1x5_Ship_Destroyed.png";
    private static final String BACKGROUND_IMAGE_PATH = "file:res/BattleshipsBackground.png";

    public static final BackgroundImage BACKGROUND_IMAGE = new BackgroundImage(new Image(BACKGROUND_IMAGE_PATH,
            Constants.FIXED_GAMEWINDOW_WIDTH, Constants.FIXED_GAMEWINDOW_HEIGHT,true, true),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

    public static final String WAV_BOMB = "res/bomb.wav";
    public static final String WAV_MISS = "res/miss.wav";
    public static final String WAV_MUSIC = "res/music.wav";
    public static final String WAV_WINNER = "res/winner.wav";

    private static final Field PLAYER_1_SMALL_SHIP_IMAGE_INITIAL_FIELD  = new Field(Constants.RIGHT_INVENTORY_X_POS,640);
    private static final Field PLAYER_1_MEDIUM_SHIP_IMAGE_INITIAL_FIELD = new Field(Constants.RIGHT_INVENTORY_X_POS,720);
    private static final Field PLAYER_1_LARGE_SHIP_IMAGE_INITIAL_FIELD = new Field(Constants.RIGHT_INVENTORY_X_POS,800);
    private static final Field PLAYER_1_X_LARGE_SHIP_IMAGE_INITIAL_FIELD = new Field(Constants.RIGHT_INVENTORY_X_POS,880);

    public static final InitialFields PLAYER_1_INITIAL_FIELDS = new InitialFields(PLAYER_1_SMALL_SHIP_IMAGE_INITIAL_FIELD,
            PLAYER_1_MEDIUM_SHIP_IMAGE_INITIAL_FIELD, PLAYER_1_LARGE_SHIP_IMAGE_INITIAL_FIELD, PLAYER_1_X_LARGE_SHIP_IMAGE_INITIAL_FIELD);

    private static final Field PLAYER_2_SMALL_SHIP_IMAGE_INITIAL_FIELD = new Field(Constants.LEFT_INVENTORY_X_POS,640);
    private static final Field PLAYER_2_MEDIUM_SHIP_IMAGE_INITIAL_FIELD = new Field(Constants.LEFT_INVENTORY_X_POS,720);
    private static final Field PLAYER_2_LARGE_SHIP_IMAGE_INITIAL_FIELD = new Field(Constants.LEFT_INVENTORY_X_POS,800);
    private static final Field PLAYER_2_X_LARGE_SHIP_IMAGE_INITIAL_FIELD = new Field(Constants.LEFT_INVENTORY_X_POS,880);

    public static final InitialFields PLAYER_2_INITIAL_FIELDS = new InitialFields(PLAYER_2_SMALL_SHIP_IMAGE_INITIAL_FIELD,
            PLAYER_2_MEDIUM_SHIP_IMAGE_INITIAL_FIELD, PLAYER_2_LARGE_SHIP_IMAGE_INITIAL_FIELD, PLAYER_2_X_LARGE_SHIP_IMAGE_INITIAL_FIELD);

    private static final String smallBattleshipImagePath = "file:res/1x2_Schiff_Horizontal_1_Fertig.png";
    private static final String mediumBattleshipImagePath = "file:res/1x3_Schiff_Horizontal_1_Fertig.png";
    private static final String largeBattleshipImagePath = "file:res/1x4_Schiff_Horizontal_1_Fertig.png";
    private static final String xlargeBattleshipImagePath = "file:res/1x5_Schiff_Horizontal_1_Fertig.png";

    public static final Image[] battleshipImages = {
            new Image(smallBattleshipImagePath),
            new Image(mediumBattleshipImagePath),
            new Image(largeBattleshipImagePath),
            new Image(xlargeBattleshipImagePath)
    };

    public static final int AMOUNT_OF_SMALL_SHIPS = 4;
    public static final int AMOUNT_OF_MEDIUM_SHIPS = 3;
    public static final int AMOUNT_OF_LARGE_SHIPS = 2;
    public static final int AMOUNT_OF_XLARGE_SHIPS = 1;

    public static final int FIELDLENGTH_OF_SMALL_SHIPS = 2;
    public static final int FIELDLENGTH_OF_MEDIUM_SHIPS = 3;
    public static final int FIELDLENGTH_OF_LARGE_SHIPS = 4;
    public static final int FIELDLENGTH_OF_XLARGE_SHIPS = 5;

    public static final int FIELD_MIN_VALUE = 0;
    public static final int FIELD_MAX_VALUE = 9;

    public static final int FIELD_PIXEL_SIZE = 40;
    public static final int GAMEFIELD_SIZE = 440;

    public static final int FIXED_GAMEWINDOW_WIDTH = 1800;
    public static final int FIXED_GAMEWINDOW_HEIGHT = 1000;

    public static final int LEFT_INVENTORY_X_POS = 1800 - 1520 - 3 * 40;
    public static final int RIGHT_INVENTORY_X_POS = 1520;
}
