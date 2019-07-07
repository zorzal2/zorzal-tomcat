/*
 * ShortDateWrapper.java
 *
 * Created on November 10, 2006, 4:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pragma.toolbar.web.decorator;

import org.displaytag.decorator.ColumnDecorator;
import org.displaytag.exception.DecoratorException;

/**
 *
 * @author gboaglio
 */
public class BooleanWrapper implements ColumnDecorator{
    
    public String decorate(Object columnValue) throws DecoratorException {
        String strDecoratedValue = String.valueOf(columnValue);
                        
        if (columnValue instanceof Boolean) 
        {                                        
          if ( ((Boolean)columnValue).booleanValue() ) {
              strDecoratedValue = "SI";
          }          
          else {          
              strDecoratedValue = "NO";
          }          
        }

        return strDecoratedValue;
    }
    
}