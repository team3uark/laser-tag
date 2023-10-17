import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameStartCountdown {
    private static JLabel countdownLabel;

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(GameStartCountdown::createAndShowGUI);
//    }
    public static void createStartCounddown() {
        JFrame frame = new JFrame("Game Start Countdown");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel textLabel = new JLabel("Game will start in", SwingConstants.CENTER);
        textLabel.setFont(new Font("Arial", Font.BOLD, 40));
        frame.add(textLabel, gbc);

        countdownLabel = new JLabel("", SwingConstants.CENTER);
        countdownLabel.setFont(new Font("Arial", Font.BOLD, 70));
        gbc.gridy = 1;
        frame.add(countdownLabel, gbc);

        startCountdown();

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public static void startCountdown() {
        Timer timer = new Timer(1000, new ActionListener() {
            int counter = 5; // starting from 5 seconds

            @Override
            public void actionPerformed(ActionEvent e) {
                if (counter > 0) {
                    countdownLabel.setText(String.valueOf(counter));
                    counter--;
                } else {
                    ((Timer) e.getSource()).stop();
                    countdownLabel.setText("Start!");
                    // Add your game start logic here
                }
            }
        });
        timer.start();
    }
}