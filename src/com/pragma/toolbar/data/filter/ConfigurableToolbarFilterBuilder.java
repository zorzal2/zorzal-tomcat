package com.pragma.toolbar.data.filter;

import com.pragma.toolbar.NotImplementedException;
import com.pragma.toolbar.properties.DefaultFilterTypeLibrary;
import com.pragma.toolbar.properties.FilterType;
import com.pragma.toolbar.properties.HQLFilterType;

public class ConfigurableToolbarFilterBuilder {
    public static ConfigurableToolbarFilter build(
    		String property, 
    		FilterType filterType, 
    		Object value, 
    		Class classType,
    		String expression) {
    	if(filterType.equals(DefaultFilterTypeLibrary.EXPRESSION)) {
    		return new ExpressionBasedToolbarFilter(expression, value, classType);
    	} else {
    		return buildPropertyBasedFilter(property, filterType, value, classType);
    	}
    }

	public static PropertyBasedToolbarFilter buildPropertyBasedFilter(String property, FilterType filterType, Object value, Class classType) {
		if(filterType instanceof HQLFilterType) {
			return new HQLPropertyBasedToolbarFilter(property, (HQLFilterType)filterType, value, classType);
		} else {
			throw new NotImplementedException("No se puede construir filtros del tipo " + filterType.getName());
		}
    	//return new BooleanPropertyBasedToolbarFilter(property, filterType, value, classType);
	}
}
