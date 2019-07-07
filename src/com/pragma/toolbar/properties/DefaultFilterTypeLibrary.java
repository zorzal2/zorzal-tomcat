/*
 * FilterTypeEnum.java
 *
 * Created on November 10, 2006, 5:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.pragma.toolbar.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.collections.iterators.ArrayIterator;

import com.pragma.toolbar.data.DataType;


/**
 * Enumeration for order type;
 * @author Ferrara Federico
 * @version $Revision$ ($Author$)
 */
public final class DefaultFilterTypeLibrary implements FilterTypeLibrary
{
   
    //Strings
    public static final HQLFilterType STRING_EQUAL	    = new HQLFilterType("filter.type.string.equal","$property = $filterValue", DataType.STRING);
    public static final HQLFilterType STRING_NOT_EQUAL = new HQLFilterType("filter.type.string.notEqual","$property <> $filterValue", DataType.STRING);
    public static final HQLFilterType CONTAINS 	    = new HQLFilterType("filter.type.string.contains","lower($property) like '%' || lower($filterValue) || '%'", DataType.STRING);
    //Dates
    public static final HQLFilterType DATE_EQUAL	    = new HQLFilterType("filter.type.date.equal", "($property >= $filterValue and $property < $filterValue + 1)", DataType.DATE);
    public static final HQLFilterType DATE_NOT_EQUAL   = new HQLFilterType("filter.type.date.notEqual","($property < to_date(to_char($filterValue, 'DD-MM-YYYY HH:MI:SS AM'), 'DD-MM-YYYY HH:MI:SS AM') or $property >= (to_date(to_char($filterValue, 'DD-MM-YYYY HH:MI:SS AM'), 'DD-MM-YYYY HH:MI:SS AM')+1))", DataType.DATE);
    public static final HQLFilterType BEFORE 	    = new HQLFilterType("filter.type.date.before","($property < to_date(to_char($filterValue, 'DD-MM-YYYY HH:MI:SS AM'), 'DD-MM-YYYY HH:MI:SS AM'))", DataType.DATE);
    public static final HQLFilterType BEFORE_OR_EQUAL  = new HQLFilterType("filter.type.date.beforeOrEqual","($property < (to_date(to_char($filterValue, 'DD-MM-YYYY HH:MI:SS AM'), 'DD-MM-YYYY HH:MI:SS AM')+1))", DataType.DATE);
    public static final HQLFilterType AFTER	    = new HQLFilterType("filter.type.date.after","($property > (to_date(to_char($filterValue, 'DD-MM-YYYY HH:MI:SS AM'), 'DD-MM-YYYY HH:MI:SS AM')+1))", DataType.DATE);
    public static final HQLFilterType AFTER_OR_EQUAL   = new HQLFilterType("filter.type.date.afterOrEqual","($property >= to_date(to_char($filterValue, 'DD-MM-YYYY HH:MI:SS AM'), 'DD-MM-YYYY HH:MI:SS AM'))", DataType.DATE);
    //Numbers
    public static final HQLFilterType NUMBER_EQUAL     = new HQLFilterType("filter.type.number.equal","$property = $filterValue", DataType.NUMBER);
    public static final HQLFilterType NUMBER_NOT_EQUAL = new HQLFilterType("filter.type.number.notEqual","$property <> $filterValue", DataType.NUMBER);
    public static final HQLFilterType NUMBER_LESS 	    = new HQLFilterType("filter.type.number.less","$property < $filterValue", DataType.NUMBER);
    public static final HQLFilterType NUMBER_LESS_OR_EQUAL    = new HQLFilterType("filter.type.number.lessOrEqual","$property <= $filterValue", DataType.NUMBER);
    public static final HQLFilterType NUMBER_GREATER 	    = new HQLFilterType("filter.type.number.greater","$property > $filterValue", DataType.NUMBER);
    public static final HQLFilterType NUMBER_GREATER_OR_EQUAL = new HQLFilterType("filter.type.number.greaterOrEqual","$property >= $filterValue", DataType.NUMBER);
    //Long
    public static final HQLFilterType LONG_EQUAL     = new HQLFilterType("filter.type.long.equal","$property = $filterValue", DataType.LONG);
    public static final HQLFilterType LONG_NOT_EQUAL = new HQLFilterType("filter.type.long.notEqual","$property <> $filterValue", DataType.LONG);
    public static final HQLFilterType LONG_LESS 	    = new HQLFilterType("filter.type.long.less","$property < $filterValue", DataType.LONG);
    public static final HQLFilterType LONG_LESS_OR_EQUAL    = new HQLFilterType("filter.type.long.lessOrEqual","$property <= $filterValue", DataType.LONG);
    public static final HQLFilterType LONG_GREATER 	    = new HQLFilterType("filter.type.long.greater","$property > $filterValue", DataType.LONG);
    public static final HQLFilterType LONG_GREATER_OR_EQUAL = new HQLFilterType("filter.type.long.greaterOrEqual","$property >= $filterValue", DataType.LONG);
    //Boolean
    public static final FilterType BOOL_EQUAL     = new HQLFilterType("filter.type.boolean.equal","$property = $filterValue", DataType.BOOLEAN);  	//new SimpleFilterType(23, "filter.type.boolean.equal", DataType.BOOLEAN);
    //Expression
   // public static final FilterType EXPRESION_COMPLEJA     = new HQLFilterType(23, "filter.type.expresion.compleja","$expresion", DataType.OBJECT);
    //Other
    public static final FilterType EXPRESSION 	    = new SimpleFilterType("filter.type.expression", DataType.OBJECT);
         
	
    private static final FilterTypeLibrary instance = new DefaultFilterTypeLibrary();
    /**
     * array containing all the export types.
     */
    public  final FilterType[] ALL = {
	STRING_EQUAL,
	STRING_NOT_EQUAL,
	CONTAINS,

	DATE_EQUAL,
	DATE_NOT_EQUAL,
	BEFORE,
	BEFORE_OR_EQUAL,
	AFTER,
	AFTER_OR_EQUAL,

	NUMBER_EQUAL,
	NUMBER_NOT_EQUAL,
	NUMBER_LESS,
	NUMBER_LESS_OR_EQUAL,
	NUMBER_GREATER,
	NUMBER_GREATER_OR_EQUAL,

    LONG_EQUAL,
    LONG_NOT_EQUAL,
    LONG_LESS,
    LONG_LESS_OR_EQUAL,
    LONG_GREATER,
    LONG_GREATER_OR_EQUAL,

    BOOL_EQUAL,
	
	EXPRESSION
    };
    /* (non-Javadoc)
	 * @see com.pragma.toolbar.properties.FilterTypeLibrary2#getAll()
	 */
    public  Collection getAll() {
	return Arrays.asList(ALL);
    }

