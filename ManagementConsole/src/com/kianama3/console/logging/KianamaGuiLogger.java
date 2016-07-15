package com.kianama3.console.logging;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class KianamaGuiLogger {
	static private FileHandler fileTxt;
	static private TextFormatter formatterTxt;
	
	static public void setup() throws IOException {
		// Create Logger : use for com.kianama3.server package and all its sub-package this logger
		Logger logger = Logger.getLogger("com.kianama3.server.gui"); 
		logger.setLevel(Level.ALL);
		
		fileTxt = new FileHandler("ManagementConsole_Log.txt",true); //open in append mode

		formatterTxt = new TextFormatter();
		fileTxt.setFormatter(formatterTxt);
		fileTxt.setEncoding("UTF8");
		logger.addHandler(fileTxt);

		logger.removeHandler(new ConsoleHandler());
	}
}