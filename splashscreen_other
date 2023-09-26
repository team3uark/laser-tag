import java.awt.*;
import javax.swing.*;

public class SplashJava extends JWindow {
   Image splashScreen;
   ImageIcon imageIcon;
   public SplashJava() {
      splashScreen = Toolkit.getDefaultToolkit().getImage("images/logo_resized.png");
      imageIcon = new ImageIcon(splashScreen);
      setSize(imageIcon.getIconWidth(),imageIcon.getIconHeight());
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      int x = (screenSize.width-getSize().width)/2;
      int y = (screenSize.height-getSize().height)/2;
      setLocation(x,y);
      setVisible(true);
   }
   // Paint image onto JWindow
   public void paint(Graphics g) {
      super.paint(g);
      g.drawImage(splashScreen, 0, 0, this);
   }
   public static void main(String[]args) {
      SplashJava splash = new SplashJava();
      try {
         // Make JWindow appear for 10 seconds before disappear
         Thread.sleep(10000);
         splash.dispose();
      } catch(Exception e) {
         e.printStackTrace();
      }
   }
}
