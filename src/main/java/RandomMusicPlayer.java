import javax.swing.*;
import java.util.Random;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RandomMusicPlayer {
    public void playRandomMusic() {
        String[] musicFiles = {
                "music/Track01.mp3",
                "music/Track02.mp3",
                "music/Track03.mp3",
                "music/Track04.mp3",
                "music/Track05.mp3",
                "music/Track06.mp3",
                "music/Track07.mp3",
                "music/Track08.mp3"
        };
        Random random = new Random();
        int randomIndex = random.nextInt(musicFiles.length);
        String musicFilePath = musicFiles[randomIndex];
        try {
            FileInputStream fileInputStream = new FileInputStream(musicFilePath);
            Player player = new Player(fileInputStream);
            player.play();
        } catch (FileNotFoundException | javazoom.jl.decoder.JavaLayerException e) {
            e.printStackTrace();
        }
    }

    //test purpose
//    public static void main(String[] args) {
//        new RandomMusicPlayer().playRandomMusic();
//    }

}