package com.pragma.toolbar.config.user_config;

import java.io.Serializable;
import java.util.Collection;

import com.pragma.toolbar.config.ColumnConfiguration;
import com.pragma.toolbar.config.default_config.DefaultToolbarConfig;
import com.pragma.toolbar.data.DataType;
import com.pragma.toolbar.properties.DefaultFilterTypeLibrary;
import com.pragma.toolbar.properties.OrderTypeEnum;
import com.pragma.util.Utils;

public class UserColumnConfiguration implements ColumnConfiguration, Serializable {
	
	private static final long serialVersionUID = 1L;
	private ColumnConfiguration defaultConfig = null;
	private UserConfiguration userConfiguration;
	
	//private String userId;
	private String toolbarUri;
	private Boolean visible;
	private Integer order;
	private OrderTypeEnum orderType;
	
	/**
	 * Creates a new instance of ToolbarColumn
	 */
	protected UserColumnConfiguration(String userId, ColumnConfiguration defaultConfig, String toolbarUri)
	{
		//this.userId = userId;
		this.defaultConfig = defaultConfig;
		this.toolbarUri = toolbarUri;
		
		//cargo las configuraciones guardadas
		this.userConfiguration = UserConfiguration.forUser(userId);
		this.visible = Utils.notNullOf(
				(Boolean)userConfiguration.getProperty(getUri("visible")),
				defaultConfig.getVisible()
		);
		
		this.order = (Integer)userConfiguration.getProperty(getUri("order"));
		if(this.order==null) this.order = defaultConfig.getOrder();
		
		this.orderType = Utils.notNullOf(
				OrderTypeEnum.fromName((String)userConfiguration.getProperty(getUri("orderType"))),
				defaultConfig.getOrderType()
		);
	}
	
	public String getProperty() {
		return defaultConfig.getProperty();
	}
	
	public String getShowProperty() {
		return defaultConfig.getShowProperty();
	}
	
	public String getOrderProperty() {
		return defaultConfig.getOrderProperty();
	}
	
	public Boolean getVisible() {
		return visible;
	}
	
	public String getTitle() {
		return defaultConfig.getTitle();
	}
	
	public String getAlign() {
		return defaultConfig.getAlign();
	}
	
	public DataType getDataType() {
		return defaultConfig.getDataType();
	}
	
	public Collection supportedFilterTypes() {
		return DefaultToolbarConfig.getFilterTypeLibrary().byDataType(getDataType());
	}
	
	public Boolean getAllowFiltering() {
		return defaultConfig.getAllowFiltering();
	}
	
	public Boolean getAllowSorting() {
		return defaultConfig.getAllowSorting();
	}
	
	public Integer getOrder() {
		return this.order;
	}
	
	public OrderTypeEnum getOrderType() {
		return this.orderType;
	}
	
	public Boolean getEscapeHtml() {
		return defaultConfig.getEscapeHtml();
	}
	
	public void setOrder(Integer order) {
		if(!this.order.equals(order)) {
			this.order = order;
			userConfiguration.setProperty(getUri("order"), order);
		}
	}
	
	public void setOrderType(OrderTypeEnum newOrder) {
		if(!this.orderType.equals(newOrder)) {
			this.orderType = newOrder;
			userConfiguration.setProperty(getUri("orderType"), this.orderType.getName());
		}	
	}
	
	public void setVisible(Boolean isVisible) {
		if(!this.visible.equals(isVisible)) {
			this.visible = isVisible;
			userConfiguration.setProperty(getUri("visible"), this.visible);
		}
	}
	
	private String getUri(String propName) {
		return this.toolbarUri+".toolbar["+getProperty()+"]."+propName;
	}
	
	public String getDecorator() {
		return defaultConfig.getDecorator();
	}
	
	public void restoreOrder() {
		userConfiguration.removeProperty(getUri("order"));
		this.order = defaultConfig.getOrder();
	}
	
	public void restoreOrderType() {
		userConfiguration.removeProperty(getUri("orderType"));
		this.orderType = defaultConfig.getOrderType();
	}
	
	public void restoreVisible() {
		userConfiguration.removeProperty(getUri("visible"));
		this.visible = defaultConfig.getVisible();
	}
}