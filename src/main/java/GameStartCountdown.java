import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameStartCountdown {
    private static JLabel countdownLabel;

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(GameStartCountdown::createStartCountdown);
//    }

    public static void createStartCountdown() {
        JFrame frame = new JFrame("Game Start Countdown");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout());

        countdownLabel = new JLabel("", SwingConstants.CENTER);
        countdownLabel.setFont(new Font("Arial", Font.BOLD, 70));
        frame.add(countdownLabel, BorderLayout.CENTER);

        startCountdown();

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public static void startCountdown() {
        Timer timer = new Timer(1000, new ActionListener() {
            int counter = 30; // starting from 30 seconds

            @Override
            public void actionPerformed(ActionEvent e) {
                if (counter > 0) {
                    countdownLabel.setText(String.valueOf(counter));
                    counter--;
                } else {
                    ((Timer) e.getSource()).stop();
                    countdownLabel.setText("Start!");
                }
            }
        });
        timer.start();
    }
}