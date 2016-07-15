package com.kianama3.server.remote;

import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import com.kianama3.server.remote.common.UserPrivilegesData;

public class UserSession {
	public final long   loginDate = Calendar.getInstance().getTimeInMillis(); 
	public long   elapsedTime = Calendar.getInstance().getTimeInMillis();
	public String userName; 
	public boolean isLocked = false;
	public Locale locale;
	public ResourceBundle  bundle;
	public UserPrivilegesData privs;
}
