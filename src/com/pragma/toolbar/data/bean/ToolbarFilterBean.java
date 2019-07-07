/*
 * Created on November 14, 2006, 1:13 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pragma.toolbar.data.bean;

import com.pragma.util.interfaces.Bean;


/**
 * @author llobeto
 */
public class ToolbarFilterBean implements Bean {

    private static final long serialVersionUID = 1L;

    private Long id; 
    private String property;
    private String expression;
    private String filterTypeDescription;
    private VariantBean value;
    private String classTypeName;
    
    private String userId;
    private String toolbarId;
    
    public String getClassTypeName() {
        return classTypeName;
    }
    public void setClassTypeName(String classTypeName) {
        this.classTypeName = classTypeName;
    }
    public String getExpression() {
        return expression;
    }
    public void setExpression(String expression) {
        this.expression = expression;
    }
    public String getFilterTypeDescription() {
        return filterTypeDescription;
    }
    public void setFilterTypeDescription(String filterTypeDescription) {
        this.filterTypeDescription = filterTypeDescription;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getProperty() {
        return property;
    }
    public void setProperty(String property) {
        this.property = property;
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
    public VariantBean getValue() {
        return value;
    }
    public void setValue(VariantBean value) {
        this.value = value;
    }
}