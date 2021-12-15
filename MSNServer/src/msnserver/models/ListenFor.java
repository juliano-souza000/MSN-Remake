package msnserver.models;

import java.util.UUID;
import msnserver.enums.Action;
import msnserver.enums.UpdateType;

public class ListenFor
{
    private UUID SessionID;
    private UpdateType _UpdateType;
    
    public ListenFor(UUID sessionID, UpdateType updateType)
    {
	this.SessionID = sessionID;
	this._UpdateType = updateType;
    }
    
    public UUID getSessionID()
    {
	return this.SessionID;
    }
    
    public UpdateType what()
    {
	return this._UpdateType;
    }
}