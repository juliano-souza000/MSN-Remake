package com.toddy.msnclientgui.models;

import com.toddy.msnclientgui.enums.MessageType;

public class TextMessage extends Message
{
    private String message;
    
    public TextMessage(String senderName, int senderID, String message)
    {
	super(senderName, senderID);
	this.message = message;
    }

    public String getMessage()
    {
	return message;
    }

    public void setMessage(String message)
    {
	this.message = message;
    }
    
}