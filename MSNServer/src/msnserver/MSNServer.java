package msnserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import msnserver.utils.Config;

public class MSNServer
{
    private static Config ConfigFile;
    
    public static void main(String args[])
    {
	PrepareConfigFile();
	LoadDefaultResources();
	if(args.length > 0)
	{
	    if((float)args.length % 2f != 0f)
	    {
		if(args.length == 1 && args[0].equals("-h"))
		{
		    DisplayHelpMessage();   
		}
		else
		{
		    System.out.println(String.format("Invalid argument %s. Or missing argument after it.", args[args.length-1]));
		}
	    }
	    else
	    {
		for(int i = 0; i < args.length; i+=2)
		{ 
		    if(args[i].equals("-db-ip"))
		    {
			ConfigFile.setDBIPAddress(args[i+1]);
		    }
		    else if(args[i].equals("-db-port"))
		    {
			ConfigFile.setDBPort(args[i+1]);
		    }
		    else if(args[i].equals("-db-name"))
		    {
			ConfigFile.setDBName(args[i+1]);
		    }
		    else if(args[i].equals("-db-user"))
		    {
			ConfigFile.setDBUser(args[i+1]);
		    }
		    else if(args[i].equals("-db-pass"))
		    {
			ConfigFile.setDBPassword(args[i+1]);
		    }
		}
	    }
	}
	try
	{
	    Config.SaveConfigFileToDisk(ConfigFile);
	} 
	catch (IOException ex)
	{
	    Logger.getLogger(MSNServer.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	StartServer();
    }
    
    private static void DisplayHelpMessage()
    {
	System.out.println("-h				Show this help message\n"+
			   "-db-ip [ip]			Set the database server IP address\n"+
			   "-db-port [port]			Set the database server port\n"+
			   "-db-name [name]			Set the database name\n"+
			   "-db-user [user]			Set the database user\n"+
			   "-db-pass [password]		Set the database password");
    }
    
    private static void LoadDefaultResources()
    {
	OutputStream outStream = null;
	String path = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();//+"/MSN/"
	String separator = Paths.get(path).getFileSystem().getSeparator();
	
	try
	{
	    InputStream initialStream = MSNServer.class.getClassLoader().getResourceAsStream("msnserver/resources/images/default_profile_picture.png");
	    byte[] buffer = new byte[initialStream.available()];
	    initialStream.read(buffer);
	    File targetFile = new File(path+separator+"MSNServer"+separator+"files"+separator+"default_profile_picture.png");
	    outStream = new FileOutputStream(targetFile);
	    outStream.write(buffer);
	} 
	catch (FileNotFoundException ex)
	{
	    Logger.getLogger(MSNServer.class.getName()).log(Level.SEVERE, null, ex);
	} 
	catch (IOException ex)
	{
	    Logger.getLogger(MSNServer.class.getName()).log(Level.SEVERE, null, ex);
	} 
	finally
	{
	    try
	    {
		outStream.close();
	    } 
	    catch (IOException ex)
	    {
		Logger.getLogger(MSNServer.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }
    
    private static void PrepareConfigFile()
    {
	String path = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();//+"/MSN/"
	String separator = Paths.get(path).getFileSystem().getSeparator();
	new File(path+separator+"MSNServer").mkdirs();
	new File(path+separator+"MSNServer"+separator+"/files").mkdirs();
	
	try
	{
	    ConfigFile = Config.ReadConfigFileFromDisk();
	} 
	catch (IOException ex)
	{
	    ConfigFile = new Config();
	    ConfigFile.CreateDefaultConfigFile();
	    try
	    {
		Config.SaveConfigFileToDisk(ConfigFile);
	    } 
	    catch (IOException exx)
	    {
		Logger.getLogger(MSNServer.class.getName()).log(Level.SEVERE, null, exx);
	    }
	} 
	catch (ClassNotFoundException ex)
	{
	    Logger.getLogger(MSNServer.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    private static void StartServer()
    {
	Server server = new Server();
	try
        {
            server.StartServer(25252);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public static Config getConfig()
    {
	return ConfigFile;
    }
}