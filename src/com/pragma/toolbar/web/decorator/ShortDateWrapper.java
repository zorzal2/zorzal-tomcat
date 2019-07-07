/*
 * LongDateWrapper.java
 *
 * Created on November 10, 2006, 4:28 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pragma.toolbar.web.decorator;


import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;
import org.displaytag.decorator.ColumnDecorator;
import org.displaytag.exception.DecoratorException;

/**
 * Simple column decorator which formats a date.
 * @author epesh
 * @version $Revision $ ($Author $)
 */
public class ShortDateWrapper implements ColumnDecorator
{
    /**
     * FastDateFormat used to format the date object.
     */
    private FastDateFormat dateFormat = FastDateFormat.getInstance("dd/MM/yyyy"); //$NON-NLS-1$

    public String decorate(Object columnValue) throws DecoratorException {
        if (columnValue instanceof Date) {
          Date date = (Date) columnValue;
          return this.dateFormat.format(date);
        } else{
          return String.valueOf(columnValue); 
        }
    }
}

