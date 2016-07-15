package com.kianama3.server.remote;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import com.ghasemkiani.util.icu.PersianCalendar;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import com.kianama3.server.remote.database.RemoteServerDataAccess;

public class SessionManager {
	private static final long LOGINTIMEOUT = 10 * 1000 * 60; 		// 10 Minutes
	private static final long LOCKTIMEOUT =  5 * 1000 * 60 * 60; 	// 5 Hours
	private static final int  MAX_LOGIN_COUNT = 3;
	private static Map<String, UserSession> consoleSessions = new HashMap<String, UserSession>();
	private static Map<String, Integer>		userLoginTime = new HashMap<String, Integer>();
	private static Map<String, Vector<String>> userSessions = new HashMap<String,Vector<String>>();
	
	public enum PrivilegeType{ManageServer,ViewServer,ManageDBMS,ViewDBMS,
							  ManageUsers,ViewUsers,ManageSessions,ViewSessions,
							  ManageClients,ViewClients,RunReports};	
	 						  
    
	private static SecureRandom random = new SecureRandom();	
	
	public static String createSession(String userName,Locale locale) throws RemoteException{
		checkLoginCountLimit(userName,locale);
		String sID = generateSessionID();
		
		UserSession s = new UserSession();
		s.userName = userName;
		s.locale = locale;
		s.privs = RemoteServerDataAccess.getUserPrivileges(userName, locale);
		
		consoleSessions.put(sID, s);
		increaseLoginCount(sID);
		RemoteServerDataAccess.insertSessionLog(userName, "login" , getCurrentDate(), null, null,null, userName);
		return sID;
	}


	private static String getCurrentDate(){
		PersianCalendar calendar;
		TimeZone timeZone = TimeZone.getTimeZone("Asia/Tehran");
		ULocale uLocale = ULocale.createCanonical("fa");
		calendar = new PersianCalendar(timeZone, uLocale);

		try {
			if (calendar.get(Calendar.MONTH)<6)
				calendar.add(Calendar.HOUR, Integer.parseInt("-1"));
		} catch (Exception e) {
		}

		SimpleDateFormat sdf = (SimpleDateFormat) calendar.getDateTimeFormat(2,2, uLocale);//new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
		return sdf.format(calendar.getTime());
	}

	private static String generateSessionID() {
		String sID = new BigInteger(100, random).toString(32);
		while (consoleSessions.get(sID) != null)
			sID = new BigInteger(100, random).toString(32);
		return sID;
	}	
	
	private static void checkLoginCountLimit(String userName,Locale locale) 
	throws RemoteException{
		if(getLoginCount(userName)>= MAX_LOGIN_COUNT){
			String msg = MessageFormat.format(ResourceBundle.getBundle("com.kianama3.server.remote.Error_Codes",
					  										locale).getString("RMT-132"),userName);
			throw new RemoteException(msg);
		}
	}
	
	private static void checkSessionExists(String sessionID) throws RemoteException{
		if (getSession(sessionID) == null)
			throw new 
			RemoteException(getSessionBundle(sessionID).getString("RMT-133"));
	}
	
	private static void checkIdleTimeLimit(String sessionID) throws RemoteException{
		if (((Calendar.getInstance().getTimeInMillis() - 
				getSession(sessionID).elapsedTime) > LOGINTIMEOUT)) {
			decreaseLoginCount(sessionID);
			consoleSessions.remove(sessionID); //prevent session vulnerability
			throw new 
			RemoteException(getSessionBundle(sessionID).getString("RMT-137"));
		}
	}
	
	private static void checkLockTimeLimit(String sessionID) throws RemoteException{
		if (((Calendar.getInstance().getTimeInMillis() - 
				getSession(sessionID).elapsedTime) >	LOCKTIMEOUT)) {
			decreaseLoginCount(sessionID);
			consoleSessions.remove(sessionID); //prevent session vulnerability
			throw new 
			RemoteException(getSessionBundle(sessionID).getString("RMT-136"));
		}
	}
	
	private static void checkSessionNotLocked(String sessionID) throws RemoteException{
		if (getSession(sessionID).isLocked)
			throw new 
			RemoteException(getSessionBundle(sessionID).getString("RMT-138"));
	}
	
	private static void checkSessionLocked(String sessionID) throws RemoteException{
		if (!getSession(sessionID).isLocked)
			throw new 
			RemoteException(getSessionBundle(sessionID).getString("RMT-135"));
	}
	
	public static void lockSession(String sessionID) throws RemoteException{
		checkSessionExists(sessionID);
		checkIdleTimeLimit(sessionID);
		checkSessionNotLocked(sessionID);
		getSession(sessionID).isLocked = true;	
		updateSessionTime(sessionID);
		String userName = getUserName(sessionID);
		RemoteServerDataAccess.insertSessionLog(getUserName(sessionID),"lock" , getCurrentDate(), null, null,null, userName);
	}
	
	public static void unlockSession(String sessionID) throws RemoteException{
		checkSessionExists(sessionID);
		checkSessionLocked(sessionID);
		checkLockTimeLimit(sessionID);
		getSession(sessionID).isLocked = false;
		updateSessionTime(sessionID);
		String userName = getUserName(sessionID);
		RemoteServerDataAccess.insertSessionLog(getUserName(sessionID),"unlock" , getCurrentDate(), null, null,null, userName);
	}
	
