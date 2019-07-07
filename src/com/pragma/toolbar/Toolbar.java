/*
 * Toolbar.java
 *
 * Created on 2 de noviembre de 2006, 18:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pragma.toolbar;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.hibernate.Session;

import com.pragma.toolbar.web.form.ToolbarFiltersForm;


/**
 *
 * @author fferrara
 */
public class Toolbar {
    
    private String id;
    
    private ToolbarQuery toolbarQuery;
    private ToolbarView toolbarView;
        
     /** Creates a new instance of Toolbar */
    public Toolbar(HttpServletRequest request, ToolbarFiltersForm form, String idToolbar, String idUser, String queryBase, Session session) {

        int pageNumber = 1;
        
        
        
        if(request.getParameter("page") != null) {
            pageNumber = new Integer(request.getParameter("page")).intValue();
        }
        
        // get userName
        String user = idUser;
        
        toolbarQuery = new ToolbarQuery(form.getFilters(), pageNumber, !isExporting(request), user, idToolbar, queryBase, session);
        toolbarView = new ToolbarView(user, idToolbar);
        this.id = idToolbar;
    }

    public ToolbarQuery getToolbarQuery() {
        return toolbarQuery;
    }

    public void setToolbarQuery(ToolbarQuery toolbarQuery) {
        this.toolbarQuery = toolbarQuery;
    }

    public ToolbarView getToolbarView() {
        return toolbarView;
    }

    public void setToolbarView(ToolbarView toolbarView) {
        this.toolbarView = toolbarView;
    }

    public String getId() {
        return id;
    }
    
	private boolean isExporting(HttpServletRequest request){
	   	return request.getParameter(TableTagParameters.PARAMETER_EXPORTING) != null;
	}
}
