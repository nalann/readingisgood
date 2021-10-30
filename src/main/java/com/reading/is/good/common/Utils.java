package com.reading.is.good.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.reading.is.good.exception.DateParseException;

public class Utils {
	
	public static long convertStrToDateLong(String dateStr) {
	    try {
			Date date = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse(dateStr);
			return date.getTime();
		} catch (ParseException e) {
			throw new DateParseException("Date format is wrong. dd-MM-yyyy hh:mm:ss");
		}  
	}
	
	public static String convertLongToMonth(long dateLong) {
		Date date = new Date(dateLong);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return new SimpleDateFormat("MMM").format(calendar.getTime()).toString();
	}
	
}
