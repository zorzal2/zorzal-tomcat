package com.pragma.util;

import java.util.Enumeration;
import java.util.Iterator;

public class EnumerationAdapter<T> implements Iterable<T>, Iterator<T> {

	private Enumeration<T> enumeration; 
	
	@SuppressWarnings("unchecked")
	public EnumerationAdapter(Enumeration enumeration) {
		this.enumeration = enumeration;
	}

	public Iterator<T> iterator() {
		return this;
	}

	public boolean hasNext() {
		return enumeration.hasMoreElements();
	}

	public T next() {
		return enumeration.nextElement();
	}

	public void remove() {
		throw new RuntimeException("Not implemented");
	}

}
