package com.pragma.toolbar.config.default_config;

import java.util.List;

import org.dom4j.DocumentException;

import com.fontar.toolbar.properties.FilterTypeLibraryDecorator;
import com.pragma.hibernate.BeforeQueryInvocationHandler;
import com.pragma.toolbar.config.PropertyNotPresentException;
import com.pragma.toolbar.config.ToolbarConfiguration;
import com.pragma.toolbar.config.user_config.UserColumnConfiguration;
import com.pragma.toolbar.properties.DefaultFilterTypeLibrary;
import com.pragma.toolbar.properties.FilterTypeLibrary;
import com.pragma.util.CollectionUtils;
import com.pragma.util.Conditions.Condition;

public class DefaultToolbarConfig implements ToolbarConfiguration {

	private static FilterTypeLibrary instance =  DefaultToolbarConfig.createFilterLibrary();
    private String toolbarId;

    public DefaultToolbarConfig(String toolbarId) {
	this.toolbarId = toolbarId;
    }
    
    private static FilterTypeLibrary createFilterLibrary() {
    	try {
			String filterTypeLibraryClassName = ConfigurationXMLHelper.getFilterTypeLibraryClassName();
			if (filterTypeLibraryClassName == null) filterTypeLibraryClassName = DefaultFilterTypeLibrary.class.getCanonicalName();
			Class clase = Class.forName(filterTypeLibraryClassName);
			return (FilterTypeLibrary) clase.getMethod("getInstance").invoke(null);
			}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

    public List getActions() {
		 try {
			 return ConfigurationXMLHelper.getActions(toolbarId);
		 } catch (DocumentException exception) {
			 throw new PropertyNotPresentException("Actions", toolbarId); 
		 }
	 }
    public List getButtons() {
	try {
	    return ConfigurationXMLHelper.getButtons(toolbarId);
	} catch (DocumentException exception) {
	    throw new PropertyNotPresentException("Buttons", toolbarId); 
	}
    }

    public List getColumns() {
	try {
	    return ConfigurationXMLHelper.getColumns(toolbarId);
	} catch (DocumentException exception) {
	    throw new PropertyNotPresentException("Columns", toolbarId); 
	}
    }

    public String getDecorator() {
	return ConfigurationXMLHelper.getDecorator(toolbarId);
    }

    public Integer getPageSize() {
	return ConfigurationXMLHelper.getPageSize(toolbarId);
    }

    public Boolean getPaging() {
	return ConfigurationXMLHelper.getPaging(toolbarId);
    }

    public String getStyleClass() {
	try {
	    return ConfigurationXMLHelper.getStyleClass(toolbarId);
	} catch (DocumentException exception) {
	    throw new PropertyNotPresentException("Columns", toolbarId);
	}
    }
    
    public boolean hasChanged() {
	return false;
    }

    public String getId() {
	return toolbarId;
    }

    public List getColumnsOrders() {
	try {
	    return ConfigurationXMLHelper.getColumnsOrders(toolbarId);
	} catch (DocumentException exception) {
	    throw new PropertyNotPresentException("Columns", toolbarId);
	}
    }

    public List getVisibleColumns() {
	Condition<UserColumnConfiguration> isVisible = new Condition<UserColumnConfiguration>() {
	    public boolean isTrueFor(UserColumnConfiguration column) {
		return column.getVisible().booleanValue();
	    }
	};
	 
	return (List)CollectionUtils.extractElementsSuch(getColumns(), isVisible);
    }

    public String getActionsTitle() {
	try {
	    return ConfigurationXMLHelper.getActionsTitle(toolbarId);
	} catch (DocumentException exception) {
	    exception.printStackTrace();
	    throw new PropertyNotPresentException("ActionTitles", toolbarId);
	}
    }

    public BeforeQueryInvocationHandler getBeforeQueryInvocationHandler() {
    	
        try {
			return ConfigurationXMLHelper.getQueryInvocationHandler(toolbarId);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
    }
    
    public static FilterTypeLibrary getFilterTypeLibrary() {
    	return instance;
    }
}
