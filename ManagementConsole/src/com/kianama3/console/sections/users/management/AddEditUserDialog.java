package com.kianama3.console.sections.users.management;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.kianama3.console.common.KianamaResourceBundle;
import com.kianama3.console.common.RemoteServerAccess;
import com.kianama3.console.calendar.PersianDateChooser;
import com.kianama3.server.remote.common.UserData;

import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class AddEditUserDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	private JPasswordField passwordField;
	private JTextField expireDateField;
	private JTextField fullNameTextField;
	private JTextField userNameField;
	private JButton btnAdd;
	private JButton btnCancel;
	private JLabel lblUserName;
	private JLabel lblFullName;
	private JLabel lblExpirationDate;
	private JLabel passwordLabel;
	private JCheckBox changePassCheckBox;
	private JButton expireDateButton;
	private JCheckBox disabledCheckBox;
	private JTextField disabledDateField;
	private JButton disabledDateButton;
	private final JDialog dlg = this;
	private GridBagLayout gridBagLayout;
	public enum DialogType {AddDialog,EditDialog};
	public enum ActionType {Approved,Canceled};
	private DialogType dialogType;
	public ActionType userAction;
	private JLabel repeatPassLabel;
	private JPasswordField repeatPasswordField;

	public AddEditUserDialog(){
		buildDialog();
		
		setTitle(KianamaResourceBundle.getString("add_user_title"));
		btnAdd.setText(KianamaResourceBundle.getString("add_label"));
		dialogType = DialogType.AddDialog;
	}
	
	public AddEditUserDialog(UserData u){
		buildDialog();
		
		passwordLabel.setVisible(false);
		passwordField.setVisible(false);
		repeatPassLabel.setVisible(false);
		repeatPasswordField.setVisible(false);

		userNameField.setText(u.userName);
		fullNameTextField.setText(u.fullName);
		expireDateField.setText(u.expireDateTime);
		disabledDateField.setText(u.disableDateTime);
		disabledCheckBox.setSelected(u.userState == 2 ? true : false);
		changePassCheckBox.setSelected(u.changePassNextLogin);
		
		setTitle(KianamaResourceBundle.getString("edit_user_title"));
		btnAdd.setText(KianamaResourceBundle.getString("save_label"));
		dialogType = DialogType.EditDialog;
		
		setModal(true);
		pack();
	}

	private void buildDialog() {
		buildLayout();
		buildUserNameLabel();
		buildNameField();
		buildFullNameLabel();
		buildFullNameField();
		buildExpireDateLabel();
		buildExpireDateField();
		buildExpireDateButton();
		buildPasswordLabel();
		buildPasswordField();
		buildRepeatLabel();
		buildRepeatPasswordField();
		buildDisabledCheckBox();
		buildDisabledDateField();
		buildDisabledDateButton();
		buildChangePassCheckBox();
		buildAddButton();
		buildCancelButton();
		
		//pack();
		setResizable(false);
		getRootPane().setDefaultButton(btnAdd);
	}

	private void buildCancelButton() {
		btnCancel = new JButton(KianamaResourceBundle.getString("cancel_label"));
		btnCancel.addActionListener(onCancelButtonClicked());
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancel.gridx = 3;
		gbc_btnCancel.gridy = 7;
		getContentPane().add(btnCancel, gbc_btnCancel);
	}

	private void buildAddButton() {
		btnAdd = new JButton(KianamaResourceBundle.getString("add_label"));
		btnAdd.addActionListener(onAddButtonClicked());
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdd.gridx = 2;
		gbc_btnAdd.gridy = 7;
		getContentPane().add(btnAdd, gbc_btnAdd);
	}

	private void buildChangePassCheckBox() {
		changePassCheckBox = new JCheckBox(KianamaResourceBundle.getString("change_pass_next_login_label"));
		GridBagConstraints gbc_changePassCheckBox = new GridBagConstraints();
		gbc_changePassCheckBox.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gbc_changePassCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_changePassCheckBox.gridwidth = 3;
		gbc_changePassCheckBox.gridx = 0;
		gbc_changePassCheckBox.gridy = 6;
		getContentPane().add(changePassCheckBox, gbc_changePassCheckBox);
	}

	private void buildDisabledDateButton() {
		disabledDateButton = new JButton("");
		disabledDateButton.addActionListener(onDisableDateButtonClicked());
		disabledDateButton.setIcon(new ImageIcon(KianamaResourceBundle.getResource("1347532287_config-date.png")));
		GridBagConstraints gbc_disabledDateButton = new GridBagConstraints();
		gbc_disabledDateButton.fill = GridBagConstraints.VERTICAL;
		gbc_disabledDateButton.anchor = GridBagConstraints.WEST;
		gbc_disabledDateButton.insets = new Insets(0, 0, 5, 0);
		gbc_disabledDateButton.gridx = 3;
		gbc_disabledDateButton.gridy = 5;
		getContentPane().add(disabledDateButton, gbc_disabledDateButton);
	}

	private ActionListener onDisableDateButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PersianDateChooser dateChooser = new PersianDateChooser((JDialog)null, "Select Date");

				disabledDateField.setText(dateChooser.select());
			}
		};
	}

	private void buildDisabledDateField() {
		disabledDateField = new JTextField();
		disabledDateField.setToolTipText(KianamaResourceBundle.getString("disable_date_tip"));
		GridBagConstraints gbc_disabledDateField = new GridBagConstraints();
		gbc_disabledDateField.gridwidth = 2;
		gbc_disabledDateField.insets = new Insets(0, 0, 5, 5);
		gbc_disabledDateField.fill = GridBagConstraints.HORIZONTAL;
		gbc_disabledDateField.gridx = 1;
		gbc_disabledDateField.gridy = 5;
		getContentPane().add(disabledDateField, gbc_disabledDateField);
		disabledDateField.setColumns(10);
	}

	private void buildDisabledCheckBox() {
		disabledCheckBox = new JCheckBox(KianamaResourceBundle.getString("disabled_label"));
		disabledCheckBox.setSelected(true);
		disabledCheckBox.addItemListener(onDisableCheckBoxClicked());
		
		GridBagConstraints gbc_disabledCheckBox = new GridBagConstraints();
		gbc_disabledCheckBox.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gbc_disabledCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_disabledCheckBox.gridx = 0;
		gbc_disabledCheckBox.gridy = 5;
		getContentPane().add(disabledCheckBox, gbc_disabledCheckBox);
	}

	private void buildRepeatPasswordField() {
		repeatPasswordField = new JPasswordField();
		repeatPasswordField.setBackground(new Color(255, 255, 153));
		GridBagConstraints gbc_repeatPasswordField = new GridBagConstraints();
		gbc_repeatPasswordField.gridwidth = 3;
		gbc_repeatPasswordField.insets = new Insets(0, 0, 5, 5);
		gbc_repeatPasswordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_repeatPasswordField.gridx = 1;
		gbc_repeatPasswordField.gridy = 4;
		getContentPane().add(repeatPasswordField, gbc_repeatPasswordField);
	}

	private void buildRepeatLabel() {
		repeatPassLabel = new JLabel(KianamaResourceBundle.getString("repeat_new_password_label"));
		GridBagConstraints gbc_repeatPassLabel = new GridBagConstraints();
		gbc_repeatPassLabel.anchor = GridBagConstraints.EAST;
		gbc_repeatPassLabel.insets = new Insets(0, 0, 5, 5);
		gbc_repeatPassLabel.gridx = 0;
		gbc_repeatPassLabel.gridy = 4;
		getContentPane().add(repeatPassLabel, gbc_repeatPassLabel);
	}

	private ItemListener onDisableCheckBoxClicked() {
		return new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED){
					disabledDateField.setEnabled(true);
					disabledDateButton.setEnabled(true);
				}else{
					disabledDateField.setEnabled(false);
					disabledDateButton.setEnabled(false);
				}
			}
		};
	}

	private void buildPasswordField() {
		passwordField = new JPasswordField();
		passwordField.setBackground(new Color(255, 255, 153));
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.anchor = GridBagConstraints.NORTH;
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.gridwidth = 3;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 3;
		getContentPane().add(passwordField, gbc_passwordField);
	}

	private void buildPasswordLabel() {
		passwordLabel = new JLabel(KianamaResourceBundle.getString("password_label"));
		GridBagConstraints gbc_password = new GridBagConstraints();
		gbc_password.anchor = GridBagConstraints.BASELINE_TRAILING;
		gbc_password.insets = new Insets(0, 0, 5, 5);
		gbc_password.gridx = 0;
		gbc_password.gridy = 3;
		getContentPane().add(passwordLabel, gbc_password);
	}

	private void buildExpireDateButton() {
		expireDateButton = new JButton("");
		expireDateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); 
				PersianDateChooser dateChooser = new PersianDateChooser((JDialog)null, "Select Date");

