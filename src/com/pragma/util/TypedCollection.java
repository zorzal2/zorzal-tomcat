package com.pragma.util;

import java.util.Collection;

public interface TypedCollection extends Collection {

	public void add(int index, Object element);

	public boolean add(Object o);

	public boolean addAll(Collection c);

	public boolean addAll(int index, Collection c);

	public Class getType();

	public void setType(Class type);
	
	public boolean canReceive(TypedCollection collection);
	
	public boolean canContain(Object object);
}
