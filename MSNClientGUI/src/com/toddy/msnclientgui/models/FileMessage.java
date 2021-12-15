package com.toddy.msnclientgui.models;

import java.nio.ByteBuffer;

public class FileMessage extends Message
{
    private ByteBuffer fileBuffer;
    private String fileName;
    
    public FileMessage(String senderName, int senderID, String fileName, ByteBuffer fileBuffer)
    {
	super(senderName, senderID);
	this.fileName = fileName;
	this.fileBuffer = fileBuffer;
    }

    public ByteBuffer getFileBuffer()
    {
	return fileBuffer;
    }

    public String getFileName()
    {
	return fileName;
    }

    public void setFileName(String fileName)
    {
	this.fileName = fileName;
    }

    public void setFileBuffer(ByteBuffer fileBuffer)
    {
	this.fileBuffer = fileBuffer;
    }
    
}