package com.kianama3.console.sections.clients.management;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.kianama3.server.remote.common.ClientData;

@SuppressWarnings("serial")
public class ClientsManagementTableCellRenderer extends DefaultTableCellRenderer {
	protected ClientsManagementTableModel m_tableModel;
	
	public ClientsManagementTableCellRenderer(ClientsManagementTableModel tableModel) {
		m_tableModel = tableModel;
		setOpaque(true);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		ClientData rec = m_tableModel.dataVector.get(row);
		if(rec.clientState == 2)
			this.setBackground(Color.pink);
		setText(value != null ? value.toString() : "");
		return this;
	}
}