	public static void logout(String sessionID) throws RemoteException{
		checkSessionExists(sessionID);
		decreaseLoginCount(sessionID);
		String userName = getUserName(sessionID);
		RemoteServerDataAccess.insertSessionLog(getUserName(sessionID),"logout" , getCurrentDate(), null, null,null, userName);
		consoleSessions.remove(sessionID);
	}
	
	public static void changePassword(String sessionID,String oldPassword,String newPassword)
	throws RemoteException{
		checkAuthenticated(sessionID); 
		Locale locale = getSessionLocale(sessionID);
		String userName = getUserName(sessionID);
		RemoteServerDataAccess.changePassword(userName,new String(oldPassword),
													new String(newPassword),
													locale,userName);
		RemoteServerDataAccess.insertSessionLog(userName,"change_password" , getCurrentDate(), null, null,null, userName);
	}
	
	public static void checkAuthenticated(String sessionID)throws RemoteException{
		checkSessionExists(sessionID);
		checkSessionNotLocked(sessionID);
		checkIdleTimeLimit(sessionID);
		updateSessionTime(sessionID);
	}
	
	public static void checkAuthorized(String sessionID,PrivilegeType priv)
	throws RemoteException{
		switch(priv){
			case ManageServer:
				if (!getSession(sessionID).privs.manageServer)
					throw new RemoteException(getSessionBundle(sessionID).getString("RMT-134"));
				break;
			case ViewServer:
				if (!getSession(sessionID).privs.viewServer)
					throw new RemoteException(getSessionBundle(sessionID).getString("RMT-134"));				
				break;
			case ManageDBMS:
				if (!getSession(sessionID).privs.manageDBMS)
					throw new RemoteException(getSessionBundle(sessionID).getString("RMT-134"));
				break;
			case ViewDBMS:
				if (!getSession(sessionID).privs.viewDBMS)
					throw new RemoteException(getSessionBundle(sessionID).getString("RMT-134"));
				break;
			case ManageUsers:
				if (!getSession(sessionID).privs.manageUsers)
					throw new RemoteException(getSessionBundle(sessionID).getString("RMT-134"));
				break;
			case ViewUsers:
				if (!getSession(sessionID).privs.viewUsers)
					throw new RemoteException(getSessionBundle(sessionID).getString("RMT-134"));
				break;
			case ManageSessions:
				if (!getSession(sessionID).privs.manageSessions)
					throw new RemoteException(getSessionBundle(sessionID).getString("RMT-134"));
				break;
			case ViewSessions:
				if (!getSession(sessionID).privs.viewSessions)
					throw new RemoteException(getSessionBundle(sessionID).getString("RMT-134"));
				break;
			case ManageClients:
				if (!getSession(sessionID).privs.manageClients)
					throw new RemoteException(getSessionBundle(sessionID).getString("RMT-134"));
				break;
			case ViewClients:
				if (!getSession(sessionID).privs.viewClients)
					throw new RemoteException(getSessionBundle(sessionID).getString("RMT-134"));
				break;
			case RunReports:
				if (!getSession(sessionID).privs.runReports)
					throw new RemoteException(getSessionBundle(sessionID).getString("RMT-134"));
				break;
		}
	}
	

	public static UserSession getSession(String sessionID){
		return consoleSessions.get(sessionID);
	}
	
	public static void killSession(String sessionID,String specifiedSession) throws RemoteException{
		checkSessionExists(specifiedSession);
		decreaseLoginCount(specifiedSession);
		consoleSessions.remove(specifiedSession);
		updateSessionTime(sessionID);
	}
	
	public static void setSessionLocale(String sessionID,Locale locale) throws RemoteException{
		checkAuthenticated(sessionID);
		if (locale != null)
			getSession(sessionID).locale = locale;
	}
	
	public static Locale getSessionLocale(String sessionID){
		return getSession(sessionID).locale;
	}
	
	public static String getUserName(String sessionID){
		return getSession(sessionID).userName;
	}
	
	private static ResourceBundle getSessionBundle(String sessionID) {
		return ResourceBundle.getBundle("com.kianama3.server.remote.Error_Codes", 
										getSession(sessionID).locale);
	}
	
	private static void updateSessionTime(String sessionID) {
		getSession(sessionID).elapsedTime = Calendar.getInstance().getTimeInMillis();
	}
	
	private static void decreaseLoginCount(String sessionID){
		String u = getUserName(sessionID);
		int n = getLoginCount(u);
		setLoginCount(u,--n);
	}
	
	private static void increaseLoginCount(String sessionID){
		String u = getUserName(sessionID);
		int n = getLoginCount(u);
		setLoginCount(u,++n);
	}
	
	private static void setLoginCount(String userName,int n) {
		userLoginTime.put(userName, new Integer(n));
	}
	
	private static int getLoginCount(String userName) {
		Integer lg = userLoginTime.get(userName);
		int loginTimes = (lg == null) ? 0 : lg.intValue();
		return loginTimes;
	}
}

