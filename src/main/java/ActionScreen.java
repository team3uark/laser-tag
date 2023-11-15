import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActionScreen extends JFrame{
    DefaultTableModel team1TableModel;
    DefaultTableModel team2TableModel;
    JTable team1Table;
    JTable team2Table;

    JLabel team1Players[], team2Players[];
    int team1Score = 0;
    int team2Score = 0;
    Vector<Player> players = new Vector<Player>();
    JLabel actionLabel = new JLabel("Play by Play Action Here");
    JLabel scoresLabel = new JLabel("Team 1 Score: " + team1Score + " | Team 2 Score: " + team2Score);

    //music player
    Thread musicThread = new Thread(new AsyncMusicPlayer());

    //countdown timer
    JLabel countdownLabel;
    private int remainingSeconds = 6 * 60;
    private Timer timer;

    //game start countdown timer
    private static JLabel startCountdownLabel;
    private static JLabel textLabel;

    //constructor
    ActionScreen(){
        createStartCountdown();
        //Vector<Player> players = new Vector<Player>();
        team1TableModel = new DefaultTableModel();
        team1TableModel.addColumn("Username");
        team1TableModel.addColumn("Score");
        team1Table = new JTable(team1TableModel);

        //-----------------------------------------------
        team2TableModel = new DefaultTableModel();
        team2TableModel.addColumn("Username");
        team2TableModel.addColumn("Score");
        team2Table = new JTable(team2TableModel);
    }

    public void createStartCountdown() {
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

        startCountdownLabel = new JLabel("", SwingConstants.CENTER);
        startCountdownLabel.setFont(new Font("Arial", Font.BOLD, 70));
        gbc.gridy = 1;
        frame.add(startCountdownLabel, gbc);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        startCountdown(frame);
    }

    public void startCountdown(JFrame frame) {
        Timer timer = new Timer(1000, new ActionListener() {
            int counter = 30; // starting from 30 seconds

            @Override
            public void actionPerformed(ActionEvent e) {
                if (counter > 1) {
                    startCountdownLabel.setText(String.valueOf(counter));
                    counter--;
                } else if (counter == 1) {
                    startCountdownLabel.setText("1");
                    counter--;
                } else {
                    ((Timer) e.getSource()).stop();
                    textLabel.setVisible(false);
                    startCountdownLabel.setText("Start!");
                    Timer delayTimer = new Timer(700, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            frame.dispose();
                            //when the timer finishes, transmit code 202
                            UdpClient udpClient = new UdpClient();
                            udpClient.sendDataToServer(202);
                            createActionScreen();
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

    void createActionScreen() {
        this.setTitle("Action Screen");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel teamScores = new JPanel();
        teamScores.setPreferredSize(new Dimension(100,100));
        //JLabel scoresLabel = new JLabel("Team 1 Score: " + team1Score + " | Team 2 Score: " + team2Score);
        teamScores.add(scoresLabel);

        //-----------------------------------------------
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(team1Table), new JScrollPane(team2Table));
        //-----------------------------------------------
        JPanel actionPanel = new JPanel();
        actionPanel.setPreferredSize(new Dimension(200,200));
        actionPanel.add(actionLabel);

        //-----------------------------------------------
        topPanel.add(teamScores, BorderLayout.NORTH);
        this.add(splitPane, BorderLayout.CENTER);
        this.add(actionPanel, BorderLayout.PAGE_END);

        //play the random music mp3 file
        musicThread.start();

        // add 6minute timer
        JPanel countdownPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        countdownLabel = new JLabel(formatTime(remainingSeconds));
        countdownPanel.add(countdownLabel);
        topPanel.add(countdownPanel, BorderLayout.EAST);
        this.add(topPanel, BorderLayout.NORTH);
        startTimer();

    }

    void addPlayers(Vector<Player> players){
        for (int i = 0; i < players.size(); i++)
        {
            //Vector<Player> players = new Vector<Player>()
            this.players.add(players.get(i));
            if (players.get(i).getPlayerID() % 2 == 0)
            {
                team1TableModel.addRow(new Object[]{players.get(i).getName(), players.get(i).getScore()});
            }
            else {
                team2TableModel.addRow(new Object[]{players.get(i).getName(), players.get(i).getScore()});
            }
        }
    }

    void incrementTeamScore(int teamNumber){
        if (teamNumber == 1)
        {
            team1Score += 10;

        }
        else if (teamNumber == 2)
        {
            team2Score += 10;
        }
        else {
            return;
        }
        this.scoresLabel.setText("Team 1 Score: " + team1Score + " | Team 2 Score: " + team2Score);

    }

    void incrementPlayerScore(Player player)
    {
        player.incrementScore();
    }

    private void startTimer() {
        timer = new Timer(1000, e -> {
            if (remainingSeconds > 0) {
                remainingSeconds--;
                countdownLabel.setText(formatTime(remainingSeconds));
            } else {
                stopTimer();
            }
        });
        timer.start();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.stop();
            musicThread.stop();
            UdpClient udpClient = new UdpClient();
            for(int i = 0; i < 3; i++) {
                udpClient.sendDataToServer(221);
            }
        }
    }

    private String formatTime(int seconds) {
        int min = seconds / 60;
        int sec = seconds % 60;
        return String.format("%02d:%02d", min, sec);
    }

}
