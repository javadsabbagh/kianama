package com.kianama3.console.sections.users.management;

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

import com.kianama3.console.sections.users.UserManagementTabPage;
import com.kianama3.server.remote.common.UserData;

public class UserManagementTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	public static final TableHeaderData columns[] = {
			new TableHeaderData(KianamaResourceBundle.getString("username_label"), 100, JLabel.LEFT),
			new TableHeaderData(KianamaResourceBundle.getString("fullname_label"), 200, JLabel.LEFT),
			new TableHeaderData(KianamaResourceBundle.getString("status_label"), 70, JLabel.LEFT) ,
			new TableHeaderData(KianamaResourceBundle.getString("disable_date_label"), 120, JLabel.LEFT) ,
			new TableHeaderData(KianamaResourceBundle.getString("change_pass_next_login_label"), 120, JLabel.LEFT) ,
			new TableHeaderData(KianamaResourceBundle.getString("expire_date_label"), 130, JLabel.LEFT) };
	public static final int COL_USERNAME = 0;
	public static final int COL_FULLNAME = 1;
	public static final int COL_STATUS = 2;
	public static final int COL_DISABLE_DATE_TIME = 3;
	public static final int COL_CHANGE_PASS = 4;
	public static final int COL_EXPIRE_DATE_TIME = 5;

	protected int sortCol = 0;
	protected boolean isSortAsc = true;
	
	protected UserManagementTabPage parentTabPage;
	protected Vector<UserData> dataVector;
	public JTable parentTabel;

	public UserManagementTableModel (JTable parentTable) {
		dataVector = new Vector<UserData>();
		this.parentTabel = parentTable;
		retrieveUsers();
	}

	public final UserData getUserInfoAt(int row){
		//System.out.println("getUserInfoAt called");
		return dataVector.elementAt(row);
	}
	
	public void retrieveUsers() {
		try {
			dataVector.clear();
			dataVector = RemoteServerAccess.getUsersList();
            repaintTableCells();            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void repaintTableCells(){
		Collections.sort(dataVector, new UserInfoComparator(sortCol,isSortAsc));
		parentTabel.tableChanged(new TableModelEvent(UserManagementTableModel.this));
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
		UserData u = dataVector.elementAt(nRow);
		switch (nCol) {
		case COL_USERNAME:
			return u.userName;
		case COL_FULLNAME:
			return u.fullName;
		case COL_STATUS:
			switch(u.userState){
			case 1:
				return KianamaResourceBundle.getString("enabled_label");
			case 2:
				return KianamaResourceBundle.getString("disabled_label");
			}
		case COL_EXPIRE_DATE_TIME:
			return u.expireDateTime;
		case COL_CHANGE_PASS:
			return new Boolean(u.changePassNextLogin);
		case COL_DISABLE_DATE_TIME:
			return u.disableDateTime;
		}
		return "";
	}

	// Class is defined in-line
	public class ClientsManagementColumnListener extends MouseAdapter {
		protected JTable table;

		public ClientsManagementColumnListener(JTable table) {
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
