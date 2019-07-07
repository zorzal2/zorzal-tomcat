package com.pragma.toolbar.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import com.pragma.toolbar.config.user_config.UserToolbarConfiguration;
import com.pragma.toolbar.web.form.ToolbarPersistentColumnsForm;

public class ToolbarColumnAction extends MappingDispatchAction {

    public ActionForward getAll(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response) {
		
    	ToolbarPersistentColumnsForm filtersForm = (ToolbarPersistentColumnsForm) form;
		
		//usuario y toolbar
		request.setAttribute("userId", filtersForm.getUserId());
		request.setAttribute("toolbarId", filtersForm.getToolbarId());
	
		List columns = UserToolbarConfiguration.get(filtersForm.getUserId(), filtersForm.getToolbarId()).getColumns();
		filtersForm.setColumns(columns);		
		
		return mapping.findForward("success");		
    }
    
    public ActionForward setAll(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response) {

    	ToolbarPersistentColumnsForm filtersForm = (ToolbarPersistentColumnsForm) form;
    	
		request.setAttribute("userId", filtersForm.getUserId());
		request.setAttribute("toolbarId", filtersForm.getToolbarId());

		return mapping.findForward("success");
    }
}
