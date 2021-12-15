/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toddy.msnclientgui.windows;

import com.toddy.msnclientgui.Main;
import com.toddy.msnclientgui.enums.AlertType;
import com.toddy.msnclientgui.utils.Utils;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class AlertDialog extends javax.swing.JFrame
{
    private JFrame parentWindow;
    private AlertType alertType;
    private String title;
    private String description;
    
    public AlertDialog(JFrame parentWindow, AlertType alertType, String title, String description)
    {
	this.parentWindow = parentWindow;
	this.alertType = alertType;
	this.title = title;
	this.description = "<html>"+description+"</html>";
	this.addWindowListener(new WindowAdapter()
	{
	    @Override
	    public void windowClosing(WindowEvent event)
	    {
		parentWindow.setEnabled(true);
	    }
	});
	initComponents();
	this.setLocationRelativeTo(parentWindow);
	try
	{
	    setIconImage(Utils.getAppIcon());
	} 
	catch (IOException ex)
	{
	    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        Outter = new com.toddy.msnclientgui.views.JImage();
        TextLabel = new javax.swing.JLabel();
        DescriptionLabel = new javax.swing.JLabel();
        CloseButton = new com.toddy.msnclientgui.views.JCustomButton();
        Icon = new com.toddy.msnclientgui.views.JImage();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Live Messenger");
        setMaximumSize(new java.awt.Dimension(358, 149));
        setMinimumSize(new java.awt.Dimension(358, 149));
        setPreferredSize(new java.awt.Dimension(358, 149));
        setResizable(false);

        try
        {
            BufferedImage backgroundToCrop = ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/default-messenger-wide.jpg"));
            Rectangle rect = new Rectangle(600, 110);
            Image backgroundCropped = backgroundToCrop.getSubimage(300, 0, rect.width, rect.height);
            Outter = new com.toddy.msnclientgui.views.JImage(backgroundCropped);
            //UserProfilePicture.setIcon(new ImageIcon(userProfilePicResized));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Outter.setBackground(new java.awt.Color(252, 252, 252));
        Outter.setMaximumSize(new java.awt.Dimension(350, 128));
        Outter.setMinimumSize(new java.awt.Dimension(350, 128));
        Outter.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TextLabel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        TextLabel.setForeground(new java.awt.Color(53, 90, 156));
        TextLabel.setText("TITLE");
        TextLabel.setText(title);
        Outter.add(TextLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 12, 254, 25));

        DescriptionLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        DescriptionLabel.setForeground(new java.awt.Color(0, 0, 0));
        DescriptionLabel.setText("DESCRITION");
        DescriptionLabel.setText(description);
        DescriptionLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        DescriptionLabel.setMaximumSize(new java.awt.Dimension(280, 75));
        DescriptionLabel.setMinimumSize(new java.awt.Dimension(280, 75));
        DescriptionLabel.setPreferredSize(new java.awt.Dimension(280, 75));
        Outter.add(DescriptionLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 43, -1, -1));

        CloseButton.setBackground(new java.awt.Color(252, 252, 252));
        CloseButton.setForeground(new java.awt.Color(0, 0, 0));
        CloseButton.setText("Close");
        CloseButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        CloseButton.setMaximumSize(new java.awt.Dimension(70, 21));
        CloseButton.setMinimumSize(new java.awt.Dimension(70, 21));
        CloseButton.setPreferredSize(new java.awt.Dimension(70, 21));
        Outter.add(CloseButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 123, -1, -1));

        try
        {
            switch(alertType)
            {
                case Error:
                Icon.setImage(ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/icons/error.png")).getScaledInstance(32, 32, Image.SCALE_SMOOTH));
                break;
                case Info:
                Icon.setImage(ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/icons/info.png")).getScaledInstance(32, 32, Image.SCALE_SMOOTH));
                break;
                case Warning:
                Icon.setImage(ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/icons/warning.png")).getScaledInstance(32, 32, Image.SCALE_SMOOTH));
                break;
            }
        }
        catch(IOException e)
        {

        }
        Icon.setBackground(new java.awt.Color(0, 0, 0));
        Icon.setMaximumSize(new java.awt.Dimension(32, 32));
        Icon.setMinimumSize(new java.awt.Dimension(32, 32));
        Icon.setOpaque(false);
        Icon.setPreferredSize(new java.awt.Dimension(32, 32));

        javax.swing.GroupLayout IconLayout = new javax.swing.GroupLayout(Icon);
        Icon.setLayout(IconLayout);
        IconLayout.setHorizontalGroup(
            IconLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );
        IconLayout.setVerticalGroup(
            IconLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        Outter.add(Icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Outter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Outter, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toddy.msnclientgui.views.JCustomButton CloseButton;
    private javax.swing.JLabel DescriptionLabel;
    private com.toddy.msnclientgui.views.JImage Icon;
    private com.toddy.msnclientgui.views.JImage Outter;
    private javax.swing.JLabel TextLabel;
    // End of variables declaration//GEN-END:variables
}
