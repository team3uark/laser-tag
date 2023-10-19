import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashScreen {
//    public static void main(String[] args) {
//        //sample test to check it works
//        SwingUtilities.invokeLater(() -> {
//            createAndShowSplashScreen();
//        });
//    }

    public static void showSplashScreen() {
        JFrame splashFrame = new JFrame("Splash Screen");
        splashFrame.setUndecorated(true);
        splashFrame.setSize(1000, 700);

        ImageIcon splashImage = new ImageIcon("logo_resized.png");
        JLabel splashLabel = new JLabel(splashImage);

        splashFrame.add(splashLabel);
        splashFrame.setLocationRelativeTo(null); // Center the splash screen on the screen
        splashFrame.setVisible(true);

        // splash screen runs for 3 seconds
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                splashFrame.dispose(); // Close the splash screen
            }
        });
        timer.setRepeats(false); // Run the timer only once
        timer.start();
    }
}
