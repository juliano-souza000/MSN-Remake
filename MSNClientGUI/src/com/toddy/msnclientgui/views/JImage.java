package com.toddy.msnclientgui.views;

import com.toddy.msnclientgui.enums.Status;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class JImage extends JPanel
{
    private Image _Img;
    private boolean shouldDrawWithPictureFrame;
    private Status status;
    private int padding;
    private boolean repeatBackground;
    
    public JImage(){ }
    
    public JImage(Image img)
    {
	this(img, false, Status.Other, 7);
    }
    
    public JImage(Image img, boolean shouldDrawWithPictureFrame, Status status, int padding)
    {
	_Img = img;
	this.shouldDrawWithPictureFrame = shouldDrawWithPictureFrame;
	this.status = status;
	this.padding = padding;
    }
    
    public void shouldDrawWithPictureFrame(boolean shouldDrawWithPictureFrame)
    {
	this.shouldDrawWithPictureFrame = shouldDrawWithPictureFrame;
    }
    
    public void shouldRepeatBackground(boolean repeatBackground)
    {
	this.repeatBackground = repeatBackground;
    }
    
    public void setImage(Image img)
    {
	this._Img  = img;
	repaint();
    }
    
    public Image getImage()
    {
	return this._Img;
    }
    
    public void setStatus(Status status)
    {
	if(this.status != status)
	{
	    this.status = status;
	    repaint();
	}
    }
    
    public Status getStatus()
    {
	return this.status;
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {
	super.paintComponent(g);
        
	if(shouldDrawWithPictureFrame)
	{
	    g.drawImage(_Img, padding, padding, null);
	    try
	    {
		switch(status)
		{
		    case Available:
			g.drawImage(ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/status/available-frame.png")).getScaledInstance(getWidth()-1, getHeight()-1,  Image.SCALE_SMOOTH), 0, 0, null);
		    break;
		    case Away:
			g.drawImage(ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/status/away-frame.png")).getScaledInstance(getWidth()-1, getHeight()-1,  Image.SCALE_SMOOTH), 0, 0, null);
		    break;
		    case Busy:
			g.drawImage(ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/status/busy-frame.png")).getScaledInstance(getWidth()-1, getHeight()-1,  Image.SCALE_SMOOTH), 0, 0, null);
		    break;
		    case Offline:
			g.drawImage(ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/status/offline-frame.png")).getScaledInstance(getWidth()-1, getHeight()-1,  Image.SCALE_SMOOTH), 0, 0, null);
		    break;
		}
	    }
	    catch(IOException e){}
	}
	else
	{
	    if(!repeatBackground)
	    {
		g.drawImage(_Img, 0, 0, null);
	    }
	    else
	    {
		int size =_Img.getWidth(null);
		for (int x = 0; x < getWidth(); x += size)
		{
		    g.drawImage(_Img, x, 0, null);
		}
	    }
	}
    }
}