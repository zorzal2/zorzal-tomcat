package com.pragma.toolbar.data.filter;

import java.io.Serializable;
import java.util.Map;

import com.pragma.toolbar.properties.FilterType;

public interface ToolbarQueryFilter extends Serializable {

    public FilterType getFilterType();
    public String getHqlForObject(String objectName);
    public Map<String, Object> getVariables();
    public boolean isEmpty();
}