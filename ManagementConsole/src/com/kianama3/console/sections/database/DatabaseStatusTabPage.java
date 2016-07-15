package com.kianama3.console.sections.database;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.kianama3.console.common.KianamaResourceBundle;
import com.kianama3.console.sections.database.status.DatabaseSessionsTableModel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;


public class DatabaseStatusTabPage extends JPanel {
	private JTable dbSessionsTable;
	private JPanel statusInfoPanel;
	private JLabel lblUptime;
	private JLabel lblUptimeValue;
	private JLabel lblBytesSent;
	private JLabel lblBytesSentValue;
	private JLabel lblBytesRecieved;
	private JLabel lblDbVersion;
	private JLabel lblDbVersionValue;
	private JLabel lblCreatedThreads;
	private JLabel lblCreatedThreadsValue;
	private JLabel lblRunningThreads;
	private JLabel lblRunningThreadsValue;
	private JLabel lblDbSesessions;
	private JLabel lblBytesRecievedValue;
	private JScrollPane scrollPane;
	private JTableHeader header;
	private DatabaseSessionsTableModel tableModel;

	
	protected static final int MINUTE_SECONDS = 60;
	protected static final int HOUR_SECONDS = 60 * 60;
	protected static final int DAY_SECONDS = 60 * 60 * 24;
	
	
	public DatabaseStatusTabPage() {
		setLayout(null);
		
		statusInfoPanel = new JPanel();
		//TODO uncomment this line later
		/*statusInfoPanel.setBorder(new TitledBorder(null, 
				KianamaResourceBundle.getString("database_status_info_label"), 
				TitledBorder.CENTER, TitledBorder.TOP, 
				null, null));*/
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 403, 832, 367);
		add(scrollPane);
		
		dbSessionsTable = new JTable();
		dbSessionsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		dbSessionsTable.setAutoCreateColumnsFromModel(false);
		tableModel = new DatabaseSessionsTableModel(dbSessionsTable);
		dbSessionsTable.setModel(tableModel);
		dbSessionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		header = dbSessionsTable.getTableHeader();
		header.addMouseListener(tableModel.new DatabaseSessionsColumnListener(dbSessionsTable)); 
		
		for (int k = 0; k < DatabaseSessionsTableModel.columns.length; k++) {
			TableCellRenderer renderer;  
				renderer = new DefaultTableCellRenderer();
				((DefaultTableCellRenderer) renderer).setHorizontalAlignment(DatabaseSessionsTableModel.columns[k].alignment);
			TableColumn column = new TableColumn(k,
					DatabaseSessionsTableModel.columns[k].width, renderer, null);
			dbSessionsTable.addColumn(column);

		}
		header.setUpdateTableInRealTime(false);
		scrollPane.setViewportView(dbSessionsTable);

		
		statusInfoPanel.setBounds(21, 22, 832, 345);
		add(statusInfoPanel);
		GridBagLayout gbl_statusInfoPanel = new GridBagLayout();
		gbl_statusInfoPanel.columnWidths = new int[]{171, 204, 0};
		gbl_statusInfoPanel.rowHeights = new int[]{28, 28, 25, 26, 27, 25, 0};
		gbl_statusInfoPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_statusInfoPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		statusInfoPanel.setLayout(gbl_statusInfoPanel);
		
		lblUptime = new JLabel(KianamaResourceBundle.getString("db_uptime_label"));
		GridBagConstraints gbc_lblUptime = new GridBagConstraints();
		gbc_lblUptime.anchor = GridBagConstraints.WEST;
		gbc_lblUptime.insets = new Insets(0, 5, 5, 5);
		gbc_lblUptime.gridx = 0;
		gbc_lblUptime.gridy = 0;
		statusInfoPanel.add(lblUptime, gbc_lblUptime);
		
		lblBytesSent = new JLabel(KianamaResourceBundle.getString("bytes_sent_label"));
		GridBagConstraints gbc_lblBytesSent = new GridBagConstraints();
		gbc_lblBytesSent.anchor = GridBagConstraints.WEST;
		gbc_lblBytesSent.insets = new Insets(0, 5, 5, 5);
		gbc_lblBytesSent.gridx = 0;
		gbc_lblBytesSent.gridy = 1;
		statusInfoPanel.add(lblBytesSent, gbc_lblBytesSent);
		
		lblBytesRecieved = new JLabel(KianamaResourceBundle.getString("bytes_recieved_label"));
		GridBagConstraints gbc_lblBytesRecieved = new GridBagConstraints();
		gbc_lblBytesRecieved.anchor = GridBagConstraints.WEST;
		gbc_lblBytesRecieved.insets = new Insets(0, 5, 5, 5);
		gbc_lblBytesRecieved.gridx = 0;
		gbc_lblBytesRecieved.gridy = 2;
		statusInfoPanel.add(lblBytesRecieved, gbc_lblBytesRecieved);
		
		lblUptimeValue = new JLabel("a");
		GridBagConstraints gbc_lblUptimeValue = new GridBagConstraints();
		gbc_lblUptimeValue.anchor = GridBagConstraints.WEST;
		gbc_lblUptimeValue.insets = new Insets(0, 5, 5, 5);
		gbc_lblUptimeValue.gridx = 1;
		gbc_lblUptimeValue.gridy = 0;
		statusInfoPanel.add(lblUptimeValue, gbc_lblUptimeValue);
		
