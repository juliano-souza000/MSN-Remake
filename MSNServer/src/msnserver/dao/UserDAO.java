package msnserver.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import msnserver.enums.Status;
import msnserver.utils.CryptUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class UserDAO
{
    public static boolean changeUserPass(String email, String password)
    {
	Connection conn = ConnectionDAO.getConnection();
	PreparedStatement ps = null;

	boolean isOk = false;
	try
	{
            String SQL = "UPDATE `Accounts` SET `UserPassword`=? WHERE UserEmail=?";
	    ps = conn.prepareStatement(SQL);
	    ps.setString(1, password);
	    ps.setString(2, email);
            ps.executeUpdate();
	    isOk = true;
	}
	catch(SQLException sqle)
	{
	    System.out.println(sqle.getMessage());
        }
        finally
	{
           ConnectionDAO.close(conn, ps);
        }
	return isOk;
    }
    
    public static boolean deleteUserAccount(int userID)
    {
	Connection conn = ConnectionDAO.getConnection();
	PreparedStatement ps = null;

	boolean isOk = false;
	try
	{
            String SQL = "DELETE FROM `Accounts` WHERE UserID=?";
	    ps = conn.prepareStatement(SQL);
	    ps.setInt(1, userID);
            ps.executeUpdate();
	    isOk = true;
	}
	catch(SQLException sqle)
	{
	    System.out.println(sqle.getMessage());
        }
        finally
	{
           ConnectionDAO.close(conn, ps);
        }
	return isOk;
    }
    
    public static boolean updateUsername(int userID, String username)
    {
	Connection conn = ConnectionDAO.getConnection();
	PreparedStatement ps = null;

	boolean isOk = false;
	try
	{
            String SQL = "UPDATE `Accounts` SET `UserName`=? WHERE UserID=?";
	    ps = conn.prepareStatement(SQL);
	    ps.setString(1, username);
	    ps.setInt(2, userID);
            ps.executeUpdate();
	    isOk = true;
	}
	catch(SQLException sqle)
	{
	    System.out.println(sqle.getMessage());
        }
        finally
	{
           ConnectionDAO.close(conn, ps);
        }
	return isOk;
    }
    
    public static boolean updateUserProfilePicture(int userID, String path)
    {
	Connection conn = ConnectionDAO.getConnection();
	PreparedStatement ps = null;

	boolean isOk = false;
	try
	{
            String SQL = "UPDATE `Accounts` SET `UserProfilePicturePath`=? WHERE UserID=?";
	    ps = conn.prepareStatement(SQL);
	    ps.setString(1, "/"+path);
	    ps.setInt(2, userID);
            ps.executeUpdate();
	    isOk = true;
	}
	catch(SQLException sqle)
	{
	    System.out.println(sqle.getMessage());
        }
        finally
	{
           ConnectionDAO.close(conn, ps);
        }
	return isOk;
    }
    
    public static String getUserName(int ID)
    {
	Connection conn = ConnectionDAO.getConnection();
	PreparedStatement ps = null;
        ResultSet rs = null;
	String name = null;
	try
	{
            String SQL = "SELECT UserName FROM `Accounts` WHERE UserID=?";
	    ps = conn.prepareStatement(SQL);
	    ps.setInt(1, ID);
            rs = ps.executeQuery();
	    while(rs.next())
	    {
		name = rs.getString("UserName");
	    }
	}
	catch(SQLException sqle)
	{
	    System.out.println(sqle.getMessage());
        }
        finally
	{
           ConnectionDAO.close(conn, ps);
        }
	return name;
    }
    
    public static String getUserAbout(int ID)
    {
	Connection conn = ConnectionDAO.getConnection();
	PreparedStatement ps = null;
        ResultSet rs = null;
	String about = null;
	try
	{
            String SQL = "SELECT UserAbout FROM `Accounts` WHERE UserID=?";
	    ps = conn.prepareStatement(SQL);
	    ps.setInt(1, ID);
            rs = ps.executeQuery();
	    while(rs.next())
	    {
		about = rs.getString("UserAbout");
	    }
	}
	catch(SQLException sqle)
	{
	    System.out.println(sqle.getMessage());
        }
        finally
	{
           ConnectionDAO.close(conn, ps);
        }
	return about;
    }
    
    public static boolean updateUserAbout(int ID, String about)
    {
	Connection conn = ConnectionDAO.getConnection();
	PreparedStatement ps = null;

	boolean isOk = false;
	try
	{
            String SQL = "UPDATE `Accounts` SET `UserAbout`=? WHERE UserID=?";
	    ps = conn.prepareStatement(SQL);
	    ps.setString(1, about);
	    ps.setInt(2, ID);
            ps.executeUpdate();
	    isOk = true;
	}
	catch(SQLException sqle)
	{
	    System.out.println(sqle.getMessage());
        }
        finally
	{
           ConnectionDAO.close(conn, ps);
        }
	return isOk;
    }
    
    public static Status getUserStatus(int ID)
    {
	Connection conn = ConnectionDAO.getConnection();
	PreparedStatement ps = null;
        ResultSet rs = null;
	Status status = Status.Offline;
	try
	{
            String SQL = "SELECT UserStatus FROM `Accounts` WHERE UserID=?";
	    ps = conn.prepareStatement(SQL);
	    ps.setInt(1, ID);
            rs = ps.executeQuery();
	    while(rs.next())
	    {
		status = Status.values()[rs.getInt("UserStatus")];
	    }
	}
	catch(SQLException sqle)
	{
	    System.out.println(sqle.getMessage());
        }
        finally
	{
           ConnectionDAO.close(conn, ps);
        }
	return status;
    }
    
    public static boolean updateUserStatus(int ID, Status userStatus)
    {
	Connection conn = ConnectionDAO.getConnection();
	PreparedStatement ps = null;

	boolean isOk = false;
	try
	{
            String SQL = "UPDATE `Accounts` SET `UserStatus`=? WHERE UserID=?";
	    ps = conn.prepareStatement(SQL);
	    ps.setInt(1, userStatus.ordinal());
	    ps.setInt(2, ID);
            ps.executeUpdate();
	    isOk = true;
	}
	catch(SQLException sqle)
	{
	    System.out.println(sqle.getMessage());
        }
        finally
	{
           ConnectionDAO.close(conn, ps);
        }
	return isOk;
    }
    
    public static JSONObject getUserProfile(int ID)
    {
	Connection conn = ConnectionDAO.getConnection();
	PreparedStatement ps = null;
        ResultSet rs = null;
	JSONObject jo = new JSONObject();
	try
	{
            String SQL = "SELECT UserID, UserName, UserProfilePicturePath, UserAbout, UserStatus FROM `Accounts` WHERE UserID=?";
	    ps = conn.prepareStatement(SQL);
	    ps.setInt(1, ID);
            rs = ps.executeQuery();
	    while(rs.next())
	    {
		jo.put("ID", rs.getInt("UserID"));
		jo.put("Username", rs.getString("UserName"));
		try
		{
		    String path = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();//+"/MSN/"
		    String separator = Paths.get(path).getFileSystem().getSeparator();
		    String image64 = Base64.getEncoder().encodeToString(Files.readAllBytes(new File(path + separator + "MSNServer" + rs.getString("UserProfilePicturePath")).toPath()));
		    jo.put("ProfileIMG", image64);
		}
		catch(IOException ex)
		{
		    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		jo.put("About", rs.getString("UserAbout"));
		jo.put("Status", Status.values()[rs.getInt("UserStatus")]);
	    }
	}
	catch(SQLException sqle)
	{
	    System.out.println(sqle.getMessage());
        }
        finally
	{
           ConnectionDAO.close(conn, ps);
        }
	return jo;
    }
    
    public static JSONArray getAllUsersProfile(int exceptID)
    {
	Connection conn = ConnectionDAO.getConnection();
	PreparedStatement ps = null;
        ResultSet rs = null;
	JSONArray jog = new JSONArray();
	try
	{
            String SQL = "SELECT UserID, UserName, UserAbout, UserStatus FROM `Accounts` WHERE UserID!=?";
	    ps = conn.prepareStatement(SQL);
	    ps.setInt(1, exceptID);
            rs = ps.executeQuery();
	    while(rs.next())
	    {
		JSONObject jo = new JSONObject();
		jo.put("ID", rs.getInt("UserID"));
		jo.put("Username", rs.getString("UserName"));
		jo.put("About", rs.getString("UserAbout"));
		jo.put("Status", Status.values()[rs.getInt("UserStatus")]);
		jog.put(jo);
	    }
	}
	catch(SQLException sqle)
	{
	    System.out.println(sqle.getMessage());
        }
        finally
	{
           ConnectionDAO.close(conn, ps);
        }
	return jog;
    }
    
    public static JSONObject getUserProfilePicture(int id)
    {
	Connection conn = ConnectionDAO.getConnection();
	PreparedStatement ps = null;
        ResultSet rs = null;
	JSONObject jo = new JSONObject();
	try
	{
            String SQL = "SELECT UserProfilePicturePath FROM `Accounts` WHERE UserID=?";
	    ps = conn.prepareStatement(SQL);
	    ps.setInt(1, id);
            rs = ps.executeQuery();
	    while(rs.next())
	    {
		try
		{
		    String path = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();//+"/MSN/"
		    String separator = Paths.get(path).getFileSystem().getSeparator();
		    String image64 = Base64.getEncoder().encodeToString(Files.readAllBytes(new File(path + separator + "MSNServer" + rs.getString("UserProfilePicturePath")).toPath()));
		    jo.put("ProfileIMG", image64);
		}
		catch(IOException ex)
		{
		    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	}
	catch(SQLException sqle)
	{
	    System.out.println(sqle.getMessage());
        }
        finally
	{
           ConnectionDAO.close(conn, ps);
        }
	return jo;
    }
    
    public static int getUserID(String email)
    {
	Connection conn = ConnectionDAO.getConnection();
	PreparedStatement ps = null;
        ResultSet rs = null;
	
	try
	{
            String SQL = "SELECT UserID FROM `Accounts` WHERE UserEmail=?";
	    ps = conn.prepareStatement(SQL);
	    ps.setString(1, email);
            rs = ps.executeQuery();
	    while(rs.next())
	    {
		return rs.getInt("UserID");
	    }
	}
	catch(SQLException sqle)
	{
	    System.out.println(sqle.getMessage());
        }
        finally
	{
           ConnectionDAO.close(conn, ps);
        }
	return -1;
    }
    
    public static void signUp(String username, String email, String password)
    {
	Connection conn = ConnectionDAO.getConnection();
	PreparedStatement ps = null;
	
	try
	{
            String SQL = "INSERT INTO `Accounts`(`UserName`, `UserEmail`, `UserPassword`) VALUES (?,?,?)";
	    ps = conn.prepareStatement(SQL);
	    ps.setString(1, username);
	    ps.setString(2, email);
	    ps.setString(3, password);
            ps.executeUpdate();
	}
	catch(SQLException sqle)
	{
	    System.out.println(sqle.getMessage());
        }
        finally
	{
           ConnectionDAO.close(conn, ps);
        }
    }
    
    public static boolean canUserLogIn(String email, String password )
    {
	Connection conn = ConnectionDAO.getConnection();
	PreparedStatement ps = null;
        ResultSet rs = null;
	
	try
	{
            String SQL = "SELECT count(*) FROM `Accounts` WHERE UserEmail=? AND UserPassword=?";
	    ps = conn.prepareStatement(SQL);
	    ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
	    while(rs.next())
	    {
		int exists = rs.getInt("count(*)");
		if(exists == 1)
		    return true;
	    }
	}
	catch(SQLException sqle)
	{
	    System.out.println(sqle.getMessage());
        }
        finally
	{
           ConnectionDAO.close(conn, ps);
        }
	return false;
    }
    
    public static boolean canUserSignup(String email)
    {
	Connection conn = ConnectionDAO.getConnection();
	PreparedStatement ps = null;
        ResultSet rs = null;
	
	try
	{
            String SQL = "SELECT count(*) FROM `Accounts` WHERE UserEmail=?";
	    ps = conn.prepareStatement(SQL);
	    ps.setString(1, email);
            rs = ps.executeQuery();
	    while(rs.next())
	    {
		int exists = rs.getInt("count(*)");
		if(exists == 0)
		    return true;
	    }
	}
	catch(SQLException sqle)
	{
	    System.out.println(sqle.getMessage());
        }
        finally
	{
           ConnectionDAO.close(conn, ps);
        }
	return false;
    }
    
}
	