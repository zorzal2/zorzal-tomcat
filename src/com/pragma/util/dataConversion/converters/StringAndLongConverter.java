package com.pragma.util.dataConversion.converters;

import com.pragma.util.dataConversion.TypeConverter;
import com.pragma.util.dataConversion.ConversionUtils.ConversionException;

public class StringAndLongConverter implements TypeConverter {

	public boolean canConvert(Class srcClass, Class destClass) {
		return 
			(srcClass.equals(Long.class) && destClass.equals(String.class)) || 
			(destClass.equals(Long.class) && srcClass.equals(String.class));
	}

	public Object convert(Object object, Class destClass) throws ConversionException {
		if(destClass.equals(String.class)) return object.toString();

		try {
			if(destClass.equals(Long.class)) return Long.valueOf(object.toString());
		} catch(Exception e) {
			throw new ConversionException("Cannot convert \""+object+"\" to a Long number");
		}
		
		throw new ConversionException("Cannot convert object of class "+object.getClass().getName());
	}

}