    /* (non-Javadoc)
	 * @see com.pragma.toolbar.properties.FilterTypeLibrary2#fromCode(int)
	 */
    public FilterType fromCode(int key)
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
     * lookup a FilterTypeEnum by an Integer key.
     * @param key Integer code - null safe: a null key returns a null Enum
     * @return FilterTypeEnum or null if no FilterTypeEnum is found with the given key
     */
    public  FilterType fromIntegerCode(Integer key)
    {
        if (key == null)
        {
            return null;
        }

        return fromCode(key.intValue());
    }

    /* (non-Javadoc)
	 * @see com.pragma.toolbar.properties.FilterTypeLibrary2#fromName(java.lang.String)
	 */
    public  FilterType fromName(String code)
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

    /* (non-Javadoc)
	 * @see com.pragma.toolbar.properties.FilterTypeLibrary2#iterator()
	 */
    public  Iterator iterator()
    {
        return new ArrayIterator(ALL);
    }
    
    /* (non-Javadoc)
	 * @see com.pragma.toolbar.properties.FilterTypeLibrary2#byDataType(com.pragma.toolbar.data.DataType)
	 */
    public Collection<FilterType> byDataType(DataType dataType) {
	Collection<FilterType> ret = new ArrayList<FilterType>();
        for (int i = 0; i < ALL.length; i++)
        {
            if (ALL[i].getDataType().equals(dataType))
            {
                ret.add(ALL[i]);
            }
        }
        return ret;
    }
    
    public static FilterTypeLibrary getInstance(){
		return instance;
	}
}
