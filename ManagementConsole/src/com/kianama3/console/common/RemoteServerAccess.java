package com.kianama3.console.common;

import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Locale;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.kianama3.server.remote.common.ClientData;
import com.kianama3.server.remote.common.DatabaseSessionData;
import com.kianama3.server.remote.common.KianamaRemoteServer;
import com.kianama3.server.remote.common.UserPrivilegesData;
import com.kianama3.server.remote.common.UserData;

public class RemoteServerAccess {
	private static String sessionID;
	private static String hostName;
	private static int	  portNumber;
	private static String userName;
	private static KianamaRemoteServer server;

	
	public static void setup(String serverAddress,int port) throws Exception{
		try{
		    //System.setSecurityManager(new RMISecurityManager());
			Registry registry = LocateRegistry.getRegistry(serverAddress,portNumber);

			server = (KianamaRemoteServer) registry.lookup("KianamaRemoteServer");
			hostName = serverAddress;
			portNumber = port;
		}catch(Exception e){
			if(e instanceof RemoteException){
				if (e.getMessage().contains("UnknownHostException") ||  //unknown host
					e.getMessage().contains("ConnectException")) 		//unknown port : connection refused
					JOptionPane.showMessageDialog(null, 
							KianamaResourceBundle.getString("failed_remote_connection"),
							KianamaResourceBundle.getString("error_title"),
							JOptionPane.ERROR_MESSAGE);	
				else 
					JOptionPane.showMessageDialog(null, //e.getMessage(),
											  getRootCause(e).getMessage(),
											  KianamaResourceBundle.getString("error_title"),
											  JOptionPane.ERROR_MESSAGE);
			}else if(e instanceof NotBoundException){
					JOptionPane.showMessageDialog(null, KianamaResourceBundle.getString("not_bound_error"),
											 KianamaResourceBundle.getString("error_title"),
											 JOptionPane.ERROR_MESSAGE);
			}
			throw e;
		}          
	}
	
	public static void cleanup() throws Exception{
		
	}
	
