/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toddy.msnclientgui.windows;

import com.toddy.msnclientgui.Main;
import com.toddy.msnclientgui.enums.AlertType;
import com.toddy.msnclientgui.enums.ResponseStatus;
import com.toddy.msnclientgui.network.NetworkUtils;
import com.toddy.msnclientgui.utils.Config;
import com.toddy.msnclientgui.utils.Utils;
import com.toddy.msnclientgui.views.JCustomButton;
import com.toddy.msnclientgui.views.RoundedBorder;
import com.toddy.msnclientgui.views.UI.JListItemCellRenderer;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.json.JSONObject;

public class OptionsWindow extends JFrame
{
    private JFrame parentWindow;
    private JSONObject userJson;
    private DocumentListener textFieldChangeListener = new DocumentListener()
    {
	
        public void changedUpdate(DocumentEvent e)
        {
            warn();
        }

        public void removeUpdate(DocumentEvent e)
	{
            warn();
	}

        public void insertUpdate(DocumentEvent e)
        {
            warn();
        }
    };
    
    public OptionsWindow(JFrame parentWindow)
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
	//this.setLocationRelativeTo(parentWindow);
	initComponents();
	
	FileLocationEditText.setText(Main.getConfig().getReceivedFileFolder());
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
    
    public void setUser(JSONObject user)
    {
	this.userJson = user;
	if(userJson != null)
	{
	    EditNameButton.setEnabled(true);
	    EditNameLabel.setText(EditNameLabel.getText() + " " + userJson.getString("Username"));
	    PersonalPanel.add(DeleteAccountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 425, -1, -1));
	}
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        Outter = new javax.swing.JPanel();
        OptionScrollPane = new javax.swing.JScrollPane();
        OptionList = new javax.swing.JList<>();
        AreaContent = new com.toddy.msnclientgui.views.JCustomOptionFrame();
        ConnectionPanel = new javax.swing.JPanel();
        ConnectionStatusLabel = new javax.swing.JLabel();
        Separator4 = new javax.swing.JSeparator();
        ServerLabel = new javax.swing.JLabel();
        ServerIPEditText = new javax.swing.JTextField();
        HistoryPanel = new javax.swing.JPanel();
        FileTransferLabel = new javax.swing.JLabel();
        Separator3 = new javax.swing.JSeparator();
        FileLocationLabel = new javax.swing.JLabel();
        FileLocationButton = new com.toddy.msnclientgui.views.JCustomButton();
        FileLocationEditText = new javax.swing.JTextField();
        PersonalPanel = new javax.swing.JPanel();
        DeleteAccountLabel = new javax.swing.JLabel();
        YourProfileLabel = new javax.swing.JLabel();
        Separator1 = new javax.swing.JSeparator();
        StatusLabel = new javax.swing.JLabel();
        Separator2 = new javax.swing.JSeparator();
        EditNameLabel = new javax.swing.JLabel();
        EditNameButton = new com.toddy.msnclientgui.views.JCustomButton();
        MinutesForAFK = new javax.swing.JTextField();
        ShowMeAsAway = new com.toddy.msnclientgui.views.JCustomCheckBox();
        MinutesForAFKLabel = new javax.swing.JLabel();
        OkButton = new com.toddy.msnclientgui.views.JCustomButton();
        CancelButton = new com.toddy.msnclientgui.views.JCustomButton();
        ApplyButton = new com.toddy.msnclientgui.views.JCustomButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(590, 500));

        Outter.setBackground(new java.awt.Color(252, 252, 252));
        Outter.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Outter.setMaximumSize(new java.awt.Dimension(590, 500));
        Outter.setPreferredSize(new java.awt.Dimension(590, 500));
        Outter.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        OptionScrollPane.setBackground(new java.awt.Color(252, 252, 252));
        OptionScrollPane.setForeground(new java.awt.Color(0, 0, 0));
        OptionScrollPane.setPreferredSize(new java.awt.Dimension(96, 330));

        OptionList.setBackground(new java.awt.Color(255, 255, 255));
        OptionList.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        OptionList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        OptionList.setForeground(new java.awt.Color(0, 0, 0));
        OptionList.setModel(new javax.swing.AbstractListModel<String>()
        {
            String[] strings = { " Personal", " History", " Connection" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        OptionList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        OptionList.setCellRenderer(new JListItemCellRenderer());
        OptionList.setFixedCellHeight(25);
        OptionList.setSelectedIndex(2);
        OptionList.setSelectionBackground(new java.awt.Color(51, 153, 255));
        OptionList.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                OptionListValueChanged(evt);
            }
        });
        OptionScrollPane.setViewportView(OptionList);

        Outter.add(OptionScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 460));

        AreaContent.setBackground(new java.awt.Color(252, 252, 252));
        AreaContent.setBorderColor(new java.awt.Color(213, 223, 229));
        AreaContent.setBorderText("Connection");
        AreaContent.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        AreaContent.setPreferredSize(new java.awt.Dimension(477, 465));
        AreaContent.setRequestFocusEnabled(false);
        AreaContent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ConnectionPanel.setBackground(new java.awt.Color(252, 252, 252));
        ConnectionPanel.setForeground(new java.awt.Color(160, 160, 160));
        ConnectionPanel.setPreferredSize(new java.awt.Dimension(467, 315));
        ConnectionPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ConnectionStatusLabel.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        ConnectionStatusLabel.setForeground(new java.awt.Color(0, 0, 0));
        ConnectionStatusLabel.setText("Status");
        ConnectionPanel.add(ConnectionStatusLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        Separator4.setBackground(new java.awt.Color(160, 160, 160));
        Separator4.setForeground(new java.awt.Color(160, 160, 160));
        Separator4.setPreferredSize(new java.awt.Dimension(365, 1));
        ConnectionPanel.add(Separator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 17, -1, -1));

        ServerLabel.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        ServerLabel.setForeground(new java.awt.Color(0, 0, 0));
        ServerLabel.setText("Server IP:");
        ConnectionPanel.add(ServerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 35, -1, -1));

        ServerIPEditText.setBorder(new RoundedBorder(new Color(171,187,208), 1, MinutesForAFK.getInsets()));
        ServerIPEditText.setText(Main.getConfig().getServerIPAdress());
        ServerIPEditText.setForeground(new java.awt.Color(0, 0, 0));
        ServerIPEditText.setAutoscrolls(false);
        ServerIPEditText.setPreferredSize(new java.awt.Dimension(29, 20));
        ServerIPEditText.getDocument().addDocumentListener(textFieldChangeListener);
        ConnectionPanel.add(ServerIPEditText, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 350, -1));

        AreaContent.add(ConnectionPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 15, -1, 440));

        HistoryPanel.setBackground(new java.awt.Color(252, 252, 252));
        HistoryPanel.setForeground(new java.awt.Color(160, 160, 160));
        HistoryPanel.setPreferredSize(new java.awt.Dimension(467, 315));
        HistoryPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        FileTransferLabel.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        FileTransferLabel.setForeground(new java.awt.Color(0, 0, 0));
        FileTransferLabel.setText("File transfer");
        HistoryPanel.add(FileTransferLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        Separator3.setBackground(new java.awt.Color(160, 160, 160));
        Separator3.setForeground(new java.awt.Color(160, 160, 160));
        Separator3.setPreferredSize(new java.awt.Dimension(365, 1));
        HistoryPanel.add(Separator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 17, -1, -1));

        FileLocationLabel.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        FileLocationLabel.setForeground(new java.awt.Color(0, 0, 0));
        FileLocationLabel.setText("Save files I receive in this folder:");
        HistoryPanel.add(FileLocationLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 35, -1, -1));

        FileLocationButton.setBackground(new java.awt.Color(252, 252, 252));
        FileLocationButton.setForeground(new java.awt.Color(0, 0, 0));
        FileLocationButton.setText("Change...");
        FileLocationButton.setFocusable(false);
        FileLocationButton.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        FileLocationButton.setPreferredSize(new java.awt.Dimension(88, 21));
        FileLocationButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                FileLocationButtonMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                FileLocationButtonMouseExited(evt);
            }
        });
        HistoryPanel.add(FileLocationButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, -1, -1));

        FileLocationEditText.setBorder(new RoundedBorder(new Color(171,187,208), 1, MinutesForAFK.getInsets()));
        FileLocationEditText.setEditable(false);
        FileLocationEditText.setForeground(new java.awt.Color(0, 0, 0));
        FileLocationEditText.setAutoscrolls(false);
        FileLocationEditText.setPreferredSize(new java.awt.Dimension(29, 20));
        HistoryPanel.add(FileLocationEditText, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 280, -1));

        AreaContent.add(HistoryPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 15, -1, -1));
        AreaContent.remove(HistoryPanel);

        PersonalPanel.setBackground(new java.awt.Color(252, 252, 252));
        PersonalPanel.setForeground(new java.awt.Color(160, 160, 160));
        PersonalPanel.setMinimumSize(new java.awt.Dimension(467, 445));
        PersonalPanel.setPreferredSize(new java.awt.Dimension(467, 445));
        PersonalPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        DeleteAccountLabel.setBackground(new java.awt.Color(0, 0, 0));
        DeleteAccountLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        DeleteAccountLabel.setForeground(new java.awt.Color(255, 50, 50));
        DeleteAccountLabel.setText("Delete Account");
        DeleteAccountLabel.setToolTipText("");
        DeleteAccountLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DeleteAccountLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                DeleteAccountLabelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                DeleteAccountLabelMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                DeleteAccountLabelMouseEntered(evt);
            }
        });
        PersonalPanel.add(DeleteAccountLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 425, -1, -1));

        YourProfileLabel.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        YourProfileLabel.setForeground(new java.awt.Color(0, 0, 0));
        YourProfileLabel.setText("Your profile");
        PersonalPanel.add(YourProfileLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        Separator1.setBackground(new java.awt.Color(160, 160, 160));
        Separator1.setForeground(new java.awt.Color(160, 160, 160));
        Separator1.setPreferredSize(new java.awt.Dimension(365, 1));
        PersonalPanel.add(Separator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 17, -1, -1));

        StatusLabel.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        StatusLabel.setForeground(new java.awt.Color(0, 0, 0));
        StatusLabel.setText("Status");
        PersonalPanel.add(StatusLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));

        Separator2.setBackground(new java.awt.Color(160, 160, 160));
        Separator2.setForeground(new java.awt.Color(160, 160, 160));
        Separator2.setPreferredSize(new java.awt.Dimension(385, 1));
        PersonalPanel.add(Separator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 177, -1, -1));

        EditNameLabel.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        EditNameLabel.setForeground(new java.awt.Color(0, 0, 0));
        EditNameLabel.setText("Name:");
        PersonalPanel.add(EditNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 35, -1, -1));

        EditNameButton.setBackground(new java.awt.Color(252, 252, 252));
        EditNameButton.setForeground(new java.awt.Color(0, 0, 0));
        EditNameButton.setText("Edit name");
        EditNameButton.setEnabled(false);
        EditNameButton.setFocusable(false);
        EditNameButton.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        EditNameButton.setPreferredSize(new java.awt.Dimension(70, 21));
        EditNameButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                EditNameButtonMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                EditNameButtonMouseExited(evt);
            }
        });
        PersonalPanel.add(EditNameButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(348, 30, -1, -1));

        MinutesForAFK.setBorder(new RoundedBorder(new Color(171,187,208), 1, MinutesForAFK.getInsets()));
        MinutesForAFK.setForeground(new java.awt.Color(0, 0, 0));
        MinutesForAFK.setText(Integer.toString(Main.getConfig().getMinutesForAFK()));
        MinutesForAFK.setAutoscrolls(false);
        MinutesForAFK.getDocument().addDocumentListener(textFieldChangeListener);
        MinutesForAFK.setPreferredSize(new java.awt.Dimension(29, 20));
        PersonalPanel.add(MinutesForAFK, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 190, -1, -1));

        ShowMeAsAway.setBackground(new java.awt.Color(252, 252, 252));
        ShowMeAsAway.setForeground(new java.awt.Color(0, 0, 0));
        ShowMeAsAway.setSelected(true);
        ShowMeAsAway.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        ShowMeAsAway.setLabel("Show me as \"Away\" when I'm inactive for");
        ShowMeAsAway.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                ShowMeAsAwayStateChanged(evt);
            }
        });
        if(!Main.getConfig().shouldShowMeAsAwayFor())
        {
            MinutesForAFK.setEnabled(false);
        }
        PersonalPanel.add(ShowMeAsAway, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 230, 20));

        MinutesForAFKLabel.setBackground(new java.awt.Color(252, 252, 252));
        MinutesForAFKLabel.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        MinutesForAFKLabel.setForeground(new java.awt.Color(0, 0, 0));
        MinutesForAFKLabel.setText("minutes");
        MinutesForAFKLabel.setPreferredSize(new java.awt.Dimension(50, 20));
        PersonalPanel.add(MinutesForAFKLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(315, 190, -1, -1));

        AreaContent.add(PersonalPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 15, -1, -1));
        AreaContent.remove(PersonalPanel);

        Outter.add(AreaContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 5, -1, -1));

        OkButton.setBackground(new java.awt.Color(252, 252, 252));
        OkButton.setForeground(new java.awt.Color(0, 0, 0));
        OkButton.setText("OK");
        OkButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        OkButton.setPreferredSize(new java.awt.Dimension(70, 20));
        OkButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                OkButtonMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                OkButtonMouseExited(evt);
            }
        });
        Outter.add(OkButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 475, -1, -1));

        CancelButton.setBackground(new java.awt.Color(252, 252, 252));
        CancelButton.setForeground(new java.awt.Color(0, 0, 0));
        CancelButton.setText("Cancel");
        CancelButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        CancelButton.setPreferredSize(new java.awt.Dimension(70, 20));
        CancelButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                CancelButtonMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                CancelButtonMouseExited(evt);
            }
        });
        Outter.add(CancelButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(425, 475, -1, -1));

        ApplyButton.setBackground(new java.awt.Color(252, 252, 252));
        ApplyButton.setForeground(new java.awt.Color(0, 0, 0));
        ApplyButton.setText("Apply");
        ApplyButton.setEnabled(false);
        ApplyButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        ApplyButton.setPreferredSize(new java.awt.Dimension(70, 20));
        ApplyButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                ApplyButtonMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                ApplyButtonMouseExited(evt);
            }
        });
        Outter.add(ApplyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(505, 475, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Outter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Outter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OptionListValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_OptionListValueChanged
    {//GEN-HEADEREND:event_OptionListValueChanged
        AreaContent.removeAll();
	AreaContent.revalidate();
	AreaContent.repaint();
	AreaContent.setBorderText(OptionList.getSelectedValue());
	switch(OptionList.getSelectedIndex())
	{
	    case 0:
		AreaContent.add(PersonalPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 15, -1, -1));
		break;
	    case 1:
		AreaContent.add(HistoryPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 15, -1, -1));
		break;
	    case 2:
		AreaContent.add(ConnectionPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 15, -1, -1));
		break;
	}
    }//GEN-LAST:event_OptionListValueChanged

    private void EditNameButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_EditNameButtonMouseExited
    {//GEN-HEADEREND:event_EditNameButtonMouseExited
        BTNMouseExited(evt);
    }//GEN-LAST:event_EditNameButtonMouseExited

    private void FileLocationButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_FileLocationButtonMouseExited
    {//GEN-HEADEREND:event_FileLocationButtonMouseExited
        BTNMouseExited(evt);
    }//GEN-LAST:event_FileLocationButtonMouseExited

    private void OkButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_OkButtonMouseExited
    {//GEN-HEADEREND:event_OkButtonMouseExited
        BTNMouseExited(evt);
    }//GEN-LAST:event_OkButtonMouseExited

    private void CancelButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_CancelButtonMouseExited
    {//GEN-HEADEREND:event_CancelButtonMouseExited
        BTNMouseExited(evt);
    }//GEN-LAST:event_CancelButtonMouseExited

    private void ApplyButtonMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ApplyButtonMouseExited
    {//GEN-HEADEREND:event_ApplyButtonMouseExited
        BTNMouseExited(evt);
    }//GEN-LAST:event_ApplyButtonMouseExited

    private void FileLocationButtonMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_FileLocationButtonMouseClicked
    {//GEN-HEADEREND:event_FileLocationButtonMouseClicked
        JFileChooser chooser = new JFileChooser();
	chooser.setCurrentDirectory(new File(FileLocationEditText.getText()));
	chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	int returnVal = chooser.showOpenDialog(this);
	if(returnVal == JFileChooser.APPROVE_OPTION) 
	{
	    FileLocationEditText.setText(chooser.getSelectedFile().getAbsolutePath());
	}
    }//GEN-LAST:event_FileLocationButtonMouseClicked

    private void ApplyButtonMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ApplyButtonMouseClicked
    {//GEN-HEADEREND:event_ApplyButtonMouseClicked
        SaveChanges();
    }//GEN-LAST:event_ApplyButtonMouseClicked

    private void OkButtonMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_OkButtonMouseClicked
    {//GEN-HEADEREND:event_OkButtonMouseClicked
        SaveChanges();
	dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_OkButtonMouseClicked

    private void ShowMeAsAwayStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_ShowMeAsAwayStateChanged
    {//GEN-HEADEREND:event_ShowMeAsAwayStateChanged
        warn();
	if(ShowMeAsAway.isSelected())
	    MinutesForAFK.setEnabled(true);
	else
	    MinutesForAFK.setEnabled(false);
    }//GEN-LAST:event_ShowMeAsAwayStateChanged

    private void CancelButtonMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_CancelButtonMouseClicked
    {//GEN-HEADEREND:event_CancelButtonMouseClicked
	dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_CancelButtonMouseClicked

    private void EditNameButtonMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_EditNameButtonMouseClicked
    {//GEN-HEADEREND:event_EditNameButtonMouseClicked
	try
	{
	    String username = JOptionPane.showInputDialog("Please enter your username: ");
	    EditNameLabel.setText("Name: " + username);
	    NetworkUtils.updateUsername(username);
	    ((MessengerMainWindow)parentWindow).setUsername(username);
	} 
	catch (IOException ex)
	{
	    AlertDialog alert = new AlertDialog(this, AlertType.Error, "Oops! Something went wrong.", "An error has occurred while updating your username.<br>Error code: 007");
	    this.setEnabled(false);
	    alert.setVisible(true);
	    Logger.getLogger(OptionsWindow.class.getName()).log(Level.SEVERE, null, ex);
	}
    }//GEN-LAST:event_EditNameButtonMouseClicked

    private void DeleteAccountLabelMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_DeleteAccountLabelMouseClicked
    {//GEN-HEADEREND:event_DeleteAccountLabelMouseClicked
        try
	{
	    int r = JOptionPane.showConfirmDialog(this, "Are you sure you want to do this?", "Delete account", JOptionPane.YES_NO_OPTION);
	    if(r == JOptionPane.YES_OPTION)
	    {
		JSONObject response = NetworkUtils.deleteAccount();
		if(response.getEnum(ResponseStatus.class, "Status") == ResponseStatus.OK)
		{	
		    AlertDialog alert = new AlertDialog(this, AlertType.Error, "Goodbye", "Your account has been deleted and you will be signed of.");
		    this.setEnabled(false);
		    alert.setVisible(true);
		    new Main().setVisible(true);
		    dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		    parentWindow.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
	    }
	} 
	catch (IOException ex)
	{
	    AlertDialog alert = new AlertDialog(this, AlertType.Error, "Oops! Something went wrong.", "An error has occurred while updating your username.<br>Error code: 007");
	    this.setEnabled(false);
	    alert.setVisible(true);
	    Logger.getLogger(OptionsWindow.class.getName()).log(Level.SEVERE, null, ex);
	}
    }//GEN-LAST:event_DeleteAccountLabelMouseClicked

    private void DeleteAccountLabelMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_DeleteAccountLabelMouseExited
    {//GEN-HEADEREND:event_DeleteAccountLabelMouseExited
        DeleteAccountLabel.setText("<HTML>Delete Account</HTML>");
    }//GEN-LAST:event_DeleteAccountLabelMouseExited

    private void DeleteAccountLabelMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_DeleteAccountLabelMouseEntered
    {//GEN-HEADEREND:event_DeleteAccountLabelMouseEntered
        DeleteAccountLabel.setText("<HTML><u>Delete Account</u></HTML>");
    }//GEN-LAST:event_DeleteAccountLabelMouseEntered
    
    private void SaveChanges()
    {
	try
	{
	    Main.getConfig().setReceivedFileFolder(FileLocationEditText.getText());
	    Main.getConfig().setServerIPAdress(ServerIPEditText.getText());
	    Main.getConfig().setShowMeAsAwayFor(ShowMeAsAway.isSelected());
	    Main.getConfig().setMinutesForAFK(Integer.parseInt(MinutesForAFK.getText()));
	
	    Config.SaveConfigFileToDisk(Main.getConfig());
	    ApplyButton.setEnabled(false);
	} 
	catch (IOException ex)
	{
	    AlertDialog alert = new AlertDialog(this, AlertType.Error, "Oops! Something went wrong.", "An error has occurred while saving changes.<br>Error code: 002");
	    this.setEnabled(false);
	    alert.setVisible(true);
	    Logger.getLogger(OptionsWindow.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    private void BTNMouseExited(java.awt.event.MouseEvent evt)
    {
	((JCustomButton)evt.getSource()).mouseExit();
    }
    
    public void warn()
    {
        ApplyButton.setEnabled(true);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toddy.msnclientgui.views.JCustomButton ApplyButton;
    private com.toddy.msnclientgui.views.JCustomOptionFrame AreaContent;
    private com.toddy.msnclientgui.views.JCustomButton CancelButton;
    private javax.swing.JPanel ConnectionPanel;
    private javax.swing.JLabel ConnectionStatusLabel;
    private javax.swing.JLabel DeleteAccountLabel;
    private com.toddy.msnclientgui.views.JCustomButton EditNameButton;
    private javax.swing.JLabel EditNameLabel;
    private com.toddy.msnclientgui.views.JCustomButton FileLocationButton;
    private javax.swing.JTextField FileLocationEditText;
    private javax.swing.JLabel FileLocationLabel;
    private javax.swing.JLabel FileTransferLabel;
    private javax.swing.JPanel HistoryPanel;
    private javax.swing.JTextField MinutesForAFK;
    private javax.swing.JLabel MinutesForAFKLabel;
    private com.toddy.msnclientgui.views.JCustomButton OkButton;
    private javax.swing.JList<String> OptionList;
    private javax.swing.JScrollPane OptionScrollPane;
    private javax.swing.JPanel Outter;
    private javax.swing.JPanel PersonalPanel;
    private javax.swing.JSeparator Separator1;
    private javax.swing.JSeparator Separator2;
    private javax.swing.JSeparator Separator3;
    private javax.swing.JSeparator Separator4;
    private javax.swing.JTextField ServerIPEditText;
    private javax.swing.JLabel ServerLabel;
    private com.toddy.msnclientgui.views.JCustomCheckBox ShowMeAsAway;
    private javax.swing.JLabel StatusLabel;
    private javax.swing.JLabel YourProfileLabel;
    // End of variables declaration//GEN-END:variables
}
