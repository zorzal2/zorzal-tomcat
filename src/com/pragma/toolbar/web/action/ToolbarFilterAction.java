package com.pragma.toolbar.web.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.pragma.toolbar.NotImplementedException;
import com.pragma.toolbar.config.default_config.DefaultToolbarConfig;
import com.pragma.toolbar.config.user_config.UserToolbarConfiguration;
import com.pragma.toolbar.data.ToolbarFilterPersister;
import com.pragma.toolbar.data.filter.ConfigurableToolbarFilter;
import com.pragma.toolbar.data.filter.ConfigurableToolbarFilterBuilder;
import com.pragma.toolbar.data.filter.ToolbarQueryFilter;
import com.pragma.toolbar.properties.FilterType;
import com.pragma.toolbar.properties.DefaultFilterTypeLibrary;
import com.pragma.toolbar.web.form.ToolbarPersistentFiltersForm;

public class ToolbarFilterAction extends MappingDispatchAction {

    public ActionForward getAll(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response) {
	ToolbarPersistentFiltersForm filtersForm = (ToolbarPersistentFiltersForm) form;
	//usuario y toolbar
	request.setAttribute("userId", filtersForm.getUserId());
	request.setAttribute("toolbarId", filtersForm.getToolbarId());
	//tipos de filtros
	request.setAttribute("filterTypes", DefaultToolbarConfig.getFilterTypeLibrary().getAll());
	//Columnas
	request.setAttribute("columns", 
		UserToolbarConfiguration.get(filtersForm.getUserId(), filtersForm.getToolbarId()).getColumns()
		);
	//Filtros
	Collection filters = new ToolbarFilterPersister(filtersForm.getUserId(), filtersForm.getToolbarId()).getAllFilters();
	request.setAttribute("filters", filters);
	return mapping.findForward("success");
    }
    public ActionForward setAll(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response) {
	ToolbarPersistentFiltersForm filtersForm = (ToolbarPersistentFiltersForm) form;
	Document document;
	try {
	    document = DocumentHelper.parseText(filtersForm.getFiltersXML());
	} catch (DocumentException exception) {
		exception.printStackTrace();
	    throw new RuntimeException("XML not valid");
	}
	Collection filtersNodes = document.selectNodes("//filter");
	Collection filters = new ArrayList();
	for (Iterator iter = filtersNodes.iterator(); iter.hasNext();) {
	    ToolbarQueryFilter filter = createFilter((Node) iter.next());
	    filters.add(filter);
	}
	new ToolbarFilterPersister(
		filtersForm.getUserId(), 
		filtersForm.getToolbarId()).setFilters(filters);
	
	request.setAttribute("userId", filtersForm.getUserId());
	request.setAttribute("toolbarId", filtersForm.getToolbarId());
	return mapping.findForward("success");
    }
    private ConfigurableToolbarFilter createFilter(Node node) {
	String property = node.valueOf("@column-id");
        FilterType nodeFilterType = DefaultToolbarConfig.getFilterTypeLibrary().fromCode(Integer.valueOf(node.valueOf("@filter-type-id")).intValue()); 
        Class valueClass = nodeFilterType.getDataType().toJavaClass();
        Object value = parse(node.getText(), valueClass);
        ConfigurableToolbarFilter ret = ConfigurableToolbarFilterBuilder.buildPropertyBasedFilter(
        	property,
        	nodeFilterType,
        	value,
        	valueClass
        );
        return ret;
    }
    /**
     * Parsea valores basicos.
     * @param text
     * @param valueClass
     * @return
     */
    private Object parse(String text, Class valueClass) {
	if(String.class.isAssignableFrom(valueClass)) {
	    return text;	    
	}
	if(valueClass.equals(Double.class) || valueClass.equals(BigDecimal.class)) {
		return new BigDecimal(text);
	}
	if(valueClass.equals(Long.class)) {
	    return new Long(text);
	}
	if(Boolean.class.isAssignableFrom(valueClass)) {
	    return new Boolean(text);
	}
	if(Date.class.isAssignableFrom(valueClass)) {
	    return new Date(Long.valueOf(text).longValue());
	}
	//Es alguna otra cosa
	throw new NotImplementedException();
    }
}