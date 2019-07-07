package com.fontar.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pragma.util.CollectionUtils;

public final class Cuit {
	
	//Patterns admitidos de cuit
	private static Pattern[] patternsDeCuit = {
		//11 caracteres sin espacio
		Pattern.compile("\\A([0-9]{2})([0-9]{8})([0-9]{1})\\z"),
		//agrupado 2 8 1 con espacio como separador
		Pattern.compile("\\A([0-9]{2}) ([0-9]{8}) ([0-9]{1})\\z"),
		//guionado en grupos 2-8-1
		Pattern.compile("\\A([0-9]{2})-([0-9]{8})-([0-9]{1})\\z")		
	};

	/**
	 * Devuelve true sii el codigo dado es un cuit o cuil valido
	 * para empresas o personas de ambos sexos. Acepta numeros
	 * sin separadores, separados con espacio o con guiones.
	 * @param cuit
	 * @return
	 */
	public static boolean esCuitValido(String cuit) {
		return esCuitDeEntidadValido(cuit) || esCuitDePersonaValido(cuit);
	}
	/**
	 * Devuelve true sii el codigo dado es un cuit valido para empresas 
	 * u organizaciones. Acepta numeros sin separadores, separados 
	 * con espacio o con guiones.
	 * @param cuit
	 * @return
	 */
	public static boolean esCuitDeEntidadValido(String cuit) {
		return esValido(aplanar(cuit), prefijosEntidades);
	}
	/**
	 * Devuelve true sii el codigo dado es un cuit o cuil valido
	 * de mujer. Acepta numeros sin separadores, separados con 
	 * espacio o con guiones.
	 * @param cuit
	 * @return
	 */
	public static boolean esCuitFemeninoValido(String cuit) {
		return esValido(aplanar(cuit), prefijosFemeninos);
	}
	/**
	 * Devuelve true sii el codigo dado es un cuit o cuil valido
	 * de hombre. Acepta numeros sin separadores, separados con 
	 * espacio o con guiones.
	 * @param cuit
	 * @return
	 */
	public static boolean esCuitMasculinoValido(String cuit) {
		return esValido(aplanar(cuit), prefijosMasculinos);
	}
	/**
	 * Devuelve true sii el codigo dado es un cuit o cuil valido
	 * de hombre o mujer. Acepta numeros sin separadores, separados con 
	 * espacio o con guiones.
	 * @param cuit
	 * @return
	 */
	public static boolean esCuitDePersonaValido(String cuit) {
		return esCuitFemeninoValido(cuit) || esCuitMasculinoValido(cuit);
	}
	/**
	 * Devuelve true sii el codigo dado es un cuit o cuil valido. 
	 * Toma el sexo como parámetro. Si el parametro sexo es null
	 * tiene el mismo efecto que esCuitDePersonaValido(String cuit).
	 * Acepta numeros sin separadores, separados con espacio o con guiones.
	 * @param cuit
	 * @param sexo
	 * @return
	 */
	public static boolean esCuitDePersonaValido(String cuit, Sexo sexo) {
		if(sexo==null) return esCuitDePersonaValido(cuit);
		else return esValido(aplanar(cuit), sexo==Sexo.FEMENINO? prefijosFemeninos : prefijosMasculinos);
	}
	/**
	 * Devuelve una cadena que representa el mismo cuit que recibe pero
	 * escrito de la forma de 13 caracteres guionados: 20-25654745-6 <br>
	 * Si el numero pasado no es un cuit sintacticamente valido devuelve null.
	 * Acepta cuits con guiones, espacios o sin separadores.
	 * @param cuit
	 * @return
	 */
	public static String normalizar(String cuit) {
		Matcher matcher = matcherFor(cuit);
		if(matcher==null) return null;
		return normalizar(matcher);
	}
	private static String normalizar(Matcher cuitMatcher) {
		return cuitMatcher.group(1)+"-"+cuitMatcher.group(2)+"-"+cuitMatcher.group(3);
	}
	
