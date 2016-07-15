package com.kianama3.console;

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

public class ChangePasswordDialog extends JDialog{
	private JPasswordField oldPasswordField;
	private JPasswordField newPasswordField;
	private JPasswordField repeatPasswordField;
	private JPanel panel;
	private JButton changePassButton;
	private JButton cancelButton;
	private JLabel lblKeyIcon;
	private JPanel fieldsPanel;
	private JLabel lblCurrentPassword;
	private JLabel lblNewPassword;
	private JLabel lblRepeatNewPassword;
	private GridBagLayout gridBagLayout;
	
	public ChangePasswordDialog() {
		buildDialogLayout();		
		buildDialogIcon();		
		buildFieldsPanel();		
		buildPasswordLabel();		
		buildOldPasswordField();		
		buildNewPasswordLabel();		
		buildNewPasswordField();		
		buildRepeatPasswordLabel();		
		buildRepeatPasswordField();		
		buildButtonsPanel();
		
		getRootPane().setDefaultButton(changePassButton);
		setResizable(false);
		setModal(true);
		setTitle(KianamaResourceBundle.getString("change_pass_title"));
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
		
		changePassButton = new JButton(KianamaResourceBundle.getString("apply_label"));
		changePassButton.addActionListener(onChangePassButtonClicked());
		
		cancelButton = new JButton(KianamaResourceBundle.getString("cancel_label"));
		cancelButton.addActionListener(onCancelButtonClicked());
		panel.add(cancelButton);
		panel.add(changePassButton);
	}

	private void buildRepeatPasswordField() {
		repeatPasswordField = new JPasswordField();
		repeatPasswordField.setBackground(new Color(255, 255, 153));
		GridBagConstraints gbc_repeatPasswordField = new GridBagConstraints();
		gbc_repeatPasswordField.insets = new Insets(3, 0, 0, 5);
		gbc_repeatPasswordField.anchor = GridBagConstraints.NORTH;
		gbc_repeatPasswordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_repeatPasswordField.gridx = 1;
		gbc_repeatPasswordField.gridy = 2;
		fieldsPanel.add(repeatPasswordField, gbc_repeatPasswordField);
	}

	private void buildRepeatPasswordLabel() {
		lblRepeatNewPassword = new JLabel(KianamaResourceBundle.getString("repeat_new_password_label"));
		GridBagConstraints gbc_lblRepeatNewPassword = new GridBagConstraints();
		gbc_lblRepeatNewPassword.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		gbc_lblRepeatNewPassword.insets = new Insets(0, 0, 0, 5);
		gbc_lblRepeatNewPassword.gridx = 0;
		gbc_lblRepeatNewPassword.gridy = 2;
		fieldsPanel.add(lblRepeatNewPassword, gbc_lblRepeatNewPassword);
	}

	private void buildNewPasswordField() {
		newPasswordField = new JPasswordField();
		newPasswordField.setBackground(new Color(255, 255, 153));
		GridBagConstraints gbc_newPasswordField = new GridBagConstraints();
		gbc_newPasswordField.anchor = GridBagConstraints.NORTH;
		gbc_newPasswordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_newPasswordField.insets = new Insets(3, 0, 5, 5);
		gbc_newPasswordField.gridx = 1;
		gbc_newPasswordField.gridy = 1;
		fieldsPanel.add(newPasswordField, gbc_newPasswordField);
	}

	private void buildNewPasswordLabel() {
		lblNewPassword = new JLabel(KianamaResourceBundle.getString("new_password_label"));
		GridBagConstraints gbc_lblNewPassword = new GridBagConstraints();
		gbc_lblNewPassword.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		gbc_lblNewPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewPassword.gridx = 0;
		gbc_lblNewPassword.gridy = 1;
		fieldsPanel.add(lblNewPassword, gbc_lblNewPassword);
	}

	private void buildOldPasswordField() {
		oldPasswordField = new JPasswordField();
		oldPasswordField.setBackground(new Color(255, 255, 153));
		GridBagConstraints gbc_oldPasswordField = new GridBagConstraints();
		gbc_oldPasswordField.anchor = GridBagConstraints.NORTH;
		gbc_oldPasswordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_oldPasswordField.insets = new Insets(5, 0, 5, 5);
		gbc_oldPasswordField.gridx = 1;
		gbc_oldPasswordField.gridy = 0;
		fieldsPanel.add(oldPasswordField, gbc_oldPasswordField);
	}

	private void buildPasswordLabel() {
		lblCurrentPassword = new JLabel(KianamaResourceBundle.getString("current_password_label"));
		GridBagConstraints gbc_lblCurrentPassword = new GridBagConstraints();
		gbc_lblCurrentPassword.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		gbc_lblCurrentPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentPassword.gridx = 0;
		gbc_lblCurrentPassword.gridy = 0;
		fieldsPanel.add(lblCurrentPassword, gbc_lblCurrentPassword);
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
		gbl_panel_1.rowHeights = new int[]{20, 20, 20, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		fieldsPanel.setLayout(gbl_panel_1);
	}

	private void buildDialogIcon() {
		lblKeyIcon = new JLabel("");
		lblKeyIcon.setIcon(new ImageIcon(ChangePasswordDialog.class.getResource("/com/kianama3/console/resources/change_password-64.png")));
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
				if (oldPasswordField.getPassword().length == 0 ||
					newPasswordField.getPassword().length == 0 ||
					repeatPasswordField.getPassword().length == 0)
					JOptionPane.showMessageDialog(null, 
							KianamaResourceBundle.getString("notify_mandatory_fields_text"),
							KianamaResourceBundle.getString("error_title"),
							JOptionPane.ERROR_MESSAGE);				
				else if (!Arrays.equals(newPasswordField.getPassword(),
						repeatPasswordField.getPassword()))
						JOptionPane.showMessageDialog(null, 
								KianamaResourceBundle.getString("two_passwords_are_not_equal_error"),
								KianamaResourceBundle.getString("error_title"),
								JOptionPane.ERROR_MESSAGE);
				else {
					RemoteServerAccess.changePassword(oldPasswordField.getPassword(), 
													newPasswordField.getPassword());
					dispose();
				}
			}
		};
	}
}
