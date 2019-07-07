/*
 * ConfigurationXMLHelper.java
 *
 * Created on November 7, 2006, 6:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pragma.toolbar.config;

import java.util.List;

import com.pragma.hibernate.BeforeQueryInvocationHandler;

/**
 *
 * @author llobeto
 */
public interface ToolbarConfiguration {
    
    public String getId();
    public Boolean getPaging();
    public Integer getPageSize();
    public List getColumns();
    public List getVisibleColumns();
    public List getButtons();
    public List getActions();
    public String getActionsTitle();
    public String getDecorator(); 
    public String getStyleClass();
    public List getColumnsOrders();
    public BeforeQueryInvocationHandler getBeforeQueryInvocationHandler();
}