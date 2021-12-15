package msnserver.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import msnserver.dao.UserDAO;

public class FileUtils
{
    public static String SaveFile(String data)
    {
	try
	{
	    String path = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();//+"/MSN/"
	    String separator = Paths.get(path).getFileSystem().getSeparator();
	    String relativePath =  "files"+separator+UUID.randomUUID().toString();
	    File file = new File(path + separator + "MSNServer" + separator + relativePath);
	    byte[] fileData = Base64.getDecoder().decode(data);
	    FileOutputStream outputStream = new FileOutputStream(file);
	    outputStream.write(fileData);
	    outputStream.close();
	    return relativePath;
	}
	catch(IOException ex)
	{
	    Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
	}
	return null;
    }
}	