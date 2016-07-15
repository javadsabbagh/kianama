package com.kianama3.console.sections.database.status;

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
import com.kianama3.console.common.RemoteServerAccess;
import com.kianama3.console.common.TableHeaderData;

import com.kianama3.server.remote.common.DatabaseSessionData;

public class DatabaseSessionsTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	
	public static final TableHeaderData columns[] = {
			new TableHeaderData(KianamaResourceBundle.getString("session_id_label"), 100, JLabel.LEFT),
			new TableHeaderData(KianamaResourceBundle.getString("user_name_label"), 200, JLabel.LEFT),
			new TableHeaderData(KianamaResourceBundle.getString("host_name_label"), 70, JLabel.LEFT) ,
			new TableHeaderData(KianamaResourceBundle.getString("db_name_label"), 100, JLabel.LEFT),
			new TableHeaderData(KianamaResourceBundle.getString("command_label"), 120, JLabel.LEFT) ,
			new TableHeaderData(KianamaResourceBundle.getString("time_label"), 120, JLabel.LEFT) ,
			new TableHeaderData(KianamaResourceBundle.getString("session_state_label"), 130, JLabel.LEFT),
			new TableHeaderData(KianamaResourceBundle.getString("session_info_label"), 100, JLabel.LEFT)};
	
	public static final int COL_SESSION_ID = 0;
	public static final int COL_USER_NAME = 1;
	public static final int COL_HOST_NAME = 2;
	public static final int COL_DB_NAME = 3;
	public static final int COL_COMMAND = 4;
	public static final int COL_TIME = 5;
	public static final int COL_SESSION_STATE = 6;
	public static final int COL_SESSION_INFO = 7;

	protected int sortCol = 0;
	protected boolean isSortAsc = true;
	
	protected Vector<DatabaseSessionData> dataVector;
	public JTable parentTabel;

	public DatabaseSessionsTableModel (JTable parentTable) {
		dataVector = new Vector<DatabaseSessionData>();
		this.parentTabel = parentTable;
		retrieveSessions();
	}

	public final DatabaseSessionData getSessionInfoAt(int row){
		return dataVector.elementAt(row);
	}
	
	public void retrieveSessions() {
		try {
			dataVector.clear();
			dataVector = RemoteServerAccess.getDatabaseSessionsList();
            repaintTableCells();            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void repaintTableCells(){
		Collections.sort(dataVector, new DatabaseSessionsComparator(sortCol,isSortAsc));
		parentTabel.tableChanged(new TableModelEvent(DatabaseSessionsTableModel.this));
		parentTabel.repaint();
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
		DatabaseSessionData s = dataVector.elementAt(nRow);
		switch (nCol) {
			case COL_SESSION_ID:
				return new Integer(s.sessionID);
			case COL_USER_NAME:
				return s.userName;
			case COL_HOST_NAME:
				return s.hostName;
			case COL_DB_NAME:
				return s.dbName;
			case COL_COMMAND:
				return s.command;
			case COL_TIME:
				return new Integer(s.time);
			case COL_SESSION_STATE:
				return s.sessionState;
			case COL_SESSION_INFO:
				return s.sessionInfo;
		}
		return "";
	}

	// Class is defined in-line
	public class DatabaseSessionsColumnListener extends MouseAdapter {
		protected JTable table;

		public DatabaseSessionsColumnListener(JTable table) {
			this.table = table;
		}

		public void mouseClicked(MouseEvent e) {
			TableColumnModel colModel = table.getColumnModel();
			int columnModelIndex = colModel.getColumnIndexAtX(e.getX());
			int modelIndex = colModel.getColumn(columnModelIndex).getModelIndex();
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
			parentTabel.getTableHeader().repaint();
			repaintTableCells();
		}
	}
}
