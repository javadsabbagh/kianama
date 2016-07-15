package com.kianama3.server.remote;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.util.Vector;
import java.util.logging.Logger;
import java.util.Locale;

import com.kianama3.server.logging.KianamaRemoteServerLogger;
import com.kianama3.server.remote.SessionManager.PrivilegeType;
import com.kianama3.server.remote.common.ClientData;
import com.kianama3.server.remote.common.DatabaseSessionData;
import com.kianama3.server.remote.common.KianamaRemoteServer;
import com.kianama3.server.remote.common.UserPrivilegesData;
import com.kianama3.server.remote.common.ReportParamData;
import com.kianama3.server.remote.common.UserData;
import com.kianama3.server.remote.common.UserSessionData;

import com.kianama3.server.remote.database.RemoteServerDataAccess;

public class KianamaRemoteServerImpl extends UnicastRemoteObject 
								 implements KianamaRemoteServer {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(KianamaRemoteServerImpl.class.getName());

	public KianamaRemoteServerImpl() throws RemoteException{
		try {
			KianamaRemoteServerLogger.setup();
			RemoteServerDataAccess.setup();
		} catch (Exception e) {
			System.out.println("Error in Access Server Setup ... " + e.getMessage());
		}
	}

	//==================================User Session=================================
	
	@Override
	public String login(String userName, char[] password) 
	throws RemoteException {
		Locale locale = new Locale("en");
		return login(userName,password,locale);
	}
	
	@Override
	public String login(String userName, char[] password, Locale locale) 
	throws RemoteException {
		RemoteServerDataAccess.checkUserPassword(userName, new String(password),locale);
		return SessionManager.createSession(userName,locale);
	}

	@Override
	public void logout(String sessionID) throws RemoteException {
		SessionManager.logout(sessionID);
	}

	@Override
	public void lock(String sessionID) throws RemoteException {
		SessionManager.lockSession(sessionID);
	}

	@Override
	public void unlock(String sessionID, char[] password)
			throws RemoteException {
		Locale locale = SessionManager.getSessionLocale(sessionID);
		String userName = SessionManager.getUserName(sessionID);
		RemoteServerDataAccess.checkUserPassword(userName, new String(password),locale);
		SessionManager.unlockSession(sessionID);
	}

	@Override
	public void setLocale(String sessionID, Locale locale) 
		throws RemoteException {
		SessionManager.checkAuthenticated(sessionID); 
		SessionManager.setSessionLocale(sessionID,locale);
	}
	
	@Override
	public void changePassword(String sessionID,char[] oldPassword,char[] newPassword) 
	throws RemoteException{
		SessionManager.changePassword(sessionID, new String(oldPassword), 
										new String(newPassword));
	}
	
	//=================================Users Section==============================
	@Override
	public void setUserPrivileges(String sessionID,String userName,UserPrivilegesData priv) 
	throws RemoteException{
		SessionManager.checkAuthenticated(sessionID);
		SessionManager.checkAuthorized(sessionID,PrivilegeType.ManageUsers);
		Locale locale = SessionManager.getSessionLocale(sessionID);
		String auditUser = SessionManager.getUserName(sessionID);
		RemoteServerDataAccess.setUserPrivileges(userName, priv, locale, auditUser);
	}
	
	@Override
	public UserPrivilegesData getUserPrivileges(String sessionID,String userName) 
	throws RemoteException{
		SessionManager.checkAuthenticated(sessionID);
		SessionManager.checkAuthorized(sessionID,PrivilegeType.ViewUsers);
		Locale locale = SessionManager.getSessionLocale(sessionID);
		return RemoteServerDataAccess.getUserPrivileges(userName, locale);
	}
	
	@Override
	public void setUserHosts(String sessionID,Vector<String> hosts) 
	throws RemoteException{
		
	}
	
	@Override
	public Vector<String> getUserHosts(String sessionID)
	throws RemoteException{
		return null;
	}
	
	
	@Override
	public void addNewUser(String sessionID,UserData userInfo) 
	throws RemoteException{
		SessionManager.checkAuthenticated(sessionID);
		SessionManager.checkAuthorized(sessionID, PrivilegeType.ManageUsers);
		Locale locale = SessionManager.getSessionLocale(sessionID);
		String auditUser = SessionManager.getUserName(sessionID);
		RemoteServerDataAccess.addNewUser(userInfo, locale,auditUser);
	}
	
	@Override
	public void editUser(String sessionID,UserData userInfo) 
	throws RemoteException{
		SessionManager.checkAuthenticated(sessionID);
		SessionManager.checkAuthorized(sessionID, PrivilegeType.ManageUsers);
		Locale locale = SessionManager.getSessionLocale(sessionID);
		String auditUser = SessionManager.getUserName(sessionID);
		RemoteServerDataAccess.editUser(userInfo,locale,auditUser);
	}
	
	@Override
	public void deleteUser(String sessionID,String userName) 
	throws RemoteException{
		SessionManager.checkAuthenticated(sessionID);
		SessionManager.checkAuthorized(sessionID, PrivilegeType.ManageUsers);
		Locale locale = SessionManager.getSessionLocale(sessionID);
		String auditUser = SessionManager.getUserName(sessionID);
		RemoteServerDataAccess.deleteUser(userName,locale,auditUser);
	}
	
	@Override
	public Vector<UserData> getUsersList(String sessionID) 
	throws RemoteException{
		SessionManager.checkAuthenticated(sessionID);
		SessionManager.checkAuthorized(sessionID, PrivilegeType.ViewUsers);
		Locale locale = SessionManager.getSessionLocale(sessionID);
		return RemoteServerDataAccess.getUsersList(locale);
	}
	
	@Override
	public void changeUserPassword(String sessionID,String userName,char[] userPassword) 
	throws RemoteException{
		SessionManager.checkAuthenticated(sessionID);
		SessionManager.checkAuthorized(sessionID, PrivilegeType.ManageUsers);
		Locale locale = SessionManager.getSessionLocale(sessionID);
		String auditUser = SessionManager.getUserName(sessionID);
		RemoteServerDataAccess.changeUserPassword(userName,new String(userPassword),
													locale,auditUser);
	}
	
	//=================================User Session Management===========================
	@Override
	public Vector<UserSessionData> getSessionsInfo(String sessionID)
			throws RemoteException {
		SessionManager.checkAuthenticated(sessionID);
		SessionManager.checkAuthorized(sessionID,PrivilegeType.ViewSessions);
		return null;
	}
	
	@Override
	public void killSession(String sessionID, String specifiedSessionID)
			throws RemoteException {
		SessionManager.checkAuthenticated(sessionID); 
		SessionManager.checkAuthorized(sessionID,PrivilegeType.ManageSessions);
		SessionManager.killSession(sessionID,specifiedSessionID);
	}
	
	//================================Clients Section================================ 
	@Override
	public Vector<ClientData> getClientsList(String sessionID) 
	throws RemoteException{		
		SessionManager.checkAuthenticated(sessionID); 
		SessionManager.checkAuthorized(sessionID,PrivilegeType.ViewClients); 
		Locale locale = SessionManager.getSessionLocale(sessionID);
		return RemoteServerDataAccess.getClientsList(locale);
	}
	
	@Override
	public void addNewClient(String sessionID,ClientData clientInfo) 
	throws RemoteException{
		SessionManager.checkAuthenticated(sessionID);
		SessionManager.checkAuthorized(sessionID, PrivilegeType.ManageClients);
		Locale locale = SessionManager.getSessionLocale(sessionID);
		String auditUser = SessionManager.getUserName(sessionID);
		RemoteServerDataAccess.addNewClient(clientInfo, locale,auditUser);
	}
	
	@Override
	public void editClient(String sessionID,String clientName,ClientData clientInfo) 
	throws RemoteException{
		SessionManager.checkAuthenticated(sessionID);
		SessionManager.checkAuthorized(sessionID, PrivilegeType.ManageClients);
		Locale locale = SessionManager.getSessionLocale(sessionID);
		String auditUser = SessionManager.getUserName(sessionID);
		RemoteServerDataAccess.editClient(clientName,clientInfo,locale,auditUser);
	}
	
	@Override
	public void deleteClient(String sessionID,String clientName)
	throws RemoteException{
		SessionManager.checkAuthenticated(sessionID);
		SessionManager.checkAuthorized(sessionID, PrivilegeType.ManageClients);
		Locale locale = SessionManager.getSessionLocale(sessionID);
		String auditUser = SessionManager.getUserName(sessionID);
		RemoteServerDataAccess.deleteClient(clientName,locale,auditUser);
	}
	
	//==================================Server Section==============================
	@Override
	public String getServerLog(String sessionID) throws RemoteException {
		SessionManager.checkAuthenticated(sessionID);
		SessionManager.checkAuthorized(sessionID,PrivilegeType.ViewServer);
		// TODO : Warning, log files may be very huge -- we must send stream
		// reader over net and client open-read-close it
		// return log file
		return null;
	}

	@Override
	public String getServerInfo(String sessionID) throws RemoteException {
		SessionManager.checkAuthenticated(sessionID);
		SessionManager.checkAuthorized(sessionID,PrivilegeType.ViewServer);
		// TODO return server info		
		return null;
	}
	
	//==================================Report Section=============================
	
	public byte[] rupReport(String sessionID,String reportName,Vector<ReportParamData> params) 
	throws RemoteException{
		SessionManager.checkAuthenticated(sessionID);
		SessionManager.checkAuthorized(sessionID,PrivilegeType.RunReports);
		// TODO return executed report
		return null;
	}
	
	//===============================Database Section==============================
	public Vector<DatabaseSessionData> getDatabaseSessionsList(String sessionID) 
	throws RemoteException{
		SessionManager.checkAuthenticated(sessionID);
		SessionManager.checkAuthorized(sessionID,PrivilegeType.ViewDBMS);
		Locale locale = SessionManager.getSessionLocale(sessionID);
		return RemoteServerDataAccess.getDatabaseSessionsList(locale);
	}
	
	//==================================Hosts Section==============================
	public Vector<String> getHostsList(String sessionID) 
	throws RemoteException{
		SessionManager.checkAuthenticated(sessionID);
		//TODO correct ManageUsers to ManageHosts
		SessionManager.checkAuthorized(sessionID,PrivilegeType.ManageUsers);
		Locale locale = SessionManager.getSessionLocale(sessionID);
		return RemoteServerDataAccess.getHostsList(locale);
	}
	
	public void addNewHost(String sessionID,String hostSerial) 
	throws RemoteException{
		SessionManager.checkAuthenticated(sessionID);
		//TODO correct ManageUsers to ManageHosts
		SessionManager.checkAuthorized(sessionID,PrivilegeType.ManageUsers);
		Locale locale = SessionManager.getSessionLocale(sessionID);
		String auditUser = SessionManager.getUserName(sessionID);
		RemoteServerDataAccess.addNewHost(hostSerial,locale,auditUser);
	}
	
	public void removeHost(String sessionID,String hostSerial) 
	throws RemoteException{
		SessionManager.checkAuthenticated(sessionID);
		//TODO correct ManageUsers to ManageHosts
		SessionManager.checkAuthorized(sessionID,PrivilegeType.ManageUsers);
		Locale locale = SessionManager.getSessionLocale(sessionID);
		String auditUser = SessionManager.getUserName(sessionID);
		RemoteServerDataAccess.removeHost(hostSerial,locale,auditUser);
	}
	//==================================Main Section================================
	public static void main(String[] args){
		try {
			Locale.setDefault(new Locale("en"));
			
			Registry registry = LocateRegistry.createRegistry(1099);
			//Registry registry = LocateRegistry.getRegistry(1099);
					
			KianamaRemoteServerImpl server = new KianamaRemoteServerImpl();
			registry.bind("KianamaRemoteServer", server);
			LOGGER.info("Access Server Started ...");
		} catch (Exception e) {
			LOGGER.severe("Can not start Kianama Access Server: "+e.getMessage());
		}
	}
}
