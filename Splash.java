import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Splash {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowSplashScreen();
        });
    }

    public static void createAndShowSplashScreen() {
        JFrame splashFrame = new JFrame("Splash Screen");
        splashFrame.setUndecorated(true);
        splashFrame.setSize(1000, 700);

        ImageIcon splashImage = new ImageIcon("logo_resized.png"); // Replace with your image file path
        JLabel splashLabel = new JLabel(splashImage);

        splashFrame.add(splashLabel);
        splashFrame.setLocationRelativeTo(null); // Center the splash screen on the screen
        splashFrame.setVisible(true);

        // Simulate some initialization process (replace with your actual initialization code)
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