package com.toddy.msnclientgui.views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JPanel;

public class JShadowEffect extends JPanel
{

    @Override
    public void paint(Graphics g)
    {
	Graphics2D g2 = (Graphics2D)g;
	setOpaque(false);
	Color color1 = new Color(33, 33, 33, 51);
	Color color2 = new Color(255, 255, 255, 0);
	g2.setPaint(new GradientPaint(0, 0, color1, getWidth()/2, getHeight()/2, color2));
	g2.fillRect(0, 0, getWidth()-1, getHeight()-1);
	g2.setPaint(new GradientPaint(getWidth(), 0, color1, getWidth()/3, getHeight()/3, color2));
	g2.fillRect(0, 0, getWidth()-1, getHeight()-1);
	g2.setPaint(new GradientPaint(0, getHeight(), color1, getWidth()/3, getHeight()/3, color2));
	g2.fillRect(0, 0, getWidth()-1, getHeight()-1);
	g2.setPaint(new GradientPaint(getWidth(), getHeight(), color1, getWidth()/3, getHeight()/3, color2));
	g2.fillRect(0, 0, getWidth()-1, getHeight()-1);
    }
    
}