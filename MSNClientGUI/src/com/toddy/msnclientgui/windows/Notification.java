/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toddy.msnclientgui.windows;

import com.toddy.msnclientgui.Main;
import com.toddy.msnclientgui.background.ListenForAboutUpdate;
import com.toddy.msnclientgui.background.ListenForMessages;
import com.toddy.msnclientgui.background.ListenForStatusUpdate;
import com.toddy.msnclientgui.background.ListenNewMembers;
import com.toddy.msnclientgui.enums.MessageType;
import com.toddy.msnclientgui.enums.ResponseStatus;
import com.toddy.msnclientgui.enums.Status;
import com.toddy.msnclientgui.models.User;
import com.toddy.msnclientgui.network.NetworkUtils;
import com.toddy.msnclientgui.utils.Utils;
import com.toddy.msnclientgui.views.JImage;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Timer;
import org.json.JSONObject;
import java.io.File;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackListener;

public class Notification extends javax.swing.JFrame
{

    private JSONObject userFromDetails;
    private Status status;
    private JSONObject message;
    
    public Notification(JSONObject message)
    {
	Notification not = this;
	this.message = message;
	try
	{
	    JSONObject response = NetworkUtils.getProfile(message.getInt("From"));
	    if(response.getEnum(ResponseStatus.class, "Status") == ResponseStatus.OK)
	    {
		this.userFromDetails = response.getJSONObject("Profile");
		this.status = userFromDetails.getEnum(Status.class, "Status");
	    }
	} 
	catch (IOException ex)
	{
	    Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
	}

	initComponents();
	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        
        Rectangle rect = ge.getMaximumWindowBounds();
        int x = (int) rect.getMaxX() - getWidth() - 20;
        int y = (int) rect.getMaxY()- getHeight() - 20;
        setLocation(x, y);
	Timer timer = new Timer(5000, new ActionListener()
	{
	    @Override
	    public void actionPerformed(ActionEvent e)
	    {
		dispatchEvent(new WindowEvent(not, WindowEvent.WINDOW_CLOSING));
	    }
	});
        timer.start();
	try
	{
	    setIconImage(Utils.getAppIcon());
	} 
	catch (IOException ex)
	{
	    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    public Notification(String username)
    {
	Notification not = this;
	initComponents2(username);
	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        
        Rectangle rect = ge.getMaximumWindowBounds();
        int x = (int) rect.getMaxX() - getWidth() - 20;
        int y = (int) rect.getMaxY()- getHeight() - 20;
        setLocation(x, y);
	Timer timer = new Timer(5000, new ActionListener()
	{
	    @Override
	    public void actionPerformed(ActionEvent e)
	    {
		dispatchEvent(new WindowEvent(not, WindowEvent.WINDOW_CLOSING));
	    }
	});
        timer.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        Outer = new com.toddy.msnclientgui.views.JImage();
        UserProfilePicture = new com.toddy.msnclientgui.views.JImage();
        AuthorLabel = new javax.swing.JLabel();
        MessageLabel = new javax.swing.JLabel();
        CloseButton = new com.toddy.msnclientgui.views.JImage();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(250, 100));
        setMinimumSize(new java.awt.Dimension(250, 100));
        setUndecorated(true);
        setResizable(false);
        setShape(new RoundRectangle2D.Double(0, 0, 250, 100, 5, 5));
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowOpened(java.awt.event.WindowEvent evt)
            {
                formWindowOpened(evt);
            }
        });

