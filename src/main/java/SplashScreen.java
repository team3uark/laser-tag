/**********************************/
/* Splash Screen Class            */
/* Add Documentation              */
/**********************************/
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;

public class SplashScreen extends JWindow {
    private int width;
    private int height;

    public SplashScreen(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void showSplashScreen() {
        // BufferedImage logo = ImageIO.read(new File("logo_resized.jpg"));
        JPanel content = new JPanel();
        content.setBackground(Color.WHITE);
        content.setLayout(new BorderLayout());

        JLabel label = new JLabel(new ImageIcon("images/logo_resized.png")); // Set the path to your splash image
        content.add(label, BorderLayout.CENTER);

        setSize(width, height);
        setLocationRelativeTo(null);
        setContentPane(content);
        setVisible(true);
    }

    public void hideSplashScreen() {
        setVisible(false);
        dispose();
    }
  }
