/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.toddy.msnclientgui.windows;

import com.toddy.msnclientgui.Main;
import com.toddy.msnclientgui.enums.AlertType;
import com.toddy.msnclientgui.enums.MessageType;
import com.toddy.msnclientgui.enums.ResponseStatus;
import com.toddy.msnclientgui.enums.Status;
import com.toddy.msnclientgui.listeners.SubmitListener;
import com.toddy.msnclientgui.models.FileMessage;
import com.toddy.msnclientgui.models.ImageMessage;
import com.toddy.msnclientgui.models.Message;
import com.toddy.msnclientgui.models.TextMessage;
import com.toddy.msnclientgui.models.User;
import com.toddy.msnclientgui.network.NetworkUtils;
import com.toddy.msnclientgui.utils.Utils;
import com.toddy.msnclientgui.views.JImage;
import com.toddy.msnclientgui.views.UI.JCustomScrollBarUI;
import com.toddy.msnclientgui.views.UI.JTextFieldHintUI;
import com.toddy.msnclientgui.views.UI.MessagesRenderer;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.json.JSONArray;
import org.json.JSONObject;


public class ChatWindow extends javax.swing.JFrame implements SubmitListener
{
    private MessengerMainWindow parent;
    private JSONObject userFromDetails;
    private JSONObject userToDetails;
    private int userFromId;
    private int userToId;
    
    private DefaultListModel<Message> messagesModel;
    
    public ChatWindow(MessengerMainWindow parent, int userFromId, int userToId) 
    {
	this.parent = parent;
	this.userFromId = userFromId;
	this.userToId = userToId;
	this.addWindowListener(new WindowAdapter()
	{
	    @Override
	    public void windowClosing(WindowEvent event)
	    {
		parent.revokeWindow(getChattingWithUserID());
	    }
	});
        try
        {
            JSONObject response = NetworkUtils.getProfile(userFromId);
            if(response.getEnum(ResponseStatus.class, "Status") == ResponseStatus.OK)
            {
                userFromDetails = response.getJSONObject("Profile");
            }
	    response = NetworkUtils.getProfile(userToId);
            if(response.getEnum(ResponseStatus.class, "Status") == ResponseStatus.OK)
            {
                userToDetails = response.getJSONObject("Profile");
            }
        }
        catch(IOException e){}
        initComponents();
	try
	{
	    setIconImage(Utils.getAppIcon());
	} 
	catch (IOException ex)
	{
	    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	}
	
    }
    
    public int getChattingWithUserID()
    {
	return this.userToId;
    }
    
    public void messageReceived(JSONObject message)
    {
	Thread r = new Thread()
	{
	    @Override
	    public void run()
	    {
		MessageType type = message.getEnum(MessageType.class, "Type");
		String username;
		if(message.getInt("From") == userFromId)
		{
		    username = userFromDetails.getString("Username");
		}
		else
		{
		    username = userToDetails.getString("Username");
		}
		switch(type)
		{
		    case TEXT:
			TextMessage tmsg = new TextMessage(username, message.getInt("From"), message.getJSONObject("Content").getString("Data"));
			messagesModel.addElement(tmsg);
			break;
		    case IMAGE:
			ImageMessage imsg = new ImageMessage(username, message.getInt("From"), Utils.toBufferedImage(Base64.getDecoder().decode(message.getJSONObject("Content").getString("Data"))));
			messagesModel.addElement(imsg);
			break;
		    case FILE:
			ByteBuffer bufferf = ByteBuffer.wrap( Base64.getDecoder().decode(message.getJSONObject("Content").getString("Data")));
			FileMessage fmsg = new FileMessage(username, message.getInt("From"), message.getJSONObject("Content").getString("Filename"), bufferf);
			messagesModel.addElement(fmsg);
			break;
		    }
		MessagesScollPanel.getVerticalScrollBar().setValue(MessagesScollPanel.getVerticalScrollBar().getMaximum());
	    }
	};
	r.start();
    }
    
    public void updateUserStatus(Status status)
    {
	userToDetails.remove("Status");
	userToDetails.put("Status", status);
        ChatToProfilePicture.setStatus(status);
	UserToStatusLabel.setText("("+status.name()+")");
    }
    
