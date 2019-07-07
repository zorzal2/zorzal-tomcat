package com.pragma.util.dataConversion.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pragma.util.dataConversion.SimpleTypeConverter;
import com.pragma.util.dataConversion.ConversionUtils.ConversionException;

public class StringToDateConverter extends SimpleTypeConverter {

	public Object convert(Object object, Class destClass) throws ConversionException {
		if (object == null || object.equals(""))
			return null;
		else {
			try {
				return parseDate(object.toString());
			} catch(Exception e) {
				throw new ConversionException("Cannot convert String \""+object+"\" to a Date.");
			}
		}
	}

	protected Class defaultDestClass() {
		return Date.class;
	}

	protected Class defaultSourceClass() {
		return String.class;
	}
    public static Date parseDate(String date) throws ParseException {
    	return dateFormat.parse(date);
    }
    public static SimpleDateFormat dateFormat = new SimpleDateFormat ("dd/MM/yyyy");
}
