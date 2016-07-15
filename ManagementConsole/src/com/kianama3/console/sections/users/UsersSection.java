package com.kianama3.console.sections.users;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.kianama3.console.common.KianamaResourceBundle;

public class UsersSection extends JPanel{
	private static final long serialVersionUID = 1L;
	private JPanel sessionsManagementTabPage;
	private JPanel usersManagementTabPage;
	private JTabbedPane tabbedPane;

	public UsersSection() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setAlignmentY(Component.TOP_ALIGNMENT);
		tabbedPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(tabbedPane);
		
		usersManagementTabPage = new UserManagementTabPage();
		tabbedPane.addTab(KianamaResourceBundle.getString("user_management_label"), null, usersManagementTabPage, null);
		
		sessionsManagementTabPage = new SessionManagementTabPage();
		tabbedPane.addTab(KianamaResourceBundle.getString("session_management_label"), null, sessionsManagementTabPage, null);
	}


}
