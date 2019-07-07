package com.pragma.data.genericdao.finder.impl;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Representa una lista de argumentos pasados a un metodo. Pueden guardarse
 * posicionalmente o por nombre.
 * @author llobeto
 *
 */
public class ArgumentMap implements Iterable<Object> {
	private Map<String, Object> argMap = new LinkedHashMap<String, Object>();
	private Object[] args = null;
	private boolean named = true;
	private int unusedId = 0;
	/**
	 * Devuelve true si los todos los argumentos se han ingresado con
	 * nombre. Si alguno no hubiera sido agregado por nombre devuelve
	 * false.
	 * @return
	 */
	public boolean areNamed() {
		return named;
	}
	/**
	 * Agrega un parametro con nombre. Si se suministra el nombre null
	 * tiene el mismo efecto que add(Object).
	 * @param name
	 * @param value
	 */
	public void add(String name, Object value) {
		if(name==null) add(value); 
		else argMap.put(name, value);
		args = null;
	}
	/**
	 * Agrega un parametro posicional. Solo podra ser
	 * accesible
	 * @param value
	 */
	public void add(Object value) {
		baseAdd(value);
		named = false;
		args = null;
	}
	private void baseAdd(Object value) {
		argMap.put("Unnamed argument "+unusedId, value);
		unusedId++;
	}
	public void add(Object... objects) {
		if(objects!=null && objects.length>0) {
			for(Object object : objects) {
				baseAdd(object);
			}
			named = false;
			args = null;
		}
	}
	public Object getNamedArgument(String name) {
		return argMap.get(name);
	}
	public Object getArgument(int index) {
		return arguments()[index];
	}
	public Object[] arguments() {
		if(args==null) {
			args = argMap.values().toArray();
		} 
		return args;
	}
	public Iterator<Object> iterator() {
		return argMap.values().iterator();
	}
	public int size() {
		return argMap.size();
	}
	public boolean hasNamedArgument(String name) {
		return argMap.containsKey(name);
	}
}
