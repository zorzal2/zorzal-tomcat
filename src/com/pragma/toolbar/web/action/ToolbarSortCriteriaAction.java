package com.pragma.toolbar.web.action;

import java.util.ArrayList;
import java.util.Collection;
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

import com.pragma.toolbar.config.ToolbarConfiguration;
import com.pragma.toolbar.config.user_config.UserToolbarConfiguration;
import com.pragma.toolbar.data.ToolbarOrder;
import com.pragma.toolbar.data.ToolbarSortCriteriaPersister;
import com.pragma.toolbar.properties.OrderTypeEnum;
import com.pragma.toolbar.web.form.ToolbarSortCriteriaForm;

public class ToolbarSortCriteriaAction extends MappingDispatchAction {

    public ActionForward getAll(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response) {
	ToolbarSortCriteriaForm sortCriteriaForm = (ToolbarSortCriteriaForm) form;
	//usuario y toolbar
	request.setAttribute("userId", sortCriteriaForm.getUserId());
	request.setAttribute("toolbarId", sortCriteriaForm.getToolbarId());

	ToolbarConfiguration config = UserToolbarConfiguration.get(sortCriteriaForm.getUserId(), sortCriteriaForm.getToolbarId());
	//Columnas
	request.setAttribute("columns", 
		config.getColumns()
		);

	//Criterios de ordenamiento actuales
	request.setAttribute("sortCriteria", config.getColumnsOrders());
	

	return mapping.findForward("success");
    }
    public ActionForward setAll(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response) {
	ToolbarSortCriteriaForm sortCriteriaForm = (ToolbarSortCriteriaForm) form;
	Document document;
	try {
	    document = DocumentHelper.parseText(sortCriteriaForm.getCriteriaXML());
	} catch (DocumentException exception) {
	    exception.printStackTrace();
	    throw new RuntimeException("XML not valid");
	}
	Collection sortCriteriaNodes = document.selectNodes("//sortCriterion");
	Collection sortCriteria = new ArrayList();
	for (Iterator iter = sortCriteriaNodes.iterator(); iter.hasNext();) {
	    ToolbarOrder sortCriterion = createOrder((Node) iter.next());
	    sortCriteria.add(sortCriterion);
	}
	new ToolbarSortCriteriaPersister(
		sortCriteriaForm.getUserId(), 
		sortCriteriaForm.getToolbarId()).setSortCriteria(sortCriteria);
	
	request.setAttribute("userId", sortCriteriaForm.getUserId());
	request.setAttribute("toolbarId", sortCriteriaForm.getToolbarId());
	return mapping.findForward("success");
    }
    private ToolbarOrder createOrder(Node node) {
	String property = node.valueOf("@column-id");
	String sortOption = node.valueOf("@sort-option");
	String order = node.valueOf("@order");
	
        return new ToolbarOrder(
        	property,
        	new Integer(order),
        	OrderTypeEnum.fromName(sortOption)
        	);
    }
}
