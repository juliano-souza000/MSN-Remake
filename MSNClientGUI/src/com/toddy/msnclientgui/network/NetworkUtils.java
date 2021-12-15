package com.toddy.msnclientgui.network;

import com.toddy.msnclientgui.Main;
import com.toddy.msnclientgui.enums.Action;
import com.toddy.msnclientgui.enums.MessageType;
import com.toddy.msnclientgui.enums.ResponseStatus;
import com.toddy.msnclientgui.enums.Status;
import com.toddy.msnclientgui.enums.UpdateType;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

public class NetworkUtils
{
    private static SocketChannel server;
    private static SocketAddress socketAddr;
    private static JSONObject loggedUserDetail;
    
    public static void connect() throws IOException
    {
	server = SocketChannel.open();
	socketAddr = new InetSocketAddress(Main.getConfig().getServerIPAdress(), 25252);
	server.connect(socketAddr);
    }
    
    public static SocketChannel connectSocket() throws IOException
    {
	SocketChannel serverl = SocketChannel.open();
	SocketAddress socketAddrl = new InetSocketAddress(Main.getConfig().getServerIPAdress(), 25252);
	serverl.connect(socketAddrl);
	return serverl;
    }
    
    public static void disconnect() throws IOException
    {
	server.close();
    }
    
    public static void send(Action action, JSONObject data) throws IOException
    {
	JSONObject contentLength = new JSONObject();
	JSONObject toSndObj = new JSONObject();
	toSndObj.put("Action", action);
	toSndObj.put("Content", data);
	contentLength.put("Content-Length", toSndObj.toString().getBytes().length);
	ByteBuffer bufferW = ByteBuffer.wrap(contentLength.toString().getBytes());
	server.write(bufferW);
	ByteBuffer bufferR = ByteBuffer.allocate(server.socket().getReceiveBufferSize());
	server.read(bufferR);
	JSONObject res = new JSONObject(new String(bufferR.array(), StandardCharsets.UTF_8).trim());
	if(res.getEnum(ResponseStatus.class, "Status") == ResponseStatus.OK)
	{
	    bufferW.clear();
	    bufferW = ByteBuffer.wrap(toSndObj.toString().getBytes());
	    server.write(bufferW);
	}
    }
    
    public static ByteBuffer read() throws IOException 
    {
	ByteBuffer bufferR = ByteBuffer.allocate(server.socket().getReceiveBufferSize());
	server.read(bufferR);
	JSONObject contentlength = new JSONObject(new String(bufferR.array(), StandardCharsets.UTF_8).trim());
	bufferR.clear();
	int length = contentlength.getInt("Content-Length");
	bufferR = ByteBuffer.allocate(length);
	int read = 0;
	do
	{
	    read += server.read(bufferR);
	} while(read != length );
	
	return bufferR;
    }
    
    public static JSONObject login(String email, String password) throws IOException
    {
	JSONObject jo = new JSONObject();
	jo.put("Email", email);
	jo.put("Password", password);
	send(Action.Login, jo);
	ByteBuffer response = read();
	String lsls = new String(response.array(), StandardCharsets.UTF_8).trim();
	//return new JSONObject(new String(response.array(), StandardCharsets.UTF_8).trim());
	return new JSONObject(lsls);
    }
    
    public static JSONObject signup(String username, String email, String password) throws IOException
    {
	JSONObject jo = new JSONObject();
	jo.put("Username", username);
	jo.put("Email", email);
	jo.put("Password", password);
	send(Action.SignUp, jo);
	ByteBuffer response = read();
	return new JSONObject(new String(response.array(), StandardCharsets.UTF_8).trim());
    }
    
    public static JSONObject getProfile(int id) throws IOException
    {
	JSONObject jo = new JSONObject();
	jo.put("SessionID", loggedUserDetail.getString("SessionID"));
	jo.put("ID", id);
	send(Action.GetProfile, jo);
	ByteBuffer response = read();
	return new JSONObject(new String(response.array(), StandardCharsets.UTF_8).trim());
    }
    
    public static JSONObject getProfilePicture(int id) throws IOException
    {
	JSONObject jo = new JSONObject();
	jo.put("SessionID", loggedUserDetail.getString("SessionID"));
	jo.put("ID", id);
	send(Action.GetUserProfilePicture, jo);
	ByteBuffer response = read();
	return new JSONObject(new String(response.array(), StandardCharsets.UTF_8).trim());
    }
    
    public static JSONObject getAllProfiles() throws IOException
    {
	JSONObject jo = new JSONObject();
	jo.put("SessionID", loggedUserDetail.getString("SessionID"));
	send(Action.GetAllProfiles, jo);
	ByteBuffer response = read();
	String jsonResp = new String(response.array(), StandardCharsets.UTF_8).trim();
	return new JSONObject(jsonResp);
    }
    
