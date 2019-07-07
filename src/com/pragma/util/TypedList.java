package com.pragma.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class TypedList extends ArrayList implements TypedCollection {

	private static final long serialVersionUID = 1L;

	private Class type=Object.class;
	
	public TypedList(Class type) {
		super();
		this.setType(type);
	}

	public TypedList(int initialCapacity, Class type) {
		super(initialCapacity);
		this.setType(type);
	}
	
	public TypedList(Collection c, Class type) {
		super(c);
		checkCollectionCompatibility(c);
	}

	private void checkCollectionCompatibility(Collection c) {
		for (Iterator iter = c.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if(!getType().isAssignableFrom(element.getClass())) {
				throw new IllegalArgumentException("I cant't contain objects of type different to "+getType().getName()+". "+element.getClass().getName()+" given.");
			}			
		}
	}
	private void checkParameter(Object element) {
		if(!getType().isAssignableFrom(element.getClass()))
			throw new IllegalArgumentException("I cant't contain objects of type different to "+getType().getName()+". "+element.getClass().getName()+" given.");
	}

	public void add(int index, Object element) {
		checkParameter(element);
		super.add(index, element);
	}

	public boolean add(Object o) {
		checkParameter(o);
		return super.add(o);
	}

	public boolean addAll(Collection c) {
		checkCollectionCompatibility(c);
		return super.addAll(c);
	}

	public boolean addAll(int index, Collection c) {
		checkCollectionCompatibility(c);
		return super.addAll(index, c);
	}

	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}

	public boolean canContain(Object object) {
		if(getType().isAssignableFrom(object.getClass())) return true;
		return false;
	}

	public boolean canReceive(TypedCollection collection) {
		try {
			checkCollectionCompatibility(collection);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
