
package com.toddy.msnclientgui;

import com.toddy.msnclientgui.enums.AlertType;
import com.toddy.msnclientgui.enums.ResponseStatus;
import com.toddy.msnclientgui.enums.Status;
import com.toddy.msnclientgui.listeners.BoundsPopupMenuListener;
import com.toddy.msnclientgui.network.NetworkUtils;
import com.toddy.msnclientgui.utils.Config;
import com.toddy.msnclientgui.utils.StatusIconUtils;
import com.toddy.msnclientgui.utils.Utils;
import com.toddy.msnclientgui.views.JCustomButton;
import com.toddy.msnclientgui.views.JImage;
import com.toddy.msnclientgui.views.ProfilePictureBorder;
import com.toddy.msnclientgui.views.RoundedBorder;
import com.toddy.msnclientgui.views.UI.IconListRenderer;
import com.toddy.msnclientgui.views.UI.JCustomComboBoxUI;
import com.toddy.msnclientgui.views.UI.JPasswordFieldHintUI;
import com.toddy.msnclientgui.views.UI.JTextFieldHintUI;
import com.toddy.msnclientgui.windows.AlertDialog;
import com.toddy.msnclientgui.windows.ChangePassword;
import com.toddy.msnclientgui.windows.MessengerMainWindow;
import com.toddy.msnclientgui.windows.OptionsWindow;
import com.toddy.msnclientgui.windows.SignUp;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import org.json.JSONObject;


public class Main extends javax.swing.JFrame
{
    private static Config ConfigFile;
    
    public Main()
    {	
	try
	{
	    ConfigFile = Config.ReadConfigFileFromDisk();
	} 
	catch (IOException ex)
	{
	    ConfigFile = new Config();
	    ConfigFile.CreateDefaultConfigFile();
	    try
	    {
		Config.SaveConfigFileToDisk(ConfigFile);
	    } 
	    catch (IOException exx)
	    {
		Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, exx);
	    }
	} 
	catch (ClassNotFoundException ex)
	{
	    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	}

