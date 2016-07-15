package com.kianama3.console.sections.users.management;

import javax.swing.JDialog;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.kianama3.console.common.KianamaResourceBundle;
import com.kianama3.console.common.RemoteServerAccess;
import com.kianama3.server.remote.common.UserPrivilegesData;

public class EditPrivilegesDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	
	private JPanel userprivilegePanel;
	private JCheckBox chckbxShowUsers;
	private JCheckBox chckbxManageUsers;
	private JCheckBox chckbxManageSessions;
	private JCheckBox chckbxShowSessions;
	private JCheckBox chckbxShowServerStatus;
	private JCheckBox chckbxManageServer;
	private JCheckBox chckbxManageDbms;
	private JCheckBox chckbxShowDatabaseServer;
	private JCheckBox chckbxShowClients;
	private JCheckBox chckbxManageClients;
	private JCheckBox reportCheckBox;
	
	private JButton btnSelectAll;
	private JButton btnSelectNone;
	private JLabel lblUserName;
	private JTextField userNameField;
	private JLabel lblFullName;
	private JTextField fullNameField;
	private JButton btnCancel;
	private JButton btnSave;
	private JSeparator separator_4;
	
	private String userName;
	private String fullName;
	private UserPrivilegesData privs;

	private GridBagLayout gbl_securityPanel;
	private GridBagConstraints gbc_chckbxManageUsers;
	private JSeparator separator_5;
	private JSeparator separator_2;
	private JSeparator separator_1;
	private JSeparator separator;

	public enum ActionType {Approved,Canceled};
	public ActionType userAction;
	
	public EditPrivilegesDialog(String userName,String fullName,UserPrivilegesData privs) {
		this.userName = userName;
		this.fullName = fullName;
		this.privs = privs;
		
		setTitle(KianamaResourceBundle.getString("edit_user_privileges_title"));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{158, 116, 108, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 276, 14, 23, 106, 24, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		lblUserName = new JLabel(KianamaResourceBundle.getString("username_label"));
		GridBagConstraints gbc_lblUserName = new GridBagConstraints();
		gbc_lblUserName.anchor = GridBagConstraints.WEST;
		gbc_lblUserName.insets = new Insets(5, 5, 5, 5);
		gbc_lblUserName.gridx = 0;
		gbc_lblUserName.gridy = 0;
		getContentPane().add(lblUserName, gbc_lblUserName);
		
		userNameField = new JTextField();
		userNameField.setEditable(false);
		GridBagConstraints gbc_userNameField = new GridBagConstraints();
		gbc_userNameField.gridwidth = 2;
		gbc_userNameField.insets = new Insets(5, 0, 5, 5);
		gbc_userNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_userNameField.gridx = 1;
		gbc_userNameField.gridy = 0;
		getContentPane().add(userNameField, gbc_userNameField);
		userNameField.setColumns(20);
		userNameField.setText(userName);
		
		lblFullName = new JLabel(KianamaResourceBundle.getString("fullname_label"));
		GridBagConstraints gbc_lblFullName = new GridBagConstraints();
		gbc_lblFullName.anchor = GridBagConstraints.WEST;
		gbc_lblFullName.insets = new Insets(0, 5, 5, 5);
		gbc_lblFullName.gridx = 0;
		gbc_lblFullName.gridy = 1;
		getContentPane().add(lblFullName, gbc_lblFullName);
		
		fullNameField = new JTextField();
		fullNameField.setEditable(false);
		GridBagConstraints gbc_fullNameField = new GridBagConstraints();
		gbc_fullNameField.gridwidth = 2;
		gbc_fullNameField.insets = new Insets(0, 0, 5, 5);
		gbc_fullNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_fullNameField.gridx = 1;
		gbc_fullNameField.gridy = 1;
		getContentPane().add(fullNameField, gbc_fullNameField);
		fullNameField.setColumns(30);
		fullNameField.setText(fullName);
		
		userprivilegePanel = new JPanel();
		userprivilegePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), 
				KianamaResourceBundle.getString("user_privileges_title"),
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 70, 213)));
		GridBagConstraints gbc_userprivilegePanel = new GridBagConstraints();
		gbc_userprivilegePanel.anchor = GridBagConstraints.NORTH;
		gbc_userprivilegePanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_userprivilegePanel.insets = new Insets(0, 5, 5, 5);
		gbc_userprivilegePanel.gridwidth = 3;
		gbc_userprivilegePanel.gridx = 0;
		gbc_userprivilegePanel.gridy = 2;
		getContentPane().add(userprivilegePanel, gbc_userprivilegePanel);
		
		gbl_securityPanel = new GridBagLayout();
		gbl_securityPanel.columnWidths = new int[]{97, 0, 13, 169, 0};
		gbl_securityPanel.rowHeights = new int[]{0, 1, 23, 8, 2, 23, 7, 23, 23, 5, 23, 0};
		gbl_securityPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_securityPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		userprivilegePanel.setLayout(gbl_securityPanel);
		
		btnSelectAll = new JButton(KianamaResourceBundle.getString("select_all_text"));
		btnSelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chckbxManageUsers.setSelected(true);
				chckbxShowUsers.setSelected(true);
				chckbxManageSessions.setSelected(true);
				chckbxShowSessions.setSelected(true);
				chckbxManageClients.setSelected(true);
				chckbxShowClients.setSelected(true);
				reportCheckBox.setSelected(true);
				chckbxManageServer.setSelected(true);
				chckbxShowServerStatus.setSelected(true);
				chckbxManageDbms.setSelected(true);
				chckbxShowDatabaseServer.setSelected(true);
			}
		});
		GridBagConstraints gbc_btnSelectAll = new GridBagConstraints();
		gbc_btnSelectAll.insets = new Insets(5, 0, 5, 5);
		gbc_btnSelectAll.gridx = 0;
		gbc_btnSelectAll.gridy = 0;
		userprivilegePanel.add(btnSelectAll, gbc_btnSelectAll);
		
		btnSelectNone = new JButton(KianamaResourceBundle.getString("select_none_text"));
		btnSelectNone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chckbxManageUsers.setSelected(false);
				chckbxShowUsers.setSelected(false);
				chckbxManageSessions.setSelected(false);
				chckbxShowSessions.setSelected(false);
				chckbxManageClients.setSelected(false);
				chckbxShowClients.setSelected(false);
				reportCheckBox.setSelected(false);
				chckbxManageServer.setSelected(false);
				chckbxShowServerStatus.setSelected(false);
				chckbxManageDbms.setSelected(false);
				chckbxShowDatabaseServer.setSelected(false);
			}
		});
		GridBagConstraints gbc_btnSelectNone = new GridBagConstraints();
		gbc_btnSelectNone.insets = new Insets(5, 0, 5, 5);
		gbc_btnSelectNone.gridx = 1;
		gbc_btnSelectNone.gridy = 0;
		userprivilegePanel.add(btnSelectNone, gbc_btnSelectNone);
		
		chckbxManageUsers = new JCheckBox(KianamaResourceBundle.getString("manage_users_label"));
		gbc_chckbxManageUsers = new GridBagConstraints();
		gbc_chckbxManageUsers.gridwidth = 2;
		gbc_chckbxManageUsers.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxManageUsers.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxManageUsers.gridx = 0;
		gbc_chckbxManageUsers.gridy = 1;
		userprivilegePanel.add(chckbxManageUsers, gbc_chckbxManageUsers);
		chckbxManageUsers.addItemListener(onManageUsersClicked());
		
		separator_4 = new JSeparator();
		separator_4.setOrientation(SwingConstants.VERTICAL);
		GridBagConstraints gbc_separator_4 = new GridBagConstraints();
		gbc_separator_4.fill = GridBagConstraints.BOTH;
		gbc_separator_4.insets = new Insets(0, 0, 0, 5);
		gbc_separator_4.gridheight = 10;
		gbc_separator_4.gridx = 2;
		gbc_separator_4.gridy = 1;
		userprivilegePanel.add(separator_4, gbc_separator_4);
		
		chckbxManageServer = new JCheckBox(KianamaResourceBundle.getString("manage_server_label"));
		GridBagConstraints gbc_chckbxManageServer = new GridBagConstraints();
		gbc_chckbxManageServer.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxManageServer.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxManageServer.gridx = 3;
		gbc_chckbxManageServer.gridy = 1;
		userprivilegePanel.add(chckbxManageServer, gbc_chckbxManageServer);
		chckbxManageServer.addItemListener(onManageServerClicked());
		
		chckbxShowUsers = new JCheckBox(KianamaResourceBundle.getString("view_users_label"));
		GridBagConstraints gbc_chckbxShowUsers = new GridBagConstraints();
		gbc_chckbxShowUsers.gridwidth = 2;
		gbc_chckbxShowUsers.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxShowUsers.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxShowUsers.gridx = 0;
		gbc_chckbxShowUsers.gridy = 2;
		userprivilegePanel.add(chckbxShowUsers, gbc_chckbxShowUsers);
		
		chckbxShowUsers.addItemListener(onViewUsersClicked());
		
		chckbxShowServerStatus = new JCheckBox(KianamaResourceBundle.getString("view_server_status_label"));
		GridBagConstraints gbc_chckbxShowServerStatus = new GridBagConstraints();
		gbc_chckbxShowServerStatus.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxShowServerStatus.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxShowServerStatus.gridx = 3;
		gbc_chckbxShowServerStatus.gridy = 2;
		userprivilegePanel.add(chckbxShowServerStatus, gbc_chckbxShowServerStatus);
		chckbxShowServerStatus.addItemListener(onViewServerClicked());
		
		separator_2 = new JSeparator();
		GridBagConstraints gbc_separator_2 = new GridBagConstraints();
		gbc_separator_2.gridwidth = 2;
		gbc_separator_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator_2.insets = new Insets(0, 0, 5, 5);
		gbc_separator_2.gridx = 0;
		gbc_separator_2.gridy = 3;
		userprivilegePanel.add(separator_2, gbc_separator_2);
		
		separator_5 = new JSeparator();
		GridBagConstraints gbc_separator_5 = new GridBagConstraints();
		gbc_separator_5.anchor = GridBagConstraints.NORTH;
		gbc_separator_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator_5.insets = new Insets(0, 0, 5, 0);
		gbc_separator_5.gridx = 3;
		gbc_separator_5.gridy = 3;
		userprivilegePanel.add(separator_5, gbc_separator_5);
		
		chckbxManageSessions = new JCheckBox(KianamaResourceBundle.getString("manage_sessions_label"));
		GridBagConstraints gbc_chckbxManageSessions = new GridBagConstraints();
		gbc_chckbxManageSessions.gridwidth = 2;
		gbc_chckbxManageSessions.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxManageSessions.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxManageSessions.gridx = 0;
		gbc_chckbxManageSessions.gridy = 4;
		userprivilegePanel.add(chckbxManageSessions, gbc_chckbxManageSessions);
		chckbxManageSessions.addItemListener(onManageSessionsClicked());
		
		chckbxManageDbms = new JCheckBox(KianamaResourceBundle.getString("manage_dbms_label"));
		GridBagConstraints gbc_chckbxManageDbms = new GridBagConstraints();
		gbc_chckbxManageDbms.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxManageDbms.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxManageDbms.gridx = 3;
		gbc_chckbxManageDbms.gridy = 4;
		userprivilegePanel.add(chckbxManageDbms, gbc_chckbxManageDbms);
		chckbxManageDbms.addItemListener(onManageDBMSClicked());
		
		chckbxShowSessions = new JCheckBox(KianamaResourceBundle.getString("view_sessions_label"));
		GridBagConstraints gbc_chckbxShowSessions = new GridBagConstraints();
		gbc_chckbxShowSessions.gridwidth = 2;
		gbc_chckbxShowSessions.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxShowSessions.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxShowSessions.gridx = 0;
		gbc_chckbxShowSessions.gridy = 5;
		userprivilegePanel.add(chckbxShowSessions, gbc_chckbxShowSessions);
		chckbxShowSessions.addItemListener(onViewSessionsClicked());
		
		chckbxShowDatabaseServer = new JCheckBox(KianamaResourceBundle.getString("view_dbms_status_label"));
		GridBagConstraints gbc_chckbxShowDatabaseServer = new GridBagConstraints();
		gbc_chckbxShowDatabaseServer.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxShowDatabaseServer.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxShowDatabaseServer.gridx = 3;
		gbc_chckbxShowDatabaseServer.gridy = 5;
		userprivilegePanel.add(chckbxShowDatabaseServer, gbc_chckbxShowDatabaseServer);
		chckbxShowDatabaseServer.addItemListener(onViewDBMSClicked());
		
		chckbxManageClients = new JCheckBox(KianamaResourceBundle.getString("manage_clients_label"));
		GridBagConstraints gbc_chckbxManageClients = new GridBagConstraints();
		gbc_chckbxManageClients.gridwidth = 2;
		gbc_chckbxManageClients.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxManageClients.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxManageClients.gridx = 0;
		gbc_chckbxManageClients.gridy = 7;
		userprivilegePanel.add(chckbxManageClients, gbc_chckbxManageClients);	
		chckbxManageClients.addItemListener(onManageClientsClicked());
		
		separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.gridwidth = 2;
		gbc_separator_1.insets = new Insets(0, 0, 5, 5);
		gbc_separator_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator_1.gridx = 0;
		gbc_separator_1.gridy = 6;
		userprivilegePanel.add(separator_1, gbc_separator_1);
		
		chckbxShowClients = new JCheckBox(KianamaResourceBundle.getString("view_clients_status_label"));
		GridBagConstraints gbc_chckbxShowClients = new GridBagConstraints();
		gbc_chckbxShowClients.gridwidth = 2;
		gbc_chckbxShowClients.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxShowClients.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxShowClients.gridx = 0;
		gbc_chckbxShowClients.gridy = 8;
		userprivilegePanel.add(chckbxShowClients, gbc_chckbxShowClients);
		chckbxShowClients.addItemListener(onViewClientsClicked());
		
		separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.gridwidth = 2;
		gbc_separator.fill = GridBagConstraints.BOTH;
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 9;
		userprivilegePanel.add(separator, gbc_separator);
		
		reportCheckBox = new JCheckBox(KianamaResourceBundle.getString("take_reports_label"));
		GridBagConstraints gbc_reportCheckBox = new GridBagConstraints();
		gbc_reportCheckBox.gridwidth = 2;
		gbc_reportCheckBox.anchor = GridBagConstraints.NORTHWEST;
		gbc_reportCheckBox.insets = new Insets(0, 0, 0, 5);
		gbc_reportCheckBox.gridx = 0;
		gbc_reportCheckBox.gridy = 10;
		userprivilegePanel.add(reportCheckBox, gbc_reportCheckBox);
		
		
		
		btnSave = new JButton(KianamaResourceBundle.getString("save_label"));
		btnSave.addActionListener(onSaveButtonClicked());
		
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnSave.insets = new Insets(0, 0, 0, 5);
		gbc_btnSave.gridx = 0;
		gbc_btnSave.gridy = 6;
		getContentPane().add(btnSave, gbc_btnSave);
		
		btnCancel = new JButton(KianamaResourceBundle.getString("cancel_label"));
		btnCancel.addActionListener(onCancelButtonClicked());
		
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 1;
		gbc_btnCancel.gridy = 6;
		getContentPane().add(btnCancel, gbc_btnCancel);
				
		setPrivilegeCheckBoxes(privs);
		
		pack();
		setResizable(false);
		setModal(true);
		getRootPane().setDefaultButton(btnSave);
	}

	private void setPrivilegeCheckBoxes(UserPrivilegesData p){
		chckbxShowUsers.setSelected(p.viewUsers);
		chckbxManageUsers.setSelected(p.manageUsers);
		chckbxManageSessions.setSelected(p.manageSessions);
		chckbxShowSessions.setSelected(p.viewSessions);
		chckbxShowServerStatus.setSelected(p.viewServer);
		chckbxManageServer.setSelected(p.manageServer);
		chckbxManageDbms.setSelected(p.manageDBMS);
		chckbxShowDatabaseServer.setSelected(p.viewDBMS);
		chckbxShowClients.setSelected(p.viewClients);
		chckbxManageClients.setSelected(p.manageClients);
		reportCheckBox.setSelected(p.runReports);
	}
	
	private ActionListener onCancelButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userAction = ActionType.Canceled;
				dispose();
			}
		};
	}

	private ActionListener onSaveButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					UserPrivilegesData pr = new UserPrivilegesData();
					
					pr.manageServer = chckbxManageServer.isSelected();
					pr.viewServer = chckbxShowServerStatus.isSelected();
					pr.manageDBMS = chckbxManageDbms.isSelected();
					pr.viewDBMS = chckbxShowDatabaseServer.isSelected();
					pr.manageUsers = chckbxManageUsers.isSelected();
					pr.viewUsers = chckbxShowUsers.isSelected();
					pr.manageSessions = chckbxManageSessions.isSelected();
					pr.viewSessions = chckbxShowSessions.isSelected();
					pr.manageClients = chckbxManageClients.isSelected();
					pr.viewClients = chckbxShowClients.isSelected();
					pr.runReports = reportCheckBox.isSelected();
					
					RemoteServerAccess.setUserPriviliges(userName, pr);
					userAction = ActionType.Approved;
					dispose();
				} catch (Exception e1) {
				}
			}
		};
	}	
	
	private ItemListener onManageClientsClicked() {
		return new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED)
					chckbxShowClients.setSelected(true);
			}
		};
	}
	
	private ItemListener onViewClientsClicked() {
		return new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.DESELECTED)
					chckbxManageClients.setSelected(false);
			}
		};
	}
	
	private ItemListener onViewDBMSClicked() {
		return new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.DESELECTED)
					chckbxManageDbms.setSelected(false);
			}
		};
	}
	private ItemListener onManageDBMSClicked() {
		return new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED)
					chckbxShowDatabaseServer.setSelected(true);
			}
		};
	}
	
	private ItemListener onManageServerClicked() {
		return new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED)
					chckbxShowServerStatus.setSelected(true);
			}
		};
	}
	
	private ItemListener onViewServerClicked() {
		return new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.DESELECTED)
					chckbxManageServer.setSelected(false);
			}
		};
	}
	
	private ItemListener onViewSessionsClicked() {
		return new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange()==ItemEvent.DESELECTED)
					chckbxManageSessions.setSelected(false);
			}
		};
	}
	
	private ItemListener onManageSessionsClicked() {
		return new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED)
					chckbxShowSessions.setSelected(true);
			}
		};
	}
	
	private ItemListener onManageUsersClicked() {
		return new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED)
					chckbxShowUsers.setSelected(true);
			}
		};
	}
	
	private ItemListener onViewUsersClicked() {
		return new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.DESELECTED)
					chckbxManageUsers.setSelected(false);
			}
		};
	}
}
