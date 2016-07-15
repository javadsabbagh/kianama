package com.kianama3.console.sections.about;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.BoxLayout;

import com.kianama3.console.common.KianamaResourceBundle;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class AboutSection extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the frame.
	 */
	public AboutSection() {
		setBounds(100, 100, 530, 475);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);
		
		JPanel m_aboutPanel = new JPanel();
		tabbedPane.addTab("About", null, m_aboutPanel, null);
		m_aboutPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Picture");
		lblNewLabel.setBounds(10, 29, 143, 127);
		m_aboutPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Copyright 2012");
		lblNewLabel_1.setBounds(163, 108, 168, 26);
		m_aboutPanel.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblVersion = new JLabel("Version 3.0.1");
		lblVersion.setBounds(163, 66, 188, 31);
		m_aboutPanel.add(lblVersion);
		lblVersion.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblKianamaManagementConsole = new JLabel("Kianama Management Console");
		lblKianamaManagementConsole.setBounds(167, 29, 307, 26);
		m_aboutPanel.add(lblKianamaManagementConsole);
		lblKianamaManagementConsole.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JPanel m_sslPanel = new JPanel();
		tabbedPane.addTab("SSL Certificate", new ImageIcon(KianamaResourceBundle.getResource("Gnome-Application-Certificate-32.png")), m_sslPanel, null);
		GridBagLayout gbl_m_sslPanel = new GridBagLayout();
		gbl_m_sslPanel.columnWidths = new int[]{200, 300, 0};
		gbl_m_sslPanel.rowHeights = new int[]{39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 0};
		gbl_m_sslPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_m_sslPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		m_sslPanel.setLayout(gbl_m_sslPanel);
		
		JLabel lblNewLabel_2 = new JLabel("Version");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 0;
		m_sslPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_4.gridx = 1;
		gbc_lblNewLabel_4.gridy = 0;
		m_sslPanel.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		JLabel lblSerialNumvber = new JLabel("Serial Numvber");
		GridBagConstraints gbc_lblSerialNumvber = new GridBagConstraints();
		gbc_lblSerialNumvber.fill = GridBagConstraints.BOTH;
		gbc_lblSerialNumvber.insets = new Insets(0, 0, 5, 5);
		gbc_lblSerialNumvber.gridx = 0;
		gbc_lblSerialNumvber.gridy = 1;
		m_sslPanel.add(lblSerialNumvber, gbc_lblSerialNumvber);
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_5.gridx = 1;
		gbc_lblNewLabel_5.gridy = 1;
		m_sslPanel.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		JLabel lblSignature = new JLabel("SignatureAlgorithm");
		GridBagConstraints gbc_lblSignature = new GridBagConstraints();
		gbc_lblSignature.fill = GridBagConstraints.BOTH;
		gbc_lblSignature.insets = new Insets(0, 0, 5, 5);
		gbc_lblSignature.gridx = 0;
		gbc_lblSignature.gridy = 2;
		m_sslPanel.add(lblSignature, gbc_lblSignature);
		
		JLabel lblNewLabel_6 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_6.gridx = 1;
		gbc_lblNewLabel_6.gridy = 2;
		m_sslPanel.add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		JLabel lblNewLabel_3 = new JLabel("Issuer");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 3;
		m_sslPanel.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		JLabel lblNewLabel_7 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_7.gridx = 1;
		gbc_lblNewLabel_7.gridy = 3;
		m_sslPanel.add(lblNewLabel_7, gbc_lblNewLabel_7);
		
		JLabel lblValidFrom = new JLabel("Valid From");
		GridBagConstraints gbc_lblValidFrom = new GridBagConstraints();
		gbc_lblValidFrom.fill = GridBagConstraints.BOTH;
		gbc_lblValidFrom.insets = new Insets(0, 0, 5, 5);
		gbc_lblValidFrom.gridx = 0;
		gbc_lblValidFrom.gridy = 4;
		m_sslPanel.add(lblValidFrom, gbc_lblValidFrom);
		
		JLabel lblNewLabel_8 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
		gbc_lblNewLabel_8.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_8.gridx = 1;
		gbc_lblNewLabel_8.gridy = 4;
		m_sslPanel.add(lblNewLabel_8, gbc_lblNewLabel_8);
		
		JLabel lblValidTo = new JLabel("Valid to");
		GridBagConstraints gbc_lblValidTo = new GridBagConstraints();
		gbc_lblValidTo.fill = GridBagConstraints.BOTH;
		gbc_lblValidTo.insets = new Insets(0, 0, 5, 5);
		gbc_lblValidTo.gridx = 0;
		gbc_lblValidTo.gridy = 5;
		m_sslPanel.add(lblValidTo, gbc_lblValidTo);
		
		JLabel lblNewLabel_9 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
		gbc_lblNewLabel_9.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_9.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_9.gridx = 1;
		gbc_lblNewLabel_9.gridy = 5;
		m_sslPanel.add(lblNewLabel_9, gbc_lblNewLabel_9);
		
		JLabel lblPublicKey = new JLabel("Public key");
		GridBagConstraints gbc_lblPublicKey = new GridBagConstraints();
		gbc_lblPublicKey.fill = GridBagConstraints.BOTH;
		gbc_lblPublicKey.insets = new Insets(0, 0, 5, 5);
		gbc_lblPublicKey.gridx = 0;
		gbc_lblPublicKey.gridy = 6;
		m_sslPanel.add(lblPublicKey, gbc_lblPublicKey);
		
		JLabel lblNewLabel_10 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_10 = new GridBagConstraints();
		gbc_lblNewLabel_10.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_10.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_10.gridx = 1;
		gbc_lblNewLabel_10.gridy = 6;
		m_sslPanel.add(lblNewLabel_10, gbc_lblNewLabel_10);
		
		JLabel lblEnhancedKeyUsage = new JLabel("Enhanced key usage");
		GridBagConstraints gbc_lblEnhancedKeyUsage = new GridBagConstraints();
		gbc_lblEnhancedKeyUsage.fill = GridBagConstraints.BOTH;
		gbc_lblEnhancedKeyUsage.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnhancedKeyUsage.gridx = 0;
		gbc_lblEnhancedKeyUsage.gridy = 7;
		m_sslPanel.add(lblEnhancedKeyUsage, gbc_lblEnhancedKeyUsage);
		
		JLabel lblNewLabel_11 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_11 = new GridBagConstraints();
		gbc_lblNewLabel_11.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_11.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_11.gridx = 1;
		gbc_lblNewLabel_11.gridy = 7;
		m_sslPanel.add(lblNewLabel_11, gbc_lblNewLabel_11);
		
		JLabel lblAlgorithmKeyIdentifier = new JLabel("Algorithm key Identifier");
		GridBagConstraints gbc_lblAlgorithmKeyIdentifier = new GridBagConstraints();
		gbc_lblAlgorithmKeyIdentifier.fill = GridBagConstraints.BOTH;
		gbc_lblAlgorithmKeyIdentifier.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlgorithmKeyIdentifier.gridx = 0;
		gbc_lblAlgorithmKeyIdentifier.gridy = 8;
		m_sslPanel.add(lblAlgorithmKeyIdentifier, gbc_lblAlgorithmKeyIdentifier);
		
		JLabel lblNewLabel_12 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_12 = new GridBagConstraints();
		gbc_lblNewLabel_12.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_12.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_12.gridx = 1;
		gbc_lblNewLabel_12.gridy = 8;
		m_sslPanel.add(lblNewLabel_12, gbc_lblNewLabel_12);
		
		JLabel lblThumbprintAlgorithm = new JLabel("Thumbprint algorithm");
		GridBagConstraints gbc_lblThumbprintAlgorithm = new GridBagConstraints();
		gbc_lblThumbprintAlgorithm.fill = GridBagConstraints.BOTH;
		gbc_lblThumbprintAlgorithm.insets = new Insets(0, 0, 5, 5);
		gbc_lblThumbprintAlgorithm.gridx = 0;
		gbc_lblThumbprintAlgorithm.gridy = 9;
		m_sslPanel.add(lblThumbprintAlgorithm, gbc_lblThumbprintAlgorithm);
		
		JLabel lblNewLabel_13 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_13 = new GridBagConstraints();
		gbc_lblNewLabel_13.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_13.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_13.gridx = 1;
		gbc_lblNewLabel_13.gridy = 9;
		m_sslPanel.add(lblNewLabel_13, gbc_lblNewLabel_13);
		
		JLabel lblThumbprint = new JLabel("Thumbprint");
		GridBagConstraints gbc_lblThumbprint = new GridBagConstraints();
		gbc_lblThumbprint.fill = GridBagConstraints.BOTH;
		gbc_lblThumbprint.insets = new Insets(0, 0, 0, 5);
		gbc_lblThumbprint.gridx = 0;
		gbc_lblThumbprint.gridy = 10;
		m_sslPanel.add(lblThumbprint, gbc_lblThumbprint);
		
		JLabel lblNewLabel_14 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_14 = new GridBagConstraints();
		gbc_lblNewLabel_14.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_14.gridx = 1;
		gbc_lblNewLabel_14.gridy = 10;
		m_sslPanel.add(lblNewLabel_14, gbc_lblNewLabel_14);
	}
}
