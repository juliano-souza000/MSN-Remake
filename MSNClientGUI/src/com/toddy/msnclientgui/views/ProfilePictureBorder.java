package com.toddy.msnclientgui.views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.border.AbstractBorder;

public class ProfilePictureBorder  extends AbstractBorder
{
    private Color borderColour;
    private int strokePad;
    private BasicStroke stroke = null;
    private Insets insets = null;
    private Image _Img;

    public ProfilePictureBorder(Color colour, int thickness, int radii, int pointerSize, Image img)
    {
        borderColour = colour;
	stroke = new BasicStroke(thickness);
        strokePad = thickness / 2;
	
	//int pad = radii + 5;
	int pad = 10;
        insets = new Insets(pad, pad, pad, pad);
	_Img = img;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
    {
        super.paintBorder(c, g, x, y, width, height);
        Graphics2D g2d = null;
        if (g instanceof Graphics2D)
        {
	    Path2D.Double p1 = new Path2D.Double();
	    GradientPaint gp = new GradientPaint(0, 0, new Color(217,243,209), 0, height, new Color(98,209,70));
	    RoundRectangle2D.Double inner = new RoundRectangle2D.Double(x + 8, y + 10, width - 17, height - 17, 7, 7);
	   
            g2d = (Graphics2D) g;
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    
	    try
	    {
		g2d.drawImage(ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/status/available-frame.png")), 0, 0, null);
	    }catch(IOException e){}
	    
	    //p1.moveTo(x + 3, y + 5);
	    //p1.moveTo(x + 3, y + 5);
	    //p1.curveTo((double)x + 3, (double)y + 5, (double)x - 3, (double)(height - 6) / 2, (double)x + 3, (double)height - 6);
	    //p1.curveTo((double)x + 3, (double)height, (double)(width+4) / 2, (double)height, (double)width - 6, (double)height - 3);
	    //p1.curveTo((double)width - 3, (double)height - 3, (double)width+2, (double)(height - 4) / 2, (double)width - 5, (double)y + 5);
	    //p1.curveTo((double)width - 3, (double)y + 5, (double)(width+4)/2, y-2, (double)x + 3, (double)y + 5);
	    
	    //g2d.setPaint(gp);
	    //g2d.fill(p1);
	    
	    //g2d.setStroke(stroke);
            //g2d.setColor(borderColour);
	    //g2d.draw(p1);
	    //g2d.draw(inner);
	    g2d.clip(inner);
	    g2d.drawImage(_Img, x + 10, y + 12, null);
	    
	    
        }
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return insets;
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        return getBorderInsets(c);
    }

    @Override
    public boolean isBorderOpaque()
    {
        return true;
    }
}