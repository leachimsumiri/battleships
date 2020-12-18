package main;

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

    public static final Field player1SmallShipImageInitialField = new Field(1520,640);
    public static final Field player1MediumShipImageInitialField = new Field(1520,720);
    public static final Field player1LargeShipImageInitialField = new Field(1520,800);
    public static final Field player1XLargeShipImageInitialField = new Field(1520,880);

    public static final Field player2SmallShipImageInitialField = new Field(1800 - 1520 - 3 * 40,640);
    public static final Field player2MediumShipImageInitialField = new Field(1800 - 1520 - 3 * 40,720);
    public static final Field player2LargeShipImageInitialField = new Field(1800 - 1520 - 3 * 40,800);
    public static final Field player2XLargeShipImageInitialField = new Field(1800 - 1520 - 3 * 40,880);
}
