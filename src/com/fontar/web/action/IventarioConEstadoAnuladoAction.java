package com.fontar.web.action;

import javax.servlet.http.HttpServletRequest;

import com.pragma.toolbar.config.default_config.DefaultToolbarConfig;
import com.pragma.toolbar.data.filter.ConfigurableToolbarFilter;
import com.pragma.toolbar.data.filter.ConfigurableToolbarFilterBuilder;
import com.pragma.toolbar.properties.FilterType;
import com.pragma.toolbar.web.form.ToolbarFiltersForm;
import com.pragma.web.action.BaseInventarioAction;

public abstract class IventarioConEstadoAnuladoAction extends BaseInventarioAction{

	protected void addCustomFilters(HttpServletRequest request,  ToolbarFiltersForm form) {
		
		if (!(form.getFilters().isEmpty()))	return;
		
		String propertyName = this.getPropertyName();
		FilterType filterType =  DefaultToolbarConfig.getFilterTypeLibrary().fromName("fontar.filter.type.object.sin_anulados");
		Object objValue = "all";
		Class typeClazz = String.class;
		String filterExpression = "";
	 			
		ConfigurableToolbarFilter filtro =
			ConfigurableToolbarFilterBuilder.build(
						propertyName,
						filterType,
						objValue,
						typeClazz,
						filterExpression);
						
		form.setFilter("CMB%20" + propertyName + "%20fontar.filter.type.object.sin_anulados%20java.lang.String",  filtro);
	}
	
	protected abstract String getPropertyName();
	
}
