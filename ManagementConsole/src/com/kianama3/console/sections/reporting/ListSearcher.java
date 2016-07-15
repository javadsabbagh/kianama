package com.kianama3.console.sections.reporting;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JList;
import javax.swing.ListModel;

public class ListSearcher extends KeyAdapter {
	protected JList m_list;
	protected ListModel m_model;
	protected String m_key = "";
	protected long m_time = 0;
	public static int CHAR_DELTA = 1000;

	public ListSearcher(JList list) {
		m_list = list;
		m_model = m_list.getModel();
	}

	public void keyTyped(KeyEvent e) {
		char ch = e.getKeyChar();
		if (!Character.isLetterOrDigit(ch))
			return;
		if (m_time + CHAR_DELTA < System.currentTimeMillis())
			m_key = "";
		m_time = System.currentTimeMillis();
		m_key += Character.toLowerCase(ch);
		for (int k = 0; k < m_model.getSize(); k++) {
			String str = ((String) m_model.getElementAt(k)).toLowerCase();
			if (str.startsWith(m_key)) {
				m_list.setSelectedIndex(k);
				m_list.ensureIndexIsVisible(k);
				break;
			}
		}
	}
}
