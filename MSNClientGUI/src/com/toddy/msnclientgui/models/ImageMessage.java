package com.toddy.msnclientgui.models;

import com.toddy.msnclientgui.enums.MessageType;
import java.awt.Image;
import java.nio.ByteBuffer;

public class ImageMessage extends Message
{
    private Image image;
    
    public ImageMessage(String senderName, int senderID, Image image)
    {
	super(senderName, senderID);
	this.image = image;
    }

    public Image getImage()
    {
	return image;
    }

    public void setImage(Image image)
    {
	this.image = image;
    }
    
}