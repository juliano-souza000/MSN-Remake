package com.toddy.msnclientgui.views.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import sun.swing.DefaultLookup;

public class JCustomComboBoxUI extends BasicComboBoxUI
{
    private boolean isOpaque;
    
    public JCustomComboBoxUI(){}
    
    public JCustomComboBoxUI(boolean isOpaque)
    {
	this.isOpaque = isOpaque;
    }

    @Override
    public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus)
    {
	if(isOpaque)
	{
	    Color t = g.getColor();
	    if (comboBox.isEnabled() )
		g.setColor(DefaultLookup.getColor(comboBox, this, "ComboBox.background", null));
            else
		g.setColor(DefaultLookup.getColor(comboBox, this, "ComboBox.disabledBackground", null));
	    g.fillRect(bounds.x,bounds.y,bounds.width,bounds.height);
	    g.setColor(t);
	}
	else
	{
	    g.setColor(new Color(0,0,0,0));
	    g.fillRect(bounds.x,bounds.y,bounds.width,bounds.height);
	}
    }
    
    @Override
    protected JButton createArrowButton() 
    {
	CustomArrowButton btn = new CustomArrowButton(CustomArrowButton.SOUTH, Color.WHITE, Color.WHITE, UIManager.getColor("ComboBox.buttonDarkShadow"), Color.WHITE, isOpaque);
	return btn;
    }
}