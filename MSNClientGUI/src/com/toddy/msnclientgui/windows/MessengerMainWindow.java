/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toddy.msnclientgui.windows;

import com.toddy.msnclientgui.Main;
import com.toddy.msnclientgui.background.*;
import com.toddy.msnclientgui.enums.AlertType;
import com.toddy.msnclientgui.enums.MessageType;
import com.toddy.msnclientgui.enums.ResponseStatus;
import com.toddy.msnclientgui.enums.Status;
import com.toddy.msnclientgui.listeners.BoundsPopupMenuListener;
import com.toddy.msnclientgui.network.NetworkUtils;
import com.toddy.msnclientgui.views.JImage;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import org.json.JSONObject;
import com.toddy.msnclientgui.listeners.MemberUpdated;
import com.toddy.msnclientgui.listeners.MessageUpdateReceived;
import com.toddy.msnclientgui.models.User;
import com.toddy.msnclientgui.utils.Utils;
import com.toddy.msnclientgui.views.RoundedBorder;
import com.toddy.msnclientgui.views.UI.IconListRenderer;
import com.toddy.msnclientgui.views.UI.JCustomScrollBarUI;
import com.toddy.msnclientgui.views.UI.UserListRenderer;
import com.toddy.msnclientgui.views.UI.JCustomComboBoxUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import org.json.JSONArray;

public class MessengerMainWindow extends javax.swing.JFrame implements MemberUpdated, MessageUpdateReceived
{
    private JSONObject userDetailToLoad;
    private ListenNewMembers listenForNewMembers;
    private ListenForStatusUpdate listenForStatusUpdates;
    private ListenForAboutUpdate listenForAboutUpdates;
    private ListenForMessages listenForMessages;
    private ListenForProfilePicture listenForProfilePicture;
    private ListenForUsernameUpdate listenForUsernameUpdate;
    private ListenForDeletedMembers listenForDeletedMembers;
    private DefaultListModel<User> userModel;
    private Status status;
    
    private ActionListener mouseAFKDetector;
    
    private List<ChatWindow> chatWindows = new ArrayList<ChatWindow>();

