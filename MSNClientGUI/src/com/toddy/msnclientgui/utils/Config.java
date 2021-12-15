package com.toddy.msnclientgui.utils;

import com.toddy.msnclientgui.enums.Status;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystem;
import java.nio.file.Paths;
import javax.swing.JFileChooser;

public class Config implements Serializable
{
    private String ReceivedFileFolder;
    private boolean ShowMeAsAwayFor;
    private int MinutesForAFK;
    private String ServerIPAdress;
    
    private String LastUserLoginUsername;
    private String LastUserPassword;
    private Status LastStatus;
    private boolean RememberMe;
    private boolean SignInAuto;

    public String getReceivedFileFolder()
    {
	return ReceivedFileFolder;
    }

    public void setReceivedFileFolder(String ReceivedFileFolder)
    {
	this.ReceivedFileFolder = ReceivedFileFolder;
    }

    public boolean shouldShowMeAsAwayFor()
    {
	return ShowMeAsAwayFor;
    }

    public void setShowMeAsAwayFor(boolean ShowMeAsAwayFor)
    {
	this.ShowMeAsAwayFor = ShowMeAsAwayFor;
    }

    public int getMinutesForAFK()
    {
	return MinutesForAFK;
    }

    public void setMinutesForAFK(int MinutesForAFK)
    {
	this.MinutesForAFK = MinutesForAFK;
    }

    public String getLastUserLoginUsername()
    {
	return LastUserLoginUsername;
    }

    public void setLastUserLoginUsername(String LastUserLoginUsername)
    {
	this.LastUserLoginUsername = LastUserLoginUsername;
    }

    public String getLastUserPassword()
    {
	return LastUserPassword;
    }

    public void setLastUserPassword(String LastUserPassword)
    {
	this.LastUserPassword = LastUserPassword;
    }

    public Status getLastStatus()
    {
	return LastStatus;
    }

    public void setLastStatus(Status LastStatus)
    {
	this.LastStatus = LastStatus;
    }

    public boolean shouldRememberMe()
    {
	return RememberMe;
    }

    public void setShouldRememberMe(boolean RememberMe)
    {
	this.RememberMe = RememberMe;
    }

    public boolean shouldSignInAuto()
    {
	return SignInAuto;
    }

    public void setShouldSignInAuto(boolean SignInAuto)
    {
	this.SignInAuto = SignInAuto;
    }

    public String getServerIPAdress()
    {
	return ServerIPAdress;
    }

    public void setServerIPAdress(String ServerIPAdress)
    {
	this.ServerIPAdress = ServerIPAdress;
    } 
    
    public void CreateDefaultConfigFile()
    {
	this.ReceivedFileFolder = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();//+"/MSN/"
	String separator = Paths.get(ReceivedFileFolder).getFileSystem().getSeparator();
	this.ReceivedFileFolder += separator+"MSN";
	new File(ReceivedFileFolder).mkdirs();
	this.ReceivedFileFolder += separator+"Received";
	new File(ReceivedFileFolder).mkdirs();
	this.LastUserLoginUsername = "";
	this.LastUserPassword = "";
	this.MinutesForAFK = 5;
	this.ShowMeAsAwayFor = true;
	this.LastStatus = Status.Available;
	this.RememberMe = false;
	this.SignInAuto = false;
	this.ServerIPAdress = "localhost";
    }
    
    public static Config ReadConfigFileFromDisk() throws FileNotFoundException, IOException, ClassNotFoundException
    {
	String path = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
	String fileSeparator = Paths.get(path).getFileSystem().getSeparator();
	ObjectInputStream stream = new ObjectInputStream(new FileInputStream(path+fileSeparator+"MSN"+fileSeparator+"config.dat"));
	Config conf = (Config)stream.readObject();
	stream.close();
	return conf;
    }
    
    public static void SaveConfigFileToDisk(Config configFile) throws IOException
    {
	String path = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
	String fileSeparator = Paths.get(path).getFileSystem().getSeparator();
	ObjectOutputStream stream = new ObjectOutputStream (new FileOutputStream(path+fileSeparator+"MSN"+fileSeparator+"config.dat"));
	stream.writeObject(configFile);
    }
}