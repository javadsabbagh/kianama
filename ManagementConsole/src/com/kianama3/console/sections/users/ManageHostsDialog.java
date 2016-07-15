package com.kianama3.console.sections.users;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.kianama3.console.common.KianamaResourceBundle;
import com.kianama3.console.common.RemoteServerAccess;
import javax.swing.ListSelectionModel;

public class ManageHostsDialog extends JDialog {
	private Vector<String> listData = new Vector<String>();
	private JList hostsList;
	private JButton btnRemove;
	private JButton btnAdd;
	private JTextField computerField;
	private JLabel lblAuthorizedComputers;
	private JScrollPane scrollPane;
	private JButton closeButton;

	public ManageHostsDialog() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{308, 65, 0};
		gridBagLayout.rowHeights = new int[]{0, 36, 335, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
				
		lblAuthorizedComputers = new JLabel(KianamaResourceBundle.getString("authorized_hosts_label"));
		GridBagConstraints gbc_lblAuthorizedComputers = new GridBagConstraints();
		gbc_lblAuthorizedComputers.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAuthorizedComputers.insets = new Insets(5, 5, 5, 5);
		gbc_lblAuthorizedComputers.gridx = 0;
		gbc_lblAuthorizedComputers.gridy = 0;
		getContentPane().add(lblAuthorizedComputers, gbc_lblAuthorizedComputers);
								
		computerField = new JTextField();
		GridBagConstraints gbc_computerField = new GridBagConstraints();
		gbc_computerField.fill = GridBagConstraints.HORIZONTAL;
		gbc_computerField.insets = new Insets(0, 5, 5, 5);
		gbc_computerField.gridx = 0;
		gbc_computerField.gridy = 1;
		getContentPane().add(computerField, gbc_computerField);
		computerField.setColumns(15);
		
		btnAdd = new JButton(KianamaResourceBundle.getString("add_label"));
		btnAdd.addActionListener(onAddBUttonClcked());
		
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gbc_btnAdd.insets = new Insets(5, 0, 5, 0);
		gbc_btnAdd.gridx = 1;
		gbc_btnAdd.gridy = 1;
		getContentPane().add(btnAdd, gbc_btnAdd);
				
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 5, 5, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		getContentPane().add(scrollPane, gbc_scrollPane);

		hostsList = new JList();
		hostsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(hostsList);
		fillHostsList();
		
		btnRemove = new JButton(KianamaResourceBundle.getString("remove_label"));
		btnRemove.addActionListener(onRemoveButtonClicked());
		
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.insets = new Insets(0, 0, 5, 0);
		gbc_btnRemove.anchor = GridBagConstraints.BASELINE_LEADING;
		gbc_btnRemove.gridx = 1;
		gbc_btnRemove.gridy = 2;
		getContentPane().add(btnRemove, gbc_btnRemove);
		
		closeButton = new JButton(KianamaResourceBundle.getString("close_label"));
		closeButton.addActionListener(onCloseButtonClicked());
		GridBagConstraints gbc_closeButton = new GridBagConstraints();
		gbc_closeButton.anchor = GridBagConstraints.WEST;
		gbc_closeButton.insets = new Insets(0, 0, 5, 0);
		gbc_closeButton.gridx = 1;
		gbc_closeButton.gridy = 3;
		getContentPane().add(closeButton, gbc_closeButton);
			
		setModal(true);
		setResizable(false);
	}

	private ActionListener onCloseButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		};
	}

	private void fillHostsList(){
		try {
			//listData.clear();
			listData = RemoteServerAccess.getHostsList();
			hostsList.setListData(listData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private ActionListener onAddBUttonClcked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!computerField.getText().trim().isEmpty()) {
					try {
						RemoteServerAccess.addNewHost(computerField.getText().trim());
						listData.addElement(computerField.getText().trim());
						hostsList.setListData(listData);
						computerField.setText("");
					} catch (Exception e1) {
						//do nothing
					}

				}
			}
		};
	}

	private ActionListener onRemoveButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selection = hostsList.getSelectedIndex();
				if (selection >= 0) {
					int rs = JOptionPane.showConfirmDialog(null, 
							MessageFormat.format(
									KianamaResourceBundle.getString("ask_remove_host_label"),
									listData.get(hostsList.getSelectedIndex())),
							KianamaResourceBundle.getString("please_confirm_label"), 
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE);
					if (rs != JOptionPane.YES_OPTION)
						return;
					
					try {
						RemoteServerAccess.removeHost(
								listData.get(hostsList.getSelectedIndex()));
						
						listData.removeElementAt(selection);
						hostsList.setListData(listData);

						// As a nice touch, select the next item
						if (selection >= listData.size())
							selection = listData.size() - 1;
						hostsList.setSelectedIndex(selection);
					} catch (Exception e1) {

					}

				}
			}
		};
	}
}
