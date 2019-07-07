/*
 * ShortDateWrapper.java
 *
 * Created on November 10, 2006, 4:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.pragma.toolbar.web.decorator;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import com.pragma.util.StringUtil;


/**
 *
 * @author gboaglio
 */
public class MoneyWrapper implements DisplaytagColumnDecorator {
    public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum mediaTypeEnum) throws DecoratorException {
        
    	if (columnValue instanceof Number) {
    		if(mediaTypeEnum.equals(MediaTypeEnum.EXCEL)){
    			return columnValue;
    		}else{
    			return StringUtil.formatMoneyForPresentation((Number)columnValue);
    		}
        } else {
            return columnValue;
        }
    	
    	
    }
}
