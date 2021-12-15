package com.toddy.msnclientgui.background;

import com.toddy.msnclientgui.enums.ResponseStatus;
import com.toddy.msnclientgui.enums.UpdateType;
import com.toddy.msnclientgui.network.NetworkUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import com.toddy.msnclientgui.listeners.MemberUpdated;

public class ListenForDeletedMembers extends Thread
{
    private static SocketChannel server;
    private MemberUpdated updateListener;
    private boolean isAlive = true;
    
    public ListenForDeletedMembers(MemberUpdated listener)
    {
	setUpdateListener(listener);
    }
    
    public void setUpdateListener(MemberUpdated listener)
    {
	this.updateListener = listener;
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
	    Logger.getLogger(ListenForDeletedMembers.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	if(server != null)
	{
	    try
	    {
		NetworkUtils.startListeningForUpdates(server, UpdateType.AccountDeleted);
	    }
	    catch (IOException ex)
	    {
	        Logger.getLogger(ListenForDeletedMembers.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    
	    while(isAlive)
	    {
		try
		{
		    JSONObject response = read();
		    if(response.getEnum(ResponseStatus.class, "Status") == ResponseStatus.OK)
		    {
			if(response.has("ID"))
			    updateListener.UpdateDeletedMember(response.getInt("ID"));
		    }
		}
		catch (IOException ex)
		{
		    Logger.getLogger(ListenForDeletedMembers.class.getName()).log(Level.SEVERE, null, ex);
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