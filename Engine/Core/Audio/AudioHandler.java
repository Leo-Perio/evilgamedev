package Engine.Core.Audio;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioHandler {
    private File[] music;
    private File[] sfx;

    private AudioInputStream in;
    private Clip clip;

    public boolean is_playing;

    public AudioHandler() {
        music = new File[] {
            new File("src/Assets/Music/JoshMadeSong.wav")
        };

        is_playing = false;
    }

    public void PlayMusic(int index) {
        //if (is_playing) StopMusic(); // If alr playing, stop playing

        try {
            in = AudioSystem.getAudioInputStream(music[index]);
            clip = AudioSystem.getClip();
            clip.open(in);
            clip.start();
            is_playing = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void StopMusic() {
        if (clip != null && is_playing) {
            clip.stop();
            clip.close();
        }
    }
}
