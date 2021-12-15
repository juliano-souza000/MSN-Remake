/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toddy.msnclientgui.windows;

import com.toddy.msnclientgui.Main;
import com.toddy.msnclientgui.enums.AlertType;
import com.toddy.msnclientgui.enums.ResponseStatus;
import com.toddy.msnclientgui.enums.Status;
import com.toddy.msnclientgui.listeners.SelectionEnded;
import com.toddy.msnclientgui.network.NetworkUtils;
import com.toddy.msnclientgui.utils.Utils;
import com.toddy.msnclientgui.views.JImage;
import com.toddy.msnclientgui.views.RoundedBorder;
import java.awt.Color;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.json.JSONObject;

public class ProfileImageUpWindow extends javax.swing.JFrame
{
    private MessengerMainWindow parentWindow;
    
    public ProfileImageUpWindow(MessengerMainWindow parentWindow)
    {
	this.parentWindow = parentWindow;
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

        Outer = new com.toddy.msnclientgui.views.JImage();
        PreviewImage = new com.toddy.msnclientgui.views.JImage();
        PathField = new javax.swing.JTextField();
        SelectImage = new com.toddy.msnclientgui.views.JCustomButton();
        Title = new javax.swing.JLabel();
        ChangeButton = new com.toddy.msnclientgui.views.JCustomButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(220, 284));
        setResizable(false);

