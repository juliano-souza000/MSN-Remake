package com.toddy.msnclientgui.views;

import com.toddy.msnclientgui.listeners.SubmitListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.plaf.basic.BasicTextAreaUI;
import javax.swing.text.JTextComponent;

public class TypeMessageLayout extends JTextArea
{
    private static final String TEXT_SUBMIT = "text-submit";
    private static final String INSERT_BREAK = "insert-break";
    private SubmitListener submitListener;
    
    public TypeMessageLayout()
    {
	InputMap input = this.getInputMap();
	KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
	KeyStroke shiftEnter = KeyStroke.getKeyStroke("shift ENTER");
	input.put(shiftEnter, INSERT_BREAK);  // input.get(enter)) = "insert-break"
	input.put(enter, TEXT_SUBMIT);
	
	ActionMap actions = this.getActionMap();
	actions.put(TEXT_SUBMIT, new AbstractAction() 
	{
	    @Override
	    public void actionPerformed(ActionEvent e) 
	    {
		if(submitListener != null)
		{
		    submitListener.SubmitPerformed(this);
		}
	    }
	});
	setUI(new TypeMessageHintUI("Type something here...", Color.GRAY));
    }
    
    public void setSubmitListener(SubmitListener submitListener)
    {
	this.submitListener = submitListener;
    }
    
    private class TypeMessageHintUI extends BasicTextAreaUI implements FocusListener
    {
	private String hint;
	private Color  hintColor;

	public TypeMessageHintUI(String hint, Color hintColor) 
	{
	    this.hint = hint;
	    this.hintColor = hintColor;
	}	
	
	private void repaint() 
	{
	    if (getComponent() != null) 
	    {
		getComponent().repaint();
	    }
	}

	@Override
	protected void paintSafely(Graphics g) 
	{
        // Render the default text field UI
		super.paintSafely(g);
	    // Render the hint text
	    JTextComponent component = getComponent();
	    if (component.getText().length() == 0 && !component.hasFocus()) 
	    {
	        g.setColor(hintColor);
	        int padding = (component.getHeight() - component.getFont().getSize()) / 2;
	        int inset = 3;
	        g.drawString(hint, inset, component.getHeight() - padding - inset);
	    }
	}

	@Override
	public void focusGained(FocusEvent e) 
	{
	    repaint();
	}

	@Override
	public void focusLost(FocusEvent e) 
	{
	    repaint();
	}

	@Override
	public void installListeners() 
	{
	    super.installListeners();
	    getComponent().addFocusListener(this);
	}

	@Override
	public void uninstallListeners() 
	{
	    super.uninstallListeners();
	    getComponent().removeFocusListener(this);
	}
    }
}