    public MessengerMainWindow(JSONObject user, Status statuss) 
    {
	this.status = statuss;
	NetworkUtils.setUser(user);
	updateUserStatus(status, 0);
	this.addWindowListener(new WindowAdapter()
	{
	    @Override
	    public void windowClosing(WindowEvent event)
	    {
		updateUserStatus(Status.Offline, 0);
		try
		{
		    listenForNewMembers.terminate();
		    listenForStatusUpdates.terminate();
		    listenForAboutUpdates.terminate();
		    listenForMessages.terminate();
		    listenForProfilePicture.terminate();
		    listenForUsernameUpdate.terminate();
		    listenForDeletedMembers.terminate();
		    NetworkUtils.disconnect();    
		}
		catch(IOException ex)
		{ }
	    }
	});
	try
	{
	    JSONObject response = NetworkUtils.getProfile(user.getInt("ID"));
	    if(response.getEnum(ResponseStatus.class, "Status") == ResponseStatus.OK)
	    {
		userDetailToLoad = response.getJSONObject("Profile");
	    }
	}
	catch(IOException ex)
	{
	}
	listenForNewMembers = new ListenNewMembers(this);
	listenForStatusUpdates = new ListenForStatusUpdate(this);
	listenForAboutUpdates = new ListenForAboutUpdate(this);
	listenForMessages = new ListenForMessages(this);
	listenForProfilePicture = new ListenForProfilePicture(this);
	listenForUsernameUpdate = new ListenForUsernameUpdate(this);
	listenForDeletedMembers = new ListenForDeletedMembers(this);
	listenForNewMembers.start();
	listenForStatusUpdates.start();
	listenForAboutUpdates.start();
	listenForMessages.start();
	listenForProfilePicture.start();
	listenForUsernameUpdate.start();
	listenForDeletedMembers.start();
	
	//UIManager.put("ScrollBarUI", "com.toddy.msnclientgui.views.UI.JCustomScrollBarUI");
	initComponents();
	
	try
	{
	    JSONObject response = NetworkUtils.getAllProfiles();
	    if(response.getEnum(ResponseStatus.class, "Status") == ResponseStatus.OK)
	    {
		JSONArray profileArr = response.getJSONArray("Profiles");
		for(int i = 0; i < profileArr.length(); i++)
		{
		    JSONObject usrJ = profileArr.getJSONObject(i);
		    UpdateNewMember(usrJ);
		}
		
	    }
	} 
	catch (IOException ex)
	{
	    Logger.getLogger(MessengerMainWindow.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	mouseAFKDetector = new ActionListener() 
	{

            Point lastPoint = MouseInfo.getPointerInfo().getLocation();;

            @Override
            public void actionPerformed(ActionEvent e) 
	    {
                Point p = MouseInfo.getPointerInfo().getLocation();
                if (p.equals(lastPoint) && !e.getActionCommand().equals("UpdateStatus")) 
		{
                    updateUserStatus(Status.Away, 0);
		    UserProfilePicture.setStatus(Status.Away);
                }
		else
		{
		    if(status != UserProfilePicture.getStatus())
		    {
			updateUserStatus(status, 0);
			UserProfilePicture.setStatus(status);
			CurrentStatusCombo.setSelectedIndex(status.ordinal());
		    }
		}
		updateUserStatusInChat();
                lastPoint = p;
            }
        };
        Timer timer = new Timer(Main.getConfig().getMinutesForAFK()*60*1000, mouseAFKDetector);
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
    
    public void setProfilePicture(Image img)
    {
	UserProfilePicture.setImage(img.getScaledInstance(55, 55, Image.SCALE_SMOOTH));
	for(int i = 0; i < chatWindows.size(); i++)
	{
	    chatWindows.get(i).updateUserFromProfilePic(img);
	}
    }
    
    public void revokeWindow(int id)
    {
	for(int i = 0; i < chatWindows.size(); i++)
	{
	    if(chatWindows.get(i).getChattingWithUserID() == id)
	    {
		chatWindows.remove(i);
		break;
	    }
	}
    }
    
    private void updateUserStatus(Status status, int tries)
    {
	try
	{
	    JSONObject response = NetworkUtils.updateStatus(status);
	    if(response.getEnum(ResponseStatus.class, "Status") == ResponseStatus.InternalError && tries < 5)
	    {
		updateUserStatus(status, tries++);
	    }
	}
	catch(IOException ex) { }
    }
    
    private void updateUserAbout(String about, int tries)
    {
	try
	{
	    JSONObject response = NetworkUtils.updateAbout(about);
	    if(response.getEnum(ResponseStatus.class, "Status") == ResponseStatus.InternalError && tries < 5)
	    {
		updateUserAbout(about, tries++);
	    }
	}
	catch(IOException ex) { }
    }

    @Override
    public void UpdateNewMember(JSONObject profile)
    {
	userModel.addElement(new User(profile.getEnum(Status.class, "Status"), profile.getString("Username"), profile.getString("About"), profile.getInt("ID")));
	PeopleCount.setText(String.format("(%d)", ContactsList.getModel().getSize()));
    }
    
    @Override
    public void UpdateStatus(JSONObject profile)
    {
	for(int i = 0; i < userModel.size(); i++)
	{
	    if(userModel.elementAt(i).getUserID() == profile.getInt("ID"))
	    {
		userModel.elementAt(i).setUserStatus(profile.getEnum(Status.class, "UserStatus"));
		ContactsList.repaint();
		break;
	    }
	}
	
	for(int i = 0; i < chatWindows.size(); i++)
	{
	    if(chatWindows.get(i).getChattingWithUserID() == profile.getInt("ID"))
	    {
		chatWindows.get(i).updateUserStatus(profile.getEnum(Status.class, "UserStatus"));
		break;
	    }
	}
    }

    @Override
    public void UpdateAbout(JSONObject profile)
    {
	for(int i = 0; i < userModel.size(); i++)
	{
	    if(userModel.elementAt(i).getUserID() == profile.getInt("ID"))
	    {
		userModel.elementAt(i).setUserAbout(profile.getString("About"));
		ContactsList.repaint();
		break;
	    }
	}
	
	for(int i = 0; i < chatWindows.size(); i++)
	{
	    if(chatWindows.get(i).getChattingWithUserID() == profile.getInt("ID"))
	    {
		chatWindows.get(i).updateUserAbout(profile.getString("About"));
		break;
	    }
	}
    }
    
    @Override
    public void UpdateProfilePicture(JSONObject picture)
    {
	for(int i = 0; i < chatWindows.size(); i++)
	{
	    if(chatWindows.get(i).getChattingWithUserID() == picture.getInt("ID"))
	    {
		chatWindows.get(i).updateUserProfilePic(picture.getJSONObject("Image"));
		break;
	    }
	}
    }
    
    @Override
    public void UpdateUsername(JSONObject username)
    {
	for(int i = 0; i < userModel.size(); i++)
	{
	    if(userModel.elementAt(i).getUserID() == username.getInt("ID"))
	    {
		userModel.elementAt(i).setUserName(username.getString("Username"));
		ContactsList.repaint();
		break;
	    }
	}
	
	for(int i = 0; i < chatWindows.size(); i++)
	{
	    if(chatWindows.get(i).getChattingWithUserID() == username.getInt("ID"))
	    {
		chatWindows.get(i).updateToUserName(username.getString("Username"));
		break;
	    }
	}
    }
    
    @Override
    public void UpdateDeletedMember(int ID)
    {
	for(int i = 0; i < userModel.size(); i++)
	{
	    if(userModel.elementAt(i).getUserID() == ID)
	    {
		userModel.removeElementAt(i);
		ContactsList.repaint();
		break;
	    }
	}
	
	for(int i = 0; i < chatWindows.size(); i++)
	{
	    if(chatWindows.get(i).getChattingWithUserID() == ID)
	    {
		chatWindows.get(i).dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		AlertDialog dialog = new AlertDialog(this, AlertType.Info, "Member left the network", "The user who you were talking to has left the network.");
		dialog.setVisible(true);
		break;
	    }
	}
	
	PeopleCount.setText(String.format("(%d)", ContactsList.getModel().getSize()));
    }
    
    @Override
    public void MessageReceived(JSONObject message)
    {
	new Notification(message).setVisible(true);
	for(int i = 0; i < chatWindows.size(); i++)
	{
	    if(chatWindows.get(i).getChattingWithUserID() == message.getInt("From"))
	    {
	        chatWindows.get(i).messageReceived(message);
	        break;
	    }
	}
    }
    
    public void setUsername(String username)
    {
	userDetailToLoad.remove("Username");
	userDetailToLoad.put("Username", username);
	NameLabel.setText(username);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        AboutEdit = new javax.swing.JTextField();
        PopupMenu = new javax.swing.JPopupMenu();
        Outer = new javax.swing.JPanel();
        UserProfilePicture = new com.toddy.msnclientgui.views.JImage();
        NameLabel = new javax.swing.JLabel();
        AboutLabel = new javax.swing.JLabel();
        Separator = new com.toddy.msnclientgui.views.JCustomSeparator();
        PeopleLabel = new javax.swing.JLabel();
        PeopleCount = new javax.swing.JLabel();
        ContactsScollPanel = new javax.swing.JScrollPane();
        userModel = new DefaultListModel<>();
        ContactsList = new javax.swing.JList(userModel);
        CurrentStatusCombo = new javax.swing.JComboBox<>();
        Logout = new javax.swing.JLabel();

        AboutEdit.setBackground(new java.awt.Color(252, 252, 252));
        AboutEdit.setForeground(new java.awt.Color(0, 0, 0));
        AboutEdit.setText("ABOUT");
        AboutEdit.setMaximumSize(new java.awt.Dimension(190, 21));
        AboutEdit.setMinimumSize(new java.awt.Dimension(190, 21));
        AboutEdit.setOpaque(false);
        AboutEdit.setBorder(new RoundedBorder(new Color(171,187,208), 1, AboutEdit.getInsets()));
        AboutEdit.setPreferredSize(new java.awt.Dimension(190, 21));
        AboutEdit.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                AboutEditFocusLost(evt);
            }
        });
        AboutEdit.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(java.awt.event.KeyEvent evt)
            {
                AboutEditKeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Contacts list");
        setMinimumSize(new java.awt.Dimension(288, 650));
        setResizable(false);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseMoved(java.awt.event.MouseEvent evt)
            {
                formMouseMoved(evt);
            }
        });

