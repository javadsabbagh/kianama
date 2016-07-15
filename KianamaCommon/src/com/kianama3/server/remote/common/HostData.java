package com.kianama3.server.remote.common;

import java.io.Serializable;

public class HostData extends Auditable implements Serializable{
	private static final long serialVersionUID = 2845732227520580812L;
	
	public String hostSerial;
	public String hostDesc;
}
