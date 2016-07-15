package com.kianama3.console.common;

import java.net.URL;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

//TODO it's better to log resource not found exception 
public class KianamaResourceBundle {
	static public String getString(String key){
		return ResourceBundle.getBundle("com.kianama3.console.common.Management_Console").getString(key);
	}
	
	static public URL getResource(String iconName){
		return KianamaResourceBundle.class.getResource("/com/kianama3/console/resources/"+iconName);
	}
	
	static public String getSetting(String key){
			return ResourceBundle.getBundle("com.kianama3.console.common.settings").getString(key);
	}
	
	static public String getScript(String scriptName){
		return ResourceBundle.getBundle("com.kianama3.console.common.scripts").getString(scriptName);
	}
}
