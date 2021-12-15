package com.toddy.msnclientgui.models;

public abstract class Message
{
    private String senderName;
    private int senderID;

    public Message(String senderName, int senderID)
    {
	this.senderName = senderName;
	this.senderID = senderID;
    }

    public String getSenderName()
    {
	return senderName;
    }

    public void setSenderName(String senderName)
    {
	this.senderName = senderName;
    }

    public int getSenderID()
    {
	return senderID;
    }

    public void setSenderID(int senderID)
    {
	this.senderID = senderID;
    }
    
    
}