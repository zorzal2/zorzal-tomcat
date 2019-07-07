package com.pragma.toolbar.web.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

import com.pragma.toolbar.config.user_config.UserColumnConfiguration;

public class ToolbarPersistentColumnsForm extends ActionForm 
{
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String toolbarId;
	private List columns;

	public List getColumns() {
		return columns;
	}
	public void setColumns(List columns) {
		this.columns = columns;
	}
	
	public void setVisible(int index, Boolean visible){
		((UserColumnConfiguration)this.columns.get(index)).setVisible(visible);
	}
	
	public Boolean getVisible(int index){
		return ((UserColumnConfiguration)this.columns.get(index)).getVisible();
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
