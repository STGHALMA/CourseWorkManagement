import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class DataConnect {
    public static Connection getConnection() {
		try {
                    Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/coursework", "root", "1");
                    return con;
                } catch (SQLException ex) {
            Logger.getLogger(DataConnect.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Database.getConnection() Error -->"
					+ ex.getMessage());
			return null;
        }
}
    public static void close(Connection con) {
		try {
			con.close();
		} catch (Exception ex) {
		}
	}   
}
