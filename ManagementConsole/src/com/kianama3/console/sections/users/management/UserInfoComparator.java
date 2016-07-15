package com.kianama3.console.sections.users.management;

import java.util.Comparator;

import com.kianama3.server.remote.common.UserData;

public class UserInfoComparator implements Comparator<Object> {
	protected int sortCol;
	protected boolean isSortAsc;

	public UserInfoComparator(int sortCol, boolean sortAsc) {
		this.sortCol = sortCol;
		this.isSortAsc = sortAsc;
	}

	public int compare(Object o1, Object o2) {
		if (!(o1 instanceof UserData)
				|| !(o2 instanceof UserData))
			return 0;
		UserData s1 = (UserData) o1;
		UserData s2 = (UserData) o2;
		int result = 0;
		switch (sortCol) {
		case UserManagementTableModel.COL_USERNAME:
			result = s1.userName.compareTo(s2.userName);
			break;
		case UserManagementTableModel.COL_FULLNAME:
			result = s1.fullName.compareTo(s2.fullName);
			break;
		case UserManagementTableModel.COL_STATUS:
			result = s1.userState > s2.userState ? 1 : 
					 s1.userState < s2.userState ? -1 : 0;
			break;
		case UserManagementTableModel.COL_EXPIRE_DATE_TIME: 
			result = s1.expireDateTime.compareTo(s2.expireDateTime);
			break;
		}
		if (!isSortAsc)
			result = -result;
		return result;
	}

	public boolean equals(Object obj) {
		if (obj instanceof UserInfoComparator) {
			UserInfoComparator compObj = (UserInfoComparator) obj;
			return (compObj.sortCol == sortCol)
					&& (compObj.isSortAsc == isSortAsc);
		}
		return false;
	}
}