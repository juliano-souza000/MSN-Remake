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
import javax.swing.border.AbstractBorder;

public class RoundedBorder  extends AbstractBorder
{
    private Color borderColour;
    private BasicStroke stroke = null;
    private Insets insets = null;
    private int thickness = 1;

    public RoundedBorder(Color colour, int thickness, Insets insets)
    {
	this.thickness = thickness;
        borderColour = colour;
	stroke = new BasicStroke(thickness);
	
        this.insets = insets;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
    {
        super.paintBorder(c, g, x, y, width, height);
        Graphics2D g2d = null;
        if (g instanceof Graphics2D)
        {
	    RoundRectangle2D.Double inner = new RoundRectangle2D.Double(x, y, width - this.thickness, height - this.thickness, 5, 5);
	   
            g2d = (Graphics2D) g;
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	   
	    
	    g2d.setStroke(stroke);
            g2d.setColor(borderColour);
	    g2d.draw(inner);
	    g2d.clip(inner);

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