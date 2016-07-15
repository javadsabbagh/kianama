package com.kianama3.console.common;

import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RecordHistoryDialog extends JDialog{
	private JTextField createDateField;
	private JTextField createByUserField;
	private JTextField updateField;
	private JTextField updateByUserField;
	private JButton btnClose;
	private JLabel lblUpdateByUser;
	private JLabel lblUpdateDate;
	private JLabel lblCreateByUser;
	private JLabel lblCreateDate;
	private GridBagLayout gridBagLayout;
	
	public RecordHistoryDialog(String createDate,String createByUser,String updateDate,String updateByUser) {
		buildLayout();
		buildCreateDateLabel();
		buildCreateDateField(createDate);
		buildCreateByUserLabel();
		bildCreateByUserField(createByUser);
		buildUpdateDateLabel();
		buildUpdateDateField(updateDate);
		buildUpdateByUserLabel();
		buildUpdateByUserField(updateByUser);
		buildCloseButton();
		setResizable(false);
		setModal(true);
	}
	
	private void buildCloseButton() {
		btnClose = new JButton(KianamaResourceBundle.getString("close_label"));
		btnClose.addActionListener(onCLoseButtonClicked());
		GridBagConstraints gbc_btnClose = new GridBagConstraints();
		gbc_btnClose.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnClose.gridx = 1;
		gbc_btnClose.gridy = 4;
		getContentPane().add(btnClose, gbc_btnClose);
	}
	
	private ActionListener onCLoseButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		};
	}
	
	private void buildUpdateByUserField(String updateByUser) {
		updateByUserField = new JTextField();
		updateByUserField.setText(updateByUser);
		updateByUserField.setEditable(false);
		GridBagConstraints gbc_updateByUserField = new GridBagConstraints();
		gbc_updateByUserField.anchor = GridBagConstraints.NORTH;
		gbc_updateByUserField.fill = GridBagConstraints.HORIZONTAL;
		gbc_updateByUserField.insets = new Insets(0, 0, 5, 5);
		gbc_updateByUserField.gridx = 1;
		gbc_updateByUserField.gridy = 3;
		getContentPane().add(updateByUserField, gbc_updateByUserField);
		updateByUserField.setColumns(10);
	}
	
	private void buildUpdateByUserLabel() {
		lblUpdateByUser = new JLabel(KianamaResourceBundle.getString("update_by_user_label"));
		GridBagConstraints gbc_lblUpdateByUser = new GridBagConstraints();
		gbc_lblUpdateByUser.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblUpdateByUser.insets = new Insets(0, 5, 5, 5);
		gbc_lblUpdateByUser.gridx = 0;
		gbc_lblUpdateByUser.gridy = 3;
		getContentPane().add(lblUpdateByUser, gbc_lblUpdateByUser);
	}
	
	private void buildUpdateDateField(String updateDate) {
		updateField = new JTextField();
		updateField.setText(updateDate);
		updateField.setEditable(false);
		GridBagConstraints gbc_updateField = new GridBagConstraints();
		gbc_updateField.anchor = GridBagConstraints.NORTH;
		gbc_updateField.fill = GridBagConstraints.HORIZONTAL;
		gbc_updateField.insets = new Insets(5, 0, 5, 5);
		gbc_updateField.gridx = 1;
		gbc_updateField.gridy = 2;
		getContentPane().add(updateField, gbc_updateField);
		updateField.setColumns(10);
	}
	
	private void buildUpdateDateLabel() {
		lblUpdateDate = new JLabel(KianamaResourceBundle.getString("update_date_label"));
		GridBagConstraints gbc_lblUpdateDate = new GridBagConstraints();
		gbc_lblUpdateDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblUpdateDate.insets = new Insets(5, 5, 5, 5);
		gbc_lblUpdateDate.gridx = 0;
		gbc_lblUpdateDate.gridy = 2;
		getContentPane().add(lblUpdateDate, gbc_lblUpdateDate);
	}
	
	private void bildCreateByUserField(String createByUser) {
		createByUserField = new JTextField();
		createByUserField.setText(createByUser);
		createByUserField.setEditable(false);
		GridBagConstraints gbc_createByUserField = new GridBagConstraints();
		gbc_createByUserField.anchor = GridBagConstraints.NORTH;
		gbc_createByUserField.fill = GridBagConstraints.HORIZONTAL;
		gbc_createByUserField.insets = new Insets(0, 0, 5, 5);
		gbc_createByUserField.gridx = 1;
		gbc_createByUserField.gridy = 1;
		getContentPane().add(createByUserField, gbc_createByUserField);
		createByUserField.setColumns(10);
	}
	private void buildCreateByUserLabel() {
		lblCreateByUser = new JLabel(KianamaResourceBundle.getString("create_by_user_label"));
		GridBagConstraints gbc_lblCreateByUser = new GridBagConstraints();
		gbc_lblCreateByUser.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblCreateByUser.insets = new Insets(0, 5, 5, 5);
		gbc_lblCreateByUser.gridx = 0;
		gbc_lblCreateByUser.gridy = 1;
		getContentPane().add(lblCreateByUser, gbc_lblCreateByUser);
	}
	
	private void buildCreateDateField(String createDate) {
		createDateField = new JTextField();
		createDateField.setText(createDate);
		createDateField.setEditable(false);
		GridBagConstraints gbc_createDateField = new GridBagConstraints();
		gbc_createDateField.anchor = GridBagConstraints.NORTH;
		gbc_createDateField.fill = GridBagConstraints.HORIZONTAL;
		gbc_createDateField.insets = new Insets(5, 0, 5, 5);
		gbc_createDateField.gridx = 1;
		gbc_createDateField.gridy = 0;
		getContentPane().add(createDateField, gbc_createDateField);
		createDateField.setColumns(10);
	}
	private void buildCreateDateLabel() {
		lblCreateDate = new JLabel(KianamaResourceBundle.getString("create_date_label"));
		GridBagConstraints gbc_lblCreateDate = new GridBagConstraints();
		gbc_lblCreateDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblCreateDate.insets = new Insets(5, 5, 5, 5);
		gbc_lblCreateDate.gridx = 0;
		gbc_lblCreateDate.gridy = 0;
		getContentPane().add(lblCreateDate, gbc_lblCreateDate);
	}
	
	private void buildLayout() {
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 226, 0};
		gridBagLayout.rowHeights = new int[]{20, 20, 20, 28, 23, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
	}
}