	public static String aplanar(String cuit) {
		Matcher matcher = matcherFor(cuit);
		if(matcher==null) return null;
		return aplanar(matcher); 
	}
	private static String aplanar(Matcher cuitMatcher) {
		return cuitMatcher.group(1)+cuitMatcher.group(2)+cuitMatcher.group(3); 
	}
	
	private static Matcher matcherFor(String cuit) {
		if(cuit==null) return null;
		for(Pattern pattern : patternsDeCuit) {
			Matcher ret = pattern.matcher(cuit);
			if(ret.matches()) return ret;			
		}
		return null;
	}
	
	private static final String[] prefijosMasculinos = {"20", "23", "24"};
	private static final String[] prefijosFemeninos = {"27", "23", "24"};
	private static final String[] prefijosEntidades = {"30", "33", "34"};
	
	private static final long[] coeficientes = {5, 4, 3, 2, 7, 6, 5, 4, 3, 2, 1};
	
	/**
	 * Chequea la validez de un cuit.
	 * @param cuit una cadena numerica de exactamente 11 posiciones.
	 * @param prefijos los prefijos admisibles que dependen del tipo de entidad.
	 * @return
	 */
	private static boolean esValido(String cuit, String[] prefijos) {
		if(cuit==null) return false;
		//Chequeo el prefijo
		String prefijo = cuit.substring(0, 2);
		if(!chequearPrefijo(prefijo, prefijos)) return false;
		//Chequeo la verificacion
		return verificar(cuit);
	}
	/**
	 * Chequea la validez de un cuit ante cualquier prefijo.
	 * @param cuit una cadena numerica de exactamente 11 posiciones.
	 * @return
	 */
	private static boolean esValido(String cuit) {
		if(cuit==null) return false;
		//Chequeo el prefijo
		String prefijo = cuit.substring(0, 2);
		if(
				!chequearPrefijo(prefijo, prefijosEntidades) &&
				!chequearPrefijo(prefijo, prefijosMasculinos) &&
				!chequearPrefijo(prefijo, prefijosFemeninos)
		) return false;
		//Chequeo la verificacion
		return verificar(cuit);
	}

	private static boolean chequearPrefijo(String prefijo, String[] prefijos) {
		for(int i = 0; i<prefijos.length; i++) {
			if(prefijo.equals(prefijos[i])) {
				return true;
			}
		}
		return false;
	}
	private static boolean verificar(String cuit) {
		long suma = 0;
		for(int i = 0; i<11; i++) {
			suma += coeficientes[i]* Long.parseLong(""+cuit.charAt(i));
		}
		return ((suma % 11)==0);
	}
	/**
	 * Crea un Cuit a partir de una cadena. Si la cadena no representa un
	 * numero de cuit valido devuelve null.
	 * @param cuit
	 * @return
	 */
	public static Cuit from(String cuit) {
		Matcher matcher = matcherFor(cuit);
		if(matcher==null) return null;
		else {
			//Chequeo que sea valido para algun tipo de entidad
			if(!esValido(aplanar(matcher))) return null;
			else return new Cuit(matcher);
		}
	}
	
	/* Miembros de instancia */
	private Matcher matcher;
	private Cuit(Matcher matcher) {
		this.matcher = matcher;
	}
	public String normalizado() {
		return normalizar(matcher);
	}
	public boolean esDePersona() {
		return !esDeEntidad();
	}
	public boolean esDeEntidad() {
		return CollectionUtils.contains(prefijosEntidades, matcher.group(1));
	}
	public boolean esValidoComoMasculino() {
		return CollectionUtils.contains(prefijosMasculinos, matcher.group(1));
	}
	public boolean esValidoComoFemenino() {
		return CollectionUtils.contains(prefijosFemeninos, matcher.group(1));
	}
	public boolean esValidoParaSexo(Sexo sexo) {
		if(sexo==null) return esDePersona();
		if(Sexo.FEMENINO.equals(sexo)) {
			return esValidoComoFemenino();
		} else {
			return esValidoComoMasculino();
		}
	}
	public String toString() {
		return normalizado();
	}
	
}