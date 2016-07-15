package com.kianama3.server.logging;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import com.ghasemkiani.util.icu.PersianCalendar;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.text.SimpleDateFormat;

class TextFormatter extends Formatter {
	public String format(LogRecord rec) {
		StringBuffer buf = new StringBuffer(1000);
		// Bold any levels >= WARNING
		buf.append("\r\n"+rec.getLevel()+"\t");  //Level
		buf.append(calcDate()+"\t\t");  		 //Date 
		buf.append(rec.getLoggerName()+"\t");	 //Name
		buf.append(formatMessage(rec));			 //Message
		return buf.toString();
	}

	private String calcDate() {
		Calendar calendar;
		TimeZone timeZone = TimeZone.getTimeZone("Asia/Tehran");
		ULocale uLocale = ULocale.createCanonical("fa");
		calendar = new PersianCalendar(timeZone, uLocale);

		try {
			if (calendar.get(Calendar.MONTH) < 6)
				calendar.add(Calendar.HOUR, Integer.parseInt("-1"));
		} catch (Exception e) {
		}

		// get date time in full format
		SimpleDateFormat sdf = 
			(SimpleDateFormat) calendar.getDateTimeFormat(0,0, uLocale); 
		return sdf.format(calendar.getTime());
	}

	public String getHead(Handler h) {
		return "\r\n"
				+ calcDate()
				+ "\r\nLevel\tDate\t\t\tName\t\t\t\t\tLog Massage\r\n"
				+ "------------------------------------------------------------------------------------------------";
	}

	public String getTail(Handler h) {
		return "\r\n";
	}
}