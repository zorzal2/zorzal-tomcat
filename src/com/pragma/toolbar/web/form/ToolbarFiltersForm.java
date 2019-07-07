/*
 * ToolbarFilters.java
 *
 * Created on November 21, 2006, 11:13 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pragma.toolbar.web.form;

import java.util.Collection;
import java.util.Map;

import com.pragma.toolbar.data.filter.ConfigurableToolbarFilter;

/**
 * Interface para acceder a los filtros rápidos
 *
 * @author fferrara
 */
public interface ToolbarFiltersForm {
    public Collection getFilters();
	public void setFiltersMap(Map filtersMap);
	public Map getFiltersMap();
	public void setFilter(String propertyKey, ConfigurableToolbarFilter filter);
}
