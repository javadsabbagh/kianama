package com.kianama3.server.remote.common;

import java.io.Serializable;

public class UserData extends Auditable implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public String  userName;
	public String  fullName;
	public String  expireDateTime;
	public String  disableDateTime;
	public int     userState;
	public boolean changePassNextLogin;
	public String  password; 
}
