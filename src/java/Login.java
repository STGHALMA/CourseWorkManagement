import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Login {
    public static boolean validate(String user, String password){
		Connection con = null;
		PreparedStatement ps = null;
                    try {
                        con = DataConnect.getConnection();
                        ps = con.prepareStatement("Select username, pass from loginData where username = ? and pass = ?");
                        ps.setString(1, user);
			ps.setString(2, password);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
				return true;
			}
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