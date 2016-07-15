package com.kianama3.console.sections.clients;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.JScrollPane;

import com.kianama3.console.common.KianamaResourceBundle;
import com.kianama3.console.common.RecordHistoryDialog;
import com.kianama3.console.common.RemoteServerAccess;
import com.kianama3.console.sections.clients.management.AddEditClientDialog;
import com.kianama3.console.sections.clients.management.ClientsManagementTableModel;
import com.kianama3.console.sections.clients.management.AddEditClientDialog.ActionType;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.text.MessageFormat;


public class ClientsManagementTabPage extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	protected ClientsManagementTableModel tableModel;
	private JTableHeader header;
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnEdit;
	private JScrollPane scrollPane;
	private GridBagLayout gridBagLayout;
	private JButton btnChangehistory;

	public ClientsManagementTabPage() {
		createLayout();
		createScrollPane();
		createTable();
		createAddButton();
		createRemoveButton();
		createEditButton();
		createChangeHistoryButton();
	}
	
	private void createTable() {
		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableModel = new ClientsManagementTableModel(table);
		scrollPane.setViewportView(table);
		table.setAutoCreateColumnsFromModel(false);
		table.setModel(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		header = table.getTableHeader();
		header.addMouseListener(tableModel.new ClientsManagementColumnListener()); 
		
		for (int k = 0; k < ClientsManagementTableModel.m_columns.length; k++) {
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();//new ClientsManagementTableCellRenderer(tableModel);
			renderer.setHorizontalAlignment(ClientsManagementTableModel.m_columns[k].alignment);
			TableColumn column = new TableColumn(k,
					ClientsManagementTableModel.m_columns[k].width, renderer, null);
			table.addColumn(column);

		}
		header.setUpdateTableInRealTime(false);
	}
	private void createScrollPane() {	
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 5, 8, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		add(scrollPane, gbc_scrollPane);
	}

	private void createChangeHistoryButton() {
		btnChangehistory = new JButton(KianamaResourceBundle.getString("change_history_label"));
		btnChangehistory.addActionListener(onChangeHistoryButtonClicked());
		GridBagConstraints gbc_btnChangehistory = new GridBagConstraints();
		gbc_btnChangehistory.insets = new Insets(5, 0, 5, 5);
		gbc_btnChangehistory.gridx = 2;
		gbc_btnChangehistory.gridy = 0;
		add(btnChangehistory, gbc_btnChangehistory);
	}

	private ActionListener onChangeHistoryButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					checkClientSelected();
					
					// ClientInfo c = tableModel.getClientRecordAt(table.getSelectedRow());
					RecordHistoryDialog dlg = new RecordHistoryDialog("", "", "", "");
					//dlg.setBounds(100, 100, 342, 190);
					dlg.pack();
					dlg.setVisible(true);
				}catch(Exception exp){}
			}
		};
	}
	private void createLayout() {
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{89, 89, 0, 606, 0};
		gridBagLayout.rowHeights = new int[]{23, 445, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
	}
	private void createEditButton() {
		btnEdit = new JButton(KianamaResourceBundle.getString("edit_label"));
		btnEdit.addActionListener(onEditButtonClicked());
		btnEdit.setToolTipText(KianamaResourceBundle.getString("edit_tip_text"));
		GridBagConstraints gbc_tglbtnEdit = new GridBagConstraints();
		gbc_tglbtnEdit.anchor = GridBagConstraints.NORTH;
		gbc_tglbtnEdit.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnEdit.insets = new Insets(5, 0, 5, 5);
		gbc_tglbtnEdit.gridx = 1;
		gbc_tglbtnEdit.gridy = 0;
		add(btnEdit, gbc_tglbtnEdit);
	}
	private void createRemoveButton() {
		btnRemove = new JButton(KianamaResourceBundle.getString("remove_label"));
		btnRemove.setToolTipText(KianamaResourceBundle.getString("remove_tip_text"));
		btnRemove.addActionListener(onRemoveButtonClicked());
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnRemove.insets = new Insets(5, 0, 5, 0);
		gbc_btnRemove.gridx = 3;
		gbc_btnRemove.gridy = 0;
		add(btnRemove, gbc_btnRemove);
	}
	private void createAddButton() {
		btnAdd = new JButton(KianamaResourceBundle.getString("add_label"));
		btnAdd.setToolTipText(KianamaResourceBundle.getString("add_tip_text"));
		btnAdd.addActionListener(onAddButtonClicked());
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.anchor = GridBagConstraints.NORTH;
		gbc_btnAdd.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAdd.insets = new Insets(5, 5, 5, 5);
		gbc_btnAdd.gridx = 0;
		gbc_btnAdd.gridy = 0;
		add(btnAdd, gbc_btnAdd);
	}
	
	private ActionListener onAddButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddEditClientDialog dlg = new AddEditClientDialog();
				dlg.setBounds(100, 100, 391, 243);
				dlg.setVisible(true);
				if(dlg.userAction == ActionType.Approved)
					tableModel.retrieveClients();
			}
		};
	}
	
	private ActionListener onEditButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					checkClientSelected();
					
					AddEditClientDialog dlg = new AddEditClientDialog(
							tableModel.getClientRecordAt(table.getSelectedRow()));
					dlg.setBounds(100, 100, 391, 243);
					dlg.setVisible(true);
					if(dlg.userAction == ActionType.Approved)
						tableModel.retrieveClients();
				}catch(Exception exp){}
			}
		};
	}
	
	private ActionListener onRemoveButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					checkClientSelected();
					
					String clientName = tableModel.getClientRecordAt(table.getSelectedRow()).clientName;
					String msg = MessageFormat.format(
									KianamaResourceBundle.getString("ask_remove_client_label"),
									clientName);
					
					int rs = JOptionPane.showConfirmDialog(null, 
							msg,
							KianamaResourceBundle.getString("please_confirm_label"), 
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE);
					if (rs == JOptionPane.YES_OPTION) {
							RemoteServerAccess.deleteClient(clientName);
						tableModel.retrieveClients();
					}
				}catch(Exception exp){
					//do nothing : already handled
				}
			}
		};
	}
	
	private void checkClientSelected() {
		if (table.getSelectedRow() == -1){
			JOptionPane.showMessageDialog(null, KianamaResourceBundle.getString("select_client_error_text"),
											KianamaResourceBundle.getString("error_title"),
											JOptionPane.ERROR_MESSAGE);
			throw new RuntimeException();
		}
	}
}