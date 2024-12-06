import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class sound {
    private Clip clip;
    private URL soundURL[] = new URL[30];

    public sound() {
        soundURL[0] = getClass().getResource("/Music/crazy-dave.wav");
    }

    public void setFile(int i) {
        try {
            // Kiểm tra nếu URL tồn tại
            if (soundURL[i] != null) {
                AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
                clip = AudioSystem.getClip();
                clip.open(ais);
            } else {
                System.out.println("Tệp âm thanh không tồn tại");
            }
        } catch (Exception e) {
            e.printStackTrace();  // In ra lỗi nếu có
        }
    }

    public void play() {
        if (clip != null) {
            clip.start();
        } else {
            System.out.println("Clip chưa được khởi tạo.");
        }
    }

    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            System.out.println("Clip chưa được khởi tạo.");
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        } else {
            System.out.println("Clip chưa được khởi tạo.");
        }
    }
}