        try
        {
            BufferedImage backgroundToCrop = ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/default-messenger-wide.jpg"));
            Outer = new com.toddy.msnclientgui.views.JImage(backgroundToCrop);
            //UserProfilePicture.setIcon(new ImageIcon(userProfilePicResized));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Outer.setBackground(new java.awt.Color(252, 252, 252));
        Outer.setForeground(new java.awt.Color(0, 0, 0));
        Outer.setToolTipText("");
        Outer.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        Outer.setMinimumSize(new java.awt.Dimension(532, 370));
        Outer.setPreferredSize(new java.awt.Dimension(220, 284));

        PreviewImage = new JImage(null, true, Status.Available, 6);
        PreviewImage.setBackground(new java.awt.Color(252, 252, 252));
        PreviewImage.setBackground(new java.awt.Color(255, 255, 255,0));
        PreviewImage.setForeground(new java.awt.Color(255, 255, 255));
        PreviewImage.setToolTipText("");
        PreviewImage.setOpaque(false);
        PreviewImage.setPreferredSize(new java.awt.Dimension(100, 100));
        PreviewImage.setStatus(com.toddy.msnclientgui.enums.Status.Available);
        PreviewImage.shouldDrawWithPictureFrame(true);

        javax.swing.GroupLayout PreviewImageLayout = new javax.swing.GroupLayout(PreviewImage);
        PreviewImage.setLayout(PreviewImageLayout);
        PreviewImageLayout.setHorizontalGroup(
            PreviewImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        PreviewImageLayout.setVerticalGroup(
            PreviewImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 99, Short.MAX_VALUE)
        );

        PathField.setBorder(new RoundedBorder(new Color(171,187,208), 1, PathField.getInsets()));
        PathField.setEditable(false);
        PathField.setBackground(new java.awt.Color(252, 252, 252));
        PathField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        PathField.setPreferredSize(new java.awt.Dimension(270, 24));

        SelectImage.setBorder(null);
        SelectImage.setText("...");
        SelectImage.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        SelectImage.setMaximumSize(new java.awt.Dimension(18, 18));
        SelectImage.setMinimumSize(new java.awt.Dimension(18, 18));
        SelectImage.setPreferredSize(new java.awt.Dimension(18, 18));
        SelectImage.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                SelectImageMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                SelectImageMouseExited(evt);
            }
        });

        Title.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        Title.setForeground(new java.awt.Color(0, 0, 0));
        Title.setText("Preview Profile Picture");

        ChangeButton.setBackground(new java.awt.Color(252, 252, 252));
        ChangeButton.setBorder(null);
        ChangeButton.setForeground(new java.awt.Color(0, 0, 0));
        ChangeButton.setText("Change");
        ChangeButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        ChangeButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                ChangeButtonMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                ChangeButtonMouseExited(evt);
            }
        });

        javax.swing.GroupLayout OuterLayout = new javax.swing.GroupLayout(Outer);
        Outer.setLayout(OuterLayout);
        OuterLayout.setHorizontalGroup(
            OuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OuterLayout.createSequentialGroup()
                .addGroup(OuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(OuterLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(PreviewImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OuterLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(OuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Title)
                            .addGroup(OuterLayout.createSequentialGroup()
                                .addComponent(PathField, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(SelectImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addGroup(OuterLayout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(ChangeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        OuterLayout.setVerticalGroup(
            OuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OuterLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(Title)
                .addGap(18, 18, 18)
                .addComponent(PreviewImage, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(OuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PathField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SelectImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(ChangeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Outer.shouldRepeatBackground(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Outer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Outer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SelectImageMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SelectImageMouseClicked
    {//GEN-HEADEREND:event_SelectImageMouseClicked
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG and JPEG images", "png", "jpg", "jpeg");
	File image = getFile(filter, false);
	try
	{
	    if(image != null)
	    {
		PreviewImage.setImage(ImageIO.read(image).getScaledInstance(90, 90, Image.SCALE_SMOOTH));
		PathField.setText(image.getAbsolutePath());
	    }
	} 
	catch (IOException ex)
	{
	    Logger.getLogger(ProfileImageUpWindow.class.getName()).log(Level.SEVERE, null, ex);
	}
    }//GEN-LAST:event_SelectImageMouseClicked

    private void ChangeButtonMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ChangeButtonMouseClicked
    {//GEN-HEADEREND:event_ChangeButtonMouseClicked
	try
	{
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ImageIO.write(Utils.toBufferedImage(PreviewImage.getImage()), "png", baos);
	    JSONObject response = NetworkUtils.updateProfilePicture(Base64.getEncoder().encodeToString(baos.toByteArray()));
	    if(response.getEnum(ResponseStatus.class, "Status")==ResponseStatus.OK)
	    {
		parentWindow.setProfilePicture(PreviewImage.getImage());
		new Notification("Server");
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	    }
	    else
	    {
		AlertDialog alert = new AlertDialog(this, AlertType.Error, "Upload failed!", "We're sorry, but an error has occured.<br>Error code: 005");
		alert.setVisible(true);
	    }
	} catch (IOException ex)
	{
	    Logger.getLogger(ProfileImageUpWindow.class.getName()).log(Level.SEVERE, null, ex);
	}
    }//GEN-LAST:event_ChangeButtonMouseClicked

    private void ChangeButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ChangeButtonMouseExited
    {//GEN-HEADEREND:event_ChangeButtonMouseExited
        ChangeButton.mouseExit();
    }//GEN-LAST:event_ChangeButtonMouseExited

    private void SelectImageMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SelectImageMouseExited
    {//GEN-HEADEREND:event_SelectImageMouseExited
       SelectImage.mouseExit();
    }//GEN-LAST:event_SelectImageMouseExited

    private File getFile(FileNameExtensionFilter filter, boolean acceptAll)
    {
	File fileToReturn = null;
	
	JFileChooser filechooser = new JFileChooser();
	filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	filechooser.setAcceptAllFileFilterUsed(acceptAll);
	filechooser.addChoosableFileFilter(filter);
	Action details = filechooser.getActionMap().get("viewTypeDetails");
	details.actionPerformed(null);
	int returnValue = filechooser.showOpenDialog(null);
	
	if (returnValue == JFileChooser.APPROVE_OPTION) 
	{
	    File selectedFile = filechooser.getSelectedFile();
	    if(selectedFile.length() > Utils.MB_BYTES)
	    {
		AlertDialog alert = new AlertDialog(this, AlertType.Error, "Error", "The image/file cannot exceed 1MB");
		this.setEnabled(false);
		alert.setVisible(true);
		return null;
	    }
	    fileToReturn = selectedFile;
	}
	return fileToReturn;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toddy.msnclientgui.views.JCustomButton ChangeButton;
    private com.toddy.msnclientgui.views.JImage Outer;
    private javax.swing.JTextField PathField;
    private com.toddy.msnclientgui.views.JImage PreviewImage;
    private com.toddy.msnclientgui.views.JCustomButton SelectImage;
    private javax.swing.JLabel Title;
    // End of variables declaration//GEN-END:variables

}
