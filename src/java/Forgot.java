import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Forgot {
    public static boolean validateUsernameSecans(String username, String secans){
        Connection con = null;
	PreparedStatement ps = null;
        String dbsecans = null;
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("Select security from loginData where username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            dbsecans = rs.getString("security");
            }
            if(dbsecans.equals(secans))
                return true;
        }    
        catch (SQLException ex) {
            System.out.println("Login error -->" + ex.getMessage());
            return false;
        } 
        finally {
            DataConnect.close(con);
        } 
        return false;
    }
}