package com.pragma.toolbar.web.form;

import org.apache.struts.action.ActionForm;

public class ToolbarSortCriteriaForm extends ActionForm 
{
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String toolbarId;
	private String criteriaXML;
	
	public String getCriteriaXML() {
	    return criteriaXML;
	}
	public void setCriteriaXML(String criteriaXML) {
	    this.criteriaXML = criteriaXML;
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
