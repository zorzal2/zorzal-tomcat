/*
 * Created on November 14, 2006, 1:13 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pragma.toolbar.data.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pragma.util.interfaces.Bean;

/**
 * @author llobeto
 */
public class VariantBean implements Bean {
    
    private static final long serialVersionUID = 1L;

    private Long id = null;
    private String internalValue = null;
    private String valueType = "null";
    
    /* Publicos */
    /**
     * Devuelve la clase a la que pertenece el valor que almacena.
     * Puede ser:
     * 		Integer
     * 		Boolean
     * 		Date
     * 		String
     * 		Long
     * 		Double
     * 		null	(si no está definido aún)
     * 
     * Proximamente Beans
     */
    public Class getType() {
	if(getValueType().equals("null")) return null;
	try {
	    return Class.forName(getValueType());
	} catch(Exception e) {
	    //No deberia ocurrir
	    throw new RuntimeException();
	}
    }
    public String getTypeName() {
	return String.valueOf(getValueType());
    }

    public Double doubleValue() {
	VariantStrategy strategy = (VariantStrategy)Strategies.get("java.lang.Double");
	return (Double)strategy.getValue(this);
    }
    public Boolean booleanValue() {
	VariantStrategy strategy = (VariantStrategy)Strategies.get("java.lang.Boolean");
	return (Boolean)strategy.getValue(this);
    }
    public String stringValue() {
	VariantStrategy strategy = (VariantStrategy)Strategies.get("java.lang.String");
	return (String)strategy.getValue(this);
    }
    public Long longValue() {
	VariantStrategy strategy = (VariantStrategy)Strategies.get("java.lang.Long");
	return (Long)strategy.getValue(this);
    }
    public void setValue(Object val) {
	String valClassName = (val==null)? 
				String.valueOf(null) :
				val.getClass().getName();
	
	VariantStrategy strategy = (VariantStrategy)Strategies.get(valClassName);
	if(strategy==null) throw new RuntimeException("Not implemented");
	strategy.setValue(this, val);
    }
    public Object getValue() {
	VariantStrategy strategy = (VariantStrategy)Strategies.get(getTypeName());
	return strategy.getValue(this);
    }
    
    /* Para uso de Hibernate */
    public Long getId() {
	return id;
    }
    public void setId(Long id) {
	this.id = id;
    }
    public String getInternalValue() {
	return internalValue;
    }
    public void setInternalValue(String value) {
        internalValue=value;
    }
    public String getValueType() {
        return valueType;
    }
    public void setValueType(String valueType) {
        this.valueType = valueType;
    }


    /* Auxiliar */
    private static interface VariantStrategy {
	public void setValue(VariantBean self, Object val);
	public Object getValue(VariantBean self);
    }
    
    static class BigDecimalStrategy implements VariantStrategy{
	public void setValue(VariantBean self, Object val) {
	    self.setInternalValue(val.toString());
	    self.setValueType(BigDecimal.class.getName());
	}
	public Object getValue(VariantBean self) {
	    if(self.getType().equals(BigDecimal.class)) {
		return new BigDecimal(self.getInternalValue());
	    }
	    throw new RuntimeException("Invalid data type");
	}
    }
    static class LongStrategy implements VariantStrategy {
	public void setValue(VariantBean self, Object val) {
	    self.setInternalValue(val.toString());
	    self.setValueType(Long.class.getName());
	}
	public Object getValue(VariantBean self) {
	    if(self.getType().equals(Long.class)) {
		return new Long(self.getInternalValue());
	    }
	    throw new RuntimeException("Invalid data type");
	}
    }
    static class BooleanStrategy implements VariantStrategy {
	public void setValue(VariantBean self, Object val) {
	    self.setInternalValue(val.toString());
	    self.setValueType(Boolean.class.getName());
	}
	public Object getValue(VariantBean self) {
	    if(self.getType().equals(Boolean.class)) {
		return new Boolean(self.getInternalValue());
	    }
	    throw new RuntimeException("Invalid data type");
	}
    }
    static class StringStrategy implements VariantStrategy {
	public void setValue(VariantBean self, Object val) {
	    self.setInternalValue(val.toString());
	    self.setValueType(String.class.getName());
	}
	public Object getValue(VariantBean self) {
	    if(self.getType().equals(String.class)) {
		return self.getInternalValue();
	    }
	    throw new RuntimeException("Invalid data type");
	}
    }
    static class DoubleStrategy implements VariantStrategy {
	public void setValue(VariantBean self, Object val) {
	    self.setInternalValue(val.toString());
	    self.setValueType(Double.class.getName());
	}
	public Object getValue(VariantBean self) {
	    if(self.getType().equals(Double.class)) {
		return new Double(self.getInternalValue());
	    }
	    throw new RuntimeException("Invalid data type");
	}
    }
    static class DateStrategy implements VariantStrategy {
	public void setValue(VariantBean self, Object val) {
	    Date date = (Date) val;
	    self.setInternalValue(String.valueOf(date.getTime()));
	    self.setValueType(Date.class.getName());
	}
	public Object getValue(VariantBean self) {
	    if(self.getType().equals(Date.class)) {
		Date date = new Date();
		date.setTime(Long.valueOf(self.getInternalValue()).longValue());
		return date;
	    }
	    throw new RuntimeException("Invalid data type");
	}
    }
    static class NullStrategy implements VariantStrategy {
	public void setValue(VariantBean self, Object val) {
	    if(val!=null)throw new RuntimeException("Invalid data type");
	    self.setInternalValue(null);
	    self.setValueType("null");
	}
	public Object getValue(VariantBean self) {
	    if(self.getType().equals(Boolean.class)) {
		return new Boolean(self.getInternalValue());
	    }
	    throw new RuntimeException("Invalid data type");
	}
    }
    
    static class IntegerStrategy implements VariantStrategy {
    	public void setValue(VariantBean self, Object val) {
    	    self.setInternalValue(val.toString());
    	    self.setValueType(Integer.class.getName());
    	}
    	public Object getValue(VariantBean self) {
    	    if(self.getType().equals(Integer.class)) {
    		return new Integer(self.getInternalValue());
    	    }
    	    throw new RuntimeException("Invalid data type");
    	}
    }
    	
    private static Map Strategies = new HashMap();
    static {
	Strategies.put("java.lang.Double", new DoubleStrategy());
	Strategies.put("java.lang.String", new StringStrategy());
	Strategies.put("java.lang.Long", new LongStrategy());
	Strategies.put("java.lang.Boolean", new BooleanStrategy());
	Strategies.put("java.lang.Integer", new IntegerStrategy());
	Strategies.put("java.util.Date", new DateStrategy());
	Strategies.put("java.math.BigDecimal", new BigDecimalStrategy());
	Strategies.put("null", new NullStrategy());
    }
}
