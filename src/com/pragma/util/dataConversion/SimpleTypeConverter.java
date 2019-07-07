package com.pragma.util.dataConversion;


public abstract class SimpleTypeConverter implements TypeConverter {
	
	@SuppressWarnings("unchecked")
	public boolean canConvert(Class srcClass, Class destClass) {
		return
			defaultSourceClass().isAssignableFrom(srcClass) &&
			destClass.isAssignableFrom(defaultDestClass());
	}
	protected abstract Class defaultSourceClass();
	protected abstract Class defaultDestClass();
	
	
}
