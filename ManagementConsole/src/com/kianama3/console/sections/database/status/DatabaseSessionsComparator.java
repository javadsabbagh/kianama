package com.kianama3.console.sections.database.status;

import java.util.Comparator;

import com.kianama3.server.remote.common.DatabaseSessionData;

public class DatabaseSessionsComparator implements Comparator<Object> {
	protected int sortCol;
	protected boolean isSortAsc;

	public DatabaseSessionsComparator(int sortCol, boolean sortAsc) {
		this.sortCol = sortCol;
		this.isSortAsc = sortAsc;
	}

	public int compare(Object o1, Object o2) {
		if (!(o1 instanceof DatabaseSessionData)
				|| !(o2 instanceof DatabaseSessionData))
			return 0;
		DatabaseSessionData s1 = (DatabaseSessionData) o1;
		DatabaseSessionData s2 = (DatabaseSessionData) o2;
		int result = 0;
		
		switch (sortCol) {
			case DatabaseSessionsTableModel.COL_SESSION_ID:
				result = s1.sessionID > s2.sessionID ? 1 : 
					     s1.sessionID < s2.sessionID ? -1 : 0;
				break;
			case DatabaseSessionsTableModel.COL_USER_NAME:
				result = s1.userName.compareTo(s2.userName);
				break;
			case DatabaseSessionsTableModel.COL_HOST_NAME:
				result = s1.hostName.compareTo(s2.hostName);
				break;
			case DatabaseSessionsTableModel.COL_DB_NAME: 
				result = s1.dbName.compareTo(s2.dbName);
				break;
			case DatabaseSessionsTableModel.COL_COMMAND: 
				result = s1.command.compareTo(s2.command);
				break;
			case DatabaseSessionsTableModel.COL_TIME:
				result = s1.time > s2.time ? 1 : 
					     s1.time < s2.time ? -1 : 0;
				break;
			case DatabaseSessionsTableModel.COL_SESSION_STATE:
				result = s1.sessionState.compareTo(s2.sessionState);
				break;
			case DatabaseSessionsTableModel.COL_SESSION_INFO:
				result = s1.sessionInfo.compareTo(s2.sessionInfo);
				break;
		}
		
		if (!isSortAsc)
			result = -result;
		return result;
	}

	public boolean equals(Object obj) {
		if (obj instanceof DatabaseSessionsComparator) {
			DatabaseSessionsComparator compObj = (DatabaseSessionsComparator) obj;
			return (compObj.sortCol == sortCol)
					&& (compObj.isSortAsc == isSortAsc);
		}
		return false;
	}
}