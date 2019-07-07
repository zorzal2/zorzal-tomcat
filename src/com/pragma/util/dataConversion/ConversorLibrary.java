package com.pragma.util.dataConversion;

import java.util.Iterator;

public interface ConversorLibrary {

	public Iterator<TypeConverter> getAll();
	public TypeConverter converterFor(Class src, Class dest);
}