    public void updateUserAbout(String about)
    {
	userFromDetails.remove("About");
	userFromDetails.put("About", about);
	UserToAboutLabel.setText(about);
    }
    
    public void updateCurrUserStatus(Status status)
    {
	userFromDetails.remove("Status");
	userFromDetails.put("Status", status);
        ChatFromProfilePicture.setStatus(status);
    }
    
    public void updateUserProfilePic(JSONObject picture)
    {
	try
	{
	    String data = picture.getString("ProfileIMG");
	    userToDetails.remove("ProfileIMG");
	    userToDetails.put("ProfileIMG", data);
	    byte[] pictureb = Base64.getDecoder().decode(data);
	    BufferedImage userProfilePic = ImageIO.read(new ByteArrayInputStream(pictureb));
	    ChatToProfilePicture.setImage(userProfilePic);
	} 
	catch (IOException ex)
	{
	    Logger.getLogger(ChatWindow.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    public void updateUserFromProfilePic(Image picture)
    {
	try
	{
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ImageIO.write(Utils.toBufferedImage(picture), "png", baos);
	    String data = Base64.getEncoder().encodeToString(baos.toByteArray());
	    userFromDetails.remove("ProfileIMG");
	    userFromDetails.put("ProfileIMG", data);
	    ChatFromProfilePicture.setImage(picture);
	} 
	catch (IOException ex)
	{
	    Logger.getLogger(ChatWindow.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    public void updateToUserName(String username)
    {
	userToDetails.remove("Username");
	userToDetails.put("Username", username);
        UserToNameLabel.setText(username);
    }

    @Override
    public void SubmitPerformed(Object sender)
    {
	if(!Utils.isNullOrWhitespace(TypeMessageEditText.getText()))
	{
	    JSONObject message = new JSONObject();
	    message.put("Data", TypeMessageEditText.getText());
	    try
	    {
		JSONObject response = NetworkUtils.sendMessage(message, userToId, MessageType.TEXT);
		if(response.getEnum(ResponseStatus.class, "Status") == ResponseStatus.OK)
		{
		    messagesModel.addElement( new TextMessage(userFromDetails.getString("Username"), userFromId, TypeMessageEditText.getText()));
		    MessagesScollPanel.getVerticalScrollBar().setValue(MessagesScollPanel.getVerticalScrollBar().getMaximum());
		}
		TypeMessageEditText.setText("");
	    } 
	    catch (IOException ex)
	    {
		Logger.getLogger(ChatWindow.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        messagesModel = new DefaultListModel<>();
        Outer = new com.toddy.msnclientgui.views.JImage();
        Pictures = new javax.swing.JPanel();
        ChatFromProfilePicture = new com.toddy.msnclientgui.views.JImage();
        ChatToProfilePicture = new com.toddy.msnclientgui.views.JImage();
        DetailsPanel = new javax.swing.JPanel();
        UserToNameLabel = new javax.swing.JLabel();
        UserToStatusLabel = new javax.swing.JLabel();
        UserToAboutLabel = new javax.swing.JLabel();
        Separator = new com.toddy.msnclientgui.views.JCustomSeparator();
        MessagesScollPanel = new javax.swing.JScrollPane();
        MessagesList = new javax.swing.JList(messagesModel);
        TypeMessageScrollPane = new javax.swing.JScrollPane();
        TypeMessageEditText = new com.toddy.msnclientgui.views.TypeMessageLayout();
        Menu = new com.toddy.msnclientgui.views.JCustomMenuBar();
        PhotosMenuItem = new javax.swing.JMenu();
        SendImageMenuItem = new javax.swing.JMenuItem();
        PhotosFilesItem = new javax.swing.JMenu();
        SendFileMenuItem = new javax.swing.JMenuItem();
        OpenFolderMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Chat");
        setMaximumSize(new java.awt.Dimension(1200, 2147483647));
        setSize(new java.awt.Dimension(500, 390));
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowOpened(java.awt.event.WindowEvent evt)
            {
                formWindowOpened(evt);
            }
        });

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
        Outer.setMaximumSize(new java.awt.Dimension(1200, 32767));
        Outer.setMinimumSize(new java.awt.Dimension(500, 390));
        Outer.setPreferredSize(new java.awt.Dimension(500, 390));

        Pictures.setBackground(new java.awt.Color(252, 252, 252));
        Pictures.setOpaque(false);
        Pictures.setLayout(new java.awt.BorderLayout());

        try
        {
            byte[] arr = Base64.getDecoder().decode(userFromDetails.getString("ProfileIMG"));
            BufferedImage userProfilePic = ImageIO.read(new ByteArrayInputStream(arr));
            Image userProfilePicResized = userProfilePic.getScaledInstance(87, 87, Image.SCALE_SMOOTH);
            ChatFromProfilePicture = new JImage(userProfilePicResized, true, userFromDetails.getEnum(Status.class, "Status"), 5);
            //UserProfilePicture.setIcon(new ImageIcon(userProfilePicResized));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        ChatFromProfilePicture.setBackground(new java.awt.Color(255, 255, 255));
        ChatFromProfilePicture.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ChatFromProfilePicture.setForeground(new java.awt.Color(0, 0, 0));
        ChatFromProfilePicture.setToolTipText("");
        ChatFromProfilePicture.setMaximumSize(new java.awt.Dimension(100, 100));
        ChatFromProfilePicture.setMinimumSize(new java.awt.Dimension(100, 100));
        ChatFromProfilePicture.setName(""); // NOI18N
        ChatFromProfilePicture.setPreferredSize(new java.awt.Dimension(100, 100));
        ChatFromProfilePicture.setBackground(new java.awt.Color(0, 0, 0));
        ChatFromProfilePicture.setForeground(new java.awt.Color(0, 0, 0));

        ChatFromProfilePicture.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ChatFromProfilePicture.setOpaque(false);
        ChatFromProfilePicture.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        Pictures.add(ChatFromProfilePicture, java.awt.BorderLayout.PAGE_END);

        try
        {
            byte[] arr = Base64.getDecoder().decode(userToDetails.getString("ProfileIMG"));
            BufferedImage userProfilePic = ImageIO.read(new ByteArrayInputStream(arr));
            Image userProfilePicResized = userProfilePic.getScaledInstance(87, 87, Image.SCALE_SMOOTH);
            ChatToProfilePicture = new JImage(userProfilePicResized, true, userToDetails.getEnum(Status.class, "Status"), 5);
            //UserProfilePicture.setIcon(new ImageIcon(userProfilePicResized));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        ChatToProfilePicture.setBackground(new java.awt.Color(255, 255, 255));
        ChatToProfilePicture.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ChatToProfilePicture.setForeground(new java.awt.Color(0, 0, 0));
        ChatToProfilePicture.setToolTipText("");
        ChatToProfilePicture.setMaximumSize(new java.awt.Dimension(100, 100));
        ChatToProfilePicture.setMinimumSize(new java.awt.Dimension(100, 100));
        ChatToProfilePicture.setName(""); // NOI18N
        ChatToProfilePicture.setPreferredSize(new java.awt.Dimension(100, 100));
        ChatToProfilePicture.setBackground(new java.awt.Color(0, 0, 0));
        ChatToProfilePicture.setForeground(new java.awt.Color(0, 0, 0));

        ChatToProfilePicture.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ChatToProfilePicture.setOpaque(false);
        ChatToProfilePicture.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        Pictures.add(ChatToProfilePicture, java.awt.BorderLayout.PAGE_START);

        DetailsPanel.setOpaque(false);

        UserToNameLabel.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        UserToNameLabel.setForeground(new java.awt.Color(69, 69, 67));
        UserToNameLabel.setText("NAME");
        UserToNameLabel.setMaximumSize(new java.awt.Dimension(200, 28));
        UserToNameLabel.setMinimumSize(new java.awt.Dimension(10, 28));
        UserToNameLabel.setRequestFocusEnabled(false);
        UserToNameLabel.setText(userToDetails.getString("Username"));

        UserToStatusLabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        UserToStatusLabel.setForeground(new java.awt.Color(69, 69, 67));
        UserToStatusLabel.setText("STATUS");
        UserToStatusLabel.setMaximumSize(new java.awt.Dimension(100, 20));
        UserToStatusLabel.setMinimumSize(new java.awt.Dimension(62, 20));
        UserToStatusLabel.setText("("+userToDetails.getEnum(Status.class, "Status").name()+")");

        UserToAboutLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        UserToAboutLabel.setForeground(new java.awt.Color(69, 69, 67));
        UserToAboutLabel.setText("ABOUT");
        UserToAboutLabel.setText(userToDetails.getString("About"));

        javax.swing.GroupLayout DetailsPanelLayout = new javax.swing.GroupLayout(DetailsPanel);
        DetailsPanel.setLayout(DetailsPanelLayout);
        DetailsPanelLayout.setHorizontalGroup(
            DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DetailsPanelLayout.createSequentialGroup()
                .addGroup(DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DetailsPanelLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(UserToAboutLabel)
                            .addGroup(DetailsPanelLayout.createSequentialGroup()
                                .addComponent(UserToNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(UserToStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(DetailsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Separator, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(14, 14, 14))
        );
        DetailsPanelLayout.setVerticalGroup(
            DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DetailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(UserToNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(UserToStatusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(UserToAboutLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Separator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        MessagesScollPanel.setBorder(null);
        MessagesScollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        MessagesScollPanel.getVerticalScrollBar().setUI(new JCustomScrollBarUI());
        MessagesScollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        MessagesScollPanel.setPreferredSize(new java.awt.Dimension(165, 210));
        MessagesScollPanel.setRequestFocusEnabled(false);

        MessagesList.setCellRenderer(new MessagesRenderer(MessagesList, userFromId, userToId));
        MessagesList.setBackground(new java.awt.Color(255, 255, 255));
        MessagesList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        MessagesList.setForeground(new java.awt.Color(0, 0, 0));
        MessagesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        MessagesList.setMinimumSize(new java.awt.Dimension(1, 16));
        MessagesList.setSelectedIndex(0);
        MessagesScollPanel.setViewportView(MessagesList);

        TypeMessageScrollPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(213, 224, 230), 1, true));
        TypeMessageScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        TypeMessageScrollPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        TypeMessageScrollPane.setPreferredSize(new java.awt.Dimension(220, 83));
        TypeMessageScrollPane.getVerticalScrollBar().setUI(new JCustomScrollBarUI());

        TypeMessageEditText.setBorder(null);
        TypeMessageEditText.setColumns(20);
        TypeMessageEditText.setRows(5);
        TypeMessageEditText.setSubmitListener(this);
        TypeMessageScrollPane.setViewportView(TypeMessageEditText);

        javax.swing.GroupLayout OuterLayout = new javax.swing.GroupLayout(Outer);
        Outer.setLayout(OuterLayout);
        OuterLayout.setHorizontalGroup(
            OuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OuterLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(Pictures, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(OuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DetailsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TypeMessageScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addComponent(MessagesScollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        OuterLayout.setVerticalGroup(
            OuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OuterLayout.createSequentialGroup()
                .addGroup(OuterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(OuterLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(DetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MessagesScollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(21, 21, 21)
                        .addComponent(TypeMessageScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(OuterLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(Pictures, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );

        Outer.shouldRepeatBackground(true);

        getContentPane().add(Outer, java.awt.BorderLayout.CENTER);

        Menu.setBackground(new java.awt.Color(255, 255, 255));
        Menu.setBorder(null);
        Menu.setForeground(new java.awt.Color(255, 255, 255));
        Menu.setMinimumSize(new java.awt.Dimension(0, 34));
        Menu.setOpaque(false);
        Menu.setPreferredSize(new java.awt.Dimension(64, 34));

        PhotosMenuItem.setBackground(new java.awt.Color(255, 255, 255));
        PhotosMenuItem.setForeground(new java.awt.Color(255, 255, 255));
        PhotosMenuItem.setText("Photos");
        PhotosMenuItem.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        PhotosMenuItem.setHideActionText(true);
        PhotosMenuItem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PhotosMenuItem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        PhotosMenuItem.setIconTextGap(11);
        PhotosMenuItem.setInheritsPopupMenu(true);
        PhotosMenuItem.setMargin(new java.awt.Insets(5, 5, 5, 5));
        PhotosMenuItem.setMaximumSize(new java.awt.Dimension(65, 300));
        PhotosMenuItem.setOpaque(false);

        SendImageMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        SendImageMenuItem.setBackground(new java.awt.Color(252, 252, 252));
        SendImageMenuItem.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        SendImageMenuItem.setForeground(new java.awt.Color(0, 0, 0));
        SendImageMenuItem.setText("Send image");
        SendImageMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                SendImageMenuItemActionPerformed(evt);
            }
        });
        PhotosMenuItem.add(SendImageMenuItem);

        Menu.add(PhotosMenuItem);

        PhotosFilesItem.setBackground(new java.awt.Color(255, 255, 255));
        PhotosFilesItem.setForeground(new java.awt.Color(255, 255, 255));
        PhotosFilesItem.setText("Files");
        PhotosFilesItem.setFocusable(false);
        PhotosFilesItem.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        PhotosFilesItem.setHideActionText(true);
        PhotosFilesItem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PhotosFilesItem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        PhotosFilesItem.setIconTextGap(11);
        PhotosFilesItem.setInheritsPopupMenu(true);
        PhotosFilesItem.setMargin(new java.awt.Insets(5, 5, 5, 5));
        PhotosFilesItem.setMaximumSize(new java.awt.Dimension(55, 300));
        PhotosFilesItem.setOpaque(false);

        SendFileMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        SendFileMenuItem.setBackground(new java.awt.Color(252, 252, 252));
        SendFileMenuItem.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        SendFileMenuItem.setForeground(new java.awt.Color(0, 0, 0));
        SendFileMenuItem.setText("Send file");
        SendFileMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                SendFileMenuItemActionPerformed(evt);
            }
        });
        PhotosFilesItem.add(SendFileMenuItem);

        OpenFolderMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        OpenFolderMenu.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        OpenFolderMenu.setForeground(new java.awt.Color(0, 0, 0));
        OpenFolderMenu.setText("Open file folder");
        OpenFolderMenu.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                OpenFolderMenuActionPerformed(evt);
            }
        });
        PhotosFilesItem.add(OpenFolderMenu);

        Menu.add(PhotosFilesItem);

        setJMenuBar(Menu);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SendImageMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_SendImageMenuItemActionPerformed
    {//GEN-HEADEREND:event_SendImageMenuItemActionPerformed
	FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG and JPEG images", "png", "jpg", "jpeg");
	File image = getFile(filter, false);
	try
	{
	    JSONObject message = new JSONObject();
	    String base64 = Base64.getEncoder().encodeToString(Files.readAllBytes(image.toPath()));
	    message.put("Data", base64);
	    message.put("Filename", "");
	    Thread thread = new Thread()
	    {
		@Override
		public void run()
		{
		    super.run();
		    try
		    {
			JSONObject response = NetworkUtils.sendMessage(message, userToId, MessageType.IMAGE);
			if(response.getEnum(ResponseStatus.class, "Status") == ResponseStatus.OK)
			{
			    messagesModel.addElement(new ImageMessage(userFromDetails.getString("Username"), userFromId, Utils.toBufferedImage(Base64.getDecoder().decode(base64))));
			    new Notification(userToDetails.getString("Username")).setVisible(true);
			    MessagesScollPanel.getVerticalScrollBar().setValue(MessagesScollPanel.getVerticalScrollBar().getMaximum());
			}
		    } 
		    catch (IOException ex)
		    {
			Logger.getLogger(ChatWindow.class.getName()).log(Level.SEVERE, null, ex);
		    }
		}
	    };
	    thread.start();
	} 
	catch (IOException ex)
	{
	    Logger.getLogger(ChatWindow.class.getName()).log(Level.SEVERE, null, ex);
	}
    }//GEN-LAST:event_SendImageMenuItemActionPerformed

    private void SendFileMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_SendFileMenuItemActionPerformed
    {//GEN-HEADEREND:event_SendFileMenuItemActionPerformed
	File file = getFile(null, true);
	try
	{
	    JSONObject message = new JSONObject();
	    String base64 = Base64.getEncoder().encodeToString(Files.readAllBytes(file.toPath()));
	    message.put("Data", base64);
	    message.put("Filename", file.getName());
	    Thread thread = new Thread()
	    {
		@Override
		public void run()
		{
		    super.run(); //To change body of generated methods, choose Tools | Templates.
		    try
		    {
			JSONObject response = NetworkUtils.sendMessage(message, userToId, MessageType.FILE);
			if(response.getEnum(ResponseStatus.class, "Status") == ResponseStatus.OK)
			{
			    ByteBuffer bufferf = ByteBuffer.wrap(Base64.getDecoder().decode(base64));
			    FileMessage fmsg = new FileMessage(userFromDetails.getString("Username"), userFromId, file.getName(), bufferf);
			    messagesModel.addElement(fmsg);
			    new Notification(userToDetails.getString("Username")).setVisible(true);
			    MessagesScollPanel.getVerticalScrollBar().setValue(MessagesScollPanel.getVerticalScrollBar().getMaximum());
			}
		    } 
		    catch (IOException ex)
		    {
			Logger.getLogger(ChatWindow.class.getName()).log(Level.SEVERE, null, ex);
		    }
		    
		}
	    };
	    thread.start();
	} 
	catch (IOException ex)
	{
	    Logger.getLogger(ChatWindow.class.getName()).log(Level.SEVERE, null, ex);
	}
    }//GEN-LAST:event_SendFileMenuItemActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowOpened
    {//GEN-HEADEREND:event_formWindowOpened
        ChatWindow t = this;
	AlertDialog alert = new AlertDialog(this, AlertType.Info, "Loading messages", "We are loading your messages. This can take some time.");
	alert.setVisible(true);
	Thread r = new Thread()
	{
	    @Override
	    public void run()
	    {
		try
		{
		    JSONObject resp = NetworkUtils.getAllMessages(userToId);
		    JSONArray messages = null;
		    if(resp.getEnum(ResponseStatus.class, "Status") == ResponseStatus.OK)
		    {
			messages = resp.getJSONArray("Messages");
			if(messages != null)
			{
			    for(int i = 0; i < messages.length();i++)
			    {
				messageReceived((JSONObject) messages.get(i));
			    }
			}
		    }
		    alert.dispatchEvent(new WindowEvent(t, WindowEvent.WINDOW_CLOSING));
		    MessagesScollPanel.getVerticalScrollBar().setValue(MessagesScollPanel.getVerticalScrollBar().getMaximum());
		} 
		catch (IOException ex)
		{
		    Logger.getLogger(ChatWindow.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	};
	r.start();
    }//GEN-LAST:event_formWindowOpened

    private void OpenFolderMenuActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_OpenFolderMenuActionPerformed
    {//GEN-HEADEREND:event_OpenFolderMenuActionPerformed
	try
	{
	    File directory = new File(Main.getConfig().getReceivedFileFolder()+"/"+userFromId+"."+userToId);
	    if(!directory.exists())
		directory.mkdir();
	    Desktop.getDesktop().open(directory);
	} 
	catch (IOException ex)
	{
	    Logger.getLogger(ChatWindow.class.getName()).log(Level.SEVERE, null, ex);
	}
    }//GEN-LAST:event_OpenFolderMenuActionPerformed

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
    private com.toddy.msnclientgui.views.JImage ChatFromProfilePicture;
    private com.toddy.msnclientgui.views.JImage ChatToProfilePicture;
    private javax.swing.JPanel DetailsPanel;
    private com.toddy.msnclientgui.views.JCustomMenuBar Menu;
    private javax.swing.JList MessagesList;
    private javax.swing.JScrollPane MessagesScollPanel;
    private javax.swing.JMenuItem OpenFolderMenu;
    private com.toddy.msnclientgui.views.JImage Outer;
    private javax.swing.JMenu PhotosFilesItem;
    private javax.swing.JMenu PhotosMenuItem;
    private javax.swing.JPanel Pictures;
    private javax.swing.JMenuItem SendFileMenuItem;
    private javax.swing.JMenuItem SendImageMenuItem;
    private com.toddy.msnclientgui.views.JCustomSeparator Separator;
    private com.toddy.msnclientgui.views.TypeMessageLayout TypeMessageEditText;
    private javax.swing.JScrollPane TypeMessageScrollPane;
    private javax.swing.JLabel UserToAboutLabel;
    private javax.swing.JLabel UserToNameLabel;
    private javax.swing.JLabel UserToStatusLabel;
    // End of variables declaration//GEN-END:variables


}
