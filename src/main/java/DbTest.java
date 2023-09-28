import javax.swing.*;
import java.sql.*;

public class DbTest {
    String dbConnectionUrl = "jdbc:postgresql://db.jftodibnhiuinhcketaf.supabase.co/postgres?user=postgres&password=jd4_2kAmcde3451&ssl=false";

    public DbTest() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbConnectionUrl);
            String sql = "SELECT count(*) from players;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet.findColumn("count"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new DbTest();
    }
}
