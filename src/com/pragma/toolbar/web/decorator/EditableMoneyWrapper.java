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

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import com.fontar.web.decorator.Util.EditableMoney;
import com.fontar.web.decorator.link.api.AbstractLink;
import com.pragma.util.StringUtil;


/**
 *
 * @author DBerkovics
 */
public class EditableMoneyWrapper implements DisplaytagColumnDecorator {
    public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum mediaTypeEnum) throws DecoratorException {
    	if(columnValue == null) 
    		return "";
    	
		BigDecimal monto = ((EditableMoney)columnValue).getMonto();
		AbstractLink link = ((EditableMoney)columnValue).getLink();;
		String montoAux =  "";
		String linkAux = "";	
		if (monto != null){
			if(!mediaTypeEnum.equals(MediaTypeEnum.fromName("excel"))){
				montoAux = StringUtil.formatMoneyForPresentation(((EditableMoney)columnValue).getMonto());
			}else{
				montoAux = monto.toString();
			}
		}
		if(link!=null){
			linkAux = link.displayValue();
		}
		
		if(montoAux != "")
			montoAux += " ";
		
		return montoAux + linkAux;
    }
}
