package msnserver;

import java.nio.channels.SocketChannel;
import java.util.UUID;
import msnserver.dao.MessageDAO;
import msnserver.dao.UserDAO;
import msnserver.enums.Action;
import msnserver.enums.MessageType;
import msnserver.enums.ResponseStatus;
import msnserver.enums.Status;
import msnserver.enums.UpdateType;
import msnserver.models.ListenFor;
import msnserver.utils.CryptUtils;
import msnserver.utils.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class HandleRequest
{
    public static JSONObject Handle(JSONObject jsonObject, SocketChannel socket)
    {
	switch(jsonObject.getEnum(Action.class, "Action"))
	{
	    case Login:
		return HandleLogin(jsonObject.getJSONObject("Content"));
	    case SignUp:
		return HandleSignup(jsonObject.getJSONObject("Content"));
	    case ChangePassword:
		return HandleChangePassword(jsonObject.getJSONObject("Content"));
	    case GetProfile:
		return HandleGetProfile(jsonObject.getJSONObject("Content"));
	    case UpdateStatus:
		return HandleUpdateStatus(jsonObject.getJSONObject("Content"));
	    case UpdateAbout:
		return HandleUpdateAbout(jsonObject.getJSONObject("Content"));
	    case UpdateProfilePicture:
		return HandleUpdateProfilePicture(jsonObject.getJSONObject("Content"));
	    case UpdateUsername:
		return HandleUpdateUsername(jsonObject.getJSONObject("Content"));
	    case SendMessage:
		return HandleSendMessage(jsonObject.getJSONObject("Content"));
	    case GetMessage:
		return HandleGetMessage(jsonObject.getJSONObject("Content"));
	    case GetAllProfiles:
	    	return HandleGetAllProfiles(jsonObject.getJSONObject("Content"));
	    case GetUserProfilePicture:
		return HandleGetProfilePicture(jsonObject.getJSONObject("Content"));
	    case GetUserAnyUpdate:
		return HandleGetUserAnyUpdate(socket, jsonObject);
	    case DeleteAccount:
		return HandleDeleteAccount(jsonObject.getJSONObject("Content"));
	    default:
		return HandleUnsupported();
	}
    }
    
    private static JSONObject HandleLogin(JSONObject jsonObject)
    {
	JSONObject response = new JSONObject();
	String email = jsonObject.getString("Email");
	String password = jsonObject.getString("Password");
	password = CryptUtils.hash256(password);
	if(UserDAO.canUserLogIn(email, password))
	{
	    int userid = UserDAO.getUserID(email);
	    if(!Server.isUsedLogged(userid))
	    {
		response.put("Status", ResponseStatus.Authorized);
		response.put("SessionID", UUID.randomUUID());
		response.put("ID", userid);   
	    }
	    else
	    {
		response.put("Status", ResponseStatus.Unauthorized);
	    }
	}
	else
	{
	    response.put("Status", ResponseStatus.Unauthorized);
	}
	return response;
    }
    
    private static JSONObject HandleSignup(JSONObject jsonObject)
    {
	JSONObject response = new JSONObject();
	String username = jsonObject.getString("Username");
	String email = jsonObject.getString("Email");
	String password = jsonObject.getString("Password");
	password = CryptUtils.hash256(password);
	if(UserDAO.canUserSignup(email))
	{
	    UserDAO.signUp(username, email, password);
	    response.put("Status", ResponseStatus.Authorized);
	    Server.warnUser(Action.SignUp, UserDAO.getUserID(email), null);
	}
	else
	{
	    response.put("Status", ResponseStatus.Unauthorized);
	}
	return response;
    }
    
    private static JSONObject HandleChangePassword(JSONObject jsonObject)
    {
	JSONObject jo = new JSONObject();
	String email = jsonObject.getString("Email");
	String password = jsonObject.getString("Password");
	password = CryptUtils.hash256(password);
	if(UserDAO.changeUserPass(email, password))
	{
	    jo.put("Status", ResponseStatus.Authorized);
	}
	else
	{
	    jo.put("Status", ResponseStatus.InternalError);
	}
	return jo;
    }
    
    private static JSONObject HandleGetProfile(JSONObject jsonObject)
    {
	JSONObject response = new JSONObject();
	int id = jsonObject.getInt("ID");
	response.put("Status", ResponseStatus.OK);
	response.put("Profile", UserDAO.getUserProfile(id));
	return response;
    }
    
    private static JSONObject HandleUpdateStatus(JSONObject jsonObject)
    {
	JSONObject response = new JSONObject();
	int id = Server.getUserIDFromSessions(UUID.fromString(jsonObject.get("SessionID").toString()));
	if(UserDAO.updateUserStatus(id, jsonObject.getEnum(Status.class, "UserStatus")))
	{
	    response.put("Status", ResponseStatus.OK);
	    Server.warnUser(Action.UpdateStatus, id, null);
	}
	else
	{
	    response.put("Status", ResponseStatus.InternalError);
	}
	
	return response;
    }
    
    private static JSONObject HandleUpdateAbout(JSONObject jsonObject)
    {
	JSONObject response = new JSONObject();
	int id = Server.getUserIDFromSessions(UUID.fromString(jsonObject.get("SessionID").toString()));
	if(UserDAO.updateUserAbout(id, jsonObject.getString("About")))
	{
	    response.put("Status", ResponseStatus.OK);
	    Server.warnUser(Action.UpdateAbout, id, null);
	}
	else
	{
	    response.put("Status", ResponseStatus.InternalError);
	}
	
	return response;
    }
    
    private static JSONObject HandleUpdateProfilePicture(JSONObject jsonObject)
    {
	JSONObject jo = new JSONObject();
	UUID sessionID = UUID.fromString(jsonObject.getString("SessionID"));
	int userID = Server.getUserIDFromSessions(sessionID);
	if(userID != -1)
	{
	    String path = FileUtils.SaveFile(jsonObject.getString("Image"));
	    if(UserDAO.updateUserProfilePicture(userID, path))
	    {
		jo.put("Status", ResponseStatus.OK);
		Server.warnUser(Action.UpdateProfilePicture, userID, null);
	    }
	    else
	    {
		jo.put("Status", ResponseStatus.InternalError);
	    }
	}
	else
	{
	    jo.put("Status", ResponseStatus.Unauthorized);
	}
	return jo;
    }
    
    private static JSONObject HandleUpdateUsername(JSONObject jsonObject)
    {
	JSONObject jo = new JSONObject();
	UUID sessionID = UUID.fromString(jsonObject.getString("SessionID"));
	int userID = Server.getUserIDFromSessions(sessionID);
	if(userID != -1)
	{
	    String username = jsonObject.getString("Username");
	    if(UserDAO.updateUsername(userID, username))
	    {
		jo.put("Status", ResponseStatus.OK);
		Server.warnUser(Action.UpdateUsername, userID, null);
	    }
	    else
	    {
		jo.put("Status", ResponseStatus.InternalError);
	    }
	}
	else
	{
	    jo.put("Status", ResponseStatus.Unauthorized);
	}
	return jo;
    }
    
    private static JSONObject HandleGetProfilePicture(JSONObject jsonObject)
    {
	JSONObject jo = new JSONObject();
	UUID sessionID = UUID.fromString(jsonObject.getString("SessionID"));
	int userID = Server.getUserIDFromSessions(sessionID);
	if(userID != -1)
	{
	    jo.put("Status", ResponseStatus.OK);
	    jo.put("Profiles", UserDAO.getUserProfilePicture(jsonObject.getInt("ID")));
	}
	else
	{
	    jo.put("Status", ResponseStatus.Unauthorized);
	}
	return jo;
    } 
    
    private static JSONObject HandleGetAllProfiles(JSONObject jsonObject)
    {
	JSONObject jo = new JSONObject();
	UUID sessionID = UUID.fromString(jsonObject.getString("SessionID"));
	int userID = Server.getUserIDFromSessions(sessionID);
	if(userID != -1)
	{
	    jo.put("Status", ResponseStatus.OK);
	    jo.put("Profiles", UserDAO.getAllUsersProfile(userID));
	}
	else
	{
	    jo.put("Status", ResponseStatus.Unauthorized);
	}
	return jo;
    }
    
    private static JSONObject HandleSendMessage(JSONObject jsonObject)
    {
	JSONObject jo = new JSONObject();
	UUID sessionID = UUID.fromString(jsonObject.getString("SessionID"));
	int userID = Server.getUserIDFromSessions(sessionID);
	if(userID != -1)
	{
	    JSONObject obj = new JSONObject();
	    JSONObject messageContentObj = new JSONObject();

	    int msgID = -1;
	    jo.put("Status", ResponseStatus.OK);
	    obj.put("Receiver", jsonObject.getInt("ReceiverID"));
	    if(jsonObject.getEnum(MessageType.class, "MessageType") == MessageType.TEXT)
	    {
		messageContentObj.put("Data", jsonObject.getJSONObject("MessageContent").getString("Data"));
	    }
	    else
	    {
		
		messageContentObj.put("Filename", jsonObject.getJSONObject("MessageContent").getString("Filename"));
		messageContentObj.put("Filepath", FileUtils.SaveFile(jsonObject.getJSONObject("MessageContent").getString("Data")));
	    }
	    msgID = MessageDAO.addMessage(jsonObject.getInt("ReceiverID"), userID, messageContentObj, jsonObject.getEnum(MessageType.class, "MessageType"));
	    obj.put("MessageID", msgID);
	    Server.warnUser(Action.SendMessage, userID, obj);
	}
	else
	{
	    jo.put("Status", ResponseStatus.Unauthorized);
	}
	return jo;
    }
    
    private static JSONObject HandleGetMessage(JSONObject jsonObject)
    {
	JSONObject jo = new JSONObject();
	UUID sessionID = UUID.fromString(jsonObject.getString("SessionID"));
	int userID = Server.getUserIDFromSessions(sessionID);
	if(jsonObject.has("MessageID"))
	{
		jo = HandleGetMessageWithID(jsonObject);
	}
	else
	{
	    if(userID != -1)
	    {
		JSONArray messages = MessageDAO.getAllMessages(userID, jsonObject.getInt("ChatWithID"));
		if(messages != null)
		{
		    jo.put("Status", ResponseStatus.OK);
		    jo.put("Messages", messages);
		}
		else
		{
		    jo.put("Status", ResponseStatus.Forbidden);
		}

	    }
	    else
	    {
		jo.put("Status", ResponseStatus.Unauthorized);
	    }
	}
	return jo;
    }
    
    private static JSONObject HandleGetMessageWithID(JSONObject jsonObject)
    {
	JSONObject jo = new JSONObject();
	UUID sessionID = UUID.fromString(jsonObject.getString("SessionID"));
	int userID = Server.getUserIDFromSessions(sessionID);
	if(userID != -1)
	{
	    JSONObject message = MessageDAO.getMessage(userID, jsonObject.getInt("MessageID"));
	    if(message != null)
	    {
		jo.put("Status", ResponseStatus.OK);
		jo.put("Message", message);
	    }
	    else
	    {
		jo.put("Status", ResponseStatus.Forbidden);
	    }
	   
	    //Server.warnUser(Action.SendMessage, userID, jsonObject);
	}
	else
	{
	    jo.put("Status", ResponseStatus.Unauthorized);
	}
	return jo;
    }
    
    private static JSONObject HandleDeleteAccount(JSONObject jsonObject)
    {
	JSONObject jo = new JSONObject();
	UUID sessionID = UUID.fromString(jsonObject.getString("SessionID"));
	int userID = Server.getUserIDFromSessions(sessionID);
	if(userID != -1)
	{
	    if(UserDAO.deleteUserAccount(userID))
	    {
		jo.put("Status", ResponseStatus.OK);
		Server.warnUser(Action.DeleteAccount, userID, jsonObject);
	    }
	    else
	    {
		jo.put("Status", ResponseStatus.InternalError);
	    }
	}
	else
	{
	    jo.put("Status", ResponseStatus.Unauthorized);
	}
	return jo;
    }
    
    private static JSONObject HandleGetUserAnyUpdate(SocketChannel socket, JSONObject jsonObject)
    {
	JSONObject jo = new JSONObject();
	UUID sessionID = UUID.fromString(jsonObject.getString("SessionID"));
	
	UpdateType updateType = jsonObject.getEnum(UpdateType.class, "UpdateType");
	if(Server.getUserIDFromSessions(sessionID) != -1 )
	{
	    jo.put("Status", ResponseStatus.OK);
	    if(jsonObject.has("ID"))
	    {
		int idToGet = jsonObject.getInt("ID");
		jo.put("ID", idToGet);
		if(idToGet != Server.getUserIDFromSessions(sessionID))
		{
		    switch(updateType)
		    {
			case MemberAdded:
			    jo.put("Profile", UserDAO.getUserProfile(idToGet));
			    break;
			case StatusUpdated:
			    jo.put("UserStatus", UserDAO.getUserStatus(idToGet));
			    break;
			case AboutUpdated:
			    jo.put("About", UserDAO.getUserAbout(idToGet));
			break;
			case ProfilePictureUpdate:
			    jo.put("Image", UserDAO.getUserProfilePicture(idToGet));
			    break;
			case UsernameUpdated:
			    jo.put("Username", UserDAO.getUserName(idToGet));
			    break;
		    }
		}
	    }
	    Server.addMemberListeningFor(socket, new ListenFor(sessionID, updateType));
	}
	else
	{
	    jo.put("Status", ResponseStatus.Unauthorized);
	}
	return jo;
    }
    
    
    private static JSONObject HandleUnsupported()
    {
	JSONObject response = new JSONObject();
	response.put("Status", ResponseStatus.Unsupported);
	return response;
    }
}