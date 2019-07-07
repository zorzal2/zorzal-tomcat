package com.fontar.util;

public class Util {

	public static boolean isBlank(String text) {
		return text == null || text.trim().equals("");
	}

	/**
	 * Chequea si un string es la representación de un entero mayor o igual a cero.
	 * 
	 */
	public static boolean isPositiveInteger(String text) {
    	boolean isPositiveInteger = true;
    	
    	try {
    		int textInt = Integer.parseInt(text);
    		
    		if (textInt < 0) {
    			isPositiveInteger = false;
    		}
    	}
    	catch(Exception e) {
    		isPositiveInteger = false;
    	}
    	
    	return isPositiveInteger ;
    }
	
	public static boolean notNullEquals(String string, String anotherString){
		return !isBlank( string) && !isBlank(anotherString) && string.equals( anotherString );
		
	}
	
	public static void debug(Object object) {
		System.out.println(String.valueOf(object));
	}
}
