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

public class Main extends JFrame {
    private JTextField idField;
    private JTextField userField;
    private JTable team1Table;
    private DefaultTableModel team1TableModel;
    private JTable team2Table;
    private DefaultTableModel team2TableModel;
    UdpClient cl = new UdpClient();
    String dbConnectionUrl = "jdbc:postgresql://db.jftodibnhiuinhcketaf.supabase.co/postgres?user=postgres&password=jd4_2kAmcde3451&ssl=false";
    Vector<Player> players = new Vector<Player>();
    public Main() {
        setTitle("EntryTerminal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);

        //set up main panel 
        JPanel mainPanel = new JPanel(new BorderLayout());

        //set up entry panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 0));

        //add input panel components
        JLabel idLabel = new JLabel("ID: ");
        idField = new JTextField();
        inputPanel.add(idLabel);
        inputPanel.add(idField);

        //set up button(s)
        JButton addButton = new JButton("Add Player");
        inputPanel.add(addButton);

        //ADD BUTTON TO CLEAR ALL ENTRIES
        //ADD BUTTON TO MOVE TO PLAY SCREEN AFTER COMPLETING PLAYER ENTRY

        //set up table
        team1TableModel = new DefaultTableModel();
        team1TableModel.addColumn("ID");
        team1TableModel.addColumn("Username");
        team1Table = new JTable(team1TableModel);

        team2TableModel = new DefaultTableModel();
        team2TableModel.addColumn("ID");
        team2TableModel.addColumn("Username");
        team2Table = new JTable(team2TableModel);

        //create two table display
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(team1Table), new JScrollPane(team2Table));

        //add components to main panel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                String user = " ";
                addPlayer(id, user);
            }
        });

        //add main panel to frame & center
        add(mainPanel);
        setLocationRelativeTo(null);

        // Add Key Bindings
        InputMap inputMap = mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = mainPanel.getActionMap();

        // F12 Key Binding to Clear All Entries
        inputMap.put(KeyStroke.getKeyStroke("F12"), "clearEntries");
        actionMap.put("clearEntries", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAllEntries();
            }
        });

        // F5 Key Binding to Exit
        inputMap.put(KeyStroke.getKeyStroke("F5"), "exitProgram");
        actionMap.put("exitProgram", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActionScreen action = new ActionScreen();
                action.setVisible(true);
                action.setLocationRelativeTo(null);
                action.addPlayers(players);
                GameStartCountdown startCountdown = new GameStartCountdown();
                startCountdown.createStartCountdown();
                dispose(); // Close the JFrame and exit the program
            }
        });
    }

    // Clear all entries in both tables
    private void clearAllEntries() {
        team1TableModel.setRowCount(0);
        team2TableModel.setRowCount(0);
    }


    //METHOD TO PROMPT FOR EQUIPMENT CODE
    public int getEqID() {
        String eqId = JOptionPane.showInputDialog("Enter your equipment ID");
        int eqIdInt = Integer.parseInt(eqId);
        cl.sendDataToServer(eqIdInt);
        return eqIdInt;

    }

    //Implement addPlayer method
    public void addPlayer(int id_in, String user_in) {
        int equipmentID = getEqID();

        //check number current players
        int team1Count = team1TableModel.getRowCount();
        int team2Count = team2TableModel.getRowCount();

        //ensure #players < 15 per team
        if(team1Count >=15 || team2Count >= 15) {
            JOptionPane.showMessageDialog(null,  "This team is full!");
            return;
        }

        //implement logic to search database
        String DB_user = searchDatabase(id_in);

        if(DB_user != null) {
            user_in = DB_user; //if found, use username from database
            System.out.println("User exists. Using " + user_in);
            Player player = new Player(user_in, id_in, equipmentID, 0);
            players.add(player);
            System.out.println(player.toString());

        } else { //prompt to enter username
            String userInput = JOptionPane.showInputDialog("New user! Please enter a username: ");
            if (userInput != null && !userInput.isEmpty()) {
                user_in = userInput;
                System.out.println("User does not exist. Creating new user " + user_in);
                Player player = new Player(user_in, id_in, equipmentID, 0);
                players.add(player);
                System.out.println(player.toString());

                //add new user to DB
                insertDatabase(id_in, userInput);
            } else { //if improper user input
                JOptionPane.showMessageDialog(null, "No username entered. Player not added."); //CHANGE TO WHILE LOOP
                return;
            }

        }
        //get equipment ID
        //getEqID();

        //chose team: even players = team 1, odd players = team 2
        DefaultTableModel tableModel;
        if(id_in % 2 == 0) {
            tableModel = team1TableModel;
        }
        else {
            tableModel = team2TableModel;
        }

        //add player to table
        Vector<String> playerInput = new Vector<>();
        playerInput.add(Integer.toString(id_in)); //ADD ERROR CHECKING FOR INT INPUT TYPE
        playerInput.add(user_in);
        tableModel.addRow(playerInput);

        //prepare for next entry
        idField.setText("");
        //userField.setText("");
    }

    //FUNCTION TO PROMPT FOR EQUIPMENT ID

    //searchDB for username by ID
    private String searchDatabase(int id_search) {
        String username = null;

        try {
            //connect to DB
            Connection connection = DriverManager.getConnection(dbConnectionUrl);

            //search statement for ID
            String sql = "SELECT username FROM players WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id_search);

            //execute search
            ResultSet resultSet = preparedStatement.executeQuery();

            //interpret search
            if (resultSet.next()) {
                username = resultSet.getString("username");
            }

            //close
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //HANDLE DB ERRORS
        }
        return username;
    }

    //update DB method
    private void updateDatabase(int id_enter, String username_enter) {
        try {
            //connect to DB
            Connection connection = DriverManager.getConnection(dbConnectionUrl);

            //statement to update username
            String sql = "UPDATE players SET username = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,  username_enter);
            preparedStatement.setInt(2,  id_enter);

            //execute
            preparedStatement.executeUpdate();

            //close
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //HANDLE DB CONNECTION ERRORS
        }
    }

    //insert DB method
    private void insertDatabase(int id_enter, String username_enter) {
        try {
            //connect to DB
            Connection connection = DriverManager.getConnection(dbConnectionUrl);

            //statement to update username
            String sql = "INSERT INTO players (username, id) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,  username_enter);
            preparedStatement.setInt(2,  id_enter);
            System.out.println(preparedStatement);

            //execute
            preparedStatement.executeUpdate();

            //close
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //HANDLE DB CONNECTION ERRORS
        }
    }


    //MAIN
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
                SplashScreen sp = new SplashScreen();
                sp.showSplashScreen();
            }
        });
    }
}
