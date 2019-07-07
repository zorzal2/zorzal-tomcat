package com.pragma.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

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
	public static <T> Collection<T> extractElementsSuch(Collection<T> collection, Condition<T> condition) {
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
    public static <T> T oneThatSatisfy(Collection<T> collection, Condition<T> condition) {
    	for(T element : collection) {
    		if(condition.isTrueFor(element)) return element;
    	}
    	throw new NoSuchElementException("No element holds given condition");
    }

    public static <T> T oneThatSatisfy(T[] array, Condition<T> condition) {
    	return oneThatSatisfy(Arrays.asList(array), condition);
    }
    
    public static <T> boolean anySatisfy(Collection<T> collection, Condition<T> condition) {
    	try {
    		oneThatSatisfy(collection, condition);
    		return true;
    	} catch(NoSuchElementException exception) {
    		return false;
    	}
    }

    public static <T> boolean anySatisfy(T[] array, Condition<T> condition) {
    	try {
    		oneThatSatisfy(array, condition);
    		return true;
    	} catch(NoSuchElementException exception) {
    		return false;
    	}
    }

    public static <T> boolean noneSatisfy(Collection<T> collection, Condition<T> condition) {
    	try {
    		oneThatSatisfy(collection, condition);
    		return false;
    	} catch(NoSuchElementException exception) {
    		return true;
    	}
    }

    public static <T> boolean noneSatisfy(T[] array, Condition<T> condition) {
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
	public static <S, D> Collection<D> transform(Collection<S> collection, Transformation<S, D> transformation) {
    	Collection<D> ret = new ArrayList<D>();
    	for(S element : collection) {
    		ret.add(transformation.applyTo(element));
    	}
    	return ret;
    }
	/**
	 * Devuelve una colección con los elementos de la dada pero sin los elementos null.
	 * Respeta el mismo orden de elementos de la coleccion original.
	 * @param <T>
	 * @param collection
	 * @return
	 */
	public static <T> Collection<T> clean(Collection<T> collection) {
    	Collection<T> ret;
    	if(collection instanceof Set) {
    		ret = new HashSet<T>(collection);
    		ret.remove(null);
    	} else {
    		ret = new ArrayList<T>(collection);
    		boolean remove = true;
    		while(remove) {
    			remove = ret.remove(null);
    		}
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
    /**
     * Devuelve un arreglo que contiene todos los elementos de t1 y
     * todos los elementos de t2. Cada elemento aparece en el arreglo
     * retornado una sola vez. No puede hacerse ninguna suposicion 
     * sobre el orden en que los elementos seran devueltos. 
     * @param t1
     * @param t2
     * @return
     */
    @SuppressWarnings("unchecked")
	public static String[] union(String[] t1, String[] t2) {
    	Set<String> set = new HashSet<String>();
    	for(String t : t1) set.add(t);
    	for(String t : t2) set.add(t);
    	return set.toArray(new String[]{});
    }
    
    public static <T> Collection<T> collectionWith(T...ts) {
    	return listWith(ts);
    }
    public static <T> List<T> listWith(T...ts) {
    	return new ArrayList<T>(Arrays.asList(ts));
    }
    public static <T> Set<T> setWith(T...ts) {
    	return new HashSet<T>(Arrays.asList(ts));
    }
    public static <T> Set<T> linkedSetWith(T...ts) {
    	return new LinkedHashSet<T>(Arrays.asList(ts));
    }
    /**
     * Crea un mapa con los objetos dados asumiendo que estan intercalados
     * claves y valores 
     * @param <K>
     * @param <V>
     * @param keysAndValues
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> mapWith(Object...keysAndValues) {
    	if(keysAndValues.length % 2 !=0) throw new IllegalArgumentException("Debe haber igual cantidad de claves y valores.");
    	Map<K, V> ret = new HashMap<K, V>(); 
    	for(int i=0; i<keysAndValues.length;i++) {
    		K k = (K) keysAndValues[i++];
    		V v = (V) keysAndValues[i];
    		ret.put(k, v);
    	}
    	return ret;
    }
    
    
    
    public static class ObjectsToAdd<T> {
    	T[] objects;
    	protected ObjectsToAdd(T...ts) {
    		objects = ts;
    	}
    	public List<T> to(List<T> list) {
    		for(T t : objects) {
    			list.add(t);
    		}
    		return list;
    	}
    	public Set<T> to(Set<T> set) {
    		for(T t : objects) {
    			set.add(t);
    		}
    		return set;
    	}
    	public Collection<T> to(Collection<T> collection) {
    		for(T t : objects) {
    			collection.add(t);
    		}
    		return collection;
    	}
    	@SuppressWarnings("unchecked")
		public <K, V> Map<K, V> to(Map<K, V> map) {
        	if(objects.length % 2 !=0) throw new IllegalArgumentException("Debe haber igual cantidad de claves y valores.");
        	for(int i=0; i<objects.length;i++) {
        		K k = (K) objects[i++];
        		V v = (V) objects[i];
        		map.put(k, v);
        	}
        	return map;
    	}
    }
    /**
     * Para agregar objetos a una coleccion:
     * CollectionUtils.add("Hugo", "Paco", "Luis").to(myCollection);
     * @param <T>
     * @param ts
     * @return
     */
    public static <T> ObjectsToAdd<T> add(T...ts) {
    	return new ObjectsToAdd<T>(ts);
    }
    
    public static <T> boolean contains(T[] ts, T t) {
    	for(T t1 : ts) {
    		if(t.equals(t1)) return true;
    	}
    	return false;
    }
    private static class IterableFromIterator<T> implements Iterable<T> {
    	private Iterator<T> iterator;
    	
		public IterableFromIterator(Iterator<T> iterator) {
			this.iterator = iterator;
		}
		public Iterator<T> iterator() {
			return iterator;
		}
    }
    @SuppressWarnings("unchecked")
	public static <T> Iterable<T> iterable(Iterator<T> iterator) {
    	return new IterableFromIterator(iterator);
    }
    
    public static <T> Set<T> union(Collection<T> c1, Collection<T> c2) {
    	Set<T> ret = new HashSet<T>(c1);
    	ret.addAll(c2);
    	return ret;
    }
    
    public static <T> Set<T> intersect(Collection<T> c1, Collection<T> c2) {
    	Set<T> ret = new HashSet<T>(c1);
    	ret.retainAll(c2);
    	return ret;
    }
    
    public static boolean intersects(Collection<?> c1, Collection<?> c2) {
    	Set<Object> ret = new HashSet<Object>(c1);
    	ret.retainAll(c2);
    	return !ret.isEmpty();
    }
    
    public static <T> T getAny(List<T> list) {
    	if(list==null || list.size()==0) return null;
    	return list.get(0);
    }
    
    public static <T> T getAny(Iterable<T> iterable) {
    	if(iterable==null) return null;
    	Iterator<T> iterator = iterable.iterator();
    	if(!iterator.hasNext()) return null;
    	return iterator.next();
    }
}
