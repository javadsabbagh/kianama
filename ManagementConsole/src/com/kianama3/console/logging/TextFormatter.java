package com.kianama3.console.logging;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import com.ghasemkiani.util.icu.PersianCalendar;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

//This custom formatter formats parts of a log record to a single line
class TextFormatter extends Formatter {
	protected static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	// This method is called for every log records
	public String format(LogRecord rec) {

		StringBuffer buf = new StringBuffer(1000);
		// Bold any levels >= WARNING

		// Level
		buf.append("\r\n" + rec.getLevel() + "\t");

		// Date
		buf.append(calcDate() + "\t\t");

		// /Name
		buf.append(rec.getLoggerName() + "\t");

		// Message
		buf.append(formatMessage(rec));
		return buf.toString();
	}

	private String calcDate(/* long millisecs */) {
		/*
		 * SimpleDateFormat date_format = new
		 * SimpleDateFormat("MMM dd,yyyy HH:mm"); Date resultdate = new
		 * Date(millisecs); return date_format.format(resultdate);
		 */

		/*
		 * PersianCalendar pCal = new PersianCalendar(); String str =
		 * pCal.getPersianLongDate(); str += " " +
		 * dateFormat.format(pCal.getTime());
		 */

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
		SimpleDateFormat sdf = (SimpleDateFormat) calendar.getDateTimeFormat(0,
				0, uLocale); /* new SimpleDateFormat("yyyy-MM-dd HH:mm"); */
		return sdf.format(calendar.getTime());
	}

	// This method is called just after the handler using this
	// formatter is created
	public String getHead(Handler h) {
		return "\r\n"
				+ calcDate()
				+ "\r\nLevel\tDate\t\t\tName\t\t\t\t\tLog Massage\r\n"
				+ "------------------------------------------------------------------------------------------------";
	}

	// This method is called just after the handler using this
	// formatter is closed
	public String getTail(Handler h) {
		// Do nothing
		return "\r\n";
	}
}