	//=============================Session Section=============================
	public static void login(String userName,char[] password) throws Exception{
		try{
			sessionID = server.login(userName, password);	
			RemoteServerAccess.userName = userName; 
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, 
					  getRootCause(e).getMessage(),
					  KianamaResourceBundle.getString("error_title"),
					  JOptionPane.ERROR_MESSAGE);
			throw e;
		}
	}
	
	public static void login(String userName,char[] password,Locale locale) throws Exception{
		try{
			sessionID = server.login(userName, password,locale);
			RemoteServerAccess.userName = userName; 
		}catch(Exception e){
			//TODO Handle --> java.net.NoRouteToHostException: NoRouteToHost : connect
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, 
					  //getRootCause(e).getMessage(),
					   e.getMessage(),
					  KianamaResourceBundle.getString("error_title"),
					  JOptionPane.ERROR_MESSAGE);
			throw e;
		}
	}
	
	public static void logout() throws Exception{
		server.logout(sessionID);
	}
	public static String getServerInfo() throws Exception{
		return server.getServerInfo(sessionID);
	} 
	public static String getServerLog() throws Exception{
		return server.getServerLog(sessionID);
	}
	public static void killSession(String specifiedSession) throws Exception{
		server.killSession(sessionID, specifiedSession);
	}
	public static void lock()throws Exception{
		server.lock(sessionID);
	}
	public static void unlock(char[] password) throws Exception{
		try {
			server.unlock(sessionID, password);
		} catch (RemoteException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, 
					getRootCause(e).getMessage(),
					KianamaResourceBundle.getString("error_title"),
					JOptionPane.ERROR_MESSAGE);
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public static void changePassword(char[] oldPassword,char[] newPassword) 
	throws RuntimeException{
		try {
			server.changePassword(sessionID, oldPassword, newPassword);
		} catch (RemoteException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, 
					getRootCause(e).getMessage(),
					KianamaResourceBundle.getString("error_title"),
					JOptionPane.ERROR_MESSAGE);
			throw new RuntimeException(e.getMessage());
		}
	}
	
	//============================Users Section============================
	public static Vector<UserData> getUsersList() throws Exception{
		try{
			return server.getUsersList(sessionID);
		}catch(Exception e){
			e.printStackTrace();
				JOptionPane.showMessageDialog(null, 
						getRootCause(e).getMessage(),
						KianamaResourceBundle.getString("error_title"),
						JOptionPane.ERROR_MESSAGE);
				throw e;
		}
	}
	
	public static UserPrivilegesData getUserPriviliges(String userName) throws Exception{ 
		try{
			return server.getUserPrivileges(sessionID,userName);
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, 
					  getRootCause(e).getMessage(),
					  KianamaResourceBundle.getString("error_title"),
					  JOptionPane.ERROR_MESSAGE);
			throw e;
		}
		
	}
	
	public static void setUserPriviliges(String userName,UserPrivilegesData prv) 
	throws Exception{ 
		try{
			server.setUserPrivileges(sessionID,userName,prv);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, 
					  getRootCause(e).getMessage(),
					  KianamaResourceBundle.getString("error_title"),
					  JOptionPane.ERROR_MESSAGE);
			throw e;
		}
		
	}
	
	public static void addNewUser(UserData u) throws Exception{
		try{
			server.addNewUser(sessionID, u);
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, 
					  getRootCause(e).getMessage(),
					  KianamaResourceBundle.getString("error_title"),
					  JOptionPane.ERROR_MESSAGE);
			throw e;
		}
	}
	
	public static void editUser(UserData u) throws Exception{
		try{
			server.editUser(sessionID, u);
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, 
					  getRootCause(e).getMessage(),
					  KianamaResourceBundle.getString("error_title"),
					  JOptionPane.ERROR_MESSAGE);
			throw e;
		}
	}
	
	public static void deleteUser(String userName) throws Exception{
		try{
			server.deleteUser(sessionID, userName);
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, 
					  getRootCause(e).getMessage(),
					  KianamaResourceBundle.getString("error_title"),
					  JOptionPane.ERROR_MESSAGE);
			throw e;
		}
	}
	
	public static String getUserName(){
		return userName;
	}
	
	public static void changeUserPassword(String userName,char[] userPassword)
	throws RuntimeException{
		try{
			server.changeUserPassword(sessionID, userName, userPassword);
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, 
					  getRootCause(e).getMessage(),
					  KianamaResourceBundle.getString("error_title"),
					  JOptionPane.ERROR_MESSAGE);
			throw new RuntimeException(e.getMessage());
		}
	}
	
	//=============================Clients Section==========================
	public static Vector<ClientData> getClientsList() throws Exception{
		try{
			return server.getClientsList(sessionID);
		}catch(Exception e){
			e.printStackTrace();
/*			if(isConnectonRefuseError(e.getMessage())){
				showLoginDialog();
				return getClientsList();
			}else{*/
				JOptionPane.showMessageDialog(null, 
						getRootCause(e).getMessage(),
						KianamaResourceBundle.getString("error_title"),
						JOptionPane.ERROR_MESSAGE);
				throw e;
			//}
		}
	}
	
	public static void addNewClient(ClientData c) throws Exception{
		try{
			server.addNewClient(sessionID, c);
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, 
					  getRootCause(e).getMessage(),
					  KianamaResourceBundle.getString("error_title"),
					  JOptionPane.ERROR_MESSAGE);
			throw e;
		}
	}
	
	public static void editClient(String clientName,ClientData c) throws Exception{
		try{
			server.editClient(sessionID,clientName,c);
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, 
					  getRootCause(e).getMessage(),
					  KianamaResourceBundle.getString("error_title"),
					  JOptionPane.ERROR_MESSAGE);
			throw e;
		}
	}
	
	public static void deleteClient(String clientName)throws RuntimeException{
		try{
			server.deleteClient(sessionID,clientName);
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, 
					  getRootCause(e).getMessage(),
					  KianamaResourceBundle.getString("error_title"),
					  JOptionPane.ERROR_MESSAGE);
			throw new RuntimeException();
		}
	}
	//===========================Database Methods===============================
	public static Vector<DatabaseSessionData> getDatabaseSessionsList() throws Exception{
		try{
			return server.getDatabaseSessionsList(sessionID);
		}catch(Exception e){
			e.printStackTrace();
				JOptionPane.showMessageDialog(null, 
						getRootCause(e).getMessage(),
						KianamaResourceBundle.getString("error_title"),
						JOptionPane.ERROR_MESSAGE);
				throw e;
		}
	}
	
	//=========================== Hosts Methods ===============================
	public static Vector<String> getHostsList() throws Exception{
		try{
			return server.getHostsList(sessionID);
		}catch(Exception e){
				JOptionPane.showMessageDialog(null, 
						getRootCause(e).getMessage(),
						KianamaResourceBundle.getString("error_title"),
						JOptionPane.ERROR_MESSAGE);
				throw e;
		}
	}
	
	public static void addNewHost(String hostSerial) throws Exception{
		try{
			server.addNewHost(sessionID,hostSerial);
		}catch(Exception e){
				JOptionPane.showMessageDialog(null, 
						getRootCause(e).getMessage(),
						KianamaResourceBundle.getString("error_title"),
						JOptionPane.ERROR_MESSAGE);
				throw e;
		}
	}
	
	public static void removeHost(String hostSerial) throws Exception{
		try{
			server.removeHost(sessionID,hostSerial);
		}catch(Exception e){
				JOptionPane.showMessageDialog(null, 
						getRootCause(e).getMessage(),
						KianamaResourceBundle.getString("error_title"),
						JOptionPane.ERROR_MESSAGE);
				throw e;
		}
	}
	//===========================Utility Methods================================
	private static void showLoginDialog(){
		//TODO correct this part
		//new LoginDialog().setVisible(true);
	}
	
	private static boolean isConnectonRefuseError(String err){
		if(err.contains("java.rmi.ConnectException: Connection refused to host"))
			return true;
		return false;
	}
	
	private static Throwable getRootCause(Throwable throwable) {
	    if (throwable.getCause() != null)
	        return getRootCause(throwable.getCause());

	    return throwable;
	}
}
