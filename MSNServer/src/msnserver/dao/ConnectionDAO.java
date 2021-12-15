package msnserver.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import msnserver.MSNServer;

public class ConnectionDAO
{
    public static Connection getConnection() 
    {
    	Connection connection = null;
    	try
    	{
	    Class.forName("com.mysql.jdbc.Driver"); 
    	}
    	catch(ClassNotFoundException e)
    	{
	    Logger.getLogger(ConnectionDAO.class.getName()).log(Level.SEVERE, null, e);
    	}

	try
	{
	    connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s", MSNServer.getConfig().getDBIPAddress(), MSNServer.getConfig().getDBPort(), MSNServer.getConfig().getDBName()), MSNServer.getConfig().getDBUser(), MSNServer.getConfig().getDBPassword());
	}
	catch(Exception e)
	{
	    Logger.getLogger(ConnectionDAO.class.getName()).log(Level.SEVERE, null, e);
	    connection = null;
	}
       
	return connection;
   }

   public static void close(Connection conn, Statement stmt, ResultSet rs) 
   {
       try
       {
	    if (rs != null) 
		rs.close();
	    if (stmt != null) 
		stmt.close();
	    if (conn != null) 
		conn.close();
       }
       catch(Exception e)
       {
	    Logger.getLogger(ConnectionDAO.class.getName()).log(Level.SEVERE, null, e);
       }
   }
   
   
   public static void close(Connection conn, Statement stmt) 
   {
       try
       {
	    if (stmt != null) 
		stmt.close();
	    if (conn != null) 
		conn.close();
       }
       catch(Exception e)
       {
	    Logger.getLogger(ConnectionDAO.class.getName()).log(Level.SEVERE, null, e);
       }
   }
}