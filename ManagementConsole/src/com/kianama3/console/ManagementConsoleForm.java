package com.kianama3.console;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

import javax.swing.ButtonGroup;

import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JToggleButton;
import javax.swing.ImageIcon;

import com.ghasemkiani.util.icu.PersianCalendar;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import com.kianama3.console.common.KianamaResourceBundle;
import com.kianama3.console.common.RemoteServerAccess;
import com.kianama3.console.sections.about.AboutSection;
import com.kianama3.console.sections.clients.ClientsSection;
import com.kianama3.console.sections.database.DatabaseSection;
import com.kianama3.console.sections.reporting.ReportingSection;
import com.kianama3.console.sections.server.ServerSection;
import com.kianama3.console.sections.settings.SettingsSectoin;
import com.kianama3.console.sections.users.UsersSection;
import com.kianama3.server.remote.common.UserPrivilegesData;

import java.awt.Toolkit;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.CardLayout;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Logger;
import java.awt.Font;
import javax.swing.JLabel;


public class ManagementConsoleForm extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(LoginDialog.class.getName());

	private JPanel contentPane;
	private ManagementConsoleForm frame;
	private JMenuBar menuBar;
	private JMenu componentsMenu;
	private ButtonGroup componentGroup;
	private JRadioButtonMenuItem clientMenuItem;
	private JRadioButtonMenuItem serverMenuItem;
	private JRadioButtonMenuItem databaseMenuItem;	
	private JRadioButtonMenuItem usersMenuItem;
	private JRadioButtonMenuItem reportingMenuItem;
	private JRadioButtonMenuItem settingMenuItem;
	private JRadioButtonMenuItem aboutMenuItem;
	private JMenuItem exitMenuItem;
	private JMenu lookAndFeelMenu;
	private JMenu helpMenu;
	private JMenu securityMenu;
	private JMenuItem lockMenuItem;
	private JMenuItem changePassMenuItem;
	private JMenuItem aboutProgMenuItem;
	private JRadioButtonMenuItem englishMenuItem;
	private JRadioButtonMenuItem farsiMenuItem;
	private JMenu languageMenu;
	private JPanel sectionsPanel;
	private JToggleButton serverButton;
	private JToggleButton databaseButton;
	private JToggleButton clientsButton;
	private JToggleButton usersButton;
	private JToggleButton reportingButton;
	private JToggleButton settingsButton;
	private JToggleButton aboutButton;
	private JToolBar toolBar;
	private ButtonGroup componentsButtonGroup;
	private JPanel serverSection;
	private JPanel databaseSection;
	private JPanel clientsSection;
	private JPanel usersSection;
	private JPanel reportingSection;
	private JPanel settingsSection;
	private JPanel aboutSection;
	private JLabel dateLabel;

	public ManagementConsoleForm(UserPrivilegesData r) {
		frame = this;
		createFormControls();
		createMenus();
		createSections();		
		createSectionButtons();
		
		try {
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			setVisible(true);
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
		}
		
		addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		        try {
					RemoteServerAccess.logout();
				} catch (Exception exp) {
					// TODO Auto-generated catch block
					exp.printStackTrace();
				}
		    }
		});
	}

	private void createFormControls() {	
		setIconImage(Toolkit.getDefaultToolkit().getImage(KianamaResourceBundle.getResource("Java-48.png")));
		setTitle(KianamaResourceBundle.getString("program_title"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 768, 746);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		contentPane.add(toolBar, BorderLayout.NORTH);
	}
	
	private void createMenus() {
		createComponentMenu();
		createLookAndFeelMenu();
		createLanguageMenu();
		createSecurityMenu();
		createHelpMenu();
	}

	private void createSections() {
		sectionsPanel = new JPanel();
		sectionsPanel.setLayout(new CardLayout(0, 0));
		contentPane.add(sectionsPanel, BorderLayout.CENTER);

		componentsButtonGroup = new ButtonGroup();
		
		serverSection = new ServerSection();
		sectionsPanel.add(serverSection, "Server");
		
		databaseSection = new DatabaseSection();
		sectionsPanel.add(databaseSection, "Database");
		
		clientsSection = new ClientsSection();
		sectionsPanel.add(clientsSection, "Clients");
		
		usersSection = new UsersSection();
		sectionsPanel.add(usersSection, "Users");
		
		reportingSection = new ReportingSection();
		sectionsPanel.add(reportingSection, "Reporting");
		
		settingsSection = new SettingsSectoin();
		sectionsPanel.add(settingsSection, "Settings");
		
		aboutSection = new AboutSection();
		sectionsPanel.add(aboutSection, "About");
	}
	
	private void createSectionButtons() {
		createServerButton();
		createDatabaseButton();
		createClientsButton();
		createUsersButton();
		createReportingButton();
		createSettingsButton();
		createAboutButton();
		toolBar.addSeparator();
		createExitButton();
	}
	
	private void createServerButton() {
		serverButton = new JToggleButton("");
		serverButton.addItemListener(onServerButtonClicked());
		serverButton.setSelected(true);
		serverButton.setToolTipText(KianamaResourceBundle.getString("server_label"));
		serverButton.setIcon(new ImageIcon(KianamaResourceBundle.getResource("Gnome-Network-Server-48.png")));
		toolBar.add(serverButton);
		componentsButtonGroup.add(serverButton);
	}

	private ItemListener onServerButtonClicked() {
		return new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					((CardLayout) sectionsPanel.getLayout()).show(sectionsPanel,"Server");
					serverMenuItem.setSelected(true);
				}
			}
		};
	}
	
	private void createDatabaseButton() {
		databaseButton = new JToggleButton("");
		databaseButton.addItemListener(onDatabaseButtonClicked());
		databaseButton.setToolTipText(KianamaResourceBundle.getString("database_label"));
		databaseButton.setIcon(new ImageIcon(KianamaResourceBundle.getResource("1347695827_database.png")));
		toolBar.add(databaseButton);
		componentsButtonGroup.add(databaseButton);
	}
	
	private ItemListener onDatabaseButtonClicked() {
		return new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					((CardLayout) sectionsPanel.getLayout()).show(sectionsPanel,"Database");
					databaseMenuItem.setSelected(true);
				}
			}
		};
	}
	
	private void createClientsButton() {
		clientsButton = new JToggleButton("");
		clientsButton.addItemListener(onClientsButtonClicked());
		clientsButton.setToolTipText(KianamaResourceBundle.getString("clients_label"));
		clientsButton.setIcon(new ImageIcon(KianamaResourceBundle.getResource("Gnome-Video-Display-48.png")));
		toolBar.add(clientsButton);
		componentsButtonGroup.add(clientsButton);
	}

	private ItemListener onClientsButtonClicked() {
		return new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED){
				((CardLayout) sectionsPanel.getLayout()).show(sectionsPanel,
						"Clients");
					clientMenuItem.setSelected(true);
				}
			}
		};
	}
	
	private void createUsersButton(){
		usersButton = new JToggleButton("");
		usersButton.addItemListener(onUsersButtonClicked());
		usersButton.setToolTipText(KianamaResourceBundle.getString("users_label"));
		usersButton.setIcon(new ImageIcon(KianamaResourceBundle.getResource("gnome-session-48.png")));
		toolBar.add(usersButton);
		componentsButtonGroup.add(usersButton);
	}
	
	private ItemListener onUsersButtonClicked() {
		return new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					((CardLayout) sectionsPanel.getLayout()).show(sectionsPanel,
							"Users");
					usersMenuItem.setSelected(true);
				}
			}
		};
	}
	
	private void createReportingButton(){
		reportingButton = new JToggleButton("");
		reportingButton.addItemListener(onReportingButtonClicked());
		reportingButton.setToolTipText(KianamaResourceBundle.getString("reporting_label"));
		reportingButton.setIcon(new ImageIcon(KianamaResourceBundle.getResource("reporting-48.png")));
		toolBar.add(reportingButton);
		componentsButtonGroup.add(reportingButton);
	}
	
	private ItemListener onReportingButtonClicked() {
		return new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					((CardLayout) sectionsPanel.getLayout()).show(sectionsPanel,
							"Reporting");
					reportingMenuItem.setSelected(true);
				}
			}
		};
	}
	
	private void createSettingsButton() {
		settingsButton = new JToggleButton("");
		settingsButton.addItemListener(onSettingsButtonClicked());
		settingsButton.setToolTipText(KianamaResourceBundle.getString("settings_label"));
		settingsButton.setIcon(new ImageIcon(KianamaResourceBundle.getResource("Tools-48.png")));
		toolBar.add(settingsButton);
		componentsButtonGroup.add(settingsButton);
	}

	private ItemListener onSettingsButtonClicked() {
		return new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
				((CardLayout) sectionsPanel.getLayout()).show(sectionsPanel,
						"Settings");
				settingMenuItem.setSelected(true);
				}
			}
		};
	}
	
	private void createAboutButton() {
		aboutButton = new JToggleButton("");
		aboutButton.addItemListener(onAboutButtonClicked());
		aboutButton.setToolTipText(KianamaResourceBundle.getString("about_label"));
		aboutButton.setIcon(new ImageIcon(KianamaResourceBundle.getResource("Information-48.png")));
		toolBar.add(aboutButton);
		componentsButtonGroup.add(aboutButton);
	}

	private ItemListener onAboutButtonClicked() {
		return new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED){
				((CardLayout) sectionsPanel.getLayout()).show(sectionsPanel,
						"About");
					aboutMenuItem.setSelected(true);
				}
			}
		};
	}
	
	private void createExitButton() {
		dateLabel = new JLabel("");
		toolBar.add(dateLabel);
		
		
		Action updateDateTimeAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				PersianCalendar now;
				TimeZone timeZone = TimeZone.getTimeZone("Asia/Tehran");
				ULocale uLocale = ULocale.createCanonical("fa");
				now = new PersianCalendar(timeZone, uLocale);

				try {
					if (now.get(Calendar.MONTH) < 6)
						now.add(Calendar.HOUR, Integer.parseInt("-1"));
				} catch (Exception e1) {
				}

				// get date time in full format
				SimpleDateFormat sdf = (SimpleDateFormat) now.getDateTimeFormat(0,3, uLocale); /* new SimpleDateFormat("yyyy-MM-dd HH:mm"); */
				//SimpleDateFormat sdf = new SimpleDateFormat("E dd M yyyy",uLocale);
				dateLabel.setFont(new Font("tahoma",Font.BOLD,15));
				dateLabel.setText(sdf.format(now.getTime()));
		    }
		};
		
		//new Timer(1000, updateDateTimeAction).start();
	}

	public String zero(int num) {
		String number = (num < 10) ? ("0" + num) : ("" + num);
		return number; // Add leading zero if needed

	}	

	private void createComponentMenu() {
		componentsMenu = new JMenu(KianamaResourceBundle.getString("components_label"));
		menuBar.add(componentsMenu);
		componentGroup = new ButtonGroup();
		createComponentServerMenuItem();
		createComponentDatbaseMenuItem();
		createComponentClientMenuItem();
		createComponentUsersMenuItem();
		createComponentReportingMenuItem();
		createComponentSettingsMenuItem();
		createComponentAboutMenuItem();
		componentsMenu.addSeparator();
		createComponentExitMenuItem();
	}

	private void createComponentServerMenuItem() {
		serverMenuItem = new JRadioButtonMenuItem(
				KianamaResourceBundle.getString("server_label"));
		serverMenuItem
				.setIcon(new ImageIcon(KianamaResourceBundle.getResource("Gnome-Network-Server-32.png")));
		serverMenuItem.setSelected(true);
		componentGroup.add(serverMenuItem);
		componentsMenu.add(serverMenuItem);
		
		serverMenuItem.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED){
					((CardLayout) sectionsPanel.getLayout()).show(sectionsPanel,
							"Server");
						serverButton.setSelected(true);
					}
			}
		});
	}
	
	private void createComponentDatbaseMenuItem() {
		databaseMenuItem = new JRadioButtonMenuItem(KianamaResourceBundle.getString("database_label"));
		databaseMenuItem.setIcon(new ImageIcon(KianamaResourceBundle.getResource("1347695827_database.png")));
		componentGroup.add(databaseMenuItem);
		componentsMenu.add(databaseMenuItem);
		
		databaseMenuItem.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED){
					((CardLayout) sectionsPanel.getLayout()).show(sectionsPanel,"Database");
						databaseButton.setSelected(true);
					}
			}
		});
	}

	private void createComponentClientMenuItem() {
		clientMenuItem = new JRadioButtonMenuItem(KianamaResourceBundle.getString("clients_label"));
		clientMenuItem.setIcon(new ImageIcon(KianamaResourceBundle.getResource("Gnome-Video-Display-32.png")));
		componentGroup.add(clientMenuItem);
		componentsMenu.add(clientMenuItem);
		
		clientMenuItem.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED){
					((CardLayout) sectionsPanel.getLayout()).show(sectionsPanel,
							"Clients");
						clientsButton.setSelected(true);
					}
			}
		});
	}

	private void createComponentUsersMenuItem() {
		usersMenuItem = new JRadioButtonMenuItem(KianamaResourceBundle.getString("users_label"));
		usersMenuItem.setIcon(new ImageIcon(KianamaResourceBundle.getResource("gnome-session-32.png")));
		componentGroup.add(usersMenuItem);
		componentsMenu.add(usersMenuItem);
		
		usersMenuItem.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED){
					((CardLayout) sectionsPanel.getLayout()).show(sectionsPanel,
							"Users");
						usersButton.setSelected(true);
					}
			}
		});
	}
	
	private void createComponentReportingMenuItem() {
		reportingMenuItem = new JRadioButtonMenuItem(KianamaResourceBundle.getString("reporting_label"));
		reportingMenuItem.setIcon(new ImageIcon(KianamaResourceBundle.getResource("reporting-32.png")));
		componentGroup.add(reportingMenuItem);
		componentsMenu.add(reportingMenuItem);
		
		reportingMenuItem.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED){
					((CardLayout) sectionsPanel.getLayout()).show(sectionsPanel,
							"Reporting");
						reportingButton.setSelected(true);
					}
			}
		});
	}
	
	private void createComponentSettingsMenuItem() {
		settingMenuItem = new JRadioButtonMenuItem(KianamaResourceBundle.getString("settings_label"));
		settingMenuItem.setIcon(new ImageIcon(KianamaResourceBundle.getResource("Setting-Tools-32.png")));
		componentGroup.add(settingMenuItem);
		componentsMenu.add(settingMenuItem);
		
		settingMenuItem.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED){
					((CardLayout) sectionsPanel.getLayout()).show(sectionsPanel,"Settings");
						settingsButton.setSelected(true);
					}
			}
		});
	}

	private void createComponentExitMenuItem() {
		exitMenuItem = new JMenuItem(KianamaResourceBundle.getString("exit_label"));
		exitMenuItem.setIcon(new ImageIcon(KianamaResourceBundle.getResource("Logout-32.png")));
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					RemoteServerAccess.logout();
				}catch(Exception exp){exp.printStackTrace();}
				System.exit(0);
			}
		});
		componentsMenu.add(exitMenuItem);
	}

	private void createComponentAboutMenuItem() {
		aboutMenuItem = new JRadioButtonMenuItem(KianamaResourceBundle.getString("about_label"));
		aboutMenuItem.setIcon(new ImageIcon(KianamaResourceBundle.getResource("Information-32.png")));
		aboutMenuItem.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED){
					((CardLayout) sectionsPanel.getLayout()).show(sectionsPanel,
							"About");
						aboutButton.setSelected(true);
					}
			}
		});
		
		componentGroup.add(aboutMenuItem);
		componentsMenu.add(aboutMenuItem);
	}

	private void createLookAndFeelMenu() {
		lookAndFeelMenu = new JMenu(KianamaResourceBundle.getString("look_and_feel_label"));
		menuBar.add(lookAndFeelMenu);
		try {

			ButtonGroup grp = new ButtonGroup();
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(info.getName());
				grp.add(menuItem);
				menuItem.addActionListener(onLookAndFeelMenuItemClicked());
				lookAndFeelMenu.add(menuItem);
				if(UIManager.getLookAndFeel().getName().equals(info.getName())){
					menuItem.setSelected(true);
				}
			}
		} catch (Exception e) {
			// ignore it
			e.printStackTrace();
		}
	}

	private ActionListener onLookAndFeelMenuItemClicked() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if (((JMenuItem) e.getSource()).getText().equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							SwingUtilities.updateComponentTreeUI(frame);
							LoginDialog.prefs.put("look_and_feel", info.getClassName());
							// frame.pack();
							break;
						}
					}
				} catch (Exception exp) {
					exp.printStackTrace();
					try {
						UIManager.setLookAndFeel(UIManager
								.getSystemLookAndFeelClassName());
						SwingUtilities.updateComponentTreeUI(frame);
						// frame.pack();
					} catch (Exception e1) {
					}
				}
			}
		};
	}

	private void createLanguageMenu() {
		languageMenu = new JMenu(KianamaResourceBundle.getString("language_label"));
		menuBar.add(languageMenu);

		ButtonGroup btGrp = new ButtonGroup();
		farsiMenuItem = new JRadioButtonMenuItem(KianamaResourceBundle.getString("farsi_label")); 
		farsiMenuItem.addActionListener(onLanguageFarsiMenuItemClicked());
		if(Locale.getDefault().getDisplayLanguage().equals("Persian"))
			farsiMenuItem.setSelected(true);
		languageMenu.add(farsiMenuItem);
		btGrp.add(farsiMenuItem);

		englishMenuItem = new JRadioButtonMenuItem(KianamaResourceBundle.getString("english_label"));
		englishMenuItem.addActionListener(onLanguageEnglishMenuItemClicked());
		if(Locale.getDefault().getDisplayLanguage().equals("English"))
			englishMenuItem.setSelected(true);
		languageMenu.add(englishMenuItem);
		btGrp.add(englishMenuItem);
	}
	
	private void createSecurityMenu(){
		securityMenu = new JMenu(KianamaResourceBundle.getString("security_label"));
		menuBar.add(securityMenu);

		lockMenuItem = new JMenuItem(KianamaResourceBundle.getString("lock_label")); 
		lockMenuItem.setIcon(new ImageIcon(KianamaResourceBundle.getResource("1347446091_stock_lock.png")));
		lockMenuItem.addActionListener(onSecurityLockMenuItemClicked());
		securityMenu.add(lockMenuItem);
		
		changePassMenuItem = new JMenuItem(KianamaResourceBundle.getString("change_password_label"));
		changePassMenuItem.setIcon(new ImageIcon(KianamaResourceBundle.getResource("1347446138_Key.png")));
		changePassMenuItem.addActionListener(onSecurityChangePasswordMenuItemClicked());
		securityMenu.add(changePassMenuItem);
	}

	private ActionListener onSecurityLockMenuItemClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//setExtendedState(JFrame.ICONIFIED);
				try{
					RemoteServerAccess.lock();
					setVisible(false);
					JDialog dlg = new UnlockDialog(frame);
					dlg.setBounds(100, 100, 400, 159);
					dlg.setVisible(true);
				}catch(Exception exp){}
			}
		};
	}
	
	private ActionListener onSecurityChangePasswordMenuItemClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog changePassDialog = new ChangePasswordDialog();
				changePassDialog.setModal(true);
				changePassDialog.setBounds(100, 100, 493, 164);
				changePassDialog.setVisible(true);
			}
		};
	}
	
	private ActionListener onLanguageEnglishMenuItemClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginDialog.prefs.put("lang", "en");
				Locale.setDefault(new Locale("en"));
				//ResourceBundle.clearCache();
				//SwingUtilities.updateComponentTreeUI(frame); 
			}
		};
	}

	private ActionListener onLanguageFarsiMenuItemClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginDialog.prefs.put("lang", "fa");
				Locale.setDefault(new Locale("fa"));
			}
		};
	}

	private void createHelpMenu() {
		helpMenu = new JMenu(KianamaResourceBundle.getString("help_label"));
		helpMenu.setMnemonic('h');
		menuBar.add(helpMenu);

		aboutProgMenuItem = new JMenuItem(KianamaResourceBundle.getString("about_label"));
		aboutProgMenuItem.addActionListener(onHelpAboutMenuItemClicked());
		helpMenu.add(aboutProgMenuItem);
	}

	private ActionListener onHelpAboutMenuItemClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(
								null,
								KianamaResourceBundle.getString("about_text"),
								"About ...", JOptionPane.INFORMATION_MESSAGE);
			}
		};
	}
	
	public void translate(){
		//XXX translate some sub-components
	} 
	
	public void setComponentOrientation(ComponentOrientation o) {
		//XXX set some sub-components orientation
		menuBar.setComponentOrientation(o);
		toolBar.setComponentOrientation(o);
	}
}