		lblDbVersion = new JLabel(KianamaResourceBundle.getString("db_version_label"));
		GridBagConstraints gbc_lblDbVersion = new GridBagConstraints();
		gbc_lblDbVersion.anchor = GridBagConstraints.WEST;
		gbc_lblDbVersion.insets = new Insets(0, 5, 5, 5);
		gbc_lblDbVersion.gridx = 0;
		gbc_lblDbVersion.gridy = 3;
		statusInfoPanel.add(lblDbVersion, gbc_lblDbVersion);
		
		lblCreatedThreads = new JLabel(KianamaResourceBundle.getString("created_threads_label"));
		GridBagConstraints gbc_lblCreatedThreads = new GridBagConstraints();
		gbc_lblCreatedThreads.anchor = GridBagConstraints.WEST;
		gbc_lblCreatedThreads.insets = new Insets(0, 5, 5, 5);
		gbc_lblCreatedThreads.gridx = 0;
		gbc_lblCreatedThreads.gridy = 4;
		statusInfoPanel.add(lblCreatedThreads, gbc_lblCreatedThreads);
		
		lblBytesSentValue = new JLabel("a");
		GridBagConstraints gbc_lblBytesSentValue = new GridBagConstraints();
		gbc_lblBytesSentValue.anchor = GridBagConstraints.WEST;
		gbc_lblBytesSentValue.insets = new Insets(0, 5, 5, 5);
		gbc_lblBytesSentValue.gridx = 1;
		gbc_lblBytesSentValue.gridy = 1;
		statusInfoPanel.add(lblBytesSentValue, gbc_lblBytesSentValue);
		
		lblRunningThreads = new JLabel(KianamaResourceBundle.getString("running_threads_label"));
		GridBagConstraints gbc_lblRunningThreads = new GridBagConstraints();
		gbc_lblRunningThreads.anchor = GridBagConstraints.WEST;
		gbc_lblRunningThreads.insets = new Insets(0, 5, 0, 5);
		gbc_lblRunningThreads.gridx = 0;
		gbc_lblRunningThreads.gridy = 5;
		statusInfoPanel.add(lblRunningThreads, gbc_lblRunningThreads);
		
		lblBytesRecievedValue = new JLabel("a");
		GridBagConstraints gbc_lblBytesRecievedValue = new GridBagConstraints();
		gbc_lblBytesRecievedValue.anchor = GridBagConstraints.WEST;
		gbc_lblBytesRecievedValue.insets = new Insets(0, 5, 5, 5);
		gbc_lblBytesRecievedValue.gridx = 1;
		gbc_lblBytesRecievedValue.gridy = 2;
		statusInfoPanel.add(lblBytesRecievedValue, gbc_lblBytesRecievedValue);
		
		lblDbVersionValue = new JLabel("a");
		GridBagConstraints gbc_lblDbVersionValue = new GridBagConstraints();
		gbc_lblDbVersionValue.fill = GridBagConstraints.BOTH;
		gbc_lblDbVersionValue.insets = new Insets(0, 5, 5, 5);
		gbc_lblDbVersionValue.gridx = 1;
		gbc_lblDbVersionValue.gridy = 3;
		statusInfoPanel.add(lblDbVersionValue, gbc_lblDbVersionValue);
		
		lblCreatedThreadsValue = new JLabel("a");
		GridBagConstraints gbc_lblCreatedThreadsValue = new GridBagConstraints();
		gbc_lblCreatedThreadsValue.fill = GridBagConstraints.BOTH;
		gbc_lblCreatedThreadsValue.insets = new Insets(0, 5, 5, 5);
		gbc_lblCreatedThreadsValue.gridx = 1;
		gbc_lblCreatedThreadsValue.gridy = 4;
		statusInfoPanel.add(lblCreatedThreadsValue, gbc_lblCreatedThreadsValue);
		
		lblRunningThreadsValue = new JLabel("a");
		GridBagConstraints gbc_lblRunningThreadsValue = new GridBagConstraints();
		gbc_lblRunningThreadsValue.anchor = GridBagConstraints.WEST;
		gbc_lblRunningThreadsValue.insets = new Insets(0, 5, 0, 5);
		gbc_lblRunningThreadsValue.gridx = 1;
		gbc_lblRunningThreadsValue.gridy = 5;
		statusInfoPanel.add(lblRunningThreadsValue, gbc_lblRunningThreadsValue);
		
		lblDbSesessions = new JLabel(KianamaResourceBundle.getString("database_sessions_label"));
		lblDbSesessions.setBounds(21, 378, 180, 14);
		add(lblDbSesessions);		
		
	}
	
	private int getSeconds(long seconds){
		return (int) (seconds - MINUTE_SECONDS * getMinutes(seconds) - HOUR_SECONDS * getHours(seconds) - DAY_SECONDS * getDays(seconds));
	}
	
	private int getMinutes(long seconds){
		return (int) ((seconds - HOUR_SECONDS * getHours(seconds) - DAY_SECONDS * getDays(seconds))/MINUTE_SECONDS);
	}
	
	private int getHours(long seconds){
		return (int) ((seconds - DAY_SECONDS * getDays(seconds)) / HOUR_SECONDS );
	}
	
	private int getDays(long seconds){
		return (int) (seconds / DAY_SECONDS) ; 
	}
}