/*				Date newDate = dateChooser.select();
				if (newDate != null) */
					expireDateField.setText(dateChooser.select()/*dateFormat.format(newDate)*/);
			}
		});
		expireDateButton.setIcon(new ImageIcon(KianamaResourceBundle.getResource("1347532287_config-date.png")));
		GridBagConstraints gbc_expireDateButton = new GridBagConstraints();
		gbc_expireDateButton.anchor = GridBagConstraints.WEST;
		gbc_expireDateButton.fill = GridBagConstraints.VERTICAL;
		gbc_expireDateButton.insets = new Insets(0, 0, 5, 0);
		gbc_expireDateButton.gridx = 3;
		gbc_expireDateButton.gridy = 2;
		getContentPane().add(expireDateButton, gbc_expireDateButton);
	}

	private void buildExpireDateField() {
		expireDateField = new JTextField();
		GridBagConstraints gbc_expireDateField = new GridBagConstraints();
		gbc_expireDateField.fill = GridBagConstraints.HORIZONTAL;
		gbc_expireDateField.insets = new Insets(0, 0, 5, 5);
		gbc_expireDateField.gridwidth = 2;
		gbc_expireDateField.gridx = 1;
		gbc_expireDateField.gridy = 2;
		getContentPane().add(expireDateField, gbc_expireDateField);
		expireDateField.setColumns(10);
	}

	private void buildExpireDateLabel() {
		lblExpirationDate = new JLabel(KianamaResourceBundle.getString("expire_date_label"));
		GridBagConstraints gbc_lblExpirationDate = new GridBagConstraints();
		gbc_lblExpirationDate.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		gbc_lblExpirationDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblExpirationDate.gridx = 0;
		gbc_lblExpirationDate.gridy = 2;
		getContentPane().add(lblExpirationDate, gbc_lblExpirationDate);
	}

	private void buildFullNameField() {
		fullNameTextField = new JTextField();
		GridBagConstraints gbc_fullNameTextField = new GridBagConstraints();
		gbc_fullNameTextField.anchor = GridBagConstraints.NORTH;
		gbc_fullNameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_fullNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_fullNameTextField.gridwidth = 3;
		gbc_fullNameTextField.gridx = 1;
		gbc_fullNameTextField.gridy = 1;
		getContentPane().add(fullNameTextField, gbc_fullNameTextField);
		fullNameTextField.setColumns(10);
	}

	private void buildFullNameLabel() {
		lblFullName = new JLabel(KianamaResourceBundle.getString("fullname_label"));
		GridBagConstraints gbc_lblFullName = new GridBagConstraints();
		gbc_lblFullName.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		gbc_lblFullName.insets = new Insets(0, 0, 5, 5);
		gbc_lblFullName.gridx = 0;
		gbc_lblFullName.gridy = 1;
		getContentPane().add(lblFullName, gbc_lblFullName);
	}

	private void buildNameField() {
		userNameField = new JTextField();
		userNameField.setBackground(new Color(255, 255, 153));
		GridBagConstraints gbc_userNameField = new GridBagConstraints();
		gbc_userNameField.anchor = GridBagConstraints.NORTH;
		gbc_userNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_userNameField.insets = new Insets(5, 0, 5, 5);
		gbc_userNameField.gridwidth = 3;
		gbc_userNameField.gridx = 1;
		gbc_userNameField.gridy = 0;
		getContentPane().add(userNameField, gbc_userNameField);
		userNameField.setColumns(10);
	}

	private void buildUserNameLabel() {
		lblUserName = new JLabel(KianamaResourceBundle.getString("username_label"));
		GridBagConstraints gbc_lblUserName = new GridBagConstraints();
		gbc_lblUserName.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		gbc_lblUserName.insets = new Insets(5, 0, 5, 5);
		gbc_lblUserName.gridx = 0;
		gbc_lblUserName.gridy = 0;
		getContentPane().add(lblUserName, gbc_lblUserName);
	}

	private void buildLayout() {
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{122, 74, 76, 70, 0};
		gridBagLayout.rowHeights = new int[]{20, 20, 23, 20, 0, 0, 23, -4, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
	}

	private ActionListener onCancelButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userAction = ActionType.Canceled;
				dlg.dispose();
			}
		};
	}

	private ActionListener onAddButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if(userNameField.getText().trim().isEmpty() ||
					   (passwordField.getPassword().length==0 && dialogType == DialogType.AddDialog) ||
					   (repeatPasswordField.getPassword().length == 0 && dialogType == DialogType.AddDialog)
					   )
							JOptionPane.showMessageDialog(null, 
									KianamaResourceBundle.getString("notify_mandatory_fields_text"),
									KianamaResourceBundle.getString("error_title"),
									JOptionPane.ERROR_MESSAGE);
					else if (!Arrays.equals(passwordField.getPassword(),
											repeatPasswordField.getPassword()))
						JOptionPane.showMessageDialog(null, 
								KianamaResourceBundle.getString("two_passwords_are_not_equal_error"),
								KianamaResourceBundle.getString("error_title"),
								JOptionPane.ERROR_MESSAGE);
					else{						
						UserData u = new UserData();
						u.userName = userNameField.getText();
						u.fullName = fullNameTextField.getText();
						u.expireDateTime = expireDateField.getText();
						u.userState = disabledCheckBox.isSelected() ? 2 : 1;
						u.disableDateTime = disabledDateField.getText();
						u.password = new String(passwordField.getPassword());
						u.changePassNextLogin = changePassCheckBox.isSelected() ? true : false;
						
						switch(dialogType){
							case AddDialog:
								RemoteServerAccess.addNewUser(u);
								break;
							case EditDialog:
								RemoteServerAccess.editUser(u);
								break;
						}
						userAction = ActionType.Approved;
						dlg.dispose();
					}
				}catch(Exception exp){
					//do nothing;already handled. just to bypass dispose() method
				}
			}
		};
	}
}