	initComponents();   
	setSize(590, 500);
	TitleP1.requestFocus();
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	try
	{
	    setIconImage(Utils.getAppIcon());
	} 
	catch (IOException ex)
	{
	    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	}
	//pack();
    }
    
    public static Config getConfig()
    {
	return ConfigFile;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jLabel1 = new javax.swing.JLabel();
        Outer = new com.toddy.msnclientgui.views.JImage();
        UserProfilePicture = new com.toddy.msnclientgui.views.JImage();
        TitleP1 = new javax.swing.JLabel();
        TitleP2 = new javax.swing.JLabel();
        TitleP3 = new javax.swing.JLabel();
        EmailField = new javax.swing.JTextField();
        PasswordField = new javax.swing.JPasswordField();
        ForgotPassword = new javax.swing.JLabel();
        SignInAsLabel = new javax.swing.JLabel();
        SignInAsCombo = new javax.swing.JComboBox<>();
        RememberMeCheckbox = new com.toddy.msnclientgui.views.JCustomCheckBox();
        SignInAutoCheckbox = new com.toddy.msnclientgui.views.JCustomCheckBox();
        OptionLabel = new javax.swing.JLabel();
        SignInBTN = new com.toddy.msnclientgui.views.JCustomButton();
        CancelBTN = new com.toddy.msnclientgui.views.JCustomButton();
        SignUpP1 = new javax.swing.JLabel();
        SignUpP2 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Live Messenger");
        setMinimumSize(new java.awt.Dimension(590, 500));
        setResizable(false);
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
            Rectangle rect = new Rectangle(600, 110);
            Image backgroundCropped = backgroundToCrop.getSubimage(300, 0, rect.width, rect.height);
            Outer = new com.toddy.msnclientgui.views.JImage(backgroundCropped);
            //UserProfilePicture.setIcon(new ImageIcon(userProfilePicResized));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Outer.setBackground(new java.awt.Color(255, 255, 255));
        Outer.setForeground(new java.awt.Color(255, 255, 255));
        Outer.setToolTipText("");
        Outer.setMaximumSize(new java.awt.Dimension(590, 500));
        Outer.setMinimumSize(new java.awt.Dimension(590, 500));
        Outer.setPreferredSize(new java.awt.Dimension(590, 500));
        Outer.setPreferredSize(new java.awt.Dimension(590, 500));

        Outer.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 20));
        Outer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        try
        {
            BufferedImage userProfilePic = ImageIO.read(getClass().getClassLoader().getResource("com/toddy/msnclientgui/resources/images/default_profile_picture.png"));
            Image userProfilePicResized = userProfilePic.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            UserProfilePicture = new JImage(userProfilePicResized, true, Status.Available,9);
            //UserProfilePicture.setIcon(new ImageIcon(userProfilePicResized));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        UserProfilePicture.setBackground(new java.awt.Color(255, 255, 255));
        UserProfilePicture.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        UserProfilePicture.setForeground(new java.awt.Color(0, 0, 0));
        UserProfilePicture.setMaximumSize(new java.awt.Dimension(120, 120));
        UserProfilePicture.setMinimumSize(new java.awt.Dimension(120, 120));
        UserProfilePicture.setName(""); // NOI18N
        UserProfilePicture.setPreferredSize(new java.awt.Dimension(120, 120));
        UserProfilePicture.setBackground(new java.awt.Color(0, 0, 0));
        UserProfilePicture.setForeground(new java.awt.Color(0, 0, 0));

        UserProfilePicture.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        UserProfilePicture.setOpaque(false);
        UserProfilePicture.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        Outer.add(UserProfilePicture, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, -1, -1));

        TitleP1.setFont(new java.awt.Font("Arial", 0, 28)); // NOI18N
        TitleP1.setForeground(new java.awt.Color(42, 42, 42));
        TitleP1.setText("Sign in to");
        Outer.add(TitleP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 140, 40));

        TitleP2.setFont(new java.awt.Font("Arial", 0, 28)); // NOI18N
        TitleP2.setForeground(new java.awt.Color(42, 42, 42));
        TitleP2.setText("Windows Live ");
        Outer.add(TitleP2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 200, 40));

        TitleP3.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        TitleP3.setForeground(new java.awt.Color(42, 42, 42));
        TitleP3.setText("Messenger");
        Outer.add(TitleP3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, -1, 40));

        EmailField.setUI(new JTextFieldHintUI("example555@mesquita.com.br", Color.gray));
        EmailField.setBorder(new RoundedBorder(new Color(171,187,208), 1, EmailField.getInsets()));
        EmailField.setText(ConfigFile.getLastUserLoginUsername());
        EmailField.setPreferredSize(new java.awt.Dimension(270, 24));
        Outer.add(EmailField, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, -1, -1));

        PasswordField.setUI(new JPasswordFieldHintUI("Password", Color.gray));
        PasswordField.setBorder(new RoundedBorder(new Color(171,187,208), 1, PasswordField.getInsets()));
        PasswordField.setText(ConfigFile.getLastUserPassword());
        PasswordField.setPreferredSize(new java.awt.Dimension(270, 24));
        Outer.add(PasswordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, -1, -1));

        ForgotPassword.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        ForgotPassword.setForeground(new java.awt.Color(0, 102, 204));
        ForgotPassword.setText("<HTML>Forgot your password?</HTML>");
        ForgotPassword.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ForgotPassword.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                ForgotPasswordMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                ForgotPasswordMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                ForgotPasswordMouseEntered(evt);
            }
        });
        Outer.add(ForgotPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, 270, 20));

        SignInAsLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        SignInAsLabel.setForeground(new java.awt.Color(0, 0, 0));
        SignInAsLabel.setText("Sign in as:");
        Outer.add(SignInAsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 240, -1, -1));

        SignInAsCombo.setBackground(new java.awt.Color(255, 255, 255));
        SignInAsCombo.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        SignInAsCombo.setForeground(new java.awt.Color(0, 0, 0));
        SignInAsCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Available", "Away", "Busy", "Appear offline" }));
        Map<Object, Icon> icons1 = new HashMap<Object, Icon>();
        Map<Object, Icon> icons2 = new HashMap<Object, Icon>();
        try
        {
            icons1.put("Available", com.toddy.msnclientgui.utils.StatusIconUtils.getAvailable1());
            icons1.put("Away",  com.toddy.msnclientgui.utils.StatusIconUtils.getAway1());
            icons1.put("Busy",  com.toddy.msnclientgui.utils.StatusIconUtils.getBusy1());
            icons1.put("Appear offline",  com.toddy.msnclientgui.utils.StatusIconUtils.getOffline1());

            icons2.put("Available", com.toddy.msnclientgui.utils.StatusIconUtils.getAvailable2());
            icons2.put("Away",  com.toddy.msnclientgui.utils.StatusIconUtils.getAway2());
            icons2.put("Busy",  com.toddy.msnclientgui.utils.StatusIconUtils.getBusy2());
            icons2.put("Appear offline",  com.toddy.msnclientgui.utils.StatusIconUtils.getOffline2());
        }
        catch (IOException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        SignInAsCombo.setRenderer(new IconListRenderer(icons1, icons2));
        SignInAsCombo.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        SignInAsCombo.setFocusable(false);
        SignInAsCombo.setMinimumSize(new java.awt.Dimension(50, 20));
        SignInAsCombo.setPreferredSize(new java.awt.Dimension(95, 20));
        SignInAsCombo.setUI(new JCustomComboBoxUI());

        BoundsPopupMenuListener listener = new BoundsPopupMenuListener(true, false);
        SignInAsCombo.addPopupMenuListener(listener);
        SignInAsCombo.addItemListener(new ItemListener()
            {
                @Override
                public void itemStateChanged(ItemEvent event)
                {
                    if (event.getStateChange() == ItemEvent.SELECTED)
                    {
                        String text = event.getItem().toString();
                        SignInAsCombo.setPrototypeDisplayValue(text);

                        if(text == "Available")
                        {
                            SignInAsCombo.setPreferredSize(new Dimension(95, 20));
                            Outer.validate();
                            Outer.repaint();
                            SignInAsCombo.setUI(new JCustomComboBoxUI());
                            UserProfilePicture.setStatus(Status.Available);
                        }
                        else if(text == "Away")
                        {
                            SignInAsCombo.setPreferredSize(new Dimension(80, 20));
                            Outer.validate();
                            Outer.repaint();
                            SignInAsCombo.setUI(new JCustomComboBoxUI());
                            UserProfilePicture.setStatus(Status.Away);
                        }
                        else if(text == "Busy")
                        {
                            SignInAsCombo.setPreferredSize(new Dimension(75, 20));
                            Outer.validate();
                            Outer.repaint();
                            SignInAsCombo.setUI(new JCustomComboBoxUI());
                            UserProfilePicture.setStatus(Status.Busy);
                        }
                        else
                        {
                            SignInAsCombo.setPreferredSize(new Dimension(120, 20));
                            Outer.validate();
                            Outer.repaint();
                            SignInAsCombo.setUI(new JCustomComboBoxUI());
                            UserProfilePicture.setStatus(Status.Offline);
                        }
                    }
                }
            });

            switch(ConfigFile.getLastStatus())
            {
                case Other:
                case Available:
                SignInAsCombo.setSelectedIndex(0);
                break;
                case Away:
                SignInAsCombo.setSelectedIndex(1);
                break;
                case Busy:
                SignInAsCombo.setSelectedIndex(2);
                break;
                case Offline:
                SignInAsCombo.setSelectedIndex(3);
                break;
            }
            Outer.add(SignInAsCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 237, -1, 20));

            RememberMeCheckbox.setBackground(new java.awt.Color(255, 255, 255));
            RememberMeCheckbox.setBorder(null);
            RememberMeCheckbox.setForeground(new java.awt.Color(0, 0, 0));
            RememberMeCheckbox.setText("Remember my ID and password");
            RememberMeCheckbox.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            RememberMeCheckbox.addItemListener(new java.awt.event.ItemListener()
            {
                public void itemStateChanged(java.awt.event.ItemEvent evt)
                {
                    RememberMeCheckboxItemStateChanged(evt);
                }
            });
            RememberMeCheckbox.setSelected(getConfig().shouldRememberMe());
            Outer.add(RememberMeCheckbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, 220, 20));

            SignInAutoCheckbox.setBackground(new java.awt.Color(255, 255, 255));
            SignInAutoCheckbox.setForeground(new java.awt.Color(0, 0, 0));
            SignInAutoCheckbox.setText("Sign me in automatically");
            SignInAutoCheckbox.setEnabled(false);
            SignInAutoCheckbox.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            if(getConfig().shouldRememberMe())
            {
                SignInAutoCheckbox.setEnabled(true);
            }
            SignInAutoCheckbox.setSelected(getConfig().shouldSignInAuto());
            Outer.add(SignInAutoCheckbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 160, 20));

            OptionLabel.setBackground(new java.awt.Color(255, 255, 255));
            OptionLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            OptionLabel.setForeground(new java.awt.Color(0, 102, 204));
            OptionLabel.setText("Options");
            OptionLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            OptionLabel.addMouseListener(new java.awt.event.MouseAdapter()
            {
                public void mouseClicked(java.awt.event.MouseEvent evt)
                {
                    OptionLabelMouseClicked(evt);
                }
                public void mouseExited(java.awt.event.MouseEvent evt)
                {
                    OptionLabelMouseExited(evt);
                }
                public void mouseEntered(java.awt.event.MouseEvent evt)
                {
                    OptionLabelMouseEntered(evt);
                }
            });
            Outer.add(OptionLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 303, -1, -1));

            SignInBTN.setBackground(new java.awt.Color(255, 255, 255));
            SignInBTN.setForeground(new java.awt.Color(0, 0, 0));
            SignInBTN.setText("Sign In");
            SignInBTN.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            SignInBTN.setPreferredSize(new java.awt.Dimension(72, 23));
            SignInBTN.addMouseListener(new java.awt.event.MouseAdapter()
            {
                public void mouseClicked(java.awt.event.MouseEvent evt)
                {
                    SignInBTNMouseClicked(evt);
                }
                public void mouseExited(java.awt.event.MouseEvent evt)
                {
                    SignInBTNMouseExited(evt);
                }
            });
            Outer.add(SignInBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 330, -1, -1));

            CancelBTN.setBackground(new java.awt.Color(255, 255, 255));
            CancelBTN.setForeground(new java.awt.Color(0, 0, 0));
            CancelBTN.setText("Cancel");
            CancelBTN.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            CancelBTN.setPreferredSize(new java.awt.Dimension(72, 23));
            CancelBTN.addMouseListener(new java.awt.event.MouseAdapter()
            {
                public void mouseClicked(java.awt.event.MouseEvent evt)
                {
                    CancelBTNMouseClicked(evt);
                }
                public void mouseExited(java.awt.event.MouseEvent evt)
                {
                    CancelBTNMouseExited(evt);
                }
            });
            Outer.add(CancelBTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(293, 330, -1, -1));

            SignUpP1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            SignUpP1.setForeground(new java.awt.Color(0, 0, 0));
            SignUpP1.setText("Don't have an account?");
            Outer.add(SignUpP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 380, -1, -1));

            SignUpP2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            SignUpP2.setForeground(new java.awt.Color(0, 102, 204));
            SignUpP2.setText("<HTML>Sign up</HTML>");
            SignUpP2.setToolTipText("");
            SignUpP2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            SignUpP2.addMouseListener(new java.awt.event.MouseAdapter()
            {
                public void mouseClicked(java.awt.event.MouseEvent evt)
                {
                    SignUpP2MouseClicked(evt);
                }
                public void mouseExited(java.awt.event.MouseEvent evt)
                {
                    SignUpP2MouseExited(evt);
                }
                public void mouseEntered(java.awt.event.MouseEvent evt)
                {
                    SignUpP2MouseEntered(evt);
                }
            });
            Outer.add(SignUpP2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 380, -1, -1));

            getContentPane().add(Outer, java.awt.BorderLayout.CENTER);

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void ForgotPasswordMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ForgotPasswordMouseExited
    {//GEN-HEADEREND:event_ForgotPasswordMouseExited
	ForgotPassword.setText("<HTML>Forgot your password?</HTML>");
    }//GEN-LAST:event_ForgotPasswordMouseExited

    private void ForgotPasswordMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ForgotPasswordMouseEntered
    {//GEN-HEADEREND:event_ForgotPasswordMouseEntered
        ForgotPassword.setText("<HTML><U>Forgot your password?</U></HTML>");
    }//GEN-LAST:event_ForgotPasswordMouseEntered

    private void CancelBTNMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_CancelBTNMouseExited
    {//GEN-HEADEREND:event_CancelBTNMouseExited
	BTNMouseExited(evt);
    }//GEN-LAST:event_CancelBTNMouseExited

    private void SignInBTNMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SignInBTNMouseExited
    {//GEN-HEADEREND:event_SignInBTNMouseExited
	BTNMouseExited(evt);
    }//GEN-LAST:event_SignInBTNMouseExited

    private void RememberMeCheckboxItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_RememberMeCheckboxItemStateChanged
    {//GEN-HEADEREND:event_RememberMeCheckboxItemStateChanged
	if(evt.getStateChange() == ItemEvent.SELECTED)
	{
	    SignInAutoCheckbox.setEnabled(true);
	}
	else
	{
	     SignInAutoCheckbox.setEnabled(false);
	}
    }//GEN-LAST:event_RememberMeCheckboxItemStateChanged

    private void SignUpP2MouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SignUpP2MouseEntered
    {//GEN-HEADEREND:event_SignUpP2MouseEntered
        SignUpP2.setText("<HTML><u>Sign up</u></HTML>");
    }//GEN-LAST:event_SignUpP2MouseEntered

    private void SignUpP2MouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SignUpP2MouseExited
    {//GEN-HEADEREND:event_SignUpP2MouseExited
	SignUpP2.setText("<HTML>Sign up</HTML>");
    }//GEN-LAST:event_SignUpP2MouseExited

    private void OptionLabelMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_OptionLabelMouseEntered
    {//GEN-HEADEREND:event_OptionLabelMouseEntered
        OptionLabel.setText("<HTML><u>Options</u></HTML>");
    }//GEN-LAST:event_OptionLabelMouseEntered

    private void OptionLabelMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_OptionLabelMouseExited
    {//GEN-HEADEREND:event_OptionLabelMouseExited
        OptionLabel.setText("<HTML>Options</HTML>");
    }//GEN-LAST:event_OptionLabelMouseExited

    private void OptionLabelMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_OptionLabelMouseClicked
    {//GEN-HEADEREND:event_OptionLabelMouseClicked
	OptionsWindow opt = new OptionsWindow(this);
	this.setEnabled(false);
	opt.setVisible(true);
    }//GEN-LAST:event_OptionLabelMouseClicked

    private void CancelBTNMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_CancelBTNMouseClicked
    {//GEN-HEADEREND:event_CancelBTNMouseClicked
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_CancelBTNMouseClicked

    private void SignInBTNMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SignInBTNMouseClicked
    {//GEN-HEADEREND:event_SignInBTNMouseClicked
        login();
    }//GEN-LAST:event_SignInBTNMouseClicked

    private void login()
    {
	try
	{
	    if(RememberMeCheckbox.isSelected())
	    {
		ConfigFile.setLastUserLoginUsername(EmailField.getText());
		ConfigFile.setLastUserPassword(new String(PasswordField.getPassword()));
		ConfigFile.setShouldRememberMe(RememberMeCheckbox.isSelected());
		ConfigFile.setShouldSignInAuto(SignInAutoCheckbox.isSelected());
		Config.SaveConfigFileToDisk(ConfigFile);
	    }
	    NetworkUtils.connect();
	    JSONObject response = NetworkUtils.login(EmailField.getText(), PasswordField.getText());
	    if(response.getEnum(ResponseStatus.class, "Status") == ResponseStatus.Authorized)
	    {
		JSONObject user = new JSONObject();
		String status = SignInAsCombo.getSelectedItem().toString();
		if(status.equals("Appear offline"))
		{
		    status = "Offline";
		}
		user.put("SessionID", response.get("SessionID"));
		user.put("ID", response.get("ID"));
		new MessengerMainWindow(user, Status.valueOf(status)).setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	    }
	    else
	    {
		AlertDialog alert = new AlertDialog(this, AlertType.Warning, "Something went wrong", "We couldn't sign you in to Live Messenger.<br>This problems occurs when you already logged in or if your email or password is wrong");
		this.setEnabled(false);
		alert.setVisible(true);
		//JOptionPane.showMessageDialog(this, ,  , JOptionPane.WARNING_MESSAGE);
		NetworkUtils.disconnect();
	    }
	}
	catch(IOException e)
	{
	    AlertDialog alert = new AlertDialog(this, AlertType.Error, "Something went wrong", "An error has occurred while you were trying to sign in.<br>Error Code: 001");
	    this.setEnabled(false);
	    alert.setVisible(true);
	    //JOptionPane.showMessageDialog(this, "",  "Live Messenger", JOptionPane.ERROR_MESSAGE);
	    Logger.getLogger(OptionsWindow.class.getName()).log(Level.SEVERE, null, e);
	}
    }
    
    private void SignUpP2MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SignUpP2MouseClicked
    {//GEN-HEADEREND:event_SignUpP2MouseClicked
        SignUp opt = new SignUp(this);
	this.setEnabled(false);
	opt.setVisible(true);
    }//GEN-LAST:event_SignUpP2MouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowOpened
    {//GEN-HEADEREND:event_formWindowOpened
        if(SignInAutoCheckbox.isSelected())
	{
	    login();
	}
    }//GEN-LAST:event_formWindowOpened

    private void ForgotPasswordMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ForgotPasswordMouseClicked
    {//GEN-HEADEREND:event_ForgotPasswordMouseClicked
        if(SwingUtilities.isLeftMouseButton(evt))
	{
	    ChangePassword changePass = new ChangePassword(this);
	    changePass.setVisible(true);
	    this.setEnabled(false);
	    //
	}
    }//GEN-LAST:event_ForgotPasswordMouseClicked

    private void BTNMouseExited(java.awt.event.MouseEvent evt)
    {
	((JCustomButton)evt.getSource()).mouseExit();
    }
    
    public static void main(String args[])
    {
	UIManager.put("ComboBox.background", new ColorUIResource(Color.WHITE));
	UIManager.put("ToggleButton.select", new Color(190, 186, 164));
	/* Set the Nimbus look and feel */
	java.awt.EventQueue.invokeLater(new Runnable()
	{
	    public void run()
	    {
		new Main().setVisible(true);
	    }
	});
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toddy.msnclientgui.views.JCustomButton CancelBTN;
    private javax.swing.JTextField EmailField;
    private javax.swing.JLabel ForgotPassword;
    private javax.swing.JLabel OptionLabel;
    private com.toddy.msnclientgui.views.JImage Outer;
    private javax.swing.JPasswordField PasswordField;
    private com.toddy.msnclientgui.views.JCustomCheckBox RememberMeCheckbox;
    private javax.swing.JComboBox<String> SignInAsCombo;
    private javax.swing.JLabel SignInAsLabel;
    private com.toddy.msnclientgui.views.JCustomCheckBox SignInAutoCheckbox;
    private com.toddy.msnclientgui.views.JCustomButton SignInBTN;
    private javax.swing.JLabel SignUpP1;
    private javax.swing.JLabel SignUpP2;
    private javax.swing.JLabel TitleP1;
    private javax.swing.JLabel TitleP2;
    private javax.swing.JLabel TitleP3;
    private com.toddy.msnclientgui.views.JImage UserProfilePicture;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
