package com.kianama3.console.sections.users.management;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ImageIcon;

import com.kianama3.console.common.KianamaResourceBundle;
import com.kianama3.console.common.RemoteServerAccess;

import java.awt.FlowLayout;
import java.util.Arrays;
import javax.swing.JTextField;

public class ChangeUserPasswordDialog extends JDialog{
	private JPasswordField newPasswordField;
	private JPasswordField repeatPasswordField;
	private JPanel panel;
	private JButton changePassButton;
	private JButton cancelButton;
	private JLabel lblKeyIcon;
	private JPanel fieldsPanel;
	private JLabel lblUserName;
	private JLabel lblNewPassword;
	private JLabel lblRepeatNewPassword;
	private GridBagLayout gridBagLayout;
	private JTextField userNameField;
	private JLabel lblFullName;
	private JTextField fullNameField;
	private String userName;
	private String fullName;
	
	public ChangeUserPasswordDialog(String userName,String fullName) {
		this.userName = userName;
		this.fullName = fullName;
		
		buildDialogLayout();		
		buildDialogIcon();		
		buildFieldsPanel();		
		buildUserNameLabel();		
		buildUserNameField();	
		buildFullNameLabel();
		buildFullNameField();
		buildNewPasswordLabel();		
		buildNewPasswordField();		
		buildRepeatPasswordLabel();		
		buildRepeatPasswordField();		
		buildButtonsPanel();
		
		getRootPane().setDefaultButton(changePassButton);
		setResizable(false);
		setModal(true);
		setTitle(getString("change_pass_title"));
	}

	private void buildButtonsPanel() {
		panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridwidth = 2;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		getContentPane().add(panel, gbc_panel);
		
		changePassButton = new JButton(getString("apply_label"));
		changePassButton.addActionListener(onChangePassButtonClicked());
		
		cancelButton = new JButton(getString("cancel_label"));
		cancelButton.addActionListener(onCancelButtonClicked());
		panel.add(cancelButton);
		panel.add(changePassButton);
	}

	private void buildRepeatPasswordField() {
		repeatPasswordField = new JPasswordField();
		repeatPasswordField.setBackground(new Color(255, 255, 153));
		GridBagConstraints gbc_repeatPasswordField = new GridBagConstraints();
		gbc_repeatPasswordField.insets = new Insets(3, 0, 0, 0);
		gbc_repeatPasswordField.anchor = GridBagConstraints.NORTH;
		gbc_repeatPasswordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_repeatPasswordField.gridx = 1;
		gbc_repeatPasswordField.gridy = 3;
		fieldsPanel.add(repeatPasswordField, gbc_repeatPasswordField);
	}

	private void buildRepeatPasswordLabel() {
		lblRepeatNewPassword = new JLabel(getString("repeat_new_password_label"));
		GridBagConstraints gbc_lblRepeatNewPassword = new GridBagConstraints();
		gbc_lblRepeatNewPassword.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		gbc_lblRepeatNewPassword.insets = new Insets(0, 0, 0, 5);
		gbc_lblRepeatNewPassword.gridx = 0;
		gbc_lblRepeatNewPassword.gridy = 3;
		fieldsPanel.add(lblRepeatNewPassword, gbc_lblRepeatNewPassword);
	}

	private void buildNewPasswordField() {
		newPasswordField = new JPasswordField();
		newPasswordField.setBackground(new Color(255, 255, 153));
		GridBagConstraints gbc_newPasswordField = new GridBagConstraints();
		gbc_newPasswordField.anchor = GridBagConstraints.NORTH;
		gbc_newPasswordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_newPasswordField.insets = new Insets(3, 0, 5, 0);
		gbc_newPasswordField.gridx = 1;
		gbc_newPasswordField.gridy = 2;
		fieldsPanel.add(newPasswordField, gbc_newPasswordField);
	}

	private void buildNewPasswordLabel() {
		lblNewPassword = new JLabel(getString("new_password_label"));
		GridBagConstraints gbc_lblNewPassword = new GridBagConstraints();
		gbc_lblNewPassword.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		gbc_lblNewPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewPassword.gridx = 0;
		gbc_lblNewPassword.gridy = 2;
		fieldsPanel.add(lblNewPassword, gbc_lblNewPassword);
	}

