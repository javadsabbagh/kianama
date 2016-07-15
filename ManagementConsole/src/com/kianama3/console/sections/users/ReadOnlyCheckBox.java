package com.kianama3.console.sections.users;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;

public class ReadOnlyCheckBox extends JCheckBox {
	private static final long serialVersionUID = 1L;

	public ReadOnlyCheckBox (String text) {
        super(text);
    }

    protected void processKeyEvent(KeyEvent e) {
    }

    protected void processMouseEvent(MouseEvent e) {

    }
    
    public void etSelected(boolean b){
    	super.setSelected(b);
    }
}