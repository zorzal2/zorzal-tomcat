package com.pragma.util;



/**
 * Declaraciones e implementaciones de condiciones utiles para
 * pasar a metodos que evaluan una condicion sobre un objeto.
 * Ejemplo:
 * 		String[] emails={
 * 						"hugo@yahoo.com",
 * 						"paco.com",
 * 						"luis@gmail.com"};
 * 
 * 		//Mails que contienen "@"
 * 		Conditions cond=Condition.contains("@");
 * 		String[] valisMails = Utils.extractElementsSuch(cond)
 * 
 * 		////Mails que NO CONTIENEN "@"
 * 		invalidMails = Utils.extractElementsSuch(
 * 									Conditions.Not(cond) )
 * @author llobeto
 *
 */
public class Conditions {
    /**
     * condicion sobre un objeto para usarse con 
     * funciones tipo extractElementsSuch.
     * @author llobeto
     *
     */
    public static interface Condition<T> {
    	boolean isTrueFor(T o);
    }
    
    /*
     * Algunas condiciones utiles
     */
    /**
     * Evalua en true para cualquier objeto.
     */
    public static <T> Condition<T> tautology() {
    	return new Tautology<T>();
    }
    private static class Tautology<T> implements Condition<T> {
		public boolean isTrueFor(T o) {
			return true;
		}
    }
    /**
     * Contencion de cadenas
     */
    public static Condition contains(String substring) {
    	return new Contains(substring);
    }
    private static class Contains implements Condition<String> {
    	private String substring;
    	public Contains(String substring) {
    		this.substring=substring;
    	}
		public boolean isTrueFor(String o) {
			return StringUtil.contains(o, this.substring);
		}
    }    
    /**
     * Negacion
     */
    private static class Not<T> implements Condition<T> {
    	private Condition<T> condition;
    	public Not(Condition<T> condition) {
    		this.condition=condition;
    	}
		public boolean isTrueFor(T o) {
			return !this.condition.isTrueFor(o);
		}    	
    }
    public static <T> Condition<T> not(Condition<T> cond) {
    	return new Not<T>(cond);
    }
    /**
     * El objeto es nulo o es una cadena vacia.
     */
    private static Condition<String> IsEmptyCondition = new IsEmpty();
    private static class IsEmpty implements Condition<String> {
		public boolean isTrueFor(String o) {
			return o==null || o.equals("");
		}    	
    }
    public static Condition<String> isEmpty() {
    	return IsEmptyCondition;
    }
    /**
     * El objeto es nulo o es una cadena vacia o es un 0.
     */
    private static Condition<Object> IsEmptyOrZeroCondition = new IsEmptyOrZero();
    private static class IsEmptyOrZero implements Condition<Object> {
		public boolean isTrueFor(Object o) {
			return 
				o==null || 
				o.equals("") || 
				o.toString().equals("0") || 
				o.toString().equals("0.0");
		}    	
    }
    public static Condition<Object> isEmptyOrZero() {
    	return IsEmptyOrZeroCondition;
    }
    /**
     * El objeto no es nulo ni es una cadena vacia
     */
    private static Condition<String> IsNotEmptyCondition = not(isEmpty());
    public static Condition<String> isNotEmpty() {
    	return IsNotEmptyCondition;
    }
	public static Condition<Object> isNotNull() {
		return NotNullCondition;
	}
	private static Condition<Object> NotNullCondition = new Condition<Object>(){
		public boolean isTrueFor(Object o) {
			return o!=null;
		}
	};
}
