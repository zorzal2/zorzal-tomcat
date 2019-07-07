package com.pragma.util.dataConversion.converters;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.pragma.util.dataConversion.SimpleTypeConverter;
import com.pragma.util.dataConversion.ConversionUtils.ConversionException;

public class DateToStringConverter extends SimpleTypeConverter {

	public Object convert(Object object, Class destClass) throws ConversionException {
		return formatDate((Date)object);
	}

	protected Class defaultDestClass() {
		return String.class;
	}

	protected Class defaultSourceClass() {
		return Date.class;
	}
    public static String formatDate(Date date) {
    	return dateFormat.format(date);
    }
    public static SimpleDateFormat dateFormat = new SimpleDateFormat ("dd/MM/yyyy");
}
