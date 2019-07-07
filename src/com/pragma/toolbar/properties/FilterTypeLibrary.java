package com.pragma.toolbar.properties;

import java.util.Collection;
import java.util.Iterator;

import com.pragma.toolbar.data.DataType;

public interface FilterTypeLibrary {

	public abstract Collection<FilterType> getAll();

	/**
	 * lookup a FilterTypeEnum by code.
	 * @param key int code
	 * @return FilterTypeEnum or null if no FilterTypeEnum is found with the given key
	 */
	public abstract FilterType fromCode(int key);

	/**
	 * Lookup a FilterTypeEnum by a String key.
	 * @param code String code - null safe: a null key returns a null Enum
	 * @return FilterTypeEnum or null if no FilterTypeEnum is found with the given key
	 */
	public abstract FilterType fromName(String code);

	/**
	 * returns an iterator on all the enumerated instaces.
	 * @return iterator
	 */
	public abstract Iterator iterator();

	public abstract Collection<FilterType> byDataType(DataType dataType);

}