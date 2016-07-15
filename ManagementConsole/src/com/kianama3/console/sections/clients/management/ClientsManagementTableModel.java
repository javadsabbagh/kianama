package com.kianama3.console.sections.clients.management;

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
import com.kianama3.server.remote.common.ClientData;

public class ClientsManagementTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	public static final TableHeaderData m_columns[] = {
			new TableHeaderData(KianamaResourceBundle.getString("name_label"), 80, JLabel.LEFT),
			new TableHeaderData(KianamaResourceBundle.getString("description_label"), 300, JLabel.LEFT),
			new TableHeaderData(KianamaResourceBundle.getString("serial_label"), 130, JLabel.LEFT),
			new TableHeaderData(KianamaResourceBundle.getString("state_label"), 80, JLabel.LEFT),
			new TableHeaderData(KianamaResourceBundle.getString("disable_date_label"), 180, JLabel.LEFT)};
	public static final int COL_NAME = 0;
	public static final int COL_DESCRIPTION = 1;
	public static final int COL_SERIAL = 2;
	public static final int COL_STATE = 3;
	public static final int COL_DISABLE_DATE = 4;
	
	public static final int ENABLED = 1;  // equivalent to their values in database
	public static final int DISABLED = 2;
	
	protected int sortCol = 0;
	protected boolean isSortAsc = true;
	public JTable parentTabel;
	
	protected Vector<ClientData> dataVector;

	public ClientsManagementTableModel (JTable parentTable) {
		dataVector = new Vector<ClientData>();
		this.parentTabel = parentTable;
		retrieveClients();
	}
	
	public final ClientData getClientRecordAt(int row){
		return dataVector.elementAt(row);
	}
	
	public void retrieveClients() {
		try {
			dataVector.clear();
			dataVector = RemoteServerAccess.getClientsList();
            repaintTableCells();            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getRowCount() {
		return dataVector == null ? 0 : dataVector.size();
	}

	public int getColumnCount() {
		return m_columns.length;
	}

	public String getColumnName(int column) {
		String str = m_columns[column].title;
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
		ClientData row = dataVector.elementAt(nRow);
		switch (nCol) {
		case COL_NAME:
			return row.clientName;
		case COL_DESCRIPTION:
			return row.clientDesc;
		case COL_SERIAL:
			return row.clientSerial;
		case COL_STATE:
			switch(row.clientState){
				case ENABLED:
					return KianamaResourceBundle.getString("enabled_label");
				case DISABLED:
					return KianamaResourceBundle.getString("disabled_label");
			}
		case COL_DISABLE_DATE:
			return row.disableDateTime;
		}
		return "";
	}

	public void repaintTableCells(){
		Collections.sort(dataVector, 
				new ClientInfoComparator(sortCol,isSortAsc));
		parentTabel.tableChanged(new TableModelEvent(ClientsManagementTableModel.this));
		parentTabel.repaint();
	}
	
	// Class is defined in-line
	public class ClientsManagementColumnListener extends MouseAdapter {
		public ClientsManagementColumnListener() {
		}

		public void mouseClicked(MouseEvent e) {
			TableColumnModel colModel = parentTabel.getColumnModel();
			int columnModelIndex = colModel.getColumnIndexAtX(e.getX());
			int modelIndex = colModel.getColumn(columnModelIndex).getModelIndex();
			if (modelIndex < 0)
				return;
			//If user clicked on the same column reverse sorting direction
			if (sortCol == modelIndex)
				isSortAsc = !isSortAsc;
			//else sort other column by last used sorting direction
			else
				sortCol = modelIndex;
			for (int i = 0; i < m_columns.length; i++) {
				//XXX we do this because of >> and << to column names
				// This will be change to icons in future
				TableColumn column = colModel.getColumn(i);
				column.setHeaderValue(getColumnName(column.getModelIndex()));
			}
			parentTabel.getTableHeader().repaint();
			repaintTableCells();
		}
	}
}
