package com.fontar.toolbar.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.fontar.data.EstadosAnulados;
import com.pragma.toolbar.data.DataType;
import com.pragma.toolbar.properties.DefaultFilterTypeLibrary;
import com.pragma.toolbar.properties.FilterType;
import com.pragma.toolbar.properties.FilterTypeLibrary;
import com.pragma.toolbar.properties.HQLFilterType;

public class FilterTypeLibraryDecorator implements FilterTypeLibrary {

	//public static final HQLFilterType FONTAR_STRING_CONTAINS_A = new HQLFilterType("fontar.filter.type.string.containsA","lower($property)	like '%' || lower($filterValue) || '%'", DataType.STRING);
	public static final HQLFilterType FONTAR_OBJECT_SIN_ANULADOS = new HQLFilterType("fontar.filter.type.object.sin_anulados", "(($filterValue = 'all' and $property not in ("
			+ EstadosAnulados.getHQLStringAnulados()  + ")) or ($filterValue = $property))", DataType.OBJECT);
	private static FilterTypeLibrary filterTypeLibrary = DefaultFilterTypeLibrary.getInstance();

	private static FilterTypeLibraryDecorator instance = new FilterTypeLibraryDecorator();

	public final FilterType[] ALLFONTAR = { 
			 FONTAR_OBJECT_SIN_ANULADOS
		//	,FONTAR_STRING_CONTAINS_A

	};

	public Collection<FilterType> getAll() {
		List<FilterType> lista = new ArrayList<FilterType>(Arrays.asList(ALLFONTAR));
		lista.addAll(filterTypeLibrary.getAll());
		return lista;// Arrays.asList(ALLFONTAR) .add() ;
	}

	/**
	 * lookup a FilterTypeEnum by code.
	 * @param key int code
	 * @return FilterTypeEnum or null if no FilterTypeEnum is found with the
	 * given key
	 */
	public FilterType fromCode(int key) {
		for (int i = 0; i < ALLFONTAR.length; i++) {
			if (key == ALLFONTAR[i].getCode()) {
				return ALLFONTAR[i];
			}
		}
		// lookup failed
		return filterTypeLibrary.fromCode(key);
	}

	/**
	 * Lookup a FilterTypeEnum by a String key.
	 * @param code String code - null safe: a null key returns a null Enum
	 * @return FilterTypeEnum or null if no FilterTypeEnum is found with the
	 * given key
	 */
	public FilterType fromName(String code) {
		for (int i = 0; i < ALLFONTAR.length; i++) {
			if (ALLFONTAR[i].getName().equals(code)) {
				return ALLFONTAR[i];
			}
		}
		// lookup failed
		return filterTypeLibrary.fromName(code);
	}

	/**
	 * returns an iterator on all the enumerated instaces.
	 * @return iterator
	 */
	public Iterator iterator() {
		return (this.getAll().iterator());
	}

	public Collection<FilterType> byDataType(DataType dataType) {
		Collection<FilterType> ret = new ArrayList<FilterType>();
		for (int i = 0; i < ALLFONTAR.length; i++) {
			if (ALLFONTAR[i].getDataType().equals(dataType)) {
				ret.add(ALLFONTAR[i]);
			}
		}
		ret.addAll(filterTypeLibrary.byDataType(dataType));
		return ret;
	}

	public static FilterTypeLibrary getInstance() {
		return instance;
	}

}
