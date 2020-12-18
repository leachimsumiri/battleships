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

    public static final String WAV_BOMB = "res/bomb.wav";
    public static final String WAV_MISS = "res/miss.wav";
    public static final String WAV_MUSIC = "res/music.wav";
    public static final String WAV_WINNER = "res/winner.wav";

    public static final Field PLAYER_1_SMALL_SHIP_IMAGE_INITIAL_FIELD = new Field(1520,640);
    public static final Field PLAYER_1_MEDIUM_SHIP_IMAGE_INITIAL_FIELD = new Field(1520,720);
    public static final Field PLAYER_1_LARGE_SHIP_IMAGE_INITIAL_FIELD = new Field(1520,800);
    public static final Field PLAYER_1_X_LARGE_SHIP_IMAGE_INITIAL_FIELD = new Field(1520,880);

    public static final Field PLAYER_2_SMALL_SHIP_IMAGE_INITIAL_FIELD = new Field(1800 - 1520 - 3 * 40,640);
    public static final Field PLAYER_2_MEDIUM_SHIP_IMAGE_INITIAL_FIELD = new Field(1800 - 1520 - 3 * 40,720);
    public static final Field PLAYER_2_LARGE_SHIP_IMAGE_INITIAL_FIELD = new Field(1800 - 1520 - 3 * 40,800);
    public static final Field PLAYER_2_X_LARGE_SHIP_IMAGE_INITIAL_FIELD = new Field(1800 - 1520 - 3 * 40,880);
}
