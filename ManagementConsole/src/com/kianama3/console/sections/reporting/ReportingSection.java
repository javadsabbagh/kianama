package com.kianama3.console.sections.reporting;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import com.kianama3.console.common.KianamaResourceBundle;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;

public class ReportingSection extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JLabel lblSelectReport;
	private JComboBox comboBox;
	private JPanel paramsPanel;
	private JPanel rpt101Panel;
	private JPanel rpt102Panel;
	private JPanel rpt103Panel;
	private JPanel rpt104Panel;
	private JPanel rpt105Panel;
	
	public ReportingSection() {
		setLayout(null);
		
		lblSelectReport = new JLabel(KianamaResourceBundle.getString("select_report_label"));
		lblSelectReport.setBounds(10, 11, 72, 24);
		add(lblSelectReport);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Order in COmboBOx could be changed
				if(comboBox.getSelectedItem().toString().equals(KianamaResourceBundle.getString("RPT-101"))){
					((CardLayout) paramsPanel.getLayout()).show(paramsPanel,"RPT-101");
				}else if (comboBox.getSelectedItem().toString().equals(KianamaResourceBundle.getString("RPT-102"))){
					((CardLayout) paramsPanel.getLayout()).show(paramsPanel,"RPT-102");
				}else if (comboBox.getSelectedItem().toString().equals(KianamaResourceBundle.getString("RPT-103"))){
					((CardLayout) paramsPanel.getLayout()).show(paramsPanel,"RPT-103");
				}else if(comboBox.getSelectedItem().toString().equals(KianamaResourceBundle.getString("RPT-104"))){
					((CardLayout) paramsPanel.getLayout()).show(paramsPanel,"RPT-104");
				}else if(comboBox.getSelectedItem().toString().equals(KianamaResourceBundle.getString("RPT-105"))){
					((CardLayout) paramsPanel.getLayout()).show(paramsPanel,"RPT-105");
				}
			}
		});

		comboBox.setModel(new DefaultComboBoxModel(new String[] {KianamaResourceBundle.getString("RPT-101"),
																 KianamaResourceBundle.getString("RPT-102"), 
																 KianamaResourceBundle.getString("RPT-103"),
																 KianamaResourceBundle.getString("RPT-104"),
																 KianamaResourceBundle.getString("RPT-105")}));
		comboBox.setBounds(92, 11, 223, 24);
		add(comboBox);
		
		paramsPanel = new JPanel();
		paramsPanel.setBounds(10, 46, 303, 430);
		add(paramsPanel);
		paramsPanel.setLayout(new CardLayout(0, 0));
		
		rpt101Panel = new DetailedClientsLogParamsPanel();
		paramsPanel.add(rpt101Panel, "RPT-101");
		
		rpt102Panel = new DiagramBasedClientsLogParamsPanel();
		paramsPanel.add(rpt102Panel, "RPT-102");
		
		rpt103Panel = new JPanel();
		paramsPanel.add(rpt103Panel, "RPT-103");
		
		rpt104Panel = new JPanel();
		paramsPanel.add(rpt104Panel, "RPT-104");
		
		rpt105Panel = new JPanel();
		paramsPanel.add(rpt105Panel, "RPT-105");
		
		JPanel panel = new JPanel();
		panel.setBounds(325, 11, 455, 465);
		add(panel);
	}
}
