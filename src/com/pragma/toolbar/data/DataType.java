package com.pragma.toolbar.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public enum DataType implements Serializable {
	STRING("String",String.class),
	DATE("Date", Date.class),
	NUMBER("Number", BigDecimal.class),
	LONG("Long", Long.class),
	BOOLEAN("Boolean", Boolean.class),
    OBJECT("Object", Object.class);
	
	private static final long serialVersionUID = 1L;
	
	private final String name;
	private Class<?> javaClass;
	
	private DataType(String name, Class<?> javaClass) {
		this.name = name;
		this.javaClass = javaClass;
	}

	public static DataType forName(String name) {
		if (name == null) return null;
		for(DataType dataType : DataType.values()) {
			if(dataType.getName().equals(name)) return dataType;
		}
		return OBJECT;
	}
	
	public Class<?> toJavaClass() {
		return this.javaClass;
	}
	
	
	public String getName() {
		return name;
	}
	
	public static DataType fromJavaClass(Class javaClass) {
		for(DataType dataType : DataType.values()) {
			if(dataType.toJavaClass().equals(javaClass)) return dataType;
		}
		return OBJECT;
	}
}