package com.pragma.toolbar.data.filter;

public interface ConfigurableToolbarFilter extends ToolbarQueryFilter {
    public Class getClassType();
    public Object getValue();
    public void setValue(Object value);
}
