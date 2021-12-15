package com.toddy.msnclientgui.utils;

import com.toddy.msnclientgui.Main;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Utils
{
    public static final int GB_BYTES = 1073741824; 
    public static final int MB_BYTES = 1048576;
    public static final int KB_BYTES = 1024;
    
    public static BufferedImage getAppIcon() throws IOException
    {
	return ImageIO.read(Utils.class.getClassLoader().getResource("com/toddy/msnclientgui/resources/icons/app.png")); 
    }
    
    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) 
    {

	int original_width = imgSize.width;
	int original_height = imgSize.height;
	int bound_width = boundary.width;
	int bound_height = boundary.height;
	int new_width = original_width;
	int new_height = original_height;

	if(imgSize.width >= imgSize.height)
	{
	    // first check if we need to scale width
	    if (original_width > bound_width) 
	    {
		//scale width to fit
		new_width = bound_width;
		//scale height to maintain aspect ratio
		new_height = (new_width * original_height) / original_width;
	    }

	    // then check if we need to scale even with the new height
	    if (new_height > bound_height) 
	    {
		//scale height to fit instead
		new_height = bound_height;
		//scale width to maintain aspect ratio
		new_width = (new_height * original_width) / original_height;
	    }
	}
	else
	{
	    // then check if we need to scale even with the new height
	    if (new_height > bound_height) 
	    {
		//scale height to fit instead
		new_height = bound_height;
		//scale width to maintain aspect ratio
		new_width = (new_height * original_width) / original_height;
	    }
	    
	    // first check if we need to scale width
	    if (original_width > bound_width) 
	    {
		//scale width to fit
		new_width = bound_width;
		//scale height to maintain aspect ratio
		new_height = (new_width * original_height) / original_width;
	    }
	}
	return new Dimension(new_width, new_height);
    }
    
    public static byte[] extractBytes (BufferedImage image) throws IOException 
    {
	WritableRaster raster = image .getRaster();
	DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();
	 return ( data.getData() );
    }
    
    public static boolean isNullOrEmpty(String s) 
    {
	return s == null || s.length() == 0;
    }

    public static boolean isNullOrWhitespace(String s) 
    {
	return s == null || isWhitespace(s);
    }
    
    private static boolean isWhitespace(String s) 
    {
	int length = s.length();
	if (length > 0) {
	    for (int i = 0; i < length; i++) 
	    {
		if (!Character.isWhitespace(s.charAt(i))) 
		{
		    return false;
		}
	    }
	    return true;
	}
	return false;
    }
    
    public static BufferedImage toBufferedImage(Image img)
    {
	if (img instanceof BufferedImage)
	{
	    return (BufferedImage) img;
	}

	// Create a buffered image with transparency
	BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	// Draw the image on to the buffered image
	Graphics2D bGr = bimage.createGraphics();
	bGr.drawImage(img, 0, 0, null);
	bGr.dispose();

	// Return the buffered image
	return bimage;
    }
    
    public static BufferedImage toBufferedImage(byte[] imageData) 
    {
	ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
	try 
	{
	    return ImageIO.read(bais);
	} 
	catch (IOException e) 
	{
	    throw new RuntimeException(e);
	}
    }

    public static Icon toIcon(Shape shape, Color color, Color strokeColor)
    {
	BufferedImage img = new BufferedImage(shape.getBounds().width, shape.getBounds().height, BufferedImage.TYPE_INT_ARGB);
	Graphics2D g2 = img.createGraphics();
	g2.setColor(color);

	g2.fill(shape);
	// g2.draw(shape);

	g2.setStroke(new BasicStroke(1));
	g2.setColor(strokeColor);
	g2.drawRect(0, 0, img.getWidth() - 1, img.getHeight() - 1);

	g2.dispose();
	return new ImageIcon(img);
    }

    public static boolean isValidEmailAddress(String email)
    {
	String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
	java.util.regex.Matcher m = p.matcher(email);
	return m.matches();
    }
}
