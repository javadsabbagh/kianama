package com.kianama3.server.logging;


import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KianamaRemoteServerLogger {
	static private FileHandler fileTxt;
	//static private SimpleFormatter formatterTxt;
	static private TextFormatter formatterTxt;

	static public void setup() throws IOException {
		Logger logger = Logger.getLogger("com.kianama3.server"); 
		logger.setLevel(Level.ALL);
		
		fileTxt = new FileHandler("Remote_Server_Log.txt",true); //open in append mode
		//fileHTML = new FileHandler("Remote_Server_Log.html");

		// Create text Formatter
		formatterTxt = new TextFormatter();
		fileTxt.setFormatter(formatterTxt);
		fileTxt.setEncoding("UTF8");
		logger.addHandler(fileTxt);

		// Create HTML Formatter
		//formatterHTML = new HtmlFormatter();
		//fileHTML.setFormatter(formatterHTML);
		//fileHTML.setEncoding("UTF8");
		//logger.addHandler(fileHTML);

		logger.removeHandler(new ConsoleHandler());
	}
}
