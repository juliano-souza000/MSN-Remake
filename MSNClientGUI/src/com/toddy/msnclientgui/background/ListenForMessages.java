package com.toddy.msnclientgui.background;

import com.toddy.msnclientgui.enums.Action;
import com.toddy.msnclientgui.enums.ResponseStatus;
import com.toddy.msnclientgui.enums.UpdateType;
import com.toddy.msnclientgui.network.NetworkUtils;
import static com.toddy.msnclientgui.network.NetworkUtils.read;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.NetworkChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import com.toddy.msnclientgui.listeners.MemberUpdated;
import com.toddy.msnclientgui.listeners.MessageUpdateReceived;
import java.util.ArrayList;
import java.util.List;

public class ListenForMessages extends Thread
{
    private static SocketChannel server;
    private List<MessageUpdateReceived> messagesListeners = new ArrayList<MessageUpdateReceived>();
    private boolean isAlive = true;
    
    public ListenForMessages(MessageUpdateReceived listener)
    {
	addMessageListener(listener);
    }
    
    public void addMessageListener(MessageUpdateReceived listener)
    {
	this.messagesListeners.add(listener);
    }
    
    public void removeMessageListener(MessageUpdateReceived listener)
    {
	this.messagesListeners.remove(listener);
    }
    
    @Override
    public void run()
    {
	super.run();
	StartListening();
    }
    
    public void terminate() throws IOException
    {
	server.close();
	isAlive = false;
    }
    
    private void StartListening()
    {
	try
	{
	    server = NetworkUtils.connectSocket();
	}
	catch (IOException ex)
	{
	    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
	}
	
	if(server != null)
	{
	    try
	    {
		NetworkUtils.startListeningForUpdates(server, UpdateType.MessageReceived);
	    }
	    catch (IOException ex)
	    {
	        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
	    }
	    
	    while(isAlive)
	    {
		try
		{
		    JSONObject response = read();
		    if(response.getEnum(ResponseStatus.class, "Status") == ResponseStatus.OK)
		    {
			if(response.has("Message"))
			{
			    for(MessageUpdateReceived messageListener : messagesListeners)
			    {
				messageListener.MessageReceived(response.getJSONObject("Message"));
			    }
			}
		    }
		}
		catch (IOException ex)
		{
		    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		}
		
	    }
	}
    }
    
    private JSONObject read() throws IOException
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
	return new JSONObject(new String(bufferR.array(), StandardCharsets.UTF_8).trim());
    }
    
}