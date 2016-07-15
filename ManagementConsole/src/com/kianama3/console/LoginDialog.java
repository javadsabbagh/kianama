package com.kianama3.console;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.logging.Logger;

import com.kianama3.console.common.KianamaResourceBundle;
import com.kianama3.console.common.RemoteServerAccess;
import com.kianama3.console.logging.KianamaGuiLogger;

import java.util.Locale;
import java.util.prefs.Preferences;
import javax.swing.JToggleButton;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.SpinnerNumberModel;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class LoginDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentsPanel = new JPanel();
	private JTextField userNameField;
	private JPasswordField passwordField;
	private final static Logger LOGGER = Logger.getLogger(LoginDialog.class.getName());
	private JLabel iconLabel;
	private JLabel userNameLabel;
	private JLabel passwordLabel;

	public static final Preferences prefs = Preferences.userRoot().node("Kianama_Management_Console");
	private JButton loginButton;
	private JButton cancelButton;
	private JPanel buttonPane;
	private JTextField serverField;
	private JPanel moreOptionsPanel;
	private JSpinner serverPort;
	private JComboBox languageComboBox;
	private JLabel lblLanguage;
	private JLabel lblPort;
	private JLabel lblServer;
	private JToggleButton moreButton;
	private JButton defaultSettingsButton;
	private static LoginDialog loginDialog;
	private JLabel lblHostserial;
	private JLabel lblSerialvalue;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				LoginDialog.setupProgramLanguage();
				LoginDialog.setupProgramLogger();
				LoginDialog.setupProgramLookAndFeel();
				loginDialog = new LoginDialog();
			}
		});
	}

	public static void setupProgramLogger() {
		try {
			KianamaGuiLogger.setup();
		} catch (IOException e) {
			// Logger Setup Exception, Show it to user
			JOptionPane.showMessageDialog(null,
					KianamaResourceBundle.getString("logger_setup_error"),
					KianamaResourceBundle.getString("error_title"),
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void setupProgramLookAndFeel() {
		try {
			String lnf = prefs.get("look_and_feel",UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel(lnf);
			UIManager.getLookAndFeelDefaults().put("defaultFont",new Font("Tahoma", Font.PLAIN, 11));
		} catch (Exception exp) {
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception e) {
				LOGGER.severe(e.getMessage());
			}
		}
	}

	public static void setupProgramLanguage() {
		UIManager.getDefaults().addResourceBundle("com.kianama3.server.gui.Management_Console");
		String lang = prefs.get("lang", Locale.getDefault().getLanguage());
		Locale.setDefault(new Locale(lang));
	}

	public LoginDialog() {
		createLoginDialogControls();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public void translate() {
		// rename some(not all) components in new language
		setTitle(KianamaResourceBundle.getString("login_title"));
		userNameLabel.setText(KianamaResourceBundle.getString("username_label"));
		passwordLabel.setText(KianamaResourceBundle.getString("password_label"));
		moreButton.setIcon(new ImageIcon(KianamaResourceBundle.getResource(KianamaResourceBundle
						.getString("more_detail_icon"))));
		moreButton.setToolTipText(KianamaResourceBundle.getString("more_detail_tip_text"));
		lblServer.setText(KianamaResourceBundle.getString("server_label"));
		serverField.setToolTipText(KianamaResourceBundle.getString("server_tip_text"));
		lblPort.setText(KianamaResourceBundle.getString("port_label"));
		serverPort.setToolTipText(KianamaResourceBundle.getString("port_tip_text"));
		lblLanguage.setText(KianamaResourceBundle.getString("language_label"));
		defaultSettingsButton.setText(KianamaResourceBundle.getString("default_settings_label"));
		defaultSettingsButton.setToolTipText(KianamaResourceBundle.getString("default_settings_tip_text"));
		loginButton.setText(KianamaResourceBundle.getString("login_label"));
		cancelButton.setText(KianamaResourceBundle.getString("cancel_label"));
		lblHostserial.setText(KianamaResourceBundle.getString("host_serial_label"));
	}

	public void setComponentOrientation(ComponentOrientation o) {
		contentsPanel.setComponentOrientation(o);
		moreOptionsPanel.setComponentOrientation(o);
		buttonPane.setComponentOrientation(o);

		if (o.equals(ComponentOrientation.RIGHT_TO_LEFT))
			((FlowLayout) buttonPane.getLayout()).setAlignment(FlowLayout.LEFT);
		else
			((FlowLayout) buttonPane.getLayout())
					.setAlignment(FlowLayout.RIGHT);

		//serverPort.setComponentOrientation(o);
		languageComboBox.setComponentOrientation(o);
	}

	private void createLoginDialogControls() {
		setTitle(KianamaResourceBundle.getString("login_title"));
		setBounds(100, 100, 317, 239);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(contentsPanel, BorderLayout.NORTH);
		// contentsPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		GridBagLayout gbl_contentsPanel = new GridBagLayout();
		gbl_contentsPanel.columnWidths = new int[] { 75, 38, 86, 6, 121, 0 };
		gbl_contentsPanel.rowHeights = new int[] { 0, 0, 33, 0 };
		gbl_contentsPanel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0,
				0.0, Double.MIN_VALUE };
		gbl_contentsPanel.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		contentsPanel.setLayout(gbl_contentsPanel);

		buildUserIcon();
		buildUserNameLabel();
		buildUserNameField();
		buildPasswordLabel();
		buildPasswordField();
		buildMoreOptionsButton();
		buildMoreOptionsPanel();
		buildServerLabel();
		buildServerField();
		buildPortLabel();
		buildServerPort();
		buildLanguageLabel();
		buildLanguageComboBox();

		buildDefaultSettingsButton();
		buildButtonPane();
		buildLoginButton();
		buildCancelButton();
		
		buildHostSerial();

		if (Locale.getDefault().getDisplayLanguage().equals("Persian"))
			languageComboBox.setSelectedIndex(0);
		else
			languageComboBox.setSelectedIndex(1);
		
		setResizable(false);
		pack();
	}

	private void buildUserIcon() {
		iconLabel = new JLabel("");
		iconLabel.setIcon(new ImageIcon(KianamaResourceBundle
				.getResource("Client-Male-Dark-64.png")));
		iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_iconLabel = new GridBagConstraints();
		gbc_iconLabel.anchor = GridBagConstraints.NORTH;
		gbc_iconLabel.gridheight = 3;
		gbc_iconLabel.insets = new Insets(5, 0, 0, 5);
		gbc_iconLabel.gridx = 0;
		gbc_iconLabel.gridy = 0;
		contentsPanel.add(iconLabel, gbc_iconLabel);
	}

	private void buildUserNameLabel() {
		userNameLabel = new JLabel(
				KianamaResourceBundle.getString("username_label"));
		GridBagConstraints gbc_userNameLabel = new GridBagConstraints();
		gbc_userNameLabel.anchor = GridBagConstraints.EAST;
		gbc_userNameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_userNameLabel.gridx = 1;
		gbc_userNameLabel.gridy = 0;
		contentsPanel.add(userNameLabel, gbc_userNameLabel);
	}

	private void buildUserNameField() {
		userNameField = new JTextField();
		userNameField.setColumns(10);
		GridBagConstraints gbc_userNameField = new GridBagConstraints();
		gbc_userNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_userNameField.gridwidth = 3;
		gbc_userNameField.insets = new Insets(10, 0, 5, 7);
		gbc_userNameField.gridx = 2;
		gbc_userNameField.gridy = 0;
		contentsPanel.add(userNameField, gbc_userNameField);
	}

	private void buildPasswordLabel() {
		passwordLabel = new JLabel(
				KianamaResourceBundle.getString("password_label"));
		GridBagConstraints gbc_passwordLabel = new GridBagConstraints();
		gbc_passwordLabel.anchor = GridBagConstraints.EAST;
		gbc_passwordLabel.insets = new Insets(0, 0, 5, 5);
		gbc_passwordLabel.gridx = 1;
		gbc_passwordLabel.gridy = 1;
		contentsPanel.add(passwordLabel, gbc_passwordLabel);
	}

	private void buildPasswordField() {
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridwidth = 3;
		gbc_passwordField.insets = new Insets(0, 0, 5, 7);
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 1;
		contentsPanel.add(passwordField, gbc_passwordField);
	}

	private void buildMoreOptionsButton() {
		moreButton = new JToggleButton("");
		moreButton.setIcon(new ImageIcon(KianamaResourceBundle
				.getResource(KianamaResourceBundle
						.getString("more_detail_icon"))));
		moreButton.setToolTipText(KianamaResourceBundle
				.getString("more_detail_tip_text"));
		moreButton.addItemListener(onMoreButtonClicked());
		GridBagConstraints gbc_toggleButton = new GridBagConstraints();
		gbc_toggleButton.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gbc_toggleButton.insets = new Insets(0, 0, 0, 5);
		gbc_toggleButton.gridx = 2;
		gbc_toggleButton.gridy = 2;
		contentsPanel.add(moreButton, gbc_toggleButton);
	}

	private void buildLanguageLabel() {
		lblLanguage = new JLabel(
				KianamaResourceBundle.getString("language_label"));
		GridBagConstraints gbc_lblLanguage = new GridBagConstraints();
		gbc_lblLanguage.insets = new Insets(0, 5, 5, 5);
		gbc_lblLanguage.gridx = 0;
		gbc_lblLanguage.gridy = 2;
		moreOptionsPanel.add(lblLanguage, gbc_lblLanguage);
	}

	private void buildServerPort() {
		serverPort = new JSpinner();
		serverPort.setModel(new SpinnerNumberModel(1100, 1, 32767, 1));

		//serverPort.setEditor(new JSpinner.NumberEditor(serverPort,"#"));
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(serverPort);
		editor.getFormat().setGroupingUsed(false);
		serverPort.setEditor(editor);
		
		serverPort.setValue(new Integer(1099));
		
		serverPort.setToolTipText(KianamaResourceBundle.getString("port_tip_text"));
		GridBagConstraints gbc_serverPort = new GridBagConstraints();
		gbc_serverPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_serverPort.anchor = GridBagConstraints.ABOVE_BASELINE;
		gbc_serverPort.insets = new Insets(0, 0, 5, 5);
		gbc_serverPort.gridx = 1;
		gbc_serverPort.gridy = 1;
		moreOptionsPanel.add(serverPort, gbc_serverPort);
	}

	private void buildPortLabel() {
		lblPort = new JLabel(KianamaResourceBundle.getString("port_label"));
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.insets = new Insets(0, 5, 5, 5);
		gbc_lblPort.gridx = 0;
		gbc_lblPort.gridy = 1;
		moreOptionsPanel.add(lblPort, gbc_lblPort);
	}

	private void buildServerField() {
		serverField = new JTextField();
		serverField.setToolTipText(KianamaResourceBundle
				.getString("server_tip_text"));
		GridBagConstraints gbc_serverField = new GridBagConstraints();
		gbc_serverField.gridwidth = 2;
		gbc_serverField.anchor = GridBagConstraints.NORTH;
		gbc_serverField.fill = GridBagConstraints.HORIZONTAL;
		gbc_serverField.insets = new Insets(6, 0, 5, 0);
		gbc_serverField.gridx = 1;
		gbc_serverField.gridy = 0;
		moreOptionsPanel.add(serverField, gbc_serverField);
		serverField.setColumns(10);
	}

	private void buildServerLabel() {
		lblServer = new JLabel(KianamaResourceBundle.getString("server_label"));
		GridBagConstraints gbc_lblServer = new GridBagConstraints();
		gbc_lblServer.insets = new Insets(6, 5, 5, 5);
		gbc_lblServer.gridx = 0;
		gbc_lblServer.gridy = 0;
		moreOptionsPanel.add(lblServer, gbc_lblServer);
	}

	private void buildMoreOptionsPanel() {
		moreOptionsPanel = new JPanel();
		getContentPane().add(moreOptionsPanel, BorderLayout.CENTER);
		GridBagLayout gbl_moreOptionsPanel = new GridBagLayout();
		gbl_moreOptionsPanel.columnWidths = new int[] { 47, 104, 89, 0 };
		gbl_moreOptionsPanel.rowHeights = new int[] { 20, 20, 20, 23, 0 };
		gbl_moreOptionsPanel.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_moreOptionsPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		moreOptionsPanel.setLayout(gbl_moreOptionsPanel);
	}

	private void buildLanguageComboBox() {
		languageComboBox = new JComboBox();
		languageComboBox.addActionListener(onLanguageComBoxSelectionChanged());
		languageComboBox.setModel(new DefaultComboBoxModel(new String[] {
				KianamaResourceBundle.getString("farsi_label"),
				KianamaResourceBundle.getString("english_label") }));
		
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.BOTH;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 2;
		moreOptionsPanel.add(languageComboBox, gbc_comboBox);
		moreOptionsPanel.setVisible(false);
	}

	private void buildCancelButton() {
		cancelButton = new JButton(KianamaResourceBundle.getString("cancel_label"));
		cancelButton.addActionListener(onCancelButtonClicked());
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}

	private void buildLoginButton() {
		loginButton = new JButton(KianamaResourceBundle.getString("login_label"));
		loginButton.addActionListener(onLoginButtonClicked());
		loginButton.setActionCommand("OK");
		buttonPane.add(loginButton);
		getRootPane().setDefaultButton(loginButton);
	}

	private void buildButtonPane() {
		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
	}

	private void buildDefaultSettingsButton() {
		defaultSettingsButton = new JButton(KianamaResourceBundle.getString("default_settings_label"));
		defaultSettingsButton.addActionListener(onDefaultSettingsButtonClicked());
		defaultSettingsButton.setToolTipText(KianamaResourceBundle.getString("default_settings_tip_text"));
		GridBagConstraints gbc_defaultSettingsButton = new GridBagConstraints();
		gbc_defaultSettingsButton.insets = new Insets(0, 0, 5, 0);
		gbc_defaultSettingsButton.gridx = 2;//2;
		gbc_defaultSettingsButton.gridy = 2;
		moreOptionsPanel.add(defaultSettingsButton, gbc_defaultSettingsButton);
	}

	private ActionListener onDefaultSettingsButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverField.setText(KianamaResourceBundle.getSetting("server_address"));
				serverPort.setValue(Integer.parseInt(KianamaResourceBundle.getSetting("server_port")));
			}
		};
	}

	private void buildHostSerial() {
		lblHostserial = new JLabel(KianamaResourceBundle.getString("host_serial_label"));
		GridBagConstraints gbc_lblHostserial = new GridBagConstraints();
		gbc_lblHostserial.insets = new Insets(0, 0, 0, 5);
		gbc_lblHostserial.gridx = 1;
		gbc_lblHostserial.gridy = 3;
		moreOptionsPanel.add(lblHostserial, gbc_lblHostserial);
		
		lblSerialvalue = new JLabel("");
		setSerialValue();
		GridBagConstraints gbc_lblSerialvalue = new GridBagConstraints();
		gbc_lblSerialvalue.gridx = 2;
		gbc_lblSerialvalue.gridy = 3;
		moreOptionsPanel.add(lblSerialvalue, gbc_lblSerialvalue);
	}
	
	private void setSerialValue(){
			String encrypt_key = KianamaResourceBundle.getSetting("serial_encrypt_key");
			String txt = "<html><b>"+getCpuID()+"</b></html>";
			lblSerialvalue.setText(txt);
	}

	private String getCpuID(){		
		try {
			String id = "";
			String[] query = {
					"cscript.exe" ,
					"//Nologo",
					getScriptTempFile()
					};					

			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(query);
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			line = br.readLine();
			
			if (line != null){  //just one line
				id = line.split(":")[1].trim();
			}
			return id;
		} catch (IOException e) {
			//TODO show user there is an error
			JOptionPane.showMessageDialog(null, "error in getting cpu id","error",JOptionPane.ERROR_MESSAGE);
			return "";
		}
	}
	
	private String getScriptTempFile(){
		try {
		    File tempFile = File.createTempFile("get_cpu_id", ".vbs");
		    tempFile.deleteOnExit();

		    BufferedWriter out = new BufferedWriter(new FileWriter(tempFile));
		    out.write(KianamaResourceBundle.getScript("get_cpu_id"));
		    out.close();
		    
		    return tempFile.toString();
		} catch (IOException e) {
			return "";
		}
	}
	
	private ActionListener onLanguageComBoxSelectionChanged() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (languageComboBox.getSelectedIndex() == 0) {
					setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
					Locale.setDefault(new Locale("fa"));
					translate();
					pack();
					LoginDialog.prefs.put("lang", "fa");
				} else {
					setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
					Locale.setDefault(new Locale("en"));
					translate();
					pack();
					LoginDialog.prefs.put("lang", "en");
				}
			}
		};
	}

	private ItemListener onMoreButtonClicked() {
		return new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					moreOptionsPanel.setVisible(true);
					moreButton.setIcon(new ImageIcon(KianamaResourceBundle
							.getResource(KianamaResourceBundle
									.getString("less_detail_icon"))));
				} else {
					moreOptionsPanel.setVisible(false);
					moreButton.setIcon(new ImageIcon(KianamaResourceBundle
							.getResource(KianamaResourceBundle
									.getString("more_detail_icon"))));
				}
				pack();
			}
		};
	}
	
	private ActionListener onCancelButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				System.exit(0);		
			}
		};
	}

	private ActionListener onLoginButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try{
					RemoteServerAccess.setup(serverField.getText(), getSelectedPort());
					RemoteServerAccess.login(userNameField.getText(), passwordField.getPassword(), Locale.getDefault());
					new ManagementConsoleForm(RemoteServerAccess.getUserPriviliges(userNameField.getText()));
					loginDialog.dispose();
				}catch(Exception e){
					// do nothing, already handled
					//but there may be ManagementConsoleForm Exceptions, so catch it
					e.printStackTrace();
				}  
			}
		};
	}
	
	private int getSelectedPort() {
		try{
			int port = Integer.parseInt(((JSpinner.DefaultEditor)serverPort.getEditor()).getTextField().getText());
			return port;
		}catch(Exception e){
			return -1;
		}
	}
}
