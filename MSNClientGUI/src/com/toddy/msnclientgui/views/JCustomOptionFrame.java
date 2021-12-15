package com.toddy.msnclientgui.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicGraphicsUtils;

public class JCustomOptionFrame extends JPanel
{
    
    private Color borderColor;
    private String borderText = "Teste";
    
    public void setBorderColor(Color borderColor)
    {
	this.borderColor = borderColor;
    }
    
    public void setBorderText(String borderText)
    {
	this.borderText = borderText;
    }
    
    public String getBorderText()
    {
	return this.borderText;
    }

    @Override
    protected void paintBorder(Graphics g)
    {
	//super.paintBorder(g); //To change body of generated methods, choose Tools | Templates.
	g.setColor(borderColor);
	g.drawRoundRect(0, 5, getWidth()-1, getHeight()-6, 7, 7);
	g.setFont(getFont());
	g.setColor(Color.black);
	g.drawString(getBorderText(), 10, 10);
    }
}