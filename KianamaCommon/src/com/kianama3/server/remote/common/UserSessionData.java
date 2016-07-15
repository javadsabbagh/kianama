package com.kianama3.server.remote.common;

import java.io.Serializable;

//It's a data structure class
public class UserSessionData implements Serializable{
	private static final long serialVersionUID = 1L;

	public long   loginDate = 0; //Elapsed milliseconds from UNIX epoch
	public String userName; 
	public boolean isLocked = false;
	public int lastExecutedCommand = -1; 
	public String computerName;
	public String computerIP;
	public String macAddress;
	public String systemInfo;
	public String hostID;
}
