import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Register {
    public static boolean checkExist(String user){
		Connection con = null;
		PreparedStatement ps = null;
                
                    try {
                        con = DataConnect.getConnection();
                        ps = con.prepareStatement("Select username from  loginData where username = \""
                                + user + "\"");
                        ps.setString(1, user);
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
    public static boolean register(int uid, String user, String pass, String security) throws SQLException{
        Connection con = null;
		PreparedStatement ps = null;
                
                    try {
                        con = DataConnect.getConnection();
                        if (con != null) {
                        String sql = "INSERT INTO loginData(uid, username, pass, security) VALUES("
                                + uid + ","
                                + user + ","
                                + pass + ","
                                + security +
                                ")";
                        
                        ps = con.prepareStatement(sql);;
                        ps.executeUpdate();
                        System.out.println("Data Added Successfully");
                        
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
    public static int countUID(){
		Connection con = null;
		PreparedStatement ps = null;
                
                    try {
                        con = DataConnect.getConnection();
                        ps = con.prepareStatement("Select count(*) from loginData");
                        ResultSet rs = ps.executeQuery();
                        int val = 0;
                        while(rs.next()){
                        val = ((Number) rs.getObject(1)).intValue();
                        }
                        return val;
                    }    
                    catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
                    } 
                    finally {
        		DataConnect.close(con);
                    }
                    return 1;
    }
}
