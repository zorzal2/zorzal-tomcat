package com.pragma.toolbar.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
    /**
     * Formatea una fecha
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
    	return dateFormat.format(date);
    }
    /**
     * Parsea una fecha.
     * @param date
     * @return
     * @throws ParseException 
     */
    public static Date parseDate(String date) throws ParseException {
    	return dateFormat.parse(date);
    }
    public static SimpleDateFormat dateFormat = new SimpleDateFormat ("dd/MM/yyyy");
    
    /**
     * Formatea el momento del dia extraido de una fecha
     * @param date
     * @return
     */
    public static String formatTime(Date date) {
    	return timeFormat.format(date);
    }
    public static SimpleDateFormat timeFormat = new SimpleDateFormat ("HH:mm");
    
    /**
     * Formatea fecha y hora de un objeto Date
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
    	return dateTimeFormat.format(date);
    }
    
    public static String formatFullDateTime(Date date) {
    	return fullDateTimeFormat.format(date);
    }
    
    public static SimpleDateFormat dateTimeFormat = new SimpleDateFormat ("dd/MM/yyyy - HH:mm");
    public static SimpleDateFormat fullDateTimeFormat = new SimpleDateFormat ("dd/MM/yyyy - HH:mm:ss");
    public static String dateTimeOracleFormat = "dd/mm/yyyy - hh24:mi:ss";
    
    /**
     * Parsea una fecha y una hora devolviendo el Date que combina ambos.
     * @param date
     * @param time
     * @return
     * @throws ParseException 
     */
    public static Date parseDateTime(String date, String time) throws ParseException {
	if(time.length()=="HH:mm".length()) {
	   //Agrego los segundos
	    time+=":00";
	}
    	return fullDateTimeFormat.parse(date+" - "+time);
    }

    /**
     * Trunca un objeto Date de forma que el momento del dia sea 00:00:00
     * Esto es necesario si se quieren comparar dos fechas por igualdad!!!
     */
    public static Date truncDate(Date date){
    	Calendar calendar = new GregorianCalendar();
    	calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dte = calendar.get(Calendar.DATE);
        calendar.clear();
        calendar.set(year, month, dte);
        return calendar.getTime();
    }
    
    public static Date addDays(Date date, long days) {
    	long dayMilliseconds = days * 24l * 60l * 60l * 1000l;
    	return new Date(date.getTime() + dayMilliseconds);
    }
    
    public static boolean periodsOverlap(Date from1, Date to1, Date from2, Date to2) {
	
	boolean dontOverlap = false;
	if(from1.before(from2)) {
	    dontOverlap = to1.before(from2);
	} else if(from2.before(from1)) {
	    dontOverlap = to2.before(from1);
	}
	return !dontOverlap;
    }
    
    public static Date getNowGMT() {
		Date nowDate = Calendar.getInstance().getTime();
		Date date = new Date(nowDate.getTime() - Calendar.getInstance().get(Calendar.ZONE_OFFSET));
		return date;
    }
}
