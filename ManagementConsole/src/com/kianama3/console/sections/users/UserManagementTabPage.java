package com.kianama3.console.sections.users;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.JScrollPane;

import com.kianama3.console.common.KianamaResourceBundle;
import com.kianama3.console.common.RecordHistoryDialog;
import com.kianama3.console.common.RemoteServerAccess;
import com.kianama3.console.sections.users.management.AddEditUserDialog;
import com.kianama3.console.sections.users.management.ChangeUserPasswordDialog;
import com.kianama3.console.sections.users.management.CheckBoxCellRenderer;
import com.kianama3.console.sections.users.management.EditPrivilegesDialog;
import com.kianama3.console.sections.users.management.UserManagementTableModel;
import com.kianama3.console.sections.users.management.AddEditUserDialog.ActionType;

import javax.swing.JCheckBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.UIManager;
import java.awt.Color;
import java.text.MessageFormat;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.kianama3.server.remote.common.UserPrivilegesData;
import com.kianama3.server.remote.common.UserData;

public class UserManagementTabPage extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	protected UserManagementTableModel tableModel;
	private JTableHeader header;
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
	private JButton tglbtnEdit;
	private JButton btnAdd;
	private JButton btnRemove;
	private JScrollPane scrollPane;
	private JCheckBox reportCheckBox;
	private JSeparator separator_4;
	private JSeparator separator_5;
	private JButton editPrivilegesButton;
	private JButton changePassButton;
	private JSeparator separator_1;
	private JButton btnChangehistory;
	private JButton btnManagehosts;
	
	private UserData      selectedUser;
	private UserPrivilegesData selectedPrv;

	public UserManagementTabPage() {
		createLayout();
		createTable();
		createAddButton();
		createRemoveButton();
		createEditButton();
		createEditPrivilegesButton();
		createChangePasswordButton();
		createChangeHistoryButton();
		createSecurityPanel();
	}
	
	private void createLayout() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{89, 89, 0, 0, 0, 0, 691, 402, 0};
		gridBagLayout.rowHeights = new int[]{23, 285, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
	}
	
	private void createTable() {
		scrollPane = new JScrollPane();
		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setAutoCreateColumnsFromModel(false);
		tableModel = new UserManagementTableModel(table);
		table.setModel(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(onUsersTableSelected());
		
		header = table.getTableHeader();
		header.addMouseListener(tableModel.new ClientsManagementColumnListener(table)); 
		
		for (int k = 0; k < UserManagementTableModel.columns.length; k++) {
			TableCellRenderer renderer;
			if(k == UserManagementTableModel.COL_CHANGE_PASS)
				renderer = new CheckBoxCellRenderer();
			else{  
				renderer = new DefaultTableCellRenderer();
				((DefaultTableCellRenderer) renderer).setHorizontalAlignment(UserManagementTableModel.columns[k].alignment);
			}
			TableColumn column = new TableColumn(k,
					UserManagementTableModel.columns[k].width, renderer, null);
			table.addColumn(column);

		}
		header.setUpdateTableInRealTime(false);
		scrollPane.setViewportView(table);
	}

	private ListSelectionListener onUsersTableSelected() {
		return new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				//System.out.println("selection event fired");
				if (table.getSelectedRow() < 0)
					return;
				
				if (e.getSource() == table.getSelectionModel() && table.getRowSelectionAllowed()) {
					selectedUser = tableModel.getUserInfoAt(table.getSelectedRow());
					try {
						selectedPrv = RemoteServerAccess.getUserPriviliges(selectedUser.userName);
						updatePrivilegesPanel(selectedPrv);
					} catch (Exception exp) {
						// TODO Auto-generated catch block
						exp.printStackTrace();
					}
				} else if (e.getSource() == table.getColumnModel().getSelectionModel() && table.getColumnSelectionAllowed()) {
				}
				if (e.getValueIsAdjusting()) {
				}
			}
		};
	}
	
	private void updatePrivilegesPanel(UserPrivilegesData p){
		chckbxManageClients.setSelected(p.manageClients);
		chckbxManageDbms.setSelected(p.manageDBMS);
		chckbxManageServer.setSelected(p.manageServer);
		chckbxManageSessions.setSelected(p.manageSessions);
		chckbxManageUsers.setSelected(p.manageUsers);
		chckbxShowClients.setSelected(p.viewClients);
		chckbxShowDatabaseServer.setSelected(p.viewDBMS);
		chckbxShowServerStatus.setSelected(p.viewServer);
		chckbxShowSessions.setSelected(p.viewSessions);
		chckbxShowUsers.setSelected(p.viewUsers);
		reportCheckBox.setSelected(p.runReports);
	}
	
	private void createEditPrivilegesButton(){
		editPrivilegesButton = new JButton(KianamaResourceBundle.getString("edit_privileges_label"));
		editPrivilegesButton.addActionListener(onEditPrivilegesButtonClicked());
		editPrivilegesButton.setToolTipText("edit_privileges_tip_text");
		GridBagConstraints gbc_editPriviligesButton = new GridBagConstraints();
		gbc_editPriviligesButton.insets = new Insets(5, 0, 5, 5);
		gbc_editPriviligesButton.gridx = 2;
		gbc_editPriviligesButton.gridy = 0;
		add(editPrivilegesButton, gbc_editPriviligesButton);
		
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 6, 0, 5);
		gbc_scrollPane.gridwidth = 7;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		add(scrollPane, gbc_scrollPane);
	}
	
	private void createRemoveButton() {
		btnRemove = new JButton(KianamaResourceBundle.getString("remove_label"));
		//btnRemove.setToolTipText(KianamaResourceBundle.getString("remove_tip_text"));
		btnRemove.addActionListener(onRemoveButtonClicked());
		
		btnManagehosts = new JButton(KianamaResourceBundle.getString("manage_hosts_label"));
		btnManagehosts.addActionListener(onManageHostsClicked());
		GridBagConstraints gbc_btnManagehosts = new GridBagConstraints();
		gbc_btnManagehosts.insets = new Insets(5, 0, 5, 5);
		gbc_btnManagehosts.gridx = 5;
		gbc_btnManagehosts.gridy = 0;
		add(btnManagehosts, gbc_btnManagehosts);
		
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnRemove.insets = new Insets(5, 0, 5, 5);
		gbc_btnRemove.gridx = 6;
		gbc_btnRemove.gridy = 0;
		add(btnRemove, gbc_btnRemove);
	}

	private ActionListener onManageHostsClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManageHostsDialog dlg = new ManageHostsDialog();
				dlg.pack();
				dlg.setVisible(true);
			}
		};
	}

	private void createChangeHistoryButton() {
		btnChangehistory = new JButton(KianamaResourceBundle.getString("change_history_label"));
		btnChangehistory.addActionListener(onChangeHistoryButtonClicked());
		GridBagConstraints gbc_btnChangehistory = new GridBagConstraints();
		gbc_btnChangehistory.insets = new Insets(5, 0, 5, 5);
		gbc_btnChangehistory.gridx = 4;
		gbc_btnChangehistory.gridy = 0;
		add(btnChangehistory, gbc_btnChangehistory);
	}

	private ActionListener onChangeHistoryButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					checkUserSelected();
					
					// ClientInfo c = tableModel.getClientRecordAt(table.getSelectedRow());
					RecordHistoryDialog dlg = new RecordHistoryDialog("", "", "", "");
					//dlg.setBounds(100, 100, 342, 190);
					dlg.pack();
					dlg.setVisible(true);
				}catch(Exception exp){
					//TODO log these errors
				}
			}
		};
	}

	private void createChangePasswordButton() {
		changePassButton = new JButton(KianamaResourceBundle.getString("change_password_label"));
		changePassButton.addActionListener(onChangePasswordButtonClicked());
		GridBagConstraints gbc_changePassButton = new GridBagConstraints();
		gbc_changePassButton.insets = new Insets(5, 0, 5, 5);
		gbc_changePassButton.gridx = 3;
		gbc_changePassButton.gridy = 0;
		add(changePassButton, gbc_changePassButton);
	}
	
	private void createAddButton() {
		btnAdd = new JButton(KianamaResourceBundle.getString("add_label"));
		//btnAdd.setToolTipText(KianamaResourceBundle.getString("add_tip_text"));
		btnAdd.addActionListener(onAddButtonClicked());
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.anchor = GridBagConstraints.NORTH;
		gbc_btnAdd.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAdd.insets = new Insets(5, 6, 5, 5);
		gbc_btnAdd.gridx = 0;
		gbc_btnAdd.gridy = 0;
		add(btnAdd, gbc_btnAdd);
	}
	
	private void createEditButton() {
		tglbtnEdit = new JButton(KianamaResourceBundle.getString("edit_label"));
		tglbtnEdit.addActionListener(onEditButtonClicked());
		GridBagConstraints gbc_tglbtnEdit = new GridBagConstraints();
		gbc_tglbtnEdit.anchor = GridBagConstraints.NORTH;
		gbc_tglbtnEdit.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnEdit.insets = new Insets(5, 0, 5, 5);
		gbc_tglbtnEdit.gridx = 1;
		gbc_tglbtnEdit.gridy = 0;
		add(tglbtnEdit, gbc_tglbtnEdit);
	}
	
	private void createSecurityPanel(){
		userprivilegePanel = new JPanel();
		userprivilegePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), 
				KianamaResourceBundle.getString("user_privileges_title"),
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 70, 213)));
		GridBagConstraints gbc_securityPanel = new GridBagConstraints();
		gbc_securityPanel.insets = new Insets(0, 5, 0, 5);
		gbc_securityPanel.fill = GridBagConstraints.BOTH;
		gbc_securityPanel.gridx = 7;
		gbc_securityPanel.gridy = 1;
		add(userprivilegePanel, gbc_securityPanel);
		GridBagLayout gbl_securityPanel = new GridBagLayout();
		gbl_securityPanel.columnWidths = new int[]{97, 0, 13, 169, 0};
		gbl_securityPanel.rowHeights = new int[]{1, 23, 8, 2, 23, 10, 23, 23, 5, 23, 0};
		gbl_securityPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_securityPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		userprivilegePanel.setLayout(gbl_securityPanel);
		
		chckbxManageUsers = new ReadOnlyCheckBox(KianamaResourceBundle.getString("manage_users_label"));
		GridBagConstraints gbc_chckbxManageUsers = new GridBagConstraints();
		gbc_chckbxManageUsers.gridwidth = 2;
		gbc_chckbxManageUsers.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxManageUsers.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxManageUsers.gridx = 0;
		gbc_chckbxManageUsers.gridy = 0;
		userprivilegePanel.add(chckbxManageUsers, gbc_chckbxManageUsers);
		
		separator_4 = new JSeparator();
		separator_4.setOrientation(SwingConstants.VERTICAL);
		GridBagConstraints gbc_separator_4 = new GridBagConstraints();
		gbc_separator_4.fill = GridBagConstraints.BOTH;
		gbc_separator_4.insets = new Insets(0, 0, 0, 5);
		gbc_separator_4.gridheight = 10;
		gbc_separator_4.gridx = 2;
		gbc_separator_4.gridy = 0;
		userprivilegePanel.add(separator_4, gbc_separator_4);
		
		chckbxManageServer = new ReadOnlyCheckBox(KianamaResourceBundle.getString("manage_server_label"));
		GridBagConstraints gbc_chckbxManageServer = new GridBagConstraints();
		gbc_chckbxManageServer.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxManageServer.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxManageServer.gridx = 3;
		gbc_chckbxManageServer.gridy = 0;
		userprivilegePanel.add(chckbxManageServer, gbc_chckbxManageServer);
		
		chckbxShowUsers = new ReadOnlyCheckBox(KianamaResourceBundle.getString("view_users_label"));
		GridBagConstraints gbc_chckbxShowUsers = new GridBagConstraints();
		gbc_chckbxShowUsers.gridwidth = 2;
		gbc_chckbxShowUsers.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxShowUsers.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxShowUsers.gridx = 0;
		gbc_chckbxShowUsers.gridy = 1;
		userprivilegePanel.add(chckbxShowUsers, gbc_chckbxShowUsers);
		
		
		chckbxShowServerStatus = new ReadOnlyCheckBox(KianamaResourceBundle.getString("view_server_status_label"));
		GridBagConstraints gbc_chckbxShowServerStatus = new GridBagConstraints();
		gbc_chckbxShowServerStatus.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxShowServerStatus.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxShowServerStatus.gridx = 3;
		gbc_chckbxShowServerStatus.gridy = 1;
		userprivilegePanel.add(chckbxShowServerStatus, gbc_chckbxShowServerStatus);
		
		JSeparator separator_2 = new JSeparator();
		GridBagConstraints gbc_separator_2 = new GridBagConstraints();
		gbc_separator_2.gridwidth = 2;
		gbc_separator_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator_2.insets = new Insets(0, 0, 5, 5);
		gbc_separator_2.gridx = 0;
		gbc_separator_2.gridy = 2;
		userprivilegePanel.add(separator_2, gbc_separator_2);
		
		separator_5 = new JSeparator();
		GridBagConstraints gbc_separator_5 = new GridBagConstraints();
		gbc_separator_5.anchor = GridBagConstraints.NORTH;
		gbc_separator_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator_5.insets = new Insets(0, 0, 5, 0);
		gbc_separator_5.gridx = 3;
		gbc_separator_5.gridy = 2;
		userprivilegePanel.add(separator_5, gbc_separator_5);
		
		chckbxManageSessions = new ReadOnlyCheckBox(KianamaResourceBundle.getString("manage_sessions_label"));
		GridBagConstraints gbc_chckbxManageSessions = new GridBagConstraints();
		gbc_chckbxManageSessions.gridwidth = 2;
		gbc_chckbxManageSessions.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxManageSessions.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxManageSessions.gridx = 0;
		gbc_chckbxManageSessions.gridy = 3;
		userprivilegePanel.add(chckbxManageSessions, gbc_chckbxManageSessions);
		
		chckbxManageDbms = new ReadOnlyCheckBox(KianamaResourceBundle.getString("manage_dbms_label"));
		GridBagConstraints gbc_chckbxManageDbms = new GridBagConstraints();
		gbc_chckbxManageDbms.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxManageDbms.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxManageDbms.gridx = 3;
		gbc_chckbxManageDbms.gridy = 3;
		userprivilegePanel.add(chckbxManageDbms, gbc_chckbxManageDbms);
		
		chckbxShowSessions = new ReadOnlyCheckBox(KianamaResourceBundle.getString("view_sessions_label"));
		GridBagConstraints gbc_chckbxShowSessions = new GridBagConstraints();
		gbc_chckbxShowSessions.gridwidth = 2;
		gbc_chckbxShowSessions.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxShowSessions.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxShowSessions.gridx = 0;
		gbc_chckbxShowSessions.gridy = 4;
		userprivilegePanel.add(chckbxShowSessions, gbc_chckbxShowSessions);
		
		chckbxShowDatabaseServer = new ReadOnlyCheckBox(KianamaResourceBundle.getString("view_dbms_status_label"));
		GridBagConstraints gbc_chckbxShowDatabaseServer = new GridBagConstraints();
		gbc_chckbxShowDatabaseServer.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxShowDatabaseServer.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxShowDatabaseServer.gridx = 3;
		gbc_chckbxShowDatabaseServer.gridy = 4;
		userprivilegePanel.add(chckbxShowDatabaseServer, gbc_chckbxShowDatabaseServer);
		
		separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator_1.gridwidth = 2;
		gbc_separator_1.insets = new Insets(0, 0, 5, 5);
		gbc_separator_1.gridx = 0;
		gbc_separator_1.gridy = 5;
		userprivilegePanel.add(separator_1, gbc_separator_1);
		
		chckbxManageClients = new ReadOnlyCheckBox(KianamaResourceBundle.getString("manage_clients_label"));
		GridBagConstraints gbc_chckbxManageClients = new GridBagConstraints();
		gbc_chckbxManageClients.gridwidth = 2;
		gbc_chckbxManageClients.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxManageClients.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxManageClients.gridx = 0;
		gbc_chckbxManageClients.gridy = 6;
		userprivilegePanel.add(chckbxManageClients, gbc_chckbxManageClients);	
		
		GridBagConstraints gbc_separator_10 = new GridBagConstraints();
		gbc_separator_10.gridwidth = 2;
		gbc_separator_10.insets = new Insets(0, 0, 5, 5);
		gbc_separator_10.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator_10.gridx = 0;
		gbc_separator_10.gridy = 6;
		
		chckbxShowClients = new ReadOnlyCheckBox(KianamaResourceBundle.getString("view_clients_status_label"));
		GridBagConstraints gbc_chckbxShowClients = new GridBagConstraints();
		gbc_chckbxShowClients.gridwidth = 2;
		gbc_chckbxShowClients.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxShowClients.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxShowClients.gridx = 0;
		gbc_chckbxShowClients.gridy = 7;
		userprivilegePanel.add(chckbxShowClients, gbc_chckbxShowClients);
		
		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.gridwidth = 2;
		gbc_separator.fill = GridBagConstraints.BOTH;
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 8;
		userprivilegePanel.add(separator, gbc_separator);
		
		reportCheckBox = new ReadOnlyCheckBox(KianamaResourceBundle.getString("take_reports_label"));
		GridBagConstraints gbc_reportCheckBox = new GridBagConstraints();
		gbc_reportCheckBox.gridwidth = 2;
		gbc_reportCheckBox.anchor = GridBagConstraints.NORTHWEST;
		gbc_reportCheckBox.insets = new Insets(0, 0, 0, 5);
		gbc_reportCheckBox.gridx = 0;
		gbc_reportCheckBox.gridy = 9;
		userprivilegePanel.add(reportCheckBox, gbc_reportCheckBox);
	}
	
	private ActionListener onAddButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {					
					AddEditUserDialog dlg = new AddEditUserDialog();
					dlg.setBounds(100, 100, 370, 270);
					dlg.setVisible(true);
					if(dlg.userAction == ActionType.Approved)
						tableModel.retrieveUsers();
			}
		};
	}
	
	private ActionListener onEditButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					checkUserSelected();
					
					AddEditUserDialog dlg = new AddEditUserDialog(
							tableModel.getUserInfoAt(table.getSelectedRow()));
					dlg.setBounds(100, 100, 370, 270);
					dlg.setVisible(true);
					if(dlg.userAction == ActionType.Approved)
						tableModel.retrieveUsers();
				}catch(Exception exp){}
			}
		};
	}
	
	private ActionListener onEditPrivilegesButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					checkUserSelected();
					
					EditPrivilegesDialog dlg = new EditPrivilegesDialog(
							selectedUser.userName,
							selectedUser.fullName,
							selectedPrv);
					dlg.setBounds(100, 100, 400, 555);
					dlg.setVisible(true);
				}catch(Exception exp){}
			}
		};
	}
	
	private ActionListener onChangePasswordButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					checkUserSelected();	
					UserData u = tableModel.getUserInfoAt(table.getSelectedRow());
					ChangeUserPasswordDialog dlg = new ChangeUserPasswordDialog(
																u.userName, u.fullName);
					dlg.setBounds(100, 100, 505, 180);
					dlg.setVisible(true);
				}catch(Exception exp){}
			}
		};
	}
	
	private ActionListener onRemoveButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					checkUserSelected();
					
					String userName = tableModel.getUserInfoAt(table.getSelectedRow()).userName;
					String msg = MessageFormat.format(
									KianamaResourceBundle.getString("ask_remove_user_label"),
									userName);
					
					int rs = JOptionPane.showConfirmDialog(null, 
							msg,
							KianamaResourceBundle.getString("please_confirm_label"), 
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE);
					if (rs == JOptionPane.YES_OPTION) {
							RemoteServerAccess.deleteUser(userName);
						tableModel.retrieveUsers();
					}
				}catch(Exception exp){
					// already handled
				}
			}
		};
	}
	
	private void checkUserSelected() {
		if (table.getSelectedRow() == -1){
			JOptionPane.showMessageDialog(null, KianamaResourceBundle.getString("select_user_error_text"),
											KianamaResourceBundle.getString("error_title"),
											JOptionPane.ERROR_MESSAGE);
			throw new RuntimeException();
		}
	}
}