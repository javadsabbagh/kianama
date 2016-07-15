package com.kianama3.console.sections.settings;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.ComponentOrientation;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class SettingsSectoin extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField moviesPathField;

	/**
	 * Create the frame.
	 */
	public SettingsSectoin() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBounds(100, 100, 715, 738);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 48, 687, 631);
		add(tabbedPane);
		
		JPanel m_generalPanel = new JPanel();
		tabbedPane.addTab("General", null, m_generalPanel, null);
		m_generalPanel.setLayout(null);
		
		JLabel lblMoviesPiath = new JLabel("Movies Path");
		lblMoviesPiath.setBounds(10, 11, 99, 14);
		m_generalPanel.add(lblMoviesPiath);
		
		moviesPathField = new JTextField();
		moviesPathField.setBounds(119, 8, 395, 20);
		m_generalPanel.add(moviesPathField);
		moviesPathField.setColumns(10);
		
		JButton button = new JButton("...");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//select a folder
				JFileChooser chooser = new JFileChooser();
				//TODO Previous directory
				//chooser.setCurrentDirectory(new File("."));
			    chooser.setDialogTitle("Select Movies' Path");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);
			    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			    	moviesPathField.setText(chooser.getSelectedFile().toString());
			    }
			}
		});
		button.setBounds(523, 7, 64, 23);
		m_generalPanel.add(button);
	}
}
