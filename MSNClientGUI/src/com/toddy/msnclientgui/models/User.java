package com.toddy.msnclientgui.models;

import com.toddy.msnclientgui.enums.Status;

public class User
{
    private Status userStatus;
    private String userName;
    private String userAbout;
    private int userID;

    public User(Status userStatus, String userName, String userAbout, int userID)
    {
	this.userStatus = userStatus;
	this.userName = userName;
	this.userAbout = userAbout;
	this.userID = userID;
    }

    public Status getUserStatus()
    {
	return userStatus;
    }

    public void setUserStatus(Status userStatus)
    {
	this.userStatus = userStatus;
    }

    public String getUserName()
    {
	return userName;
    }

    public void setUserName(String userName)
    {
	this.userName = userName;
    }

    public String getUserAbout()
    {
	return userAbout;
    }

    public void setUserAbout(String userAbout)
    {
	this.userAbout = userAbout;
    }

    public int getUserID()
    {
	return userID;
    }

    public void setUserID(int userID)
    {
	this.userID = userID;
    }
    
    
    
}