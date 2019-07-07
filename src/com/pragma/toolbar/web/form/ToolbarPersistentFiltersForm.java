package com.pragma.toolbar.web.form;

import org.apache.struts.action.ActionForm;

public class ToolbarPersistentFiltersForm extends ActionForm 
{
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String toolbarId;
	private String filtersXML;
	
	public String getFiltersXML() {
	    return filtersXML;
	}
	public void setFiltersXML(String filtersXML) {
	    this.filtersXML = filtersXML;
	}
	public String getToolbarId() {
	    return toolbarId;
	}
	public void setToolbarId(String toolbarId) {
	    this.toolbarId = toolbarId;
	}
	public String getUserId() {
	    return userId;
	}
	public void setUserId(String userId) {
	    this.userId = userId;
	}
}
