/*
 * FilterTypeEnum.java
 *
 * Created on November 10, 2006, 5:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pragma.toolbar.properties;

import java.util.Iterator;

import org.apache.commons.collections.iterators.ArrayIterator;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Enumeration for order type;
 * @author Ferrara Federico
 * @version $Revision: 1.1 $ ($Author: llobeto $)
 */
public final class OrderTypeEnum
{

    public static final OrderTypeEnum ASC = new OrderTypeEnum(1, "Ascending","asc");
    public static final OrderTypeEnum DESC  = new OrderTypeEnum(2, "Descending","desc");
           

    /**
     * array containing all the export types.
     */
    public static final OrderTypeEnum[] ALL = {ASC, DESC};

    /**
     * Code; this is the primary key for these objects.
     */
    private final int enumCode;

    /**
     * description.
     */
    private final String enumName;
    
    /**
     * HQL Code
     */
    private final String hqlCode;    

    /**
     * private constructor. Use only constants.
     * @param code int code
     * @param name description of enumerated type
     */
    private OrderTypeEnum(int code, String name, String hql)
    {
        this.enumCode = code;
        this.enumName = name;
        this.hqlCode  = hql;
    }

    /**
     * returns the int code.
     * @return int code
     */
    public int getCode()
    {
        return this.enumCode;
    }

    /**
     * returns the description.
     * @return String description of the sort order ("ascending" or "descending")
     */
    public String getName()
    {
        return this.enumName;
    }

    /**
     * lookup a OrderTypeEnum by key.
     * @param key int code
     * @return OrderTypeEnum or null if no OrderTypeEnum is found with the given key
     */
    public static OrderTypeEnum fromCode(int key)
    {
        for (int i = 0; i < ALL.length; i++)
        {
            if (key == ALL[i].getCode())
            {
                return ALL[i];
            }
        }
        // lookup failed
        return null;
    }

    /**
     * lookup a OrderTypeEnum by an Integer key.
     * @param key Integer code - null safe: a null key returns a null Enum
     * @return OrderTypeEnum or null if no OrderTypeEnum is found with the given key
     */
    public static OrderTypeEnum fromIntegerCode(Integer key)
    {
        if (key == null)
        {
            return null;
        }

        return fromCode(key.intValue());
    }

    /**
     * Lookup a OrderTypeEnum by a String key. Case insenstive.
     * @param code String code - null safe: a null key returns a null Enum
     * @return OrderTypeEnum or null if no OrderTypeEnum is found with the given key
     */
    public static OrderTypeEnum fromName(String code)
    {
        for (int i = 0; i < ALL.length; i++)
        {
            if (ALL[i].getName().equalsIgnoreCase(code))
            {
                return ALL[i];
            }
        }
        // lookup failed
        return null;
    }

    /**
     * returns an iterator on all the enumerated instaces.
     * @return iterator
     */
    public static Iterator iterator()
    {
        return new ArrayIterator(ALL);
    }

    /**
     * returns the enumeration description.
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return getName();
    }

    /**
     * Only a single instance of a specific enumeration can be created, so we can check using ==.
     * @param o the object to compare to
     * @return hashCode
     */
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }

        return false;
    }
    
    public String getHqlCode() {
        return hqlCode;
    }    

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return new HashCodeBuilder(1123997057, -1289836553).append(this.enumCode).toHashCode();
    }



}
