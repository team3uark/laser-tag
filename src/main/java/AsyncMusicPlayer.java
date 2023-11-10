import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class AsyncMusicPlayer implements Runnable {
    public String[] musicFiles;

    public AsyncMusicPlayer() {
        this.musicFiles = new String[] {
                "music/Track01.mp3",
                "music/Track02.mp3",
                "music/Track03.mp3",
                "music/Track04.mp3",
                "music/Track05.mp3",
                "music/Track06.mp3",
                "music/Track07.mp3",
                "music/Track08.mp3"
        };
    }

    @Override
    public void run() {
        Random random = new Random();
        int randomIndex = random.nextInt(this.musicFiles.length);
        String musicFilePath = this.musicFiles[randomIndex];
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
//        AsyncMp3Player asyncMusicPlayer = new AsyncMp3Player();
//        Thread thread = new Thread(asyncMusicPlayer);
//        thread.start();
//    }
}