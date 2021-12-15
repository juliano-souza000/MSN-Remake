package com.toddy.msnclientgui.views.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import static javax.swing.SwingConstants.EAST;
import static javax.swing.SwingConstants.NORTH;
import static javax.swing.SwingConstants.SOUTH;
import static javax.swing.SwingConstants.WEST;
import javax.swing.UIManager;
import javax.swing.plaf.UIResource;

public class CustomArrowButton extends JButton implements SwingConstants
{
        protected int direction;

        private Color shadow;
        private Color darkShadow;
        private Color highlight;

	
	public CustomArrowButton(int direction, Color background, Color shadow, Color darkShadow, Color highlight, boolean isOpaque) 
	{
	    super();
            setRequestFocusEnabled(false);
	    setOpaque(isOpaque);
            setDirection(direction);
	    setBackground(background);
	    setSize(new Dimension(5, 5));
            this.shadow = shadow;
            this.darkShadow = darkShadow;
            this.highlight = highlight;
            
        }
	
        public CustomArrowButton(int direction, Color background, Color shadow, Color darkShadow, Color highlight) 
	{
            this(direction, background, shadow, darkShadow, highlight, true);
        }

        public CustomArrowButton(int direction) 
	{
            this(direction, UIManager.getColor("control"), UIManager.getColor("controlShadow"), UIManager.getColor("controlDkShadow"), UIManager.getColor("controlLtHighlight"));
        }


        public int getDirection() 
	{
            return direction;
        }

        public void setDirection(int direction)
	{
            this.direction = direction;
        }

        public void paint(Graphics g) 
	{
            Color origColor;
            boolean isPressed, isEnabled;
            int w, h, size;

            w = getSize().width;
            h = getSize().height;
            origColor = g.getColor();
            isPressed = getModel().isPressed();
            isEnabled = isEnabled();

	    if(isOpaque())
	    {
		g.setColor(getBackground());
		g.fillRect(0, 0, w, h);
	    }

            // If there's no room to draw arrow, bail
            if(h < 5 || w < 5)      
	    {
                g.setColor(origColor);
                return;
            }
            // Draw the arrow
            size = Math.min((h - 4) / 3, (w - 4) / 3);
            size = Math.max(size, 2);
            paintTriangle(g, (w) / 2, (h - 3) / 2, 3, direction, isEnabled);
	    
            g.setColor(origColor);

        }

        public Dimension getPreferredSize() 
	{
            return new Dimension(5, 5);
        }

        public Dimension getMinimumSize() 
	{
            return new Dimension(1, 1);
        }

        public Dimension getMaximumSize() 
	{
            return new Dimension(5, 5);
        }

        public boolean isFocusTraversable() 
	{
	    return false;
        }

        public void paintTriangle(Graphics g, int x, int y, int size, int direction, boolean isEnabled) 
	{
            Color oldColor = g.getColor();
            int mid, i, j;

            j = 0;
            size = Math.max(size, 2);
            mid = (size / 2) - 1;

            g.translate(x, y);
            if(isEnabled)
                g.setColor(Color.BLACK);
            else
                g.setColor(shadow);

            switch(direction)       
	    {
		case NORTH:
		    for(i = 0; i < size; i++)      
		    {
			g.drawLine(mid-i, i, mid+i, i);
		    }
		    if(!isEnabled)  
		    {
			g.setColor(highlight);
	                g.drawLine(mid-i+2, i, mid+i, i);
		    }
		    break;
		case SOUTH:
		    if(!isEnabled)  
		    {
			g.translate(1, 1);
			g.setColor(highlight);
			for(i = size-1; i >= 0; i--)   
			{
			    g.drawLine(mid-i, j, mid+i, j);
			    j++;
			}
			g.translate(-1, -1);
			g.setColor(shadow);
		    }

		    j = 0;
		    for(i = size-1; i >= 0; i--)   
		    {
			g.drawLine(mid-i, j, mid+i, j);
			j++;
		    }
		    break;
		case WEST:
		    for(i = 0; i < size; i++)      
		    {
		        g.drawLine(i, mid-i, i, mid+i);
		    }
		    if(!isEnabled)  
		    {
		        g.setColor(highlight);
		        g.drawLine(i, mid-i+2, i, mid+i);
		    }
		    break;
		case EAST:
		    if(!isEnabled)  
		    {
		        g.translate(1, 1);
		        g.setColor(highlight);
		        for(i = size-1; i >= 0; i--)   
		        {
		            g.drawLine(j, mid-i, j, mid+i);
		            j++;
		        }
		        g.translate(-1, -1);
		        g.setColor(shadow);
		    }

		    j = 0;
		    for(i = size-1; i >= 0; i--)   
		    {
		        g.drawLine(j, mid-i, j, mid+i);
		        j++;
		    }
		    break;
		}
		g.translate(-x, -y);
		g.setColor(oldColor);
        }

}