package com.kianama3.console;

import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.MessageFormat;
import javax.swing.ImageIcon;

import com.kianama3.console.common.KianamaResourceBundle;
import com.kianama3.console.common.RemoteServerAccess;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class UnlockDialog extends JDialog{
	private JPasswordField passwordField;
	private JLabel lblPassword;
	private JLabel lblNewLabel;
	private JLabel lblUserIsLocked;
	private String message;
	private JButton unlockButton;
	private ManagementConsoleForm form;

	private JButton closeButton;
	public UnlockDialog(ManagementConsoleForm form) {
		this.form = form;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{76, 51, 41, 0, 50, 20, 48, 0};
		gridBagLayout.rowHeights = new int[]{29, 69, 33, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		message = KianamaResourceBundle.getString("user_locked_session_label");
		lblUserIsLocked = new JLabel(MessageFormat.format(message, RemoteServerAccess.getUserName()));
		lblUserIsLocked.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblUserIsLocked = new GridBagConstraints();
		gbc_lblUserIsLocked.anchor = GridBagConstraints.ABOVE_BASELINE;
		gbc_lblUserIsLocked.insets = new Insets(0, 5, 5, 0);
		gbc_lblUserIsLocked.gridwidth = 7;
		gbc_lblUserIsLocked.gridx = 0;
		gbc_lblUserIsLocked.gridy = 0;
		getContentPane().add(lblUserIsLocked, gbc_lblUserIsLocked);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(KianamaResourceBundle.getResource("1347446085_secure-server-px-png.png")));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		lblPassword = new JLabel(KianamaResourceBundle.getString("password_label"));
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.ABOVE_BASELINE;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 1;
		getContentPane().add(lblPassword, gbc_lblPassword);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.gridwidth = 5;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 1;
		getContentPane().add(passwordField, gbc_passwordField);
		
		unlockButton = new JButton(KianamaResourceBundle.getString("unlock_label"));
		unlockButton.addActionListener(onUnlockButtonClicked());
		GridBagConstraints gbc_unlockButton = new GridBagConstraints();
		gbc_unlockButton.anchor = GridBagConstraints.EAST;
		gbc_unlockButton.fill = GridBagConstraints.VERTICAL;
		gbc_unlockButton.insets = new Insets(0, 0, 10, 5);
		gbc_unlockButton.gridx = 3;
		gbc_unlockButton.gridy = 2;
		getContentPane().add(unlockButton, gbc_unlockButton);
		
		closeButton = new JButton(KianamaResourceBundle.getString("close_label"));
		closeButton.addActionListener(onCloseButtonClicked());
		GridBagConstraints gbc_closeButton = new GridBagConstraints();
		gbc_closeButton.insets = new Insets(0, 0, 10, 5);
		gbc_closeButton.anchor = GridBagConstraints.WEST;
		gbc_closeButton.fill = GridBagConstraints.VERTICAL;
		gbc_closeButton.gridx = 4;
		gbc_closeButton.gridy = 2;
		getContentPane().add(closeButton, gbc_closeButton);
		
		getRootPane().setDefaultButton(unlockButton);
		setModal(true);
		setResizable(false);
		
		addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		        try {
					RemoteServerAccess.logout();
				} catch (Exception exp) {
					// TODO Auto-generated catch block
					exp.printStackTrace();
				}
				System.exit(0);
		    }
		});
	}
	private ActionListener onUnlockButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					RemoteServerAccess.unlock(passwordField.getPassword());
					form.setVisible(true);
					dispose();
				} catch (Exception e1) {

				}
			}
		};
	}
	private ActionListener onCloseButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO close session in remote server
				//TODO save changes and do clean up
				try{
					RemoteServerAccess.logout();
				}catch(Exception exp){}
				System.exit(0);
			}
		};
	}

}
