package com.pragma.util.dataConversion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.pragma.util.dataConversion.converters.BigDecimalToStringConverter;
import com.pragma.util.dataConversion.converters.DateToStringConverter;
import com.pragma.util.dataConversion.converters.DoubleToIntegerConverter;
import com.pragma.util.dataConversion.converters.DoubleToLongConverter;
import com.pragma.util.dataConversion.converters.DoubleToStringConverter;
import com.pragma.util.dataConversion.converters.IntegerToDoubleConverter;
import com.pragma.util.dataConversion.converters.LongToDoubleConverter;
import com.pragma.util.dataConversion.converters.ObjectToStringConverter;
import com.pragma.util.dataConversion.converters.StringAndIntegerConverter;
import com.pragma.util.dataConversion.converters.StringAndLongConverter;
import com.pragma.util.dataConversion.converters.StringToBigDecimalConverter;
import com.pragma.util.dataConversion.converters.StringToDateConverter;

/**
 * Mantiene la lista de conversores disponibles. Mantener actualizado.
 * @author llobeto
 *
 */
public class DefaultConversorLibrary implements ConversorLibrary {
	private static class InstanceHolder {
		public static ConversorLibrary instance = new DefaultConversorLibrary();
	}
	
	protected void populate() {
		//TODO Completar
		add(new BigDecimalToStringConverter());
		add(new StringToBigDecimalConverter());
		add(new StringAndLongConverter());
		add(new StringAndIntegerConverter());
		add(new LongToDoubleConverter());
		add(new DoubleToLongConverter());
		add(new IntegerToDoubleConverter());
		add(new DoubleToIntegerConverter());
		add(new StringToDateConverter());
		add(new DateToStringConverter());
		add(new DoubleToStringConverter());
		add(new ObjectToStringConverter());
	}

	private List<TypeConverter>  all;
	private DefaultConversorLibrary() {
		all = new ArrayList<TypeConverter>();
		populate();
	}
	
	public static ConversorLibrary instance() {
		return InstanceHolder.instance;
	}

    /* (non-Javadoc)
	 * @see com.pragma.util.dataConversion.ConversorLibrary#getAll()
	 */
    public Iterator<TypeConverter> getAll() {
    	return all.iterator();
    }
    public void add(TypeConverter o) {
    	all.add(o);
    }
	public TypeConverter converterFor(Class src, Class dest) {
		for (TypeConverter converter : all) {
			if(converter.canConvert(src, dest))return converter;	
		}
		return null;
	}
}
