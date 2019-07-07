/*
 * ToolbarFilter.java
 *
 * Created on 3 de noviembre de 2006, 10:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pragma.toolbar.data.filter;

import com.pragma.toolbar.properties.HQLFilterType;
import com.pragma.util.StringUtil;

/**
 *
 * @author llobeto
 */
public class HQLPropertyBasedToolbarFilter extends PropertyBasedToolbarFilter {

    private static final long serialVersionUID = 1L;

    /** Creates a new instance of ToolbarFilter */
    protected HQLPropertyBasedToolbarFilter(String property, HQLFilterType filterTypeEnum, Object value, Class classType) {
    	super(property, filterTypeEnum, value, classType);
    }

    /** Creates a new instance of ToolbarFilter */
    public HQLPropertyBasedToolbarFilter(String property, HQLFilterType filterTypeEnum, Class classType) {
    	super(property, filterTypeEnum, classType);
    }

    public String getHqlForObject(String o) {
    	HQLFilterType filterType = (HQLFilterType) getFilterType();
		return StringUtil.replace(
			StringUtil.replace(
				filterType.getHqlTemplate(),
				"$filterValue",
				":"+varName()
			),
			"$property",
			o + "." + getProperty()
		);
    }
}