    public static JSONObject updateStatus(Status status) throws IOException
    {
	JSONObject jo = new JSONObject();
	jo.put("SessionID", loggedUserDetail.getString("SessionID"));
	jo.put("UserStatus", status);
	send(Action.UpdateStatus, jo);
	ByteBuffer response = read();
	return new JSONObject(new String(response.array(), StandardCharsets.UTF_8).trim());
    }
    
    public static JSONObject updateAbout(String about) throws IOException
    {
	JSONObject jo = new JSONObject();
	jo.put("SessionID", loggedUserDetail.getString("SessionID"));
	jo.put("About", about);
	send(Action.UpdateAbout, jo);
	ByteBuffer response = read();
	return new JSONObject(new String(response.array(), StandardCharsets.UTF_8).trim());
    }
    
    public static JSONObject updateProfilePicture(String image) throws IOException
    {
	JSONObject jo = new JSONObject();
	jo.put("SessionID", loggedUserDetail.getString("SessionID"));
	jo.put("Image", image);
	send(Action.UpdateProfilePicture, jo);
	ByteBuffer response = read();
	return new JSONObject(new String(response.array(), StandardCharsets.UTF_8).trim());
    }
    
    public static JSONObject updateUsername(String name) throws IOException
    {
	JSONObject jo = new JSONObject();
	jo.put("SessionID", loggedUserDetail.getString("SessionID"));
	jo.put("Username", name);
	send(Action.UpdateUsername, jo);
	ByteBuffer response = read();
	return new JSONObject(new String(response.array(), StandardCharsets.UTF_8).trim());
    }
    
    public static JSONObject sendMessage(JSONObject messageContent, int receiverID, MessageType messageType) throws IOException
    {
	JSONObject jo = new JSONObject();
	jo.put("SessionID", loggedUserDetail.getString("SessionID"));
	jo.put("MessageContent", messageContent);
	jo.put("ReceiverID", receiverID);
	jo.put("MessageType", messageType);
	send(Action.SendMessage, jo);
	ByteBuffer response = read();
	return new JSONObject(new String(response.array(), StandardCharsets.UTF_8).trim());
    }
    
    public static JSONObject getAllMessages(int chatWithID) throws IOException
    {
	JSONObject jo = new JSONObject();
	jo.put("SessionID", loggedUserDetail.getString("SessionID"));
	jo.put("ChatWithID", chatWithID);
	send(Action.GetMessage, jo);
	ByteBuffer response = read();
	return new JSONObject(new String(response.array(), StandardCharsets.UTF_8).trim());
    }
    
    public static void startListeningForUpdates(SocketChannel serverl, UpdateType updateType) throws IOException
    {
	ByteBuffer bufferR = ByteBuffer.allocate(server.socket().getReceiveBufferSize());
	JSONObject contentLength = new JSONObject();
	JSONObject toSndObj = new JSONObject();
	JSONObject res;
	ByteBuffer bufferW;
	toSndObj.put("Action", Action.GetUserAnyUpdate);
	toSndObj.put("UpdateType", updateType);
	toSndObj.put("SessionID", loggedUserDetail.getString("SessionID"));
	contentLength.put("Content-Length", toSndObj.toString().getBytes().length);
	bufferW = ByteBuffer.wrap(contentLength.toString().getBytes());
	serverl.write(bufferW);
	bufferR = ByteBuffer.allocate(server.socket().getReceiveBufferSize());
	serverl.read(bufferR);
	res = new JSONObject(new String(bufferR.array(), StandardCharsets.UTF_8).trim());
	if(res.getEnum(ResponseStatus.class, "Status") == ResponseStatus.OK)
	{
	    bufferW.clear();
	    bufferW = ByteBuffer.wrap(toSndObj.toString().getBytes());
	    serverl.write(bufferW);
	}
    }
    
    public static JSONObject changePass(String email, String password) throws IOException
    {
	JSONObject jo = new JSONObject();
	jo.put("Email", email);
	jo.put("Password", password);
	send(Action.ChangePassword, jo);
	ByteBuffer response = read();
	return new JSONObject(new String(response.array(), StandardCharsets.UTF_8).trim());
    }
    
    public static JSONObject deleteAccount() throws IOException
    {
	JSONObject jo = new JSONObject();
	jo.put("SessionID", loggedUserDetail.getString("SessionID"));
	send(Action.DeleteAccount, jo);
	ByteBuffer response = read();
	return new JSONObject(new String(response.array(), StandardCharsets.UTF_8).trim());
    }
    
    public static void setUser(JSONObject user)
    {
	loggedUserDetail = user;
    }
    
    public static JSONObject getUser()
    {
	return loggedUserDetail;
    }
}
