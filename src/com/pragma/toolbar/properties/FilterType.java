package com.pragma.toolbar.properties;

import java.io.Serializable;

import com.pragma.toolbar.data.DataType;


/**
 * Enumeration for order type;
 * @author llobeto, Ferrara Federico
 * @version $Revision: 1.1 $ ($Author: llobeto $)
 */
public interface FilterType extends Serializable
{
    /**
     * returns the int code.
     * @return int code
     */
    public int getCode();

    /**
     * returns the description.
     */
    public String getName();

    public DataType getDataType();
}
