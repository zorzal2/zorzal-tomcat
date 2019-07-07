/*
 * ToolbarRegister.java
 *
 * Created on November 30, 2006, 10:07 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pragma.toolbar.util;

import javax.servlet.http.HttpServletRequest;

import com.pragma.toolbar.Toolbar;

/**
 *
 * @author fferrara
 */
public class ToolbarRegister {
    
    /** Creates a new instance of ToolbarRegister */
    public static void registerDefault(HttpServletRequest request, Toolbar toolbar) {
        request.setAttribute("list",toolbar.getToolbarQuery());
        request.setAttribute("view",toolbar.getToolbarView());
        request.setAttribute("toolbarId", toolbar.getId());
        request.setAttribute("userId", request.getUserPrincipal().getName());
    }
    
}
