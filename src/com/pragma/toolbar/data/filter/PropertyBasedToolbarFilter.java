/*
 * ToolbarFilter.java
 *
 * Created on 3 de noviembre de 2006, 10:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pragma.toolbar.data.filter;

import java.util.Hashtable;
import java.util.Map;

import com.pragma.toolbar.properties.FilterType;

/**
 *
 * @author llobeto
 */
public abstract class PropertyBasedToolbarFilter implements ConfigurableToolbarFilter {

    private static final long serialVersionUID = 1L;

    private String property;
    private FilterType filterTypeEnum;
    private Object value;
    private Class classType;   

    /** Creates a new instance of ToolbarFilter */
    protected PropertyBasedToolbarFilter(String property, FilterType filterTypeEnum, Object value, Class classType) {
		this.setProperty(property);
		this.setFilterType(filterTypeEnum);
		this.setValue(value);
		this.setClassType(classType);
    }

    /** Creates a new instance of ToolbarFilter */
    public PropertyBasedToolbarFilter(String property, FilterType filterTypeEnum, Class classType) {
    	this(property, filterTypeEnum, null, classType);
    }

    public String getProperty() {
    	return property;
    }

    public void setProperty(String property) {
	this.property = property;
    }

    public Object getValue() {
    	return value;
    }

    public void setValue(Object value) {
    	this.value = value;
    }

    /* (non-Javadoc)
     * @see com.pragma.toolbar.data.ToolbarQueryFilter#getFilterType()
     */
    public FilterType getFilterType() {
	return filterTypeEnum;
    }

    protected void setFilterType(FilterType filterType) {
	this.filterTypeEnum = filterType;
    }

    public Class getClassType() {
	return classType;
    }

    protected void setClassType(Class clazz){
    	classType = clazz;
    }
  
    public boolean isEmpty() {
    	return value==null || value.equals("");
    }

    public abstract String getHqlForObject(String o);	

    public Map<String, Object> getVariables() {
    	Map<String, Object> ret = new Hashtable<String, Object>();
    	ret.put(varName(), getValue());
    	return ret;
    }

    protected String varName() {
    	return "var"+this.hashCode();
    }
}
