package com.fontar.util.bean;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.beanutils.Converter;

import com.pragma.util.DateTimeUtil;

public class DateConverter implements Converter {

	public Object convert(Class type, Object value) {

		if (value == null || value.equals(""))
			return null;
		else
			if(value instanceof Date) return value;
			try {
				return DateTimeUtil.getDate((String) value);
			}
			catch (ParseException e) {
				throw new RuntimeException(e);
			}

	}
}
