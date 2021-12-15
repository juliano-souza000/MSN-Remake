package com.toddy.msnclientgui.views.UI;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;


public class JListItemCellRenderer implements ListCellRenderer
{
    private final JLabel jlblCell = new JLabel();
    private boolean isOpaque = true;
    
    public JListItemCellRenderer(){}
    
    public JListItemCellRenderer(boolean isOpaque)
    {
	this.isOpaque = isOpaque;
    }
    
    public void setIsOpaque(boolean isOpaque)
    {
	this.isOpaque = isOpaque;
    }
    

    @Override
    public Component getListCellRendererComponent(JList jList, Object value, int index, boolean isSelected, boolean cellHasFocus) 
    {
        jlblCell.setOpaque(isOpaque);
	jlblCell.setFont(jList.getFont());
	jlblCell.setText(value.toString());
	
        if (isSelected) 
	{
	    
	    Border outsideLineBorder = BorderFactory.createLineBorder(new Color(51, 153, 255), 1);
	    Border insideLineBorder = BorderFactory.createLineBorder(new Color(204, 102, 0), 1);
	    Border compound = BorderFactory.createCompoundBorder(outsideLineBorder, insideLineBorder);
	    jlblCell.setForeground(jList.getSelectionForeground());
	    jlblCell.setBackground(jList.getSelectionBackground());
            jlblCell.setBorder(compound);
        } 
	else
	{
            jlblCell.setForeground(jList.getForeground());
	    jlblCell.setBackground(jList.getBackground());
	    jlblCell.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        }

        return jlblCell;
    }
}