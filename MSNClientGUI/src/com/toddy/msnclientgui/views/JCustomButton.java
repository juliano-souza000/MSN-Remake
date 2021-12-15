package com.toddy.msnclientgui.views;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JButton;

public class JCustomButton extends JButton
{
    private boolean isMouseOver;
    private boolean isMouseDown;
    
    public JCustomButton()
    {
    }

    public void mouseExit()
    {
	isMouseOver = false;
	repaint();
    }
    
    @Override
    protected void processMouseMotionEvent(MouseEvent e)
    {
	super.processMouseMotionEvent(e); //To change body of generated methods, choose Tools | Templates.
	
	if ((e.getX() >= 0 && e.getX() < getWidth()) && (e.getY() >= 0 && e.getY() < getHeight()))
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
	    if ((e.getX() >= 0 && e.getX() < getWidth()) && (e.getY() >= 0 && e.getY() < getHeight()))
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
	Graphics2D g2 = (Graphics2D)g;
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g2.setColor(getBackground());
	g2.fillRect(0, 0, getWidth(), getHeight());
	
	Map attributes = getFont().getAttributes();
	attributes.put(TextAttribute.TRACKING, -0.001f);
	Font font = getFont().deriveFont(attributes);
	g2.setFont(font);
	
	try
	{
	    FontMetrics metrics = g2.getFontMetrics(font);
	    if (isEnabled())
	    {
		//new File("D:\\Documentos\\Java\\MSNClientGUI\\resources\\pictures\\views\\button-4.png")
		if (isMouseOver)
		{
		    if(isMouseDown)
			g2.drawImage(ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/views/button-4.png")).getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
		    else
			g2.drawImage(ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/views/button-3.png")).getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, null) ;
		} 
		else
		{
		    if(hasFocus())
			g2.drawImage(ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/views/button-2.png")).getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
		    else
			g2.drawImage(ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/views/button-1.png")).getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
		}
		g2.setColor(getForeground());
		g2.drawString(getText(), (getWidth()/2-metrics.stringWidth(getText())/2), ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent());
	    }
	    else
	    {
		g2.drawImage(ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/views/button-5.png")).getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
		g2.setColor(new Color(193, 193, 193));
		g2.drawString(getText(),(getWidth()/2-metrics.stringWidth(getText())/2),  ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent());
	    }
	} 
	catch (IOException e)
	{
	    e.printStackTrace();
	}
    }
}