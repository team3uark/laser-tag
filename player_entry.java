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

public class PlayerEntryScreen extends JFrame {
    private JTextField idField;
    private JTextField userField;
    private JTable team1Table;
    private DefaultTableModel team1TableModel;
    private JTable team2Table;
    private DefaultTableModel team2TableModel;
    
    public PlayerEntryScreen() {
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
    }
    
    //Implement addPlayer method 
    public void addPlayer(int id_in, String user_in) {
    	//String user_in = "";
    	
    	//implement logic to search database
    	String DB_user = searchDatabase(id_in);
    	
    	if(DB_user != null) {
    		user_in = DB_user; //if found, use username from database
    	} else { //prompt to enter username
    			String userInput = JOptionPane.showInputDialog("New user! Please enter a username: ");
    			if (userInput != null && !userInput.isEmpty()) {
    				user_in = userInput;
    				//add new user to DB
    				updateDatabase(id_in, userInput);
    				} else { //if improper user input
    					JOptionPane.showMessageDialog(null, "No username entered. Player not added."); //CHANGE TO WHILE LOOP
    					return;
    				}	
    			}
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
    	userField.setText("");
    }
    
    //searchDB for username by ID
    private String searchDatabase(int id_search) {
    	String username = null;
    	
    	try {
    		//connect to DB
    		Connection connection = DriverManager.getConnection("db_url", "your username", "your password");
    		
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
    		Connection connection = DriverManager.getConnection("db_url", "your username", "your password");
    		
    		//statement to update username
    		String sql = "UPDATE players SET username = ? WHERE id = ?'";
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

    //MAIN
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                splashScreen splashScreen = new SplashScreen(1500, 1000);
                splashScreen.showSplashScreen();
                try {
                    Thread.sleep(3000); // Simulating a delay of 3 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                //splashScreen.hideSplashScreen();
                new PlayerEntryScreen().setVisible(true);
            }
        });
    }
}
