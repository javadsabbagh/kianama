package com.kianama3.console.logging;

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

//This custom formatter formats parts of a log record to a single line
class HtmlFormatter extends Formatter {
	protected static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	// This method is called for every log records
	public String format(LogRecord rec) {

		StringBuffer buf = new StringBuffer(1000);
		// Bold any levels >= WARNING
		buf.append("<tr>");
		
		//Level
		buf.append("<td>");
		if (rec.getLevel().intValue() >= Level.WARNING.intValue()) {
			buf.append("<b>");
			buf.append(rec.getLevel());
			buf.append("</b>");
		} else {
			buf.append(rec.getLevel());
		}
		buf.append("</td>");
		
		//Date 
		buf.append("<td>");
		// buf.append(calcDate(rec.getMillis()));
		buf.append(calcDate());
		buf.append("</td>");
		
		///Name
		buf.append("<td>");
		buf.append(rec.getLoggerName());
		buf.append("</td>");
		
		//Message
		buf.append("<td>");
		buf.append(formatMessage(rec));
		//buf.append('\n');
		buf.append("</td>");
		
		buf.append("</tr>\n");
		return buf.toString();
	}

	private String calcDate(/* long millisecs */) {
		/*
		 * SimpleDateFormat date_format = new
		 * SimpleDateFormat("MMM dd,yyyy HH:mm"); Date resultdate = new
		 * Date(millisecs); return date_format.format(resultdate);
		 */
/*		PersianCalendar pCal = new PersianCalendar();
		String str = pCal.getPersianLongDate();

		str += " " + dateFormat.format(pCal.getTime());*/
		
		Calendar calendar;
		TimeZone timeZone = TimeZone.getTimeZone("Asia/Tehran");
		ULocale uLocale = ULocale.createCanonical("fa");
		calendar = new PersianCalendar(timeZone, uLocale);

		try {
			if (calendar.get(Calendar.MONTH)<6)
				calendar.add(Calendar.HOUR, Integer.parseInt("-1"));
		} catch (Exception e) {
		}

		//get date time in full format
		SimpleDateFormat sdf = (SimpleDateFormat) calendar.getDateTimeFormat(0,0, uLocale); /*new SimpleDateFormat("yyyy-MM-dd HH:mm");*/
		return sdf.format(calendar.getTime());
	}

	// This method is called just after the handler using this
	// formatter is created
	public String getHead(Handler h) {
		return "<HTML>\n<HEAD>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n"
				//+ (new Date())
				+calcDate()
				+ "\n</HEAD>\n<BODY>\n<PRE>\n"
				+ "<table border>\n  "
				+ "<tr><th>Level</th><th>Date</th><th>Name</th><th>Log Message</th></tr>\n";
	}

	// This method is called just after the handler using this
	// formatter is closed
	public String getTail(Handler h) {
		return "</table>\n  </PRE></BODY>\n</HTML>\n";
	}
}