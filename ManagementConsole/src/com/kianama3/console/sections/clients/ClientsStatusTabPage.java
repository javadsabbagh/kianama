package com.kianama3.console.sections.clients;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.kianama3.console.common.KianamaResourceBundle;
import com.kianama3.console.sections.clients.status.ClientsStatusTableCellRenderer;
import com.kianama3.console.sections.clients.status.ClientsStatusTableModel;
import com.kianama3.console.sections.clients.status.ViewButtonCellEditor;
import com.kianama3.console.sections.clients.status.ViewButtonCellRenderer;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class ClientsStatusTabPage extends JPanel {
	private static final long serialVersionUID = 1L;
	public JTextArea displayInfoTextArea;
	public JTextArea descriptionTextArea;
	private JTable clientsStatusTable;
	protected ClientsStatusTableModel clientsStatusTableModel;
	private JLabel lblClientsStatus;
	private JScrollPane scrollPane_2;
	private JPanel panel_1;
	private JLabel lblDescription;
	private JLabel lblMonitorInformation;

	public ClientsStatusTabPage() {
		createStatusTabel();
		createOtherControls();
	}

	private void createOtherControls() {
		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 1;
		this.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{318, 0};
		gbl_panel_1.rowHeights = new int[]{14, 131, 14, 131, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
						
		lblDescription = new JLabel(
				KianamaResourceBundle.getString("description_label"));
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblDescription.insets = new Insets(0, 0, 5, 0);
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 0;
		panel_1.add(lblDescription, gbc_lblDescription);
				
		descriptionTextArea = new JTextArea();
		GridBagConstraints gbc_descriptionTextArea = new GridBagConstraints();
		gbc_descriptionTextArea.fill = GridBagConstraints.BOTH;
		gbc_descriptionTextArea.insets = new Insets(0, 0, 5, 0);
		gbc_descriptionTextArea.gridx = 0;
		gbc_descriptionTextArea.gridy = 1;
		panel_1.add(descriptionTextArea, gbc_descriptionTextArea);
		descriptionTextArea.setEditable(false);
				
		lblMonitorInformation = new JLabel(
				KianamaResourceBundle.getString("display_info_label"));
		GridBagConstraints gbc_lblMonitorInformation = new GridBagConstraints();
		gbc_lblMonitorInformation.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblMonitorInformation.insets = new Insets(0, 0, 5, 0);
		gbc_lblMonitorInformation.gridx = 0;
		gbc_lblMonitorInformation.gridy = 2;
		panel_1.add(lblMonitorInformation, gbc_lblMonitorInformation);
		
		displayInfoTextArea = new JTextArea();
		GridBagConstraints gbc_displayInfoTextArea = new GridBagConstraints();
		gbc_displayInfoTextArea.fill = GridBagConstraints.BOTH;
		gbc_displayInfoTextArea.gridx = 0;
		gbc_displayInfoTextArea.gridy = 3;
		panel_1.add(displayInfoTextArea, gbc_displayInfoTextArea);
		displayInfoTextArea.setEditable(false);
	}

	private void createStatusTabel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 701, 338, 0 };
		gridBagLayout.rowHeights = new int[] { 14, 441, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		lblClientsStatus = new JLabel(
				KianamaResourceBundle.getString("clients_status_label"));
		GridBagConstraints gbc_lblClientsStatus = new GridBagConstraints();
		gbc_lblClientsStatus.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblClientsStatus.insets = new Insets(5, 5, 5, 5);
		gbc_lblClientsStatus.gridx = 0;
		gbc_lblClientsStatus.gridy = 0;
		this.add(lblClientsStatus, gbc_lblClientsStatus);
		clientsStatusTable = new JTable();
		// TODO set Table's row height
		clientsStatusTable.setRowHeight(35);
		// Add selection Listener
		clientsStatusTable.getSelectionModel().addListSelectionListener(onStatusTableSelected());

		clientsStatusTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.insets = new Insets(0, 5, 5, 5);
		gbc_scrollPane_2.gridx = 0;
		gbc_scrollPane_2.gridy = 1;
		this.add(scrollPane_2, gbc_scrollPane_2);
		scrollPane_2.setViewportView(clientsStatusTable);
		clientsStatusTable.setAutoCreateColumnsFromModel(false);
		clientsStatusTableModel = new ClientsStatusTableModel();
		clientsStatusTable.setModel(clientsStatusTableModel);
		clientsStatusTableModel.setParent(clientsStatusTable);

		JTableHeader hd = clientsStatusTable.getTableHeader();
		hd.addMouseListener(clientsStatusTableModel.new ClientsStatusColumnListener(
				clientsStatusTable)); // because it is defined in-line you have
		
		// to new
		// it in this way

		for (int k = 0; k < ClientsStatusTableModel.columnsHeader.length; k++) {
			if (k != ClientsStatusTableModel.columnsHeader.length - 1) {
				DefaultTableCellRenderer renderer = new ClientsStatusTableCellRenderer();
				renderer.setHorizontalAlignment(ClientsStatusTableModel.columnsHeader[k].alignment);
				TableColumn column = new TableColumn(k,
						ClientsStatusTableModel.columnsHeader[k].width,
						renderer, null);
				clientsStatusTable.addColumn(column);
			} else {
				TableCellRenderer renderer = new ViewButtonCellRenderer();
				TableCellEditor editor = new ViewButtonCellEditor();
				((ViewButtonCellEditor) editor)
						.setParent(clientsStatusTableModel);
				TableColumn column = new TableColumn(k,
						ClientsStatusTableModel.columnsHeader[k].width,
						renderer, editor);
				clientsStatusTable.addColumn(column);
			}
		}
		hd.setUpdateTableInRealTime(true);
		hd.setReorderingAllowed(true);
		clientsStatusTableModel.retrieveData();
	}

	private ListSelectionListener onStatusTableSelected() {
		return new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getSource() == clientsStatusTable.getSelectionModel()
						&& clientsStatusTable.getRowSelectionAllowed()) {
					descriptionTextArea.setText((String) clientsStatusTable.getModel()
							.getValueAt(clientsStatusTable.getSelectedRow(), 5));
					displayInfoTextArea.setText((String) clientsStatusTable.getModel()
							.getValueAt(clientsStatusTable.getSelectedRow(), 6));
				} else if (e.getSource() == clientsStatusTable.getColumnModel()
						.getSelectionModel() && clientsStatusTable.getColumnSelectionAllowed()) {
				}
				if (e.getValueIsAdjusting()) {
				}
			}
		};
	}
}
