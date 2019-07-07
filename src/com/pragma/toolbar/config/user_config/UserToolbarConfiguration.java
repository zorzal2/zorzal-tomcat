package com.pragma.toolbar.config.user_config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.pragma.hibernate.BeforeQueryInvocationHandler;
import com.pragma.toolbar.config.ColumnConfiguration;
import com.pragma.toolbar.config.ToolbarConfiguration;
import com.pragma.toolbar.config.default_config.DefaultToolbarConfig;
import com.pragma.toolbar.data.ToolbarOrder;
import com.pragma.toolbar.properties.OrderTypeEnum;
import com.pragma.util.CollectionUtils;
import com.pragma.util.Conditions.Condition;

public class UserToolbarConfiguration implements ToolbarConfiguration {
	
	private ToolbarConfiguration defaultConfig;
	private UserConfiguration userConfiguration;
	
	private String userId;
	
	//TODO Implementar algo tipo factory
	public static UserToolbarConfiguration get(String userId, String toolbarId) {
		return new UserToolbarConfiguration(
				new DefaultToolbarConfig(toolbarId), 
				userId
		);
	}
	
	private UserToolbarConfiguration(ToolbarConfiguration defaultConfig, String userId) {
		this.defaultConfig = defaultConfig;
		this.userId = userId;
		this.userConfiguration = UserConfiguration.forUser(userId);
	}
	
	public List getActions() {
		return defaultConfig.getActions();
	}
	
	public List getButtons() {
		return defaultConfig.getButtons();
	}
	
	public List getColumns() {
		List defaultColumnList = defaultConfig.getColumns();
		List retList = new ArrayList();
		for (Iterator iter = defaultColumnList.iterator(); iter.hasNext();) {
			ColumnConfiguration colConfig = (ColumnConfiguration) iter.next();
			retList.add(
					new UserColumnConfiguration(userId , colConfig, getUri())
			);
		}
		return retList;
	}
	
	public String getDecorator() {
		return defaultConfig.getDecorator();
	}
	
	public Integer getPageSize() {
		Integer userPageSize= (Integer)userConfiguration.getProperty(getUri("pageSize"));
		if (userPageSize == null){
			return defaultConfig.getPageSize();
		} else {
			return userPageSize;
		}
	}
	
	public void setPageSize(Integer pageSize) {
		userConfiguration.setProperty(getUri("pageSize"),pageSize);
	}
	
	public Boolean getPaging() {
		return defaultConfig.getPaging();
	}
	
	public String getStyleClass() {
		return defaultConfig.getStyleClass();
	}
	
	public String getId() {
		return defaultConfig.getId();
	}
	
	private String getUri() {
		return "toolbar["+getId()+"]";
	}
	
	private String getUri(String propertyName) {
		return "toolbar["+getId()+"]." + propertyName;
	}
	
	public List getColumnsOrders() {
		List ret = new ArrayList();
		for (Iterator iter = getColumns().iterator(); iter.hasNext();) {
			UserColumnConfiguration column = (UserColumnConfiguration) iter.next();
			Integer columnOrder = column.getOrder();
			int colOrderInt = (columnOrder==null)? 0 : columnOrder.intValue(); 
			OrderTypeEnum columnOrderType = column.getOrderType();
			if(colOrderInt != 0 && columnOrderType != null) {
				ret.add(
						new ToolbarOrder(
								column.getOrderProperty(),
								columnOrder,
								columnOrderType
						)
				);
			}
		}
		return ret;
	}
	
	public List getVisibleColumns() {
		Condition isVisible = new Condition() {
			public boolean isTrueFor(Object o) {
				UserColumnConfiguration column = (UserColumnConfiguration)o;
				return column.getVisible().booleanValue();
			}};
			
			return (List)CollectionUtils.extractElementsSuch(getColumns(), isVisible);
	}
	
	public String getActionsTitle() {
		return defaultConfig.getActionsTitle();
	}
	public BeforeQueryInvocationHandler getBeforeQueryInvocationHandler() {
		return defaultConfig.getBeforeQueryInvocationHandler();
	}
}
