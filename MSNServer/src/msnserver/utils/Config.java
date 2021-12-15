package msnserver.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Paths;
import javax.swing.JFileChooser;
import msnserver.MSNServer;

public class Config implements Serializable
{
    private String DBIPAddress;
    private String DBPort;
    private String DBName;
    private String DBUser;
    private String DBPassword;

    public String getDBIPAddress()
    {
	return DBIPAddress;
    }

    public void setDBIPAddress(String DBIPAddress)
    {
	this.DBIPAddress = DBIPAddress;
    }
    
    public void setDBPort(String DBPort)
    {
	this.DBPort = DBPort;
    }
    
    public String getDBPort()
    {
	return DBPort;
    }

    public String getDBName()
    {
	return DBName;
    }

    public void setDBName(String DBName)
    {
	this.DBName = DBName;
    }

    public String getDBUser()
    {
	return DBUser;
    }

    public void setDBUser(String DBUser)
    {
	this.DBUser = DBUser;
    }

    public String getDBPassword()
    {
	return DBPassword;
    }

    public void setDBPassword(String DBPassword)
    {
	this.DBPassword = DBPassword;
    }

    public void CreateDefaultConfigFile()
    {
	this.DBIPAddress = "localhost";
	this.DBPort = "3306";
	this.DBName = "MSN";
	this.DBUser = "root";
	this.DBPassword = "";
    }
    
    public static Config ReadConfigFileFromDisk() throws FileNotFoundException, IOException, ClassNotFoundException
    {
	String path = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();//+"/MSN/"
	String separator = Paths.get(path).getFileSystem().getSeparator();
	ObjectInputStream stream = new ObjectInputStream(new FileInputStream(path+separator+"MSNServer"+separator+"config.dat"));
	Config conf = (Config)stream.readObject();
	stream.close();
	
	return conf;
    }
    
    public static void SaveConfigFileToDisk(Config configFile) throws IOException
    {
	String path = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();//+"/MSN/"
	String separator = Paths.get(path).getFileSystem().getSeparator();
	ObjectOutputStream stream = new ObjectOutputStream (new FileOutputStream(path+separator+"MSNServer"+separator+"config.dat"));
	stream.writeObject(configFile);
    }
}