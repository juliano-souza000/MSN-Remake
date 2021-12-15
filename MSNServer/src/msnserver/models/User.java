package msnserver.models;

import java.util.UUID;

public class User
{
    private UUID SessionID;
    private int UserID;
    
    public User(UUID sessionID, int userID)
    {
	this.SessionID = sessionID;
	this.UserID = userID;
    }

    public UUID getSessionID()
    {
	return SessionID;
    }
    
    public int getUserID()
    {
	return UserID;
    }
    
}