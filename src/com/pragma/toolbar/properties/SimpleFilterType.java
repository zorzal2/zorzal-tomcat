package com.pragma.toolbar.properties;

import com.pragma.toolbar.data.DataType;


/**
 * @author llobeto
 */
public class SimpleFilterType implements FilterType
{
    private static final long serialVersionUID = 1L;
    
    private final String name;
    private final DataType dataType;


    /**
     * private constructor. Use only constants.
     * @param name description of enumerated type
     */
    public SimpleFilterType(String name, DataType dataType)
    {
        this.name = name;
        this.dataType = dataType;
    }

    public int getCode()
    {
        return this.hashCode();
    }

    public String getName()
    {
        return this.name;
    }

    public String toString()
    {
        return getName();
    }

    public DataType getDataType() {
    	return dataType;
    }
}