        try
        {
            BufferedImage backgroundToCrop = ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/default-messenger-wide.jpg"));
            Rectangle rect = new Rectangle(600, 110);
            Image backgroundCropped = backgroundToCrop.getSubimage(300, 0, rect.width, rect.height);
            Outer = new com.toddy.msnclientgui.views.JImage(backgroundCropped);
            //UserProfilePicture.setIcon(new ImageIcon(userProfilePicResized));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Outer.setBackground(new java.awt.Color(252, 252, 252));
        Outer.setForeground(new java.awt.Color(0, 0, 0));
        Outer.setToolTipText("");
        Outer.setMinimumSize(new java.awt.Dimension(288, 650));
        Outer.setName(""); // NOI18N
        Outer.setPreferredSize(new java.awt.Dimension(288, 650));
        Outer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        try
        {
            byte[] arr = Base64.getDecoder().decode(userDetailToLoad.getString("ProfileIMG"));
            BufferedImage userProfilePic = ImageIO.read(new ByteArrayInputStream(arr));
            Image userProfilePicResized = userProfilePic.getScaledInstance(55, 55, Image.SCALE_SMOOTH);
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
        UserProfilePicture.setMaximumSize(new java.awt.Dimension(65, 65));
        UserProfilePicture.setMinimumSize(new java.awt.Dimension(65, 65));
        UserProfilePicture.setName(""); // NOI18N
        UserProfilePicture.setPreferredSize(new java.awt.Dimension(65, 65));
        UserProfilePicture.setBackground(new java.awt.Color(0, 0, 0));
        UserProfilePicture.setForeground(new java.awt.Color(0, 0, 0));

        UserProfilePicture.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        UserProfilePicture.setOpaque(false);
        UserProfilePicture.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        UserProfilePicture.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                UserProfilePictureMouseClicked(evt);
            }
        });

        UserProfilePicture.setToolTipText("Right click to open menu");

        Outer.add(UserProfilePicture, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        NameLabel.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        NameLabel.setForeground(new java.awt.Color(0, 0, 0));
        NameLabel.setText("NAME");
        NameLabel.setMaximumSize(new java.awt.Dimension(100, 21));
        NameLabel.setPreferredSize(new java.awt.Dimension(100, 21));
        NameLabel.setText(userDetailToLoad.getString("Username"));
        Outer.add(NameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 15, -1, -1));

        AboutLabel.setBackground(new java.awt.Color(252, 252, 252));
        AboutLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        AboutLabel.setForeground(new java.awt.Color(0, 0, 0));
        AboutLabel.setText("ABOUT");
        AboutLabel.setToolTipText("Double click to change");
        AboutLabel.setMaximumSize(new java.awt.Dimension(190, 14));
        AboutLabel.setPreferredSize(new java.awt.Dimension(190, 14));
        AboutLabel.setRequestFocusEnabled(false);
        AboutLabel.setText(userDetailToLoad.getString("About"));
        AboutLabel.setToolTipText("Double click to change");
        AboutLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        try
        {
            AboutLabel.setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/baseline_edit_black.png")).getScaledInstance(14, 14, Image.SCALE_SMOOTH)));
            AboutLabel.setIconTextGap(10);
        } catch(IOException e) {}
        AboutLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                AboutLabelMouseClicked(evt);
            }
        });
        Outer.add(AboutLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 40, -1, -1));
        Outer.add(Separator, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 270, 10));

        PeopleLabel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        PeopleLabel.setForeground(new java.awt.Color(46, 86, 134));
        PeopleLabel.setText("People");
        Outer.add(PeopleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 100, -1, -1));

        PeopleCount.setBackground(new java.awt.Color(184, 199, 220));
        PeopleCount.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        PeopleCount.setForeground(new java.awt.Color(184, 199, 220));
        PeopleCount.setText("(0)");
        Outer.add(PeopleCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, -1, -1));

        ContactsScollPanel.setBorder(null);
        ContactsScollPanel.getVerticalScrollBar().setUI(new JCustomScrollBarUI());
        ContactsScollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        ContactsScollPanel.getVerticalScrollBar().setPreferredSize(new Dimension(15, 0));
        ContactsScollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        ContactsScollPanel.setPreferredSize(new java.awt.Dimension(288, 520));
        ContactsScollPanel.setRequestFocusEnabled(false);

        ContactsList.setCellRenderer(new UserListRenderer());
        ContactsList.setBackground(new java.awt.Color(255, 255, 255));
        ContactsList.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 5));
        ContactsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ContactsList.setSelectedIndex(0);
        ContactsList.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                ContactsListMouseClicked(evt);
            }
        });
        ContactsScollPanel.setViewportView(ContactsList);

        Outer.add(ContactsScollPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, -1, -1));

        CurrentStatusCombo.setBackground(new java.awt.Color(252, 252, 252));
        CurrentStatusCombo.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        CurrentStatusCombo.setForeground(new java.awt.Color(0, 0, 0));
        CurrentStatusCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "(Available)", "(Away)", "(Busy)", "(Appear Offline)" }));
        CurrentStatusCombo.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        CurrentStatusCombo.setFocusable(false);
        CurrentStatusCombo.setMinimumSize(new java.awt.Dimension(50, 20));
        CurrentStatusCombo.setOpaque(false);
        CurrentStatusCombo.setPreferredSize(new java.awt.Dimension(95, 20));
        CurrentStatusCombo.setUI(new JCustomComboBoxUI(false));
        CurrentStatusCombo.setRenderer(new DefaultListCellRenderer()
        {
            @Override
            public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus)
                {
                    JComponent result = (JComponent)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if(index == -1)
                    result.setOpaque(false);
                    else
                    result.setOpaque(true);
                    //if(isSelected)
                    //    result.setBackground(Color.RED);
                    return result;
                }});
                CurrentStatusCombo.setSelectedIndex(this.status.ordinal());
                BoundsPopupMenuListener listener = new BoundsPopupMenuListener(true, false);
                CurrentStatusCombo.addPopupMenuListener(listener);
                CurrentStatusCombo.addItemListener(new ItemListener()
                    {
                        @Override
                        public void itemStateChanged(ItemEvent event)
                        {
                            if (event.getStateChange() == ItemEvent.SELECTED)
                            {
                                String text = event.getItem().toString();
                                CurrentStatusCombo.setPrototypeDisplayValue(text);

                                if(text == "(Available)")
                                {
                                    CurrentStatusCombo.setPreferredSize(new Dimension(95, 20));
                                    Outer.validate();
                                    Outer.repaint();
                                    CurrentStatusCombo.setUI(new JCustomComboBoxUI());
                                    status = Status.Available;
                                    UserProfilePicture.setStatus(status);
                                    updateUserStatus(status, 0);
                                }
                                else if(text == "(Away)")
                                {
                                    CurrentStatusCombo.setPreferredSize(new Dimension(80, 20));
                                    Outer.validate();
                                    Outer.repaint();
                                    CurrentStatusCombo.setUI(new JCustomComboBoxUI());
                                    status = Status.Away;
                                    UserProfilePicture.setStatus(status);
                                    updateUserStatus(status, 0);
                                }
                                else if(text == "(Busy)")
                                {
                                    CurrentStatusCombo.setPreferredSize(new Dimension(75, 20));
                                    Outer.validate();
                                    Outer.repaint();
                                    CurrentStatusCombo.setUI(new JCustomComboBoxUI());
                                    status = Status.Busy;
                                    UserProfilePicture.setStatus(status);
                                    updateUserStatus(status, 0);
                                }
                                else
                                {
                                    CurrentStatusCombo.setPreferredSize(new Dimension(120, 20));
                                    Outer.validate();
                                    Outer.repaint();
                                    CurrentStatusCombo.setUI(new JCustomComboBoxUI());
                                    status = Status.Offline;
                                    UserProfilePicture.setStatus(status);
                                    updateUserStatus(status, 0);
                                }
                                updateUserStatusInChat();
                            }
                        }
                    });
                    Outer.add(CurrentStatusCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 17, 100, -1));

                    Logout.setBackground(new java.awt.Color(255, 255, 255));
                    Logout.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
                    Logout.setForeground(new java.awt.Color(0, 102, 204));
                    Logout.setText("Logout");
                    Logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                    Logout.addMouseListener(new java.awt.event.MouseAdapter()
                    {
                        public void mouseClicked(java.awt.event.MouseEvent evt)
                        {
                            LogoutMouseClicked(evt);
                        }
                        public void mouseExited(java.awt.event.MouseEvent evt)
                        {
                            LogoutMouseExited(evt);
                        }
                        public void mouseEntered(java.awt.event.MouseEvent evt)
                        {
                            LogoutMouseEntered(evt);
                        }
                    });
                    Outer.add(Logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 100, -1, -1));

                    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                    getContentPane().setLayout(layout);
                    layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Outer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    );
                    layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(Outer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE))
                    );

                    pack();
                }// </editor-fold>//GEN-END:initComponents

    private void AboutLabelMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_AboutLabelMouseClicked
    {//GEN-HEADEREND:event_AboutLabelMouseClicked
        if(evt.getClickCount() == 2)
	{
	   RemoveLabelAbout();
	}
    }//GEN-LAST:event_AboutLabelMouseClicked

    private void AboutEditKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_AboutEditKeyPressed
    {//GEN-HEADEREND:event_AboutEditKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_ESCAPE)
	{
	    RemoveEditAbout();
	}
    }//GEN-LAST:event_AboutEditKeyPressed

    private void AboutEditFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_AboutEditFocusLost
    {//GEN-HEADEREND:event_AboutEditFocusLost
        RemoveEditAbout();
    }//GEN-LAST:event_AboutEditFocusLost

    private void ContactsListMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ContactsListMouseClicked
    {//GEN-HEADEREND:event_ContactsListMouseClicked
	JList list = (JList)evt.getSource();
        if (evt.getClickCount() == 2) 
	{
            int index = list.locationToIndex(evt.getPoint());
	    boolean shouldOpenNew = true;
	    int ID = userModel.elementAt(index).getUserID();
	    for(int i = 0; i < chatWindows.size(); i++)
	    {
		if(chatWindows.get(i).getChattingWithUserID() == ID)
		{
		    shouldOpenNew = false;
		    chatWindows.get(i).setVisible(true);
		    break;
		}
	    }
	    if(shouldOpenNew)
	    {
		ChatWindow newchat = new ChatWindow(this, NetworkUtils.getUser().getInt("ID"), ID);
		chatWindows.add(newchat);
		newchat.setVisible(true);
	    }
        } 
    }//GEN-LAST:event_ContactsListMouseClicked

    private void LogoutMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_LogoutMouseClicked
    {//GEN-HEADEREND:event_LogoutMouseClicked
	Main.getConfig().CreateDefaultConfigFile();
	try
	{
	    Main.getConfig().SaveConfigFileToDisk(Main.getConfig());
	} 
	catch (IOException ex)
	{
	    Logger.getLogger(MessengerMainWindow.class.getName()).log(Level.SEVERE, null, ex);
	}
	new Main().setVisible(true);
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

    }//GEN-LAST:event_LogoutMouseClicked

    private void LogoutMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_LogoutMouseExited
    {//GEN-HEADEREND:event_LogoutMouseExited
        Logout.setText("<HTML>Logout</HTML>");
    }//GEN-LAST:event_LogoutMouseExited

    private void LogoutMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_LogoutMouseEntered
    {//GEN-HEADEREND:event_LogoutMouseEntered
        Logout.setText("<HTML><u>Logout</u></HTML>");
    }//GEN-LAST:event_LogoutMouseEntered

    private void formMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_formMouseMoved
    {//GEN-HEADEREND:event_formMouseMoved
        mouseAFKDetector.actionPerformed(new ActionEvent(this, 1, "UpdateStatus"));
    }//GEN-LAST:event_formMouseMoved

    private void UserProfilePictureMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_UserProfilePictureMouseClicked
    {//GEN-HEADEREND:event_UserProfilePictureMouseClicked
        if(SwingUtilities.isRightMouseButton(evt))
	{
	    MessengerMainWindow thisWindow = this;
	    JMenuItem item = new JMenuItem("Change Picture");
	    JMenuItem item2 = new JMenuItem("Open settings");
	    PopupMenu = new JPopupMenu();
	    PopupMenu.add(item);
	    PopupMenu.add(item2);
	    PopupMenu.show(this, evt.getX(), evt.getY()+40);
	    item.addActionListener(new ActionListener()
	    {
		@Override
		public void actionPerformed(ActionEvent e)
		{
		    ProfileImageUpWindow window = new ProfileImageUpWindow(thisWindow);
		    window.setVisible(true);
		    
		}
	    });
	    item2.addActionListener(new ActionListener()
	    {
		@Override
		public void actionPerformed(ActionEvent e)
		{
		    OptionsWindow window = new OptionsWindow(thisWindow);
		    window.setUser(userDetailToLoad);
		    window.setVisible(true);
		}
	    });
	}
    }//GEN-LAST:event_UserProfilePictureMouseClicked
    
    private void RemoveLabelAbout()
    {
	AboutEdit.setText(AboutLabel.getText());
	Outer.remove(AboutLabel);
	Outer.add(AboutEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 40, -1, -1));
	pack();
    }
    
    private void RemoveEditAbout()
    {
	AboutLabel.setText(AboutEdit.getText());
	Outer.remove(AboutEdit);
	Outer.add(AboutLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 40, -1, -1));
	Outer.repaint();
	pack();
	updateUserAbout(AboutLabel.getText(), 0);
    }
    
    private void updateUserStatusInChat()
    {
	for(int i = 0; i < chatWindows.size(); i++)
	{
	    chatWindows.get(i).updateCurrUserStatus(status);
	}
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AboutEdit;
    private javax.swing.JLabel AboutLabel;
    private javax.swing.JList<User> ContactsList;
    private javax.swing.JScrollPane ContactsScollPanel;
    private javax.swing.JComboBox<String> CurrentStatusCombo;
    private javax.swing.JLabel Logout;
    private javax.swing.JLabel NameLabel;
    private javax.swing.JPanel Outer;
    private javax.swing.JLabel PeopleCount;
    private javax.swing.JLabel PeopleLabel;
    private javax.swing.JPopupMenu PopupMenu;
    private com.toddy.msnclientgui.views.JCustomSeparator Separator;
    private com.toddy.msnclientgui.views.JImage UserProfilePicture;
    // End of variables declaration//GEN-END:variables

}
