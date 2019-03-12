import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reset {
    public static void resetPassword(String username, String password){
		Connection con = null;
		PreparedStatement ps = null;
                
                    try {
                        con = DataConnect.getConnection();
                        ps = con.prepareStatement("update loginData set pass = \""
                                + password + "where username = \""
                        + username + "\"");
                        ps.executeUpdate();
                    }    
                    catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());		
                    } 
                    finally {
        		DataConnect.close(con);
                    }
                             
                }
}
