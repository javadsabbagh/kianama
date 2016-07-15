package com.kianama3.server.remote.common;

import java.io.Serializable;
import java.util.Date;

public class ClientData extends Auditable implements Serializable{
	private static final long serialVersionUID = 1L;
	
	 public int 	clientId;
	 public String 	clientName;
	 public String 	clientDesc = null;
	 public String 	clientDisplayInfo = null;
	 public String 	clientSerial;
	 public String 	expireDateTime = null;
	 public String 	disableDateTime = null;
	 public int 	clientState;
}
