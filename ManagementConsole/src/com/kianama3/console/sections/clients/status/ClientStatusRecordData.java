package com.kianama3.console.sections.clients.status;

import javax.swing.ImageIcon;

class ClientStatusRecordData {
	public static ImageIcon ICON_CONNECTED = 
		new ImageIcon(ClientStatusRecordData.class.getResource("/com/kianama3/server/gui/resources/Connect-32 .png"));
	public static ImageIcon ICON_DISCONNECTED = 
		new ImageIcon(ClientStatusRecordData.class.getResource("/com/kianama3/server/gui/resources/Disconnect-32.png"));
	public static ImageIcon ICON_MONITOR_ON = 
		new ImageIcon(ClientStatusRecordData.class.getResource("/com/kianama3/server/gui/resources/Monitor-32.png"));
	public static ImageIcon ICON_MONITOR_OFF = 
		new ImageIcon(ClientStatusRecordData.class.getResource("/com/kianama3/server/gui/resources/Monitor-32 (1).png"));
	public static ImageIcon ICON_UPDATED = 
		new ImageIcon(ClientStatusRecordData.class.getResource("/com/kianama3/server/gui/resources/Arrow-Up-32.png"));
	public static ImageIcon ICON_NOT_UPDATED = 
		new ImageIcon(ClientStatusRecordData.class.getResource("/com/kianama3/server/gui/resources/Gnome-Software-Update-Urgent-32.png"));

	public String m_name;
	public String m_ip;
	public ClientStatusColumnData m_clientState; // each one has 5 field info in it.
	public ClientStatusColumnData m_monitorState;
	public ClientStatusColumnData m_updateState;
	public String m_clientInfo;
	public String m_monitorInfo;

	public ClientStatusRecordData(String name, String ip, int clientState,
			int monitorState, int updateState, String clientDesc,
			String clientDate, String monitorDesc, String monitorDate,
			String updateDesc, String updateDate, String clientInfo,
			String monitorInfo) {
		m_name = name;
		m_ip = ip;
		m_clientState = new ClientStatusColumnData(getIcon(1, clientState), new Integer(
				1), new Integer(clientState), clientDesc, clientDate);
		m_monitorState = new ClientStatusColumnData(getIcon(2, monitorState),
				new Integer(2), new Integer(monitorState), monitorDesc,
				monitorDate);
		m_updateState = new ClientStatusColumnData(getIcon(3, updateState), new Integer(
				3), new Integer(updateState), updateDesc, updateDate);
		m_clientInfo = clientInfo;
		m_monitorInfo = monitorInfo;
	}

	public static ImageIcon getIcon(int type, int state) {
		if (type == 1) {
			if (state == 1) {
				return ICON_CONNECTED;
			} else if (state == 2) {
				return ICON_DISCONNECTED;
			}
		} else if (type == 2) {
			if (state == 1) {
				return ICON_MONITOR_ON;
			} else if (state == 2) {
				return ICON_MONITOR_OFF;
			}
		} else if (type == 3) {
			if (state == 1) {
				return ICON_UPDATED;
			} else if (state == 2) {
				return ICON_NOT_UPDATED;
			}
		}
		return new ImageIcon(); // invalid type or state or has no data
	}
}