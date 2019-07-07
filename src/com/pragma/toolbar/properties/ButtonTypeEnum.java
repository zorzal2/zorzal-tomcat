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
public final class ButtonTypeEnum
{
    public static final ButtonTypeEnum COLUMNS_VISIBILITY   = new ButtonTypeEnum(1, "columnsVisibility");
    public static final ButtonTypeEnum COLUMNS_FILTER       = new ButtonTypeEnum(2, "columnsFilter");
    public static final ButtonTypeEnum EXPORT_PDF           = new ButtonTypeEnum(3, "exportPDF");
    public static final ButtonTypeEnum EXPORT_EXCEL         = new ButtonTypeEnum(4, "exportEXCEL");
    public static final ButtonTypeEnum EXPORT_TXT           = new ButtonTypeEnum(5, "exportTXT");
    
    /**
     * array containing all the export types.
     */
    public static final ButtonTypeEnum[] ALL = {COLUMNS_VISIBILITY, COLUMNS_FILTER, EXPORT_PDF, EXPORT_EXCEL, EXPORT_TXT};

    /**
     * Code; this is the primary key for these objects.
     */
    private final int enumCode;

    /**
     * description.
     */
    private final String enumName;
    
    /**
     * private constructor. Use only constants.
     * @param code int code
     * @param name description of enumerated type
     */
    private ButtonTypeEnum(int code, String name)
    {
        this.enumCode = code;
        this.enumName = name;
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
     * lookup a ButtonTypeEnum by key.
     * @param key int code
     * @return ButtonTypeEnum or null if no ButtonTypeEnum is found with the given key
     */
    public static ButtonTypeEnum fromCode(int key)
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
     * lookup a ButtonTypeEnum by an Integer key.
     * @param key Integer code - null safe: a null key returns a null Enum
     * @return ButtonTypeEnum or null if no ButtonTypeEnum is found with the given key
     */
    public static ButtonTypeEnum fromIntegerCode(Integer key)
    {
        if (key == null)
        {
            return null;
        }

        return fromCode(key.intValue());
    }

    /**
     * Lookup a ButtonTypeEnum by a String key.
     * @param code String code - null safe: a null key returns a null Enum
     * @return ButtonTypeEnum or null if no ButtonTypeEnum is found with the given key
     */
    public static ButtonTypeEnum fromName(String code)
    {
        for (int i = 0; i < ALL.length; i++)
        {
            if (ALL[i].getName().equals(code))
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
    
    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return new HashCodeBuilder(1123997057, -1289836553).append(this.enumCode).toHashCode();
    }



}
