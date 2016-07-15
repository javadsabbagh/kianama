package com.kianama3.console;

import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JTable;

public class JournalDialog extends JDialog{
	private JTable table;
	public JournalDialog() {
		getContentPane().setLayout(null);
		
		JButton btnClose = new JButton("close");
		btnClose.setBounds(575, 349, 89, 23);
		getContentPane().add(btnClose);
		
		table = new JTable();
		table.setBounds(10, 11, 654, 327);
		getContentPane().add(table);
	}
}
