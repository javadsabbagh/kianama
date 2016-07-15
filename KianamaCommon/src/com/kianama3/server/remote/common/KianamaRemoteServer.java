package com.kianama3.server.remote.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Locale;
import java.util.Vector;

public interface KianamaRemoteServer extends Remote {
	public void setLocale(String sessionID,Locale local)throws RemoteException;
	public String login(String userName , char[] password) throws RemoteException;
	public String login(String userName , char[] password,Locale locale) throws RemoteException;
	public void logout(String sessionID) throws RemoteException;
	public String getServerInfo(String sessionID) throws RemoteException;
	public String getServerLog(String sessionID) throws RemoteException;
	public void lock(String sessionID)throws RemoteException;
	public void unlock(String sessionID, char[] password)throws RemoteException;
	public void killSession(String sessionID,String specifiedSession) throws RemoteException;
	public void changePassword(String sessionID,char[] oldPassword,char[] newPassword) throws RemoteException;

	public Vector<UserSessionData> getSessionsInfo(String sessionID) throws RemoteException;

	public void setUserPrivileges(String sessionID,String userName,UserPrivilegesData priv) throws RemoteException;
	public UserPrivilegesData getUserPrivileges(String sessionID,String userName) throws RemoteException;
	public void setUserHosts(String sessionID,Vector<String> hosts) throws RemoteException;
	public Vector<String> getUserHosts(String sessionID) throws RemoteException;
	public void addNewUser(String sessionID,UserData userInfo) throws RemoteException;
	public void editUser(String sessionID,UserData userInfo) throws RemoteException;
	public void changeUserPassword(String sessionID,String userName,char[] userPassword) throws RemoteException;
	public void deleteUser(String sessionID,String userName) throws RemoteException;
	public Vector<UserData> getUsersList(String sessionID) throws RemoteException;

	public void addNewClient(String sessionID,ClientData clientInfo) throws RemoteException;
	public void editClient(String sessionID,String clientName,ClientData clientInfo) throws RemoteException;
	public void deleteClient(String sessionID,String clientName) throws RemoteException;
	public Vector<ClientData> getClientsList(String sessionID) throws RemoteException; 

	public byte[] rupReport(String sessionID,String reportName,Vector<ReportParamData> params) throws RemoteException;
	
	public Vector<DatabaseSessionData> getDatabaseSessionsList(String sessionID) throws RemoteException;
	public void addNewHost(String sessionID,String hostSerial) throws RemoteException;
	public void removeHost(String sessionID,String hostSerial) throws RemoteException;
	
	public Vector<String> getHostsList(String sessionID) throws RemoteException;
	//public void startup()throws RemoteException;
	//public void shutdown()throws RemoteException;
	//public void restart()throws RemoteExceptionn;
}
