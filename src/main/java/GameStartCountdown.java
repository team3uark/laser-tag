import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameStartCountdown {
    private static JLabel countdownLabel;
    private static JLabel textLabel;

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(GameStartCountdown::createStartCountdown);
//    }

    public static void createStartCountdown() {
        JFrame frame = new JFrame("Game Start Countdown");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);

        textLabel = new JLabel("Game will start in", SwingConstants.CENTER);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        frame.add(textLabel, gbc);

        countdownLabel = new JLabel("", SwingConstants.CENTER);
        countdownLabel.setFont(new Font("Arial", Font.BOLD, 70));
        gbc.gridy = 1;
        frame.add(countdownLabel, gbc);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        startCountdown(frame);
    }

    public static void startCountdown(JFrame frame) {
        Timer timer = new Timer(1000, new ActionListener() {
            int counter = 30; // starting from 30 seconds

            @Override
            public void actionPerformed(ActionEvent e) {
                if (counter > 1) {
                    countdownLabel.setText(String.valueOf(counter));
                    counter--;
                } else if (counter == 1) {
                    countdownLabel.setText("1");
                    counter--;
                } else {
                    ((Timer) e.getSource()).stop();
                    textLabel.setVisible(false);
                    countdownLabel.setText("Start!");
                    Timer delayTimer = new Timer(700, new ActionListener() {
                        @Override   
                        public void actionPerformed(ActionEvent e) {
                            frame.dispose();
                            //when the timer finishes, transmit code 202
                            UdpClient udpClient = new UdpClient();
                            udpClient.sendDataToServer(202);
                        }
                    });
                    delayTimer.setRepeats(false);
                    delayTimer.start();
                }
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }
}
