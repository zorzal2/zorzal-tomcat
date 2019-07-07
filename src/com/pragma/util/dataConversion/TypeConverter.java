package com.pragma.util.dataConversion;

import com.pragma.util.dataConversion.ConversionUtils.ConversionException;


public interface TypeConverter {
	
	public abstract boolean canConvert(Class srcClass, Class destClass);
	public abstract Object convert(Object object, Class destClass) throws ConversionException;
}