/*
 * ShortDateWrapper.java
 *
 * Created on November 10, 2006, 4:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.pragma.toolbar.web.decorator;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import com.fontar.data.impl.domain.bean.PersonaBean;


/**
 *
 * @author gboaglio
 */
public class PersonaWrapper implements DisplaytagColumnDecorator {
    public Object decorate(Object columnValue, PageContext pageContext,
        MediaTypeEnum mediaTypeEnum) throws DecoratorException {
    	
        String strDecoratedValue = "";

        if (columnValue instanceof PersonaBean) {            
        	strDecoratedValue = ((PersonaBean) columnValue).getNombre();                     
        }

        return strDecoratedValue;
    }
}
