package com.kianama3.console.sections.clients.status;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;

@SuppressWarnings("serial")
public class ViewButtonCellEditor extends AbstractCellEditor implements TableCellEditor {
	// This is the component that will handle the editing of the cell value
	JComponent component = new JButton("View");
	int row;
	protected ClientsStatusTableModel parentModel; 
	protected static Border m_noFocusBorder;

	public void setParent(AbstractTableModel parent) {
		if (parent instanceof ClientsStatusTableModel) {
			parentModel = (ClientsStatusTableModel) parent;
		}
	}

	public ViewButtonCellEditor() {
		super();
		m_noFocusBorder = new EmptyBorder(10, 5, 10, 5);
		component.setOpaque(true);
		component.setBorder(m_noFocusBorder);
		
		((JButton) component).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// JOptionPane.showMessageDialog(null, String.valueOf(row));
				if (parentModel != null) {
					JOptionPane.showMessageDialog(null,
							parentModel.dataVector.get(row).m_name);
				} else {
					JOptionPane.showMessageDialog(null,
							"Unable to retrieve data", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	@Override
	// This method is called when a cell value is edited by the user.
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int rowIndex, int vColIndex) {
		row = rowIndex;
		if (isSelected) {
			
		}
		component.setBackground(isSelected && !component.hasFocus() ? table
				.getSelectionBackground() : table.getBackground());
		component.setForeground(isSelected && !component.hasFocus() ? table
				.getSelectionForeground() : table.getForeground());
		component.setFont(table.getFont());
		component.setBorder(component.hasFocus() ? UIManager
				.getBorder("Table.focusCellHighlightBorder") : m_noFocusBorder);
		// Return the configured component
		return component;
	}
	
	@Override
	public Object getCellEditorValue() {
		return new String("Veiw");
	}
}