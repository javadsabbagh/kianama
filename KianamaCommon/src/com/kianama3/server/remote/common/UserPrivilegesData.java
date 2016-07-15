package com.kianama3.server.remote.common;

import java.io.Serializable;

public class UserPrivilegesData extends Auditable implements Serializable{
	public static final long serialVersionUID = 1L;
	public boolean manageServer = false;
	public boolean viewServer = false;
	public boolean manageDBMS = false;
	public boolean viewDBMS = false;
	public boolean manageUsers = false;
	public boolean viewUsers = false;
	public boolean manageSessions = false;
	public boolean viewSessions = false;
	public boolean manageClients = false;
	public boolean viewClients = false;
	public boolean runReports = false;
}
