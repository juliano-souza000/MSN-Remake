package com.toddy.msnclientgui.views.UI;

import com.toddy.msnclientgui.enums.Status;
import com.toddy.msnclientgui.models.User;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class UserListRenderer extends JLabel implements ListCellRenderer<User>
{ 

    public UserListRenderer() { }
    
    @Override
    public Component getListCellRendererComponent(JList<? extends User> list, User user, int index, boolean isSelected, boolean cellHasFocus) 
    {
	
	Icon icon = null;
	try
	{ 
	    switch(user.getUserStatus())
	    {
		case Available:
		    icon = com.toddy.msnclientgui.utils.StatusIconUtils.getAvailable2();
		    break;
		case Away:
		    icon = com.toddy.msnclientgui.utils.StatusIconUtils.getAway2();
		    break;
		case Busy:
		    icon = com.toddy.msnclientgui.utils.StatusIconUtils.getBusy2();
		    break;
		default:
		    icon = com.toddy.msnclientgui.utils.StatusIconUtils.getOffline2();
		    break;
		    
	    }
	} 
	catch (IOException ex)
	{
	    Logger.getLogger(UserListRenderer.class.getName()).log(Level.SEVERE, null, ex);
	}
	setOpaque(true);
	if(isSelected)
	{
	    setBackground(new Color(218, 221, 226));
	}
	else
	{
	    setBackground(Color.white);
	}
	    
	    setMaximumSize(new Dimension(235,21));
	    setPreferredSize(new Dimension(235,21));
	    setMinimumSize(new Dimension(235,21));
	    setSize(new Dimension(235,21));
	    repaint();
	    setBorder(new EmptyBorder(0, 5, 0, 0));
	    setText(getFormatedDisplayText(user));
	    setFont(new Font("Arial", Font.PLAIN, 12));
	    setIcon(icon);
	    return this;
	
    } 
    
    private String getFormatedDisplayText(User user)
    {
	String lastText = "("+user.getUserStatus().name()+") - "+user.getUserAbout();
	int firstWidth = getFontMetrics(getFont()).stringWidth(user.getUserName());
	int lastWidth = getFontMetrics(getFont()).stringWidth(lastText);
	
	while((firstWidth+lastWidth) > getPreferredSize().getWidth())
	{
	    if(lastText.length()-4 >= 0)
		lastText = lastText.substring(0, lastText.length()-4)+"...";
	    lastWidth = getFontMetrics(getFont()).stringWidth(lastText);
	} 
	return "<html><nobr>"+ user.getUserName()+"</nobr><nobr style='color: #B8B8B8;'> "+lastText+"</nobr></html>";
    }
}