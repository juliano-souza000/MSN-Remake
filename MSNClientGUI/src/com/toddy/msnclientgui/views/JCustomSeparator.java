package com.toddy.msnclientgui.views;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JSeparator;

public class JCustomSeparator extends JSeparator
{

    @Override
    public void paint(Graphics g)
    {
	Graphics2D g2 = (Graphics2D)g;
	g2.setPaint(new GradientPaint(new Point(0, 0), new Color(181, 207, 218, 125), new Point(getWidth()/2, 0), new Color(181, 207, 218, 255)));
	g2.drawLine(0, 0, getWidth()/2, 0);
	g2.setPaint(new GradientPaint(new Point(getWidth()/2, 0), new Color(181, 207, 218, 255) , new Point(getWidth(), 0),new Color(181, 207, 218, 125)));
	g2.drawLine(getWidth()/2, 0, getWidth(), 0);
	
	g2.setPaint(new GradientPaint(new Point(0, 0), new Color(199, 220, 230, 50), new Point(getWidth()/2, 0), new Color(199, 220, 230, 255)));
	g2.drawLine(0, 1, getWidth()/2, 1);
	g2.setPaint(new GradientPaint(new Point(getWidth()/2, 0), new Color(199, 220, 230, 255) , new Point(getWidth(), 0),new Color(199, 220, 230, 85)));
	g2.drawLine(getWidth()/2, 1, getWidth(), 1);
	//super.paint(g);
    }
    
}