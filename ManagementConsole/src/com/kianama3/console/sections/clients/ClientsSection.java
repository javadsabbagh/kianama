package com.kianama3.console.sections.clients;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.BoxLayout;

import com.kianama3.console.common.KianamaResourceBundle;


public class ClientsSection extends JPanel {
	private static final long serialVersionUID = 1L;
	private JPanel managementPanel;
	private JPanel statusPanel;
	private JTabbedPane tabbedPane;

	public ClientsSection() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);
		
		managementPanel = new ClientsManagementTabPage();
		tabbedPane.addTab(KianamaResourceBundle.getString("clients_label"), null, managementPanel, null);
		
		statusPanel = new ClientsStatusTabPage();
		tabbedPane.addTab(KianamaResourceBundle.getString("status_label"), null, statusPanel, null);
	}

}
