package RedBall;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

public class SoundManager {

    private Clip clip;
    private static Clip backgroundClip;

    // Phương thức phát âm thanh thông thường (cho hiệu ứng âm thanh)
    public void playSound(String soundFileName) {
        try {
            // Sử dụng getClass().getResource để lấy đường dẫn tài nguyên chính xác
            URL soundURL = getClass().getResource("/sounds/" + soundFileName);
            if (soundURL != null) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start(); // Bắt đầu phát âm thanh
            } else {
                System.out.println("File âm thanh không tìm thấy: " + soundFileName);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Phương thức dừng âm thanh
    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // Phương thức phát nhạc nền liên tục
    public static void playBackgroundMusic(String musicFileName) {
        try {
            // Sử dụng getClass().getResource để lấy đường dẫn tài nguyên chính xác
            URL musicURL = SoundManager.class.getResource("/sounds/" + musicFileName);
            if (musicURL != null) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicURL);
                backgroundClip = AudioSystem.getClip();
                backgroundClip.open(audioStream);
                backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);  // Lặp lại nhạc nền liên tục
                backgroundClip.start(); // Bắt đầu phát nhạc nền
            } else {
                System.out.println("File nhạc nền không tìm thấy: " + musicFileName);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Phương thức dừng nhạc nền
    public static void stopBackgroundMusic() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
        }
    }
}
