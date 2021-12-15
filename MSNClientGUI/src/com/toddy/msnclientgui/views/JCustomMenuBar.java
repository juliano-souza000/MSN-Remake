package com.toddy.msnclientgui.views;

import com.toddy.msnclientgui.Main;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JMenuBar;
import javax.swing.text.html.HTML;

public class JCustomMenuBar extends JMenuBar
{
    
    @Override
    public void paint(Graphics g)
    {
	Image img = null;
	try
	{
	    img = ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/views/menu-background.png"));
	} catch (IOException ex)
	{
	    Logger.getLogger(JCustomMenuBar.class.getName()).log(Level.SEVERE, null, ex);
	}

	for (int x = 0; x < getWidth(); x += 57)
	{
	    g.drawImage(img, x, 0, null);
	}
	g.setColor(new Color(197,216,228));
	g.drawLine(0, 0, 0, getHeight()-1);
	g.drawLine(0, 0, getWidth()-1, 0);
	g.drawLine(getWidth()-1, 0, getWidth()-1, getHeight()-1);
	super.paint(g); //To change body of generated methods, choose Tools | Templates.
    }
    
}