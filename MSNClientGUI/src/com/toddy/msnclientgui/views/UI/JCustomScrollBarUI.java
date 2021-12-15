package com.toddy.msnclientgui.views.UI;

import com.toddy.msnclientgui.views.JCustomScrollBarArrow;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;
import static sun.swing.SwingUtilities2.drawHLine;
import static sun.swing.SwingUtilities2.drawRect;
import static sun.swing.SwingUtilities2.drawVLine;

public class JCustomScrollBarUI extends BasicScrollBarUI implements MouseListener
{
    private boolean isMouseOver;
    
    public JCustomScrollBarUI()
    {
    }
    
    @Override
    protected JButton createDecreaseButton(int orientation)
    {
	return new JCustomScrollBarArrow(orientation, new Color(252,252,252),new Color(252,252,252), Color.WHITE);
    }

    @Override
    protected JButton createIncreaseButton(int orientation)
    {
	return new JCustomScrollBarArrow(orientation, new Color(252,252,252),new Color(252,252,252), Color.WHITE);
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds)
    {
    	Graphics2D g2 = (Graphics2D)g;
    	g2.setPaint(new GradientPaint(0, 15, new Color(231,231,231, 0), 0, c.getHeight()/2-15, new Color(130,130,130, 255)));
    	g2.drawLine(0, 15, 0, c.getHeight()/2);
    	g2.setPaint(new GradientPaint(0, c.getHeight()/2-15, new Color(130,130,130, 255), 0, c.getHeight()-11, new Color(130,130,130, 0)));
    	g2.drawLine(0, c.getHeight()/2, 0, c.getHeight()-16);
    }

    @Override
    protected void paintThumb(Graphics g2, JComponent c, Rectangle thumbBounds)
    {
	Graphics2D g = (Graphics2D)g2;
	//super.paintThumb(g, c, thumbBounds); //To change body of generated methods, choose Tools | Templates.
	if(isMouseOver)
	{
	    g.setColor(new Color(215,237,250));
	    g.fillRoundRect(thumbBounds.x+1, thumbBounds.y+1, thumbBounds.width-3, thumbBounds.height-3, 2, 2);
	}
	
	if(!isMouseOver)
	    g.setColor(new Color(172,170,171));
	else
	    g.setColor(new Color(66,129,175));
	g.drawRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width-2, thumbBounds.height-2, 2, 2);
	
	
	c.addMouseListener(this);
	try
	{
	    Image img;
	    if(!isMouseOver)
		img = ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/views/scrollbar_detail.png"));
	    else
		img = ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/views/scrollbar_detail_hovered.png"));
	    g.drawImage(img, thumbBounds.x+3, thumbBounds.y-3+(thumbBounds.height-1)/2, null);
	    g.drawImage(img, thumbBounds.x+3, thumbBounds.y+(thumbBounds.height-1)/2, null);
	    g.drawImage(img, thumbBounds.x+3, thumbBounds.y+3+(thumbBounds.height-1)/2, null);
	} 
	catch (IOException ex)
	{
	    Logger.getLogger(JCustomScrollBarUI.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
	//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
	//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
	//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
	isMouseOver = true;
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
	isMouseOver = false;
    }

    
    
    
    
}