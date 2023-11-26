import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.Comparator;
import java.util.Collections;
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
    private JTextArea killFeed = new JTextArea();
    JLabel team1ScoreLabel = new JLabel("Team 1 Score: " + team1Score);
    JLabel team2ScoreLabel = new JLabel("Team 2 Score: " + team2Score);

    JButton backButton = new JButton("Back to Player Entry");

    Thread musicThread = new Thread(new AsyncMusicPlayer());

    //countdown timer
    JLabel countdownLabel;
    private int remainingSeconds = 6 * 60;
    private Timer timer;


    private static JLabel startCountdownLabel;
    private static JLabel textLabel;

    //constructor
    ActionScreen(){
        createStartCountdown();
        //Vector<Player> players = new Vector<Player>();
        team1TableModel = new DefaultTableModel();
        team1TableModel.addColumn("Base");
        team1TableModel.addColumn("Username");
        team1TableModel.addColumn("Score");
        team1Table = new JTable(team1TableModel);

        //-----------------------------------------------
        team2TableModel = new DefaultTableModel();
        team2TableModel.addColumn("Base");
        team2TableModel.addColumn("Username");
        team2TableModel.addColumn("Score");
        team2Table = new JTable(team2TableModel);


        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel();

        backButton = new JButton("Back to Player Entry");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the tables and reset scores when going back to player entry
                team1TableModel.setRowCount(0);
                team2TableModel.setRowCount(0);
                team1Score = 0;
                team2Score = 0;
                //scoresLabel.setText("Team 1 Score: " + team1Score + " | Team 2 Score: " + team2Score);
                musicThread.stop();
                //show player entry screen
                Main entryScreen = new Main();
                entryScreen.setVisible(true);

                dispose();
            }
        });

        buttonPanel.add(backButton);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);


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
                if (counter > 17) {
                    startCountdownLabel.setText(String.valueOf(counter));
                    counter--;
                } else if( counter == 17)  {
                    musicThread.start();
                    startCountdownLabel.setText(String.valueOf(counter));
                    counter--;
                } else if ( counter < 17 && counter > 1) {
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

    // Method to create ActionScreen
    void createActionScreen() {
        this.setTitle("Action Screen");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 700);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel teamScores = new JPanel();
        teamScores.setPreferredSize(new Dimension(100, 100));
        //team1ScoreLabel
        teamScores.add(team1ScoreLabel);
        teamScores.add(team2ScoreLabel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(team1Table), new JScrollPane(team2Table));

        //  killFeed = new JTextArea();
        killFeed.setEditable(false);
        //killFeed.setBackground(Color.GRAY);
        JPanel actionPanel = new JPanel();
        actionPanel.setPreferredSize(new Dimension(200, 200));
        actionPanel.add(killFeed);
        killFeed.insert("Play by Play Action Here\n", 0);
        killFeed.setCaretPosition(0);



        topPanel.add(teamScores, BorderLayout.NORTH);
        this.add(splitPane, BorderLayout.CENTER);
        this.add(actionPanel, BorderLayout.PAGE_END);

        // Add the "Back to Player Entry" button to the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Play the random music mp3 file
        // musicThread.start();

        // Add 6-minute timer
        JPanel countdownPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        countdownLabel = new JLabel(formatTime(remainingSeconds));
        countdownPanel.add(countdownLabel);
        topPanel.add(countdownPanel, BorderLayout.EAST);
        this.add(topPanel, BorderLayout.NORTH);
        startTimer();

    }

    void addPlayers(Vector<Player> players){
        team1TableModel.setRowCount(0);
        team2TableModel.setRowCount(0);

        int high = players.size() - 1;
        for(int count = 0; count < high; count++)
        {
            for(int i = 0; i < high; i++)
            {
                if(players.get(i).getScore() < players.get(i + 1).getScore())
                {
                    Player temp = players.get(i);
                    players.set(i, players.get(i + 1));
                    players.set(i + 1, temp);
                }
            }
        }

        for (int i = 0; i < players.size(); i++)
        {
            //Vector<Player> players = new Vector<Player>()
            this.players.add(players.get(i));
            if (players.get(i).getPlayerID() % 2 == 0)
            {
                team1TableModel.addRow(new Object[]{players.get(i).getHitBaseIndicator(), players.get(i).getName(), players.get(i).getScore()});
            }
            else
            {
                team2TableModel.addRow(new Object[]{players.get(i).getHitBaseIndicator(), players.get(i).getName(), players.get(i).getScore()});
            }

        }
    }

    void incrementTeamScore(int teamNumber){
        if (teamNumber == 1)
        {
            team1Score += 10;
            team1ScoreLabel.setText("Team 1 Score: " + team1Score);

        }
        else if (teamNumber == 2)
        {
            team2Score += 10;
            team2ScoreLabel.setText("Team 2 Score: " + team2Score);
        }
        else {
            return;
        }

    }

    void incrementTeamScoreBaseHit(int teamNumber){
        if (teamNumber == 1)
        {
            team1Score += 100;
            team1ScoreLabel.setText("Team 1 Score: " + team1Score);

        }
        else if (teamNumber == 2)
        {
            team2Score += 100;
            team2ScoreLabel.setText("Team 2 Score: " + team2Score);
        }
        else {
            return;
        }

    }

    void incrementPlayerScore(int eq_id)
    {
        for(int i = 0; i < players.size(); i++)
        {
            if(players.get(i).getEquipmentID() == eq_id)
            {
                players.get(i).incrementScore();
                if(players.get(i).getPlayerID() % 2 == 0)
                {
                    incrementTeamScore(1);
                }
                else
                {
                    incrementTeamScore(2);
                }
            }
        }
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
            for (int i = 0; i < 3; i++) {
                udpClient.sendDataToServer(221);
            }}
    }

    private String formatTime(int seconds) {
        int min = seconds / 60;
        int sec = seconds % 60;
        return String.format("%02d:%02d", min, sec);
    }


    public void paintComponent(Graphics g)
    {
        addPlayers(players);
    }

    /*
    public void flashingScore()
    {
        int flashingCounter = 20;
        while(team1Score > team2Score)
        {
            while(flashingCounter > 10) {
                team1ScoreLabel.setVisible(false);
                flashingCounter--;
            }
            while(flashingCounter <= 10 && flashingCounter > 0)
            {
                team1ScoreLabel.setVisible(true);
                flashingCounter--;
            }
            flashingCounter = 20;
        }
        if(team2Score > team1Score)
        {

        }
    }
    */

    public void scoreOnBase(int eq_id)
    {
        for(int i = 0; i < players.size(); i++)
        {
            if(players.get(i).getEquipmentID() == eq_id && players.get(i).getHitBase() == false)
            {
                players.get(i).incrementScoreBaseHit();
                players.get(i).setHitBase(eq_id);
                if(players.get(i).getPlayerID() % 2 == 0)
                {
                    incrementTeamScoreBaseHit(1);
                }
                else
                {
                    incrementTeamScoreBaseHit(2);
                }
            }
        }
    }

    public void addToFeed(int eq_id_1, int eq_id_2)
    {
        String feed_message = "";
        for(int i = 0; i < players.size(); i++)
        {
            if(eq_id_1 == players.get(i).getEquipmentID())
            {
                feed_message = feed_message + players.get(i).getName() + " Shot ";

            }
            if(eq_id_2 == players.get(i).getEquipmentID())
            {
                feed_message = feed_message + players.get(i).getName();
            }
        }
        killFeed.insert(feed_message + "\n", 0);
        killFeed.setCaretPosition(0);


    }

}
