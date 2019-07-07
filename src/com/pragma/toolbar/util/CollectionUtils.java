package com.pragma.toolbar.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;

import com.pragma.util.Conditions.Condition;
import com.pragma.util.Transformations.Transformation;

/**
 * Funciones útiles referentes a colecciones y arreglos
 * @author llobeto
 *
 */
public class CollectionUtils {
	/* Condiciones */
    /**
     * Toma una coleccion y una condicion y devuelve otra
     * coleccion del mismo tipo que contiene todos los
     * elementos que cumplen con la condicion.
     */
	public static <T> Collection<T> extractElementsSuch(Collection<T> collection, Condition condition) {
    	Collection<T> ret = compatibleWith(collection);

    	for (T element : collection) {
			if(condition.isTrueFor(element)) ret.add(element);
		}
    	return ret;
	}

    /**
     * Devuelve un objeto de una coleccion que cumpla la
     * condicion dada o null si no hay objeto que la cumpla.
     * @param collection
     * @param condition
     * @return
     */
    public static <T> T oneThatSatisfy(Collection<T> collection, Condition condition) {
    	for(T element : collection) {
    		if(condition.isTrueFor(element)) return element;
    	}
    	throw new NoSuchElementException("No element holds given condition");
    }

    public static Object oneThatSatisfy(Object[] array, Condition condition) {
    	return oneThatSatisfy(Arrays.asList(array), condition);
    }
    
    public static <T> boolean anySatisfy(Collection<T> collection, Condition condition) {
    	try {
    		oneThatSatisfy(collection, condition);
    		return true;
    	} catch(NoSuchElementException exception) {
    		return false;
    	}
    }

    public static boolean anySatisfy(Object[] array, Condition condition) {
    	return oneThatSatisfy(array, condition)!=null;
    }

    public static <T> boolean noneSatisfy(Collection<T> collection, Condition condition) {
    	try {
    		oneThatSatisfy(collection, condition);
    		return false;
    	} catch(NoSuchElementException exception) {
    		return true;
    	}
    }

    public static boolean noneSatisfy(Object[] array, Condition condition) {
    	try {
    		oneThatSatisfy(array, condition);
    		return false;
    	} catch(NoSuchElementException exception) {
    		return true;
    	}
    }    

    /* Transformaciones */
    /**
     * Toma una coleccion y una transformacion y devuelve otra
     * coleccion que contiene todos los
     * elementos resultantes de transformar los de la coleccion original.
     * @throws Exception 
     */
	@SuppressWarnings("unchecked")
	public static <T> Collection<T> transform(Collection<T> collection, Transformation transformation) {
    	Collection<T> ret = compatibleWith(collection);
    	for(T element : collection) {
    		ret.add((T)transformation.applyTo(element));
    	}
    	return ret;
    }

    /**
     * Devuelve, si es posible una coleccion nueva de la misma 
     * clase que la dada. Si no es posible devuelve un ArrayList.
     * @param collection
     * @return coleccion compatible
     */
    @SuppressWarnings("unchecked")
	public static <T> Collection<T> compatibleWith(Collection<T> collection) {
	try {
	    return (Collection<T>)collection.getClass().newInstance();
	} 
	catch (InstantiationException exception) {}
	catch (IllegalAccessException exception) {}
	return new ArrayList<T>();
    }
}
