package sample;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

public class ThreadPlayMusic implements Runnable {
    @Override
    public void run() {
        //URL url = ThreadPlayMusic.class.getResource("D:\\Programs\\Sudoku\\Sudoku\\src\\resources\\music\\winNew.wav");
        try {
            URL defaultSound = getClass().getResource("/resources/music/loseNew.wav");
            File soundFile = new File(defaultSound.toURI());
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(defaultSound);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
