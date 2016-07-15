package com.kianama3.console.sections.users.management;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class UserManagementTableCellRenderer extends DefaultTableCellRenderer {
	protected UserManagementTableModel tableModel;
	public void setTableModel(UserManagementTableModel tableModel){
		tableModel = tableModel;
	}
	
	public UserManagementTableCellRenderer() {
		setOpaque(true);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		return this;
	}
}