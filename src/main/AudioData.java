package main;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class AudioData {
    private static final Media bombMedia = new Media(new File(Constants.WAV_BOMB).toURI().toString());
    private static final Media missMedia = new Media(new File(Constants.WAV_MISS).toURI().toString());
    private static final Media musicMedia = new Media(new File(Constants.WAV_MUSIC).toURI().toString());
    private static final Media winnerMedia = new Media(new File(Constants.WAV_WINNER).toURI().toString());

    public static MediaPlayer bombPlayer = new MediaPlayer(bombMedia);
    public static MediaPlayer missPlayer = new MediaPlayer(missMedia);
    public static MediaPlayer musicPlayer = new MediaPlayer(musicMedia);
    public static MediaPlayer winnerPlayer = new MediaPlayer(winnerMedia);
}
