package com.kianama3.console.sections.server;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTabbedPane;

import com.kianama3.console.common.KianamaResourceBundle;


import java.awt.Component;

public class ServerSection extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private JPanel statusPanel;
	private JPanel mangementPanel;

	public ServerSection() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setAlignmentY(Component.TOP_ALIGNMENT);
		tabbedPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(tabbedPane);
		
		statusPanel = new ServerStatusTabPage();
		tabbedPane.addTab(KianamaResourceBundle.getString("server_status_title"), null, statusPanel, null);
		
		mangementPanel = new ServerManagementTabPage();
		tabbedPane.addTab(KianamaResourceBundle.getString("server_management_title"), null, mangementPanel, null);
		
	}

}
