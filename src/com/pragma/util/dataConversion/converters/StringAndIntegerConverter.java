package com.pragma.util.dataConversion.converters;

import com.pragma.util.dataConversion.TypeConverter;
import com.pragma.util.dataConversion.ConversionUtils.ConversionException;

public class StringAndIntegerConverter implements TypeConverter {

	public boolean canConvert(Class srcClass, Class destClass) {
		return 
			(srcClass.equals(Integer.class) && destClass.equals(String.class)) || 
			(destClass.equals(Integer.class) && srcClass.equals(String.class));
	}

	public Object convert(Object object, Class destClass) throws ConversionException {
		if(destClass.equals(String.class)) return object.toString();

		try {
			if(destClass.equals(Integer.class)) return object.equals("")? null : Integer.valueOf(object.toString());
		} catch(Exception e) {
			throw new ConversionException("Cannot convert \""+object+"\" to an Integer number");
		}
		
		throw new ConversionException("Cannot convert object of class "+object.getClass().getName());
	}

}
