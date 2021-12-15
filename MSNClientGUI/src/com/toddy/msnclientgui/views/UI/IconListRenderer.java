package com.toddy.msnclientgui.views.UI;

import java.awt.Component;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.EmptyBorder;

public class IconListRenderer extends DefaultListCellRenderer
{ 
    private static final long serialVersionUID = 1L;
    private Map<Object, Icon> icons1 = null; 
    private Map<Object, Icon> icons2 = null; 

    public IconListRenderer(Map<Object, Icon> icons1, Map<Object, Icon> icons2)
    { 
        this.icons1 = icons1; 
	this.icons2 = icons2; 
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    { 
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); 
	Icon icon;
        // Get icon to use for the list item value 
	if(index == -1)
	{
	    icon = icons1.get(value); 
	}
	else
	{
	    icon = icons2.get(value); 
	}
	
	label.setBorder(new EmptyBorder(0, 5, 0, 0));
        // Set icon to display for value 
	label.setText("   " + label.getText());
        label.setIcon(icon);
        return label; 
    } 
}