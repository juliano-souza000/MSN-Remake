package msnserver;

import msnserver.models.User;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import msnserver.dao.UserDAO;
import msnserver.enums.Action;
import msnserver.enums.ResponseStatus;
import msnserver.enums.UpdateType;
import msnserver.models.ListenFor;
import org.json.JSONObject;

public class Server 
{
    private ServerSocketChannel server;
    private Selector selector;
    private static Map<SocketChannel, User> sessions = new HashMap<SocketChannel, User>();
    private static Map<SocketChannel, ListenFor> listeningFor = new HashMap<SocketChannel, ListenFor>();

    public void StartServer(int port) throws IOException 
    {
        selector = Selector.open();
        server = ServerSocketChannel.open();
        server.configureBlocking(false);
        server.socket().bind(new InetSocketAddress(port));
        server.register(selector, SelectionKey.OP_ACCEPT);
        Listen();
    }

    private void Listen() throws IOException 
    {
        while (selector.isOpen()) 
        {
            selector.select();
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) 
            {
                SelectionKey key = keys.next();
                if (!key.isValid()) 
                {
                    continue;
                }

                if (key.isReadable()) 
                {
		    
                    SocketChannel socket = (SocketChannel) key.channel();
		    ByteBuffer buffer = ByteBuffer.allocate(socket.socket().getReceiveBufferSize());
                    buffer.clear();
		    try
		    {
			if (socket.read(buffer) == -1) 
			{ //-1 is end of stream
			    System.out.println("Client Disconnected " + socket);
			    RevokeSession(socket);
			    socket.close();
			    continue;
			} 
			else 
			{
			    String strc = new String(buffer.array(), StandardCharsets.UTF_8).trim();
			    JSONObject contentLength = new JSONObject(strc);
			    JSONObject contentLengthR = new JSONObject();
			    contentLengthR.put("Status",  ResponseStatus.OK);
			    socket.write(ByteBuffer.wrap(contentLengthR.toString().getBytes()));
			    buffer.clear();
			    buffer = ByteBuffer.allocate(contentLength.getInt("Content-Length"));
			    int read = 0;
			    do
			    {
				read += socket.read(buffer);
			    }while(read != contentLength.getInt("Content-Length") );
			    if (read > 0) 
			    {
				String str = new String(buffer.array(), StandardCharsets.UTF_8).trim();
				JSONObject clientRequest = new JSONObject(str);
				JSONObject responseJSON;
				contentLength = new JSONObject();
			    
				try
				{
				    responseJSON = HandleRequest.Handle(clientRequest, socket);
				    if(responseJSON.has("SessionID"))
				    {
					sessions.put(socket, new User((UUID)responseJSON.get("SessionID"), UserDAO.getUserID(clientRequest.getJSONObject("Content").getString("Email"))));
				    }
				}
				catch(Exception ex)
				{
				    responseJSON = new JSONObject();
				    responseJSON.put("Status", ResponseStatus.InternalError);
				    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
				}
				contentLength.put("Content-Length", responseJSON.toString().getBytes().length);
				ByteBuffer response = ByteBuffer.wrap(contentLength.toString().getBytes());
				socket.write(response);
				response = ByteBuffer.wrap(responseJSON.toString().getBytes());
				socket.write(response);
				buffer = ByteBuffer.allocate(socket.socket().getReceiveBufferSize());
			    }
			}
		    }
		    catch(IOException e) 
		    { 
			RevokeSession(socket);
		    }
                } 
                else if (key.isAcceptable()) 
                {
                    ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socket = serverChannel.accept();
                    socket.configureBlocking(false);
                    socket.register(selector, SelectionKey.OP_READ);
                    System.out.println("Client Connected " + socket);
                }
            }
            selector.selectedKeys().clear();
        }
    }
    
        
    public static int getUserIDFromSessions(UUID sessionID)
    {
	int id = -1;
	for(User user : sessions.values())
	{
	    if(user.getSessionID().toString().equals(sessionID.toString()))
	    {
		id = user.getUserID();
		break;
	    }
	}
	return id;
    }
    
    public static boolean isUsedLogged(int id)
    {
	boolean logged = false;
	for(User user : sessions.values())
	{
	    if(id == user.getUserID())
	    {
		logged = true;
		break;
	    }
	}
	return logged;
    }
    
    public static void warnUser(Action action, int ID, JSONObject params)
    {
	ListenFor[] listenForArray = listeningFor.values().toArray(new ListenFor[listeningFor.values().size()]);
	Set<SocketChannel> keys = listeningFor.keySet();
	SocketChannel[] sockets = keys.toArray(new SocketChannel[keys.size()]);
	for(int i = 0; i < listeningFor.size(); i++)
	{
	    try
	    {
		JSONObject jo = new JSONObject();
		jo.put("SessionID",  listenForArray[i].getSessionID().toString());
		jo.put("ID", ID);
		if(getUserIDFromSessions(listenForArray[i].getSessionID()) != ID)
		{
		    switch(action)
		    {
			case SignUp:
			    if(listenForArray[i].what() == UpdateType.MemberAdded)
			    {
				JSONObject contentLength = new JSONObject();
				JSONObject responseJSON;
				ByteBuffer response;
				jo.put("Action", Action.GetUserAnyUpdate);
				jo.put("UpdateType", UpdateType.MemberAdded);
				responseJSON = HandleRequest.Handle(jo,  sockets[i]);
				contentLength.put("Content-Length", responseJSON.toString().getBytes().length);
				response = ByteBuffer.wrap(contentLength.toString().getBytes());
				sockets[i].write(response);
				response.clear();
				response = ByteBuffer.wrap(responseJSON.toString().getBytes());
				sockets[i].write(response);
			    }
			case UpdateStatus:
			    if(listenForArray[i].what() ==  UpdateType.StatusUpdated)
			    {
				JSONObject contentLength = new JSONObject();
				JSONObject responseJSON;
				ByteBuffer response;
				jo.put("Action", Action.GetUserAnyUpdate);
				jo.put("UpdateType", UpdateType.StatusUpdated);
				responseJSON = HandleRequest.Handle(jo,  sockets[i]);
				contentLength.put("Content-Length", responseJSON.toString().getBytes().length);
				response = ByteBuffer.wrap(contentLength.toString().getBytes());
				sockets[i].write(response);
				response.clear();
				response = ByteBuffer.wrap(responseJSON.toString().getBytes());
				sockets[i].write(response);
			    }
			    break;
			case UpdateAbout:
			    if(listenForArray[i].what() == UpdateType.AboutUpdated)
			    {
				JSONObject contentLength = new JSONObject();
				JSONObject responseJSON;
				ByteBuffer response;
				jo.put("Action", Action.GetUserAnyUpdate);
				jo.put("UpdateType", UpdateType.AboutUpdated);
				responseJSON = HandleRequest.Handle(jo,  sockets[i]);
				contentLength.put("Content-Length", responseJSON.toString().getBytes().length);
				response = ByteBuffer.wrap(contentLength.toString().getBytes());
				sockets[i].write(response);
				response.clear();
				response = ByteBuffer.wrap(responseJSON.toString().getBytes());
				sockets[i].write(response);
			    }
			break;
			case SendMessage:
			    if(listenForArray[i].what() == UpdateType.MessageReceived)
			    {
				JSONObject content = new JSONObject();
				JSONObject contentLength = new JSONObject();
				JSONObject responseJSON;
				ByteBuffer response;
				jo.put("Action", Action.GetMessage);
				content.put("SessionID", listenForArray[i].getSessionID().toString());
				content.put("Receiver", params.getInt("Receiver"));
				content.put("Sender", ID);
				content.put("MessageID", params.getInt("MessageID"));
				jo.put("Content", content);
				responseJSON = HandleRequest.Handle(jo,  sockets[i]);
				contentLength.put("Content-Length", responseJSON.toString().getBytes().length);
				response = ByteBuffer.wrap(contentLength.toString().getBytes());
				sockets[i].write(response);
				response.clear();
				response = ByteBuffer.wrap(responseJSON.toString().getBytes());
				sockets[i].write(response);
			    }
			    break;
			case UpdateProfilePicture:
			    if(listenForArray[i].what() == UpdateType.ProfilePictureUpdate)
			    {
				JSONObject contentLength = new JSONObject();
				JSONObject responseJSON;
				ByteBuffer response;
				jo.put("Action", Action.GetUserAnyUpdate);
				jo.put("UpdateType", UpdateType.ProfilePictureUpdate);
				responseJSON = HandleRequest.Handle(jo,  sockets[i]);
				contentLength.put("Content-Length", responseJSON.toString().getBytes().length);
				response = ByteBuffer.wrap(contentLength.toString().getBytes());
				sockets[i].write(response);
				response.clear();
				response = ByteBuffer.wrap(responseJSON.toString().getBytes());
				sockets[i].write(response);
			    }
			    break;
			case UpdateUsername:
			    if(listenForArray[i].what() == UpdateType.UsernameUpdated)
			    {
				JSONObject contentLength = new JSONObject();
				JSONObject responseJSON;
				ByteBuffer response;
				jo.put("Action", Action.GetUserAnyUpdate);
				jo.put("UpdateType", UpdateType.UsernameUpdated);
				responseJSON = HandleRequest.Handle(jo,  sockets[i]);
				contentLength.put("Content-Length", responseJSON.toString().getBytes().length);
				response = ByteBuffer.wrap(contentLength.toString().getBytes());
				sockets[i].write(response);
				response.clear();
				response = ByteBuffer.wrap(responseJSON.toString().getBytes());
				sockets[i].write(response);
			    }
			    break;
			case DeleteAccount:
			    if(listenForArray[i].what() == UpdateType.AccountDeleted)
			    {
				JSONObject contentLength = new JSONObject();
				JSONObject responseJSON;
				ByteBuffer response;
				jo.put("Action", Action.GetUserAnyUpdate);
				jo.put("UpdateType", UpdateType.UsernameUpdated);
				responseJSON = HandleRequest.Handle(jo,  sockets[i]);
				contentLength.put("Content-Length", responseJSON.toString().getBytes().length);
				response = ByteBuffer.wrap(contentLength.toString().getBytes());
				sockets[i].write(response);
				response.clear();
				response = ByteBuffer.wrap(responseJSON.toString().getBytes());
				sockets[i].write(response);
			    }
			    break;
		    }
		}
	    } 
	    catch (IOException ex)
	    {
		Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		if(ex instanceof ClosedChannelException || !ex.getMessage().equals("An existing connection was forcibly closed by the remote host"));
		{
		    listeningFor.remove(sockets[i]);
		}
	    }
	}
    }
    
    public static void addMemberListeningFor(SocketChannel socket, ListenFor listenFor)
    {
	if(!listeningFor.containsKey(socket))
	    listeningFor.put(socket, listenFor);
    }
    
    private void RevokeSession(SocketChannel socket)
    {
	sessions.remove(socket);
    }
}
