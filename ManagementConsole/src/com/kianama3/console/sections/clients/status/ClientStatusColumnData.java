package com.kianama3.console.sections.clients.status;

import javax.swing.ImageIcon;

public class ClientStatusColumnData {
	public Integer m_type; // Client State - Monitor State - Update State
	public ImageIcon m_icon;
	public Integer m_state;
	public String m_desc;
	public String m_date; // last state date

	public ClientStatusColumnData(ImageIcon icon, Integer type, Integer state,
			String desc, String date) {
		m_icon = icon;
		m_type = type;
		m_state = state;
		m_desc = desc;
		m_date = date;
	}

	public String toString() {
		return m_state.toString();
	}
}
