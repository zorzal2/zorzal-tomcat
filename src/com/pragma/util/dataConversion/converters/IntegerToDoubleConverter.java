package com.pragma.util.dataConversion.converters;

import com.pragma.util.dataConversion.SimpleTypeConverter;

public class IntegerToDoubleConverter extends SimpleTypeConverter {

	@Override
	protected Class defaultSourceClass() {
		return Integer.class;
	}

	@Override
	protected Class defaultDestClass() {
		return Double.class;
	}

	public Object convert(Object object, Class destClass) {
		return ((Integer)object).doubleValue();
	}

}
