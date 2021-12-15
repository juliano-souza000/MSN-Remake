package com.toddy.msnclientgui.views.UI;

import com.toddy.msnclientgui.Main;
import com.toddy.msnclientgui.models.FileMessage;
import com.toddy.msnclientgui.models.ImageMessage;
import com.toddy.msnclientgui.models.Message;
import com.toddy.msnclientgui.models.TextMessage;
import com.toddy.msnclientgui.models.User;
import com.toddy.msnclientgui.utils.Utils;
import com.toddy.msnclientgui.views.JImage;
import com.toddy.msnclientgui.views.JShadowEffect;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.BevelBorder;

public class MessagesRenderer extends JPanel implements ListCellRenderer<Message>
{
    private int hoveredItemIndex = -1;
    private Rectangle bounds;
    private Point mousePos;
    public MessagesRenderer(JList list, int fromID, int toID)
    {
	list.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
	{
	    @Override
	    public void mouseMoved(MouseEvent e)
	    {
	        int index = list.locationToIndex(e.getPoint());
		mousePos = e.getPoint();
	        if(index != hoveredItemIndex)
	        {
		    hoveredItemIndex = index;
		    bounds = list.getCellBounds(hoveredItemIndex, hoveredItemIndex);
		}
		list.repaint();
	    }
		
	});
	
	list.addMouseListener(new MouseAdapter()
	{
	    @Override
	    public void mouseExited(MouseEvent e)
	    {
		super.mouseExited(e); //To change body of generated methods, choose Tools | Templates.
		mousePos = e.getPoint();
		hoveredItemIndex = -1;
		list.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		list.repaint();
	    }

	    @Override
	    public void mouseClicked(MouseEvent e)
	    {
		super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
		int index = list.locationToIndex(e.getPoint());
		if(index >= 0)
		{
		    bounds = list.getCellBounds(hoveredItemIndex, hoveredItemIndex);
		    if(list.getSelectedValue() instanceof ImageMessage)
		    {
			if((mousePos.x >=15 && mousePos.x <= 250) && (mousePos.y - bounds.y > 25 && mousePos.y - bounds.y  <= bounds.height))
			{
			    try 
			    {
				File file = File.createTempFile("temp", ".png");
				System.out.println(file.getAbsolutePath());
				ImageIO.write(Utils.toBufferedImage(((ImageMessage)list.getSelectedValue()).getImage()), "png", file);
				file.deleteOnExit();
				Desktop.getDesktop().open(file);
			    } 
			    catch (IOException ex) 
			    {
				Logger.getLogger(MessagesRenderer.class.getName()).log(Level.SEVERE, null, ex);
			    }
			}
		    }
		    else if(list.getSelectedValue() instanceof FileMessage)
		    {
			AffineTransform affinetransform = new AffineTransform();     
			FontRenderContext frc = new FontRenderContext(affinetransform, true, true);  
			Font font = new java.awt.Font("Arial", Font.PLAIN, 12);
			int textwidth = (int) font.getStringBounds("Click to save file: "+((FileMessage)list.getSelectedValue()).getFileName(), frc).getWidth();
			if((mousePos.x >=15 && mousePos.x <= textwidth+15) && (mousePos.y - bounds.y > 20 && mousePos.y - bounds.y  <= bounds.height))
			{   
			    new File(Main.getConfig().getReceivedFileFolder()+"/"+fromID+"."+toID).mkdir();
			    try
			    {
				FileOutputStream fos = new FileOutputStream(Main.getConfig().getReceivedFileFolder()+"/"+fromID+"."+toID+"/"+((FileMessage)list.getSelectedValue()).getFileName());
				fos.write(((FileMessage)list.getSelectedValue()).getFileBuffer().array());
				fos.close();
				Desktop.getDesktop().open(new File(Main.getConfig().getReceivedFileFolder()+"/"+fromID+"."+toID+"/"+((FileMessage)list.getSelectedValue()).getFileName()));
			    } 
			    catch (FileNotFoundException ex) 
			    {
				Logger.getLogger(MessagesRenderer.class.getName()).log(Level.SEVERE, null, ex);
			    }
			    catch (IOException ex)
			    {
				Logger.getLogger(MessagesRenderer.class.getName()).log(Level.SEVERE, null, ex);
			    }
			    
			}
		    }
		}
	    }
	    
	});
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Message> list, Message value, int index, boolean isSelected, boolean cellHasFocus)
    {
	JLabel whoSaid = new JLabel();
	whoSaid.setForeground(new Color(150, 150, 150));
	whoSaid.setFont(new java.awt.Font("Arial", Font.PLAIN, 12));
	setBackground(new Color(252, 252, 252));
	removeAll();
	setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
	//message.setPreferredSize(new Dimension(list.getSize().width, 21));
	//int width = (int) (list.getSize().width-list.getSize().width*0.26f);
	//whoSaid.setPreferredSize(new Dimension(width, 150));
	if (value instanceof TextMessage)
	{
	    JLabel message = new JLabel(((TextMessage) value).getMessage());
	    whoSaid.setText(value.getSenderName() + " said:");
	    message.setForeground(Color.BLACK);
	    message.setFont(new java.awt.Font("Arial", Font.PLAIN, 12));
	    add(message, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 20, -1, -1));
	} 
	else if (value instanceof FileMessage)
	{
	    JLabel fileDownload = new JLabel();
	    AffineTransform affinetransform = new AffineTransform();     
	    FontRenderContext frc = new FontRenderContext(affinetransform, true, true);  
	    Font font = new java.awt.Font("Arial", Font.PLAIN, 12);

	    whoSaid.setText(value.getSenderName() + " sent:");
	    fileDownload.setFont(font);
	    String text = "Click to save file: "+((FileMessage) value).getFileName();
	    if(hoveredItemIndex != index)
	    {
		//list.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		fileDownload.setText("<html><span style='color:#0066CC;'>"+text+"</span></html>");
	    }
	    else
	    {
		int textwidth = (int) font.getStringBounds(text, frc).getWidth();
		if((mousePos.x >=15 && mousePos.x <= textwidth+15) && (mousePos.y - bounds.y > 20 && mousePos.y - bounds.y  <= bounds.height))
		{
		    list.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		    fileDownload.setText("<html><u style='color:#0066CC;'>"+text+"</u></html>");
		}
		else
		{
		    list.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		    fileDownload.setText("<html><span style='color:#0066CC;'>"+text+"</span></html>");
		}    
	    }
	    add(fileDownload, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 20, -1, -1));
	    
	}
	else if(value instanceof ImageMessage)
	{
	    JImage image = new JImage();
	    image.setPreferredSize(new Dimension(250,250));
	    image.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	    image.setImage(((ImageMessage) value).getImage());//.getScaledInstance(100, 100, Image.SCALE_SMOOTH)
	    whoSaid.setText(value.getSenderName() + " sent:");
	    if(hoveredItemIndex == index)
	    {
		if((mousePos.x >=15 && mousePos.x <= 250) && (mousePos.y - bounds.y > 25 && mousePos.y - bounds.y  <= bounds.height))
		{
		    JShadowEffect shadow = new JShadowEffect();
		    shadow.setPreferredSize(new Dimension(250, 250));
		    list.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		    add(shadow, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 25, -1, -1));
		}
		else
		{
		    list.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		}
	    }
	    add(image, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 25, -1, -1));
	}
	add(whoSaid, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 7, -1, -1));
	//message.setMaximumSize(new Dimension(width, Integer.MAX_VALUE));
	return this;
    }
}
