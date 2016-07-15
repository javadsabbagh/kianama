package com.kianama3.console.sections.users.management;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class CheckBoxCellRenderer extends JCheckBox implements TableCellRenderer {
	private static final long serialVersionUID = 467325749474689007L;
	protected static Border m_noFocusBorder;

	public CheckBoxCellRenderer() {
		super();
		m_noFocusBorder = new EmptyBorder(1, 2, 1, 2);
		setOpaque(true);
		setBorder(m_noFocusBorder);
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (value instanceof Boolean) {
			Boolean b = (Boolean) value;
			setSelected(b.booleanValue());
		}
		setBackground(isSelected && !hasFocus ? table.getSelectionBackground()
				: table.getBackground());
		setForeground(isSelected && !hasFocus ? table.getSelectionForeground()
				: table.getForeground());
		setFont(table.getFont());
		setBorder(hasFocus ? UIManager
				.getBorder("Table.focusCellHighlightBorder") : m_noFocusBorder);
		return this;
	}
}