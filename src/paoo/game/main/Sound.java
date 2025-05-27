package paoo.game.main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    private Clip clip;
    private URL[] soundURL = new URL[20];
    private FloatControl fc;
    private int volumeScale = 3;
    private float volume;

    public int getVolumeScale() {
        return volumeScale;
    }

    public void setVolumeScale(int volumeScale) {
        this.volumeScale = volumeScale;
    }

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

    public void setFile(int i) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public void checkVolume() {
        switch (volumeScale) {
            case 0 -> volume = -80f;
            case 1 -> volume = -20f;
            case 2 -> volume = -12f;
            case 3 -> volume = -5f;
            case 4 -> volume = 1f;
            case 5 -> volume = 6f;
        }

        fc.setValue(volume);
    }
}

