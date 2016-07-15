package com.kianama3.server.remote.common;

import java.io.Serializable;

public class ServerMonitorData implements Serializable {
	private static final long serialVersionUID = 4169380011578229515L;
	public int serverCpuUsage;
	public int kianamaCpuUsage;
	public int databaseCpuUsage;
	public int serverMemoryUsage;
	public int kianamaMemoryUsage;
	public int databaseMemoryUsage;
	public int serverBytesOut;
	public int serverBytesIn;
	public int kianamaBytesOut;
	public int kianamaBytesIn;
	public int databaseBytesIn;
	public int databaseBytesOut;
	public int connectedClients;
	public int disconnectedClients;	
}
