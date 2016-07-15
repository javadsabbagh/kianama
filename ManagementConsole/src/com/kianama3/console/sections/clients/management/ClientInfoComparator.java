package com.kianama3.console.sections.clients.management;

import java.util.Comparator;

import com.kianama3.server.remote.common.ClientData;

public class ClientInfoComparator implements Comparator<Object> {
	protected int m_sortCol;
	protected boolean m_sortAsc;

	public ClientInfoComparator(int sortCol, boolean sortAsc) {
		m_sortCol = sortCol;
		m_sortAsc = sortAsc;
	}

	public int compare(Object o1, Object o2) {
		if (!(o1 instanceof ClientData)
				|| !(o2 instanceof ClientData))
			return 0;
		ClientData s1 = (ClientData) o1;
		ClientData s2 = (ClientData) o2;
		int result = 0;
		switch (m_sortCol) {
		case ClientsManagementTableModel.COL_NAME:
			result = s1.clientName.compareTo(s2.clientName);
			break;
		case ClientsManagementTableModel.COL_DESCRIPTION:
			result = s1.clientDesc.compareTo(s2.clientDesc);
			break;
		case ClientsManagementTableModel.COL_SERIAL:
			result = s1.clientSerial.compareTo(s2.clientSerial);
			break;
		case ClientsManagementTableModel.COL_STATE:
			result = s1.clientState > s2.clientState ? 1 : s1.clientState < s2.clientState ? -1 : 0;
			break;
		case ClientsManagementTableModel.COL_DISABLE_DATE:
			result = s1.disableDateTime.compareTo(s2.disableDateTime);
			break;
		}
		if (!m_sortAsc)
			result = -result;
		return result;
	}

	public boolean equals(Object obj) {
		if (obj instanceof ClientInfoComparator) {
			ClientInfoComparator compObj = (ClientInfoComparator) obj;
			return (compObj.m_sortCol == m_sortCol)
					&& (compObj.m_sortAsc == m_sortAsc);
		}
		return false;
	}
}
