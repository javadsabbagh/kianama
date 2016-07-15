package com.kianama3.console.sections.clients.status;

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

@SuppressWarnings("serial")
public class ClientsStatusTableModel extends AbstractTableModel {
	static final public TableHeaderData columnsHeader[] = {
			new TableHeaderData(KianamaResourceBundle.getString("client_name_label"), 50, JLabel.LEFT),
			new TableHeaderData(KianamaResourceBundle.getString("current_ip_address_label"), 50, JLabel.LEFT),
			new TableHeaderData(KianamaResourceBundle.getString("connection_status_label"), 100, JLabel.LEFT),
			new TableHeaderData(KianamaResourceBundle.getString("monitor_status_label"), 100, JLabel.LEFT),
			new TableHeaderData(KianamaResourceBundle.getString("update_status_label"), 100, JLabel.LEFT),
			new TableHeaderData(KianamaResourceBundle.getString("view_label"), 50, JLabel.CENTER) };

	protected Vector<ClientStatusRecordData> dataVector;

	protected int sortCol = 0;
	protected boolean isSortAsc = true;
	protected int m_result = 0;
	protected JTable m_parent = null;

	public ClientsStatusTableModel () {
		dataVector = new Vector<ClientStatusRecordData>();
		setDefaultData();
	}

	public void setParent(JTable parent) {
		m_parent = parent;
	}

	public void setDefaultData() {
		dataVector.removeAllElements();
		Collections.sort(dataVector, new ClientsStatusComparator(sortCol,isSortAsc));
	}

	public int getRowCount() {
		return dataVector == null ? 0 : dataVector.size();
	}

	public int getColumnCount() {
		return columnsHeader.length;
	}

	public String getColumnName(int column) {
		String str = columnsHeader[column].title;
		if (column == sortCol)
			str += isSortAsc ? " »" : " «";
		return str;
	}

	public boolean isCellEditable(int nRow, int nCol) {
		if (nCol == columnsHeader.length - 1)
			return true;
		return false;
	}

	public Object getValueAt(int nRow, int nCol) {
		if (nRow < 0 || nRow >= getRowCount())
			return "";
		ClientStatusRecordData row = (ClientStatusRecordData) dataVector.elementAt(nRow);
		switch (nCol) {
		case 0:
			return row.m_name;
		case 1:
			return row.m_ip;
		case 2:
			return row.m_clientState;
		case 3:
			return row.m_monitorState;
		case 4:
			return row.m_updateState;
		case 5:
			return row.m_clientInfo;
		case 6:
			return row.m_monitorInfo;
		}
		return "";
	}

	public void retrieveData() {
	}

	// Class is defined in-line
	public class ClientsStatusColumnListener extends MouseAdapter {
		protected JTable parnetTable;

		public ClientsStatusColumnListener(JTable table) {
			parnetTable = table;
		}

		public void mouseClicked(MouseEvent e) {
			TableColumnModel colModel = parnetTable.getColumnModel();
			int columnModelIndex = colModel.getColumnIndexAtX(e.getX());
			int modelIndex = colModel.getColumn(columnModelIndex).getModelIndex();
			if (modelIndex < 0)
				return;
			if (sortCol == modelIndex)
				isSortAsc = !isSortAsc;
			else
				sortCol = modelIndex;
			for (int i = 0; i < columnsHeader.length; i++) {
				TableColumn column = colModel.getColumn(i);
				column.setHeaderValue(getColumnName(column.getModelIndex()));
			}
			parnetTable.getTableHeader().repaint();
			Collections.sort(dataVector, new ClientsStatusComparator(modelIndex,isSortAsc));
			parnetTable.tableChanged(new TableModelEvent(ClientsStatusTableModel.this));
			parnetTable.repaint();
		}
	}
}