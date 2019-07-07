package com.pragma.toolbar.web.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;
import org.apache.struts.util.LabelValueBean;

import com.pragma.toolbar.config.user_config.UserToolbarConfiguration;

public class ToolbarPageSizeAction extends MappingDispatchAction {

	private static int[] TEMPLATE_VALUES = {10,50,100}; 
	
    public ActionForward getAll(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response) {
		
    	DynaActionForm pageSizeForm = (DynaActionForm) form;
    	
    	String userId = pageSizeForm.getString("userId");
    	String toolbarId = pageSizeForm.getString("toolbarId");
    	
    	Integer pageSize = UserToolbarConfiguration.get(userId,toolbarId).getPageSize();
		
		//usuario y toolbar
		request.setAttribute("userId", userId);
		request.setAttribute("toolbarId", toolbarId);
		
		ArrayList templateBeans = new ArrayList();
			
		for (int i = 0; i < TEMPLATE_VALUES.length; i++) {
			String labelValue = String.valueOf(TEMPLATE_VALUES[i]);
			templateBeans.add(new LabelValueBean(labelValue,labelValue));
		}
		
		request.setAttribute("pageSizeOptions",templateBeans);
		
		if (ArrayUtils.contains(TEMPLATE_VALUES,pageSize.intValue())){
			pageSizeForm.set("pageSize",pageSize);
			pageSizeForm.set("customValue",null);
		} else {
			pageSizeForm.set("customValue",pageSize);
			pageSizeForm.set("pageSize",null);
		}
		return mapping.findForward("success");		
    }
    
    public ActionForward setAll(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response) {

    	DynaActionForm pageSizeForm = (DynaActionForm) form;
    	
    	String userId = pageSizeForm.getString("userId");
    	String toolbarId = pageSizeForm.getString("toolbarId");
    	
    	// check template value
    	Integer pageSize = (Integer)pageSizeForm.get("pageSize");
    	
    	if (pageSize == null || pageSize.equals(new Integer(0))) {
    		pageSize = (Integer)pageSizeForm.get("customValue");
    	}
    	
    	UserToolbarConfiguration.get(userId,toolbarId).setPageSize(pageSize);

    	//usuario y toolbar
		request.setAttribute("userId", userId);
		request.setAttribute("toolbarId", toolbarId);

		return mapping.findForward("success");
    }
}
