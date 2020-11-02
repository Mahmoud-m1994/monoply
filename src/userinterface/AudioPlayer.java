package userinterface;

import javax.sound.sampled.*;

import java.io.File;
import java.io.IOException;

/**
 * This class is used to manage the game music and other audio
 * @author Håkon
 */
public class AudioPlayer {
    public static final String BUTTON_CLICK     = "src/userinterface/resources/sound/click.wav";
    public static final String BACKGROUND_MUSIC = "src/userinterface/resources/sound/background_music.wav";

    private static Clip music = null;

    private static boolean musicEnabled = true;

    /**
     * This method is used to play a sound file
     * @param path is the path of the sound file
     * @param loop if true the file loops, if false it plays only once
     */
    public static void play (String path,boolean loop){
        File file = new File(path);

        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile())) {

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            if (path.equals(BACKGROUND_MUSIC))
                music = clip;

            if (loop)
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            else
                clip.loop(0);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to play a sound file (loop is off by default)
     * @param path is the path of the sound file
     */
    public static void play (String path){
        play(path, false);
    }

    /**
     * This method sets the volume for the background music
     * @param val is the value (in decibels) the volume should be increased/decreased by
     */
    public static void setVolume ( float val){
        FloatControl volume = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(val);
    }

    /**
     * This method stops the background music
     */
    public static void stopMusic () {
        music.stop();
        musicEnabled = false;
    }

    /**
     * This method starts the background music
     */
    public static void startMusic () {
        music.start();
        musicEnabled = true;
    }

    /**
     * This method checks if background music is playing
     * @return if the background music is playing or not
     */
    public static boolean musicEnabled () {
        return musicEnabled;
    }
}