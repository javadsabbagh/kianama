package com.kianama3.server.remote.common;

import java.io.Serializable;

public class DatabaseMonitorData implements Serializable{

	private static final long serialVersionUID = 1614714203568609078L;

	public long upTime;
	public long bytesSent;
	public long bytesRecieved;
	public String version;
	public int threadsCreated;
	public int threadsRunning;
}
