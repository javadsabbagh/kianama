package com.kianama3.server.remote.common;

import java.io.Serializable;
import java.util.Date;

public class Auditable implements Serializable{

	private static final long serialVersionUID = -3332023811519357304L;

	public Date  createDate;
	public String  createByUser;
	public Date  updateDate;
	public String  updateByUser;
}
