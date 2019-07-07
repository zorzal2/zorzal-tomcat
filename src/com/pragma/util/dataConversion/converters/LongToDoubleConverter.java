package com.pragma.util.dataConversion.converters;

import com.pragma.util.dataConversion.SimpleTypeConverter;

public class LongToDoubleConverter extends SimpleTypeConverter {

	@Override
	protected Class defaultSourceClass() {
		return Long.class;
	}

	@Override
	protected Class defaultDestClass() {
		return Double.class;
	}

	public Object convert(Object object, Class destClass) {
		return ((Long)object).doubleValue();
	}

}
