package com.toddy.msnclientgui.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;

public class JCustomCheckBox extends JToggleButton
{

    private boolean isMouseOver;
    private boolean isMouseDown;

    public JCustomCheckBox()
    {
    }

    @Override
    protected void processMouseMotionEvent(MouseEvent e)
    {
	super.processMouseMotionEvent(e); //To change body of generated methods, choose Tools | Templates.
	if ((e.getX() >= 0 && e.getX() < 14) && (e.getY() >= getHeight() / 2 - 6 && e.getY() < (getHeight() / 2 - 6) + 13))
	{
	    if(!isMouseOver)
	    {
		isMouseOver = true;
		repaint();
	    }
	} 
	else
	{
	    if(isMouseOver)
	    {
		isMouseOver = false;
		repaint();
	    }
	}
    }

    @Override
    protected void processMouseEvent(MouseEvent e)
    {
	super.processMouseEvent(e); //To change body of generated methods, choose Tools | Templates.
	if(e.getButton() == MouseEvent.BUTTON1)
	{
	    if ((e.getX() >= 0 && e.getX() < 14) && (e.getY() >= getHeight() / 2 - 6 && e.getY() < (getHeight() / 2 - 6) + 13))
	    {
		if(!isMouseDown)
		{
		    isMouseDown = true;
		    repaint();
		}
	    } 
	    else
	    {
		if(isMouseDown)
		{
		    isMouseDown = false;
		    repaint();
		}
	    }
	}
	else
	{
	     isMouseDown = false;
	}
    }
    
    @Override
    public void setText(String text)
    {
	super.setText(text); //To change body of generated methods, choose Tools | Templates.
	//repaint();
    }

    @Override
    public void setEnabled(boolean b)
    {
	super.setEnabled(b); //To change body of generated methods, choose Tools | Templates.
	if(!b)
	    setSelected(false);
	repaint();
    }

    @Override
    public void paint(Graphics g)
    {
	//super.paint(g); //To change body of generated methods, choose Tools | Templates.
	g.setColor(getBackground());
	g.fillRect(0, 0, getWidth(), getHeight());
	g.setFont(getFont());
	try
	{
	    if (isEnabled())
	    {
		g.setColor(getForeground());
		g.drawString(getText(), 18, getHeight() - (int) getFont().getSize2D() / 2);
		if (isSelected())
		{
		    if (isMouseOver)
		    {
			if(isMouseDown)
			    g.drawImage(ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/views/checkbox-7.png")), 0, getHeight() / 2 - 6, null);
			else
			    g.drawImage(ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/views/checkbox-4.png")), 0, getHeight() / 2 - 6, null);
		    } 
		    else
		    {
			g.drawImage(ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/views/checkbox-3.png")), 0, getHeight() / 2 - 6, null);
		    }
		} 
		else
		{
		    if (isMouseOver)
		    {
			if(isMouseDown)
			    g.drawImage(ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/views/checkbox-6.png")), 0, getHeight() / 2 - 6, null);
			else
			    g.drawImage(ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/views/checkbox-2.png")), 0, getHeight() / 2 - 6, null);
		    } 
		    else
		    {
			g.drawImage(ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/views/checkbox-1.png")), 0, getHeight() / 2 - 6, null);
		    }
		}
	    }
	    else
	    {
		g.setColor(new Color(193, 193, 193));
		g.drawString(getText(), 18, getHeight() - (int) getFont().getSize2D() / 2);
		g.drawImage(ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/views/checkbox-5.png")), 0, getHeight() / 2 - 6, null);
	    }
	} 
	catch (IOException e)
	{
	    e.printStackTrace();
	}

    }
}
