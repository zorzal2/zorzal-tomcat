package com.pragma.toolbar.data;

import java.util.Collection;
import java.util.Iterator;

import com.pragma.toolbar.config.ColumnConfiguration;
import com.pragma.toolbar.config.ToolbarConfiguration;
import com.pragma.toolbar.config.user_config.UserToolbarConfiguration;

//TODO: interfacear
public class ToolbarSortCriteriaPersister {
	private ToolbarConfiguration toolbarConfiguration;
	
	public ToolbarSortCriteriaPersister(String userId, String toolbarId) {
		toolbarConfiguration = UserToolbarConfiguration.get(userId, toolbarId);
	}
	
	public void setSortCriteria(Collection toolbarSortCriterionCollection) {
		for (Iterator iter = toolbarConfiguration.getColumns().iterator(); iter
		.hasNext();) {
			ColumnConfiguration column = (ColumnConfiguration) iter.next();
			
			//busco si hay un orden asociado a la columna
			ToolbarOrder toolbarOrder = null;
			for (Iterator iterator = toolbarSortCriterionCollection.iterator(); iterator.hasNext();) {
				ToolbarOrder order = (ToolbarOrder) iterator.next();
				if(order.getProperty().equals(column.getOrderProperty())) {
					toolbarOrder = order;
					break;
				}
			}
			if(toolbarOrder==null) {
				column.setOrder(new Integer(0));
			} else {
				column.setOrder(toolbarOrder.getOrder());
				column.setOrderType(toolbarOrder.getOrderType());
			}
		}
	}
	
	public Collection getSortCriteria() {
		return toolbarConfiguration.getColumnsOrders();
	}
}
