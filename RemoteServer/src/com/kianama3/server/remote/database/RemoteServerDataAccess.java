package com.kianama3.server.remote.database;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Logger;

import com.kianama3.server.remote.common.ClientData;
import com.kianama3.server.remote.common.DatabaseMonitorData;
import com.kianama3.server.remote.common.DatabaseSessionData;
import com.kianama3.server.remote.common.UserPrivilegesData;
import com.kianama3.server.remote.common.DatabaseSessionData;
import com.kianama3.server.remote.common.UserData;
import com.mchange.v2.c3p0.*;

public class RemoteServerDataAccess {
	//protected final static String HostName = "127.0.0.1:3306";
	protected final static String HostName = "";
	protected final static String UserName = "remote_server";
	protected final static String Password = "j.Po!J$w_d;K*,@tyM>pf3m-XDnRk8";
	protected final static String DatabaseName = "db_kianama2";
	protected static ComboPooledDataSource cpds;

	private final static Logger LOGGER = Logger.getLogger(RemoteServerDataAccess.class.getName());

	public static void setup() throws Exception {
			cpds = new ComboPooledDataSource();
			// 1- load jdbc driver
			cpds.setDriverClass("com.mysql.jdbc.Driver");
			cpds.setJdbcUrl("jdbc:mysql://" + HostName + "/" + DatabaseName);
			cpds.setUser(UserName);
			cpds.setPassword(Password);
			// the settings below are optional -- c3p0 can work with defaults
			cpds.setMinPoolSize(15);
			cpds.setAcquireIncrement(5);
			cpds.setMaxPoolSize(50);
			cpds.setMaxStatementsPerConnection(100); // needed for fetching clients' status
			cpds.setMaxConnectionAge(0);
			//cpds.setAcquireRetryAttempts(5);
			//TODO investigate this parameter, 
			// does it have any effect on database connection setting, error received from db by setting this parameter
			//cpds.setMaxIdleTimeExcessConnections(300);  //it's in milliseconds ?
			cpds.setIdleConnectionTestPeriod(28000);
			cpds.setPreferredTestQuery("SELECT 1");
			cpds.setTestConnectionOnCheckin(false);
			cpds.setTestConnectionOnCheckout(false);
			cpds.setCheckoutTimeout(60000);
			//TODO investigate this parameter
			//cpds.setMaxIdleTime(500);
	}

	public static void cleanup() {
		try {
			DataSources.destroy(cpds);
		} catch (SQLException e) {
			LOGGER.warning("Error in closing cpds : "+e.getMessage());
		}
	}
	@Override
	public void finalize() throws Throwable{
		super.finalize();
		cleanup();
	}

