package com.kianama3.server.remote.common;

import java.io.Serializable;

public class DatabaseSessionData implements Serializable{

	private static final long serialVersionUID = -3520115610984717901L;
	
	public int     sessionID;
	public String  userName;
	public String  hostName;
	public String  dbName;
	public String  command;
	public int     time;
	public String  sessionState;
	public String  sessionInfo;

}
