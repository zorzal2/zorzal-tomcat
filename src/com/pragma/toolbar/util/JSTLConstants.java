package com.pragma.toolbar.util;
 
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.pragma.toolbar.data.filter.ConfigurableToolbarFilter;

/**
 * Class to reveal java constants to JSTL Expression Language
 * Uses reflection to scan the declared fields of a Constants class
 * Adds these fields to the Map.
 * Map is unmodifiable after initialization.
 */
public class JSTLConstants extends HashMap {
 
    private boolean initialised = false;
    protected static Class[] adaptees = {ConfigurableToolbarFilter.class};
        	
    public JSTLConstants() {
            
            for (int i = 0; i < adaptees.length; i++) {
                Class c = adaptees[i];
		Field[] fields = c.getDeclaredFields();
		for (int j = 0; j < fields.length; j++) {
			Field field = fields[j];
			int modifier = field.getModifiers();
			if (Modifier.isFinal(modifier) && !Modifier.isPrivate(modifier))
				try {
					this.put(field.getName(), field.get(this));
				}
				catch (IllegalAccessException e) {}
		}
            }
            initialised = true;
	}
 
	public void clear() {
		if (!initialised)
			super.clear();
		else
			throw new UnsupportedOperationException("Cannot modify this map");
	}
 
	public Object put(Object key, Object value) {
		if (!initialised)
			return super.put(key, value);
		else
			throw new UnsupportedOperationException("Cannot modify this map");
	}
 
	public void putAll(Map m) {
		if (!initialised)
			super.putAll(m);
		else
			throw new UnsupportedOperationException("Cannot modify this map");
	}
 
	public Object remove(Object key) {
		if (!initialised)
			return super.remove(key);
		else
			throw new UnsupportedOperationException("Cannot modify this map");
	}
        
        public Object get(Object key) {
                return super.get(key);
        }
}