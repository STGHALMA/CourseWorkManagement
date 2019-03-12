
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author xhunter
 */
public class SessionEntry {
    public static boolean insert(String uname){
        Connection con = null;
		PreparedStatement ps = null;
                
                    try {
                        con = DataConnect.getConnection();
                        if (con != null) {
                        String sql = "INSERT INTO session VALUES(?)";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, uname);
                        ps.executeUpdate();
                        System.out.println("User logged in successfully");
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
    public static boolean delete(String uname){
        Connection con = null;
		PreparedStatement ps = null;
                
                    try {
                        con = DataConnect.getConnection();
                        if (con != null) {
                        String sql = "delete from session where username = ?";
                        ps = con.prepareStatement(sql);
                        ps.setString(1, uname);
                        ps.executeUpdate();
                        System.out.println("User logged out successfully");
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
    public static boolean check(String user){
		Connection con = null;
		PreparedStatement ps = null;
                
                    try {
                        con = DataConnect.getConnection();
                        ps = con.prepareStatement("Select username from session where username = ?");
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
}
