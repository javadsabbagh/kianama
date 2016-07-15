package com.kianama3.console.calendar;

import java.util.Calendar;

import com.ghasemkiani.util.icu.PersianCalendar;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;

public class TestCalendar {
	public static void main(String args[]){
		PersianCalendar calendar;
		TimeZone timeZone = TimeZone.getTimeZone("Asia/Tehran");
		ULocale uLocale = ULocale.createCanonical("fa");
		calendar = new PersianCalendar(timeZone, uLocale);

		try {
			if (calendar.get(Calendar.MONTH)<6)
				calendar.add(Calendar.HOUR, Integer.parseInt("-1"));
		} catch (Exception e) {
		}

		SimpleDateFormat sdf = (SimpleDateFormat) calendar.getDateTimeFormat(2,2, uLocale);//new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
		System.out.println( sdf.format(calendar.getTime()));
	
		System.out.println( String.valueOf(calendar.YEAR )+"/"+
										   format(calendar.MONTH)+"/"+
										   format(calendar.DAY_OF_MONTH));
	}

	public static String format(int n){
			return (n < 10 ? "0"+String.valueOf(n) : String.valueOf(n));
	}
}
