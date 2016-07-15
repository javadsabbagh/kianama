package com.kianama3.console.sections.reporting;

import java.awt.Font;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.kianama3.console.common.KianamaResourceBundle;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ImageIcon;

public class DetailedClientsLogParamsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField toTextField;
	private JTextField fromTextField;
	private JLabel clientsLabel;
	private JLabel logTypeLabel;
	private JLabel toDateLabel;
	private JLabel fromDateLabel;
	private JButton fromButton;
	private JButton toButton;
	private JList clientsList;
	private JList clientsLogTypeList;
	private JButton btnSearch;
	private TabListCellRenderer listRenderer;

	@SuppressWarnings("serial")
	public DetailedClientsLogParamsPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 204, 0, 39, 0 };
		gridBagLayout.rowHeights = new int[] { 25, 23, 18, 27, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);
		clientsLabel = new JLabel(KianamaResourceBundle.getString(
				"clients_label"));
		GridBagConstraints gbc_clientsLabel = new GridBagConstraints();
		gbc_clientsLabel.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gbc_clientsLabel.insets = new Insets(5, 5, 5, 5);
		gbc_clientsLabel.gridx = 0;
		gbc_clientsLabel.gridy = 0;
		this.add(clientsLabel, gbc_clientsLabel);
		clientsList = new JList();
		clientsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				JList list = (JList) e.getSource();
				int[] inds = list.getSelectedIndices();
				Vector<String> v = new Vector<String>();
				for (int i = 0; i < inds.length; i++) {
					// separate client name from its ip address
					String str = (String) list.getModel().getElementAt(inds[i]);
					v.add(str);
				}
			}
		});
		clientsList.setCellRenderer(listRenderer);
		clientsList.addKeyListener(new ListSearcher(clientsList));
		// m_clientsList.repaint();
		GridBagConstraints gbc_clientsList = new GridBagConstraints();
		gbc_clientsList.gridwidth = 3;
		gbc_clientsList.fill = GridBagConstraints.BOTH;
		gbc_clientsList.insets = new Insets(0, 5, 5, 1);
		gbc_clientsList.gridheight = 4;
		gbc_clientsList.gridx = 0;
		gbc_clientsList.gridy = 1;
		this.add(clientsList, gbc_clientsList);
		
		logTypeLabel = new JLabel(KianamaResourceBundle.getString("log_type_label"));
		GridBagConstraints gbc_logTypeLabel = new GridBagConstraints();
		gbc_logTypeLabel.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gbc_logTypeLabel.insets = new Insets(10, 5, 5, 5);
		gbc_logTypeLabel.gridx = 0;
		gbc_logTypeLabel.gridy = 5;
		this.add(logTypeLabel, gbc_logTypeLabel);
				
		clientsLogTypeList = new JList();
		clientsLogTypeList.addListSelectionListener(onLogTypeListItemSelected());
		clientsLogTypeList.setModel(new AbstractListModel() {
			String[] values = new String[] {
					KianamaResourceBundle
							.getString("connection_status_label"),
					KianamaResourceBundle
							.getString("monitor_status_label"),
					KianamaResourceBundle
							.getString("update_status_label") };

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
						
		GridBagConstraints gbc_clientsLogTypeList = new GridBagConstraints();
		gbc_clientsLogTypeList.gridwidth = 3;
		gbc_clientsLogTypeList.fill = GridBagConstraints.BOTH;
		gbc_clientsLogTypeList.insets = new Insets(0, 5, 5, 0);
		gbc_clientsLogTypeList.gridheight = 3;
		gbc_clientsLogTypeList.gridx = 0;
		gbc_clientsLogTypeList.gridy = 6;
		this.add(clientsLogTypeList, gbc_clientsLogTypeList);
						
		fromDateLabel = new JLabel(
				KianamaResourceBundle.getString("from_date_label"));
		GridBagConstraints gbc_fromDateLabel = new GridBagConstraints();
		gbc_fromDateLabel.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gbc_fromDateLabel.insets = new Insets(10, 5, 5, 5);
		gbc_fromDateLabel.gridx = 0;
		gbc_fromDateLabel.gridy = 9;
		this.add(fromDateLabel, gbc_fromDateLabel);
								
		fromTextField = new JTextField();
										
		GridBagConstraints gbc_fromTextField = new GridBagConstraints();
		gbc_fromTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_fromTextField.insets = new Insets(0, 5, 5, 5);
		gbc_fromTextField.gridx = 0;
		gbc_fromTextField.gridy = 10;
		this.add(fromTextField, gbc_fromTextField);
		fromTextField.setColumns(10);
																
		fromButton = new JButton("");
		fromButton.setIcon(new ImageIcon(KianamaResourceBundle.getResource("1347532287_config-date.png")));
		fromButton.setFont(new Font("SansSerif", Font.BOLD, 14));
		fromButton.setVerticalAlignment(SwingConstants.BOTTOM);
		GridBagConstraints gbc_fromButton = new GridBagConstraints();
		gbc_fromButton.fill = GridBagConstraints.BOTH;
		gbc_fromButton.insets = new Insets(0, 0, 5, 5);
		gbc_fromButton.anchor = GridBagConstraints.WEST;
		gbc_fromButton.gridx = 1;
		gbc_fromButton.gridy = 10;
		this.add(fromButton, gbc_fromButton);
														
		toDateLabel = new JLabel(KianamaResourceBundle.getString(
				"to_date_label"));
		GridBagConstraints gbc_toDateLabel = new GridBagConstraints();
		gbc_toDateLabel.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gbc_toDateLabel.insets = new Insets(0, 5, 5, 5);
		gbc_toDateLabel.gridx = 0;
		gbc_toDateLabel.gridy = 11;
		this.add(toDateLabel, gbc_toDateLabel);
																
		toTextField = new JTextField();
																		
		GridBagConstraints gbc_toTextField = new GridBagConstraints();
		gbc_toTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_toTextField.insets = new Insets(0, 5, 5, 5);
		gbc_toTextField.gridx = 0;
		gbc_toTextField.gridy = 12;
		this.add(toTextField, gbc_toTextField);
		toTextField.setColumns(10);
																												
		toButton = new JButton("");
		toButton.setIcon(new ImageIcon(KianamaResourceBundle.getResource("1347532287_config-date.png")));
		toButton.setVerticalAlignment(SwingConstants.BOTTOM);
		toButton.setFont(new Font("SansSerif", Font.BOLD, 14));
		GridBagConstraints gbc_toButton = new GridBagConstraints();
		gbc_toButton.anchor = GridBagConstraints.WEST;
		gbc_toButton.fill = GridBagConstraints.BOTH;
		gbc_toButton.insets = new Insets(0, 0, 5, 5);
		gbc_toButton.gridx = 1;
		gbc_toButton.gridy = 12;
		this.add(toButton, gbc_toButton);
																										
		btnSearch = new JButton(KianamaResourceBundle.getString("run_report_label"));
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.insets = new Insets(0, 5, 5, 5);
		gbc_btnSearch.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gbc_btnSearch.gridx = 0;
		gbc_btnSearch.gridy = 13;
		add(btnSearch, gbc_btnSearch);

		listRenderer = new TabListCellRenderer();
		listRenderer.setTabs(new int[] { 90, 100 });

	}

	private ListSelectionListener onLogTypeListItemSelected() {
		return new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				JList list = (JList) e.getSource();
				int[] indx = list.getSelectedIndices();
				Vector<Integer> v = new Vector<Integer>();
				for (int i = 0; i < indx.length; i++) {
					String str = (String) list.getModel().getElementAt(
							indx[i]);
					if (str.equals(KianamaResourceBundle.getString("connection_status_label")))
						v.add(new Integer(1));
					if (str.equals(KianamaResourceBundle.getString("monitor_status_label")))
						v.add(new Integer(2));
					if (str.equals(KianamaResourceBundle.getString("update_status_label")))
						v.add(new Integer(3));
				}
			}
		};
	}

	public void translate() {

	}
}
