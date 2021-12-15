package com.toddy.msnclientgui.utils;

import com.toddy.msnclientgui.Main;
import java.awt.Color;
import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class StatusIconUtils 
{
    
    public static Icon getAvailable1() throws IOException 
    {
	return new ImageIcon(ImageIO.read(Main.class.getClassLoader().getResource("com/toddy/msnclientgui/resources/images/status/available-1.png")));
    }

    public static Icon getAvailable2() throws IOException 
    {
	return new ImageIcon(ImageIO.read(Main.class.getClassLoader().getResource("com/toddy/msnclientgui/resources/images/status/available-2.png")));
    }

    public static Icon getAway1() throws IOException 
    {
	return new ImageIcon(ImageIO.read(Main.class.getClassLoader().getResource("com/toddy/msnclientgui/resources/images/status/away-1.png")));
    }

    public static Icon getAway2() throws IOException 
    {
	return new ImageIcon(ImageIO.read(Main.class.getClassLoader().getResource("com/toddy/msnclientgui/resources/images/status/away-2.png")));
    }

    public static Icon getBusy1() throws IOException 
    {
	return new ImageIcon(ImageIO.read(Main.class.getClassLoader().getResource("com/toddy/msnclientgui/resources/images/status/busy-1.png")));
    }

    public static Icon getBusy2() throws IOException 
    {
	return new ImageIcon(ImageIO.read(Main.class.getClassLoader().getResource("com/toddy/msnclientgui/resources/images/status/busy-2.png")));
    }
    
    public static Icon getOffline1() throws IOException 
    {
	return  new ImageIcon(ImageIO.read(Main.class.getClassLoader().getResource("com/toddy/msnclientgui/resources/images/status/offline-1.png")));
    }

    public static Icon getOffline2() throws IOException 
    {
	return  new ImageIcon(ImageIO.read(Main.class.getClassLoader().getResource("com/toddy/msnclientgui/resources/images/status/offline-2.png")));
    }
}