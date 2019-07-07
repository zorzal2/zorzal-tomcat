package com.pragma.toolbar.config.default_config;

import java.util.Collection;

import com.pragma.toolbar.NotImplementedException;
import com.pragma.toolbar.config.ColumnConfiguration;
import com.pragma.toolbar.data.DataType;
import com.pragma.toolbar.properties.DefaultFilterTypeLibrary;
import com.pragma.toolbar.properties.OrderTypeEnum;

public class DefaultColumnConfiguration implements ColumnConfiguration {
    
    private final String property;
    private final String showProperty;
    private final String orderProperty;
    private final Boolean visible;
    private final String title;
    private final String decorator;
    private final String align;
    private final DataType dataType;
    private final Boolean allowFiltering;
    private final Boolean allowSorting;
    private final Integer order;
    private final OrderTypeEnum orderType;
    private final Boolean escapeHtml;
        
    /**
     * Creates a new instance of ToolbarColumn
     */
    public DefaultColumnConfiguration(
	    String property, 
	    String showProperty,
	    String orderProperty,
	    Boolean visible, 
	    String title, 
	    String decorator, 
	    String align, 
	    DataType dataType,
	    Boolean allowFiltering,
	    Boolean allowSorting,
	    Integer order,
	    OrderTypeEnum orderType,
	    Boolean escapeHtml
	    ) 
    {
        this.property = property;
        this.showProperty = showProperty;
        this.orderProperty = orderProperty;
        this.visible = visible;
        this.title = title;
        this.decorator = decorator;
        this.align = align;
        this.dataType = dataType;
        this.allowFiltering = allowFiltering;
        this.allowSorting = allowSorting;
        this.order = order;
        this.orderType = orderType;
        this.escapeHtml = escapeHtml;
    }
    
    public String getProperty() {
        return property;
    }

    public Boolean getVisible() {
        return visible;
    }

    public String getTitle() {
        return title;
    }

    public String getDecorator() {
        return decorator;
    }
    
     public String getAlign() {
        return align;
    }

    public DataType getDataType() {
        return dataType;
    }
    
    public Collection supportedFilterTypes() {
	return DefaultToolbarConfig.getFilterTypeLibrary().byDataType(getDataType());
    }
    
    public Boolean getAllowFiltering() {
	return this.allowFiltering;
    }

    public Boolean getAllowSorting() {
	return this.allowSorting;
    }

    public Integer getOrder() {
	return this.order;
    }

    public OrderTypeEnum getOrderType() {
	return this.orderType;
    }

    public void setOrder(Integer order) {
	throw new NotImplementedException();
    }

    public void setOrderType(OrderTypeEnum newOrder) {
	throw new NotImplementedException();
	
    }

    public void setVisible(Boolean isVisible) {
	throw new NotImplementedException();
    }

    public void restoreOrder() {
	//Yo soy el default
    }

    public void restoreOrderType() {
	//Yo soy el default    
    }

    public void restoreVisible() {
	//Yo soy el default
    }
    
    public String getOrderProperty() {
    	if(orderProperty!=null) return orderProperty;
    	else return getProperty();
    }

	public String getShowProperty() {
		if(showProperty!=null) return showProperty;
		else return getProperty();
	}

	public Boolean getEscapeHtml() {
		return escapeHtml;
	}
}