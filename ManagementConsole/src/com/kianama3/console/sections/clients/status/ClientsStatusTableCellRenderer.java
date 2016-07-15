package com.kianama3.console.sections.clients.status;

import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class ClientsStatusTableCellRenderer extends DefaultTableCellRenderer {
	public void setValue(Object value) {
		if (value instanceof ClientStatusColumnData) {
			ClientStatusColumnData ivalue = (ClientStatusColumnData) value;
			if (ivalue.m_icon != null)
				setIcon(ivalue.m_icon);
			Integer i = (Integer) ivalue.m_type;
			Integer j = (Integer) ivalue.m_state;
			switch (i.intValue()) {
			case 1:
				if (j == 1) {
					setText("Connected");
					setToolTipText("<html>Connected From: <b>" + ivalue.m_date
							+ "<br>" + ivalue.m_desc + "</b></html>");
				} else if (j == 2) {
					setText("<html><font color='red'>Disconnected</html>");
					setToolTipText("<html><font color='red'>Disconnected From: </font><b>"
							+ ivalue.m_date
							+ "<br>"
							+ ivalue.m_desc
							+ "</b></html>");
				} else { // invalid state or has no data
					setText("");
					setToolTipText("<html><font color = 'red'>اطلاعات مورد نظر موجود نیست</font></html>");
				}
				break;
			case 2:
				if (j == 1) {
					setText("Monitor On");
					setToolTipText("<html>Monitor On From: <b>" + ivalue.m_date
							+ "<br>" + ivalue.m_desc + "</b></html>");
				} else if (j == 2) {
					setText("<html><font color='red'>Monitor Off</html>");
					setToolTipText("<html><font color='red'>Monitor Off From: </font><b>"
							+ ivalue.m_date
							+ "<br>"
							+ ivalue.m_desc
							+ "</b></html>");
				} else { // invalid state or has no data
					setText("");
					setToolTipText("<html><font color = 'red'>اطلاعات مورد نظر موجود نیست</font></html>");
				}
				break;
			case 3:
				if (j == 1) {
					setText("Updated");
					setToolTipText("<html>Updated From: <b>" + ivalue.m_date
							+ "<br>" + ivalue.m_desc + "</b></html>");
				} else if (j == 2) {
					setText("<html><font color='red'>Out of Update</html>");
					setToolTipText("<html><font color='red'>Out Of Update From: </font><b>"
							+ ivalue.m_date
							+ "<br>"
							+ ivalue.m_desc
							+ "</b></html>");
				} else { // invalid state or has no data
					setText("");
					setToolTipText("<html><font color = 'red'>اطلاعات مورد نظر موجود نیست</font></html>");
				}
				break;
			}
		} else {
			// never reaches this part
			super.setValue(value);
		}
	}
}