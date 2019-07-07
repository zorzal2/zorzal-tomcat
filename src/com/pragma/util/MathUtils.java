package com.pragma.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MathUtils {

	private static double round(double value, int decimals) {
		double coefficient = Math.pow(10, decimals);
		return Math.round(value * coefficient) / coefficient;
	}

	public static double round(Number value, int decimals) {
		return round(value.doubleValue(), decimals);
	}
	
	public static String formatNumber(Number value) {
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");
		return decimalFormat.format(round(value, 2));
	}
	
	public static String formatTwoPlaces(BigDecimal value) {
		DecimalFormat decimalFormat = new DecimalFormat("###,##0.00");
		
		if (value == null) 
			value = BigDecimal.ZERO;
		
		return decimalFormat.format(value);
	}
	
	public static double floor(double value, int decimals) {
		double coefficient = Math.pow(10, decimals);
		return Math.floor(value * coefficient) / coefficient;
	}

	/**
	 * Calcula el porcentaje de un numero con respecto a otro.
	 * Ambos numeros deben ser distintos de null. Además el total
	 * no debe ser cero. En caso de que un argumento sea nulo o
	 * el total sea cero devuelve null.	
	 */
	public static BigDecimal getPorcentaje(BigDecimal valor, BigDecimal total) {
		//precondiciones
		if(
				valor==null ||
				total==null ||
				total.equals(BigDecimal.ZERO)
			) return null;
		
		BigDecimal cien = new BigDecimal(100);		
		
		BigDecimal result = valor.multiply(cien); 
		result = result.divide(total, MathContext.DECIMAL64);
 	    
		return result;
	}
	/**
	 * Calcula un porcentaje del numero dado.
	 * @param porcentaje
	 * @param numeor
	 * @return
	 */
	public static BigDecimal getPorcientoDe(BigDecimal porcentaje, BigDecimal numero) {
		return porcentaje.divide(new BigDecimal(100)).multiply(numero);
	}
	
	/**
	 * Redondea un BigDecimal. Si el argumento value es null devuelve null.
	 * 
	 * @decimals	 = Cantidad de decimales a usar en el resultado. 
	 */
	public static BigDecimal round(BigDecimal value, int decimals){
		return value.setScale(decimals, RoundingMode.HALF_UP);
	}

}
