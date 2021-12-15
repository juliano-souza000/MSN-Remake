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
import javax.swing.JFileChooser;
import msnserver.enums.MessageType;
import org.json.JSONArray;
import org.json.JSONObject;

public class MessageDAO
{
    public static JSONArray getAllMessages(int fromID, int toID)
    {
	Connection conn = ConnectionDAO.getConnection();
	PreparedStatement ps = null;
        ResultSet rs = null;
	JSONArray ja = new JSONArray();
	try
	{
            String SQL = "SELECT Messages.UserIDTo, Messages.UserIDFrom, Messages.MessageTimestamp, MessageContent.MessageContent, MessageContent.MessageType FROM Messages, MessageContent WHERE Messages.MessageContentID = MessageContent.MessageContentID AND ((Messages.UserIDFrom = ? OR Messages.UserIDTo = ?) AND (Messages.UserIDFrom = ? OR Messages.UserIDTo = ?))";
	    ps = conn.prepareStatement(SQL);
	    ps.setInt(1, fromID);
	    ps.setInt(2, fromID);
	    ps.setInt(3, toID);
	    ps.setInt(4, toID);
            rs = ps.executeQuery();
	    while(rs.next())
	    {
		JSONObject message = new JSONObject();
		JSONObject contentObj = new JSONObject();
		MessageType type = MessageType.values()[rs.getInt("MessageType")];
		String content = rs.getString("MessageContent");
		
		if(type != MessageType.TEXT)
		{
		    JSONObject contentObjl = new JSONObject(content);
		    try
		    {
			String path = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();//+"/MSN/"
			String separator = Paths.get(path).getFileSystem().getSeparator();
			contentObj.put("Filename", contentObjl.getString("Filename"));
			contentObj.put("Data", Base64.getEncoder().encodeToString(Files.readAllBytes(new File(path + separator + "MSNServer" + separator+contentObjl.getString("Filepath")).toPath())));
		    }
		    catch(IOException ex)
		    {
			Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
		    }
		}
		else
		{
		    contentObj.put("Data", content);
		}
		message.put("Type", type);
		message.put("Content", contentObj);
		message.put("From", rs.getInt("UserIDFrom"));
		message.put("To", rs.getInt("UserIDTo"));
		message.put("Timestamp", rs.getTimestamp("MessageTimestamp"));
		ja.put(message);
	    }
	    
	    if(ja.length() <= 0)
	    {
		ja = null;
	    }
	}
	catch(SQLException sqle)
	{
	    sqle.printStackTrace();
        }
        finally
	{
           ConnectionDAO.close(conn, ps);
        }
	return ja;
    }
    
    public static JSONObject getMessage(int getterID, int messageID)
    {
	Connection conn = ConnectionDAO.getConnection();
	PreparedStatement ps = null;
        ResultSet rs = null;
	JSONObject jo = new JSONObject();
	try
	{
            String SQL = "SELECT Messages.UserIDTo, Messages.UserIDFrom, Messages.MessageTimestamp, MessageContent.MessageContent, MessageContent.MessageType FROM Messages, MessageContent WHERE Messages.MessageContentID = MessageContent.MessageContentID AND MessageContent.MessageContentID = ? AND (Messages.UserIDFrom = ? OR Messages.UserIDTo = ?)";
	    ps = conn.prepareStatement(SQL);
	    ps.setInt(1, messageID);
	    ps.setInt(2, getterID);
	    ps.setInt(3, getterID);
            rs = ps.executeQuery();
	    while(rs.next())
	    {
		JSONObject contentObj = new JSONObject();
		MessageType type = MessageType.values()[rs.getInt("MessageType")];
		String content = rs.getString("MessageContent");
		
		if(type != MessageType.TEXT)
		{
		    JSONObject contentObjl = new JSONObject(content);
		    try
		    {
			String path = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();//+"/MSN/"
			String separator = Paths.get(path).getFileSystem().getSeparator();
			contentObj.put("Filename", contentObjl.getString("Filename"));
			contentObj.put("Data", Base64.getEncoder().encodeToString(Files.readAllBytes(new File(path + separator + "MSNServer" + separator+contentObjl.getString("Filepath")).toPath())));
		    }
		    catch(IOException ex)
		    {
			Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
		    }
		}
		else
		{
		    contentObj.put("Data", content);
		}
		jo.put("Type", type);
		jo.put("Content", contentObj);
		jo.put("From", rs.getInt("UserIDFrom"));
		jo.put("To", rs.getInt("UserIDTo"));
		jo.put("Timestamp", rs.getTimestamp("MessageTimestamp"));
	    }
	    
	    if(!jo.has("Content"))
	    {
		jo = null;
	    }
	}
	catch(SQLException sqle)
	{
	    sqle.printStackTrace();
        }
        finally
	{
           ConnectionDAO.close(conn, ps);
        }
	return jo;
    }
    
    public static int addMessage(int receiverID, int fromID, JSONObject messageContentObj, MessageType messageType)
    {
	Connection conn = ConnectionDAO.getConnection();
	PreparedStatement ps = null;
        ResultSet rs = null;
	int messageID = -1;
	try
	{
	    String SQL = "INSERT INTO `Messages`(`UserIDFrom`, `UserIDTo`, `MessageContentID`) VALUES (?,?,?)";
	    String InsertContentSQL = "INSERT INTO `MessageContent`(`MessageContent`,`MessageType`) VALUES (?,?)";
	    String IDSQL = "SELECT (auto_increment-1) AS lastId FROM information_schema.tables WHERE table_name = 'MessageContent' AND table_schema = 'MSN'";
	    int messageContentID = 0;
	    String messageContent;
	    ps = conn.prepareStatement(InsertContentSQL);
	    if(messageType == MessageType.TEXT)
	    {
		messageContent = messageContentObj.getString("Data");
	    }
	    else
	    {
		messageContent = messageContentObj.toString();
	    }
	    ps.setString(1, messageContent);
	    ps.setInt(2, messageType.ordinal());
	    ps.executeUpdate();
	    ps = conn.prepareStatement(IDSQL);
            rs = ps.executeQuery();
	    
	    rs.next();
	    messageContentID = rs.getInt("lastId");
            
	    ps = conn.prepareStatement(SQL);
	    ps.setInt(1, fromID);
	    ps.setInt(2, receiverID);
	    ps.setInt(3, messageContentID);
            ps.executeUpdate();
	    IDSQL = "SELECT (auto_increment-1) AS lastId FROM information_schema.tables WHERE table_name = 'Messages' AND table_schema = 'MSN'";
	    ps = conn.prepareStatement(IDSQL);
            rs = ps.executeQuery();
	    rs.next();
	    messageID = rs.getInt("lastId");
	}
	catch(SQLException sqle)
	{
	    System.out.println(sqle.getMessage());
        }
        finally
	{
           ConnectionDAO.close(conn, ps);
        }
	return messageID;
    }
}