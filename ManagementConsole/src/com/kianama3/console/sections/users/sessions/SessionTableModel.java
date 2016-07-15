package com.kianama3.console.sections.users.sessions;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.kianama3.console.common.KianamaResourceBundle;
import com.kianama3.console.common.TableHeaderData;

public class SessionTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	public static final TableHeaderData columns[] = {
			new TableHeaderData(KianamaResourceBundle.getString("session_id_label"), 80, JLabel.LEFT),
			new TableHeaderData(KianamaResourceBundle.getString("user_name_label"), 80, JLabel.LEFT),
			new TableHeaderData(KianamaResourceBundle.getString("computer_name_label"), 80, JLabel.LEFT),
			new TableHeaderData(KianamaResourceBundle.getString("ip_address_label"), 80, JLabel.LEFT),
			new TableHeaderData(KianamaResourceBundle.getString("mac_address_label"), 80, JLabel.LEFT),
			new TableHeaderData(KianamaResourceBundle.getString("executed_command_label"), 80, JLabel.LEFT),
			new TableHeaderData(KianamaResourceBundle.getString("login_date_label"), 80, JLabel.LEFT),
			new TableHeaderData(KianamaResourceBundle.getString("status_label"), 80, JLabel.LEFT) };
	public static final int COL_SESSIONID = 0;
	public static final int COL_USERNAME = 1;
	public static final int COL_COMPUTERNAME = 2;
	public static final int COL_IPADDRESS = 3;
	public static final int COL_MACADDRESS = 4;
	public static final int COL_COMMAND = 5;
	public static final int COL_LOGINDATE = 6;
	public static final int COL_STATUS = 7;

	protected int sortCol = 0;
	protected boolean isSortAsc = true;
	
	protected Vector<SessionTableRecordData> dataVector;

	public SessionTableModel () {
		dataVector = new Vector<SessionTableRecordData>();
		retrieveSessions();
	}

	public void retrieveSessions() {
	}

	public int getRowCount() {
		return dataVector == null ? 0 : dataVector.size();
	}

	public int getColumnCount() {
		return columns.length;
	}

	public String getColumnName(int column) {
		String str = columns[column].title;
		if (column == sortCol)
			str += isSortAsc ? " »" : " «";
		return str;
	}

	public boolean isCellEditable(int nRow, int nCol) {
			return false;
	}

	public Object getValueAt(int nRow, int nCol) {
		if (nRow < 0 || nRow >= getRowCount())
			return "";
		SessionTableRecordData row = dataVector.elementAt(nRow);
		switch (nCol) {
		case COL_SESSIONID:
			return row.sessionID;
		case COL_USERNAME:
			return row.userName;
		case COL_COMPUTERNAME: 
			return row.computerName;
		case COL_IPADDRESS:
			return row.ipAddress;
		case COL_MACADDRESS:
			return row.macAddress;
		case COL_COMMAND: 
			return row.command;
		case COL_LOGINDATE:
			return row.loginDate;
		case COL_STATUS: 
			return row.status;
		}
		return "";
	}

	public void setValueAt(Object value, int nRow, int nCol) {
	}

	public void insert() {
	}

	public boolean delete(int row) {
		if (row < 0 || row >= dataVector.size())
			return false;
		dataVector.remove(row);
		return true;
	}

	
	// Class is defined in-line
	public class SessionTableHeaderListener extends MouseAdapter {
		protected JTable parentTable;

		public SessionTableHeaderListener(JTable table) {
			parentTable = table;
		}

		public void mouseClicked(MouseEvent e) {
			TableColumnModel colModel = parentTable.getColumnModel();
			int columnModelIndex = colModel.getColumnIndexAtX(e.getX());
			int modelIndex = colModel.getColumn(columnModelIndex)
					.getModelIndex();
			if (modelIndex < 0)
				return;
			if (sortCol == modelIndex)
				isSortAsc = !isSortAsc;
			else
				sortCol = modelIndex;
			for (int i = 0; i < columns.length; i++) {
				TableColumn column = colModel.getColumn(i);
				column.setHeaderValue(getColumnName(column.getModelIndex()));
			}
			parentTable.getTableHeader().repaint();
			Collections.sort(dataVector, new SessionTableComparator(modelIndex,isSortAsc));
			parentTable.tableChanged(new TableModelEvent(SessionTableModel.this));
			parentTable.repaint();
		}
	}
}
