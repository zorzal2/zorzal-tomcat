package com.pragma.toolbar.properties;

import com.pragma.toolbar.data.DataType;


/**
 * Enumeration for order type;
 * @author llobeto, Ferrara Federico
 * @version $Revision: 1.2 $ ($Author: dberkovics $)
 */
public class HQLFilterType extends SimpleFilterType
{
    private static final long serialVersionUID = 1L;

    private final String hqlCode;
    
    /**
     * private constructor. Use only constants.
     * @param name description of enumerated type
     */
    public HQLFilterType(String name, String hql, DataType dataType)
    {
    	super(name, dataType);
        this.hqlCode  = hql;
    }

    public String getHqlTemplate() {
        return hqlCode;
    }    
}