	private void buildFullNameField() {
		fullNameField = new JTextField();
		fullNameField.setEditable(false);
		fullNameField.setText(fullName);
		GridBagConstraints gbc_fullNameField = new GridBagConstraints();
		gbc_fullNameField.insets = new Insets(0, 0, 5, 0);
		gbc_fullNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_fullNameField.gridx = 1;
		gbc_fullNameField.gridy = 1;
		fieldsPanel.add(fullNameField, gbc_fullNameField);
		fullNameField.setColumns(10);
	}

	private void buildFullNameLabel() {
		lblFullName = new JLabel(getString("fullname_label"));
		GridBagConstraints gbc_lblFullName = new GridBagConstraints();
		gbc_lblFullName.anchor = GridBagConstraints.EAST;
		gbc_lblFullName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFullName.gridx = 0;
		gbc_lblFullName.gridy = 1;
		fieldsPanel.add(lblFullName, gbc_lblFullName);
	}

	private void buildUserNameField() {
		userNameField = new JTextField();
		userNameField.setEditable(false);
		userNameField.setText(userName);
		GridBagConstraints gbc_userNameField = new GridBagConstraints();
		gbc_userNameField.insets = new Insets(0, 0, 5, 0);
		gbc_userNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_userNameField.gridx = 1;
		gbc_userNameField.gridy = 0;
		fieldsPanel.add(userNameField, gbc_userNameField);
		userNameField.setColumns(10);
	}

	private void buildUserNameLabel() {
		lblUserName = new JLabel(getString("user_name_label"));
		GridBagConstraints gbc_lblUserName = new GridBagConstraints();
		gbc_lblUserName.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		gbc_lblUserName.insets = new Insets(0, 0, 5, 5);
		gbc_lblUserName.gridx = 0;
		gbc_lblUserName.gridy = 0;
		fieldsPanel.add(lblUserName, gbc_lblUserName);
	}

	private void buildFieldsPanel() {
		fieldsPanel = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		getContentPane().add(fieldsPanel, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{120, 38, 0};
		gbl_panel_1.rowHeights = new int[]{20, 0, 20, 20, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		fieldsPanel.setLayout(gbl_panel_1);
	}

	private void buildDialogIcon() {
		lblKeyIcon = new JLabel("");
		lblKeyIcon.setIcon(new ImageIcon(ChangeUserPasswordDialog.class.getResource("/com/kianama3/console/resources/change_password-64.png")));
		GridBagConstraints gbc_lblKeyIcon = new GridBagConstraints();
		gbc_lblKeyIcon.anchor = GridBagConstraints.WEST;
		gbc_lblKeyIcon.fill = GridBagConstraints.VERTICAL;
		gbc_lblKeyIcon.insets = new Insets(0, 5, 5, 5);
		gbc_lblKeyIcon.gridx = 0;
		gbc_lblKeyIcon.gridy = 0;
		getContentPane().add(lblKeyIcon, gbc_lblKeyIcon);
	}

	private void buildDialogLayout() {
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{77, 416, 0};
		gridBagLayout.rowHeights = new int[]{90, 33, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
	}
	
	private ActionListener onCancelButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		};
	}
	
	private ActionListener onChangePassButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (newPasswordField.getPassword().length == 0 ||
					repeatPasswordField.getPassword().length == 0)
					JOptionPane.showMessageDialog(null, 
							getString("notify_mandatory_fields_text"),
							getString("error_title"),
							JOptionPane.ERROR_MESSAGE);				
				else if (!Arrays.equals(newPasswordField.getPassword(),
						repeatPasswordField.getPassword()))
						JOptionPane.showMessageDialog(null, 
								getString("two_passwords_are_not_equal_error"),
								getString("error_title"),
								JOptionPane.ERROR_MESSAGE);
				else {
					RemoteServerAccess.changeUserPassword(userName,
							newPasswordField.getPassword());
					dispose();
				}
			}
		};
	}
	
	private String getString(String resourceName){
		return KianamaResourceBundle.getString(resourceName);
	}
}
