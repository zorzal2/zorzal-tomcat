/*
 * ToolbarOrder.java
 *
 * Created on 6 de noviembre de 2006, 11:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pragma.toolbar.data;

import com.pragma.toolbar.properties.OrderTypeEnum;

/**
 *
 * @author fferrara
 */
public class ToolbarOrder {
    
    private String property;
    private OrderTypeEnum orderType;
    private Integer order;
    
    /** Creates a new instance of ToolbarOrder */
    public ToolbarOrder(String property, Integer order, OrderTypeEnum orderType) {
        this.property = property;
        this.order = order;
        this.orderType = orderType;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public OrderTypeEnum getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderTypeEnum orderType) {
        this.orderType = orderType;
    }
    
    public Integer getOrder(){
        return order;
    }
    
}
