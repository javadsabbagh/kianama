package com.kianama3.console.sections.users.sessions;

import java.util.Comparator;

public class SessionTableComparator implements Comparator<Object> {
	protected int sortCol;
	protected boolean isSortAsc;

	public SessionTableComparator(int sortCol, boolean sortAsc) {
		this.sortCol = sortCol;
		isSortAsc = sortAsc;
	}

	public int compare(Object o1, Object o2) {
		if (!(o1 instanceof SessionTableRecordData)
				|| !(o2 instanceof SessionTableRecordData))
			return 0;
		SessionTableRecordData s1 = (SessionTableRecordData) o1;
		SessionTableRecordData s2 = (SessionTableRecordData) o2;
		int result = 0;
		switch (sortCol) {
		case SessionTableModel.COL_SESSIONID:
			result = s1.sessionID > s2.sessionID ? 1 :
					(s1.sessionID < s2.sessionID) ? -1 : 0;
			break;
		case SessionTableModel.COL_USERNAME:
			result = s1.userName.compareTo(s2.userName);
			break;
		case SessionTableModel.COL_COMPUTERNAME: 
			result = s1.computerName.compareTo(s2.computerName);
			break;
		case SessionTableModel.COL_IPADDRESS:
			result = s1.ipAddress.compareTo(s2.ipAddress);
			break;
		case SessionTableModel.COL_MACADDRESS:
			result = s1.macAddress.compareTo(s2.macAddress);
			break;
		case SessionTableModel.COL_COMMAND: 
			result = s1.command.compareTo(s2.command);
			break;
		case SessionTableModel.COL_LOGINDATE: 
			result = s1.loginDate.compareTo(s2.loginDate);
			break;
		case SessionTableModel.COL_STATUS: 
			result = s1.status.compareTo(s2.status);
			break;
		}
		if (!isSortAsc)
			result = -result;
		return result;
	}

	public boolean equals(Object obj) {
		if (obj instanceof SessionTableComparator) {
			SessionTableComparator compObj = (SessionTableComparator) obj;
			return (compObj.sortCol == sortCol)
					&& (compObj.isSortAsc == isSortAsc);
		}
		return false;
	}
}