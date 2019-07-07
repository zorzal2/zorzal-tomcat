package com.pragma.toolbar.web.decorator;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;


/**
 * Wrapper inocuo.
 * Table.tag elimina el código html de un campo si un
 * campo no tiene decorator. Este wrapper
 * sirve para poder insertar código html mediante un
 * decorator que no hace nada.<br>
 * @author ssanchez
 */
public class DummyWrapper implements DisplaytagColumnDecorator {
    public Object decorate(Object columnValue, PageContext pageContext,
        MediaTypeEnum mediaTypeEnum) throws DecoratorException {
   			return columnValue;
    }
}
