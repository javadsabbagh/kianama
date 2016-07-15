package com.kianama3.console.sections.database;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTabbedPane;

import com.kianama3.console.common.KianamaResourceBundle;


import java.awt.Component;

public class DatabaseSection extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private JPanel statusPanel;
	private JPanel mangementPanel;

	public DatabaseSection() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setAlignmentY(Component.TOP_ALIGNMENT);
		tabbedPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(tabbedPane);
		
		statusPanel = new DatabaseStatusTabPage();
		tabbedPane.addTab(KianamaResourceBundle.getString("database_status_title"), null, statusPanel, null);
		
		mangementPanel = new DatabaseManagementTabPage();
		tabbedPane.addTab(KianamaResourceBundle.getString("database_management_title"), null, mangementPanel, null);
		
	}

}
