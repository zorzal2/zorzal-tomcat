package com.pragma.data.genericdao.finder;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import com.pragma.data.genericdao.finder.impl.ArgumentMap;

public interface FinderExecutor<T> {
	/**
	 * Execute a finder method with the appropriate arguments
	 */
	List<T> executeFinder(Method method, ArgumentMap queryArgs);

	Iterator<T> iterateFinder(Method method, ArgumentMap queryArgs);

	Object executeSelect(Method method, ArgumentMap queryArgs);
	// ScrollableResults scrollFinder(Method method, Object[] queryArgs);
}
