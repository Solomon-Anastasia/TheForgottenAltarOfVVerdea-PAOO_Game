package paoo.game.main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

/**
 * Manages audio playback for the game including background music and sound effects.
 * This class provides functionality to load, play, loop, and control the volume of audio files.
 * It supports various game sounds such as background music, UI sounds, and gameplay effects.
 * <p>
 * The Sound class uses Java's Sound API to handle WAV audio files and provides
 * volume control through a scale-based system (0-5 scale mapped to decibel values).
 */
public class Sound {

    /**
     * The current audio clip being managed
     */
    private Clip clip;

    /**
     * Array storing URLs to all sound files used in the game
     */
    private URL[] soundURL = new URL[20];

    /**
     * Float control for managing audio volume
     */
    private FloatControl fc;

    /**
     * Current volume scale setting (0-5 range)
     */
    private int volumeScale = 3;

    /**
     * Current volume level in decibels
     */
    private float volume;

    /**
     * Gets the current volume scale setting.
     *
     * @return the volume scale (0-5 where 0 is muted and 5 is maximum volume)
     */
    public int getVolumeScale() {
        return volumeScale;
    }

    /**
     * Sets the volume scale and updates the audio volume accordingly.
     * The scale ranges from 0 (muted) to 5 (maximum volume).
     *
     * @param volumeScale the new volume scale (should be between 0-5)
     */
    public void setVolumeScale(int volumeScale) {
        this.volumeScale = volumeScale;
    }

    /**
     * Constructs a new Sound manager and initializes all sound file URLs.
     * This constructor loads references to all audio files used in the game:
     * <p>
     * Sound file mapping:
     * - Index 0: Background music for general gameplay
     * - Index 1: Carrot pickup sound effect
     * - Index 2: UI cursor/menu navigation sound
     * - Index 3: Game over sound effect
     * - Index 4: NPC speaking/dialogue sound
     * - Index 5: Chest/door unlock sound effect
     * - Index 6: Player damage received sound
     * - Index 7: Monster hit/attack sound
     * - Index 8: Victory/achievement fanfare sound
     * <p>
     * All sound files are expected to be in WAV format and located in the "/sound/" resource directory.
     */
    public Sound() {
        soundURL[0] = getClass().getResource("/sound/BackgroundMusic1.wav");
        soundURL[1] = getClass().getResource("/sound/carrot.wav");
        soundURL[2] = getClass().getResource("/sound/cursor.wav");
        soundURL[3] = getClass().getResource("/sound/gameover.wav");
        soundURL[4] = getClass().getResource("/sound/speak.wav");
        soundURL[5] = getClass().getResource("/sound/unlock.wav");
        soundURL[6] = getClass().getResource("/sound/receivedamage.wav");
        soundURL[7] = getClass().getResource("/sound/hitmonster.wav");
        soundURL[8] = getClass().getResource("/sound/fanfare.wav");
    }

    /**
     * Loads and prepares an audio file for playback.
     * This method sets up the audio clip, opens the audio stream, and initializes volume control.
     *
     * @param i the index of the sound file to load (corresponds to soundURL array indices)
     * @throws Exception if there's an error loading the audio file or initializing the clip
     *                   <p>
     *                   Usage example:
     *                   - setFile(0) loads background music
     *                   - setFile(1) loads carrot pickup sound
     *                   - setFile(3) loads game over sound
     */
    public void setFile(int i) {
        try {
            // Create audio input stream from the selected sound file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[i]);

            // Get a new clip instance and open it with the audio stream
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // Set up volume control for the clip
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume(); // Apply current volume setting
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Starts playback of the currently loaded audio clip.
     * The sound will play once from the beginning. For looping playback, use loop() instead.
     */
    public void play() {
        clip.start();
    }

    /**
     * Starts continuous looping playback of the currently loaded audio clip.
     * The sound will repeat indefinitely until stop() is called.
     * This is typically used for background music.
     */
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stops playback of the currently playing audio clip.
     * This immediately halts audio playback and resets the clip position to the beginning.
     */
    public void stop() {
        clip.stop();
    }

    /**
     * Updates the audio volume based on the current volume scale setting.
     * This method converts the user-friendly volume scale (0-5) to decibel values
     * that the audio system can understand.
     * <p>
     * Volume scale mapping:
     * - 0: -80dB (effectively muted)
     * - 1: -20dB (very quiet)
     * - 2: -12dB (quiet)
     * - 3: -5dB (normal/default volume)
     * - 4: +1dB (loud)
     * - 5: +6dB (maximum volume)
     * <p>
     * This method is automatically called when loading a new audio file
     * and should be called after changing the volume scale.
     */
    public void checkVolume() {
        switch (volumeScale) {
            case 0 -> volume = -80f; // Muted
            case 1 -> volume = -20f; // Very quiet
            case 2 -> volume = -12f; // Quiet
            case 3 -> volume = -5f;  // Normal (default)
            case 4 -> volume = 1f;   // Loud
            case 5 -> volume = 6f;   // Maximum
        }

        fc.setValue(volume); // Apply the volume setting to the audio clip
    }
}