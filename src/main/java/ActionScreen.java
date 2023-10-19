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


    ActionScreen(){
        this.setTitle("Action Screen");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,1000);
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
        this.add(teamScores, BorderLayout.NORTH);
        this.add(splitPane, BorderLayout.CENTER);
        this.add(actionPanel, BorderLayout.PAGE_END);

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

}
