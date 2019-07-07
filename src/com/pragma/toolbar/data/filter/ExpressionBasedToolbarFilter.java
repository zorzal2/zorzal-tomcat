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
import com.pragma.toolbar.properties.DefaultFilterTypeLibrary;
import com.pragma.util.StringUtil;

/**
 *
 * @author llobeto
 */
public class ExpressionBasedToolbarFilter implements ConfigurableToolbarFilter {
	
	private static final long serialVersionUID = 1L;
	
	private String expression;
	private Object value;
	private Class classType;
	private boolean dependsOnValue = false;
	
	/** Creates a new instance of ToolbarFilter */
	public ExpressionBasedToolbarFilter(String expression, Object value, Class classType) {
		this.setValue(value);
		this.setExpression(expression);
		this.setClassType(classType);
	}
	
	/** Creates a new instance of ToolbarFilter */
	public ExpressionBasedToolbarFilter(String expression, Class classType) {
		this.setExpression(expression);
		this.setClassType(classType);
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	public FilterType getFilterType() {
		return DefaultFilterTypeLibrary.EXPRESSION;
	}
	
	public String getExpression() {
		return expression;
	}
	
	public void setExpression(String expression) {
		this.expression = expression;
		this.dependsOnValue = expression.indexOf("$filterValue")>=0;
	}
	
	public Class getClassType() {
		return classType;
	}
	
	private void setClassType(Class classType) {
		this.classType = classType;
	}
	
	public boolean isEmpty() {
		if(dependsOnValue) {
			return value==null;
		} else { //Expresion fija que no depende de variables.
			return (expression==null || expression.equals(""));
		}
	}
	
	public String getHqlForObject(String o) {
		return StringUtil.replace(
				expression,
				"$filterValue",
				":"+varName()
		);
	}
	
	public Map<String, Object> getVariables() {
		Map<String, Object> ret = new Hashtable<String, Object>();
		if(this.dependsOnValue) ret.put(varName(), getValue());
		return ret;
	}
	
	private String varName() {
		return "var"+this.hashCode();
	}
}