	//=============================== Current Session Methods =================================
	public static void checkUserPassword(String userName, String enteredPassword,Locale locale) 
	throws RemoteException{ 
		try {
			Connection conn = cpds.getConnection();
			String query = "{ CALL check_user_password(?,?) }";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userName);
			stmt.setString(2, enteredPassword);;
			stmt.execute();
		} catch (Exception e) {
			LOGGER.severe("Database error in checkUserPassword: "+userName+e.getMessage());
			if (e.getMessage().contains("DBMS-131"))
				throw new RemoteException(getLocaleBundle(locale).getString("RMT-131"));
			throw new RemoteException(getLocaleBundle(locale).getString("RMT-121"));
		}	
		
	}
	
	public static void changePassword(String userName,String oldPassword,String newPassword,Locale locale,String auditUser)
	throws RemoteException{
		try {
			Connection conn = cpds.getConnection();
			String query = "{ CALL change_password(?,?,?,?) }";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userName);
			stmt.setString(2, oldPassword);
			stmt.setString(3, newPassword);
			stmt.setString(4, auditUser);
			stmt.execute();
		} catch (Exception e) {
			LOGGER.severe("Database error in changing password: "
					+ e.getMessage());
			if (e.getMessage().contains("DBMS-125"))
				throw new RemoteException(getLocaleBundle(locale).getString("RMT-125"));
			if (e.getMessage().contains("DBMS-126"))
				throw new RemoteException(getLocaleBundle(locale).getString("RMT-126"));
			throw new RemoteException(getLocaleBundle(locale).getString("RMT-121"));
		}		
	}
	
	public static void insertSessionLog(String userName,String commandCode,String commandDate,String computerName,String ipAddress,String macAddress,String auditUser)
	throws RemoteException{
		try {
			Connection conn = cpds.getConnection();
			//Note : audit user is Access Server
			String query = "{  CALL insert_session_log(?,?,?,?,?,?,?) }";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userName);
			stmt.setString(2, commandCode);
			stmt.setString(3, commandDate);
			stmt.setString(4, computerName);
			stmt.setString(5, ipAddress);
			stmt.setString(6, macAddress);
			stmt.setString(7, auditUser);
			stmt.execute();
		} catch (Exception e) {
			LOGGER.severe("Database error in inserting session log: "
					+ e.getMessage());
		}		
	}
	
	//================================= Clients Methods ==========================================
	public static Vector<ClientData> getClientsList(Locale locale) 
	throws RemoteException{
		try {
			Connection conn = cpds.getConnection();
			String query = "SELECT client_name,client_desc,client_serial,client_state,disable_date_time" +
					" FROM t_clients "
				  + " where client_state != 0";
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet clrs = stmt.executeQuery();
			Vector<ClientData> v = new Vector<ClientData>();		
			while (clrs.next()){
				ClientData c = new ClientData();
				c.clientName = checkNotNull(clrs.getString("client_name"));
				c.clientDesc = checkNotNull(clrs.getString("client_desc"));
				c.clientSerial = checkNotNull(clrs.getString("client_serial"));
				c.clientState = clrs.getInt("client_state");
				c.disableDateTime = checkNotNull(clrs.getString("disable_date_time"));
				v.add(c);
			}
			return v;
		} catch (Exception e) {
			LOGGER.severe("Unable to fetch Clients List : "
					+ e.getMessage());
			throw new RemoteException(getLocaleBundle(locale).getString("RMT-121"));
		}
	}
	
	public static void addNewClient(ClientData clientInfo,Locale locale,String auditUser)
	throws RemoteException{
		try {
			Connection conn = cpds.getConnection();
			String query = "{ CALL add_new_client(?,?,?,?,?,?,?,?) }";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, clientInfo.clientName);
			stmt.setString(2, clientInfo.clientDesc);
			stmt.setString(3, clientInfo.clientDisplayInfo);
			stmt.setString(4, clientInfo.clientSerial);
			stmt.setString(5, clientInfo.expireDateTime);
			stmt.setString(6, clientInfo.disableDateTime);
			stmt.setInt(7, clientInfo.clientState);
			stmt.setString(8, auditUser);
			stmt.execute();
		} catch (Exception e) {
			LOGGER.severe("Database error in adding new client : "
					+ e.getMessage());
			if (e.getMessage().contains("Duplicate entry")&&
				e.getMessage().contains("name_indx"))
				throw new RemoteException(MessageFormat.format(getLocaleBundle(locale).getString("RMT-122"),
																clientInfo.clientName));
			if (e.getMessage().contains("Duplicate entry") &&
				e.getMessage().contains("serial_indx"))
				throw new RemoteException(MessageFormat.format(getLocaleBundle(locale).getString("RMT-123"),
										 						clientInfo.clientSerial));
			else
				throw new RemoteException(getLocaleBundle(locale).getString("RMT-121"));
		}
	}
	public static void editClient(String clientName,ClientData clientInfo,Locale locale,String auditUser)
	throws RemoteException{
		try {
			Connection conn = cpds.getConnection();
			String query = "{ CALL edit_client(?,?,?,?,?,?,?,?,?) }";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, clientName);
			stmt.setString(2, clientInfo.clientName);
			stmt.setString(3, clientInfo.clientDesc);
			stmt.setString(4, clientInfo.clientDisplayInfo);
			stmt.setString(5, clientInfo.clientSerial);
			stmt.setString(6, clientInfo.expireDateTime);
			stmt.setString(7, clientInfo.disableDateTime);
			stmt.setInt(8, clientInfo.clientState);
			stmt.setString(9, auditUser);
			stmt.execute();
		} catch (Exception e) {
			LOGGER.severe("Database error in editing client : "
					+ e.getMessage());
			if (e.getMessage().contains("Duplicate entry")&&
				e.getMessage().contains("name_indx"))
				throw new RemoteException(MessageFormat.format(getLocaleBundle(locale).getString("RMT-122"),
										clientInfo.clientName));
			if (e.getMessage().contains("Duplicate entry") &&
				e.getMessage().contains("serial_indx"))
				throw new RemoteException(MessageFormat.format(getLocaleBundle(locale).getString("RMT-123"),
										  clientInfo.clientSerial));
			else
				throw new RemoteException(getLocaleBundle(locale).getString("RMT-121"));
		}
	}
	
	public static void deleteClient(String clientName,Locale locale,String auditUser)
	throws RemoteException{
		try {
			Connection conn = cpds.getConnection();
			String query = "{ CALL delete_client(?,?) }";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, clientName);
			stmt.setString(2, auditUser);
			stmt.execute();
		} catch (Exception e) {
			LOGGER.severe("Database error in deleting client : "
					+ e.getMessage());
				throw new RemoteException(getLocaleBundle(locale).getString("RMT-121"));
		}
	}
	//============================ User Methods ======================================== 
	public static UserPrivilegesData getUserPrivileges(String userName,Locale locale) 
	throws RemoteException{
		try {
			Connection conn = cpds.getConnection();
			String query = "SELECT * FROM t_users_access WHERE user_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userName);
			ResultSet prs = stmt.executeQuery();
			
			UserPrivilegesData privilege = new UserPrivilegesData();
			if(prs.next()){
				privilege  = new UserPrivilegesData();
				privilege.manageClients = prs.getBoolean("manage_clients");
				privilege.manageDBMS = prs.getBoolean("manage_dbms");
				privilege.manageServer = prs.getBoolean("manage_server");
				privilege.manageSessions = prs.getBoolean("manage_sessions");
				privilege.manageUsers = prs.getBoolean("manage_users");
				privilege.runReports = prs.getBoolean("run_reports");
				privilege.viewClients = prs.getBoolean("show_clients");
				privilege.viewDBMS = prs.getBoolean("show_dbms_status");
				privilege.viewServer = prs.getBoolean("show_server_status");
				privilege.viewSessions = prs.getBoolean("show_sessions");
				privilege.viewUsers = prs.getBoolean("show_users");
			}
			return privilege;
		} catch (Exception e) {
			LOGGER.severe("Unable to get user privileges : "
					+ e.getMessage());
			throw new RemoteException(getLocaleBundle(locale).getString("RMT-121"));
		}
	}
	
	public static void setUserPrivileges(String userName,UserPrivilegesData priv,Locale locale,String auditUser)
	throws RemoteException{
		try {
			Connection conn = cpds.getConnection();
			//Note : audit user is Access Server
			String query = "{  CALL edit_user_privileges(?,?,?,?,?,?,?,?,?,?,?,?,?) }";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString( 1,   userName);
			stmt.setBoolean(2,  priv.viewUsers );
			stmt.setBoolean(3,  priv.manageUsers );
			stmt.setBoolean(4,  priv.viewSessions );
			stmt.setBoolean(5,  priv.manageSessions );
			stmt.setBoolean(6,  priv.viewServer );
			stmt.setBoolean(7,  priv.manageServer );
			stmt.setBoolean(8,  priv.viewDBMS );
			stmt.setBoolean(9,  priv.manageDBMS );
			stmt.setBoolean(10, priv.viewClients);
			stmt.setBoolean(11, priv.manageClients);
			stmt.setBoolean(12, priv.runReports);
			stmt.setString(13, auditUser);
			stmt.execute();
		} catch (Exception e) {
			LOGGER.severe("Database error in setting user privileges: "
					+ e.getMessage());
			throw new RemoteException(getLocaleBundle(locale).getString("RMT-121"));
		}	
	}
	
	public static Vector<String> getUserHosts(String userName,Locale locale)
	throws RemoteException{
		try {
			Connection conn = cpds.getConnection();
			String query = "SELECT host_serial FROM t_authorized_hosts WHERE user_name = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userName);
			ResultSet hrs = stmt.executeQuery();
			
			Vector<String> v = new Vector<String>();
			if(hrs.next()){
				v.add(hrs.getString("host_serial"));
			}
			return v;
		} catch (Exception e) {
			LOGGER.severe("Unable to get user hosts : "
					+ e.getMessage());
			throw new RemoteException(getLocaleBundle(locale).getString("RMT-121"));
		}
	}
	
	public static void setUserHost(String userName,String hostSerial,Locale locale,String auditUser)
	throws RemoteException{
		try {
			Connection conn = cpds.getConnection();
			//Note : audit user is Access Server
			String query = "{  CALL set_user_host(?,?,?) }";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString( 1,   userName);
			stmt.setString(2,  hostSerial );
			stmt.setString(3, auditUser);
			stmt.execute();
		} catch (Exception e) {
			LOGGER.severe("Database error in setting user host: " + userName +"|"+hostSerial+"|"
					+ e.getMessage());
			if (e.getMessage().contains("DBMS-127"))
				throw new RemoteException(getLocaleBundle(locale).getString("RMT-127"));
			throw new RemoteException(getLocaleBundle(locale).getString("RMT-121"));
		}	
	}
	
	public static void deleteUserHost(String userName,String hostSerial, Locale locale,String auditUser)
	throws RemoteException{
		try {
			Connection conn = cpds.getConnection();
			//Note : audit user is Access Server
			String query = "{  CALL delete_user_host(?,?,?) }";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString( 1,   userName);
			stmt.setString(2,  hostSerial );
			stmt.setString(3, auditUser);
			stmt.execute();
		} catch (Exception e) {
			LOGGER.severe("Database error in deleting user host: " + userName +"|"+hostSerial+"|"
					+ e.getMessage());
			throw new RemoteException(getLocaleBundle(locale).getString("RMT-121"));
		}	
	}
	
	public static Vector<UserData> getUsersList(Locale locale)
	throws RemoteException{
			try {
				Connection conn = cpds.getConnection();
				String query = "SELECT user_name, full_name, " +
						" expire_date_time, disable_date_time, user_state," +
						" change_pass_next_login, create_date, create_by_user," +
						" update_date, update_by_user " +
						" FROM t_users " +
						" WHERE user_state != 0";
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet clrs = stmt.executeQuery();
				Vector<UserData> list = new Vector<UserData>();
				while (clrs.next()){
					UserData u = new UserData();
					
					u.userName = clrs.getString("user_name");
					u.fullName = clrs.getString("full_name");
					u.expireDateTime = clrs.getString("expire_date_time");
					u.disableDateTime = clrs.getString("disable_date_time");
					u.userState = clrs.getInt("user_state");
					u.changePassNextLogin = clrs.getBoolean("change_pass_next_login");
					u.createDate = clrs.getDate("create_date");
					u.createByUser = clrs.getString("create_by_user");
					u.updateDate = clrs.getDate("update_date");
					u.updateByUser = clrs.getString("update_by_user");
					list.add(u);
				}
				return list;
			} catch (Exception e) {
				LOGGER.severe("Unable to fetch Clients List : "
						+ e.getMessage());
				throw new RemoteException(getLocaleBundle(locale).getString("RMT-121"));
			}
	}
	
	//
	public static void addNewUser(UserData userInfo,Locale locale,String auditUser)
	throws RemoteException{
		try {
			Connection conn = cpds.getConnection();
			String query = "{ CALL add_new_user(?,?,?,?,?,?,?,?) }";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userInfo.userName);
			stmt.setString(2, userInfo.fullName);
			stmt.setString(3, userInfo.password);
			stmt.setString(4, userInfo.expireDateTime);
			stmt.setString(5, userInfo.disableDateTime);
			stmt.setInt(6, userInfo.userState);
			stmt.setBoolean(7, userInfo.changePassNextLogin);
			stmt.setString(8, auditUser);
			stmt.execute();
		} catch (Exception e) {
			LOGGER.severe("Database error in adding new user : "
					+ e.getMessage());
			if (e.getMessage().contains("Duplicate entry")&&
					e.getMessage().contains("PRIMARY"))				
				throw new RemoteException(MessageFormat.format(getLocaleBundle(locale).getString("RMT-124"), 
											userInfo.userName));
			else
				throw new RemoteException(getLocaleBundle(locale).getString("RMT-121"));
		}		
	}
	
	public static void editUser(UserData userInfo,Locale locale,String auditUser)
	throws RemoteException{
		try {
			Connection conn = cpds.getConnection();
			String query = "{ CALL edit_user(?,?,?,?,?,?,?) }";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userInfo.userName);
			stmt.setString(2, userInfo.fullName);
			//stmt.setString(3, userInfo.password);
			stmt.setString(3, userInfo.expireDateTime);
			stmt.setString(4, userInfo.disableDateTime);
			stmt.setInt(5, userInfo.userState);
			stmt.setBoolean(6, userInfo.changePassNextLogin);
			stmt.setString(7, auditUser);
			stmt.execute();
		} catch (Exception e) {
			LOGGER.severe("Database error in editing user : "
					+ e.getMessage());
			throw new RemoteException(getLocaleBundle(locale).getString("RMT-121"));
		}		
	}
	
	public static void changeUserPassword(String userName,String userPassword,Locale locale,String auditUser)
	throws RemoteException{
		try {
			Connection conn = cpds.getConnection();
			String query = "{ CALL change_user_password(?,?,?) }";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userName);
			stmt.setString(2, userPassword);
			stmt.setString(3, auditUser);
			stmt.execute();
		} catch (Exception e) {
			LOGGER.severe("Database error in changing user password: "
					+ e.getMessage());
			if (e.getMessage().contains("DBMS-126"))
				throw new RemoteException(getLocaleBundle(locale).getString("RMT-126"));
			throw new RemoteException(getLocaleBundle(locale).getString("RMT-121"));
		}		
	}
	
	
	public static void deleteUser(String userName,Locale locale,String auditUser)
	throws RemoteException{
		try {
			Connection conn = cpds.getConnection();
			String query = "{ CALL delete_user(?,?) }";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userName);
			stmt.setString(2, auditUser);
			stmt.execute();
		} catch (Exception e) {
			LOGGER.severe("Database error in deleting user : "
					+ e.getMessage());
			throw new RemoteException(getLocaleBundle(locale).getString("RMT-121"));
		}		
	}
	
	//=========================================User Sessions Methods ================================

	//=========================================Database Methods======================================
	public static Vector<DatabaseSessionData> getDatabaseSessionsList(Locale locale)
	throws RemoteException{
			try {
				Connection conn = cpds.getConnection();
				String query = "SELECT * FROM information_schema.processlist";
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet clrs = stmt.executeQuery();
				Vector<DatabaseSessionData> list = new Vector<DatabaseSessionData>();
				while (clrs.next()){
					DatabaseSessionData dbSession = new DatabaseSessionData();
					
					dbSession.sessionID = clrs.getInt("id");
					dbSession.userName = clrs.getString("user");
					dbSession.hostName = clrs.getString("host");
					dbSession.dbName = clrs.getString("db");
					dbSession.command = clrs.getString("command");
					dbSession.time = clrs.getInt("time");
					dbSession.sessionState = clrs.getString("state");
					dbSession.sessionInfo = clrs.getString("info");
		
					list.add(dbSession);
				}
				return list;
			} catch (Exception e) {
				LOGGER.severe("Unable to fetch database sessions List : "
						+ e.getMessage());
				throw new RemoteException(getLocaleBundle(locale).getString("RMT-121"));
			}
	}
	
	public static DatabaseMonitorData getDatabaseMonitorInfo(Locale locale)
	throws RemoteException{
		try {
			Connection conn = cpds.getConnection();
			String query = "SELECT "+
							"	VARIABLE_NAME,"+
							"	VARIABLE_VALUE " +
							"FROM " +
							"	INFORMATION_SCHEMA.GLOBAL_STATUS";
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet drs = stmt.executeQuery();
			DatabaseMonitorData m = new DatabaseMonitorData();
			if (drs.next()){
				
				m.upTime = drs.getLong("UPTIME");
				m.bytesSent = drs.getLong("BYTES_SENT");
				m.bytesRecieved = drs.getLong("BYTES_RECEIVED");
				//TODO correct this part
				m.version = drs.getString("5.5.8");
				m.threadsCreated = drs.getInt("THREADS_CREATED");
				m.threadsRunning = drs.getInt("THREADS_RUNNING");
	
			}
			return m;
		} catch (Exception e) {
			LOGGER.severe("Unable to fetch database sessions List : "
					+ e.getMessage());
			throw new RemoteException(getLocaleBundle(locale).getString("RMT-121"));
		}
	}
	//======================================  Hosts Section ==========================================
	public static Vector<String> getHostsList(Locale locale) throws RemoteException{
		try {
			Connection conn = cpds.getConnection();
			String query = "SELECT host_serial FROM t_authorized_hosts";
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet hrs = stmt.executeQuery();
			
			Vector<String> v = new Vector<String>();						
			while (hrs.next()){
				//TODO we need to send host changes audit columns
				v.add(hrs.getString("host_serial"));
			}
			return v;
		} catch (Exception e) {
			LOGGER.severe("Unable to fetch hosts List : "
					+ e.getMessage());
			throw new RemoteException(getLocaleBundle(locale).getString("RMT-121"));
		}
	}
	
	public static void addNewHost(String hostSerial,Locale locale,String auditUser) throws RemoteException{
		try {
			Connection conn = cpds.getConnection();
			String query = "{ CALL add_new_host(?,?) }";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, hostSerial);
			stmt.setString(2, auditUser);
			stmt.execute();
		} catch (Exception e) {
			if (e.getMessage().contains("Duplicate entry"))
				throw new RemoteException(
						MessageFormat.format(
								getLocaleBundle(locale).getString("RMT-128"),
								hostSerial));
			
			LOGGER.severe("Database error in adding new host : "+hostSerial +"\r\n"
					+ e.getMessage());
			throw new RemoteException(getLocaleBundle(locale).getString("RMT-121"));
		}
	}
	
	
	public static void removeHost(String hostSerial,Locale locale,String auditUser) throws RemoteException{
		try {
			Connection conn = cpds.getConnection();
			String query = "{ CALL delete_host(?,?) }";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, hostSerial);
			stmt.setString(2, auditUser);
			stmt.execute();
		} catch (Exception e) {
			LOGGER.severe("Database error in removing host : "
					+ hostSerial + "\r\n" + e.getMessage());
			throw new RemoteException(getLocaleBundle(locale).getString("RMT-121"));
		}
	}
	//======================================= Utility Methods ========================================
	private static String checkNotNull(String p){
		return p != null ? p : new String(""); 
	}
	
	private static ResourceBundle getLocaleBundle(Locale locale) {
		return ResourceBundle.getBundle("com.kianama3.server.remote.Error_Codes", locale);
	}
}
