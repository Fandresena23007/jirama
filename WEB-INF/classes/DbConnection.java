package connexion;
import java.sql.*;
public class DbConnection
{
    public Connection connected(String user,String pswd ) throws ClassNotFoundException, SQLException{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connect=null;
            connect=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",user,pswd);
            System.out.println("yes");
            return connect;
    }
}