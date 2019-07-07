/*
 * ToolbarColumn.java
 *
 * Created on 2 de noviembre de 2006, 17:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pragma.toolbar.data;

import java.util.Collection;

import com.pragma.toolbar.config.default_config.DefaultToolbarConfig;
import com.pragma.toolbar.properties.DefaultFilterTypeLibrary;

/**
 *
 * @author fferrara
 */
public class ToolbarColumn {
    
    private String property;
    private Boolean visible;
    private String title;
    private String decorator;
    private String align;
    private DataType dataType;
        
    /**
     * Creates a new instance of ToolbarColumn
     */
    public ToolbarColumn(String property, Boolean visible, String title, String decorator, String align, DataType dataType) 
    {
        this.property = property;
        this.visible = visible;
        this.title = title;
        this.decorator = decorator;
        this.align = align;
        this.dataType = dataType;
    }
    
    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDecorator() {
        return decorator;
    }

    public void setDecorator(String decorator) {
        this.decorator = decorator;
    }
    
     public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }
    
    public Collection supportedFilterTypes() {
	return DefaultToolbarConfig.getFilterTypeLibrary().byDataType(getDataType());
    } 

}



