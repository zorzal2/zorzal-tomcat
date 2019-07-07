package com.pragma.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * El objetivo de la clase es administrar el manejo de Fecha y hora
 * 
 * @author Pragma Consultores
 * @version $Revision: 1.1 $
 */
public class DateTimeUtil {
	// ~ Static fields/initializers
	// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

	public static final String DATE_FORMAT = "dd/MM/yyyy";

	// ~ Methods
	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Le da a una fecha el formato correcto usado
	 * 
	 * @param fecha sin formatear
	 * 
	 * @return fecha formateada
	 */
	public static String formatDate(Date fecha) {
		String dateFormated = "";
		if (fecha != null) {
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			dateFormated = df.format(fecha);
		}
		return dateFormated;
	}

	/**
	 * Le da a una fecha y hora el formato correcto usado
	 * 
	 * @param fecha información correspondiente a fecha y hora
	 * 
	 * @return Fecha y hora formateada
	 */
	public static String formatDateTime(Date fecha) {
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat(DATETIME_FORMAT);
		sb.append(formatter.format(fecha));

		return sb.toString();
	}

	/**
	 * Convierte un string a una fecha
	 * 
	 * @param fecha string de fecha sin formatear
	 * 
	 * @return fecha formateada con el tipo fecha
	 * 
	 * @throws ParseException Excepcion de tipo invalido
	 */
	public static Date getDate(String fecha) throws ParseException {
		if (StringUtil.isEmpty(fecha)) {
			return null;
		}
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);

		return df.parse(fecha);
	}

	/**
	 * Obtiene la fecha actual
	 * 
	 * @return Fecha actual en el formato correcto
	 * 
	 * @throws ParseException Excepcion de tipo invalido
	 */
	public static Date getDate() {
		// return
		// getDate(formatDate(GregorianCalendar.getInstance().getTime()));
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();

	}

	/**
	 * Obtiene la fecha actual, en formato String y formateada
	 * 
	 * @return Fecha actual en el formato correcto
	 * 
	 * @throws ParseException Excepcion de tipo invalido
	 * 
	 * @author ssanchez
	 */
	public static String getStringFormatDate() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MILLISECOND, 0);
		return formatDate(c.getTime());
	}
	
	/**
	 * Obtiene la fecha y hora actual, en formato String y formateada
	 * 
	 * @return Fecha y hora actual en el formato correcto
	 * 
	 * @throws ParseException Excepcion de tipo invalido
	 * 
	 * @author mberra
	 */
	public static String getStringFormatDateTime() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MILLISECOND, 0);
		return formatDateTime(c.getTime());
	}

	public static String getDateAsString() {
		return formatDate(new Date());
	}


	/**
	 * Devuelve un <code>Calendar</code> que se crea a partir de 
	 * un objeto de tipo <code>Date</code> que está deprecada
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar getCalendar(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar;
	}

}




