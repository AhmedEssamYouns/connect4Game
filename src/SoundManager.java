import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {

    // Method to load a sound file and play it
    public static void playSound(String soundFile) {
        try {
            // Load the sound file
            File file = new File(soundFile);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);

            // Play the sound
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method to loop background music
    public static void loopSound(String soundFile) {
        try {
            File file = new File(soundFile);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);

            // Loop the sound (set to LOOP_CONTINUOUSLY)
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
