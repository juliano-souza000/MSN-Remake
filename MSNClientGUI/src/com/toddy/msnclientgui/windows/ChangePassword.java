package com.toddy.msnclientgui.windows;

import com.toddy.msnclientgui.enums.AlertType;
import com.toddy.msnclientgui.enums.ResponseStatus;
import com.toddy.msnclientgui.network.NetworkUtils;
import com.toddy.msnclientgui.utils.Utils;
import com.toddy.msnclientgui.views.RoundedBorder;
import com.toddy.msnclientgui.views.UI.JPasswordFieldHintUI;
import com.toddy.msnclientgui.views.UI.JTextFieldHintUI;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.json.JSONObject;

public class ChangePassword extends javax.swing.JFrame
{
    private JFrame parentWindow;
    public ChangePassword(JFrame parentWindow)
    {
	this.parentWindow = parentWindow;
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
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        Outter = new javax.swing.JPanel();
        Title = new javax.swing.JLabel();
        EmailLabel = new javax.swing.JLabel();
        EmailField = new javax.swing.JTextField();
        PasswordLabel = new javax.swing.JLabel();
        PasswordField = new javax.swing.JPasswordField();
        SignUpButton = new com.toddy.msnclientgui.views.JCustomButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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
        Outter.setPreferredSize(new java.awt.Dimension(210, 300));
        Outter.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Title.setBackground(new java.awt.Color(252, 252, 252));
        Title.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        Title.setForeground(new java.awt.Color(0, 0, 0));
        Title.setText("Forgot password");
        Outter.add(Title, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 30, -1, -1));

        EmailLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        EmailLabel.setForeground(new java.awt.Color(0, 0, 0));
        EmailLabel.setText("Email:");
        EmailLabel.setPreferredSize(new java.awt.Dimension(60, 14));
        Outter.add(EmailLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 80, -1, -1));

        EmailField.setUI(new JTextFieldHintUI("example555@mesquita.com.br", Color.gray));
        EmailField.setBorder(new RoundedBorder(new Color(171,187,208), 1, EmailField.getInsets()));
        EmailField.setForeground(new java.awt.Color(0, 0, 0));
        EmailField.setMaximumSize(new java.awt.Dimension(180, 24));
        EmailField.setMinimumSize(new java.awt.Dimension(180, 24));
        EmailField.setPreferredSize(new java.awt.Dimension(180, 24));
        Outter.add(EmailField, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 97, -1, -1));

        PasswordLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        PasswordLabel.setForeground(new java.awt.Color(0, 0, 0));
        PasswordLabel.setText("Password:");
        PasswordLabel.setPreferredSize(new java.awt.Dimension(60, 14));
        Outter.add(PasswordLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 127, -1, -1));

        PasswordField.setMinimumSize(new java.awt.Dimension(180, 24));
        PasswordField.setPreferredSize(new java.awt.Dimension(180, 24));
        PasswordField.setUI(new JPasswordFieldHintUI("Password", Color.gray));
        PasswordField.setBorder(new RoundedBorder(new Color(171,187,208), 1, PasswordField.getInsets()));
        Outter.add(PasswordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 147, -1, -1));

        SignUpButton.setBackground(new java.awt.Color(252, 252, 252));
        SignUpButton.setForeground(new java.awt.Color(0, 0, 0));
        SignUpButton.setText("Sign Up");
        SignUpButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        SignUpButton.setMaximumSize(new java.awt.Dimension(74, 21));
        SignUpButton.setMinimumSize(new java.awt.Dimension(74, 21));
        SignUpButton.setPreferredSize(new java.awt.Dimension(74, 21));
        SignUpButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                SignUpButtonMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                SignUpButtonMouseExited(evt);
            }
        });
        Outter.add(SignUpButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 183, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Outter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Outter, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SignUpButtonMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SignUpButtonMouseClicked
    {//GEN-HEADEREND:event_SignUpButtonMouseClicked
        String pass = new String(PasswordField.getPassword());
        boolean isEmailOk = Utils.isValidEmailAddress(EmailField.getText());
        boolean isPassOk = (pass != null && !pass.isEmpty());
        if(isEmailOk && isPassOk)
        {
            EmailField.setBorder(new RoundedBorder(new Color(171,187,208), 1, EmailField.getInsets()));
            PasswordField.setBorder(new RoundedBorder(new Color(171,187,208), 1, PasswordField.getInsets()));
            try
            {
                NetworkUtils.connect();
                JSONObject response = NetworkUtils.changePass(EmailField.getText(), pass);
                NetworkUtils.disconnect();
                if(response.getEnum(ResponseStatus.class, "Status") == ResponseStatus.Authorized)
                {
                    JOptionPane.showMessageDialog(this, "Your password was changed up. Please, log in!",  "Live Messenger", JOptionPane.INFORMATION_MESSAGE);
                    dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                }
                else if(response.getEnum(ResponseStatus.class, "Status") == ResponseStatus.InternalError)
                {
                    throw new Exception("Internal error");
                }
            }
            catch (Exception ex)
            {
		AlertDialog dialog = new AlertDialog(this, AlertType.Warning, "Could not change password", "An error has occurred while trying to change password<br>Error Code: 008");
                this.setEnabled(false);
		dialog.setVisible(true);
		Logger.getLogger(ChangePassword.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            if(!isEmailOk)
            {
                EmailField.setBorder(new RoundedBorder(Color.RED, 1, EmailField.getInsets()));
            }
            else
            {
                EmailField.setBorder(new RoundedBorder(new Color(171,187,208), 1, EmailField.getInsets()));
            }

            if(!isPassOk)
            {
                PasswordField.setBorder(new RoundedBorder(Color.RED, 1, PasswordField.getInsets()));
            }
            else
            {
                PasswordField.setBorder(new RoundedBorder(new Color(171,187,208), 1, PasswordField.getInsets()));
            }
        }
    }//GEN-LAST:event_SignUpButtonMouseClicked

    private void SignUpButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_SignUpButtonMouseExited
    {//GEN-HEADEREND:event_SignUpButtonMouseExited
        SignUpButton.mouseExit();
    }//GEN-LAST:event_SignUpButtonMouseExited

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField EmailField;
    private javax.swing.JLabel EmailLabel;
    private javax.swing.JPanel Outter;
    private javax.swing.JPasswordField PasswordField;
    private javax.swing.JLabel PasswordLabel;
    private com.toddy.msnclientgui.views.JCustomButton SignUpButton;
    private javax.swing.JLabel Title;
    // End of variables declaration//GEN-END:variables
}
