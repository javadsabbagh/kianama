package com.kianama3.server.logging;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.ghasemkiani.util.icu.PersianCalendar;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

class HtmlFormatter extends Formatter {
	protected static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	// This method is called for every log records
	public String format(LogRecord rec) {

		StringBuffer buf = new StringBuffer(1000);
		// Bold any levels >= WARNING
		buf.append("<tr>");
		
		buf.append("<td>");		//Level
		if (rec.getLevel().intValue() >= Level.WARNING.intValue()) {
			buf.append("<b>");
			buf.append(rec.getLevel());
			buf.append("</b>");
		} else {
			buf.append(rec.getLevel());
		}
		buf.append("</td>");
		 
		buf.append("<td>");		//Date
		buf.append(calcDate());
		buf.append("</td>");
		
		buf.append("<td>");		//Name
		buf.append(rec.getLoggerName());
		buf.append("</td>");
		
		buf.append("<td>");		//Message
		buf.append(formatMessage(rec));
		
		buf.append("</td>");	//buf.append('\n');
		
		buf.append("</tr>\n");
		return buf.toString();
	}

	private String calcDate() {
		Calendar calendar;
		TimeZone timeZone = TimeZone.getTimeZone("Asia/Tehran");
		ULocale uLocale = ULocale.createCanonical("fa");
		calendar = new PersianCalendar(timeZone, uLocale);

		try {
			if (calendar.get(Calendar.MONTH)<6)
				calendar.add(Calendar.HOUR, Integer.parseInt("-1"));
		} catch (Exception e) {
		}

		SimpleDateFormat sdf = (SimpleDateFormat) calendar.getDateTimeFormat(0,0, uLocale); 
		return sdf.format(calendar.getTime());
	}

	public String getHead(Handler h) {
		return "<HTML>\n<HEAD>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n"
				//+ (new Date())
				+calcDate()
				+ "\n</HEAD>\n<BODY>\n<PRE>\n"
				+ "<table border>\n  "
				+ "<tr><th>Level</th><th>Date</th><th>Name</th><th>Log Message</th></tr>\n";
	}

	public String getTail(Handler h) {
		return "</table>\n  </PRE></BODY>\n</HTML>\n";
	}
}