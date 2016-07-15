package com.kianama3.console.sections.clients.status;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class ViewButtonCellRenderer extends JButton implements TableCellRenderer {
	// This method is called each time a cell in a column
	// using this renderer needs to be rendered.

	protected static Border m_noFocusBorder;
	public ViewButtonCellRenderer() {
		super();
		m_noFocusBorder = new EmptyBorder(10, 5, 10, 5);
		setOpaque(true);
		setBorder(m_noFocusBorder);
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
		// 'value' is value contained in the cell located at
		// (rowIndex, vColIndex)

		if (isSelected) {
			
		}
		if (hasFocus) {
			
		}

		setText("View");
		setToolTipText("View Client's Monitor");
		setBackground(isSelected && !hasFocus ? table.getSelectionBackground()
				: table.getBackground());
		setForeground(isSelected && !hasFocus ? table.getSelectionForeground()
				: table.getForeground());
		setFont(table.getFont());
		setBorder(hasFocus ? UIManager
				.getBorder("Table.focusCellHighlightBorder") : m_noFocusBorder);
		return this;
	}

	// The following methods override the defaults for performance reasons
	@Override
	public void validate() {
	}
	@Override
	public void revalidate() {
	}
	@Override
	protected void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
	}
	@Override
	public void firePropertyChange(String propertyName, boolean oldValue,
			boolean newValue) {
	}
}