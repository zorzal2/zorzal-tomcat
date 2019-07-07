package com.pragma.toolbar.properties;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.pragma.toolbar.config.default_config.DefaultToolbarConfig;

public class CodeFilterTypeFinder implements FilterTypeFinder {

	static Map<Class, String> translation = new HashMap<Class, String>();
	static{
		translation.put(String.class , "filter.type.string.");
		translation.put(Long.class , "filter.type.number.");
		translation.put(Boolean.class , "filter.type.boolean.");		
	}

	public FilterType find(Class typeClazz, String filterType) {
		String prefix = translation.get( typeClazz );
		if(prefix !=null){
			String translatedFilterType = prefix + filterType;
			Iterator iterator = DefaultToolbarConfig.getFilterTypeLibrary().iterator();
			while(iterator.hasNext()){
				FilterType type = (FilterType) iterator.next();
				if(type.getName().equals( translatedFilterType ))
					return type;
			}
		}
		return null;
	}

}
