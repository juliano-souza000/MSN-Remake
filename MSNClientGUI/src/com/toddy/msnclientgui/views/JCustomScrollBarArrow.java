package com.toddy.msnclientgui.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import static javax.swing.SwingConstants.EAST;
import static javax.swing.SwingConstants.NORTH;
import static javax.swing.SwingConstants.SOUTH;
import static javax.swing.SwingConstants.WEST;
import javax.swing.UIManager;

public class JCustomScrollBarArrow extends JButton
{

    protected int direction;

    private Color shadow;
    private Color highlight;
    private boolean isMouseOver;

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
    
    public JCustomScrollBarArrow(int direction, Color background, Color shadow, Color highlight)
    {
	super();
	setRequestFocusEnabled(false);
	setDirection(direction);
	setBackground(background);
	//setSize(new Dimension(5, 15));
	this.shadow = shadow;
	this.highlight = highlight;
    }

    public JCustomScrollBarArrow(int direction)
    {
	this(direction, UIManager.getColor("control"), UIManager.getColor("controlShadow"), UIManager.getColor("controlLtHighlight"));
    }

    public int getDirection()
    {
	return direction;
    }

    public void setDirection(int direction)
    {
	this.direction = direction;
    }

    public void paint(Graphics g2)
    {
	Graphics2D g = (Graphics2D)g2;
	Color origColor;
	boolean isPressed;
	boolean isEnabled;
	int w;
	int h;
	int size;

	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	w = getSize().width;
	h = getSize().height;
	origColor = g.getColor();
	isPressed = getModel().isPressed();
	isEnabled = isEnabled();

	g.setColor(getBackground());
	g.fillRect(0, 0, w, h);

	// If there's no room to draw arrow, bail
	if (h < 5 || w < 5)
	{
	    g.setColor(origColor);
	    return;
	}
	
		
	if (isMouseOver)
	{
	    g.setColor(new Color(60,127,177));
	    g.drawRoundRect(0, 0, w-1, h-1, 2, 3);
	    g.setPaint(new GradientPaint(0,0, new Color(238,248,253), 0, 5, new Color(214,238,251)));
	    g.fillRect(2, 2, 6, h-4);
	    g.setPaint(new GradientPaint(0,0, new Color(198,231,249), 0, 30, new Color(148,192,216)));
	    g.fillRect(7, 2, 6, h-4);
	}
	
	if (isPressed)
	{
	    g.setColor(new Color(24,89,138));
	    g.drawRoundRect(0, 0, w-1, h-1, 2, 3);
	    g.setPaint(new GradientPaint(0,0, new Color(221,243,251), 0, 5, new Color(170,217,236)));
	    g.fillRect(2, 2, 6, h-4);
	    g.setPaint(new GradientPaint(0,0, new Color(159,220,245), 0, 30, new Color(98,177,211)));
	    g.fillRect(7, 2, 6, h-4);
	}


	// Draw the arrow
	size = Math.min((h - 4) / 3, (w - 4) / 3);
	size = Math.max(size, 2);
	paintTriangle((Graphics2D)g, (w) / 2, (h - 3) / 2, 4, direction, isEnabled, isPressed);

	// Reset the Graphics back to it's original settings
	
	g.setColor(origColor);

    }

    public Dimension getPreferredSize()
    {
	return new Dimension(15, 15);
    }

    public Dimension getMinimumSize()
    {
	return new Dimension(5, 5);
    }

    public Dimension getMaximumSize()
    {
	return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public boolean isFocusTraversable()
    {
	return false;
    }

    public void paintTriangle(Graphics2D g, int x, int y, int size, int direction, boolean isEnabled, boolean isPressed)
    {
	Color oldColor = g.getColor();
	int mid; 
	int i;
	int j;

	j = 0;
	size = Math.max(size, 2);
	mid = (size / 2) - 1;

	g.translate(x, y);
	if (isEnabled)
	{
	    g.setPaint(new GradientPaint(0, 0, new Color(0, 0, 0), 4, 5, new Color(255, 255, 255)));
	} 
	else
	{
	    g.setColor(shadow);
	}
	
	if(isMouseOver)
	{
	    g.setPaint(new GradientPaint(0, 0, new Color(18, 60, 82), 4, 5, new Color(175, 215, 237)));
	}
	
	if(isPressed)
	{
	    g.setPaint(new GradientPaint(0, 0, new Color(20, 50, 65), 4, 5, new Color(176, 205, 220)));
	    //
	}

	switch (direction)
	{
	    case NORTH:
		for (i = 0; i < size; i++)
		{
		    g.drawLine(mid - i, i, mid + i, i);
		}
		if (!isEnabled)
		{
		    g.setColor(highlight);
		    g.drawLine(mid - i + 2, i, mid + i, i);
		}
		break;
	    case SOUTH:
		if (!isEnabled)
		{
		    g.translate(1, 1);
		    g.setColor(highlight);
		    for (i = size - 1; i >= 0; i--)
		    {
			g.drawLine(mid - i, j, mid + i, j);
			j++;
		    }
		    g.translate(-1, -1);
		    g.setColor(shadow);
		}

		j = 0;
		for (i = size - 1; i >= 0; i--)
		{
		    g.drawLine(mid - i, j, mid + i, j);
		    j++;
		}
		break;
	    case WEST:
		for (i = 0; i < size; i++)
		{
		    g.drawLine(i, mid - i, i, mid + i);
		}
		if (!isEnabled)
		{
		    g.setColor(highlight);
		    g.drawLine(i, mid - i + 2, i, mid + i);
		}
		break;
	    case EAST:
		if (!isEnabled)
		{
		    g.translate(1, 1);
		    g.setColor(highlight);
		    for (i = size - 1; i >= 0; i--)
		    {
			g.drawLine(j, mid - i, j, mid + i);
			j++;
		    }
		    g.translate(-1, -1);
		    g.setColor(shadow);
		}

		j = 0;
		for (i = size - 1; i >= 0; i--)
		{
		    g.drawLine(j, mid - i, j, mid + i);
		    j++;
		}
		break;
	}
	g.translate(-x, -y);
	g.setColor(oldColor);
    }
}
