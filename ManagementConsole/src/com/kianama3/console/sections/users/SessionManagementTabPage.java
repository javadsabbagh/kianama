package com.kianama3.console.sections.users;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import com.kianama3.console.common.KianamaResourceBundle;
import com.kianama3.console.sections.users.sessions.SessionTableModel;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;


public class SessionManagementTabPage extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTable sessionsTable;
	protected SessionTableModel sessionsTableModel;
	private JLabel lblClientsStatus;
	private JScrollPane scrollPane_2;
	private JButton killSessionButton;

	public SessionManagementTabPage() {
		createSessionsTabel();
		createOtherControls();
	}

	private void createOtherControls() {
	}

	private void createSessionsTabel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{112, 994, 0};
		gridBagLayout.rowHeights = new int[]{23, 427, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
				lblClientsStatus = new JLabel(KianamaResourceBundle.getString("current_sessions_label"));
				GridBagConstraints gbc_lblClientsStatus = new GridBagConstraints();
				gbc_lblClientsStatus.fill = GridBagConstraints.HORIZONTAL;
				gbc_lblClientsStatus.insets = new Insets(6, 5, 5, 5);
				gbc_lblClientsStatus.gridx = 0;
				gbc_lblClientsStatus.gridy = 0;
				this.add(lblClientsStatus, gbc_lblClientsStatus);
		
		killSessionButton = new JButton(KianamaResourceBundle.getString("kill_session_label"));
		killSessionButton.setToolTipText(KianamaResourceBundle.getString("kill_session_tip_text"));
		killSessionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO kill session
			}
		});
		GridBagConstraints gbc_killSessionButton = new GridBagConstraints();
		gbc_killSessionButton.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gbc_killSessionButton.insets = new Insets(6, 0, 5, 0);
		gbc_killSessionButton.gridx = 1;
		gbc_killSessionButton.gridy = 0;
		add(killSessionButton, gbc_killSessionButton);

		sessionsTableModel = new SessionTableModel();
		sessionsTable = new JTable();
		// TODO set Table's row height
		sessionsTable.setRowHeight(35);
		sessionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.insets = new Insets(3, 5, 10, 10);
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridwidth = 2;
		gbc_scrollPane_2.gridx = 0;
		gbc_scrollPane_2.gridy = 1;
		this.add(scrollPane_2, gbc_scrollPane_2);
		scrollPane_2.setViewportView(sessionsTable);
		sessionsTable.setAutoCreateColumnsFromModel(false);
		sessionsTable.setModel(sessionsTableModel);
		
				JTableHeader hd = sessionsTable.getTableHeader();
				hd.addMouseListener(sessionsTableModel.new SessionTableHeaderListener(
						sessionsTable)); // because it is defined in-line you have to new
										// it in this way

		for (int k = 0; k < SessionTableModel.columns.length; k++) {
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setHorizontalAlignment(SessionTableModel.columns[k].alignment);
			TableColumn column = new TableColumn(k,
					SessionTableModel.columns[k].width, renderer,
					null);
			sessionsTable.addColumn(column);
		}
		hd.setUpdateTableInRealTime(true);
		hd.setReorderingAllowed(true);
		sessionsTableModel.retrieveSessions();
	}
}