        try
        {
            BufferedImage backgroundToCrop = ImageIO.read(Main.class.getClassLoader().getResource("com/toddy/msnclientgui/resources/images/views/notification_popup_background.png"));
            Outer = new com.toddy.msnclientgui.views.JImage(backgroundToCrop);
            //UserProfilePicture.setIcon(new ImageIcon(userProfilePicResized));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Outer.setBackground(new java.awt.Color(252, 252, 252));
        Outer.setForeground(new java.awt.Color(255, 255, 255));
        Outer.setToolTipText("");
        Outer.setMaximumSize(new java.awt.Dimension(250, 100));
        Outer.setMinimumSize(new java.awt.Dimension(250, 100));
        Outer.setPreferredSize(new java.awt.Dimension(250, 100));

        try
        {
            byte[] arr = Base64.getDecoder().decode(userFromDetails.getString("ProfileIMG"));
            BufferedImage userProfilePic = ImageIO.read(new ByteArrayInputStream(arr));
            Image userProfilePicResized = userProfilePic.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
            UserProfilePicture = new JImage(userProfilePicResized, true, this.status, 5);
            //UserProfilePicture.setIcon(new ImageIcon(userProfilePicResized));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        UserProfilePicture.setBackground(new java.awt.Color(255, 255, 255));
        UserProfilePicture.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        UserProfilePicture.setForeground(new java.awt.Color(0, 0, 0));
        UserProfilePicture.setToolTipText("");
        UserProfilePicture.setMaximumSize(new java.awt.Dimension(55, 55));
        UserProfilePicture.setMinimumSize(new java.awt.Dimension(55, 55));
        UserProfilePicture.setName(""); // NOI18N
        UserProfilePicture.setPreferredSize(new java.awt.Dimension(55, 55));
        UserProfilePicture.setBackground(new java.awt.Color(0, 0, 0));
        UserProfilePicture.setForeground(new java.awt.Color(0, 0, 0));

        UserProfilePicture.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        UserProfilePicture.setOpaque(false);
        UserProfilePicture.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        AuthorLabel.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        AuthorLabel.setForeground(new java.awt.Color(0, 0, 0));
        AuthorLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        AuthorLabel.setText("sender says:");
        AuthorLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        AuthorLabel.setMaximumSize(new java.awt.Dimension(150, 17));
        AuthorLabel.setMinimumSize(new java.awt.Dimension(150, 17));
        AuthorLabel.setPreferredSize(new java.awt.Dimension(150, 17));
        if(message.getEnum(MessageType.class, "Type") == MessageType.TEXT)
        {
            AuthorLabel.setText(userFromDetails.getString("Username")+" says:");
        }
        else if(message.getEnum(MessageType.class, "Type") == MessageType.IMAGE)
        {
            AuthorLabel.setText(userFromDetails.getString("Username")+" sent an image!");
        }
        else
        {
            AuthorLabel.setText(userFromDetails.getString("Username")+" sent an file!");
        }

        MessageLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        MessageLabel.setForeground(new java.awt.Color(0, 0, 0));
        MessageLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MessageLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        MessageLabel.setMaximumSize(new java.awt.Dimension(150, 34));
        MessageLabel.setMinimumSize(new java.awt.Dimension(150, 34));
        MessageLabel.setPreferredSize(new java.awt.Dimension(150, 34));
        if(message.getEnum(MessageType.class, "Type") == MessageType.TEXT)
        {
            MessageLabel.setText(message.getJSONObject("Content").getString("Data"));
        }

        try
        {
            BufferedImage backgroundToCrop = ImageIO.read(Main.class.getClassLoader().getResource("com/toddy/msnclientgui/resources/images/views/close-btn.png"));
            CloseButton = new com.toddy.msnclientgui.views.JImage(backgroundToCrop);
            //UserProfilePicture.setIcon(new ImageIcon(userProfilePicResized));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        CloseButton.setMaximumSize(new java.awt.Dimension(16, 16));
        CloseButton.setMinimumSize(new java.awt.Dimension(16, 16));
        CloseButton.setOpaque(false);
        CloseButton.setPreferredSize(new java.awt.Dimension(16, 16));
        CloseButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                CloseButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout CloseButtonLayout = new javax.swing.GroupLayout(CloseButton);
        CloseButton.setLayout(CloseButtonLayout);
        CloseButtonLayout.setHorizontalGroup(
            CloseButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );
        CloseButtonLayout.setVerticalGroup(
            CloseButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout OuterLayout = new javax.swing.GroupLayout(Outer);
        Outer.setLayout(OuterLayout);
        OuterLayout.setHorizontalGroup(
            OuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OuterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(UserProfilePicture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(OuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(OuterLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addGroup(OuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(MessageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(AuthorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(31, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OuterLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))))
        );
        OuterLayout.setVerticalGroup(
            OuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OuterLayout.createSequentialGroup()
                .addGroup(OuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(OuterLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(CloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(AuthorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MessageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(OuterLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(UserProfilePicture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Outer.shouldRepeatBackground(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Outer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(Outer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initComponents2(String username)
    {
        Outer = new com.toddy.msnclientgui.views.JImage();
        AuthorLabel = new javax.swing.JLabel();
        MessageLabel = new javax.swing.JLabel();
        CloseButton = new com.toddy.msnclientgui.views.JImage();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(250, 100));
        setMinimumSize(new java.awt.Dimension(250, 100));
        setUndecorated(true);
        setResizable(false);
        setShape(new RoundRectangle2D.Double(0, 0, 250, 100, 5, 5));
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowOpened(java.awt.event.WindowEvent evt)
            {
                formWindowOpened(evt);
            }
        });

        try
        {
            BufferedImage backgroundToCrop = ImageIO.read(Main.class.getClassLoader().getResource("com/toddy/msnclientgui/resources/images/views/notification_popup_background.png"));
            Outer = new com.toddy.msnclientgui.views.JImage(backgroundToCrop);
            //UserProfilePicture.setIcon(new ImageIcon(userProfilePicResized));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Outer.setBackground(new java.awt.Color(252, 252, 252));
        Outer.setForeground(new java.awt.Color(255, 255, 255));
        Outer.setToolTipText("");
        Outer.setMaximumSize(new java.awt.Dimension(250, 100));
        Outer.setMinimumSize(new java.awt.Dimension(250, 100));
        Outer.setPreferredSize(new java.awt.Dimension(250, 100));

        AuthorLabel.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        AuthorLabel.setForeground(new java.awt.Color(0, 0, 0));
        AuthorLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        AuthorLabel.setText("Upload info");
        AuthorLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        AuthorLabel.setMaximumSize(new java.awt.Dimension(150, 17));
        AuthorLabel.setMinimumSize(new java.awt.Dimension(150, 17));
        AuthorLabel.setPreferredSize(new java.awt.Dimension(150, 17));

        MessageLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        MessageLabel.setForeground(new java.awt.Color(0, 0, 0));
        MessageLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MessageLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        MessageLabel.setMaximumSize(new java.awt.Dimension(150, 34));
        MessageLabel.setMinimumSize(new java.awt.Dimension(150, 34));
        MessageLabel.setPreferredSize(new java.awt.Dimension(150, 34));
        MessageLabel.setText("The file was sent successfully to "+username);

        try
        {
            BufferedImage backgroundToCrop = ImageIO.read(Main.class.getClassLoader().getResource("com/toddy/msnclientgui/resources/images/views/close-btn.png"));
            CloseButton = new com.toddy.msnclientgui.views.JImage(backgroundToCrop);
            //UserProfilePicture.setIcon(new ImageIcon(userProfilePicResized));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        CloseButton.setMaximumSize(new java.awt.Dimension(16, 16));
        CloseButton.setMinimumSize(new java.awt.Dimension(16, 16));
        CloseButton.setOpaque(false);
        CloseButton.setPreferredSize(new java.awt.Dimension(16, 16));
        CloseButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                CloseButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout CloseButtonLayout = new javax.swing.GroupLayout(CloseButton);
        CloseButton.setLayout(CloseButtonLayout);
        CloseButtonLayout.setHorizontalGroup(
            CloseButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );
        CloseButtonLayout.setVerticalGroup(
            CloseButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout OuterLayout = new javax.swing.GroupLayout(Outer);
        Outer.setLayout(OuterLayout);
        OuterLayout.setHorizontalGroup(
            OuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OuterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(OuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(OuterLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addGroup(OuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(MessageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(AuthorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(31, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OuterLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))))
        );
        OuterLayout.setVerticalGroup(
            OuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OuterLayout.createSequentialGroup()
                .addGroup(OuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(OuterLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(CloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(AuthorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MessageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Outer.shouldRepeatBackground(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Outer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(Outer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }
    
    private void formWindowOpened(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowOpened
    {//GEN-HEADEREND:event_formWindowOpened

	try
	{
	    AdvancedPlayer player = new AdvancedPlayer(Main.class.getClassLoader().getResourceAsStream("com/toddy/msnclientgui/resources/sounds/vimdone.mp3"));
	    player.play();
	} 
	catch (JavaLayerException ex)
	{
	    Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
	} 
    }//GEN-LAST:event_formWindowOpened

    private void CloseButtonMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_CloseButtonMouseClicked
    {//GEN-HEADEREND:event_CloseButtonMouseClicked
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_CloseButtonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AuthorLabel;
    private com.toddy.msnclientgui.views.JImage CloseButton;
    private javax.swing.JLabel MessageLabel;
    private com.toddy.msnclientgui.views.JImage Outer;
    private com.toddy.msnclientgui.views.JImage UserProfilePicture;
    // End of variables declaration//GEN-END:variables
}
