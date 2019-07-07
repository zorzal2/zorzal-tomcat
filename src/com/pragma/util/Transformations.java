package com.pragma.util;


/**
 * Declaraciones e implementaciones de transformaciones utiles. para
 * @author llobeto
 *
 */
public class Transformations {
    /**
     * @author llobeto
     *
     */
    public static interface Transformation<SrcType, DestType> {
    	DestType applyTo(SrcType o);
    }
    
    /*
     * Algunas transformaciones utiles
     */
    /**
     * Devuelve lo que recibe
     */
    public static <S> Transformation<S, S> identity() {
    	return new Identity<S>();
    }
    private static class Identity<S> implements Transformation<S, S> {

		public S applyTo(S o) {
			return o;
		}

    }
}
